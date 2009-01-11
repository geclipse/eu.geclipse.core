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

package eu.geclipse.aws.ui.wizards;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;

import eu.geclipse.aws.auth.AWSAuthToken;
import eu.geclipse.aws.auth.AWSAuthTokenDescription;
import eu.geclipse.aws.ui.Messages;
import eu.geclipse.aws.ui.internal.AWSKeyStoreCreationOperation;
import eu.geclipse.aws.vo.AWSVirtualOrganization;
import eu.geclipse.core.auth.IAuthenticationTokenDescription;
import eu.geclipse.core.reporting.IProblem;
import eu.geclipse.ui.dialogs.ProblemDialog;
import eu.geclipse.ui.wizards.VoSelectionWizardPage;
import eu.geclipse.ui.wizards.wizardselection.IInitializableWizard;

/**
 * A Wizard providing the infrastructure to create an {@link AWSAuthToken}. The
 * data input takes place in the {@link AWSAuthTokenWizardPage}. If the Wizard
 * is not able to create a {@link AWSAuthToken} with the provided details, an
 * {@link IProblem} with the id
 * <code>eu.geclipse.aws.ec2.problem.auth.authFailed</code> is evaluated and
 * displayed in a {@link ProblemDialog}.
 * 
 * @author Moritz Post
 * @see AWSAuthTokenWizardPage
 */
public class AWSAuthTokenWizard extends Wizard implements IInitializableWizard {

  /**
   * The {@link IAuthenticationTokenDescription} to generate the
   * {@link AWSAuthToken} from.
   */
  private AWSAuthTokenDescription authTokenDesc;

  /** The wizard page, which provides the form elements. */
  private AWSAuthTokenWizardPage authTokenWizardPage;

  /** The wizard page to select a Vo. */
  private VoSelectionWizardPage voSelectionPage;

  /** A flag denoting if all the fields in the wizard are correctly filed. */
  private boolean canFinish;

  /**
   * The default constructor initializes the Wizard as unfinished.
   */
  public AWSAuthTokenWizard() {
    setCanFinish( false );
  }

  @Override
  public void addPages() {

    // show VO selection page if no vo is registered yet
    if( this.authTokenDesc != null ) {
      if( this.authTokenDesc.getAwsVo() == null ) {
        this.voSelectionPage = new VoSelectionWizardPage( false,
                                                          AWSVirtualOrganization.class );
        addPage( this.voSelectionPage );
      }
      this.authTokenWizardPage = new AWSAuthTokenWizardPage( this,
                                                             this.authTokenDesc );
      addPage( this.authTokenWizardPage );
    } else {
      this.voSelectionPage = new VoSelectionWizardPage( false,
                                                        AWSVirtualOrganization.class );
      this.authTokenWizardPage = new AWSAuthTokenWizardPage( this, null );
      addPage( this.voSelectionPage );
      addPage( this.authTokenWizardPage );
    }
    setNeedsProgressMonitor( true );
  }

  @Override
  public String getWindowTitle() {
    return Messages.getString( "AWSAuthTokenWizard.wizard_title" ); //$NON-NLS-1$
  }

  @Override
  public boolean performFinish() {

    WizardPage currentPage = ( WizardPage )getContainer().getCurrentPage();
    if( currentPage == this.authTokenWizardPage ) {
      if( !this.authTokenWizardPage.isValid() ) {
        this.authTokenWizardPage.registerFormListener();
        return false;
      }
    }
    AWSAuthTokenDescription tokenDesc = this.authTokenWizardPage.getTokenDescription();
    AWSKeyStoreCreationOperation op = new AWSKeyStoreCreationOperation( tokenDesc );

    try {
      getContainer().run( false, false, op );
    } catch( InvocationTargetException itExc ) {
      Throwable cause = itExc.getCause();
      ProblemDialog.openProblem( getShell(),
                                 Messages.getString( "AWSAuthTokenWizard.problem_dialog_title" ), //$NON-NLS-1$
                                 Messages.getString( "AWSAuthTokenWizard.problem_dialog_description" ), //$NON-NLS-1$
                                 cause );
      currentPage.setErrorMessage( cause.getLocalizedMessage() );
    } catch( InterruptedException intExc ) {
      currentPage.setErrorMessage( intExc.getLocalizedMessage() );
    }

    String errorMessage = currentPage.getErrorMessage();

    if( errorMessage == null || errorMessage.length() == 0 ) {
      return true;
    }
    return false;
  }

  public boolean init( final Object data ) {
    if( data instanceof AWSAuthTokenDescription ) {
      this.authTokenDesc = ( AWSAuthTokenDescription )data;
    }
    return true;
  }

  @Override
  public boolean canFinish() {
    return this.canFinish;
  }

  /**
   * Sets the flag whether this wizard page can be finished.
   * 
   * @param canFinish the flag to set
   */
  public void setCanFinish( final boolean canFinish ) {
    this.canFinish = canFinish;
  }

}
