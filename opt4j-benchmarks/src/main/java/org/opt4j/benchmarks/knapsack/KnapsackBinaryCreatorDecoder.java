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

package org.opt4j.benchmarks.knapsack;

import java.util.ArrayList;
import java.util.List;

import org.opt4j.core.common.random.Rand;
import org.opt4j.core.genotype.BooleanMapGenotype;
import org.opt4j.core.problem.Creator;
import org.opt4j.core.problem.Decoder;

import com.google.inject.Inject;

/**
 * The {@link KnapsackBinaryCreatorDecoder} creates and decodes the given
 * {@link KnapsackProblemRandom} using one bit per item, i.e. a
 * {@link BooleanMapGenotype} having a length which equals the number of
 * available items.
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
	 * Decodes a {@link BooleanMapGenotype} to the corresponding
	 * {@link ItemSelection}.
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
