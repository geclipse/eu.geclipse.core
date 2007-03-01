package eu.geclipse.core.model.impl;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import eu.geclipse.core.Extensions;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridElementCreator;

/**
 * Base implementation of the {@link IGridContainer} interface
 */
public abstract class AbstractGridContainer
    extends AbstractGridElement
    implements IGridContainer {
  
  private List< IGridElement > children
    = new ArrayList< IGridElement >();
  
  private boolean dirty;
  
  protected AbstractGridContainer( final IResource resource ) {
    super( resource );
    if ( !isLazy() ) {
      fetchChildren( null );
    } else {
      setDirty();
    }
  }
  
  public boolean contains( final IGridElement element ) {
    return this.children.contains( element  );
  }
  
  public IGridElement create( final IGridElementCreator creator )
      throws GridModelException {
    IGridElement newChild = creator.create( this );
    IGridElement addedElement = addElement( newChild );    
    return addedElement;
  }
  
  public void delete( final IGridElement child ) {
    this.children.remove( child );
    child.dispose();
  }
  
  @Override
  public void dispose() {
    deleteAll();
    super.dispose();
  }
  
  public int getChildCount() {
    return this.children.size();
  }

  public IGridElement[] getChildren( final IProgressMonitor monitor ) {
    if ( isLazy() && isDirty() ) {
      fetchChildren( monitor );
    }
    return this.children.toArray( new IGridElement[this.children.size()] );
  }

  public boolean hasChildren() {
    return isLazy() || !this.children.isEmpty();
  }
  
  public IGridElement findChild( final String name ) {
    IGridElement result = null;
    for ( IGridElement child : this.children ) {
      if ( child.getName().equals( name ) ) {
        result = child;
        break;
      } 
    }
    return result;
  }
  
  public IGridElement findChildWithResource( final String resourceName ) {
    IGridElement result = null;
    for ( IGridElement child : this.children ) {
      IResource resource = child.getResource();
      if ( resource != null ) {
        if ( resource.getName().equals( resourceName ) ) {
          result = child;
          break;
        }
      } 
    }
    return result;
  }
  
  public boolean isDirty() {
    return this.dirty;
  }
  
  public void setDirty() {
    setDirty( true );
  }
  
  protected IGridElement addElement( final IGridElement element ) {
    if ( element != null ) {
      IGridElement oldChild = findChild( element.getName() );
      if ( oldChild != null ) {
        delete( oldChild );
      }
      this.children.add( element );
    }
    return element;
  }
  
  protected boolean fetchChildren( final IProgressMonitor monitor ) {
    
    IProgressMonitor localMonitor
      = monitor != null
      ? monitor
      : new NullProgressMonitor();
    
    boolean result = false;
    deleteAll();
    IResource resource = getResource();
    if ( ( resource != null ) && ( resource instanceof IContainer ) ) {
      try {
        IResource[] members = ( ( IContainer ) resource ).members();
        localMonitor.beginTask( "Loading children", members.length );
        for ( IResource member : members ) {

          IGridElementCreator creator = findCreator( member );
          if ( creator != null ) {
            create( creator );
          }
          
          localMonitor.worked( 1 );
          if ( localMonitor.isCanceled() ) {
            break;
          }
          
        }
        result = true;
      } catch ( CoreException cExc ) {
        Activator.logException( cExc );
      }
    }
    
    localMonitor.done();
    setDirty( !result );
    
    return result;
    
  }
  
  protected void deleteAll() {
    for ( IGridElement child : this.children ) {
      child.dispose();
    }
    this.children.clear();
  }
  
  protected IGridElementCreator findCreator( final IResource resource ) {
    IGridElementCreator result = null;
    List<IGridElementCreator> creators
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
  
  protected IGridElementCreator findStandardCreator( final IResource resource ) {
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
  
  protected void setDirty( final boolean d ) {
    this.dirty = d;
  }

}
