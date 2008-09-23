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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.SelectionListenerAction;

import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridJobDescription;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.internal.Activator;
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

    boolean enabled = ! selection.isEmpty() && super.updateSelection( selection );
    
    if ( enabled ) {
      this.jobDescriptions.clear();
      IGridProject project = null;
      Iterator<?> iter = selection.iterator();
      while( iter.hasNext() && enabled ) {
        Object element = iter.next();
        List<IGridJobDescription> descriptions = getJobDescriptions( element );        
        if( descriptions != null 
            && !descriptions.isEmpty() ) {
          if( project == null ) {
            project = descriptions.get( 0 ).getProject();
          }
          if(project != descriptions.get( 0 ).getProject()){
            //job description from different projects cannot be submitted together
            return false;
          }
          this.jobDescriptions.addAll( descriptions );
        } else {
          enabled = false;
        }
      }
    }
    
    return enabled && ( this.jobDescriptions != null );
    
  }

  private List<IGridJobDescription> getJobDescriptions( final Object element ) {
    List<IGridJobDescription> descriptions = null;
    if( element instanceof IGridJobDescription ) {
      descriptions = Collections.singletonList( ( IGridJobDescription )element );
    } else if( element instanceof IGridContainer ) {
      IGridContainer container = ( IGridContainer )element;
      descriptions = getDescriptionsFromContainer( container );      
    } 
    
    if( descriptions == null
        && element instanceof IAdaptable ) {
      IGridJobDescription jsdl = ( IGridJobDescription )( ( IAdaptable )element ).getAdapter( IGridJobDescription.class );
      if( jsdl != null ) {
        descriptions = Collections.singletonList( jsdl );
      }
    }
    return descriptions;
  }

  private List<IGridJobDescription> getDescriptionsFromContainer( final IGridContainer container )
  {
    List<IGridJobDescription> descriptions = null;
    if( container.isLocal() ) {
      try {
        for( IGridElement gridElement : container.getChildren( null ) ) {
          if( gridElement instanceof IGridJobDescription ) {
            if( descriptions == null ) {
              descriptions = new ArrayList<IGridJobDescription>();
            }
            descriptions.add( ( IGridJobDescription )gridElement );
          }
        }
      } catch( ProblemException exception ) {
        Activator.logException( exception );
      }
    }
    return descriptions;
  }
}
