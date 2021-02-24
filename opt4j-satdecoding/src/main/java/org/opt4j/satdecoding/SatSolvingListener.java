package org.opt4j.satdecoding;

import org.opt4j.satdecoding.Model;
import org.opt4j.satdecoding.SATGenotype;

/**
 * Interface for the classes which are to be notified each time that the
 * resolution of the constraint set according to a genotype creates a model.
 * 
 * @author Fedor Smirnov
 *
 */
public interface SatSolvingListener {

	/**
	 * Notifies the listener of a constraint resolution which was done following the
	 * strategy in the genotype and yielded the model.
	 * 
	 * @param genotype genotype describing the strategy of the SAT resolution
	 * @param model    model describing the feasible solution
	 */
	public void notifyListener(SATGenotype genotype, Model model);

}
