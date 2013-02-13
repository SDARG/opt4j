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

import org.opt4j.core.genotype.BooleanGenotype;

/**
 * The {@link DiversityBooleanFraction} calculates the {@link Diversity} between
 * two {@link BooleanGenotype}s by calculating the fraction of different vector
 * entries.
 * 
 * @author glass
 * 
 */
public class DiversityBooleanFraction implements DiversityBoolean {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.operator.diversity.Diversity#diversity(org.opt4j.core.Genotype,
	 * org.opt4j.core.Genotype)
	 */
	@Override
	public double diversity(BooleanGenotype a, BooleanGenotype b) {

		double diversity = 0;
		for (int i = 0; i < a.size(); i++) {
			if (a.get(i) != b.get(i)) {
				diversity++;
			}
		}

		return diversity / a.size();

	}

}
