package eu.geclipse.ui.internal.actions;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.Action;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.IGridStorage;
import eu.geclipse.ui.internal.Activator;

public class MountAction extends Action {
  
  private String accessProtocol;
  
  private IGridStorage[] sources;
  
  protected MountAction( final IGridStorage[] sources,
                         final String protocol ) {
    super( protocol );
    this.accessProtocol = protocol;
    this.sources = sources;
  }
  
  @Override
  public void run() {
    for ( IGridStorage source : this.sources ) {
      try {
        createMount( source );
      } catch( CoreException cExc ) {
        Activator.logException( cExc );
      }
    }
  }
  
  protected InputStream createContents( final IGridStorage source ) {
    URI accessToken = findAccessToken( this.accessProtocol, source );
    String accessString = accessToken.toString() + '/';
    byte[] bytes = accessString.getBytes();
    ByteArrayInputStream biStream = new ByteArrayInputStream( bytes );
    return biStream;
  }
  
  protected void createMount( final IGridStorage source )
      throws CoreException {
    IFile mountFile = getMountFile( source );
    InputStream contents = createContents( source );
    mountFile.create( contents, true, null );
  }
  
  protected URI findAccessToken( final String protocol,
                                 final IGridStorage source ) {
    URI result = null;
    URI[] accessTokens = source.getAccessTokens();
    for ( URI token : accessTokens ) {
      if ( MountMenu.getProtocol( token ).equalsIgnoreCase( protocol ) ) {
        result = token;
        break;
      }
    }
    return result;
  }
  
  protected IFile getMountFile( final IGridStorage source ) {
    IFile result = null;
    IGridProject project = source.getProject();
    if ( project != null ) {
      String mountPointName = IGridProject.DIR_MOUNTS;
      IGridElement mountPoint = project.findChild( mountPointName );
      if ( mountPoint instanceof IGridContainer ) {
        IContainer container = ( IContainer ) mountPoint.getResource();
        if ( container != null ) {
          IPath filePath = new Path( getMountName( source ) );
          result = container.getFile( filePath );
        }
      }
    }
    return result;
  }
  
  protected String getMountName( final IGridStorage source ) {
    String[] parts = this.accessProtocol.split( ":" ); //$NON-NLS-1$
    String name = '.' + parts[0] + '.' + source.getName()
      + '.' + parts[1] + ".fs"; //$NON-NLS-1$
    return name;
  }
  
}
