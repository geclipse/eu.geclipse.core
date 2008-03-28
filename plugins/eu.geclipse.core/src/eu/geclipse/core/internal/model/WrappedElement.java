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

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;

import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IWrappedElement;
import eu.geclipse.core.model.impl.AbstractGridElement;

public class WrappedElement
    extends AbstractGridElement
    implements IWrappedElement {
  
  private IGridContainer parent;
  
  private IGridElement wrapped;
  
  public WrappedElement( final IGridContainer parent,
                         final IGridElement wrapped ) {
    this.parent = parent;
    this.wrapped = wrapped;
  }

  public IGridElement getWrappedElement() {
    return this.wrapped;
  }

  public IFileStore getFileStore() {
    return getParent().getFileStore().getChild( getName() );
  }

  public String getName() {
    return this.wrapped.getName();
  }

  public IGridContainer getParent() {
    return this.parent;
  }

  public IPath getPath() {
    return getParent().getPath().append( getName() );
  }

  public IResource getResource() {
    return getWrappedElement().getResource();
  }

  public boolean isLocal() {
    return getWrappedElement().isLocal();
  }

}
