package eu.geclipse.ui.wizards;

import java.net.URI;
import java.net.URL;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import eu.geclipse.core.Extensions;
import eu.geclipse.core.model.IGridElementCreator;
import eu.geclipse.core.model.impl.GenericVoCreator;
import eu.geclipse.ui.dialogs.ServiceDialog;
import eu.geclipse.ui.internal.Activator;

public class VoServiceSelectionPage extends WizardPage {
  
  private Table serviceTable;
  
  private Button addButton;
  
  private Button removeButton;
  
  public VoServiceSelectionPage() {
    super( "voServiceSelectionPage", //$NON-NLS-1$
           "Service Selection",
           null );
    setDescription( "Add services to your VO" );
    URL imgUrl = Activator.getDefault().getBundle().getEntry( "icons/wizban/vo_wiz.gif" ); //$NON-NLS-1$
    setImageDescriptor( ImageDescriptor.createFromURL( imgUrl ) );
  }
  
  public IStatus apply( final GenericVoCreator creator ) {
    
    IStatus result = Status.OK_STATUS;
    
    TableItem[] items = this.serviceTable.getItems();
    for ( TableItem item : items ) {
      try {
        IConfigurationElement element = ( IConfigurationElement ) item.getData( "configuration" );
        URI uri = ( URI ) item.getData( "uri" );
        IGridElementCreator serviceCreator
          = ( IGridElementCreator ) element.createExecutableExtension( Extensions.GRID_ELEMENT_CREATOR_EXECUTABLE );
        creator.addServiceCreator( serviceCreator, uri );
      } catch ( Exception exc ) {
        result = new Status( IStatus.ERROR, Activator.PLUGIN_ID, exc.getLocalizedMessage(), exc );
        break;
      }
    }
    
    return result;
    
  }

  public void createControl( final Composite parent ) {
    
    Composite mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
    mainComp.setLayout( new GridLayout( 2, false ) );
    
    Label label = new Label( mainComp, SWT.NONE );
    label.setText( "Services" );
    GridData labelData = new GridData( SWT.BEGINNING, SWT.CENTER, false, false );
    labelData.horizontalSpan = 2;
    label.setLayoutData( labelData );
    
    this.serviceTable = new Table( mainComp, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI );
    this.serviceTable.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
    this.serviceTable.setHeaderVisible( true );
    this.serviceTable.setLinesVisible( true );
    
    TableColumn typeColumn = new TableColumn( this.serviceTable, SWT.NULL );
    typeColumn.setText( "Type" );
    typeColumn.setWidth( 100 );
    TableColumn urlColumn = new TableColumn( this.serviceTable, SWT.NULL );
    urlColumn.setText( "Endpoint" );
    urlColumn.setWidth( 100 );
    
    Composite buttonComp = new Composite( mainComp, SWT.NONE );
    buttonComp.setLayoutData( new GridData( SWT.BEGINNING, SWT.BEGINNING, false, false ) );
    buttonComp.setLayout( new GridLayout( 1, false ) );
    
    this.addButton = new Button( buttonComp, SWT.NONE );
    this.addButton.setText( "&Add..." );
    this.addButton.setLayoutData( new GridData( SWT.FILL, SWT.BEGINNING, true, false ) );
    
    this.removeButton = new Button( buttonComp, SWT.NONE );
    this.removeButton.setText( "&Remove" );
    this.removeButton.setLayoutData( new GridData( SWT.FILL, SWT.BEGINNING, true, false ) );
    
    this.addButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        showServiceDialog();
      }
    } );
    
    setControl( mainComp );
    
  }
  
  protected void showServiceDialog() {
    ServiceDialog dialog = new ServiceDialog( getShell() );
    if ( dialog.open() == Window.OK ) {
      IConfigurationElement selectedElement = dialog.getSelectedElement();
      URI selectedURI = dialog.getSelectedURI();
      TableItem item = new TableItem( this.serviceTable, SWT.NONE );
      item.setText( 0, selectedElement.getAttribute( Extensions.GRID_ELEMENT_CREATOR_NAME_ATTRIBUTE ) );
      item.setText( 1, selectedURI.toString() );
      item.setData( "configuration", selectedElement );
      item.setData( "uri", selectedURI );
    }
  }

}
