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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.RenderingHints;
import java.awt.SplashScreen;
import java.io.IOException;
import java.util.Properties;

import org.opt4j.core.config.ModuleAutoFinder;
import org.opt4j.core.config.ModuleAutoFinderListener;
import org.opt4j.core.config.ModuleList;
import org.opt4j.core.config.ModuleListUser;
import org.opt4j.core.config.Task;
import org.opt4j.core.config.visualization.About;
import org.opt4j.core.config.visualization.Configurator;
import org.opt4j.core.config.visualization.DelayTask;
import org.opt4j.core.config.visualization.TasksPanel;

import com.google.inject.Binder;
import com.google.inject.Module;

/**
 * The {@link Opt4J} configuration GUI.
 * 
 * @author lukasiewycz
 * 
 */
public class Opt4J extends Configurator {

	protected static final ModuleListUser moduleList = new ModuleListUser();

	protected static Properties props = new Properties();

	static {
		try {
			props.load(Opt4J.class.getResourceAsStream("/main.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the version of the current build.
	 * 
	 * @return the version
	 */
	public static String getVersion() {
		return props.getProperty("version");
	}

	/**
	 * Returns the date of the current build.
	 * 
	 * @return the date
	 */
	public static String getDateISO() {
		return props.getProperty("date");
	}

	static class SplashDecorator {

		protected DelayTask delay = new DelayTask(1);

		protected SplashScreen splash;

		public SplashDecorator(SplashScreen splash) {
			this.splash = splash;
		}

		public SplashScreen getSplash() {
			return splash;
		}

		public void print(final String message, final Color color) {
			delay.execute(new Runnable() {
				@Override
				public void run() {
					Graphics2D g = splash.createGraphics();
					g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					g.setColor(Color.WHITE);
					g.setBackground(new Color(252, 230, 212));
					g.clearRect(10, 175, 280, 20);
					g.setClip(10, 175, 280, 20);
					g.setColor(color);
					g.setFont(new Font("SansSerif", Font.BOLD, 9));
					g.drawString(message, 12, 188);
					splash.update();
				}
			});
		}
	}

	/**
	 * Starts the {@link Opt4J} configuration GUI.
	 * 
	 * @param args
	 *            accepts a configuration file
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("Starting Opt4J " + getVersion() + " (Build " + getDateISO() + ")");
		if (args.length > 0 && args[0].equalsIgnoreCase("-s")) {
			SplashScreen splash = null;
			try {
				splash = SplashScreen.getSplashScreen();
			} catch (HeadlessException e) {
				// ignore
			}
			if (splash != null) {
				splash.close();
			}
			String[] a = new String[args.length - 1];
			System.arraycopy(args, 1, a, 0, a.length);
			Opt4JStarter.main(a);
		} else {
			SplashScreen splash = SplashScreen.getSplashScreen();
			SplashDecorator decorator = null;
			if (splash != null) {
				decorateVersionDate(splash);
				decorator = new SplashDecorator(splash);
			}
			searchModules(decorator);

			Configurator configurator = new Opt4J();
			configurator.start(args);
		}
	}

	/**
	 * Decorate the splash screen with the version and date.
	 * 
	 * @param splash
	 *            the splash screen
	 */
	protected static void decorateVersionDate(SplashScreen splash) {
		if (splash != null) {
			Graphics2D g = splash.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setColor(new Color(242, 130, 38));
			g.setFont(new Font("SansSerif", Font.BOLD, 11));
			g.drawString("version " + getVersion(), 170, 76);
			g.drawString(getDateISO(), 170, 91);
			splash.update();
		}
	}

	/**
	 * Search for modules and forward the output to the splash screen.
	 * 
	 * @param splash
	 *            the splash screen
	 */
	private static void searchModules(SplashDecorator splash) {

		class SplashSearchDecorator implements ModuleAutoFinderListener {

			protected final SplashDecorator splash;

			SplashSearchDecorator(SplashDecorator splash) {
				this.splash = splash;
			}

			@Override
			public void err(String message) {
				splash.print(message, Color.RED);
			}

			@Override
			public void out(final String message) {
				splash.print(message, Color.GRAY.darker());
			}
		}

		ModuleAutoFinder finder = new ModuleAutoFinder();
		if (splash != null) {
			SplashSearchDecorator deco = new SplashSearchDecorator(splash);
			finder.addListener(deco);
		}

		for (Class<? extends Module> module : finder.getModules()) {
			moduleList.add(module);
		}

		if (splash != null) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.config.gui.Configurator#start(java.lang.String[])
	 */
	@Override
	public void start(String[] args) {
		String filename = null;
		if (args.length > 0) {
			filename = args[0];
		}
		main(Opt4JTask.class, filename);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.config.gui.Configurator#getModule(java.lang.Class)
	 */
	@Override
	public Module getModule(final Class<? extends Task> taskClass) {
		Module module = new Module() {
			@Override
			public void configure(Binder b) {
				b.bind(Task.class).to(taskClass);
				b.bind(About.class).to(Opt4JAbout.class);
				b.bind(TasksPanel.class).to(Opt4JTasksPanel.class);
				b.bind(ModuleList.class).toInstance(moduleList);
			}
		};
		return module;
	}

}
