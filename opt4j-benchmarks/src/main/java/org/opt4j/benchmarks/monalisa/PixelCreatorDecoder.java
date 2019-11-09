package org.opt4j.benchmarks.monalisa;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.opt4j.core.common.random.Rand;
import org.opt4j.core.genotype.IntegerGenotype;
import org.opt4j.core.problem.Creator;
import org.opt4j.core.problem.Decoder;

import com.google.inject.Inject;

public class PixelCreatorDecoder implements Creator<MonaLisaGenotype>, Decoder<MonaLisaGenotype, BufferedImage> {
	protected MonaLisaProblem problem;
	protected Rand random;

	@Inject
	public PixelCreatorDecoder(MonaLisaProblem problem, Rand random) {
		this.problem = problem;
		this.random = random;
	}

	@Override
	public BufferedImage decode(MonaLisaGenotype genotype) {
		BufferedImage image = new BufferedImage(problem.getWidth(), problem.getHeight(), BufferedImage.TYPE_INT_ARGB);

		IntegerGenotype reds = genotype.get(MonaLisaGenotype.REDS);
		IntegerGenotype greens = genotype.get(MonaLisaGenotype.GREENS);
		IntegerGenotype blues = genotype.get(MonaLisaGenotype.BLUES);

		int colorOffset = 255 / problem.getColorsPerChannel();

		// draw each polygon
		for (int x = 0; x < problem.getWidth(); x++) {
			for (int y = 0; y < problem.getHeight(); y++) {
				Color c = new Color(reds.get(x * y + x) * colorOffset, greens.get(x * y + x) * colorOffset,
						blues.get(x * y + x) * colorOffset);
				image.setRGB(x, y, c.getRGB());
			}
		}
		return image;
	}

	@Override
	public MonaLisaGenotype create() {
		MonaLisaGenotype genotype = new MonaLisaGenotype();

		IntegerGenotype reds = new IntegerGenotype(0, problem.getColorsPerChannel());
		reds.init(random, problem.getWidth() * problem.getHeight());
		genotype.put(MonaLisaGenotype.REDS, reds);

		IntegerGenotype greens = new IntegerGenotype(0, problem.getColorsPerChannel());
		greens.init(random, problem.getWidth() * problem.getHeight());
		genotype.put(MonaLisaGenotype.GREENS, greens);

		IntegerGenotype blues = new IntegerGenotype(0, problem.getColorsPerChannel());
		blues.init(random, problem.getWidth() * problem.getHeight());
		genotype.put(MonaLisaGenotype.BLUES, blues);

		return genotype;
	}

}
