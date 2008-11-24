/*****************************************************************************
 * Copyright (c) 2007, 2008 g-Eclipse Consortium 
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
 *    - UCY
 *    Nikolaos Tsioutsias
 *****************************************************************************/
package eu.geclipse.info.model;

import java.util.ArrayList;

import org.eclipse.core.runtime.IProgressMonitor;

import eu.geclipse.core.model.IGridInfoService;
import eu.geclipse.core.model.IVirtualOrganization;


/**
 * This interface defines an information service. An information service can
 * be used to retrieve information like computing elements and can fill a glue
 * store will all the available information.
 * 
 * @author tnikos
 */
public interface IExtentedGridInfoService extends IGridInfoService {

  /**
   * Returns the store that holds all the glue information for sites, computing elements
   * and storage elements
   * @see eu.geclipse.glite.info.bdii.BDIIStore
   * @return an eu.geclipse.info.model.AbstractGlueStore or <code> null </code>
   */
  public AbstractGlueStore getStore();
  
  /**
   * This method begins the procedure to store the glue information in the glue store 
   * @param monitor a progress monitor or <code> null </code>
   */
  public void scheduleFetch( final IProgressMonitor monitor );
  
  /**
   * Sets the VO that the information service has
   * @param vo The Virtual Organization of the information service
   */
  public void setVO( final IVirtualOrganization vo );
  
  /**
   * Returns the type of the VO
   * @return a string with the type of the vo. It can be 
   * <code>"Voms VO"</code> or <code> "GRIA VO" </code>
   */
  public String getVoType();
  
  /**
   * Returns the list of the top tree elements that should be placed in the
   * info view.
   * @return An ArrayList<String[]> or an empty ArrayList.
   */
  public ArrayList<InfoTopTreeElement> getTopTreeElements();

}
