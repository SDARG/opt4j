package org.opt4j.config.visualization;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;

import org.opt4j.config.Icons;
import org.opt4j.start.Opt4J;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The {@link DefaultApplicationFrame}.
 * 
 * @author lukasiewycz
 * 
 */
@SuppressWarnings("serial")
@Singleton
public class DefaultApplicationFrame extends ApplicationFrame {

	protected final ContentPanel contentPanel;
	protected final About about;
	protected final ToolBar toolBar;
	protected final Menu menu;
	protected final FileOperations fileOperations;

	protected final String title = "Opt4J "+Opt4J.getVersion()+" Configurator";

	/**
	 * Constructs a {@link DefaultApplicationFrame} .
	 * 
	 * @param panel
	 *            the content panel
	 * @param about
	 *            the about information
	 * @param menu
	 *            the menu
	 * @param toolBar
	 *            the tool bar
	 * @param fileOperations
	 *            the file operations
	 * @throws HeadlessException
	 */
	@Inject
	public DefaultApplicationFrame(ContentPanel panel, About about, Menu menu, ToolBar toolBar,
			FileOperations fileOperations) throws HeadlessException {
		this.contentPanel = panel;
		this.about = about;
		this.menu = menu;
		this.toolBar = toolBar;
		this.fileOperations = fileOperations;
	}

	/**
	 * Registers the listeners.
	 */
	@Inject
	public void init() {
		fileOperations.addListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.config.visualization.Startupable#startup()
	 */
	@Override
	public void startup() {
		WindowListener exitListener = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Window window = e.getWindow();
				window.setVisible(false);
				window.dispose();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ignore) {
				} finally {
					System.exit(0);
				}
			}
		};
		addWindowListener(exitListener);

		setIconImage(Icons.getIcon(Icons.OPT4J).getImage());
		setTitle(title);
		setLayout(new BorderLayout());
		JSeparator jSeparator = new JSeparator();

		JPanel toolBarPanel = new JPanel(new BorderLayout());
		toolBarPanel.add(toolBar, BorderLayout.CENTER);
		toolBarPanel.add(jSeparator, BorderLayout.SOUTH);

		add(toolBarPanel, BorderLayout.NORTH);
		add(contentPanel, BorderLayout.CENTER);

		setJMenuBar(menu);

		menu.startup();
		toolBar.startup();
		contentPanel.startup();

		pack();
		setVisible(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.conf.gui.FileOperations.FileOperationsListener#setCurrentFile
	 * (java.io.File)
	 */
	@Override
	public void setCurrentFile(final File file) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					setTitle(title + " [" + file.getCanonicalPath() + "]");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

}
