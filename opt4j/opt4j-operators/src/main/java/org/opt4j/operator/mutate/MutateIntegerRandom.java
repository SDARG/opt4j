package org.opt4j.operator.mutate;

import java.util.Random;

import org.opt4j.common.random.Rand;
import org.opt4j.genotype.IntegerGenotype;

import com.google.inject.Inject;

/**
 * The {@link MutateIntegerRandom} mutates each element of the
 * {@link IntegerGenotype} with the mutation rate. Here, a new value is created
 * randomly between the lower and upper bounds.
 * 
 * @author lukasiewycz
 * 
 */
public class MutateIntegerRandom implements MutateInteger {

	protected final Random random;

	/**
	 * Constructs a {@link MutateIntegerRandom}.
	 * 
	 * @param random
	 *            the random number generator
	 */
	@Inject
	public MutateIntegerRandom(Rand random) {
		this.random = random;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.operator.mutate.Mutate#mutate(org.opt4j.core.problem.Genotype,
	 * double)
	 */
	@Override
	public void mutate(IntegerGenotype genotype, double p) {

		int size = genotype.size();
		for (int i = 0; i < size; i++) {
			if (random.nextDouble() < p) {
				int lb = genotype.getLowerBound(i);
				int ub = genotype.getUpperBound(i);
				int value = random.nextInt(ub - lb + 1) + lb;
				genotype.set(i, value);
			}
		}
	}

}
