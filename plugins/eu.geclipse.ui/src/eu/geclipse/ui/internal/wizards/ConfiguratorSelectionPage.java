package eu.geclipse.ui.internal.wizards;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import eu.geclipse.core.ExtensionManager;

public class ConfiguratorSelectionPage extends WizardPage {
  
  protected Tree confTree;
  
  private ConfigurationDetailsComposite detailsFolder;
  
  public ConfiguratorSelectionPage() {
    super( "confSelectPage", //$NON-NLS-1$
           "Configurators",
           null );
    setDescription( "Select one of the available configurators" );
  }
  
  public void createControl( final Composite parent ) {
    
    GridData gData;
    
    SashForm mainComp = new SashForm( parent, SWT.HORIZONTAL );
    mainComp.setLayout( new FillLayout() );
    mainComp.setLayoutData( new GridData( GridData.FILL_BOTH ) );
    
    Group selectionGroup = new Group( mainComp, SWT.NONE );
    selectionGroup.setLayout( new GridLayout( 1, false ) );
    selectionGroup.setText( "Available Configuration" );
    
    this.confTree = new Tree( selectionGroup, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.MULTI );
    gData = new GridData( GridData.FILL, GridData.FILL, true, true );
    gData.widthHint = 150;
    this.confTree.setLayoutData( gData );
    
    Group detailsGroup = new Group( mainComp, SWT.NONE );
    detailsGroup.setLayout( new GridLayout( 1, false ) );
    detailsGroup.setText( "Configuration Details" );
    
    this.detailsFolder = new ConfigurationDetailsComposite( detailsGroup, SWT.NONE );
    detailsFolder.setLayoutData( new GridData ( GridData.FILL, GridData.FILL, true, true ) );
    
    mainComp.setWeights( new int[] { 1, 2 } );
    
    this.confTree.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        updateDetails();
        validatePage();
      }
    } );
    this.confTree.addMouseListener( new MouseAdapter() {
      @Override
      public void mouseDoubleClick( final MouseEvent e ) {
        TreeItem[] selection = ConfiguratorSelectionPage.this.confTree.getSelection();
        if ( ( selection != null ) && ( selection.length == 1 ) ) {
          selection[ 0 ].setExpanded( ! selection[ 0 ].getExpanded() );
        }
      }
    } );
    
    initConfigTree( this.confTree );
    updateDetails();
    validatePage();
    
    setControl( mainComp );
    
  }
  
  public IConfigurationElement[] getSelection() {
    List< IConfigurationElement > result = new ArrayList< IConfigurationElement >();
    TreeItem[] selection = this.confTree.getSelection();
    if ( selection != null ) {
      for ( TreeItem item : selection ) {
        IConfigurationElement configurator = ( IConfigurationElement ) item.getData( "configurator" );
        if ( configurator != null ) {
          result.add( configurator );
        }
      }
    }
    return result.toArray( new IConfigurationElement[ result.size() ] );
  }
  
  protected void updateDetails() {
    
    IConfigurationElement configurator = null;
    
    TreeItem[] selection = this.confTree.getSelection();
    if ( ( selection != null ) && ( selection.length == 1 ) ) {
      configurator = ( IConfigurationElement ) selection[ 0 ].getData( "configurator" );
    }
    
    this.detailsFolder.setConfigurator( configurator );
    
    /*
    if ( configurator != null ) {
      IConfigurationElement[] certs = configurator.getChildren( "certificates" );
      if ( ( certs != null ) && ( certs.length > 0 ) ) {
        for ( int i = 0 ; i < certs.length ; i++ ) {
          TreeItem certItem = new TreeItem( this.certTree, SWT.NONE );
          certItem.setText( String.format( "Certificate Configuration #%d", Integer.valueOf( i + 1 ) ) );
          String loaderID = certs[ i ].getAttribute( "loaderID" );
          TreeItem loaderIdItem = new TreeItem( certItem, SWT.NONE );
          loaderIdItem.setText( 0, "Loader ID" );
          loaderIdItem.setText( 1, loaderID );
          IConfigurationElement[] loaders = certs[ i ].getChildren();
          if ( loaders != null ) {
            for ( int j = 0 ; j < loaders.length ; j++ ) {
              if ( loaders[ j ].getName().equals( "certificateDistribution" ) ) {
                String authorityID = loaders[ j ].getAttribute( "authorityID" );
                String distributionID = loaders[ j ].getAttribute( "distributionID" );
                TreeItem authIdItem = new TreeItem( certItem, SWT.NONE );
                authIdItem.setText( 0, String.format( "Authority ID #%d", Integer.valueOf( j + 1 ) ) );
                authIdItem.setText( 1, authorityID );
                TreeItem distIdItem = new TreeItem( certItem, SWT.NONE );
                distIdItem.setText( 0, String.format( "Distribution ID #%d", Integer.valueOf( j + 1 ) ) );
                distIdItem.setText( 1, distributionID );
              } else if ( loaders[ j ].getName().equals( "certificateURL" ) ) {
                String url = loaders[ j ].getAttribute( "url" );
                TreeItem urlItem = new TreeItem( certItem, SWT.NONE );
                urlItem.setText( 0, String.format( "URL #%d", Integer.valueOf( j + 1 ) ) );
                urlItem.setText( 1, url );
              }
            }
          }
          certItem.setExpanded( true );
        }
      } else {
        new TreeItem( this.certTree, SWT.NONE ).setText( "None" );
      }
    } else {
      new TreeItem( this.certTree, SWT.NONE ).setText( "N/A" );
    }
    */
  }
  
  protected void validatePage() {
    IConfigurationElement[] selection = getSelection();
    setPageComplete( ( selection != null ) && ( selection.length > 0 ) );
  }
  
  private void buildPath( final Tree tree, final IConfigurationElement element ) {
    
    TreeItem parent = null;
    
    String pathAtt = element.getAttribute( "path" );
    String[] path = pathAtt == null ? new String[ 0 ] : pathAtt.split( "/" );
    
    for ( String part : path ) {
      
      TreeItem[] items = parent == null ? tree.getItems() : parent.getItems();
      
      TreeItem pItem = null;
      for ( TreeItem item : items ) {
        if ( item.getText().equals( part ) ) {
          pItem = item;
          break;
        }
      }
      
      if ( pItem == null ) {
        if ( parent == null ) {
          pItem = new TreeItem( tree, SWT.NONE );
        } else {
          pItem = new TreeItem( parent, SWT.NONE );
        }
        pItem.setText( part );
      }
      
      parent = pItem;
      
    }
    
    TreeItem item = null;
    if ( parent == null ) {
      item = new TreeItem( tree, SWT.NONE );
    } else {
      item = new TreeItem( parent, SWT.NONE );
    }
    
    String name = element.getAttribute( "name" );
    item.setText( name );
    item.setData( "configurator", element );
    
  }
  
  private void initConfigTree( final Tree tree ) {
    
    ExtensionManager manager = new ExtensionManager();
    List< IConfigurationElement > elements
      = manager.getConfigurationElements( "eu.geclipse.core.configurator", "configuration" );
    
    for ( IConfigurationElement element : elements ) {
      buildPath( tree, element );
    }
    
  }
  
}
