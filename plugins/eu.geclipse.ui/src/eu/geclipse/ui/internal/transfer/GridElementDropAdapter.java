package eu.geclipse.ui.internal.transfer;

import java.lang.reflect.InvocationTargetException;
import org.eclipse.jface.util.TransferDropTargetListener;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.progress.IProgressService;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.ui.dialogs.NewProblemDialog;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.views.GridModelViewPart;

public class GridElementDropAdapter
    implements TransferDropTargetListener {

  private GridModelViewPart view;
  
  public GridElementDropAdapter( final GridModelViewPart view ) {
    this.view = view;
  }
  
  public void dragEnter( final DropTargetEvent event ) {
    handleDrag( event );
  }

  public void dragLeave( final DropTargetEvent event ) {
    // TODO Auto-generated method stub
  }

  public void dragOperationChanged( final DropTargetEvent event ) {
    // TODO Auto-generated method stub
  }

  public void dragOver( final DropTargetEvent event ) {
    handleDrag( event );
  }

  public void drop( final DropTargetEvent event ) {
    Widget item = event.item;
    if ( item != null ) {
      Object data = item.getData();
      if ( data instanceof IGridContainer ) {
        IGridElement[] elements
          = ( IGridElement[] ) event.data;
        boolean deleteOriginals = ( event.detail & DND.DROP_MOVE ) != 0;
        copyElements( ( IGridContainer ) data, elements, deleteOriginals );
      }
    }
  }

  public void dropAccept( final DropTargetEvent event ) {
    handleDrag( event );
  }
  
  public Transfer getTransfer() {
    return GridElementTransfer.getInstance();
  }

  public boolean isEnabled( final DropTargetEvent event ) {
    return true;
  }
  
  protected void copyElements( final IGridContainer target,
                               final IGridElement[] elements,
                               final boolean move ) {
    IProgressService progressService
      = PlatformUI.getWorkbench().getProgressService();
    WorkspaceModifyOperation op
      = new GridElementCopyOperation( target, elements, move );
    try {
      progressService.run( true, true, op );
    } catch( InvocationTargetException itExc ) {
      Throwable exc = itExc.getCause();
      if ( exc == null ) {
        exc = itExc;
      }
      NewProblemDialog.openProblem( this.view.getSite().getShell(),
                                    "Transfer problem",
                                    "A problem occured during a drag'n'drop operation",
                                    exc );
    } catch( InterruptedException intExc ) {
      Activator.logException( intExc );
    }
  }
  
  protected void handleDrag( final DropTargetEvent event ) {
    
    Widget item = event.item;
    event.detail = DND.DROP_NONE;
    
    if ( item != null ) {
      Object data = item.getData();
      if ( data instanceof IGridContainer ) {
        IGridContainer container
          = ( IGridContainer ) data;
        GridElementTransfer geTransfer
          = GridElementTransfer.getInstance();
        IGridElement[] elements
          = ( IGridElement[] ) geTransfer.nativeToJava( event.currentDataType );
        event.detail = this.view.acceptDrop( container, elements );
      }
    }
    
  }
  
}
