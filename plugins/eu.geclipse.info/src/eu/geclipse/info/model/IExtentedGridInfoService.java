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
 *    George Tsouloupas - initial API and implementation
 *    Mathias Stuempert - architectural redesign and documentation
 *****************************************************************************/
package eu.geclipse.info.model;

import org.eclipse.core.runtime.IProgressMonitor;

import eu.geclipse.core.model.IGridInfoService;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.info.IGlueInfoStore;


public interface IExtentedGridInfoService extends IGridInfoService {

  public IGlueInfoStore getStore();
  
  public void scheduleFetch( final IProgressMonitor monitor );
  
  public void setVO( final IVirtualOrganization vo );
  
  public String getVoType();
}
