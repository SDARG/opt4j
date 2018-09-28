module org.opt4j.operators {
	exports org.opt4j.operators;
	exports org.opt4j.operators.algebra;
	exports org.opt4j.operators.copy;
	exports org.opt4j.operators.crossover;
	exports org.opt4j.operators.diversity;
	exports org.opt4j.operators.mutate;
	exports org.opt4j.operators.neighbor;
	exports org.opt4j.operators.normalize;
	
    requires org.opt4j.core;
    requires com.google.guice;
}
