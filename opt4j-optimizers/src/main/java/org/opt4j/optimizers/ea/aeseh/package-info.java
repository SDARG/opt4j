/**
 * 
 * <p>
 * Package for the classes of the Adaptive ε-Sampling ε-Hood MOEA. This
 * evolutionary algorithm performs both the survivor and the parent selection
 * based on ε-sampling.
 * </p>
 * <p>
 * The {@link org.opt4j.optimizers.ea.aeseh.EpsilonSamplingSelector} uses the
 * {@link org.opt4j.optimizers.ea.aeseh.ESamplingSurvivorGeneration} to pick the
 * individuals that form the pool of possible parents. Hereby, ε-dominant
 * {@link org.opt4j.core.Individual}s are preferred. The check for ε-dominance
 * is implemented by enhancing the {@link org.opt4j.core.Objectives} of the
 * considered individual using the
 * {@link org.opt4j.optimizers.ea.aeseh.EpsilonMapping}. For the parent
 * selection, the {@link org.opt4j.optimizers.ea.aeseh.EpsilonNeighborhoodCoupler} divides the
 * survivor pool based on ε-dominance among the survivors. A pair of parents is
 * then always picked from the same neighborhood, while the arbitration of the
 * neighborhoods to pick from is handled by the
 * {@link org.opt4j.optimizers.ea.aeseh.NeighborhoodScheduler}.
 * </p>
 * <p>
 * Throughout the exploration, the ε-values used for the survivor generation and
 * the parent selection are adjusted in order to pick a number of ε-dominant
 * survivors that equals the number of individuals that are to be generated in
 * each generation and to create a number of neighborhoods close to a number
 * provided by the user. The adaptation of the ε-values is hereby managed by the
 * {@link org.opt4j.optimizers.ea.aeseh.EpsilonAdaptation}.
 * </p>
 * <p>
 * In the Opt4J GUI, the AeSeH is configured using the
 * {@link org.opt4j.optimizers.ea.aeseh.AeSeHModule} and the
 * {@link org.opt4j.optimizers.ea.EvolutionaryAlgorithmModule}.
 * </p>
 */
package org.opt4j.optimizers.ea.aeseh;
