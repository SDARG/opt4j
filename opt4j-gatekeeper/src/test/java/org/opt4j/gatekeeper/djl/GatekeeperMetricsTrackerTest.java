package org.opt4j.gatekeeper.djl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GatekeeperMetricsTrackerTest {

    @Test
    void testMetricsRecordingAndWriting(@TempDir Path tempDir) throws Exception {
        GatekeeperMetricsTracker tracker = new GatekeeperMetricsTracker();
        File tempFile = tempDir.resolve("accuracy.tsv").toFile();

        // Inject temp filename
        Field filenameField = GatekeeperMetricsTracker.class.getDeclaredField("accuracyFilename");
        filenameField.setAccessible(true);
        filenameField.set(tracker, tempFile.getAbsolutePath());

        // Record metrics: TP=10, TN=10, FP=5, FN=5 (Total=30, Accuracy=20/30=0.66...)
        tracker.recordBatchMetrics(10, 10, 5, 5, 100, 500);

        // Record empty batch to cover 0-division safety branch
        tracker.recordBatchMetrics(0, 0, 0, 0, 101, 501);

        tracker.close();

        List<String> lines = Files.readAllLines(tempFile.toPath());
        assertTrue(lines.size() >= 2);
        assertTrue(lines.get(0).contains("Accuracy"));

        // Assert the logged metrics for iteration 100 and evaluations 500
        String dataRow = lines.get(1);
        assertTrue(dataRow.contains("100"));
        assertTrue(dataRow.contains("500"));
        assertTrue(dataRow.contains("NeuralNet"));
    }
}
