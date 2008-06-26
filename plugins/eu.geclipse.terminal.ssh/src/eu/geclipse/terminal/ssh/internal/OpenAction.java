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
 * Contributors:
 *    Thomas Koeckerbauer GUP, JKU - initial API and implementation
 *****************************************************************************/

package eu.geclipse.terminal.ssh.internal;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import eu.geclipse.core.model.IGridComputing;

public class OpenAction implements IObjectActionDelegate {

  private IGridComputing computingElement;

  public OpenAction() {
    // empty constructor
  }

  public void setActivePart( final IAction action, final IWorkbenchPart targetPart ) {
    // unused
  }

  public void run( final IAction action ) {
    if ( this.computingElement != null ) {
      SSHWizard wizard = new SSHWizard( this.computingElement.getHostName() );
      WizardDialog dialog = new WizardDialog( null, wizard );
      dialog.open();
    }
  }

  public void selectionChanged( final IAction action, final ISelection selection ) {
    this.computingElement = null;
    if( selection instanceof IStructuredSelection ) {
      IStructuredSelection sselection = ( IStructuredSelection )selection;
      if( sselection.getFirstElement() instanceof IGridComputing ) {
        this.computingElement = ( IGridComputing )sselection.getFirstElement();
      }
    }
  }
}
