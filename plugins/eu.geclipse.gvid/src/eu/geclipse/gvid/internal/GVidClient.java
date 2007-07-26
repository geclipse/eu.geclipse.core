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
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.swing.event.EventListenerList;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Display;

import eu.geclipse.core.GridException;
import eu.geclipse.core.ProblemRegistry;
import eu.geclipse.gvid.Activator;
import eu.geclipse.gvid.IDecoder;
import eu.geclipse.gvid.IGVidStatsListener;
import eu.geclipse.gvid.internal.preferences.PreferenceConstants;
import eu.geclipse.ui.dialogs.NewProblemDialog;

/**
 * Output client for GVid. Allows to interact with remote rendered interactive
 * visualization applictions.
 */
public class GVidClient extends Component implements Runnable {
  private static final long serialVersionUID = 1L;
  private Connection connection;
  private IDecoder decoder;
  private int oldFps = -1;
  private long oldRecvSpd = -1;
  private long oldSendSpd = -1;
  private Events events;
  private AwtEventConverter awtEventConverter;
  private boolean running;
  private BufferedImage lastImage;
  private Graphics graphics;
  private final EventListenerList listenerList = new EventListenerList();
  private final FpsCounter fpsCounter = new FpsCounter();
  
  /**
   * Creates a new GVid client.
   * @param in stream to read the video stream from.
   * @param out stream to write the event data to.
   */
  public GVidClient( final InputStream in, final OutputStream out ) {
    IPreferenceStore store = Activator.getDefault().getPreferenceStore();
    String codecName = store.getString( PreferenceConstants.P_CODEC_NAME );
    this.connection = new Connection( in, out );
    this.events = new Events( this.connection );
    this.awtEventConverter = new AwtEventConverter( this.events );
    this.running = false;
    this.lastImage = new BufferedImage( 100, 100, BufferedImage.TYPE_3BYTE_BGR );
    this.decoder = getDecoderImpl( codecName );
    if( this.decoder != null ) {
      this.decoder.init( this.connection, this.events );
    }
    registerEvents();
  }

  private IDecoder getDecoderImpl( final String codecName ) {
    CoreException exception = null;
    IDecoder decoderImpl = null;
    try {
      IExtensionPoint p = Platform.getExtensionRegistry()
        .getExtensionPoint( IDecoder.CODEC_EXTENSION_POINT );
      IExtension[] extensions = p.getExtensions();
      for( IExtension extension : extensions ) {
        IConfigurationElement[] elements = extension.getConfigurationElements();
        for( IConfigurationElement element : elements ) {
          if( IDecoder.EXT_CODEC.equals( element.getName() )
              && codecName.equals( element.getAttribute( IDecoder.EXT_NAME ) ) ) {
            decoderImpl = (IDecoder) element.createExecutableExtension( IDecoder.EXT_DECODER_CLASS );
          }
        }
      }
    } catch( CoreException coreException ) {
      exception = coreException;
      // Activator.logException( coreException );
    }
    if ( decoderImpl == null || exception != null) {
      NewProblemDialog.openProblem( Display.getCurrent().getActiveShell(),
                                    Messages.getString( "GVidClient.gvid" ), //$NON-NLS-1$
                                    Messages.formatMessage( "GVidClient.cantInstanciateCodec", codecName ), //$NON-NLS-1$
                                    exception );
    }
    return decoderImpl;
  }

  /**
   * Sends a quit event to the remote host and stops the GVid client. 
   */
  public void stop() {
    try {
      this.events.sendQuitEvent();
      this.running = false;
    } catch( IOException exception ) {
      // ignore
    }
  }

  /**
   * Checks if the GVid output client is running. 
   * @return true if the GVid output client is running, false otherwise.
   */
  public boolean isRunning() {
    return this.running;
  }

  /* (non-Javadoc)
   * @see java.awt.Component#getPreferredSize()
   */
  @Override
  public Dimension getPreferredSize() {
    return new Dimension( this.lastImage.getWidth(), this.lastImage.getHeight() );
  }

  private void registerEvents() {
    addMouseListener( this.awtEventConverter );
    addMouseMotionListener( this.awtEventConverter );
    addMouseWheelListener( this.awtEventConverter );
    addKeyListener( this.awtEventConverter );
    addComponentListener( this.awtEventConverter );
    // frame.addWindowListener(awtEventConverter);
  }

  private void unregisterEvents() {
    removeMouseListener( this.awtEventConverter );
    removeMouseMotionListener( this.awtEventConverter );
    removeMouseWheelListener( this.awtEventConverter );
    removeKeyListener( this.awtEventConverter );
    removeComponentListener( this.awtEventConverter );
    // frame.removeWindowListener(awtEventConverter);
  }

  /* (non-Javadoc)
   * @see java.awt.Component#getFocusTraversalKeysEnabled()
   */
  @Override
  public boolean getFocusTraversalKeysEnabled() {
    return false;
  }

  /* (non-Javadoc)
   * @see java.lang.Runnable#run()
   */
  public void run() {
    boolean initialSizeSet = false;
    if( this.decoder == null )
      return;
    this.running = true;
    this.graphics = getGraphics();
    requestFocusInWindow();
    while( this.running ) {
      try {
        if( this.decoder.decodeNextFrame() ) {
          this.fpsCounter.incFrameCount();
          if( this.lastImage.getWidth() != this.decoder.getImage().getWidth()
              || this.lastImage.getHeight() != this.decoder.getImage().getHeight() ) {
            this.graphics = getGraphics();
          }
          this.lastImage = this.decoder.getImage();
          if( !initialSizeSet ) {
            setSize( this.lastImage.getWidth(), this.lastImage.getHeight() );
            initialSizeSet = true;
          }
          this.graphics.drawImage( this.lastImage, 0, 0, null );
        } else {
          Thread.sleep( 10 );
        }
        this.awtEventConverter.checkForException();
        updateTransferStats();
        this.events.flush();
      } catch( InterruptedException interruptedEx ) {
        this.running = false;
      } catch( IOException ioException ) {
        this.running = false;
      } catch( Exception exception ) {
        this.running = false;
        final GridException gridException = new GridException( ProblemRegistry.UNKNOWN_PROBLEM, exception );
        Display.getDefault().asyncExec( new Runnable() {
          public void run() {
            NewProblemDialog.openProblem( null,
                                          "Error during video decoding",
                                          "An error occured during video decoding (is the right codec selected?)",
                                          gridException, null );
          }
        } );
      }
    }
    unregisterEvents();
  }

  private void updateTransferStats() {
    if( this.fpsCounter.getFps() != this.oldFps
        || this.connection.getCurrentRecvSpeed() != this.oldRecvSpd
        || this.connection.getCurrentSendSpeed() != this.oldSendSpd ) {
      this.oldFps = this.fpsCounter.getFps();
      this.oldRecvSpd = this.connection.getCurrentRecvSpeed();
      this.oldSendSpd = this.connection.getCurrentSendSpeed();
      GVidStatsEvent event = new GVidStatsEvent( this,
                                                 this.oldFps,
                                                 this.oldRecvSpd,
                                                 this.oldSendSpd );
      fireStatsEvent( event );
    }
  }

  /**
   * Adds a listener for GVid status updates. 
   * @param listener the listener to register.
   */
  public void addStatsListener( final IGVidStatsListener listener ) {
    this.listenerList.add(IGVidStatsListener.class, listener);
  }
  
  /**
   * Removes a listener for GVid status updates.
   * @param listener the listener to unregister.
   */
  public void removeStatsListener( final IGVidStatsListener listener ) {
    this.listenerList.remove(IGVidStatsListener.class, listener);
  }

  private void fireStatsEvent( final GVidStatsEvent event ) {
    IGVidStatsListener[] listeners = this.listenerList.getListeners( IGVidStatsListener.class );
    for (int i = 0; i < listeners.length; i++) {
       listeners[i].statsUpdated( event );
    }
  }
  
  /* (non-Javadoc)
   * @see java.awt.Component#paint(java.awt.Graphics)
   */
  @Override
  public void paint( final Graphics g ) {
    g.drawImage( this.lastImage, 0, 0, null );
  }
}
