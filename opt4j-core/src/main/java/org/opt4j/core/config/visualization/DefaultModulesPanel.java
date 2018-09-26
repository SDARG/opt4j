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
import java.awt.Point;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceAdapter;
import java.awt.dnd.DragSourceListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.RejectedExecutionException;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreeSelectionModel;

import org.opt4j.core.config.Icons;
import org.opt4j.core.config.ModuleRegister;
import org.opt4j.core.config.PropertyModule;
import org.opt4j.core.config.annotations.Category;
import org.opt4j.core.config.annotations.Multi;
import org.opt4j.core.config.annotations.Parent;

import com.google.inject.Inject;
import com.google.inject.Module;

/**
 * The {@link DefaultModulesPanel}. This implementation is a tree of all
 * available modules.
 * 
 * @author lukasiewycz
 * 
 */
@SuppressWarnings("serial")
public class DefaultModulesPanel extends ModulesPanel {

	protected final Format format;

	protected final ModuleRegister allModules;

	protected final SelectedModules selectedModules;

	protected final RootTreeNode root = new RootTreeNode(true);

	protected JTree tree = null;

	protected final JPopupMenu moduleMenu = new JPopupMenu();

	/**
	 * The {@link UserNode} is a basic node of the tree.
	 */
	protected abstract class UserNode extends DefaultMutableTreeNode {

		public UserNode(Object object) {
			super(object);
		}

		@Override
		public String toString() {
			return format.getName(getType());
		}

		public ImageIcon getIcon() {
			return format.getIcon(getType());
		}

		public String getTooltip() {
			return format.getTooltip(getType());
		}

		public abstract Class<?> getType();
	}

	/**
	 * The {@link RootTreeNode} is the root node of the tree.
	 */
	protected class RootTreeNode extends UserNode {

		public RootTreeNode(boolean b) {
			super(b);
		}

		public void setLoading(boolean b) {
			userObject = b;
		}

		public boolean isLoading() {
			return (Boolean) userObject;
		}

		@Override
		public ImageIcon getIcon() {
			if (isLoading()) {
				ImageIcon icon = Icons.getIcon(Icons.LOADING);
				icon.setImageObserver(tree);
				return icon;
			}
			return format.asFolder(null);
		}

		@Override
		public String toString() {
			if (isLoading()) {
				return "Loading";
			}
			return "Modules";
		}

		@Override
		public String getTooltip() {
			return null;
		}

		@Override
		public Class<?> getType() {
			return null;
		}
	}

	/**
	 * The {@link ModuleTreeNode} is used for nodes that represent modules.
	 */
	@SuppressWarnings("unchecked")
	protected class ModuleTreeNode extends UserNode {
		public ModuleTreeNode(Class<? extends Module> module) {
			super(module);
		}

		public PropertyModule getModule() {
			return allModules.get((Class<? extends Module>) userObject);
		}

		@Override
		public Class<?> getType() {
			return (Class<?>) userObject;
		}
	}

	/**
	 * The {@link CategoryTreeNode} is used for nodes that represent categories.
	 */
	protected class CategoryTreeNode extends UserNode {

		public CategoryTreeNode(Class<?> clazz) {
			super(clazz);
		}

		@Override
		public Class<?> getType() {
			return (Class<?>) userObject;
		}

		@Override
		public String toString() {
			return format.getName(getType());
		}

		@Override
		public ImageIcon getIcon() {
			return format.asFolder(super.getIcon());
		}
	}

	/**
	 * The {@link Default} category interface.
	 * 
	 * @author lukasiewycz
	 * 
	 */
	@Category("Default")
	class Default {

	}

	/**
	 * The {@link TreeCellRenderer} for the modules tree.
	 * 
	 */
	protected static class TreeCellRenderer extends DefaultTreeCellRenderer {
		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
				boolean leaf, int row, boolean hasFocus) {
			Component comp = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

			ImageIcon icon = null;
			String tooltip = null;

			if (value instanceof UserNode) {
				UserNode userNode = (UserNode) value;
				icon = userNode.getIcon();
				tooltip = userNode.getTooltip();
			}
			if (icon == null) {
				icon = Icons.getDefault();
			}

			setIcon(icon);
			setToolTipText(tooltip);

			return comp;
		}
	}

	/**
	 * The {@link MyTree} that extends a {@link JTree} by a drag method.
	 * 
	 */
	protected static class MyTree extends JTree implements DragGestureListener {

		protected DragSource dragSource = null;

		protected DragSourceListener dragSourceListener = new DragSourceAdapter() {
		};

		public MyTree(TreeNode root) {
			super(root);

			dragSource = new DragSource();
			dragSource.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_MOVE, this);
		}

		@Override
		public void dragGestureRecognized(DragGestureEvent dge) {
			TreeNode node = (TreeNode) this.getLastSelectedPathComponent();

			if (node instanceof ModuleTreeNode) {
				ModuleTreeNode moduleNode = (ModuleTreeNode) node;
				PropertyModule module = moduleNode.getModule();

				Transferable transferable = new ModuleTransferable(module);

				dragSource.startDrag(dge, DragSource.DefaultMoveDrop, transferable, dragSourceListener);
			}
		}

	}

	/**
	 * Constructs a {@link DefaultModulesPanel}.
	 * 
	 * @param format
	 *            the format
	 * @param allModules
	 *            the set of all modules
	 * @param selectedModules
	 *            the set of selected modules
	 */
	@Inject
	public DefaultModulesPanel(Format format, ModuleRegister allModules, SelectedModules selectedModules) {
		super();
		this.format = format;
		this.allModules = allModules;
		this.selectedModules = selectedModules;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.conf.gui.Startupable#startup()
	 */
	@Override
	public void startup() {

		tree = new MyTree(root);
		tree.setToggleClickCount(1000); // don't expand on double click hack
		tree.addMouseListener(mouseListener);
		tree.setCellRenderer(new TreeCellRenderer());
		tree.setSelectionModel(null);

		ToolTipManager.sharedInstance().registerComponent(tree);

		JScrollPane scroll = new JScrollPane(tree, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		setLayout(new BorderLayout());
		add(scroll, BorderLayout.CENTER);

		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					populateTree();
				} catch (RejectedExecutionException e) {
				}
			}
		};
		thread.setPriority(Thread.MIN_PRIORITY);
		thread.start();
	}

	private Class<?> getCategory(Class<?> clazz, boolean include) {
		if (include && isCategory(clazz)) {
			return clazz;
		}
		if (clazz.equals(Object.class)) {
			return null;
		} else if (clazz.getAnnotation(Parent.class) != null) {
			Class<?> pClass = clazz.getAnnotation(Parent.class).value();
			return pClass;
		} else {
			assert (clazz.getSuperclass() != null) : clazz;
			return getCategory(clazz.getSuperclass(), true);
		}
	}

	private boolean isCategory(Class<?> clazz) {
		Category category = clazz.getAnnotation(Category.class);
		return (category != null);
	}

	/**
	 * Build and order the tree
	 */
	protected void populateTree() {
		// get all relevant categories

		// map from the modules (categories) to their categories
		// (supercategories)
		Map<Class<?>, Class<?>> map = new HashMap<Class<?>, Class<?>>();

		for (Class<? extends Module> module : allModules) {
			Class<?> category = getCategory(module, false);
			assert (!module.equals(category)) : module;
			map.put(module, category);
		}

		int size;
		do {
			size = map.size();
			Set<Class<?>> values = new HashSet<Class<?>>(map.values());
			for (Class<?> cat : values) {
				if (cat != null) {
					Class<?> category = getCategory(cat, false);
					map.put(cat, category);
				}
			}
		} while (map.size() != size);

		// create all nodes
		Map<Class<?>, CategoryTreeNode> ctn = new HashMap<Class<?>, CategoryTreeNode>();
		Map<Class<?>, ModuleTreeNode> mtn = new HashMap<Class<?>, ModuleTreeNode>();
		Map<Class<?>, DefaultMutableTreeNode> atn = new HashMap<Class<?>, DefaultMutableTreeNode>();

		for (Class<?> clazz : map.keySet()) {
			if (isCategory(clazz)) {
				CategoryTreeNode node = new CategoryTreeNode(clazz);
				ctn.put(clazz, node);
			} else {
				@SuppressWarnings("unchecked")
				ModuleTreeNode node = new ModuleTreeNode((Class<? extends Module>) clazz);
				mtn.put(clazz, node);
			}
		}
		atn.putAll(ctn);
		atn.putAll(mtn);

		// create hierarchy by parent annotation
		CategoryTreeNode def = new CategoryTreeNode(Default.class);
		root.add(def);
		for (Entry<Class<?>, DefaultMutableTreeNode> entry : atn.entrySet()) {
			Class<?> clazz = entry.getKey();
			DefaultMutableTreeNode node = entry.getValue();

			Class<?> category = map.get(clazz);

			if (category == null) {
				if (isCategory(clazz)) {
					root.add(node);
				} else {
					def.add(node);
				}
			} else {
				DefaultMutableTreeNode n = atn.get(category);
				assert (n != null && n != node) : clazz + " " + category;
				n.add(node);
			}

		}

		// clean empty categories
		boolean removed;
		do {
			removed = removeEmptyCategories(root);
		} while (removed);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				tree.setSelectionModel(new DefaultTreeSelectionModel());
				tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
				root.setLoading(false);
				sort(tree);
				tree.expandRow(0);
			}
		});
		revalidate();
		repaint();
	}

	/**
	 * Removes empty categories. This method can create some new empty
	 * categories!
	 * 
	 * @param current
	 *            the current node
	 * @return true if an empty category was removed
	 */
	private boolean removeEmptyCategories(DefaultMutableTreeNode current) {
		if (!current.children().hasMoreElements()) {
			return false;
		}

		TreeNode node = current.getFirstChild();
		boolean removed = false;
		while (node != null) {
			DefaultMutableTreeNode n = (DefaultMutableTreeNode) node;
			node = current.getChildAfter(n);

			if (n.isLeaf()) {
				if (n instanceof CategoryTreeNode && n.getChildCount() == 0) {
					DefaultMutableTreeNode parent = (DefaultMutableTreeNode) n.getParent();
					parent.remove(n);
					removed = true;
				}
			} else {
				removed = removed || removeEmptyCategories(n);
			}
		}
		return removed;
	}

	protected MouseListener mouseListener = new MouseAdapter() {

		@Override
		public void mousePressed(MouseEvent e) {
			if (e.isPopupTrigger()) {
				displayMenu(e);
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			int count = e.getClickCount();

			if (e.isPopupTrigger()) {
				displayMenu(e);
			} else if (count > 1) {
				Object object = tree.getLastSelectedPathComponent();
				if (object instanceof ModuleTreeNode) {
					ModuleTreeNode node = (ModuleTreeNode) object;
					PropertyModule pmodule = node.getModule();
					if (selectedModules.contains(pmodule)) {
						remove(pmodule);
					} else {
						add(pmodule);
					}
				}
			}
		}
	};

	/**
	 * Adds a new {@link PropertyModule}.
	 * 
	 * @param pm
	 *            the property module to be added
	 */
	protected void add(PropertyModule pm) {
		Module module = pm.getModule();
		if (module.getClass().getAnnotation(Multi.class) != null) {
			try {
				Module m2 = module.getClass().newInstance();
				PropertyModule pm2 = new PropertyModule(m2);
				selectedModules.add(pm2);
			} catch (InstantiationException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			}
		} else {
			selectedModules.add(pm);
		}
	}

	/**
	 * Removes a {@link PropertyModule}.
	 * 
	 * @param pm
	 *            the property module to be removed
	 */
	protected void remove(PropertyModule pm) {
		selectedModules.remove(pm);
	}

	/**
	 * Displays a popup menu for the tree elements.
	 * 
	 * @param e
	 *            the mouse event
	 */
	protected void displayMenu(MouseEvent e) {
		if (e.isPopupTrigger()) {
			Point pt = e.getPoint();
			tree.setSelectionPath(tree.getPathForLocation(pt.x, pt.y));

			Object object = tree.getLastSelectedPathComponent();
			if (object instanceof ModuleTreeNode) {

				ModuleTreeNode node = (ModuleTreeNode) object;
				final PropertyModule module = node.getModule();

				moduleMenu.removeAll();

				JMenuItem add = new JMenuItem("Add Module", Icons.getIcon(Icons.ADD));
				add.addActionListener((ActionEvent event) -> add(module));

				JMenuItem remove = new JMenuItem("Remove Module", Icons.getIcon(Icons.DELETE));
				remove.addActionListener((ActionEvent event) -> remove(module));

				if (selectedModules.contains(module)) {
					add.setEnabled(false);
				} else {
					remove.setEnabled(false);
				}

				moduleMenu.add(add);
				moduleMenu.add(remove);
				moduleMenu.show(e.getComponent(), e.getX(), e.getY());
			}

		}
	}

	/**
	 * Sorts the {@link JTree} alphabetically.
	 * 
	 * @param tree
	 *            the tree to be sorted
	 */
	protected void sort(JTree tree) {
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();
		sort(root);

	}

	/**
	 * Sorts the child elements of one tree node alphabetically.
	 * 
	 * @param node
	 *            the node which children are sorted
	 */
	protected void sort(DefaultMutableTreeNode node) {
		if (!node.children().hasMoreElements()) {
			return;
		}

		List<UserNode> nodes = new ArrayList<UserNode>();

		Enumeration<?> children = node.children();

		while (children.hasMoreElements()) {
			UserNode n = (UserNode) children.nextElement();
			nodes.add(n);
		}

		Collections.sort(nodes, new Comparator<UserNode>() {
			@Override
			public int compare(UserNode o1, UserNode o2) {
				boolean cat1 = o1 instanceof CategoryTreeNode;
				boolean cat2 = o2 instanceof CategoryTreeNode;

				if (cat1 && !cat2) {
					return -1;
				} else if (!cat1 && cat2) {
					return 1;
				} else {
					return o1.toString().compareTo(o2.toString());
				}
			}
		});

		node.removeAllChildren();

		for (UserNode n : nodes) {
			node.add(n);
			sort(n);
		}
	}

}
