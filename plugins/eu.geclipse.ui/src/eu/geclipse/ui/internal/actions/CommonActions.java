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

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.jface.window.SameShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.dialogs.PropertyDialogAction;
import org.eclipse.ui.navigator.ICommonMenuConstants;

import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.internal.GridElementSelectionAdapter;
import eu.geclipse.ui.views.GridModelViewPart;

/**
 * Action group that holds actions that are common to all Grid
 * model views.
 */
public class CommonActions extends ActionGroup {
  
  /**
   * The refresh action.
   */
  RefreshAction refreshAction;
  
  /**
   * The {@link IWorkbenchSite} this action is associated with.
   */
  private IWorkbenchSite site;
  
  /**
   * The {@link GridElementSelectionAdapter} used to map the current selection.
   */
  private GridElementSelectionAdapter selectionAdapter;
  
  /**
   * The {@link PropertyDialogAction}.
   */
  private PropertyDialogAction propertyAction;
  
  /**
   * Construct a new <code>CommonActions</code> action group for the
   * specified {@link IWorkbenchSite}.
   * 
   * @param site The {@link IWorkbenchSite} this action is associated with.
   */
  public CommonActions( final GridModelViewPart part ) {
  
    this.site = part.getSite();
    Shell shell = this.site.getShell();
    IShellProvider shellProvider = new SameShellProvider( shell );
    ISelectionProvider selectionProvider = this.site.getSelectionProvider();
    
    this.selectionAdapter = new GridElementSelectionAdapter();
    selectionProvider.addSelectionChangedListener( this.selectionAdapter );
    
    this.propertyAction
      = new PropertyDialogAction( shellProvider, this.selectionAdapter );
    
    this.refreshAction = new RefreshAction( shell );
    this.refreshAction.setText( Messages.getString("CommonActions.refresh") ); //$NON-NLS-1$
    selectionProvider.addSelectionChangedListener( this.refreshAction );
    
    ImageRegistry imgReg = Activator.getDefault().getImageRegistry();
    Image image = imgReg.get( "refresh" ); //$NON-NLS-1$
    ImageDescriptor refreshImage = ImageDescriptor.createFromImage( image );
    this.refreshAction.setImageDescriptor( refreshImage );
    
    part.getViewer().getControl().addKeyListener( new KeyAdapter() {
      @Override
      public void keyPressed( final KeyEvent e ) {
        if ( e.keyCode == SWT.F5 ) {
          CommonActions.this.refreshAction.run();
        }
      }
    } );
    
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.ActionGroup#dispose()
   */
  @Override
  public void dispose() {
    ISelectionProvider selectionProvider = this.site.getSelectionProvider();
    selectionProvider.removeSelectionChangedListener( this.selectionAdapter );
    selectionProvider.removeSelectionChangedListener( this.refreshAction );
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.action.IMenuManager)
   */
  @Override
  public void fillContextMenu( final IMenuManager menu ) {
    if ( this.propertyAction.isEnabled() ) {
      menu.appendToGroup( ICommonMenuConstants.GROUP_PROPERTIES, 
                          this.propertyAction );
    }
    if ( this.refreshAction.isEnabled() ) {
      menu.appendToGroup( ICommonMenuConstants.GROUP_BUILD,
                          this.refreshAction );
    }
    super.fillContextMenu(menu);
  }
  
}
