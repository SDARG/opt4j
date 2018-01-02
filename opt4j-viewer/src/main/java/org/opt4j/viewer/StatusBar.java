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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import org.opt4j.core.config.visualization.DelayTask;
import org.opt4j.core.optimizer.Optimizer;
import org.opt4j.core.optimizer.OptimizerIterationListener;
import org.opt4j.core.optimizer.OptimizerStateListener;
import org.opt4j.core.start.Progress;

import com.google.inject.Inject;

/**
 * The {@link StatusBar} contains informations about the optimization progress
 * and time per iteration.
 * 
 * @author lukasiewycz
 * 
 */
public class StatusBar implements OptimizerIterationListener, OptimizerStateListener {

	protected final Progress progress;

	protected final DelayTask task = new DelayTask(40);

	protected final JLabel label = new JLabel();

	protected final JLabel timeLabel = new JLabel();

	protected final JProgressBar bar = new JProgressBar();

	protected final JPanel panel = new JPanel();

	protected long time;

	/**
	 * Constructs a {@link StatusBar}.
	 * 
	 * @param progress
	 *            the progress
	 */
	@Inject
	public StatusBar(Progress progress) {
		this.progress = progress;
	}

	/**
	 * Initialization. This method has to called once after construction.
	 */
	public void init() {
		panel.setLayout(new BorderLayout());
		Border border = BorderFactory.createMatteBorder(1, 0, 0, 0, label.getBackground().darker());
		panel.setBorder(border);

		JPanel containerText = new JPanel(new FlowLayout(0, 5, 0));
		timeLabel.setText("[time per iteration in ms]");
		timeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		label.setPreferredSize(new Dimension(50, 18));
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setPreferredSize(new Dimension(50, 18));

		containerText.add(label);
		containerText.add(timeLabel);
		panel.add(containerText, BorderLayout.WEST);

		JPanel container = new JPanel(new BorderLayout());
		container.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 4));
		bar.setMinimum(0);
		bar.setMaximum(progress.getMaxIterations());

		bar.setPreferredSize(new Dimension(200, 16));
		bar.setStringPainted(true);

		container.add(bar);
		panel.add(container, BorderLayout.EAST);

		time = System.nanoTime();

		update("", "init");
	}

	protected void update(final String message, final String progressMessage) {
		task.execute(new Runnable() {
			@Override
			public void run() {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						String pm = progressMessage;
						if (pm == null) {
							pm = "" + progress.getCurrentIteration() + "/" + progress.getMaxIterations();
						}
						label.setText(" " + message);
						bar.setValue(progress.getCurrentIteration());
						bar.setString(pm);
					}
				});
			}
		});
	}

	/**
	 * Returns the component.
	 * 
	 * @return the component
	 */
	public JComponent get() {
		return panel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.core.optimizer.OptimizerIterationListener#iterationComplete
	 * (org.opt4j.core.optimizer.Optimizer, int)
	 */
	@Override
	public void iterationComplete(int iteration) {
		final long diff = Math.round(((System.nanoTime() - time)) / 1000000.0);
		time = System.nanoTime();

		DecimalFormat format = new DecimalFormat();

		update(format.format(diff), null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.core.optimizer.OptimizerStateListener#optimizationStarted(org
	 * .opt4j.core.optimizer.Optimizer)
	 */
	@Override
	public void optimizationStarted(Optimizer optimizer) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.core.optimizer.OptimizerStateListener#optimizationStopped(org
	 * .opt4j.core.optimizer.Optimizer)
	 */
	@Override
	public void optimizationStopped(Optimizer optimizer) {
		update("", "done");
		timeLabel.setText("");
	}
}
