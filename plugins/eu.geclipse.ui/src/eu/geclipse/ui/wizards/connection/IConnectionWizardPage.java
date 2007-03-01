/******************************************************************************
 * Copyright (c) 2006 g-Eclipse consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     PSNC - Katarzyna Bylec
 *           
 *****************************************************************************/

package eu.geclipse.ui.wizards.connection;

import java.net.URI;
import org.eclipse.jface.wizard.IWizardPage;
import eu.geclipse.ui.wizards.connection.managers.AbstractConnectionWizardManager;

/**
 * Abstraction of a wizard page used in {@link ConnectionWizard} and managed by ({@link AbstractConnectionWizardManager}
 * 
 * @author katis
 */
public interface IConnectionWizardPage extends IWizardPage {

  /**
   * Method to perform when wizard is closed
   * 
   * @return URI to a filesystem (connection string)
   */
  public URI finish();

  /**
   * Method to indicate that page is or is not the last page of a pages group
   * (Pages group are pages managed by one
   * {@link AbstractConnectionWizardManager}
   * 
   * @return <code>true</code> if this page is last, <code>false</code>
   *         otherwise
   */
  public boolean isLastPage();
}
