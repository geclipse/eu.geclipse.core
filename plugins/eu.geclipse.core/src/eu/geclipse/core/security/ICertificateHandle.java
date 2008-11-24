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

package eu.geclipse.core.security;

import java.security.cert.X509Certificate;

import eu.geclipse.core.security.ICertificateManager.CertTrust;

/**
 * A certificate handle is used by the certificate manager to connect
 * certificates with their trust level and eventually with a underlying file.
 */
public interface ICertificateHandle {
  
  /**
   * Delete the certificate and all related files.
   * 
   * @return <code>true</code> if the deletion was complete, <code>false</code>
   * otherwise.
   */
  public boolean delete();
  
  /**
   * Get the {@link X509Certificate} corresponding to this handle.
   * 
   * @return The certificate itself.
   */
  public X509Certificate getCertificate();
  
  /**
   * Get the trust level of this certificate.
   * 
   * @return The certificate's trust level.
   */
  public CertTrust getTrust();
  
}
