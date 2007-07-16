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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ITreeViewerListener;
import org.eclipse.jface.viewers.TreeExpansionEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Shell;

import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.ui.dialogs.NewProblemDialog;
import eu.geclipse.ui.internal.Activator;

/**
 * Tree content provider for data contained in the grid model.
 */
public class GridModelContentProvider
    implements ITreeContentProvider, ITreeViewerListener {
  
  /**
   * The associated tree viewer.
   */
  protected TreeViewer treeViewer;
  
  /**
   * The comparator used for sorting the children of a node.
   */
  private GridModelComparator comparator
    = new GridModelComparator();

  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
   */
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

  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
   */
  public Object getParent( final Object element ) {
    Object parent = null;
    if ( element instanceof IGridElement ) {
      parent = ( ( IGridElement ) element ).getParent();
    }
    return parent;
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
   */
  public boolean hasChildren( final Object element ) {
    boolean result = false;
    if ( element instanceof IGridContainer ) {
      result = ( ( IGridContainer ) element ).hasChildren();
    }
    return result;
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
   */
  public Object[] getElements( final Object inputElement ) {
    Object[] elements = getChildren( inputElement );
    if ( elements == null ) {
      elements = new Object[0];
    }
    return elements;
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.IContentProvider#dispose()
   */
  public void dispose() {
    // empty implementation
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
   */
  public void inputChanged( final Viewer viewer,
                            final Object oldInput,
                            final Object newInput ) {
    if ( viewer instanceof TreeViewer ) {
      treeViewerChanged( this.treeViewer, ( TreeViewer ) viewer );
    } 
  }
  
  /**
   * Get the children of the specified container. If the container
   * is lazy and dirty the returned array will contain only one
   * element, i.e. a {@link ProgressRunner} that is used to monitor
   * the progress of the children fetching operation that will
   * be started immediately by this method.
   * 
   * @param container The container from which to fetch the children.
   * @return An array containing either the list of childen or a
   * {@link ProgressRunner} object.
   */
  protected Object[] getChildren( final IGridContainer container ) {
    Object[] children = null;
    if ( container.isLazy() && container.isDirty() ) {
      ProgressRunner runner = new ProgressRunner( this.treeViewer, container );
      ProgressTreeNode monitor = runner.getMonitor();
      Thread thread = new Thread( runner );
      thread.start();
      children = new Object[] { monitor };
    } else {
      try {
        IGridElement[] childElements = container.getChildren( null );
        List< IGridElement > visibleChildren = new ArrayList< IGridElement >();
        for ( IGridElement child : childElements ) {
          if ( ! child.isHidden() ) {
            visibleChildren.add( child );
          }
        }
        children = visibleChildren.toArray( new IGridElement[ visibleChildren.size() ] );
      } catch( GridModelException gmExc ) {
        if ( this.treeViewer != null ) {
          Shell shell = this.treeViewer.getControl().getShell();
          NewProblemDialog.openProblem( shell,
                                        Messages.getString("GridModelContentProvider.problem_title"), //$NON-NLS-1$
                                        Messages.getString("GridModelContentProvider.problem_text") + container.getName(), //$NON-NLS-1$
                                        gmExc );
        } else {
          Activator.logException( gmExc );
        }
      }
    }
    return children;
  }
  
  /**
   * Called when a new tree viewer was set and/or an old viewer
   * was unset.
   * 
   * @param oldViewer The old viewer.
   * @param newViewer The new viewer.
   */
  protected void treeViewerChanged( final TreeViewer oldViewer,
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

  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.ITreeViewerListener#treeCollapsed(org.eclipse.jface.viewers.TreeExpansionEvent)
   */
  public void treeCollapsed( final TreeExpansionEvent event ) {
    Object element = event.getElement();
    if ( element instanceof IGridContainer ) {
      ( ( IGridContainer ) element ).setDirty();
      this.treeViewer.setChildCount( element, 0 );
      this.treeViewer.setChildCount( element, 1 );
    }
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.ITreeViewerListener#treeExpanded(org.eclipse.jface.viewers.TreeExpansionEvent)
   */
  public void treeExpanded( final TreeExpansionEvent event ) {
    // empty implementation
  }
  
}
