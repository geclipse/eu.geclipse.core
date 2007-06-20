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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.ui.internal.Activator;

/**
 * Job for transfering elements from local to local, local to remote,
 * remote to local and remote to remote. Elements may either by copied
 * or moved. A move operation is an ordinary copy operation followed
 * by a delete operation. Both files and directories are supported.  
 */
public class GridElementTransferOperation
    extends Job {
  
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
      
    MultiStatus status = new MultiStatus( Activator.PLUGIN_ID, IStatus.OK, Messages.getString("GridElementTransferOperation.op_status"), null ); //$NON-NLS-1$
      
    localMonitor.beginTask( Messages.getString("GridElementTransferOperation.transfering_element_progress"), this.elements.length ); //$NON-NLS-1$
    
    for ( IGridElement element : this.elements ) {
      
      localMonitor.subTask( element.getName() );
      
      IProgressMonitor subMonitor = new SubProgressMonitor( localMonitor, 1 );
      IStatus tempStatus = transferElement( element, this.globalTarget, subMonitor );
      
      if ( !tempStatus.isOK() ) {
        status.merge( tempStatus );
      }
      
      if ( localMonitor.isCanceled() ) {
        throw new OperationCanceledException();
      }
      
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
            = new MultiStatus( Activator.PLUGIN_ID, IStatus.OK, Messages.getString("GridElementTransferOperation.copying_members_status") + from.getName(), null ); //$NON-NLS-1$
          
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
    
    // Prepare copy operation
    monitor.beginTask( Messages.getString("GridElementTransferOperation.copying_progress") + from.getName(), 10 ); //$NON-NLS-1$
    InputStream inStream = null;
    OutputStream outStream = null;
    IStatus status = Status.OK_STATUS;
    
    // Put in try-catch-clause to ensure that monitor.done() is called and streams are closed
    try {
    
      // Fetch file infos
      IFileInfo fromInfo = from.fetchInfo();
      long length = fromInfo.getLength();
      IFileStore toStore = to.getChild( from.getName() );
      IFileInfo toInfo = toStore.fetchInfo();
            
      // Test if target exists and omit operation in case it exists

      if ( toInfo.exists() ) {
        status = new Status( IStatus.ERROR,
                             Activator.PLUGIN_ID,
                             IStatus.OK,
                             String.format( Messages.getString("GridElementTransferOperation.unable_to_transfer_file"), toStore.getName(), to.getName() ), //$NON-NLS-1$
                             null );
      }
      
      // Open input stream
      if ( status.isOK() ) {
        try {
          inStream = from.openInputStream( EFS.NONE, new SubProgressMonitor( monitor, 1 ) );
        } catch( CoreException cExc ) {
          status = new Status( IStatus.ERROR,
                               Activator.PLUGIN_ID,
                               IStatus.OK,
                               String.format( Messages.getString("GridElementTransferOperation.unable_to_open_istream"), from.getName() ), //$NON-NLS-1$
                               cExc );
        }
      }
      
      // Open output stream
      if ( status.isOK() ) {
        try {
          outStream = toStore.openOutputStream( EFS.NONE, new SubProgressMonitor( monitor, 1 ) );
        } catch( CoreException cExc ) {
          status = new Status( IStatus.ERROR,
                               Activator.PLUGIN_ID,
                               IStatus.OK,
                               String.format( Messages.getString("GridElementTransferOperation.unable_to_open_ostream"), toStore.getName() ), //$NON-NLS-1$
                               cExc );
        }
      }
      
      // Prepare copy operation
      if ( status.isOK() && ( inStream != null ) && ( outStream != null ) ) {
        
        byte[] buffer = new byte[ 1024 ];
        SubProgressMonitor subMonitor = new SubProgressMonitor( monitor, 8 );
        subMonitor.beginTask( Messages.getString("GridElementTransferOperation.copying_progress") + from.getName(), (int)(length/buffer.length) ); //$NON-NLS-1$
        
        // Copy the data
        int kb_counter = 0;
        while ( !subMonitor.isCanceled() && status.isOK() ) {
          
          int bytesRead = -1;
          
          // Read data from source
          try {
            bytesRead = inStream.read( buffer );
          } catch( IOException ioExc ) {
            status = new Status( IStatus.ERROR,
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
            status = new Status( IStatus.ERROR,
                                 Activator.PLUGIN_ID,
                                 IStatus.OK,
                                 String.format( Messages.getString("GridElementTransferOperation.writing_error"), toStore.getName() ), //$NON-NLS-1$
                                 ioExc );
          }
          
          kb_counter++;
          subMonitor.worked( 1 );
          subMonitor.subTask( String.format( Messages.getString("GridElementTransferOperation.transfer_progress_format"),  //$NON-NLS-1$
                                             fromInfo.getName(),
                                             new Integer( (int)(100./length*buffer.length*kb_counter) ),
                                             new Integer( kb_counter ),
                                             new Integer( (int)(length/buffer.length) ) ) );
          
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
    
    return status;
        
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

    // Prepare variables
    IStatus status = Status.OK_STATUS;
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
      
      monitor.beginTask( Messages.getString("GridElementTransferOperation.transfering_progress") + element.getName(), 10 ); //$NON-NLS-1$
      
      // Put in try-finally-clause to ensure that monitor.done() is called
      try {
        
        // Copy operation
        IProgressMonitor subMonitor = new SubProgressMonitor( monitor, this.move ? 5 : 9 );
        status = copy( inStore, outStore, subMonitor );
        
        if ( status.isOK() ) {
          
          try {
            target.refresh( new SubProgressMonitor( monitor, 1 ) );
          } catch( GridModelException gmExc ) {
            // refresh errors should not disturb the operation
            // but should be tracked, therefore just log it
            Activator.logException( gmExc );
          }
          
          if ( monitor.isCanceled() ) {
            throw new OperationCanceledException();
          }
          
          if ( this.move ) {
            
            status = delete( inStore, new SubProgressMonitor( monitor, 3 ) );
            
            if ( status.isOK() ) {
              try {
                element.getParent().refresh( new SubProgressMonitor( monitor, 1 ) );
              } catch( GridModelException gmExc ) {
                // refresh errors should not disturb the operation
                // but should be tracked, therefore just log it
                Activator.logException( gmExc );
              }
            }
            
          }
          
        }
        
      } finally {
        monitor.done();
      }
      
    }
    
    return status;
    
  }
  
  /**
   * Get the file store from the specified element.
   *  
   * @param element The element to get the file store from.
   * @return The element's file store.
   * @throws CoreException If an error occures while retrieving the file store.
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
  
}
