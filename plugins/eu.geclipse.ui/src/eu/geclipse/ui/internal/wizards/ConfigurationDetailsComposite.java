package eu.geclipse.ui.internal.wizards;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

public class ConfigurationDetailsComposite extends Composite {
  
  private Text confNameText;
  
  private Text confDescText;
  
  private Text confPrereqText;
  
  private Tree certTree;
  
  private Tree voTree;
  
  private Tree projectTree;

  public ConfigurationDetailsComposite( final Composite parent, final int style ) {
    
    super( parent, style );
    
    setLayout( new GridLayout( 1, false ) );
    
    TabFolder tabber = new TabFolder( this, SWT.NONE );
    tabber.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
    
    createGeneralTab( tabber );
    createCertificateTab( tabber );
    createVoTab( tabber );
    createProjectTab( tabber );
    
  }
  
  public void setConfigurator( final IConfigurationElement configurator ) {
    updateGeneralInfo( configurator );
    updateCertificateInfo( configurator );
    updateVoInfo( configurator );
    updateProjectInfo( configurator );
  }
  
  protected void updateCertificateInfo( final IConfigurationElement configurator ) {
   
    this.certTree.removeAll();
    
    if ( configurator != null ) {
      
      IConfigurationElement[] certs = configurator.getChildren( "certificates" );
      
      if ( ( certs != null ) && ( certs.length > 0 ) ) {
        
        for ( int i = 0 ; i < certs.length ; i++ ) {
          
          TreeItem certItem = createTreeItem( this.certTree,  String.format( "Certificate Configuration #%d", Integer.valueOf( i + 1 ) ), null );
          createTreeItem( certItem, "Loader ID", certs[ i ].getAttribute( "loaderID" ) );
          IConfigurationElement[] loaders = certs[ i ].getChildren();
          
          if ( loaders != null ) {
            
            for ( int j = 0 ; j < loaders.length ; j++ ) {
            
              if ( loaders[ j ].getName().equals( "certificateDistribution" ) ) {
                
                String authorityID = loaders[ j ].getAttribute( "authorityID" );
                String distributionID = loaders[ j ].getAttribute( "distributionID" );
                
                createTreeItem( certItem, String.format( "Authority ID #%d", Integer.valueOf( j + 1 ) ), authorityID );
                createTreeItem( certItem, String.format( "Distribution ID #%d", Integer.valueOf( j + 1 ) ), distributionID );
                
              }
              
              else if ( loaders[ j ].getName().equals( "certificateURL" ) ) {
                String url = loaders[ j ].getAttribute( "url" );
                createTreeItem( certItem, String.format( "URL #%d", Integer.valueOf( j + 1 ) ), url );
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
    
  }
  
  protected void updateGeneralInfo( final IConfigurationElement configurator ) {
    
    this.confNameText.setText( securelyGetAttribute( configurator, "name" ) );
    this.confDescText.setText( securelyGetAttribute( configurator, "description" ) );
    
    String prereq = configurator == null ? null : configurator.getAttribute( "prerequisites" );
    if ( prereq == null ) {
      this.confPrereqText.setText( "N/A" );
    } else {
      String[] split = prereq.split( ";" );
      StringBuffer buffer = new StringBuffer();
      for ( String s : split ) {
        buffer.append( "- " + s + "\n" );
      }
      this.confPrereqText.setText( buffer.toString() );
    }
    
  }
  
  protected void updateProjectInfo( final IConfigurationElement configurator ) {
    
    this.projectTree.removeAll();
    
    if ( configurator != null ) {
      
      IConfigurationElement[] projects = configurator.getChildren( "project" );
      
      if ( ( projects != null ) && ( projects.length > 0 ) ) {
        
        for ( IConfigurationElement project : projects ) {
          
          TreeItem projectItem = createTreeItem( this.projectTree, project.getAttribute( "projectName" ), null );
          createTreeItem( projectItem, "VO Name", project.getAttribute( "voName" ) );

          IConfigurationElement[] folders = project.getChildren( "projectFolder" );
          TreeItem foldersItem = createTreeItem( projectItem, "Project Folders", null );
          
          for ( IConfigurationElement folder : folders ) {
            createTreeItem( foldersItem, folder.getAttribute( "folderID" ), folder.getAttribute( "folderName" ) );
          }
          
          foldersItem.setExpanded( true );
          projectItem.setExpanded( true );
          
        }
        
      } else {
        new TreeItem( this.projectTree, SWT.NONE ).setText( "None" );
      }
      
    } else {
      new TreeItem( this.projectTree, SWT.NONE ).setText( "N/A" );
    }
    
  }
  
  protected void updateVoInfo( final IConfigurationElement configurator ) {
    
    this.voTree.removeAll();
    
    if ( configurator != null ) {
      
      IConfigurationElement[] vos = configurator.getChildren( "vo" );
      
      if ( ( vos != null ) && ( vos.length > 0 ) ) {
        
        for ( IConfigurationElement vo : vos ) {
          
          TreeItem voItem = createTreeItem( this.voTree, vo.getAttribute( "voName" ), null );
          createTreeItem( voItem, "VO Creator", vo.getAttribute( "creatorID" ) );

          IConfigurationElement[] parameters = vo.getChildren( "voParameter" );
          TreeItem paramsItem = createTreeItem( voItem, "Parameters", null );
          
          for ( IConfigurationElement parameter : parameters ) {
            IConfigurationElement[] values = parameter.getChildren( "parameterValue" );
            if ( values != null ) {
              if ( values.length == 1 ) {
                createTreeItem( paramsItem, parameter.getAttribute( "key" ), values[ 0 ].getAttribute( "value" ) );
              } else if ( values.length > 1 ) {
                TreeItem paramItem = createTreeItem( paramsItem, parameter.getAttribute( "key" ), null );
                for ( IConfigurationElement value : values ) {
                  createTreeItem( paramItem, value.getAttribute( "value" ), null );
                }
                paramItem.setExpanded( true );
              }
            }
          }
          
          paramsItem.setExpanded( true );
          voItem.setExpanded( true );
          
        }
        
      } else {
        new TreeItem( this.voTree, SWT.NONE ).setText( "None" );
      }
      
    } else {
      new TreeItem( this.voTree, SWT.NONE ).setText( "N/A" );
    }
        
  }
  
  private void createCertificateTab( final TabFolder tabber ) {
    
    TabItem certificatesTab = new TabItem( tabber, SWT.NONE );
    certificatesTab.setText( "Certificates" );
    
    Composite certComp = new Composite( tabber, SWT.NONE );
    certComp.setLayout( new GridLayout( 1, false ) );
    certComp.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
    certificatesTab.setControl( certComp );
    
    Label certLabel = new Label( certComp, SWT.NONE );
    certLabel.setText( "Certificate Configurations" );
    certLabel.setLayoutData( new GridData() );
    
    this.certTree = new Tree( certComp, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER );
    this.certTree.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
    this.certTree.setHeaderVisible( true );
    
    TreeColumn certFieldColumn = new TreeColumn( this.certTree, SWT.LEFT );
    certFieldColumn.setText( "Field" );
    certFieldColumn.setWidth( 200 );
    
    TreeColumn certValueColumn = new TreeColumn( this.certTree, SWT.LEFT );
    certValueColumn.setText( "Value" );
    certValueColumn.setWidth( 100 );
    
  }
    
  private void createGeneralTab( final TabFolder tabber ) {  
  
    TabItem generalTab = new TabItem( tabber, SWT.NONE );
    generalTab.setText( "General" );
    
    Composite generalComp = new Composite( tabber, SWT.NONE );
    generalComp.setLayout( new GridLayout( 1, false ) );
    generalComp.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
    generalTab.setControl( generalComp );
    
    Label confNameLabel = new Label( generalComp, SWT.NONE );
    confNameLabel.setLayoutData( new GridData() );
    confNameLabel.setText( "Configuration Name:" );
    
    this.confNameText = new Text( generalComp, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY );
    this.confNameText.setLayoutData( new GridData( GridData.FILL, GridData.BEGINNING, true, false ) );
    
    Label confDescLabel = new Label( generalComp, SWT.NONE );
    confDescLabel.setLayoutData( new GridData() );
    confDescLabel.setText( "Description:" );
    
    this.confDescText = new Text( generalComp, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI | SWT.READ_ONLY );
    GridData gData = new GridData( GridData.FILL, GridData.FILL, true, true );
    gData.widthHint = 300;
    gData.heightHint = 80;
    this.confDescText.setLayoutData( gData );
    
    Label confPrereqLabel = new Label( generalComp, SWT.NONE );
    confPrereqLabel.setLayoutData( new GridData() );
    confPrereqLabel.setText( "Prerequisites:" );
    
    this.confPrereqText = new Text( generalComp, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI | SWT.READ_ONLY );
    gData = new GridData( GridData.FILL, GridData.FILL, true, true );
    gData.widthHint = 300;
    gData.heightHint = 80;
    this.confPrereqText.setLayoutData( gData );
    
  }
  
  private void createProjectTab( final TabFolder tabber ) {
    
    TabItem projectTab = new TabItem( tabber, SWT.NONE );
    projectTab.setText( "Projects" );
    
    Composite projectComp = new Composite( tabber, SWT.NONE );
    projectComp.setLayout( new GridLayout( 1, false ) );
    projectComp.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
    projectTab.setControl( projectComp );
    
    Label projectLabel = new Label( projectComp, SWT.NONE );
    projectLabel.setText( "Project Configurations" );
    projectLabel.setLayoutData( new GridData() );
    
    this.projectTree = new Tree( projectComp, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER );
    this.projectTree.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
    this.projectTree.setHeaderVisible( true );
    
    TreeColumn voFieldColumn = new TreeColumn( this.projectTree, SWT.LEFT );
    voFieldColumn.setText( "Field" );
    voFieldColumn.setWidth( 200 );
    
    TreeColumn voValueColumn = new TreeColumn( this.projectTree, SWT.LEFT );
    voValueColumn.setText( "Value" );
    voValueColumn.setWidth( 100 );
    
  }
  
  private TreeItem createTreeItem( final Tree tree, final String s1, final String s2 ) {
    TreeItem item = new TreeItem( tree, SWT.NONE );
    fillTreeItem( item, s1, s2 );
    return item;
  }
  
  private TreeItem createTreeItem( final TreeItem parent, final String s1, final String s2 ) {
    TreeItem item = new TreeItem( parent, SWT.NONE );
    fillTreeItem( item, s1, s2 );
    return item;
  }
  
  private void createVoTab( final TabFolder tabber ) {
    
    TabItem voTab = new TabItem( tabber, SWT.NONE );
    voTab.setText( "Virtual Organizations" );
    
    Composite voComp = new Composite( tabber, SWT.NONE );
    voComp.setLayout( new GridLayout( 1, false ) );
    voComp.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
    voTab.setControl( voComp );
    
    Label voLabel = new Label( voComp, SWT.NONE );
    voLabel.setText( "VO Configurations" );
    voLabel.setLayoutData( new GridData() );
    
    this.voTree = new Tree( voComp, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER );
    this.voTree.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
    this.voTree.setHeaderVisible( true );
    
    TreeColumn voFieldColumn = new TreeColumn( this.voTree, SWT.LEFT );
    voFieldColumn.setText( "Field" );
    voFieldColumn.setWidth( 200 );
    
    TreeColumn voValueColumn = new TreeColumn( this.voTree, SWT.LEFT );
    voValueColumn.setText( "Value" );
    voValueColumn.setWidth( 100 );
    
  }
  
  private void fillTreeItem( final TreeItem item, final String s1, final String s2 ) {
    item.setText( 0, s1 == null ? "" : s1 );
    item.setText( 1, s2 == null ? "" : s2 );
  }
  
  private String securelyGetAttribute( final IConfigurationElement conf,
                                       final String attribute ) {
    
    String result = "N/A"; //$NON-NLS-1$
    
    if ( conf != null ) {
      String s = conf.getAttribute( attribute );
      if ( ( s != null ) && ( s.trim().length() > 0 ) ) {
        result = s;
      }
    }
    
    return result;
    
  }
  
}
