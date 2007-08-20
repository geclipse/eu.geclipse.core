package eu.geclipse.ui.internal.wizards;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;

import eu.geclipse.core.ExtensionManager;
import eu.geclipse.core.Extensions;
import eu.geclipse.core.auth.ICaCertificateLoader;
import eu.geclipse.ui.internal.Activator;

public class CertificateLoaderSelectionPage extends WizardPage {
  
  protected List list;
  
  private CaCertificateImportWizard.ImportMethod importMethod;
  
  public CertificateLoaderSelectionPage( final CaCertificateImportWizard.ImportMethod importMethod ) {
    super( "certLoaderPage",
           "Certificate Loader",
           null );
    setDescription( "Choose one of the available Certificate Loaders" );
    this.importMethod = importMethod;
  }

  public void createControl( final Composite parent ) {
    
    GridData gData;
    
    Composite mainComp = new Composite( parent, SWT.NULL );
    mainComp.setLayout( new GridLayout( 1, false ) );
    gData = new GridData( GridData.FILL_BOTH );
    mainComp.setLayoutData( gData );
    
    this.list = new List( mainComp, SWT.BORDER | SWT.SINGLE );
    gData = new GridData( GridData.FILL_BOTH );
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    this.list.setLayoutData( gData );
    
    this.list.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        setPageComplete( CertificateLoaderSelectionPage.this.list.getSelectionCount() > 0 );
      }
    } );
    
    setControl( mainComp );
    
    populateList();
    setPageComplete( false );
    
  }
  
  public ICaCertificateLoader getSelectedLoader() {
    
    ICaCertificateLoader result = null;
    
    String[] selection = this.list.getSelection();
    if ( ( selection != null ) && ( selection.length > 0 ) ) {
      
      ExtensionManager manager = new ExtensionManager();
      java.util.List< IConfigurationElement > elements
        = manager.getConfigurationElements(
            Extensions.CA_CERT_LOADER_POINT, 
            Extensions.CA_CERT_LOADER_ELEMENT
        );
      
      for ( IConfigurationElement element : elements ) {
        String name = element.getAttribute( Extensions.CA_CERT_LOADER_NAME_ATTRIBUTE );
        if ( selection[ 0 ].equals( name ) ) {
          try {
            result = ( ICaCertificateLoader ) element.createExecutableExtension( Extensions.CA_CERT_LOADER_CLASS_ATTRIBUTE );
          } catch ( CoreException cExc ) {
            Activator.logException( cExc );
          }
          break;
        }
      }
      
    }
    
    return result;
    
  }
  
  protected void populateList() {
    
    this.list.removeAll();
    
    ExtensionManager manager = new ExtensionManager();
    java.util.List< IConfigurationElement > elements
      = manager.getConfigurationElements(
          Extensions.CA_CERT_LOADER_POINT, 
          Extensions.CA_CERT_LOADER_ELEMENT
      );

    for ( IConfigurationElement element : elements ) {
      
      boolean methodSupported = false;
      
      if ( this.importMethod == CaCertificateImportWizard.ImportMethod.LOCAL ) {
        String fromLocal = element.getAttribute( Extensions.CA_CERT_LOADER_FROM_LOCAL_ATTRIBUTE );
        methodSupported = Boolean.parseBoolean( fromLocal );
      } else if ( this.importMethod == CaCertificateImportWizard.ImportMethod.REMOTE ) {
        String fromRemote = element.getAttribute( Extensions.CA_CERT_LOADER_FROM_REMOTE_ATTRIBUTE );
        methodSupported = Boolean.parseBoolean( fromRemote );
      }
      
      if ( methodSupported ) {
        String name = element.getAttribute( Extensions.CA_CERT_LOADER_NAME_ATTRIBUTE );
        this.list.add( name );
      }
      
    }
    
  }
  
}
