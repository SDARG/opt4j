/*******************************************************************************
 * Copyright (c) 2026 Opt4J
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *******************************************************************************/

package org.opt4j.gatekeeper;

import com.google.inject.Inject;
import org.opt4j.core.Individual;
import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;
import org.opt4j.core.Value;
import org.opt4j.core.optimizer.Archive;
import org.opt4j.core.optimizer.Optimizer;
import org.opt4j.core.optimizer.OptimizerIterationListener;
import org.opt4j.core.optimizer.OptimizerStateListener;
import org.opt4j.core.start.Constant;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EvaluationLogger implements OptimizerIterationListener, OptimizerStateListener {

    private final Archive archive;
    private final DominancePredictionManager manager;
    private final String filename;
    private BufferedWriter writer;
    private long startTime;
    private boolean headerWritten = false;
    private List<Objective> sortedObjectives;

    @Inject
    public EvaluationLogger(Archive archive, DominancePredictionManager manager,
                                @Constant(value = "tsvFilename", namespace = NeuralGatekeeperModule.class) String filename) {
        this.archive = archive;
        this.manager = manager;
        this.filename = filename == null ? "run.tsv" : filename;
    }

    @Override
    public void optimizationStarted(Optimizer optimizer) {
        startTime = System.currentTimeMillis();
        try {
            File file = new File(filename);
            if (file.getParentFile() != null) file.getParentFile().mkdirs();
            writer = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public void optimizationStopped(Optimizer optimizer) {
        if (writer != null) {
            try { writer.close(); } catch (IOException e) { e.printStackTrace(); }
        }
    }

    @Override
    public void iterationComplete(int iteration) {
        if (writer == null) return;
        try {
            if (!headerWritten && !archive.isEmpty()) writeHeader(archive.iterator().next().getObjectives());
            if (headerWritten) {
                int trueEvaluations = manager.getEvaluatedCount();
                double runtime = (System.currentTimeMillis() - startTime) / 1000.0;
                for (Individual ind : archive) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(iteration).append("\t").append(trueEvaluations).append("\t").append(runtime);
                    for (Objective obj : sortedObjectives) {
                        sb.append("\t");
                        Value<?> v = ind.getObjectives().get(obj);
                        if (v == null || v == Objective.INFEASIBLE) sb.append("INFEASIBLE");
                        else sb.append(v.getDouble());
                    }
                    writer.write(sb.toString());
                    writer.newLine();
                }
                writer.flush();
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void writeHeader(Objectives sampleObjectives) throws IOException {
        sortedObjectives = new ArrayList<>(sampleObjectives.getKeys());
        Collections.sort(sortedObjectives, Comparator.comparing(Objective::getName));
        StringBuilder sb = new StringBuilder("iteration\tevaluations\truntime[s]");
        for (Objective obj : sortedObjectives) {
            sb.append("\t").append(obj.getName());
            sb.append(obj.getSign() == Objective.Sign.MIN ? "[MIN]" : "[MAX]");
        }
        writer.write(sb.toString());
        writer.newLine();
        headerWritten = true;
    }
}
