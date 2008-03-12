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

package eu.geclipse.terminal.ssh.internal;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

class SSHConnectionWizardPage extends WizardPage implements Listener {
  private SSHConnectionComposite composite;
  private String preSelectedHostname = null;

  protected SSHConnectionWizardPage( final String pageName ) {
    super( pageName );
  }

  public void createControl( final Composite parent ) {
    this.composite = new SSHConnectionComposite( parent, SWT.NONE, this, this.preSelectedHostname );
    setPageComplete( false );
    setControl( this.composite );
    IStatus status = this.composite.getStatus();
    setPageComplete( status.getSeverity() != IStatus.ERROR );
  }

  void setPreselectedHostname( final String hostname ) {
    this.preSelectedHostname  = hostname;
  }

  @Override
  public String getTitle() {
    return Messages.getString( "SSHConnectionWizardPage.ssh" ); //$NON-NLS-1$
  }

  @Override
  public String getDescription() {
    return Messages.getString( "SSHConnectionWizardPage.description" ); //$NON-NLS-1$
  }

  public void handleEvent( final Event event ) {
    validateInput();
    getWizard().getContainer().updateButtons();
  }

  private void validateInput() {
    IStatus status = this.composite.getStatus();
    applyToStatusLine( status );
    setPageComplete( status.getSeverity() != IStatus.ERROR );
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
  
  SSHConnectionInfo getConnectionInfo() {
    return this.composite.getConnectionInfo();
  }
}
