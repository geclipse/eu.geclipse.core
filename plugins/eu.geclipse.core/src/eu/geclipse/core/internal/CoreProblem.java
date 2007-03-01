package eu.geclipse.core.internal;

import eu.geclipse.core.AbstractProblem;

public class CoreProblem extends AbstractProblem {

  public CoreProblem( final int id,
                      final String text ) {
    super( id, text );
  }
  
  public CoreProblem( final int id,
                      final String text,
                      final Throwable exception ) {
    super( id, text, exception );
  }

  @Override
  protected String getPluginID() {
    return Activator.PLUGIN_ID;
  }

}
