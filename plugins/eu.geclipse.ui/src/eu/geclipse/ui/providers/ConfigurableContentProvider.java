package eu.geclipse.ui.providers;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.runtime.IPath;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;

public class ConfigurableContentProvider
    extends GridModelContentProvider {
  
  public static final int MODE_FLAT = 1;
  
  public static final int MODE_HIERARCHICAL = 2;
  
  private int mode = MODE_FLAT;
  
  private Object rootElement;
  
  private List< IConfigurationListener > listeners
    = new ArrayList< IConfigurationListener >();
  
  public void addConfigurationListener( final IConfigurationListener listener ) {
    this.listeners.add( listener );
  }
  
  @Override
  public Object[] getChildren( final Object parentElement ) {
    Object[] children = super.getChildren( parentElement );
    if ( this.mode == MODE_HIERARCHICAL ) {
      children = filterChildren( children );
    }
    return children;
  }
  
  @Override
  public Object[] getElements( final Object inputElement ) {
    this.rootElement = inputElement;
    Object[] elements = super.getElements( inputElement );
    if ( this.mode == MODE_HIERARCHICAL ) {
      elements = remapElements( inputElement, elements );
    }
    return elements;
  }
  
  public void setMode( final int m ) {
    if ( this.mode != m ) {
      this.mode = m;
      fireConfigurationChanged();
    }
  }
  
  public int getMode() {
    return this.mode;
  }
  
  public void removeConfigurationListener( final IConfigurationListener listener ) {
    this.listeners.remove( listener );
  }
  
  protected void fireConfigurationChanged() {
    for ( IConfigurationListener listener : this.listeners ) {
      listener.configurationChanged( this );
    }
  }
  
  protected boolean isVisible( final IGridElement element ) {
    boolean result = false;
    Object[] rootChildren = super.getChildren( this.rootElement );
    IPath elementPath = element.getPath();
    for ( Object obj : rootChildren ) {
      if ( obj instanceof IGridElement ) {
        IPath rootPath = ( ( IGridElement ) obj ).getPath();
        if ( elementPath.isPrefixOf( rootPath ) || rootPath.isPrefixOf( elementPath ) ) {
          result = true;
          break;
        }
      }
    }
    return result;
  }
  
  private Object[] filterChildren( final Object[] children ) {
    List< Object > result = new ArrayList< Object >();
    if ( children != null ) {
      for ( Object child : children ) {
        if ( ( child instanceof IGridElement
            && isVisible( ( IGridElement ) child ) )
            || ( child instanceof ProgressTreeNode ) ) {
          result.add( child );
        }
      }
    }
    return result.toArray( new Object[ result.size() ] );
  }
  
  private Object[] remapElements( final Object parent,
                                  final Object[] elements ) {
    List< Object > result = new ArrayList< Object >();
    for ( Object element : elements ) {
      if ( ( element instanceof IGridElement ) && ( parent instanceof IGridContainer ) ) {
        element = remapElement( ( IGridContainer ) parent,
                                ( IGridElement ) element );
      }
      if ( !result.contains( element ) ) {
        result.add( element );
      }
    }
    return result.toArray( new Object[ result.size() ] );
  }
  
  private Object remapElement( final IGridContainer container,
                               final IGridElement element ) {
    Object result = element;
    IPath parentPath = container.getPath();
    IPath childPath = element.getPath();
    int childSegments = childPath.segmentCount();
    int matchingSegments = childPath.matchingFirstSegments( parentPath );
    if ( matchingSegments != childSegments - 1 ) {
      IPath remappedPath = childPath.removeLastSegments( childSegments - matchingSegments - 1 );
      result = GridModel.getRoot().findElement( remappedPath );
    }
    return result;
  }
    
}
