package eu.geclipse.info.model;

import java.net.URI;

import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridService;
import eu.geclipse.info.glue.GlueService;


public class GriaService extends GridGlueElement implements IGridService{

  public GriaService( final IGridContainer parent,
                      final GlueService glueService ) {
    super( parent, glueService );
  }

  public URI getURI() {
    // TODO Auto-generated method stub
    return null;
  }
  
  public GlueService getGlueService() {
    return ( GlueService ) getGlueElement();
  }
  
  public String getName() {
    return getGlueService().name; //$NON-NLS-1$
  }
}
