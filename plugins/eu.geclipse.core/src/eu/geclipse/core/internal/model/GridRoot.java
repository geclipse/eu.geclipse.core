/*****************************************************************************
 * Copyright (c) 2006-2008 g-Eclipse Consortium 
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

package eu.geclipse.core.internal.model;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;

import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.internal.model.notify.GridNotificationService;
import eu.geclipse.core.internal.model.notify.ResourceNotificationService;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridElementManager;
import eu.geclipse.core.model.IGridModelEvent;
import eu.geclipse.core.model.IGridModelListener;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.IGridRoot;
import eu.geclipse.core.model.IManageable;
import eu.geclipse.core.model.impl.ResourceGridContainer;
import eu.geclipse.core.reporting.ProblemException;


/**
 * Core implementation of the {@link IGridRoot} interface.
 * This Grid root is associated with the underlying
 * {@link org.eclipse.core.resources.IWorkspaceRoot}. 
 */
public final class GridRoot
    extends ResourceGridContainer
    implements IGridRoot {
  
  /**
   * The singleton instance.
   */
  private static GridRoot singleton;
  
  /**
   * Private constructor to ensure to have only one instance of
   * this class. This can be obtained by {@link #getInstance()}.
   */
  private GridRoot() {
    super( ResourcesPlugin.getWorkspace().getRoot() );
    addGridModelListener( JobManager.getManager() );
    addGridModelListener( ServiceJobManager.getManager() );
    getGridNotificationService();
    getResourceNotificationService();
  }
  
  /**
   * Get the singleton instance of this Grid root. If the singleton is
   * not yet instantiated this method will instantiate it.
   * 
   * @return The singleton instance.
   */
  public final static GridRoot getInstance() {
    if ( singleton == null ) {
      singleton = new GridRoot();
    }
    return singleton;
  }
  
  /**
   * Get the {@link GridNotificationService} that is used to
   * notify model listeners about changes in the model.
   * 
   * @return The models notification service.
   */
  public static GridNotificationService getGridNotificationService() {
    return GridNotificationService.getInstance();
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.ResourceGridContainer#getParent()
   */
  @Override
  public IGridContainer getParent() {
    return null;
  }
  
  /**
   * Get the {@link ResourceNotificationService} that is used to
   * notify the model about changes in the resource tree.
   * 
   * @return The resource notification service.
   */
  public static ResourceNotificationService getResourceNotificationService() {
    return ResourceNotificationService.getInstance();
  }
  
  /**
   * Get the singleton instance of this Grid root. If the singleton is
   * not yet instantiated this method will return <code>null</code>.
   * 
   * @return The singleton instance or <code>null</code> if the
   * singleton was not yet instantiated.
   */
  public final static GridRoot getRoot() {
    return singleton;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridModelNotifier#addGridModelListener(eu.geclipse.core.model.IGridModelListener)
   */
  public void addGridModelListener( final IGridModelListener listener ) {
    getGridNotificationService().addListener( listener );
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridContainer#canContain(eu.geclipse.core.model.IGridElement)
   */
  @Override
  public boolean canContain( final IGridElement element ) {
    return element instanceof IGridProject;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridRoot#findElement(org.eclipse.core.runtime.IPath)
   */
  public final IGridElement findElement( final IPath path ) {
    IGridElement element = path.isRoot() ? this : null;
    String[] segments = path.segments();
    IGridContainer container = this;
    for ( String segment : segments ) {
      element = container.findChild( segment );
      if ( element instanceof IGridContainer ) {
        container = ( IGridContainer ) element;
      } else {
        break;
      }
    }
    return element;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridRoot#findElement(org.eclipse.core.resources.IResource)
   */
  public final IGridElement findElement( final IResource resource ) {
    IGridElement result = null;
    IPath path = resource.getFullPath();
    if ( ( path.segmentCount() == 0 ) && !path.isEmpty() ) {
      result = GridModel.getRoot();
    } else {
      IGridContainer parent = this;
      for ( int i = 0 ; i < path.segmentCount() ; i++) {
        String name = path.segment( i );
        IGridElement element = parent.findChildWithResource( name );
        if ( i == path.segmentCount() - 1 ) {
          result = element;
        } else if ( element instanceof IGridContainer ) {
          parent = ( IGridContainer ) element;
        } 
      }
    }
    return result;
  }
  
  /**
   * Distribute the specified event to all registered
   * {@link IGridModelListener}s.
   * 
   * @param event The event to be distributed.
   */
  public void fireGridModelEvent( final IGridModelEvent event ) {
    getGridNotificationService().queueEvent( event );
  }
  
  /**
   * This method is used to register any new element appearing in the model.
   * It takes care that managed elements are added to their manager.
   * 
   * @param element The element that appeared somewhere in the model.
   */
  public static void registerElement( final IGridElement element ) {
    if ( element instanceof IManageable ) {
      IGridElementManager manager
        = ( ( IManageable ) element ).getManager();
      try {
        manager.addElement( element );
      } catch ( ProblemException pExc ) {
        // Should never happen, therefore take no special measures
        Activator.logException( pExc );
      }
    }
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridModelNotifier#removeGridModelListener(eu.geclipse.core.model.IGridModelListener)
   */
  public void removeGridModelListener( final IGridModelListener listener ) {
    getGridNotificationService().removeListener( listener );
  }
  
}
