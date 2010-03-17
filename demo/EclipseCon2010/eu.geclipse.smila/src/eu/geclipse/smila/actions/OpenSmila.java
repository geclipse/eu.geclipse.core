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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;

import eu.geclipse.aws.ec2.EC2Instance;

public class OpenSmila implements IObjectActionDelegate {

	/** The {@link IWorkbenchPart} to use for opening the dialog. */
	private IWorkbenchPart workbenchPart;

	/** The list of selected {@link EC2Instance}s. */
	private List<EC2Instance> instanceList;

	/**
		   * 
		   */
	public OpenSmila() {
		instanceList = new ArrayList<EC2Instance>();
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.workbenchPart = targetPart;

	}

	@Override
	public void run(IAction action) {

		String endpoint = null;
		for (int i = 0; i < instanceList.size(); i++) {
			EC2Instance inst = instanceList.get(i);
			endpoint = "http://" + inst.getHostName()
					+ ":8080/SMILA/search";
		}

		IWebBrowser browser;

		try {
			browser = PlatformUI.getWorkbench().getBrowserSupport()
					.createBrowser("org.eclipse.ui.browser.view");
			browser.openURL(new URL(endpoint));

		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
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
