package eu.geclipse.ui.internal.wizards;

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;

import eu.geclipse.core.ExtensionManager;
import eu.geclipse.core.Extensions;
import eu.geclipse.core.security.ICertificateLoader;
import eu.geclipse.ui.widgets.StoredCombo;

public class CertificateLoaderSelectionPage extends WizardPage {
  
  private StoredCombo uriCombo;
  
  private TreeViewer certLoaderViewer;
  
  private Text descriptionText;

  public CertificateLoaderSelectionPage() {
    super( "certLoaderPage", //$NON-NLS-1$
           "Certificate Repository",
           null );
    setDescription( "Select one of the available repositories" );
  }
  
  public void createControl( final Composite parent ) {
    
    GridData gData;
    
    Composite mainComp = new Composite( parent, SWT.NULL );
    mainComp.setLayout( new GridLayout( 2, false ) );
    gData = new GridData( GridData.FILL_BOTH );
    mainComp.setLayoutData( gData );
    
    Label uriLabel = new Label( mainComp, SWT.NONE );
    uriLabel.setText( "URI:" );
    uriLabel.setLayoutData( new GridData() );
    
    this.uriCombo = new StoredCombo( mainComp, SWT.NONE );
    this.uriCombo.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false ) );
    
    Label repoLabel = new Label( mainComp, SWT.NONE );
    repoLabel.setText( "Certificate repositories:" );
    gData = new GridData();
    gData.horizontalSpan = 2;
    repoLabel.setLayoutData( gData );
    
    this.certLoaderViewer = new TreeViewer( mainComp, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.SINGLE );
    gData = new GridData( GridData.FILL, GridData.FILL, true, true );
    gData.horizontalSpan = 2;
    gData.heightHint = 150;
    certLoaderViewer.getTree().setLayoutData( gData );
    
    certLoaderViewer.setContentProvider( new CertificateLoaderContentProvider() );
    certLoaderViewer.setLabelProvider( new CertificateLoaderLabelProvider() );
    ExtensionManager manager = new ExtensionManager();
    certLoaderViewer.setInput(
      manager.getConfigurationElements(
        Extensions.CERT_LOADER_POINT,
        Extensions.CERT_LOADER_ELEMENT
      )
    );
    
    Label descLabel = new Label( mainComp, SWT.NONE );
    descLabel.setText( "Description:" );
    gData = new GridData();
    gData.horizontalSpan = 2;
    descLabel.setLayoutData( gData );
    
    this.descriptionText = new Text( mainComp, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI | SWT.READ_ONLY );
    gData = new GridData( GridData.FILL, GridData.CENTER, true, false );
    gData.horizontalSpan = 2;
    gData.heightHint = 50;
    this.descriptionText.setLayoutData( gData );
    
    certLoaderViewer.addSelectionChangedListener( new ISelectionChangedListener() {
      public void selectionChanged( final SelectionChangedEvent event ) {
        setSelection( event.getSelection() );
      }
    } );
    this.uriCombo.addModifyListener( new ModifyListener() {
      public void modifyText( final ModifyEvent e ) {
        validatePage();
      }
    } );
    
    certLoaderViewer.expandAll();
    TreeItem[] items = certLoaderViewer.getTree().getItems();
    while ( ( items != null ) && ( items.length > 0 ) ) {
      Object data = items[ 0 ].getData();
      if ( ( data instanceof IConfigurationElement )
          && Extensions.CERT_LOADER_DISTRIBUTION_ELEMENT.equals( ( ( IConfigurationElement ) data ).getName() ) ) {
        certLoaderViewer.setSelection( new StructuredSelection( data ) );
        break;
      }
      items = items[ 0 ].getItems();
    }
    
    validatePage();    
    setControl( mainComp );
    
  }
  
  public ICertificateLoader getSelectedLoader() {
    
    ICertificateLoader result = null;
    
    ISelection selection = this.certLoaderViewer.getSelection();
    IConfigurationElement element = null;
    if ( selection instanceof IStructuredSelection ) {
      Object object = ( ( IStructuredSelection ) selection ).getFirstElement();
      while ( ( object != null ) && ( object instanceof IConfigurationElement ) ) {
        element = ( IConfigurationElement ) object;
        object = element.getParent();
      }
    }
    
    if ( ( element != null ) && Extensions.CERT_LOADER_ELEMENT.equals( element.getName() ) ) {
      try {
        result = ( ICertificateLoader ) element.createExecutableExtension( Extensions.CERT_LOADER_CLASS_ATTRIBUTE );
      } catch ( CoreException cExc ) {
        setErrorMessage( "No valid certificate loader found: " + cExc.getLocalizedMessage() );
      }
    } else {
      setErrorMessage( "No valid certificate loader found" );
    }
    
    return result;
    
  }
  
  public URI getURI() {
    
    URI result = null;
    String text = this.uriCombo.getText().trim();
    
    if ( text.length() > 0 ) {    
      try {
        result = new URI( text );
        setErrorMessage( null );
        setDescription( "Select one of the available repositories" );
      } catch ( URISyntaxException uriExc ) {
        setErrorMessage( "Invalid URI: " + uriExc.getLocalizedMessage() );
      }
    }
    
    return result;
    
  }
  
  protected void setSelection( final ISelection selection ) {
    if ( selection instanceof IStructuredSelection ) {
      Object element = ( ( IStructuredSelection ) selection ).getFirstElement();
      if ( element instanceof IConfigurationElement ) {
        setSelection( ( IConfigurationElement ) element );
      }
    }
  }
  
  protected void validatePage() {
    URI uri = getURI();
    ICertificateLoader loader = getSelectedLoader();
    setPageComplete( ( uri != null ) && ( loader != null ) );
  }
  
  private void setSelection( final IConfigurationElement element ) {
    
    String url = ""; //$NON-NLS-1$
    String description = ""; //$NON-NLS-1$
    String name = element.getName();
    
    if ( Extensions.CERT_LOADER_AUTHORITY_ELEMENT.equals( name ) ) {
      description = element.getAttribute( Extensions.CERT_LOADER_AUTHORITY_DESCRIPTION_ATTRIBUTE );
    } else if ( Extensions.CERT_LOADER_DISTRIBUTION_ELEMENT.equals( name ) ) {
      url = element.getAttribute( Extensions.CERT_LOADER_DISTRIBUTION_URL_ATTRIBUTE );
      description = element.getAttribute( Extensions.CERT_LOADER_DISTRIBUTION_DESCRIPTION_ATTRIBUTE );
    }
    
    this.uriCombo.setText( url );
    this.descriptionText.setText( description );
    
  }

}
