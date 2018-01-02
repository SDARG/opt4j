package ptolemy.plot.zoomBox;

import java.net.URL;

import javax.swing.ImageIcon;

public class ZoomIcon extends ImageIcon {
	private static final long serialVersionUID = 1L;

	public ZoomIcon(URL url) {
		super(url);
	}

	/**
	 * Return the best vertical offset for the given icon.
	 * 
	 * @return the vertical offset to use
	 */
	public int getVerticalOffset() {
		return -10;
	}

	/**
	 * Return the best horizontal offset for the given icon.
	 * 
	 * @return the horizontal offset to use
	 */
	public int getHorizontalOffset() {
		return -12;
	}
}
