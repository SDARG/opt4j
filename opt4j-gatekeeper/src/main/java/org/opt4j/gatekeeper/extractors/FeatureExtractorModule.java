/*******************************************************************************
 * Copyright (c) 2026 Opt4J
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

package org.opt4j.gatekeeper.extractors;

import com.google.inject.Singleton;
import org.opt4j.core.config.Icons;
import org.opt4j.core.config.annotations.Icon;
import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.config.annotations.Order;
import org.opt4j.core.config.annotations.Parent;
import org.opt4j.core.start.Opt4JModule;
import org.opt4j.gatekeeper.FeatureExtractor;
import org.opt4j.gatekeeper.NeuralGatekeeper;

@Parent(NeuralGatekeeper.class)
@Icon(Icons.PUZZLE_BLUE)
@Info("Select the Feature Extractor that matches your problem's Genotype type.")
public class FeatureExtractorModule extends Opt4JModule {

    public enum ExtractorType {
        BOOLEAN,
        DOUBLE,
        INTEGER,
        COMPOSITE
    }

    @Order(1)
    @Info("The target Genotype used by the problem.")
    protected ExtractorType extractorType = ExtractorType.BOOLEAN;

    public ExtractorType getExtractorType() {
        return extractorType;
    }

    public void setExtractorType(ExtractorType extractorType) {
        this.extractorType = extractorType;
    }

    @Override
    protected void config() {
        switch (extractorType) {
            case BOOLEAN:
                bind(FeatureExtractor.class).to(BooleanFeatureExtractor.class).in(Singleton.class);
                break;
            case DOUBLE:
                bind(FeatureExtractor.class).to(DoubleFeatureExtractor.class).in(Singleton.class);
                break;
            case INTEGER:
                bind(FeatureExtractor.class).to(IntegerFeatureExtractor.class).in(Singleton.class);
                break;
            case COMPOSITE:
                bind(FeatureExtractor.class).to(CompositeFeatureExtractor.class).in(Singleton.class);
                break;
        }
    }
}
