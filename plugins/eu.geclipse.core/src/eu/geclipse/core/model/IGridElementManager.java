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

import org.eclipse.compare.IContentChangeNotifier;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * Base interface for all element managers. A manager is a virtual
 * grid container that is a child of the {@link IGridRoot} element
 * but is not accessible with the
 * {@link IGridRoot#getChildren(IProgressMonitor monitor)}
 * method. A manager can always manage only one type of grid elements.
 * So there are dedicated managers for different grid element, e.g.
 * {@link IVirtualOrganization}. A manager can therefore always be seen
 * as a central repository for a special type of grid elements. The
 * {@link #canManage(IGridElement)} method therefore determines the
 * type of elements that can be managed by a dedicated manager. To
 * get a certain manager make use of the getManager-methods of
 * {@link IGridRoot}.
 * 
 * Note that dedicated managers are always singletons!
 */
public interface IGridElementManager
    extends IGridContainer, IContentChangeNotifier {

  /**
   * Determines if this manager is able to manage the specified
   * grid element. A manager is always dedicated to one special
   * type of grid elements so this method has only to return true
   * for this special type and its subtypes.
   * 
   * @param element The element that wants to be managed by this
   * manager.
   * @return True if the manager is able to manage the specified
   * grid element.
   */
  public boolean canManage( final IGridElement element );
  
}
