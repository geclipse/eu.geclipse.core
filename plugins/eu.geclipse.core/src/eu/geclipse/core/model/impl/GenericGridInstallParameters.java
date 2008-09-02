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

package eu.geclipse.core.model.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;

import eu.geclipse.core.model.IGridComputing;
import eu.geclipse.core.model.IGridInstallParameters;
import eu.geclipse.core.reporting.ProblemException;

/**
 * Generic core implemetation of application installation parameters.
 * Middleware-specific implementations may extend this class in order
 * to provide other necessary parameters.
 */
public class GenericGridInstallParameters
    implements IGridInstallParameters {
  
  /**
   * The sources of the installation process.
   */
  private List< URI > sources = new ArrayList< URI >();
  
  /**
   * The targets of the installation process.
   */
  private List< IGridComputing > targets = new ArrayList< IGridComputing >();
  
  /**
   * The software tag.
   */
  private String tag;
  
  /**
   * Add a new source to the list of sources.
   * 
   * @param uri The source to be added.
   */
  public void addSource( final URI uri ) {
    this.sources.add( uri );
  }
  
  /**
   * Add new sources to the list of sources.
   * 
   * @param uris The sources to be added.
   */
  public void addSources( final URI[] uris ) {
    this.sources.addAll( Arrays.asList( uris ) );
  }
  
  /**
   * Add a new target to the list of targets.
   * 
   * @param computing The target to be added.
   */
  public void addTarget( final IGridComputing computing ) {
    this.targets.add( computing );
  }
  
  /**
   * Add new targets to the list of targets.
   * 
   * @param computings The new targets.
   */
  public void addTargets( final IGridComputing[] computings ) {
    this.targets.addAll( Arrays.asList( computings ) );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridInstallParameters#getSources()
   */
  public URI[] getSources() {
    return this.sources.toArray( new URI[ this.sources.size() ] );
  }
  
  /**
   * Get the sources of this installation process. The sources
   * are returned as {@link IFileStore}s. This may fail if any of the
   * provided {@link URI}s is invalid.
   * 
   * @return The sources as {@link IFileStore}s.
   * @throws ProblemException If no file store could be created from
   * one of the source {@link URI}s.
   */
  public IFileStore[] getSourcesAsFileStore()
      throws ProblemException {
    
    URI[] sourceURIs = getSources();
    IFileStore[] result = new IFileStore[ sourceURIs.length ];
    
    for ( int i = 0 ; i < sourceURIs.length ; i++ ) {
      try {
        result[ i ] = EFS.getStore( sourceURIs[ i ] );
      } catch ( CoreException cExc ) {
        if ( cExc instanceof ProblemException ) {
          throw ( ProblemException ) cExc;
        }
        throw new ProblemException( cExc.getStatus() );
      }
    }
    
    return result;
    
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridInstallParameters#getTag()
   */
  public String getTag() {
    return this.tag;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridInstallParameters#getTargets()
   */
  public IGridComputing[] getTargets() {
    return this.targets.toArray( new IGridComputing[ this.targets.size() ] );
  }
  
  /**
   * Sets the sources for this installation.
   * 
   * @param uris The new sources.
   */
  public void setSources( final URI[] uris ) {
    this.sources.clear();
    addSources( uris );
  }
  
  /**
   * Sets the software tag for this installation.
   * 
   * @param tag The new software tag.
   */
  public void setTag( final String tag ) {
    this.tag = tag;
  }
  
  /**
   * Sets the targets for this installation.
   * 
   * @param targets The new targets.
   */
  public void setTargets( final IGridComputing[] targets ) {
    this.targets.clear();
    addTargets( targets );
  }

}
