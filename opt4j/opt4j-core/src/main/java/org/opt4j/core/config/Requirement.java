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
 * The {@link Requirement} indicates if a {@link Property} is active or not.
 * 
 * @author lukasiewycz
 * 
 */
public interface Requirement {

	/**
	 * Returns {@code true} if the requirement is fulfilled.
	 * 
	 * @return {@code true} if the requirement is fulfilled
	 */
	public boolean isFulfilled();

	/**
	 * Returns the property of the requirement.
	 * 
	 * @return the property of the requirement
	 */
	public Property getProperty();

}
