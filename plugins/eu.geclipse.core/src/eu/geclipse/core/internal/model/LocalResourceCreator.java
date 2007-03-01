package eu.geclipse.core.internal.model;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.impl.AbstractGridElementCreator;

public class LocalResourceCreator extends AbstractGridElementCreator {

  public boolean canCreate( final Class<? extends IGridElement> elementType ) {
    return LocalResource.class.isAssignableFrom( elementType );
  }

  public IGridElement create( final IGridContainer parent ) throws GridModelException {
    IGridElement result = null;
    Object obj = getObject();
    if ( isFile( obj ) ) {
      result = new LocalFile( parent, ( IFile ) obj );
    } else if ( isFolder( obj ) ) {
      result = new LocalFolder( parent, ( IFolder ) obj );
    }
    return result;
  }
  
  @Override
  protected boolean internalCanCreate( final Object fromObject ) {
    return ( isFile( fromObject ) || isFolder( fromObject ) )
    && isVisible( ( IResource ) fromObject );
  }
  
  private boolean isFile( final Object object ) {
    return ( object instanceof IFile );
  }
  
  private boolean isFolder( final Object object ) {
    return ( object instanceof IFolder );
  }
  
  private boolean isVisible( final IResource resource ) {
    return !resource.getName().startsWith( "." ); //$NON-NLS-1$
  }
  
}
