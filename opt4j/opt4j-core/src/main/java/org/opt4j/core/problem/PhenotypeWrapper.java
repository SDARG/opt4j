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
package org.opt4j.core.problem;

import org.opt4j.core.Phenotype;

/**
 * The {@link PhenotypeWrapper} might be used if the phenotype class of a given
 * problem is already defined and cannot be extended with the {@link Phenotype}
 * marker interface.
 * 
 * @see Phenotype
 * 
 * @author lukasiewycz
 * 
 */
public class PhenotypeWrapper<O> implements Phenotype {

	protected final O object;

	/**
	 * Constructs a {@link PhenotypeWrapper} for the given object.
	 * 
	 * @param object
	 *            the object
	 */
	public PhenotypeWrapper(O object) {
		super();
		this.object = object;
	}

	/**
	 * Returns the wrapped object.
	 * 
	 * @return the wrapped object
	 */
	public O get() {
		return object;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.valueOf(object);
	}
}
