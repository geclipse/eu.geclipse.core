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

class FpsCounter {
  private long frameNr, lastFrameNr;
  private int fps;
  private long lastMeasureTime;

  FpsCounter() {
    this.frameNr = 0;
    this.lastFrameNr = 0;
    this.fps = 0;
    this.lastMeasureTime = System.currentTimeMillis();
  }

  /**
   * @brief Increments frame count. This method has to be called for every frame
   *        processed.
   */
  void incFrameCount() {
    this.frameNr++;
  }

  /**
   * @brief Returns frames per second value. Returns the number of frames which
   *        where processed in the last second.
   * @return frames per second value.
   */
  int getFps() {
    long currentTime;
    long timeDiff;
    currentTime = System.currentTimeMillis();
    timeDiff = currentTime - this.lastMeasureTime;
    if( timeDiff > 1000 ) {
      this.lastMeasureTime = currentTime;
      this.fps = ( int )( ( ( this.frameNr - this.lastFrameNr ) * 1000 ) / timeDiff );
      this.lastFrameNr = this.frameNr;
    }
    return this.fps;
  }
}
