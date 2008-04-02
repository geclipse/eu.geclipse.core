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

import java.io.File;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import eu.geclipse.core.internal.Activator;

/**
 * Abstract implementation of the {@link ICaCertificate} interface that
 * mainly provides methods for dealing with the underlying files.
 *  
 * @author stuempert-m
 */

public abstract class AbstractCaCertificate
    implements ICaCertificate {
  
  private String id;
  
  private byte[] certificateData;
  
  protected AbstractCaCertificate( final String id,
                                   final byte[] certificateData ) {
    Assert.isNotNull( id );
    Assert.isNotNull( certificateData );
    this.id = id;
    this.certificateData = certificateData;
  }
  
  public void delete( final IPath fromDirectory ) {
    String[] fileNames = getFileNames();
    for ( String fileName : fileNames ) {
      File file = fromDirectory.append( fileName ).toFile();
      if ( ! file.delete() ) {
        Activator.logStatus(
            new Status(
                IStatus.WARNING,
                Activator.PLUGIN_ID,
                String.format( Messages.getString("AbstractCaCertificate.deletion_failed_status"), getID() ) //$NON-NLS-1$
            )
        );
      }
    }
  }
  
  public byte[] getCertificateData() {
    byte[] copy = null;
    if ( this.certificateData != null ) {
      int length = this.certificateData.length;
      copy = new byte[ length ];
      System.arraycopy( this.certificateData, 0, copy, 0, length );
    }
    return copy;
  }
  
  public String getID() {
    return this.id;
  }

  protected abstract String[] getFileNames();
  
}