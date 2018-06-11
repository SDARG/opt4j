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
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *******************************************************************************/

package org.opt4j.benchmarks.wfg;

import org.opt4j.benchmarks.BinaryCreator;
import org.opt4j.benchmarks.BinaryToDoubleDecoder;
import org.opt4j.benchmarks.Bits;
import org.opt4j.benchmarks.DoubleCopyDecoder;
import org.opt4j.benchmarks.DoubleCreator;
import org.opt4j.benchmarks.K;
import org.opt4j.benchmarks.M;
import org.opt4j.benchmarks.N;
import org.opt4j.core.config.annotations.Citation;
import org.opt4j.core.config.annotations.Citation.PublicationMonth;
import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.config.annotations.Order;
import org.opt4j.core.config.annotations.Required;
import org.opt4j.core.problem.Creator;
import org.opt4j.core.problem.Decoder;
import org.opt4j.core.problem.Evaluator;
import org.opt4j.core.problem.ProblemModule;

/**
 * Module for the WFG (Walking Fish Group) benchmarks, see Huband et al. 2005.
 * 
 * @author lukasiewycz
 * 
 */
@Info("WFG Problem Suite. The number of search variables is n=k+l.")
@Citation(title = "A Scalable Multi-objective Test Problem Toolkit", authors = "Simon Huband, Luigi Barone, Lyndon While, and Philip Hingston", journal = "Evolutionary Multi-Criterion Optimization", pageFirst = 280, pageLast = 295, year = 2005, month = PublicationMonth.UNKNOWN, doi = "10.1007/978-3-540-31880-4_20")
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
		default: //BINARY
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
