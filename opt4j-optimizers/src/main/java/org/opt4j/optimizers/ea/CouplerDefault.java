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
 

package org.opt4j.optimizers.ea;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.opt4j.core.Individual;
import org.opt4j.operators.crossover.Pair;

/**
 * The {@link CouplerDefault} uses the pairs based on their index in the list:
 * {@code p0+p1,p2+p3,etc.}.
 * 
 * @author glass, lukasiewycz
 * 
 */
public class CouplerDefault implements Coupler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.operator.mating.Coupler#getCouples(int, java.util.List)
	 */
	@Override
	public Collection<Pair<Individual>> getCouples(int size, List<Individual> parents) {
		List<Pair<Individual>> couples = new ArrayList<Pair<Individual>>();
		for (int i = 0; i < size; i++) {
			Individual first = parents.get((2 * i) % parents.size());
			Individual second = parents.get((2 * i + 1) % parents.size());
			Pair<Individual> pair = new Pair<Individual>(first, second);
			couples.add(pair);
		}
		return couples;
	}

}
