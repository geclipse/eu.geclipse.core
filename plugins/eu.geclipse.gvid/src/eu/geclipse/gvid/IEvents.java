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

package eu.geclipse.gvid;

import java.io.IOException;

/**
 * Interface the the GVid event handling class, allows decoders to send
 * frame finished events to the encoder. 
 */
public interface IEvents {
  /**
   * Sends a frame finished Event (for use after decoding a frame).
   * @param frame_num number of the finished frame.
   * @param enc_time time the decryption required.
   * @param comp_time time the decompression required.
   * @throws IOException if an I/O error occurs.
   */
  public abstract void sendFrameFinished( final int frame_num,
                                          final int enc_time,
                                          final int comp_time )
    throws IOException;
}