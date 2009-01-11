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
 *    Moritz Post - initial API and implementation
 *****************************************************************************/

package eu.geclipse.aws;

import java.net.URL;

import eu.geclipse.core.model.IGridElementCreator;

/**
 * This interface acts as a gateway to an service creator. The decisive factor
 * is to fetch the services name and the corresponding service {@link URL}. A
 * common scenario is to extract these details from this
 * <code>eu.geclipse.core.gridElementCreator</code> extension point defining
 * the {@link IGridElementCreator}.
 * 
 * @author Moritz Post
 */
public interface IAWSServiceCreator extends IGridElementCreator {

  /**
   * Set the name of the service creator.
   * 
   * @param name the name to set
   */
  public void setName( String name );

  /**
   * Get the name of the service creator
   * 
   * @return the name of the creator
   */
  public String getName();

  /**
   * Set the url the service interacts with.
   * 
   * @param url the url to set
   */
  public void setServiceURL( String url );

  /**
   * Gets the service url of the service to be created.
   * 
   * @return the service url to get
   */
  public String getServiceURL();
}
