package eu.geclipse.ui.dialogs;

import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import eu.geclipse.core.Extensions;
import eu.geclipse.core.model.IGridService;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.widgets.StoredCombo;

public class ServiceDialog
    extends TitleAreaDialog {
  
  /**
   * Key for the key store file preference.
   */
  private static String SERVICE_URI_ID = "vo_service_uri";  //$NON-NLS-1$
  
  private Combo typeCombo;
  
  private StoredCombo uriCombo;
  
  private Hashtable< String, IConfigurationElement > configs;
  
  private URI selectedURI;
  
  private IConfigurationElement selectedElement;
  
  public ServiceDialog( final Shell parentShell ) {
    super( parentShell );
    URL imgURL = Activator.getDefault().getBundle().getEntry( "icons/wizban/service_wiz.gif" ); //$NON-NLS-1$
    setTitleImage( ImageDescriptor.createFromURL(imgURL).createImage() );
  }
  
  public IConfigurationElement getSelectedElement() {
    return this.selectedElement;
  }
  
  public URI getSelectedURI() {
    return this.selectedURI;
  }
  
  @Override
  protected void okPressed() {
    try {
      this.selectedElement = this.configs.get( this.typeCombo.getText() );
      this.selectedURI = new URI( this.uriCombo.getText() );
      super.okPressed();
    } catch ( Exception exc ) {
      setErrorMessage( exc.getLocalizedMessage() );
    }
  }
  
  protected Control createDialogArea( final Composite parent ) {
    
    IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
    
    Composite mainComp = ( Composite ) super.createDialogArea( parent );
    
    mainComp.setLayout( new GridLayout( 1, false ) );
    
    GridData gData;
    
    getShell().setText( "Add service" );
    
    Label typeLabel = new Label( mainComp, SWT.NONE );
    typeLabel.setText( "Service &Type:" );
    gData = new GridData();
    typeLabel.setLayoutData( gData );
    
    this.typeCombo = new Combo( mainComp, SWT.READ_ONLY );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    this.typeCombo.setLayoutData( gData );
    
    Label urlLabel = new Label( mainComp, SWT.NONE );
    urlLabel.setText( "Service &URI:" );
    gData = new GridData();
    urlLabel.setLayoutData( gData );
    
    this.uriCombo = new StoredCombo( mainComp, SWT.NONE );
    this.uriCombo.setPreferences( prefStore, SERVICE_URI_ID );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    this.uriCombo.setLayoutData( gData );

    setTitle( "Add service" );
    setMessage( "Specify a service to be added to your VO." );
    
    initServiceTypeCombo();
    
    return mainComp;
    
  }
  
  private void initServiceTypeCombo() {
    
    List< IConfigurationElement > elements
      = Extensions.getRegisteredElementCreatorConfigurations( URI.class, IGridService.class );
    this.configs = new Hashtable< String, IConfigurationElement >();
    
    for ( IConfigurationElement element : elements ) {
      String name
        = element.getAttribute( Extensions.GRID_ELEMENT_CREATOR_NAME_ATTRIBUTE );
      this.configs.put( name, element );
    }
    
    String[] names = this.configs.keySet().toArray( new String[ this.configs.size() ] );
    Arrays.sort( names );
    this.typeCombo.setItems( names );
    
    if ( names.length > 0 ) {
      this.typeCombo.setText( names[ 0 ] );
    }
       
  }

}
