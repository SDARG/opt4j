/**
 * Opt4J is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Opt4J. If not, see http://www.gnu.org/licenses/.
 */

package org.opt4j.satdecoding.sat4j;

import org.opt4j.core.config.Icons;
import org.opt4j.core.config.annotations.Icon;
import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.config.annotations.Order;
import org.opt4j.core.config.annotations.Required;
import org.opt4j.core.start.Constant;
import org.opt4j.satdecoding.SATModule;
import org.opt4j.satdecoding.sat4j.SAT4JSolver.Learning;
import org.opt4j.satdecoding.sat4j.SAT4JSolver.Restarts;

/**
 * The {@link SAT4JModule} enables and configures the {@link SAT4JSolver}.
 * 
 * @author lukasiewycz
 * 
 */
@Icon(Icons.PUZZLE_GREEN)
@Info("The SAT/PB-Solver configuration for <a href=\"http://www.sat4j.org\">Sat4J.org</a>.")
public class SAT4JModule extends SATModule {

	@Info("Timeout in seconds.")
	@Order(0)
	@Constant(value = "timeout", namespace = SAT4JSolver.class)
	protected int timeout = 3600;

	@Info("Learned clauses up to this size are kept.")
	@Required(property = "learning", elements = { "FIXEDLENGTH" })
	@Order(2)
	@Constant(value = "clauseLearningLength", namespace = SAT4JSolver.class)
	protected int learnSize = 10;

	@Info("Learning strategy.")
	@Order(1)
	@Constant(value = "learning", namespace = SAT4JSolver.class)
	protected Learning learning = Learning.FIXEDLENGTH;

	@Info("Restart strategy.")
	@Order(3)
	@Constant(value = "restarts", namespace = SAT4JSolver.class)
	protected Restarts restarts = Restarts.MINISAT;

	@Info("The number of instances.")
	@Order(4)
	protected int instances = 1;

	/**
	 * Returns the number of instances.
	 * 
	 * @see #setInstances
	 * @return the number of instances
	 */
	public int getInstances() {
		return instances;
	}

	/**
	 * Sets the number of instances.
	 * 
	 * @see #getInstances
	 * @param instances
	 *            the number of instances
	 */
	public void setInstances(int instances) {
		if (instances <= 0) {
			throw new IllegalArgumentException("The number of instances must be positive: " + instances);
		}
		this.instances = instances;
	}

	/**
	 * Returns the learning strategy.
	 * 
	 * @see #setLearning
	 * @return the learning strategy
	 */
	public Learning getLearning() {
		return learning;
	}

	/**
	 * Sets the learning strategy.
	 * 
	 * @see #getLearning
	 * @param learning
	 *            the learning strategy to set
	 */
	public void setLearning(Learning learning) {
		this.learning = learning;
	}

	/**
	 * Returns the restart strategy.
	 * 
	 * @see #setRestarts
	 * @return the restart strategy
	 */
	public Restarts getRestarts() {
		return restarts;
	}

	/**
	 * Sets the restart strategy.
	 * 
	 * @see #getRestarts
	 * @param restarts
	 *            the restart strategy to set
	 */
	public void setRestarts(Restarts restarts) {
		this.restarts = restarts;
	}

	/**
	 * Returns the number of learned clauses that are kept.
	 * 
	 * @see #setLearnSize
	 * @return the number of learned clauses that are kept
	 */
	public int getLearnSize() {
		return learnSize;
	}

	/**
	 * Sets the number of learned clauses that are kept.
	 * 
	 * @see #getLearnSize
	 * @param learnSize
	 *            the number of learned clauses
	 */
	public void setLearnSize(int learnSize) {
		this.learnSize = learnSize;
	}

	/**
	 * Returns the timeout (in seconds) of the {@link SAT4JSolver}.
	 * 
	 * @see #setTimeout
	 * @return the timeout (in seconds) of the solver
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * Sets the timeout (in seconds) for the {@link SAT4JSolver}.
	 * 
	 * @see #getTimeout
	 * @param timeout
	 *            the timeout for the solver
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.start.Opt4JModule#config()
	 */
	@Override
	public void config() {
		bindSolver(SAT4JSolver.class, instances);
	}

}
