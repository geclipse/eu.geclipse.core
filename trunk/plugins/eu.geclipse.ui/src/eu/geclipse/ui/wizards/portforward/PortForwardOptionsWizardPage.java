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

package eu.geclipse.ui.wizards.portforward;

import java.util.List;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import eu.geclipse.core.portforward.IForward;

/**
 * Wizard page allowing to specify port forwards.
 */
public class PortForwardOptionsWizardPage extends WizardPage {

  private PortForwardingOptionsComposite composite;

  /**
   * Creates a new port forward wizard page.
   * @param pageName name of the wizard page.
   */
  public PortForwardOptionsWizardPage( final String pageName ) {
    super( pageName );
  }

  @Override
  public String getTitle() {
    return Messages.getString("PortForwardOptionsWizardPage.title"); //$NON-NLS-1$
  }
  
  @Override
  public String getDescription() {
    return Messages.getString("PortForwardOptionsWizardPage.description"); //$NON-NLS-1$
  }

  public void createControl( final Composite parent ) {
    this.composite = new PortForwardingOptionsComposite( parent, SWT.NONE );
    setPageComplete( true );
    setControl( this.composite );
  }

  /**
   * Returns the list of forwards defined by the user.
   * @return list of port forwards.
   */
  public List<IForward> getForwards() {
    return this.composite.getForwards();
  }
}
