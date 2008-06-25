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

package eu.geclipse.core.model;

/**
 * Grid resources are grouped within so called resource categories.
 * This interface is the base of this mechanism. Resource categories
 * are either top level or are children of other categories. Furthermore
 * they may be active in which case they are used to query resources with
 * an information system or they are passive in which case they just
 * serve as container for other categories.
 * 
 * Resource categories can be defined with the eu.geclipse.core.gridResourceCategory
 * extension point.
 */
public interface IGridResourceCategory {

  /**
   * Get the name of this resource category. This name will be displayed
   * in the resource tree of a grid project.
   * 
   * @return The categories name.
   */
  public String getName();

  /**
   * Get the parent category or <code>null</code> if this is a top-level
   * category.
   * 
   * @return The parent category of this category or <code>null</code>.
   */
  public IGridResourceCategory getParent();
  
  /**
   * Returns <code>true</code> if this category is an active category.
   * In this case this category is used to query the information
   * system for available resources. If this category is passive it is
   * only used as a container for other categories.
   * 
   * @return <code>true</code> if this category is active.
   */
  public boolean isActive();

}
