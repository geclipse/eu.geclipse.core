package eu.geclipse.ui.problems;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.core.reporting.IConfigurableSolver;

public class ShowWizardPageSolver
    implements IConfigurableSolver {
  
  public static final String PAGE_ID_ATTRIBUTE
    = "pageID"; //$NON-NLS-1$
  
  private String pageID;

  public void setInitializationData( final IConfigurationElement config,
                                     final String propertyName,
                                     final Object data )
      throws CoreException {
    this.pageID = config.getAttribute( PAGE_ID_ATTRIBUTE );
  }
  
  public void solve() throws InvocationTargetException {
    // TODO mathias
  }

}
