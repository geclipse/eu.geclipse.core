package eu.geclipse.core.model.impl;

import org.eclipse.core.resources.IFile;


public abstract class AbstractFileElementCreator
    extends AbstractGridElementCreator {

  @Override
  protected boolean internalCanCreate( final Object fromObject ) {
    boolean result = false;
    if ( fromObject instanceof IFile ) {
      String fileExtension
        = ( ( IFile ) fromObject ).getLocation().getFileExtension();
      result = internalCanCreate( fileExtension );
    }
    return result;
  }
  
  protected abstract boolean internalCanCreate( final String fileExtension );
  
}
