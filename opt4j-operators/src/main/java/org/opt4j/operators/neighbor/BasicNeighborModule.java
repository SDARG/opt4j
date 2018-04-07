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
		default:
			permutation = NeighborPermutationMixed.class;
			break;
		}
		bind(NeighborPermutation.class).to(permutation).in(SINGLETON);
	}
}
