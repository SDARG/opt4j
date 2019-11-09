package org.opt4j.benchmarks.monalisa;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

import org.opt4j.core.common.random.Rand;
import org.opt4j.core.genotype.IntegerGenotype;
import org.opt4j.core.problem.Creator;
import org.opt4j.core.problem.Decoder;

import com.google.inject.Inject;

public class MonaLisaCreatorDecoder implements Creator<MonaLisaGenotype>, Decoder<MonaLisaGenotype, BufferedImage> {

	protected MonaLisaProblem problem;
	protected Rand random;

	@Inject
	public MonaLisaCreatorDecoder(MonaLisaProblem problem, Rand random) {
		this.problem = problem;
		this.random = random;
	}

	@Override
	public MonaLisaGenotype create() {
		MonaLisaGenotype genotype = new MonaLisaGenotype();
		IntegerGenotype xPositions = new IntegerGenotype(0, problem.getWidth() - 1);
		xPositions.init(random, problem.getNumberOfPolygons() * problem.getVerticesPerPolygon());
		genotype.put(MonaLisaGenotype.XPOSITIONS, xPositions);

		IntegerGenotype yPositions = new IntegerGenotype(0, problem.getHeight() - 1);
		yPositions.init(random, problem.getNumberOfPolygons() * problem.getVerticesPerPolygon());
		genotype.put(MonaLisaGenotype.YPOSITIONS, yPositions);

		IntegerGenotype reds = new IntegerGenotype(0, problem.getColorsPerChannel());
		reds.init(random, problem.getNumberOfPolygons() + 1);
		genotype.put(MonaLisaGenotype.REDS, reds);

		IntegerGenotype greens = new IntegerGenotype(0, problem.getColorsPerChannel());
		greens.init(random, problem.getNumberOfPolygons() + 1);
		genotype.put(MonaLisaGenotype.GREENS, greens);

		IntegerGenotype blues = new IntegerGenotype(0, problem.getColorsPerChannel());
		blues.init(random, problem.getNumberOfPolygons() + 1);
		genotype.put(MonaLisaGenotype.BLUES, blues);

		IntegerGenotype alphas = new IntegerGenotype(0, problem.getColorsPerChannel());
		alphas.init(random, problem.getNumberOfPolygons());
		genotype.put(MonaLisaGenotype.ALPHAS, alphas);

		return genotype;
	}

	@Override
	public BufferedImage decode(MonaLisaGenotype genotype) {
		BufferedImage image = new BufferedImage(problem.getWidth(), problem.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D canvas = (Graphics2D) image.getGraphics();

		IntegerGenotype xPositions = genotype.get(MonaLisaGenotype.XPOSITIONS);
		IntegerGenotype yPositions = genotype.get(MonaLisaGenotype.YPOSITIONS);
		IntegerGenotype reds = genotype.get(MonaLisaGenotype.REDS);
		IntegerGenotype greens = genotype.get(MonaLisaGenotype.GREENS);
		IntegerGenotype blues = genotype.get(MonaLisaGenotype.BLUES);
		IntegerGenotype alphas = genotype.get(MonaLisaGenotype.ALPHAS);

		int polygons = problem.getNumberOfPolygons();
		int vertices = problem.getVerticesPerPolygon();

		int colorOffset = 255 / problem.getColorsPerChannel();

		canvas.setBackground(new Color(reds.get(polygons) * colorOffset, greens.get(polygons) * colorOffset,
				blues.get(polygons) * colorOffset));
		canvas.clearRect(0, 0, problem.getWidth(), problem.getHeight());

		// draw each polygon
		for (int i = 0; i < polygons; i++) {
			Polygon polygon = new Polygon();
			int index = i * vertices;
			for (int offset = 0; offset < vertices; offset++) {
				polygon.addPoint(xPositions.get(index + offset), yPositions.get(index + offset));
			}
			canvas.setColor(new Color(reds.get(i) * colorOffset, greens.get(i) * colorOffset,
					blues.get(i) * colorOffset, alphas.get(i) * colorOffset));
			canvas.fillPolygon(polygon);
		}

		return image;
	}

}
