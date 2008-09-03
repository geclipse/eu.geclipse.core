/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
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

public class ConnectionNameWizardPage extends WizardPage {
  
  private Text nameText;
  
  private String initialName;
  
  public ConnectionNameWizardPage( final String initialName ) {
    super( Messages.getString("ConnectionNameWizardPage.name"), //$NON-NLS-1$
           Messages.getString("ConnectionNameWizardPage.title"), //$NON-NLS-1$
           null
    );
    this.initialName = initialName;
    setDescription( Messages.getString("ConnectionNameWizardPage.description") ); //$NON-NLS-1$
  }
  
  public void createControl( final Composite parent ) {
    
    GridData gData;
    
    Composite mainComp = new Composite( parent, SWT.NULL );
    mainComp.setLayout( new GridLayout( 2, false ) );
    gData = new GridData( GridData.FILL_BOTH );
    mainComp.setLayoutData( gData );
    
    Label nameLabel = new Label( mainComp, SWT.NULL );
    nameLabel.setText( Messages.getString("ConnectionNameWizardPage.name_label") ); //$NON-NLS-1$
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
    
    if ( this.initialName != null ) {
      this.nameText.setText( this.initialName );
    }
    
    setControl( mainComp );
    validatePage();

  }

  public String getConnectionName() {
    return this.nameText.getText();
  }
  
  public void setConnectionName( final String name ) {
    this.nameText.setText( name );
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
            setErrorMessage( Messages.getString("ConnectionNameWizardPage.connection_already_exists") ); //$NON-NLS-1$
            valid = false;
          }
        }
        
      }
      
    } else {
      
      valid = false;
      
    }
    
    setPageComplete( valid );
    
    return valid;
    
  }

}
