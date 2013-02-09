package ptolemy.plot.zoomBox;

import java.net.URL;

public class ZoomIcons {
	/**
	 * The icon for the zoom-in box.
	 */
	public static final String ZOOMiN = "icons/view-zoom-in.png";

	/**
	 * The icon for the zoom-out box.
	 */
	public static final String ZOOMoUT = "icons/view-zoom-out.png";

	/**
	 * The icon for automatic zoom.
	 */
	public static final String ZOOMaUTO = "icons/view-zoom-auto.png";

	/**
	 * Returns an {@code ImageIcon} from a filename.
	 * 
	 * @param filename
	 *            the filename
	 * @return an {@code ImageIcon} from a filename
	 */
	public static ZoomIcon getIcon(String filename) {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		URL url = classLoader.getResource(filename);
		try {
			return new ZoomIcon(url);
		} catch (NullPointerException e) {
			System.err.println("Image " + filename + " not found.");
			e.printStackTrace();
			return null;
		}
	}
}
