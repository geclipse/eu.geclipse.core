package eu.geclipse.core.model.impl;

import org.eclipse.core.resources.IFile;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridJobDescription;

public class JSDLJobDescriptionCreator extends AbstractFileElementCreator {

  public boolean canCreate( final Class< ? extends IGridElement > elementType ) {
    return IGridJobDescription.class.isAssignableFrom( elementType );
  }
  
  public IGridElement create( final IGridContainer parent ) throws GridModelException {
    IGridElement result = null;
    IFile file = ( IFile ) getObject();
    if ( file != null ) {
      result = new JSDLJobDescription( file );
    }
    return result;
  }
  
  @Override
  protected boolean internalCanCreate( final String fileExtension ) {
    return "jsdl".equalsIgnoreCase( fileExtension ); //$NON-NLS-1$
  }
  
}
