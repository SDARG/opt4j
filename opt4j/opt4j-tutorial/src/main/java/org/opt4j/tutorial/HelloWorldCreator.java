package org.opt4j.tutorial;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.opt4j.core.problem.Creator;
import org.opt4j.genotype.SelectGenotype;

public class HelloWorldCreator implements Creator<SelectGenotype<Character>> {

	List<Character> chars = new ArrayList<Character>();
	{
		for (char c = 'A'; c <= 'Z'; c++) {
			chars.add(c);
		}
		chars.add(' ');
	}
	Random random = new Random();

	@Override
	public SelectGenotype<Character> create() {
		SelectGenotype<Character> genotype = new SelectGenotype<Character>(chars);
		genotype.init(random, 11);
		return genotype;
	}

}
