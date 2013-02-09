/**
 * Opt4J is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with Opt4J. If not, see
 * http://www.gnu.org/licenses/.
 */

package org.opt4j.common.logger;

import org.opt4j.config.Icons;
import org.opt4j.config.annotations.File;
import org.opt4j.config.annotations.Icon;
import org.opt4j.config.annotations.Info;
import org.opt4j.config.annotations.Order;
import org.opt4j.config.annotations.Required;
import org.opt4j.start.Constant;
import org.opt4j.viewer.VisualizationModule;

/**
 * Module for logging.
 * 
 * @see Logger
 * @author reimann, lukasiewycz
 * 
 */
@Icon(Icons.TEXT)
@Info("Logs the contents of the archive to a file.")
public class LoggerModule extends VisualizationModule {

	@Info("The name of the output file.")
	@Order(0)
	@File
	@Constant(value = "filename", namespace = TsvLogger.class)
	protected String filename = "output.tsv";

	@Info("Log per evaluation activated.")
	protected boolean loggingPerEvaluation = false;

	@Info("Log per iteration activated.")
	protected boolean loggingPerIteration = true;

	@Info("Number of evaluations after which the archive is logged.")
	@Required(property = "loggingPerEvaluation", elements = { "TRUE" })
	protected int evaluationStep = 100;

	@Info("Number of iterations after which the archive is logged.")
	@Required(property = "loggingPerIteration", elements = { "TRUE" })
	protected int iterationStep = 1;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.start.Opt4JModule#config()
	 */
	@Override
	public void config() {

		bind(TsvLogger.class).in(SINGLETON);

		addIndividualStateListener(TsvLogger.class);
		addOptimizerIterationListener(TsvLogger.class);
		addOptimizerStateListener(TsvLogger.class);

		int evaluationStep = this.evaluationStep;
		int iterationStep = this.iterationStep;

		if (!loggingPerEvaluation) {
			evaluationStep = -1;
		}
		if (!loggingPerIteration) {
			iterationStep = -1;
		}

		bindConstant("evaluationStep", TsvLogger.class).to(evaluationStep);
		bindConstant("iterationStep", TsvLogger.class).to(iterationStep);
	}

	/**
	 * Returns the filename.
	 * 
	 * @see #setFilename
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * Sets the filename.
	 * 
	 * @see #getFilename
	 * @param filename
	 *            the filename
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * Returns the step size of the evaluations.
	 * 
	 * @see #setEvaluationStep
	 * @return the step size of the evaluations
	 */
	public int getEvaluationStep() {
		return evaluationStep;
	}

	/**
	 * Sets the step size of the evaluations.
	 * 
	 * @see #getEvaluationStep
	 * @param evaluationStep
	 *            the step size of the evaluations
	 */
	public void setEvaluationStep(int evaluationStep) {
		this.evaluationStep = evaluationStep;
	}

	/**
	 * Returns the step size of the iterations.
	 * 
	 * @see #setIterationStep
	 * @return the step size of the iterations
	 */
	public int getIterationStep() {
		return iterationStep;
	}

	/**
	 * Sets the step size of the iterations.
	 * 
	 * @see #getIterationStep
	 * @param iterationStep
	 *            the step size of the iterations
	 */
	public void setIterationStep(int iterationStep) {
		this.iterationStep = iterationStep;
	}

	/**
	 * Returns {@code true} if the logger observes the number of evaluations.
	 * 
	 * @return {@code true} if the logger observes the number of evaluations
	 */
	public boolean isLoggingPerEvaluation() {
		return loggingPerEvaluation;
	}

	/**
	 * Sets the observation for the number of evaluations.
	 * 
	 * @param loggingPerEvaluation
	 *            {@code true} if the logger observes the number of evaluations
	 */
	public void setLoggingPerEvaluation(boolean loggingPerEvaluation) {
		this.loggingPerEvaluation = loggingPerEvaluation;
	}

	/**
	 * Returns {@code true} if the logger observes the number of iterations.
	 * 
	 * @return {@code true} if the logger observes the number of iterations
	 */
	public boolean isLoggingPerIteration() {
		return loggingPerIteration;
	}

	/**
	 * Sets the observation for the number of iterations.
	 * 
	 * @param loggingPerIteration
	 *            {@code true} if the logger observes the number of iterations
	 */
	public void setLoggingPerIteration(boolean loggingPerIteration) {
		this.loggingPerIteration = loggingPerIteration;
	}
}