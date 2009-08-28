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

package eu.geclipse.traceview.nope.markers;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.traceview.EventType;
import eu.geclipse.traceview.IEvent;
import eu.geclipse.traceview.ITraceView;
import eu.geclipse.traceview.nope.Activator;
import eu.geclipse.traceview.nope.preferences.PreferenceConstants;
import eu.geclipse.traceview.nope.tracereader.Event;
import eu.geclipse.traceview.utils.AbstractEventMarker;

/**
 * Marks Nope Events according to the preferences of the NOPE Legend
 */
public class NopeEventMarker extends AbstractEventMarker {
  private IPreferenceStore store;
  private Color foreground;
  private Color background;
  private Color canvasBackground;
  private Color[] colors = new Color[ PreferenceConstants.Names.length ];
  private boolean[] enabled = new boolean[ PreferenceConstants.Names.length ];
  private int[] shapes = new int[ PreferenceConstants.Names.length ];
  private Color messageColor;

  /**
   * Creates a new NopeEventMarker
   */
  public NopeEventMarker() {
    this.store = Activator.getDefault().getPreferenceStore();
    updateFromPreferences();
    this.store.addPropertyChangeListener( new IPropertyChangeListener() {

      public void propertyChange( final PropertyChangeEvent event ) {
        handleProperyChanged( event );
      }
    } );
  }

  private void updateFromPreferences() {
    for( int i = 0; i < PreferenceConstants.Names.length; i++ ) {
      this.colors[ i ] = new Color( Display.getDefault(),
                                    PreferenceConverter.getColor( this.store,
                                                                  PreferenceConstants.Names[ i ]
                                                                      + PreferenceConstants.color ) );
    }
    for( int i = 0; i < PreferenceConstants.Names.length; i++ ) {
      this.enabled[ i ] = this.store.getBoolean( PreferenceConstants.Names[ i ]
                                                 + PreferenceConstants.enabled );
    }
    for( int i = 0; i < PreferenceConstants.Names.length; i++ ) {
      this.shapes[ i ] = this.store.getInt( PreferenceConstants.Names[ i ]
                                            + PreferenceConstants.shape );
    }
  }

  protected void handleProperyChanged( final PropertyChangeEvent event ) {
    boolean running = true;
    for( int i = 0; i < PreferenceConstants.Names.length && running; i++ ) {
      if( event.getProperty().equals( PreferenceConstants.Names[ i ]
                                      + PreferenceConstants.color ) ) {
        this.colors[ i ].dispose();
        this.colors[ i ] = new Color( Display.getDefault(),
                                      PreferenceConverter.getColor( this.store,
                                                                    PreferenceConstants.Names[ i ]
                                                                        + PreferenceConstants.color ) );
        running = false;
      } else if( event.getProperty().equals( PreferenceConstants.Names[ i ]
                                             + PreferenceConstants.enabled ) ) {
        this.enabled[ i ] = this.store.getBoolean( PreferenceConstants.Names[ i ]
                                                   + PreferenceConstants.enabled );
        running = false;
      } else if( event.getProperty().equals( PreferenceConstants.Names[ i ]
                                             + PreferenceConstants.shape ) ) {
        this.shapes[ i ] = this.store.getInt( PreferenceConstants.Names[ i ]
                                              + PreferenceConstants.shape );
        running = false;
      }
    }
    Display.getDefault().asyncExec( new Runnable() {
      public void run() {
        try {
          ITraceView traceView = ( ITraceView )PlatformUI.getWorkbench()
            .getActiveWorkbenchWindow()
            .getActivePage()
            .showView( "eu.geclipse.traceview.views.TraceView" ); //$NON-NLS-1$
          traceView.redraw();
        } catch( PartInitException exception ) {
          Activator.logException( exception );
        }
      }
    } );
  }

  @Override
  public Color getForegroundColor( final int type ) {
    return this.foreground;
  }

  @Override
  public Color getBackgroundColor( final int type ) {
    return this.background;
  }
  
  @Override
  public Color getMessageColor() {
    return this.messageColor;
  }

  @Override
  public Color getCanvasBackgroundColor() {
    return this.canvasBackground;
  }
  
  @Override
  public int mark( final IEvent event ) {
    int result = 0;
    boolean running = true;
    if( event instanceof Event ) {
      Event nopeEvent = ( Event )event;
      for( int i = 0; i < PreferenceConstants.Names.length && running; i++ ) {
        if( this.enabled[ i ] ) {
          if( nopeEvent.getSubType() == PreferenceConstants.Codes[ i ] ) {
            result = this.shapes[ i ];
            this.foreground = this.colors[ i ];
            this.background = null;
          }
        }
      }
      Event lenCheckEvent = nopeEvent;
      if (nopeEvent.getType().equals( EventType.SEND )) lenCheckEvent = ( Event )nopeEvent.getPartnerEvent();
      this.messageColor = Display.getDefault().getSystemColor( SWT.COLOR_GREEN );
      if (lenCheckEvent != null && lenCheckEvent.getType().equals( EventType.RECV )) {
        if (lenCheckEvent.getAcceptedMessageLength() > lenCheckEvent.getSupposedMessageLength()) {
          this.messageColor = Display.getDefault().getSystemColor( SWT.COLOR_RED );
        } else if (lenCheckEvent.getAcceptedMessageLength() < lenCheckEvent.getSupposedMessageLength()) {
          this.messageColor = Display.getDefault().getSystemColor( SWT.COLOR_YELLOW );
        }
      }
      if (nopeEvent.getType().equals( EventType.RECV ) &&
          nopeEvent.getSupposedPartnerProcess() == -1) {
        this.canvasBackground = Display.getDefault().getSystemColor( SWT.COLOR_GRAY );
      } else {
        this.canvasBackground = null;
      }
    }
    return Ellipse_Event;
  }
}
