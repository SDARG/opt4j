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

package org.opt4j.gatekeeper.djl;

import ai.djl.engine.Engine;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.logging.Logger;

/**
 * Initializes the DJL PyTorch engine.
 * <p>
 * This service is only instantiated if the {@link org.opt4j.gatekeeper.NeuralGatekeeperModule} is part of the
 * configuration. Opt4J itself does not touch DJL otherwise, so the native PyTorch libraries are loaded on demand:
 * On the first use, DJL downloads the matching native libraries (CPU or CUDA) into {@code ~/.djl.ai}. The cache
 * directory can be changed with the environment variable {@code DJL_CACHE_DIR}, the variant can be forced with
 * {@code PYTORCH_FLAVOR} (e.g. {@code cpu} or {@code cu118}).
 */
@Singleton
public class DjlInitializerService {

    private static final Logger log = Logger.getLogger(DjlInitializerService.class.getName());

    @Inject
    public DjlInitializerService() {
        // PyTorch thread limits
        System.setProperty("ai.djl.pytorch.num_threads", "1");
        System.setProperty("ai.djl.pytorch.num_interop_threads", "1");

        try {
            // Initialize the DJL Engine (downloads the native libraries on the first run)
            Engine.getInstance();
            log.info("SUCCESS: DJL Engine initialized natively.");
        } catch (Exception | UnsatisfiedLinkError e) {
            log.severe("FATAL: Failed to initialize the DJL PyTorch engine. On the first run, DJL downloads the native "
                    + "PyTorch libraries into ~/.djl.ai (or DJL_CACHE_DIR). Check the internet connection or provide "
                    + "the libraries offline by adding ai.djl.pytorch:pytorch-native-cpu (or a CUDA variant) and "
                    + "ai.djl.pytorch:pytorch-jni to the classpath.");
            throw new RuntimeException("Could not initialize DJL Engine.", e);
        }
    }
}
