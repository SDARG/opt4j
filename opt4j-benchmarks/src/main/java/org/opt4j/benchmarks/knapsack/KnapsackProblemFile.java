/*******************************************************************************
 * Copyright (c) 2014 Opt4J
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/

package org.opt4j.benchmarks.knapsack;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.opt4j.core.start.Constant;

import com.google.inject.Inject;

/**
 * 
 * The {@link KnapsackProblemFile} is a {@link KnapsackProblem} that is initialized by a file. The format can be found
 * on the referenced website.
 * 
 * @see <a href=
 *      "http://www.tik.ee.ethz.ch/sop/download/supplementary/testProblemSuite/">http://www.tik.ee.ethz.ch/sop/download/supplementary/testProblemSuite/</a>
 * 
 * @author lukasiewycz
 * 
 */
public class KnapsackProblemFile implements KnapsackProblem {

	protected static final String INT = "[+-]?\\d+";
	protected static final String DOUBLE = "[+-]?\\d+(\\.\\d*)?";
	protected static final String BLANK = "[\\p{Blank}]*";
	protected static final String POSINT = "[-]?\\d+"; // skip +
	protected static final String POSDOUBLE = "[-]?\\d+(\\.\\d*)?"; // skip +

	protected final List<Knapsack> knapsacks = new ArrayList<Knapsack>();
	protected final List<Item> items = new ArrayList<Item>();

	/**
	 * Constructs a {@link KnapsackProblemFile} with a given file name.
	 * 
	 * @param filename
	 *            the file name
	 */
	@Inject
	public KnapsackProblemFile(@Constant(value = "filename", namespace = KnapsackProblemFile.class) String filename) {
		FileReader fileReader;
		try {
			fileReader = new FileReader(filename);
		} catch (FileNotFoundException e1) {
			throw new IllegalArgumentException("Knapsack problem file not found " + filename, e1);
		}

		BufferedReader reader = new BufferedReader(fileReader);
		try {

			String line;

			Knapsack knapsack = null;
			Item item = null;

			while ((line = reader.readLine()) != null) {

				if (line.matches(BLANK + "knapsack" + BLANK + INT + BLANK + ":" + BLANK)) {
					knapsack = new Knapsack(getInt(line));
					knapsacks.add(knapsack);
				} else if (knapsack == null) {
					throw new IllegalStateException("knapsack not initialized." + filename);
				} else if (line.matches(BLANK + "capacity:" + BLANK + DOUBLE + BLANK)) {
					double capacity = getDouble(line);
					knapsack.setCapacity(capacity);
				} else if (line.matches(BLANK + "item" + BLANK + INT + BLANK + ":" + BLANK)) {
					int i = getInt(line);
					if (items.size() < i) {
						item = new Item(getInt(line));
						items.add(i - 1, item);
					} else {
						item = items.get(i - 1);
					}
				} else if (line.matches(BLANK + "weight:" + BLANK + DOUBLE + BLANK)) {
					int weight = getInt(line);
					knapsack.setWeight(item, weight);
				} else if (line.matches(BLANK + "profit:" + BLANK + DOUBLE + BLANK)) {
					int profit = getInt(line);
					knapsack.setProfit(item, profit);
				}
			}
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * Get the integer value in the line.
	 * 
	 * @param line
	 *            the line
	 * @return the integer value
	 */
	protected int getInt(String line) {
		Matcher matcher = Pattern.compile(POSINT).matcher(line);
		matcher.find();
		return Integer.parseInt(matcher.group());
	}

	/**
	 * Get the double value in the line.
	 * 
	 * @param line
	 *            the line
	 * @return the double value
	 */
	protected double getDouble(String line) {
		Matcher matcher = Pattern.compile(POSDOUBLE).matcher(line);
		matcher.find();
		return Double.parseDouble(matcher.group());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.benchmark.knapsack.KnapsackProblem#getKnapsacks()
	 */
	@Override
	public Collection<Knapsack> getKnapsacks() {
		return knapsacks;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.benchmark.knapsack.KnapsackProblem#getItems()
	 */
	@Override
	public Collection<Item> getItems() {
		return items;
	}

}
