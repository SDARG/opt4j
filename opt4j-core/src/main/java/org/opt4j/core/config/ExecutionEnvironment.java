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

package org.opt4j.core.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * The {@link ExecutionEnvironment} for {@link Task}s. The environment starts
 * and monitors optimization tasks. Tasks are not submitted directly, instead a
 * collection of {@link Module}s is submitted and a {@link Task} is created. The
 * {@link ExecutionEnvironment} is processing the {@link Task}s sequentially.
 * 
 * @author lukasiewycz
 * @see Task
 * 
 */
@Singleton
public class ExecutionEnvironment implements TaskStateListener {

	protected final Provider<Task> taskProvider;

	protected final ExecutorService executor;

	protected final List<Task> tasks = new ArrayList<Task>();

	protected final Set<TaskListener> listeners = new CopyOnWriteArraySet<TaskListener>();

	/**
	 * Constructs a {@link ExecutionEnvironment}.
	 * 
	 * @param taskProvider
	 *            the task provider
	 */
	@Inject
	public ExecutionEnvironment(Provider<Task> taskProvider) {
		this.taskProvider = taskProvider;
		this.executor = Executors.newFixedThreadPool(1);
	}

	/**
	 * Executes the specified modules: A {@link Task} is created and submitted.
	 * 
	 * @param modules
	 *            the collection of modules for a {@link Task}
	 */
	public void execute(Collection<Module> modules) {

		final Task task = taskProvider.get();
		task.addStateListener(this);
		task.init(modules);

		addTask(task);

		Thread thread = new Thread() {
			@Override
			public void run() {
				Future<Void> future = executor.submit(task);
				try {
					future.get();
				} catch (InterruptedException e) {
				} catch (ExecutionException e) {
				}
			}
		};
		thread.start();
	}

	/**
	 * Adds a {@link Task} and calls the listeners.
	 * 
	 * @param task
	 *            the task to add
	 */
	protected void addTask(final Task task) {
		tasks.add(task);
		for (TaskListener listener : listeners) {
			listener.added(task);
		}
	}

	/**
	 * Adds a {@link TaskListener}.
	 * 
	 * @param listener
	 *            the listener to be added
	 */
	public void addListener(TaskListener listener) {
		listeners.add(listener);
	}

	/**
	 * Removes a {@link TaskListener}.
	 * 
	 * @see #addListener
	 * @param listener
	 *            the listener to be removed
	 */
	public void removeListener(TaskListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Returns the list of all {@link Task}s.
	 * 
	 * @return the list of all tasks
	 */
	public List<Task> getTasks() {
		return tasks;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.config.TaskStateListener#stateChanged(org.opt4j.config.Task)
	 */
	@Override
	public void stateChanged(Task task) {
		for (TaskListener listener : listeners) {
			listener.stateChanged(task);
		}
	}

}
