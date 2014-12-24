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
 

package org.opt4j.benchmarks.lotz;

import static org.opt4j.core.Objective.Sign.MAX;

import org.opt4j.benchmarks.BinaryString;
import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;
import org.opt4j.core.problem.Evaluator;

/**
 * The {@link LOTZEvaluator}.
 * 
 * @author lukasiewycz
 * 
 */
public class LOTZEvaluator implements Evaluator<BinaryString> {

	protected final Objective onesObj = new Objective("LeadingOnes", MAX);
	protected final Objective zerosObj = new Objective("TrailingZeros", MAX);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.problem.Evaluator#evaluate(org.opt4j.core.Phenotype)
	 */
	@Override
	public Objectives evaluate(BinaryString phenotype) {
		int ones = 0;
		int size = phenotype.size();
		for (; ones < size; ones++) {
			boolean value = phenotype.get(ones);
			if (!value) {
				break;
			}
		}
		int zeros = 0;
		for (; zeros < size; zeros++) {
			boolean value = phenotype.get(size - zeros - 1);
			if (value) {
				break;
			}
		}

		Objectives objectives = new Objectives();
		objectives.add(onesObj, ones);
		objectives.add(zerosObj, zeros);
		return objectives;
	}
}
