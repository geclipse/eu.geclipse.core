/*****************************************************************************
 * Copyright (c) 2006, 2008 g-Eclipse Consortium 
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
 *    Christof Klausecker GUP, JKU - initial API and implementation
 *****************************************************************************/

package eu.geclipse.efs.sftp.internal;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * An {@link InputStream} that releases its {@link SFTPConnection} when closing.
 */
public class CloseInputStream extends BufferedInputStream {

  SFTPConnection connection;

  /**
   * Creates a new {@link CloseInputStream}
   * 
   * @param inputStream
   * @param connection
   */
  public CloseInputStream( final InputStream inputStream,
                           final SFTPConnection connection )
  {
    super( inputStream );
    this.connection = connection;
  }

  @Override
  public void close() throws IOException {
    super.close();
    this.connection.unlock();
  }
}
