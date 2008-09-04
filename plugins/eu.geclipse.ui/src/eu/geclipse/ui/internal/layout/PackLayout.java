package eu.geclipse.ui.internal.layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Scrollable;

public class PackLayout extends Layout {
  
  public int type;
  
  public int marginWidth;
  
  public int marginHeight;
  
  public int spacing;
  
  public PackLayout() {
    this( SWT.HORIZONTAL );
  }
  
  public PackLayout( final int type ) {
    this.type = type;
    this.marginWidth = 0;
    this.marginHeight = 0;
    this.spacing = 0;
  }

  @Override
  protected Point computeSize( final Composite composite,
                               final int wHint,
                               final int hHint,
                               final boolean flushCache ) {

    Control[] children = composite.getChildren();
    
    int width = 0;
    int height = 0;
    
    if ( children != null ) {
      
      for ( Control child : children ) {
        Point size = computeChildSize( child, wHint, hHint, flushCache );
        if ( type == SWT.VERTICAL ) {
          width = Math.max( size.x, width );
          height += size.y;
        } else {
          width += size.x;
          height = Math.max( size.y, height );
        }
      }
      
      if ( children.length > 1 ) {
        if ( this.type == SWT.VERTICAL ) {
          height += ( children.length - 1 ) * this.spacing;
        } else {
          width += ( children.length -1 ) * this.spacing;
        }
      }
        
    }
    
    width += 2 * this.marginWidth;
    height += 2 * this.marginHeight;
    
    return new Point( width, height );
    
  }
  
  @Override
  protected boolean flushCache ( final Control control ) {
    Object data = control.getLayoutData();
    if ( data != null ) ( ( PackData ) data ).flushCache();
    return true;
  }

  @Override
  protected void layout( final Composite composite,
                         final boolean flushCache ) {
    
    Rectangle area = composite.getClientArea();
    int x = area.x + marginWidth;
    int y = area.y + marginHeight;
    int w = area.width - 2 * marginWidth;
    int h = area.height - 2 * marginHeight;
    
    Control[] children = composite.getChildren();
    
    if ( children != null ) {
      
      for ( Control child : children ) {
      
        Point size = computeChildSize( child, SWT.DEFAULT, SWT.DEFAULT, flushCache );
        
        if ( this.type == SWT.VERTICAL ) {
          h = size.y;
        } else {
          w = size.x;
        }
        
        child.setBounds( x, y, w, h );
        
        if ( this.type == SWT.VERTICAL ) {
          y += this.spacing + h;
        } else {
          x += this.spacing + w;
        }
        
      }
      
    }

  }
  
  private Point computeChildSize( final Control control,
                                  final int wHint,
                                  final int hHint,
                                  final boolean flushCache ) {
    
    PackData data = ( PackData ) control.getLayoutData();
    
    if (data == null) {
      data = new PackData();
      control.setLayoutData( data );
    }
    
    Point size = null;
    
    if ( wHint == SWT.DEFAULT && hHint == SWT.DEFAULT ) {
      size = data.computeSize ( control, wHint, hHint, flushCache );
    } else {
      int trimX, trimY;
      if ( control instanceof Scrollable ) {
        Rectangle rect = ( ( Scrollable ) control ).computeTrim ( 0, 0, 0, 0 );
        trimX = rect.width;
        trimY = rect.height;
      } else {
        trimX = control.getBorderWidth () * 2;
        trimY = trimX; 
      }
      int w = wHint == SWT.DEFAULT ? wHint : Math.max ( 0, wHint - trimX );
      int h = hHint == SWT.DEFAULT ? hHint : Math.max ( 0, hHint - trimY );
      size = data.computeSize ( control, w, h, flushCache );
    }
    
    return size;
    
  }

}
