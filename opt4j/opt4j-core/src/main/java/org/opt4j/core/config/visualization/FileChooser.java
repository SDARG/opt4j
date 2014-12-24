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
 

package org.opt4j.core.config.visualization;

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
