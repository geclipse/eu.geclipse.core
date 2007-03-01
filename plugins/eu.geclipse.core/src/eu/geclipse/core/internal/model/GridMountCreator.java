package eu.geclipse.core.internal.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridMount;
import eu.geclipse.core.model.impl.AbstractGridElementCreator;

public class GridMountCreator extends AbstractGridElementCreator {

  public boolean canCreate( final Class< ? extends IGridElement > elementType ) {
    return IGridMount.class.isAssignableFrom( elementType );
  }
  
  public IGridElement create( final IGridContainer parent )
      throws GridModelException {
    return createGridMount( parent, ( IFile ) getObject() );
  }
  
  protected IGridElement createGridMount( final IGridContainer parent,
                                          final IFile fsFile ) {
    
    IFileStore fileStore = null;
    try {
      InputStream contents = fsFile.getContents();
      InputStreamReader isReader = new InputStreamReader( contents );
      BufferedReader bReader = new BufferedReader( isReader );
      String line = bReader.readLine();
      if ( line != null ) {
        URI uri = new URI( line.trim() );
        IFileSystem fileSystem = EFS.getFileSystem( uri.getScheme() );
        fileStore = fileSystem.getStore( uri );
      }
      bReader.close();
    } catch ( IOException ioExc ) {
      // Nothing to do, just catch and leave GridMount invalid
    } catch( CoreException cExc ) {
      // Nothing to do, just catch and leave GridMount invalid
    } catch( URISyntaxException uriExc ) {
      // Nothing to do, just catch and leave GridMount invalid
    } catch( NullPointerException nullExc ){
      //katis: This prevents GridProjectView from crashing when 
      // there is malformed URI in .name.fs file
      eu.geclipse.core.internal.Activator.logException( nullExc );
    }
    
    return new GridMount( parent, fileStore, fsFile );
    
  }
  
  @Override
  protected boolean internalCanCreate( final Object fromObject ) {
    return isFsFile( fromObject );
  }
  
  private boolean isFsFile( final Object object ) {
    return ( object instanceof IFile )
      && ( ( IFile ) object ).getName().startsWith( "." ) //$NON-NLS-1$
      && ( ( IFile ) object ).getName().endsWith( ".fs" );  //$NON-NLS-1$
  }
  
}
