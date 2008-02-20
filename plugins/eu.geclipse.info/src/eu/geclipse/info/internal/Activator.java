/******************************************************************************
 * Copyright (c) 2007,2008 g-Eclipse consortium
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
 *     UCY (http://www.ucy.cs.ac.cy)
 *      - George Tsouloupas (georget@cs.ucy.ac.cy)
 *
 *****************************************************************************/

package eu.geclipse.info.internal;

import java.util.ArrayList;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.osgi.framework.BundleContext;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.info.IGlueStoreChangeListerner;
import eu.geclipse.info.InfoServiceFactory;
import eu.geclipse.info.glue.AbstractGlueTable;
import eu.geclipse.info.model.IExtentedGridInfoService;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends Plugin {

  /** The plug-in ID */
  public static final String PLUGIN_ID = "eu.geclipse.info"; //$NON-NLS-1$

  // The shared instance
  private static Activator plugin;

  /**
   * The constructor
   */
  public Activator() {
    plugin = this;
    
    Job storeInitializeJob=new Job("Retrieving information"){ //$NON-NLS-1$
      
      @Override
      protected IStatus run( final IProgressMonitor monitor )
      {
        ArrayList<IExtentedGridInfoService> infoServicesArray = null;
        infoServicesArray = InfoServiceFactory.getAllExistingInfoService();
        
        // Get the number of projects. The number is used in the monitor.
        int gridProjectNumbers = 0;
        ArrayList<String> existingVoTypes = new ArrayList<String>();
        IGridElement[] projectElements;
        try {
          projectElements = GridModel.getRoot().getChildren( null );
          for (int i=0; projectElements != null && i<projectElements.length; i++)
          {
            IGridProject igp = (IGridProject)projectElements[i];
            if (igp!= null && !igp.isHidden())
            {
              String voTypeName = igp.getVO().getTypeName();
              if ( !existingVoTypes.contains( voTypeName ))
              {
                gridProjectNumbers++;
                existingVoTypes.add(voTypeName);
              }
            }
          }
        } catch( GridModelException e ) {
          Activator.logException( e );
        }
        
        // Set the monitor task. Each task has 10 work units.
        monitor.beginTask( "Retrieving information", gridProjectNumbers * 10 ); //$NON-NLS-1$
        
        for (int i=0; infoServicesArray != null && i<infoServicesArray.size(); i++)
        {
          IExtentedGridInfoService infoService = infoServicesArray.get( i );
          infoService.scheduleFetch(monitor);
          if (infoService.getStore() != null)
          {
            infoService.getStore().addListener( new IGlueStoreChangeListerner(){
              public void infoChanged( final ArrayList<AbstractGlueTable> modifiedGlueEntries ) {
                //Do nothing, just listen so the the glueStore starts fetching
              }
            }, null );
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
        
        monitor.done();
        
        return new Status(Status.OK,"eu.geclipse.glite.info","BDII initialized");  //$NON-NLS-1$  //$NON-NLS-2$
      }
      
    };
    storeInitializeJob.schedule( );
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
   */
  @Override
  public void start( final BundleContext context ) throws Exception {
    super.start( context );
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
   */
  @Override
  public void stop( final BundleContext context ) throws Exception {
    plugin = null;
    super.stop( context );
  }

  /**
   * Returns the shared instance
   * 
   * @return the shared instance
   */
  public static Activator getDefault() {
    return plugin;
  }

  /**
   * Logs an exception.
   * 
   * @param exception the exception.
   */
  public static void logException( final Exception exception ) {
    String message = exception.getLocalizedMessage();
    if ( message == null ) message = exception.getClass().getName();
    IStatus status = new Status( IStatus.ERROR,
                                 PLUGIN_ID,
                                 IStatus.OK,
                                 message,
                                 exception );
    getDefault().getLog().log( status );
  }
}
