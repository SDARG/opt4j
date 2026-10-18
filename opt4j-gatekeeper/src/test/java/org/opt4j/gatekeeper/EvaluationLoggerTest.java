package org.opt4j.gatekeeper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opt4j.core.Individual;
import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;
import org.opt4j.core.Value;
import org.opt4j.core.optimizer.Archive;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EvaluationLoggerTest {

    @Mock private Archive archive;
    @Mock private DominancePredictionManager manager;

    @Test
    void testLoggingLifecycle(@TempDir Path tempDir) throws Exception {
        File tempFile = tempDir.resolve("run.tsv").toFile();
        EvaluationLogger logger = new EvaluationLogger(archive, manager, tempFile.getAbsolutePath());

        Objective costObj = new Objective("cost", Objective.Sign.MIN);
        Objective powerObj = new Objective("power", Objective.Sign.MAX);

        Objectives obj = mock(Objectives.class);
        when(obj.getKeys()).thenReturn(Set.of(costObj, powerObj));

        // Mock Values: Cost gets 10.0, Power gets null
        Value<?> costValue = mock(Value.class);
        when(costValue.getDouble()).thenReturn(10.0);

        when(obj.get(costObj)).thenAnswer(inv -> costValue);
        when(obj.get(powerObj)).thenReturn(null);

        Individual ind = mock(Individual.class);
        when(ind.getObjectives()).thenReturn(obj);

        when(archive.isEmpty()).thenReturn(false);
        when(archive.iterator()).thenAnswer(inv -> List.of(ind).iterator());
        when(manager.getEvaluatedCount()).thenReturn(42);

        // Run
        logger.optimizationStarted(null);
        logger.iterationComplete(1); // Writes Header + 1 Row
        logger.optimizationStopped(null); // Closes file

        // Verify Output
        List<String> lines = Files.readAllLines(tempFile.toPath());
        assertTrue(lines.size() >= 2);

        String header = lines.get(0);
        assertTrue(header.contains("cost[MIN]"), "Header should contain cost");
        assertTrue(header.contains("power[MAX]"), "Header should contain power");

        String dataRow = lines.get(1);
        assertTrue(dataRow.contains("42"), "Data row should contain eval count");
        assertTrue(dataRow.contains("10.0"), "Data row should contain cost val");
        assertTrue(dataRow.contains("INFEASIBLE"), "Data row should contain INFEASIBLE");
    }
}
