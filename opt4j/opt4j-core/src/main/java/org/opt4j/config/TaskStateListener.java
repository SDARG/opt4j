package org.opt4j.config;

/**
 * The {@link TaskStateListener}.
 * 
 * @author lukasiewycz
 * 
 */
public interface TaskStateListener {

	/**
	 * Invoked if a {@link Task} changes its state.
	 * 
	 * @param task
	 *            the task that changed its state
	 */
	public void stateChanged(Task task);

}
