package org.opt4j.core;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Singleton;

/**
 * The {@link ObjectivesWrapper} is used as a handle by all classes which
 * require the knowledge about the objectives of the current opitmization.
 * 
 * @author Fedor Smirnov
 */
@Singleton
public class ObjectivesWrapper {

	protected boolean objectivesInit = false;
	protected final List<Objective> optimizationObjectives = new ArrayList<>();

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

	/**
	 * Initializes the objectives
	 * 
	 * @param the objectives obtained from an evaluated individual
	 */
	public void init(Objectives objs) {
		for (Objective obj : objs.getKeys()) {
			optimizationObjectives.add(obj);
			objectivesInit = true;
		}
	}

	public boolean isObjectivesInit() {
		return objectivesInit;
	}
}
