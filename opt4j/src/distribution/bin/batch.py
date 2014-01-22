#!/usr/bin/python
'''
@author: Martin Lukasiewycz

opt4j batch program

features:
 - execute xml configurations in a batch 
   * automatically replaces __RUN__ by the current run counter (use for the seed)
 - evaluate the statistics
   * use edominance and hypervolume indicators
   * determine the optimal results of each method
   
folder organization:
 - basefolder
   * configs - the xml configurations
   * runs - the results of the optimization runs (tsv)
   * results - the results of the evaluation

'''
from multiprocessing import Pool
from bisect import insort
from math import sqrt, floor
from xml.etree.ElementTree import ElementTree, SubElement
from optparse import OptionParser
import os
import shutil
import subprocess
import time
import signal


PROG = [os.path.abspath('opt4j')]

FOLDER_CONFIGS = "configs"
FOLDER_RUNS = "runs"
FOLDER_RESULTS = "results"

''' available: avg, dev, min, max, median, lq, uq '''
STATS = ["avg", "dev"]
COVSTATS = ["avg", "dev", "min", "max", "median", "lq", "uq"] 

MODULESREMOVE = ['org.opt4j.core.common.logger.LoggerModule', 'org.opt4j.viewer.ViewerModule']
RUNS = 30
PROCESSES = 1
EXPROCESSES = 1
MIN = True
MAX = False
DEADLINE = None

''' objectives are determined in the function readRun '''
objectives = None
gNadir = None

''' assumes that files are named 'method.RUN.tsv' '''
def getGroup(file):
    str = os.path.split(file)[1]
    return str[0:str.index('.')]

def statistics(values):
    length = len(values)
    average = sum(values) / length
    deviation = sqrt(sum([(value - average) ** 2 for value in values]) / (length - 1)) if length > 1 else 0
    sortedvalues = sorted(list(values))
    min = sortedvalues[0]
    max = sortedvalues[length - 1]
    median = sortedvalues[int(floor(length / 2))]
    lq = sortedvalues[int(floor(length / 4))]
    uq = sortedvalues[int(floor(3 * length / 4))]
    return {"avg":average, "dev":deviation, "min":min, "max":max, "median":median, "lq":lq, "uq":uq}

class Objectives:
    def __init__(self, values):
        self.values = values
        self.names = [value[:-5] for value in values]
        self.dirs = []
        for value in values:
            string = value.strip()[-5:] # strip whitespace
            if string == '[MIN]':
                self.dirs.append(MIN)
            elif string == '[MAX]':
                self.dirs.append(MAX)
            else:
                raise ValueError('Invalid dir: "%s"' % string)
    def __str__(self):
        return str(self.names) + ' ' + str(['MIN' if x == True else 'MAX' for x in self.dirs])
    def normalized(self):
        return Objectives([name + '[MIN]' for name in self.names])

class Sample:
    def __init__(self, values):
        self.values = values
        self.minvalues = self.invValues(self.values)
        self.hash = hash(sum([x for x in self.values if isnumber(x)]))
    def __str__(self):
        return str(self.values)
    def __len__(self):
        return len(self.values)
    def __hash__(self):
        return self.hash
    def __eq__(self, o):
        return self.values == o.values
    def equal(self, other):
        return self.minvalues == other.minvalues;
    def weakdominates(self, other):
        return all(sv <= ov for sv, ov in zip(self.minvalues, other.minvalues))
    def strongdominates(self, other):
        return self.weakdominates(self, other) and not self.equal(self, other)  
    def __repr__(self):
        return ((self.minvalues))
    @staticmethod
    def invValues(values):
        global objectives
        return [v if d == MIN else -v for v, d in zip(values, objectives.dirs)]
    
class Run:
    def __init__(self, name):
        self.name = name
        self.iterations = []
        self.evaluations = {}
        self.runtime = {}
        self.samples = {}
        self.cache = {}
    def __str__(self):
        global objectives
        return self.name + ' ' + str(len(self.iterations)) + ' ' + str(self.getSampleSize()) + ' ' + str(objectives)
    def addSample(self, iteration, evaluations, runtime, sample):
        if iteration not in self.iterations:
            insort(self.iterations, iteration)
            self.samples[iteration] = []
        self.evaluations[iteration] = evaluations
        self.runtime[iteration] = runtime
        self.samples[iteration].append(self.newSample(sample))
    def getSampleSize(self):
        return sum([len(s) for s in self.samples.values()])
    def getAllSamples(self):
        return list(set([sample for samples in self.samples.values() for sample in samples]))
    def getGroup(self):
        return getGroup(self.name)
    def __hash__(self):
        return hash(self.name)
    def __eq__(self, other):
        return self.name == other.name
    def newSample(self, values):
        sample = Sample(values)
        if sample in self.cache:
            return self.cache[sample]
        else:
            self.cache[sample] = sample
            return sample
    
class Result:
    def __init__(self):
        self.dict = {}
        self.inds = set()
    def addIndicator(self, indicator):
        self.inds.add(indicator)
    def add(self, iteration, indicator, value):     
        self.dict[(iteration, indicator)] = value
    def iterations(self):
        return sorted(list(set([iteration for (iteration, _) in self.dict.keys()])))
    def indicators(self):
        return sorted(list(self.inds))
    def get(self, iteration, indicator):
        return self.dict[(iteration, indicator)]

def normalizeSample(sample, zenit, nadir):
    nvalues = [1 + (value - mi) / (ma - mi) for mi, value, ma in zip(zenit.minvalues, sample.minvalues, nadir.minvalues)]
    return Sample(Sample.invValues(nvalues))

def normalizeSamples(samples, zenit, nadir):
    return [normalizeSample(sample, zenit, nadir) for sample in samples]

def normalize(run, minr, maxr):
    global objectives
    nrun = Run(run.name)
    for iteration in run.iterations:
        for sample in run.samples[iteration]:
            nvalues = [1 + (value - mi) / (ma - mi) for mi, value, ma in zip(minr.minvalues, sample.minvalues, maxr.minvalues)]
            nrun.addSample(iteration, run.evaluations[iteration], run.runtime[iteration], Sample.invValues(nvalues))
    return nrun
    
def readRun(filename):
    global objectives
    global gNadir
    
    with open(filename, 'r') as file:
        run = None
        permutation = None
        for line in file:
            parts = line.split('\t') 
            if isnumber(parts[0]):
                parts = [float(x) if isnumber(x) else None for x in parts]
                if all([isnumber(x) for x in parts]):
                    run.addSample(parts[0], parts[1], parts[2], [parts[3:][i] for i in permutation])
                elif gNadir is not None:
                    run.addSample(parts[0], parts[1], parts[2], [x for x in gNadir])
                else:
                    run.addSample(parts[0], parts[1], parts[2], [None for i in permutation])
            else:
                if not line.isspace():
                  parts =  [x.replace(" ", "_") for x in parts]
                  if objectives is None: objectives = Objectives(parts[3:])
                  permutation = [parts[3:].index(e) for e in objectives.values]
                  run = Run(filename)
        return run
    
def isfeasible(sample):
    return all(isnumber(x) for x in sample.values)

def getNonDominated(samples):
    nondominated = set()
    for sample in samples:
        if isfeasible(sample):
            if not any(nondom.weakdominates(sample) for nondom in nondominated):
                nondominated -= set([nondom for nondom in nondominated if sample.weakdominates(nondom)])
                nondominated |= set([sample])            
    return list(nondominated)

def single(samples):
    for sample in samples:
        return sample
    
def getComp(samples, comp):
    global objectives
    minvalues = [comp(elements) for elements in zip(*[sample.minvalues for sample in samples if isfeasible(sample)])]
    sample = Sample(Sample.invValues(minvalues))
    return sample

def getMin(samples):
    return getComp(samples, min)

def getMax(samples):
    return getComp(samples, max)

def writeRun(run, filename):
    global objectives
    out = open(filename, 'w')
    out.write('\t'.join(['iteration', 'evaluations', 'runtime'] + [obj for obj in objectives.values]) + "\n")
    for iteration in run.iterations:
        evaluations = run.evaluations[iteration]
        runtime = run.runtime[iteration]
        for sample in run.samples[iteration]:
            out.write('\t'.join(str(v) for v in [iteration, evaluations, runtime] + [value for value in sample.values]) + "\n")

''' determines if p1 strong dominates p2 '''
def hv_dominates(p1, p2, nObjectives):
    p1 = p1[0:nObjectives]
    p2 = p2[0:nObjectives]
    return all(x1 >= x2 for x1, x2 in zip(p1, p2)) and not p1 == p2

''' determines all points that are not dominated up to a given objective '''
def hv_filterNondominatedSet(front, nObjectives):
    nondominated = []
    for p1 in front:
        if not any(hv_dominates(p2, p1, nObjectives) for p2 in nondominated):
            nondominated = [p2 for p2 in nondominated if not hv_dominates(p1, p2, nObjectives)] + [p1]
    return nondominated

''' calculate next value regarding dimension 'objective' '''
def hv_surfaceUnchangedTo(front, objective):
    assert len(front) > 0
    return min([p[objective] for p in front])

''' returns a subset of the front that satisfies a threshold '''
def hv_reduceNondominatedSet(front, objective, threshold):
    return [p for p in front if p[objective] > threshold]

''' calculate the hypervolume '''
def hv_calculateHypervolume(front, nObjectives):
    volume = 0
    distance = 0
    while len(front) > 0:
        nondominatedPoints = hv_filterNondominatedSet(front, nObjectives - 1)
        if nObjectives < 3:
            assert len(nondominatedPoints) > 0
            tempVolume = nondominatedPoints[0][0]
        else:
            tempVolume = hv_calculateHypervolume(nondominatedPoints, nObjectives - 1)
    
        tempDistance = hv_surfaceUnchangedTo(front, nObjectives - 1)
        volume += tempVolume * (tempDistance - distance)
        distance = tempDistance
        front = hv_reduceNondominatedSet(front, nObjectives - 1, distance)
    return volume

def hypervolume(samples, nadir, add=0.0):
    return hv_calculateHypervolume([[n + add - s for n, s in zip(nadir.minvalues, sample.minvalues)] for sample in samples], len(nadir))

def edominance(samples, reference):
    A = [s.minvalues for s in samples]
    B = [r.minvalues for r in reference]
    ''''v = max(min(max(x/y for x,y in zip(a,b)) for a in A) for b in B)'''
    v = max(min(max(x - y for x, y in zip(a, b)) for a in A) for b in B)
    return v

def coverage(a, b):
    dominated = 0.0
    for bsample in b:
        if any(asample.weakdominates(bsample) for asample in a):
            dominated += 1.0
    return dominated / len(b) if len(b) > 0 else 1

def writeSamples(samples, filename):
    run = Run("void")
    for sample in samples:
        run.addSample(0, 0, 0, sample.values)
    writeRun(run, filename)

def writeResult(result, filename):
    out = open(filename, 'w')
    iterations = result.iterations();
    indicators = result.indicators();
    out.write('\t'.join(['iteration'] + [str(indicator) for indicator in indicators]) + '\n')
    for iteration in iterations:
        out.write('\t'.join([str(iteration)] + [str(result.get(iteration, indicator)) for indicator in indicators]) + '\n')

def isnumber(s):
    try:
        float(s)
        return True
    except ValueError:
        return False
    except TypeError:
        return False

def average(sources, dest):

    out = open(dest, 'w')
    input = [open(source, 'r') for source in sources]
    
    while True:
        lines = [value for value in [file.readline().split() for file in input] if value]
        if not lines:
            break
        first = lines[0][0]
        if not isnumber(first):
            outline = [value + "(" + stat + ")" for value in lines[0] for stat in STATS]
        else:
            outline = []
            for values in zip(*lines):
                statitics = statistics([float(value) for value in values])
                outline += [str(statitics[stat]) for stat in STATS]
        out.write('\t'.join(outline) + '\n')
    
class DirectoryException(Exception):
    def __init__(self, value):
        self.value = value
    def __str__(self):
        return str(self.value)

def assertdir(dir):
    if not os.path.exists(dir):
        raise DirectoryException('directory ' + str(dir) + ' does not exist')
    
def rmdir(dir):
    if os.path.exists(dir):
        shutil.rmtree(dir)
        
def mkdir(dir):
    if not os.path.exists(dir):
        os.makedirs(dir)
        
def replaceString(filename, old, new):
    f = open(filename, 'r')
    newlines = [line.replace(str(old), str(new)) for line in f]
    f.close()
    outfile = open(filename, 'w')
    outfile.writelines(newlines)
    outfile.close()
         
def doResultsIndicators(file, runsfolder, zenit, nadir, hypervolumeopt, normReference, resultsfoldertmp):
    global objectives
    print('\tdetermine indicators for ' + file)
    run = readRun(os.path.join(runsfolder, file))
    result = Result()
    
    for objective in objectives.names:
        result.addIndicator('objective_' + objective)
    
    for iteration in run.iterations:
        samples = run.samples[iteration]    
        best = getMin(samples)
        for objective, value in zip(objectives.names, best.values):
            result.add(iteration, 'objective_' + objective, value)
    run = normalize(run, zenit, nadir)
    normNadir = normalizeSample(nadir, zenit, nadir)
    
    result.addIndicator('evaluations')
    result.addIndicator('runtime')
    result.addIndicator('indicator_hypervolume')
    result.addIndicator('indicator_dominance')
    
    for iteration in run.iterations:
        samples = run.samples[iteration]
        result.add(iteration, 'evaluations', run.evaluations[iteration])
        result.add(iteration, 'runtime', run.runtime[iteration])
        result.add(iteration, 'indicator_hypervolume', hypervolumeopt - hypervolume(samples, normNadir, 0.1))
        result.add(iteration, 'indicator_dominance', edominance(samples, normReference))
    writeResult(result, os.path.join(resultsfoldertmp, file))
    
def doReadFile(file, resultsfoldertmp):
    print('\tread file ' + file)
    run = readRun(file)
    runsamples = run.getAllSamples()
    runnondom = getNonDominated(runsamples)
    if len(runnondom) > 0:
        nadir = getMax(runsamples)
        zenit = getMin(runsamples)
    else:
        nadir = zenit = None
    writeSamples(runnondom, resultsfoldertmp + '/' + os.path.basename(file) + '.ref')
    return (zenit, nadir, runnondom)
    
def calculate(func, args):
    return func(*args)
def calculatestar(args):
    return calculate(*args)
    
def parallel(tasks, threads):
    if threads == 1:
        return [task[0](*task[1]) for task in tasks]
    else:
        imap_it = Pool(threads).imap(calculatestar, tasks)
        return [x for x in imap_it]
        
def doResults(basefolder):
    print('start results')    
    runsfolder = os.path.join(basefolder, FOLDER_RUNS)
    resultsfolder = os.path.join(basefolder, FOLDER_RESULTS)
    resultsfoldertmp = os.path.join(resultsfolder, "tmp")
    
    assertdir(runsfolder)
    mkdir(resultsfolder)
    mkdir(resultsfoldertmp)
           
    print('\tread all input files in ' + runsfolder)
    
    files = os.listdir(runsfolder)
    groups = {}
    for file in files:
        group = getGroup(file)
        groups[group] = [os.path.join(runsfolder, file) for file in os.listdir(runsfolder) if getGroup(file) == group]
    
    references = {}
    nadir = []
    zenit = []
    
    for group, groupfiles in groups.items():
        print('\tread group ' + group)
        tasks = [(doReadFile, (file, resultsfoldertmp)) for file in groupfiles]
        result = parallel(tasks, 1)
        
        
        '''result = [doReadFile(file,resultsfoldertmp) for file in groupfiles]; sequential '''
        
        references[group] = []
        for (zen, nad, nondom) in result:
            if len(nondom) > 0:
                references[group] = getNonDominated(references[group] + nondom)
                zenit.append(zen)
                nadir.append(nad)
        references[group] = sorted(references[group], key=lambda reference: reference.minvalues)

    nadir = getMax(nadir)
    zenit = getMin(zenit)
    
    ''' adjust nadir in case an objective is equal for nadir and zenit '''
    nadmin = [x if x < y else x + 1 for x, y in zip(nadir.minvalues, zenit.minvalues)]
    nadir = Sample(Sample.invValues(nadmin));
    
    global gNadir
    gNadir = nadir.values
    
    reference = []
    for samples in references.values():
        reference = getNonDominated(reference + samples)
    reference = sorted(reference, key=lambda reference: reference.minvalues)
    
    print('\twrite references to ' + resultsfolder)
    writeSamples(reference, resultsfolder + "/reference")
    for group in groups:
        writeSamples(references[group], resultsfolder + "/" + group + ".ref")
    
    normReference = normalizeSamples(reference, zenit, nadir)
    normNadir = normalizeSample(nadir, zenit, nadir)
        
    print('\treference set size ' + str(len(normReference)))
    hypervolumeopt = hypervolume(normReference, normNadir, 0.1)
    print('\thypervolume reference ' + str(hypervolumeopt))

    tasks = [(doResultsIndicators, (file, runsfolder, zenit, nadir, hypervolumeopt, normReference, resultsfoldertmp)) for file in files]
    parallel(tasks, PROCESSES)

    ''' determine stats ''' 
        
    resultfiles = os.listdir(resultsfoldertmp)
    resultgroups = set([getGroup(file) for file in resultfiles])

    map = dict([[group, [file for file in resultfiles if getGroup(file) == group and file.endswith('.tsv')]] for group in resultgroups])
    
    print('\tdetermine stats')
    for group, gresultfiles in map.items():
        gresultfiles = [os.path.join(resultsfoldertmp, file) for file in gresultfiles]
        statfile = resultsfolder + "/" + group + ".stats"
        average(gresultfiles, statfile)
        
    ''' determine coverage '''
    
    with open(resultsfolder + "/coverage", 'w') as covfile:        
        covfile.write('\t'.join(["method1", "method2"] + [stat for stat in COVSTATS]) + '\n')
        
        groupvalues = sorted(groups.keys())        
        for (group1, group2) in [(g1, g2) for g1 in groupvalues  for g2 in groupvalues if g1 != g2]:
            values = []
            print('\tdetermine coverage ' + group1 + ' ' + group2)
            for (file1, file2) in [(f1, f2) for f1 in groups[group1] for f2 in groups[group2]]:
                run1 = readRun(resultsfoldertmp + '/' + os.path.basename(file1) + '.ref')
                run2 = readRun(resultsfoldertmp + '/' + os.path.basename(file2) + '.ref')
                c = coverage(run1.getAllSamples(), run2.getAllSamples())
                values.append(c)
            stats = statistics(values)
            covfile.write('\t'.join([group1, group2] + [str(stats[stat]) for stat in COVSTATS]) + '\n')
    
    '''rmdir(resultsfoldertmp)'''
    print('results successful')


def doRun(configfile):
    print('\texecute config ' + str(configfile))
    if DEADLINE == None:
      return subprocess.call(PROG + ["-s", configfile])
    else:
      process = subprocess.Popen(PROG + ["-s", configfile])
      
      end = time.time() + DEADLINE
      while (True):
        returncode = process.poll()
        if returncode != None:
          return returncode
        if time.time() > end:
          pid = process.pid
          try:
            os.kill(pid, signal.SIGTERM)
          except OSError:
            pass
          del process
          return -1 
        time.sleep(max(DEADLINE / 100.0, 1.0))
        

def doRuns(basefolder):        
    print('start runs')
    
    configsfolder = os.path.join(basefolder, FOLDER_CONFIGS)
    runsfolder = os.path.join(basefolder, FOLDER_RUNS)
    
    assertdir(configsfolder)
    mkdir(runsfolder)
    
    configsfoldertmp = os.path.join(configsfolder, "tmp")
    rmdir(configsfoldertmp)
    mkdir(configsfoldertmp)

    for config in [os.path.join(configsfolder, file) for file in os.listdir(configsfolder) if file.endswith(".xml")]:
        for nrun in range(RUNS):
            configfile = os.path.split(config)[1]
            group = configfile[0:configfile.index('.')]
            runfile = os.path.join(runsfolder, group + '.' + str(nrun) + '.tsv')
            tree = ElementTree()
            tree.parse(config)
            application = tree.getroot()
            for module in [module for module in tree.findall("module") if module.attrib['class'] in MODULESREMOVE]: application.remove(module)         
            logger = SubElement(application, 'module', {'class':'org.opt4j.core.common.logger.LoggerModule'})
            property1 = SubElement(logger, 'property', {'name':'filename'})
            property1.text = runfile
            property2 = SubElement(logger, 'property', {'name':'printInfeasible'})
            property2.text = 'true'	
            seed = SubElement(application, 'module', {'class':'org.opt4j.core.common.random.RandomModule'})
            seedProperty1 = SubElement(seed, 'property', {'name':'type'})
            seedProperty1.text = 'MERSENNE_TWISTER'
            seedProperty2 = SubElement(seed, 'property', {'name':'usingSeed'})
            seedProperty2.text = 'true'
            seedProperty3 = SubElement(seed, 'property', {'name':'seed'})
            seedProperty3.text = str(nrun)
            file = os.path.join(configsfoldertmp, group + "." + str(nrun) + ".config.xml")
            tree.write(file)
            replaceString(file, "__RUN__", nrun)
            
    tasks = [(doRun, (configfile,)) for configfile in [os.path.join(configsfoldertmp, file) for file in os.listdir(configsfoldertmp)]]  
    parallel(tasks, EXPROCESSES)  
    rmdir(configsfoldertmp)

    
def doClear(basefolder):
    runsfolder = os.path.join(basefolder, FOLDER_RUNS)
    resultsfolder = os.path.join(basefolder, FOLDER_RESULTS)
    configsfoldertmp = os.path.join(basefolder, FOLDER_CONFIGS + "tmp")
    rmdir(runsfolder)
    rmdir(resultsfolder)
    rmdir(configsfoldertmp)
    
    
def resetObjectives():
    global objectives
    objectives = None
    
if __name__ == '__main__':
    '''print(os.getcwd())'''
    
    help='''usage: %prog [options] <directories>", version="%prog 1.0 
     
    Each directory in <directories> contains a folder configs that has to contain 
    one or more xml configuration files which can be created from the configurator GUI.
    Note that each configuration file has to solve a problem (usually the same problem) 
    with identical objectives.
    
    The batch script will execute run each configuration file n times and put the
    results in the runs folder.
    
    Finally, the statistics (incl. hypervolume and e-dominance) will be calculated
    based on the runs and written in the results folder.'''

    
    parser = OptionParser(usage=help)
    parser.add_option("-c", "--clear", action="store_true", default=False, help="clear all previous runs and results")
    parser.add_option("-r", "--runs", action="store_true", default=False, help="perform the runs")
    parser.add_option("-s", "--results", action="store_true", default=False, help="determine the results")
    parser.add_option("-n", metavar="RUNS", type="int", default=RUNS, help="set the number of runs [default: %default]")
    parser.add_option("-p", metavar="PROCESSES", type="int", default=PROCESSES, help="set the number of processes for the result determination [default: %default]")
    parser.add_option("-e", metavar="EXPROCESSES", type="int", default=EXPROCESSES, help="set the number of execution processes for the runs [default: %default]")
    parser.add_option("-j", metavar="PROG", default=PROG, help="set the command to use for the runs [default: %default]")
    parser.add_option("-d", metavar="DEADLINE", type="int", default=DEADLINE, help="set the deadline per run in seconds [default: %default]")
    (options, args) = parser.parse_args()
    
    if len(args) == 0:
        parser.parse_args(["-h"])
    else:
        if not options.clear and not options.runs and not options.results:
            options.clear, options.runs, options.results = True, True, True
        if options.n != None:
            RUNS = options.n
        if options.p != None:
            PROCESSES = options.p
        if options.e != None:
            EXPROCESSES = options.e
        if options.j != None:
            PROG = options.j.split()
        if options.d != None:
            DEADLINE = options.d

        for basefolder in args:
            try:
                resetObjectives()
                assertdir(basefolder)
                if options.clear: doClear(basefolder)
                if options.runs: doRuns(basefolder)
                if options.results: doResults(basefolder) 
            except DirectoryException as e:
                print('Skip directory ' + str(basefolder) + ': ' + str(e))
    
