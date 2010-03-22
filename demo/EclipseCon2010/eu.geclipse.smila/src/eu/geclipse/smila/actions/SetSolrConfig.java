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
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import eu.geclipse.aws.ec2.EC2Instance;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.smila.actions.util.JMXUtilException;
import eu.geclipse.smila.actions.util.JMXUtilSolrPiplet;

public class SetSolrConfig implements IObjectActionDelegate {

	/** The {@link IWorkbenchPart} to use for opening the dialog. */
	private IWorkbenchPart workbenchPart;

	/** The list of selected {@link EC2Instance}s. */
	private List<EC2Instance> instanceList;

	/**
	 * 
	 */
	public SetSolrConfig() {
		instanceList = new ArrayList<EC2Instance>();
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.workbenchPart = targetPart;

	}

	public void run(IAction action) {

		// create a list with all selected nodes
		List<String> liste = new ArrayList<String>();

		for (int i = 0; i < instanceList.size(); i++) {
			EC2Instance inst = instanceList.get(i);

			String publicIP = inst.getHostName();
			String privateIP = inst.getPrivateDnsName();

			if (publicIP != null && privateIP != null) {
//				privateIP = convertPrivateIP(privateIP);
				System.out.println(i + " " + publicIP + "|" + privateIP);
				liste.add(publicIP + "|" + privateIP);
			}
		}

		int iSolrHost = 0;  // the default
		StringBuffer message = new StringBuffer("Enter the number for the node to configure: \n\n");
		for (int i = 0; i < liste.size(); i++) {
			message.append(" " + i + ": " + liste.get(i) + "\n");
		}
		
		String solrHostNb = "" + iSolrHost ; 
		
		InputDialog input = new InputDialog(workbenchPart.getSite().getShell(), 
				"Solr Remote Search Configuration", 
				message.toString(),
				solrHostNb,
				null) ; 
		if (input.open() == Window.OK ) {
            // User clicked OK; update the label with the input
            solrHostNb = input.getValue();
		}
		
		iSolrHost = Integer.parseInt(solrHostNb); 
		
		// now look for the JMX endpoint 
		String solrHost = liste.get(iSolrHost);
		solrHost = solrHost.substring(0, solrHost.indexOf("|"));

		String endpoint = "service:jmx:rmi:///jndi/rmi://" + solrHost
				+ ":9004/jmxrmi";

		// set the argument 
		StringBuffer hostnames = new StringBuffer("localhost,");		
		for (int i = 0; i < liste.size(); i++) {
			if (i!= iSolrHost) {
				String temp = liste.get(i); 
				temp = temp.substring(temp.indexOf("|")+1); 
				hostnames.append(temp + ","); 
			}
		}
			
		// set the solr hosts with JMX

		JMXUtilSolrPiplet solrpipelet = new JMXUtilSolrPiplet();
		try {
			solrpipelet.setShards(endpoint, "SMILA:Agent=SolrPipelet",
					hostnames.toString());
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

	private String convertPrivateIP(String privateIP) {
		String temp = privateIP.substring(0, privateIP.indexOf("."));
		String temp2 = temp.substring(temp.indexOf("-") + 1);
		String temp3 = temp2.replaceAll("-", ".");
		return temp3;
	}

}
