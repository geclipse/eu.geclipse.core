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
 *    Moritz Post - initial API and implementation
 *****************************************************************************/

package eu.geclipse.aws.ui.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import eu.geclipse.aws.auth.AWSAuthToken;
import eu.geclipse.aws.auth.AWSAuthTokenDescription;
import eu.geclipse.aws.ui.Messages;
import eu.geclipse.core.auth.IAuthenticationToken;
import eu.geclipse.ui.dialogs.AuthTokenInfoDialog;

/**
 * This Dialog extension show details specific to the {@link AWSAuthToken}.
 * 
 * @author Moritz Post
 */
public class AWSAuthTokenInfoDialog extends AuthTokenInfoDialog {

  /**
   * Create a new {@link AWSAuthTokenInfoDialog} with the given token as its
   * information host.
   * 
   * @param token the token to take the information from
   * @param parentShell the shell to open the dialog with
   */
  public AWSAuthTokenInfoDialog( final IAuthenticationToken token,
                                 final Shell parentShell )
  {
    super( token, parentShell );
  }

  @Override
  protected Control createInfoArea( final Composite parent ) {

    AWSAuthTokenDescription desc = ( AWSAuthTokenDescription )getToken().getDescription();
    GridData gData;

    Label spacerLabel = new Label( parent, SWT.LEFT );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    gData.horizontalSpan = 2;
    spacerLabel.setLayoutData( gData );

    // access id text
    Label accessIdLabel = new Label( parent, SWT.LEFT );
    accessIdLabel.setText( Messages.getString( "AWSAuthTokenInfoDialog.label_aws_access_id" ) ); //$NON-NLS-1$
    gData = new GridData();
    accessIdLabel.setLayoutData( gData );

    Text accessIdText = new Text( parent, SWT.LEFT
                                          | SWT.SINGLE
                                          | SWT.BORDER
                                          | SWT.READ_ONLY );
    accessIdText.setText( desc.getAwsAccessId() );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    accessIdText.setLayoutData( gData );

    // secret id
    Label secretIdLabel = new Label( parent, SWT.LEFT );
    secretIdLabel.setText( Messages.getString( "AWSAuthTokenInfoDialog.label_aws_secret_id" ) ); //$NON-NLS-1$
    gData = new GridData();
    secretIdLabel.setLayoutData( gData );

    Text secretIdText = new Text( parent, SWT.LEFT
                                          | SWT.SINGLE
                                          | SWT.BORDER
                                          | SWT.PASSWORD
                                          | SWT.READ_ONLY );
    secretIdText.setText( desc.getAwsSecretId() );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    secretIdText.setLayoutData( gData );

    return parent;
  }
}
