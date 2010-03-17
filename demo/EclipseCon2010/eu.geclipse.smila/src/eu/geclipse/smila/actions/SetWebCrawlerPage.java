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

import javax.xml.ws.WebEndpoint;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import eu.geclipse.aws.ec2.EC2Instance;
import eu.geclipse.smila.actions.util.JMXUtilCrawler;
import eu.geclipse.smila.actions.util.JMXUtilException;
import eu.geclipse.smila.actions.util.JMXUtilWebConfigAgent;

public class SetWebCrawlerPage implements IObjectActionDelegate {

		/** The {@link IWorkbenchPart} to use for opening the dialog. */
		private IWorkbenchPart workbenchPart;

		/** The list of selected {@link EC2Instance}s. */
		private List<EC2Instance> instanceList;

		/**
			   * 
			   */
		public SetWebCrawlerPage() {
			instanceList = new ArrayList<EC2Instance>();
		}

		@Override
		public void setActivePart(IAction action, IWorkbenchPart targetPart) {
			this.workbenchPart = targetPart;

		}

		@Override
		public void run(IAction action) {
			
			String webpage = "http://www.eclipse.org/geclipse" ;   // the default
			InputDialog input = new InputDialog(workbenchPart.getSite().getShell(), 
					"Web Crawler Configuration", 
					"Enter the webpage for SMILA to crawl", 
					webpage, 
					null) ; 
			if (input.open() == Window.OK ) {
	            // User clicked OK; update the label with the input
	            webpage = input.getValue();
			}
			
			// now the part for the JMX call is needed!
			
			String endpoint = null;
			for (int i = 0; i < instanceList.size(); i++) {
				EC2Instance inst = instanceList.get(i);
				endpoint = "service:jmx:rmi:///jndi/rmi://" + inst.getHostName()
						+ ":9004/jmxrmi";
			}

			//   set the Webpage with 
			
			JMXUtilWebConfigAgent crawler = new JMXUtilWebConfigAgent();
			try {
				crawler.invoke(endpoint, "SMILA:Agent=WebConfigAgent", webpage );
			} catch (JMXUtilException e) {
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
