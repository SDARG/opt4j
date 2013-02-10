package org.opt4j.tutorial;

import org.opt4j.genotype.BooleanGenotype;
import org.opt4j.operator.crossover.Crossover;
import org.opt4j.operator.crossover.Pair;

// By implementing CrossoverBoolean an operator is created which performs a crossover
// operation (as used by, e.g., EAs) on the BooleanGenotype.
public class MyOperator implements Crossover<BooleanGenotype> {

	// method defined by CrossoverBoolean
	@Override
	public Pair<BooleanGenotype> crossover(BooleanGenotype parent1, BooleanGenotype parent2) {
		// Instantiate genotypes of the same class as the parents. Each
		// Genotype has the newInstance() method.
		BooleanGenotype g1 = parent1.newInstance();
		BooleanGenotype g2 = parent1.newInstance();

		assert parent1.size() == parent2.size() : "problem size differs";

		for (int i = 0; i < parent1.size(); i++) {
			// replace this with your operator logic
			g1.add(i, parent1.get(i) || parent2.get(i));
			g2.add(i, parent1.get(i) && parent2.get(i));
		}

		// The crossover operator returns two new genotypes based on the two
		// parents.
		return new Pair<BooleanGenotype>(g1, g2);
	}
}
