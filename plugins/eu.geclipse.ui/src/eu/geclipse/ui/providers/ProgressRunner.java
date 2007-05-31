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

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.ui.dialogs.NewProblemDialog;

/**
 * Runnable implementation that loads the children of a lazy
 * {@link IGridContainer} in background and offers the possibility to
 * display a {@link ProgressTreeNode} in {@link TreeViewer}s.
 */
public class ProgressRunner implements Runnable {
  
  /**
   * The progress tree node used to monitor the progress.
   */
  protected ProgressTreeNode monitor;
  
  /**
   * The container from which to load the children.
   */
  protected IGridContainer container;
  
  /**
   * Construct a new <code>ProgressRunner</code> for the specified
   * {@link TreeViewer}.
   * 
   * @param treeViewer The {@link TreeViewer} where the progress of
   * this operation will be shown. The progress is displayed as a
   * child node of the provided {@link IGridContainer}.
   * @param container The {@link IGridContainer} whose children
   * should be loaded.
   */
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
  
  /**
   * Get the {@link ProgressTreeNode} used to show the progress
   * of this operation.
   * 
   * @return The associated {@link ProgressTreeNode}.
   */
  public ProgressTreeNode getMonitor() {
    return this.monitor;
  }

  /* (non-Javadoc)
   * @see java.lang.Runnable#run()
   */
  public void run() {
    synchronized ( this.container ) {
      
      try {
        this.container.getChildren( this.monitor );
      } catch( final GridModelException gmExc ) {
        final TreeViewer viewer = this.monitor.getTreeViewer();
        Control control = viewer.getControl();
        if ( !control.isDisposed() ) {
          Display display = control.getDisplay();
          display.syncExec( new Runnable() {
            public void run() {
              Shell shell = ProgressRunner.this.monitor.getTreeViewer().getControl().getShell();
              NewProblemDialog.openProblem( shell,
                                            Messages.getString("ProgressRunner.problem_title"), //$NON-NLS-1$
                                            Messages.getString("ProgressRunner.problem_text") //$NON-NLS-1$
                                            + ProgressRunner.this.container.getName(),
                                            gmExc );
            }
          } );
        }
      }
      
      if ( !this.container.isDirty() ) {
        if ( this.container.hasChildren() ) {
          final TreeViewer viewer = this.monitor.getTreeViewer();
          Control control = viewer.getControl();
          if ( !control.isDisposed() ) {
            Display display = control.getDisplay();
            display.asyncExec( new Runnable() {
              public void run() {
                viewer.refresh( ProgressRunner.this.container );
                viewer.expandToLevel( ProgressRunner.this.container, 1 );
              }
            } );
          }
        } else {
          this.monitor.setTaskName( Messages.getString("ProgressRunner.folder_empty") ); //$NON-NLS-1$
          this.monitor.done();
        }
      } else {
        this.monitor.setError( Messages.getString("ProgressRunner.fetch_error") ); //$NON-NLS-1$
        this.monitor.done();
      }
    }
  }
  
}
