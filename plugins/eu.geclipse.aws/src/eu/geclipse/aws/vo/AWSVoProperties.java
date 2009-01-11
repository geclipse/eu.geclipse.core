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
package eu.geclipse.aws.vo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

import eu.geclipse.aws.internal.Activator;
import eu.geclipse.core.ICoreProblems;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IStorableElement;
import eu.geclipse.core.model.impl.AbstractGridElement;
import eu.geclipse.core.reporting.ProblemException;


/**
 * The {@link AWSVoProperties} class is a javabean class able to store and
 * retrieve values to be used by a {@link AWSVirtualOrganization}. The storage
 * container used here is the {@link IFileStore}. The class uses plain
 * {@link BufferedReader} and {@link BufferedWriter} to read and write the data
 * from the store. Each storage element is serialized to a String and stored
 * line by line.
 * <p>
 * To explicitly identify this storage element, the {@link #STORAGE_NAME}
 * property holds the fixed key <code>".aws_vo_properties"</code>.
 * <p>
 * TODO: Implement a more sophisticated storage schema.
 * 
 * @author Moritz Post
 * @see AWSVirtualOrganization
 * @see AWSVoCreator
 */
public class AWSVoProperties extends AbstractGridElement
  implements IStorableElement
{

  /** Name to reference the properties in the {@link GridModel}. */
  public static final String STORAGE_NAME = ".aws_vo_properties"; //$NON-NLS-1$

  /** The virtual organization these properties belong to. */
  private AWSVirtualOrganization vo;

  /** The aws access id to bind this VO to a specific account. */
  private String awsAccessId;

  /**
   * This constructor creates a new instance of the {@link AWSVoProperties} with
   * the {@link AWSVirtualOrganization} provided and uses the property data in
   * the {@link AWSVoCreator} to populate its own bean fields.
   * 
   * @param virtualOrganization to {@link AWSVirtualOrganization} to be
   *            associated with
   * @param voCreator the {@link AWSVoCreator} to get the data from
   */
  public AWSVoProperties( final AWSVirtualOrganization virtualOrganization,
                          final AWSVoCreator voCreator )
  {
    this.vo = virtualOrganization;
    if( voCreator != null ) {
      this.awsAccessId = voCreator.getAwsAccessId();
    }
  }

  /**
   * Convenience constructor to associate the properties with a
   * {@link AWSVirtualOrganization} but without populating any fields. Maps to
   * {@link #AWSVoProperties(AWSVirtualOrganization, AWSVoCreator)} where the
   * {@linkplain AWSVoCreator creator} is <code>null</code>.
   * 
   * @param virtualOrganization the {@link AWSVirtualOrganization} to associate
   *            with
   */
  public AWSVoProperties( final AWSVirtualOrganization virtualOrganization ) {
    this( virtualOrganization, null );
  }

  public void load() throws ProblemException {
    BufferedReader bufferedReader = null;
    try {
      IFileStore fileStore = getFileStore();
      InputStream iStream = fileStore.openInputStream( EFS.NONE, null );
      InputStreamReader iReader = new InputStreamReader( iStream );
      bufferedReader = new BufferedReader( iReader );

      // load from file
      this.awsAccessId = bufferedReader.readLine();
    } catch ( CoreException cExc ) {
      throw new ProblemException( ICoreProblems.MODEL_ELEMENT_LOAD_FAILED,
                                  cExc,
                                  Activator.PLUGIN_ID );
    } catch ( IOException ioEx ) {
      Activator.log( "Could not load aws vo properties", ioEx ); //$NON-NLS-1$
      throw new ProblemException( ICoreProblems.MODEL_ELEMENT_SAVE_FAILED,
                                  ioEx,
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
    OutputStreamWriter osWriter = null;
    try {
      OutputStream oStream = fileStore.openOutputStream( EFS.NONE, null );
      osWriter = new OutputStreamWriter( oStream );

      // write output
      if ( this.awsAccessId != null ) {
        osWriter.write( this.awsAccessId + "\n" ); //$NON-NLS-1$
      }
    } catch ( CoreException cExc ) {
      throw new ProblemException( ICoreProblems.MODEL_ELEMENT_SAVE_FAILED,
                                  cExc,
                                  Activator.PLUGIN_ID );
    } catch ( IOException ioEx ) {
      Activator.log( "Could not save aws vo properties", ioEx ); //$NON-NLS-1$
      throw new ProblemException( ICoreProblems.MODEL_ELEMENT_SAVE_FAILED,
                                  ioEx,
                                  Activator.PLUGIN_ID );
    } finally {
      try {
        if ( osWriter != null ) {
          osWriter.close();
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
    return AWSVoProperties.STORAGE_NAME;
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

  /**
   * A getter for the {@link #awsAccessId}.
   * 
   * @return the awsAccessId
   */
  public String getAwsAccessId() {
    return this.awsAccessId;
  }

  /**
   * A setter for the {@link #awsAccessId}.
   * 
   * @param awsAccessId the awsAccessId to set
   */
  public void setAwsAccessId( final String awsAccessId ) {
    this.awsAccessId = awsAccessId;
  }
}
