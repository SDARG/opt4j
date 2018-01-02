package org.opt4j.tutorial.optimizer;

import java.util.Collection;

import org.opt4j.core.Genotype;
import org.opt4j.core.Individual;
import org.opt4j.core.IndividualFactory;
import org.opt4j.core.optimizer.IterativeOptimizer;
import org.opt4j.core.optimizer.Population;
import org.opt4j.core.optimizer.TerminationException;
import org.opt4j.operators.copy.Copy;
import org.opt4j.operators.mutate.Mutate;
import org.opt4j.optimizers.ea.Selector;

import com.google.inject.Inject;

public class MutateOptimizer implements IterativeOptimizer {

	protected final IndividualFactory individualFactory;

	protected final Mutate<Genotype> mutate;

	protected final Copy<Genotype> copy;

	protected final Selector selector;

	private final Population population;

	public static final int POPSIZE = 100;

	public static final int OFFSIZE = 25;

	@Inject
	public MutateOptimizer(Population population, IndividualFactory individualFactory,
			Selector selector, Mutate<Genotype> mutate, Copy<Genotype> copy) {
		this.individualFactory = individualFactory;
		this.mutate = mutate;
		this.copy = copy;
		this.selector = selector;
		this.population = population;
	}

	public void initialize() throws TerminationException {
		selector.init(OFFSIZE + POPSIZE);
	}

	public void next() throws TerminationException {
		if (population.isEmpty()) {
			for (int i = 0; i < POPSIZE; i++) {
				population.add(individualFactory.create());
			}
		} else {
			if (population.size() > POPSIZE) {
				Collection<Individual> lames = selector.getLames(population.size() - POPSIZE,
						population);
				population.removeAll(lames);
			}

			Collection<Individual> parents = selector.getParents(OFFSIZE, population);

			for (Individual parent : parents) {
				Genotype genotype = copy.copy(parent.getGenotype());
				mutate.mutate(genotype, 0.1);

				Individual child = individualFactory.create(genotype);
				population.add(child);
			}
		}
	}
}
