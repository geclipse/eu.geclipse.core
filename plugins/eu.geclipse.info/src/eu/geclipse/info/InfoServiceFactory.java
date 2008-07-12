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
 *   nloulloud
 *****************************************************************************/
package eu.geclipse.info;

import java.util.ArrayList;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridInfoService;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.info.internal.Activator;


/**
 * This class is responsible for returning existing information services.
 * 
 * @author tnikos
 */
public class InfoServiceFactory {
  
  
  /**
   * Returns existing information services.
   * @return An array with all the different kind of information services found
   * in the projects. Only one of each type is returned, the first that is found
   * by browsing the grid projects. 
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
    } catch ( ProblemException e ) {
      Activator.logException( e );
    } catch ( Exception e ) {
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