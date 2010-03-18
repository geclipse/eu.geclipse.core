/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * 
 * Contributors:
 *    Harald Kornmayer - NEC Laboratories Europe
 *    
 *****************************************************************************/
package eu.geclipse.smila.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import eu.geclipse.aws.ec2.EC2Instance;

public class CopyHostname implements IObjectActionDelegate {

	/** The {@link IWorkbenchPart} to use for opening the dialog. */
	private IWorkbenchPart workbenchPart;

	/** The list of selected {@link EC2Instance}s. */
	private List<EC2Instance> instanceList;

	/**
		   * 
		   */
	public CopyHostname() {
		instanceList = new ArrayList<EC2Instance>();
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.workbenchPart = targetPart;

	}

	public void run(IAction action) {

		String hostname = null;
		for (int i = 0; i < instanceList.size(); i++) {
			EC2Instance inst = instanceList.get(i);
			hostname = inst.getHostName();
		}

		// create the clipboard and put the hostname!
		
		Display dis = Display.getCurrent();
		Clipboard cb = new Clipboard(dis);

		TextTransfer textTransfer = TextTransfer.getInstance();
		cb.setContents(new Object[] { hostname },
				new Transfer[] { textTransfer });
	}

	public void selectionChanged(IAction action, ISelection selection) {
		boolean enable = false;
		this.instanceList.clear();

		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			for (Object element : structuredSelection.toList()) {
				if (element instanceof EC2Instance) {
					EC2Instance ec2Instance = (EC2Instance) element;
					this.instanceList.add(ec2Instance);
				}
			}
		}
		if (this.instanceList.size() > 0) {
			enable = true;
		}
		action.setEnabled(enable);
	}
}
