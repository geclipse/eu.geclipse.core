package eu.geclipse.core.internal.model;

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
import eu.geclipse.core.model.IGridRoot;
import eu.geclipse.core.model.IVoManager;
import eu.geclipse.core.model.impl.AbstractGridContainer;

public class GridRoot
    extends AbstractGridContainer
    implements IGridRoot {
  
  public GridRoot() {
    super( ResourcesPlugin.getWorkspace().getRoot() );
    ResourcesPlugin.getWorkspace().addResourceChangeListener(
      new IResourceChangeListener() {
        public void resourceChanged( final IResourceChangeEvent event ) {
          handleResourceChange( event );
        }
      }
    );
  }
  
  public IGridElement findElement( final IPath path ) {
    IGridElement element = null;
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
  
  public IGridElement findElement( final IResource resource ) {
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
  
  public IVoManager getVoManager() {
    return VoManager.getManager();
  }
  
  public boolean isLazy() {
    return false;
  }

  public boolean isLocal() {
    return true;
  }

  public boolean isVirtual() {
    return false;
  }

  public void handleResourceChange( final IResourceChangeEvent event ) {
    switch ( event.getType() ) {
      case IResourceChangeEvent.POST_CHANGE:
        handlePostChange( event.getDelta() );
        break;
    }
  }
  
  protected void handlePostChange( final IResourceDelta delta ) {
    switch ( delta.getKind() ) {
      case IResourceDelta.ADDED:
        resourceAdded( delta.getResource() );
        break;
      case IResourceDelta.REMOVED:
        resourceRemoved( delta.getResource() );
        break;
    }
    IResourceDelta[] children = delta.getAffectedChildren();
    for ( IResourceDelta child : children ) {
      handlePostChange( child );
    }
  }
  
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
  
  protected void resourceRemoved( final IResource resource ) {
    if ( resource != null ) {
      IGridElement element = findElement( resource );
      if ( element != null ) {
        IGridContainer parent = element.getParent();
        if ( parent != null ) {
          parent.delete( element );
        }
      }
    }
  }

}
