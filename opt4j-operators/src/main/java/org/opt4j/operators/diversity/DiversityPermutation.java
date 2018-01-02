/*******************************************************************************
 * Copyright (c) 2014 Opt4J
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
 

package org.opt4j.operators.diversity;

import org.opt4j.core.genotype.PermutationGenotype;

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
