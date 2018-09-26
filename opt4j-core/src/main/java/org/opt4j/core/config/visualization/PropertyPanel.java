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

package org.opt4j.core.config.visualization;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;

import org.opt4j.core.config.Icons;
import org.opt4j.core.config.Property;
import org.opt4j.core.config.PropertyModule;
import org.opt4j.core.config.Requirement;
import org.opt4j.core.config.annotations.Citation;
import org.opt4j.core.config.annotations.File;

/**
 * The {@link PropertyPanel} is a panel for the configuration of one module.
 * Properties and values are arranged in a table.
 * 
 * @author lukasiewycz
 * 
 */
@SuppressWarnings("serial")
public class PropertyPanel extends JPanel {

	protected final PropertyModule module;

	protected final FileChooser fileChooser;

	protected final Format format;

	protected final JPanel panel;

	protected final Map<Property, Component> components = new HashMap<Property, Component>();

	/**
	 * Constructs a {@link PropertyPanel} for one {@link PropertyModule}
	 * instance.
	 * 
	 * @param module
	 *            the instance of the {@link PropertyModule}
	 * @param fileChooser
	 *            the FileChooser
	 * @param format
	 *            the format
	 */
	public PropertyPanel(PropertyModule module, FileChooser fileChooser, Format format) {
		this.module = module;
		this.fileChooser = fileChooser;
		this.format = format;

		fillComponentsMap();

		panel = new JPanel(new DialogLayout(20, 2));
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		setLayout(new BorderLayout());
		add(BorderLayout.NORTH, panel);

		update();
	}

	protected Component createComponent(final Property property) {
		Component component = null;
		Object value = property.getValue();
		Class<?> type = property.getType();

		if (type.isEnum()) {
			Object[] obj = new Object[type.getEnumConstants().length];
			for (int k = 0; k < obj.length; k++) {
				obj[k] = type.getEnumConstants()[k];
			}
			final JComboBox<Object> box = new JComboBox<Object>(obj);
			box.setSelectedItem(value);

			box.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Object selected = box.getSelectedItem();
					try {
						property.setValue(selected);
						update();
					} catch (InvocationTargetException ex) {
						System.err.println(ex.getMessage());
					}
				}
			});

			component = box;
		} else if (type.equals(Boolean.TYPE)) {
			final JCheckBox checkbox = new JCheckBox();

			boolean b = (Boolean) property.getValue();
			checkbox.setSelected(b);
			checkbox.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					boolean b = checkbox.isSelected();
					try {
						property.setValue(b);
						update();
					} catch (InvocationTargetException ex) {
						System.err.println(ex.getMessage());
					}

				}
			});

			component = checkbox;
		} else {
			final JTextField field = new JTextField();

			if (property.isNumber()) {
				field.setHorizontalAlignment(SwingConstants.RIGHT);
			}
			if (value == null) {
				field.setText("");
			} else {
				field.setText(value.toString());
			}

			field.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					String value = format(property, field.getText());
					try {
						property.setValue(value);
					} catch (InvocationTargetException ex) {
						System.err.println(ex.getMessage());
					} finally {
						field.setText(property.getValue().toString());
						update();
					}
				}
			});

			field.addKeyListener(new KeyAdapter() {

				@Override
				public void keyReleased(KeyEvent e) {
					String value = format(property, field.getText());

					try {
						property.setValue(value);
					} catch (InvocationTargetException ex) {
						System.err.println(ex.getMessage());
						field.setText(property.getValue().toString());
					}
				}
			});

			component = field;
		}
		return component;
	}

	protected String format(Property property, String value) {
		String result = value;
		if (property.isNumber()) {
			result = result.trim();
			if ("".equals(result)) {
				result = "0";
			} else if ("-".equals(result)) {
				result = "0";
			} else {
				if (result.startsWith(".")) {
					result = "0" + result;
				}
				if (result.endsWith(".")) {
					result = result + "0";
				}
			}
			return result;
		}
		return result;
	}

	protected void fillComponentsMap() {
		for (final Property property : module.getProperties()) {
			Component component = createComponent(property);
			components.put(property, component);
		}
	}

	protected void updatePropertyPanel() {
		panel.removeAll();

		for (final Property property : module.getProperties()) {
			if (property.isActive()) {
				String name = property.getName();

				int i = getIndent(property);
				String s = "";
				for (int j = 0; j < i; j++) {
					s += "     ";
				}
				if (i > 0) {
					s = s.substring(2) + "\u21aa ";
				}

				JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
				JLabel label = new JLabel(s + name);
				label.setFocusable(false);
				String tooltip = format.getTooltip(property);
				if (tooltip != null) {
					label.setToolTipText(tooltip);
				}
				labelPanel.add(label);

				File file = property.getAnnotation(File.class);
				if (file != null) {
					JButton browse = new JButton(Icons.getIcon(Icons.FOLDER));
					browse.setFocusable(false);
					browse.setBorderPainted(false);
					browse.setContentAreaFilled(false);
					browse.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 0));

					browse.addActionListener((ActionEvent e) -> selectFile(property));
					browse.setCursor(new Cursor(Cursor.HAND_CURSOR));
					browse.setToolTipText("Browse ...");
					labelPanel.add(browse);
				}
				panel.add(labelPanel);

				Component component = components.get(property);
				panel.add(component);
			}
		}

		if (module.getModule().getClass().isAnnotationPresent(Citation.class)) {
			Citation citation = module.getModule().getClass().getAnnotation(Citation.class);
			addReferenceRow(citation);
		}
	}

	/**
	 * Adds a row showing the {@link Citation}. On the left hand side,
	 * "reference" is printed while on the right hand side, the {@link Citation}
	 * is added in a static {@link TextArea}.
	 * 
	 * @param citation
	 *            the citation to add
	 */
	protected void addReferenceRow(Citation citation) {
		JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		JLabel label = new JLabel("reference");
		label.setFocusable(false);
		labelPanel.add(label);

		panel.add(labelPanel);

		final JTextArea field = new JTextArea();
		field.setLineWrap(true);
		field.setWrapStyleWord(true);
		field.setFont(label.getFont());
		field.setBackground(label.getBackground());
		field.setEditable(false);
		field.setText(Format.formatJava(citation));

		if (!citation.doi().isEmpty()) {
			final String doi = citation.doi();
			field.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			field.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {
					// nothing to be done
				}

				@Override
				public void mousePressed(MouseEvent e) {
					// nothing to be done
				}

				@Override
				public void mouseExited(MouseEvent e) {
					// nothing to be done
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// nothing to be done
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					try {
						Desktop.getDesktop().browse(new URI("https://doi.org/" + doi));
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (URISyntaxException e1) {
						e1.printStackTrace();
					}
				}
			});
		}

		JPanel rightPanel = new JPanel(new BorderLayout());
		rightPanel.add(BorderLayout.NORTH, field);
		rightPanel.setPreferredSize(
				new Dimension((int) (panel.getPreferredSize().width * 0.70), panel.getPreferredSize().height));
		panel.add(rightPanel);
	}

	protected void update() {
		updatePropertyPanel();
		revalidate();
		repaint();
	}

	/**
	 * Returns the instance of the {@link PropertyModule}.
	 * 
	 * @return the instance of the property module
	 */
	public PropertyModule getModule() {
		return module;
	}

	protected int getIndent(Property property) {
		int i = 0;
		for (Requirement requirement : property.getRequirements()) {
			i = Math.max(i, getIndent(requirement.getProperty()));
		}
		if (!property.getRequirements().isEmpty()) {
			i++;
		}
		return i;
	}

	private void selectFile(Property property) {
		final File file = property.getAnnotation(File.class);
		final JFileChooser fileChooser = PropertyPanel.this.fileChooser.get();

		java.io.File dir = null;
		try {
			dir = new java.io.File(property.getValue().toString());
		} catch (Exception ex) {
		}

		fileChooser.setCurrentDirectory(dir);

		if (file != null && !file.value().equals("")) {
			FileFilter filter = new FileFilter() {

				@Override
				public boolean accept(java.io.File pathname) {
					if (pathname.isDirectory()) {
						return true;
					}
					String f = pathname.getName().toLowerCase();
					String ext = file.value().toLowerCase();

					if (f.endsWith(ext)) {
						return true;
					}
					return false;
				}

				@Override
				public String getDescription() {
					String ext = file.value().toLowerCase();
					return "(*" + ext + ")";
				}
			};

			fileChooser.setFileFilter(filter);
		} else {
			fileChooser.setFileFilter(null);
		}

		if (file.folder()) {
			if (file.file()) {
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			} else {
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			}
		} else {
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		}
		fileChooser.setVisible(true);

		int status = fileChooser.showOpenDialog(null);
		if (status == JFileChooser.APPROVE_OPTION) {
			java.io.File f = fileChooser.getSelectedFile();
			try {
				property.setValue(f.getAbsolutePath());
				JTextField field = (JTextField) components.get(property);
				field.setText("" + property.getValue());
				update();
			} catch (InvocationTargetException ex) {
				System.err.println(ex.getMessage());
			}
		}
	}
}
