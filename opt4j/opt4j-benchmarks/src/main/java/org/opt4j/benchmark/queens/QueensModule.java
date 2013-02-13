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

package org.opt4j.benchmark.queens;

import org.opt4j.config.Icons;
import org.opt4j.config.annotations.Icon;
import org.opt4j.config.annotations.Info;
import org.opt4j.config.annotations.Order;
import org.opt4j.core.problem.Creator;
import org.opt4j.core.problem.Decoder;
import org.opt4j.core.problem.ProblemModule;
import org.opt4j.start.Constant;

/**
 * The {@link QueensModule} is used for the configuration of the queens problem.
 * Containing the size and dimensions of the problem as well as the
 * {@link Decoder} strategy.
 * 
 * @author lukasiewycz
 * 
 */
@Icon(Icons.PROBLEM)
@Info("The n-Queens problem as optimization problem with linear objective functions.")
public class QueensModule extends ProblemModule {

	@Info("The size of the board (size*size).")
	@Order(0)
	@Constant(value = "size", namespace = QueensProblem.class)
	protected int size = 40;

	@Info("The number of objective functions.")
	@Order(1)
	@Constant(value = "dim", namespace = QueensProblem.class)
	protected int dimensions = 2;

	@Info("The seed of the problem generator (for the objective function).")
	@Order(2)
	@Constant(value = "seed", namespace = QueensProblem.class)
	protected int seed = 0;

	@Info("the used decoder")
	@Order(3)
	protected Dec decoder = Dec.SAT;

	/**
	 * Constructs a {@link QueensModule}.
	 */
	public QueensModule() {
		super();
	}

	/**
	 * The {@link Decoder} strategy for the queens problem.
	 * 
	 * @author lukasiewycz
	 * 
	 */
	public enum Dec {
		/**
		 * Use the {@link QueensCopyDecoder}.
		 */
		COPY,
		/**
		 * Use the {@link QueensPermutationDecoder}.
		 */
		PERMUTATION,
		/**
		 * Use the {@link QueensSATDecoder}.
		 */
		SAT;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.start.Opt4JModule#config()
	 */
	@Override
	public void config() {

		bind(QueensProblem.class).in(SINGLETON);

		Class<? extends Creator<?>> creatorClass = null;
		Class<? extends Decoder<?, ?>> decoderClass = null;

		switch (decoder) {
		case SAT:
			creatorClass = QueensSATDecoder.class;
			decoderClass = QueensSATDecoder.class;
			break;
		case PERMUTATION:
			creatorClass = QueensPermutationDecoder.class;
			decoderClass = QueensPermutationDecoder.class;
			break;
		case COPY:
			creatorClass = QueensCopyDecoder.class;
			decoderClass = QueensCopyDecoder.class;
			break;
		}

		bindProblem(creatorClass, decoderClass, QueensErrorEvaluator.class);
		addEvaluator(QueensEvaluator.class);
	}

	/**
	 * Returns the decoder strategy.
	 * 
	 * @return the decoder strategy
	 */
	public Dec getDecoder() {
		return decoder;
	}

	/**
	 * Sets the decoder strategy.
	 * 
	 * @param decoder
	 *            the decoder strategy
	 */
	public void setDecoder(Dec decoder) {
		this.decoder = decoder;
	}

	/**
	 * Returns the number of dimensions of the queens problem.
	 * 
	 * @return the number of dimensions
	 */
	public int getDimensions() {
		return dimensions;
	}

	/**
	 * Sets the number of dimensions of the queens problem.
	 * 
	 * @param dimensions
	 *            the number of dimensions
	 */
	public void setDimensions(int dimensions) {
		this.dimensions = dimensions;
	}

	/**
	 * Sets the size of the board of the queens problem.
	 * 
	 * @param size
	 *            the size of the board
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * Returns the size of the board of the queens problem.
	 * 
	 * @return the size of the board
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Return the seed of the problem generator.
	 * 
	 * @return the seed of the problem generator
	 */
	public int getSeed() {
		return seed;
	}

	/**
	 * Sets the seed of the problem generator.
	 * 
	 * @param seed
	 *            the seed of the problem generator
	 */
	public void setSeed(int seed) {
		this.seed = seed;
	}

}