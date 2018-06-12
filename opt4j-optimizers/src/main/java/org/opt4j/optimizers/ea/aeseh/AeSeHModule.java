package org.opt4j.optimizers.ea.aeseh;

import static org.opt4j.core.config.annotations.Citation.PublicationMonth.UNKNOWN;

import org.opt4j.core.config.annotations.Citation;
import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.config.annotations.Parent;
import org.opt4j.core.optimizer.OptimizerModule;
import org.opt4j.optimizers.ea.Coupler;
import org.opt4j.optimizers.ea.EvolutionaryAlgorithmModule;
import org.opt4j.optimizers.ea.Selector;

/**
 * The {@link AeSeHModule} binds the {@link EpsilonSamplingSelector} as
 * {@link Selector} and the {@link EpsilonNeighborhoodCoupler} as
 * {@link Coupler} and configures them with the parameters used in the AeSeH
 * paper (see the citation for details). Together with the
 * {@link EvolutionaryAlgorithmModule}, the {@link AeSeHModule}, hence, provides
 * a default configuration for the Epsilon Sampling Epsilon Neighborhood
 * Evolutionary Algorithm.
 * 
 * @author Fedor Smirnov
 */
@Info("Multi-objective evolutionary algorithm where the survival selection and the creation of neighborhoods is based on epsilon-dominance. The selection of parents is done within the created neighborhoods.")
@Citation(authors = "Hernán Aguirre, Akira Oyama, and Kiyoshi Tanaka", title = "Adaptive ε-sampling and ε-hood for evolutionary many-objective optimization.", journal = "Evolutionary Multi-Criterion Optimization (EMO)", pageFirst = 322, pageLast = 336, year = 2013, month = UNKNOWN)
@Parent(EvolutionaryAlgorithmModule.class)
public class AeSeHModule extends OptimizerModule {

	@Override
	protected void config() {

		bindConstant("epsilonSample", ESamplingSurvivorGeneration.class).to(0.0);
		bindConstant("epsilonSampleDelta", ESamplingSurvivorGeneration.class).to(0.005);
		bindConstant("epsilonSampleDeltaMax", ESamplingSurvivorGeneration.class).to(0.005);
		bindConstant("epsilonSampleDeltaMin", ESamplingSurvivorGeneration.class).to(0.0001);

		bindConstant("epsilonNeighborhood", EpsilonNeighborhoodCoupler.class).to(0.0);
		bindConstant("epsilonNeighborhoodDelta", EpsilonNeighborhoodCoupler.class).to(0.005);
		bindConstant("epsilonNeighborhoodDeltaMax", EpsilonNeighborhoodCoupler.class).to(0.005);
		bindConstant("epsilonNeighborhoodDeltaMin", EpsilonNeighborhoodCoupler.class).to(0.0001);
		bindConstant("neighborhoodNumber", EpsilonNeighborhoodCoupler.class).to(5);

		bind(Selector.class).to(EpsilonSamplingSelector.class);
		bind(Coupler.class).to(EpsilonNeighborhoodCoupler.class);
	}
}
