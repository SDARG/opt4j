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

package org.opt4j.benchmark.lotz;

import static org.opt4j.core.Objective.Sign.MAX;

import org.opt4j.benchmark.BinaryString;
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
