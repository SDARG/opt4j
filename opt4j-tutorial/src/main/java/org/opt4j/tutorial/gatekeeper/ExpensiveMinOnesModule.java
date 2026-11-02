package org.opt4j.tutorial.gatekeeper;

import org.opt4j.core.config.annotations.Parent;
import org.opt4j.core.problem.ProblemModule;
import org.opt4j.gatekeeper.FeatureExtractor;
import org.opt4j.tutorial.TutorialModule;
import org.opt4j.tutorial.minones.MinOnesDecoder;

@Parent(TutorialModule.class)
public class ExpensiveMinOnesModule extends ProblemModule {
    @Override
    protected void config() {
        // We reuse the Creator and Decoder from MinOnes, but plug in our ExpensiveEvaluator
        bindProblem(MinOnesDecoder.class, MinOnesDecoder.class, ExpensiveEvaluator.class);

        // Bind the custom feature extractor to the gatekeeper
        bind(FeatureExtractor.class).to(MinOnesFeatureExtractor.class);
    }
}
