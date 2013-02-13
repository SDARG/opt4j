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

package org.opt4j.benchmark.wfg;

import org.opt4j.benchmark.BinaryCreator;
import org.opt4j.benchmark.BinaryToDoubleDecoder;
import org.opt4j.benchmark.Bits;
import org.opt4j.benchmark.DoubleCopyDecoder;
import org.opt4j.benchmark.DoubleCreator;
import org.opt4j.benchmark.K;
import org.opt4j.benchmark.M;
import org.opt4j.benchmark.N;
import org.opt4j.config.annotations.Info;
import org.opt4j.config.annotations.Order;
import org.opt4j.config.annotations.Required;
import org.opt4j.core.problem.Creator;
import org.opt4j.core.problem.Decoder;
import org.opt4j.core.problem.Evaluator;
import org.opt4j.core.problem.ProblemModule;

/**
 * Module for the WFG (Walking Fish Group) benchmarks, see "A Scalable
 * Multi-objective Test Problem Toolkit, Simon Huband, Luigi Barone, R. Lyndon
 * While, and Philip Hingston (EMO 2005)".
 * 
 * @author lukasiewycz
 * 
 */
@Info("WFG Problem Suite. The number of search variables is n=k+l.")
public class WFGModule extends ProblemModule {

	@Order(0)
	@Info("The WFG function.")
	protected Function function = Function.WFG1;

	@Order(2)
	@Info("The number of objectives.")
	protected int m = 2;

	@Order(3)
	@Info("The number of distance parameters. (suggested: l=4 for m=2, l=2*(m-1) for m>2)")
	protected int k = 6;

	@Order(4)
	@Info("The number of position parameters. (k%(m-1)=0)")
	protected int l = 4;

	@Required(property = "encoding", elements = { "BINARY" })
	@Info("The number of bits per double value.")
	protected int bits = 30;

	@Order(10)
	@Info("The encoding of the optimization problem.")
	protected Encoding encoding = Encoding.DOUBLE;

	/**
	 * The used WFG function.
	 * 
	 * @author lukasiewycz
	 */
	public enum Function {
		/**
		 * Use the WFG1 function.
		 */
		WFG1,
		/**
		 * Use the WFG2 function.
		 */
		WFG2,
		/**
		 * Use the WFG3 function.
		 */
		WFG3,
		/**
		 * Use the WFG4 function.
		 */
		WFG4,
		/**
		 * Use the WFG5 function.
		 */
		WFG5,
		/**
		 * Use the WFG6 function.
		 */
		WFG6,
		/**
		 * Use the WFG7 function.
		 */
		WFG7,
		/**
		 * Use the WFG8 function.
		 */
		WFG8,
		/**
		 * Use the WFG9 function.
		 */
		WFG9,
		/**
		 * Use the I1 function.
		 */
		I1,
		/**
		 * Use the I2 function.
		 */
		I2,
		/**
		 * Use the I3 function.
		 */
		I3,
		/**
		 * Use the I4 function.
		 */
		I4,
		/**
		 * Use the I5 function.
		 */
		I5;
	}

	/**
	 * The used encoding type.
	 * 
	 * @author lukasiewycz
	 */
	public enum Encoding {
		/**
		 * Use double values.
		 */
		DOUBLE,
		/**
		 * Use binary values.
		 */
		BINARY;
	}

	/**
	 * Returns the number of bits per double value.
	 * 
	 * @return the bits
	 */
	public int getBits() {
		return bits;
	}

	/**
	 * Sets the number of bits per double value.
	 * 
	 * @param bits
	 *            the bits to set
	 */
	public void setBits(int bits) {
		this.bits = bits;
	}

	/**
	 * Returns the encoding (double or binary).
	 * 
	 * @return the encoding
	 */
	public Encoding getEncoding() {
		return encoding;
	}

	/**
	 * Set the encoding (double or binary).
	 * 
	 * @param encoding
	 *            the encoding
	 */
	public void setEncoding(Encoding encoding) {
		this.encoding = encoding;
	}

	/**
	 * Returns the WFG function.
	 * 
	 * @return the WFG function
	 */
	public Function getFunction() {
		return function;
	}

	/**
	 * Sets the WFG function.
	 * 
	 * @param function
	 *            the WFG function
	 */
	public void setFunction(Function function) {
		this.function = function;

	}

	/**
	 * Returns the l value.
	 * 
	 * @return the l value
	 */
	public int getL() {
		return l;
	}

	/**
	 * Sets the l value.
	 * 
	 * @param l
	 *            the l value
	 */
	public void setL(int l) {
		assert (l > 0);

		if (function == Function.WFG2 || function == Function.WFG3) {
			this.l = (int) Math.ceil((double) l / 2) * 2;
		} else {
			this.l = l;
		}

	}

	/**
	 * Returns the m value.
	 * 
	 * @return the m
	 */
	public int getM() {
		return m;
	}

	/**
	 * Sets the m value.
	 * 
	 * @param m
	 *            the m to set
	 */
	public void setM(int m) {
		assert (m >= 2);

		this.m = m;
		if (m == 2) {
			setL(4);
		} else {
			setL(2 * (m - 1));
		}
		setK(k);
	}

	/**
	 * Returns the k value.
	 * 
	 * @return the k
	 */
	public int getK() {
		return k;
	}

	/**
	 * Sets the k value.
	 * 
	 * @param k
	 *            the k to set
	 */
	public void setK(int k) {
		assert (k > 0);

		if ((k % (m - 1)) == 0) {
			this.k = k;
		} else {
			double div = (double) k / (m - 1);
			this.k = (m - 1) * (int) Math.ceil(div);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.start.Opt4JModule#config()
	 */
	@Override
	public void config() {

		Class<? extends Creator<?>> creator = null;
		Class<? extends Decoder<?, ?>> decoder = null;
		Class<? extends Evaluator<?>> evaluator = null;

		switch (encoding) {
		case BINARY:
			creator = BinaryCreator.class;
			decoder = BinaryToDoubleDecoder.class;
			break;
		case DOUBLE:
			creator = DoubleCreator.class;
			decoder = DoubleCopyDecoder.class;
			break;
		}

		switch (function) {
		default: // WFG1
			evaluator = WFG1.class;
			break;
		case WFG2:
			evaluator = WFG2.class;
			break;
		case WFG3:
			evaluator = WFG3.class;
			break;
		case WFG4:
			evaluator = WFG4.class;
			break;
		case WFG5:
			evaluator = WFG5.class;
			break;
		case WFG6:
			evaluator = WFG6.class;
			break;
		case WFG7:
			evaluator = WFG7.class;
			break;
		case WFG8:
			evaluator = WFG8.class;
			break;
		case WFG9:
			evaluator = WFG9.class;
			break;
		case I1:
			evaluator = WFGI1.class;
			break;
		case I2:
			evaluator = WFGI2.class;
			break;
		case I3:
			evaluator = WFGI3.class;
			break;
		case I4:
			evaluator = WFGI4.class;
			break;
		case I5:
			evaluator = WFGI5.class;
			break;
		}

		bindConstant(N.class).to(k + l);
		bindConstant(M.class).to(m);
		bindConstant(K.class).to(k);
		bindConstant(Bits.class).to(bits);
		bindProblem(creator, decoder, evaluator);

	}

}
