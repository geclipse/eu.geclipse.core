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

package eu.geclipse.ui.internal.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;

/**
 * Refreshes all selected elements. Delegates the refresh of non-virtual
 * elements to {@link org.eclipse.ui.actions.RefreshAction}. Virtual
 * elements are currently not handled. Connections are only handled in the
 * first level of the hierarchy, i.e. only directly selected connection
 * elements are refreshed but not the complete file tree.
 */
public class RefreshAction extends BaseSelectionListenerAction {
  
  /**
   * The refresh action used to delegate non-virtual elements to.
   */
  private org.eclipse.ui.actions.RefreshAction resourcesRefresh;
  
  private List< IGridElement > virtualElements;
  
  /**
   * Construct a new refresh action.
   * 
   * @param shell The Shell for this action.
   */
  protected RefreshAction( final Shell shell ) {
    super( Messages.getString("RefreshAction.refresh") ); //$NON-NLS-1$
    this.resourcesRefresh = new org.eclipse.ui.actions.RefreshAction( shell );
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    
    if ( this.resourcesRefresh.isEnabled() ) {
      this.resourcesRefresh.run();
    }
    
    if ( ! this.virtualElements.isEmpty() ) {
      refreshElements( this.virtualElements );
    }
    
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org.eclipse.jface.viewers.IStructuredSelection)
   */
  @Override
  protected boolean updateSelection( final IStructuredSelection selection ) {
    
    boolean result = false;
    
    List< IResource > resources = new ArrayList< IResource >();
    this.virtualElements = new ArrayList< IGridElement >();
    result = filterSelection( selection, resources, this.virtualElements );
    
    if ( result ) {
      
      if ( ! resources.isEmpty() || ( resources.isEmpty() && this.virtualElements.isEmpty() ) ) {
        this.resourcesRefresh.selectionChanged( new StructuredSelection( resources ) );
        result = this.resourcesRefresh.isEnabled();
      }
      
      result |= resources.isEmpty() && ! this.virtualElements.isEmpty();
      
    }
    
    return result;
    
  }
  
  private boolean filterSelection( final IStructuredSelection selection,
                                   final List< IResource > resources,
                                   final List< IGridElement > elements ) {
    
    Iterator< ? > iter = selection.iterator();
    
    while ( iter.hasNext() ) {
      Object o = iter.next();
      IResource resource = getResource( o );
      if ( resource != null ) {
        resources.add( resource );
      } else if ( o instanceof IGridElement ) {
        elements.add( ( IGridElement ) o );
      }
    }
    
    return ( resources.size() + elements.size() ) == selection.size();
    
  }
  
  private void refreshElements( final List< IGridElement > elements ) {
    
    WorkspaceJob job = new WorkspaceJob( "Element Refresh" ) {
     
      @Override
      public IStatus runInWorkspace( final IProgressMonitor monitor )
          throws CoreException {
        
        SubMonitor sMonitor = SubMonitor.convert( monitor, "Refreshing virtual grid elements", elements.size() );
          
        for ( IGridElement element : elements ) {
          if ( element instanceof IGridContainer ) {
            IGridContainer container = ( IGridContainer ) element;
            container.setDirty();
            container.refresh( sMonitor.newChild( 1 ) );
          }
        }
        
        return Status.OK_STATUS;
        
      }
      
    };
    
    job.schedule();
    
  }
  
  /**
   * Searches for all connection elements in the specified selection
   * and activates them.
   * 
   * @param selection The selection to be searched for connection elements.
   */
  /*private void activateConnections( final IStructuredSelection selection ) {
    Iterator< ? > iter = selection.iterator();
    while ( iter.hasNext() ) {
      Object obj = iter.next();
      if ( obj instanceof IGridConnectionElement ) {
        IGridConnectionElement connection
          = ( IGridConnectionElement ) obj;
        connection.setDirty();
      }
    }
  }*/
  
  /**
   * Get all resources in the specified selection.
   * 
   * @param selection The selection to be searched for resources.
   * @return A selection containing the selected resources.
   */
  /*private IStructuredSelection filterResources( final IStructuredSelection selection ) {
    List< IResource > resources = new ArrayList< IResource >();
    Iterator< ? > iter = selection.iterator();
    while ( iter.hasNext() ) {
      Object next = iter.next();
      IResource resource = getResource( next );
      if ( resource != null ) {
        resources.add( resource );
      }
    }
    return new StructuredSelection( resources );
  }*/
  
  /**
   * Get all virtual elements in the specified selection.
   * 
   * @param selection The selection to be searched for virtual elements.
   * @return A selection containing the selected virtual elements.
   */
  /*private IStructuredSelection filterVirtualElements( final IStructuredSelection selection ) {
    List< IGridElement > elements = new ArrayList< IGridElement >();
    Iterator< ? > iter = selection.iterator();
    while ( iter.hasNext() ) {
      Object next = iter.next();
      if ( next instanceof IGridElement ) {
        IGridElement element = ( IGridElement ) next;
        if ( element.isVirtual() ) {
          elements.add( element );
        }
      }
    }
    return new StructuredSelection( elements );
  }*/
  
  /**
   * Try to obtain an {@link IResource} from the specified object.
   * 
   * @param o The object from which to obtain a resource.
   * @return An {@link IResource} or <code>null</code> if no resource
   * could be retrieved from the object.
   */
  private IResource getResource( final Object o ) {
    IResource resource = null;
    if ( o instanceof IResource ) {
      resource = ( IResource ) o;
    } else if ( o instanceof IAdaptable ) {
      resource = ( IResource )( ( IAdaptable ) o ).getAdapter( IResource.class );
    }
    return resource;
  }

}
