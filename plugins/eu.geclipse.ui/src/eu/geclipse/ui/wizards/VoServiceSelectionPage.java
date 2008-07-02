/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributors:
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.wizards;

import java.net.URI;
import java.net.URL;
import java.util.Hashtable;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import eu.geclipse.core.Extensions;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridElementCreator;
import eu.geclipse.core.model.IGridService;
import eu.geclipse.core.model.impl.GenericVirtualOrganization;
import eu.geclipse.core.model.impl.GenericVoCreator;
import eu.geclipse.ui.dialogs.ServiceDialog;
import eu.geclipse.ui.internal.Activator;

public class VoServiceSelectionPage extends WizardPage {
  
  private static final String SERVICE_IMAGE = "icons/obj16/service_obj.gif"; //$NON-NLS-1$
  
  private static final String SERVICE_NEW_IMAGE = "icons/obj16/service_new_obj.gif"; //$NON-NLS-1$
  
  private static final String CONFIGURATION_DATA_KEY = "configuration"; //$NON-NLS-1$
  
  private static final String SERVICE_DATA_KEY = "service"; //$NON-NLS-1$
  
  private static final String URI_DATA_KEY = "uri"; //$NON-NLS-1$
  
  private static Hashtable< String, Image > images
    = new Hashtable< String, Image >();
  
  private GenericVirtualOrganization initialVo;
  
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
        
        IGridService service = ( IGridService ) item.getData( SERVICE_DATA_KEY );
        IConfigurationElement element = ( IConfigurationElement ) item.getData( CONFIGURATION_DATA_KEY );
        URI uri = ( URI ) item.getData( URI_DATA_KEY );
        
        if ( service != null ) {
          creator.maintainService( service );
        }
        
        else if ( ( element != null ) && ( uri != null ) ) {
          IGridElementCreator serviceCreator
            = ( IGridElementCreator ) element.createExecutableExtension( Extensions.GRID_ELEMENT_CREATOR_EXECUTABLE );
          creator.createService( serviceCreator, uri );
        }
        
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
    
    this.serviceTable = new Table( mainComp, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI | SWT.FULL_SELECTION );
    this.serviceTable.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
    this.serviceTable.setHeaderVisible( true );
    this.serviceTable.setLinesVisible( true );
    
    TableColumn typeColumn = new TableColumn( this.serviceTable, SWT.NULL );
    typeColumn.setText( "Type/Name" );
    typeColumn.setWidth( 150 );
    TableColumn urlColumn = new TableColumn( this.serviceTable, SWT.NULL );
    urlColumn.setText( "Endpoint" );
    urlColumn.setWidth( 300 );
    
    Composite buttonComp = new Composite( mainComp, SWT.NONE );
    buttonComp.setLayoutData( new GridData( SWT.BEGINNING, SWT.BEGINNING, false, false ) );
    buttonComp.setLayout( new GridLayout( 1, false ) );
    
    this.addButton = new Button( buttonComp, SWT.NONE );
    this.addButton.setText( "&Add..." );
    this.addButton.setLayoutData( new GridData( SWT.FILL, SWT.BEGINNING, true, false ) );
    
    this.removeButton = new Button( buttonComp, SWT.NONE );
    this.removeButton.setText( "&Remove" );
    this.removeButton.setLayoutData( new GridData( SWT.FILL, SWT.BEGINNING, true, false ) );
    
    this.serviceTable.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        updateUI();
      }
    } );
    
    this.addButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        showServiceDialog();
      }
    } );
    
    this.removeButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        removeSelectedServices();
      }
    } );
    
    if ( this.initialVo != null ) {
      initVo( this.initialVo );
    }
    
    updateUI();
    
    setControl( mainComp );
    
  }
  
  /**
   * Initializes the controls of this wizard page with the attributes
   * of the specified VO.
   * 
   * @param vo The VO whose attributes should be set to the page's controls.
   * @throws GridModelException If any error occurs.
   */
  protected void initVo( final GenericVirtualOrganization vo ) {
    
    try {
      
      IGridElement[] children = vo.getChildren( null );
      
      for ( IGridElement child : children ) {
        if ( child instanceof IGridService ) {
          IGridService service = ( IGridService ) child;
          TableItem item = new TableItem( this.serviceTable, SWT.NONE );
          item.setText( 0, service.getName() );
          item.setText( 1, service.getURI().toString() );
          item.setImage( getImage( SERVICE_IMAGE ) );
          item.setData( SERVICE_DATA_KEY, service );
        }
      }
      
    } catch ( GridModelException gmExc ) {
      Activator.logException( gmExc );
    }
    
  }
  
  protected void removeSelectedServices() {
    int[] indices = this.serviceTable.getSelectionIndices();
    this.serviceTable.remove( indices );
  }
  
  /**
   * Set the specified VO as initial VO. This means that the controls
   * of the page will be initialized with the attributes of the specified
   * VO.
   * 
   * @param vo The initial VO.
   */
  protected void setInitialVo( final GenericVirtualOrganization vo ) {
    this.initialVo = vo;
  }
  
  protected void showServiceDialog() {
    ServiceDialog dialog = new ServiceDialog( getShell() );
    if ( dialog.open() == Window.OK ) {
      IConfigurationElement selectedElement = dialog.getSelectedElement();
      URI selectedURI = dialog.getSelectedURI();
      TableItem item = new TableItem( this.serviceTable, SWT.NONE );
      item.setText( 0, selectedElement.getAttribute( Extensions.GRID_ELEMENT_CREATOR_NAME_ATTRIBUTE ) );
      item.setText( 1, selectedURI.toString() );
      item.setData( CONFIGURATION_DATA_KEY, selectedElement );
      item.setData( URI_DATA_KEY, selectedURI );
      item.setImage( getImage( SERVICE_NEW_IMAGE ) );
    }
  }
  
  protected static Image getImage( final String name ) {
    
    Image result = images.get( name );
    
    if ( result == null ) {
      URL url = Activator.getDefault().getBundle().getResource( name );
      ImageDescriptor desc = ImageDescriptor.createFromURL( url );
      result = desc.createImage();
      images.put( name, result );
    }
    
    return result;
    
  }
  
  protected void updateUI() {
    boolean selected = this.serviceTable.getSelectionCount() > 0;
    this.removeButton.setEnabled( selected );
  }

}
