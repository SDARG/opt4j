package org.opt4j.benchmarks.monalisa;

import static org.opt4j.core.Objective.Sign.MIN;

import java.awt.image.BufferedImage;

import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;
import org.opt4j.core.problem.Evaluator;

import com.google.inject.Inject;

public class MonaLisaEvaluator implements Evaluator<BufferedImage> {

	protected final MonaLisaProblem problem;

	protected final Objective differenceObjective = new Objective("difference", MIN);

	@Inject
	public MonaLisaEvaluator(MonaLisaProblem problem) {
		this.problem = problem;
	}

	@Override
	public Objectives evaluate(BufferedImage phenotype) {
		Objectives objectives = new Objectives();
		BufferedImage monaLisa = problem.getMonaLisa();
		int width = problem.getWidth();
		int height = problem.getHeight();

		double difference = 0;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int colorML = monaLisa.getRGB(x, y);
				int[] colorsML = new int[4];
				colorsML[0] = colorML & 0xff;
				colorsML[1] = (colorML & 0xff00) >> 8;
				colorsML[2] = (colorML & 0xff0000) >> 16;
				colorsML[3] = (colorML & 0xff000000) >>> 24;

				int color = phenotype.getRGB(x, y);
				int[] colors = new int[4];
				colors[0] = color & 0xff;
				colors[1] = (color & 0xff00) >> 8;
				colors[2] = (color & 0xff0000) >> 16;
				colors[3] = (color & 0xff000000) >>> 24;

				for (int k = 0; k < 4; k++) {
					difference += Math.abs(colorsML[k] - colors[k]);
				}
			}
		}

		objectives.add(differenceObjective, difference);
		return objectives;

	}

}
