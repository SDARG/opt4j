package org.opt4j.core;

import java.util.ArrayList;
import java.util.List;

import org.opt4j.core.Individual.State;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The {@link ObjectivesWrapper} is used as a handle by all classes which
 * require the knowledge about the objectives of the current opitmization.
 * 
 * @author Fedor Smirnov
 */
@Singleton
public class ObjectivesWrapper implements IndividualStateListener {

	protected boolean objectivesInit = false;
	protected final List<Objective> optimizationObjectives = new ArrayList<>();

	@Inject
	public ObjectivesWrapper(IndividualFactory indiFactory) {
		indiFactory.addIndividualStateListener(this);
	}
	
	/**
	 * Returns the list of the objectives of the current optimization (same order as
	 * in {@link Objectives}). Throws en {@link IllegalStateException} if called
	 * before the first complete evaluation of an individual.
	 * 
	 * @return the list of the objectives of the current optimization (same order as
	 *         in {@link Objectives})
	 */
	public List<Objective> getOptimizationObjectives() {
		if (objectivesInit) {
			return optimizationObjectives;
		} else {
			throw new IllegalStateException("Objectives requested before the first individual evaluation.");
		}
	}

	@Override
	public void individualStateChanged(Individual individual) {
		if (individual.getState().equals(State.EVALUATED) && !objectivesInit) {
			Objectives objs = individual.getObjectives();
			for (Objective obj : objs.getKeys()) {
				optimizationObjectives.add(obj);
			}
			objectivesInit = true;
		}
	}
}
