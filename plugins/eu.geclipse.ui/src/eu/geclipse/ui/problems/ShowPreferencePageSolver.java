package eu.geclipse.ui.problems;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PreferencesUtil;

import eu.geclipse.core.reporting.IConfigurableSolver;

public class ShowPreferencePageSolver
    implements IConfigurableSolver {
  
  public static final String PREFERENCE_PAGE_ID_ATTRIBUTE
    = "pageID"; //$NON-NLS-1$
  
  private String preferencePageID;
  
  public void setInitializationData( final IConfigurationElement config,
                                     final String propertyName,
                                     final Object data ) {
    this.preferencePageID = config.getAttribute( PREFERENCE_PAGE_ID_ATTRIBUTE );
  }

  public void solve() {
    PreferenceDialog dialog
      = PreferencesUtil.createPreferenceDialogOn( getShell(),
                                                  this.preferencePageID,
                                                  null,
                                                  null );
    dialog.getShell().forceActive();
    dialog.open();
  }
  
  protected Shell getShell() {
    
    Shell result = null;
    
    IWorkbench workbench = PlatformUI.getWorkbench();
    IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
    
    if ( window != null ) { 
      result = window.getShell();
    }

    return result;
    
  }

}
