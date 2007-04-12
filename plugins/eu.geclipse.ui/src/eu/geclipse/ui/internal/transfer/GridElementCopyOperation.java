package eu.geclipse.ui.internal.transfer;

import java.io.InputStream;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.ui.internal.Activator;

public class GridElementCopyOperation
    extends GridElementTransferOperation {
  
  public GridElementCopyOperation( final IGridContainer target,
                                   final IGridElement[] elements ) {
    super( target, elements );
  }
  
  protected void transferElement( final IGridContainer target,
                                  final IGridElement element,
                                  final IProgressMonitor monitor )
      throws CoreException {
    if ( element.isLocal() && !target.isLocal() ) {
      transferFromLocalToRemote( target, element, monitor );
    } else {
      IResource resource = element.getResource();
      String name = resource.getName();
      IPath destination = target.getPath().append( name );
      resource.copy( destination, IResource.NONE, monitor );
    }
  }
  
  protected void transferFromLocalToRemote( final IGridContainer target,
                                            final IGridElement element,
                                            final IProgressMonitor monitor )
      throws CoreException {
    IContainer container = ( IContainer ) target.getResource();
    checkExists( container );
    IResource resource = element.getResource();
    if ( resource instanceof IFile ) {
      transferLocalFileToRemote( container, ( IFile ) resource, monitor );
    } else {
      IStatus status = new Status( IStatus.ERROR,
                                   Activator.PLUGIN_ID,
                                   IStatus.CANCEL,
                                   "Transfer of local resources to remote locations are not yet implemented for other resources than files",
                                   null );
      throw new CoreException( status );
    }
  }
  
  protected void transferLocalFileToRemote( final IContainer target,
                                            final IFile file,
                                            final IProgressMonitor monitor )
      throws CoreException {
    IFile newFile = target.getFile( new Path( file.getName() ) );
    InputStream contents = file.getContents();
    newFile.create( contents, IResource.NONE, monitor );
  }
  
  private static void checkExists( final IResource resource )
      throws CoreException {
    if ( !resource.exists() ) {
      IStatus status = new Status( IStatus.ERROR,
                                   Activator.PLUGIN_ID,
                                   IStatus.CANCEL,
                                   "Resource " + resource.getName() + " does not exist",
                                   null );
      throw new CoreException( status );
    }
  }
  
}
