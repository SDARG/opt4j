package org.opt4j.optimizers.ea.aeseh;

import java.util.List;
import java.util.Set;

import org.opt4j.core.Individual;

/**
 * The {@link NeighborhoodSchedulerRoundRobin} schedules the neighborhoods in a simple
 * round-robin fashion.
 * 
 * @author Fedor Smirnov
 *
 */
public class NeighborhoodSchedulerRoundRobin implements NeighborhoodScheduler {

	protected final List<Set<Individual>> neighborhoods;
	protected int nextIdx;

	public NeighborhoodSchedulerRoundRobin(List<Set<Individual>> neighborhoods) {
		this.neighborhoods = neighborhoods;
		this.nextIdx = 0;
	}

	@Override
	public Set<Individual> next() {
		Set<Individual> neighborhood = neighborhoods.get(nextIdx);
		nextIdx = (nextIdx + 1) % neighborhoods.size();
		return neighborhood;
	}
}
