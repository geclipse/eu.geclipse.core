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
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.internal;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import eu.geclipse.ui.internal.actions.UpdateJobStatusAction;
import eu.geclipse.ui.internal.preference.PerspectivePreferencePage;
import eu.geclipse.ui.properties.PropertiesAdapterFactory;


/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

  /**
   * The plug-in ID of g-Eclipse UI
   * */
  public static final String PLUGIN_ID = "eu.geclipse.ui"; //$NON-NLS-1$
  
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
   * The id of the job view.
   */
  public static final String ID_JOB_VIEW
    = "eu.geclipse.ui.views.jobView"; //$NON-NLS-1$

  /**
   * The id of the GlueInfoViewer.
   */
  public static final String ID_GLUE_INFO_VIEW
    = "eu.geclipse.info.views.GlueInfoViewer"; //$NON-NLS-1$
  
  /**
   * The id of the Grid3DView.
   */
  //public static final String ID_GRID_3D_VIEW
  //  = "eu.geclipse.gridbench.views.Grid3DView"; //$NON-NLS-1$
  
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
   * The id of the jobs view.
   */
  public static final String ID_JOBS_VIEW
    = "eu.geclipse.ui.views.jobView"; //$NON-NLS-1$

  /**
   * The id of the job details view.
   */
  public static final String ID_JOBDETAILS_VIEW
    =  "eu.geclipse.ui.views.jobdetails.JobDetailsView"; //$NON-NLS-1$
  
  /**
   * The id of the grid project wizard.
   */
  public static final String ID_PROJECT_WIZARD
    = "eu.geclipse.ui.wizards.gridProjectWizard"; //$NON-NLS-1$
  
  /**
   * The id of the connection wizard.
   */
  public static final String ID_CONNECTION_WIZARD
    = "eu.geclipse.ui.wizards.connectionWizard"; //$NON-NLS-1$
  
  /**
   * The id of the workflow wizard.
   */
  public static final String ID_WORKFLOW_WIZARD
    = "eu.geclipse.workflow.ui.part.WorkflowCreationWizardID"; //$NON-NLS-1$
  
  /**
   * The id of the Grid user perspective.
   */
  public static final String ID_USER_PERSPECTIVE
    = "eu.geclipse.ui.userPerspective"; //$NON-NLS-1$
  
  /**
   * The id of the Grid operator perspective.
   */
  public static final String ID_OPERATOR_PERSPECTIVE
    = "eu.geclipse.ui.operatorPerspective"; //$NON-NLS-1$
  
  /**
   * The id of the Grid developer perspective.
   */
  public static final String ID_DEVELOPER_PERSPECTIVE
    = "eu.geclipse.ui.developerPerspective"; //$NON-NLS-1$
  
  /**
   * The id of the Grid explorer perspective.
   */
  public static final String ID_EXPLORER_PERSPECTIVE
    = "eu.geclipse.ui.explorerPerspective"; //$NON-NLS-1$
  
  /**
   * The id of the ProcessMonitoring View.
   */
  public static final String ID_PROCESS_STATUS
    = "eu.geclipse.ui.views.ProcessStatView"; //$NON-NLS-1$
  
  /**
   * Image for action Refresh
   */
  public static final String IMG_REFRESH = "refresh"; //$NON-NLS-1$
  
  /**
   * Image for action DownloadJobOutputsAction
   */
  public static final String IMG_DOWNLOAD_JOB_OUTPUT = "DownloadJobOutput"; //$NON-NLS-1$
  
  /**
   * Image for action {@link UpdateJobStatusAction}
   */
  public static final String IMG_UPDATE_JOB_STATUS = "UpdateJobStatus";  //$NON-NLS-1$
  
  /**
   * Image for actions "See details", "see more" etc
   */
  public static final String IMG_SEE = "See"; //$NON-NLS-1$
  
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
    PropertiesAdapterFactory.register();
    setPreferencesDefaults();
  }
  
  private void setPreferencesDefaults() {
    IPreferenceStore preferenceStore = Activator.getDefault()
    .getPreferenceStore();
    preferenceStore.setDefault( PerspectivePreferencePage.KEY_NOT_SWITCH_FROM_GECLIPSE_PERSPECTIVE, true );
    preferenceStore.setDefault( PerspectivePreferencePage.KEY_DEFAULT_PERSPECTIVE, Activator.ID_USER_PERSPECTIVE );
    preferenceStore.setDefault( PerspectivePreferencePage.KEY_REMEMBER_SWITCHING, MessageDialogWithToggle.PROMPT );
  }

  /*
   * (non-Javadoc)
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
   */
  @Override
  public void stop( final BundleContext context ) throws Exception
  {
    PropertiesAdapterFactory.unregister();
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
    String[][] images = {
      { "activestate", "icons/elcl16/activate.gif" }, //$NON-NLS-1$ //$NON-NLS-2$
      { "computing", "icons/obj16/computing_obj.gif" }, //$NON-NLS-1$ //$NON-NLS-2$
      { "filesystem", "icons/obj16/filesystem_obj.gif" }, //$NON-NLS-1$ //$NON-NLS-2$
      { "geclipse_logo_prefs", "icons/extras/geclipse_logo_prefs.gif" }, //$NON-NLS-1$ //$NON-NLS-2$
      { "inactivestate", "icons/elcl16/deactivate.gif" }, //$NON-NLS-1$ //$NON-NLS-2$
      { "invalidelement", "icons/obj16/invalid_element_obj.gif" }, //$NON-NLS-1$ //$NON-NLS-2$
      { "job", "icons/obj16/job_file_obj.gif" }, //$NON-NLS-1$ //$NON-NLS-2$
      { "jobdescription", "icons/obj16/jsdl_file_obj.gif" }, //$NON-NLS-1$ //$NON-NLS-2$
//      { "lightbulb_green", "icons/lightbulb_green_16.gif" }, //$NON-NLS-1$ //$NON-NLS-2$
//      { "lightbulb_red", "icons/lightbulb_red_16.gif" }, //$NON-NLS-1$ //$NON-NLS-2$
//      { "lightbulb_white", "icons/lightbulb_white_16.gif" }, //$NON-NLS-1$ //$NON-NLS-2$
      { "refresh", "icons/elcl16/refresh_nav.gif" }, //$NON-NLS-1$ //$NON-NLS-2$
      { "service", "icons/obj16/service_obj.gif" }, //$NON-NLS-1$ //$NON-NLS-2$
      { "service_unsupported", "icons/obj16/service_unsupported_obj.gif" }, //$NON-NLS-1$ //$NON-NLS-2$
      { "storage", "icons/obj16/storage_obj.gif" }, //$NON-NLS-1$ //$NON-NLS-2$
      { "solution", "icons/obj16/quickassist_obj.gif" }, //$NON-NLS-1$ //$NON-NLS-2$
//      { "view_flat", "icons/view_flat.gif" }, //$NON-NLS-1$ //$NON-NLS-2$
//      { "view_hierarchical", "icons/view_hierarchical.gif" }, //$NON-NLS-1$ //$NON-NLS-2$
      { "virtualfile", "icons/obj16/virtual_file_obj.gif" }, //$NON-NLS-1$ //$NON-NLS-2$
      { "virtualfolder", "icons/obj16/virtual_folder_obj.gif" }, //$NON-NLS-1$ //$NON-NLS-2$
      { "vo", "icons/obj16/vo_obj.gif" }, //$NON-NLS-1$ //$NON-NLS-2$
      { "formsbackground", "icons/form_banner.gif" }, //$NON-NLS-1$ //$NON-NLS-2$
      { "configure_filters", "icons/obj16/filter_ps.gif" },  //$NON-NLS-1$ //$NON-NLS-2$
      { "configure_filters_on", "icons/obj16/filter_on.gif" },  //$NON-NLS-1$ //$NON-NLS-2$
      { "helplink", "icons/elcl16/linkto_help.gif" }, //$NON-NLS-1$ //$NON-NLS-2$
      { "calendar", "icons/calendar.gif" },  //$NON-NLS-1$//$NON-NLS-2$
      { IMG_REFRESH, "icons/eview16/refresh.gif" },  //$NON-NLS-1$
      { IMG_DOWNLOAD_JOB_OUTPUT, "icons/DownloadJobOutput.gif" }, //$NON-NLS-1$
      { IMG_UPDATE_JOB_STATUS, "icons/UpdateJobStatus.gif" }, //$NON-NLS-1$
      { IMG_SEE, "icons/obj16/see.gif" }, //$NON-NLS-1$
      { "emptyfoldermarker", "icons/obj16/ihigh_obj.gif" }  //$NON-NLS-1$//$NON-NLS-2$
    };
    
    ImageDescriptor imgDsc = null;
    for( String[] image : images ) {
      imgDsc = imageDescriptorFromPlugin( PLUGIN_ID, image[ 1 ] );
      reg.put( image[ 0 ], imgDsc );
    }
  }

  
}
