/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
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
 *    Szymon Mueller - initial API and implementation
 *****************************************************************************/
package eu.geclipse.ui.internal.transfer;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.core.filesystem.TransferManager;
import eu.geclipse.core.filesystem.TransferInformation;


/**
 * Class responsible for starting transfer resuming operation.
 *
 */
public class TransferResumer extends Job {

  /**
   * Basic constructor
   * 
   * @param name
   */
  public TransferResumer( final String name ) {
    super( name );
  }

  @Override
  protected IStatus run( final IProgressMonitor monitor ) {
    
    class Runner implements Runnable {
      int exitCode;
      private final Display display;
      private final List<TransferInformation> operations;
      private List<TransferInformation> operationsToResume;
      
      Runner( final Display display, final List<TransferInformation> operations ) {
        this.display = display;
        this.operations = operations;
      }

      public void run() {        
        Shell shell = null;
        String[] labels = { Messages.getString("GridElementTransferOperation.buttonYes"), Messages.getString("GridElementTransferOperation.buttonYesAll"), Messages.getString("GridElementTransferOperation.buttonNo"), Messages.getString("GridElementTransferOperation.buttonNoAll"), Messages.getString("GridElementTransferOperation.buttonCancel") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
        
        if( this.display != null ) {
          shell = this.display.getActiveShell();
        }        
           
        TransferResumeDialog dialog = new TransferResumeDialog( shell, this.operations );
        this.exitCode = dialog.open();
        this.operationsToResume = dialog.getOperationsToResume();
      }      
      
      public List<TransferInformation> getOperationsToResume() {
        return this.operationsToResume;
      }
    }
    
    IStatus status = Status.OK_STATUS;
    TransferManager trManager = TransferManager.getManager();
    List<TransferInformation> pendingTransfers = trManager.getPendingTransfers();
    
    //show dialog with list of pending transfers
    if( pendingTransfers.size() > 0 ) {
      Display display = PlatformUI.getWorkbench().getDisplay();
      Runner runner = new Runner( display, pendingTransfers );
      display.syncExec( runner );
      List<TransferInformation> operationsToResume = runner.getOperationsToResume();
      switch( runner.exitCode ) {
        case 0:
          //YES
          for( TransferInformation op: pendingTransfers ) {
            if( operationsToResume.contains( op ) ) {
              GridElementTransferOperation gridElementTransferOp = new GridElementTransferOperation( op );
              gridElementTransferOp.setUser( true );
              gridElementTransferOp.schedule();
            } else {
              trManager.unregisterTransfer( op.getId() );
            }
          }
          break;
        case 1:
          //CANCEL
          //Do not remove informations about the transfer from the repository
          break;
        case 3:
          //NO
          for( TransferInformation op: pendingTransfers ) {
            trManager.unregisterTransfer( op.getId() );
          }
          break;
        default:
          break;
      }
    }
    
    
    return status;
  }
}
