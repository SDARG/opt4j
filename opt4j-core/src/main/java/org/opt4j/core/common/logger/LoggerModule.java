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


package org.opt4j.core.common.logger;

import org.opt4j.core.config.Icons;
import org.opt4j.core.config.annotations.File;
import org.opt4j.core.config.annotations.Icon;
import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.config.annotations.Order;
import org.opt4j.core.config.annotations.Required;
import org.opt4j.core.start.Constant;

/**
 * Module for logging.
 * 
 * @see Logger
 * @author reimann, lukasiewycz
 * 
 */
@Icon(Icons.TEXT)
@Info("Logs the contents of the archive to a file.")
public class LoggerModule extends OutputModule {

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