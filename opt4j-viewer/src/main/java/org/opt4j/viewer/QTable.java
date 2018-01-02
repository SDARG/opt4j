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
