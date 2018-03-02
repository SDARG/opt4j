package org.opt4j.optimizers.ea;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.opt4j.core.Individual;

/**
 * Interface for the classes that sort given individuals based on objective
 * dominance.
 * 
 * @author Fedor Smirnov
 *
 */
public interface NonDominatedSorting {

	/**
	 * Applies non-dominated sorting to an input collection of individuals. They
	 * are hereby sorted into so-called fronts. A front contains individuals
	 * that are dominated by a certain number of other individuals in the given
	 * collection. For example, the first front contains the Pareto-optimal
	 * solutions that are not dominated by any individuals.
	 * 
	 * @param individuals
	 *            : The input collection of individuals
	 * @return : A list of the shape [f_1, f_2, ... , f_n] with f_i being the
	 *         individuals in the ith front and n being the number of fronts.
	 */
	public List<Collection<Individual>> generateFronts(Collection<Individual> individuals);

	/**
	 * Sweep the input collection of individuals (the first non-dominated front)
	 * and return a set of individuals with extreme objective values (in the
	 * optimization direction, i.e. the maxima of maximization objectives and
	 * the minima of the objectives that are to be minimized).
	 * 
	 * @param firstFront
	 *            : The input collection of individuals
	 * @return : The set of individuals with extreme values
	 */
	public Set<Individual> getExtremeIndividuals(Collection<Individual> firstFront);

}
