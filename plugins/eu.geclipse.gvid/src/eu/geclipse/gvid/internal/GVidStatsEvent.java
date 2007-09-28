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

package eu.geclipse.gvid.internal;

import java.util.EventObject;

/**
 * GVid status event class.
 */
public class GVidStatsEvent extends EventObject {
  private static final long serialVersionUID = 1L;
  private int fps;
  private long recvSpd;
  private long sendSpd;

  /**
   * Creates a new GVid status event object.
   * @param source source of the event.
   * @param fps number of frames per second.
   * @param recvSpd receive speed in frames per second.
   * @param sendSpd sens speed in frames per second.
   */
  public GVidStatsEvent( final Object source, final int fps, final long recvSpd, final long sendSpd ) {
    super( source );
    this.fps = fps;
    this.recvSpd = recvSpd;
    this.sendSpd = sendSpd;
  }
  
  /**
   * Returns the number of frames per second.
   * @return frames per second.
   */
  public int getFps() {
    return this.fps;
  }
  
  /**
   * Returns the receive speed.
   * @return speed in bytes per second.
   */
  public long getRecvSpeed() {
    return this.recvSpd;
  }
  
  /**
   * Returns the send speed.
   * @return speed in bytes per second.
   */
  public long getSendSpeed() {
    return this.sendSpd;
  }
}
