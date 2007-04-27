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
 *          - Mateusz Pabis
 *           
 *****************************************************************************/

package eu.geclipse.ui.wizards.connection.managers.gridftp;

import java.net.URI;
import org.eclipse.jface.wizard.Wizard;
import eu.geclipse.ui.wizards.connection.ConnectionWizard;
import eu.geclipse.ui.wizards.wizardselection.IInitalizableWizard;

/**
 * Manages wizard pages that allow user to create new gridFTP connection
 * 
 * @author katis
 */
public class GridFTPConnectionWizard extends Wizard implements IInitalizableWizard {
  private GridFTPConnectionWizardPage page;
  private ConnectionWizard wizard;

  @Override
  public void addPages() {
    this.page = new GridFTPConnectionWizardPage( Messages.getString( "GridFTPConnectionWizard.wizard_page_name" ) ); //$NON-NLS-1$
    addPage( this.page );
  }

  @Override
  public String getWindowTitle() {
    return Messages.getString("GridFTPConnectionWizard.windowTitle"); //$NON-NLS-1$
  }

  @Override
  public boolean performFinish() {
    URI fileSystemUri = this.page.finish();
    return this.wizard.commonPerformFinish( fileSystemUri );
  }

  public boolean init( final Object data ) {
    boolean result = false;
    if ( data instanceof ConnectionWizard ) {
      this.wizard = ( ConnectionWizard ) data;
      result = true;
    }
    return result;
  }
}
