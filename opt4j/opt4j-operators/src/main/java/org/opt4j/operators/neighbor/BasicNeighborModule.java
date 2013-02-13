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

package org.opt4j.operators.neighbor;

import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.genotype.PermutationGenotype;

/**
 * The basic {@link NeighborModule}.
 * 
 * @author lukasiewycz
 * 
 */
@Info("Setting for the basic neighbor classOperators for genotype variation.")
public class BasicNeighborModule extends NeighborModule {

	@Info("The type of the neighbor operator for the Permutation genotype.")
	protected PermutationType permutationType = PermutationType.MIXED;

	/**
	 * Type of {@link Neighbor} operator for the {@link PermutationGenotype}.
	 * 
	 * @author lukasiewycz
	 * 
	 */
	public enum PermutationType {
		/**
		 * Use the {@link NeighborPermutationMixed}.
		 */
		@Info("Use randomly SWAP,INSERT, or REVERT")
		MIXED,
		/**
		 * Use the {@link NeighborPermutationSwap}.
		 */
		@Info("Swaps two elements")
		SWAP,
		/**
		 * Use the {@link NeighborPermutationInsert}.
		 */
		@Info("Moves one element to another position")
		INSERT,
		/**
		 * Use the {@link NeighborPermutationRevert}.
		 */
		@Info("Reverts a part for the genotype")
		REVERT;
	}

	/**
	 * Returns the permutation mode.
	 * 
	 * @return the permutation mode
	 */
	public PermutationType getPermutationType() {
		return permutationType;
	}

	/**
	 * Sets the permutation mode.
	 * 
	 * @param permutationMode
	 *            the permutation mode
	 */
	public void setPermutationType(PermutationType permutationMode) {
		this.permutationType = permutationMode;
	}

	/**
	 * Constructs a {@link BasicNeighborModule}.
	 */
	public BasicNeighborModule() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.start.Opt4JModule#config()
	 */
	@Override
	public void config() {

		Class<? extends NeighborPermutation> permutation = NeighborPermutationMixed.class;
		switch (permutationType) {
		case MIXED:
			permutation = NeighborPermutationMixed.class;
			break;
		case SWAP:
			permutation = NeighborPermutationSwap.class;
			break;
		case INSERT:
			permutation = NeighborPermutationInsert.class;
			break;
		case REVERT:
			permutation = NeighborPermutationRevert.class;
			break;
		}
		bind(NeighborPermutation.class).to(permutation).in(SINGLETON);
	}
}
