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

import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridInfoService;
import eu.geclipse.core.model.IGridResourceCategory;
import eu.geclipse.core.model.IGridService;

/**
 * The {@link IAWSService} is the interface which allows to contribute an Amazon
 * Webservice (EC2, S3, DevPay...) to the AWS base Vo. The services use the
 * authentication infrastructure provided by the AWS plugin.
 * 
 * @author Moritz Post
 */
public interface IAWSService extends IGridService, IGridContainer {

  /**
   * Returns an {@link IGridInfoService} which contributes to the
   * {@link AWSInfoService}. Every element in the provided info services is
   * added to the list of services provided by the {@link AWSInfoService}.
   * Thereby it is possible to contribute to the "computing", "storage" etc
   * folders.
   * 
   * @return the {@link IGridInfoService} for the particular service
   *         implementation
   */
  public IGridInfoService getInfoService();

  /**
   * Provides an array of categories contributing to the AWS VO subfolders.
   * 
   * @return an array of categories
   */
  public IGridResourceCategory[] getSupportedResources();

}