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

package eu.geclipse.ui.wizards.connection.managers.local;

import eu.geclipse.core.connection.AbstractConnectionTypeManager;
import eu.geclipse.ui.wizards.connection.IConnectionWizardPage;
import eu.geclipse.ui.wizards.connection.managers.AbstractConnectionWizardManager;

/**
 * Manages wizard pages that allow user to create new connection to local
 * filesystem
 * 
 * @author katis
 */
public class LocalConnectionWizardManager
  extends AbstractConnectionWizardManager
{

  @Override
  public void addPages()
  {
    addPage( new LocalConnectionWizardPage( Messages.getString( "LocalConnectionWizardManager.page_name" ) ) ); //$NON-NLS-1$
  }

  @Override
  public AbstractConnectionTypeManager getAbstractConnectionTypeManager( final String hostName )
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getConnectionName()
  {
    // TODO Auto-generated method stub
    return Messages.getString( "LocalConnectionWizardManager.connection_name" ); //$NON-NLS-1$
  }

  @Override
  public IConnectionWizardPage getFirstPage()
  {
    // TODO Auto-generated method stub
    return getPagesList().get( 0 );
  }
}
