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

package eu.geclipse.ui.internal.actions;

import java.net.URL;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.providers.ConfigurableContentProvider;
import eu.geclipse.ui.providers.IConfigurationListener;
import eu.geclipse.ui.views.ElementManagerViewPart;

/**
 * Action for settings the view mode of an {@link ElementManagerViewPart}.
 */
public class ViewModeAction
    extends Action
    implements IConfigurationListener {
  
  static final String VIEW_FLAT_IMAGE = "icons/obj16/view_flat.gif"; //$NON-NLS-1$

  static final String VIEW_HIERARCHICAL_IMAGE = "icons/obj16/view_hierarchical.gif"; //$NON-NLS-1$
  
  /**
   * The view for which to set the mode.
   */
  private ElementManagerViewPart view;
  
  /**
   * The view mode to be set on the view.
   */
  private int viewMode;
  
  /**
   * Create a new view mode action for the specified view.
   * 
   * @param name The name of the action.
   * @param viewMode The view mode this action stands for, i.e. either
   * {@link ConfigurableContentProvider#MODE_FLAT} or
   * {@link ConfigurableContentProvider#MODE_HIERARCHICAL}.
   * @param view The view for which to set the view mode. 
   */
  protected ViewModeAction( final String name,
                            final int viewMode,
                            final ElementManagerViewPart view ) {
    super( name );
    
    this.view = view;
    this.viewMode = viewMode;
    updateActionState();
    
    URL imgURL = null;
    if ( viewMode == ConfigurableContentProvider.MODE_FLAT ) {
      imgURL = Activator.getDefault().getBundle().getEntry( ViewModeAction.VIEW_FLAT_IMAGE );
    } else if ( viewMode == ConfigurableContentProvider.MODE_HIERARCHICAL ) {
      imgURL = Activator.getDefault().getBundle().getEntry( ViewModeAction.VIEW_HIERARCHICAL_IMAGE );
    }
    
    if ( imgURL != null ) {
      ImageDescriptor descriptor = ImageDescriptor.createFromURL( imgURL );
      setImageDescriptor( descriptor );
    }
    
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.ui.providers.IConfigurationListener#configurationChanged(eu.geclipse.ui.providers.ConfigurableContentProvider)
   */
  public void configurationChanged( final ConfigurableContentProvider source ) {
    if ( source == getContentProvider() ) {
      updateActionState();
    }
  }
 
  /* (non-Javadoc)
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    ConfigurableContentProvider contentProvider
      = getContentProvider();
    if ( contentProvider != null ) {
      contentProvider.setMode( this.viewMode );
      this.view.refreshViewer();
    }
    updateActionState();
  }
  
  /**
   * Get the content provider of the {@link ElementManagerViewPart}.
   * 
   * @return The element managers content provider or <code>null</code>
   * if it has no content provider or the content provider is not a
   * {@link ConfigurableContentProvider}.
   */
  public ConfigurableContentProvider getContentProvider() {
    StructuredViewer viewer = this.view.getViewer();
    ConfigurableContentProvider result = null;
    IContentProvider contentProvider = viewer.getContentProvider();
    if ( contentProvider instanceof ConfigurableContentProvider ) {
      result = ( ConfigurableContentProvider ) contentProvider;
    }
    return result;
  }
  
  /**
   * Get the current mode of the views content provider.
   * 
   * @return The current view mode.
   */
  protected int getProviderMode() {
    int result = 0;
    ConfigurableContentProvider contentProvider
      = getContentProvider();
    if ( contentProvider != null ) {
      result = contentProvider.getMode();
    }
    return result;
  }
  
  /**
   * Update the state of this action.
   */
  protected void updateActionState() {
    int pMode = getProviderMode();
    setChecked( pMode == this.viewMode );
  }
  
}
