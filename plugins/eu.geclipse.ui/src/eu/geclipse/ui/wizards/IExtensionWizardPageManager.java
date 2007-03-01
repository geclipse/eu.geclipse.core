/******************************************************************************
  * Copyright (c) 2006 g-Eclipse consortium
  * All rights reserved. This program and the accompanying materials
  * are made available under the terms of the Eclipse Public License v1.0
  * which accompanies this distribution, and is available at
  * http://www.eclipse.org/legal/epl-v10.html
  *
  * Initialial development of the original code was made for
  * project g-Eclipse founded by European Union
  * project number: FP6-IST-034327  http://www.geclipse.eu/
  *
  * Contributor(s):
  *     FZK (http://www.fzk.de)
  *      - Mathias Stuempert (mathias.stuempert@iwr.fzk.de)
  *****************************************************************************/

package eu.geclipse.ui.wizards;

import java.util.List;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardPage;

/**
 * This interface is used in conjunction with the {@link ExtensionWizard}
 * and {@link ExtensionWizardFirstPage} to define wizard contributions
 * of extension point that should be handles with these classes.
 * 
 * @author stuempert-m
 */
public interface IExtensionWizardPageManager {
  
  public boolean setInitialData( final Object initialData );
  
  /**
   * Get a list of the pages that are needed to define the extension.
   * 
   * @return A list of wizard pages that will show up in the
   * {@link ExtensionWizard} in the order they are defined in this list
   * if the corresponding extension is selected.
   */
  public List< IWizardPage > getPages();
  
  /**
   * Determines of the {@link ExtensionWizard} can be finished with the
   * specified page.
   * 
   * @param currentPage The page that is currently displayed in the wizard.
   * @return True if the wizard can be finished.
   */
  public boolean canFinish( final IWizardPage currentPage );
  
  /**
   * Perform any special processing at the end of the wizard's lifecycle.
   * 
   * @param container The <code>IWizardContainer</code> that can be used
   * to run a job with progress.
   * @return True if the processing was successful and the wizard can be finished.
   */
  public boolean performFinish( final IWizardContainer container );
  
}
