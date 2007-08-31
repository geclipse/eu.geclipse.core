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
 *    Thomas Koeckerbauer GUP, JKU - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.wizards.portforward;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

class NewPortForwardDialog extends TitleAreaDialog implements Listener {
  private NewPortForwardComposite composite;
  private IForwardTableEntry forward;

  protected NewPortForwardDialog( final Shell parentShell ) {
    super( parentShell );
  }

  @Override
  public void create() {
    super.create();
    setTitle( Messages.getString( "NewPortForwardDialog.title" ) ); //$NON-NLS-1$
    getShell().setText( Messages.getString("NewPortForwardDialog.title") ); //$NON-NLS-1$
    getButton( IDialogConstants.OK_ID ).setEnabled( false );
  }

  @Override
  protected Control createDialogArea( final Composite parent ) {
    this.composite = new NewPortForwardComposite( parent, SWT.NONE, this );
    this.composite.setLayoutData( new GridData( GridData.FILL_BOTH ) );
    return this.composite;
  }

  public void handleEvent( final Event event ) {
    validateInput();
  }

  private void validateInput() {
    IStatus status = this.composite.getStatus();
    applyToStatusLine( status );
    getButton( IDialogConstants.OK_ID ).setEnabled( status.getSeverity() != IStatus.ERROR );
  }

  private void applyToStatusLine( final IStatus status ) {
    String message = status.getMessage();
    if ( message.length() == 0 ) message = null;
    switch ( status.getSeverity() ) {
      case IStatus.OK:
        setErrorMessage( null );
        setMessage( message );
        break;
      case IStatus.WARNING:
        setErrorMessage( null );
        setMessage( message, IMessageProvider.WARNING );
        break;
      case IStatus.INFO:
        setErrorMessage( null );
        setMessage( message, IMessageProvider.INFORMATION );
        break;
      default:
        setErrorMessage( message );
        setMessage( null );
        break;
    }
  }

  @Override
  protected void okPressed() {
    this.forward = this.composite.getForward();
    super.okPressed();
  }
  
  IForwardTableEntry getForward() {
    return this.forward;
  }
}
