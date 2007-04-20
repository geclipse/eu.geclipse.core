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
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
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
  
  public IStatus run( final IProgressMonitor monitor ) {
    
    IProgressMonitor localMonitor
      = monitor == null
      ? new NullProgressMonitor()
      : monitor;
      
    String errors = ""; //$NON-NLS-1$
    localMonitor.beginTask( "Transfering elements ... ", this.elements.length );
    try {
      
      for ( IGridElement element : this.elements ) {
        
        localMonitor.subTask( element.getName() );
        
        IProgressMonitor subMonitor = new SubProgressMonitor( localMonitor, 1 );
        try {
          transferElement( element, this.globalTarget, subMonitor );
        } catch ( CoreException cExc ) {
          // TODO mathias proper exception handling
          errors += cExc.getLocalizedMessage() + "; "; //$NON-NLS-1$
        } finally {
          subMonitor.done();
        }
        
        if ( localMonitor.isCanceled() ) {
          throw new OperationCanceledException();
        }
        
      }
      
    } finally {
      localMonitor.done();
      if ( errors.length() > 0 ) {
        //throw new GridException( UIProblems.ELEMENT_TRANSFER_FAILED, errors );
      }
    }
    
    return Status.OK_STATUS;
    
  }
  
  private void copy( final IFileStore from,
                     final IFileStore to,
                     final IProgressMonitor monitor )
      throws CoreException {
    
    IFileInfo fromInfo = from.fetchInfo();
    if ( fromInfo.isDirectory() ) {
      copyDirectory( from, to, monitor );
    } else {
      try {
        copyFile( from, to, monitor );
      } catch( IOException ioExc ) {
        // TODO mathias proper exception handling
        Activator.logException( ioExc );
      }
    }
    
  }
  
  private void copyDirectory( final IFileStore from,
                              final IFileStore to,
                              final IProgressMonitor monitor )
      throws CoreException {
    
    monitor.beginTask( "Copying " + from.getName(), 10 );
    
    try {
      
      IFileStore[] children = from.childStores( EFS.NONE, new SubProgressMonitor( monitor, 1 ) );
      IFileStore newTo = to.getChild( from.getName() );
      IFileInfo newToInfo = newTo.fetchInfo();
      boolean mkdir = !newToInfo.exists();
      
      SubProgressMonitor subMonitor = new SubProgressMonitor( monitor, 9 );
      subMonitor.beginTask( "Copying " + from.getName(), mkdir ? children.length + 1 : children.length );
      
      if ( mkdir ) {
        newTo.mkdir( EFS.NONE, new SubProgressMonitor( subMonitor, 1 ) );
      }
      
      for ( IFileStore child : children ) {
        copy( child, newTo, new SubProgressMonitor( subMonitor, 1) );
        if ( subMonitor.isCanceled() ) break;
      }
      
    } finally {
      monitor.done();
    }
    
  }
  
  private void copyFile( final IFileStore from,
                         final IFileStore to,
                         final IProgressMonitor monitor )
      throws CoreException, IOException {
    
    monitor.beginTask( "Copying " + from.getName(), 10 );
    
    InputStream inStream = null;
    OutputStream outStream = null;
    
    try {
    
      IFileInfo fromInfo = from.fetchInfo();
      long length = fromInfo.getLength();
      inStream = from.openInputStream( EFS.NONE, new SubProgressMonitor( monitor, 1 ) );
      
      IFileStore toStore = to.getChild( from.getName() );
      IFileInfo toInfo = to.fetchInfo();
      if ( !toInfo.exists() ) {
        
        outStream = toStore.openOutputStream( EFS.NONE, new SubProgressMonitor( monitor, 1 ) );
        
        byte[] buffer = new byte[ 1024 ];
        SubProgressMonitor subMonitor = new SubProgressMonitor( monitor, 8 );
        subMonitor.beginTask( "Copying " + from.getName(), (int)(length/buffer.length) );
        
        int kb_counter = 0;
        while ( !subMonitor.isCanceled() ) {
          int bytesRead = inStream.read( buffer );
          if ( bytesRead == -1 ) {
            break;
          }
          outStream.write( buffer, 0, bytesRead );
          subMonitor.worked( 1 );
          subMonitor.subTask( fromInfo.getName() + " (" + ( kb_counter++ ) + "/" + (int)(length/buffer.length) + "kB)" );
        }
        
      } else {
        // TODO mathias
      }
      
    } finally {
      if ( outStream != null ) {
        outStream.close();
      }
      if ( inStream != null ) {
        inStream.close();
      }
      monitor.done();
    }
        
  }
  
  private void delete( final IFileStore store,
                       final IProgressMonitor monitor ) {
    
  }
  
  private void transferElement( final IGridElement element,
                                final IGridContainer target,
                                final IProgressMonitor monitor )
      throws CoreException {
    
    IFileStore inStore = getFileStore( element );
    IFileStore outStore = getFileStore( target );
    
    monitor.beginTask( "Transfering " + element.getName(), 10 );
    try {
      IProgressMonitor subMonitor = new SubProgressMonitor( monitor, this.move ? 7 : 10 );
      copy( inStore, outStore, subMonitor );
      if ( this.move ) {
        delete( inStore, new SubProgressMonitor( monitor, 3 ) );
      }
    } finally {
      monitor.done();
    }
    
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
