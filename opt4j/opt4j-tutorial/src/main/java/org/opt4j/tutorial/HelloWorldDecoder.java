package org.opt4j.tutorial;

import org.opt4j.core.problem.Decoder;
import org.opt4j.genotype.SelectGenotype;

public class HelloWorldDecoder implements Decoder<SelectGenotype<Character>, String> {

	@Override
	public String decode(SelectGenotype<Character> genotype) {
		String phenotype = "";
		for (int i = 0; i < genotype.size(); i++) {
			phenotype += genotype.getValue(i);
		}
		return phenotype;
	}

}
