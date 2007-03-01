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

package eu.geclipse.ui.wizards;

import org.eclipse.jface.wizard.IWizard;

/**
 * Interface for wizards to be listed in a WizardSelectionWizard.
 * @param <InitDataType>
 */
public interface INewWizard<InitDataType> extends IWizard {
  /**
   * Element containing the wizards attributes.
   */
  public String EXT_WIZARD = "wizard"; //$NON-NLS-1$

  /**
   * Attribute specifying the name of the wizard.
   */
  public String EXT_NAME = "name"; //$NON-NLS-1$

  /**
   * Attribute specifying the wizards class.
   */
  public String EXT_CLASS = "class"; //$NON-NLS-1$

  /**
   * Attribute specifying the wizards icon.
   */
  public String EXT_ICON = "icon"; //$NON-NLS-1$
  
  /**
   * Initialize the wizard.
   * @param initData data to initialize the wizard with.
   */
  public abstract void init( InitDataType initData );
}
