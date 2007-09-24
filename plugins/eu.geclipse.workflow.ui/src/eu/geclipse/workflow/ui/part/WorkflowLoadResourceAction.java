/*******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Initial development of the original code was made for the g-Eclipse project 
 * funded by European Union project number: FP6-IST-034327 
 * http://www.geclipse.eu/
 *  
 * Contributors:
 *     RUR (http://acet.rdg.ac.uk/)
 *     - Ashish Thandavan - initial API and implementation
 ******************************************************************************/
package eu.geclipse.workflow.ui.part;

import org.eclipse.emf.edit.ui.action.LoadResourceAction.LoadResourceDialog;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import eu.geclipse.workflow.ui.edit.parts.WorkflowEditPart;

/**
 * @generated
 */
public class WorkflowLoadResourceAction implements IObjectActionDelegate {

  /**
   * @generated
   */
  private WorkflowEditPart mySelectedElement;
  /**
   * @generated
   */
  private Shell myShell;

  /**
   * @generated
   */
  public void setActivePart( IAction action, IWorkbenchPart targetPart ) {
    myShell = targetPart.getSite().getShell();
  }

  /**
   * @generated
   */
  public void run( IAction action ) {
    LoadResourceDialog loadResourceDialog = new LoadResourceDialog( myShell,
                                                                    mySelectedElement.getEditingDomain() );
    loadResourceDialog.open();
  }

  /**
   * @generated
   */
  public void selectionChanged( IAction action, ISelection selection ) {
    mySelectedElement = null;
    if( selection instanceof IStructuredSelection ) {
      IStructuredSelection structuredSelection = ( IStructuredSelection )selection;
      if( structuredSelection.size() == 1
          && structuredSelection.getFirstElement() instanceof WorkflowEditPart )
      {
        mySelectedElement = ( WorkflowEditPart )structuredSelection.getFirstElement();
      }
    }
    action.setEnabled( isEnabled() );
  }

  /**
   * @generated
   */
  private boolean isEnabled() {
    return mySelectedElement != null;
  }
}
