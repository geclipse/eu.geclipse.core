package eu.geclipse.ui;

import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardPage;
import eu.geclipse.core.ISolution;


public class GotoWizardPageSolution extends UISolution {
  
  private IWizardPage pageToGo;

  public GotoWizardPageSolution( final int id,
                                 final String text,
                                 final IWizardPage pageToGo ) {
    super( id, text, pageToGo.getControl().getShell() );
    this.pageToGo = pageToGo;
  }
  
  public GotoWizardPageSolution( final ISolution slave,
                                 final IWizardPage pageToGo ) {
    super( slave, pageToGo.getControl().getShell() );
    this.pageToGo = pageToGo;
  }
  
  @Override
  public void solve() {
    IWizardContainer container = this.pageToGo.getWizard().getContainer();
    IWizardPage previousPage = this.pageToGo.getPreviousPage();
    container.showPage( this.pageToGo );
    this.pageToGo.setPreviousPage( previousPage );
  }
  
}
