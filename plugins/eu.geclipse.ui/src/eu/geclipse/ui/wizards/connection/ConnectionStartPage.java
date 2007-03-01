/******************************************************************************
 * Copyright (c) 2006 g-Eclipse consortium 
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
 *     PSNC - Katarzyna Bylec
 *           
 *****************************************************************************/

package eu.geclipse.ui.wizards.connection;

import java.net.URI;
import java.util.Hashtable;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import eu.geclipse.ui.Messages;
import eu.geclipse.ui.wizards.connection.managers.AbstractConnectionWizardManager;

/**
 * This page provides a list of types of connections available and allows user
 * to choose one of them
 * 
 * @author katis
 */
public class ConnectionStartPage extends WizardPage
  implements ModifyListener, IConnectionWizardPage
{

  /**
   * List of types of connections available
   */
  private Combo connectionTypeCombo;
  /**
   * Table that assigns name of the connection type to this connection's
   * WizardManager
   */
  private Hashtable<String, AbstractConnectionWizardManager> connectionManagers;
  /**
   * Flag to indicate that validation of the fields of this page should proceed
   */
  private boolean notFirst = false;

  /**
   * Method to create ConnectionStartPage
   * 
   * @param pageName name of the page
   * @param connectionManagers parameter to initialize
   *          {@link ConnectionStartPage#connectionManagers} field of this
   *          wizard page
   */
  protected ConnectionStartPage( final String pageName,
                                 final Hashtable<String, AbstractConnectionWizardManager> connectionManagers )
  {
    super( pageName );
    this.connectionManagers = connectionManagers;
  }

  public void createControl( final Composite parent ) {
    Composite mainComp = new Composite( parent, SWT.NONE );
    GridLayout gLayout = new GridLayout( 2, false );
    gLayout.horizontalSpacing = 10;
    gLayout.verticalSpacing = 12;
    mainComp.setLayout( gLayout );
    GridData layout = new GridData( GridData.BEGINNING );
    Label typeLabel = new Label( mainComp, SWT.LEAD );
    typeLabel.setText( Messages.getString( "ConnectionStartPage.connection_type_label" ) ); //$NON-NLS-1$
    typeLabel.setLayoutData( layout );
    layout = new GridData( GridData.BEGINNING
                           | GridData.FILL_HORIZONTAL
                           | GridData.GRAB_HORIZONTAL );
    this.connectionTypeCombo = new Combo( mainComp, SWT.NONE );
    this.connectionTypeCombo.setLayoutData( layout );
    for( String name : this.connectionManagers.keySet() ) {
      this.connectionTypeCombo.add( name );
    }
    this.connectionTypeCombo.addModifyListener( this );
    // ProjectChooser a = new ProjectChooser(parent.getShell(), new
    // WorkbenchLabelProvider(), new WorkbenchContentProvider());
    // a.setInput(ResourcesPlugin.getWorkspace().getRoot());
    // TreeViewer viewer = a.getViewer();
    setControl( mainComp );
  }

  public URI finish() {
    return null;
  }

  /**
   * Method fetching {@link AbstractConnectionWizardManager} instance for
   * connection with given name
   * 
   * @param connectionType name of the connection type
   * @return Class that manages wizard pages of connection type with a given
   *         name
   */
  public AbstractConnectionWizardManager getManager( final String connectionType )
  {
    return this.connectionManagers.get( connectionType );
  }

  /**
   * Method indicating if wizard can be finished on this page
   */
  public boolean isLastPage() {
    boolean result = false;
    if( this.connectionTypeCombo.getSelectionIndex() >= 0 ) {
      result = getManager( this.connectionTypeCombo.getText() ) == null;
    }
    return result;
  }

  @Override
  public boolean canFlipToNextPage()
  {
    boolean result = false;
    if( this.notFirst ) {
      String message = null;
      if( this.connectionTypeCombo.getSelectionIndex() < 0 ) {
        message = Messages.getString( "ConnectionWizard.req_conn_type" ); //$NON-NLS-1$
      } else if( getNextPage() != null ) {
        result = true;
      }
      setErrorMessage( message );
    }
    return result;
  }

  @Override
  public IWizardPage getNextPage()
  {
    IWizardPage result = null;
    if( this.connectionTypeCombo.getSelectionIndex() >= 0 ) {
      if( !isLastPage() ) {
        AbstractConnectionWizardManager manager = getManager( this.connectionTypeCombo.getText() );
        if( manager != null ) {
          result = manager.getFirstPage();
        }
      }
    }
    return result;
  }

  public void modifyText( final ModifyEvent e ) {
    this.notFirst = true;
    getContainer().updateButtons();
  }
}
