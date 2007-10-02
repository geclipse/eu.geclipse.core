/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium 
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
 *     PSNC: 
 *      - Katarzyna Bylec (katis@man.poznan.pl)
 *           
 *****************************************************************************/
package eu.geclipse.core.internal.model;

import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridModelEvent;
import eu.geclipse.core.model.IGridModelListener;
import eu.geclipse.core.model.IGridTest;

/**
 * Abstract class that makes some core's internal interfaces available for
 * external plug-in.
 */
public class GridTestManager
  extends
  AbstractGridElementManager implements IGridModelListener
{
  /**
   * The name of this manager.
   */
  private static final String NAME = ".tests"; //$NON-NLS-1$

  public void gridModelChanged( final IGridModelEvent event ) {
    // TODO Auto-generated method stub
    
  }

  public boolean canManage( final IGridElement element ) {
    return element instanceof IGridTest;
  }

  public String getName() {
    return NAME;
  }
}
