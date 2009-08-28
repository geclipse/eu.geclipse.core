package eu.geclipse.traceview.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

import eu.geclipse.traceview.IEvent;
import eu.geclipse.traceview.IEventMarker;
import eu.geclipse.traceview.ITrace;


public abstract class AbstractEventMarker implements IEventMarker {
  protected ITrace trace;

  public Color getBackgroundColor( int type ) {
    return null;
  }

  public Color getCanvasBackgroundColor() {
    return null;
  }

  public Color getForegroundColor( int type ) {
    return null;
  }

  public Color getMessageColor() {
    return null;
  }

  public int getLineStyle( int type ) {
    return SWT.LINE_SOLID;
  }

  public int getLineWidth( int type ) {
    return 0;
  }

  public String getToolTip() {
    return null;
  }

  public int mark( IEvent event ) {
    return 0;
  }

  public void setTrace( ITrace trace ) {
    this.trace = trace;
  }
}
