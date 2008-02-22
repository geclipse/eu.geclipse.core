/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
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

package eu.geclipse.core.internal.model.notify;

import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;

import eu.geclipse.core.Extensions;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.internal.model.GridRoot;
import eu.geclipse.core.internal.model.LocalFile;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridElementCreator;
import eu.geclipse.core.model.IGridModelEvent;
import eu.geclipse.core.model.IGridProject;

/**
 * The <code>GridElementLifecycleManager</code> is an implementation
 * of {@link IResourceDeltaVisitor} that is used to visit resources
 * deltas comming from resource change events. This class handles
 * the creation and deletion of Grid model elements and therefore
 * keeps the Grid model in sync with the resource tree.
 */
public class GridElementLifecycleManager
    implements IResourceDeltaVisitor {
  
  /**
   * Find an element creator for the specified {@link IResource}.
   * Searches first all externally registered creators and afterwards
   * the internal creators.
   * 
   * @param resource The resource for which to find a creator.
   * @return The creator or <code>null</code> if no such
   * creator could be found.
   * @see GridModel#getElementCreators()
   * @see GridModel#getStandardCreators()
   */
  public static IGridElementCreator findCreator( final IResource resource ) {
    IGridElementCreator result = null;
    List< IGridElementCreator > creators
      = Extensions.getRegisteredElementCreators();
    for ( IGridElementCreator creator : creators ) {
      if ( creator.canCreate( resource ) ) {
        result = creator;
        break;
      }
    }
    if ( result == null ) {
      result = findStandardCreator( resource );
    }
    return result;
  }
  
  /**
   * Find an element creator for the specified {@link IResource}.
   * Searches only the standard creators.
   * 
   * @param resource The resource for which to find a creator.
   * @return The creator or <code>null</code> if no such
   * creator could be found.
   * @see GridModel#getStandardCreators()
   */
  public static IGridElementCreator findStandardCreator( final IResource resource ) {
    IGridElementCreator result = null;
    List< IGridElementCreator > standardCreators
      = GridModel.getStandardCreators();
    for ( IGridElementCreator creator : standardCreators ) {
      if ( creator.canCreate( resource ) ) {
        result = creator;
        break;
      }
    }
    return result;
  }

  public boolean visit( final IResourceDelta delta )
      throws CoreException {
    
    switch ( delta.getKind() ) {
      case IResourceDelta.ADDED:
        resourceAdded( delta.getResource() );
        break;
      case IResourceDelta.REMOVED:
        resourceRemoved( delta.getResource() );
        break;
      case IResourceDelta.CHANGED:
        resourceChanged( delta.getResource(), delta.getFlags() );
        break;
    }
    
    return true;
    
  }
    
  private void elementChanged( final IGridElement element ) {
    
    int type = IGridModelEvent.ELEMENTS_CHANGED;
    IGridContainer source = element.getParent();
    IGridElement[] elements = new IGridElement[] { element };
    
    queueEvent( type, source, elements );
    
  }
  
  /**
   * Handle a project state changed, i.e. project opened or project closed.
   * 
   * @param project The project whose state has changed.
   */
  private void projectStateChanged( final IGridProject project ) {
    
    int type
      = project.isOpen()
      ? IGridModelEvent.PROJECT_OPENED
      : IGridModelEvent.PROJECT_CLOSED;
    IGridContainer source = project.getParent();
    IGridElement[] elements = new IGridElement[] { project };
    
    queueEvent( type, source, elements );
    
  }
  
  private void queueEvent( final int type,
                           final IGridElement source,
                           final IGridElement[] elements ) {
    
    IGridElement src = source == null ? GridModel.getRoot() : source;
    
    IGridModelEvent event = new GridModelEvent( type, src, elements );
    GridRoot.getGridNotificationService().queueEvent( event );
    
  }
  
  /**
   * Handle a resource added change.
   * 
   * @param resource The added resource.
   */
  private void resourceAdded( final IResource resource ) {
    if ( resource != null ) {
      IGridElementCreator creator = findCreator( resource );
      if ( creator != null ) {
        IContainer parent = resource.getParent();
        if ( parent != null ) {
          IGridContainer parentElement = ( IGridContainer ) GridRoot.getInstance().findElement( parent );
          if ( parentElement != null ) {
            try {
              parentElement.create( creator );
            } catch ( GridModelException gmExc ) {
              Activator.logException( gmExc );
            }
          }
        }
      }
    }
  }
  
  private void resourceChanged( final IResource resource,
                                final int flags ) {
    if ( resource != null ) {
      IGridElement element = GridRoot.getInstance().findElement( resource );
      if ( element != null ) {
        if ( ( element instanceof IGridProject ) && ( ( flags & IResourceDelta.OPEN ) != 0 ) ) {
          projectStateChanged( ( IGridProject ) element );
        } else if ( element instanceof LocalFile ) {
          LocalFile localFile = ( LocalFile ) element;
          if ( localFile.isProjectFoldersProperties() || localFile.isProjectProperties() ) {
            elementChanged( localFile.getProject() );
          } else {
            elementChanged( element );
          }
        } else {
          elementChanged( element );
        }
      }
    }
  }
  
  /**
   * Handle a resource removed change.
   * 
   * @param resource The removed resource.
   */
  private void resourceRemoved( final IResource resource ) {
    if ( resource != null ) {
      IGridElement element = GridRoot.getInstance().findElement( resource );
      if ( element != null ) {
        IGridContainer parent = element.getParent();
        if ( parent != null ) {
          try {
            parent.delete( element );
          } catch( GridModelException gmExc ) {
            Activator.logException( gmExc );
          }
        }
      }
    }
  }

}
