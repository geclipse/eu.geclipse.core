/******************************************************************************
 * Copyright (c) 2007, 2008 g-Eclipse consortium
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
 *     UCY (http://www.cs.ucy.ac.cy)
 *      - Harald Gjermundrod (harald@cs.ucy.ac.cy)
 *     FZK - Ariel Garcia - updated to new problem reporting
 *****************************************************************************/
package eu.geclipse.batch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

import eu.geclipse.batch.internal.Activator;
import eu.geclipse.core.ICoreProblems;
import eu.geclipse.core.model.IGridSiteConfig;
import eu.geclipse.core.model.impl.ResourceGridContainer;
import eu.geclipse.core.reporting.ProblemException;


/**
 * Holder object for the necessary information to connect to a batch service.
 */
public class BatchConnectionInfo extends ResourceGridContainer implements IGridSiteConfig {
  private static final long serialVersionUID = 1L;

  private String batchName;
  private String account;
  private String batchType;
  private int updateInterval;

  /**
   * Create a new Batch service description from the specified {@link IFile}.
   * 
   * @param file The file from which to create the description.
   */
  public BatchConnectionInfo( final IFile file ) {
    super( file );
  }

  /**
   * Sets the properties of the connection
   * @param bName
   * @param acc
   * @param type
   * @param interval
   */
  public void setConnectionInfo ( final String bName, final String acc, 
                               final String type, final int interval ) {
    this.batchName = bName;
    this.account = acc;
    this.batchType = type;
    this.updateInterval = interval;
  }

  /**
   * @return the account
   */
  public String getAccount() {
    return this.account;
  }

  /**
   * @return the batchName
   */
  public String getBatchName() {
    return this.batchName;
  }


  /**
   * @return the batchType
   */
  public String getBatchType() {
    return this.batchType;
  }

  /**
   * @return the updateInterval
   */
  public int getUpdateInterval() {
    return this.updateInterval;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IStoreableElement#load()
   */ 
  public void load() throws ProblemException {
    BufferedReader bReader = null;
    try {
      IFileStore fileStore = getFileStore();
      InputStream iStream =  fileStore.openInputStream( EFS.NONE, null ); 
      InputStreamReader iReader = new InputStreamReader( iStream );
      bReader = new BufferedReader( iReader );
      this.batchName = bReader.readLine();
      this.account = bReader.readLine();
      this.batchType = bReader.readLine();

      String str = bReader.readLine();
      if ( null == str ) {
        throw new ProblemException( ICoreProblems.MODEL_ELEMENT_LOAD_FAILED, Activator.PLUGIN_ID );
      }
      this.updateInterval = Integer.parseInt( str );
    } catch ( IOException ioExc ) {
      throw new ProblemException( ICoreProblems.MODEL_ELEMENT_LOAD_FAILED, ioExc, Activator.PLUGIN_ID );
    } catch ( CoreException cExc ) {
      throw new ProblemException( ICoreProblems.MODEL_ELEMENT_LOAD_FAILED, cExc, Activator.PLUGIN_ID );
    } finally {
      if ( null != bReader ) {
        try {
          bReader.close();
        } catch( IOException e ) {
          // Ignore this exception
        }
      }
    }
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IStorableElement#save()
   */
  public void save() throws ProblemException {
    IFileStore fileStore = getFileStore();
    try {
      OutputStream oStream = fileStore.openOutputStream( EFS.NONE, null );
      OutputStreamWriter osWriter = new OutputStreamWriter( oStream );
      BufferedWriter bWriter = new BufferedWriter( osWriter );
      bWriter.write( this.batchName );
      bWriter.newLine();
      bWriter.write( this.account );
      bWriter.newLine();
      bWriter.write( this.batchType );
      bWriter.newLine();
      bWriter.write( String.valueOf( this.updateInterval ) );
      bWriter.close();
    } catch ( CoreException cExc ) {
      throw new ProblemException( ICoreProblems.MODEL_ELEMENT_SAVE_FAILED, cExc, Activator.PLUGIN_ID );
    } catch ( IOException ioExc ) {
      throw new ProblemException( ICoreProblems.MODEL_ELEMENT_SAVE_FAILED, ioExc, Activator.PLUGIN_ID );
    }
  }
}
