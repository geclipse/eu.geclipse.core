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

import java.io.IOException;
import org.eclipse.core.runtime.IStatus;
import eu.geclipse.gvid.Activator;
import eu.geclipse.gvid.IEvents;

class ActiveEvent {
  /** 0 if window isn't active anymore, other value if window gained activeness */
  byte gain;
}

class KeyboardEvent {
  /** Keycode. */
  short key;
  /** Bitmask containing modifier information. */
  short modifier;
}

class MouseMotionEvent {
  /** Bitmask containing the state of the mouse buttons. */
  byte state;
  /** New x position of cursor in window. */
  short x;
  /** New y position of cursor in window. */
  short y;
  /** Relative mouse movement in x direction. */
  short xrel;
  /** Relative mouse movement in y direction. */
  short yrel;
}

class MouseButtonEvent {
  /** Mouse button number. */
  byte button;
  /** X position of the mouse cursor. */
  short x; 
  /** Y position of the mouse cursor. */
  short y;
}

class ResizeEvent {
  /** New width of window. */
  short w;
  /** New height of window. */
  short h;
}

class FrameFinishedEvent {
  /** Number of frame. */
  int frame_num;
  /** Encryption time in usec */
  int enc_time;
  /** Compression time in usec */
  int comp_time;
}

class Event {
  /** Event type constant (from EventTypes enum). */
  byte type;
  /** Active event (focus gained/focus lost) */
  ActiveEvent active = new ActiveEvent();
  /** Keyboard event (key pressed/released). */
  KeyboardEvent key = new KeyboardEvent();
  /** Mouse motion event. */
  MouseMotionEvent motion = new MouseMotionEvent();
  /** Mouse button event (button pressed/released). */
  MouseButtonEvent button = new MouseButtonEvent();
  /** Window resize event. */
  ResizeEvent resize = new ResizeEvent();
  FrameFinishedEvent frame_finished = new FrameFinishedEvent();
}

/**
 * GVid event handling class. 
 */
public class Events implements IEvents {
  /** No event occurred. */
  public final static int NO_EVENT = 0;
  /** Window activation/deactivation. */
  public final static int ACTIVE_EVENT = 1;
  /** Key on keyboard pressed. */
  public final static int KEY_PRESS_EVENT = 2; 
  /** Key on keyboard released.*/
  public final static int KEY_RELEASE_EVENT = 3;
  /** Mouse moved. */
  public final static int MOUSE_MOVE_EVENT = 4;
  /** Mouse button pressed. */
  public final static int MOUSE_PRESS_EVENT = 5;
  /** Mouse button released. */
  public final static int MOUSE_RELEASE_EVENT = 6;
  /** Application quit. */
  public final static int QUIT_EVENT = 7;
  /** Window resize. */
  public final static int VIDEO_RESIZE_EVENT = 8;
  /** Frame decoded. */
  public final static int FRAME_FINISHED = 9;
  /** Trigger redraw. */
  public final static int REDRAW = 10;
  private Connection connection;
  private boolean haveOldMotion;
  private Event oldMotion, redrawEvent, quitEvent;
  private int lastDecoderFrameNr;

  /**
   * Creates a Event handling class for the specified Connection.
   * @param connection connection on which the events should be sent
   */
  public Events( final Connection connection ) {
    this.connection = connection;
    this.haveOldMotion = false;
    this.oldMotion = new Event();
    this.oldMotion.motion.x = 0;
    this.oldMotion.motion.y = 0;
    this.oldMotion.motion.xrel = 0;
    this.oldMotion.motion.yrel = 0;
    this.oldMotion.type = MOUSE_MOVE_EVENT;
    this.redrawEvent = new Event();
    this.redrawEvent.type = REDRAW;
    this.quitEvent = new Event();
    this.quitEvent.type = QUIT_EVENT;
    this.lastDecoderFrameNr = -1;
  }

  /**
   * Reads a Event from the Connection. If no data is available from the
   * connection null is returned.
   * @return a new Event object.
   */
  Event recvEvent() throws IOException {
    Event event = null;
    if( this.connection.getNumBytesInBuffer() != 0 ) {
      event = new Event();
      event.type = this.connection.readByte();
      switch( event.type ) {
        case QUIT_EVENT:
          break;
        case VIDEO_RESIZE_EVENT:
          event.resize.w = this.connection.readUint16();
          event.resize.h = this.connection.readUint16();
          break;
        case ACTIVE_EVENT:
          event.active.gain = this.connection.readByte();
          break;
        case KEY_PRESS_EVENT:
        case KEY_RELEASE_EVENT:
          event.key.key = this.connection.readUint16();
          event.key.modifier = this.connection.readUint16();
          break;
        case MOUSE_MOVE_EVENT:
          event.motion.state = this.connection.readByte();
          event.motion.x = this.connection.readUint16();
          event.motion.y = this.connection.readUint16();
          event.motion.xrel = this.connection.readUint16();
          event.motion.yrel = this.connection.readUint16();
          break;
        case MOUSE_PRESS_EVENT:
        case MOUSE_RELEASE_EVENT:
          event.button.button = this.connection.readByte();
          event.button.x = this.connection.readUint16();
          event.button.y = this.connection.readUint16();
          break;
        case FRAME_FINISHED:
          event.frame_finished.frame_num = this.connection.readUint32();
          event.frame_finished.enc_time = this.connection.readUint32();
          event.frame_finished.comp_time = this.connection.readUint32();
          this.lastDecoderFrameNr = event.frame_finished.frame_num;
          // if (encoder != NULL)
          // encoder.setDecoderFrameNumber(event.frame_finished.frame_num,
          // event.frame_finished.enc_time, event.frame_finished.comp_time);
          break;
        case REDRAW:
          break;
        case NO_EVENT:
          break;
        default:
          Activator.logMessage( IStatus.ERROR, 
                                Messages.getString( "Events.invalidEventType" ) ); //$NON-NLS-1$
          break;
      }
    }
    return event;
  }

  int getLastDecoderFrameNr() {
    return this.lastDecoderFrameNr;
  }

  /**
   * Sends the specified Event object over the Connection.
   * @param event Event object to send.
   */
  void sendEvent( final Event event ) throws IOException {
    synchronized( this.connection ) {
      if( !( event == this.oldMotion ) ) { // skip if called from flush
        if( this.haveOldMotion
            && ( event.type != MOUSE_MOVE_EVENT
                 || event.motion.state != this.oldMotion.motion.state ) ) {
          flush();
        }
        if( event.type == MOUSE_MOVE_EVENT
            && ( !this.haveOldMotion
                 || this.oldMotion.motion.state == event.motion.state ) ) {
          this.oldMotion.motion.state = event.motion.state;
          this.oldMotion.motion.x = event.motion.x;
          this.oldMotion.motion.y = event.motion.y;
          this.oldMotion.motion.xrel += event.motion.xrel;
          this.oldMotion.motion.yrel += event.motion.yrel;
          this.haveOldMotion = true;
          return;
        }
      }
      byte[] sendBuffer = new byte[ 30/* sizeof(Event) */]; // TODO use real event size
      int pos = 0;
      sendBuffer[ pos++ ] = event.type;
      switch( event.type ) {
        case QUIT_EVENT:
          break;
        case VIDEO_RESIZE_EVENT:
          pos = putUint16( pos, sendBuffer, event.resize.w );
          pos = putUint16( pos, sendBuffer, event.resize.h );
          break;
        case ACTIVE_EVENT:
          sendBuffer[ pos++ ] = event.active.gain;
          break;
        case KEY_PRESS_EVENT:
        case KEY_RELEASE_EVENT:
          pos = putUint16( pos, sendBuffer, event.key.key );
          pos = putUint16( pos, sendBuffer, event.key.modifier );
          break;
        case MOUSE_MOVE_EVENT:
          sendBuffer[ pos++ ] = event.motion.state;
          pos = putUint16( pos, sendBuffer, event.motion.x );
          pos = putUint16( pos, sendBuffer, event.motion.y );
          pos = putUint16( pos, sendBuffer, event.motion.xrel );
          pos = putUint16( pos, sendBuffer, event.motion.yrel );
          break;
        case MOUSE_PRESS_EVENT:
        case MOUSE_RELEASE_EVENT:
          sendBuffer[ pos++ ] = event.button.button;
          pos = putUint16( pos, sendBuffer, event.button.x );
          pos = putUint16( pos, sendBuffer, event.button.y );
          break;
        case FRAME_FINISHED:
          pos = putUint32( pos, sendBuffer, event.frame_finished.frame_num );
          pos = putUint32( pos, sendBuffer, event.frame_finished.enc_time );
          pos = putUint32( pos, sendBuffer, event.frame_finished.comp_time );
          break;
        case REDRAW:
          break;
        case NO_EVENT:
          break;
        default:
          pos = 0;
          Activator.logMessage( IStatus.ERROR, 
                                Messages.getString( "Events.invalidEventType" ) ); //$NON-NLS-1$
          break;
      }
      if( pos != 0 || event.type == REDRAW ) {
        this.connection.writeData( sendBuffer, pos );
      }
    }
  }

  /**
   * Sends an Event to the server that the image should be redrawn an
   * newly encoded.
   */
  void sendRedrawEvent() throws IOException {
    sendEvent( this.redrawEvent );
  }

  void sendQuitEvent() throws IOException {
    sendEvent( this.quitEvent );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.gvid.internal.IEvents#sendFrameFinished(int, int, int)
   */
  public void sendFrameFinished( final int frame_num, final int enc_time,
                                 final int comp_time ) throws IOException {
    Event event = new Event();
    event.type = FRAME_FINISHED;
    event.frame_finished.frame_num = frame_num;
    event.frame_finished.enc_time = enc_time;
    event.frame_finished.comp_time = comp_time;
    sendEvent( event );
  }

  /**
   * Sends all events which are buffered. Mouse motion events can be
   * buffered because successive mouse motion events get merged together
   * to a single one.
   */
  void flush() throws IOException {
    synchronized( this.connection ) {
      if( this.haveOldMotion ) {
        sendEvent( this.oldMotion );
        this.connection.flush();
        this.oldMotion.motion.x = 0;
        this.oldMotion.motion.y = 0;
        this.oldMotion.motion.xrel = 0;
        this.oldMotion.motion.yrel = 0;
        this.haveOldMotion = false;
      }
    }
  }

  int putUint16( final int pos, final byte[] buffer, final short val ) {
    int newPos = pos;
    buffer[ newPos++ ] = ( byte )( val >> 8 );
    buffer[ newPos++ ] = ( byte )( val & 0x00FF );
    return newPos;
  }

  int putUint32( final int pos, final byte[] buffer, final int val ) {
    int newPos = pos;
    buffer[ newPos++ ] = ( byte )( val >> 24 );
    buffer[ newPos++ ] = ( byte )( ( val & 0x00FF0000 ) >> 16 );
    buffer[ newPos++ ] = ( byte )( ( val & 0x0000FF00 ) >> 8 );
    buffer[ newPos++ ] = ( byte )( val & 0x000000FF );
    return newPos;
  }
}
