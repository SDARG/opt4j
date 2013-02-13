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

package org.opt4j.benchmark.zdt;

import org.opt4j.benchmark.BinaryCopyDecoder;
import org.opt4j.benchmark.BinaryCreator;
import org.opt4j.benchmark.BinaryToDoubleDecoder;
import org.opt4j.benchmark.Bits;
import org.opt4j.benchmark.DoubleCopyDecoder;
import org.opt4j.benchmark.DoubleCreator;
import org.opt4j.benchmark.N;
import org.opt4j.config.annotations.Info;
import org.opt4j.config.annotations.Order;
import org.opt4j.config.annotations.Required;
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
