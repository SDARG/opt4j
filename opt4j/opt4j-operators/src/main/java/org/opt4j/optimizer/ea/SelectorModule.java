/**
 * Opt4J is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with Opt4J. If not, see http://www.gnu.org/licenses/. 
 */

package org.opt4j.optimizer.ea;

import org.opt4j.config.Icons;
import org.opt4j.config.annotations.Category;
import org.opt4j.config.annotations.Icon;
import org.opt4j.config.annotations.Parent;
import org.opt4j.core.optimizer.OptimizerModule;
import org.opt4j.start.Opt4JModule;

/**
 * Abstract module class for the {@link Selector}.
 * 
 * @author lukasiewycz
 * 
 */
@Icon(Icons.SELECTOR)
@Category
@Parent(OptimizerModule.class)
public abstract class SelectorModule extends Opt4JModule {

	/**
	 * Binds the given {@link Selector}.
	 * 
	 * @param selector
	 *            the selector to bind
	 */
	protected void bindSelector(Class<? extends Selector> selector) {
		bind(Selector.class).to(selector).in(SINGLETON);
	}
}
