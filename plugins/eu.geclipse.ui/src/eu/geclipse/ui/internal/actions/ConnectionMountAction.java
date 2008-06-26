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
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.internal.actions;

import java.net.URI;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.SelectionListenerAction;

import eu.geclipse.core.filesystem.GEclipseURI;
import eu.geclipse.core.model.IGridConnection;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.ui.dialogs.ProblemDialog;

/**
 * Mount action for directly mounting a folder from an
 * already mounted connection.
 */
public class ConnectionMountAction extends SelectionListenerAction {
  
  /**
   * Shell used for showing errors.
   */
  private Shell shell;
  
  /**
   * Standard constructor which just takes a {@link Shell}.
   * 
   * @param shell A {@link Shell} used to show error dialogs.
   */
  protected ConnectionMountAction( final Shell shell ) {
    super( Messages.getString("ConnectionMountAction.name") ); //$NON-NLS-1$
    this.shell = shell;
    setEnabled( false );
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    IStructuredSelection selection = getStructuredSelection();
    for ( Object o : selection.toArray() ) {
      if ( ( o instanceof IGridConnectionElement )
          && ! ( o instanceof IGridConnection ) ) {
        try {
          createMount( ( IGridConnectionElement ) o );
        } catch( CoreException cExc ) {
          ProblemDialog.openProblem(
              this.shell,
              Messages.getString("MountAction.problem_dialog_title"), //$NON-NLS-1$
              Messages.getString("MountAction.problem_dialog_text"), //$NON-NLS-1$
              cExc
          );
        }
      }
    }
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org.eclipse.jface.viewers.IStructuredSelection)
   */
  @Override
  protected boolean updateSelection( final IStructuredSelection selection ) {
    
    boolean enabled = ! selection.isEmpty() && super.updateSelection( selection );
    
    if ( enabled ) {
      for ( Object o : selection.toList() ) {
        if ( ( o instanceof IGridConnectionElement )
            && ! ( o instanceof IGridConnection ) ){
          if ( ! ( ( IGridConnectionElement ) o ).isFolder() ) {
            enabled = false;
            break;
          }
        } else {
          enabled = false;
          break;
        }
      }
    }
    
    return enabled;
    
  }
  
  /**
   * Create a mount for the specified connection. The connection
   * may not be an {@link IGridConnection} but only an
   * {@link IGridConnectionElement}.
   * 
   * @param connection The connection to be mounted separately.
   * @throws CoreException If the mount fails for some reason.
   */
  private void createMount( final IGridConnectionElement connection )
      throws CoreException {
    IGridProject project = connection.getProject();
    IGridContainer mountPoint = project.getProjectFolder( IGridConnection.class );
    IContainer container = ( IContainer ) mountPoint.getResource();
    if ( container != null ) {
      IPath path = new Path( getMountName( connection ) );
      IFolder folder = container.getFolder( path );
      URI accessToken = connection.getURI();
      GEclipseURI geclURI = new GEclipseURI( accessToken );
      URI masterURI = geclURI.toMasterURI();
      folder.createLink( masterURI, IResource.ALLOW_MISSING_LOCAL, null );
    }
  }

  /**
   * Create a reasonable name for the specified connection.
   * 
   * @param connection The {@link IGridConnectionElement} for
   * which to create a name.
   * @return The created name.
   */
  private String getMountName( final IGridConnectionElement connection ) {
    return connection.getName();
  }

}
