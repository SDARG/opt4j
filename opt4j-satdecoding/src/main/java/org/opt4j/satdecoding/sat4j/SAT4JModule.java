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
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *******************************************************************************/
 

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
