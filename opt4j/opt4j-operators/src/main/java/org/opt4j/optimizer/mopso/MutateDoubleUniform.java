package org.opt4j.optimizer.mopso;

import org.opt4j.common.random.Rand;
import org.opt4j.genotype.DoubleGenotype;
import org.opt4j.operator.mutate.MutateDoubleElementwise;
import org.opt4j.operator.normalize.NormalizeDouble;
import org.opt4j.start.Constant;

import com.google.inject.Inject;

/**
 * The {@link MutateDoubleUniform} uniformly mutates a {@link DoubleGenotype}
 * elementwise.
 * 
 * @author lukasiewycz
 * 
 */
public class MutateDoubleUniform extends MutateDoubleElementwise {

	protected final double perturbation;

	/**
	 * Constructs a {@link MutateDoubleUniform}.
	 * 
	 * @param random
	 *            the random number generator
	 * @param normalize
	 *            the normalize operator
	 * @param perturbation
	 *            the perturbation index
	 */
	@Inject
	public MutateDoubleUniform(Rand random, NormalizeDouble normalize,
			@Constant(value = "perturbation", namespace = MutateDoubleNonUniform.class) double perturbation) {
		super(random, normalize);
		this.perturbation = perturbation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.operator.mutate.MutateDoubleElementwise#mutateElement(double,
	 * double, double, double)
	 */
	@Override
	protected double mutateElement(double x, double lb, double ub, double p) {
		if (random.nextDouble() < p) {
			double v = (random.nextDouble() - 0.5) * perturbation;
			x += v;
		}
		return x;
	}

}
