/*****************************************************************************
 * Copyright (c) 2006-2008 g-Eclipse Consortium 
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
 *    Ariel Garcia      - updated to new problem reporting
 *****************************************************************************/

package eu.geclipse.core.model.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import eu.geclipse.core.Extensions;
import eu.geclipse.core.ICoreProblems;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridElementCreator;
import eu.geclipse.core.model.IGridService;
import eu.geclipse.core.model.IStorableElement;
import eu.geclipse.core.reporting.ProblemException;


/**
 * Properties for the {@link GenericVirtualOrganization}.
 */
public class GenericVoProperties
    extends AbstractGridElement
    implements IStorableElement {
  
  /**
   * Name to reference the properties.
   */
  public static final String NAME = ".generic_vo_properties"; //$NON-NLS-1$

  private static final String FIELD_SEPARATOR = " "; //$NON-NLS-1$
  
  private GenericVirtualOrganization vo;
  
  GenericVoProperties( final GenericVirtualOrganization vo ) {
    this.vo = vo;
  }

  public IFileStore getFileStore() {
    return getParent().getFileStore().getChild( getName() );
  }

  public String getName() {
    return NAME;
  }

  public IGridContainer getParent() {
    return this.vo;
  }

  public IPath getPath() {
    return getParent().getPath().append( getName() );
  }

  public IResource getResource() {
    return null;
  }

  public boolean isLocal() {
    return true;
  }
  
  public void load() throws ProblemException {
    
    IFileStore fileStore = getFileStore();
    List< IConfigurationElement > elements
      = Extensions.getRegisteredElementCreatorConfigurations( URI.class, IGridService.class );
    
    try {
      
      InputStream iStream = fileStore.openInputStream( EFS.NONE, null );
      InputStreamReader isReader = new InputStreamReader( iStream );
      BufferedReader bReader = new BufferedReader( isReader );
      
      String line;
      for ( ; ; ) {
        
        line = bReader.readLine();
        
        if ( line == null ) {
          break;
        }
        
        String[] parts = line.split( FIELD_SEPARATOR );

        if ( parts.length == 2 ) {
          
          String serviceType = parts[ 0 ];
          URI serviceURI = new URI( parts[ 1 ] );
          IGridElementCreator creator = null;
          
          for ( IConfigurationElement element : elements ) {
            String contributor = element.getContributor().getName();
            Bundle bundle = Platform.getBundle( contributor );
            try {
              Class< ? > serviceClass = bundle.loadClass( serviceType );
              IConfigurationElement[] targetElements = element.getChildren( Extensions.GRID_ELEMENT_CREATOR_TARGET_ELEMENT );
              for ( IConfigurationElement targetElement : targetElements ) {
                String targetType = targetElement.getAttribute( Extensions.GRID_ELEMENT_CREATOR_TARGET_CLASS_ATTRIBUTE );
                Class< ? > targetClass = bundle.loadClass( targetType );
                if ( targetClass.isAssignableFrom( serviceClass ) ) {
                  creator = ( IGridElementCreator ) element.createExecutableExtension( Extensions.GRID_ELEMENT_CREATOR_EXECUTABLE );
                  if ( creator.canCreate( serviceURI ) ) {
                    break;
                  }
                  creator = null;
                }
              }
              if ( creator != null ) {
                break;
              }
            } catch ( ClassNotFoundException cnfExc ) {
              // Just ignore and go on
            }
          }
          
          if ( creator != null ) {
            this.vo.create( creator );
          }
          
        }
          
      }
      
    } catch ( CoreException cExc ) {
      throw new ProblemException( ICoreProblems.MODEL_ELEMENT_LOAD_FAILED,
                                  cExc,
                                  Activator.PLUGIN_ID );
    } catch ( IOException ioExc ) {
      throw new ProblemException( ICoreProblems.MODEL_ELEMENT_LOAD_FAILED,
                                  ioExc,
                                  Activator.PLUGIN_ID );
    } catch ( URISyntaxException uriExc ) {
      throw new ProblemException( ICoreProblems.MODEL_ELEMENT_LOAD_FAILED,
                                  uriExc,
                                  Activator.PLUGIN_ID );
    }
    
  }

  public void save() throws ProblemException {
    
    IFileStore fileStore = getFileStore();
    IGridElement[] children = this.vo.getChildren( null );
    
    try {
      
      OutputStream oStream = fileStore.openOutputStream( EFS.NONE, null );
      OutputStreamWriter osWriter = new OutputStreamWriter( oStream );
      BufferedWriter bWriter = new BufferedWriter( osWriter );
      bWriter.write( this.vo.getName() );
      
      for ( IGridElement child : children ) {
        if ( child instanceof IGridService ) {
          URI uri = ( ( IGridService ) child ).getURI();
          bWriter.newLine();
          bWriter.write( child.getClass().getName() + FIELD_SEPARATOR );
          bWriter.write( uri.toString() );
        }
      }
      
      bWriter.close();
      
    } catch ( CoreException cExc ) {
      throw new ProblemException( ICoreProblems.MODEL_ELEMENT_SAVE_FAILED,
                                  cExc,
                                  Activator.PLUGIN_ID );
    } catch ( IOException ioExc ) {
      throw new ProblemException( ICoreProblems.MODEL_ELEMENT_SAVE_FAILED,
                                  ioExc,
                                  Activator.PLUGIN_ID );
    }
    
  }

}
