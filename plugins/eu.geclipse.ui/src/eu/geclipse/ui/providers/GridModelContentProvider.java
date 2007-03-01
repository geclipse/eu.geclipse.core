package eu.geclipse.ui.providers;

import java.util.Arrays;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ITreeViewerListener;
import org.eclipse.jface.viewers.TreeExpansionEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;

public class GridModelContentProvider
    implements ITreeContentProvider, ITreeViewerListener {
  
  protected TreeViewer treeViewer;
  
  private GridModelComparator comparator = new GridModelComparator();

  public Object[] getChildren( final Object parentElement ) {
    Object[] children = null;
    if ( hasChildren( parentElement ) ) {
      children = getChildren( ( IGridContainer ) parentElement );
    }
    if ( children != null ) {
      Arrays.sort( children, this.comparator );
    }
    return children;
  }

  public Object getParent( final Object element ) {
    Object parent = null;
    if ( element instanceof IGridElement ) {
      parent = ( ( IGridElement ) element ).getParent();
    }
    return parent;
  }

  public boolean hasChildren( final Object element ) {
    boolean result = false;
    if ( element instanceof IGridContainer ) {
      result = ( ( IGridContainer ) element ).hasChildren();
    }
    return result;
  }

  public Object[] getElements( final Object inputElement ) {
    Object[] elements = getChildren( inputElement );
    if ( elements == null ) {
      elements = new Object[0];
    }
    return elements;
  }

  public void dispose() {
    // empty implementation
  }

  public void inputChanged( final Viewer viewer,
                            final Object oldInput,
                            final Object newInput ) {
    if ( viewer instanceof TreeViewer ) {
      treeViewerChanged( this.treeViewer, ( TreeViewer ) viewer );
    } 
  }
  
  protected Object[] getChildren( final IGridContainer container ) {
    Object[] children = null;
    if ( container.isLazy() && container.isDirty() ) {
      ProgressRunner runner = new ProgressRunner( this.treeViewer, container );
      ProgressTreeNode monitor = runner.getMonitor();
      Thread thread = new Thread( runner );
      thread.start();
      children = new Object[] { monitor };
    } else {
      children = container.getChildren( null );
    }
    return children;
  }
  
  private void treeViewerChanged( final TreeViewer oldViewer,
                                  final TreeViewer newViewer ) {
    if ( oldViewer != newViewer ) {
      this.treeViewer = newViewer;
      if ( oldViewer != null ) {
        oldViewer.removeTreeListener( this );
      }
      if ( newViewer != null ) {
        newViewer.addTreeListener( this );
      }
    }
  }
  
  /*private Object[] fetchChildren( final IGridMount mount ) {
    ProgressRunner runner = new ProgressRunner( this.treeViewer, mount );
    ProgressTreeNode monitor = runner.getMonitor();
    Thread thread = new Thread( runner );
    thread.start();
    return new Object[] { monitor };
  }*/

  public void treeCollapsed( final TreeExpansionEvent event ) {
    Object element = event.getElement();
    /*if ( element instanceof IGridMount ) {
      ( ( IGridMount ) element ).setDirty( true );
      this.treeViewer.setChildCount( element, 0 );
      this.treeViewer.setChildCount( element, 1 );
    }*/
    if ( element instanceof IGridContainer ) {
      ( ( IGridContainer ) element ).setDirty();
      this.treeViewer.setChildCount( element, 0 );
      this.treeViewer.setChildCount( element, 1 );
    }
  }

  public void treeExpanded( final TreeExpansionEvent event ) {
    // empty implementation
  }
  
}
