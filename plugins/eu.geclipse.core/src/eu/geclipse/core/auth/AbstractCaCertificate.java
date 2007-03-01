/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
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

package eu.geclipse.core.auth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import org.eclipse.core.runtime.IPath;

/**
 * Abstract implementation of the {@link ICaCertificate} interface that
 * mainly provides methods for dealing with the underlying files.
 *  
 * @author stuempert-m
 */

public abstract class AbstractCaCertificate implements ICaCertificate {
  
  /**
   * The file extension for the certificate file itself.
   */
  protected static final String CERTIFICATE_EXTENSION = "0"; //$NON-NLS-1$
  
  /**
   * The file extension for the revocation list.
   */
  protected static final String CRL_EXTENSION = "r0"; //$NON-NLS-1$
  
  /**
   * The file extension for the URL of the revocation list.
   */
  protected static final String CRL_URL_EXTENSION = "crl_url"; //$NON-NLS-1$
  
  /**
   * The file extension for the info file.
   */
  protected static final String INFO_EXTENSION = "info"; //$NON-NLS-1$
  
  /**
   * The hash code of the CA.
   */
  private long caHash = -1;
  
  /**
   * The unique ID of the CA (taken from the info file if present, otherwise
   * the string representation of the hash code.
   */
  private String caID = null;
  
  /**
   * The table that holds the files related to this CA.
   */
  private Hashtable< String, IPath > files = new Hashtable< String, IPath >();
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.auth.ICaCertificate#getID()
   */
  public String getID() {
    if ( this.caID == null ) {
      this.caID = getAlias();
      if ( this.caID == null ) {
        this.caID = Long.toHexString( getCaHash() );
      }
    }
    return this.caID;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.auth.ICaCertificate#getCaHash()
   */
  public long getCaHash() {
    if ( this.caHash < 0 ) {
      IPath path = getCertificateFile();
      if ( path != null ) {
        String hashString = path.removeFileExtension().lastSegment();
        this.caHash = Long.parseLong( hashString, 16 );
      }
    }
    return this.caHash;
  }
  
  /**
   * Get the certificate file that is associated with this certificate.
   * 
   * @return The associated certificate file or null.
   */
  public IPath getCertificateFile() {
    return findFile( CERTIFICATE_EXTENSION );
  }
  
  /**
   * Get the info file that is associated with this certificate.
   * 
   * @return The associated info file or null.
   */
  public IPath getInfoFile() {
    return findFile( INFO_EXTENSION );
  }
  
  /**
   * Find the file with the specified extension.
   * 
   * @param typeExtension The extension of the file.
   * @return The file with the specified extension or null.
   */
  protected IPath findFile( final String typeExtension ) {
    return this.files.get( typeExtension );
  }
  
  /**
   * Get all files that are currently associated with this
   * certificate.
   * 
   * @return An array containing all files currently associated
   * with this certificate. 
   */
  protected IPath[] getFiles() {
    return this.files.values().toArray(new IPath[0]);
  }
  
  /**
   * Add a file to the related files of this certificate.
   * 
   * @param file The file to be added to the related files of this
   * certificate.
   */
  protected void addFile( final IPath file ) {
    String extension = file.getFileExtension();
    this.files.put( extension, file );
  }
  
  /**
   * Read the alias from the info file. This alias is used as ID if the info file
   * is present and the alias field was found.
   * 
   * @return The alias of this CA.
   */
  private String getAlias() {
    String result = null;
    IPath infoPath = getInfoFile();
    if ( infoPath != null ) {
      File infoFile = infoPath.toFile();
      if ( infoFile.exists() ) {
        try {
          FileReader fReader = new FileReader( infoFile );
          BufferedReader bReader = new BufferedReader( fReader );
          String line;
          while ( ( line = bReader.readLine() ) != null ) {
            String[] parts = line.split( "=" ); //$NON-NLS-1$
            String  fieldID = parts[0].trim();
            if ( fieldID.equalsIgnoreCase( "alias" ) && ( parts.length > 1 ) ) { //$NON-NLS-1$
              result = parts[1].trim();
              break;
            }
          }
          bReader.close();
          fReader.close();
        } catch ( final IOException ioExc ) {
          // Ignored since the info file has not necessarily to exist
        }
      }
    }
    return result;
  }
  
}
