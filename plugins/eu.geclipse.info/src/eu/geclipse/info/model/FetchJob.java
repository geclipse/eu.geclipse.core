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

import java.util.ArrayList;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridInfoService;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.info.InfoCacheListenerHandler;
import eu.geclipse.info.InfoServiceFactory;
import eu.geclipse.info.glue.GlueIndex;
import eu.geclipse.info.internal.Activator;


/**
 * This job calls all the infoservices of the existing projects and fetch the data
 * that are going to be displayed in the glue info view.
 * @author tnikos
 *
 */
public class FetchJob extends Job{

  private static FetchJob instance = null;
  
  /**
   * The constructor of the FetchJob
   * @param name The name to be displayed in the progress view
   */
  private FetchJob( final String name ) {
    super( name );
  }

  /**
   * Get a singleton instance of FetchJob
   * @param name the name that will be displayed in the progress view
   * @return a FetchJob
   */
  public static FetchJob getInstance(final String name)
  {
    if (instance == null)
      instance = new FetchJob(name);
    
    return instance;
    
  }
  
  @Override
  protected IStatus run( final IProgressMonitor monitor ) {
   
    GlueIndex.drop(); // Clear the glue index.
    GlueIndex.getInstance(); // Initialize the glue index and set the listener.
    
    IProgressMonitor localMonitor
    = ( monitor != null )
      ? monitor
      : new NullProgressMonitor();
    
    /*
    Status status = new Status( IStatus.ERROR,
                                "eu.geclipse.glite.info", //$NON-NLS-1$
                                "BDII fetch from " //$NON-NLS-1$
                                    + " Failed" ); //$NON-NLS-1$
    */
    ArrayList<IGridInfoService> infoServicesArray = null;
    InfoServiceFactory myInfoServiceFactory = new InfoServiceFactory();
    infoServicesArray = myInfoServiceFactory.getAllExistingInfoService();
    
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
    
    localMonitor.beginTask( "Retrieving information", gridProjectNumbers * 10 ); //$NON-NLS-1$
    
    // Get the information from the info systems to file the glue view.
    for (int i=0; infoServicesArray!= null && !localMonitor.isCanceled() && i<infoServicesArray.size() ; i++)
    {
      if (infoServicesArray.get( i ) instanceof IExtentedGridInfoService)
      {
        IExtentedGridInfoService infoService = ( IExtentedGridInfoService )infoServicesArray.get( i );
        if (infoService != null)
        {
          infoService.scheduleFetch(localMonitor);
        }
      }
    }
    
    /*
    // Notify the listeners that the info has changed.
    for (int i=0; infoServicesArray != null && i<infoServicesArray.size(); i++)
    {
      if ( infoServicesArray.get( i ) instanceof IExtentedGridInfoService)
      {
        IExtentedGridInfoService infoService = ( IExtentedGridInfoService )infoServicesArray.get( i );
        if (infoService != null && infoService.getStore() != null)
        {
          hasNotified = true;
          infoService.getStore().notifyListeners( null );
        }
      }
    }
    
    // If no other info service has been notified that the glue store has changed we send a 
    // notification to the listeners of FetchJob. 
    if (!hasNotified)
      this.notifyListeners( null );
    */
    
    InfoCacheListenerHandler.getInstance().notifyListeners( );
    
    localMonitor.done();
    Status status = new Status( IStatus.OK,
                         "eu.geclipse.glite.info", //$NON-NLS-1$
                         "Information data fetched successfully." ); //$NON-NLS-1$
    return status;
  }
}
