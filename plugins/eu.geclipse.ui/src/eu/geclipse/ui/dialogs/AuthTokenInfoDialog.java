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

package eu.geclipse.ui.dialogs;

import java.util.Date;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.IconAndMessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import eu.geclipse.core.auth.IAuthenticationToken;

/**
 * A dialog that presents information about authentication tokens to the user.
 */
public class AuthTokenInfoDialog extends IconAndMessageDialog {
  
  /**
   * The token for which to display information.
   */
  private IAuthenticationToken token;

  /**
   * Construct a new info dialog from the specified token.
   * 
   * @param token The <code>IAuthenticationToken</code> for which to display the information.
   * @param parentShell The parent shell of this dialog.
   */
  public AuthTokenInfoDialog( final IAuthenticationToken token, final Shell parentShell ) {
    super( parentShell );
    setShellStyle( SWT.CLOSE | SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL
                   | SWT.RESIZE | SWT.MIN | SWT.MAX );
    this.token = token;
  }
  
  /**
   * Get the token of this info dialog.
   * 
   * @return The <code>IAuthenticationToken</code> for which to display the info.
   */
  protected IAuthenticationToken getToken() {
    return this.token;
  }
 
  /* (non-Javadoc)
   * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Control createDialogArea( final Composite parent ) {
    
    createMessageArea(parent);
    
    GridData gData;
    
    Composite mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayout( new GridLayout( 2, false ) );
    gData = new GridData( GridData.FILL_BOTH );
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    gData.widthHint = 600;
    gData.heightHint = 400;
    mainComp.setLayoutData( gData );
    
    Label idLabel = new Label( mainComp, SWT.LEFT );
    idLabel.setText( Messages.getString("AuthTokenInfoDialog.token_id_label") ); //$NON-NLS-1$
    gData = new GridData();
    gData.horizontalAlignment = GridData.BEGINNING;
    idLabel.setLayoutData( gData );
    
    Text idText = new Text( mainComp, SWT.LEFT | SWT.BORDER );
    idText.setText( this.token.getID() );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    idText.setLayoutData( gData );
    idText.setEditable( false );
    
    Label typeLabel = new Label( mainComp, SWT.LEFT );
    typeLabel.setText( Messages.getString("AuthTokenInfoDialog.token_type_label") ); //$NON-NLS-1$
    gData = new GridData();
    gData.horizontalAlignment = GridData.BEGINNING;
    typeLabel.setLayoutData( gData );
    
    Text typeText = new Text( mainComp, SWT.LEFT | SWT.BORDER );
    typeText.setText( this.token.getDescription().getTokenTypeName() );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    typeText.setLayoutData( gData );
    typeText.setEditable( false );
    
    Label stateLabel = new Label( mainComp, SWT.LEFT );
    stateLabel.setText( Messages.getString("AuthTokenInfoDialog.token_state_label") ); //$NON-NLS-1$
    gData = new GridData();
    gData.horizontalAlignment = GridData.BEGINNING;
    stateLabel.setLayoutData( gData );
    
    String state =   this.token.isActive()
                   ? Messages.getString( "AuthTokenInfoDialog.state_active" ) //$NON-NLS-1$
                   : Messages.getString( "AuthTokenInfoDialog.state_inactive" ); //$NON-NLS-1$
    Text stateText = new Text( mainComp, SWT.LEFT | SWT.BORDER );
    stateText.setText( state );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    stateText.setLayoutData( gData );
    stateText.setEditable( false );
    
    Label expLabel = new Label( mainComp, SWT.LEFT );
    expLabel.setText( Messages.getString("AuthTokenInfoDialog.exp_date_label") ); //$NON-NLS-1$
    gData = new GridData();
    gData.horizontalAlignment = GridData.BEGINNING;
    expLabel.setLayoutData( gData );
    
    Text expText = new Text( mainComp, SWT.LEFT | SWT.BORDER );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    expText.setLayoutData( gData );
    expText.setEditable( false );
    
    if ( !this.token.isActive() ) {
      expText.setText( Messages.getString( "AuthTokenInfoDialog.token_not_active_message" ) ); //$NON-NLS-1$
    } else {
      long lifetime = this.token.getTimeLeft();
      if ( lifetime < 0 ) {
        expText.setText( Messages.getString( "AuthTokenInfoDialog.infinite_lifetime_message" ) ); //$NON-NLS-1$
      } else if ( lifetime == 0 ) {
        expText.setText( Messages.getString( "AuthTokenInfoDialog.token_expired_message" ) ); //$NON-NLS-1$
      } else { 
        Date expDate = new Date();
        expDate.setTime( expDate.getTime() + lifetime * 1000 );
        StringBuilder bf = new StringBuilder( expDate.toString() );
        int days = ( int )( lifetime / 86400 );
        int hours = ( int )( ( lifetime % 86400 ) / 3600 );
        int minutes = ( int )( ( lifetime % 3600 ) / 60 );
        int seconds = ( int )( lifetime % 60 );
        String ltString = String.format( " (%1$dd %2$2dh %3$2dm %4$2ds)", //$NON-NLS-1$
                                         Integer.valueOf( days ),
                                         Integer.valueOf( hours ),
                                         Integer.valueOf( minutes ),
                                         Integer.valueOf(seconds ) );
        expText.setText( bf.toString()+ltString );
      }
    }
    
    Control infoArea = createInfoArea( mainComp );
    gData = new GridData( GridData.FILL_BOTH );
    gData.horizontalSpan = 2;
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    infoArea.setLayoutData( gData );
    
    return mainComp;
    
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.dialogs.IconAndMessageDialog#getImage()
   */
  @Override
  protected Image getImage() {
    return getInfoImage();
  }
  
  /**
   * Create the token specific info area.
   * 
   * @param parent The parent composite that will contain the created info area.
   * @return The created info area.
   */
  protected Control createInfoArea( final Composite parent ) {
    
    GridData gData;
    
    Composite mainComp = new Composite( parent, SWT.BORDER );
    mainComp.setLayout( new GridLayout( 2, false ) );
    
    Label label = new Label( mainComp, SWT.NONE );
    label.setText( Messages.getString( "AuthTokenInfoDialog.no_info_label" ) ); //$NON-NLS-1$
    gData = new GridData();
    label.setLayoutData( gData );
    
    return mainComp;
    
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.dialogs.Dialog#getDialogBoundsSettings()
   */
  @Override
  protected IDialogSettings getDialogBoundsSettings() {
    return eu.geclipse.ui.internal.Activator.getDefault().getDialogSettings();
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
   */
  @Override
  protected void configureShell( final Shell shell ) {
    super.configureShell( shell );
    String tokenType = this.token.getDescription().getTokenTypeName();
    shell.setText( tokenType + " " + Messages.getString("AuthTokenInfoDialog.info_suffix") ); //$NON-NLS-1$ //$NON-NLS-2$
  }

  @Override
  protected void createButtonsForButtonBar( final Composite parent) {
    createButton(parent, IDialogConstants.CANCEL_ID,
            "Close", false);
  }
}
