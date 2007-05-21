/*******************************************************************************
 * Copyright (c) 2006 g-Eclipse consortium All rights reserved. This program and
 * the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html Initial development of the original
 * code was made for project g-Eclipse founded by European Union project number:
 * FP6-IST-034327 http://www.geclipse.eu/ Contributor(s): PSNC - Katarzyna Bylec -
 * Pawel Wolniewicz - Mateusz Pabis
 ******************************************************************************/
package eu.geclipse.ui.wizards.connection.managers.gridftp;

import java.net.URI;
import java.net.URISyntaxException;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import eu.geclipse.ui.widgets.NumberVerifier;
import eu.geclipse.ui.widgets.StoredCombo;

/**
 * Wizard Page for GridFTP connection
 * 
 * @author katis
 */
public class GridFTPConnectionWizardPage extends WizardPage
  implements Listener, ModifyListener
{

  private static String KEY_HOSTS_ID = "key_hosts"; //$NON-NLS-1$
  private static String KEY_PATH_ID = "key_path"; //$NON-NLS-1$
  private StoredCombo hostCombo;
  private Text portText;
  private boolean validate = false;
  private boolean isValisThis = false;
  /**
   * used for setting error message for this wizard page
   */
  private String errorMessage;
  private StoredCombo pathCombo;

  /**
   * inherited constructor
   * 
   * @param pageName name of the page
   */
  protected GridFTPConnectionWizardPage( final String pageName ) {
    super( pageName );
    this.setTitle( Messages.getString( "GridFTPConnectionWizardPage.page_title" ) ); //$NON-NLS-1$
    this.setDescription( Messages.getString( "GridFTPConnectionWizardPage.page_description" ) ); //$NON-NLS-1$
  }

  /**
   * performs finish when Wizard's Finish button is pressed
   */
  URI finish() {
    URI result = null;
    try {
      String validPath = this.pathCombo.getText().replaceAll( " ", "%20" ); //$NON-NLS-1$ //$NON-NLS-2$
      result = new URI( "gridftp://" //$NON-NLS-1$
                        + this.hostCombo.getText()
                        + ":" //$NON-NLS-1$
                        + this.portText.getText()
                        + validPath );
    } catch( NumberFormatException nfException ) {
      eu.geclipse.ui.internal.Activator.logException( nfException );
    } catch( URISyntaxException uriSyntExc ) {
      eu.geclipse.ui.internal.Activator.logException( uriSyntExc );
    }
    return result;
  }

  /**
   * Returns true if user can leave this page.
   * 
   * @return true if all fields are filled and port text field holds positive
   *         integer value
   */
  @Override
  public boolean isPageComplete()
  {
    boolean result = false;
    if (! this.validate){
      result = checkPageCompleteness();
    } else {
      result = this.isValisThis;
    }
    return result;
  }

  /**
   * Method used to find out if this page has all the fields complete (holding
   * proper values)
   * 
   * @return true if there are proper values in all page's fields, false - if
   *         one or more is malformed or missing
   */
  public boolean checkPageCompleteness() {
    boolean result = true;
    this.errorMessage = null;
    if( "".equals( this.hostCombo.getText() ) //$NON-NLS-1$
        || "".equals( this.portText.getText() ) //$NON-NLS-1$
        || "".equals( this.pathCombo.getText() ) ) //$NON-NLS-1$
    {
      result = false;
      this.errorMessage = Messages.getString( "GridFTPConnectionWizardPage.fields_are_empty_error" ); //$NON-NLS-1$
    } else {
      String separator = "/"; //$NON-NLS-1$
      if( !this.pathCombo.getText().endsWith( separator ) ) {
        result = false;
        this.errorMessage = Messages.getString( "GridFTPConnectionWizardPage.directory_path_empty_error" ) + separator; //$NON-NLS-1$
      }
    }
    if( this.validate ) {
      setErrorMessage( this.errorMessage );
    }
    this.isValisThis = result;
    
    return result;
  }

  /**
   * creates controls on this page
   */
  public void createControl( final Composite parent ) {
    IPreferenceStore prefStore = eu.geclipse.ui.internal.Activator.getDefault()
      .getPreferenceStore();
    Composite mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayout( new GridLayout( 2, false ) );
    GridData gData = new GridData();
    Label hostLabel = new Label( mainComp, SWT.LEFT );
    hostLabel.setText( Messages.getString( "GridFTPConnectionWizardPage.host_label" ) ); //$NON-NLS-1$
    gData.horizontalAlignment = GridData.BEGINNING;
    hostLabel.setLayoutData( gData );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    this.hostCombo = new StoredCombo( mainComp, SWT.LEFT
                                                | SWT.SINGLE
                                                | SWT.BORDER );
    this.hostCombo.setPreferences( prefStore, KEY_HOSTS_ID );
    this.hostCombo.setLayoutData( gData );
    this.hostCombo.addModifyListener( this );
    gData = new GridData();
    Label portLabel = new Label( mainComp, SWT.LEFT );
    portLabel.setText( Messages.getString( "GridFTPConnectionWizardPage.port_label" ) ); //$NON-NLS-1$
    gData.horizontalAlignment = GridData.BEGINNING;
    portLabel.setLayoutData( gData );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    this.portText = new Text( mainComp, SWT.LEFT | SWT.SINGLE | SWT.BORDER );
    this.portText.setLayoutData( gData );
    this.portText.setText( "2811" ); //$NON-NLS-1$
    this.portText.addModifyListener( this );
    this.portText.setTextLimit( 5 );
    this.portText.addListener( SWT.Verify, new NumberVerifier() );
    gData = new GridData();
    Label pathLabel = new Label( mainComp, SWT.LEFT );
    pathLabel.setText( Messages.getString( "GridFTPConnectionWizardPage.path_label" ) ); //$NON-NLS-1$
    gData.horizontalAlignment = GridData.BEGINNING;
    pathLabel.setLayoutData( gData );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    this.pathCombo = new StoredCombo( mainComp, SWT.LEFT
                                                | SWT.SINGLE
                                                | SWT.BORDER );
    this.pathCombo.setLayoutData( gData );
    this.pathCombo.setPreferences( prefStore, KEY_PATH_ID );
    this.pathCombo.setText( "/" ); //$NON-NLS-1$
    this.pathCombo.addModifyListener( this );
    setControl( mainComp );
    getContainer().updateButtons();
    this.validate = true;  
  }

  /**
   * reacts when text changes in any of the text fields on this page
   */
  public void modifyText( final ModifyEvent e ) {
    if( this.validate ) {
      setPageComplete( checkPageCompleteness() );
      getContainer().updateButtons();
    }
  }

  /**
   * reacts when sth happens on this page
   */
  public void handleEvent( final Event event ) {
    // do nothing
  }
}
