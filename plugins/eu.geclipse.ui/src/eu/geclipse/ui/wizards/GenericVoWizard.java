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
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.wizards;

import org.eclipse.jface.wizard.Wizard;

import eu.geclipse.core.model.impl.GenericVirtualOrganization;
import eu.geclipse.ui.wizards.wizardselection.IInitalizableWizard;

public class GenericVoWizard
    extends Wizard
    implements IInitalizableWizard {
  
  private GenericVirtualOrganization initialVo;
  
  private GenericVoWizardPage page;
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.Wizard#addPages()
   */
  @Override
  public void addPages() {
    this.page = new GenericVoWizardPage();
    if ( this.initialVo != null ) {
      this.page.setInitialVo( this.initialVo );
    }
    addPage( this.page );
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.Wizard#getWindowTitle()
   */
  @Override
  public String getWindowTitle() {
    return "Generic VO";
  }
  
  public boolean init( final Object initialData ) {
    boolean result = false;
    if ( initialData instanceof GenericVirtualOrganization ) {
      this.initialVo = ( GenericVirtualOrganization ) initialData;
      result = true;
    }
    return result;
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.Wizard#performFinish()
   */
  @Override
  public boolean performFinish() {
    return this.page.createVo();
  }
  
}
