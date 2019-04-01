package org.opt4j.benchmarks.monalisa;

/*******************************************************************************
 * Copyright (c) 2019 Opt4J
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

import org.opt4j.core.config.Icons;
import org.opt4j.core.config.annotations.Icon;
import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.config.annotations.Order;
import org.opt4j.core.problem.ProblemModule;
import org.opt4j.core.start.Constant;

/**
 * The {@link MonaLisaModule} is used for the configuration of the Mona Lisa
 * problem. It contains the image to resemble, the number of polygons as well as
 * how many different color are to be considered.
 * 
 * @author michaelhglass
 * 
 */
@Icon(Icons.PROBLEM)
@Info("The Mona Lisa problem as formulated in https://rogerjohansson.blog/2008/12/07/genetic-programming-evolution-of-mona-lisa/ which basically tries to resemble a given image by means of a number of semi-transparent colored polygons.")
public class MonaLisaModule extends ProblemModule {

	@Info("The width of the image.")
	@Order(0)
	@Constant(value = "width", namespace = MonaLisaProblem.class)
	protected int width = 200;

	@Info("The height of the image.")
	@Order(1)
	@Constant(value = "height", namespace = MonaLisaProblem.class)
	protected int height = 300;

	@Info("The number of polygons to be used.")
	@Order(1)
	@Constant(value = "numberOfPolygons", namespace = MonaLisaProblem.class)
	protected int numberOfPolygons = 50;

	@Info("The number of colors per color channel.")
	@Order(1)
	@Constant(value = "colorsPerChannel", namespace = MonaLisaProblem.class)
	protected int colorsPerChannel = 10;
	
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.start.Opt4JModule#config()
	 */
	@Override
	public void config() {

		bind(MonaLisaProblem.class).in(SINGLETON);

		bindProblem(MonaLisaCreatorDecoder.class, MonaLisaCreatorDecoder.class, MonaLisaEvaluator.class);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getNumberOfPolygons() {
		return numberOfPolygons;
	}

	public void setNumberOfPolygons(int numberOfPolygons) {
		this.numberOfPolygons = numberOfPolygons;
	}

	public int getColorsPerChannel() {
		return colorsPerChannel;
	}

	public void setColorsPerChannel(int colorsPerChannel) {
		this.colorsPerChannel = colorsPerChannel;
	}

}
