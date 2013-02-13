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
