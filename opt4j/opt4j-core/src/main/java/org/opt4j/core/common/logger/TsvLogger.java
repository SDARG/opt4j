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

package org.opt4j.core.common.logger;

import static org.opt4j.core.Objective.INFEASIBLE;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import org.opt4j.core.Individual;
import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;
import org.opt4j.core.Value;
import org.opt4j.core.optimizer.Archive;
import org.opt4j.core.start.Constant;

import com.google.inject.Inject;

/**
 * The {@link TsvLogger} writes all {@link Individual}s from the {@link Archive}
 * to the specified file. It can be configured to write the data each
 * {@code iterationStep} iterations or each {@code evaluationStep} evaluations.
 * 
 * The file format is TSV (tab separated values), according to the <a href=
 * "http://www.iana.org/assignments/media-types/text/tab-separated-values"
 * >Definition of tab-separated-values (tsv)</a> by IANA.
 * 
 * An infeasible Objective is printed as <it>INFEASIBLE</it>.
 * 
 * @see Objective
 * @author reimann, lukasiewycz
 */
public class TsvLogger extends AbstractLogger implements Logger {

	protected final Archive archive;

	private final PrintWriter out;
	private long startTime = -1;

	/**
	 * Creates an {@link TsvLogger}.
	 * 
	 * @param archive
	 *            the archive
	 * @param filename
	 *            the filename (using namespace {@link TsvLogger})
	 * @param evaluationStep
	 *            the number of evaluations between two logging events (using
	 *            namespace {@link TsvLogger})
	 * @param iterationStep
	 *            the number of iterations between two logging events (using
	 *            namespace {@link TsvLogger})
	 */
	@Inject
	public TsvLogger(Archive archive, @Constant(value = "filename", namespace = TsvLogger.class) String filename,
			@Constant(value = "evaluationStep", namespace = TsvLogger.class) int evaluationStep,
			@Constant(value = "iterationStep", namespace = TsvLogger.class) int iterationStep) {
		super(iterationStep, evaluationStep);
		this.archive = archive;
		this.out = initWriter(filename);
	}

	/**
	 * Initialize the print write from a filename
	 * 
	 * @param filename
	 *            the filename
	 * @return the print writer
	 */
	protected PrintWriter initWriter(String filename) {
		if (filename == null || filename.equals("")) {
			throw new IllegalArgumentException("File name must not be the empty string.");
		}

		File file = new File(filename);
		File dir = file.getParentFile();
		if (dir != null) {
			dir.mkdirs();
		}
		try {
			return new PrintWriter(new BufferedWriter(new FileWriter(file)));
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.common.logger.AbstractLogger#optimizationStarted()
	 */
	@Override
	public void optimizationStarted() {
		startTime = System.currentTimeMillis();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.common.logger.AbstractLogger#logEvent(int, int)
	 */
	@Override
	public void logEvent(int iteration, int evaluation) {
		assert startTime != -1 : "not initialized";
		double time = ((double) System.currentTimeMillis() - startTime) / 1000.0;
		for (Individual individual : archive) {
			String statistics = getStatistics(iteration, evaluation, time);
			String objectives = getIndividual(individual);
			if (objectives != null) {
				out.println(statistics + objectives);
			}
		}
		out.flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.common.logger.AbstractLogger#optimizationStopped()
	 */
	@Override
	public void optimizationStopped() {
		out.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.common.logger.AbstractLogger#logHeader(java.util.Collection)
	 */
	@Override
	public void logHeader(Collection<Objective> objectives) {
		String header = getCommentDelimiter() + "iteration" + getColumnDelimiter() + "evaluations"
				+ getColumnDelimiter() + "runtime[s]";
		for (Objective objective : objectives) {
			String name = objective.getName().replaceAll("[ \n\t\r]", "_");
			header += getColumnDelimiter() + name + "[" + objective.getSign() + "]";
		}
		out.println(header);
	}

	/**
	 * The {@link String} separating two columns.
	 * 
	 * The tab character ("\t") is the default, which leads to a tab separated
	 * values file format (TSV).
	 * 
	 * @return the delimiter
	 */
	protected String getColumnDelimiter() {
		return "\t";
	}

	/**
	 * Creates a {@link String} representation of the given {@link Individual}.
	 * 
	 * Per default, the {@link Value}s of all {@link Objectives} of the
	 * individual, separated by {@link #getColumnDelimiter()}, are returned. The
	 * {@link String} representation of a {@link Value} must not contain the tab
	 * character.
	 * 
	 * If one of the values of the {@link Objectives} is INFEASIBLE,
	 * {@code null} is returned.
	 * 
	 * @see Objective
	 * @param individual
	 *            the individual
	 * @return the corresponding string or {@code null}
	 */
	protected String getIndividual(Individual individual) {
		String output = "";
		Objectives objectives = individual.getObjectives();

		for (Objective objective : objectives.getKeys()) {
			Value<?> value = objectives.get(objective);
			assert value != null : "Objective " + objective.getName() + " not set for individual " + individual;

			String valueString;
			if (value == INFEASIBLE || value == null || value.getValue() == null) {
				valueString = "INFEASIBLE";
			} else {
				String v = value.getValue().toString();
				if (v.contains("\t")) {
					System.err.println(this + ": Value must not contain the tab character:" + v);
					v.replace("\t", "_");
				}
				valueString = v;
			}
			output += getColumnDelimiter() + valueString;
		}
		return output;
	}

	/**
	 * Statistics for the given {@code iteration} and {@code evaluation}.
	 * 
	 * Per default, the iteration, the number of evaluations and the
	 * optimization run time in milliseconds, separated by
	 * {@link #getColumnDelimiter()}, are returned.
	 * 
	 * @param iteration
	 *            the current iteration
	 * @param evaluation
	 *            the current number of evaluations
	 * @param time
	 *            the current runtime of the optimization
	 * @return the corresponding string
	 */
	protected String getStatistics(int iteration, int evaluation, double time) {
		return iteration + getColumnDelimiter() + evaluation + getColumnDelimiter() + time;
	}

	/**
	 * The {@link String} starting a line comment.
	 * 
	 * Default is the empty String ("") according to the TSV specification.
	 * 
	 * @return the comment delimiter
	 */
	protected String getCommentDelimiter() {
		return "";
	}

}
