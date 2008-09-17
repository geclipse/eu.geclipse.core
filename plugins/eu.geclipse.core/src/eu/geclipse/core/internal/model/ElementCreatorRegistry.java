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

public class ElementCreatorRegistry
    implements IElementCreatorRegistry, IRegistryEventListener {
  
  private static ElementCreatorRegistry singleton;
  
  private List< ElementCreatorReference > creators;
  
  private ElementCreatorRegistry() {
    init();
    Platform.getExtensionRegistry().addListener( this, Extensions.GRID_ELEMENT_CREATOR_POINT );
  }
  
  public static ElementCreatorRegistry getRegistry() {
    if ( singleton == null ) {
      singleton = new ElementCreatorRegistry();
    }
    return singleton;
  }
  
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

  public void added( final IExtensionPoint[] extensionPoints ) {
    // We do not listen to extension points but only to extensions 
  }
  
  public IGridElementCreator getCreator( final Object source,
                                         final Class< ? extends IGridElement > target ) {
    
    IGridElementCreator result = null;
    
    List< ElementCreatorReference > references = findReferences( source, target );
    if ( ! references.isEmpty() ) {
      for ( ElementCreatorReference reference : references ) {
        result = reference.getElementCreator();
        if ( result != null ) {
          result.setSource( source );
          break;
        }
      }
    }
    
    return result;
    
  }
  
  public List< IGridElementCreator > getCreators() {
    
    List< IGridElementCreator > result = new ArrayList< IGridElementCreator >();
    
    List< ElementCreatorReference > references = findReferences( null, null );
    if ( references != null ) {
      for ( ElementCreatorReference reference : references ) {
        IGridElementCreator creator = reference.getElementCreator();
        if ( creator != null ) {
          result.add( creator );
        }
      }
    }
    
    return result;
    
  }

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

  public void removed( final IExtensionPoint[] extensionPoints ) {
    // We do not listen to extension points but only to extensions
  }
  
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
  
  private List< ElementCreatorReference > findReferences( final Object source,
                                                          final Class< ? extends IGridElement > target ) {
    //System.out.print( "ElementCreatorRegistry#findReference( " + ( source == null ? "null" : source.getClass() ) + ", " + ( target == null ? "null" : target ) + " )" );
    List< ElementCreatorReference > result = new ArrayList< ElementCreatorReference >();
    
    for ( ElementCreatorReference reference : Collections.synchronizedCollection( this.creators ) ) {
      try {
        if ( reference.checkSource( source ) && reference.checkTarget( target ) ) {
          result.add( reference );
        }
      } catch ( ProblemException pExc ) {
        Activator.logException( pExc );
      }
    }
    System.out.println( result.getClass() );
    
    return result;
    
  }
  
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
