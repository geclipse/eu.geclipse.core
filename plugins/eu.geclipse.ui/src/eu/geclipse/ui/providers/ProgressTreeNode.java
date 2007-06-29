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
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.providers;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * A progress monitor implementation to be used within {@link TreeViewer}s.
 */
public class ProgressTreeNode
    implements IProgressMonitor, Listener {
  
  /**
   * Internal class that handles UI updates.
   */
  private class ProgressNodeUpdater implements Runnable {
    
    /**
     * The progress tree node used to show the progress.
     */
    private ProgressTreeNode node;
    
    /**
     * The last progress string.
     */
    private String lastProgress;
    
    /**
     * Construct a new updater for the specified {@link ProgressTreeNode}.
     * 
     * @param node The progress tree node showing the progress.
     */
    public ProgressNodeUpdater( final ProgressTreeNode node ) {
      this.node = node;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run() {
      String progress = this.node.toString();
      if ( ( progress != null ) && !progress.equals( this.lastProgress ) ) {
        this.lastProgress = progress;
        this.node.getTreeViewer().update( ProgressTreeNode.this, null );
      }
    }
    
    /**
     * Reset the progress.
     */
    public void reset() {
      this.lastProgress = null;
    }
    
  }
  
  /**
   * The default width of the progress bar. 
   */
  private static final int DEFAULT_PROGRESS_BAR_WIDTH = 50;
  
  /**
   * The default margin between the percentage and the progress bar ends.
   */
  private static final int DEFAULT_PROGRESS_BAR_MARGIN = 5;
  
  /**
   * The default gap between the progress bar and the task text.
   */
  private static final int DEFAULT_TEXT_GAP = 5;
  
  /**
   * The amount of work already done.
   */
  protected double worked;
  
  /**
   * The name of the current task.
   */
  protected String taskName;
  
  /**
   * The total number of work steps. 
   */
  private int tWork;
  
  private String errorString;
  
  /**
   * The canceled flag.
   */
  private boolean canceled;
  
  private boolean done;
  
  private TreeViewer treeViewer;
  
  private ProgressNodeUpdater updater;
  
  protected ProgressTreeNode( final TreeViewer treeViewer ) {
    this.treeViewer = treeViewer;
    this.updater = new ProgressNodeUpdater( this );
  }
  
  /**
   * Get the {@link TreeViewer} associated with this {@link ProgressTreeNode}.
   * 
   * @return The associated {@link TreeViewer}.
   */
  public TreeViewer getTreeViewer() {
    return this.treeViewer;
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.runtime.IProgressMonitor#beginTask(java.lang.String, int)
   */
  public void beginTask( final String name,
                         final int totalWork ) {
    synchronized ( this ) {
      this.taskName = name;
      this.tWork = totalWork;
      this.worked = 0;
      this.canceled = false;
      this.done = false;
    }
    update();
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.runtime.IProgressMonitor#done()
   */
  public void done() {
    this.worked = this.tWork;
    this.done = true;
    update();
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.runtime.IProgressMonitor#internalWorked(double)
   */
  public void internalWorked( final double work ) {
    this.worked += work;
    update();
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.runtime.IProgressMonitor#isCanceled()
   */
  public boolean isCanceled() {
    return this.canceled;
  }
  
  public boolean isDone() {
    return this.done;
  }

  public void setCanceled( final boolean value ) {
    this.canceled = value;
    update();
  }
  
  public void setError( final String error ) {
    this.errorString = error;
  }

  public void setTaskName( final String name ) {
    synchronized ( this.taskName ) {
      this.taskName = name;
    }
    update();
  }

  public void subTask( final String name ) {
    setTaskName( name );
  }

  public void worked( final int work ) {
    internalWorked( work );
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
  
  @Override
  public String toString() {
    String resultString = this.taskName
                        + " (" //$NON-NLS-1$
                        + String.valueOf( getProgressPercent() )
                        + ")"; //$NON-NLS-1$
    return resultString;
  }
  
  /**
   * Get the current progress in percent.
   * 
   * @return The current progress.
   */
  protected long getProgressPercent() {
    return Math.round( 100.*this.worked/this.tWork );
  }
  
  /**
   * Convenience method for the SWT.MeasureItem event.
   * 
   * @param event The event triggering this measurement.
   */
  protected void measureItem( final Event event ) {
    event.height = this.treeViewer.getTree().getItemHeight();//event.gc.getFontMetrics().getHeight();
    event.width = getProgressBarWidth( event.gc ) + 3;
    if ( this.taskName != null ) {
      event.width += DEFAULT_TEXT_GAP
                  + event.gc.textExtent( this.taskName ).x;
    }
  }
  
  /**
   * Convenience method for the SWT.EraseItem event.
   * 
   * @param event The event triggering this erasure.
   */
  protected void eraseItem( @SuppressWarnings("unused")
                            final Event event ) {
    // empty implementation
  }
  
  /**
   * Convenience method for the SWT.PaintItem event.
   * 
   * @param event The event triggering this paint.
   */
  protected void paintItem( final Event event ) {
    
    String tName;
    synchronized ( this ) {
      tName = this.taskName;
    }
        
    Display display = this.treeViewer.getTree().getDisplay();
    Color black = display.getSystemColor( SWT.COLOR_BLACK );
    Color white = display.getSystemColor( SWT.COLOR_WHITE );
    Color red = display.getSystemColor( SWT.COLOR_RED );
    Color yellow = display.getSystemColor( SWT.COLOR_YELLOW );
    Color blue = display.getSystemColor( SWT.COLOR_BLUE );
    Color bg = event.gc.getBackground();
    
    long progress = getProgressPercent();
    int progBarWidth = getProgressBarWidth( event.gc );
    event.gc.fillRectangle( event.x, event.y, event.width, event.height );
    
    event.gc.setForeground( black );
    event.gc.setBackground( isDone() ?  blue : bg );
    event.gc.drawRectangle( event.x + 1, event.y + 1, progBarWidth + 1, event.height - 3 );
    if ( isDone() ) {
      event.gc.fillRectangle( event.x + 1, event.y + 1, progBarWidth + 1, event.height - 3 );
    }
    event.gc.drawRectangle( event.x + 1, event.y + 1, progBarWidth + 1, event.height - 3 );
    if ( !isDone() ) {
      event.gc.setClipping( event.x + 2, event.y + 2,
                            ( int )( progBarWidth * progress / 100. ),
                            event.height-4 );
      event.gc.setForeground( red );
      event.gc.setBackground( yellow );
      event.gc.fillGradientRectangle( event.x + 2, event.y + 2, progBarWidth, event.height - 4, false );
      event.gc.setClipping( ( Rectangle ) null );
    }
    
    String progressString = isDone() ? "done" : String.valueOf( progress )+"%"; //$NON-NLS-1$ //$NON-NLS-2$
    Point progressExtend = event.gc.textExtent( progressString );
    int progressX = event.x + 2 + ( progBarWidth - progressExtend.x ) / 2;
    event.gc.setForeground( white );
    event.gc.drawText( progressString, progressX+1, event.y + 2, true );
    event.gc.setForeground( black );
    event.gc.drawText( progressString, progressX, event.y + 1, true );

    int textX = event.x + 2 + progBarWidth + DEFAULT_TEXT_GAP;
    if ( this.errorString != null ) {
      event.gc.setForeground( red );
      event.gc.drawText( this.errorString, textX, event.y + 1, true );
    } else if ( tName != null ) {
      event.gc.setForeground( black );
      event.gc.drawText( tName, textX, event.y + 1, true );
    }
    
  }
  
  /**
   * Compute the optimal with of the progress bar.
   * 
   * @param gc The graphical context used to render the progress bar.
   * @return The width of the progress bar as it is rendered.
   */
  private int getProgressBarWidth( final GC gc ) {
    Point maxProgExtend = gc.textExtent( "100%" ); //$NON-NLS-1$
    int progBarWidth = DEFAULT_PROGRESS_BAR_WIDTH;
    int maxProgBarWidth = maxProgExtend.x + 2 * DEFAULT_PROGRESS_BAR_MARGIN;
    if ( progBarWidth < maxProgBarWidth ) {
      progBarWidth = maxProgBarWidth;
    }
    return progBarWidth;
  }
  
  /**
   * Update the tree in order to repaint this node.
   */
  private void update() {
    Tree tree = this.treeViewer.getTree();
    if ( !tree.isDisposed() ) {
      Display display = tree.getDisplay();
      display.asyncExec( this.updater );
    }
  }
  
}
