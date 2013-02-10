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

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JToolTip;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

/**
 * The {@link QTable} extends the {@link JTable} by automatically adding
 * tooltips.
 * 
 * @author lukasiewycz
 * 
 */
class QTable extends JTable {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a {@link QTable}.
	 * 
	 * @param dm
	 *            the model
	 */
	public QTable(TableModel dm) {
		super(dm);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.JTable#prepareRenderer(javax.swing.table.TableCellRenderer,
	 * int, int)
	 */
	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
		final Component c = super.prepareRenderer(renderer, row, column);
		if (c instanceof JComponent) {
			final JComponent jc = (JComponent) c;

			Object value = getValueAt(row, column);

			if (value != null) {
				final String text = value.toString();
				final int length = jc.getFontMetrics(jc.getFont()).stringWidth(text);

				if (getColumnModel().getColumn(column).getWidth() < length) {
					jc.setToolTipText(text);
				} else {
					jc.setToolTipText(null);
				}
			}

		}
		return c;
	}

	/**
	 * The {@link WrapToolTip} auto-wraps long tooltips.
	 * 
	 * @author lukasiewycz
	 * 
	 */
	class WrapToolTip extends JToolTip {

		private static final long serialVersionUID = 1L;

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.JToolTip#setTipText(java.lang.String)
		 */
		@Override
		public void setTipText(String tipText) {

			if (tipText != null && tipText.length() > 0) {
				String s = "<html>";
				for (int i = 0; i < tipText.length(); i += 150) {
					s += tipText.substring(i, Math.min(i + 150, tipText.length()));
					s += "<br>";
				}
				s += "</html>";
				super.setTipText(s);
			} else {
				super.setTipText(null);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#createToolTip()
	 */
	@Override
	public JToolTip createToolTip() {
		return new WrapToolTip();
	}

}
