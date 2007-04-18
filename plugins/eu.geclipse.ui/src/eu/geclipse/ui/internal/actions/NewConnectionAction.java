package eu.geclipse.ui.internal.actions;

import java.net.URL;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.BaseSelectionListenerAction;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.wizards.connection.ConnectionWizard;

public class NewConnectionAction
    extends BaseSelectionListenerAction {
  
  private IWorkbenchWindow workbenchWindow;
  
  protected NewConnectionAction( final IWorkbenchWindow workbenchWindow ) {
    super( "New Connection" );
    this.workbenchWindow = workbenchWindow;
    URL imgUrl = Activator.getDefault().getBundle().getEntry( "icons/etool16/newconn_wiz.gif" ); //$NON-NLS-1$
    setImageDescriptor( ImageDescriptor.createFromURL( imgUrl ) );
  }
  
  @Override
  public void run() {

    IWorkbench workbench = workbenchWindow.getWorkbench();
    IStructuredSelection selection = getStructuredSelection();
    Shell shell = workbenchWindow.getShell();
    
    ConnectionWizard wizard = new ConnectionWizard();
    wizard.init( workbench, selection );
    WizardDialog dialog = new WizardDialog( shell, wizard );
    
    dialog.open();
    
  }
  
}
