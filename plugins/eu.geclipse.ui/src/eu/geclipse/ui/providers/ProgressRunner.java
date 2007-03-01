package eu.geclipse.ui.providers;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import eu.geclipse.core.model.IGridContainer;


public class ProgressRunner implements Runnable {
  
  protected ProgressTreeNode monitor;
  
  protected IGridContainer container;
  
  public ProgressRunner( final TreeViewer treeViewer,
                         final IGridContainer container ) {
    this.container = container;
    final Tree tree = treeViewer.getTree();
    this.monitor = new ProgressTreeNode( treeViewer );
    if ( !tree.isDisposed() ) {
      Display display = tree.getDisplay();
      display.syncExec( new Runnable() {
        public void run() {
          if ( !tree.isDisposed() ) {
            tree.addListener( SWT.MeasureItem, ProgressRunner.this.monitor );
            tree.addListener( SWT.EraseItem, ProgressRunner.this.monitor );
            tree.addListener( SWT.PaintItem, ProgressRunner.this.monitor );
          }
        }
      } );
    }
  }
  
  public ProgressTreeNode getMonitor() {
    return this.monitor;
  }

  public void run() {
    synchronized ( this.container ) {
      this.container.getChildren( this.monitor );
      if ( !this.container.isDirty() ) {
        final TreeViewer viewer = this.monitor.getTreeViewer();
        Control control = viewer.getControl();
        if ( !control.isDisposed() ) {
          Display display = control.getDisplay();
          display.asyncExec( new Runnable() {
            public void run() {
              viewer.refresh( ProgressRunner.this.container );
            }
          } );
        }
      } else {
        this.monitor.setError( "Unable to fetch children" );
        this.monitor.done();
      }
    }
  }
  
}
