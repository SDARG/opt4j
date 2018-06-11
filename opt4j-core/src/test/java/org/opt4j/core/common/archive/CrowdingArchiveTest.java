/*******************************************************************************
 * Copyright (c) 2014 Opt4J
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package org.opt4j.core.common.archive;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.opt4j.core.Genotype;
import org.opt4j.core.Individual;
import org.opt4j.core.IndividualFactory;
import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;
import org.opt4j.core.common.archive.CrowdingArchive;
import org.opt4j.core.genotype.BooleanGenotype;
import org.opt4j.core.problem.Creator;
import org.opt4j.core.problem.Decoder;
import org.opt4j.core.problem.Evaluator;
import org.opt4j.core.problem.ProblemModule;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class CrowdingArchiveTest {

	protected final static int SIZE = 200;

	protected Set<Individual> set = new HashSet<Individual>();
	protected CrowdingArchive crowdingArchive = null;

	protected static class MockProblem implements Creator<Genotype>, Decoder<Genotype, Object>, Evaluator<Object> {
		@Override
		public Genotype create() {
			return new BooleanGenotype();
		}

		@Override
		public Object decode(Genotype genotype) {
			return null;
		}

		@Override
		public Objectives evaluate(Object phenotype) {
			return new Objectives();
		}
	}

	@Ignore
	protected static class MockProblemModule extends ProblemModule {
		@Override
		protected void config() {
			bindProblem(MockProblem.class, MockProblem.class, MockProblem.class);
		}
	}

	@Before
	public void setUp() throws Exception {

		Objective o0 = new Objective("o0");
		Objective o1 = new Objective("o1");

		Injector injector = Guice.createInjector(new MockProblemModule());
		IndividualFactory factory = injector.getInstance(IndividualFactory.class);

		for (int i = 0; i < SIZE; i++) {
			Individual individual = factory.create();
			Objectives objectives = new Objectives();
			objectives.add(o0, i);
			objectives.add(o1, SIZE - i);
			individual.setObjectives(objectives);
			set.add(individual);
		}
	}

	@Test
	@Ignore
	public void testCrowdingArchiveConsistency() {
		CrowdingArchive c0 = new CrowdingArchive(100);
		c0.update(set);

		Set<Individual> i0 = new HashSet<Individual>(c0);

		c0.clear();
		c0.update(set);

		Set<Individual> i1 = new HashSet<Individual>(c0);

		Assert.assertArrayEquals(i0.toArray(), i1.toArray());

	}

}
