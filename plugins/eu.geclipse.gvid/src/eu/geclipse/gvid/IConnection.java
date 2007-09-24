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
 * Interface to a buffering connection class which allows decoders to have
 * a look into the buffer before reading out of it.
 */
public interface IConnection {
  /**
   * Try to fill internal read buffer. This method trys to fill the
   * internal read buffer. It checks if data is available from the
   * connection and reads it into the buffer. If no data is available and
   * blocking is set to false the method will return immediately, if
   * blocking is set to true the method will block until data arives.
   * @param blocking behavior if no data is available.
   * @throws IOException if an I/O error occurs.
   */
  public abstract void fillBuffer( final boolean blocking ) throws IOException;

  /**
   * Returns number of bytes in buffer. Can be used to find out if the
   * next read call will block or not. If the amount of bytes to read is
   * lower or equal the number of bytes in the buffer the read method
   * will not block, if it greater the read method will block.
   * @return number of bytes in read buffer.
   * @throws IOException if an I/O error occurs.
   */
  public abstract int getNumBytesInBuffer() throws IOException;

  /**
   * Drops the specified amount of bytes from the read buffer. This
   * method can be used in combiantion with the getDataStartAddr method
   * (Useful if the amount of data needed isn't known at the time of
   * reading. This function also doesn't require a memcpy).
   * @param amount amount of bytes to drop.
   * @throws IOException if an I/O error occurs.
   */
  public abstract void dropBytes( final int amount ) throws IOException;

  /**
   * @brief Returns start address of data read. This method returns the start
   *        address of the next databytes in the read buffer. When using this
   *        pointer no other read operations may be performed becase these
   *        change the pointer position.
   * @return offset in internal buffer where data is starting.
   * @see #dropBytes(int)
   */
  public abstract int getDataStart();

  /**
   * Returns the read buffer.
   * @return the read buffer.
   */
  public abstract byte[] getDataBuffer();

  /**
   * Read data into specified buffer. This method reads the specified
   * amount of data from the connection and copys it into the specified
   * buffer. The data will be droped from the internal streambuffer
   * (important if getDataStartAddr is also used). The method will block
   * if no data is available until data is available.
   * @param data pointer to buffer for data.
   * @param maxLen maximal number of bytes to read.
   * @return number of bytes read.
   * @throws IOException if an I/O error occurs.
   */
  public abstract int readData( final byte[] data, final int maxLen )
    throws IOException;

  /**
   * Read data into specified buffer. This method reads the specified
   * amount of data from the connection and copys it into the specified
   * buffer. The data will be droped from the internal streambuffer
   * (important if getDataStartAddr is also used). The method will block
   * if no data is available until data is available.
   * @param data pointer to buffer for data.
   * @param maxLen maximal number of bytes to read.
   * @param startPos start position in the buffer for the read operation.
   * @return number of bytes read.
   * @throws IOException if an I/O error occurs.
   */
  public abstract int readData( final byte[] data,
                                final int maxLen,
                                final int startPos ) throws IOException;

  /**
   * @brief Returns the current send speed of the last second in bytes per
   *        second.
   * @return speed in bytes per second.
   */
  public abstract long getCurrentSendSpeed();

  /**
   * @brief Returns the current receive speed of the last second.
   * @return speed in bytes per second.
   */
  public abstract long getCurrentRecvSpeed();

  /**
   * @brief Reads a single byte from the connection.
   * @return value of the byte read.
   * @throws IOException if an I/O error occurs.
   */
  public abstract byte readByte() throws IOException;

  /**
   * @brief Reads a short (16 bit) from the connection.
   * @return value of the short read.
   * @throws IOException if an I/O error occurs.
   */
  public abstract short readUint16() throws IOException;

  /**
   * @brief Reads a long (32 bit) from the connection.
   * @return value of the long read.
   * @throws IOException if an I/O error occurs.
   */
  public abstract int readUint32() throws IOException;
}