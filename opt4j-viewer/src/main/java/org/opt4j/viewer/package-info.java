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
 

/**
 * <p>
 * Provides the classes for the optimization visualization, i.e., the
 * {@link org.opt4j.viewer.Viewer}.
 * </p>
 * <p>
 * The {@link org.opt4j.viewer.Viewer} is a {@link javax.swing.JFrame}
 * consisting of the following elements:
 * <ul>
 * <li>{@link org.opt4j.viewer.ToolBar} - a panel for arbitrary buttons</li>
 * <li>{@link org.opt4j.viewer.Viewport} - a desktop for the widgets</li>
 * </ul>
 * </p>
 * <p>
 * The {@link org.opt4j.viewer.Viewport} is a desktop for
 * {@link org.opt4j.viewer.Widget} elements. A widget is similar to a
 * {@link javax.swing.JInternalFrame}, several properties are defined in the
 * {@link org.opt4j.viewer.WidgetParameters}. Widgets are added directly to the
 * viewport.
 * </p>
 * <p>
 * Additional buttons are added to the {@link org.opt4j.viewer.ToolBar} using
 * the {@link org.opt4j.viewer.ToolBarService}. Each
 * {@link org.opt4j.viewer.ToolBarService} is added in the
 * {@link org.opt4j.viewer.VisualizationModule}.
 * </p>
 * <p>
 * One predefined specific widget is the {@link org.opt4j.viewer.ArchiveWidget}.
 * This widget shows all {@link org.opt4j.core.Individual}s which are currently
 * in the {@link org.opt4j.core.optimizer.Archive}. By default, these are the
 * best {@link org.opt4j.core.Individual}s found so far during the optimization
 * process. In practice a user might want to visualize one of these
 * {@link org.opt4j.core.Individual}s or their phenotype, respectively. The
 * {@link org.opt4j.viewer.IndividualMouseListener} is used to listen to the
 * double-clicking of an {@link org.opt4j.core.Individual} in the
 * {@link org.opt4j.core.optimizer.Archive} as well as a popup handling which is
 * usually invoked by a right-click. An
 * {@link org.opt4j.viewer.IndividualMouseListener} is added in the
 * {@link org.opt4j.viewer.VisualizationModule}.
 * </p>
 * 
 */
package org.opt4j.viewer;