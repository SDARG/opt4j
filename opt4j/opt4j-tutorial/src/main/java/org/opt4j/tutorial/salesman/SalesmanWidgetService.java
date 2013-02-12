package org.opt4j.tutorial.salesman;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.opt4j.core.Individual;
import org.opt4j.tutorial.salesman.SalesmanProblem.City;
import org.opt4j.viewer.IndividualMouseListener;
import org.opt4j.viewer.Viewport;
import org.opt4j.viewer.Widget;
import org.opt4j.viewer.WidgetParameters;

import com.google.inject.Inject;

// The SalesmanWidgetService is an additional feature of this tutorial. It enables the visualization
// of a single route in the viewer.
public class SalesmanWidgetService implements IndividualMouseListener {

	// Panel that paints a single SalesmanRoute (which is the phenotype of an
	// Individual)
	public static class MyPanel extends JPanel {

		private static final long serialVersionUID = 1L;

		protected final Individual individual;

		public MyPanel(Individual individual) {
			super();
			this.individual = individual;
			setPreferredSize(new Dimension(208, 208));
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setBackground(Color.WHITE);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setStroke(new BasicStroke(2f));
			g2d.clearRect(0, 0, 208, 212);

			SalesmanRoute salesmanRoute = (SalesmanRoute) individual.getPhenotype();

			for (int i = 0; i < salesmanRoute.size(); i++) {
				final int j = (i + 1) % salesmanRoute.size();
				City one = salesmanRoute.get(i);
				City two = salesmanRoute.get(j);

				int x1 = (int) (one.getX() * 2) + 4;
				int y1 = (int) (one.getY() * 2) + 4;
				int x2 = (int) (two.getX() * 2) + 4;
				int y2 = (int) (two.getY() * 2) + 4;

				g2d.drawLine(x1, y1, x2, y2);
				g2d.drawOval(x1 - 2, y1 - 2, 4, 4);

			}
		}
	}

	// Use a custom widget
	@WidgetParameters(title = "Route", resizable = false, maximizable = false)
	protected static class SalesmanWidget implements Widget {

		final Individual individual;

		public SalesmanWidget(Individual individual) {
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
		}

	}

	protected final Viewport viewport;

	// The route is shown by a double click of a individual in the archive
	// monitor panel. Thus we need the ArchiveMonitorPanel and the main
	// GUIFrame.
	@Inject
	public SalesmanWidgetService(Viewport viewport) {
		this.viewport = viewport;
	}

	// If an individual is double clicked, paint the route.
	@Override
	public void onDoubleClick(Individual individual, Component table, Point p) {
		paintRoute(individual);
	}

	// If an individual is clicked with the right mouse button, open a popup
	// menu that contains the option to paint the route.
	@Override
	public void onPopup(final Individual individual, Component table, Point p, JPopupMenu menu) {
		JMenuItem paint = new JMenuItem("show route");
		menu.add(paint);

		paint.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				paintRoute(individual);
			}
		});

	}

	// Paint the route: Construct a JInternalFrame, add the MyPanel and add the
	// frame to the desktop of the main GUIFrame.
	protected void paintRoute(Individual individual) {
		Widget widget = new SalesmanWidget(individual);
		viewport.addWidget(widget);
	}

}
