package eu.geclipse.ui.internal.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import eu.geclipse.ui.internal.wizards.ConfigurationWizard;

public class ConfigurationAction implements IWorkbenchWindowActionDelegate {

  private Shell shell;
  
  public void dispose() {
    // TODO Auto-generated method stub
  }

  public void init( final IWorkbenchWindow window ) {
    this.shell = window.getShell();
  }

  public void run( final IAction action ) {
    if ( this.shell != null ) {
      Wizard wizard = new ConfigurationWizard();
      WizardDialog dialog = new WizardDialog( this.shell, wizard );
      dialog.open();
    }
  }

  public void selectionChanged( final IAction action, final ISelection selection ) {
    // TODO Auto-generated method stub
  }

}
