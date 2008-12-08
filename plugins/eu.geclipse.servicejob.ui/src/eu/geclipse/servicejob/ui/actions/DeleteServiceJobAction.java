/******************************************************************************
 * Copyright (c) 2007-2008 g-Eclipse consortium 
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
 *     PSNC:
 *      - Szymon Mueller
 *      - Katarzyna Bylec (katis@man.poznan.pl)
 *           
 *****************************************************************************/

package eu.geclipse.servicejob.ui.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.core.model.IServiceJob;
import eu.geclipse.ui.dialogs.ProblemDialog;


/**
 * Action for deleting Operator's Job from workspace.
 *
 */
public class DeleteServiceJobAction implements IObjectActionDelegate {

  private List<IServiceJob> serviceJobList;

  public void run( final IAction action ) {
    if ( this.serviceJobList != null ) {
      IContainer cont = null;
      try {
        for ( IServiceJob sjToDelete : this.serviceJobList ) {
          if ( sjToDelete.getResource() != null ) {
            cont = sjToDelete.getResource().getParent();
          }
          if ( cont != null ) {
            sjToDelete.getResource().refreshLocal( 1, null );
            sjToDelete.dispose();
            cont.refreshLocal( 1, null );
            cont.delete( true, new NullProgressMonitor() );
          }
        }
      } catch ( CoreException e ) {
        ProblemDialog.openProblem( PlatformUI.getWorkbench()
                                   .getActiveWorkbenchWindow()
                                   .getShell(),
                                   "Error when deleting service job",
                                   "Error when deleting service job's files",
                                   e );
      }
    }
  }

  public void selectionChanged( final IAction action, final ISelection selection )
  {
    this.serviceJobList = new ArrayList<IServiceJob>();
    if( selection instanceof IStructuredSelection ) {
      IStructuredSelection sselection = ( IStructuredSelection )selection;
      for( Object obj : sselection.toArray() ) {
        if( obj instanceof IServiceJob ) {
          this.serviceJobList.add( ( IServiceJob )obj );
        }
      }
    }
  }

  public void setActivePart( final IAction action,
                             final IWorkbenchPart targetPart )
  {
    //empty implementation
  }
}
