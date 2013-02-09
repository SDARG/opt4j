package org.opt4j.genotype;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Random;

import org.opt4j.core.Genotype;

/**
 * The {@link IntegerGenotype} is a {@link Genotype} that consists of integer
 * values.
 * 
 * @author lukasiewycz
 * 
 */
public class IntegerGenotype extends ArrayList<Integer> implements ListGenotype<Integer> {

	protected final Bounds<Integer> bounds;

	/**
	 * Constructs a {@link IntegerGenotype} with a specified lower and upper
	 * bound for all values.
	 * 
	 * @param lowerBound
	 *            the lower bound
	 * @param upperBound
	 *            the upper bound
	 */
	public IntegerGenotype(int lowerBound, int upperBound) {
		this(new FixedBounds<Integer>(lowerBound, upperBound));
	}

	/**
	 * Constructs a {@link IntegerGenotype} with the given {@link Bounds}.
	 * 
	 * @param bounds
	 *            the bounds
	 */
	public IntegerGenotype(Bounds<Integer> bounds) {
		this.bounds = bounds;
	}

	/**
	 * Returns the lower bound for the {@code i}-th element.
	 * 
	 * @param index
	 *            the {@code i}-th element
	 * @return the lower bound of the {@code i}-th element
	 */
	public int getLowerBound(int index) {
		return bounds.getLowerBound(index);
	}

	/**
	 * Returns the upper bound for the {@code i}-th element.
	 * 
	 * @param index
	 *            the {@code i}-th element
	 * @return the upper bound of the {@code i}-th element
	 */
	public int getUpperBound(int index) {
		return bounds.getUpperBound(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.Genotype#newInstance()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <G extends Genotype> G newInstance() {
		try {
			Constructor<? extends IntegerGenotype> cstr = this.getClass().getConstructor(Bounds.class);
			return (G) cstr.newInstance(bounds);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Initialize this genotype with {@code n} random values.
	 * 
	 * @param random
	 *            the random number generator
	 * @param n
	 *            the number of elements in the resulting genotype
	 */
	public void init(Random random, int n) {
		for (int i = 0; i < n; i++) {
			int lo = getLowerBound(i);
			int hi = getUpperBound(i);
			int value = lo + random.nextInt(hi - lo + 1);
			if (i >= size()) {
				add(value);
			} else {
				set(i, value);
			}
		}
	}

	private static final long serialVersionUID = 1L;

}
