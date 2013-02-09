/**
 * Opt4J is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with Opt4J. If not, see http://www.gnu.org/licenses/. 
 */

package org.opt4j.operator.diversity;

import org.opt4j.genotype.PermutationGenotype;

/**
 * <p>
 * The {@link DiversityPermutation} is the interface for {@link Diversity}
 * operators for {@link PermutationGenotype}s.
 * </p>
 * <p>
 * Given are {@code n} elements {@code e in E} two permutations {@code p1, p2}
 * of these elements. The function {@code p(e)} returns the position of the
 * element {@code e} in the permutation {@code p}. This operator calculates the
 * following value:
 * </p>
 * 
 * <pre>
 * 	diversity(p1,p2)=sum[e in E] |p1(e)-p2(e)|/(n^2/2)
 * </pre>
 * 
 * This value is bounded by {@code 0} and {@code 1} since
 * 
 * <pre>
 * 	min { sum[e in E] |p1(e)-p2(e)| } = 0
 * </pre>
 * 
 * and
 * 
 * <pre>
 * 	max { sum[e in E] |p1(e)-p2(e)| } =
 * 		= 2 * sum[i=1 to n/2] (n-i)-(i-1) =
 * 		= 2 * sum[i=1 to n/2] (n-2i+1) =
 * 		= 2 * ( n^2/2 + n/2 - n/2(n/2+1)) =
 * 		= 2 * ( n^2/2 + n/2 - n^2/4 - n/2) =
 * 		= n^2/2
 * </pre>
 * 
 * .
 * 
 * @author lukasiewycz
 * 
 */
public class DiversityPermutation implements Diversity<PermutationGenotype<?>> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.operator.diversity.Diversity#diversity(org.opt4j.core.Genotype,
	 * org.opt4j.core.Genotype)
	 */
	@Override
	public double diversity(PermutationGenotype<?> a, PermutationGenotype<?> b) {
		int n = a.size();

		int sum = 0;
		for (int i = 0; i < n; i++) {
			Object o = a.get(i);
			int j = b.indexOf(o);
			sum += Math.abs(i - j);
		}

		double diversity = sum / (n * n / 2.0);

		assert (diversity >= 0);
		assert (diversity <= 1);

		return diversity;
	}

}
