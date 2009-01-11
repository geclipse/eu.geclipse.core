package eu.geclipse.aws.ui.wizards;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;

import eu.geclipse.aws.ui.Messages;
import eu.geclipse.aws.vo.AWSVirtualOrganization;
import eu.geclipse.ui.wizards.wizardselection.IInitializableWizard;

/**
 * This {@link Wizard} creates a new {@link AWSVirtualOrganization}.
 * 
 * @author Moritz Post
 */
public class AWSVoWizard extends Wizard implements IInitializableWizard {

  /** The {@link WizardPage} providing the form elements. */
  private AWSVoWizardPage wizardPage;

  /** The initial VO. */
  private AWSVirtualOrganization initialVo;

  @Override
  public boolean performFinish() {
    return this.wizardPage.createVo();
  }

  public boolean init( final Object data ) {
    if( data instanceof AWSVirtualOrganization ) {
      this.initialVo = ( AWSVirtualOrganization )data;
      return true;
    }
    return false;
  }

  @Override
  public String getWindowTitle() {
    return Messages.getString( "AWSVoWizard.wizard_title" ); //$NON-NLS-1$
  }

  @Override
  public void addPages() {
    this.wizardPage = new AWSVoWizardPage();

    if( this.initialVo != null ) {
      this.wizardPage.setInitialVo( this.initialVo );
    }
    addPage( this.wizardPage );
  }
}
