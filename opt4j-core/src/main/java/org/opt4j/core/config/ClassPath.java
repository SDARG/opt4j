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
 

package org.opt4j.core.config;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * The {@link ClassPath} is also known as the {@code ClassPathHacker} and
 * enables adding new files to the classpath.
 * 
 * @author lukasiewycz
 * 
 */
public class ClassPath {

	private static final Class<?>[] parameters = new Class[] { URL.class };

	/**
	 * Adds a new {@link File} to the classpath.
	 * 
	 * @param s
	 *            the name of the file
	 * @throws IOException
	 */
	public static void addFile(String s) throws IOException {
		File f = new File(s);
		addFile(f);
	}// end method

	/**
	 * Adds a new {@link File} to the classpath.
	 * 
	 * @param f
	 *            the file
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public static void addFile(File f) throws IOException {
		addURL(f.toURL());
	}// end method

	/**
	 * Adds a new {@link File} to the classpath.
	 * 
	 * @param u
	 *            the {@link URL} of the file
	 * @throws IOException
	 */
	public static void addURL(URL u) throws IOException {

		URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		Class<?> sysclass = URLClassLoader.class;

		try {
			Method method = sysclass.getDeclaredMethod("addURL", parameters);
			method.setAccessible(true);
			method.invoke(sysloader, new Object[] { u });
		} catch (Throwable t) {
			t.printStackTrace();
			throw new IOException("Error, could not add URL to system classloader");
		}
	}

}
