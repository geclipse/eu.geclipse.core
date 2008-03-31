/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium
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
 *     UCY (http://www.cs.ucy.ac.cy)
 *      - Harald Gjermundrod (harald@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.batch.ui.internal;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {
  /** The plug-in ID */
  public static final String PLUGIN_ID = "eu.geclipse.batch.ui"; //$NON-NLS-1$

  /**
   * The id of the batch job view.
   */
  public static final String ID_BATCH_JOB_VIEW
    = "eu.geclipse.batch.ui.views.BatchJobView"; //$NON-NLS-1$

  /**
   * The id of the batch wizard.
   */
  public static final String ID_BATCH_WIZARD
   = "eu.geclipse.batch.ui.wizards.BatchCreationWizard"; //$NON-NLS-1$

  /**
   * The id of the context menu.
   */
  public static final String ID_BATCH_CONTEXT
   = "eu.geclipse.batch.ui.contextmenu"; //$NON-NLS-1$

  /**
   * Image for action Refresh
   */
  public static final String IMG_REFRESH = "refresh"; //$NON-NLS-1$
  
  /**
   * Image for action move job
   */
  public static final String IMG_MOVEJOB = "moveJob"; //$NON-NLS-1$

  /**
   * Image for action hold job
   */
  public static final String IMG_HOLDJOB = "holdJob"; //$NON-NLS-1$
  
  /**
   * Image for action release job
   */
  public static final String IMG_RELEASEJOB = "releaseJob"; //$NON-NLS-1$
  
  /**
   * Image for action enable resource (WN, Queue, etc)
   */
  public static final String IMG_ENABLE = "enableResource"; //$NON-NLS-1$
  
  /**
   * Image for action move job
   */
  public static final String IMG_DRAIN = "drainQueue"; //$NON-NLS-1$
  
  /**
   * Image for action move job
   */
  public static final String IMG_NEWQUEUE = "newQueue"; //$NON-NLS-1$
  
  /**
   * Image for action start a resource
   */
  public static final String IMG_START = "startResource";  //$NON-NLS-1$

  /**
   * Image for action stop a resource
   */
  public static final String IMG_STOP = "stopResource";  //$NON-NLS-1$

  /**
   * Image representing a queue
   */
  public static final String IMG_QUEUE = "queueIcon"; //$NON-NLS-1$
  
  /**
   * Image representing a computing element
   */
  public static final String IMG_COMPUTING_ELEMENT = "computingElementIcon"; //$NON-NLS-1$ 
  
  /**
   * Image representing a worker node
   */
  public static final String IMG_WORKER_NODE = "workerNodeIcon"; //$NON-NLS-1$

  /**
   * Image representing a busy arrow 1 "task"
   */
  public static final String IMG_BUSY_ARROW1 = "busyArrow1Icon"; //$NON-NLS-1$

  /**
   * Image representing a busy arrow 2 "task"
   */
  public static final String IMG_BUSY_ARROW2 = "busyArrow2Icon"; //$NON-NLS-1$

  /**
   * Image representing a busy arrow 3 "task"
   */
  public static final String IMG_BUSY_ARROW3 = "busyArrow3Icon"; //$NON-NLS-1$

  /**
   * Image representing a busy arrow 4 "task"
   */
  public static final String IMG_BUSY_ARROW4 = "busyArrow4Icon"; //$NON-NLS-1$

  /** Single plugin instance. */
  private static Activator singleton;

  /**
   * Handles Form Colors that share colors between editors.
   */
  private FormColors formColors;

  /**
   * The constructor.
   */
  public Activator() {
    if ( singleton == null ) {
      singleton = this;
    }
  }

  /*
   * (non-Javadoc)
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
   */
  @Override
  public void start( final BundleContext context ) throws Exception {
    super.start( context );
  }
  
  /*
   * (non-Javadoc)
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
   */
  @Override
  public void stop( final BundleContext context ) throws Exception {
    singleton = null;
    super.stop( context );
  }

  /**
   * Returns the shared plugin instance.
   * @return returns the shared plugin instance.
   */
  public static Activator getDefault() {
    return singleton;
  }

  /**
   * Logs an exception to the eclipse logger.
   *
   * @param exc The exception to be logged.
   */
  public static void logException( final Exception exc ) {
    String message = exc.getLocalizedMessage();
    if ( message == null )
      message = exc.getClass().getName();
    IStatus status = new Status( IStatus.ERROR,
                                 PLUGIN_ID,
                                 IStatus.OK,
                                 message,
                                 exc );
    getDefault().getLog().log( status );
  }
  
  /**
   * Logs a status object to the eclipse logger.
   * 
   * @param status The status to be logged.
   */
  public static void logStatus( final IStatus status ) {
    getDefault().getLog().log( status );
  }
  
  /**
   * Get the form colors for the specified display.
   * 
   * @param display The {@link Display} for which to create the form colors.
   * @return The {@link FormColors} for the specified display.
   */
  public FormColors getFormColors( final Display display) {
    if (this.formColors == null) {
      this.formColors = new FormColors(display);
      this.formColors.markShared();
    }
    return this.formColors;
  }  
  
  @Override
  protected void initializeImageRegistry( final ImageRegistry reg )
  {
    String prefix = "icons" + System.getProperty( "file.separator" );//$NON-NLS-1$ //$NON-NLS-2$
    
    String[][] images = {
      { IMG_REFRESH, prefix + "refresh.gif" }, //$NON-NLS-1$
      { IMG_MOVEJOB, prefix + "moveJob.gif" }, //$NON-NLS-1$
      { IMG_HOLDJOB, prefix + "holdJob.gif" }, //$NON-NLS-1$
      { IMG_RELEASEJOB, prefix + "releaseJob.gif" }, //$NON-NLS-1$
      { IMG_ENABLE, prefix + "enableCL16.gif" }, //$NON-NLS-1$
      { IMG_DRAIN, prefix + "drain_mix_yy.gif" }, //$NON-NLS-1$
      { IMG_NEWQUEUE, prefix + "queueCL16.gif" }, //$NON-NLS-1$
      { IMG_START, prefix + "start.gif" }, //$NON-NLS-1$
      { IMG_STOP, prefix + "stop.gif" }, //$NON-NLS-1$
      { IMG_QUEUE, prefix + "queue16.gif" }, //$NON-NLS-1$
      { IMG_COMPUTING_ELEMENT, prefix +"ce16.gif" }, //$NON-NLS-1$
      { IMG_WORKER_NODE, prefix + "wn16.gif" }, //$NON-NLS-1$
      { IMG_BUSY_ARROW1, prefix + "busyArrow1.gif" }, //$NON-NLS-1$
      { IMG_BUSY_ARROW2, prefix + "busyArrow2.gif" }, //$NON-NLS-1$
      { IMG_BUSY_ARROW3, prefix + "busyArrow3.gif" }, //$NON-NLS-1$
      { IMG_BUSY_ARROW4, prefix + "busyArrow4.gif" } //$NON-NLS-1$
    };
    
    ImageDescriptor imgDsc = null;
    for( String[] image : images ) {
      imgDsc = imageDescriptorFromPlugin( PLUGIN_ID, image[ 1 ] );
      reg.put( image[ 0 ], imgDsc );
    }
  }
}
