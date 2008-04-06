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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.text.DateFormat;
import java.util.Date;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.core.filesystem.GEclipseURI;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridConnection;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
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
    super( Messages.getString("GridElementTransferOperation.transfer_name") ); //$NON-NLS-1$
    this.globalTarget = target;
    this.elements = elements;
    this.move = move;
  }
  
  /**
   * Get the elements that should be transfered.
   * 
   * @return The source elements of this transfer.
   */
  public IGridElement[] getElements() {
    return this.elements;
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
          } catch ( GridModelException gmExc ) {
            status.merge( gmExc.getStatus() );
          }
        }
      }
      
    }
    
    try {
      this.globalTarget.refresh( new SubProgressMonitor( localMonitor, 1 ) );
    } catch ( GridModelException gmExc ) {
      status.merge( gmExc.getStatus() );
    }
   
    return status;
    
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
    
    IStatus status = Status.OK_STATUS;
    IFileInfo fromInfo = from.fetchInfo();
    
    if ( fromInfo.isDirectory() ) {
      status = copyDirectory( from, to, monitor );
    } else {
      status = copyFile( from, to, monitor );
    }
    
    return status;
    
  }
  
  /**
   * Copies this specified directory file store and its children recursively to the
   * destination file store.
   * 
   * @param from The source of the transfer.
   * @param to The target of the transfer.
   * @param monitor A progress monitor used to track the transfer.
   * @return A status object tracking errors of the transfer.
   */
  private IStatus copyDirectory( final IFileStore from,
                                 final IFileStore to,
                                 final IProgressMonitor monitor ) {
    
    // Prepare operation
    IStatus status = Status.OK_STATUS;
    monitor.beginTask( Messages.getString("GridElementTransferOperation.copying_progress") + from.getName(), 10 ); //$NON-NLS-1$
    
    try {
      
      IFileStore[] children = null;
      try {
        children = from.childStores( EFS.NONE, new SubProgressMonitor( monitor, 1 ) );
      } catch( CoreException cExc ) {
        status = new Status( IStatus.ERROR,
                             Activator.PLUGIN_ID,
                             IStatus.OK,
                             Messages.getString("GridElementTransferOperation.fetching_children_error") + from.getName(), //$NON-NLS-1$
                             cExc );
      }
      
      if ( status.isOK() && ( children != null ) ) {
        
        IFileStore newTo = to.getChild( from.getName() );
        IFileInfo newToInfo = newTo.fetchInfo();
        boolean mkdir = !newToInfo.exists();
      
        SubProgressMonitor subMonitor = new SubProgressMonitor( monitor, 9 );
        subMonitor.beginTask( Messages.getString("GridElementTransferOperation.copying_progress") //$NON-NLS-1$
                              + from.getName(),
                              mkdir ? children.length + 1 : children.length );
      
        if ( mkdir ) {
          try {
            newTo.mkdir( EFS.NONE, new SubProgressMonitor( subMonitor, 1 ) );
          } catch( CoreException cExc ) {
            status = new Status( IStatus.ERROR,
                                 Activator.PLUGIN_ID,
                                 IStatus.OK,
                                 Messages.getString("GridElementTransferOperation.create_dir_error") + newTo.getName(), //$NON-NLS-1$
                                 cExc );
          }
        }
      
        if ( status.isOK() ) {
        
          MultiStatus mStatus
            = new MultiStatus( Activator.PLUGIN_ID,
                               IStatus.OK,
                               Messages.getString("GridElementTransferOperation.copying_members_status") //$NON-NLS-1$
                                 + from.getName(),
                               null );
          
          for ( IFileStore child : children ) {
            
            IStatus tempStatus = copy( child, newTo, new SubProgressMonitor( subMonitor, 1) );
            
            if ( !tempStatus.isOK() ) {
              mStatus.merge( tempStatus );
            }
            
            if ( subMonitor.isCanceled() ) break;
            
          }
          
          if ( !mStatus.isOK() ) {
            status = mStatus;
          }
        
        }
        
      }
      
    } finally {
      monitor.done();
    }
    
    return status;
    
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
      this.monitor = monitor;
    }
  }
  
  /**
   * Copy the content of the specified file store to the destination file store.
   * The source may be a file or a directory. The source has to be a file.
   * 
   * @param from The source of the transfer.
   * @param to The target of the transfer.
   * @param monitor A progress monitor used to track the transfer.
   * @return A status object tracking errors of the transfer.
   */
  private IStatus copyFile( final IFileStore from,
                            final IFileStore to,
                            final IProgressMonitor monitor ) {
    
    TransferParams data = new TransferParams( from, null, to, to.getChild( from.getName() ), monitor );

    // Prepare copy operation
    monitor.beginTask( Messages.getString("GridElementTransferOperation.copying_progress") + from.getName(), 100 ); //$NON-NLS-1$
    monitor.setTaskName( Messages.getString("GridElementTransferOperation.copying_progress") + from.getName() ); //$NON-NLS-1$
    InputStream inStream = null;
    OutputStream outStream = null;    
    
    // Put in try-catch-clause to ensure that monitor.done() is called and streams are closed
    try {
      
      // Fetch file info
      data.sourceFileInfo = from.fetchInfo();
      long length = data.sourceFileInfo.getLength();            
      
      checkExistingTarget( data );
      
      // Open input stream
      if ( data.status.isOK() ) {
        try {
          inStream = from.openInputStream( EFS.NONE, new SubProgressMonitor( monitor, 8 ) );
        } catch( CoreException cExc ) {
          data.status = new Status( IStatus.ERROR,
                               Activator.PLUGIN_ID,
                               IStatus.OK,
                               String.format( Messages.getString("GridElementTransferOperation.unable_to_open_istream"), from.getName() ), //$NON-NLS-1$
                               cExc );
        }
      }
      
      // Open output stream
      if ( data.status.isOK() ) {
        try {
          outStream = data.targetFile.openOutputStream( EFS.NONE, new SubProgressMonitor( monitor, 8 ) );
        } catch( CoreException cExc ) {
          data.status = new Status( IStatus.ERROR,
                               Activator.PLUGIN_ID,
                               IStatus.OK,
                               String.format( Messages.getString("GridElementTransferOperation.unable_to_open_ostream"), data.targetFile.getName() ), //$NON-NLS-1$
                               cExc );
        }
      }
      
      // Prepare copy operation
      if ( data.status.isOK() && ( inStream != null ) && ( outStream != null ) ) {
        
        byte[] buffer = new byte[ 1024 * 64 ];
        Integer totalKb = Integer.valueOf( (int) Math.ceil((double)length/1024) ) ;
        
        SubProgressMonitor subMonitor = new SubProgressMonitor( monitor, 84 );
        subMonitor.beginTask( Messages.getString("GridElementTransferOperation.copying_progress") + from.getName(), (int)length ); //$NON-NLS-1$
        subMonitor.subTask( Messages.getString("GridElementTransferOperation.copying_progress") + from.getName() ); //$NON-NLS-1$
        
        // Copy the data
        long byteCounter = 0;
        while ( !subMonitor.isCanceled() && data.status.isOK() ) {
          
          int bytesRead = -1;
          
          // Read data from source
          try {
            bytesRead = inStream.read( buffer );
          } catch( IOException ioExc ) {
            data.status = new Status( IStatus.ERROR,
                                 Activator.PLUGIN_ID,
                                 IStatus.OK,
                                 String.format( Messages.getString("GridElementTransferOperation.reading_error"), from.getName() ), //$NON-NLS-1$
                                 ioExc );
          }
          
          // Check if there is still data available
          if ( bytesRead == -1 ) {
            break;
          }

          // Write data to target
          try {
            outStream.write( buffer, 0, bytesRead );
          } catch( IOException ioExc ) {
            data.status = new Status( IStatus.ERROR,
                                 Activator.PLUGIN_ID,
                                 IStatus.OK,
                                 String.format( Messages.getString("GridElementTransferOperation.writing_error"), data.targetFile.getName() ), //$NON-NLS-1$
                                 ioExc );
          }
          
          byteCounter += bytesRead;
          subMonitor.worked( bytesRead );
          subMonitor.subTask( String.format( Messages.getString("GridElementTransferOperation.transfer_progress_format"),  //$NON-NLS-1$
                                             data.sourceFileInfo.getName(),
                                             new Integer( (int)(100.* byteCounter / length ) ),
                                             new Integer( (int)(byteCounter/1024) ),
                                             totalKb ) );
          
        }
        
        subMonitor.done();
        
      }
      
    } finally {
      
      monitor.subTask( Messages.getString("GridElementTransferOperation.closing_streams") ); //$NON-NLS-1$
      
      // Close output stream
      if ( outStream != null ) {
        try {
          outStream.close();
        } catch ( IOException ioExc ) {
          // just ignore this
        }
      }
      
      // Close input stream
      if ( inStream != null ) {
        try {
          inStream.close();
        } catch( IOException e ) {
          // just ignore this
        }
      }
      
      monitor.done();
      
    }

    return data.status;
        
  }

  /**
   * Deletes the specified file store.
   * 
   * @param store The file store to be deleted.
   * @param monitor A progress monitor used to track the transfer.
   * @return A status object tracking errors of the deletion.
   */
  private IStatus delete( final IFileStore store,
                          final IProgressMonitor monitor ) {
    
    IStatus status = Status.OK_STATUS;
    
    try {
      store.delete( EFS.NONE, monitor );
    } catch( CoreException cExc ) {
      status = new Status( IStatus.ERROR,
                           Activator.PLUGIN_ID,
                           IStatus.OK,
                           Messages.getString("GridElementTransferOperation.deletion_error") + store.getName(), //$NON-NLS-1$
                           cExc );
    }
    
    return status;
    
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
      
        URI uri = ( ( IGridConnection ) element ).getConnectionFileStore().toURI();
        IResource sResource = element.getResource();
        IFolder tFolder = ( IFolder ) target.getResource();
        IFolder folder = tFolder.getFolder( sResource.getName() );      
        
        folder.createLink( uri, IResource.NONE, new SubProgressMonitor( monitor, 5 ) );
        
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
          status = copy( inStore, outStore, subMonitor );
          
          if ( monitor.isCanceled() ) {
            throw new OperationCanceledException();
          }
          
          if ( status.isOK() && this.move ) {
            status = delete( inStore, new SubProgressMonitor( monitor, 1 ) );
          }
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
  
  private void checkExistingTarget( final TransferParams data ) {
    IFileInfo targetInfo = data.targetFile.fetchInfo();
    if( targetInfo.exists() ) {
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
                                        formatDate( data.sourceFileInfo.getLastModified() )
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
        } catch ( GridModelException gmExc ) {
          Activator.logException( gmExc );
        }
        return Status.OK_STATUS;
      }
    };
    refreshJob.setSystem( true );
    refreshJob.schedule();
    
  }
  */
}
