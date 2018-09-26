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

package org.opt4j.core.config.visualization;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import org.opt4j.core.config.ExecutionEnvironment;
import org.opt4j.core.config.Task;
import org.opt4j.core.config.TaskListener;

import com.google.inject.Inject;

/**
 * The {@link DefaultTasksPanel} extends the {@link TasksPanel} and shows all
 * {@link Task}s in a table.
 * 
 * @author lukasiewycz
 * 
 */
@SuppressWarnings("serial")
public class DefaultTasksPanel extends TasksPanel implements TaskListener {

	protected final ExecutionEnvironment executionEnvironment;

	protected final Format format;

	protected JTable table;

	protected JScrollPane scroll;

	protected final DelayTask delay = new DelayTask(40);

	/**
	 * The {@link Table}.
	 */
	protected class Table extends JTable {

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.JTable#prepareRenderer(javax.swing.table.
		 * TableCellRenderer , int, int)
		 */
		@Override
		public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
			Component c = super.prepareRenderer(renderer, row, column);
			Task task = executionEnvironment.getTasks().get(row);
			if (task.getException() == null) {
				c.setForeground(Color.BLACK);
			} else {
				c.setForeground(Color.RED);
			}

			if (c instanceof JComponent) {
				JComponent jc = (JComponent) c;
				jc.setBorder(BorderFactory.createEmptyBorder(0, 3, 0, 0));

				char[] chars = this.getValueAt(row, column).toString().toCharArray();
				int length = jc.getFontMetrics(jc.getFont()).charsWidth(chars, 0, chars.length);
				if (this.getColumnModel().getColumn(column).getWidth() < length) {
					jc.setToolTipText(format.formatTooltip(this.getValueAt(row, column).toString()));
				} else {
					jc.setToolTipText(null);
				}
			}

			return c;
		}

	}

	/**
	 * The {@link Model}.
	 */
	protected class Model extends AbstractTableModel {

		protected String[] columnNames = { "Task", "State" };

		@Override
		public int getColumnCount() {
			return 2;
		}

		@Override
		public int getRowCount() {
			return executionEnvironment.getTasks().size();
		}

		@Override
		public Object getValueAt(int row, int column) {
			Task task = executionEnvironment.getTasks().get(row);
			if (column == 0) {
				return row + " " + task.toString();
			} else if (column == 1) {
				if (task.getException() != null) {
					return "EXCEPTION: \n" + task.getException();
				}
				return task.getState();
			}
			return null;
		}

		@Override
		public String getColumnName(int i) {
			return columnNames[i];
		}
	}

	/**
	 * Constructs a {@link DefaultTasksPanel}.
	 * 
	 * @param executionEnvironment
	 *            the execution environment
	 * @param format
	 *            the format informations
	 */
	@Inject
	public DefaultTasksPanel(ExecutionEnvironment executionEnvironment, Format format) {
		super();
		this.executionEnvironment = executionEnvironment;
		this.format = format;
	}

	/**
	 * Registers a listener at the execution environment.
	 */
	@Inject
	public void init() {
		executionEnvironment.addListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.conf.gui.Startupable#startup()
	 */
	@Override
	public void startup() {
		this.setLayout(new BorderLayout());

		AbstractTableModel model = getModel();
		table = getTable();
		table.setModel(model);
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(500);

		scroll = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		this.add(scroll, BorderLayout.CENTER);
	}

	/**
	 * Returns the instance of the table.
	 * 
	 * @return the table
	 */
	protected JTable getTable() {
		return new Table();
	}

	/**
	 * Returns the instance of the model.
	 * 
	 * @return the model
	 */
	protected AbstractTableModel getModel() {
		return new Model();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.config.TaskListener#added(org.opt4j.config.Task)
	 */
	@Override
	public void added(Task task) {
		table.revalidate();
		table.repaint();
		SwingUtilities.invokeLater(() -> scroll.getVerticalScrollBar().setValue(table.getRowCount() * 120));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.config.TaskStateListener#stateChanged(org.opt4j.config.Task)
	 */
	@Override
	public void stateChanged(Task task) {
		delay.execute(() -> {
			table.revalidate();
			table.repaint();
		});
	}
}
