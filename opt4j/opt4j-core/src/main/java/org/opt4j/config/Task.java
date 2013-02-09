/**
 * Opt4J is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with Opt4J. If not, see http://www.gnu.org/licenses/. 
 */

package org.opt4j.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArraySet;

import com.google.inject.Module;

/**
 * The {@link Task} is an abstract class for optimization tasks.
 * 
 * @author lukasiewycz
 * @see ExecutionEnvironment
 */
public abstract class Task implements Callable<Void> {

	protected State state = State.WAITING;

	protected Exception e = null;

	protected final List<Module> modules = new ArrayList<Module>();

	protected boolean isInit = false;

	protected final Set<TaskStateListener> listeners = new CopyOnWriteArraySet<TaskStateListener>();

	/**
	 * The {@link State} of a task.
	 * 
	 * @author lukasiewycz
	 * 
	 */
	public enum State {
		/**
		 * The task is waiting for its execution.
		 */
		WAITING,
		/**
		 * The task is currently executed.
		 */
		EXECUTING,
		/**
		 * The task is done.
		 */
		DONE;
	}

	/**
	 * Initializes the {@link Task} with modules.
	 * 
	 * @see Task#init(Collection)
	 * @param modules
	 *            the modules for the task
	 */
	public void init(Module... modules) {
		init(Arrays.asList(modules));
	}

	/**
	 * Initializes the {@link Task} with modules. Note that if a module is a
	 * {@link PropertyModule}, a copy is made with the
	 * {@link PropertyModule#clone()} method. If the {@link Module} is not a
	 * {@link PropertyModule}, the module is copied as a reference, i.e., it can
	 * be changed until the {@link Task#execute()} is called.
	 * 
	 * @param modules
	 *            the modules for the task
	 */
	public void init(Collection<Module> modules) {
		for (Module module : modules) {
			if (module instanceof PropertyModule) {
				PropertyModule pModule = (PropertyModule) module;
				this.modules.add(pModule.clone());
			} else {
				this.modules.add(module);
			}
		}
		isInit = true;
	}

	/**
	 * Executes the {@link Task}.
	 * 
	 * @throws Exception
	 */
	public abstract void execute() throws Exception;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public Void call() throws Exception {
		setState(State.EXECUTING);
		try {
			execute();
		} catch (Error e) {
			this.e = new RuntimeException(e);
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			this.e = e;
			e.printStackTrace();
			throw e;
		} finally {
			setState(State.DONE);
		}

		return null;
	}

	/**
	 * Returns the {@link Exception} that was thrown during the {@link Task}
	 * execution if an error occurred.
	 * 
	 * @return the occurred exception
	 */
	public Exception getException() {
		return e;
	}

	/**
	 * Returns the state of the {@link Task}.
	 * 
	 * @return the state of the task
	 */
	public State getState() {
		return state;
	}

	/**
	 * Sets the state of the {@link Task}.
	 * 
	 * @param state
	 *            the state of the task to be set
	 */
	protected void setState(State state) {
		this.state = state;
		for (TaskStateListener listener : listeners) {
			listener.stateChanged(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "";
	}

	/**
	 * Adds a {@link TaskStateListener}.
	 * 
	 * @see #removeStateListener
	 * @param listener
	 *            the listener to be added
	 */
	public void addStateListener(TaskStateListener listener) {
		listeners.add(listener);
	}

	/**
	 * Removes a {@link TaskStateListener}.
	 * 
	 * @see #addStateListener
	 * @param listener
	 *            the listener to be removed
	 */
	public void removeStateListener(TaskStateListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Returns a copy of the {@link Module}s of this {@link Task}.
	 * 
	 * @return a set of modules
	 */
	public Collection<Module> getModules() {
		List<Module> clones = new ArrayList<Module>();
		for (Module module : modules) {
			PropertyModule pModule;
			if (module instanceof PropertyModule) {
				pModule = (PropertyModule) module;
			} else {
				pModule = new PropertyModule(module);
			}

			clones.add(pModule.clone());
		}
		return clones;
	}
}
