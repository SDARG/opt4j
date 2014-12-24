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
 

package org.opt4j.benchmarks.zdt;

import org.opt4j.benchmarks.BinaryCopyDecoder;
import org.opt4j.benchmarks.BinaryCreator;
import org.opt4j.benchmarks.BinaryToDoubleDecoder;
import org.opt4j.benchmarks.Bits;
import org.opt4j.benchmarks.DoubleCopyDecoder;
import org.opt4j.benchmarks.DoubleCreator;
import org.opt4j.benchmarks.N;
import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.config.annotations.Order;
import org.opt4j.core.config.annotations.Required;
import org.opt4j.core.problem.Creator;
import org.opt4j.core.problem.Decoder;
import org.opt4j.core.problem.Evaluator;
import org.opt4j.core.problem.ProblemModule;

/**
 * The {@link ZDTModule} configures the ZDT benchmarks, see "Comparison of
 * Multiobjective Evolutionary Algorithms: Empirical Results, Eckart Zitzler,
 * Kalyanmoy Deb, Lothar Thiele, Evolutionary Computation Volume 8 , Issue 2
 * (June 2000)".
 * 
 * @author lukasiewycz
 * 
 */
@Info("ZDT Problem Suite.")
public class ZDTModule extends ProblemModule {

	@Order(0)
	@Info("The ZDT function.")
	protected Function function = Function.ZDT1;

	@Order(1)
	@Info("The size of the search space.")
	protected int n = 30;

	@Order(2)
	@Required(property = "function", elements = { "ZDT1", "ZDT2", "ZDT3", "ZDT4", "ZDT6" })
	@Info("The encoding of the optimization problem.")
	protected Encoding encoding = Encoding.BINARY;

	@Order(3)
	@Required(property = "encoding", elements = { "BINARY" })
	@Info("The number of bits per double value.")
	protected int bits = 30;

	/**
	 * The used ZDT function.
	 * 
	 * @author lukasiewycz
	 */
	public enum Function {
		/**
		 * Use the ZDT1 function.
		 */
		ZDT1,
		/**
		 * Use the ZDT2 function.
		 */
		ZDT2,
		/**
		 * Use the ZDT3 function.
		 */
		ZDT3,
		/**
		 * Use the ZDT4 function.
		 */
		ZDT4,
		/**
		 * Use the ZDT5 function.
		 */
		ZDT5,
		/**
		 * Use the ZDT6 function.
		 */
		ZDT6;
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
		validate();
	}

	/**
	 * Returns the ZDT function.
	 * 
	 * @return the ZDT function
	 */
	public Function getFunction() {
		return function;
	}

	/**
	 * Sets the ZDT function.
	 * 
	 * @param function
	 *            the ZDT function
	 */
	public void setFunction(Function function) {
		this.function = function;
		validate();

		switch (function) {
		case ZDT1:
			n = 30;
			break;
		case ZDT2:
			n = 30;
			break;
		case ZDT3:
			n = 30;
			break;
		case ZDT4:
			n = 10;
			break;
		case ZDT5:
			n = 11;
			break;
		case ZDT6:
			n = 10;
			break;
		default:
			n = 30;
		}

	}

	/**
	 * Returns the n value.
	 * 
	 * @return the n value
	 */
	public int getN() {
		return n;
	}

	/**
	 * Sets the n value.
	 * 
	 * @param n
	 *            the n value
	 */
	public void setN(int n) {
		this.n = n;
	}

	/**
	 * Validates all settings. In particular, ZDT5 can be encoded binary only.
	 */
	protected void validate() {
		if (function == Function.ZDT5 && encoding == Encoding.DOUBLE) {
			encoding = Encoding.BINARY;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.start.Opt4JModule#config()
	 */
	@Override
	public void config() {
		validate();

		Class<? extends Creator<?>> creator = null;
		Class<? extends Decoder<?, ?>> decoder = null;
		Class<? extends Evaluator<?>> evaluator = null;

		switch (encoding) {
		case BINARY:
			if (function == Function.ZDT5) {
				creator = ZDT5BinaryCreator.class;
				decoder = BinaryCopyDecoder.class;
			} else {
				creator = BinaryCreator.class;
				decoder = BinaryToDoubleDecoder.class;
			}
			break;
		case DOUBLE:
			// only for ZDT1,ZDT2,ZDT3,ZDT4,ZDT6
			creator = DoubleCreator.class;
			decoder = DoubleCopyDecoder.class;
			break;
		}

		switch (function) {
		case ZDT1:
			evaluator = ZDT1.class;
			break;
		case ZDT2:
			evaluator = ZDT2.class;
			break;
		case ZDT3:
			evaluator = ZDT3.class;
			break;
		case ZDT4:
			evaluator = ZDT4.class;
			break;
		case ZDT5:
			evaluator = ZDT5.class;
			break;
		case ZDT6:
			evaluator = ZDT6.class;
			break;
		default:
		}

		bindConstant(N.class).to(n);
		bindConstant(Bits.class).to(bits);
		bindProblem(creator, decoder, evaluator);

	}

}
