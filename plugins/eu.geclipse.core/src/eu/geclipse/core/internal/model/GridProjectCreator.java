package eu.geclipse.core.internal.model;

import org.eclipse.core.resources.IProject;

import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridElementCreator;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.impl.AbstractGridElementCreator;

/**
 * Implementation of the {@link IGridElementCreator} interface
 * for projects in the workspace. These projects have not
 * to be grid projects.
 */
public class GridProjectCreator
    extends AbstractGridElementCreator {

  public boolean canCreate( final Class< ? extends IGridElement > elementType ) {
    return IGridProject.class.isAssignableFrom( elementType );
  }
  
  public IGridElement create( final IGridContainer parent ) {
    
    IGridProject result = null;
    IProject project = ( IProject ) getObject();
    String name = project.getName();
    
    if ( name.equals( HiddenProject.NAME ) ) {
      try {
        result = HiddenProject.getInstance( project );
      } catch ( GridModelException gmExc ) {
        Activator.logException( gmExc );
      }
    } else {
      result = new GridProject( project );
    }
    
    return result;
    
  }
  
  @Override
  public boolean internalCanCreate( final Object object ) {
    /*boolean result = false;
    if ( object instanceof IProject ) {
      IProject project = ( IProject ) object;
      String name = project.getName();
      result = ! name.startsWith( "." ); //$NON-NLS-1$
    }
    return result;*/
    return ( object instanceof IProject );
  }

}
