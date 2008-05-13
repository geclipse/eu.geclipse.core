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

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.SelectionListenerAction;

import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridJob;
import eu.geclipse.core.model.IGridJobDescription;
import eu.geclipse.core.model.IGridJobService;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.ui.wizards.jobsubmission.JobCreatorSelectionWizard;

/**
 * Action dedicated to the submission of Grid jobs.
 */
public class SubmitJobAction extends SelectionListenerAction {

  /**
   * The workbench site this action belongs to.
   */
  private IWorkbenchSite site;
  private List<IGridJobDescription> jobDescriptions;

  protected SubmitJobAction( final IWorkbenchSite site ) {
    super( Messages.getString( "SubmitJobAction.title" ) ); //$NON-NLS-1$
    this.site = site;
    this.jobDescriptions = new ArrayList<IGridJobDescription>();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    if(this.jobDescriptions.size()>0)
    {
      JobCreatorSelectionWizard wizard = new JobCreatorSelectionWizard( this.jobDescriptions);
      WizardDialog dialog = new WizardDialog( this.site.getShell(), wizard );
      dialog.setBlockOnOpen( false );
      dialog.open();
    }
  }


  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org.eclipse.jface.viewers.IStructuredSelection)
   */
  @Override
  protected boolean updateSelection( final IStructuredSelection selection ) {

    boolean enabled = super.updateSelection( selection );
    this.jobDescriptions.clear();
    IGridProject project = null;
    Iterator<?> iter = selection.iterator();
    while( iter.hasNext() && enabled ) {
      Object element = iter.next();
      IGridJobDescription jobDescription = getJobDescription( element );
      enabled &= ( jobDescription != null );
      if( jobDescription != null ) {
        if( project == null ) {
          project = jobDescription.getProject();
        }
        if(project!=jobDescription.getProject()){
          //job description from different projects cannot be submitted together
          return false;
        }
        this.jobDescriptions.add( jobDescription );
      }
    }
    return enabled && ( this.jobDescriptions != null );
  }

private IGridJobDescription getJobDescription( final Object element ) {
    IGridJobDescription description = null;
    if( element instanceof IGridJobDescription ) {
      description = ( IGridJobDescription )element;
    } else if( element instanceof IGridJob ) {
      IGridJob job = ( IGridJob )element;
      description = job.getJobDescription();
    } else if ( element instanceof IAdaptable ) {
      description = ( IGridJobDescription ) ( ( IAdaptable ) element ).getAdapter( IGridJobDescription.class );
    }
    return description;
  }
}
