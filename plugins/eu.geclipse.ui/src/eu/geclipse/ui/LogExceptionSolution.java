package eu.geclipse.ui;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import eu.geclipse.ui.internal.Activator;

public class LogExceptionSolution
    extends UISolution {
  
  private static final String LOG_VIEW_ID
    = "org.eclipse.pde.runtime.LogView"; //$NON-NLS-1$
  
  private Throwable exc;

  public LogExceptionSolution( final Shell shell,
                               final Throwable exc ) {
    super( UISolutionRegistry.LOG_EXCEPTION, "Log exception", shell );
    this.exc = exc;
  }

  @Override
  public void solve() {
    Activator.logException( this.exc );
    IWorkbench workbench
      = PlatformUI.getWorkbench();
    IWorkbenchWindow window
      = workbench.getActiveWorkbenchWindow();
    IWorkbenchPage page
      = window.getActivePage();
    try {
      page.showView( LOG_VIEW_ID );
    } catch( PartInitException piExc ) {
      Activator.logException( piExc );
    }
  }
  
}
