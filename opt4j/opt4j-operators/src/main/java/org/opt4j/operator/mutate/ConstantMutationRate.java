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

package org.opt4j.operator.mutate;

import org.opt4j.start.Constant;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Constant mutation rate. The {@link MutationRate} is set once.
 * 
 * @author lukasiewycz
 * 
 */
@Singleton
public class ConstantMutationRate implements MutationRate {

	protected final double rate;

	/**
	 * Constructs a {@link ConstantMutationRate} with a given value.
	 * 
	 * @param rate
	 *            the mutation rate value
	 */
	@Inject
	public ConstantMutationRate(@Constant(value = "rate", namespace = ConstantMutationRate.class) double rate) {
		this.rate = rate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.operator.mutate.MutationRate#get()
	 */
	@Override
	public double get() {
		return rate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.operator.mutate.MutationRate#set(double)
	 */
	@Override
	public void set(double value) {
		// do nothing
	}

}
