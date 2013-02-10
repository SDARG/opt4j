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

/**
 * <p>
 * Provides the classes for the optimization visualization, i.e., the {@link
 * org.opt4j.viewer.Viewer}.
 * </p>
 * <p>
 * The {@link org.opt4j.viewer.Viewer} is a {@link javax.swing.JFrame} consisting of the
 * following elements:
 * <ul>
 * <li>{@link org.opt4j.viewer.ToolBar} - a panel for arbitrary buttons</li>
 * <li>{@link org.opt4j.viewer.Viewport} - a desktop for the widgets</li>
 * </ul>
 * </p>
 * <p>
 * The {@link org.opt4j.viewer.Viewport} is a desktop for {@link org.opt4j.viewer.Widget} elements.
 * A widget is similar to a {@link javax.swing.JInternalFrame}, several properties are
 * defined in the {@link org.opt4j.viewer.WidgetParameters}. Widgets are added
 * directly to the viewport.
 * </p>
 * <p>
 * Additional buttons are added to the {@link org.opt4j.viewer.ToolBar} using the
 * {@link org.opt4j.viewer.ToolBarService}. Each {@link org.opt4j.viewer.ToolBarService} is added in
 * the {@link org.opt4j.viewer.VisualizationModule}.
 * </p>
 * <p>
 * One predefined specific widget is the {@link org.opt4j.viewer.ArchiveWidget}.
 * This widget shows all {@link org.opt4j.core.Individual}s which are currently in the
 * {@link org.opt4j.core.optimizer.Archive}. By default, these are the best {@link
 * org.opt4j.core.Individual}s found so far during the optimization process. In practice a user
 * might want to visualize one of these {@link org.opt4j.core.Individual}s or their {@link
 * org.opt4j.core.Phenotype}, respectively. The {@link org.opt4j.viewer.IndividualMouseListener}
 * is used to listen to the double-clicking of an {@link org.opt4j.core.Individual} in the
 * {@link org.opt4j.core.optimizer.Archive} as well as a popup handling which is usually invoked by a
 * right-click. An {@link org.opt4j.viewer.IndividualMouseListener} is added in the
 * {@link org.opt4j.viewer.VisualizationModule}.
 * </p>
 * 
 */
package org.opt4j.viewer;