package org.opt4j.core;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class IndividiualSetTest {

	@Test
	public void addTest() {
		IndividualSet set = new IndividualSet();
		Individual individual = new Individual();

		Assertions.assertTrue(set.add(individual));

		Assertions.assertTrue(set.contains(individual));
	}

	private class AddIndividualSetListener implements IndividualSetListener {
		protected IndividualSet collection;
		protected Individual i;

		@Override
		public void individualRemoved(IndividualSet collection, Individual i) {
			Assertions.assertTrue(false); // shall not happen in this testcase
		}

		@Override
		public void individualAdded(IndividualSet collection, Individual i) {
			Assertions.assertNull(this.collection);
			Assertions.assertNull(this.i);
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

		Assertions.assertTrue(set.add(individual));

		Assertions.assertEquals(set, l.collection);
		Assertions.assertEquals(individual, l.i);
	}

	public void iteratorTest() {
		IndividualSet set = new IndividualSet();
		Individual individual = new Individual();
		set.add(individual);

		Iterator<Individual> iterator = set.iterator();
		Assertions.assertTrue(iterator.hasNext());
		Assertions.assertEquals(individual, iterator.next());
	}

	public void iteratorRemoveTest() {
		final IndividualSet set = new IndividualSet();
		final Individual individual = new Individual();
		set.add(individual);

		IndividualSetListener l = new IndividualSetListener() {
			@Override
			public void individualRemoved(IndividualSet collection, Individual i) {
				Assertions.assertEquals(set, collection);
				Assertions.assertEquals(individual, i);
				Assertions.assertTrue(collection.isEmpty());
			}

			@Override
			public void individualAdded(IndividualSet collection, Individual individual) {
				// nothing to be done
			}
		};
		set.addListener(l);

		Iterator<Individual> iterator = set.iterator();
		Assertions.assertTrue(iterator.hasNext());
		Assertions.assertEquals(individual, iterator.next());
		iterator.remove();
	}

	@Test
	public void sizeTest() {
		IndividualSet set = new IndividualSet();
		Individual individual = new Individual();

		Assertions.assertEquals(0, set.size());
		set.add(individual);
		Assertions.assertEquals(1, set.size());
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

		Assertions.assertTrue(set.listeners.contains(l));
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
		Assertions.assertTrue(set.listeners.contains(l));

		set.removeListener(l);
		Assertions.assertFalse(set.listeners.contains(l));
		Assertions.assertTrue(set.listeners.isEmpty());
	}

	@Test
	public void addAllTest() {
		IndividualSet set = new IndividualSet();
		Individual i1 = new Individual();
		Individual i2 = new Individual();
		Individual i3 = new Individual();

		Assertions.assertTrue(set.addAll(i1, i2));
		Assertions.assertTrue(set.addAll(i3));
		Assertions.assertFalse(set.addAll());
		Assertions.assertFalse(set.addAll(i1, i2, i3));
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

		Assertions.assertTrue(set.addAll(is));
		Assertions.assertTrue(set.containsAll(is));

		is.add(i3);
		Assertions.assertFalse(set.containsAll(is));
	}

	@Test
	public void isEmptyTest() {
		IndividualSet set = new IndividualSet();
		Individual i1 = new Individual();

		Assertions.assertTrue(set.isEmpty());
		set.add(i1);
		Assertions.assertFalse(set.isEmpty());
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

		Assertions.assertTrue(set.remove(i));
		Assertions.assertEquals(set, l.collection);
		Assertions.assertEquals(i, l.individual);
		Assertions.assertTrue(set.isEmpty());

		Assertions.assertFalse(set.remove(i));
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

		Assertions.assertTrue(set.removeAll(rem));
		Assertions.assertTrue(set.isEmpty());

		Assertions.assertFalse(set.removeAll(rem));
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

		Assertions.assertTrue(set.retainAll(rem));
		Assertions.assertTrue(set.contains(i2));
		Assertions.assertEquals(1, set.size());

		Assertions.assertFalse(set.retainAll(rem));
	}

	@Test
	public void toArrayTest() {
		IndividualSet set = new IndividualSet();
		Individual individual = new Individual();
		Assertions.assertTrue(set.add(individual));

		Individual[] array = { individual };
		set.toArray(array);
		Assertions.assertEquals(1, array.length);
		Assertions.assertEquals(individual, array[0]);
	}
}
