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

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.ImageIcon;

/**
 * The {@link Icons} class is used to get {@link ImageIcon}s from a given
 * {@code filename}. Moreover, it contains the {@code filenames} of some
 * standard icons.
 * 
 * @author glass
 * 
 */
public class Icons {

	/**
	 * The icon for the xml.
	 */
	public static final String XMLTAG = "icons/xmltag.png";

	/**
	 * The icon for the xml.
	 */
	public static final String XML = "icons/xml.png";

	/**
	 * The icon for the optimizer.
	 */
	public static final String OPTIMIZER = "icons/tools-blue.png";

	/**
	 * The icon for the problem.
	 */
	public static final String PROBLEM = "icons/gear-gray.png";

	/**
	 * The icon for the archive.
	 */
	public static final String ARCHIVE = "icons/user-green.png";

	/**
	 * The icon for the population.
	 */
	public static final String POPULATION = "icons/user-blue.png";

	/**
	 * The icon for the pareto contentPanel.
	 */
	public static final String PARETO = "icons/stat.png";

	/**
	 * The icon for the convergence plot.
	 */
	public static final String CONVERGENCE = "icons/convergence.png";

	/**
	 * The icon for the play button.
	 */
	public static final String PLAY = "icons/play.png";

	/**
	 * The icon for the operator.
	 */
	public static final String OPERATOR = "icons/tools-orange.png";

	/**
	 * The icon for the application.
	 */
	public static final String APPLICATION = "icons/appl.png";

	/**
	 * The icon for the green puzzle.
	 */
	public static final String PUZZLE_GREEN = "icons/puzzle-green.png";

	/**
	 * The icon for the blue puzzle.
	 */
	public static final String PUZZLE_BLUE = "icons/puzzle-blue.png";

	/**
	 * The icon for the selector.
	 */
	public static final String SELECTOR = "icons/tools-orange.png";

	/**
	 * The icon for the tutorial.
	 */
	public static final String HELP = "icons/help.png";

	/**
	 * The icon for the control start.
	 */
	public static final String CONTROL_START = "icons/control_start.png";

	/**
	 * The icon for the control stop.
	 */
	public static final String CONTROL_STOP = "icons/control_stop.png";

	/**
	 * The icon for the control pause.
	 */
	public static final String CONTROL_PAUSE = "icons/control_pause.png";

	/**
	 * The icon for the control term.
	 */
	public static final String CONTROL_TERM = "icons/control_term.png";

	/**
	 * The icon for the control term.
	 */
	public static final String FOLDER = "icons/folder.png";

	/**
	 * The icon for the control term.
	 */
	public static final String FOLDER_ADD = "icons/folder_add.png";

	/**
	 * The icon for the control term.
	 */
	public static final String SFOLDER = "icons/small_folder.png";

	/**
	 * A disk.
	 */
	public static final String DISK = "icons/disk.png";

	/**
	 * A console.
	 */
	public static final String CONSOLE = "icons/console.png";

	/**
	 * The Opt4J logo.
	 */
	public static final String OPT4J = "icons/logo2.png";

	/**
	 * An add bullet.
	 */
	public static final String ADD = "icons/add.png";

	/**
	 * A delete bullet.
	 */
	public static final String DELETE = "icons/delete.png";

	/**
	 * A delete bullet.
	 */
	public static final String LOADING = "icons/loading.gif";

	/**
	 * A text sheet.
	 */
	public static final String TEXT = "icons/text.png";

	/**
	 * Constructs {@link Icons}.
	 */
	public Icons() {
		super();
	}

	/**
	 * Returns the {@link URL} of a file.
	 * 
	 * @param filename
	 *            the filename
	 * @return the url
	 */
	public static URL getURL(String filename) {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		URL url = classLoader.getResource(filename);
		return url;
	}

	/**
	 * Returns an {@link ImageIcon} from a filename.
	 * 
	 * @param filename
	 *            the filename
	 * @return the corresponding image icon
	 */
	public static ImageIcon getIcon(String filename) {
		URL url = getURL(filename);
		try {
			return new ImageIcon(url);
		} catch (NullPointerException e) {
			System.err.println("Image " + filename + " not found.");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Returns a transparent 16x16px {@link ImageIcon}.
	 * 
	 * @return a transparent 16x16px {@link ImageIcon}
	 */
	public static ImageIcon getDefault() {
		Image image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		ImageIcon icon = new ImageIcon(image);

		return icon;
	}

	/**
	 * This methods merges multiple {@link ImageIcon}s into a single
	 * {@link ImageIcon}.
	 * 
	 * @param icons
	 *            the icons to merge
	 * @return the merged icons
	 */
	public static ImageIcon merge(ImageIcon... icons) {

		/**
		 * The {@link MergedIcon} merges two icons into one.
		 * 
		 * @author lukasiewycz
		 * 
		 */
		@SuppressWarnings("serial")
		class MergedIcon extends ImageIcon {

			private final ImageIcon[] icons;

			public MergedIcon(ImageIcon[] icons) {
				super();
				this.icons = icons;
			}

			@Override
			public void paintIcon(Component c, Graphics g, int x, int y) {
				for (ImageIcon icon : icons) {
					icon.paintIcon(c, g, x, y);
				}
			}

			@Override
			public int getIconWidth() {
				return icons[0].getIconWidth();
			}

			@Override
			public int getIconHeight() {
				return icons[0].getIconHeight();
			}
		}

		return new MergedIcon(icons);
	}
}
