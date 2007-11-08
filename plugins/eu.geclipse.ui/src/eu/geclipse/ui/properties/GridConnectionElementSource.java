/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     Mariusz Wojtysiak - initial API and implementation
 *     
 *****************************************************************************/
package eu.geclipse.ui.properties;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

import eu.geclipse.core.filesystem.internal.filesystem.GEclipseFileStore;
import eu.geclipse.core.model.IGridConnection;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IPropertiesProvider;
import eu.geclipse.ui.internal.Activator;


/**
 * Property source for IGridConnectionElement
 */
public class GridConnectionElementSource extends AbstractPropertySource<IGridConnectionElement> {
  static private List<IProperty<IGridConnectionElement>> staticProperties;

  /**
   * @param sourceObject connection object, which properties will be displayed
   */
  public GridConnectionElementSource( final IGridConnectionElement sourceObject ) {
    super( sourceObject );
    
    try {
      if( sourceObject.getConnectionFileInfo() != null ) {
        addChildSource( new FileInfoSource( sourceObject.getConnectionFileInfo() ) );        
      }
    } catch ( CoreException cExc ) {
      Activator.logException( cExc );
    }
    
    // TODO mariusz/mateusz Don't use GEclipseFileStore. Rather add ConnectionElement#getRemoteFileStore()
    try {      
      IFileStore fileStore = sourceObject.getConnectionFileStore();
      if( fileStore instanceof GEclipseFileStore ) {
        GEclipseFileStore geclFS = (GEclipseFileStore) fileStore;
        IFileStore slaveFileStore = geclFS.getSlave();
        
        if( slaveFileStore instanceof IPropertiesProvider ) {
          IPropertiesProvider propProvider = ( IPropertiesProvider )slaveFileStore;
          addChildSource( new PropertiesProviderSource( propProvider ) );
        }
      }      
    } catch( CoreException exception ) {
      // ignore exceptions
    }
  }

  @Override
  protected Class<? extends AbstractPropertySource<?>> getPropertySourceClass()
  {
    return GridConnectionElementSource.class;
  }

  @Override
  protected List<IProperty<IGridConnectionElement>> getStaticProperties()
  {
    if( staticProperties == null ) {
      staticProperties = createProperties();
    }
    return staticProperties;
  }
  
  static private List<IProperty<IGridConnectionElement>> createProperties() {
    ArrayList<IProperty<IGridConnectionElement>> propertiesList = new ArrayList<IProperty<IGridConnectionElement>>();
    
    propertiesList.add( createUri() );    

    return propertiesList;
  }

  private static IProperty<IGridConnectionElement> createUri() {
    return new AbstractProperty<IGridConnectionElement>( Messages.getString( "GridConnectionElementSource.uri" ), null, false ) { //$NON-NLS-1$

      @Override
      public Object getValue( final IGridConnectionElement sourceObject ) {
        String uriString = null;
        IGridContainer parent = sourceObject;
        int lastRelativeSegments = 0;
        while( parent != null && !( parent instanceof IGridConnection ) ) {
          parent = parent.getParent();
          lastRelativeSegments++;
        }
        
        if( sourceObject == parent ) {
          URI uri = sourceObject.getURI();
          if( uri != null ) {
            uriString = uri.toString();
          }
        }
        else if( parent != null && parent instanceof IGridConnection ) {
          URI parentUri = ( ( IGridConnection )parent ).getURI();
          IPath localPath = sourceObject.getPath();
          if( lastRelativeSegments <= localPath.segmentCount() ) {
            IPath relativePath = localPath.removeFirstSegments( localPath.segmentCount()
                                                                - lastRelativeSegments );
            try {
              URI newUri = parentUri.resolve( relativePath.toString() );
              uriString = newUri.toString();
            } catch( IllegalArgumentException exception ) {
              // do nothing
            }
          }
        }
        return uriString;
      }
    };
  }  
}
