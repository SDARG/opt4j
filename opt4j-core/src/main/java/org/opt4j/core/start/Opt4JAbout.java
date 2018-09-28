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
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *******************************************************************************/

package org.opt4j.core.start;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import org.opt4j.core.config.Icons;
import org.opt4j.core.config.visualization.About;
import org.opt4j.core.config.visualization.ApplicationFrame;
import org.opt4j.core.config.visualization.Startupable;

import com.google.inject.Singleton;

/**
 * The {@link Opt4JAbout} information.
 * 
 * @author lukasiewycz
 * 
 */
@SuppressWarnings("serial")
@Singleton
public class Opt4JAbout extends JPanel implements About, Startupable {

	/**
	 * Contributers to Opt4J.
	 */
	public static final String[] AUTHORS = { "Martin Lukasiewycz", "Michael Gla&szlig;", "Sabine Helwig",
			"Felix Reimann" };

	/**
	 * MIT license disclaimer
	 */
	private static final String LICENSE_TEXT = "Copyright (c) 2014 Opt4J.\n\n"

			+ "Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the \"Software\"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:\n\n"

			+ "The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.\n\n"

			+ "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.config.gui.Startupable#startup()
	 */
	@Override
	public void startup() {
		Container content = this;

		content.setLayout(new BorderLayout());

		JLabel logoLabel = new JLabel(Icons.getIcon("img/top_logo.png"));
		logoLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		JPanel logo = new JPanel(new BorderLayout());
		logo.setBackground(Color.WHITE);
		logo.add(logoLabel);

		content.add(logo, BorderLayout.PAGE_START);

		JTextPane license = new JTextPane();
		license.setEditable(false);
		final JScrollPane licenseScroll = new JScrollPane(license);
		licenseScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		StyledDocument doc = license.getStyledDocument();

		Style regular = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);

		try {
			doc.insertString(doc.getLength(), LICENSE_TEXT, regular);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		license.setPreferredSize(new Dimension(360, 100));
		content.add(licenseScroll, BorderLayout.CENTER);

		SwingUtilities.invokeLater(() -> licenseScroll.getVerticalScrollBar().setValue(0));
		JPanel footer = new JPanel(new BorderLayout());
		footer.setBackground(Color.WHITE);

		// Add Copyright & Credits
		String copyright = "<html>Build " + Opt4J.getDateISO() + " <br /> Version " + Opt4J.getVersion()
				+ "   \u00a9 Opt4J.org 2007</html>";
		JLabel copyrightLabel = new JLabel(copyright);
		copyrightLabel.setHorizontalAlignment(SwingConstants.CENTER);
		copyrightLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		copyrightLabel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		footer.add(copyrightLabel, BorderLayout.EAST);

		String credits = "<html><p>Credits:<br />";
		for (String author : AUTHORS) {
			credits += author + "<br/>";
		}
		credits += "</p></html>";

		JLabel creditsLabel = new JLabel(credits);
		creditsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		creditsLabel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		footer.add(creditsLabel, BorderLayout.WEST);

		content.add(footer, BorderLayout.PAGE_END);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.config.visualization.About#getDialog(org.opt4j.config.
	 * visualization .ApplicationFrame)
	 */
	@Override
	public JDialog getDialog(ApplicationFrame frame) {
		JDialog dialog = new JDialog(frame, "About Opt4J", true);
		dialog.setBackground(Color.WHITE);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		dialog.setResizable(false);

		Opt4JAbout content = new Opt4JAbout();
		content.startup();
		dialog.add(content);

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension window = dialog.getPreferredSize();
		dialog.setLocation((screen.width - window.width) / 2, (screen.height - window.height) / 2);

		return dialog;
	}
}
