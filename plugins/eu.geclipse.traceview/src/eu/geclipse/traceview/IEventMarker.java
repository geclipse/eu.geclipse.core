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

package eu.geclipse.traceview;

import org.eclipse.swt.graphics.Color;

/**
 * Interface to be implemented by event markers.
 */
public interface IEventMarker {

  /** does not mark the event */
  public static int No_Mark = 0;
  /** draws no event */
  public static int No_Event = 1;
  /** draws a rectangle event */
  public static int Rectangle_Event = 2;
  /** draws an ellipse event */
  public static int Ellipse_Event = 4;
  /** draws a cross event */
  public static int Cross_Event = 8;
  /** draws a triangle event */
  public static int Triangle_Event = 16;
  /** draws a diamond event */
  public static int Diamond_Event = 32;
  /** draws a connection */
  public int Connection = 64;
  /** draws a line */
  public int Long_Rectangle = 128;

  /**
   * Called before a redraw takes place. Can be used to prepare
   * the marker for drawing the following events.
   */
  public void startMarking();

  /**
   * Checks if the specified event should be marked.
   * 
   * @param event The event to check.
   * @return the types of markers applied to the event.
   */
  public int mark( final IEvent event );

  /**
   * Returns the foreground Color for the specified type of the last marked
   * event.
   * 
   * @param type The type of the EventMarker
   * @return the Color to mark the event. null for no foreground
   */
  public Color getForegroundColor( int type );

  /**
   * Returns the background Color for the specified type of the last marked
   * event.
   * 
   * @param type the type of the EventMarker
   * @return the color to mark the event. null for no background
   */
  public Color getBackgroundColor( int type );

  /**
   * Returns the background Color of the canvas for the last marked
   * event.
   * 
   * @return the color to mark the event. null for no canvas background
   */
  public Color getCanvasBackgroundColor();

  /**
   * Returns the line width for the specified type of the last marked event.
   * 
   * @param type the type of the EventMarker
   * @return the line width
   */
  public int getLineWidth( int type );

  /**
   * Returns the line style for the specified type of the last marked event.
   * 
   * @param type the type of the EventMarker
   * @return the line style
   */
  public int getLineStyle( int type );

  /**
   * Returns the color of the connection arrow.
   * 
   * @return the color of the connection arrow
   */
  public Color getMessageColor();

  /**
   * Sets the Trace for the EventMarker
   * 
   * @param trace
   */
  public void setTrace( ITrace trace );

  public String getToolTip();
}
