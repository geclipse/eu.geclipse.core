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
 *     Mariusz Wojtysiak - initial API and implementation
 *     
 *****************************************************************************/
package eu.geclipse.core.jobs;

import java.util.List;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.SubMonitor;

import eu.geclipse.core.filesystem.internal.filesystem.GEclipseFileStore;
import eu.geclipse.core.jobs.internal.Activator;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.impl.ResourceGridContainer;
import eu.geclipse.core.reporting.ProblemException;

/**
 * This is container connected with local {@link IFolder}, which contains links
 * to remote files.<br>
 * This container force calling fetchInfo() for all {@link GEclipseFileStore}
 * children during fetching it content (listing folder).<br>
 * Typically is used for Job folders "Input/Output Files".
 */
public class RemoteFilesContainer extends ResourceGridContainer {
  boolean constructed = false;

  public RemoteFilesContainer( final IResource resource ) {
    super( resource );
    this.constructed = true;
  }

  @Override
  public boolean isLazy() {
    return true;
  }

  @Override
  protected IStatus fetchChildren( final IProgressMonitor monitor ) {
    SubMonitor subMonitor = SubMonitor.convert( monitor );
    IStatus status = super.fetchChildren( monitor );
    
    if( this.constructed ) { // fetchChildren() is called also from constructor!
      fetchChildrenInfo( subMonitor );
    }
    
    return status;    
  }
  
  protected void fetchChildrenInfo( final SubMonitor monitor ) {
    try {
      List<IGridElement> currentChildren = getCachedChildren();
      monitor.setWorkRemaining( currentChildren.size() );
      for( IGridElement gridElement : currentChildren ) {
        if( gridElement instanceof IGridConnectionElement ) {
          IGridConnectionElement connectionElement = ( IGridConnectionElement )gridElement;
          IFileStore fileStore = connectionElement.getConnectionFileStore();

          if( fileStore instanceof GEclipseFileStore ) {
            GEclipseFileStore geclFileStore = ( GEclipseFileStore )fileStore;
            geclFileStore.setActive( GEclipseFileStore.FETCH_INFO_ACTIVE_POLICY );
            geclFileStore.fetchInfo( EFS.NONE, monitor.newChild( 1 ) );
          } else {
            monitor.worked( 1 );
          }
        }
      }
    } catch( ProblemException exception ) {
      Activator.logException( exception );
    } catch( CoreException exception ) {
      Activator.logException( exception );
    }    
  }

  @Override
  public boolean canContain( final IGridElement element ) {
    return element instanceof IGridConnectionElement;
  }
  
  
}
