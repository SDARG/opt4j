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

package org.opt4j.viewer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import org.opt4j.core.Individual;
import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;
import org.opt4j.core.Value;
import org.opt4j.core.config.Icons;
import org.opt4j.core.config.visualization.DelayTask;
import org.opt4j.core.optimizer.Archive;
import org.opt4j.core.optimizer.Optimizer;
import org.opt4j.core.optimizer.OptimizerIterationListener;
import org.opt4j.core.optimizer.Population;
import org.opt4j.viewer.ConvergencePlotWidget.ObjectiveDropDown;
import org.opt4j.viewer.ObjectivesMonitor.ObjectivesListener;

import ptolemy.plot.Plot;

import com.google.inject.Inject;

/**
 * The {@link ParetoPlotWidget} is a widget that displays the {@link Population}
 * and {@link Archive} in two dimensional plot.
 * 
 * @author lukasiewycz
 * 
 */
@WidgetParameters(title = "Pareto Plot", icon = Icons.PARETO)
public class ParetoPlotWidget implements OptimizerIterationListener, Widget, ObjectivesListener {

	protected final SynchronizedIndividualSet population;

	protected final SynchronizedIndividualSet archive;

	protected final DelayTask task = new DelayTask(40);

	protected final Plot plot;

	protected final Selection selection;

	protected final JPanel panel = new JPanel();

	/**
	 * The selection box for the current two objectives.
	 * 
	 * @author lukasiewycz
	 * 
	 */
	protected class Selection extends JToolBar implements ActionListener {

		private static final long serialVersionUID = 1L;

		protected ObjectiveDropDown firstComboBox;

		protected ObjectiveDropDown secondComboBox;

		public Selection() {
			firstComboBox = new ObjectiveDropDown();
			secondComboBox = new ObjectiveDropDown();

			firstComboBox.addActionListener(this);
			secondComboBox.addActionListener(this);

			add(new JLabel("x-Axis: "));
			add(firstComboBox);
			addSeparator();
			add(new JLabel("y-Axis: "));
			add(secondComboBox);
			setFloatable(false);

			Border border = BorderFactory.createMatteBorder(0, 0, 1, 0, getBackground().darker());
			setBorder(border);

		}

		public void init(Collection<Objective> objectives) {
			for (Objective objective : objectives) {
				firstComboBox.addItem(objective);
				secondComboBox.addItem(objective);
			}

			initSelection(new ArrayList<Objective>(objectives));
		}

		private void initSelection(List<Objective> objectives) {
			if (objectives.size() <= 0) {
				return;
			}

			final Objective objective1 = objectives.get(0);
			final Objective objective2;
			if (objectives.size() > 1) {
				objective2 = objectives.get(1);
			} else {
				objective2 = objectives.get(0);
			}
			firstComboBox.setSelected(objective1);
			secondComboBox.setSelected(objective2);
		}

		public Objective getFirst() {
			return firstComboBox.getSelected();
		}

		public Objective getSecond() {
			return secondComboBox.getSelected();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			ParetoPlotWidget.this.doPaint();
		}
	}

	/**
	 * Constructs a {@link ParetoPlotWidget}.
	 * 
	 * @param population
	 *            the population
	 * @param archive
	 *            the archive
	 * @param autoZoomButton
	 *            the button to reset zooming
	 */
	@Inject
	public ParetoPlotWidget(Population population, Archive archive, AutoZoomButton autoZoomButton) {
		this.population = new SynchronizedIndividualSet(population);
		this.archive = new SynchronizedIndividualSet(archive);

		selection = new Selection();
		selection.addSeparator();
		selection.add(autoZoomButton);

		plot = new Plot();
		autoZoomButton.setPlotBox(plot);

		plot.addLegend(0, "Archive");
		plot.addLegend(1, "Population");
		plot.setMarksStyle("dots");

		Color[] colors = new Color[3];
		colors[0] = Color.RED;
		colors[1] = Color.LIGHT_GRAY;
		colors[2] = Color.BLUE;
		plot.setColors(colors);

		panel.setLayout(new BorderLayout());
		panel.add(selection, BorderLayout.NORTH);
		panel.add(plot, BorderLayout.CENTER);
	}

	/**
	 * Register at the {@link Optimizer}.
	 * 
	 * @param optimizer
	 *            the optimizer
	 */
	@Inject(optional = true)
	public void registerAtOptimizer(Optimizer optimizer) {
		optimizer.addOptimizerIterationListener(this);
	}

	/**
	 * Register at the {@link ObjectivesMonitor}.
	 * 
	 * @param objectivesMonitor
	 *            the objectives monitor
	 */
	@Inject(optional = true)
	public void registerAtObjectivesMonitor(ObjectivesMonitor objectivesMonitor) {
		objectivesMonitor.addListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.viewer.Widget#init(org.opt4j.viewer.Viewport)
	 */
	@Override
	public void init(Viewport viewport) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.core.optimizer.OptimizerIterationListener#iterationComplete
	 * (org.opt4j.core.optimizer.Optimizer, int)
	 */
	@Override
	public void iterationComplete(int iteration) {
		doPaint();
	}

	/**
	 * Returns the two dimensional representation of the given
	 * {@code IndividualCollection}.
	 * 
	 * @param indivualCollection
	 *            the {IndividualCollection
	 * @return a set of points
	 */
	protected Set<Point2D.Double> getPoints(Collection<Individual> indivualCollection, Objective one, Objective two) {

		Set<Point2D.Double> points = new HashSet<Point2D.Double>();
		for (Individual individual : indivualCollection) {
			if (individual != null && individual.isEvaluated()) {
				Objectives objectives = individual.getObjectives();

				if (one == null || two == null) {
					continue;
				}

				Value<?> onev = objectives.get(one);
				Value<?> twov = objectives.get(two);

				if (onev == null || twov == null || onev.getValue() == Objective.INFEASIBLE
						|| twov.getValue() == Objective.INFEASIBLE) {
					continue;
				}

				double x = objectives.get(one).getDouble();
				double y = objectives.get(two).getDouble();
				Point2D.Double point = new Point2D.Double(x, y);
				points.add(point);
			}
		}

		return points;
	}

	/**
	 * Repaints the diagram.
	 */
	protected void doPaint() {
		task.execute(new Runnable() {
			@Override
			public void run() {
				final Collection<Individual> a = new HashSet<Individual>();
				synchronized (archive) {
					a.addAll(archive);
				}
				final Collection<Individual> p = new HashSet<Individual>();
				synchronized (population) {
					p.addAll(population);
				}

				paint(a, p);
			}
		});
	}

	/**
	 * Repaints the diagram.
	 */
	protected void paint(Collection<Individual> archive, Collection<Individual> population) {
		final Objective one = selection.getFirst();
		final Objective two = selection.getSecond();

		population.removeAll(archive);
		final Set<Point2D.Double> archivePoints = getPoints(archive, one, two);
		final Set<Point2D.Double> populationPoints = getPoints(population, one, two);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				plot.clear(false);
				plot.setXLabel(one != null ? one.getName() : "");
				plot.setYLabel(two != null ? two.getName() : "");

				for (Point2D.Double point : archivePoints) {
					plot.addPoint(0, point.getX(), point.getY(), false);
				}

				for (Point2D.Double point : populationPoints) {
					plot.addPoint(1, point.getX(), point.getY(), false);
				}

				plot.revalidate();
				plot.repaint();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.viewer.Widget#getPanel()
	 */
	@Override
	public JPanel getPanel() {
		return panel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.viewer.ObjectivesMonitor.ObjectivesListener#objectives(java
	 * .util.Collection)
	 */
	@Override
	public void objectives(Collection<Objective> objectives) {
		selection.init(objectives);
	}
}
