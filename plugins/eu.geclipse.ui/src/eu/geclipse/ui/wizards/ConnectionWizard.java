package eu.geclipse.ui.wizards;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import eu.geclipse.ui.internal.EmptySelection;


public class ConnectionWizard
    extends Wizard
    implements INewWizard {
  
  private ISelection initialSelection;
  
  private ConnectionLocationWizardPage fileCreationPage;
  
  private ConnectionDefinitionWizardPage definitionPage;
  
  public ConnectionWizard() {
    setNeedsProgressMonitor( true );
  }
  
  @Override
  public void addPages() {
    
    if ( this.initialSelection instanceof IStructuredSelection ) {
      this.fileCreationPage
        = new ConnectionLocationWizardPage( "newGridConnectionLocationPage",
                                            ( IStructuredSelection ) this.initialSelection );
      this.fileCreationPage.setTitle( "Connection location" );
      this.fileCreationPage.setDescription( "Choose the location in the workspace where the new connection will be created" );
      addPage( this.fileCreationPage );
    }
    
    this.definitionPage = new ConnectionDefinitionWizardPage();
    addPage( this.definitionPage );
    
  }

  @Override
  public String getWindowTitle() {
    return "New Filesystem Connection";
  }
  
  @Override
  public boolean performFinish() {
    // TODO Auto-generated method stub
    return false;
  }

  public void init( final IWorkbench workbench,
                    final IStructuredSelection selection ) {
    this.initialSelection = selection;
    if ( this.initialSelection == null ) {
      this.initialSelection = new EmptySelection();
    }
  }
  
}
