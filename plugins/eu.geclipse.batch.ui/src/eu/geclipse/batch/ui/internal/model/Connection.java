/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     UCY (http://www.cs.ucy.ac.cy)
 *      - Harald Gjermundrod (harald@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.batch.ui.internal.model;

import org.eclipse.draw2d.Graphics;

/**
 * A connection between two distinct batch resources.
 */
public class Connection extends ModelElement {

  /**
   * Constant for a solid line.
   */
  public static final int SOLID_CONNECTION = Graphics.LINE_SOLID ;
  /**
   * Constant for a dashed line.
   */
  public static final int DASHED_CONNECTION = Graphics.LINE_DASH;
  /**
   * Property ID to use when the line style of this connection is modified.
   */
  public static final String LINESTYLE_PROP = "LineStyle"; //$NON-NLS-1$

  private static final long serialVersionUID = 1;
  private boolean isConnected;
  private int lineStyle;
  private BatchResource source;
  private BatchResource target;

  /**
   * Create a connection between two distinct batch resources.
   *
   * @param source a source endpoint for this connection (non null)
   * @param target a target endpoint for this connection (non null)
   * @param lineStyle the line style.
   */
  public Connection( final BatchResource source, final BatchResource target, final int lineStyle ) {
    this.lineStyle = lineStyle;
    this.source = source;
    this.target = target;
  }

  /**
   * Disconnect this connection from the resources it is attached to.
   */
  public void disconnect() {
    if ( this.isConnected ) {
      this.source.removeConnection( this );
      this.target.removeConnection( this );
      this.isConnected = false;
    }
  }

  /**
   * Returns the line drawing style of this connection.
   *
   * @return an int value of the drawing style.
   */
  public int getLineStyle() {
    return this.lineStyle;
  }

  /**
   * Returns the source endpoint of this connection.
   *
   * @return a non-null BatchResource instance
   */
  public BatchResource getSource() {
    return this.source;
  }

  /**
   * Returns the target endpoint of this connection.
   *
   * @return a non-null BatchResource instance
   */
  public BatchResource getTarget() {
    return this.target;
  }

  /**
   * Reconnect this connection. The connection will reconnect with the BatchResource it
   * was previously attached to.
   */
  public void reconnect() {
    if ( !this.isConnected ) {
      this.source.addConnection( this );
      this.target.addConnection( this );
      this.isConnected = true;
    }
  }

  /**
   * Reconnect to a different source and/or target batch resource. The connection will
   * disconnect from its current attachments and reconnect to the new source and
   * target.
   *
   * @param newSource a new source endpoint for this connection (non null)
   * @param newTarget a new target endpoint for this connection (non null)
   * @throws IllegalArgumentException if any of the parameters are null or
   *           newSource == newTarget
   */
  public void reconnect( final BatchResource newSource, final BatchResource newTarget ) {
    if ( newSource == null || newTarget == null || newSource == newTarget ) {
      throw new IllegalArgumentException();
    }
    disconnect();
    this.source = newSource;
    this.target = newTarget;
    reconnect();
  }
}
