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

package org.opt4j.common.completer;

import org.opt4j.config.Icons;
import org.opt4j.config.annotations.Icon;
import org.opt4j.config.annotations.Info;
import org.opt4j.config.annotations.Required;
import org.opt4j.core.optimizer.IndividualCompleter;
import org.opt4j.start.Constant;
import org.opt4j.start.Opt4JModule;

/**
 * The {@link IndividualCompleterModule} is used to choose and configure a
 * {@link IndividualCompleter}.
 * 
 * @author lukasiewycz
 * 
 */
@Icon(Icons.PUZZLE_BLUE)
@Info("The IndividualCompleter decodes and evaluates the individuals in the optimization process.")
public class IndividualCompleterModule extends Opt4JModule {

	@Info("Sets the type of the individual completer.")
	protected Type type = Type.SEQUENTIAL;

	@Info("Sets the number of parallel processes.")
	@Required(property = "type", elements = { "PARALLEL" })
	@Constant(value = "maxThreads", namespace = ParallelIndividualCompleter.class)
	protected int threads = 4;

	/**
	 * The {@link Type} of {@link IndividualCompleter} to use.
	 * 
	 * @author glass
	 * 
	 */
	public enum Type {
		/**
		 * Use a sequential completer.
		 * 
		 * @see SequentialIndividualCompleter
		 */
		@Info("Use a SequentialCompleter")
		SEQUENTIAL,

		/**
		 * Use a parallel completer.
		 * 
		 * @see ParallelIndividualCompleter
		 */
		@Info("Use a ParallelCompleter")
		PARALLEL;
	}

	/**
	 * Returns the type of the completer.
	 * 
	 * @see #setType
	 * @return type of the completer
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Sets the type of the completer.
	 * 
	 * @see #getType
	 * @param type
	 *            the type of the completer
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * Returns the maximal number of parallel threads.
	 * 
	 * @see #setThreads
	 * @return the maximal number of parallel threads
	 */
	public int getThreads() {
		return threads;
	}

	/**
	 * Sets the maximal number of parallel threads.
	 * 
	 * @see #getThreads
	 * @param threads
	 *            the maximal number of parallel threads
	 */
	public void setThreads(int threads) {
		if (threads <= 0) {
			throw new IllegalArgumentException("The number of threads must be positive: " + threads);
		}
		this.threads = threads;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.start.Opt4JModule#config()
	 */
	@Override
	public void config() {
		switch (type) {
		case SEQUENTIAL:
			bind(IndividualCompleter.class).to(SequentialIndividualCompleter.class).in(SINGLETON);
			break;
		default: // PARALLEL
			bind(ParallelIndividualCompleter.class).in(SINGLETON);
			bind(IndividualCompleter.class).to(ParallelIndividualCompleter.class);
			addOptimizerStateListener(ParallelIndividualCompleter.class);
			break;
		}
	}
}
