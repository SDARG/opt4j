package org.opt4j.optimizers.ea;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.opt4j.core.Individual;

public class RoundRobinSchedulerTest {

	@SuppressWarnings("serial")
	class MockNeighborhood extends HashSet<Individual>{
		protected final String name;
		public MockNeighborhood(String name){
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
		List<Set<Individual>> neighborhoods = new ArrayList<Set<Individual>>();
		neighborhoods.add(first);
		neighborhoods.add(second);
		neighborhoods.add(third);
		RoundRobinScheduler scheduler = new RoundRobinScheduler(neighborhoods);
		assertEquals(scheduler.next().toString(), "first");
		assertEquals(scheduler.next().toString(), "second");
		assertEquals(scheduler.next().toString(), "third");
		assertEquals(scheduler.next().toString(), "first");
	}
}
