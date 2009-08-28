package eu.geclipse.traceview.internal;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.traceview.EventType;
import eu.geclipse.traceview.IEvent;
import eu.geclipse.traceview.ITraceView;
import eu.geclipse.traceview.preferences.PreferenceConstants;
import eu.geclipse.traceview.utils.AbstractEventMarker;

public class DefaultEventMarker extends AbstractEventMarker {
  protected static boolean sendEventDraw;
  protected static boolean recvEventDraw;
  protected static boolean testEventDraw;
  protected static boolean otherEventDraw;
  protected static boolean sendEventFill;
  protected static boolean recvEventFill;
  protected static boolean testEventFill;
  protected static boolean otherEventFill;
  protected static Color sendEventFillColor;
  protected static Color recvEventFillColor;
  protected static Color testEventFillColor;
  protected static Color otherEventFillColor;
  protected static Color sendEventColor;
  protected static Color recvEventColor;
  protected static Color testEventColor;
  protected static Color otherEventColor;
  protected static Color messageColor;
  protected static IPropertyChangeListener listener;

  protected Color bgColor;
  protected Color fgColor;

  public DefaultEventMarker() {
    if (listener == null) {
      listener = new IPropertyChangeListener() {
        public void propertyChange( final PropertyChangeEvent event ) {
          handleProperyChanged( event );
        }
      };
      IPreferenceStore store = Activator.getDefault().getPreferenceStore();
      store.addPropertyChangeListener( listener );
      updatePropertiesFromPreferences();
    }
  }
  
  @Override
  public Color getBackgroundColor( int type ) {
    return this.bgColor;
  }

  @Override
  public Color getForegroundColor( int type ) {
    return this.fgColor;
  }

  @Override
  public Color getMessageColor() {
    return messageColor;
  }

  @Override
  public int mark( final IEvent event ) {
    int result = 0;
    if (event.getType().equals( EventType.SEND )) {
      this.bgColor = sendEventFill ? sendEventFillColor : null;
      this.fgColor = sendEventDraw ? sendEventColor : null;
      result = Ellipse_Event;
    } else if (event.getType().equals( EventType.RECV )) {
      this.bgColor = recvEventFill ? recvEventFillColor : null;
      this.fgColor = recvEventDraw ? recvEventColor : null;
      result = Ellipse_Event;
    } else if (event.getType().equals( EventType.TEST )) {
      this.bgColor = testEventFill ? testEventFillColor : null;
      this.fgColor = testEventDraw ? testEventColor : null;
      result = Rectangle_Event;
    } else if (event.getType().equals( EventType.OTHER )) {
      this.bgColor = otherEventFill ? otherEventFillColor : null;
      this.fgColor = otherEventDraw ? otherEventColor : null;
      result = Diamond_Event;
    }  
    return result;
  }

  void updatePropertiesFromPreferences() {
    IPreferenceStore store = Activator.getDefault().getPreferenceStore();
    // event draw
    sendEventDraw = store.getBoolean( PreferenceConstants.P_SEND_EVENT
                                      + PreferenceConstants.P_DRAW );
    recvEventDraw = store.getBoolean( PreferenceConstants.P_RECV_EVENT
                                      + PreferenceConstants.P_DRAW );
    testEventDraw = store.getBoolean( PreferenceConstants.P_TEST_EVENT
                                      + PreferenceConstants.P_DRAW );
    otherEventDraw = store.getBoolean( PreferenceConstants.P_RECV_EVENT
                                       + PreferenceConstants.P_DRAW );
    // event fill
    sendEventFill = store.getBoolean( PreferenceConstants.P_SEND_EVENT
                                      + PreferenceConstants.P_FILL );
    recvEventFill = store.getBoolean( PreferenceConstants.P_RECV_EVENT
                                      + PreferenceConstants.P_FILL );
    testEventFill = store.getBoolean( PreferenceConstants.P_TEST_EVENT
                                      + PreferenceConstants.P_FILL );
    otherEventFill = store.getBoolean( PreferenceConstants.P_RECV_EVENT
                                            + PreferenceConstants.P_FILL );
    // message color
    messageColor = new Color( Display.getDefault(),
                                   PreferenceConverter.getColor( store,
                                                                 PreferenceConstants.P_MESSAGE
                                                                     + PreferenceConstants.P_COLOR ) );
    // event color
    sendEventColor = new Color( Display.getDefault(),
                                PreferenceConverter.getColor( store,
                                                              PreferenceConstants.P_SEND_EVENT
                                                                  + PreferenceConstants.P_COLOR ) );
    recvEventColor = new Color( Display.getDefault(),
                                PreferenceConverter.getColor( store,
                                                              PreferenceConstants.P_RECV_EVENT
                                                                  + PreferenceConstants.P_COLOR ) );
    testEventColor = new Color( Display.getDefault(),
                                PreferenceConverter.getColor( store,
                                                              PreferenceConstants.P_TEST_EVENT
                                                                  + PreferenceConstants.P_COLOR ) );
    otherEventColor = new Color( Display.getDefault(),
                                 PreferenceConverter.getColor( store,
                                                               PreferenceConstants.P_OTHER_EVENT
                                                                   + PreferenceConstants.P_COLOR ) );
    // event fill color
    sendEventFillColor = new Color( Display.getDefault(),
                                    PreferenceConverter.getColor( store,
                                                                  PreferenceConstants.P_SEND_EVENT
                                                                      + PreferenceConstants.P_FILL_COLOR ) );
    recvEventFillColor = new Color( Display.getDefault(),
                                    PreferenceConverter.getColor( store,
                                                                  PreferenceConstants.P_RECV_EVENT
                                                                      + PreferenceConstants.P_FILL_COLOR ) );
    testEventFillColor = new Color( Display.getDefault(),
                                    PreferenceConverter.getColor( store,
                                                                  PreferenceConstants.P_TEST_EVENT
                                                                      + PreferenceConstants.P_FILL_COLOR ) );
    otherEventFillColor = new Color( Display.getDefault(),
                                     PreferenceConverter.getColor( store,
                                                                   PreferenceConstants.P_OTHER_EVENT
                                                                       + PreferenceConstants.P_FILL_COLOR ) );
  }

  protected void handleProperyChanged( final PropertyChangeEvent event ) {
    IPreferenceStore store = Activator.getDefault().getPreferenceStore();
    String property = event.getProperty();
    // draw
    if( property.equals( PreferenceConstants.P_SEND_EVENT
                                    + PreferenceConstants.P_DRAW ) ) {
      sendEventDraw = store.getBoolean( property );
    } else if( property.equals( PreferenceConstants.P_RECV_EVENT
                                           + PreferenceConstants.P_DRAW ) ) {
      recvEventDraw = store.getBoolean( property );
    } else if( property.equals( PreferenceConstants.P_TEST_EVENT
                                           + PreferenceConstants.P_DRAW ) ) {
      testEventDraw = store.getBoolean( property );
    } else if( property.equals( PreferenceConstants.P_OTHER_EVENT
                                           + PreferenceConstants.P_DRAW ) ) {
      otherEventDraw = store.getBoolean( property );
    }
    // Fill
    else if( property.equals( PreferenceConstants.P_SEND_EVENT
                                         + PreferenceConstants.P_FILL ) ) {
      sendEventFill = store.getBoolean( property );
    } else if( property.equals( PreferenceConstants.P_RECV_EVENT
                                           + PreferenceConstants.P_FILL ) ) {
      recvEventFill = store.getBoolean( property );
    } else if( property.equals( PreferenceConstants.P_TEST_EVENT
                                           + PreferenceConstants.P_FILL ) ) {
      testEventFill = store.getBoolean( property );
    } else if( property.equals( PreferenceConstants.P_OTHER_EVENT
                                           + PreferenceConstants.P_FILL ) ) {
      otherEventFill = store.getBoolean( property );
    }
    // Message color
    else if( property.equals( PreferenceConstants.P_MESSAGE
                                         + PreferenceConstants.P_COLOR ) ) {
      messageColor.dispose();
      messageColor = new Color( Display.getDefault(),
                                PreferenceConverter.getColor( store, property ) );
    }
    // Event Color
    else if( property.equals( PreferenceConstants.P_SEND_EVENT
                                         + PreferenceConstants.P_COLOR ) ) {
      sendEventColor.dispose();
      sendEventColor = new Color( Display.getDefault(),
                                  PreferenceConverter.getColor( store, property ) );
    } else if( property.equals( PreferenceConstants.P_RECV_EVENT
                                           + PreferenceConstants.P_COLOR ) ) {
      recvEventColor.dispose();
      recvEventColor = new Color( Display.getDefault(),
                                  PreferenceConverter.getColor( store, property ) );
    } else if( property.equals( PreferenceConstants.P_TEST_EVENT
                                           + PreferenceConstants.P_COLOR ) ) {
      testEventColor.dispose();
      testEventColor = new Color( Display.getDefault(),
                                  PreferenceConverter.getColor( store, property ) );
    } else if( property.equals( PreferenceConstants.P_OTHER_EVENT
                                           + PreferenceConstants.P_COLOR ) ) {
      otherEventColor.dispose();
      otherEventColor = new Color( Display.getDefault(),
                                   PreferenceConverter.getColor( store, property ) );
    }
    // Event Fill Color
    else if( property.equals( PreferenceConstants.P_SEND_EVENT
                                         + PreferenceConstants.P_FILL_COLOR ) ) {
      sendEventFillColor.dispose();
      sendEventFillColor = new Color( Display.getDefault(),
                                      PreferenceConverter.getColor( store, property ) );
    } else if( property.equals( PreferenceConstants.P_RECV_EVENT
                                           + PreferenceConstants.P_FILL_COLOR ) ) {
      recvEventFillColor.dispose();
      recvEventFillColor = new Color( Display.getDefault(),
                                      PreferenceConverter.getColor( store, property ) );
    } else if( property.equals( PreferenceConstants.P_TEST_EVENT
                                           + PreferenceConstants.P_FILL_COLOR ) ) {
      testEventFillColor.dispose();
      testEventFillColor = new Color( Display.getDefault(),
                                      PreferenceConverter.getColor( store, property ) );
    } else if( property.equals( PreferenceConstants.P_OTHER_EVENT
                                           + PreferenceConstants.P_FILL_COLOR ) ) {
      otherEventFillColor.dispose();
      otherEventFillColor = new Color( Display.getDefault(),
                                       PreferenceConverter.getColor( store, property ) );
    }
    try {
      ITraceView traceView = ( ITraceView )PlatformUI.getWorkbench()
        .getActiveWorkbenchWindow()
        .getActivePage()
        .showView( "eu.geclipse.traceview.views.TraceView" ); //$NON-NLS-1$
      traceView.redraw();
    } catch( PartInitException e ) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
