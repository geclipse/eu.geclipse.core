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

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionGroup;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridElementManager;
import eu.geclipse.core.model.IGridJobManager;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.internal.actions.ActionGroupManager;
import eu.geclipse.ui.providers.JobViewLabelProvider;
import eu.geclipse.ui.views.filters.AbstractGridViewerFilter;
import eu.geclipse.ui.views.filters.JobFiltersDialog;
import eu.geclipse.ui.views.filters.JobStatusFilter;

/**
 * Job view that shows all jobs that are currently managed by
 * the default implementation of the {@link IGridJobManager}
 * interface
 */
public class GridJobView extends ElementManagerViewPart {
  private IMemento memento;

  /* (non-Javadoc)
   * @see eu.geclipse.ui.views.GridElementManagerViewPart#getManager()
   */
  @Override
  protected IGridElementManager getManager() {
    return GridModel.getJobManager();
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.ui.views.GridModelViewPart#createLabelProvider()
   */
  @Override
  protected IBaseLabelProvider createLabelProvider() {
    return new JobViewLabelProvider();
  }
  
  
  protected boolean createTreeColumns( final Tree tree ) {
    
    super.createTreeColumns( tree );
    
    TreeColumn idColumn = new TreeColumn( tree, SWT.NONE );
    idColumn.setText( "ID" );
    idColumn.setAlignment( SWT.CENTER );
    idColumn.setWidth( 200 );
    
    TreeColumn statusColumn = new TreeColumn( tree, SWT.NONE );
    statusColumn.setText( "Status" );
    statusColumn.setAlignment( SWT.CENTER );
    statusColumn.setWidth( 100 );
    
    TreeColumn reasonColumn = new TreeColumn( tree, SWT.NONE );
    reasonColumn.setText( "Reason" );
    reasonColumn.setAlignment( SWT.CENTER );
    reasonColumn.setWidth( 100 );
    
    TreeColumn lastUpdateColumn = new TreeColumn( tree, SWT.NONE );
    lastUpdateColumn.setText( "Last update time" );
    lastUpdateColumn.setAlignment( SWT.CENTER );
    lastUpdateColumn.setWidth( 100 );
    
    return true;
    
  }  

  /* (non-Javadoc)
   * @see eu.geclipse.ui.views.ElementManagerViewPart#contributeAdditionalActions(eu.geclipse.ui.internal.actions.ActionGroupManager)
   */
  @Override
  protected void contributeAdditionalActions( ActionGroupManager groups )
  {
    groups.addGroup( createFilterActions() );
    super.contributeAdditionalActions( groups );
  }
  
  private ActionGroup createFilterActions() {
    return new ActionGroup() {

      /* (non-Javadoc)
       * @see org.eclipse.ui.actions.ActionGroup#fillActionBars(org.eclipse.ui.IActionBars)
       */
      @Override
      public void fillActionBars( IActionBars actionBars )
      {
        actionBars.getToolBarManager().add( createFilterAction() );
        super.fillActionBars( actionBars );
      }
      
    };
  }
  
  private IAction createFilterAction() {
    IAction action = new Action( "&Configure Filters..." ) {

      /* (non-Javadoc)
       * @see org.eclipse.jface.action.Action#run()
       */
      @Override
      public void run()
      {
        ViewerFilter[] filters = null;
        StructuredViewer viewer = getViewer();
        
        if( viewer != null ) {
          filters = viewer.getFilters();
          
          JobFiltersDialog dialog = new JobFiltersDialog( getSite().getShell(), filters );
          
          if( dialog.open() == Window.OK ) {
            viewer.setFilters( dialog.getFilters() );
          }
        }
        
        
        super.run();
      }      
    };    
    
    action.setImageDescriptor( Activator.getDefault().getImageRegistry().getDescriptor( "configure_filters" ) );    
    action.setToolTipText( "Configure the filters to be applied to this view" );    
    
    return action;
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.part.ViewPart#saveState(org.eclipse.ui.IMemento)
   */
  @Override
  public void saveState( IMemento memento )
  {
    StructuredViewer viewer = getViewer();
    if( viewer != null ) {
      ViewerFilter[] filters = viewer.getFilters();
      if( filters != null ) {
        for( ViewerFilter filter : filters ) {
          if( filter instanceof AbstractGridViewerFilter ) {
            ( ( AbstractGridViewerFilter )filter ).saveState( memento );
          }
        }
      }
    }
    super.saveState( memento );
  }

  /* (non-Javadoc)
   * @see org.eclipse.ui.part.ViewPart#init(org.eclipse.ui.IViewSite, org.eclipse.ui.IMemento)
   */
  @Override
  public void init( IViewSite site, IMemento memento ) throws PartInitException
  {
    this.memento = memento;
    super.init( site, memento );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.ui.views.GridModelViewPart#initViewer(org.eclipse.jface.viewers.StructuredViewer)
   */
  @Override
  protected void initViewer( StructuredViewer sViewer )
  {
    super.initViewer( sViewer );
    
    int index = 0;
    ViewerFilter[] filters = new ViewerFilter[1];
    filters[index++] = new JobStatusFilter( this.memento ).getFilter();
    sViewer.setFilters( filters );
  }
  

}
