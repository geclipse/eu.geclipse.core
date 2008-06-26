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

package eu.geclipse.ui.providers;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.runtime.IPath;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;

/**
 * A {@link GridModelContentProvider} that may provide the content
 * either in a flat mode or in a hierarchical mode. In flat mode all
 * elements will be represented as direct children of the root node.
 * In hierarchical mode the elements are represented in their normal
 * hierarchical structure as provided by the Grid model.
 */
public class ConfigurableContentProvider
    extends GridModelContentProvider {
  
  /**
   * Static field that denotes the flat representation mode.
   */
  public static final int MODE_FLAT = 1;
  
  /**
   * Static field that denotes the hierarchical representation mode.
   */
  public static final int MODE_HIERARCHICAL = 2;
  
  /**
   * The current mode.
   */
  private int mode = MODE_FLAT;
  
  /**
   * The root element of the underlying model.
   */
  private Object rootElement;
  
  /**
   * The list of currently registered listeners.
   */
  private List< IConfigurationListener > listeners
    = new ArrayList< IConfigurationListener >();
  
  /**
   * Add an {@link IConfigurationListener} to the list of currently
   * registered listeners.
   * 
   * @param listener The new {@link IConfigurationListener} to be added.
   */
  public void addConfigurationListener( final IConfigurationListener listener ) {
    this.listeners.add( listener );
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.ui.providers.GridModelContentProvider#getChildren(java.lang.Object)
   */
  @Override
  public Object[] getChildren( final Object parentElement ) {
    Object[] children = super.getChildren( parentElement );
    if ( this.mode == MODE_HIERARCHICAL ) {
      children = filterChildren( children );
    }
    return children;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.ui.providers.GridModelContentProvider#getElements(java.lang.Object)
   */
  @Override
  public Object[] getElements( final Object inputElement ) {
    this.rootElement = inputElement;
    Object[] elements = super.getElements( inputElement );
    switch( this.mode ) {
      case MODE_HIERARCHICAL:
        elements = remapElements( inputElement, elements );
        break;
      case MODE_FLAT:
        elements = filterHierarchicalChildrens( elements );
        break;
    }
    return elements;
  }
  
  /**
   * Set the current mode of this {@link ConfigurableContentProvider}.
   * 
   * @param m The new mode, i.e. either <code>MODE_FLAT</code>
   * or <code>MODE_HIERARCHICAL</code>.
   */
  public void setMode( final int m ) {
    if ( this.mode != m ) {
      this.mode = m;
      fireConfigurationChanged();
    }
  }
  
  /**
   * Get the current mode of this {@link ConfigurableContentProvider}.
   * 
   * @return Either <code>MODE_FLAT</code>
   * or <code>MODE_HIERARCHICAL</code>.
   */
  public int getMode() {
    return this.mode;
  }
  
  /**
   * Remove the specified {@link IConfigurationListener} from the
   * list of listeners.
   * 
   * @param listener The listener to be removed.
   */
  public void removeConfigurationListener( final IConfigurationListener listener ) {
    this.listeners.remove( listener );
  }
  
  /**
   * Notify all currently registered {@link IConfigurationListener}s
   * that this {@link ConfigurableContentProvider} has changed its
   * mode.
   */
  protected void fireConfigurationChanged() {
    for ( IConfigurationListener listener : this.listeners ) {
      listener.configurationChanged( this );
    }
  }
  
  /**
   * Determine if the specified element is currently visible.
   * 
   * @param element The element to be tested.
   * @return True if the element should be displayed.
   */
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
  
  /**
   * Checks the specified list of children if they are visible
   * or not. Creates a new array that only holds the visible
   * children.
   * 
   * @param children The children to be tested.
   * @return A new array containing all visible children. This
   * array may be empty if none of the provided children is
   * actually visible.
   * @see #isVisible(IGridElement)
   */
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
  
  /**
   * Remaps the specified elements with respect to the specified parent.
   * 
   * @param parent The reference object for the remapping.
   * @param elements The elements to be remapped.
   * @return the remapped elements.
   * @see #remapElement(IGridContainer, IGridElement) 
   */
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
  
  /**
   * Remaps the specified element with respect to the specified container.
   * 
   * @param container The reference container for the remapping.
   * @param element The element to be remapped.
   * @return The element that is a direct or indirect parent of the
   * specified element and is a direct child of the specified container.
   */
  protected Object remapElement( final IGridContainer container,
                                 final IGridElement element ) {
    IGridElement result = element;
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
  
  /**
   * Removes elements, which parent already is on element list (this elements
   * will be visibled as children)
   * 
   * @param elements
   * @return
   */
  private Object[] filterHierarchicalChildrens( final Object[] elements ) {
    List< Object > result = new ArrayList< Object >();
    
    for( Object object : elements ) {
      
      if( object instanceof IGridElement ) {
        IGridElement gridElement = ( IGridElement )object;
        IGridContainer parent = gridElement.getParent();
        if( !isOnList( elements, parent ) ) {
          result.add( object );
        }
      } else if ( object instanceof ProgressTreeNode ) {
        result.add( object );
      }
      
    }
    
    return result.toArray( new Object[ result.size() ] );
    
  }

  private boolean isOnList( final Object[] elements, final Object object ) {    
    boolean onList = false;
    
    for( Object curObject : elements ) {
      if( curObject == object ) {
        onList = true;
        break;
      }
    }
    return onList;
  }

    
}
