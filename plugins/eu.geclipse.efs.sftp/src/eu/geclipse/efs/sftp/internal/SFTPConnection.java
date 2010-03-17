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

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.jcraft.jsch.ChannelSftp;

/**
 * A SSH Connection
 */
public class SFTPConnection implements Lock {

  private ReentrantLock lock;
  private ChannelSftp channel;

  /**
   * Create a new SSH Connection
   * 
   * @param channel
   */
  public SFTPConnection( final ChannelSftp channel ) {
    this.lock = new ReentrantLock( false );
    this.channel = channel;
  }

  /**
   * Returns a SFTP Channel
   * 
   * @return channel
   */
  public ChannelSftp getChannel() {
    return this.channel;
  }

  /*
   * (non-Javadoc)
   * @see java.util.concurrent.locks.Lock#lock()
   */
  public void lock() {
    this.lock.lock();
  }

  /*
   * (non-Javadoc)
   * @see java.util.concurrent.locks.Lock#lockInterruptibly()
   */
  public void lockInterruptibly() throws InterruptedException {
    this.lock.lockInterruptibly();
  }

  /*
   * (non-Javadoc)
   * @see java.util.concurrent.locks.Lock#newCondition()
   */
  public Condition newCondition() {
    return this.lock.newCondition();
  }

  /*
   * (non-Javadoc)
   * @see java.util.concurrent.locks.Lock#tryLock()
   */
  public boolean tryLock() {
    return this.lock.tryLock();
  }

  /*
   * (non-Javadoc)
   * @see java.util.concurrent.locks.Lock#tryLock(long,
   * java.util.concurrent.TimeUnit)
   */
  public boolean tryLock( final long time, final TimeUnit unit )
    throws InterruptedException
  {
    return this.lock.tryLock( time, unit );
  }

  /*
   * (non-Javadoc)
   * @see java.util.concurrent.locks.Lock#unlock()
   */
  public void unlock() {
    this.lock.unlock();
  }
}
