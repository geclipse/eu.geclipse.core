package eu.geclipse.terminal.ssh.internal;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import eu.geclipse.core.model.IGridComputing;

public class OpenAction implements IObjectActionDelegate {

  private IGridComputing computingElement;

  public OpenAction() {
    // empty constructor
  }

  public void setActivePart( final IAction action, final IWorkbenchPart targetPart ) {
    // unused
  }

  public void run( final IAction action ) {
    if ( this.computingElement != null ) {
      SSHWizard wizard = new SSHWizard( this.computingElement.getHostName() );
      WizardDialog dialog = new WizardDialog( null, wizard );
      dialog.open();
    }
  }

  public void selectionChanged( final IAction action, final ISelection selection ) {
    this.computingElement = null;
    if( selection instanceof IStructuredSelection ) {
      IStructuredSelection sselection = ( IStructuredSelection )selection;
      if( sselection.getFirstElement() instanceof IGridComputing ) {
        this.computingElement = ( IGridComputing )sselection.getFirstElement();
      }
    }
  }
}
