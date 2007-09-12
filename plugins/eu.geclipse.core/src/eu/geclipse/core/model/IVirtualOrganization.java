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
  
  public IGridComputing[] getComputing() throws GridModelException;
  
  /**
   * Get the info service of this VO.
   * 
   * @return The info service that can be queried for VO related
   * information. 
   * @throws GridModelException If an error occures while the service
   * is fetched.
   */
  public IGridInfoService getInfoService() throws GridModelException;
  
  /**
   * Get all services that are registered within this VO.
   * 
   * @return All services that are currently available for
   * this VO.
   * @throws GridModelException If an error occures while the services
   * are fetched.
   */
  public IGridService[] getServices() throws GridModelException;
  
  public IGridStorage[] getStorage() throws GridModelException;
  
  /**
   * Get a list of all job submission services that are available for
   * this virtual organization.
   * 
   * @return A list of all currently available job submission services.
   * @throws GridModelException If an error occures while retrieving
   * the services.
   */
  public IGridJobSubmissionService[] getJobSubmissionServices() throws GridModelException;
  
  /**
   * Get a string that denotes the type of this VO. This string
   * has to be a singleton for each implementation. So each object
   * of a specific implementation has to return the same type name.
   * 
   * @return An implementation-specific type name.
   */
  public String getTypeName();
  
  /**
   * Returns the id of the wizard which should be used to edit VOs of this type.
   * @return if of the wizard to edit the VO
   */
  public String getWizardId();
}
