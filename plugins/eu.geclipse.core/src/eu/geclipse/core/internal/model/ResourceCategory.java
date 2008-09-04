/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
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

import eu.geclipse.core.model.IGridResourceCategory;

/**
 * Internal implementation of the {@link IGridResourceCategory} interface.
 * Corresponds to the eu.geclipse.core.gridResourceCategory extension point.
 */
public class ResourceCategory implements IGridResourceCategory {
  
  /**
   * The category's name.
   */
  private String name;
  
  /**
   * The parent of this category. May be <code>null</code>.
   */
  private IGridResourceCategory parent;
  
  /**
   * The active state of this category.
   */
  private boolean isActive;
  
  /**
   * Create a new resource category.
   * 
   * @param name The category's name.
   * @param parent The parent of this category. May be <code>null</code> if this
   * is a top-level category.
   * @param isActive The active state of this category. Active categories are
   * used to query a VO's info service for resource. Passive categories only
   * serve as containers - i.e. parent - for other categories. 
   */
  public ResourceCategory( final String name,
                           final IGridResourceCategory parent,
                           final boolean isActive ) {
    this.name = name;
    this.parent = parent;
    this.isActive = isActive;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridResourceCategory#getName()
   */
  public String getName() {
    return this.name;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridResourceCategory#getParent()
   */
  public IGridResourceCategory getParent() {
    return this.parent;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridResourceCategory#isActive()
   */
  public boolean isActive() {
    return this.isActive;
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals( final Object o ) {
    
    boolean result = false;
    
    if ( o instanceof IGridResourceCategory ) {
      
      IGridResourceCategory category = ( IGridResourceCategory ) o;
      
      if ( getName().equals( category.getName() ) ) {
        
        IGridResourceCategory par = getParent();
        IGridResourceCategory oPar = category.getParent();
      
        if ( par != null ) {
          result = par.equals( oPar );
        } else if ( oPar == null ) {
          result = true;
        } 
          
      }
      
    }
    
    return result;
    
  }

}
