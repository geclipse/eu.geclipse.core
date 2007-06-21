package eu.geclipse.ui.wizards;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.List;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IConnectionManager;
import eu.geclipse.core.model.IGridConnection;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridElementCreator;

public class ConnectionNameWizardPage extends WizardPage {
  
  private Text nameText;
  
  private URI initialContent;
  
  public ConnectionNameWizardPage() {
    super( "connectionNamePage",
           "Connection Name",
           null
    );
    setDescription( "Enter the name of the new connection" );
  }

  public void createControl( final Composite parent ) {
    
    GridData gData;
    
    Composite mainComp = new Composite( parent, SWT.NULL );
    mainComp.setLayout( new GridLayout( 2, false ) );
    gData = new GridData( GridData.FILL_BOTH );
    mainComp.setLayoutData( gData );
    
    Label nameLabel = new Label( mainComp, SWT.NULL );
    nameLabel.setText( "Connection name:" );
    gData = new GridData();
    nameLabel.setLayoutData( gData );
    
    this.nameText = new Text( mainComp, SWT.BORDER );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    this.nameText.setLayoutData( gData );
    
    this.nameText.addModifyListener( new ModifyListener() {
      public void modifyText( final ModifyEvent e ) {
        validatePage();
      }
    } );
    
    setControl( mainComp );
    validatePage();

  }
  
  public IGridConnection createNewConnection() {
    
    IGridConnection result = null;
    
    try {
      
      IFileStore fileStore = getConnectionFilestore();
      IGridElementCreator creator = findConnectionCreator( fileStore );
      
      if ( creator != null ) {
        IConnectionManager connectionManager
          = GridModel.getConnectionManager();
        result = ( IGridConnection ) connectionManager.create( creator );
      }
      
    } catch ( CoreException cExc ) {
      setErrorMessage( cExc.getMessage() );
    } catch ( IOException ioExc ) {
      setErrorMessage( ioExc.getMessage() );
    }
    
    return result;
    
  }

  public String getConnectionName() {
    return this.nameText.getText();
  }
  
  public void setConnectionName( final String name ) {
    this.nameText.setText( name );
  }
  
  public String getConnectionFileName() {
    String filename = getConnectionName();
    return ConnectionWizard.CONNECTION_PREFIX + filename + ConnectionWizard.CONNECTION_SUFFIX;
  }
  
  protected void setInitialContent( final URI uri ) {
    this.initialContent = uri;
  }
  
  protected boolean validatePage() {
    
    boolean valid = true;
    setErrorMessage( null );
    
    String name = getConnectionName();
    
    if ( ( name != null ) && ( name.length() != 0 ) ) {
    
      IConnectionManager connectionManager
        = GridModel.getConnectionManager();
      IGridConnection[] gConnections
        = connectionManager.getGlobalConnections();
      
      if ( gConnections != null ) {
      
        for ( IGridConnection connection : gConnections ) {
          if ( connection.getName().equals( name ) ) {
            setErrorMessage( "A connection with the specified name already exists" );
            valid = false;
          }
        }
        
      }
      
    } else {
      
      setErrorMessage( "You have to define a valid connection name" );
      valid = false;
      
    }
    
    setPageComplete( valid );
    
    return valid;
    
  }
  
  private void createInitialContent( final IFileStore fileStore )
      throws CoreException, IOException {
    
    if ( this.initialContent != null ) {
      OutputStream oStream = fileStore.openOutputStream( EFS.NONE, null );
      oStream.write( this.initialContent.toString().getBytes() );
      oStream.close();
    }
    
  }
  
  private IGridElementCreator findConnectionCreator( final IFileStore fileStore ) {
    
    IGridElementCreator result = null;
    List< IGridElementCreator > standardCreators = GridModel.getStandardCreators();
    
    for ( IGridElementCreator creator : standardCreators ) {
      if ( creator.canCreate( fileStore ) ) {
        result = creator;
        break;
      }
    }
    
    return result;
    
  }
  
  private IFileStore getConnectionFilestore()
      throws CoreException, IOException {
    
    String filename = getConnectionFileName();
    
    IConnectionManager connectionManager = GridModel.getConnectionManager();
    IFileStore connectionStore = connectionManager.getFileStore();
    IFileStore childStore = connectionStore.getChild( filename );
    
    createInitialContent( childStore );
    
    return childStore;
    
  }

}
