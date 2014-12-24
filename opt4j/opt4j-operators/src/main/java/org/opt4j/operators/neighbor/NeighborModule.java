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
 

package org.opt4j.operators.neighbor;

import org.opt4j.core.config.Icons;
import org.opt4j.core.config.annotations.Icon;
import org.opt4j.operators.OperatorModule;
import com.google.inject.TypeLiteral;

/**
 * The {@link NeighborModule} is used for modules for the {@link Neighbor}
 * operator.
 * 
 * A new {@link Neighbor} operator can be registered by implementing this module
 * and adding the operator with {@link #addOperator}.
 * 
 * @author lukasiewycz
 * @see Neighbor
 * 
 */
@Icon(Icons.OPERATOR)
public abstract class NeighborModule extends OperatorModule<Neighbor<?>> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.operator.OperatorModule#getOperatorTypeLiteral()
	 */
	@Override
	protected TypeLiteral<Neighbor<?>> getOperatorTypeLiteral() {
		return new TypeLiteral<Neighbor<?>>() {
		};
	}
}
