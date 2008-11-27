/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
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
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IRegistryEventListener;
import org.eclipse.core.runtime.Platform;

import eu.geclipse.core.ExtensionManager;
import eu.geclipse.core.Extensions;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.model.IElementCreatorRegistry;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridElementCreator;
import eu.geclipse.core.reporting.ProblemException;

/**
 * Internal implementation of the {@link IElementCreatorRegistry} interface that
 * is implemented as a singleton. At creation time it looks up all extensions
 * of the <code>eu.geclipse.core.gridElementCreator</code> extension point and
 * stores them internally. It registers itself as an extension listener to the
 * Platform and therefore is ready for hot-plugged plug-ins.
 */
public class ElementCreatorRegistry
    implements IElementCreatorRegistry, IRegistryEventListener {
  
  /**
   * The singleton instance.
   */
  private static ElementCreatorRegistry singleton;
  
  /**
   * All currently registered element creators.
   */
  private List< ElementCreatorReference > creators;
  
  /**
   * Private constructor.
   */
  private ElementCreatorRegistry() {
    init();
    Platform.getExtensionRegistry().addListener( this, Extensions.GRID_ELEMENT_CREATOR_POINT );
  }
  
  /**
   * Get the singleton instance of the registry.
   * 
   * @return The singleton.
   */
  public static ElementCreatorRegistry getRegistry() {
    if ( singleton == null ) {
      singleton = new ElementCreatorRegistry();
    }
    return singleton;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IElementCreatorRegistry#getConfigurations()
   */
  public List< IConfigurationElement > getConfigurations() {
    return getConfigurations( null, null );
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IElementCreatorRegistry#getConfigurations(java.lang.Class, java.lang.Class)
   */
  public List< IConfigurationElement > getConfigurations( final Class< ? extends Object > source,
                                                          final Class< ? extends IGridElement > target ) {

    List< IConfigurationElement > result = new ArrayList< IConfigurationElement >();
    
    List< ElementCreatorReference > references = findReferences( source, target );
    
    if ( ! references.isEmpty() ) {
      for ( ElementCreatorReference reference : references ) {
        IConfigurationElement configuration = reference.getConfigurationElement();
        if ( configuration != null ) {
          result.add( configuration );
        }
      }
    }
    
    return result;
    
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IElementCreatorRegistry#getCreator(java.lang.Class, java.lang.Class)
   */
  public IGridElementCreator getCreator( final Class< ? extends Object > source,
                                         final Class< ? extends IGridElement > target ) {
    return getCreator( ( Object ) source, target );
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IElementCreatorRegistry#getCreator(java.lang.Object, java.lang.Class)
   */
  public IGridElementCreator getCreator( final Object source,
                                         final Class< ? extends IGridElement > target ) {
    
    IGridElementCreator result = null;
    
    List< ElementCreatorReference > references = findReferences( source, target );
    int priority = -1;
    
    if ( ! references.isEmpty() ) {
      for ( ElementCreatorReference ref : references ) {
        try {
          int p = ref.checkSource( source );
          if ( p > priority ) {
            result = ref.getElementCreator();
            if ( result != null ) {
              priority = p;
            }
          }
        } catch ( ProblemException pExc ) {
          // Should never happen since this was already checked before in findReferences
          Activator.logException( pExc );
        }
      }
    }
    
    if ( ( result != null ) && ! ( source instanceof Class< ? > ) ) {
      result.setSource( source );
    }
    
    return result;
    
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IElementCreatorRegistry#getCreators()
   */
  public List< IGridElementCreator > getCreators() {
    return getCreators( null, null );    
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IElementCreatorRegistry#getCreators(java.lang.Class, java.lang.Class)
   */
  public List< IGridElementCreator > getCreators( final Class< ? extends Object > source,
                                                  final Class< ? extends IGridElement > target ) {
    return getCreators( ( Object ) source, target );
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IElementCreatorRegistry#getCreators(java.lang.Object, java.lang.Class)
   */
  public List< IGridElementCreator > getCreators( final Object source,
                                                  final Class< ? extends IGridElement > target ) {
    
    List< IGridElementCreator > result = new ArrayList< IGridElementCreator >();
    
    List< ElementCreatorReference > references = findReferences( source, target );
    
    if ( ! references.isEmpty() ) {
      for ( ElementCreatorReference reference : references ) {
        IGridElementCreator creator = reference.getElementCreator();
        if ( creator != null ) {
          if ( ! ( source instanceof Class< ? > ) ) {
            creator.setSource( source );
          }
          result.add( creator );
        }
      }
    }
    
    return result;
    
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.core.runtime.IRegistryEventListener#added(org.eclipse.core.runtime.IExtension[])
   */
  public void added( final IExtension[] extensions ) {
    for ( IExtension extension : extensions ) {
      IConfigurationElement[] elements
        = extension.getConfigurationElements();
      for ( IConfigurationElement element : elements ) {
        ElementCreatorReference creator = findReference( element );
        if ( creator == null ) {
          this.creators.add( new ElementCreatorReference( element ) );
        }
      }
    }
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.runtime.IRegistryEventListener#added(org.eclipse.core.runtime.IExtensionPoint[])
   */
  public void added( final IExtensionPoint[] extensionPoints ) {
    // We do not listen to extension points but only to extensions 
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.runtime.IRegistryEventListener#removed(org.eclipse.core.runtime.IExtension[])
   */
  public void removed( final IExtension[] extensions ) {
    for ( IExtension extension : extensions ) {
      IConfigurationElement[] elements
        = extension.getConfigurationElements();
      for ( IConfigurationElement element : elements ) {
        ElementCreatorReference reference = findReference( element );
        if ( reference != null ) {
          this.creators.remove( reference );
        }
      }
    }
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.runtime.IRegistryEventListener#removed(org.eclipse.core.runtime.IExtensionPoint[])
   */
  public void removed( final IExtensionPoint[] extensionPoints ) {
    // We do not listen to extension points but only to extensions
  }
  
  /**
   * Search for the {@link ElementCreatorReference} representing the specified
   * {@link IConfigurationElement}.
   * 
   * @param element The {@link IConfigurationElement} that should be represented
   * by the result.
   * @return The corresponding {@link ElementCreatorReference} or
   * <code>null</code> if no such reference is currently registered.
   */
  private ElementCreatorReference findReference( final IConfigurationElement element ) {
    
    ElementCreatorReference result = null;
    
    for ( ElementCreatorReference creator : this.creators ) {
      if ( creator.getConfigurationElement().equals( element ) ) {
        result = creator;
        break;
      }
    }
    
    return result;
    
  }
  
  /**
   * Searches all currently registered element creators for creators that are
   * able to create an element of the specified target type from an object of
   * the specified source type. Both the source and the target may be
   * <code>null</code> in which case the result list is not filtered in respect
   * to the null-argument. Specifying both arguments as <code>null</code> will
   * return a list of all currently registered element creators.
   * <p>
   * If no appropriate element creator could be found this method returns an
   * empty list.
   * 
   * @param source The source object for which to filter the result list. This
   * may also be a {@link Class} in which case the elements are not filtered by
   * an object but only by the type of this object. Therefore filtering for
   * patterns will not be available then. 
   * @param target The target element for which to filter the result list.
   * @return
   */
  private List< ElementCreatorReference > findReferences( final Object source,
                                                          final Class< ? extends IGridElement > target ) {
    
    List< ElementCreatorReference > result = new ArrayList< ElementCreatorReference >();
    
    for ( ElementCreatorReference reference : Collections.synchronizedCollection( this.creators ) ) {
      try {
        if ( ( reference.checkSource( source ) != -1 ) && reference.checkTarget( target ) ) {
          result.add( reference );
        }
      } catch ( ProblemException pExc ) {
        Activator.logException( pExc );
      }
    }
    
    return result;
    
  }
  
  /**
   * Initialize this registry, i.e. get all extensions of the
   * <code>eu.geclipse.core.gridElementCreator</code> extension point and
   * register them to this registry.
   */
  private void init() {
    this.creators = new ArrayList< ElementCreatorReference >();
    ExtensionManager manager = new ExtensionManager();
    List< IConfigurationElement > configurationElements
      = manager.getConfigurationElements( Extensions.GRID_ELEMENT_CREATOR_POINT,
                                          Extensions.GRID_ELEMENT_CREATOR_ELEMENT );
    for ( IConfigurationElement element : configurationElements ) {
      this.creators.add( new ElementCreatorReference( element ) );
    }
  }

}
