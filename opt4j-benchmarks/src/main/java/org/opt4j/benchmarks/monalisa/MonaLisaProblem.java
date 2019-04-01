package org.opt4j.benchmarks.monalisa;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.opt4j.core.start.Constant;

import com.google.inject.Inject;

public class MonaLisaProblem {

	protected int width;
	protected int height;
	protected int numberOfPolygons;
	protected int colorsPerChannel;
	protected int verticesPerPolygon = 3;

	public int getVerticesPerPolygon() {
		return verticesPerPolygon;
	}

	protected BufferedImage monaLisa;

	@Inject
	public MonaLisaProblem(@Constant(value = "width", namespace = MonaLisaProblem.class) int width,
			@Constant(value = "height", namespace = MonaLisaProblem.class) int height,
			@Constant(value = "numberOfPolygons", namespace = MonaLisaProblem.class) int numberOfPolygons,
			@Constant(value = "colorsPerChannel", namespace = MonaLisaProblem.class) int colorsPerChannel) {
		this.width = width;
		this.height = height;
		this.numberOfPolygons = numberOfPolygons;
		this.colorsPerChannel = colorsPerChannel;

		initialize();

	}

	private void initialize() {
		File img = new File("./opt4j-benchmarks/images/monaLisa.jpg");
		monaLisa = null;
		try {
			monaLisa = ImageIO.read(img);
		} catch (IOException e) {
			e.printStackTrace();
		}

		JFrame mainMap = new JFrame();
		mainMap.setResizable(false);

		mainMap.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel p = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				File img = new File("./opt4j-benchmarks/images/monaLisa.jpg");
				monaLisa = null;
				try {
					monaLisa = ImageIO.read(img);
				} catch (IOException e) {
					e.printStackTrace();
				}
				g.drawImage(monaLisa, 0, 0, null);
			}

			@Override
			public Dimension getPreferredSize() {
				return new Dimension(200, 300);
			}
		};

		mainMap.add(p);
		mainMap.pack();
		mainMap.setVisible(true);

	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getNumberOfPolygons() {
		return numberOfPolygons;
	}

	public int getColorsPerChannel() {
		return colorsPerChannel;
	}

	public BufferedImage getMonaLisa() {
		return monaLisa;
	}

}
