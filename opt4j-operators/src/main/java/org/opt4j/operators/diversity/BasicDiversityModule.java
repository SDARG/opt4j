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
 

package org.opt4j.operators.diversity;

import org.opt4j.core.config.PropertyModule;
import org.opt4j.core.config.annotations.Ignore;
import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.genotype.BooleanGenotype;
import org.opt4j.core.genotype.DoubleGenotype;

/**
 * The {@link BasicDiversityModule} is the basic {@link PropertyModule} for the
 * {@link Diversity} operator.
 * 
 * @author glass
 * 
 */
@Info("Setting for the basic diversity classOperators for genotypes.")
public class BasicDiversityModule extends DiversityModule {

	@Ignore
	@Info("The type of the diversity operator for the Boolean genotype.")
	BooleanType booleanType = BooleanType.FRACTION;

	@Info("The type of the diversity operator for the Double genotype.")
	DoubleType doubleType = DoubleType.ABSOLUTE;

	/**
	 * Type of {@link Diversity} operator for the {@link BooleanGenotype}.
	 * 
	 * @author glass
	 */
	public enum BooleanType {
		/**
		 * Use the {@link DiversityBooleanFraction} operator.
		 */
		FRACTION;
	}

	/**
	 * Type of {@link Diversity} operator for the {@link DoubleGenotype}.
	 * 
	 * @author glass
	 */
	public enum DoubleType {
		/**
		 * Use the {@link DiversityDoubleAbsolute} operator.
		 */
		ABSOLUTE,
		/**
		 * Use the {@link DiversityDoubleEuclidean} operator.
		 */
		EUCLIDEAN;
	}

	/**
	 * Constructs a {@link BasicDiversityModule}.
	 * 
	 */
	public BasicDiversityModule() {
		super();
	}

	/**
	 * Returns the Type of {@link Diversity} operator for
	 * {@link BooleanGenotype}.
	 * 
	 * @return the type of diversity operator for boolean genotypes
	 */
	public BooleanType getBooleanType() {
		return booleanType;
	}

	/**
	 * Sets the Type of {@link Diversity} operator for {@link BooleanGenotype}.
	 * 
	 * @param booleanType
	 *            the booleanType to set
	 */
	public void setBooleanType(BooleanType booleanType) {
		this.booleanType = booleanType;
	}

	/**
	 * Returns the Type of {@link Diversity} operator for {@link DoubleGenotype}
	 * .
	 * 
	 * @return the type of diversity operator for double genotypes
	 */
	public DoubleType getDoubleType() {
		return doubleType;
	}

	/**
	 * Sets the the Type of {@link Diversity} operator for
	 * {@link DoubleGenotype}.
	 * 
	 * @param doubleType
	 *            the doubleType to set
	 */
	public void setDoubleType(DoubleType doubleType) {
		this.doubleType = doubleType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.start.Opt4JModule#config()
	 */
	@Override
	public void config() {
		switch (booleanType) {
		case FRACTION:
			bind(DiversityBoolean.class).to(DiversityBooleanFraction.class).in(SINGLETON);
			break;
		}

		switch (doubleType) {
		case ABSOLUTE:
			bind(DiversityDouble.class).to(DiversityDoubleAbsolute.class).in(SINGLETON);
			break;
		case EUCLIDEAN:
			bind(DiversityDouble.class).to(DiversityDoubleEuclidean.class).in(SINGLETON);
			break;
		}
	}
}
