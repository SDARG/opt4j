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
package org.opt4j.config.visualization;

/**
 * The {@link DelayTask} executes tasks immediately or delayed if they arrive
 * too close (delay). If they arrive way too close, older tasks will be dropped.
 * 
 * @author lukasiewycz, reimann
 * 
 */
public class DelayTask {

	private final DelayThread thread;

	/**
	 * The {@link DelayThread} executes a task and sleeps {@code delay}
	 * milliseconds. Then it executes the next task.
	 * 
	 * If no task is available, it waits until it is notified of the arrival of
	 * a new task.
	 * 
	 * @author reimann, lukasiewycz
	 * 
	 */
	private static class DelayThread extends Thread {
		private Runnable task = null;
		private boolean run = true;
		private final long delay;

		/**
		 * Creates a new {@link DelayTask}.
		 * 
		 * @param delay
		 *            the time between the execution of two tasks
		 */
		public DelayThread(long delay) {
			this.delay = delay;
		}

		/**
		 * Stops this thread.
		 */
		public synchronized void doStop() {
			run = false;
			notify();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			while (run) {
				Runnable task = getTaskAndSetToNull();
				if (task != null) {
					task.run();
					try {
						sleep(delay);
					} catch (InterruptedException e) {
					}
				} else {
					synchronized (this) {
						try {
							wait();
						} catch (InterruptedException e) {
						}
					}
				}
			}
		}

		/**
		 * Synchronized getter for the task that sets the task to {@code null}.
		 * 
		 * @return the task
		 */
		protected synchronized Runnable getTaskAndSetToNull() {
			Runnable t = task;
			task = null;
			return t;
		}

		/**
		 * Synchronized update of the task.
		 * 
		 * @param task
		 *            the task to execute
		 */
		public synchronized void updateTask(Runnable task) {
			this.task = task;
			notify();
		}
	}

	/**
	 * Constructs a {@link DelayTask}.
	 * 
	 * @param delay
	 *            the minimum delay between two tasks
	 */
	public DelayTask(long delay) {
		this(delay, Thread.NORM_PRIORITY);
	}

	/**
	 * Constructs a {@link DelayTask} with a given priority.
	 * 
	 * 
	 * @param delay
	 *            the minimum delay between two tasks
	 * @param priority
	 *            the priority of the executing thread
	 */
	public DelayTask(long delay, int priority) {
		super();
		if (delay < 0) {
			throw new IllegalArgumentException("Delay is negative:" + delay);
		}
		thread = new DelayThread(delay);
		if (priority < Thread.MIN_PRIORITY || priority > Thread.MAX_PRIORITY) {
			throw new IllegalArgumentException("Invalid priority: " + priority);
		}
		thread.setPriority(priority);
		thread.setDaemon(true);
		thread.start();
	}

	/**
	 * Executes the task. If tasks arrive to close, some of them are dropped.
	 * 
	 * @param task
	 *            the task to be executed
	 */
	public void execute(final Runnable task) {
		thread.updateTask(task);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		thread.doStop();
		super.finalize();
	}
}
