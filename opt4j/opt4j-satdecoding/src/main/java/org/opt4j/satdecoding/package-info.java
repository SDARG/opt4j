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

/**
 * <p>
 * Provides classes for using a PB solver as
 * {@link org.opt4j.core.problem.Decoder}.
 * </p>
 * <p>
 * Many optimization problems with a discrete search space consist of binary
 * variables. However, in some cases not all representations of these variables
 * are <em>feasible</em> . Common approaches deteriorate the objectives if the
 * solution is not feasible. As a matter of fact, in case that the number of
 * feasible solutions is much lower than the number of infeasible solutions, the
 * optimization process is more focused on the search of feasible solutions than
 * optimizing the objectives.
 * </p>
 * <p>
 * This package includes a specialized {@link org.opt4j.core.problem.Decoder}
 * that gives the user the opportunity to define constraints that have to be
 * fulfilled to obtain a feasible solution.
 * </p>
 * <p>
 * As an example for the <em>SAT Decoding</em> we will use the following
 * example: Given is a vector with a fixed number of binary (0/1) variables. The
 * objective is to minimize the number of 1s of this vector. The solution of
 * this problem is quite simple: Set all variables to 0. However, in order to
 * make this problem more difficult, we introduce the following condition: A
 * solution is only considered feasible if the variables satisfy a set of
 * <em>constraints</em> . The following example is used to illustrate this
 * problem:
 * 
 * <pre>
 * minimize w + x + y + z
 * subject to:
 *     w + x + y > 0    (constraint 1)
 *     y + z > 0        (constraint 2)
 *     w + z > 0        (constraint 3)
 *     x + y + z > 0    (constraint 4)
 * </pre>
 * 
 * Solution {@code (w=1,x=1,y=0,z=1)} is feasible which fulfills all constraints
 * and the objective is 3. On the other hand, for {@code (w=0,x=0,y=0,z=0)}, the
 * objective is 0 but this solution is not feasible and therefore invalid. A
 * good solution that fulfills all constraints would be
 * {@code (w=0,x=1,y=0,z=1)} with the objective value 2.
 * </p>
 * <h2>Example</h2>
 * <p>
 * In the following, an example that minimizes the ones of a random is outlined.
 * First, the phenotype and {@link org.opt4j.core.problem.Evaluator} are
 * defined.
 * 
 * <pre>
 * public class MinOnesResult extends ArrayList&lt;Boolean&gt; {
 * }
 * </pre>
 * 
 * <pre>
 * public class MinOnesEvaluator implements Evaluator&lt;MinOnesResult&gt; {
 * 
 * 	public Objectives evaluate(MinOnesResult minOnesResult) {
 * 
 * 		int value = 0;
 * 		for (Boolean v : minOnesResult) {
 * 			if (v != null &amp;&amp; v) {
 * 				value++;
 * 			}
 * 		}
 * 
 * 		Objectives objectives = new Objectives();
 * 		objectives.add(&quot;ones&quot;, Sign.MIN, value);
 * 		return objectives;
 * 	}
 * }
 * </pre>
 * 
 * The {@link org.opt4j.core.problem.Decoder} is defined as follows:
 * 
 * <pre>
 * public class MinOnesDecoder extends AbstractSATDecoder&lt;Genotype, MinOnesResult&gt; {
 * 
 * 	&#064;Inject
 * 	public MinOnesDecoder(SATManager satManager, Rand random) {
 * 		super(satManager, random);
 * 	}
 * 
 * 	// Here you can set the constraints of your problem. In our case, we will
 * 	// randomly generate a problem as a 3SAT problem (3 literals per clause)
 * 	// with 1000 variables and 1000 clauses. This problem is known to be
 * 	// NP-complete. However, we hope that there exists at least one feasible
 * 	// solution (and with the seed 0 of random it does).
 * 	public Set&lt;Constraint&gt; createConstraints() {
 * 		Set&lt;Constraint&gt; constraints = new HashSet&lt;Constraint&gt;();
 * 		Random random = new Random(0);
 * 
 * 		for (int i = 0; i &lt; 1000; i++) {
 * 			Constraint clause = new Constraint(&quot;&gt;=&quot;, 1);
 * 			HashSet&lt;Integer&gt; vars = new HashSet&lt;Integer&gt;();
 * 			do {
 * 				vars.add(random.nextInt(1000));
 * 			} while (vars.size() &lt; 3);
 * 
 * 			for (int n : vars) {
 * 				clause.add(new Literal(n, random.nextBoolean()));
 * 			}
 * 
 * 			constraints.add(clause);
 * 		}
 * 
 * 		return constraints;
 * 	}
 * 
 * 	public MinOnesResult convertModel(Model model) {
 * 		MinOnesResult minOnesResult = new MinOnesResult();
 * 
 * 		for (int i = 0; i &lt; 1000; i++) {
 * 			minOnesResult.add(model.get(i));
 * 		}
 * 
 * 		return minOnesResult;
 * 	}
 * }
 * </pre>
 * 
 * Finally, the problem specific module is defined as follows:
 * 
 * <pre>
 * public class MinOnesModule extends ProblemModule {
 * 	public void config() {
 * 		bindProblem(MinOnesDecoder.class, MinOnesDecoder.class, MinOnesEvaluator.class);
 * 	}
 * }
 * </pre>
 * 
 * </p>
 */
package org.opt4j.satdecoding;

