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
 *    Martin Polak GUP, JKU - initial implementation
 *****************************************************************************/
package eu.geclipse.ui.internal.actions;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.SelectionListenerAction;
import eu.geclipse.core.model.IGridComputing;
import eu.geclipse.core.model.IGridJob;
import eu.geclipse.core.model.IGridStorage;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.views.ProcessStatView;
import eu.geclipse.ui.views.jobdetails.JobDetailsView;

/**
 * Contributes an action to the context menu of a GridProject that allows the user
 * to select a computing resource to be monitored and add it to the ProcessStatView. 
 * @author mpolak
 * 
 */
public class MonitorComputingAction extends SelectionListenerAction {
  /**
   * The workbench site this action belongs to.
   */
  private IWorkbenchSite site;
  /**
   * Get the structured selection.
   */
  private IStructuredSelection selection;

  /**
   * The computing ressources elements to be monitored
   */
  private List< IGridComputing > sources
    = new ArrayList< IGridComputing >();
  /**
   * Create an action.
   * 
   * @param site The {@link IWorkbenchSite} to create this action for.
   */
  public MonitorComputingAction( final IWorkbenchSite site ) {
    super( "Monitor Computing Ressource" ); //$NON-NLS-1$
    this.setEnabled( false );
    this.site = site;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run()
  {
    this.selection = this.getStructuredSelection();
    ProcessStatView pviewer = null;

    Object element
    = this.selection.getFirstElement();
    if ( ( element != null ) && ( element instanceof IGridComputing ) ) {
      IGridComputing remote = ( IGridComputing ) element;
      try {
        pviewer = (ProcessStatView)this.site.getPage().showView( Activator.ID_PROCESS_STATUS,
                                     "Monitoring Your CE",
                                     IWorkbenchPage.VIEW_ACTIVATE );
        
        
        URI targeturi = new URI( "gsiftp://"+remote.getName()); //$NON-NLS-1$
        URI connecturi = new URI(targeturi.getScheme(),targeturi.getUserInfo(),targeturi.getHost(), 2811,null, null, null); //$NON-NLS-1$
        pviewer.addSite(connecturi);
        
      } catch( PartInitException e ) {
        // Just ignore this exception and do not open the job
      } catch( URISyntaxException e ) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org.eclipse.jface.viewers.IStructuredSelection)
   */
  @Override
  protected boolean updateSelection( 
  final IStructuredSelection structuredSelection )
  {
    this.setEnabled( true );
    boolean enabled = super.updateSelection( structuredSelection );
    
    
    List< ? > selectionList
    = structuredSelection.toList();
    this.sources.clear();
    for ( Object obj : selectionList ) {
      if ( obj instanceof IGridComputing) {
        IGridComputing computing = ( IGridComputing ) obj;
        this.sources.add( computing );
      }
    }
    return !this.sources.isEmpty();  //we found at least one CE to add
  }
}
