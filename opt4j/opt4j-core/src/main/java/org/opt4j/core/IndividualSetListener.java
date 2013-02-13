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

package org.opt4j.core;

/**
 * The {@link IndividualSetListener} receives notifications if an
 * {@link Individual} is added to or removed from an {@link IndividualSet}.
 * 
 * @see IndividualSet
 * @author lukasiewycz
 * 
 */
public interface IndividualSetListener {

	/**
	 * Invoked if the {@link Individual} is added to the {@link IndividualSet}.
	 * 
	 * @param collection
	 *            the observed collection
	 * @param individual
	 *            the added individual
	 */
	public void individualAdded(IndividualSet collection, Individual individual);

	/**
	 * Invoked if the {@link Individual} is removed from the
	 * {@link IndividualSet}.
	 * 
	 * @param collection
	 *            the observed collection
	 * @param individual
	 *            the removed individual
	 */
	public void individualRemoved(IndividualSet collection, Individual individual);

}
