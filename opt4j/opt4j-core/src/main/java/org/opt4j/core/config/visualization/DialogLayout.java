/**
 * Opt4J is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Opt4J. If not, see http://www.gnu.org/licenses/.
 */

package org.opt4j.core.config.visualization;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

/**
 * The {@link DialogLayout} is used for label-field pair layout.
 * 
 * @see <a
 *      href="http://www.javafaq.nu/java-allbooks-8.html">http://www.javafaq.nu/java-allbooks-8.html</a>
 * @see <a
 *      href="http://www.javafaq.nu/java-bookpage-15-5.html">http://www.javafaq.nu/java-bookpage-15-5.html</a>
 * 
 * @author lukasiewycz
 * 
 */
class DialogLayout implements LayoutManager {

	public static final int DEFAULT_HGAP = 10;
	public static final int DEFAULT_VGAP = 5;

	protected final int hGap;
	protected final int vGap;

	/**
	 * Constructs a {@link DialogLayout}.
	 */
	public DialogLayout() {
		this(DEFAULT_HGAP, DEFAULT_VGAP);
	}

	/**
	 * Constructs a {@link DialogLayout}.
	 * 
	 * @param hGap
	 *            the horizontal gap
	 * @param vGap
	 *            the vertical gap
	 */
	public DialogLayout(int hGap, int vGap) {
		this.hGap = hGap;
		this.vGap = vGap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.LayoutManager#addLayoutComponent(java.lang.String,
	 * java.awt.Component)
	 */
	@Override
	public void addLayoutComponent(String name, Component comp) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.LayoutManager#removeLayoutComponent(java.awt.Component)
	 */
	@Override
	public void removeLayoutComponent(Component comp) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
	 */
	@Override
	public Dimension preferredLayoutSize(Container parent) {

		int divider = getDivider(parent);
		int w = 0;
		int h = 0;

		for (int k = 1; k < parent.getComponentCount(); k += 2) {

			Component comp = parent.getComponent(k);
			Dimension d = comp.getMinimumSize();

			w = Math.max(w, d.width);
			h += d.height + vGap;

		}

		h += vGap;

		Insets insets = parent.getInsets();

		return new Dimension(divider + w + insets.left + insets.right, h + insets.top + insets.bottom);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
	 */
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return preferredLayoutSize(parent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
	 */
	@Override
	public void layoutContainer(Container parent) {

		int divider = getDivider(parent);
		Insets insets = parent.getInsets();

		int w = parent.getWidth() - insets.left - insets.right - divider;
		int x = insets.left;
		int y = insets.top;

		for (int k = 1; k < parent.getComponentCount(); k += 2) {
			Component comp1 = parent.getComponent(k - 1);
			Component comp2 = parent.getComponent(k);
			Dimension d = comp2.getPreferredSize();
			comp1.setBounds(x, y, divider - hGap, d.height);
			comp2.setBounds(x + divider, y, w, d.height);
			y += d.height + vGap;
		}

	}

	/**
	 * Returns the divider for the labels.
	 * 
	 * @param parent
	 *            the parent container
	 * @return the divider
	 */
	protected int getDivider(Container parent) {

		Insets insets = parent.getInsets();
		int half = (parent.getWidth() - insets.left - insets.right) / 2;

		int divider = 0;

		for (int k = 0; k < parent.getComponentCount(); k += 2) {
			Component comp = parent.getComponent(k);
			Dimension d = comp.getPreferredSize();
			divider = Math.max(divider, d.width);
		}

		divider += hGap;

		return Math.max(divider, half);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getName() + "[hgap=" + hGap + ",vgap=" + vGap + "]";
	}
}
