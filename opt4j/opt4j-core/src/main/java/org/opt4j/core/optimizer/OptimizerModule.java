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

package org.opt4j.core.optimizer;

import org.opt4j.core.config.Icons;
import org.opt4j.core.config.annotations.Category;
import org.opt4j.core.config.annotations.Icon;
import org.opt4j.core.start.Opt4JModule;

/**
 * Abstract module class for the {@link Optimizer} modules.
 * 
 * @author lukasiewycz
 * @see Optimizer
 * @see IterativeOptimizer
 * 
 */
@Icon(Icons.OPTIMIZER)
@Category
public abstract class OptimizerModule extends Opt4JModule {

	/**
	 * Binds the given {@link Optimizer}.
	 * 
	 * @param optimizer
	 *            the optimizer to bind
	 */
	protected void bindOptimizer(Class<? extends Optimizer> optimizer) {
		bind(Optimizer.class).to(optimizer).in(SINGLETON);
	}

	/**
	 * Binds the given {@link IterativeOptimizer}.
	 * 
	 * @param optimizer
	 *            the iterative optimizer to bind
	 */
	protected void bindIterativeOptimizer(Class<? extends IterativeOptimizer> optimizer) {
		bind(IterativeOptimizer.class).to(optimizer).in(SINGLETON);
	}

}
