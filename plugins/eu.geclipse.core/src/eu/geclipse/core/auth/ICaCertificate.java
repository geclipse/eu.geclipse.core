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

import java.io.IOException;

import org.eclipse.core.runtime.IPath;

/**
 * Definition of a CA certificate. A CA certificate has always an ID and
 * an associated certificate file. It may also have additional files like
 * the revocation list or an info file. 
 * 
 * @author stuempert-m
 */
public interface ICaCertificate {
  
  /**
   * Get the ID of this certificate. This has to be unique and is used to
   * reference this <code>CaCertificate</code>.
   * 
   * @return The unique ID of this certificate.
   */
  public String getID();
  
  /**
   * Get the raw certificate data of this certificate.
   * 
   * @return The certificate's raw data.
   */
  public byte[] getCertificateData();
  
  /**
   * Delete this certificate from the specified directory. This
   * deleted all files that belong to this certificate.
   * 
   * @param fromDirectory The directory from which to delete the
   * certificate.
   * @see #write(IPath) 
   */
  public void delete( final IPath fromDirectory );
  
  /**
   * Write this certificate to the specified directory. Certificates may
   * contain several files and only the concrete {@link ICaCertificate}
   * implementation knows about these files. So in order to completely
   * remove a certificate the {@link #delete(IPath)}-method should be
   * used.
   * @param toDirectory
   * @throws IOException
   */
  public void write( final IPath toDirectory ) throws IOException;
  
}
