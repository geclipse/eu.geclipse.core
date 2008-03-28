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
 *    Thomas Koeckerbauer GUP, JKU - initial API and implementation
 *****************************************************************************/

package eu.geclipse.core.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Repacement for {@link java.io.PipedOutputStream}.
 * (because of Java bug nr. 4404700)
 */
public class PipedOutputStream extends OutputStream {
  private PipedInputStream in;

  /**
   * @see java.io.PipedOutputStream#PipedOutputStream(java.io.PipedInputStream)
   * @param in instance of the replacement PipedInputStream implementation
   * @throws IOException not thrown, for API compatibility to
   *                     java.io.PipedOutputStream
   */
  public PipedOutputStream( final PipedInputStream in ) throws IOException {
    this();
    connect( in );
  }

  /**
   * @see java.io.PipedOutputStream#PipedOutputStream()
   */
  public PipedOutputStream() {
    // empty
  }

  /**
   * @see java.io.PipedOutputStream#connect(java.io.PipedInputStream)
   * @param pipedInputStream instance of the replacement
   *                         PipedInputStream implementation
   * @throws IOException not thrown, for API compatibility to
   *                     java.io.PipedOutputStream
   */
  @SuppressWarnings("unused")
  public void connect( final PipedInputStream pipedInputStream ) throws IOException {
    this.in = pipedInputStream;
  }

  /* (non-Javadoc)
   * @see java.io.OutputStream#write(int)
   */
  @Override
  public void write( final int b ) throws IOException {
    this.in.write( b );
  }

  /* (non-Javadoc)
   * @see java.io.OutputStream#write(byte[], int, int)
   */
  @Override
  public void write( final byte[] b, final int off, final int len ) throws IOException {
    this.in.write( b, off, len );
  }

  /* (non-Javadoc)
   * @see java.io.OutputStream#flush()
   */
  @Override
  public void flush() {
    // empty
  }

  /* (non-Javadoc)
   * @see java.io.OutputStream#close()
   */
  @Override
  public void close() {
    this.in.close();
  }
}
