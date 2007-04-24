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

import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.actions.BuildAction;
import org.eclipse.ui.actions.RefreshAction;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import eu.geclipse.ui.internal.Activator;

/**
 * An {@link ActionGroup} that holds all build related actions.
 */
public class BuildActions extends ActionGroup {
  
  /**
   * Workbench site that is used to create the build actions.
   */
  private IWorkbenchSite site;
  
  /**
   * The build action itself.
   */
  private BuildAction buildAction;
  
  /**
   * The refresh action.
   */
  private RefreshAction refreshAction;
  
  /**
   * Construct a new build actions group for the specified
   * workbench site.
   * 
   * @param site The {@link IWorkbenchSite} for which to create
   * this build action group.
   */
  public BuildActions( final IWorkbenchSite site ) {
    
    this.site = site;
    Shell shell = site.getShell();
    ISelectionProvider provider = site.getSelectionProvider();
    
    this.buildAction = new BuildAction( shell, IncrementalProjectBuilder.INCREMENTAL_BUILD );
    this.refreshAction = new RefreshAction( shell );
    //this.refreshAction.setText( "Re&fresh@F5" );

    ImageRegistry imgReg = Activator.getDefault().getImageRegistry();
    Image image = imgReg.get( "refresh" ); //$NON-NLS-1$
    ImageDescriptor refreshImage = ImageDescriptor.createFromImage( image );
    this.refreshAction.setImageDescriptor( refreshImage );

    provider.addSelectionChangedListener( this.buildAction );
    provider.addSelectionChangedListener( this.refreshAction );
    
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.ActionGroup#dispose()
   */
  @Override
  public void dispose() {
    ISelectionProvider provider = this.site.getSelectionProvider();
    provider.removeSelectionChangedListener( this.buildAction );
    provider.removeSelectionChangedListener( this.refreshAction );
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.action.IMenuManager)
   */
  @Override
  public void fillContextMenu( final IMenuManager menu ) {
    if ( this.buildAction.isEnabled() ) {
      menu.appendToGroup( ICommonMenuConstants.GROUP_BUILD, 
                          this.buildAction );
    }
    if ( this.refreshAction.isEnabled() ) {
      menu.appendToGroup( ICommonMenuConstants.GROUP_BUILD,
                          this.refreshAction );
    }
    super.fillContextMenu(menu);
  }
  
}
