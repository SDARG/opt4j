package ptolemy.plot.zoomBox;

import static ptolemy.plot.zoomBox.ZoomIcons.ZOOMiN;
import static ptolemy.plot.zoomBox.ZoomIcons.ZOOMoUT;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

import ptolemy.plot.PlotBox;

/**
 * The {@code ZoomRectangle} visualizes the zoom region, that the user is
 * selecting.
 * 
 * @author reimann
 */
public class ZoomRectangle {
	private boolean valid = false;
	private int x1, x2, y1, y2;
	private boolean showFancyIcon = true;

	private final PlotBox plotBox;

	private final int signLength = 6;
	private final int signMiddle = signLength / 2;
	private final int signBorder = 3;

	/**
	 * A stroke of width 1.
	 */
	private static final BasicStroke lineStroke1 = new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND);

	/**
	 * Creates a {@code ZoomRectangle}.
	 * 
	 * @param plotBox
	 *            the corresponding plotBox
	 */
	public ZoomRectangle(PlotBox plotBox) {
		this(plotBox, 0, 0);
	}

	/**
	 * Creates a {@code ZoomRectangle} with a given starting point.
	 * 
	 * @param plotBox
	 *            the corresponding plotBox
	 * @param x1
	 *            the x value of the starting point
	 * @param y1
	 *            the y value of the starting point
	 */
	public ZoomRectangle(PlotBox plotBox, int x1, int y1) {
		this.plotBox = plotBox;

		if (x1 > plotBox.get_lrx()) {
			x1 = plotBox.get_lrx();
		}

		if (x1 < plotBox.get_ulx()) {
			x1 = plotBox.get_ulx();
		}
		this.x1 = x1;

		if (y1 > plotBox.get_lry()) {
			y1 = plotBox.get_lry();
		}

		if (y1 < plotBox.get_uly()) {
			y1 = plotBox.get_uly();
		}
		this.y1 = y1;
	}

	/**
	 * The User zooms in, if the end point is right and below the starting
	 * point.
	 * 
	 * @return true if user zooms in
	 */
	public boolean isZoomingIn() {
		return x2 > x1 && y2 > y1;
	}

	/**
	 * The User zooms out, if the end point is left and above the starting
	 * point.
	 * 
	 * @return true if user zooms out
	 */
	public boolean isZoomingOut() {
		return x2 < x1 && y2 < y1;
	}

	/**
	 * The x value of the starting point.
	 * 
	 * @return the x1
	 */
	public int getX1() {
		return x1;
	}

	/**
	 * Sets the x value of the starting point.
	 * 
	 * @param x1
	 *            the x1 to set
	 */
	public void setX1(int x1) {
		if (x1 > plotBox.get_lrx()) {
			x1 = plotBox.get_lrx();
		}

		if (x1 < plotBox.get_ulx()) {
			x1 = plotBox.get_ulx();
		}
		this.x1 = x1;
	}

	/**
	 * Updates the end point of the zoom box.
	 * 
	 * @param x2
	 *            the x value of the end point
	 * @param y2
	 *            the y value of the end point
	 */
	public void setEnd(int x2, int y2) {
		setX2(x2);
		setY2(y2);
		setValid();
	}

	/**
	 * The x value of the end point.
	 * 
	 * @return the x2
	 */
	public int getX2() {
		return x2;
	}

	/**
	 * Sets the x value of the end point.
	 * 
	 * @param x2
	 *            the x2 to set
	 */
	public void setX2(int x2) {
		if (x2 > plotBox.get_lrx()) {
			x2 = plotBox.get_lrx();
		}

		if (x2 < plotBox.get_ulx()) {
			x2 = plotBox.get_ulx();
		}
		this.x2 = x2;
	}

	/**
	 * The y value of the start point.
	 * 
	 * @return the y1
	 */
	public int getY1() {
		return y1;
	}

	/**
	 * Sets the y value of the start point.
	 * 
	 * @param y1
	 *            the y1 to set
	 */
	public void setY1(int y1) {
		if (y1 > plotBox.get_lry()) {
			y1 = plotBox.get_lry();
		}

		if (y1 < plotBox.get_uly()) {
			y1 = plotBox.get_uly();
		}
		this.y1 = y1;
	}

	/**
	 * The y value of the end point.
	 * 
	 * @return the y2
	 */
	public int getY2() {
		return y2;
	}

	/**
	 * Sets the y value of the end point.
	 * 
	 * @param y2
	 *            the y2 to set
	 */
	public void setY2(int y2) {
		if (y2 > plotBox.get_lry()) {
			y2 = plotBox.get_lry();
		}

		if (y2 < plotBox.get_uly()) {
			y2 = plotBox.get_uly();
		}
		this.y2 = y2;
	}

	/**
	 * Sets this zoom box to valid, i.e. an end point is set.
	 */
	protected void setValid() {
		valid = true;
	}

	/**
	 * This zoom box is valid if an end point is set.
	 * 
	 * @return the valid
	 */
	public boolean isValid() {
		return valid;
	}

	/**
	 * Draws the {@code ZoomRectangle}.
	 * 
	 * @param component
	 *            the component to be used as the observer if this icon has no
	 *            image observer
	 * @param graphics
	 *            the graphics context
	 */
	public void paintZoomRectangle(Component component, Graphics graphics) {
		if (isValid()) {
			Color save = graphics.getColor();
			graphics.setColor(Color.DARK_GRAY);

			if (graphics instanceof Graphics2D) {
				((Graphics2D) graphics).setStroke(lineStroke1);
			}

			if (isZoomingIn()) {
				paintZoomingInRectangle(component, graphics);
			} else if (isZoomingOut()) {
				paintZoomingOutRectangle(component, graphics);
			}

			graphics.setColor(save);
		}
	}

	/**
	 * Draws the zoom in rectangle.
	 * 
	 * @param component
	 *            the component to be used as the observer if this icon has no
	 *            image observer
	 * @param graphics
	 *            the graphics context
	 */
	protected void paintZoomingInRectangle(Component component, Graphics graphics) {
		// draw box
		graphics.drawRect(getX1(), getY1(), getX2() - getX1(), getY2() - getY1());

		if (isShowingFancyIcons()) {
			drawZoomInIcon(component, graphics);
		} else {
			// draw simple plus sign
			graphics.drawLine(getX2() - signLength - signBorder, getY1() + signBorder + signMiddle, getX2()
					- signBorder, getY1() + signBorder + signMiddle);
			graphics.drawLine(getX2() - signMiddle - signBorder, getY1() + signBorder, getX2() - signBorder
					- signMiddle, getY1() + signBorder + signLength);
		}
	}

	/**
	 * Draws the zoom out rectangle.
	 * 
	 * @param component
	 *            the component to be used as the observer if this icon has no
	 *            image observer
	 * @param graphics
	 *            the graphics context
	 */
	protected void paintZoomingOutRectangle(Component component, Graphics graphics) {
		// draw box
		graphics.drawRect(getX2(), getY2(), getX1() - getX2(), getY1() - getY2());
		int innerBoxWidth = 5;
		graphics.drawRect(getX2() + (getX1() - getX2()) / 2 - innerBoxWidth, getY2() + (getY1() - getY2()) / 2
				- innerBoxWidth, 2 * innerBoxWidth, 2 * innerBoxWidth);

		if (isShowingFancyIcons()) {
			drawZoomOutIcon(component, graphics);
		} else {
			// draw simple minus sign
			graphics.drawLine(getX1() - signLength - signBorder, getY2() + signBorder + signMiddle, getX1()
					- signBorder, getY2() + signBorder + signMiddle);
		}
	}

	/**
	 * Draws the zoom-in icon.
	 * 
	 * @param component
	 *            the component to be used as the observer if this icon has no
	 *            image observer
	 * @param graphics
	 *            the graphics context
	 */
	protected void drawZoomInIcon(Component component, Graphics graphics) {
		ZoomIcon i = ZoomIcons.getIcon(ZOOMiN);
		i.paintIcon(component, graphics, getX2() + i.getHorizontalOffset(), getY1() + i.getVerticalOffset());

	}

	/**
	 * Draws the zoom-out icon.
	 * 
	 * @param component
	 *            the component to be used as the observer if this icon has no
	 *            image observer
	 * @param graphics
	 *            the graphics context
	 */
	protected void drawZoomOutIcon(Component component, Graphics graphics) {
		ZoomIcon i = ZoomIcons.getIcon(ZOOMoUT);
		i.paintIcon(component, graphics, getX1() + i.getHorizontalOffset(), getY2() + i.getVerticalOffset());
	}

	/**
	 * Set this to false, to disable drawing of the {@code ZoomIcons}. Then,
	 * small plus and minus signs are drawn.
	 * 
	 * @param showFancyIcon
	 *            the showFancyIcon to set
	 */
	public void setShowFancyIcons(boolean showFancyIcon) {
		this.showFancyIcon = showFancyIcon;
	}

	/**
	 * True, if icons are drawn at the corner of this {@code ZoomRectangle}.
	 * 
	 * 
	 * @return if fancy icons are drawn
	 */
	public boolean isShowingFancyIcons() {
		return showFancyIcon;
	}
}
