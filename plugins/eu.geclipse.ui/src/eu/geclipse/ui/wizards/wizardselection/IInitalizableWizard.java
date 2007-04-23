package eu.geclipse.ui.wizards.wizardselection;

import org.eclipse.jface.wizard.IWizard;

/**
 * Interface for wizards that have to be initialized after
 * creation.
 */
public interface IInitalizableWizard extends IWizard {

  /**
   * This method is called after the creation of the wizard.
   * @param data data object passed to the wizard for the 
   *             initialization.
   * @return true if initialization was successful,
   *         false otherwise.
   */
  boolean init( Object data );
}
