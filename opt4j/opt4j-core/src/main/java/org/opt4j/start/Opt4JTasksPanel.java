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
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.WeakHashMap;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import org.opt4j.config.ExecutionEnvironment;
import org.opt4j.config.Icons;
import org.opt4j.config.PropertyModule;
import org.opt4j.config.Task;
import org.opt4j.config.Task.State;
import org.opt4j.config.visualization.DefaultTasksPanel;
import org.opt4j.config.visualization.Format;
import org.opt4j.config.visualization.SelectedModules;
import org.opt4j.core.optimizer.Control;
import org.opt4j.core.optimizer.Optimizer;
import org.opt4j.viewer.Progress;

import com.google.inject.Inject;
import com.google.inject.Module;

/**
 * The {@link Opt4JTasksPanel} extends the {@link DefaultTasksPanel} by
 * additional functionality: Additional control and extended state.
 * 
 * @author lukasiewycz
 * 
 */
@SuppressWarnings("serial")
public class Opt4JTasksPanel extends DefaultTasksPanel {

	protected final Map<Task, Progress> progessMap = new WeakHashMap<Task, Progress>();

	protected final SelectedModules selectedModules;

	static class ProgressRenderer extends DefaultTableCellRenderer {
		private final JProgressBar b = new JProgressBar(0, 100);

		public ProgressRenderer() {
			super();
			setOpaque(true);
			b.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
			b.setStringPainted(true);
			b.setBackground(Color.WHITE);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
				boolean hasFocus, int row, int column) {
			if (value instanceof Progress) {
				Progress progress = (Progress) value;
				Double v = progress.get();
				if (v == null) {
					v = 0.0;
				}
				b.setValue((int) (v * 100));
				b.setString("" + progress.getCurrentIteration() + "/" + progress.getMaxIterations());
				return b;
			}
			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}
	}

	class Table extends JTable {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * javax.swing.JTable#prepareRenderer(javax.swing.table.TableCellRenderer
		 * , int, int)
		 */
		@Override
		public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
			Component c = super.prepareRenderer(renderer, row, column);

			if (!(renderer instanceof ProgressRenderer)) {
				Task task = executionEnvironment.getTasks().get(row);
				if (task.getException() == null) {
					c.setForeground(Color.BLACK);
				} else {
					c.setForeground(Color.RED);
				}

				if (c instanceof JComponent) {
					JComponent jc = (JComponent) c;

					jc.setBorder(BorderFactory.createEmptyBorder(0, 3, 0, 0));

					Object value = this.getValueAt(row, column);
					if (value != null) {
						char[] chars = value.toString().toCharArray();
						int length = jc.getFontMetrics(jc.getFont()).charsWidth(chars, 0, chars.length);
						if (this.getColumnModel().getColumn(column).getWidth() < length) {
							jc.setToolTipText(format.formatTooltip(value.toString()));
						} else {
							jc.setToolTipText(null);
						}
					}
				}
			}

			return c;
		}

		@Override
		public TableCellRenderer getCellRenderer(final int row, final int column) {
			if (column == 2) {
				return new ProgressRenderer();
			}
			return super.getCellRenderer(row, column);
		}

	}

	/**
	 * The {@link Model} of the table.
	 */
	protected class Model extends AbstractTableModel {
		protected final String[] columnNames = { "Task", "State", "Progress" };

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public int getRowCount() {
			return executionEnvironment.getTasks().size();
		}

		@Override
		public Object getValueAt(int row, int column) {
			Opt4JTask task = (Opt4JTask) executionEnvironment.getTasks().get(row);
			if (column == 0) {
				return row + " " + task.toString();
			} else if (column == 1) {
				if (task.getException() != null) {
					return "EXCEPTION: \n" + task.getException();
				}
				try {
					Control control = task.getInstance(Control.class);
					return "" + control.getState();
				} catch (RuntimeException e) {
					return "";
				}
			} else if (column == 2) {
				switch (task.getState()) {
				case EXECUTING:
					Progress progress = Opt4JTasksPanel.this.progessMap.get(task);
					if (progress != null) {
						return progress;
					}
					Optimizer optimizer = task.getInstance(Optimizer.class);
					if (optimizer != null) {
						progress = task.getInstance(Progress.class);
						optimizer.addOptimizerIterationListener(progress);
						progessMap.put(task, progress);
						return progress;
					}
					return " " + task.getState();
				default:
					return "  " + task.getState();
				}
			}
			return null;
		}

		@Override
		public String getColumnName(int i) {
			return columnNames[i];
		}
	}

	/**
	 * Constructs a {@link Opt4JTasksPanel}.
	 * 
	 * @param executionEnvironment
	 *            the execution environment
	 * @param format
	 *            the format informations
	 * @param selectedModules
	 *            the selected modules
	 */
	@Inject
	public Opt4JTasksPanel(ExecutionEnvironment executionEnvironment, Format format, SelectedModules selectedModules) {
		super(executionEnvironment, format);

		this.selectedModules = selectedModules;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.conf.gui.Startupable#startup()
	 */
	@Override
	public void startup() {
		super.startup();

		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(300);
		table.getColumnModel().getColumn(2).setPreferredWidth(250);

		table.addMouseListener(new PopupListener());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.config.visualization.DefaultTasksPanel#getModel()
	 */
	@Override
	protected AbstractTableModel getModel() {
		return new Model();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.config.visualization.DefaultTasksPanel#getTable()
	 */
	@Override
	protected JTable getTable() {
		return new Table();
	}

	/**
	 * The {@link PopupListener} for the table elements.
	 */
	protected class PopupListener extends MouseAdapter {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
		 */
		@Override
		public void mousePressed(MouseEvent e) {
			if (e.isPopupTrigger()) {
				display(e);
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseReleased(MouseEvent e) {
			if (e.isPopupTrigger()) {
				display(e);
			}
		}

		/**
		 * Displays a popup menu with controls for running tasks.
		 * 
		 * @param event
		 *            the triggered event
		 */
		void display(MouseEvent event) {

			if (event.isPopupTrigger()) {
				try {
					Point p = event.getPoint();
					int row = table.rowAtPoint(p);

					Opt4JTask task = (Opt4JTask) executionEnvironment.getTasks().get(row);

					JPopupMenu menu = new JPopupMenu();
					addConfigLoadMenuItems(menu, task);
					if (task.getState() == State.EXECUTING) {
						menu.addSeparator();
						addControlMenuItems(menu, task);
					}
					menu.show(event.getComponent(), event.getX(), event.getY());
				} catch (IndexOutOfBoundsException e) {
				}
			}
		}

		/**
		 * Adds an item to the given menu which allows to restore the
		 * configuration of a task.
		 * 
		 * @param menu
		 *            the menu
		 * @param task
		 *            the task which configuration is restored
		 */
		private void addConfigLoadMenuItems(JPopupMenu menu, final Opt4JTask task) {
			JMenuItem loadConfig = new JMenuItem("Restore Configuration", Icons.getIcon(Icons.FOLDER_ADD));

			loadConfig.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					selectedModules.clear();
					for (Module module : task.getModules()) {
						PropertyModule pModule;
						if (module instanceof PropertyModule) {
							pModule = (PropertyModule) module;
						} else {
							pModule = new PropertyModule(module);
						}

						selectedModules.add(pModule);
					}
				}
			});

			menu.add(loadConfig);
		}

		/**
		 * Adds the control menu items to start, pause, stop and terminate the
		 * given task.
		 * 
		 * @param menu
		 * @param task
		 */
		private void addControlMenuItems(JPopupMenu menu, Opt4JTask task) {
			final Control control;
			try {
				control = task.getInstance(Control.class);
			} catch (RuntimeException e) {
				return;
			}
			assert control != null : "Task should be in state EXECUTING but is in state " + task.getState();

			JMenuItem start = new JMenuItem("Start", Icons.getIcon(Icons.CONTROL_START));
			JMenuItem pause = new JMenuItem("Pause", Icons.getIcon(Icons.CONTROL_PAUSE));
			JMenuItem stop = new JMenuItem("STOP", Icons.getIcon(Icons.CONTROL_STOP));
			JMenuItem terminate = new JMenuItem("Terminate", Icons.getIcon(Icons.CONTROL_TERM));
			terminate.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					control.doTerminate();
				}
			});
			start.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					control.doStart();
				}
			});
			pause.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					control.doPause();
				}
			});
			stop.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					control.doStop();
				}
			});

			if (control != null) {
				switch (control.getState()) {
				case RUNNING:
					start.setEnabled(false);
					break;
				case PAUSED:
					pause.setEnabled(false);
					break;
				case TERMINATED:
					terminate.setEnabled(false);
					start.setEnabled(false);
					pause.setEnabled(false);
					stop.setEnabled(false);
					break;
				case STOPPED:
					start.setEnabled(false);
					pause.setEnabled(false);
					stop.setEnabled(false);
					break;
				}
			} else {
				terminate.setEnabled(false);
				start.setEnabled(false);
				pause.setEnabled(false);
				stop.setEnabled(false);
			}

			menu.add(start);
			menu.add(pause);
			menu.add(stop);
			menu.add(terminate);
		}
	}
}
