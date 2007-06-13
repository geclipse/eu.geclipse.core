package eu.geclipse.ui.wizards;

import java.net.URI;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import eu.geclipse.ui.internal.EmptySelection;


public class ConnectionWizard
    extends Wizard
    implements INewWizard {
  
  ConnectionLocationWizardPage fileCreationPage;
  
  private ISelection initialSelection;
    
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
    
    boolean result = false;
    URI uri = this.definitionPage.getURI();
    
    if ( uri != null ) {
    
      if ( this.fileCreationPage != null ) {
        result = createLocalConnection();
      } else {
        result = createGlobalConnection();
      }
    
    }
    
    return result;
    
  }

  public void init( final IWorkbench workbench,
                    final IStructuredSelection selection ) {
    this.initialSelection = selection;
    if ( this.initialSelection == null ) {
      this.initialSelection = new EmptySelection();
    }
  }
  
  private boolean createGlobalConnection() {
    boolean result = false;
    return result;
  }
  
  private boolean createLocalConnection() {
    
    boolean result = false;
    
    URI uri = this.definitionPage.getURI();
    this.fileCreationPage.setInitialContent( uri );
    
    if ( uri != null ) {
      IFile file = this.fileCreationPage.createNewFile();
      result = ( file != null ) && ( file.exists() );
    }
    
    return result;
    
  }
  
}
