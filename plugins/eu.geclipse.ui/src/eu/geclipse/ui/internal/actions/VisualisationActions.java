/******************************************************************************
 * Copyright (c) 2006 g-Eclipse consortium
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
 *     Sylva Girtelschmid - JKU
 *****************************************************************************/
package eu.geclipse.ui.internal.actions;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.navigator.ICommonMenuConstants;


/**
 * @author sgirtel
 *
 */
public class VisualisationActions extends ActionGroup {
  
  /**
   * The workbench this action group belongs to.
   */
  private IWorkbenchPartSite site;
  
  private RenderLocalVTKPipelineAction renderLocalPipelineAction;
  private RenderRemoteVTKPipelineAction renderRemotePipelineAction;

  /**
   * @param site
   */
  public VisualisationActions(final IWorkbenchPartSite site){
    this.site = site;
    ISelectionProvider selectionProvider = site.getSelectionProvider();
    this.renderLocalPipelineAction = new RenderLocalVTKPipelineAction( site );
    this.renderRemotePipelineAction = new RenderRemoteVTKPipelineAction( site );
    selectionProvider.addSelectionChangedListener( this.renderLocalPipelineAction );
    selectionProvider.addSelectionChangedListener( this.renderRemotePipelineAction );
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.actions.ActionGroup#dispose()
   */
  @Override
  public void dispose()
  {
    ISelectionProvider selectionProvider = this.site.getSelectionProvider();
    selectionProvider.removeSelectionChangedListener( this.renderLocalPipelineAction );
    selectionProvider.removeSelectionChangedListener( this.renderRemotePipelineAction );
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.action.IMenuManager)
   */
  @Override
  public void fillContextMenu( final IMenuManager mgr )
  {
    super.fillContextMenu( mgr );
    if ( this.renderLocalPipelineAction.isEnabled() ) {
      mgr.appendToGroup( ICommonMenuConstants.GROUP_BUILD, this.renderLocalPipelineAction );
    }
    if ( this.renderRemotePipelineAction.isEnabled() ) {
      mgr.appendToGroup( ICommonMenuConstants.GROUP_BUILD, this.renderRemotePipelineAction );
    }
  }
}
