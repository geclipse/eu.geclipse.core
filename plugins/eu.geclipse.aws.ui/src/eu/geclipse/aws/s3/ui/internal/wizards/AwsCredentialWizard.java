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
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.aws.s3.ui.internal.wizards;

import org.eclipse.jface.wizard.Wizard;

import eu.geclipse.aws.s3.auth.AwsCredentialDescription;
import eu.geclipse.core.auth.AuthenticationTokenManager;
import eu.geclipse.core.auth.IAuthenticationToken;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.dialogs.ProblemDialog;
import eu.geclipse.ui.wizards.wizardselection.IInitalizableWizard;

/**
 * Wizard responsible for creating AWS credentials.
 */
public class AwsCredentialWizard
    extends Wizard
    implements IInitalizableWizard {
  
  /**
   * The creation page.
   */
  private AwsCredentialWizardPage credentialPage;
  
  /**
   * Initial description coming from token requests.
   */
  private AwsCredentialDescription initialDescription;

  @Override
  public String getWindowTitle() {
    return Messages.getString("AwsCredentialWizard.windowTitle"); //$NON-NLS-1$
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.Wizard#addPages()
   */
  @Override
  public void addPages() {
    
    this.credentialPage = new AwsCredentialWizardPage( this.initialDescription );
    addPage( this.credentialPage );
    
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.ui.wizards.wizardselection.IInitalizableWizard#init(java.lang.Object)
   */
  public boolean init( final Object data ) {
    
    boolean result = false;
    
    if ( data instanceof AwsCredentialDescription ) {
      this.initialDescription = ( AwsCredentialDescription ) data;
      result = true;
    }
    
    return result;
    
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.Wizard#performFinish()
   */
  @Override
  public boolean performFinish() {
    
    AwsCredentialDescription description
      = this.credentialPage.getCredentialDescription();
    
    AuthenticationTokenManager manager = AuthenticationTokenManager.getManager();
    IAuthenticationToken token = null;
    
    try {
    
      token = manager.createToken( description );
    
      if ( token != null ) {
        token.validate( null );
      }
      
    } catch ( ProblemException pExc ) {
      ProblemDialog.openProblem(
          getShell(),
          Messages.getString("AwsCredentialWizard.creation_error_title"), //$NON-NLS-1$
          Messages.getString("AwsCredentialWizard.creation_error_text"), //$NON-NLS-1$
          pExc );
    }
      
    return token != null;
    
  }

}
