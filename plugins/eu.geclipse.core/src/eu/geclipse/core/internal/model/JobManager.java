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

package eu.geclipse.core.internal.model;

import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridJob;
import eu.geclipse.core.model.IJobManager;

/**
 * Core implementation of an {@link IJobManager}.
 */
public class JobManager
    extends AbstractGridElementManager
    implements IJobManager {
  
  /**
   * The name of this manager.
   */
  private static final String NAME = ".jobs"; //$NON-NLS-1$
  
  /**
   * The singleton.
   */
  private static JobManager singleton;
    
  /**
   * Private constructor to ensure to have only one instance of
   * this class. This can be obtained by {@link #getManager()}.
   */
  private JobManager() {
    // empty imlementation
  }
  
  /**
   * Get the singleton instance of the <code>JobManager</code>.
   * 
   * @return The singleton.
   */
  public static JobManager getManager() {
    if ( singleton == null ) {
      singleton = new JobManager();
    }
    return singleton;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.internal.model.AbstractGridElementManager#getName()
   */
  public String getName() {
    return NAME;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElementManager#canManage(eu.geclipse.core.model.IGridElement)
   */
  public boolean canManage( final IGridElement element ) {
    return element instanceof IGridJob;
  }
  
}
