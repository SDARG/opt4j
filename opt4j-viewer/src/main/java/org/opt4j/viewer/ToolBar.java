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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JToolBar;
import javax.swing.border.Border;

import com.google.inject.Inject;

/**
 * The {@link ToolBar} is a collection of the {@link ToolBarService}s.
 * 
 * @see ToolBarService
 * @author lukasiewycz
 * 
 */
public class ToolBar {

	protected final JToolBar toolBar = new JToolBar();
	protected final Set<ToolBarService> toolBarServices;

	/**
	 * Constructs a {@link ToolBar}.
	 * 
	 * @param toolBarServices
	 *            the set of tool bar services
	 */
	@Inject
	public ToolBar(Set<ToolBarService> toolBarServices) {
		this.toolBarServices = toolBarServices;
	}

	/**
	 * Initialization. This method has to be called once after construction.
	 */
	public void init() {
		toolBar.setFloatable(false);
		Border border = BorderFactory.createMatteBorder(0, 0, 1, 0, toolBar.getBackground().darker());
		toolBar.setBorder(border);

		List<ToolBarService> list = new ArrayList<ToolBarService>();
		list.addAll(toolBarServices);
		Collections.sort(list, new ToolBarOrderComparator<ToolBarService>());

		for (ToolBarService toolBarService : list) {
			JToolBar component = toolBarService.getToolBar();
			component.setFloatable(false);
			toolBar.add(component);
		}
	}

	/**
	 * Returns the {@link JToolBar} component.
	 * 
	 * @return the component
	 */
	public JToolBar get() {
		return toolBar;
	}

}
