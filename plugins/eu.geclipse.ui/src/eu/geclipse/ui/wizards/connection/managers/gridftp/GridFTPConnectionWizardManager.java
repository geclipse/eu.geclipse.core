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

import eu.geclipse.core.connection.AbstractConnectionTypeManager;
import eu.geclipse.ui.wizards.connection.IConnectionWizardPage;
import eu.geclipse.ui.wizards.connection.managers.AbstractConnectionWizardManager;

/**
 * Manages wizard pages that allow user to create new gridFTP connection
 * 
 * @author katis
 */
public class GridFTPConnectionWizardManager
  extends AbstractConnectionWizardManager
{

  /**
   * Creates new instance of this class
   */
  public GridFTPConnectionWizardManager() {
    // empty constructor
  }

  @Override
  public String getConnectionName()
  {
    return Messages.getString( "GridFTPConnectionWizardManager.connection_name" ); //$NON-NLS-1$
  }

  @Override
  public IConnectionWizardPage getFirstPage()
  {
    return getPagesList().get( 0 );
  }

  @Override
  public void addPages()
  {
    addPage( new GridFTPConnectionWizardPage( Messages.getString( "GridFTPConnectionWizardManager.wizard_page_name" ) ) ); //$NON-NLS-1$
  }

  /**
   * delete this method - from AbstractConnectionTypeManager
   */
  @Override
  public AbstractConnectionTypeManager getAbstractConnectionTypeManager( final String hostName )
  {
    // TODO Auto-generated method stub
    return null;
  }
}
