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
package eu.geclipse.core.sla.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import eu.geclipse.core.sla.ui.wizards.SLAQueryWizard;

/**
 * @author korn
 *
 */
public class SlaAction implements IWorkbenchWindowActionDelegate {

  IWorkbenchWindow window;
  IStructuredSelection selection;
  IWorkbench workbench;

  public void dispose() {
    // TODO Auto-generated method stub
  }

  public void init(IWorkbenchWindow window ) {
    this.window = window;
    this.workbench = window.getWorkbench();
    this.selection = ( IStructuredSelection )window.getSelectionService()
      .getSelection();
  }

  public void run( IAction action ) {
    SLAQueryWizard query = new SLAQueryWizard();
    WizardDialog wizard = new WizardDialog( this.window.getShell(),
                                            query );
    wizard.open();
  }

  public void selectionChanged( IAction action, ISelection selection ) {
    // TODO Auto-generated method stub
  }
}
