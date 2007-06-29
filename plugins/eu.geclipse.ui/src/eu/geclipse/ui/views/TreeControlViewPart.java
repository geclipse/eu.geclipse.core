package eu.geclipse.ui.views;

import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;

public abstract class TreeControlViewPart extends GridModelViewPart {
  
  @Override
  public void refreshViewer( final IGridElement element ) {
    if ( ( element != null ) && ( element instanceof IGridContainer ) ) {
      IGridContainer container = ( IGridContainer ) element;
      if ( container.isLazy() && container.isDirty() ) {
        Control control = this.viewer.getControl();
        if ( ! control.isDisposed() ) {
          Display display = control.getDisplay();
          display.syncExec( new Runnable() {
            public void run() {
              TreeViewer tViewer = ( TreeViewer ) getViewer();
              tViewer.setChildCount( element, 0 );
              tViewer.setChildCount( element, 1 );
            }
          } );
        }
      }
    }
    super.refreshViewer( element );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.ui.views.GridModelViewPart#createViewer(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected StructuredViewer createViewer( final Composite parent ) {
    TreeViewer tViewer = new TreeViewer( parent, SWT.VIRTUAL | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL );
    Tree tree = tViewer.getTree();
    boolean hasColumns = createTreeColumns( tree );
    if ( hasColumns ) {
      tree.setHeaderVisible( true );
    }
    return tViewer;
  }
  
  protected boolean createTreeColumns( @SuppressWarnings("unused")
                                       final Tree tree ) {
    return false;
  }
  
}
