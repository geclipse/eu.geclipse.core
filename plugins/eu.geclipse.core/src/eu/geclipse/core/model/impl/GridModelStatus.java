package eu.geclipse.core.model.impl;

import org.eclipse.core.runtime.Status;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.model.IGridModelStatus;

public class GridModelStatus
    extends Status
    implements IGridModelStatus {

  public GridModelStatus( final int severity,
                          final int code,
                          final String message,
                          final Throwable exception ) {
    super( severity, Activator.PLUGIN_ID, code, message, exception );
  }
  
}
