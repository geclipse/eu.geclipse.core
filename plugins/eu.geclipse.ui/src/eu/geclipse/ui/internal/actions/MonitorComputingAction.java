package eu.geclipse.ui.internal.actions;

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

    Object element
    = getStructuredSelection().getFirstElement();
    if ( ( element != null ) && ( element instanceof IGridComputing ) ) {
      IGridComputing remote = ( IGridComputing ) element;
      try {
        this.site.getPage().showView( Activator.ID_PROCESS_STATUS,
                                     "Hallo",
                                     IWorkbenchPage.VIEW_ACTIVATE );
        
        // get reference to the current active View and add the VIEW to it
      } catch( PartInitException e ) {
        // Just ignore this exception and do not open the job
      }
      //ProcessStatView remote = this.site.getWorkbenchWindow().
      //add new monitored site
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
