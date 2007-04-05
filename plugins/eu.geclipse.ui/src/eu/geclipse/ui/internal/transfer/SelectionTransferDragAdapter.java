package eu.geclipse.ui.internal.transfer; 

import java.util.Iterator;
import org.eclipse.jface.util.Assert;
import org.eclipse.jface.util.TransferDragSourceListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.ui.views.navigator.LocalSelectionTransfer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.ui.views.GridModelViewPart;

public class SelectionTransferDragAdapter
    implements TransferDragSourceListener {
  
  private GridModelViewPart view;
  
  public SelectionTransferDragAdapter( final GridModelViewPart view ) {
    Assert.isNotNull( view );
    this.view = view;
  }

  public Transfer getTransfer() {
    return LocalSelectionTransfer.getInstance();
  }

  public void dragFinished( final DragSourceEvent event ) {
    // TODO mathias - implement move functionality
    LocalSelectionTransfer.getInstance().setSelection(null);
    LocalSelectionTransfer.getInstance().setSelectionSetTime(0);
  }

  public void dragSetData( final DragSourceEvent event ) {
    event.data = LocalSelectionTransfer.getInstance().getSelection();
  }

  public void dragStart( final DragSourceEvent event ) {
    ISelection selection = this.view.getViewer().getSelection();
    event.doit = isDragable( selection );
    if ( event.doit ) {
      LocalSelectionTransfer.getInstance().setSelection( selection );
      LocalSelectionTransfer.getInstance().setSelectionSetTime( event.time & 0xFFFFFFFFL );
    }
  }
  
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
