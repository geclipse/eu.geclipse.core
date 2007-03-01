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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

class AwtEventConverter implements MouseMotionListener, MouseListener,
         KeyListener, WindowListener, ComponentListener, MouseWheelListener {
  private Events events;
  private byte mouseState;
  private IOException exception;

  AwtEventConverter( final Events events ) {
    this.events = events;
    this.mouseState = 0;
    this.exception = null;
  }

  void checkForException() throws IOException {
    if( this.exception != null )
      throw this.exception;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
   */
  public void mouseClicked( final MouseEvent mouseEvent ) {
    // not needed
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
   */
  public void mouseEntered( final MouseEvent mouseEvent ) {
    try {
      Event event = new Event();
      event.type = Events.ACTIVE_EVENT;
      event.active.gain = 1;
      this.events.sendEvent( event );
    } catch( IOException ioException ) {
      this.exception = ioException;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
   */
  public void mouseExited( final MouseEvent mouseEvent ) {
    try {
      Event event = new Event();
      event.type = Events.ACTIVE_EVENT;
      event.active.gain = 0;
      this.events.sendEvent( event );
    } catch( IOException ioException ) {
      this.exception = ioException;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
   */
  public void mousePressed( final MouseEvent mouseEvent ) {
    try {
      Event event = new Event();
      event.type = Events.MOUSE_PRESS_EVENT;
      event.button.button = ( byte )mouseEvent.getButton();
      this.mouseState |= 1 << event.button.button;
      event.button.x = ( short )mouseEvent.getX();
      event.button.y = ( short )mouseEvent.getY();
      this.events.sendEvent( event );
    } catch( IOException ioException ) {
      this.exception = ioException;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
   */
  public void mouseReleased( final MouseEvent mouseEvent ) {
    try {
      Event event = new Event();
      event.type = Events.MOUSE_RELEASE_EVENT;
      event.button.button = ( byte )mouseEvent.getButton();
      this.mouseState &= ~( 1 << event.button.button );
      event.button.x = ( short )mouseEvent.getX();
      event.button.y = ( short )mouseEvent.getY();
      this.events.sendEvent( event );
    } catch( IOException ioException ) {
      this.exception = ioException;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
   */
  public void keyPressed( final KeyEvent keyEvent ) {
    try {
      Event event = convertKeyEvent( keyEvent );
      event.type = Events.KEY_PRESS_EVENT;
      this.events.sendEvent( event );
    } catch( IOException ioException ) {
      this.exception = ioException;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
   */
  public void keyReleased( final KeyEvent keyEvent ) {
    try {
      Event event = convertKeyEvent( keyEvent );
      event.type = Events.KEY_RELEASE_EVENT;
      this.events.sendEvent( event );
    } catch( IOException ioException ) {
      this.exception = ioException;
    }
  }

  private Event convertKeyEvent( final KeyEvent awtEvent ) {
    Event event = new Event();
    short modifier = 0;
    event.type = Events.KEY_PRESS_EVENT;
    event.key.key = (short) awtEvent.getKeyChar();
    switch (awtEvent.getKeyCode()) {
      case KeyEvent.VK_ENTER:        event.key.key =  13; break;
      case KeyEvent.VK_F1:           event.key.key = 282; break;
      case KeyEvent.VK_F2:           event.key.key = 283; break;
      case KeyEvent.VK_F3:           event.key.key = 284; break;
      case KeyEvent.VK_F4:           event.key.key = 285; break;
      case KeyEvent.VK_F5:           event.key.key = 286; break;
      case KeyEvent.VK_F6:           event.key.key = 287; break;
      case KeyEvent.VK_F7:           event.key.key = 288; break;
      case KeyEvent.VK_F8:           event.key.key = 289; break;
      case KeyEvent.VK_F9:           event.key.key = 290; break;
      case KeyEvent.VK_F10:          event.key.key = 291; break;
      case KeyEvent.VK_F11:          event.key.key = 292; break;
      case KeyEvent.VK_F12:          event.key.key = 293; break;
      case KeyEvent.VK_SHIFT:        event.key.key = 303; break;
      case KeyEvent.VK_CONTROL:      event.key.key = 306; break;
      case KeyEvent.VK_ALT:          event.key.key = 308; break;
      case KeyEvent.VK_PAUSE:        event.key.key = 302; break;
      case KeyEvent.VK_BACK_SPACE:   event.key.key =   8; break;
      case KeyEvent.VK_MINUS:        event.key.key = 269; break;
      case KeyEvent.VK_UP:           event.key.key = 273; break;
      case KeyEvent.VK_DOWN:         event.key.key = 274; break;
      case KeyEvent.VK_RIGHT:        event.key.key = 275; break;
      case KeyEvent.VK_LEFT:         event.key.key = 276; break;
      default:
        event.key.key = (short) awtEvent.getKeyChar();
        break;
    }
    
    //  SDK modifier constants
    if ((awtEvent.getModifiers()
        & InputEvent.SHIFT_MASK) != 0) modifier |= 0x0001; 
    if ((awtEvent.getModifiers()
        & InputEvent.CTRL_MASK) != 0)  modifier |= 0x0040;
    if ((awtEvent.getModifiers()
        & InputEvent.ALT_MASK) != 0)   modifier |= 0x0100;
    if ((awtEvent.getModifiers()
        & InputEvent.META_MASK) != 0)  modifier |= 0x0400;
    event.key.modifier = modifier;
    return event;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
   */
  public void keyTyped( final KeyEvent keyEvent ) {
    // not needed
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
   */
  public void windowActivated( final WindowEvent windowEvent ) {
    // not needed
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
   */
  public void windowClosed( final WindowEvent windowEvent ) {
    // not needed
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
   */
  public void windowClosing( final WindowEvent windowEvent ) {
    try {
      Event event = new Event();
      event.type = Events.QUIT_EVENT;
      this.events.sendEvent( event );
    } catch( IOException ioException ) {
      this.exception = ioException;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
   */
  public void windowDeactivated( final WindowEvent windowEvent ) {
    // not needed
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
   */
  public void windowDeiconified( final WindowEvent windowEvent ) {
    // not needed
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
   */
  public void windowIconified( final WindowEvent windowEvent ) {
    // not needed
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
   */
  public void windowOpened( final WindowEvent windowEvent ) {
    // not needed
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.event.ComponentListener#componentHidden(java.awt.event.ComponentEvent)
   */
  public void componentHidden( final ComponentEvent componentEvent ) {
    // not needed
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.event.ComponentListener#componentMoved(java.awt.event.ComponentEvent)
   */
  public void componentMoved( final ComponentEvent componentEvent ) {
    // not needed
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.event.ComponentListener#componentResized(java.awt.event.ComponentEvent)
   */
  public void componentResized( final ComponentEvent componentEvent ) {
    try {
      Component component = ( Component )componentEvent.getSource();
      Dimension componentSize = component.getSize();
      Event event = new Event();
      event.type = Events.VIDEO_RESIZE_EVENT;
      event.resize.w = ( short )componentSize.getWidth();
      event.resize.h = ( short )componentSize.getHeight();
      this.events.sendEvent( event );
    } catch( IOException ioException ) {
      this.exception = ioException;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.event.ComponentListener#componentShown(java.awt.event.ComponentEvent)
   */
  public void componentShown( final ComponentEvent componentEvent ) {
    // not needed
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
   */
  public void mouseDragged( final MouseEvent mouseEvent ) {
    try {
      Event event = new Event();
      event.type = Events.MOUSE_MOVE_EVENT;
      event.motion.state = this.mouseState;
      event.motion.x = ( short )mouseEvent.getX();
      event.motion.y = ( short )mouseEvent.getY();
      event.motion.xrel = 0;
      event.motion.yrel = 0;
      this.events.sendEvent( event );
    } catch( IOException ioException ) {
      this.exception = ioException;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
   */
  public void mouseMoved( final MouseEvent mouseEvent ) {
    try {
      Event event = new Event();
      event.type = Events.MOUSE_MOVE_EVENT;
      event.motion.state = this.mouseState;
      event.motion.x = ( short )mouseEvent.getX();
      event.motion.y = ( short )mouseEvent.getY();
      event.motion.xrel = 0;
      event.motion.yrel = 0;
      this.events.sendEvent( event );
    } catch( IOException ioException ) {
      this.exception = ioException;
    }
  }

  public void mouseWheelMoved( final MouseWheelEvent mouseWheelEvent ) {
    try {
      Event event = new Event();
      event.type = Events.MOUSE_PRESS_EVENT;
      if( mouseWheelEvent.getWheelRotation() < 0 )
        event.button.button = 4; // should be done in a different way
      else
        event.button.button = 5;
      event.button.x = ( short )mouseWheelEvent.getX();
      event.button.y = ( short )mouseWheelEvent.getY();
      this.events.sendEvent( event );
      event.type = Events.MOUSE_RELEASE_EVENT;
      this.events.sendEvent( event );
    } catch( IOException ioException ) {
      this.exception = ioException;
    }
  }
}
