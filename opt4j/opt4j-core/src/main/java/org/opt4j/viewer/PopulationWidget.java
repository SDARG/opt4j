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

package org.opt4j.viewer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.opt4j.config.Icons;
import org.opt4j.core.Individual;
import org.opt4j.core.Individual.State;
import org.opt4j.core.IndividualFactory;
import org.opt4j.core.IndividualSet;
import org.opt4j.core.IndividualSetListener;
import org.opt4j.core.IndividualStateListener;
import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;
import org.opt4j.core.optimizer.Archive;
import org.opt4j.core.optimizer.Population;
import org.opt4j.viewer.ObjectivesMonitor.ObjectivesListener;

import com.google.inject.Inject;

/**
 * A widget that monitors the {@link Population}.
 * 
 * @author lukasiewycz
 * 
 */
@WidgetParameters(title = "Population Monitor", icon = Icons.POPULATION)
public class PopulationWidget implements IndividualStateListener, IndividualSetListener, Widget, ObjectivesListener {

	protected final static int OFFSET = 3;

	protected final Archive archive;

	protected final SynchronizedIndividualList population;
	protected List<Individual> swtIndividuals = Collections.emptyList();

	protected final List<Objective> objectives = new ArrayList<Objective>();

	protected final JPanel panel = new JPanel();

	protected final JTable table;

	protected int size = 0;

	protected final DelayTask task = new DelayTask(40);

	/**
	 * The {@link Table}.
	 * 
	 * @author lukasiewycz
	 * 
	 */
	protected class Table extends QTable {
		private static final long serialVersionUID = 1L;

		public Table(TableModel tableModel) {
			super(tableModel);
		}

		@Override
		public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
			Component c = super.prepareRenderer(renderer, row, column);

			if (row < swtIndividuals.size()) {
				try {
					Individual individual = swtIndividuals.get(row);

					if (individual != null) {
						if (archive.contains(individual)) {
							String family = c.getFont().getFamily();
							int size = c.getFont().getSize();
							c.setFont(new Font(family, Font.BOLD, size));
						}
						if (individual.getState().isProcessing()) {
							c.setForeground(Color.RED);
						} else {
							c.setForeground(Color.BLACK);
						}

					}
				} catch (IndexOutOfBoundsException e) {
				}
			}
			return c;
		}
	}

	/**
	 * The {@link Model}.
	 * 
	 * @author lukasiewycz
	 * 
	 */
	protected class Model extends AbstractTableModel {
		private static final long serialVersionUID = 1L;

		@Override
		public int getColumnCount() {
			return OFFSET + objectives.size();
		}

		@Override
		public int getRowCount() {
			size = Math.max(size, swtIndividuals.size());
			return size;
		}

		@Override
		public String getColumnName(int col) {
			if (col == 0) {
				return "#";
			} else if (col == 1) {
				return "Individual";
			} else if (col == 2) {
				return "State";
			} else {
				final int index = col - OFFSET;
				final Objective objective = objectives.get(index);

				return objective.getName() + " (" + objective.getSign() + ")";
			}
		}

		@Override
		public Object getValueAt(final int row, final int col) {

			try {
				final Individual individual = swtIndividuals.get(row);
				final State state = individual.getState();

				if (individual != null) {
					if (col == 0) {
						return row + 1;
					} else if (col == 1) {
						return individual.getPhenotype();
					} else if (col == 2) {
						return state;
					} else {
						if (state == Individual.State.EVALUATED) {

							final int index = col - OFFSET;
							final Objectives o = individual.getObjectives();
							final Objective objective = objectives.get(index);

							return o.get(objective).getValue();
						}
					}
				}
			} catch (IndexOutOfBoundsException e) {
			} catch (NullPointerException e) {
			}

			return null;
		}
	}

	/**
	 * Constructs a {@link PopulationWidget}.
	 * 
	 * @param population
	 *            the population
	 * @param archive
	 *            the archive
	 * @param individualFactory
	 *            the individual creator
	 * @param objectivesMonitor
	 *            the objective monitor that determine the objective of the
	 *            optimization problem
	 */
	@Inject
	public PopulationWidget(Population population, Archive archive, IndividualFactory individualFactory,
			ObjectivesMonitor objectivesMonitor) {
		this.archive = archive;

		this.population = new SynchronizedIndividualList(population);
		this.population.addListener(this);

		table = getTable();

		JScrollPane scroll = new JScrollPane(table);

		panel.setLayout(new BorderLayout());
		panel.add(scroll, BorderLayout.CENTER);

		individualFactory.addIndividualStateListener(this);
		objectivesMonitor.addListener(this);

		paint();
	}

	protected final JTable getTable() {
		Model model = new Model();
		JTable table = new Table(model);

		final TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(25);
		columnModel.getColumn(1).setPreferredWidth(140);
		return table;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.viewer.Widget#init(org.opt4j.viewer.Viewport)
	 */
	@Override
	public synchronized void init(Viewport viewport) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.core.IndividualStateListener#inidividualStateChanged(org.opt4j
	 * .core.Individual)
	 */
	@Override
	public void inidividualStateChanged(Individual individual) {
		paint();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.IndividualSetListener#individualAdded(org.opt4j.core.
	 * IndividualSet, org.opt4j.core.Individual)
	 */
	@Override
	public synchronized void individualAdded(IndividualSet collection, Individual individual) {
		paint();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.core.IndividualSetListener#individualRemoved(org.opt4j.core
	 * .IndividualSet, org.opt4j.core.Individual)
	 */
	@Override
	public synchronized void individualRemoved(IndividualSet collection, Individual individual) {
		paint();
	}

	protected void paint() {
		task.execute(new Runnable() {
			@Override
			public void run() {
				final List<Individual> temp = new ArrayList<Individual>();
				temp.addAll(population);

				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						swtIndividuals = temp;
						if (table != null) {
							if (table.getColumnCount() != table.getModel().getColumnCount()) {
								table.createDefaultColumnsFromModel();
							}
							table.revalidate();
							table.repaint();
						}
					}
				});
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
	public void objectives(final Collection<Objective> obj) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				objectives.addAll(obj);
			}
		});
		paint();
	}

}
