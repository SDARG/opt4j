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
 

package org.opt4j.operators.crossover;

import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.config.annotations.Required;
import org.opt4j.core.genotype.BooleanGenotype;
import org.opt4j.core.genotype.DoubleGenotype;
import org.opt4j.core.genotype.IntegerGenotype;
import org.opt4j.core.genotype.PermutationGenotype;
import org.opt4j.core.optimizer.Operator;
import org.opt4j.core.start.Constant;
import org.opt4j.operators.crossover.CrossoverDoubleSBX.Nu;

/**
 * The {@link BasicCrossoverModule}.
 * 
 * @author lukasiewycz
 * 
 */
@Info("Setting for the basic crossover classOperators for genotype variation.")
public class BasicCrossoverModule extends CrossoverModule {

	@Info("The type of the crossover operator for the Boolean genotype.")
	protected BooleanType booleanType = BooleanType.RATE;

	@Required(property = "booleanType", elements = { "RATE" })
	@Info("The probability for a crossover point.")
	@Constant(value = "rate", namespace = CrossoverBooleanRate.class)
	protected double booleanRate = 0.5;

	@Required(property = "booleanType", elements = { "XPOINT" })
	@Info("The number of crossover points.")
	@Constant(value = "x", namespace = CrossoverBooleanXPoint.class)
	protected int booleanXPoints = 1;

	@Info("The type of the crossover operator for the Double genotype.")
	protected DoubleType doubleType = DoubleType.SBX;

	@Required(property = "doubleType", elements = { "BLX", "UNFAIR_AVERAGE" })
	protected double alpha = 0.5;

	@Required(property = "doubleType", elements = { "SBX" })
	@Constant(value = "nu", namespace = CrossoverDoubleSBX.class)
	protected double nu = 15;

	@Info("The type of the crossover operator for the Permutation genotype.")
	protected PermutationType permutationType = PermutationType.ONEPOINT;

	@Required(property = "permutationType", elements = { "ONEPOINT" })
	@Constant(value = "rotation", namespace = CrossoverPermutationOnePoint.class)
	protected boolean rotation = false;

	@Info("The type of the crossover operator for the Integer genotype.")
	protected IntegerType integerType = IntegerType.RATE;

	@Required(property = "integerType", elements = { "RATE" })
	@Info("The probability for a crossover point.")
	@Constant(value = "rate", namespace = CrossoverIntegerRate.class)
	protected double integerRate = 0.5;

	@Required(property = "integerType", elements = { "XPOINT" })
	@Info("The number of crossover points.")
	@Constant(value = "x", namespace = CrossoverIntegerXPoint.class)
	protected int integerXPoints = 1;

	/**
	 * Type of {@link Crossover} operator for the {@link BooleanGenotype}.
	 * 
	 * @author lukasiewycz
	 */
	public enum BooleanType {
		/**
		 * Use the {@link CrossoverBooleanRate}.
		 */
		RATE,
		/**
		 * Use the {@link CrossoverBooleanXPoint}.
		 */
		XPOINT;
	}

	/**
	 * Type of {@link Crossover} operator for the {@link DoubleGenotype}.
	 * 
	 * @author glass
	 */
	public enum DoubleType {
		/**
		 * Use the {@link CrossoverDoubleBLX}.
		 */
		BLX,
		/**
		 * Use the {@link CrossoverDoubleSBX}.
		 */
		SBX,
		/**
		 * Use the {@link CrossoverDoubleUnfairAverage}.
		 */
		UNFAIR_AVERAGE;
	}

	/**
	 * Type of {@link Crossover} operator for the {@link PermutationGenotype}.
	 * 
	 * @author lukasiewycz
	 * 
	 */
	public enum PermutationType {
		/**
		 * Use the {@link CrossoverPermutationOnePoint}.
		 */
		ONEPOINT,
		/**
		 * Use the {@link CrossoverPermutationBucket}.
		 */
		BUCKET;
	}

	/**
	 * Type of {@link Crossover} operator for the {@link IntegerGenotype}.
	 * 
	 * @author lukasiewycz
	 */
	public enum IntegerType {
		/**
		 * Use the {@link CrossoverBooleanRate}.
		 */
		RATE,
		/**
		 * Use the {@link CrossoverBooleanXPoint}.
		 */
		XPOINT;
	}

	/**
	 * Constructs a {@link BasicCrossoverModule}.
	 */
	public BasicCrossoverModule() {
		super();
	}

	/**
	 * Returns the rate of the {@link CrossoverIntegerRate}.
	 * 
	 * @return the rate
	 */
	public double getIntegerRate() {
		return integerRate;
	}

	/**
	 * Sets the rate of the {@link CrossoverIntegerRate}.
	 * 
	 * @param integerRate
	 *            the rate
	 */
	public void setIntegerRate(double integerRate) {
		this.integerRate = integerRate;
	}

	/**
	 * Returns the number of crossover points of the
	 * {@link CrossoverIntegerXPoint}.
	 * 
	 * @return the number of crossover points
	 */
	public int getIntegerXPoints() {
		return integerXPoints;
	}

	/**
	 * Sets the number of crossover points of the {@link CrossoverIntegerXPoint}
	 * .
	 * 
	 * @param integerXPoint
	 *            the number of crossover points
	 */
	public void setIntegerXPoints(int integerXPoint) {
		this.integerXPoints = integerXPoint;
	}

	/**
	 * Return the {@link Operator} for {@link IntegerGenotype}.
	 * 
	 * @return the operator
	 */
	public IntegerType getIntegerType() {
		return integerType;
	}

	/**
	 * Sets the {@link Operator} for {@link IntegerGenotype}.
	 * 
	 * @param integerType
	 *            the operator
	 */
	public void setIntegerType(IntegerType integerType) {
		this.integerType = integerType;
	}

	/**
	 * Returns the rate of the {@link CrossoverBooleanRate}.
	 * 
	 * @return the rate
	 */
	public double getBooleanRate() {
		return booleanRate;
	}

	/**
	 * Sets the rate of the {@link CrossoverBooleanRate}.
	 * 
	 * @param booleanRate
	 *            the rate
	 */
	public void setBooleanRate(double booleanRate) {
		this.booleanRate = booleanRate;
	}

	/**
	 * Returns the number of crossover points of the
	 * {@link CrossoverBooleanXPoint}.
	 * 
	 * @return the number of crossover points
	 */
	public int getBooleanXPoints() {
		return booleanXPoints;
	}

	/**
	 * Sets the number of crossover points of the {@link CrossoverBooleanXPoint}
	 * .
	 * 
	 * @param booleanXPoints
	 *            the number of crossover points
	 */
	public void setBooleanXPoints(int booleanXPoints) {
		this.booleanXPoints = booleanXPoints;
	}

	/**
	 * Return the {@link Operator} for {@link BooleanGenotype}.
	 * 
	 * @return the operator
	 */
	public BooleanType getBooleanType() {
		return booleanType;
	}

	/**
	 * Sets the {@link Operator} for {@link BooleanGenotype}.
	 * 
	 * @param booleanType
	 *            the operator
	 */
	public void setBooleanType(BooleanType booleanType) {
		this.booleanType = booleanType;
	}

	/**
	 * Returns the alpha value of the {@link CrossoverDoubleBLX} or
	 * {@link CrossoverDoubleUnfairAverage}, respectively.
	 * 
	 * @return the alpha value
	 */
	public double getAlpha() {
		return alpha;
	}

	/**
	 * Sets the alpha value of the {@link CrossoverDoubleBLX} or
	 * {@link CrossoverDoubleUnfairAverage}, respectively.
	 * 
	 * @param alpha
	 *            the alpha value
	 */
	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	/**
	 * Returns the nu value of the {@link CrossoverDoubleSBX}.
	 * 
	 * @return the nu value
	 */
	public double getNu() {
		return nu;
	}

	/**
	 * Sets the nu value of the {@link CrossoverDoubleSBX}.
	 * 
	 * @param nu
	 *            the nu value
	 */
	public void setNu(double nu) {
		this.nu = nu;
	}

	/**
	 * Return the {@link Operator} for {@link DoubleGenotype}.
	 * 
	 * @return the operator
	 */
	public DoubleType getDoubleType() {
		return doubleType;
	}

	/**
	 * Sets the {@link Operator} for {@link DoubleGenotype}.
	 * 
	 * @param doubleType
	 *            the operator
	 */
	public void setDoubleType(DoubleType doubleType) {
		this.doubleType = doubleType;
	}

	/**
	 * Returns the {@link PermutationType}.
	 * 
	 * @return the permutation type
	 */
	public PermutationType getPermutationType() {
		return permutationType;
	}

	/**
	 * Sets the {@link PermutationType}.
	 * 
	 * @param permutationType
	 *            the permutation type
	 */
	public void setPermutationType(PermutationType permutationType) {
		this.permutationType = permutationType;
	}

	/**
	 * Returns {@code true} if rotation is used for the {@link Crossover}
	 * operator for the {@link PermutationGenotype}.
	 * 
	 * @return {@code true} if rotation is used
	 */
	public boolean isRotation() {
		return rotation;
	}

	/**
	 * Sets the rotation value for the {@link Crossover} operator for the
	 * {@link PermutationGenotype}.
	 * 
	 * @param rotation
	 *            the rotation value
	 */
	public void setRotation(boolean rotation) {
		this.rotation = rotation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.start.Opt4JModule#config()
	 */
	@Override
	public void config() {
		switch (booleanType) {
		case XPOINT:
			bind(CrossoverBoolean.class).to(CrossoverBooleanXPoint.class).in(SINGLETON);
			break;
		default: // RATE
			bind(CrossoverBoolean.class).to(CrossoverBooleanRate.class).in(SINGLETON);
			break;
		}

		switch (integerType) {
		case XPOINT:
			bind(CrossoverInteger.class).to(CrossoverIntegerXPoint.class).in(SINGLETON);
			break;
		default: // RATE
			bind(CrossoverInteger.class).to(CrossoverIntegerRate.class).in(SINGLETON);
			break;
		}

		switch (doubleType) {
		case BLX:
			bind(CrossoverDouble.class).to(CrossoverDoubleBLX.class).in(SINGLETON);
			break;
		case UNFAIR_AVERAGE:
			bind(CrossoverDouble.class).to(CrossoverDoubleUnfairAverage.class).in(SINGLETON);

			break;
		default: // SBX
			bind(CrossoverDouble.class).to(CrossoverDoubleSBX.class).in(SINGLETON);
			break;
		}

		switch (permutationType) {
		case BUCKET:
			bind(CrossoverPermutation.class).to(CrossoverPermutationBucket.class).in(SINGLETON);
			break;
		default: // ONEPOINT
			bind(CrossoverPermutation.class).to(CrossoverPermutationOnePoint.class).in(SINGLETON);
			break;
		}

		bindConstant("alpha", CrossoverDoubleBLX.class).to(alpha);
		bindConstant("alpha", CrossoverDoubleUnfairAverage.class).to(alpha);
		bindConstant(Nu.class).to(nu);
	}

}
