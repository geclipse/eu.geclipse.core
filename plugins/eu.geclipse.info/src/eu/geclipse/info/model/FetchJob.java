/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     Nikolaos Tsioutsias - University of Cyprus
 *****************************************************************************/
package eu.geclipse.info.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.IWorkbench;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.info.InfoServiceFactory;
import eu.geclipse.info.glue.GlueIndex;
import eu.geclipse.info.internal.Activator;


/**
 * This job calls all the infoservices of the existing projects and fetch the data
 * that are going to be displayed in the glue info view.
 * @author tnikos
 *
 */
public class FetchJob extends Job {

  /**
   * The constructor of the FetchJob
   * @param name The name to be displayed in the progress view
   */
  public FetchJob( final String name ) {
    super( name );
  }

  @Override
  protected IStatus run( final IProgressMonitor monitor ) {
    
    //TODO tnikos: remove the measurements
    long start = System.currentTimeMillis();
    
    GlueIndex.drop(); // Clear the glue index.
    
    Status status = new Status( IStatus.ERROR,
                                "eu.geclipse.glite.info", //$NON-NLS-1$
                                "BDII fetch from " //$NON-NLS-1$
                                    + " Failed" ); //$NON-NLS-1$
    ArrayList<IExtentedGridInfoService> infoServicesArray = null;
    infoServicesArray = InfoServiceFactory.getAllExistingInfoService();
    
    // Get the number of projects. The number is used in the monitor.
    int gridProjectNumbers = 0;
    IGridElement[] projectElements;
    try {
      projectElements = GridModel.getRoot().getChildren( null );
      for (int i=0; projectElements != null && i<projectElements.length; i++)
      {
        IGridProject igp = (IGridProject)projectElements[i];
        if (igp != null && !igp.isHidden() && igp.getVO() != null)
        {
          gridProjectNumbers++;
        }
      }
    } catch( GridModelException e ) {
      Activator.logException( e );
    }
    
    monitor.beginTask( "Retrieving information", gridProjectNumbers * 10 ); //$NON-NLS-1$
    
    // Get the information from the info systems to file the glue view.
    for (int i=0; infoServicesArray!= null && i<infoServicesArray.size(); i++)
    {
      IExtentedGridInfoService infoService = infoServicesArray.get( i );
      if (infoService != null)
      {
        infoService.scheduleFetch(monitor);
      }
    }
    
    // Notify the listeners that the info has changed.
    for (int i=0; infoServicesArray != null && i<infoServicesArray.size(); i++)
    {
      IExtentedGridInfoService infoService = infoServicesArray.get( i );
      if (infoService != null && infoService.getStore() != null)
      {
        infoService.getStore().notifyListeners( null );
      }
    }
    
    // Get the path where to write the file and write the file
    long elapsed = System.currentTimeMillis() - start;
    String myPath = Platform.getLocation().toString();
    try {
      BufferedWriter out = new BufferedWriter(new FileWriter(myPath + "/debug_fetchTotal.txt", true));
      out.write("Elapsed time to fetch the Services from bdii " + elapsed + " ms\n");
      out.close();
    } catch( IOException e ) {
      // Ignore Exception
    }
    
    monitor.done();
    status = new Status( IStatus.OK,
                         "eu.geclipse.glite.info", //$NON-NLS-1$
                         "Information data fetched successfully." ); //$NON-NLS-1$
    return status;
  }
}
