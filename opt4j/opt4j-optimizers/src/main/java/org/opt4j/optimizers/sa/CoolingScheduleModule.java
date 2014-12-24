/*******************************************************************************
 * Copyright (c) 2014 Opt4J
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *******************************************************************************/
 

package org.opt4j.optimizers.sa;

import org.opt4j.core.config.Icons;
import org.opt4j.core.config.annotations.Category;
import org.opt4j.core.config.annotations.Icon;
import org.opt4j.core.config.annotations.Parent;
import org.opt4j.core.start.Opt4JModule;

/**
 * Abstract class for modules that bind to the {@link CoolingSchedule}
 * interface.
 * 
 * @author lukasiewycz
 * 
 */
@Icon(Icons.SELECTOR)
@Category
@Parent(SimulatedAnnealingModule.class)
public abstract class CoolingScheduleModule extends Opt4JModule {

	/**
	 * Binds the given {@link CoolingSchedule}.
	 * 
	 * @param coolingSchedule
	 *            the CoolingSchedule to bind
	 */
	protected void bindCoolingSchedule(Class<? extends CoolingSchedule> coolingSchedule) {
		bind(CoolingSchedule.class).to(coolingSchedule).in(SINGLETON);
	}

}
