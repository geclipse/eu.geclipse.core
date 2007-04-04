package eu.geclipse.ui.wizards.jobs.wizardnodes;

import java.util.ArrayList;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.graphics.Point;


public class SpecificWizard extends Wizard implements IWizardNode {

  private ArrayList<WizardPage> internalPagesI;
  private IWizard parentWizard;
  private boolean isCreated;

  public SpecificWizard(final ArrayList<WizardPage> internalPages, final IWizard parentWizard) {
    super();
    this.internalPagesI = internalPages;
    this.parentWizard = parentWizard;
    this.addPages();
    this.isCreated = false;
   }
  
  @Override
  public boolean performFinish()
  {
    return this.parentWizard.performFinish();
  }

  public Point getExtent() {
    return null;
  }

  public IWizard getWizard() {
    return this;
  }

  public boolean isContentCreated() {
    return this.isCreated;
  }

  @Override
  public void addPages()
  {
    //tutaj parsowanie xmla... dodawanie stron i te sprawy :/
  }
  
  
  
}
