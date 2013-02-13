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

package org.opt4j.sat.sat4j;

import static org.sat4j.core.LiteralsUtils.negLit;
import static org.sat4j.core.LiteralsUtils.posLit;

import org.sat4j.minisat.core.Heap;
import org.sat4j.minisat.core.ILits;
import org.sat4j.minisat.orders.UserFixedPhaseSelectionStrategy;
import org.sat4j.minisat.orders.VarOrderHeap;

/**
 * A {@link VariableOrder} implementation for the SAT4J interface. <br>
 * (not documented, see <a href="http://www.sat4j.org">Sat4J.org</a>)
 * 
 * @author lukasiewycz
 * 
 */
public class VariableOrder extends VarOrderHeap {

	protected static final double VAR_RESCALE_FACTOR = 1e-100;

	protected static final double VAR_RESCALE_BOUND = 1 / VAR_RESCALE_FACTOR;

	private static final long serialVersionUID = 1L;

	protected double varInc = 0;

	protected boolean[] phase;

	/**
	 * Constructs a {@link VariableOrder}.
	 */
	public VariableOrder() {
		super(new UserFixedPhaseSelectionStrategy());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sat4j.minisat.orders.VarOrderHeap#setLits(org.sat4j.minisat.core.
	 * ILits)
	 */
	@Override
	public void setLits(ILits lits) {
		this.lits = lits;

		int nlength = lits.nVars() + 1;
		activity = new double[nlength];
		phase = new boolean[nlength];
		activity[0] = -1;
		heap = new Heap(activity);
		heap.setBounds(nlength);
		for (int i = 1; i < nlength; i++) {
			assert i > 0;
			assert i <= lits.nVars() : lits.nVars() + "/" + i;
			activity[i] = 0.0;
			if (lits.belongsToPool(i)) {
				heap.insert(i);
				phase[i] = false;
			} else {
				phase[i] = false;
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sat4j.minisat.orders.VarOrderHeap#init()
	 */
	@Override
	public void init() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sat4j.minisat.orders.VarOrderHeap#updateActivity(int)
	 */
	@Override
	protected void updateActivity(final int var) {
		if ((activity[var] += varInc) > VAR_RESCALE_BOUND) {
			varRescaleActivity();
		}
	}

	/**
	 * Increments the activity of a variable {@code var} by the specified
	 * {@code value}.
	 * 
	 * @param var
	 *            the variable
	 * @param value
	 *            the value to increment the variable
	 */
	protected void updateActivity(final int var, final double value) {
		if ((activity[var] += value) > VAR_RESCALE_BOUND) {
			varRescaleActivity();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sat4j.minisat.orders.VarOrderHeap#updateVar(int)
	 */
	@Override
	public void updateVar(int p) {
		int var = p >> 1;
		updateActivity(var);
		if (heap.inHeap(var)) {
			heap.increase(var);
		}
	}

	/**
	 * Sets the value {@code varInc} to increase the activity of the variables.
	 * 
	 * @param value
	 *            the value to increase the activity of the variables
	 */
	public void setVarInc(double value) {
		varInc = value;
	}

	/**
	 * Rescales the activities of the variables.
	 */
	protected void varRescaleActivity() {
		for (int i = 1; i < activity.length; i++) {
			activity[i] *= VAR_RESCALE_FACTOR;
		}
		varInc *= VAR_RESCALE_FACTOR;
	}

	/**
	 * Sets the activity of a variable {@code var} to the specified
	 * {@code value}.
	 * 
	 * @param var
	 *            the variable
	 * @param value
	 *            the activity to set
	 */
	public void setVarActivity(int var, double value) {
		updateActivity(var, value);
		if (heap.inHeap(var)) {
			heap.increase(var);
		}
	}

	/**
	 * Sets the {@code phase} of a variable {@code var}.
	 * 
	 * @param var
	 *            the variable
	 * @param phase
	 *            the phase
	 */
	public void setVarPhase(int var, boolean phase) {
		this.phase[var] = phase;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sat4j.minisat.orders.VarOrderHeap#select()
	 */
	@Override
	public int select() {
		while (!heap.empty()) {
			int var = heap.getmin();
			int next;
			if (phase[var]) {
				next = posLit(var);
			} else {
				next = negLit(var);
			}
			if (lits.isUnassigned(next)) {
				return next;
			}
		}
		return ILits.UNDEFINED;
	}
}
