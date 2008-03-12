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
 *    Harald Gjermundrod - added the getHostName method
 *****************************************************************************/

package eu.geclipse.core.internal.model;

import java.net.URI;

import eu.geclipse.core.model.IGridComputing;
import eu.geclipse.core.model.IGridContainer;

public class ComputingWrapper
    extends WrappedElement
    implements IGridComputing {
  
  /**
   * The argument constructor for this class.
   * @param parent The container for this storage.
   * @param computing The computing resource that is wrapped.
   */  
  public ComputingWrapper( final IGridContainer parent,
                           final IGridComputing computing ) {
    super( parent, computing );
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.core.model.getURI#getURI()
   */
 
  public URI getURI() {
    return ( ( IGridComputing ) getWrappedElement() ).getURI();
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.core.model.IGridResource#getHostName()
   */
  public String getHostName() {
    return ( ( IGridComputing ) getWrappedElement() ).getHostName();
  }
}
