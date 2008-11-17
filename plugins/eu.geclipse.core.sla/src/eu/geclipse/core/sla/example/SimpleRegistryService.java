/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium
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
 *     IT Research Division, NEC Laboratories Europe, NEC Europe Ltd. (http://www.it.neclab.eu)
 *     - Harald Kornmayer (harald.kornmayer@it.neclab.eu)
 *
 *****************************************************************************/
package eu.geclipse.core.sla.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Preferences;

import eu.geclipse.core.sla.ISLAService;
import eu.geclipse.core.sla.model.SltContainer;
import eu.geclipse.core.sla.model.SimpleTermModel;
import eu.geclipse.core.sla.preferences.PreferenceConstants;

/**
 * The exemplary implementation of the SLAService interface.
 * 
 * @author korn
 */
public class SimpleRegistryService implements ISLAService {

  private URI registryURI;

  /**
   * 
   */
  public SimpleRegistryService() {
    // just the constructor
  }

  public void setRegistryURI( final String registryURI )
    throws URISyntaxException
  {
    this.registryURI = new URI( registryURI );
  }

  /**
   * 
   */
  public void publishSLT( final IFile file ) {
    // the registryURI is expected to be an endpoint
    // to the file system (i.e. file:/c:/SLAregistry
    try {
      IFileStore source = EFS.getStore( file.getLocationURI() );
      IFileStore targetStore = EFS.getStore( this.registryURI )
        .getChild( file.getName() );
      source.copy( targetStore, EFS.OVERWRITE, null );
    } catch( CoreException e ) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public Object[] queryRegistry( final String terms ) {
    ArrayList<SltContainer> results = new ArrayList<SltContainer>( 10 );
    // we look in the registry (directory) for the files which contains the
    // term for the service name
    // and concatenate all files containing the services names.
    // 
    try {
      Preferences prefs = eu.geclipse.core.sla.Activator.getDefault()
        .getPluginPreferences();
      String registryURIString = prefs.getString( PreferenceConstants.pRegistryURI );
      this.setRegistryURI( registryURIString );
      IFileStore sourceStore = EFS.getStore( this.registryURI );
      IFileStore[] liste = sourceStore.childStores( EFS.NONE, null );
      for( int i = 0; i < liste.length; i++ ) {
        StringBuilder result = new StringBuilder( 1024 );
        liste[ i ].openInputStream( EFS.NONE, null );
        BufferedReader reader = new BufferedReader( new InputStreamReader( liste[ i ].openInputStream( EFS.NONE,
                                                                                                       null ) ) );
        String line = null;
        while( ( line = reader.readLine() ) != null ) {
          result.append( line + System.getProperty( "line.separator" ) ); //$NON-NLS-1$
        }
        reader.close();
        SltContainer slt = new SltContainer( liste[ i ].getName(),
                                             result.toString(),
                                             "", i );//$NON-NLS-1$
        results.add( slt );
      }
    } catch( CoreException e ) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch( URISyntaxException e ) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch( IOException e ) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return results.toArray();
  }

  public String confirmSLA( final String slaDocument ) {
    return slaDocument;
  }

  public String getRequirements( final SimpleTermModel model ) {
    return ""; //$NON-NLS-1$
  }
}
