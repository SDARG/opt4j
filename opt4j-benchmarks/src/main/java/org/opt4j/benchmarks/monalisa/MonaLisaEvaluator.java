package org.opt4j.benchmarks.monalisa;

import static org.opt4j.core.Objective.Sign.MIN;

import java.awt.image.BufferedImage;

import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;
import org.opt4j.core.problem.Evaluator;

import com.google.inject.Inject;

public class MonaLisaEvaluator implements Evaluator<BufferedImage> {
	int width;
	int height;
	BufferedImage monaLisa;
	int[] colorsML;
	protected final Objective differenceObjective = new Objective("difference", MIN);
	private MonaLisaProblem problem;

	@Inject
	public MonaLisaEvaluator(MonaLisaProblem problem) {
		this.problem = problem;

	}

	@Inject
	public void init() {
		width = problem.getWidth();
		height = problem.getHeight();
		monaLisa = problem.getMonaLisa();

		colorsML = new int[height * width * 4];

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				synchronized (monaLisa) {
					int colorML = monaLisa.getRGB(x, y);
					colorsML[x * y * 4 + y * 4 + 0] = colorML & 0xff;
					colorsML[x * y * 4 + y * 4 + 1] = (colorML & 0xff00) >> 8;
					colorsML[x * y * 4 + y * 4 + 2] = (colorML & 0xff0000) >> 16;
					colorsML[x * y * 4 + y * 4 + 3] = (colorML & 0xff000000) >>> 24;
				}
			}
		}
	}

	@Override
	public Objectives evaluate(BufferedImage phenotype) {
		Objectives objectives = new Objectives();

		double difference = 0;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int color = phenotype.getRGB(x, y);
				int[] colors = new int[4];
				colors[0] = color & 0xff;
				colors[1] = (color & 0xff00) >> 8;
				colors[2] = (color & 0xff0000) >> 16;
				colors[3] = (color & 0xff000000) >>> 24;

				difference += Math.abs(colorsML[x * y * 4 + y * 4 + 0] - colors[0]);
				difference += Math.abs(colorsML[x * y * 4 + y * 4 + 1] - colors[1]);
				difference += Math.abs(colorsML[x * y * 4 + y * 4 + 2] - colors[2]);
				difference += Math.abs(colorsML[x * y * 4 + y * 4 + 3] - colors[3]);
			}
		}

		objectives.add(differenceObjective, difference);
		return objectives;

	}

}
