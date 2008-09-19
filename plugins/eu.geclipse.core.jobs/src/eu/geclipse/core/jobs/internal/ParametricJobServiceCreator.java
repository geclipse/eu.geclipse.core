/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium 
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
 *     Mariusz Wojtysiak - initial API and implementation
 *     
 *****************************************************************************/
package eu.geclipse.core.jobs.internal;

import eu.geclipse.core.jobs.ParametricJobService;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.impl.AbstractGridElementCreator;
import eu.geclipse.core.reporting.ProblemException;

public class ParametricJobServiceCreator extends AbstractGridElementCreator {

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.core.model.IGridElementCreator#create(eu.geclipse.core.model.IGridContainer)
   */
  public IGridElement create( final IGridContainer parent )
    throws ProblemException
  {
    IGridElement result = null;
    Object object = getSource();
    if( object instanceof ParametricJobID ) {
      result = new ParametricJobService( ( ParametricJobID )object );
    }
    return result;
  }
}
