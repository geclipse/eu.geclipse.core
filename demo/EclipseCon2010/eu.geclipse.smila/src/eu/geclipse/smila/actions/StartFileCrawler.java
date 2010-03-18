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
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import eu.geclipse.aws.ec2.EC2Instance;
import eu.geclipse.smila.actions.util.JMXUtilCrawler;
import eu.geclipse.smila.actions.util.JMXUtilException;

public class StartFileCrawler implements IObjectActionDelegate {

	/** The {@link IWorkbenchPart} to use for opening the dialog. */
	private IWorkbenchPart workbenchPart;

	/** The list of selected {@link EC2Instance}s. */
	private List<EC2Instance> instanceList;

	/**
		   * 
		   */
	public StartFileCrawler() {
		instanceList = new ArrayList<EC2Instance>();
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.workbenchPart = targetPart;

	}

	public void run(IAction action) {

		String endpoint = null;
		for (int i = 0; i < instanceList.size(); i++) {
			EC2Instance inst = instanceList.get(i);
			endpoint = "service:jmx:rmi:///jndi/rmi://" + inst.getHostName()
					+ ":9004/jmxrmi";

			String privDNS = inst.getPrivateDnsName();
			String DNS = inst.getDnsName();

			System.out.println(privDNS + " " + DNS);
		}

		// System.out.println("Start File Crawler for " + endpoint);

		JMXUtilCrawler crawler = new JMXUtilCrawler();
		try {
			crawler.invoke(endpoint, "SMILA:Agent=CrawlerController", "file");
		} catch (JMXUtilException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
