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

package org.opt4j.core.config.visualization;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import org.opt4j.core.config.Icons;
import org.opt4j.core.config.ModuleSaver;
import org.opt4j.core.config.PropertyModule;
import org.opt4j.core.config.annotations.Panel;

import com.google.inject.Inject;
import com.google.inject.Module;

/**
 * The {@link DefaultSelectedPanel}. The selected modules are organized as tabs.
 * 
 * @author lukasiewycz
 * 
 */
@SuppressWarnings("serial")
public class DefaultSelectedPanel extends SelectedPanel implements SetListener {

	protected static class MyScrollPane extends JScrollPane {

		PropertyPanel panel;

		public MyScrollPane(PropertyPanel panel) {
			super(panel);
			this.panel = panel;
		}

		public PropertyPanel getPanel() {
			return panel;
		}

	}

	/**
	 * The {@link TabbedPane} is a custom {@link JTabbedPane}.
	 */
	protected class TabbedPane extends JTabbedPane {
		@Override
		public void remove(int index) {
			Component component = tabs.getComponentAt(index);
			super.remove(index);

			if (component instanceof MyScrollPane) {
				MyScrollPane scroll = (MyScrollPane) component;
				map.remove(scroll.getPanel().getModule());
			}
		}

		protected int getIndex(PropertyModule module) {
			if (map.keySet().contains(module)) {
				for (int i = 0; i < tabs.getTabCount(); i++) {
					Component component = tabs.getComponentAt(i);
					if (component instanceof MyScrollPane) {
						MyScrollPane scroll = (MyScrollPane) component;
						if (scroll.getPanel().getModule().equals(module)) {
							return i;
						}
					}
				}
			}
			return -1;
		}
	}

	/**
	 * The custom tab of the {@link JTabbedPane}.
	 */
	protected class TabComponent extends JPanel {

		private JLabel label;
		private final JButton xmlButton;
		private final JButton closeButton;

		private final PropertyModule module;

		public TabComponent(PropertyModule module) {
			super(new FlowLayout(FlowLayout.LEFT, 0, 0));
			this.module = module;
			setOpaque(false);

			String name = format.getName(module);
			ImageIcon icon = format.getIcon(module);

			label = new JLabel(name, icon, SwingConstants.LEFT);
			xmlButton = new XMLButton();
			xmlButton.setToolTipText("Show module xml");
			closeButton = new CloseButton();
			closeButton.setToolTipText("Remove module");

			setLabelName(name);
		}

		protected void setLabelName(String name) {
			if (name.length() > 22) {
				name = name.substring(0, 18) + "...";
			}
			label.setName(name);
			removeAll();

			label = new JLabel(label.getName(), label.getIcon(), SwingConstants.LEFT);

			add(label);
			add(new JLabel(" "));
			label.setBorder(BorderFactory.createEmptyBorder(0, label.getIcon() == null ? 5 : 0, 0, 5));

			add(xmlButton);
			add(closeButton);
			setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
		}

		protected PropertyModule getModule() {
			return module;
		}

		private abstract class TabButton extends JButton implements ActionListener {

			protected final static int SIZE = 16;

			TabButton() {
				setPreferredSize(new Dimension(SIZE, SIZE));
				setContentAreaFilled(false);
				setFocusable(false);
				setBorder(BorderFactory.createEtchedBorder());
				setBorderPainted(false);
				addMouseListener(buttonMouseListener);
				setRolloverEnabled(true);
				addActionListener(this);
			}

			// paint the cross
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				// shift the image for pressed buttons
				if (getModel().isPressed()) {
					g2.translate(1, 1);
				}
				drawIcon(g2);
				g2.dispose();
			}

			protected abstract void drawIcon(Graphics2D g);

		}

		private class CloseButton extends TabButton {

			@Override
			public void actionPerformed(ActionEvent e) {
				int i = tabs.indexOfTabComponent(TabComponent.this);
				if (i != -1) {
					selectedModules.remove(module);
				}
			}

			@Override
			protected void drawIcon(Graphics2D g) {
				g.setStroke(new BasicStroke(2));
				g.setColor(Color.RED);

				int delta = 5;
				g.drawLine(delta, delta, getWidth() - delta - 2, getHeight() - delta - 2);
				g.drawLine(getWidth() - delta - 2, delta, delta, getHeight() - delta - 2);
			}
		}

		private class XMLButton extends TabButton {
			@Override
			public void actionPerformed(ActionEvent e) {
				ModuleSaver saver = new ModuleSaver();
				final ClipboardFrame frame = new ClipboardFrame(saver.toXMLString(module));
				frame.pack();
				frame.setVisible(true);
			}

			@Override
			protected void drawIcon(Graphics2D g) {
				g.setStroke(new BasicStroke(1));
				Image image = Icons.getIcon(Icons.XMLTAG).getImage();
				g.drawImage(image, 0, -1, 15, 16, this);
			}
		}

		private final MouseListener buttonMouseListener = new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Component component = e.getComponent();
				if (component instanceof AbstractButton) {
					AbstractButton button = (AbstractButton) component;
					button.setBorderPainted(true);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				Component component = e.getComponent();
				if (component instanceof AbstractButton) {
					AbstractButton button = (AbstractButton) component;
					button.setBorderPainted(false);
				}
			}
		};
	}

	protected final TabbedPane tabs = new TabbedPane();

	protected final Format format;

	protected final SelectedModules selectedModules;

	protected final FileChooser fileChooser;

	protected final Map<PropertyModule, PropertyPanel> map = new HashMap<PropertyModule, PropertyPanel>();

	protected final DropTarget dropTarget;

	/**
	 * The drop listener for the drag-and-drop functionality.
	 */
	protected final DropTargetListener dropListener = new DropTargetAdapter() {
		@Override
		public void drop(DropTargetDropEvent dtde) {
			Transferable transferable = dtde.getTransferable();
			DataFlavor flaver = ModuleTransferable.localModuleFlavor;
			try {

				if (transferable.isDataFlavorSupported(flaver)) {
					Object o = transferable.getTransferData(flaver);
					PropertyModule module = (PropertyModule) o;
					selectedModules.add(module);

					dtde.acceptDrop(DnDConstants.ACTION_MOVE);
					dtde.getDropTargetContext().dropComplete(true);
				} else {
					dtde.rejectDrop();
				}

			} catch (UnsupportedFlavorException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};

	/**
	 * Constructs a {@link DefaultSelectedPanel}.
	 * 
	 * @param format
	 *            the format
	 * @param selectedModules
	 *            the selected modules
	 * @param fileChooser
	 *            the file chooser
	 */
	@Inject
	public DefaultSelectedPanel(Format format, SelectedModules selectedModules, FileChooser fileChooser) {
		this.format = format;
		this.selectedModules = selectedModules;
		this.fileChooser = fileChooser;

		dropTarget = new DropTarget(this, dropListener);
	}

	/**
	 * Registers the listeners.
	 */
	@Inject
	public void init() {
		selectedModules.addListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.conf.gui.Startupable#startup()
	 */
	@Override
	public void startup() {
		setLayout(new BorderLayout());
		add(tabs);
	}

	/**
	 * Adds a module to the panel.
	 * 
	 * @param module
	 *            the module to add
	 */
	protected void addModule(PropertyModule module) {
		if (!map.keySet().contains(module)) {

			PropertyPanel panel = null;
			Panel p = module.getModule().getClass().getAnnotation(Panel.class);
			if (p != null) {
				Class<? extends PropertyPanel> clazz = p.value();

				try {
					Constructor<? extends PropertyPanel> cstr = clazz.getConstructor(PropertyModule.class,
							FileChooser.class, Format.class);
					panel = cstr.newInstance(module, fileChooser, format);

				} catch (Exception e) {
					throw new RuntimeException(e);
				}

			} else {
				panel = new PropertyPanel(module, fileChooser, format);
			}

			MyScrollPane scroll = new MyScrollPane(panel);
			scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			scroll.setPreferredSize(new Dimension(300, 300));

			String name = format.getName(module);
			String tooltip = format.getTooltip(module);

			int count = selectedModules.subSet(selectedModules.first(), module).size();
			tabs.insertTab(name, null, scroll, tooltip, count);
			tabs.setSelectedIndex(count);
			tabs.setTabComponentAt(count, new TabComponent(module));

			map.put(module, panel);

		} else {
			int index = tabs.getIndex(module);
			if (index != -1) {
				tabs.setSelectedIndex(index);
			}
		}
		updateTabNames();
	}

	/**
	 * Removes a module from the panel.
	 * 
	 * @param module
	 *            the module to remove
	 */
	protected void removeModule(PropertyModule module) {
		int index = tabs.getIndex(module);
		if (index != -1) {
			map.remove(module);
			tabs.remove(index);
		}
		updateTabNames();
	}

	/**
	 * Update the tab names. Consider multiple tabs of the same module.
	 */
	protected void updateTabNames() {
		Set<Class<? extends Module>> exist = new HashSet<Class<? extends Module>>();
		Set<Class<? extends Module>> multi = new HashSet<Class<? extends Module>>();
		for (PropertyModule pm : map.keySet()) {
			Class<? extends Module> module = pm.getModule().getClass();
			if (exist.contains(module)) {
				multi.add(module);
			} else {
				exist.add(module);
			}
		}

		Map<Class<? extends Module>, Integer> counter = new HashMap<Class<? extends Module>, Integer>();
		for (Class<? extends Module> module : multi) {
			counter.put(module, 1);
		}

		for (int i = 0; i < tabs.getTabCount(); i++) {
			TabComponent comp = (TabComponent) tabs.getTabComponentAt(i);

			Class<? extends Module> module = comp.getModule().getModule().getClass();
			if (counter.containsKey(module)) {
				int c = counter.get(module);
				String name = "(" + c + ") " + format.getName(comp.getModule());
				comp.setLabelName(name);
				counter.put(module, c + 1);
			}
		}
		tabs.repaint();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.conf.gui.SetListener#moduleAdded(java.util.Collection,
	 * org.opt4j.conf.PropertyModule)
	 */
	@Override
	public void moduleAdded(Collection<PropertyModule> collection, PropertyModule module) {
		addModule(module);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.conf.gui.SetListener#moduleRemoved(java.util.Collection,
	 * org.opt4j.conf.PropertyModule)
	 */
	@Override
	public void moduleRemoved(Collection<PropertyModule> collection, PropertyModule module) {
		removeModule(module);
	}

}
