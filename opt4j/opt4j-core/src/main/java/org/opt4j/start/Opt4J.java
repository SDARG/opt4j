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
package org.opt4j.start;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.SplashScreen;
import java.io.IOException;
import java.util.Properties;

import org.opt4j.config.ModuleAutoFinder;
import org.opt4j.config.ModuleAutoFinderListener;
import org.opt4j.config.ModuleList;
import org.opt4j.config.ModuleListUser;
import org.opt4j.config.Task;
import org.opt4j.config.visualization.About;
import org.opt4j.config.visualization.Configurator;
import org.opt4j.config.visualization.DelayTask;
import org.opt4j.config.visualization.TasksPanel;

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

		DelayTask delay = new DelayTask(1);

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
		System.out.println("Starting Opt4J "+getVersion()+" (Build "+getDateISO()+")");
		if (args.length > 0 && args[0].equalsIgnoreCase("-s")) {
			SplashScreen splash = SplashScreen.getSplashScreen();
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
			//initVisualization(decorator);
			searchModules(decorator);

			Configurator configurator = new Opt4J();
			configurator.start(args);
		}
	}

	protected static void initVisualization(SplashDecorator splash) {
		if (splash != null) {
			splash.print("Initialize Visualization", Color.GRAY.darker());
		}
		/*if (DefaultFonts.LABElFONT == null) {
			throw new IllegalStateException();
		}*/
		if (splash != null) {
			splash.print("Initialized Visualization", Color.GRAY.darker());
		}
	}

	/**
	 * Decorate the splash screen with the version and date.
	 * 
	 * @param splash
	 *            the slpash screen
	 */
	protected static void decorateVersionDate(SplashScreen splash) {
		if (splash != null) {
			Graphics2D g = splash.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setColor(new Color(242, 130, 38));
			g.setFont(new Font("SansSerif", Font.BOLD, 11));
			g.drawString("version "+getVersion(), 170, 76);
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
