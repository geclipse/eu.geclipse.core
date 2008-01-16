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

import java.net.URI;

import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridStorage;

public class StorageWrapper
    extends WrappedElement
    implements IGridStorage {

  public StorageWrapper( final IGridContainer parent,
                         final IGridStorage storage ) {
    super( parent, storage );
  }

  public URI[] getAccessTokens() {
    return ( ( IGridStorage ) getWrappedElement() ).getAccessTokens();
  }

  public URI getURI() {
    return ( ( IGridStorage ) getWrappedElement() ).getURI();
  }

}
