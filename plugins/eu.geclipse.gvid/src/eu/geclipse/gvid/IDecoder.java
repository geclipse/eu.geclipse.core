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

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * This interface has to be implemented by plugins which provide a video decoder
 * implementation for GVid.
 */
public interface IDecoder {
  /**
   * Extension point which has to be implemented to provide a video decoder for GVid.
   */
  public static final String CODEC_EXTENSION_POINT = "eu.geclipse.gvid.codec"; //$NON-NLS-1$
  /**
   * Name of the attribute which specifies the class name of the IDecoder
   * interface implementation.
   */
  public static final String EXT_DECODER_CLASS = "decoderClass"; //$NON-NLS-1$
  /**
   * Name of the attribute which specifies the name of the decoder implementation.
   */
  public static final String EXT_NAME = "name"; //$NON-NLS-1$
  /**
   * Name of the element which contains the decoder specification.
   */
  public static final String EXT_CODEC = "codec"; //$NON-NLS-1$

  /**
   * Initialize the video decoder.
   * @param connection connection read the video data from.
   * @param events events instance for sending frame finished events.
   */
  public void init( final IConnection connection, final IEvents events );

  /**
   * @brief Returns the current width of the XviD stream.
   * @return current width of the XviD stream in pixels.
   */
  public int getXSize();

  /**
   * @brief Returns the current height of the XviD stream.
   * @return current height of the XviD stream in pixels.
   */
  public int getYSize();

  /**
   * Decodes the next frame (non-blocking).
   * @return true if a new frame was decoded, false if there is not enough data
   *         available to decode the next frame. 
   * @throws IOException thrown if a connection error occurs.
   */
  public boolean decodeNextFrame() throws IOException;

  /**
   * Returns the last decoded image.
   * @return the last decoded image.
   */
  public abstract BufferedImage getImage();
}
