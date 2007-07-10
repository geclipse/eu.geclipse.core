package eu.geclipse.core.internal.model;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.impl.AbstractGridElementCreator;

/**
 * Creator for local resources like files and folders.
 */
public class LocalResourceCreator
    extends AbstractGridElementCreator {

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElementCreator#canCreate(java.lang.Class)
   */
  public boolean canCreate( final Class<? extends IGridElement> elementType ) {
    return LocalFile.class.isAssignableFrom( elementType )
      || LocalFolder.class.isAssignableFrom( elementType );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElementCreator#create(eu.geclipse.core.model.IGridContainer)
   */
  public IGridElement create( final IGridContainer parent ) throws GridModelException {
    IGridElement result = null;
    Object obj = getObject();
    if ( isFile( obj ) ) {
      result = new LocalFile( ( IFile ) obj );
    } else if ( isFolder( obj ) ) {
      result = new LocalFolder( ( IFolder ) obj );
    }
    return result;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridElementCreator#internalCanCreate(java.lang.Object)
   */
  @Override
  protected boolean internalCanCreate( final Object fromObject ) {
    return isFile( fromObject ) || isFolder( fromObject );
  }
  
  /**
   * Determines if the specified object is an instanceof an
   * {@link IFile}.
   * 
   * @param object The object to be tested.
   * @return True if the object is an {@link IFile}.
   */
  private boolean isFile( final Object object ) {
    return ( object instanceof IFile );
  }
  
  /**
   * Determines if the specified object is an instanceof an
   * {@link IFolder}.
   * 
   * @param object The object to be tested.
   * @return True if the object is an {@link IFolder}.
   */
  private boolean isFolder( final Object object ) {
    return ( object instanceof IFolder );
  }
  
}
