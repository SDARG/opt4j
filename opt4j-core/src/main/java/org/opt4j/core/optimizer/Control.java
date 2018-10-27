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
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *******************************************************************************/

package org.opt4j.core.optimizer;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import com.google.inject.Singleton;

/**
 * <p>
 * The {@link Control} allows to pause, stop, and terminate, the optimization
 * process.
 * </p>
 * <p>
 * The {@link Optimizer} calls the methods
 * <ul>
 * <li>{@link Control#checkpoint()} and</li>
 * <li>{@link Control#checkpointStop()}</li>
 * </ul>
 * in which the process might get paused, terminated (by the
 * {@link TerminationException}) or stopped (by the {@link StopException}). By
 * definition, a {@link StopException} might get thrown only between iterations,
 * a {@link TerminationException} also within an iteration of the optimization
 * algorithm. In this context, the optimization algorithm calls
 * {@link #checkpointStop()} between the iterations and {@link #checkpoint()}
 * within the iteration.
 * </p>
 * 
 * @author lukasiewycz
 * 
 */
@Singleton
public class Control {

	protected State state = State.RUNNING;

	protected final Set<ControlListener> listeners = new CopyOnWriteArraySet<>();

	/**
	 * The {@link State} of the control.
	 * 
	 * @author lukasiewycz
	 * 
	 */
	public enum State {
		/**
		 * The running state.
		 */
		RUNNING,
		/**
		 * The paused state.
		 */
		PAUSED,
		/**
		 * The stopped state.
		 */
		STOPPED,
		/**
		 * The terminated state.
		 */
		TERMINATED;

		/**
		 * Returns {@code true} if the current state allows to start the
		 * optimization.
		 * 
		 * @return {@code true} if start is available
		 */
		public boolean isStartable() {
			return (this == State.PAUSED);
		}

		/**
		 * Returns {@code true} if the current state allows to pause the
		 * optimization.
		 * 
		 * @return {@code true} if pause is available
		 */
		public boolean isPausable() {
			return (this == State.RUNNING);
		}

		/**
		 * Returns {@code true} if the current state allows to stop the
		 * optimization.
		 * 
		 * @return {@code true} if stop is available
		 */
		public boolean isStoppable() {
			return (this != State.TERMINATED);
		}
	}

	/**
	 * Starts the optimization.
	 */
	public synchronized void doStart() {
		if (state.isStartable()) {
			setState(State.RUNNING);
		}
	}

	/**
	 * Pauses the optimization.
	 */
	public synchronized void doPause() {
		if (state.isPausable()) {
			setState(State.PAUSED);
		}
	}

	/**
	 * Stops the optimization.
	 */
	public synchronized void doStop() {
		if (state.isStoppable()) {
			setState(State.STOPPED);
		}
	}

	/**
	 * Terminates the optimization.
	 */
	public synchronized void doTerminate() {
		setState(State.TERMINATED);
	}

	/**
	 * Returns {@code true} if the optimization is running.
	 * 
	 * @return {@code true} if the optimization is running
	 */
	public synchronized boolean isRunning() {
		return (state == State.RUNNING);
	}

	/**
	 * Returns {@code true} if the optimization is paused.
	 * 
	 * @return {@code true} if the optimization is paused
	 */
	public synchronized boolean isPaused() {
		return (state == State.PAUSED);
	}

	/**
	 * Returns {@code true} if the optimization is stopped.
	 * 
	 * @return {@code true} if the optimization is stopped
	 */
	public synchronized boolean isStopped() {
		return (state == State.STOPPED);
	}

	/**
	 * Returns {@code true} if the optimization is terminated.
	 * 
	 * @return {@code true} if the optimization is terminated
	 */
	public synchronized boolean isTerminated() {
		return (state == State.TERMINATED);
	}

	/**
	 * A checkpoint that checks for termination.
	 * 
	 * @throws TerminationException
	 *             if the optimization is terminated
	 */
	public synchronized void checkpoint() throws TerminationException {
		while (state == State.PAUSED) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}

		if (state == State.TERMINATED) {
			throw new TerminationException();
		}
	}

	/**
	 * A checkpoint that checks for termination and stop.
	 * 
	 * @throws TerminationException
	 *             if the optimization is terminated
	 * @throws StopException
	 *             if the optimization is stopped
	 */
	public synchronized void checkpointStop() throws TerminationException, StopException {
		checkpoint();
		if (state == State.STOPPED) {
			throw new StopException();
		}
	}

	/**
	 * Returns the state.
	 * 
	 * @return the state
	 */
	public State getState() {
		return state;
	}

	/**
	 * Sets the state and invokes listeners if the state has changed.
	 * 
	 * @param state
	 *            the desired state
	 */
	protected synchronized void setState(State state) {
		boolean changed = (this.state != state);

		if (changed) {
			this.state = state;
			// wake up all potentially waiting (paused) processes
			notifyAll();

			for (ControlListener listener : listeners) {
				listener.stateChanged(state);
			}
		}
	}

	/**
	 * Adds a {@link ControlListener}.
	 * 
	 * @see #removeListener
	 * @param listener
	 *            the listener to add
	 */
	public void addListener(ControlListener listener) {
		listeners.add(listener);
	}

	/**
	 * Removes a {@link ControlListener}.
	 * 
	 * @see #addListener
	 * @param listener
	 *            the listener to remove
	 */
	public void removeListener(ControlListener listener) {
		listeners.remove(listener);
	}
}
