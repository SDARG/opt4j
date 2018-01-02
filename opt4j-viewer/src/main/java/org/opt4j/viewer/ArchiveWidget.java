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
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

import org.opt4j.core.Individual;
import org.opt4j.core.IndividualSet;
import org.opt4j.core.IndividualSetListener;
import org.opt4j.core.Objective;
import org.opt4j.core.Objective.Sign;
import org.opt4j.core.Objectives;
import org.opt4j.core.config.Icons;
import org.opt4j.core.config.visualization.DelayTask;
import org.opt4j.core.optimizer.Archive;
import org.opt4j.core.optimizer.Optimizer;
import org.opt4j.core.optimizer.OptimizerIterationListener;
import org.opt4j.viewer.ObjectivesMonitor.ObjectivesListener;

import com.google.inject.Inject;

/**
 * A widget that monitors the archive.
 * 
 * @author lukasiewycz
 * 
 */
@WidgetParameters(title = "Archive Monitor", icon = Icons.ARCHIVE)
public class ArchiveWidget implements OptimizerIterationListener, IndividualSetListener, Widget, ObjectivesListener {

	protected final static int OFFSET = 2;

	protected final SynchronizedIndividualSet archive;
	protected List<Individual> swtIndividuals = Collections.emptyList();

	protected final List<Objective> objectives = new ArrayList<Objective>();

	protected final List<Objective> order = new ArrayList<Objective>();

	protected final JPanel panel = new JPanel();

	protected final JTable table;

	protected final JCheckBox autoUpdate;

	protected final JLabel sizeLabel;

	protected boolean changed = true;

	protected final List<IndividualMouseListener> individualMouseListeners = new CopyOnWriteArrayList<IndividualMouseListener>();

	protected final DelayTask task = new DelayTask(40);

	protected static final String INDEX = "#";

	/**
	 * The {@link TableMouseListener} that listens to right and double click of
	 * the table item.
	 * 
	 * @author lukasiewycz
	 * 
	 */
	class TableMouseListener extends MouseAdapter {
		private void reservTableShow(MouseEvent e, boolean released) {
			if (table.isEnabled()) {
				Point p = new Point(e.getX(), e.getY());
				int row = table.rowAtPoint(p);

				if (row >= 0 && row < table.getRowCount()) {
					Individual individual = swtIndividuals.get(row);

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

		@Override
		public void mouseReleased(MouseEvent e) {
			reservTableShow(e, true);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			reservTableShow(e, false);
		}
	}

	/**
	 * The model of the table.
	 * 
	 * @author lukasiewycz
	 * 
	 */
	protected class Model extends AbstractTableModel {
		private static final long serialVersionUID = 1L;

		@Override
		public String getColumnName(int col) {
			if (col == 0) {
				return INDEX;
			} else if (col == 1) {
				return "Individual";
			}
			final int index = col - OFFSET;
			final Objective objective = objectives.get(index);

			return objective.getName() + " (" + objective.getSign() + ")";
		}

		@Override
		public int getColumnCount() {
			return OFFSET + objectives.size();
		}

		@Override
		public int getRowCount() {
			return swtIndividuals.size();
		}

		@Override
		public Object getValueAt(final int row, final int col) {

			try {
				final Individual individual = swtIndividuals.get(row);

				if (individual != null) {
					if (col == 0) {
						return row + 1;
					} else if (col == 1) {
						return individual.getPhenotype();
					} else if (individual.getState() == Individual.State.EVALUATED) {

						final int index = col - OFFSET;
						final Objectives o = individual.getObjectives();
						final Objective objective = objectives.get(index);

						return o.get(objective).getValue();
					}
				}
			} catch (IndexOutOfBoundsException e) {
			} catch (NullPointerException e) {

			}
			return null;
		}
	}

	/**
	 * Constructs an {@link ArchiveWidget}.
	 * 
	 * @param archive
	 *            the archive
	 */
	@Inject
	public ArchiveWidget(Archive archive) {
		this.archive = new SynchronizedIndividualSet(archive);
		this.archive.addListener(this);

		table = getTable();
		JScrollPane scroll = new JScrollPane(table);

		/*
		 * configure the autoupdate checkbox
		 */
		autoUpdate = new JCheckBox();
		autoUpdate.setSelected(true);
		autoUpdate.setText("Auto Update");
		autoUpdate.setHorizontalTextPosition(SwingConstants.LEADING);
		autoUpdate.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (autoUpdate.isSelected()) {
					paint();
				}
			}
		});
		sizeLabel = new JLabel(Integer.toString(archive.size()));

		/*
		 * configure the panel
		 */
		JPanel north = new JPanel();
		north.setLayout(new BoxLayout(north, BoxLayout.X_AXIS));
		Dimension dim = new Dimension(10, 10);
		north.add(new Box.Filler(dim, dim, dim));
		north.add(new JLabel("Size: "));
		north.add(sizeLabel);
		north.add(Box.createHorizontalGlue());
		north.add(autoUpdate);

		panel.setLayout(new BorderLayout());
		panel.add(north, BorderLayout.NORTH);
		panel.add(scroll, BorderLayout.CENTER);

		table.getColumnModel().addColumnModelListener(new TableColumnModelListener() {

			@Override
			public void columnAdded(TableColumnModelEvent arg0) {
			}

			@Override
			public void columnMarginChanged(ChangeEvent arg0) {
			}

			@Override
			public void columnMoved(TableColumnModelEvent arg0) {
			}

			@Override
			public void columnRemoved(TableColumnModelEvent arg0) {
			}

			@Override
			public void columnSelectionChanged(ListSelectionEvent arg0) {
				if (updateOrder()) {
					paint();
				}
			}
		});
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

	protected final JTable getTable() {
		Model model = new Model();
		QTable table = new QTable(model);
		table.addMouseListener(new TableMouseListener());

		final TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(25);
		columnModel.getColumn(1).setPreferredWidth(140);
		return table;
	}

	protected void sortIndividiualMouseListeners() {
		List<IndividualMouseListener> list = new ArrayList<IndividualMouseListener>();
		list.addAll(individualMouseListeners);
		Collections.sort(list, new ToolBarOrderComparator<IndividualMouseListener>());
		individualMouseListeners.clear();
		individualMouseListeners.addAll(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.viewer.Widget#init(org.opt4j.viewer.Viewport)
	 */
	@Override
	public synchronized void init(Viewport viewport) {

	}

	protected boolean updateOrder() {
		if (table.getColumnCount() != objectives.size() + OFFSET) {
			return false;
		}

		List<Objective> list = new ArrayList<Objective>();
		TableColumnModel model = table.getColumnModel();

		for (int i = 0; i < model.getColumnCount(); i++) {
			int index = model.getColumn(i).getModelIndex();
			if (index >= OFFSET) {
				Objective o = objectives.get(index - OFFSET);
				list.add(o);
			}
		}

		if (!list.equals(order)) {
			order.clear();
			order.addAll(list);
			return true;
		}
		return false;
	}

	/**
	 * Repaints the table.
	 */
	protected void paint() {
		task.execute(new Runnable() {
			@Override
			public void run() {
				final List<Individual> temp = new ArrayList<Individual>();
				temp.addAll(archive);
				Collections.sort(temp, new Comp());

				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						swtIndividuals = temp;
						sizeLabel.setText(Integer.toString(swtIndividuals.size()));

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

	protected class Comp implements Comparator<Individual> {

		@Override
		public int compare(Individual arg0, Individual arg1) {
			if (arg0 == null) {
				if (arg1 == null) {
					return 0;
				}
				return 1;
			} else if (arg1 == null) {
				return -1;
			}

			Objectives obj0 = arg0.getObjectives();
			Objectives obj1 = arg1.getObjectives();

			for (Objective o : order) {
				assert obj0.get(o) != null;
				assert obj1.get(o) != null;

				Double v0 = obj0.get(o).getDouble();
				Double v1 = obj1.get(o).getDouble();

				int c = 0;

				if (v0 == null) {
					if (v1 == null) {
						continue;
					}
					c = 1;
				} else if (v1 == null) {
					c = -1;
				} else {
					c = v0.compareTo(v1);
				}

				if (c != 0) {
					if (o.getSign() == Sign.MIN) {
						return c;
					}
					return -c;
				}
			}
			return 0;
		}

	}

	/**
	 * Adds a {@link IndividualMouseListener}.
	 * 
	 * @param listener
	 *            the individual mouse listener to be added
	 */
	public void addIndividualMouseListener(IndividualMouseListener listener) {
		individualMouseListeners.add(listener);
		sortIndividiualMouseListeners();
	}

	/**
	 * Removes a {@link IndividualMouseListener}.
	 * 
	 * @param listener
	 *            the individual mouse listener to be removed
	 */
	public void removeIndivdiualMouseListener(IndividualMouseListener listener) {
		individualMouseListeners.remove(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.core.optimizer.OptimizerIterationListener#iterationComplete
	 * (org.opt4j.core.optimizer.Optimizer, int)
	 */
	@Override
	public synchronized void iterationComplete(int iteration) {
		if (changed && autoUpdate.isSelected()) {
			changed = false;
			paint();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.IndividualSetListener#individualAdded(org.opt4j.core.
	 * IndividualSet, org.opt4j.core.Individual)
	 */
	@Override
	public synchronized void individualAdded(IndividualSet collection, Individual individual) {
		changed = true;
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
		changed = true;
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

	@Override
	public void objectives(final Collection<Objective> obj) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				order.addAll(obj);
				objectives.addAll(obj);
			}
		});
		paint();
	}

}
