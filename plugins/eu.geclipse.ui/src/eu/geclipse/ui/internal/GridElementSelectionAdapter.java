package eu.geclipse.ui.internal;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import eu.geclipse.core.model.IGridElement;

public class GridElementSelectionAdapter
    implements ISelectionProvider, ISelectionChangedListener {
  
  private List< ISelectionChangedListener > listeners
    = new ArrayList< ISelectionChangedListener >();
  
  private ISelection currentSelection;

  public void addSelectionChangedListener( final ISelectionChangedListener listener ) {
    if ( ! this.listeners.contains( listener ) ) {
      this.listeners.add( listener );
    }
  }

  public ISelection getSelection() {
    return
      this.currentSelection == null
      ? new EmptySelection()
      : this.currentSelection;
  }

  public void removeSelectionChangedListener( final ISelectionChangedListener listener ) {
    this.listeners.remove( listener );
  }
  
  public void selectionChanged( final SelectionChangedEvent event ) {
    setSelection( event.getSelection() );
  }

  public void setSelection( final ISelection selection ) {
    if ( selection instanceof IStructuredSelection ) {
      setSelection( ( IStructuredSelection ) selection ); 
    }
  }
  
  public void setSelection( final IStructuredSelection selection ) {
    this.currentSelection = remapSelection( selection );
    fireSelectionChanged();
  }
  
  protected void fireSelectionChanged() {
    
    SelectionChangedEvent event
      = new SelectionChangedEvent( this, getSelection() );
    
    for ( ISelectionChangedListener listener : this.listeners ) {
      listener.selectionChanged( event );
    }
    
  }
  
  protected IStructuredSelection remapSelection( final IStructuredSelection selection ) {
    
    List< IResource > resources = new ArrayList< IResource >();
    
    for ( Object o : selection.toList() ) {
      IResource resource = remapObject( o );
      if ( resource != null ) {
        resources.add( resource );
      }
    }
    
    return new StructuredSelection( resources );
    
  }
  
  private IResource remapGridElement( final IGridElement e ) {
    return e.getResource();
  }
  
  private IResource remapObject( final Object o ) {
    
    IResource resource = null;
    
    if ( o instanceof IResource ) {
      resource = ( IResource ) o;
    } else if ( o instanceof IGridElement ) {
      resource = remapGridElement( ( IGridElement ) o );
    } else if ( o instanceof IAdaptable ) {
      resource = ( IResource )( ( IAdaptable ) o ).getAdapter( IResource.class );
    }
    
    return resource;
    
  }
  
}
