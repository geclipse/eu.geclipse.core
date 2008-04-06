/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium 
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
 *     Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.providers;

import java.net.URL;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import eu.geclipse.ui.internal.Activator;

public class NewProgressTreeNode
    implements IProgressMonitor, Listener {
  
  
  /**
   * Internal class that handles UI updates.
   */
  private static class ProgressNodeUpdater implements Runnable {
    
    /**
     * The progress tree node used to show the progress.
     */
    private NewProgressTreeNode node;
    
    /**
     * The last progress string.
     */
    private String lastProgress;
    
    private boolean wasAlreadyDone;
    
    /**
     * Construct a new updater for the specified {@link ProgressTreeNode}.
     * 
     * @param node The progress tree node showing the progress.
     */
    public ProgressNodeUpdater( final NewProgressTreeNode node ) {
      this.node = node;
      this.wasAlreadyDone = false;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run() {
      String progress = this.node.toString();
      if ( ( progress != null ) && ! progress.equals( this.lastProgress ) ) {
        this.lastProgress = progress;
        this.node.getViewer().update( this.node, null );
      } else if ( this.node.isDone() && ! this.wasAlreadyDone ) {
        this.wasAlreadyDone = true;
        this.node.getViewer().getTree().redraw();
      }
    }
    
    /**
     * Reset the progress.
     */
    public void reset() {
      this.lastProgress = null;
    }
    
  }
  
  
  private static Image emptyProgress;
  
  private static Image fullProgress;
  
  static {
    URL emptyURL = Activator.getDefault().getBundle()
                    .getEntry( "icons/extras/progress_bar_empty.gif" ); //$NON-NLS-1$
    emptyProgress = ImageDescriptor.createFromURL( emptyURL ).createImage();
    URL fullURL = Activator.getDefault().getBundle()
                    .getEntry( "icons/extras/progress_bar_full.gif" ); //$NON-NLS-1$
    fullProgress = ImageDescriptor.createFromURL( fullURL ).createImage();
  }
  
  protected boolean done;
  
  private int tWork;
  
  private double worked;
  
  private String taskName;
  
  private boolean canceled;
  
  private TreeViewer viewer;
  
  private ProgressNodeUpdater updater;
  
  public NewProgressTreeNode( final TreeViewer viewer ) {
    
    this.tWork = 100;
    this.worked = 0;
    this.done = false;
    this.canceled = false;
    this.taskName = "Pending...";
    this.updater = new ProgressNodeUpdater( this );
    this.viewer = viewer;
    
    final Tree tree = viewer.getTree();
    if ( ! tree.isDisposed() ) {
      Display display = tree.getDisplay();
      display.syncExec( new Runnable() {
        public void run() {
          if ( !tree.isDisposed() ) {
            tree.addListener( SWT.MeasureItem, NewProgressTreeNode.this );
            tree.addListener( SWT.EraseItem, NewProgressTreeNode.this );
            tree.addListener( SWT.PaintItem, NewProgressTreeNode.this );
          }
        }
      } );
    }
    
  }
  
  public void beginTask( final String name, final int totalWork ) {
    this.taskName = name;
    this.tWork = totalWork;
    this.worked = 0;
    this.done = false;
    this.canceled = false;
    update();
  }

  public void done() {
    this.worked = this.tWork;
    this.done = true;
    update();
  }
  
  public TreeViewer getViewer() {
    return this.viewer;
  }
  
  public void handleEvent( final Event event ) {
    if ( ( event.item instanceof TreeItem ) && ( event.index == 0 ) ) {
      Object data = ( ( TreeItem ) event.item ).getData();
      if ( data == this ) {
        switch ( event.type ) {
          case SWT.MeasureItem:
            measureItem( event );
            break;
          case SWT.EraseItem:
            eraseItem( event );
            break;
          case SWT.PaintItem:
            paintItem( event );
            break;
        }
      }
    }
  }

  public void internalWorked( final double work ) {
    this.worked += work;
    update();
  }

  public boolean isCanceled() {
    return this.canceled;
  }
  
  /**
   * Determine if the job is done.
   * 
   * @return True if the job is done.
   */
  public boolean isDone() {
    return this.done;
  }

  public void setCanceled( final boolean value ) {
    this.canceled = value;
    update();
  }

  public void setTaskName( final String name ) {
    this.taskName = name;
    update();
  }
  
  @Override
  public String toString() {
    String resultString = this.taskName
                        + " (" //$NON-NLS-1$
                        + String.valueOf( getProgressPercent() )
                        + ")"; //$NON-NLS-1$
    return resultString;
  }

  public void subTask( final String name ) {
    setTaskName( name );
  }

  public void worked( final int work ) {
    internalWorked( work );
  }
  
  /**
   * Get the current progress in percent.
   * 
   * @return The current progress.
   */
  private int getProgressPercent() {
    return ( int ) Math.round( 100.*this.worked/this.tWork );
  }
  
  /**
   * Convenience method for the SWT.EraseItem event.
   * 
   * @param event The event triggering this erasure.
   */
  private void eraseItem( @SuppressWarnings("unused")
                            final Event event ) {
    // empty implementation
  }
  
  /**
   * Convenience method for the SWT.MeasureItem event.
   * 
   * @param event The event triggering this measurement.
   */
  private void measureItem( final Event event ) {
    
    int textHeight = event.gc.getFontMetrics().getHeight();
    int imageWidth = emptyProgress.getBounds().width;
    int imageHeight = emptyProgress.getBounds().height;
    int textWidth
      = this.taskName == null
      ? 0
      : event.gc.textExtent( this.taskName ).x;
    
    event.height = Math.max( textHeight, imageHeight );
    event.width = textWidth + imageWidth + 2;
    
  }
  
  /**
   * Convenience method for the SWT.PaintItem event.
   * 
   * @param event The event triggering this paint.
   */
  protected void paintItem( final Event event ) {
    
    Display display = this.viewer.getTree().getDisplay();
    Color black = display.getSystemColor( SWT.COLOR_BLACK );
    event.gc.fillRectangle( event.x, event.y, event.width, event.height );
    
    Color white = display.getSystemColor( SWT.COLOR_WHITE );

    int progress = getProgressPercent();

    int barwidth = fullProgress.getBounds().width;
    int barheight = fullProgress.getBounds().height;
    int bary = event.y + ( event.height - barheight ) / 2;
    int barp = barwidth * progress / 100;

    event.gc.drawImage( emptyProgress, barp, 0, barwidth-barp, barheight, event.x+barp+2, bary, barwidth-barp, barheight );
    event.gc.drawImage( fullProgress, 0, 0, barp, barheight, event.x+2, bary, barp, barheight );

    String progressString = String.valueOf( progress )+"%"; //$NON-NLS-1$ //$NON-NLS-2$
    Point textExtend = event.gc.textExtent( progressString );
    int progressX = event.x + 2 + ( barwidth - textExtend.x ) / 2;
    event.gc.setForeground( white );
    event.gc.drawText( progressString, progressX+1, event.y + 2, true );
    event.gc.setForeground( black );
    event.gc.drawText( progressString, progressX, event.y + 1, true );

    int textX = event.x + 6 + barwidth;
    if ( this.taskName != null ) {
      event.gc.setForeground( black );
      event.gc.drawText( this.taskName, textX, event.y + 1, true );
    }

  }
  
  /**
   * Update the tree in order to repaint this node.
   */
  protected void update() {
    Tree tree = getViewer().getTree();
    if ( !tree.isDisposed() ) {
      Display display = tree.getDisplay();
      display.asyncExec( this.updater );
    }
  }

}
