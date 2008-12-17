/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
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
 *    Thomas Koeckerbauer GUP, JKU - initial API and implementation
 *****************************************************************************/
package eu.geclipse.ui.wizards.wizardselection;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.swt.graphics.Image;

/**
 * Interface to be implemented by nodes to be displayed in a
 * WizardSelectionListPage.
 */
public interface IWizardSelectionNode extends IWizardNode {

  /**
   * Returns the name displayed in the WizardSelectionListPage.
   * 
   * @return the name to display.
   */
  public abstract String getName();

  /**
   * Returns the icon to be displayed in the WizardSelectionListPage.
   * 
   * @return the icon to display.
   */
  public abstract Image getIcon();

  /**
   * This method is for accessing reference to wizard that will be returned by
   * {@link IWizardNode#getWizard()} method. The difference and reason for
   * introducing this method is that calling getWizard() may result in changing
   * node's state, while this method will only return instance of node's wizard,
   * but won't change any node's fields (e.g. won't set wizard or isCreated
   * fields).
   * 
   * @return instance of IWizard that {@link IWizardNode#getWizard()} would
   *         return
   */
  public IWizard getTempWizard();
}
