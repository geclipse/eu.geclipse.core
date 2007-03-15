package eu.geclipse.ui.views;

import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;

public abstract class TreeControlViewPart extends GridModelViewPart {

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
