package eu.geclipse.ui.wizards.wizardselection;

import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.swt.graphics.Image;

/**
 * Interface to be implemented by nodes to be displayed in a
 * WizardSelectionListPage.
 */
interface IWizardSelectionNode extends IWizardNode {

  /**
   * Returns the name displayed in the WizardSelectionListPage.
   * @return the name to display.
   */
  public abstract String getName();

  /**
   * Returns the icon to be displayed in the WizardSelectionListPage.
   * @return the icon to display.
   */
  public abstract Image getIcon();
}
