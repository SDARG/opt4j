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

package org.opt4j.core.config;

/**
 * The {@link Transformer} design pattern.
 * 
 * @author reimann
 * 
 * @param <I>
 *            the type of the source object
 * @param <O>
 *            the type of the target object
 */
public interface Transformer<I, O> {
	/**
	 * The given input {@code in} is not changed by transforming it to a new
	 * object of type <O>. Subsequent calls with equal instances of {@code in}
	 * should result in equal outputs.
	 * 
	 * @param in
	 *            the input to convert
	 * @return the corresponding output
	 */
	public O transform(I in);
}
