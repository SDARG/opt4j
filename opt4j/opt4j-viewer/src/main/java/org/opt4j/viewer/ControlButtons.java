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
package org.opt4j.viewer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.opt4j.config.Icons;
import org.opt4j.core.optimizer.Control;
import org.opt4j.core.optimizer.Control.State;
import org.opt4j.core.optimizer.ControlListener;
import org.opt4j.core.optimizer.Optimizer;
import org.opt4j.core.optimizer.OptimizerStateListener;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * <p>
 * The {@link ControlButtons} contains {@link JButton}s for the controls:
 * </p>
 * <ul>
 * <li>start</li>
 * <li>pause</li>
 * <li>stop</li>
 * <li>terminate</li>
 * </ul>
 * 
 * @see Control
 * @author lukasiewycz
 * 
 */
@Singleton
public class ControlButtons implements OptimizerStateListener, ControlListener {

	protected final Control control;

	protected JButton start;
	protected JButton pause;
	protected JButton stop;
	protected JButton terminate;

	/**
	 * Constructs a {@link ControlButtons}.
	 * 
	 * @param control
	 *            the control
	 */
	@Inject
	public ControlButtons(Control control) {
		this.control = control;

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		construct();
	}

	/**
	 * Constructs the buttons.
	 */
	protected final void construct() {
		start = new JButton("", Icons.getIcon(Icons.CONTROL_START));
		pause = new JButton("", Icons.getIcon(Icons.CONTROL_PAUSE));
		stop = new JButton("", Icons.getIcon(Icons.CONTROL_STOP));
		terminate = new JButton("", Icons.getIcon(Icons.CONTROL_TERM));

		start.setToolTipText("Start");
		pause.setToolTipText("Pause");
		stop.setToolTipText("Stop");
		terminate.setToolTipText("Terminate");

		start.setFocusable(false);
		pause.setFocusable(false);
		stop.setFocusable(false);
		terminate.setFocusable(false);

		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				control.doStart();
				update(null);
			}
		});

		pause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				control.doPause();
				update(null);
			}
		});

		stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				control.doStop();
				update(null);
			}
		});

		terminate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				control.doTerminate();
				update(null);
			}
		});
	}

	/**
	 * Updates the view.
	 */
	protected void update(Optimizer optimizer) {

		final State state;

		if (optimizer != null && !optimizer.isRunning()) {
			state = State.TERMINATED;
		} else {
			state = control.getState();
		}

		final boolean bStart = (state == State.PAUSED);
		final boolean bPause = (state == State.RUNNING);
		final boolean bStop = (state == State.RUNNING || state == State.PAUSED);
		final boolean bTerminate = (state != State.TERMINATED);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				start.setEnabled(bStart);
				pause.setEnabled(bPause);
				stop.setEnabled(bStop);
				terminate.setEnabled(bTerminate);
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.core.optimizer.OptimizationStartListener#optimizationStarted
	 * (org.opt4j.core.optimizer.Optimizer)
	 */
	@Override
	public void optimizationStarted(Optimizer optimizer) {
		update(optimizer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.core.optimizer.OptimizationStopListener#optimizationStopped
	 * (org.opt4j.core.optimizer.Optimizer)
	 */
	@Override
	public void optimizationStopped(Optimizer optimizer) {
		update(optimizer);
	}

	/**
	 * Returns the start button.
	 * 
	 * @return the start button
	 */
	public JButton getStart() {
		return start;
	}

	/**
	 * Returns the pause button.
	 * 
	 * @return the pause button
	 */
	public JButton getPause() {
		return pause;
	}

	/**
	 * Returns the stop button.
	 * 
	 * @return the stop button
	 */
	public JButton getStop() {
		return stop;
	}

	/**
	 * Returns the terminate button.
	 * 
	 * @return the terminate button
	 */
	public JButton getTerminate() {
		return terminate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.core.optimizer.ControlListener#stateChanged(org.opt4j.core.
	 * optimizer.Control.State)
	 */
	@Override
	public void stateChanged(State state) {
		update(null);
	}

}
