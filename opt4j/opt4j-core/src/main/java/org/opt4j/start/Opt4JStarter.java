package org.opt4j.start;

import org.opt4j.config.Starter;

/**
 * The {@link Opt4JStarter} starts configuration files directly without the
 * configurator (GUI).
 * 
 * @author lukasiewycz
 * 
 */
public class Opt4JStarter extends Starter {

	/**
	 * Starts the configuration files.
	 * 
	 * @param args
	 *            the files
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Starter starter = new Opt4JStarter();
		starter.execute(args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.config.Starter#execute(java.lang.String[])
	 */
	@Override
	public void execute(String[] args) throws Exception {
		addPlugins();
		execute(Opt4JTask.class, args);
	}
}
