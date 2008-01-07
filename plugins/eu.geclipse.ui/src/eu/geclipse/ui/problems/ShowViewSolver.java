package eu.geclipse.ui.problems;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.core.reporting.IConfigurableSolver;
import eu.geclipse.ui.internal.Activator;

public class ShowViewSolver
    implements IConfigurableSolver {
  
  public static final String VIEW_ID_ATTRIBUTE
    = "viewID"; //$NON-NLS-1$
  
  private String viewID;
  
  public void setInitializationData( final IConfigurationElement config,
                                     final String propertyName,
                                     final Object data)
      throws CoreException {
    this.viewID = config.getAttribute( VIEW_ID_ATTRIBUTE );
  }

  public void solve() {
    IWorkbench workbench = PlatformUI.getWorkbench();
    IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
    IWorkbenchPage page = window.getActivePage();
    try {
      page.showView( this.viewID );
    } catch( PartInitException piExc ) {
      Activator.logException( piExc );
    }
  }

}
