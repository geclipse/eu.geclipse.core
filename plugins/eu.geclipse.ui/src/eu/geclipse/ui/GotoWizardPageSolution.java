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

package eu.geclipse.ui;

import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardPage;
import eu.geclipse.core.ISolution;


public class GotoWizardPageSolution extends UISolution {
  
  private IWizardPage pageToGo;

  public GotoWizardPageSolution( final int id,
                                 final String text,
                                 final IWizardPage pageToGo ) {
    super( id, text, pageToGo.getControl().getShell() );
    this.pageToGo = pageToGo;
  }
  
  public GotoWizardPageSolution( final ISolution slave,
                                 final IWizardPage pageToGo ) {
    super( slave, pageToGo.getControl().getShell() );
    this.pageToGo = pageToGo;
  }
  
  @Override
  public void solve() {
    IWizardContainer container = this.pageToGo.getWizard().getContainer();
    IWizardPage previousPage = this.pageToGo.getPreviousPage();
    container.showPage( this.pageToGo );
    this.pageToGo.setPreviousPage( previousPage );
  }
  
}
