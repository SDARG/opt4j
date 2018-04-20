package org.opt4j.tutorial.helloworld;

import java.util.Random;

import org.opt4j.core.genotype.SelectGenotype;
import org.opt4j.core.problem.Creator;

public class HelloWorldCreator implements Creator<SelectGenotype<Character>> {

	protected Character[] ALPHABET = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
			'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ' ' };
	protected Random random = new Random();

	public SelectGenotype<Character> create() {
		SelectGenotype<Character> genotype = new SelectGenotype<Character>(ALPHABET);
		genotype.init(random, 11);
		return genotype;
	}

}
