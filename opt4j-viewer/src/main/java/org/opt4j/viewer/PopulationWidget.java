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
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
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
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.opt4j.core.Individual;
import org.opt4j.core.Individual.State;
import org.opt4j.core.IndividualFactory;
import org.opt4j.core.IndividualSet;
import org.opt4j.core.IndividualSetListener;
import org.opt4j.core.IndividualStateListener;
import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;
import org.opt4j.core.config.Icons;
import org.opt4j.core.config.visualization.DelayTask;
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

	protected final List<IndividualMouseListener> individualMouseListeners = new CopyOnWriteArrayList<IndividualMouseListener>();

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
		table.addMouseListener(new TableMouseListener());

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

	/**
	 * Set the {@link IndividualMouseListener}s to inform.
	 * 
	 * @param mouseListeners
	 *            the mouse listeners
	 */
	@Inject(optional = true)
	public void addMouseListeners(Set<IndividualMouseListener> mouseListeners) {
		this.individualMouseListeners.addAll(mouseListeners);
		sortIndividiualMouseListeners();
	}

	protected void sortIndividiualMouseListeners() {
		List<IndividualMouseListener> list = new ArrayList<IndividualMouseListener>();
		list.addAll(individualMouseListeners);
		Collections.sort(list, new ToolBarOrderComparator<IndividualMouseListener>());
		individualMouseListeners.clear();
		individualMouseListeners.addAll(list);
	}

	/**
	 * The {@link TableMouseListener} that listens to right and double click of
	 * the table item.
	 * 
	 * @author lukasiewycz, reimann
	 * 
	 */
	class TableMouseListener extends MouseAdapter {
		private void reservTableShow(MouseEvent e, boolean released) {
			if (table.isEnabled()) {
				Point p = new Point(e.getX(), e.getY());
				int row = table.rowAtPoint(p);

				if (row >= 0 && row < table.getRowCount()) {
					Individual individual = swtIndividuals.get(row);

					if (individual.isEvaluated()) {
						if (e.isPopupTrigger()) {
							table.getSelectionModel().setSelectionInterval(row, row);
							JPopupMenu menu = new JPopupMenu();
							for (IndividualMouseListener listener : individualMouseListeners) {
								listener.onPopup(individual, table, p, menu);
							}
							if (menu.getComponentCount() > 0) {
								menu.show(table, p.x, p.y);
							}
						} else if (e.getClickCount() == 2 && released) {
							for (IndividualMouseListener listener : individualMouseListeners) {
								listener.onDoubleClick(individual, table, p);
							}
						}
					}
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			reservTableShow(e, true);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			reservTableShow(e, false);
		}
	}
}
