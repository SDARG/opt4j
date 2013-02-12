package org.opt4j.config;

/**
 * The {@link Transformer} design pattern.
 * 
 * @author reimann
 * 
 * @param <I>
 *            the type of the source object
 * @param <O>
 *            the type of the target object
 */
public interface Transformer<I, O> {
	/**
	 * The given input {@code in} is not changed by transforming it to a new object of type <O>. Subsequent calls with
	 * equal instances of {@code in} should result in equal outputs.
	 * 
	 * @param in
	 *            the input to convert
	 * @return the corresponding output
	 */
	public O transform(I in);
}
