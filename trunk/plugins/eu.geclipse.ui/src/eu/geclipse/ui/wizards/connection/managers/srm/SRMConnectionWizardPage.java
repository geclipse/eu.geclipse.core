/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium 
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
package eu.geclipse.ui.wizards.connection.managers.srm;

import java.net.URI;
import java.net.URISyntaxException;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import eu.geclipse.ui.widgets.NumberVerifier;
import eu.geclipse.ui.wizards.connection.managers.gridftp.Messages;


class SRMConnectionWizardPage extends WizardPage implements ModifyListener {

  private Text host;
  private Text port;
  private Text path;
  
  /**
   * used for setting error message for this wizard page
   */
  private String errorMessage;
  
  protected SRMConnectionWizardPage( final String pageName ) {
    super( pageName );  
  }
  

  public void createControl( final Composite parent ) {
    Composite mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayout( new GridLayout( 2, false ) );
    
    GridData gData = new GridData();
    
    gData.horizontalSpan = 2;
    Label title = new Label(mainComp, SWT.NONE);
    title.setText( Messages.getString("SRMConnectionWizardPage.web_service_point") ); //$NON-NLS-1$
    title.setLayoutData( gData );
    
    Label hostLabel = new Label( mainComp, SWT.NONE );
    hostLabel.setText( Messages.getString("SRMConnectionWizardPage.host") ); //$NON-NLS-1$
    
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    this.host = new Text( mainComp, SWT.BORDER );
    this.host.setLayoutData( gData );
    
    Label portLabel = new Label( mainComp, SWT.NONE );
    portLabel.setText( Messages.getString("SRMConnectionWizardPage.port") ); //$NON-NLS-1$
    
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    this.port = new Text( mainComp, SWT.BORDER );
    this.port.setLayoutData( gData );
    
    Label pathLabel = new Label( mainComp, SWT.NONE );
    pathLabel.setText( Messages.getString("SRMConnectionWizardPage.path") ); //$NON-NLS-1$
    
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    this.path = new Text( mainComp, SWT.BORDER );
    this.path.setLayoutData( gData );
    
    Label srmVersionLabel = new Label(mainComp, SWT.NONE);
    srmVersionLabel.setText( Messages.getString("SRMConnectionWizardPage.version") ); //$NON-NLS-1$
        
    List list = new List(mainComp, SWT.SINGLE);
    list.add( Messages.getString("SRMConnectionWizardPage.ver_1_0") ); //$NON-NLS-1$
    list.add( Messages.getString("SRMConnectionWizardPage.ver_2_0") ); //$NON-NLS-1$
    list.add( Messages.getString("SRMConnectionWizardPage.ver_2_1_1") ); //$NON-NLS-1$
    list.add( Messages.getString("SRMConnectionWizardPage.ver_3_0") ); //$NON-NLS-1$
    
    
    this.port.addListener( SWT.Verify, new NumberVerifier() );
    this.path.addModifyListener( this );
    this.host.addModifyListener( this );
    
    setControl( mainComp );
  }




  URI finish() {
    URI result = null;
    try {
      String validPath = this.path.getText().replaceAll( " ", "%20" ); //$NON-NLS-1$ //$NON-NLS-2$
      result = new URI( "srm://" //$NON-NLS-1$
                      + this.host.getText()
                      + ":" //$NON-NLS-1$
                      + this.port.getText()
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
   *         int value
   */
  @Override
  public boolean isPageComplete() {
    this.errorMessage = null;
    boolean result = true;
    if( "".equals( this.host.getText() ) //$NON-NLS-1$
        || "".equals( this.port.getText() ) //$NON-NLS-1$
        || "".equals( this.path.getText() ) ) //$NON-NLS-1$
    {
      result = false;
      this.errorMessage = Messages.getString("GridFTPConnectionWizardPage.fields_are_empty_error"); //$NON-NLS-1$
    } else {
      String separator = "/"; //$NON-NLS-1$
      if( !this.path.getText().endsWith( separator ) )    
      {
        result = false;
        this.errorMessage = Messages.getString("GridFTPConnectionWizardPage.directory_path_empty_error") + separator; //$NON-NLS-1$
      }
    }
    setErrorMessage( this.errorMessage );
    return result;
  }
  
  /**
   * reacts when text changes in any of the text fields on this page
   */
  public void modifyText( final ModifyEvent event ) {
    getContainer().updateButtons();
    getContainer().updateMessage();
  }
  
}
