/*******************************************************************************
 * Copyright (c) 2014 Opt4J
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/

package org.opt4j.core.config.visualization;

import static java.time.format.TextStyle.FULL;
import static java.util.Locale.ENGLISH;
import static org.opt4j.core.config.annotations.Citation.PublicationMonth.UNKNOWN;

import java.lang.reflect.Field;

import javax.swing.ImageIcon;

import org.opt4j.core.config.Icons;
import org.opt4j.core.config.Property;
import org.opt4j.core.config.PropertyModule;
import org.opt4j.core.config.annotations.Category;
import org.opt4j.core.config.annotations.Citation;
import org.opt4j.core.config.annotations.Icon;
import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.config.annotations.Name;

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

		Citation citation = c.getAnnotation(Citation.class);
		if (citation != null)
			text += xmlBreak + xmlBreak + "<span style='color: gray;'>[" + formatIEEE(citation) + "]</span>";

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

					Citation c = field.getAnnotation(Citation.class);
					if (c != null)
						text += "&nbsp;&nbsp;<span style='color: gray;'>[" + formatIEEE(c) + "]</span>";
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

	public static String formatIEEE(Citation citation) {
		StringBuilder builder = new StringBuilder(citation.authors());
		builder.append(": <em>").append(citation.title()).append(".</em> ");
		if (!citation.journal().isEmpty()) {
			builder.append("In: <em>").append(citation.journal()).append(".</em> ");
		}
		if (citation.volume() >= 0) {
			builder.append("vol. ").append(citation.volume()).append(", ");
		}
		if (citation.number() >= 0) {
			builder.append("no. ").append(citation.number()).append(", ");
		}
		if (citation.pageFirst() >= 0) {
			if (citation.pageLast() > 0 && citation.pageFirst() != citation.pageLast()) {
				builder.append("pp. ").append(citation.pageFirst()).append("&ndash;").append(citation.pageLast())
						.append(", ");
			} else {
				builder.append("p. ").append(citation.pageFirst()).append(", ");
			}
		}
		if (citation.month() != UNKNOWN) {
			builder.append(citation.month().toMonth().getDisplayName(FULL, ENGLISH)).append(" ");
		}
		builder.append(citation.year()).append(".");

		if (!citation.doi().isEmpty()) {
			builder.append(" <a href=\"https://doi.org/").append(citation.doi()).append("\">").append(citation.doi())
					.append("</a>");
		}
		return builder.toString();
	}

	public static String formatJava(Citation citation) {
		StringBuilder builder = new StringBuilder();
		if (!citation.authors().isEmpty())
			builder.append(citation.authors()).append(": ");
		builder.append("\"").append(citation.title()).append("\". ");
		if (!citation.journal().isEmpty())
			builder.append("In: ").append(citation.journal()).append(". ");
		if (citation.volume() >= 0)
			builder.append("vol. ").append(citation.volume()).append(", ");
		if (citation.number() >= 0)
			builder.append("no. ").append(citation.number()).append(", ");
		if (citation.pageFirst() >= 0) {
			if (citation.pageLast() > 0 && citation.pageFirst() != citation.pageLast()) {
				builder.append("pp. ").append(citation.pageFirst()).append("–").append(citation.pageLast())
						.append(", ");
			} else {
				builder.append("p. ").append(citation.pageFirst()).append(", ");
			}
		}
		if (citation.month() != UNKNOWN) {
			builder.append(citation.month().toMonth().getDisplayName(FULL, ENGLISH)).append(" ");
		}
		builder.append(citation.year()).append(".");
		if (!citation.doi().isEmpty()) {
			builder.append(" https://doi.org/").append(citation.doi());
		}
		return builder.toString();
	}
}
