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

/**
 * Abstract implementation of the {@link ICaCertificate} interface that
 * mainly provides methods for dealing with the underlying files.
 *  
 * @author stuempert-m
 */

public abstract class AbstractCaCertificate
    implements ICaCertificate {
  
  private String id;
  
  private byte[] certfificateData;
  
  protected AbstractCaCertificate( final String id,
                                   final byte[] certificateData ) {
    Assert.isNotNull( id );
    Assert.isNotNull( certificateData );
    this.id = id;
    this.certfificateData = certificateData;
  }
  
  public void delete( final IPath fromDirectory ) {
    String[] fileNames = getFileNames();
    for ( String fileName : fileNames ) {
      File file = fromDirectory.append( fileName ).toFile();
      file.delete();
    }
  }
  
  public byte[] getCertificateData() {
    return this.certfificateData;
  }
  
  public String getID() {
    return this.id;
  }

  protected abstract String[] getFileNames();
  
}