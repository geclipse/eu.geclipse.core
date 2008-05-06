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
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridJob;
import eu.geclipse.core.model.IGridJobCreator;
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
  // private List< IGridJobCreator > jobCreators;
  private List<IGridJobService> jobServices;

  protected SubmitJobAction( final IWorkbenchSite site ) {
    super( Messages.getString( "SubmitJobAction.title" ) ); //$NON-NLS-1$
    this.site = site;
    this.jobServices = new ArrayList<IGridJobService>();
    this.jobDescriptions = new ArrayList<IGridJobDescription>();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    JobCreatorSelectionWizard wizard = new JobCreatorSelectionWizard( this.jobDescriptions,
                                                                      this.jobServices );
    WizardDialog dialog = new WizardDialog( this.site.getShell(), wizard );
    dialog.setBlockOnOpen( false );
    dialog.open();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org.eclipse.jface.viewers.IStructuredSelection)
   */
  @Override
  protected boolean updateSelection( final IStructuredSelection selection ) {

    boolean enabled = super.updateSelection( selection );
    this.jobServices.clear();
    this.jobDescriptions.clear();
    IGridProject project = null;
    Iterator<?> iter = selection.iterator();
    while( iter.hasNext() && enabled ) {
      Object element = iter.next();
      IGridJobDescription jobDescription = getJobDescription( element );
      enabled &= ( jobDescription != null );
      IGridJobService[] allServices = null;
      if( jobDescription != null ) {
        if( project == null ) {
          //check project and initiate list of services
          project = jobDescription.getProject();
          try {
            allServices = project.getVO().getJobSubmissionServices( null );
            for( IGridJobService service : allServices ) {
              this.jobServices.add( service );
            }
          } catch( GridModelException e ) {
            //TODO pawelw - handle error
            return false;
          }
        }
        if(project!=jobDescription.getProject()){
          //job description from different projects cannot be submitted together
          return false;
        }
        this.jobDescriptions.add( jobDescription );
        for( int i=this.jobServices.size()-1;i>=0;i--) {
          IGridJobService service=this.jobServices.get( i );
          if( !service.canSubmit( jobDescription ) )
            this.jobServices.remove( service );
        }
      }
    }
    return enabled && ( this.jobServices != null );
  }

  // @Override
  // protected boolean updateSelection( final IStructuredSelection selection ) {
  // this.jobCreators = null;
  // this.jobDescriptions = new ArrayList< IGridJobDescription >();
  // boolean enabled = super.updateSelection( selection );
  // Iterator< ? > iter = selection.iterator();
  // while ( iter.hasNext() && enabled ) {
  // Object element = iter.next();
  // IGridJobDescription jobDescription = getJobDescription( element );
  // enabled &= ( jobDescription != null );
  // if ( jobDescription != null ) {
  // this.jobDescriptions.add( jobDescription );
  // List< IGridJobCreator > creators
  // = GridModel.getJobCreators( ( IGridJobDescription ) jobDescription );
  // if ( this.jobCreators == null ) {
  // this.jobCreators = creators;
  // } else {
  // this.jobCreators = mergeCreators( this.jobCreators, creators );
  // }
  // }
  // }
  // return enabled && ( this.jobCreators != null );
  // }
  private IGridJobDescription getJobDescription( final Object element ) {
    IGridJobDescription description = null;
    if( element instanceof IGridJobDescription ) {
      description = ( IGridJobDescription )element;
    } else if( element instanceof IGridJob ) {
      IGridJob job = ( IGridJob )element;
      description = job.getJobDescription();
    }
    return description;
  }
  // private List< IGridJobCreator > mergeCreators( final List< IGridJobCreator
  // > orig,
  // final List< IGridJobCreator > merge ) {
  // List< IGridJobCreator > result = new ArrayList< IGridJobCreator >();
  // for ( IGridJobCreator oCreator : orig ) {
  // boolean found = false;
  // for ( IGridJobCreator mCreator : merge ) {
  // if ( oCreator.getClass().equals( mCreator.getClass() ) ) {
  // found = true;
  // break;
  //        }
  //      }
  //      if ( found ) {
  //        result.add( oCreator );
  //      }
  //    }
  //    return result;
  //  }
  //  
}
