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

import org.opt4j.common.logger.OutputModule;

import com.google.inject.Binder;
import com.google.inject.multibindings.Multibinder;

/**
 * The {@link VisualizationModule}.
 * 
 * @author lukasiewycz
 * 
 */
public abstract class VisualizationModule extends OutputModule {

	/**
	 * Add a {@link ToolBarService}.
	 * 
	 * @param toolBarService
	 *            the tool bar service to be added
	 */
	public void addToolBarService(Class<? extends ToolBarService> toolBarService) {
		Multibinder<ToolBarService> multibinder = Multibinder.newSetBinder(binder(), ToolBarService.class);
		multibinder.addBinding().to(toolBarService);
	}

	/**
	 * Add a {@link ToolBarService}.
	 * 
	 * @param binder
	 *            the binder
	 * @param toolBarService
	 *            the tool bar service to be added
	 */
	public static void addToolBarService(Binder binder, Class<? extends ToolBarService> toolBarService) {
		Multibinder<ToolBarService> multibinder = Multibinder.newSetBinder(binder, ToolBarService.class);
		multibinder.addBinding().to(toolBarService);
	}

	/**
	 * Add a {@link IndividualMouseListener}.
	 * 
	 * @param individualMouseListener
	 *            the individual mouse listener to be added
	 */
	public void addIndividualMouseListener(Class<? extends IndividualMouseListener> individualMouseListener) {
		Multibinder<IndividualMouseListener> multibinder = Multibinder.newSetBinder(binder(),
				IndividualMouseListener.class);
		multibinder.addBinding().to(individualMouseListener);
	}

	/**
	 * Add a {@link IndividualMouseListener}.
	 * 
	 * @param binder
	 *            the binder
	 * @param individualMouseListener
	 *            the individual mouse listener to be added
	 */
	public static final void addIndividualMouseListener(Binder binder,
			Class<? extends IndividualMouseListener> individualMouseListener) {
		Multibinder<IndividualMouseListener> multibinder = Multibinder.newSetBinder(binder,
				IndividualMouseListener.class);
		multibinder.addBinding().to(individualMouseListener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		super.configure();

		multi(ToolBarService.class);
		multi(IndividualMouseListener.class);
	}

}
