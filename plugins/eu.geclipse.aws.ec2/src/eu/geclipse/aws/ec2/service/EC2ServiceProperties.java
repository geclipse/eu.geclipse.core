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
 *    Moritz Post - initial API and implementation
 *****************************************************************************/

package eu.geclipse.aws.ec2.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

import eu.geclipse.aws.IAWSService;
import eu.geclipse.aws.ec2.internal.Activator;
import eu.geclipse.core.ICoreProblems;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IStorableElement;
import eu.geclipse.core.model.impl.AbstractGridElement;
import eu.geclipse.core.reporting.ProblemException;


/**
 * The {@link EC2ServiceProperties} hold the various properties specific to the
 * EC2 infrastructure. Both in terms of EC2 specific as well as gEclipse
 * specific means. The currently kept properties are:
 * <ul>
 * <li>{@link #ec2Url}</li>
 * <li>{@link #serviceName}</li>
 * </ul>
 * These properties are kept on persistent storage under the key provided by
 * {@link #STORAGE_NAME}.
 * 
 * @author Moritz Post
 * @see EC2Service
 */
public class EC2ServiceProperties extends AbstractGridElement
  implements IStorableElement
{

  /** Name to reference the properties. */
  public static final String STORAGE_NAME = ".ec2_service_properties"; //$NON-NLS-1$

  /** The parent {@link IGridElement}. */
  private EC2Service ec2Service;

  /** The url to access amazon ec2 webservices. */
  private String ec2Url;

  /** The name of this {@link IAWSService} */
  private String serviceName;

  /**
   * Create a new properties object with the given {@link EC2Service} as parent.
   * 
   * @param ec2Service the parent {@link IGridElement}
   */
  public EC2ServiceProperties( final EC2Service ec2Service ) {
    this.ec2Service = ec2Service;
  }

  /**
   * Create a new properties object with the given {@link EC2Service} as parent.
   * Tje {@link EC2ServiceCreator} provides the initial properties.
   * 
   * @param ec2Service the parent {@link IGridElement}
   * @param serviceCreator the provider for configuration directives
   */
  public EC2ServiceProperties( final EC2Service ec2Service,
                               final EC2ServiceCreator serviceCreator )
  {
    this.ec2Service = ec2Service;
    this.ec2Url = serviceCreator.getServiceURL();
    this.serviceName = serviceCreator.getServiceName();
  }

  public void load() throws ProblemException {
    BufferedReader bufferedReader = null;
    try {
      IFileStore fileStore = getFileStore();
      InputStream iStream = fileStore.openInputStream( EFS.NONE, null );
      InputStreamReader iReader = new InputStreamReader( iStream );
      bufferedReader = new BufferedReader( iReader );

      String serviceName = bufferedReader.readLine();
      if ( serviceName != null && serviceName.trim().length() != 0 ) {
        this.serviceName = serviceName;
      }

      String url = bufferedReader.readLine();
      if ( url != null && url.trim().length() != 0 ) {
        this.ec2Url = url;
      }
    } catch ( IOException ioExc ) {
      throw new ProblemException( ICoreProblems.MODEL_ELEMENT_LOAD_FAILED,
                                  ioExc,
                                  Activator.PLUGIN_ID );
    } catch ( CoreException cExc ) {
      throw new ProblemException( ICoreProblems.MODEL_ELEMENT_LOAD_FAILED,
                                  cExc,
                                  Activator.PLUGIN_ID );
    } finally {
      if ( bufferedReader != null ) {
        try {
          bufferedReader.close();
        } catch ( IOException ioExc ) {
          throw new ProblemException( ICoreProblems.MODEL_ELEMENT_LOAD_FAILED,
                                    ioExc,
                                    Activator.PLUGIN_ID );
        }
      }
    }
  }

  public void save() throws ProblemException {
    IFileStore fileStore = getFileStore();
    BufferedWriter bWriter = null;
    try {
      OutputStream oStream = fileStore.openOutputStream( EFS.NONE, null );
      OutputStreamWriter osWriter = new OutputStreamWriter( oStream );
      bWriter = new BufferedWriter( osWriter );

      if ( this.serviceName != null ) {
        bWriter.write( this.serviceName );
      }
      bWriter.write( '\n' );

      if ( this.ec2Url != null ) {
        bWriter.write( this.ec2Url );
      }
      bWriter.write( '\n' );
    } catch ( IOException ioExc ) {
      throw new ProblemException( ICoreProblems.MODEL_ELEMENT_LOAD_FAILED,
                                  ioExc,
                                  Activator.PLUGIN_ID );
    } catch ( CoreException cExc ) {
      throw new ProblemException( ICoreProblems.MODEL_ELEMENT_SAVE_FAILED,
                                  cExc,
                                  Activator.PLUGIN_ID );
    } finally {
      try {
        if ( bWriter != null ) {
          bWriter.close();
        }
      } catch ( IOException ioExc ) {
        throw new ProblemException( ICoreProblems.MODEL_ELEMENT_SAVE_FAILED,
                                    ioExc,
                                    Activator.PLUGIN_ID );
      }
    }
  }

  public IFileStore getFileStore() {
    return getParent().getFileStore().getChild( getName() );
  }

  public String getName() {
    return EC2ServiceProperties.STORAGE_NAME;
  }

  public IGridContainer getParent() {
    return this.ec2Service;
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

  /**
   * A getter for the Amazon EC2 {@link URL}.
   * 
   * @return the {@link URL} to the Amazon EC2 service.
   */
  public String getEc2Url() {
    return this.ec2Url;
  }

  /**
   * A setter for the Amazon EC2 {@link URL}
   * 
   * @param ec2Url the url to set
   */
  public void setEc2Url( final String ec2Url ) {
    this.ec2Url = ec2Url;
  }

  /**
   * @return the serviceName
   */
  public String getServiceName() {
    return this.serviceName;
  }

  /**
   * @param serviceName the serviceName to set
   */
  public void setServiceName( final String serviceName ) {
    this.serviceName = serviceName;
  }
}
