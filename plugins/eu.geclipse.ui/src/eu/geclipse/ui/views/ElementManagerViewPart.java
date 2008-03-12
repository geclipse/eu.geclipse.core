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
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.actions.ActionGroup;

import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridElementManager;
import eu.geclipse.core.model.IGridModelListener;
import eu.geclipse.ui.internal.actions.ActionGroupManager;
import eu.geclipse.ui.internal.actions.ViewModeActions;
import eu.geclipse.ui.providers.ConfigurableContentProvider;
import eu.geclipse.ui.providers.GridModelLabelProvider;
import eu.geclipse.ui.providers.IConfigurationListener;

/**
 * Base class for all views that want to show managed elements.
 */
public abstract class ElementManagerViewPart
    extends TreeControlViewPart
    implements IGridModelListener {
  
  TreeColumn projectColumn;
  
  /**
   * Standard creator that creates a new element manager.
   */
  protected ElementManagerViewPart() {
    IGridElementManager manager = getManager();
    manager.addGridModelListener( this );
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.compare.IContentChangeListener#contentChanged(org.eclipse.compare.IContentChangeNotifier)
   */
  /*@Override
  public void gridModelChanged( final IGridModelEvent event ) {
    refreshViewer( event.getSource() );
  }*/
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.part.WorkbenchPart#dispose()
   */
  @Override
  public void dispose() {
    IGridElementManager manager = getManager();
    manager.removeGridModelListener( this );
    super.dispose();
  }
  
  @Override
  protected void contributeAdditionalActions( final ActionGroupManager groups ) {
    ActionGroup group = new ViewModeActions( this );
    groups.addGroup( group );
  }

  /**
   * Get the manager that is used as the root element for the
   * viewer.
   * 
   * @return The root element of the viewer. Has to be an instance of
   * an {@link IGridElementManager}.
   */
  protected abstract IGridElementManager getManager();
  
  /* (non-Javadoc)
   * @see eu.geclipse.ui.views.GridModelViewPart#getRootElement()
   */
  @Override
  protected IGridElement getRootElement() {
    return getManager();
  }
  
  protected ConfigurableContentProvider createConfigurableContentProvider() {
    ConfigurableContentProvider provider
      = new ConfigurableContentProvider();
    return provider;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.ui.views.GridModelViewPart#createContentProvider()
   */
  @Override
  protected IContentProvider createContentProvider() {
    
    ConfigurableContentProvider provider
      = createConfigurableContentProvider();
    
    provider.addConfigurationListener( new IConfigurationListener() {
      
      private int lastWidth = 100;
      
      public void configurationChanged( final ConfigurableContentProvider source ) {
        if ( ElementManagerViewPart.this.projectColumn != null ) {
          int mode = source.getMode();
          if ( mode == ConfigurableContentProvider.MODE_FLAT ) {
            ElementManagerViewPart.this.projectColumn.setWidth( this.lastWidth );
          } else {
            this.lastWidth = ElementManagerViewPart.this.projectColumn.getWidth();
            ElementManagerViewPart.this.projectColumn.setWidth( 0 );
          }
        }
      }
    } );
    
    return provider;
    
  }

  /* (non-Javadoc)
   * @see eu.geclipse.ui.views.GridModelViewPart#createLabelProvider()
   */
  @Override
  protected IBaseLabelProvider createLabelProvider() {
    return new GridModelLabelProvider();
  }
  
  @Override
  protected boolean createTreeColumns( final Tree tree ) {
    
    super.createTreeColumns( tree );
    
    TreeColumn nameColumn = new TreeColumn( tree, SWT.NONE );
    nameColumn.setText( Messages.getString("ElementManagerViewPart.name_column") ); //$NON-NLS-1$
    nameColumn.setAlignment( SWT.LEFT );
    nameColumn.setWidth( 300 );
    
    this.projectColumn = new TreeColumn( tree, SWT.NONE );
    this.projectColumn.setText( Messages.getString("ElementManagerViewPart.project_column") ); //$NON-NLS-1$
    this.projectColumn.setAlignment( SWT.LEFT );
    this.projectColumn.setWidth( 100 );
    
    return true;
    
  }
  
  @Override
  public boolean isDragSource( final IGridElement element ) {
    return
      super.isDragSource( element )
      && getManager().canManage( element );
  }
  
}
