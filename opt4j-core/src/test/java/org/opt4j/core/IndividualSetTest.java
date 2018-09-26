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
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *******************************************************************************/
package org.opt4j.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opt4j.core.optimizer.Population;

public class IndividualSetTest {

	protected final static int SIZE = 10000;

	protected List<Individual> list = new ArrayList<>();

	@Before
	public void setUp() throws Exception {
		for (int i = 0; i < SIZE; i++) {
			Individual individual = new Individual();
			list.add(individual);
		}
	}

	@Test
	public void testPopulationOrder() {
		Population p0 = new Population();
		Population p1 = new Population();
		p0.addAll(list);
		p1.addAll(list);

		Iterator<Individual> it0 = p0.iterator();
		Iterator<Individual> it1 = p1.iterator();

		while (it0.hasNext()) {
			Individual i0 = it0.next();
			Individual i1 = it1.next();
			Assert.assertEquals(i0, i1);
		}
	}

}
