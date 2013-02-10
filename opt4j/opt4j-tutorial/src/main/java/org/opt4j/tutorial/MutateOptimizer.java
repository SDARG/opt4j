package org.opt4j.tutorial;

import java.util.Collection;

import org.opt4j.core.Genotype;
import org.opt4j.core.Individual;
import org.opt4j.core.IndividualFactory;
import org.opt4j.core.optimizer.IterativeOptimizer;
import org.opt4j.core.optimizer.Population;
import org.opt4j.core.optimizer.TerminationException;
import org.opt4j.operator.copy.Copy;
import org.opt4j.operator.mutate.Mutate;
import org.opt4j.optimizer.ea.Selector;

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
	public MutateOptimizer(Population population, IndividualFactory individualFactory, Selector selector,
			Mutate<Genotype> mutate, Copy<Genotype> copy) {
		this.individualFactory = individualFactory;
		this.mutate = mutate;
		this.copy = copy;
		this.selector = selector;
		this.population = population;
	}

	@Override
	public void initialize() throws TerminationException {
		for (int i = 0; i < OFFSIZE + POPSIZE; i++) {
			population.add(individualFactory.create());
		}
	}

	@Override
	public void next() throws TerminationException {
		Collection<Individual> lames = selector.getLames(OFFSIZE, population);
		population.removeAll(lames);

		Collection<Individual> parents = selector.getParents(OFFSIZE, population);

		for (Individual parent : parents) {
			Genotype genotype = copy.copy(parent.getGenotype());
			mutate.mutate(genotype, 0.1);

			Individual child = individualFactory.create(genotype);
			population.add(child);
		}
	}
}
