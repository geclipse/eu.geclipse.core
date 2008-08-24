/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
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
 *    Ariel Garcia - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.internal.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.SelectionListenerAction;

import eu.geclipse.core.accesscontrol.IACL;
import eu.geclipse.core.model.IProtectable;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.dialogs.AccessControlDialog;
import eu.geclipse.ui.dialogs.ProblemDialog;
import eu.geclipse.ui.internal.Activator;


/**
 * The action to access the permissions management dialog.
 * 
 * @author agarcia
 */
public class ManagePermissionsAction extends SelectionListenerAction {

  private IWorkbenchSite site;
  
  /** The <code>IProtectable</code> elements whose permissions should be managed */
  private List< IProtectable > selectedElements;
  
  
  /**
   * Constructs the action for opening the ACL editor.
   * 
   * @param site the {@link IWorkbenchPartSite} where this action will be present.
   */
  public ManagePermissionsAction( final IWorkbenchPartSite site ) {
    super( Messages.getString( "ManagePermissionsAction.title" ) ); //$NON-NLS-1$
    super.setToolTipText( Messages.getString( "ManagePermissionsAction.tooltip" ) ); //$NON-NLS-1$
    
    this.site = site;
    this.selectedElements = new ArrayList< IProtectable >();
  }
  
  
  @Override
  public void run() {
    
    if ( this.selectedElements == null ) {
      return;
    }
    
    List< IACL > aclList = new ArrayList< IACL >();
    
    try {
      Iterator< ? > it = this.selectedElements.iterator();
      IProtectable next;
      while ( it.hasNext() ) {
        next = ( IProtectable ) it.next();
        aclList.add( next.fetchACL( null ) );
      }
      
      // All elements of the list must be of the same type, so query the first
      boolean saveAsWhole = aclList.get( 0 ).canSaveWholeACL();
      AccessControlDialog dialog = new AccessControlDialog( aclList,
                                                            saveAsWhole,
                                                            this.site.getShell() );
      dialog.open();
      
    } catch ( ProblemException pe ) {
      
      ProblemDialog.openProblem(
        this.site.getShell(),
        Messages.getString("ManagePermissionsAction.error_fetching_ACL_title"), //$NON-NLS-1$
        Messages.getString("ManagePermissionsAction.error_fetching_ACL_description"), //$NON-NLS-1$
        pe );
      
      Activator.logException( pe );
    }
  
  }
  
  /*
   * (non-Javadoc)
   * @see org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org.eclipse.jface.viewers.IStructuredSelection)
   */
  @Override
  protected boolean updateSelection( final IStructuredSelection selection ) {
    
    Class< ? > type = null;
    IProtectable protectable;
    
    this.selectedElements.clear();
    boolean enabled = super.updateSelection( selection );
    
    Iterator< ? > it = selection.iterator();
    while ( enabled && it.hasNext() ) {
      
      Object next = it.next();
      protectable = null;
      
      // Connection elements do not implement IProtectable directly but adapt to it
      if ( next instanceof IProtectable ) {
        protectable = ( IProtectable ) next;
      } else if ( next instanceof IAdaptable ) {
        IAdaptable adaptable = ( IAdaptable ) next;
        protectable = ( IProtectable ) adaptable.getAdapter( IProtectable.class );
      }
      
      // Enable only if the selected elements implement the interface
      if ( protectable != null ) {
        this.selectedElements.add( protectable );
      } else {
        enabled = false;
      }
      
      // Enable only if all the selected elements are of the same type
      if ( type == null ) {
        type = next.getClass();
      } else if ( type != next.getClass() ) {
        enabled = false;
      }
    }
    
    // TODO allow editing multiple objects at the same time (if canSaveWholeACL() == true)
    if ( this.selectedElements.size() > 1 ) {
      enabled = false;
    }
    
    return enabled && ! this.selectedElements.isEmpty();
  }

}
