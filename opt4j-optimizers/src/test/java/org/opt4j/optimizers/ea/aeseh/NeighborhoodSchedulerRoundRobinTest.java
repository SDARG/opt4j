package org.opt4j.optimizers.ea.aeseh;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opt4j.core.Individual;

public class NeighborhoodSchedulerRoundRobinTest {

	@SuppressWarnings("serial")
	class MockNeighborhood extends HashSet<Individual> {
		protected final String name;

		public MockNeighborhood(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	@Test
	public void test() {
		MockNeighborhood first = new MockNeighborhood("first");
		MockNeighborhood second = new MockNeighborhood("second");
		MockNeighborhood third = new MockNeighborhood("third");
		List<Set<Individual>> neighborhoods = new ArrayList<>();
		neighborhoods.add(first);
		neighborhoods.add(second);
		neighborhoods.add(third);
		NeighborhoodSchedulerRoundRobin scheduler = new NeighborhoodSchedulerRoundRobin(neighborhoods);
		Assertions.assertEquals(scheduler.next().toString(), "first");
		Assertions.assertEquals(scheduler.next().toString(), "second");
		Assertions.assertEquals(scheduler.next().toString(), "third");
		Assertions.assertEquals(scheduler.next().toString(), "first");
	}
}
