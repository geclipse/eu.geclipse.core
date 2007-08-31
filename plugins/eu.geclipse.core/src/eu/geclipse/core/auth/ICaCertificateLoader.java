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

import java.net.URI;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

import eu.geclipse.core.GridException;

/**
 * Definition of a certificate loader. A certificate loader is
 * mechanism to import CA certificates of a dediacted type from
 * a dedicated source, i.e. a remote repository or
 * alternatively a local directory or file.
 */
public interface ICaCertificateLoader {
  
  /**
   * Get a certificate from the specified path. The path has
   * to point to a file. If the certificate loader is not able to
   * retrieve a certificate from the specified path it simply
   * returns <code>null</code>.
   * 
   * @param path The path pointing to a file from which to
   * import the certificate.
   * @return The imported certificate or <code>null</code> if
   * this certificate loader was not able to retrieve a certificate
   * from the specified path.
   * @throws GridException If an error occures while the certificate
   * is imported.
   */
  public ICaCertificate getCertificate( final IPath path )
    throws GridException;
  
  /**
   * Get the certificate with the specified ID from the specified
   * <code>URI</code>. The <code>URI</code> may be a remote or local
   * repository. In each case the <code>URI</code> has to point
   * to a directory.
   * 
   * @param uri The location from which to import the certificate.
   * @param certID The ID of the certificate. Normally this is
   * the certificates file name. But this may depend on the
   * concrete implementation.
   * @param monitor A {@link IProgressMonitor} that is used to
   * indicate the progress of the import operation.
   * @return The imported certificate or <code>null</code> if no
   * certificate could be imported from the specified location.
   * @throws GridException If an error occurs while the
   * certificate is imported.
   */
  public ICaCertificate getCertificate( final URI uri,
                                        final String certID,
                                        final IProgressMonitor monitor )
    throws GridException;

  /**
   * Get a list of certificate IDs that are found at the specified
   * <code>URI</code>. The returned IDs can be used in combination with
   * the <code>URI</code> to finally retrieve a certificate with
   * {@link #getCertificate(URI, String, IProgressMonitor)}.
   * 
   * @param uri The location to be queried for available certificates.
   * @param monitor A progress monitor for monitoring the progress of
   * this operation.
   * @return A list of certifcate IDs that could be found at the
   * specified location. This may be <code>null</code> if no certificates
   * could be found.
   * @throws GridException If an error occurs while querying the specified
   * location.
   * @see #getCertificate(URI, String, IProgressMonitor)
   */
  public String[] getCertificateList( final URI uri,
                                      final IProgressMonitor monitor )
    throws GridException;
  
  /**
   * Get a list of predefined import location for this certificate
   * loader.
   * 
   * @return The list of predefined certificate repositories.
   */
  public URI[] getPredefinedRemoteLocations();

}
