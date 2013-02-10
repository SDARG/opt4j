/**
 * Opt4J is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with Opt4J. If not, see
 * http://www.gnu.org/licenses/.
 */

package org.opt4j.optimizer.ea;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.opt4j.common.archive.FrontDensityIndicator;
import org.opt4j.core.Individual;
import org.opt4j.start.Constant;

import com.google.inject.Inject;

/**
 * The {@link Hypervolume} is a {@link FrontDensityIndicator} based on determination of the hypervolume contribution.
 * The calculation is based on a normalization between 0 and 1 in each dimension and a transformation to a maximization
 * problem. Additionally an offset value (default 1) is added to each dimension.
 * 
 * 
 * @see SMSModule
 * @see "Zitzler, E., and Thiele, L. (1998): Multiobjective Optimization Using Evolutionary Algorithms - A Comparative
 *      Case Study. Parallel Problem Solving from Nature (PPSN-V), 292-301."
 * @author Ramin Etemaadi
 * @author Johannes Kruisselbrink
 * @author Rui Li
 * @author lukasiewycz
 * 
 */
public class Hypervolume implements FrontDensityIndicator {

	protected final double offset;

	/**
	 * Constructs a {@link Hypervolume}.
	 * 
	 * @param offset
	 *            the offset that is added to each dimension before the hypervolume is calculated
	 */
	@Inject
	public Hypervolume(@Constant(value = "offset", namespace = Hypervolume.class) double offset) {
		this.offset = offset;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.optimizer.ea.FrontDensityIndicator#getDensityValues(java.util .Collection)
	 */
	@Override
	public Map<Individual, Double> getDensityValues(Collection<Individual> individuals) {
		return getDensityValues(individuals, this.offset);
	}

	/**
	 * Calculates the density values for a front of non-dominated individuals based on the contribution of the
	 * {@link Hypervolume}.
	 * 
	 * A special approach for two dimension exists as well as a general approach for n dimensions.
	 * 
	 * @param individuals
	 *            the individuals
	 * @param offset
	 *            the offset
	 * @return the map of density values
	 */
	protected Map<Individual, Double> getDensityValues(Collection<Individual> individuals, double offset) {
		if (individuals.isEmpty()) {
			throw new IllegalArgumentException("Individuals is empty.");
		}

		List<Individual> orderIndividuals = new ArrayList<Individual>(individuals);
		int m = individuals.iterator().next().getObjectives().size();

		if (m < 2) {
			Map<Individual, Double> result = new HashMap<Individual, Double>();
			for (Individual individual : individuals) {
				result.put(individual, 0.0);
			}
			return result;
		} else if (m == 2) {
			return calculateHypervolumeContribution2D(orderIndividuals, offset);
		} else {
			return calculateHypervolumeContributionN(orderIndividuals, offset);
		}
	}

	/**
	 * Calculates the {@link Hypervolume} contribution for n dimensions.
	 * 
	 * @param individuals
	 *            the individuals
	 * @param offset
	 *            the offset
	 * @return the map of density values
	 */
	protected Map<Individual, Double> calculateHypervolumeContributionN(List<Individual> individuals, double offset) {
		Map<Individual, Double> result = new HashMap<Individual, Double>();
		List<double[]> front = invert(normalize(getMinValues(individuals)), offset);

		int m = front.get(0).length;

		double hvAll = calculateHypervolume(front, m);

		for (int i = 0; i < front.size(); i++) {
			List<double[]> iFront = new ArrayList<double[]>(front);
			iFront.remove(i);
			double iHv = calculateHypervolume(iFront, m);
			result.put(individuals.get(i), hvAll - iHv);
		}

		return result;
	}

	/**
	 * Calculates the {@link Hypervolume} contribution for two dimensions.
	 * 
	 * @param individuals
	 *            the individuals
	 * @param offset
	 *            the offset
	 * @return the map of density values
	 */
	protected Map<Individual, Double> calculateHypervolumeContribution2D(List<Individual> individuals, double offset) {
		Map<Individual, Double> result = new HashMap<Individual, Double>();
		List<double[]> front = invert(normalize(getMinValues(individuals)), offset);
		List<double[]> sorted = new ArrayList<double[]>(front);

		Collections.sort(sorted, new Comparator<double[]>() {
			@Override
			public int compare(double[] o1, double[] o2) {
				Double v1 = o1[0];
				Double v2 = o2[0];
				return v1.compareTo(v2);
			}
		});

		final int size = sorted.size();

		for (int i = 0; i < size; i++) {
			double diffX = sorted.get(i)[0] - (i > 0 ? sorted.get(i - 1)[0] : 0);
			double diffY = sorted.get(i)[1] - (i < size - 1 ? sorted.get(i + 1)[1] : 0);
			double contribution = diffX * diffY;

			result.put(individuals.get(front.indexOf(sorted.get(i))), contribution);
		}

		return result;
	}

	/**
	 * Transforms the non-dominated {@link Individual}s to a front where each objective is to be minimized.
	 * 
	 * @param individuals
	 *            the individuals
	 * @return the front of vectors that is minimized
	 */
	protected List<double[]> getMinValues(List<Individual> individuals) {
		List<double[]> minValues = new ArrayList<double[]>();
		for (Individual individual : individuals) {
			minValues.add(individual.getObjectives().array());
		}
		return minValues;
	}

	/**
	 * Normalizes a front of non-dominated solutions to values between 0 and 1.
	 * 
	 * @param front
	 *            the front of non-dominated solutions
	 * @return the normalized front
	 */
	protected List<double[]> normalize(List<double[]> front) {
		int m = front.get(0).length;

		double[] min = new double[m];
		double[] max = new double[m];

		Arrays.fill(min, +Double.MAX_VALUE);
		Arrays.fill(max, -Double.MAX_VALUE);

		for (double[] p : front) {
			for (int i = 0; i < m; i++) {
				min[i] = Math.min(min[i], p[i]);
				max[i] = Math.max(max[i], p[i]);
			}
		}

		for (int i = 0; i < m; i++) {
			if (min[i] == max[i]) {
				max[i]++;
			}
		}

		List<double[]> normalized = new ArrayList<double[]>();
		for (double[] p : front) {
			double[] pn = new double[m];
			for (int i = 0; i < m; i++) {
				pn[i] = (p[i] - min[i]) / (max[i] - min[i]);
			}
			normalized.add(pn);
		}
		return normalized;
	}

	/**
	 * Inverts (from a minimization to a maximization problem) a front of solutions and adds an offset value to each
	 * dimension.
	 * 
	 * @param front
	 *            the front of non-dominated solutions
	 * @param offset
	 *            the offset
	 * @return the inverted front
	 */
	protected List<double[]> invert(List<double[]> front, double offset) {
		int m = front.get(0).length;

		double[] nadir = new double[m];
		Arrays.fill(nadir, 1.0 + offset);

		List<double[]> inverted = new ArrayList<double[]>();
		for (double[] element : front) {
			double[] in = new double[element.length];
			for (int i = 0; i < element.length; i++) {
				in[i] = nadir[i] - element[i];
			}
			inverted.add(in);
		}

		return inverted;
	}

	/**
	 * Implements the {@link Hypervolume} calculations as proposed by Zitzler, E., and Thiele, L. (1998). All points
	 * have positive values in all dimensions and the hypervolume is calculated from 0.
	 * 
	 * @param front
	 *            the front of non-dominated solutions
	 * @param nObjectives
	 *            the number of objectives
	 * @return the hypervolume
	 */
	protected double calculateHypervolume(List<double[]> front, int nObjectives) {
		double volume = 0.0;
		double distance = 0.0;

		while (!front.isEmpty()) {
			List<double[]> nondominatedPoints = filterNondominatedSet(front, nObjectives - 1);
			double tempVolume;
			if (nObjectives < 3) {
				assert nondominatedPoints.size() > 0;
				tempVolume = nondominatedPoints.get(0)[0];
			} else {
				tempVolume = calculateHypervolume(nondominatedPoints, nObjectives - 1);
			}
			double tempDistance = surfaceUnchangedTo(front, nObjectives - 1);
			volume += tempVolume * (tempDistance - distance);
			distance = tempDistance;
			front = reduceNondominatedSet(front, nObjectives - 1, distance);
		}

		return volume;
	}

	protected List<double[]> filterNondominatedSet(List<double[]> front, int nObjectives) {
		List<double[]> nondominated = new ArrayList<double[]>();

		for (double[] p1 : front) {
			boolean dominated = false;
			for (double[] p2 : nondominated) {
				if (dominates(p2, p1, nObjectives)) {
					dominated = true;
					break;
				}
			}

			if (!dominated) {
				for (Iterator<double[]> it = nondominated.iterator(); it.hasNext();) {
					double[] p2 = it.next();
					if (dominates(p1, p2, nObjectives)) {
						it.remove();
					}
				}
				nondominated.add(p1);
			}
		}

		return nondominated;
	}

	protected boolean dominates(double[] p1, double[] p2, int nObjectives) {
		boolean strong = false;
		for (int i = 0; i < nObjectives; i++) {
			if (p1[i] > p2[i]) {
				strong = true;
			} else if (p1[i] < p2[i]) {
				return false;
			}
		}
		return strong;
	}

	protected double surfaceUnchangedTo(List<double[]> front, int objective) {
		assert front.size() > 0;
		double value = Double.MAX_VALUE;
		for (double[] p : front) {
			value = Math.min(value, p[objective]);
		}
		return value;
	}

	protected List<double[]> reduceNondominatedSet(List<double[]> front, int objective, double threshold) {
		List<double[]> result = new ArrayList<double[]>();

		for (double[] p : front) {
			if (p[objective] > threshold) {
				result.add(p);
			}
		}
		return result;
	}

}
