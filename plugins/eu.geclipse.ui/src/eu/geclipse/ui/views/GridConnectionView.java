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

package eu.geclipse.ui.views;

import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.IWorkbenchWindow;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridElementManager;
import eu.geclipse.ui.internal.actions.ActionGroupManager;
import eu.geclipse.ui.internal.actions.ConnectionViewActions;
import eu.geclipse.ui.internal.actions.NewWizardActions;
import eu.geclipse.ui.providers.ConfigurableContentProvider;
import eu.geclipse.ui.providers.ConnectionViewContentProvider;
import eu.geclipse.ui.providers.ConnectionViewLabelProvider;

public class GridConnectionView extends ElementManagerViewPart {

  @Override
  protected void contributeAdditionalActions( final ActionGroupManager groups ) {
    
    IWorkbenchSite site = getSite();
    IWorkbenchWindow window = site.getWorkbenchWindow();
    
    NewWizardActions newWizardActions = new NewWizardActions( window );
    groups.addGroup( newWizardActions );
    
    ConnectionViewActions cActions = new ConnectionViewActions( site );
    groups.addGroup( cActions );
     
    super.contributeAdditionalActions( groups );
    
  }
  
  @Override
  protected IGridElementManager getManager() {
    return GridModel.getConnectionManager();
  }
  
  @Override
  protected ConfigurableContentProvider createConfigurableContentProvider() {
    ConfigurableContentProvider provider
      = new ConnectionViewContentProvider();
    return provider;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.ui.views.GridModelViewPart#createLabelProvider()
   */
  @Override
  protected IBaseLabelProvider createLabelProvider() {
    return new ConnectionViewLabelProvider();
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.ui.views.ElementManagerViewPart#createTreeColumns(org.eclipse.swt.widgets.Tree)
   */
  @Override
  protected boolean createTreeColumns( final Tree tree ) {
    
    super.createTreeColumns( tree );
    
    TreeColumn sizeColumn = new TreeColumn( tree, SWT.NONE );
    sizeColumn.setText( Messages.getString("GridConnectionView.size_column") ); //$NON-NLS-1$
    sizeColumn.setAlignment( SWT.RIGHT );
    sizeColumn.setWidth( 100 );
    
    TreeColumn modColumn = new TreeColumn( tree, SWT.NONE );
    modColumn.setText( Messages.getString("GridConnectionView.modification_column") ); //$NON-NLS-1$
    modColumn.setAlignment( SWT.CENTER );
    modColumn.setWidth( 200 );
    
    return true;
    
  }
  
  @Override
  public boolean isDragSource( final IGridElement element ) {
    return element instanceof IGridConnectionElement;
  }
  
}
