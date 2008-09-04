package eu.geclipse.ui.internal.layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;

public class PackData {
  
  public int minWidth = -1;
  
  public int minHeight = -1;
  
  public int maxWidth = -1;
  
  public int maxHeight = -1;
  
  private int currentWidth = -1;
  
  private int currentHeight = -1;
  
  private int currentWHint = SWT.DEFAULT;
  
  private int currentHHint = SWT.DEFAULT;
  
  Point computeSize ( final Control control,
                      final int wHint,
                      final int hHint,
                      final boolean flushCache ) {
  
    if ( flushCache ) {
      flushCache();
    }
    
    if ( ( this.currentWidth == -1 ) || ( this.currentHeight == -1 )
        || ( this.currentWHint != wHint ) || ( this.currentHHint != hHint ) ) {
      Point size = control.computeSize( wHint, hHint, flushCache );
      this.currentWidth = size.x;
      this.currentHeight = size.y;
      this.currentWHint = wHint;
      this.currentHHint = hHint;
    }
    
    if ( ( this.minWidth != -1 ) && ( this.currentWidth < this.minWidth ) ) {
      this.currentWidth = this.minWidth;
    } else if ( ( this.maxWidth != -1 ) && ( this.currentWidth > this.maxWidth ) ) {
      this.currentWidth = this.maxWidth;
    }
    
    if ( ( this.minHeight != -1 ) && ( this.currentHeight < this.minHeight ) ) {
      this.currentHeight = this.minHeight;
    } else if ( ( this.maxHeight != -1 ) && ( this.currentHeight > this.maxHeight ) ) {
      this.currentHeight = this.maxHeight;
    }
    
    return new Point( this.currentWidth, this.currentHeight );
    
  }
  
  void flushCache () {
    this.currentWidth = -1;
    this.currentHeight = -1;
    this.currentWHint = SWT.DEFAULT;
    this.currentHHint = SWT.DEFAULT;
  }
  
}
