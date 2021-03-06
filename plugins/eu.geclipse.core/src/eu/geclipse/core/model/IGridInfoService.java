/*****************************************************************************
 * Copyright (c) 2006-2008 g-Eclipse Consortium 
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

import org.eclipse.core.runtime.IProgressMonitor;

import eu.geclipse.core.reporting.ProblemException;

/**
 * An info service is an {@link IGridService} that provides access
 * to a grid-related information database.
 */
public interface IGridInfoService extends IGridService {
  
  /**
   * Convenience method that fetches all resources for the specified VO
   * and for the specified (inclusive) category without applying a type
   * filter. So this is equivalent to
   * <p>
   * <code>fetchResources( parent, vo, category, false, null, monitor )</code>
   * 
   * @param parent The parent of the returned resources. This is the point in the
   * Grid model were the resources are hooked in.
   * @param vo The VO for which to query the information service. Only resources
   * that are accessible for the specified VO will be returned.
   * @param category The category of resources that should be fetched. This may be
   * <code>null</code>. In that case either all (if <code>exclusive</code> is false)
   * or no resources (if <code>exclusive</code> is true) will be returned.
   * @param monitor A progress monitor used to monitor this operation.
   * @return An array of Grid resources that apply to the specified arguments.
   * @throws ProblemException A problem Exception
   * @see #fetchResources(IGridContainer, IVirtualOrganization, IGridResourceCategory, boolean, Class, IProgressMonitor)
   * @see IGridResourceCategory
   */
  public IGridResource[] fetchResources( final IGridContainer parent,
                                         final IVirtualOrganization vo,
                                         final IGridResourceCategory category,
                                         final IProgressMonitor monitor ) 
  throws ProblemException;
  
  /**
   * This method fetches resources that are available for the specified VO.
   * The returned resources will belong to the specified resource category.
   * If exclusive is <code>true</code> only resources of the specified category
   * are returned, otherwise also resources of child categories are returned.
   * If a <code>typeFilter</code> is defined the returned array will only contain
   * elements of the specified type. This is a convenience functionality in order
   * to prevent invokers from stepping through the array by themselves and checking
   * each element against a specific type. 
   * 
   * @param parent The parent of the returned resources. This is the point in the
   * Grid model were the resources are hooked in.
   * @param vo The VO for which to query the information service. Only resources
   * that are accessible for the specified VO will be returned.
   * @param category The category of resources that should be fetched. This may be
   * <code>null</code>. In that case either all (if <code>exclusive</code> is false)
   * or no resources (if <code>exclusive</code> is true) will be returned.
   * @param exclusive Determines if only resources specific for the specified
   * resource category will be returned (<code>exclusive == true</code>) or if also
   * resources of child categories will be returned (<code>exclusive == false</code>).
   * @param typeFilter A filter that is applied to the returned resources. If such
   * a filter is specified callers may safely cast the elements of the result array
   * to the filtered type. This parameter may be <code>null</code>.
   * @param monitor A progress monitor used to monitor this operation.
   * @return An array of Grid resources that apply to the specified arguments.
   * Does not return <code>null</code>.
   * @throws ProblemException A problem Exception
   * @see #fetchResources(IGridContainer, IVirtualOrganization, IGridResourceCategory, IProgressMonitor)
   * @see IGridResourceCategory
   */
  public IGridResource[] fetchResources( final IGridContainer parent,
                                         final IVirtualOrganization vo,
                                         final IGridResourceCategory category,
                                         final boolean exclusive,
                                         final Class< ? extends IGridResource > typeFilter,
                                         final IProgressMonitor monitor ) 
  throws ProblemException;

}
