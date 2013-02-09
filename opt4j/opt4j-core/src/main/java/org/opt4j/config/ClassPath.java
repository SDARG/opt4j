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
