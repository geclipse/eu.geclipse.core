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
import eu.geclipse.core.model.IGridService;
import eu.geclipse.servicejob.ui.wizard.ServiceJobWizard;


/**
 * Action for testing grid service element.
 * 
 */
public class ServiceJobServiceAction implements IObjectActionDelegate {

  private IWorkbenchPart workbench;
  
  private List<IGridResource> serviceElements = new ArrayList<IGridResource>();

  private IGridProject selectedProject;
  
  
  public void run( final IAction action ) {
    if( this.serviceElements != null && ! this.serviceElements.isEmpty()) {
      
      ServiceJobWizard wizard = new ServiceJobWizard( this.selectedProject, this.serviceElements );
      WizardDialog dialog = new WizardDialog( this.workbench.getSite()
        .getShell(), wizard );
      dialog.open();
    }
  }

  public void selectionChanged( final IAction action, final ISelection selection )
  {
    this.serviceElements = new ArrayList<IGridResource>();
    if( selection instanceof IStructuredSelection ) {
      IStructuredSelection sselection = ( IStructuredSelection )selection;
      if( !sselection.isEmpty() && sselection.getFirstElement() instanceof IGridService ) {
          this.selectedProject = ( ( IGridService )sselection.getFirstElement() ).getProject();
          if( this.selectedProject != null ) {
            for( Object selObj : sselection.toList() ) {
              if( selObj instanceof IGridService
                  && ( ( IGridService )selObj ).getProject()
                    .equals( this.selectedProject ) )
              {
                this.serviceElements.add( ( IGridService )selObj );
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
