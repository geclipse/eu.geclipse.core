/*******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Initial development of
 * the original code was made for the g-Eclipse project founded by European
 * Union project number: FP6-IST-034327 http://www.geclipse.eu/ Contributors:
 * Harald Kornmayer - initial API and implementation
 ******************************************************************************/
package eu.geclipse.webview.views;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import eu.geclipse.webview.Activator;
import eu.geclipse.webview.preferences.PreferenceConstants;

/**
 * This sample class demonstrates how to plug-in a new workbench view. The view
 * will open the webpages that are specified in the actions.
 * 
 * @author Harald Kornmayer
 */
public class WebView extends ViewPart {

  protected Browser browser;
  protected String projectHomeUrl;
  protected String projectUserSupport;
  protected String projectVoms;
  private Action gEclipseHomeAction;
  private Action projectHomeAction;
  private Action ggusAction;
  private Action dgridAction;
  private Action back;
  private Action forward;
  private Action refresh;

  /**
   * This is a callback that will allow us to create the viewer and initialize
   * it.
   */
  @Override
  public void createPartControl( final Composite parent ) {
    // readin the Preferences from the Preference Page
    updateUrlsFromPreferences();
    try {
      this.browser = new Browser( parent, SWT.BORDER );
      this.browser.setUrl( this.projectHomeUrl );
      GridData gridData = new GridData( GridData.FILL_BOTH );
      gridData.grabExcessHorizontalSpace = true;
      gridData.grabExcessVerticalSpace = true;
      this.browser.setLayoutData( gridData );
    } catch( SWTException swtException ) {
      IStatus status = new Status( IStatus.ERROR,
                                   Activator.PLUGIN_ID,
                                   IStatus.OK,
                                   Messages.WebView_initializeError,
                                   swtException );
      Activator.getDefault().getLog().log( status );
    }
    createActions();
    contributeToActionBars();
  }

  /**
   * Leave the focus as it is
   */
  @Override
  public void setFocus() {
    // TODO
  }

  // helping methods
  // ////////////////
  /*
   * set the Browser's URL to the value defined in the action!
   */
  public void setUrl( final String url ) {
    this.browser.setUrl( url );
  }

  public void setProjectUrl(final String url){
    Preferences prefs = Activator.getDefault().getPluginPreferences();
    prefs.setValue( PreferenceConstants.PROJECT_URL, url );
    this.updateUrlsFromPreferences();
  }

  private void contributeToActionBars() {
    IActionBars bars = getViewSite().getActionBars();
    fillLocalPullDown( bars.getMenuManager() );
    fillLocalToolBar( bars.getToolBarManager() );
  }

  private void fillLocalPullDown( final IMenuManager manager ) {
    manager.add( this.gEclipseHomeAction );
    manager.add( this.projectHomeAction );
    manager.add( this.ggusAction );
    manager.add( this.dgridAction );
  }

  /**
   * Read the preferences of this plugin and update the content of the URLs
   */
  private void updateUrlsFromPreferences() {
    Preferences prefs = Activator.getDefault().getPluginPreferences();
    this.projectHomeUrl = prefs.getString( PreferenceConstants.PROJECT_URL );
    this.projectUserSupport = prefs.getString( PreferenceConstants.USERSUPPORT_URL );
    this.projectVoms = prefs.getString( PreferenceConstants.VOMS_URL );
  }

  private void fillLocalToolBar( final IToolBarManager manager ) {
    manager.add( this.refresh );
    manager.add( this.back );
    manager.add( this.forward );
  }

  /**
   * Define the actions/webpages that can be selected in the pull down menu of
   * the view toolbar
   */
  private void createActions() {
    ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
    ImageDescriptor image = sharedImages.getImageDescriptor( ISharedImages.IMG_OBJS_INFO_TSK );
    this.gEclipseHomeAction = new Action() {

      @Override
      public void run() {
        setUrl( "http://www.geclipse.eu" ); //$NON-NLS-1$
      }
    };
    this.gEclipseHomeAction.setText( Messages.WebView_g_eclipse );
    this.gEclipseHomeAction.setToolTipText( Messages.WebView_action_2_tooltip );
    this.gEclipseHomeAction.setImageDescriptor( image );
    this.projectHomeAction = new Action() {

      @Override
      public void run() {
        setUrl( WebView.this.projectHomeUrl );
      }
    };
    this.projectHomeAction.setText( Messages.WebView_project_home );
    this.projectHomeAction.setToolTipText( Messages.WebView_action_2_tooltip );
    this.projectHomeAction.setImageDescriptor( image );
    this.ggusAction = new Action() {

      @Override
      public void run() {
        setUrl( WebView.this.projectUserSupport );
      }
    };
    this.ggusAction.setText( Messages.WebView_GGUS );
    this.ggusAction.setToolTipText( Messages.WebView_GGUSshow );
    this.ggusAction.setImageDescriptor( image );
    this.dgridAction = new Action() {

      @Override
      public void run() {
        setUrl( WebView.this.projectVoms );
      }
    };
    this.dgridAction.setText( Messages.WebView_VOMS );
    this.dgridAction.setToolTipText( Messages.WebView_VOMSshow );
    this.dgridAction.setImageDescriptor( image );
    this.back = new Action() {

      @Override
      public void run() {
        WebView.this.browser.back();
      }
    };
    this.back.setText( Messages.WebView_back );
    this.back.setToolTipText( Messages.WebView_back );
    ImageDescriptor backImage = sharedImages.getImageDescriptor( ISharedImages.IMG_TOOL_BACK );
    this.back.setImageDescriptor( backImage );
    this.forward = new Action() {

      @Override
      public void run() {
        WebView.this.browser.forward();
      }
    };
    this.forward.setText( Messages.WebView_forward );
    this.forward.setToolTipText( Messages.WebView_forward );
    ImageDescriptor forwareImage = sharedImages.getImageDescriptor( ISharedImages.IMG_TOOL_FORWARD );
    this.forward.setImageDescriptor( forwareImage );
    this.refresh = new Action() {

      @Override
      public void run() {
         WebView.this.browser.refresh();
      }
    };
    this.refresh.setText( Messages.WebView_refresh );
    this.refresh.setToolTipText( Messages.WebView_refresh );
    ImageDescriptor refreshImage = sharedImages.getImageDescriptor( ISharedImages.IMG_ETOOL_HOME_NAV );
    this.refresh.setImageDescriptor( refreshImage );
  }
}