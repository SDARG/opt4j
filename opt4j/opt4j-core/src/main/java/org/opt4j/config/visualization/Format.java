/**
 * Opt4J is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Opt4J. If not, see http://www.gnu.org/licenses/.
 */

package org.opt4j.config.visualization;

import java.lang.reflect.Field;

import javax.swing.ImageIcon;

import org.opt4j.config.Icons;
import org.opt4j.config.Property;
import org.opt4j.config.PropertyModule;
import org.opt4j.config.annotations.Category;
import org.opt4j.config.annotations.Icon;
import org.opt4j.config.annotations.Info;
import org.opt4j.config.annotations.Name;

/**
 * The {@link Format} contains several format rules.
 * 
 * @author lukasiewycz
 * 
 */
public class Format {
	protected static final String xmlBreak = "<br/>";

	/**
	 * Returns the name of a {@link Class}.
	 * 
	 * @param clazz
	 *            the class
	 * @return the name
	 */
	public String getName(Class<?> clazz) {
		Name name = clazz.getAnnotation(Name.class);
		Category category = clazz.getAnnotation(Category.class);
		if (name != null && !"".equals(name.value())) {
			return name.value();
		} else if (category != null && !"".equals(category.value())) {
			return category.value();
		} else {
			String n = clazz.getName();
			int c;
			while ((c = n.indexOf('.')) != -1) {
				n = n.substring(c + 1);
			}

			// Trim suffix "Module".
			if (n.endsWith("Module")) {
				n = n.substring(0, n.length() - "Module".length());
			}
			return n;
		}
	}

	/**
	 * Returns the info of a {@link Class}.
	 * 
	 * @param c
	 *            the class
	 * @return the info
	 */
	public String getInfo(Class<?> c) {
		Info info = c.getAnnotation(Info.class);
		String text = null;
		if (info != null && !"".equals(info.value())) {
			text = info.value();
		}
		return text;
	}

	/**
	 * Returns the tooltip of a {@link Class}.
	 * 
	 * @param c
	 *            the class
	 * @return the tooltip
	 */
	public String getTooltip(Class<?> c) {

		String name = getName(c);
		String info = getInfo(c);

		String text = "<html><b>" + name + "</b>";
		if (info != null) {
			text += xmlBreak + info;
		}
		text += "</html>";

		return text;
	}

	/**
	 * Returns the icon of a {@link Class}.
	 * 
	 * @param clazz
	 *            the class
	 * @return the icon
	 */
	public ImageIcon getIcon(Class<?> clazz) {
		Icon icon = clazz.getAnnotation(Icon.class);

		if (icon == null) {
			return null;
		}

		return Icons.getIcon(icon.value());
	}

	/**
	 * Returns the name of a {@link PropertyModule}.
	 * 
	 * @param module
	 *            the module
	 * @return the name
	 */
	public String getName(PropertyModule module) {
		Class<?> c = module.getModule().getClass();

		return getName(c);
	}

	/**
	 * Returns the tooltip of a {@link PropertyModule}.
	 * 
	 * @param module
	 *            the module
	 * @return the tooltip
	 */
	public String getTooltip(PropertyModule module) {
		Class<?> c = module.getModule().getClass();

		return getTooltip(c);
	}

	/**
	 * Returns the {@link ImageIcon} of a {@link PropertyModule}.
	 * 
	 * @param module
	 *            the module
	 * @return the icon
	 */
	public ImageIcon getIcon(PropertyModule module) {
		Class<?> c = module.getModule().getClass();

		return getIcon(c);
	}

	/**
	 * Converts the given {@link ImageIcon} into a folder icon.
	 * 
	 * @param icon
	 *            the icon
	 * @return the converted folder icon
	 */
	public ImageIcon asFolder(ImageIcon icon) {
		ImageIcon i = Icons.getIcon(Icons.FOLDER);
		if (icon != null) {
			ImageIcon background = icon;
			ImageIcon foreground = Icons.getIcon(Icons.SFOLDER);
			i = Icons.merge(background, foreground);
		}
		return i;
	}

	/**
	 * Returns the formatted name of the property.
	 * 
	 * @param property
	 *            the property
	 * @return the formatted name
	 */
	public String getName(Property property) {
		String name;
		Name n = property.getAnnotation(Name.class);
		if (n == null) {
			name = property.getName();
		} else {
			name = n.value();
		}
		return name;
	}

	/**
	 * Returns the formatted (html) tooltip of a {@link Property}.
	 * 
	 * @param property
	 *            the property
	 * @return the tooltip
	 */
	public String getTooltip(Property property) {
		String text = "<html><b>" + getName(property) + "</b>";
		String info = property.getInfo();
		if (info != null) {
			text += xmlBreak + info;
		}
		if (property.getType().isEnum()) {
			Class<?> type = property.getType();
			text += xmlBreak;

			Field[] fields = type.getDeclaredFields();
			for (Field field : fields) {
				if (field.isEnumConstant()) {
					String name = field.getName();
					Icon icon = field.getAnnotation(Icon.class);
					Info i = field.getAnnotation(Info.class);
					text += "&nbsp;" + name;
					if (icon != null || i != null) {
						text += " - ";
					}
					if (icon != null) {
						text += "<img src=\"" + Icons.getURL(icon.value()) + "\">";

						System.out.println(text + " " + icon.value());
					}
					if (i != null) {
						text += i.value();
					}
					text += xmlBreak;
				}
			}
		}

		text += "</html>";

		return text;
	}

	/**
	 * Formats a text for tooltip (inserts break-lines).
	 * 
	 * @param text
	 *            the input text
	 * @return the formatted tooltip text
	 */
	public String formatTooltip(String text) {
		return "<html>" + text.replaceAll("\n", xmlBreak) + "</html>";
	}
}
