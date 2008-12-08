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
 *     PSNC: 
 *      - Katarzyna Bylec (katis@man.poznan.pl)
 *      - Szymon Mueller
 *           
 *****************************************************************************/
package eu.geclipse.servicejob.ui;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import eu.geclipse.core.model.IServiceJob;
import eu.geclipse.core.model.IServiceJobResult;
import eu.geclipse.servicejob.ui.properties.Factory;
import eu.geclipse.servicejob.ui.views.ServiceJobDetailsView;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

  /**
   * The plug-in ID
   */
  public static final String PLUGIN_ID = "eu.geclipse.servicejob.ui"; //$NON-NLS-1$
  /**
   * The shared instance
   */
  private static Activator plugin;
  private static Map<IServiceJobResult, File> serviceJobResultsVsExternalFiles = new HashMap<IServiceJobResult, File>();

  /**
   * The constructor
   */
  public Activator() {
    // empty implementation
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
   */
  @Override
  public void start( final BundleContext context ) throws Exception {
    super.start( context );
    plugin = this;
    File serviceJobsFiles = getDefault().getStateLocation()
      .append( ServiceJobDetailsView.SERVICE_JOB_FILES_FOLDER )
      .toFile();
    if( serviceJobsFiles.exists() && serviceJobsFiles.isDirectory() ) {
      boolean isEmpty = true;
      for( String fileName : serviceJobsFiles.list() ) {
        IPath path = new Path( serviceJobsFiles.getAbsolutePath() );
        path = path.append( fileName );
        if( !path.toFile().delete() ) {
          isEmpty = false;
          break;
        }
      }
      if( isEmpty ) {
        serviceJobsFiles.delete();
      }
    }
    Platform.getAdapterManager().registerAdapters( new Factory(),
                                                   IServiceJob.class );
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
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
   * Logs an exception to the eclipse logger.
   * 
   * @param exc The exception to be logged.
   */
  public static void logException( final Throwable exc ) {
    String message = exc.getLocalizedMessage();
    if( message == null ) {
      message = exc.getClass().getName();
    }
    IStatus status = new Status( IStatus.ERROR,
                                 PLUGIN_ID,
                                 IStatus.OK,
                                 message,
                                 exc );
    getDefault().getLog().log( status );
  }

  /**
   * ???
   * TODO katis
   * @param result
   * @param file
   */
  public static void addFileForServiceJobResult( final IServiceJobResult result,
                                                 final File file )
  {
    serviceJobResultsVsExternalFiles.put( result, file );
  }

  /**
   * ???
   * TODO katis
   * @param result
   * @return file
   */
  public static File getFileForServiceJobResult( final IServiceJobResult result ) {
    return serviceJobResultsVsExternalFiles.get( result );
  }
}
