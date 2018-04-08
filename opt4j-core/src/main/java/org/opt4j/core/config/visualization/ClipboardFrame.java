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

package org.opt4j.core.config.visualization;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

/**
 * The {@link ClipboardFrame} is a tooltip that copies the text to the clipboard
 * if it is clicked.
 * 
 * @author lukasiewycz
 * 
 */
class ClipboardFrame extends JFrame implements ClipboardOwner {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a {@link ClipboardFrame}.
	 * 
	 * @param content
	 *            the content as a string
	 */
	public ClipboardFrame(final String content) {
		final JTextPane text = new JTextPane() {
			private static final long serialVersionUID = 1L;

			@Override
			public void processMouseEvent(MouseEvent me) {
				switch (me.getID()) {
				case MouseEvent.MOUSE_CLICKED:
					switch (me.getButton()) {
					case MouseEvent.BUTTON1:
						StringSelection stringSelection = new StringSelection(content);
						Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
						clipboard.setContents(stringSelection, ClipboardFrame.this);
						break;
					default:
						break;
					}
					ClipboardFrame.this.dispose();
					break;
				default:
					break;
				}
			}
		};
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				ClipboardFrame.this.dispose();
			}
		});

		text.setCaretColor(Color.WHITE);
		text.setEditable(false);
		text.setHighlighter(null);
		text.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));
		final StyledDocument doc = text.getStyledDocument();
		try {
			doc.insertString(doc.getLength(), content, null);
			doc.remove(doc.getLength() - 1, 1);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(text);
		panel.add(BorderLayout.SOUTH, new JLabel(" left-click: copy to clipboard"));
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

		add(panel);

		setUndecorated(true);
		Point2D p = MouseInfo.getPointerInfo().getLocation();
		setLocation((int) p.getX() - 8, (int) p.getY() - 8);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.datatransfer.ClipboardOwner#lostOwnership(java.awt.datatransfer
	 * .Clipboard, java.awt.datatransfer.Transferable)
	 */
	@Override
	public void lostOwnership(Clipboard clipboard, Transferable contents) {
		// do nothing
	}
}
