/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium 
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
 *     Szymon Mueller
 *           
 *****************************************************************************/
package eu.geclipse.servicejob.ui.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.IGridResource;
import eu.geclipse.core.model.IGridStorage;
import eu.geclipse.servicejob.ui.wizard.ServiceJobWizard;


/**
 * Action for testing grid storage element.
 *
 */
public class ServiceJobStorageAction implements IObjectActionDelegate {

  private IWorkbenchPart workbench;
  
  private List<IGridResource> storageElements = new ArrayList<IGridResource>();

  private IGridProject selectedProject;

  public void run( final IAction action ) {
    if( this.storageElements != null && !this.storageElements.isEmpty()) {
      
      ServiceJobWizard wizard = new ServiceJobWizard( this.selectedProject, this.storageElements );
      WizardDialog dialog = new WizardDialog( this.workbench.getSite().getShell(),
                                              wizard );
      dialog.open();
    }
  }

  public void selectionChanged( final IAction action, final ISelection selection )
  {
    this.storageElements = new ArrayList<IGridResource>();
    if( selection instanceof IStructuredSelection ) {
      IStructuredSelection sselection = ( IStructuredSelection )selection;
      if( !sselection.isEmpty()
          && sselection.getFirstElement() instanceof IGridStorage )
      {
        this.selectedProject = ( ( IGridStorage )sselection.getFirstElement() ).getProject();
        if( this.selectedProject != null ) {
          for( Object selObj : sselection.toList() ) {
            if( selObj instanceof IGridStorage
                && ( ( IGridStorage )selObj ).getProject()
                  .equals( this.selectedProject ) )
            {
              this.storageElements.add( ( IGridStorage )selObj );
            }
          }
        }
      }
    }
  }

  public void setActivePart( final IAction action, final IWorkbenchPart targetPart ) {
    this.workbench = targetPart;
  }
  
  
  
}
