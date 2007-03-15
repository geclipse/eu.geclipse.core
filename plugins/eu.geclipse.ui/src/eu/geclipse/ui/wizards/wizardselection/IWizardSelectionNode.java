package eu.geclipse.ui.wizards.wizardselection;

import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.swt.graphics.Image;

interface IWizardSelectionNode extends IWizardNode {

  public abstract String getName();

  public abstract Image getIcon();
}