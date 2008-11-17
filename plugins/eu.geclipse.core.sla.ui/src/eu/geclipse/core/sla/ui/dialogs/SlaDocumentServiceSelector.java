/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     IT Research Division, NEC Laboratories Europe, NEC Europe Ltd. (http://www.it.neclab.eu)
 *     - Harald Kornmayer (harald.kornmayer@it.neclab.eu)
 *
 *****************************************************************************/
package eu.geclipse.core.sla.ui.dialogs;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.core.sla.ISLADocumentServiceSelector;
import eu.geclipse.core.sla.ui.Messages;

/**
 * Realisation of the graphical selection dialog for SLA service
 * implementations.
 * 
 * @author korn
 */
public class SlaDocumentServiceSelector implements ISLADocumentServiceSelector {

  public Object[] selectSLAImplementation() {
    Object[] result = null;
    // start a ListDialog to define the Service to be used.
    Shell shell = PlatformUI.getWorkbench()
      .getActiveWorkbenchWindow()
      .getShell();
    ListSlaServicesDialog listdialog = new ListSlaServicesDialog( shell );
    listdialog.setContentProvider( new SlaDocumentServiceContentProvider() );
    listdialog.setLabelProvider( new SlaDocumentServiceLabelProvider() );
    listdialog.setInput( eu.geclipse.core.sla.DocumentExtensions.getRegisteredSlaServiceNames()
      .toArray() );
    listdialog.setTitle( Messages.getString( "SlaServiceSelector.Title" ) ); //$NON-NLS-1$
    listdialog.create();
    listdialog.open();
    result = listdialog.getResult();
    return result;
  }
}
