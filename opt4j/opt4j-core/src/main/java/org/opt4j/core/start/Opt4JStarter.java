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
 
package org.opt4j.core.start;

import org.opt4j.core.config.Starter;

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
