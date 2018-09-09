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

package org.opt4j.core.genotype;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.opt4j.core.Genotype;

public class CompositeGenotypeTest {

	@Test
	public void mapConstructor() {
		HashMap<String, Genotype> map = new HashMap<String, Genotype>();
		Genotype one = new IntegerGenotype(0, 2);
		map.put("one", one);
		map.put("two", new IntegerGenotype(0, 2));

		CompositeGenotype<String, Genotype> composite = new CompositeGenotype<String, Genotype>(map);

		Assert.assertEquals(2, composite.keySet().size());
		Assert.assertTrue(composite.values().contains(one));
	}

	@Test
	public void size() {
		CompositeGenotype<String, Genotype> composite = new CompositeGenotype<String, Genotype>();
		IntegerGenotype one = new IntegerGenotype(0, 2);
		one.add(1);
		IntegerGenotype two = new IntegerGenotype(0, 2);
		two.add(1);
		two.add(2);
		composite.put("one", one);
		composite.put("two", two);
		Assert.assertEquals(3, composite.size());
	}

	@Test
	public void get() {
		CompositeGenotype<String, Genotype> composite = new CompositeGenotype<String, Genotype>();
		Genotype one = new IntegerGenotype(0, 2);
		composite.put("one", one);
		composite.put("two", new IntegerGenotype(0, 2));
		Assert.assertEquals(one, composite.get("one"));
	}

	@Test
	public void clear() {
		CompositeGenotype<String, Genotype> composite = new CompositeGenotype<String, Genotype>();
		IntegerGenotype one = new IntegerGenotype(0, 2);
		one.add(1);
		composite.put("one", one);
		composite.put("two", new IntegerGenotype(0, 2));
		Assert.assertEquals(1, composite.size());
		composite.clear();
		Assert.assertEquals(0, composite.size());
	}

	@Test
	public void keySet() {
		CompositeGenotype<String, Genotype> composite = new CompositeGenotype<String, Genotype>();
		composite.put("one", new IntegerGenotype(0, 2));
		composite.put("two", new IntegerGenotype(0, 2));

		Set<String> keys = new HashSet<String>();
		keys.add("one");
		keys.add("two");

		Assert.assertEquals(keys, composite.keySet());
	}

	@Test
	public void values() {
		CompositeGenotype<String, Genotype> composite = new CompositeGenotype<String, Genotype>();
		Genotype one = new IntegerGenotype(0, 2);
		Genotype two = new IntegerGenotype(0, 2);
		composite.put("one", one);
		composite.put("two", two);

		Assert.assertEquals(2, composite.values().size());
		Assert.assertTrue(composite.values().contains(one));
		Assert.assertTrue(composite.values().contains(two));
	}

	@Test
	public void newInstance() {
		CompositeGenotype<String, Genotype> composite = new CompositeGenotype<String, Genotype>();
		composite.put("one", new IntegerGenotype(0, 2));
		composite.put("two", new IntegerGenotype(0, 2));

		CompositeGenotype<String, Genotype> composite2 = composite.newInstance();
		Assert.assertEquals(0, composite2.size());
		Assert.assertFalse(composite.equals(composite2));
	}

	@Test
	public void toStringTest() {
		CompositeGenotype<String, Genotype> composite = new CompositeGenotype<String, Genotype>();
		composite.put("one", new IntegerGenotype(0, 2));
		composite.put("two", new IntegerGenotype(0, 2));

		Assert.assertEquals("[one=[];two=[];]", composite.toString());
	}
}
