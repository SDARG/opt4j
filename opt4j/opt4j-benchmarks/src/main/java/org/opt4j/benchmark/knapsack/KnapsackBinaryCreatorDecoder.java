/**
 * Opt4J is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with Opt4J. If not, see
 * http://www.gnu.org/licenses/.
 */
package org.opt4j.benchmark.knapsack;

import java.util.ArrayList;
import java.util.List;

import org.opt4j.common.random.Rand;
import org.opt4j.core.problem.Creator;
import org.opt4j.core.problem.Decoder;
import org.opt4j.genotype.BooleanMapGenotype;

import com.google.inject.Inject;

/**
 * The {@link KnapsackBinaryCreatorDecoder} creates and decodes the given {@link KnapsackProblemRandom} using one bit
 * per item, i.e. a {@link BooleanMapGenotype} having a length which equals the number of available items.
 * 
 * @author reimann
 * 
 */
public class KnapsackBinaryCreatorDecoder implements Creator<BooleanMapGenotype<Item>>,
		Decoder<BooleanMapGenotype<Item>, ItemSelection> {
	private final List<Item> itemList = new ArrayList<Item>();
	private final Rand random;

	/**
	 * Creates a {@link KnapsackBinaryCreatorDecoder}.
	 * 
	 * @param problem
	 *            the problem
	 * @param random
	 *            the random number generator
	 */
	@Inject
	public KnapsackBinaryCreatorDecoder(KnapsackProblem problem, Rand random) {
		this.random = random;
		itemList.addAll(problem.getItems());
	}

	/**
	 * Decodes a {@link BooleanMapGenotype} to the corresponding {@link ItemSelection}.
	 */
	@Override
	public ItemSelection decode(BooleanMapGenotype<Item> genotype) {
		ItemSelection itemSelection = new ItemSelection();

		for (Item item : itemList) {
			if (genotype.getValue(item)) {
				itemSelection.add(item);
			}
		}
		return itemSelection;
	}

	/**
	 * Creates a {@link BooleanMapGenotype} for the given {@link Item}s.
	 */
	@Override
	public BooleanMapGenotype<Item> create() {
		BooleanMapGenotype<Item> b = new BooleanMapGenotype<Item>(itemList);
		b.init(random);
		return b;
	}
}
