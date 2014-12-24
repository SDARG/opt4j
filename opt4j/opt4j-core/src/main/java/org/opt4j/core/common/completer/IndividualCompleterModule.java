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
 

package org.opt4j.core.common.completer;

import org.opt4j.core.config.Icons;
import org.opt4j.core.config.annotations.Icon;
import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.config.annotations.Required;
import org.opt4j.core.optimizer.IndividualCompleter;
import org.opt4j.core.start.Constant;
import org.opt4j.core.start.Opt4JModule;

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
