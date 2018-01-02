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

import org.opt4j.core.config.annotations.File;
import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.config.annotations.Required;
import org.opt4j.core.problem.ProblemModule;
import org.opt4j.core.start.Constant;

/**
 * The multiobjective 0/1 ILP knapsack problem as proposed in "E. Zitzler and L.
 * Thiele: Multiobjective evolutionary algorithms: A comparative case study and
 * the strength Pareto approach. IEEE Transactions on Evolutionary Computation,
 * vol. 3, no. 4, pp. 257-271, Nov. 1999.". Either one of the nine benchmark
 * problems from Zitzler and Thiele 1999 can be selected or the number of
 * knapsacks and items can be set manually.
 * 
 * @see <a
 *      href="http://www.tik.ee.ethz.ch/sop/download/supplementary/testProblemSuite/">http://www.tik.ee.ethz.ch/sop/download/supplementary/testProblemSuite/</a>
 * 
 * @author reimann, lukasiewycz
 * 
 */
public class KnapsackModule extends ProblemModule {

	protected int knapsacks = 5;

	protected int items = 250;

	@Required(property = "testCase", elements = { "MANUAL", "ZT1", "ZT2", "ZT3", "ZT4", "ZT5", "ZT6", "ZT7", "ZT8",
			"ZT9" })
	@Constant(value = "seed", namespace = KnapsackProblemRandom.class)
	protected int seed = 0;

	@File
	@Required(property = "testCase", elements = { "FILE" })
	@Constant(value = "filename", namespace = KnapsackProblemFile.class)
	protected String filename = "";

	protected Representation representation = Representation.BITSTRING;

	protected TestCase testCase = TestCase.MANUAL;

	public enum TestCase {
		FILE, @Info("use test data from an input file")
		MANUAL, @Info("2 knapsacks, 250 items")
		ZT1, @Info("3 knapsacks, 250 items")
		ZT2, @Info("5 knapsacks, 250 items")
		ZT3, @Info("2 knapsacks, 500 items")
		ZT4, @Info("3 knapsacks, 500 items")
		ZT5, @Info("5 knapsacks, 500 items")
		ZT6, @Info("2 knapsacks, 750 items")
		ZT7, @Info("3 knapsacks, 750 items")
		ZT8, @Info("5 knapsacks, 750 items")
		ZT9;
	}

	public enum Representation {
		@Info("Bitstring genotype")
		BITSTRING, @Info("SAT decoding")
		SAT;
	}

	@Override
	protected void config() {
		switch (testCase) {
		case ZT1:
			knapsacks = 2;
			items = 250;
			break;
		case ZT2:
			knapsacks = 3;
			items = 250;
			break;
		case ZT3:
			knapsacks = 5;
			items = 250;
			break;
		case ZT4:
			knapsacks = 2;
			items = 500;
			break;
		case ZT5:
			knapsacks = 3;
			items = 500;
			break;
		case ZT6:
			knapsacks = 5;
			items = 500;
			break;
		case ZT7:
			knapsacks = 2;
			items = 750;
			break;
		case ZT8:
			knapsacks = 3;
			items = 750;
			break;
		case ZT9:
			knapsacks = 5;
			items = 750;
			break;
		default:
			break;
		}

		bindConstant("items", KnapsackProblemRandom.class).to(items);
		bindConstant("knapsacks", KnapsackProblemRandom.class).to(knapsacks);

		if (testCase == TestCase.FILE) {
			bind(KnapsackProblem.class).to(KnapsackProblemFile.class).in(SINGLETON);
		} else {
			bind(KnapsackProblem.class).to(KnapsackProblemRandom.class).in(SINGLETON);
		}

		switch (representation) {
		case BITSTRING:
			bindProblem(KnapsackBinaryCreatorDecoder.class, KnapsackBinaryCreatorDecoder.class,
					KnapsackProfitEvaluator.class);
			break;
		case SAT:
			bindProblem(KnapsackSATCreatorDecoder.class, KnapsackSATCreatorDecoder.class, KnapsackProfitEvaluator.class);
			break;
		}
		addEvaluator(KnapsackOverloadEvaluator.class);
	}

	@Required(property = "testCase", elements = { "MANUAL" })
	public int getKnapsacks() {
		return knapsacks;
	}

	public void setKnapsacks(int knapsacks) {
		this.knapsacks = knapsacks;
	}

	@Required(property = "testCase", elements = { "MANUAL" })
	public int getItems() {
		return items;
	}

	public void setItems(int items) {
		this.items = items;
	}

	public Representation getRepresentation() {
		return representation;
	}

	public void setRepresentation(Representation representation) {
		this.representation = representation;
	}

	public TestCase getTestCase() {
		return testCase;
	}

	public void setTestCase(TestCase testCase) {
		this.testCase = testCase;
	}

	public int getSeed() {
		return seed;
	}

	public void setSeed(int seed) {
		this.seed = seed;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

}
