package org.opt4j.gatekeeper.djl;

import com.google.inject.Provider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opt4j.gatekeeper.DominancePredictionManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DjlDominancePredictorTest {

    @Mock private GatekeeperMetricsTracker metricsTracker;
    @Mock private Provider<DominancePredictionManager> dominanceManagerProvider;
    @Mock private DominancePredictionManager dominanceManager;
    @Mock private DjlInitializerService initService;

    private DjlDominancePredictor predictor;

    @BeforeEach
    void setUp() {
        // Setup Provider to return our mock manager
        lenient().when(dominanceManagerProvider.get()).thenReturn(dominanceManager);
        lenient().when(dominanceManager.getCurrentIteration()).thenReturn(5);
        lenient().when(dominanceManager.getEvaluatedCount()).thenReturn(50);

        // Instantiate the real predictor
        predictor = new DjlDominancePredictor(
                metricsTracker,
                dominanceManagerProvider,
                initService,
                1,      // hiddenLayerCount
                16,     // hiddenLayerSize
                0.001,  // learningRate
                0.8,    // predictionThreshold
                1.0,    // falsePositivePenalty
                2,      // batchSize
                false   // useGpu
        );
    }

    @AfterEach
    void tearDown() {
        if (predictor != null) {
            predictor.close();
        }
    }

    @Test
    void testPredictIsDominated_SmokeTest_ShapeCompatibility() {
        float[] newFeatures = { 0.1f, 0.2f, 0.3f, 0.4f, 0.5f };
        List<float[]> archiveFeaturesList = List.of(
                new float[]{ 0.9f, 0.8f, 0.7f, 0.6f, 0.5f },
                new float[]{ 1.0f, 0.0f, 1.0f, 0.0f, 1.0f }
        );

        // If shapes mismatch, DJL throws an exception
        // assertDoesNotThrow proves the dynamic lazy-initialization and tensor manipulation works
        assertDoesNotThrow(() -> {
            boolean isDominated = predictor.predictIsDominated(newFeatures, archiveFeaturesList);
        });
    }

    @Test
    void testTrain_SmokeTest_BackpropagationCompletesSuccessfully() {
        // Batch of 2 training samples. Length = 10 features
        // [ArchiveFeatures (5), NewFeatures (5)]
        float[] pair1 = { 1f, 1f, 1f, 1f, 1f,  0f, 0f, 0f, 0f, 0f };
        float[] pair2 = { 0f, 0f, 0f, 0f, 0f,  1f, 1f, 1f, 1f, 1f };

        List<float[]> combinedFeatures = List.of(pair1, pair2);
        List<Float> labels = List.of(1.0f, 0.0f); // 1 = Dominated, 0 = Not Dominated

        // Execute training step
        assertDoesNotThrow(() -> predictor.train(combinedFeatures, labels));

        // Ensure metrics tracker was updated
        // Validates that TP, TN, FP, FN were calculated from the logits
        verify(metricsTracker, times(1)).recordBatchMetrics(
                anyLong(), anyLong(), anyLong(), anyLong(), anyInt(), anyInt()
        );
    }

    @Test
    void testPredictIsDominated_ReturnsFalseWhenArchiveIsEmpty() {
        // Edge Case: If the archive is empty, a new individual cannot possibly be dominated.
        // Needs to return false without attempting tensor math on empty arrays.
        float[] newFeatures = { 1.0f, 2.0f };
        List<float[]> emptyArchive = List.of();

        boolean result = predictor.predictIsDominated(newFeatures, emptyArchive);

        assertFalse(result, "Should immediately return false if archive is empty");
    }
}
