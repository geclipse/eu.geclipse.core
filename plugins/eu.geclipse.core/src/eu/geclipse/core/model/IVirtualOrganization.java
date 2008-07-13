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
 * This is the base interface that all implementations of
 * virtual organizations have to implement. It is basically
 * a combination of {@link IGridContainer} since it may hold
 * other elements (i.e. services) and {@link IStorableElement}
 * since it is a persistent element that is stored as a
 * preference.  
 */
public interface IVirtualOrganization
    extends IGridContainer, IStorableElement {
  
  /**
   * Gets the resource categories that are supported by this VO.
   * 
   * @return The {@link IGridResourceCategory} instances supported by
   * this VO.
   */
  public IGridResourceCategory[] getSupportedResources();
  
  /**
   * Gets all available resources of the specified resource category.
   *  
   * @param category The resource category.
   * @param exclusive If <code>true<code> only resources are returned that
   * directly match the specified category. Otherwise also resource are
   * returned that match any child category of the specified resource
   * category.
   * @param monitor Use to monitor the progress.
   * @return An array of available resources.
   * @throws ProblemException If for any reason the resources could not
   * be fetched from the underlying information service.
   * @see IGridInfoService#fetchResources(IGridContainer, IVirtualOrganization, IGridResourceCategory, IProgressMonitor)
   */
  public IGridResource[] getAvailableResources( final IGridResourceCategory category,
                                                final boolean exclusive,
                                                final IProgressMonitor monitor )
      throws ProblemException;
  
  /**
   * Gets the info service of this VO.
   * 
   * @return The info service that can be queried for VO related
   * information. 
   * @throws ProblemException If an error occurs while the service
   * is fetched.
   */
  public IGridInfoService getInfoService() throws ProblemException;
  
  /**
   * Gets a list of all job submission services that are available for
   * this virtual organization.
   * 
   * @param monitor Use to monitor the progress.
   * @return A list of all currently available job submission services.
   * @throws ProblemException If an error occurs while retrieving
   * the services.
   */
  public IGridJobService[] getJobSubmissionServices( final IProgressMonitor monitor ) throws ProblemException;
  
  /**
   * Gets all services that are registered within this VO.
   * 
   * @param monitor Use to monitor the progress.
   * @return All services that are currently available for
   * this VO.
   * @throws ProblemException If an error occurs while retrieving the services.
   */
  public IGridService[] getServices( final IProgressMonitor monitor ) throws ProblemException;
  
  public IGridComputing[] getComputing( final IProgressMonitor monitor ) throws ProblemException;
  
  public IGridStorage[] getStorage( final IProgressMonitor monitor ) throws ProblemException;
  
  public IGridApplicationManager getApplicationManager();
  
  /**
   * Returns a user-friendly string denoting the type of this VO. This string
   * has to be a singleton for each implementation. So each object
   * of a specific implementation has to return the same type name.
   * 
   * @return An implementation-specific type name.
   * @see #getId()
   */
  public String getTypeName();
  
  /**
   * Returns the ID of the wizard which should be used to edit VOs of this type.
   * 
   * @return ID of the wizard to edit the VO.
   */
  public String getWizardId();

  /**
   * Returns a unique identifier for the type of this VO.
   * 
   * @return The name of the class implementing this interface.
   * @see #getTypeName() 
   */
  public String getId();
}
