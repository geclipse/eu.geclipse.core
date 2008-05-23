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
 *   nloulloud
 *****************************************************************************/
package eu.geclipse.info;

import java.util.ArrayList;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridInfoService;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.info.internal.Activator;
import eu.geclipse.info.model.IExtentedGridInfoService;

/**
 * This class is responsible for returning existing information services.
 * @author tnikos
 *
 */
public class InfoServiceFactory {
  
  
  /**
   * Returns existing information services.
   * @return An array with all the information services found in the projects. Only one of 
   * each type is returned. 
   */
  public ArrayList<IGridInfoService> getAllExistingInfoService()
  {
    ArrayList<IGridInfoService> infoServiceArray = new ArrayList<IGridInfoService>();
    IGridInfoService infoService = null;
 
    IGridElement[] projectElements;
    try {
      projectElements = GridModel.getRoot().getChildren( null );
      for (int i=0; projectElements != null && i<projectElements.length; i++)
      {
        IGridProject igp = (IGridProject)projectElements[i];
        if (igp != null && !igp.isHidden() && igp.getVO() != null)
        {
          infoService = igp.getVO().getInfoService();
          if (infoService != null && !exists(infoServiceArray, infoService))
            infoServiceArray.add( infoService );
        }
      }
    } catch( GridModelException e ) {
      Activator.logException( e );
    }
    
    return infoServiceArray;
  }
  
  private boolean exists(final ArrayList<IGridInfoService> infoServiceArray,
                         final IGridInfoService infoService)
  {
    boolean result = false;
    for (int i=0; infoServiceArray!= null 
                  && i<infoServiceArray.size()
                  && !result; i++)
    {
      if (infoServiceArray.get( i ).getClass().equals( infoService.getClass() ))
        result = true;
    }
    
    return result;
  }
}