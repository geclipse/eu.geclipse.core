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

package eu.geclipse.ui.internal.transfer; 

import java.util.Iterator;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.util.TransferDragSourceListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.ui.views.navigator.LocalSelectionTransfer;

import eu.geclipse.core.model.IGridElement;
import eu.geclipse.ui.views.GridModelViewPart;

/**
 * Transfer drag adapter for selections specialised for the transfer of
 * Grid elements.
 */
public class SelectionTransferDragAdapter
    implements TransferDragSourceListener {
  
  private GridModelViewPart view;
  
  /**
   * Construct a new transfer drag adapter for the specified model view.
   * 
   * @param view The {@link GridModelViewPart} for which to create
   * this transfer drag adapter.
   */
  public SelectionTransferDragAdapter( final GridModelViewPart view ) {
    Assert.isNotNull( view );
    this.view = view;
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.util.TransferDragSourceListener#getTransfer()
   */
  public Transfer getTransfer() {
    return LocalSelectionTransfer.getInstance();
  }

  /* (non-Javadoc)
   * @see org.eclipse.swt.dnd.DragSourceListener#dragFinished(org.eclipse.swt.dnd.DragSourceEvent)
   */
  public void dragFinished( final DragSourceEvent event ) {
    // TODO mathias - implement move functionality
    LocalSelectionTransfer.getInstance().setSelection(null);
    LocalSelectionTransfer.getInstance().setSelectionSetTime(0);
  }

  /* (non-Javadoc)
   * @see org.eclipse.swt.dnd.DragSourceListener#dragSetData(org.eclipse.swt.dnd.DragSourceEvent)
   */
  public void dragSetData( final DragSourceEvent event ) {
    event.data = LocalSelectionTransfer.getInstance().getSelection();
  }

  /* (non-Javadoc)
   * @see org.eclipse.swt.dnd.DragSourceListener#dragStart(org.eclipse.swt.dnd.DragSourceEvent)
   */
  public void dragStart( final DragSourceEvent event ) {
    ISelection selection = this.view.getViewer().getSelection();
    event.doit = isDragable( selection );
    if ( event.doit ) {
      LocalSelectionTransfer.getInstance().setSelection( selection );
      LocalSelectionTransfer.getInstance().setSelectionSetTime( event.time & 0xFFFFFFFFL );
    }
  }
  
  /**
   * Determines if the specified selection contains only dragable elements.
   * 
   * @param selection The selection to be tested.
   * @return True if {@link GridModelViewPart#isDragSource(IGridElement)} returns
   * true for all elements contained in the specified selection.
   */
  protected boolean isDragable( final ISelection selection ) {
    boolean result = true;
    if ( !selection.isEmpty() && ( selection instanceof IStructuredSelection ) ) {
      IStructuredSelection sSelection = ( IStructuredSelection ) selection;
      Iterator< ? > iter = sSelection.iterator();
      while ( iter.hasNext() && result ) {
        Object obj = iter.next();
        if ( !( obj instanceof IGridElement )
            || !this.view.isDragSource( ( IGridElement ) obj ) ) {
          result = false;
        }
      }
    }
    return result;
  }
  
}
