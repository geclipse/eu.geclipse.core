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

package eu.geclipse.core.model.impl;

import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridElementCreator;
import eu.geclipse.core.reporting.ProblemException;

/**
 * Abstract implementation of the {@link IGridElementCreator}
 * interface.
 */
public abstract class AbstractGridElementCreator
    implements IGridElementCreator {
  
  /**
   * The source object for the element creation.
   */
  private Object internalSource;
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElementCreator#create(eu.geclipse.core.model.IGridContainer, java.lang.Object)
   */
  public IGridElement create( final IGridContainer parent, final Object source ) throws ProblemException {
    setSource( source );
    return create( parent );
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElementCreator#getSource()
   */
  public Object getSource() {
    return this.internalSource;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElementCreator#setSource(java.lang.Object)
   */
  public void setSource( final Object source ) {
    this.internalSource = source;
  }
  
}
