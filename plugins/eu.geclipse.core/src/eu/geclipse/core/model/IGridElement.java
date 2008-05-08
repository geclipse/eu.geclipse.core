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

package eu.geclipse.core.model;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;

/**
 * This is the base interface of all interfaces and classes that
 * are members of a Grid model. Elements may have associated
 * <code>IResource</code>s. In that case they are called non-virtual.
 * If they have no corresponding resource the element is called
 * virtual. If the element represents a resource or at least any item
 * that is local, i.e. contained somewhere on the local machine,
 * the element itself is called to be local.
 */
public interface IGridElement
    extends IAdaptable {
  
  /**
   * Clean up all resources that this element uses internally.
   */
  public void dispose();
  
  /**
   * Get the file store that this element is associated with.
   * May return <code>null</code> if there is no such associated
   * file store. If this element is not virtual the filestore
   * corresponds to the file store of the corresponding resource.
   * 
   * @return The corresponding file store.
   */
  public IFileStore getFileStore();
  
  /**
   * Get the name of this element. The names of all children of
   * a container have to be unique within this container. If this
   * element is not virtual the name is the name of the corresponding
   * resource.
   * 
   * @return The name of this element.
   */
  public String getName();
  
  /**
   * Get the parent element of this element. The parent can only be
   * an {@link IGridContainer}. This method may not return
   * <code>null</code> since the only element that is allowed to
   * return <code>null</code> is the {@link IGridRoot} which is a
   * singleton and is internally defined within the model.
   * 
   * @return The parent container of this element.
   */
  public IGridContainer getParent();

  /**
   * Get the path of this element relative to the grid root element.
   * If this element is not virtual this has to be the path of
   * the corresponding <code>@IResource</code>.
   * 
   * @return The path of this element relative to the grid root.
   */
  public IPath getPath();

  /**
   * Get the {@link IGridProject} this element belongs to. This method
   * may return <code>null</code> if the element is not contained in
   * a project like for example an element manager and its children.
   * 
   * @return The {@link IGridProject} this element belongs to.
   */
  public IGridProject getProject();
    
  /**
   * Get the corresponding resource of this element. This may be
   * <code>null</code> if - and only if - this element is virtual.
   * 
   * @return The corresponding resource or <code>null</code> if this
   * element is virtual.
   */
  public IResource getResource();
  
  /**
   * Determines if this element is hidden. Hidden elements are not
   * shown in the Grid model views.
   * 
   * @return True if this is a hidden element, false otherwise.
   */
  public boolean isHidden();
  
  /**
   * Determines if this element is local. A local element has to be
   * any related item (not necessarily an <code>IResource</code>) that
   * is local in the means that it is located on the local machine.
   * 
   * @return True if this element is local.
   */
  public boolean isLocal();
  
  /**
   * Determines if this element is virtual. Virtual elements have no
   * corresponding resources. If this method returns <code>false</code>
   * the {@link #getResource()} method returns <code>null</code>. If
   * this method returns <code>true</code> the {@link #getResource()}
   * has to return a valid <code>IResource</code> that is related to
   * this element.
   * 
   * @return True if this element is virtual, i.e. has no corresponding
   * resource.
   */
  public boolean isVirtual();
  
}
