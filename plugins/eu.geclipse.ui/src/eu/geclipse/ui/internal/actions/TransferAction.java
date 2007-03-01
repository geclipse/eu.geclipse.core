package eu.geclipse.ui.internal.actions;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.ui.actions.SelectionListenerAction;
import org.eclipse.ui.part.ResourceTransfer;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;

public class TransferAction extends SelectionListenerAction {
  
  private Clipboard clipboard;
  
  protected TransferAction( final String text,
                            final Clipboard clipboard ) {
    super( text );
    this.clipboard = clipboard;
  }
  
  public Clipboard getClipboard() {
    return this.clipboard;
  }
  
  public boolean isDragSource( final Object obj ) {
    boolean result = false;
    if ( obj instanceof IGridElement ) {
      IGridElement element = ( IGridElement ) obj;
      if ( !element.isVirtual() ) {
        result = true;
      }
    }
    return result;
  }
  
  public boolean isDropTarget( final Object obj ) {
    boolean result = false;
    if ( obj instanceof IGridContainer ) {
      IGridContainer container = ( IGridContainer ) obj;
      if ( container.isLocal() && !container.isVirtual() ) {
        result = true;
      }
    }
    return result;
  }
  
  private boolean checkDestination( final IResource resource,
                                    final IContainer destination ) {
    IPath resourcePath = resource.getFullPath().removeLastSegments( 1 );
    IPath destinationPath = destination.getFullPath();
    return !resourcePath.equals( destinationPath );
  }
  
  private boolean checkExists( final IResource resource,
                               final IContainer destination ) {
    String resourceName = resource.getName();
    IResource member = destination.findMember( resourceName );
    return ( member != null );
  }
  
  private void copyFile( final IFile file,
                         final IContainer destination )
      throws CoreException {
    
    IFile localFile = null;
    
    if ( !file.exists() ) {
      IPath path = file.getFullPath();
      IGridElement element = GridModel.getRoot().findElement( path );
      if ( element != null ) {
        IResource resource = element.getResource();
        if ( resource instanceof IFile ) {
          localFile = ( IFile ) resource;
        }
      }
    } else {
      localFile = file;
    }
    
    String name = localFile.getName();
    IFile newFile = destination.getFile( new Path( name ) );
    InputStream contents = localFile.getContents();
    newFile.create( contents, IResource.NONE, null );
    
  }
  
  protected void copyResource( final IResource resource,
                               final IContainer destination )
      throws CoreException {
    if ( checkDestination( resource, destination ) ) {
      boolean performCopy = !checkExists( resource, destination );
      if ( !performCopy ) {
        // TODO mathias ask for overwriting existing resource
      }
      if ( performCopy ) {
        if ( resource instanceof IFile ) {
          copyFile( ( IFile ) resource, destination );
        } else {
          // TODO mathias copy also whole folders
        }
      }
    }
  }
  
  protected void copyResources( final IResource[] resources,
                                final IContainer destination )
      throws CoreException {
    for ( IResource resource : resources ) {
      copyResource( resource, destination );
    }
  }
  
  protected Object getData( final Object input ) {
    Object result = null;
    if ( input instanceof IResource ) {
      result = input;
    } else if ( input instanceof IGridElement ) {
      result = ( ( IGridElement ) input ).getResource();
    }
    return result;
  }
  
  protected Object[] getData( final Object[] input ) {
    List< IResource > resources = new ArrayList< IResource >();
    for ( Object obj : input ) {
      Object output = getData( obj );
      if ( output instanceof IResource ) {
        resources.add( ( IResource ) output ); 
      }
    }
    Object[] result = new Object[] {
      resources.toArray( new IResource[ resources.size() ] )
    };
    return result;
  }
  
  protected Transfer getDataType( final Object data ) {
    Transfer type = null;
    if ( data instanceof IResource[] ) {
      type = ResourceTransfer.getInstance();
    }
    return type;
  }
  
  protected Transfer[] getDataTypes( final Object[] data ) {
    Transfer[] dataTypes = new Transfer[ data.length ];
    for ( int i = 0 ; i < data.length ; i++ ) {
      dataTypes[i] = getDataType( data[i] );
    }
    return dataTypes;
  }
  
}
