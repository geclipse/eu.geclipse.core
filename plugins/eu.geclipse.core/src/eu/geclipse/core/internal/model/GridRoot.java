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

package eu.geclipse.core.internal.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;

import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridElementCreator;
import eu.geclipse.core.model.IGridElementManager;
import eu.geclipse.core.model.IGridModelEvent;
import eu.geclipse.core.model.IGridModelListener;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.IGridRoot;
import eu.geclipse.core.model.IManageable;
import eu.geclipse.core.model.impl.ResourceGridContainer;

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
   * The list of {@link IGridModelListener}s.
   */
  private List< IGridModelListener > listeners
    = new ArrayList< IGridModelListener >();
  
  private List< IGridModelEvent > globalEventQueue
    = new ArrayList< IGridModelEvent >();
  
  /**
   * Private constructor to ensure to have only one instance of
   * this class. This can be obtained by {@link #getInstance()}.
   */
  private GridRoot() {
    super( ResourcesPlugin.getWorkspace().getRoot() );
    ResourcesPlugin.getWorkspace().addResourceChangeListener(
      new IResourceChangeListener() {
        public void resourceChanged( final IResourceChangeEvent event ) {
          setProcessEvents( false );
          handleResourceChange( event );
          setProcessEvents( true );
        }
      }
    );
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
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.ResourceGridContainer#getParent()
   */
  @Override
  public IGridContainer getParent() {
    return null;
  }
  
  /**
   * Get the singleton instance of this Grid root. If the singleton is
   * not yet instantiated this method will return <code>null</code>.
   * 
   * @return The singleton instance or <code>null</code> if the
   * singleton was not yet instatiated.
   */
  public final static GridRoot getRoot() {
    return singleton;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridModelNotifier#addGridModelListener(eu.geclipse.core.model.IGridModelListener)
   */
  public void addGridModelListener( final IGridModelListener listener ) {
    if ( !this.listeners.contains( listener ) ) {
      this.listeners.add( listener );
    }
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
    queueEvent( event );
  }
  
  @Override
  protected void setProcessEvents( final boolean enabled ) {

    super.setProcessEvents( enabled );
    
    if ( getProcessEvents() ) {
      processGlobalEvents();
    }
    
  }

  private void queueEvent( final IGridModelEvent event ) {
    
    IGridModelEvent newEvent = event;
    
    for ( IGridModelEvent ev : this.globalEventQueue ) {
      IGridModelEvent mergedEvent = mergeEvents( ev, event );
      if ( mergedEvent != null ) {
        this.globalEventQueue.remove( ev );
        newEvent = mergedEvent;
        break;
      }
    }
    
    this.globalEventQueue.add( newEvent );
    
    if ( getProcessEvents() ) {
      processGlobalEvents();
    }
    
  }
  
  private IGridModelEvent mergeEvents( final IGridModelEvent event1,
                                       final IGridModelEvent event2 ) {
    IGridModelEvent result = null;
    if ( ( event1.getType() == event2.getType() ) && ( event1.getSource() == event2.getSource() ) ) {
      IGridElement[] mergedElements
        = mergeElements( event1.getElements(), event2.getElements() );
      result = new GridModelEvent( event1.getType(), event1.getSource(), mergedElements );
    }
    return result;
  }
  
  private IGridElement[] mergeElements( final IGridElement[] elements1,
                                        final IGridElement[] elements2 ) {
    
    List< IGridElement > mergedElements
      = new ArrayList< IGridElement >();
    
    for ( IGridElement element : elements1 ) {
      if ( ! mergedElements.contains( element ) ) {
        mergedElements.add( element );
      }
    }
    
    for ( IGridElement element : elements2 ) {
      if ( ! mergedElements.contains( element ) ) {
        mergedElements.add( element );
      }
    }
    
    return mergedElements.toArray( new IGridElement[ mergedElements.size() ] );
    
  }
  
  private synchronized void processGlobalEvents() {
    if ( ( this.globalEventQueue != null ) && ( this.listeners != null ) ) {
      for ( IGridModelEvent event : this.globalEventQueue ) {
        for ( IGridModelListener listener : this.listeners ) {
          listener.gridModelChanged( event );
        }
      }
      this.globalEventQueue.clear();
    }
  }
  
  /**
   * Handle a resource change event.
   * 
   * @param event The event to be handled.
   */
  protected void handleResourceChange( final IResourceChangeEvent event ) {
    switch ( event.getType() ) {
      case IResourceChangeEvent.POST_CHANGE:
        handlePostChange( event.getDelta() );
        break;
    }
  }
  
  /**
   * Handle a post change.
   * 
   * @param delta The corresponding resource delta.
   */
  protected void handlePostChange( final IResourceDelta delta ) {
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
    IResourceDelta[] children = delta.getAffectedChildren();
    for ( IResourceDelta child : children ) {
      handlePostChange( child );
    }
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
      } catch( GridModelException gmExc ) {
        // Should never happen, therefore take no special measures
        Activator.logException( gmExc );
      }
    }
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridModelNotifier#removeGridModelListener(eu.geclipse.core.model.IGridModelListener)
   */
  public void removeGridModelListener( final IGridModelListener listener ) {
    this.listeners.remove( listener );
  }
  
  /**
   * Handle a project state changed, i.e. project opened or project closed.
   * 
   * @param project The project whose state has changed.
   */
  protected void projectStateChanged( final IGridProject project ) {
    
    int type
      = project.isOpen()
      ? IGridModelEvent.PROJECT_OPENED
      : IGridModelEvent.PROJECT_CLOSED;
    IGridContainer source = project.getParent();
    IGridElement[] elements = new IGridElement[] { project };
    
    IGridModelEvent event = new GridModelEvent( type, source, elements );
    fireGridModelEvent( event );
    
  }
  
  /**
   * Handle a resource added change.
   * 
   * @param resource The added resource.
   */
  protected void resourceAdded( final IResource resource ) {
    if ( resource != null ) {
      IGridElementCreator creator = findCreator( resource );
      if ( creator != null ) {
        IContainer parent = resource.getParent();
        if ( parent != null ) {
          IGridContainer parentElement = ( IGridContainer ) findElement( parent );
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
  
  /**
   * Handle a resource removed change.
   * 
   * @param resource The removed resource.
   */
  protected void resourceRemoved( final IResource resource ) {
    if ( resource != null ) {
      IGridElement element = findElement( resource );
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
  
  protected void resourceChanged( final IResource resource,
                                  final int flags ) {
    if ( resource != null ) {
      IGridElement element = findElement( resource );
      if ( element != null ) {
        if ( ( element instanceof IGridProject ) && ( ( flags & IResourceDelta.OPEN ) != 0 ) ) {
          projectStateChanged( ( IGridProject ) element );
        }
      }
    }
  }

}
