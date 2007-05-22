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
