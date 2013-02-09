/**
 * Opt4J is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with Opt4J. If not, see http://www.gnu.org/licenses/. 
 */

package org.opt4j.config.visualization;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.opt4j.config.PropertyModule;

/**
 * The {@link ModuleTransferable} is a (Drag and Drop) {@link Transferable} for
 * {@link PropertyModule}s.
 * 
 * @author lukasiewycz
 * 
 */
public class ModuleTransferable implements Transferable {

	protected PropertyModule module;

	public static DataFlavor localModuleFlavor = null;

	static {
		try {
			localModuleFlavor = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + "; class="
					+ PropertyModule.class.getCanonicalName(), "Local PropertyModule");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Constructs a {@link ModuleTransferable}.
	 * 
	 * @param module
	 *            the property module
	 */
	public ModuleTransferable(PropertyModule module) {
		this.module = module;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.datatransfer.Transferable#getTransferData(java.awt.datatransfer
	 * .DataFlavor)
	 */
	@Override
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		if (flavor.equals(DataFlavor.stringFlavor)) {
			return module.getModule().getClass().toString();
		}
		return module;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.datatransfer.Transferable#getTransferDataFlavors()
	 */
	@Override
	public DataFlavor[] getTransferDataFlavors() {
		DataFlavor[] flavors = new DataFlavor[2];
		flavors[0] = localModuleFlavor;
		flavors[1] = DataFlavor.stringFlavor;
		return flavors;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.datatransfer.Transferable#isDataFlavorSupported(java.awt.
	 * datatransfer.DataFlavor)
	 */
	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		Collection<DataFlavor> list = Arrays.asList(getTransferDataFlavors());
		return list.contains(flavor);
	}

}
