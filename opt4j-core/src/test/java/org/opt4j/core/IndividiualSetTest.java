package org.opt4j.core;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class IndividiualSetTest {

	@Test
	public void addTest() {
		IndividualSet set = new IndividualSet();
		Individual individual = new Individual();

		Assert.assertTrue(set.add(individual));

		Assert.assertTrue(set.contains(individual));
	}

	private class AddIndividualSetListener implements IndividualSetListener {
		protected IndividualSet collection;
		protected Individual i;

		@Override
		public void individualRemoved(IndividualSet collection, Individual i) {
			Assert.assertTrue(false); // shall not happen in this testcase
		}

		@Override
		public void individualAdded(IndividualSet collection, Individual i) {
			Assert.assertNull(this.collection);
			Assert.assertNull(this.i);
			this.collection = collection;
			this.i = i;
		}
	};

	@Test
	public void addWithListenerTest() {
		final IndividualSet set = new IndividualSet();
		final Individual individual = new Individual();

		AddIndividualSetListener l = new AddIndividualSetListener();
		set.addListener(l);

		Assert.assertTrue(set.add(individual));

		Assert.assertEquals(set, l.collection);
		Assert.assertEquals(individual, l.i);
	}

	public void iteratorTest() {
		IndividualSet set = new IndividualSet();
		Individual individual = new Individual();
		set.add(individual);

		Iterator<Individual> iterator = set.iterator();
		Assert.assertTrue(iterator.hasNext());
		Assert.assertEquals(individual, iterator.next());
	}

	public void iteratorRemoveTest() {
		final IndividualSet set = new IndividualSet();
		final Individual individual = new Individual();
		set.add(individual);

		IndividualSetListener l = new IndividualSetListener() {
			@Override
			public void individualRemoved(IndividualSet collection, Individual i) {
				Assert.assertEquals(set, collection);
				Assert.assertEquals(individual, i);
				Assert.assertTrue(collection.isEmpty());
			}

			@Override
			public void individualAdded(IndividualSet collection, Individual individual) {
				// nothing to be done
			}
		};
		set.addListener(l);

		Iterator<Individual> iterator = set.iterator();
		Assert.assertTrue(iterator.hasNext());
		Assert.assertEquals(individual, iterator.next());
		iterator.remove();
	}

	@Test
	public void sizeTest() {
		IndividualSet set = new IndividualSet();
		Individual individual = new Individual();

		Assert.assertEquals(0, set.size());
		set.add(individual);
		Assert.assertEquals(1, set.size());
	}

	@Test
	public void addListenerTest() {
		IndividualSet set = new IndividualSet();
		IndividualSetListener l = new IndividualSetListener() {
			@Override
			public void individualRemoved(IndividualSet collection, Individual individual) {
				// nothing to be done
			}

			@Override
			public void individualAdded(IndividualSet collection, Individual individual) {
				// nothing to be done
			}
		};
		set.addListener(l);

		Assert.assertTrue(set.listeners.contains(l));
	}

	@Test
	public void removeListenerTest() {
		IndividualSet set = new IndividualSet();
		IndividualSetListener l = new IndividualSetListener() {
			@Override
			public void individualRemoved(IndividualSet collection, Individual individual) {
			}

			@Override
			public void individualAdded(IndividualSet collection, Individual individual) {
			}
		};

		set.addListener(l);
		Assert.assertTrue(set.listeners.contains(l));

		set.removeListener(l);
		Assert.assertFalse(set.listeners.contains(l));
		Assert.assertTrue(set.listeners.isEmpty());
	}

	@Test
	public void addAllTest() {
		IndividualSet set = new IndividualSet();
		Individual i1 = new Individual();
		Individual i2 = new Individual();
		Individual i3 = new Individual();

		Assert.assertTrue(set.addAll(i1, i2));
		Assert.assertTrue(set.addAll(i3));
		Assert.assertFalse(set.addAll());
		Assert.assertFalse(set.addAll(i1, i2, i3));
	}

	@Test
	public void containsAllTest() {
		IndividualSet set = new IndividualSet();
		Individual i1 = new Individual();
		Individual i2 = new Individual();
		Individual i3 = new Individual();

		Set<Individual> is = new HashSet<>();
		is.add(i1);
		is.add(i2);

		Assert.assertTrue(set.addAll(is));
		Assert.assertTrue(set.containsAll(is));

		is.add(i3);
		Assert.assertFalse(set.containsAll(is));
	}

	@Test
	public void isEmptyTest() {
		IndividualSet set = new IndividualSet();
		Individual i1 = new Individual();

		Assert.assertTrue(set.isEmpty());
		set.add(i1);
		Assert.assertFalse(set.isEmpty());
	}

	private class RemoveIndividualSetListener implements IndividualSetListener {
		protected IndividualSet collection;
		protected Individual individual;

		@Override
		public void individualRemoved(IndividualSet collection, Individual individual) {
			this.collection = collection;
			this.individual = individual;
		}

		@Override
		public void individualAdded(IndividualSet collection, Individual individual) {
		}
	};

	@Test
	public void removeTest() {
		final IndividualSet set = new IndividualSet();
		final Individual i = new Individual();
		set.add(i);

		RemoveIndividualSetListener l = new RemoveIndividualSetListener();
		set.addListener(l);

		Assert.assertTrue(set.remove(i));
		Assert.assertEquals(set, l.collection);
		Assert.assertEquals(i, l.individual);
		Assert.assertTrue(set.isEmpty());

		Assert.assertFalse(set.remove(i));
	}

	@Test
	public void removeAllTest() {
		final IndividualSet set = new IndividualSet();
		final Individual i1 = new Individual();
		final Individual i2 = new Individual();
		set.add(i1);
		set.add(i2);

		Set<Individual> rem = new HashSet<>();
		rem.add(i1);
		rem.add(i2);

		Assert.assertTrue(set.removeAll(rem));
		Assert.assertTrue(set.isEmpty());

		Assert.assertFalse(set.removeAll(rem));
	}

	@Test
	public void retainAllTest() {
		final IndividualSet set = new IndividualSet();
		final Individual i1 = new Individual();
		final Individual i2 = new Individual();
		set.add(i1);
		set.add(i2);

		Set<Individual> rem = new HashSet<>();
		rem.add(i2);

		Assert.assertTrue(set.retainAll(rem));
		Assert.assertTrue(set.contains(i2));
		Assert.assertEquals(1, set.size());

		Assert.assertFalse(set.retainAll(rem));
	}

	@Test
	public void toArrayTest() {
		IndividualSet set = new IndividualSet();
		Individual individual = new Individual();
		Assert.assertTrue(set.add(individual));

		Individual[] array = { individual };
		set.toArray(array);
		Assert.assertEquals(1, array.length);
		Assert.assertEquals(individual, array[0]);
	}
}
