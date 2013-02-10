/**
 * Opt4J is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with Opt4J. If not, see
 * http://www.gnu.org/licenses/.
 */
package org.opt4j.viewer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.Collection;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import org.opt4j.config.Icons;
import org.opt4j.config.visualization.DelayTask;
import org.opt4j.core.Objective;
import org.opt4j.core.optimizer.Optimizer;
import org.opt4j.core.optimizer.OptimizerIterationListener;
import org.opt4j.viewer.ObjectivesMonitor.ObjectivesListener;

import ptolemy.plot.Plot;

import com.google.inject.Inject;

/**
 * The {@link ConvergencePlotWidget} plots the convergence for each {@link Objective}.
 * 
 * @author lukasiewycz
 * 
 */
@WidgetParameters(title = "Convergence Plot", icon = Icons.CONVERGENCE)
public class ConvergencePlotWidget implements Widget, OptimizerIterationListener, ObjectivesListener {

	protected final boolean isObjectivesInitialized = false;
	protected final DelayTask task = new DelayTask(40);

	protected final ConvergencePlotData data;
	protected final Selection selection;

	protected final JPanel panel;
	protected final Plot plot;

	/**
	 * Constructs a {@link ConvergencePlotWidget}.
	 * 
	 * @param optimizer
	 *            the optimizer
	 * @param data
	 *            the data
	 * @param objectivesMonitor
	 *            the objective monitor that determine the objective of the optimization problem
	 */
	@Inject
	public ConvergencePlotWidget(Optimizer optimizer, ConvergencePlotData data, ObjectivesMonitor objectivesMonitor,
			AutoZoomButton autoZoom) {
		super();
		this.data = data;
		selection = new Selection();

		panel = new JPanel();
		panel.setLayout(new BorderLayout());

		plot = new Plot();
		plot.addLegend(0, "Max");
		plot.addLegend(1, "Mean");
		plot.addLegend(2, "Min");
		Color[] colors = new Color[3];
		colors[0] = Color.RED;
		colors[1] = Color.LIGHT_GRAY;
		colors[2] = Color.BLUE;
		plot.setColors(colors);

		panel.add(plot);

		JToolBar menu = new JToolBar();
		menu.setFloatable(false);
		menu.add(selection);
		menu.addSeparator();
		autoZoom.setPlotBox(plot);
		menu.add(autoZoom);

		Border border = BorderFactory.createMatteBorder(0, 0, 1, 0, menu.getBackground().darker());
		menu.setBorder(border);

		panel.add(menu, BorderLayout.NORTH);

		optimizer.addOptimizerIterationListener(this);

		objectivesMonitor.addListener(this);
		doPaint();
	}

	/**
	 * The {@link ObjectiveDropDown} is a combo box for objectives.
	 * 
	 * @author lukasiewycz
	 * 
	 */
	static class ObjectiveDropDown extends JComboBox {

		private static final long serialVersionUID = 1L;

		public ObjectiveDropDown() {
			super();

			setRenderer(new ListCellRenderer() {
				protected DefaultListCellRenderer renderer = new DefaultListCellRenderer();

				@Override
				public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
						boolean cellHasFocus) {
					JLabel cell = (JLabel) renderer.getListCellRendererComponent(list, value, index, isSelected,
							cellHasFocus);
					Objective objective = (Objective) value;
					cell.setText((objective != null ? "objective: " + objective.getName() : ""));
					return cell;
				}
			});

			setMaximumSize(getPreferredSize());
		}

		public void addItem(Objective objective) {
			super.addItem(objective);
			setMaximumSize(getPreferredSize());
		}

		public Objective getSelected() {
			return (Objective) getSelectedItem();
		}

		public void setSelected(Objective objective) {
			setSelectedItem(objective);
		}
	}

	/**
	 * The selection box for the current objective.
	 * 
	 * @author lukasiewycz
	 * 
	 */
	class Selection extends ObjectiveDropDown implements ActionListener {

		private static final long serialVersionUID = 1L;

		public Selection() {
			super();
			addActionListener(this);
		}

		@Override
		public Objective getSelected() {
			return super.getSelected();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			ConvergencePlotWidget.this.doPaint();
		}
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
	 * @see org.opt4j.viewer.Widget#init(org.opt4j.viewer.Viewport)
	 */
	@Override
	public void init(Viewport viewport) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.optimizer.OptimizerIterationListener#iterationComplete (org.opt4j.core.optimizer.Optimizer,
	 * int)
	 */
	@Override
	public void iterationComplete(final int iteration) {
		doPaint();
	}

	/**
	 * Force a repaint of the plot.
	 */
	protected void doPaint() {
		task.execute(new Runnable() {
			@Override
			public void run() {
				paint();
			}
		});
	}

	/**
	 * Repaints the plot. Do not call this method directly, call {@link #doPaint()} instead.
	 */
	protected void paint() {
		final Objective objective = selection.getSelected();

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				plot.clear(false);

				plot.setXLabel("iteration");

				if (objective != null) {
					plot.setYLabel(objective.getName());

					paintList(data.getMaxPoints(objective), 0);
					paintList(data.getMeanPoints(objective), 1);
					paintList(data.getMinPoints(objective), 2);
				} else {
					plot.setYLabel("");
				}

				plot.revalidate();
				plot.repaint();
			}
		});
	}

	private void paintList(List<Double> list, int dataset) {
		final int iteration = data.getIteration();

		for (int i = 0; i < list.size(); i++) {
			Point2D.Double p1 = list.get(i);

			double x = p1.getX();
			double y = p1.getY();

			plot.addPoint(dataset, x, y, i != 0);
		}

		if (!list.isEmpty() && iteration > 0) {
			Point2D.Double p1 = list.get(list.size() - 1);
			double x = iteration;
			double y = p1.getY();

			plot.addPoint(dataset, x, y, true);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.viewer.ObjectivesMonitor.ObjectivesListener#objectives(java .util.Collection)
	 */
	@Override
	public void objectives(Collection<Objective> objectives) {
		for (Objective objective : objectives) {
			selection.addItem(objective);
			selection.setSelectedIndex(0);
			selection.repaint();
		}
	}
}
