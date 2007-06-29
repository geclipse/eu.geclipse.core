package eu.geclipse.ui.wizards;

import java.net.URI;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import eu.geclipse.core.model.IGridConnection;


public class ConnectionWizard
    extends Wizard
    implements INewWizard {
  
  protected static final String CONNECTION_PREFIX = "."; //$NON-NLS-1$
  
  protected static final String CONNECTION_SUFFIX = ".fs"; //$NON-NLS-1$
  
  IWizardPage firstPage;
  
  private boolean createGlobalConnection;
  
  private ISelection initialSelection;
    
  private ConnectionDefinitionWizardPage definitionPage;
  
  public ConnectionWizard() {
    this( false );
  }
  
  public ConnectionWizard( final boolean createGlobalConnection ) {
    setNeedsProgressMonitor( true );
    this.createGlobalConnection = createGlobalConnection;
  }
  
  @Override
  public void addPages() {
    
    if ( this.createGlobalConnection ) {
      this.firstPage = new ConnectionNameWizardPage();
    } else {
      this.firstPage
        = new ConnectionLocationWizardPage( Messages.getString("ConnectionWizard.location_page_name"), //$NON-NLS-1$
                                            ( IStructuredSelection ) this.initialSelection );
      this.firstPage.setTitle( Messages.getString("ConnectionWizard.location_page_title") ); //$NON-NLS-1$
      this.firstPage.setDescription( Messages.getString("ConnectionWizard.location_page_description") ); //$NON-NLS-1$
    }
    addPage( this.firstPage );
    
    this.definitionPage = new ConnectionDefinitionWizardPage();
    addPage( this.definitionPage );
    
  }
  
  @Override
  public boolean canFinish() {
    
    IWizardContainer container = getContainer();
    IWizardPage currentPage = container.getCurrentPage();
    
    return ( currentPage != this.firstPage ) && super.canFinish();
    
  }

  @Override
  public String getWindowTitle() {
    return Messages.getString("ConnectionWizard.window_title"); //$NON-NLS-1$
  }
  
  @Override
  public boolean performFinish() {
    
    boolean result = false;
    URI uri = this.definitionPage.getURI();
    
    if ( uri != null ) {
    
      if ( this.createGlobalConnection ) {
        result = createGlobalConnection();
      } else {
        result = createLocalConnection();
      }
    
    }
    
    return result;
    
  }

  public void init( final IWorkbench workbench,
                    final IStructuredSelection selection ) {
    this.initialSelection = selection;
  }
  
  private boolean createGlobalConnection() {
    
    boolean result = false;
    
    ConnectionNameWizardPage page
      = ( ConnectionNameWizardPage ) this.firstPage;
    
    URI uri = this.definitionPage.getURI();

    if ( uri != null ) {
      page.setInitialContent( uri );
      IGridConnection connection = page.createNewConnection();
      result = connection != null;
    }
    
    if ( ! result ) {
      setCurrentErrorMessage( page );
    }
    
    return result;
    
  }
  
  private boolean createLocalConnection() {
    
    boolean result = false;

    ConnectionLocationWizardPage page
      = ( ConnectionLocationWizardPage ) this.firstPage;
    
    URI uri = this.definitionPage.getURI();
    
    if ( uri != null ) {
      page.setInitialContent( uri );
      IFile file = page.createNewFile();
      result = ( file != null ) && file.exists();
    }
    
    if ( ! result ) {
      setCurrentErrorMessage( page );
    }
    
    return result;
    
  }
  
  private void setCurrentErrorMessage( final IWizardPage fromPage ) {
    String errorMessage = fromPage.getErrorMessage();
    WizardPage toPage = ( WizardPage ) getContainer().getCurrentPage();
    toPage.setErrorMessage( errorMessage );
  }
  
}
