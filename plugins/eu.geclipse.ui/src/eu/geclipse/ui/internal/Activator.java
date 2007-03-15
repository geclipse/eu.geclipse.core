/******************************************************************************
  * Copyright (c) 2006 g-Eclipse consortium
  * All rights reserved. This program and the accompanying materials
  * are made available under the terms of the Eclipse Public License v1.0
  * which accompanies this distribution, and is available at
  * http://www.eclipse.org/legal/epl-v10.html
  *
  * Initialial development of the original code was made for
  * project g-Eclipse founded by European Union
  * project number: FP6-IST-034327  http://www.geclipse.eu/
  *
  * Contributor(s):
  *     FZK (http://www.fzk.de)
  *      - Mathias Stuempert (mathias.stuempert@iwr.fzk.de)
  *****************************************************************************/

package eu.geclipse.ui.internal;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.preference.IPreferenceStore;
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

  /** The plug-in ID of g-Eclipse UI */
  public static final String PLUGIN_ID = "eu.geclipse.ui"; //$NON-NLS-1$
  
  public static final String VOS_PREFERENCE = "vos"; //$NON-NLS-1$
  public static final String SEL_VO_PREFERENCE = "vosel"; //$NON-NLS-1$
  
  public static final String DEFAULT_VOS = ""; //$NON-NLS-1$
  public static final String DEFAULT_SEL_VO = ""; //$NON-NLS-1$
  
  /**
   * The id of the id project view.
   */
  public static final String ID_GPROJECT_VIEW
    = "eu.geclipse.ui.views.gridProjectView"; //$NON-NLS-1$
  
  /**
   * The id of the authentication token management view.
   */
  public static final String ID_AUTH_VIEW
    = "eu.geclipse.ui.views.authenticationTokenView"; //$NON-NLS-1$
  
  /**
   * The id of the connection view.
   */
  public static final String ID_CONNECTION_VIEW
    = "eu.geclipse.ui.views.connectionView"; //$NON-NLS-1$
  
  /**
   * The id of the gExplorer view.
   */
  public static final String ID_GEXPLORER_VIEW
    = "eu.geclipse.ui.views.gExplorer"; //$NON-NLS-1$
  
  /**
   * The id of the GlueInfoViewer.
   */
  public static final String ID_GLUE_INFO_VIEW
    = "eu.geclipse.gridbench.views.GlueInfoViewer"; //$NON-NLS-1$
  
  /**
   * The id of the Grid3DView.
   */
  public static final String ID_GRID_3D_VIEW
    = "eu.geclipse.gridbench.views.Grid3DView"; //$NON-NLS-1$
  
  /**
   * The id of the GVid view.
   */
  public static final String ID_GVID_VIEW 
    = "eu.geclipse.gvid.views.GVidView"; //$NON-NLS-1$
  
  /**
   * The id of the terminal view.
   */
  public static final String ID_TERMINAL_VIEW
    = "eu.geclipse.terminal.views.TerminalView"; //$NON-NLS-1$
  
  /**
   * The id of the web view.
   */
  public static final String ID_WEB_VIEW
    = "eu.geclipse.webview.views.WebView"; //$NON-NLS-1$
  
  /**
   * The id of the grid project wizard.
   */
  public static final String ID_PROJECT_WIZARD
    = "eu.geclipse.ui.wizards.gridProjectWizard"; //$NON-NLS-1$
  
  /**
   * The id of the grid job wizard.
   */
  public static final String ID_JOB_WIZARD
    = "eu.geclipse.ui.wizards.gridJobWizard"; //$NON-NLS-1$
  
  /**
   * The id of the connection wizard.
   */
  public static final String ID_CONNECTION_WIZARD
    = "eu.geclipse.ui.wizards.connectionWizard"; //$NON-NLS-1$
  
  /**
   * The shared instance.
   */
  private static Activator plugin;
  

  /**
   * Handles Form Colors that share colors between editors.
   */
  private FormColors formColors;
  
  
  /**
   * The constructor
   */
  public Activator() {
    plugin = this;
  }
  
  /*
   * (non-Javadoc)
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
   */
  @Override
  public void start( final BundleContext context ) throws Exception
  {
    super.start( context );
  }
  
  /*
   * (non-Javadoc)
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
   */
  @Override
  public void stop( final BundleContext context ) throws Exception
  {
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
    if ( message == null ) message = exc.getClass().getName();
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
  
  @Override
  protected void initializeDefaultPreferences( final IPreferenceStore store )
  {
    store.setDefault( VOS_PREFERENCE, DEFAULT_VOS );
    store.setDefault( SEL_VO_PREFERENCE, DEFAULT_SEL_VO );
  }
  
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
    String[][] images = {
      { "activestate", "icons/elcl16/activate.gif" }, //$NON-NLS-1$ //$NON-NLS-2$
      { "computing", "icons/obj16/computing_obj.gif" }, //$NON-NLS-1$ //$NON-NLS-2$
      { "filesystem", "icons/obj16/filesystem_obj.gif" }, //$NON-NLS-1$ //$NON-NLS-2$
      { "geclipse_logo_prefs", "icons/extras/geclipse_logo_prefs.gif" }, //$NON-NLS-1$ //$NON-NLS-2$
      { "inactivestate", "icons/elcl16/deactivate.gif" }, //$NON-NLS-1$ //$NON-NLS-2$
      { "invalidelement", "icons/obj16/invalid_element_obj.gif" }, //$NON-NLS-1$ //$NON-NLS-2$
//      { "lightbulb_green", "icons/lightbulb_green_16.gif" }, //$NON-NLS-1$ //$NON-NLS-2$
//      { "lightbulb_red", "icons/lightbulb_red_16.gif" }, //$NON-NLS-1$ //$NON-NLS-2$
//      { "lightbulb_white", "icons/lightbulb_white_16.gif" }, //$NON-NLS-1$ //$NON-NLS-2$
      { "refresh", "icons/elcl16/refresh_nav.gif" }, //$NON-NLS-1$ //$NON-NLS-2$
      { "service", "icons/obj16/service_obj.gif" }, //$NON-NLS-1$ //$NON-NLS-2$
      { "storage", "icons/obj16/storage_obj.gif" }, //$NON-NLS-1$ //$NON-NLS-2$
      { "solution", "icons/obj16/quickassist_obj.gif" }, //$NON-NLS-1$ //$NON-NLS-2$
//      { "view_flat", "icons/view_flat.gif" }, //$NON-NLS-1$ //$NON-NLS-2$
//      { "view_hierarchical", "icons/view_hierarchical.gif" }, //$NON-NLS-1$ //$NON-NLS-2$
      { "virtualfile", "icons/obj16/virtual_file_obj.gif" }, //$NON-NLS-1$ //$NON-NLS-2$
      { "virtualfolder", "icons/obj16/virtual_folder_obj.gif" }, //$NON-NLS-1$ //$NON-NLS-2$
      { "vo", "icons/obj16/vo_obj.gif" } //$NON-NLS-1$ //$NON-NLS-2$
    };
    
    ImageDescriptor imgDsc = null;
    for( String[] image : images ) {
      imgDsc = imageDescriptorFromPlugin( PLUGIN_ID, image[ 1 ] );
      reg.put( image[ 0 ], imgDsc );
    }
  }

}
