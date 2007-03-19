package eu.geclipse.ui.providers;

import org.eclipse.jface.viewers.ITableLabelProvider;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridJob;
import eu.geclipse.core.model.IGridJobStatus;
import eu.geclipse.ui.views.GridJobView;

/**
 * This is a specialized {@link GridModelLabelProvider} to be used
 * by the {@link GridJobView}. It additionally implements the
 * {@link ITableLabelProvider} to give the user more information
 * about his jobs.
 */
public class JobViewLabelProvider
    extends ElementManagerLabelProvider {

  protected String getColumnText( final IGridElement element,
                                  final int columnIndex ) {
    String text = ""; //$NON-NLS-1$
    if ( element instanceof IGridJob ) {
      IGridJob job = ( IGridJob ) element;
      switch ( columnIndex ) {
        case 2:
          text = job.getID().getJobID();
          break;
        case 3:
          IGridJobStatus status = job.getStatus();
          text = status.getName();
          break;
      }
    }
    return text;
  }
  
}
