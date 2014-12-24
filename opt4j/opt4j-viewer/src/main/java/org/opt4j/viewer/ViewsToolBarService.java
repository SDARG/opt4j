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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToolBar;

import org.opt4j.core.config.Icons;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * The {@link ViewsToolBarService} can open a {@link ArchiveWidget},
 * {@link PopulationWidget}, or {@link ParetoPlotWidget}. This service has a
 * {@link ToolBarOrder} with {@code -50}.
 * 
 * @see ArchiveWidget
 * @see PopulationWidget
 * @see ParetoPlotWidget
 * @author lukasiewycz
 * 
 */
@ToolBarOrder(-50)
public class ViewsToolBarService implements ToolBarService {

	protected final Viewport viewport;
	protected final Provider<ArchiveWidget> archiveWidgetProvider;
	protected final Provider<PopulationWidget> populationWidgetProvider;
	protected final Provider<ParetoPlotWidget> plotWidgetProvider;
	protected final Provider<ConvergencePlotWidget> objectivesPlotWidgetProvider;
	protected ArchiveWidget archiveWidget = null;
	protected PopulationWidget populationWidget = null;

	/**
	 * Constructs a {@link ViewsToolBarService}.
	 * 
	 * @param viewport
	 *            the viewport
	 * @param archiveWidgetProvider
	 *            the archiveWidgetProvider
	 * @param populationWidgetProvider
	 *            the populationWidgetProvider
	 * @param plotWidgetProvider
	 *            the plotWidgetProvider
	 * @param objectivesPlotWidgetProvider
	 *            the objectivesPlotWidgetProvider
	 */
	@Inject
	public ViewsToolBarService(Viewport viewport, Provider<ArchiveWidget> archiveWidgetProvider,
			Provider<PopulationWidget> populationWidgetProvider, Provider<ParetoPlotWidget> plotWidgetProvider,
			Provider<ConvergencePlotWidget> objectivesPlotWidgetProvider) {
		super();
		this.viewport = viewport;
		this.archiveWidgetProvider = archiveWidgetProvider;
		this.populationWidgetProvider = populationWidgetProvider;
		this.plotWidgetProvider = plotWidgetProvider;
		this.objectivesPlotWidgetProvider = objectivesPlotWidgetProvider;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.viewer.ToolBarService#getToolBar()
	 */
	@Override
	public JToolBar getToolBar() {
		JToolBar toolbar = new JToolBar("Views");

		JButton buttonArchive = new JButton(Icons.getIcon(Icons.ARCHIVE));
		buttonArchive.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Widget widget = getArchiveWidget();
				viewport.addWidget(widget);
			}
		});
		buttonArchive.setToolTipText("Archive");
		buttonArchive.setFocusable(false);

		JButton buttonPopulation = new JButton(Icons.getIcon(Icons.POPULATION));
		buttonPopulation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Widget widget = getPopulationWidget();
				viewport.addWidget(widget);
			}
		});
		buttonPopulation.setToolTipText("Population");
		buttonPopulation.setFocusable(false);

		JButton buttonPlot = new JButton(Icons.getIcon(Icons.PARETO));
		buttonPlot.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Widget widget = getPlotWidget();
				viewport.addWidget(widget);
			}
		});
		buttonPlot.setToolTipText("Pareto Plot");
		buttonPlot.setFocusable(false);

		JButton buttonObjectivesPlot = new JButton(Icons.getIcon(Icons.CONVERGENCE));
		buttonObjectivesPlot.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Widget widget = objectivesPlotWidgetProvider.get();
				viewport.addWidget(widget);
			}
		});
		buttonObjectivesPlot.setToolTipText("Objectives Plot");
		buttonObjectivesPlot.setFocusable(false);

		toolbar.add(buttonArchive);
		toolbar.add(buttonPopulation);
		toolbar.add(buttonPlot);
		toolbar.add(buttonObjectivesPlot);

		return toolbar;
	}

	protected ArchiveWidget getArchiveWidget() {
		if (archiveWidget == null) {
			archiveWidget = archiveWidgetProvider.get();
		}
		return archiveWidget;
	}

	protected PopulationWidget getPopulationWidget() {
		if (populationWidget == null) {
			populationWidget = populationWidgetProvider.get();
		}
		return populationWidget;
	}

	protected ParetoPlotWidget getPlotWidget() {
		return plotWidgetProvider.get();
	}

}
