/*****************************************************************************
 * Copyright (c) 2006-2008 g-Eclipse Consortium 
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

import java.net.URI;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.core.filesystem.GEclipseURI;
import eu.geclipse.core.filesystem.TransferInformation;
import eu.geclipse.core.filesystem.TransferManager;
import eu.geclipse.core.model.IGridConnection;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.reporting.IProblem;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.dialogs.ProblemDialog;
import eu.geclipse.ui.internal.Activator;


/**
 * Job for transferring elements from local to local, local to remote,
 * remote to local and remote to remote. Elements may either by copied
 * or moved. A move operation is an ordinary copy operation followed
 * by a delete operation. Both files and directories are supported.  
 */
public class GridElementTransferOperation
    extends Job {
  
  private enum OverwriteMode {
    /**
     * 
     */
    ASK,
    /**
     * 
     */
    OVERWRITE_ALL,
    /**
     * 
     */
    IGNORE_ALL
  }
  
  /**
   * The target of the transfer operation.
   */
  private IGridContainer globalTarget;
  
  /**
   * The elements to be transfered.
   */
  private IGridElement[] elements;
  
  /**
   * TransferOperation
   */
  private TransferInformation transferOperation;
  
  /**
   * Determines if this is a copy or a move operation. 
   */
  private boolean move;
  
  private OverwriteMode overwriteMode = OverwriteMode.ASK;
  
  /**
   * Create a new transfer operation. This may either be a copy or a
   * move operation.
   * 
   * @param elements The elements that should be transfered.
   * @param target The destination of the transfer.
   * @param move If true this operation will move the elements to the
   * target and will afterward delete the original files, otherwise
   * the original files will not be deleted.
   */
  public GridElementTransferOperation( final IGridElement[] elements,
                                       final IGridContainer target,
                                       final boolean move ) {
    super( Messages.getString( "GridElementTransferOperation.transfer_name" ) ); //$NON-NLS-1$
    this.globalTarget = target;
    this.elements = elements;
    this.move = move;
  }
  
  /**
   * Create a new grid element transfer operation for file stores.
   * 
   * @param transferOperation transfer operation with information about transfer
   */
  public GridElementTransferOperation( final TransferInformation transferOperation ) {
    super( Messages.getString( "GridElementTransferOperation.transfer_name" ) ); //$NON-NLS-1$
    this.transferOperation = transferOperation;
  }
  
  /**
   * Get the destination of this transfer.
   * 
   * @return This transfer's target.
   */
  public IGridContainer getTarget() {
    return this.globalTarget;
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public IStatus run( final IProgressMonitor monitor ) {
    
    IProgressMonitor localMonitor
      = monitor == null
      ? new NullProgressMonitor()
      : monitor;
    
    MultiStatus status = new MultiStatus( Activator.PLUGIN_ID,
                                          IStatus.OK,
                                          Messages.getString("GridElementTransferOperation.op_status"), //$NON-NLS-1$
                                          null );

    if( this.transferOperation != null ) {
      //Resume file stores
      localMonitor.beginTask( Messages.getString("GridElementTransferOperation.transfering_element_progress"), 1 ); //$NON-NLS-1$
      String sourceName = new Path( this.transferOperation.getSource()
                                    .toURI()
                                    .getPath() ).lastSegment();
      TransferParams data = new TransferParams( this.transferOperation.getSource(),
                                                null,
                                                this.transferOperation.getDestination(),
                                                this.transferOperation.getDestination()
                                                  .getChild( sourceName ),
                                                localMonitor );
      try {
        checkExistingTarget( data );
      } catch( ProblemException exc ) {
        String msg = String.format( Messages.getString( "GridElementTransferOperation.errCannotCopyFile" ), //$NON-NLS-1$
                                    sourceName );
        data.status = new Status( IStatus.ERROR, Activator.PLUGIN_ID, msg, exc );
        status.merge( data.status );
      }
      if ( localMonitor.isCanceled() ) {
        throw new OperationCanceledException();
      }
      if( data.status.isOK() ) {
        IStatus resStatus = TransferManager.getManager().resumeTransfer( this.transferOperation, localMonitor );
        status.merge( resStatus );
      }
    } else {
      //Transfer grid elements
      localMonitor.beginTask( Messages.getString("GridElementTransferOperation.transfering_element_progress"), this.elements.length + 1 ); //$NON-NLS-1$
      for ( int i = 0 ; i < this.elements.length ; i++ ) {
        
        localMonitor.subTask( this.elements[i].getName() );
        
        IProgressMonitor subMonitor = new SubProgressMonitor( localMonitor, 1 );
        IStatus tempStatus = transferElement( this.elements[i], this.globalTarget, subMonitor );
        
        if ( !tempStatus.isOK() ) {
          status.merge( tempStatus );
        }
        
        if ( localMonitor.isCanceled() ) {
          throw new OperationCanceledException();
        }
        
        if ( this.move ) {
          IGridContainer parent = this.elements[i].getParent();
          boolean refresh = true;
          for ( int j = i+1 ; j < this.elements.length ; j++ ) {
            if ( this.elements[j].getParent().equals( parent ) ) {
              refresh = false;
              break;
            }
          }
          if ( refresh ) {
            try {
              // TODO mathias progress monitoring
              parent.refresh( null );
            } catch ( ProblemException pExc ) {
              status.merge( pExc.getStatus() );
            }
          }
        }
        
      }
    
      try {
        if( status.isOK() ) {
          if( this.globalTarget.getResource() instanceof IContainer ) {
            //Refresh only if target is a container
            this.globalTarget.refresh( new SubProgressMonitor( localMonitor, 1 ) );
          } else {
            this.globalTarget.getParent().refresh( new SubProgressMonitor( localMonitor, 1 ) );
            for( IGridElement elem: this.globalTarget.getParent().getChildren( new SubProgressMonitor( localMonitor, 1 ) ) ) {
              if( elem.getResource() instanceof IContainer && elem instanceof IGridContainer ) {
                ((IGridContainer)elem).refresh( new SubProgressMonitor( localMonitor,1 ) );
              }
            }
          }
        }
      } catch ( ProblemException pExc ) {
        status.merge( pExc.getStatus() );
      }
    }
    
    if( status.getSeverity() == IStatus.ERROR ) {
      showProblemDialog( status );
    }
    return Status.OK_STATUS;    // always return OK, because we displays our own ProblemDialog 
  }
  
  private void showProblemDialog( final MultiStatus status ) {
    IWorkbench workbench = PlatformUI.getWorkbench();
    IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
    if( window == null ) {
      IWorkbenchWindow[] windows = workbench.getWorkbenchWindows();
      if( windows.length > 0 ) {
        window = windows[ 0 ];
      }
    }
    if( window != null ) {
      final Shell shell = window.getShell();
      shell.getDisplay().syncExec( new Runnable() {

        public void run() {
          ProblemDialog.openProblem( shell,
                                     Messages.getString("GridElementTransferOperation.problemDialogTitle"), //$NON-NLS-1$
                                     status.getChildren().length == 1 ? Messages.getString("GridElementTransferOperation.msgElementCannotBeCopied") : Messages.getString("GridElementTransferOperation.msgElementsCannotBeCopied"), //$NON-NLS-1$ //$NON-NLS-2$
                                     createProblemException( status ) );
        }
      } );
    }
  }
  
  ProblemException createProblemException( final MultiStatus multiStatus )
  {
    ProblemException problemException = null;
    List<IStatus> failedStatuses = new ArrayList<IStatus>();
    for( IStatus childStatus : multiStatus.getChildren() ) {
      if( childStatus.getSeverity() != IStatus.OK ) {
        failedStatuses.add( childStatus );
      }
    }
    Throwable firstException = failedStatuses.size() > 0
                                                        ? failedStatuses.get( 0 )
                                                          .getException()
                                                        : null;
    problemException = new ProblemException( "eu.geclipse.ui.problem.tranfserOperationFailed", //$NON-NLS-1$
                                             firstException,
                                             Activator.PLUGIN_ID );
    IProblem problem = problemException.getProblem();
    for( IStatus status : failedStatuses ) {
      String msg = status.getMessage();
      if( status.getException() != null
          && !msg.equals( status.getException().getMessage() ) ) {
        msg = msg + "\n" + status.getException().getMessage(); //$NON-NLS-1$
      }
      problem.addReason( msg );
    }
    return problemException;
  }

  /**
   * Copy the specified file store to the destination file store.
   * The source may be a file or a directory. Directories will be copied
   * recursively.
   * 
   * @param from The source of the transfer.
   * @param to The target of the transfer.
   * @param monitor A progress monitor used to track the transfer.
   * @return A status object tracking errors of the transfer. 
   */
  private IStatus copy( final IFileStore from,
                        final IFileStore to,
                        final IProgressMonitor monitor ) {
    
    TransferParams data = new TransferParams( from, null, to, to.getChild( from.getName() ), monitor );
    try {
      checkExistingTarget( data );
    } catch( ProblemException exc ) {
      String msg = String.format( Messages.getString( "GridElementTransferOperation.errCannotCopyFile" ), from.getName() ); //$NON-NLS-1$
      data.status = new Status( IStatus.ERROR, Activator.PLUGIN_ID, msg, exc );
    }
    if ( monitor.isCanceled() ) {
      throw new OperationCanceledException();
    }
    if( data.status.isOK() ) {
      data.status = TransferManager.getManager().startTransfer( from, 
                                                                to,
                                                                this.move, 
                                                                monitor );
    }
    return data.status;
    
  }
  
  /**
   * Private class holding parameters passed to methods during transferring file.
   * Some parameters can be changed within method, and those changes should be visibled outside method,
   * so its cannot be passed as normal parameters.
   */
  private static class TransferParams {
    IFileStore sourceFile;
    IFileStore targetDirectory;
    IFileStore targetFile;
    IFileInfo sourceFileInfo;
    IStatus status = Status.OK_STATUS;
    IProgressMonitor monitor;
    
    TransferParams( final IFileStore sourceFile, final IFileInfo sourceFileInfo, final IFileStore toDirectory, final IFileStore to, final IProgressMonitor monitor ) {
      super();
      this.sourceFile = sourceFile;
      this.targetDirectory = toDirectory;
      this.targetFile = to;
      this.sourceFileInfo = sourceFileInfo;
      this.monitor = monitor;
    }
  }
  
  private IStatus transferElement( final IGridElement element,
                                   final IGridContainer target,
                                   final IProgressMonitor monitor ) {
    
    IStatus status = Status.OK_STATUS;
    monitor.beginTask( Messages.getString("GridElementTransferOperation.transfering_progress") + element.getName(), 10 ); //$NON-NLS-1$
    
    if ( ! ( element instanceof IGridConnectionElement )
        && ! ( target instanceof IGridConnectionElement ) ) {
      
      IResource sResource = element.getResource();
      IResource tResource = target.getResource();
      IPath destination = tResource.getFullPath().append( sResource.getName() );
      
      try {
        if ( this.move ) {
          sResource.move( destination, IResource.NONE, monitor );
        } else {
          sResource.copy( destination, IResource.NONE, monitor );
        }
      } catch ( CoreException cExc ) {
        status = new Status( IStatus.ERROR,
                             Activator.PLUGIN_ID,
                             IStatus.OK,
                             Messages.getString("GridElementTransferOperation.copy_resources_failed"), //$NON-NLS-1$
                             cExc );
      }
      
    }
    
    else if ( ( element instanceof IGridConnection ) 
        && ! ( target instanceof IGridConnectionElement ) ) {
      
      try {
      
        IGridConnection connection = ( IGridConnection ) element;
        URI uri = ( ( IGridConnection ) element ).getConnectionFileStore().toURI();
        IResource sResource = element.getResource();
        IContainer tFolder = ( IContainer ) target.getResource();
        
        if ( connection.isFolder() ) {
          IFolder folder = tFolder.getFolder( new Path( sResource.getName() ) );      
          folder.createLink( uri, IResource.NONE, new SubProgressMonitor( monitor, 5 ) );
        } else {
          IFile file = tFolder.getFile( new Path( sResource.getName() ) );
          file.createLink( uri, IResource.NONE, new SubProgressMonitor( monitor, 5 ) );
        }
        
        if ( this.move ) {
          sResource.delete( IResource.NONE, new SubProgressMonitor( monitor, 5 ) );
        }
        
      } catch ( CoreException cExc ) {
        status = new Status( IStatus.ERROR,
                             Activator.PLUGIN_ID,
                             IStatus.OK,
                             Messages.getString("GridElementTransferOperation.copy_linked_resource_failed"), //$NON-NLS-1$
                             cExc );
      }
      
    }

    else {

      // Prepare variables
      IFileStore inStore = null;
      IFileStore outStore = null;
      
      // Get input file store
      try {
        inStore = getFileStore( element );
      } catch ( CoreException cExc ) {
        status = new Status( IStatus.ERROR,
                             Activator.PLUGIN_ID,
                             IStatus.OK,
                             String.format( Messages.getString("GridElementTransferOperation.unable_get_filestore"), element.getName() ), //$NON-NLS-1$
                             cExc );
      }
      
      if ( status.isOK() ) {
        try {
          outStore = getFileStore( target );
        } catch ( CoreException cExc ) {
          status = new Status( IStatus.ERROR,
                               Activator.PLUGIN_ID,
                               IStatus.OK,
                               String.format( Messages.getString("GridElementTransferOperation.unable_get_filestore"), target.getName() ), //$NON-NLS-1$
                               cExc );
        }
      }
      
      if ( status.isOK() ) {
        
        // Put in try-finally-clause to ensure that monitor.done() is called
        try {
          
          // Copy operation
          IProgressMonitor subMonitor = new SubProgressMonitor( monitor, this.move ? 9 : 10 );
          if ( monitor.isCanceled() ) {
            throw new OperationCanceledException();
          }
          status = copy( inStore, outStore, subMonitor );
            
          /*
          if ( status.isOK() ) {
            startRefresh( target );
            
            if ( monitor.isCanceled() ) {
              throw new OperationCanceledException();
            }
            
            if ( this.move ) {
              status = delete( inStore, new SubProgressMonitor( monitor, 3 ) );
              if ( status.isOK() ) {
                startRefresh( element.getParent() );
              }
            }
          }
          */
        } finally {
          monitor.done();
        }
      }
    }
    
    return status;
  }
  
  /**
   * Get the file store from the specified element.
   *  
   * @param element The element to get the file store from.
   * @return The element's file store.
   * @throws CoreException If an error occurs while retrieving the file store.
   */
  private IFileStore getFileStore( final IGridElement element )
      throws CoreException {
    
    IFileStore result = null;
    
    if ( element instanceof IGridConnectionElement ) {
      result = ( ( IGridConnectionElement ) element ).getConnectionFileStore();
    } else {
      result = element.getFileStore();
    }
    
    return result; 
  }
  
  private void checkExistingTarget( final TransferParams data ) throws ProblemException {
    IFileInfo targetInfo = null;
    GEclipseURI geclURI = new GEclipseURI( data.targetFile.toURI() );
    try {
      targetInfo = EFS.getStore( geclURI.toSlaveURI() )
        .fetchInfo( EFS.NONE, new NullProgressMonitor() );
    } catch( ProblemException problemExc ) {
      throw new ProblemException( "eu.geclipse.core.filesystem.serverCouldNotBeContacted",
                                  String.format( "Could not contact server to fetch file info for %s",
                                                 data.targetFile.toURI().toString() ),
                                  problemExc,
                                  Activator.PLUGIN_ID );
    } catch( CoreException exc ) {
      throw new ProblemException( "eu.geclipse.core.problem.net.malformedURL",
                                  String.format( "URL %s is malformed or EFS using scheme %s doesn't exist",
                                                 data.targetFile.toURI().toString(),
                                                 geclURI.getSlaveScheme() ),
                                  exc,
                                  Activator.PLUGIN_ID );
    }
    
    if( targetInfo != null && targetInfo.exists() ) {
      
      if( data.sourceFile.toURI().equals( data.targetFile.toURI() ) ) {
        String msg = String.format( Messages.getString("GridElementTransferOperation.errSourceAndTargetAreTheSame"), data.sourceFile.getName() ); //$NON-NLS-1$
        throw new ProblemException( "eu.geclipse.ui.problem.fileCannotOverwriteItself", msg, Activator.PLUGIN_ID ); //$NON-NLS-1$
      }
      
      switch( this.overwriteMode ) {
        case ASK:
          askOverwrite( data, targetInfo );
        break;
        case OVERWRITE_ALL:
          deleteTarget( data );
        break;
        case IGNORE_ALL:
          ignoreTransfer( data );
        break;
      }
    }
  }
  
  
  private void askOverwrite( final TransferParams data, final IFileInfo targetInfo ) {
    
    
    class Runner implements Runnable {
      int exitCode;
      private final Display display;
      
      Runner( final Display display ) {
        this.display = display;
      }

      public void run() {        
        Shell shell = null;
        String[] labels = { Messages.getString("GridElementTransferOperation.buttonYes"), Messages.getString("GridElementTransferOperation.buttonYesAll"), Messages.getString("GridElementTransferOperation.buttonNo"), Messages.getString("GridElementTransferOperation.buttonNoAll"), Messages.getString("GridElementTransferOperation.buttonCancel") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
        
        if( this.display != null ) {
          shell = this.display.getActiveShell();
        }        
        
        String message = String.format( Messages.getString("GridElementTransferOperation.overwriteMsg"),  //$NON-NLS-1$
                                        data.targetFile.getName(),
                                        formatURI( data.targetFile.toURI() ),
                                        formatDate( targetInfo.getLastModified() ), 
                                        formatURI( data.sourceFile.toURI() ),
                                        "" //$NON-NLS-1$
                                        );        
        
        MessageDialog dialog = new MessageDialog( shell,
                                                  Messages.getString("GridElementTransferOperation.overwriteTitle"), //$NON-NLS-1$
                                                  null,
                                                  message,
                                                  MessageDialog.QUESTION,
                                                  labels,
                                                  0 );
        this.exitCode = dialog.open();     
      }      
    }
    
    Display display = PlatformUI.getWorkbench().getDisplay();
    Runner runner = new Runner( display );
    display.syncExec( runner );
    
    switch( runner.exitCode ) {
      case 0:
        deleteTarget( data );
        break;
        
      case 1:
        this.overwriteMode = OverwriteMode.OVERWRITE_ALL;
        deleteTarget( data );
        break;
        
      case 2:
        ignoreTransfer( data );
        break;
        
      case 3:
        this.overwriteMode = OverwriteMode.IGNORE_ALL;
        ignoreTransfer( data );
        break;
        
      case 4:
        data.status = Status.CANCEL_STATUS;
        data.monitor.setCanceled( true );
        break;
    }
  } 

  String formatURI( final URI uri ) {
    String uriString = ""; //$NON-NLS-1$
    GEclipseURI gUri = new GEclipseURI( uri );
    URI slaveURI = gUri.toSlaveURI();
    
    if( slaveURI == null ) {
      uriString = uri.toString();
    } else {
      uriString = slaveURI.toString();
    }
    return uriString;
  }

  String formatDate( final long lastModified ) {
    String dateString = ""; //$NON-NLS-1$
    
    if( lastModified != EFS.NONE ) {      
      dateString = DateFormat.getDateTimeInstance().format( new Date( lastModified ) );
    }
    
    return dateString;
  }

  private void deleteTarget( final TransferParams data ) {
    String filename = data.targetFile.getName();

    try {          
      data.monitor.subTask( String.format( Messages.getString("GridElementTransferOperation.deletingTarget"), filename ) );  //$NON-NLS-1$
      data.targetFile.delete( EFS.NONE, data.monitor );
      
      if( !data.monitor.isCanceled() ) {
        data.targetFile = data.targetDirectory.getChild( filename );
      }
    } catch( CoreException exception ) {
      data.status = new Status( IStatus.ERROR, Activator.PLUGIN_ID, String.format( Messages.getString("GridElementTransferOperation.errCannotDeleteTarget"), data.targetFile.getName() ), exception ); //$NON-NLS-1$
    }
  }
  
  private void ignoreTransfer( final TransferParams data ) {
    data.status = new Status( IStatus.INFO, Activator.PLUGIN_ID, Messages.getString("GridElementTransferOperation.targetExists") ); //$NON-NLS-1$
  }
  /*
  private void startRefresh( final IGridContainer container ) {
    
    Job refreshJob = new Job( "Refresh @ " + container.getName() ) {
      @Override
      protected IStatus run( final IProgressMonitor monitor ) {
        try {
          container.refresh( new SubProgressMonitor( monitor, 1 ) );
        } catch ( ProblemException pExc ) {
          Activator.logException( pExc );
        }
        return Status.OK_STATUS;
      }
    };
    refreshJob.setSystem( true );
    refreshJob.schedule();
    
  }
  */
}
