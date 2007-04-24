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
 *     Mathias Stuempert - FZK
 *     Pawel Wolniewicz - PSNC
 *****************************************************************************/

package eu.geclipse.ui.internal.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.SelectionListenerAction;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridJobCreator;
import eu.geclipse.core.model.IGridJobDescription;
import eu.geclipse.ui.wizards.jobsubmission.JobCreatorSelectionWizard;

/**
 * Action dedicated to the submission of Grid jobs.
 */
public class SubmitJobAction extends SelectionListenerAction {
  
  /**
   * The workbench site this action belongs to.
   */
  private IWorkbenchSite site;
  
  private List< IGridJobDescription > jobDescriptions;
  
  private List< IGridJobCreator > jobCreators;

  protected SubmitJobAction( final IWorkbenchSite site ) {
    super( Messages.getString( "SubmitJobAction.title" ) ); //$NON-NLS-1$
    this.site = site;
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    JobCreatorSelectionWizard wizard
      = new JobCreatorSelectionWizard( this.jobDescriptions, this.jobCreators );
    WizardDialog dialog = new WizardDialog( this.site.getShell(), wizard );
    dialog.open();
  }

  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org.eclipse.jface.viewers.IStructuredSelection)
   */
  @Override
  protected boolean updateSelection( final IStructuredSelection selection ) {
    this.jobCreators = null;
    this.jobDescriptions = new ArrayList< IGridJobDescription >();
    boolean enabled = super.updateSelection( selection );
    Iterator< ? > iter = selection.iterator();
    while ( iter.hasNext() && enabled ) {
      Object element = iter.next();
      boolean isDescription = isJobDescription( element );
      enabled &= isDescription;
      if ( isDescription ) {
        this.jobDescriptions.add( ( IGridJobDescription ) element );
        List< IGridJobCreator > creators
          = GridModel.getJobCreators( ( IGridJobDescription ) element );
        if ( this.jobCreators == null ) {
          this.jobCreators = creators;
        } else {
          this.jobCreators = mergeCreators( this.jobCreators, creators );
        }
      }
    }
    return enabled && ( this.jobCreators != null );
  }
  
  protected boolean isJobDescription( final Object element ) {
    return element instanceof eu.geclipse.core.model.IGridJobDescription;
  }
  
  private List< IGridJobCreator > mergeCreators( final List< IGridJobCreator > orig,
                                                 final List< IGridJobCreator > merge ) {
    List< IGridJobCreator > result = new ArrayList< IGridJobCreator >();
    for ( IGridJobCreator oCreator : orig ) {
      boolean found = false;
      for ( IGridJobCreator mCreator : merge ) {
        if ( oCreator.getClass().equals( mCreator.getClass() ) ) {
          found = true;
          break;
        }
      }
      if ( found ) {
        result.add( oCreator );
      }
    }
    return result;
  }
  
}
