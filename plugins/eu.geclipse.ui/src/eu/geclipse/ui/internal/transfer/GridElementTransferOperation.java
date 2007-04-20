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

public class GridElementTransferOperation
    extends Job {
  
  private IGridContainer globalTarget;
  
  private IGridElement[] elements;
  
  private boolean move;
  
  public GridElementTransferOperation( final IGridElement[] elements,
                                       final IGridContainer target,
                                       final boolean move ) {
    super( "Element-Transfer" );
    this.globalTarget = target;
    this.elements = elements;
    this.move = move;
  }
  
  public IGridElement[] getElements() {
    return this.elements;
  }
  
  public IGridContainer getTarget() {
    return this.globalTarget;
  }
  
  @Override
  public IStatus run( final IProgressMonitor monitor ) {
    
    IProgressMonitor localMonitor
      = monitor == null
      ? new NullProgressMonitor()
      : monitor;
      
    MultiStatus status = new MultiStatus( Activator.PLUGIN_ID, IStatus.OK, "Transfer operation status", null );
      
    localMonitor.beginTask( "Transfering elements ... ", this.elements.length );
    
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
  
  private IStatus copyDirectory( final IFileStore from,
                                 final IFileStore to,
                                 final IProgressMonitor monitor ) {
    
    // Prepare operation
    IStatus status = Status.OK_STATUS;
    monitor.beginTask( "Copying " + from.getName(), 10 );
    
    try {
      
      IFileStore[] children = null;
      try {
        children = from.childStores( EFS.NONE, new SubProgressMonitor( monitor, 1 ) );
      } catch( CoreException cExc ) {
        status = new Status( IStatus.ERROR,
                             Activator.PLUGIN_ID,
                             IStatus.OK,
                             "Error while getting children from " + from.getName(),
                             cExc );
      }
      
      if ( status.isOK() ) {
        
        IFileStore newTo = to.getChild( from.getName() );
        IFileInfo newToInfo = newTo.fetchInfo();
        boolean mkdir = !newToInfo.exists();
      
        SubProgressMonitor subMonitor = new SubProgressMonitor( monitor, 9 );
        subMonitor.beginTask( "Copying " + from.getName(), mkdir ? children.length + 1 : children.length );
      
        if ( mkdir ) {
          try {
            newTo.mkdir( EFS.NONE, new SubProgressMonitor( subMonitor, 1 ) );
          } catch( CoreException cExc ) {
            status = new Status( IStatus.ERROR,
                                 Activator.PLUGIN_ID,
                                 IStatus.OK,
                                 "Unable to create directory " + newTo.getName(),
                                 cExc );
          }
        }
      
        if ( status.isOK() ) {
        
          MultiStatus mStatus
            = new MultiStatus( Activator.PLUGIN_ID, IStatus.OK, "Copying members from " + from.getName(), null );
          
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
  
  private IStatus copyFile( final IFileStore from,
                            final IFileStore to,
                            final IProgressMonitor monitor ) {
    
    // Prepare copy operation
    monitor.beginTask( "Copying " + from.getName(), 10 );
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
                             "Unable to transfer file " + toStore.getName() + " since destination file already exists in " + to.getName(),
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
                               "Unable to open input stream for " + from.getName(),
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
                               "Unable to open output stream for " + toStore.getName(),
                               cExc );
        }
      }
      
      // Prepare copy operation
      if ( status.isOK() ) {
        
        byte[] buffer = new byte[ 1024 ];
        SubProgressMonitor subMonitor = new SubProgressMonitor( monitor, 8 );
        subMonitor.beginTask( "Copying " + from.getName(), (int)(length/buffer.length) );
        
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
                                 "Error while reading data from " + from.getName(),
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
                                 "Error while writing data to " + toStore.getName(),
                                 ioExc );
          }
          
          kb_counter++;
          subMonitor.worked( 1 );
          subMonitor.subTask( fromInfo.getName()
                              + ": "
                              + (int)(100./length*buffer.length*kb_counter)
                              + "% ("
                              + kb_counter
                              + "/"
                              + (int)(length/buffer.length)
                              + "kB)" );
          
        }
        
      }
      
    } finally {
      
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
  
  private IStatus delete( final IFileStore store,
                          final IProgressMonitor monitor ) {
    
    IStatus status = Status.OK_STATUS;
    
    try {
      store.delete( EFS.NONE, monitor );
    } catch( CoreException cExc ) {
      status = new Status( IStatus.ERROR,
                           Activator.PLUGIN_ID,
                           IStatus.OK,
                           "Error while deleting " + store.getName(),
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
                           "Unable to get file store for " + element.getName(),
                           cExc );
    }
    
    if ( status.isOK() ) {
      try {
        outStore = getFileStore( target );
      } catch ( CoreException cExc ) {
        status = new Status( IStatus.ERROR,
                             Activator.PLUGIN_ID,
                             IStatus.OK,
                             "Unable to get file store for " + target.getName(),
                             cExc );
      }
    }
    
    if ( status.isOK() ) {
      
      monitor.beginTask( "Transfering " + element.getName(), 10 );
      
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
