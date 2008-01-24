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

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.actions.BaseSelectionListenerAction;
import org.eclipse.ui.actions.OpenFileAction;

import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridJob;

/**
 * Action for opening Grid model elements.
 */
public class OpenElementAction
    extends Action
    implements ISelectionChangedListener {
  
  /**
   * Ordinary open file action.
   */
  private OpenFileAction openFileAction;
    
  /**
   * Specialised action to open Grid jobs.
   */
  private OpenJobAction openJobAction;
  
  /**
   * The currently active action.
   */
  private BaseSelectionListenerAction activeAction;
  
  /**
   * Create a new open element action for the specified workbench page.
   * 
   * @param page The {@link IWorkbenchPage} for which to create this
   * action.
   */
  protected OpenElementAction( final IWorkbenchPage page ) {
    
    super( Messages.getString("OpenElementAction.open_element_action_text") ); //$NON-NLS-1$
    
    this.openFileAction = new OpenFileAction( page );
    this.openJobAction = new OpenJobAction( page );
    
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    if ( this.activeAction != null ) {
      this.activeAction.run();
    }
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
   */
  public void selectionChanged( final SelectionChangedEvent event ) {
    ISelection selection = event.getSelection();
    if ( selection instanceof IStructuredSelection ) {
      selectionChanged( ( IStructuredSelection ) selection );
    } else {
      selectionChanged( StructuredSelection.EMPTY );
    }
  }
  
  /**
   * Called whenever the selection of the action's selection provider has changed.
   * 
   * @param selection The new selection.
   */
  public void selectionChanged( final IStructuredSelection selection ) {
    
    this.activeAction = null;
    
    if ( isGridJob( selection ) ) {
      this.activeAction = this.openJobAction;
    } else if( !selection.isEmpty() ) {
      this.activeAction = this.openFileAction;
    }
    
    if ( this.activeAction != null ) {
      this.activeAction.selectionChanged( selection );
      setEnabled( this.activeAction.isEnabled() );
    } else {
      setEnabled( false );
    }
    
  }
  
  /**
   * Determines if the specified selection is equal to one {@link IGridConnectionElement}.
   * 
   * @param selection The current selection.
   * @return True if the selection contains exactly one {@link IGridConnectionElement}.
   */
  protected boolean isGridConnection( final IStructuredSelection selection ) {
    return
      ( selection.size() == 1 )
      && ( selection.getFirstElement() instanceof IGridConnectionElement );
  }
  
  /**
   * Determines if the specified selection is equal to one {@link IGridJob}.
   * 
   * @param selection The current selection.
   * @return True if the selection contains exactly one {@link IGridJob}.
   */
  protected boolean isGridJob( final IStructuredSelection selection ) {
    return
    ( selection.size() == 1 )
    && ( selection.getFirstElement() instanceof IGridJob );
  }
    
}
