package org.opt4j.tutorial;

import java.util.Random;

import org.opt4j.core.problem.Creator;
import org.opt4j.genotype.SelectGenotype;

public class HelloWorldCreator implements Creator<SelectGenotype<Character>> {

	Character[] ALPHABET = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
			'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ' ' };
	Random random = new Random();

	@Override
	public SelectGenotype<Character> create() {
		SelectGenotype<Character> genotype = new SelectGenotype<Character>(ALPHABET);
		genotype.init(random, 11);
		return genotype;
	}

}
