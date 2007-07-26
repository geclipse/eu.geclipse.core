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
 *    George Tsouloupas - initial API and implementation
 *    Mathias Stuempert - architectural redesign and documentation
 *****************************************************************************/

package eu.geclipse.info.model;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridResource;
import eu.geclipse.core.model.impl.AbstractGridElement;
import eu.geclipse.info.glue.AbstractGlueTable;

/**
 * Wrapper class for {@link AbstractGlueTable} objects to be integrated
 * in the {@link eu.geclipse.core.model.GridModel}. This wrapper class
 * is marked as abstract. Instead of using it directly the developer should
 * implement subclasses that are specific for a certain glue element type.  
 */
public abstract class GridGlueElement
    extends AbstractGridElement
    implements IGridResource {
  
  /**
   * The parent of this element.
   */
  private IGridContainer parent;
  
  /**
   * The associated glue table.
   */
  private AbstractGlueTable glueTable;
  
  /**
   * Construct a new <code>GridGlueElement</code> with the specified
   * parent and associated glue table.
   * 
   * @param parent The parent of this element.
   * @param glueTable The associated {@link AbstractGlueTable}.
   */
  public GridGlueElement( final IGridContainer parent,
                          final AbstractGlueTable glueTable ) {
    this.parent = parent;
    this.glueTable = glueTable;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getFileStore()
   */
  public IFileStore getFileStore() {
    return null;
  }
  
  /**
   * Get this <code>GridGlueElement</code>'s associated
   * {@link AbstractGlueTable} object.
   * 
   * @return The associated glue object.
   */
  public AbstractGlueTable getGlueElement() {
    return this.glueTable;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getName()
   */
  public String getName() {
    return this.glueTable.getID();
  }
  
  public IGridContainer getParent() {
    return this.parent;
  }

  public IPath getPath() {
    return getParent().getPath().append( getName() );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getResource()
   */
  public IResource getResource() {
    return null;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#isLocal()
   */
  public boolean isLocal() {
    return false;
  }

}
