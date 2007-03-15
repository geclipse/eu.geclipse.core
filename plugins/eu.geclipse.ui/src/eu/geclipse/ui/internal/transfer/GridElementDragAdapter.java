package eu.geclipse.ui.internal.transfer;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jface.util.TransferDragSourceListener;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.Transfer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.ui.views.GridModelViewPart;

public class GridElementDragAdapter implements TransferDragSourceListener {
  
  private GridModelViewPart view;
  
  private List< IGridElement > selectedElements
    = new ArrayList< IGridElement >();
  
  public GridElementDragAdapter( final GridModelViewPart view ) {
    this.view = view;
  }
  
  public void dragFinished( final DragSourceEvent event ) {
    // TODO mathias
  }

  public void dragSetData( final DragSourceEvent event ) {
    if ( getTransfer().isSupportedType( event.dataType ) ) {
      event.data
        = this.selectedElements.toArray( new IGridElement[ this.selectedElements.size() ] );
    }
  }

  public void dragStart( final DragSourceEvent event ) {
    
    StructuredViewer viewer = this.view.getViewer();
    StructuredSelection selection = ( StructuredSelection ) viewer.getSelection();
    List< ? > selectionList = selection.toList();
    this.selectedElements.clear();
    
    event.doit = true;
    for ( Object obj : selectionList ) {
      if ( !this.view.isDragSource( ( IGridElement ) obj ) ) {
        event.doit = false;
        this.selectedElements.clear();
        break;
      }
      this.selectedElements.add( ( IGridElement  ) obj );
    }
    
  }
  
  public Transfer getTransfer() {
    return GridElementTransfer.getInstance();
  }

}
