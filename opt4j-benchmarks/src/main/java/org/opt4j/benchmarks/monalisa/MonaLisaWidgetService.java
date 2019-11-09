package org.opt4j.benchmarks.monalisa;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.opt4j.core.Individual;
import org.opt4j.viewer.IndividualMouseListener;
import org.opt4j.viewer.Viewport;
import org.opt4j.viewer.Widget;
import org.opt4j.viewer.WidgetParameters;

import com.google.inject.Inject;

public class MonaLisaWidgetService implements IndividualMouseListener {

	protected final Viewport viewport;

	public static class MyPanel extends JPanel {

		private static final long serialVersionUID = 1L;

		protected final Individual individual;

		@Inject
		public MyPanel(Individual individual) {
			super();
			this.individual = individual;
			int x = ((BufferedImage) individual.getPhenotype()).getWidth();
			int y = ((BufferedImage) individual.getPhenotype()).getHeight();
			setPreferredSize(new Dimension(x, y));
		}

		@Override
		protected void paintComponent(Graphics g) {
			g.drawImage((BufferedImage) individual.getPhenotype(), 0, 0, this);
		}
	}

	@WidgetParameters(title = "MonaLisa", resizable = false, maximizable = false)
	protected static class MonaLisaWidget implements Widget {

		protected final Individual individual;

		public MonaLisaWidget(Individual individual) {
			super();
			this.individual = individual;
		}

		@Override
		public JPanel getPanel() {
			JPanel panel = new MyPanel(individual);
			return panel;
		}

		@Override
		public void init(Viewport viewport) {
			// nothing to be done
		}
	}

	// The route is shown by a double click of a individual in the archive
	// monitor panel. Thus we need the ArchiveMonitorPanel and the main
	// GUIFrame.
	@Inject
	public MonaLisaWidgetService(Viewport viewport) {
		this.viewport = viewport;
	}

	@Override
	public void onDoubleClick(Individual individual, Component table, Point p) {
		paintMonaLisa(individual);
	}

	protected void paintMonaLisa(Individual individual) {
		Widget widget = new MonaLisaWidget(individual);
		viewport.addWidget(widget);
	}

	@Override
	public void onPopup(Individual individual, Component component, Point p, JPopupMenu menu) {
		JMenuItem paint = new JMenuItem("Paint Mona Lisa");
		menu.add(paint);

		paint.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				paintMonaLisa(individual);
			}
		});

	}

}
