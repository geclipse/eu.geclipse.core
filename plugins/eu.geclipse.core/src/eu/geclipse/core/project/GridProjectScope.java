package eu.geclipse.core.project;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScope;

public class GridProjectScope implements IScope {

  public IEclipsePreferences create( final IEclipsePreferences parent,
                                     final String name ) {
    IEclipsePreferences node
      = ( IEclipsePreferences ) parent.node( name );
    return node;
  }
  
}
