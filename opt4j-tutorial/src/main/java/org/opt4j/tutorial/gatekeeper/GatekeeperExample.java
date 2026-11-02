package org.opt4j.tutorial.gatekeeper;

import org.opt4j.core.start.Opt4JTask;
import org.opt4j.gatekeeper.NeuralGatekeeperModule;
import org.opt4j.optimizers.ea.EvolutionaryAlgorithmModule;

public class GatekeeperExample {
    public static void main(String[] args) {
        // The base problem and optimizer
        ExpensiveMinOnesModule minOnes = new ExpensiveMinOnesModule();

        EvolutionaryAlgorithmModule ea = new EvolutionaryAlgorithmModule();
        ea.setGenerations(100);
        ea.setPopulationSize(100);

        // Configure the Gatekeeper (using default parameters)
        NeuralGatekeeperModule gatekeeper = new NeuralGatekeeperModule();

        // Run the Task
        Opt4JTask task = new Opt4JTask(false);
        task.init(ea, minOnes, gatekeeper);

        try {
            task.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            task.close();
        }
    }
}
