/******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     PSNC: 
 *      - Katarzyna Bylec (katis@man.poznan.pl)
 *           
 *****************************************************************************/
package eu.geclipse.ui.wizards.connection;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import eu.geclipse.core.connection.ConnectionManager;

/**
 * First page of a {@link ConnectionWizard} created from gExplorerView. This
 * page is used to get connection's name and check if connection with this name
 * already exists
 * 
 * @author katis
 */
public class ConnectionNameChooser extends WizardPage
  implements IConnectionFirstPage, ModifyListener {

  private Text connectionName;

  protected ConnectionNameChooser( final String pageName )
  {
    super( pageName );
    setTitle( Messages.getString("ConnectionNameChooser.page_title") ); //$NON-NLS-1$
    setDescription( Messages.getString("ConnectionNameChooser.page_description") ); //$NON-NLS-1$
  }

  public IFile createNewFile() {
    // creation is not permited from this class
    return null;
  }

  public String getConnectionName() {
    String result = ""; //$NON-NLS-1$
    if( this.connectionName != null ) {
      result = this.connectionName.getText();
    }
    return result;
  }

  public void setFileName( final String name ) {
    if( this.connectionName != null ) {
      this.connectionName.setText( name );
    }
  }

  public void setInitialText( final String text ) {
    //nothing to do here
  }

  public void createControl( final Composite parent ) {
    Composite mostTopLevel = new Composite( parent, SWT.NONE );
    mostTopLevel.setLayout( new GridLayout( 2, false ) );
    mostTopLevel.setLayoutData( new GridData( GridData.VERTICAL_ALIGN_FILL
                                              | GridData.HORIZONTAL_ALIGN_FILL ) );
    mostTopLevel.setFont( parent.getFont() );
    Label connectionNameLabel = new Label( mostTopLevel, SWT.NONE );
    connectionNameLabel.setText( Messages.getString( "ConnectionNameChooser.new_connection_label" ) ); //$NON-NLS-1$
    GridData layout = new GridData( GridData.FILL_HORIZONTAL
                                    | GridData.GRAB_HORIZONTAL );
    this.connectionName = new Text( mostTopLevel, SWT.BORDER | SWT.SINGLE );
    this.connectionName.setLayoutData( layout );
    this.connectionName.addModifyListener( this );
    setControl( mostTopLevel );
  }

  @Override
  public boolean canFlipToNextPage() {
    boolean result = false;
    String message = null;
    if( this.getConnectionName().equals( "" ) ) { //$NON-NLS-1$
//      message = Messages.getString( "ConnectionNameChooser.connection_name_empty" ); //$NON-NLS-1$
    } else {
      if( ConnectionManager.getManager()
        .getConnectionNames()
        .contains( this.getConnectionName() ) )
      {
        message = Messages.getString( "ConnectionNameChooser.connection_already_exists" ); //$NON-NLS-1$
      } else {
        result = true;
      }
    }
    this.setMessage( message );
    return result;
  }

  public void modifyText( final ModifyEvent e ) {
    setPageComplete( canFlipToNextPage() );
  }
}
