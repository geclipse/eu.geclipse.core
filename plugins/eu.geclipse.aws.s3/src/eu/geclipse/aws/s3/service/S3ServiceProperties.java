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

package eu.geclipse.aws.s3.service;

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
import org.jets3t.service.S3Service;

import eu.geclipse.aws.IAWSService;
import eu.geclipse.aws.s3.internal.Activator;
import eu.geclipse.core.ICoreProblems;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IStorableElement;
import eu.geclipse.core.model.impl.AbstractGridElement;
import eu.geclipse.core.reporting.ProblemException;


/**
 * The {@link S3ServiceProperties} hold the various properties specific to the
 * S3 infrastructure. Both in terms of S3 specific as well as gEclipse specific
 * means. The currently kept properties are:
 * <ul>
 * <li>{@link #s3Url}</li>
 * <li>{@link #serviceName}</li>
 * </ul>
 * These properties are kept on persistent storage under the key provided by
 * {@link #STORAGE_NAME}.
 * 
 * @author Moritz Post
 * @see S3Service
 */
public class S3ServiceProperties extends AbstractGridElement
  implements IStorableElement
{

  /** Name to reference the properties. */
  public static final String STORAGE_NAME = ".s3_service_properties"; //$NON-NLS-1$

  /** The parent {@link IGridElement}. */
  private S3AWSService s3Service;

  /** The url to access amazon s3 webservices. */
  private String s3Url;

  /** The name of this {@link IAWSService} */
  private String serviceName;

  /**
   * Create a new properties object with the given {@link S3AWSService} as
   * parent.
   * 
   * @param s3Service the parent {@link IGridElement}
   */
  public S3ServiceProperties( final S3AWSService s3Service ) {
    this.s3Service = s3Service;
  }

  /**
   * Create a new properties object with the given {@link S3AWSService} as
   * parent. The {@link S3AWSServiceCreator} provides the initial properties.
   * 
   * @param s3Service the parent {@link IGridElement}
   * @param serviceCreator the provider for configuration directives
   */
  public S3ServiceProperties( final S3AWSService s3Service,
                              final S3AWSServiceCreator serviceCreator )
  {
    this.s3Service = s3Service;
    this.s3Url = serviceCreator.getServiceURL();
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
        this.s3Url = url;
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

      if ( this.s3Url != null ) {
        bWriter.write( this.s3Url );
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
    return S3ServiceProperties.STORAGE_NAME;
  }

  public IGridContainer getParent() {
    return this.s3Service;
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
   * A getter for the Amazon S3 {@link URL}.
   * 
   * @return the {@link URL} to the Amazon S3 service.
   */
  public String getS3Url() {
    return this.s3Url;
  }

  /**
   * A setter for the Amazon S3 {@link URL}
   * 
   * @param s3Url the url to set
   */
  public void setS3Url( final String s3Url ) {
    this.s3Url = s3Url;
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
