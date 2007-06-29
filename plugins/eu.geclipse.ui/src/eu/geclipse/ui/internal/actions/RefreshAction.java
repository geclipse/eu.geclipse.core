package eu.geclipse.ui.internal.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import eu.geclipse.core.model.IGridElement;

public class RefreshAction extends BaseSelectionListenerAction {
  
  private org.eclipse.ui.actions.RefreshAction resourcesRefresh;
  
  private IStructuredSelection lastElementSelection;

  protected RefreshAction( final Shell shell ) {
    super( Messages.getString("RefreshAction.refresh") ); //$NON-NLS-1$
    this.resourcesRefresh = new org.eclipse.ui.actions.RefreshAction( shell );
  }
  
  @Override
  public void run() {
    if ( this.resourcesRefresh.isEnabled() ) {
      this.resourcesRefresh.run();
    }
  }
  
  @Override
  protected boolean updateSelection( final IStructuredSelection selection ) {
    
    boolean result = false;
    
    IStructuredSelection resources = filterResources( selection );
    this.lastElementSelection = filterVirtualElements( selection );
    
    if ( resources.size() + this.lastElementSelection.size() == selection.size() ) {
      this.resourcesRefresh.selectionChanged( resources );
      result = this.resourcesRefresh.isEnabled();
    }
    
    return result;
    
  }
  
  private IStructuredSelection filterResources( final IStructuredSelection selection ) {
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
  }
  
  private IStructuredSelection filterVirtualElements( final IStructuredSelection selection ) {
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
  }
  
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
