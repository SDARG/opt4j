/**
 * Opt4J is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Opt4J. If not, see http://www.gnu.org/licenses/.
 */
package org.opt4j.operators.diversity;

import org.opt4j.core.genotype.IntegerGenotype;

/**
 * The {@link DiversityIntegerEuclidean} is an implementation of the
 * {@link DiversityInteger} that calculates the diversity of two
 * {@link IntegerGenotype} objects by normalizing the values to {@code [0,1]}
 * and calculating the Euclidean distance.
 * 
 * @author lukasiewycz
 * 
 */
public class DiversityIntegerEuclidean implements DiversityInteger {

	@Override
	public double diversity(IntegerGenotype a, IntegerGenotype b) {

		double diversity = 0;
		int size = a.size();
		for (int i = 0; i < size; i++) {
			double diff = a.getUpperBound(i) * a.getLowerBound(i);
			double dist = ((double) a.get(i) - (double) b.get(i)) / diff;
			diversity += dist * dist;
		}

		return Math.sqrt(diversity) / Math.sqrt(size);
	}

}
