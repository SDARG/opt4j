/**
 * Opt4J is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Opt4J. If not, see http://www.gnu.org/licenses/.
 */
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
 * The {@link KnapsackProblemFile} is a {@link KnapsackProblem} that is
 * initialized by a file. The format can be found on the referenced website.
 * 
 * @see <a
 *      href="http://www.tik.ee.ethz.ch/sop/download/supplementary/testProblemSuite/">http://www.tik.ee.ethz.ch/sop/download/supplementary/testProblemSuite/</a>
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
		return new Integer(matcher.group());
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
		return new Double(matcher.group());
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
