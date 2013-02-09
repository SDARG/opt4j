package org.opt4j.genotype;

/**
 * The {@link FixedBounds} are {@link Bounds} that return a fixed lower and
 * upper bound for each index.
 * 
 * @author lukasiewycz
 * 
 * @param <E>
 */
class FixedBounds<E extends Number> implements Bounds<E> {

	protected final E lowerBound;
	protected final E upperBound;

	/**
	 * Construct a {@link FixedBounds}.
	 * 
	 * @param lowerBound
	 *            the lower bound
	 * @param upperBound
	 *            the upper bound
	 */
	public FixedBounds(E lowerBound, E upperBound) {
		super();
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.genotype.Bounds#getLowerBound(int)
	 */
	@Override
	public E getLowerBound(int index) {
		return lowerBound;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.genotype.Bounds#getUpperBound(int)
	 */
	@Override
	public E getUpperBound(int index) {
		return upperBound;
	}

}
