package eu.geclipse.ui.wizards.connection.managers.srm;

import java.net.URI;
import org.eclipse.jface.wizard.Wizard;
import eu.geclipse.ui.wizards.connection.ConnectionWizard;
import eu.geclipse.ui.wizards.wizardselection.IInitalizableWizard;


public class SRMConnectionWizard extends Wizard implements IInitalizableWizard {

  private ConnectionWizard wizard;
  private SRMConnectionWizardPage page;
  
  public boolean init( final Object data ) {
    boolean result = false;
    if ( data instanceof ConnectionWizard ) {
      this.wizard = ( ConnectionWizard ) data;
      result = true;
    }
    return result;
  }

  @Override
  public void addPages()
  {
    this.page = new SRMConnectionWizardPage("name");
    this.addPage( this.page );
  }
  
  @Override
  public boolean performFinish() {
    URI fileSystemUri = this.page.finish();
    return this.wizard.commonPerformFinish( fileSystemUri );
  }
  
  
}
