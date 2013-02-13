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

import java.io.File;
import java.util.concurrent.RejectedExecutionException;

import javax.swing.JFileChooser;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The {@link FileChooser}.
 * 
 * @author lukasiewycz
 * 
 */
@Singleton
public class FileChooser {

	protected JFileChooser fileChooser = null;

	/**
	 * Constructs a {@link FileChooser}.
	 * 
	 */
	@Inject
	public FileChooser() {
		// Initializing custom file chooser in new thread to prevent blocking
		// the main thread
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					fileChooser = new MyFileChooser();
					fileChooser.setVisible(false);
					ready();
				} catch (RejectedExecutionException e) {
					// if configurator is closed before the file chooser is
					// ready
				}
			}
		};
		thread.start();
	}

	/**
	 * Returns the file chooser.
	 * 
	 * @return the file chooser
	 */
	public synchronized JFileChooser get() {
		while (fileChooser == null) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		return fileChooser;
	}

	/**
	 * Notify all.
	 */
	public synchronized void ready() {
		notifyAll();
	}

	/**
	 * The {@link MyFileChooser} is a custom file chooser.
	 * 
	 * @author lukasiewycz
	 * 
	 */
	@SuppressWarnings("serial")
	private static class MyFileChooser extends JFileChooser {

		public MyFileChooser() {
			super(new File(System.getProperty("user.home")));
		}

		@Override
		public void setCurrentDirectory(final File file) {
			if (file == null) {
				return;
			}

			File dir;
			if (!file.isDirectory()) {
				dir = file.getParentFile();
				super.setSelectedFile(file);
			} else {
				dir = file;
			}

			if (dir == null || !dir.equals(getCurrentDirectory())) {
				super.setCurrentDirectory(dir);
			}
		}

	}

}
