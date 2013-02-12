package org.opt4j.tutorial.helloworld;

import org.opt4j.core.problem.Decoder;
import org.opt4j.core.problem.PhenotypeWrapper;
import org.opt4j.genotype.SelectGenotype;

public class HelloWorldDecoder implements Decoder<SelectGenotype<Character>, PhenotypeWrapper<String>> {

	@Override
	public PhenotypeWrapper<String> decode(SelectGenotype<Character> genotype) {
		String s = "";
		for (int i = 0; i < genotype.size(); i++) {
			s += genotype.getValue(i);
		}
		return new PhenotypeWrapper<String>(s);
	}

}
