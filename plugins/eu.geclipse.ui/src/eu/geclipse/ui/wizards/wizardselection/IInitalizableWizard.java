package eu.geclipse.ui.wizards.wizardselection;

import org.eclipse.jface.wizard.IWizard;

public interface IInitalizableWizard extends IWizard {
  boolean init( Object data );
}
