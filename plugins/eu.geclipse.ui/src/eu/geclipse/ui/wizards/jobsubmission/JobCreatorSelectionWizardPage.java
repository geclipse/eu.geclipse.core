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
 *     Pawel Wolniewicz - PSNC
 *****************************************************************************/

package eu.geclipse.ui.wizards.jobsubmission;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import eu.geclipse.core.model.IGridJobCreator;
import eu.geclipse.ui.wizards.wizardselection.ExtPointWizardSelectionListPage;

/**
 *
 */
public class JobCreatorSelectionWizardPage
  extends ExtPointWizardSelectionListPage
{

  static final String EXTPOINT_NAME = "eu.geclipse.ui.jobSubmissionWizard"; //$NON-NLS-1$
  static final String EXT_CLASS = "class"; //$NON-NLS-1$
  static final String EXT_WIZARD = "wizard"; //$NON-NLS-1$
  static final String EXT_JOBCREATOR = "job_creator"; //$NON-NLS-1$

  protected JobCreatorSelectionWizardPage( final JobCreatorSelectionWizard parent )
  {
    super( "jobCreationPage", //$NON-NLS-1$
           "eu.geclipse.ui.jobSubmissionWizard", //$NON-NLS-1$
           getFilterList( parent ),
           false,
           Messages.getString("JobCreatorSelectionWizardPage.wizardJobCreatorTitle"), //$NON-NLS-1$
           Messages.getString("JobCreatorSelectionWizardPage.wizardJobCreatorDescription"), //$NON-NLS-1$
           Messages.getString("JobCreatorSelectionWizardPage.wizardJobCreatorNoneSelectedMsg") ); //$NON-NLS-1$
  }

  /**
   * Find job submission wizards that are able to create jobs using given
   * creators
   * 
   * @param parent
   * @return list with wizzard names
   */
  private static List<String> getFilterList( final JobCreatorSelectionWizard parent )
  {
    List<String> filteredWizardsList = new LinkedList<String>();
    List<JobSubmissionWizard> allWizards = getJobSubmissionWizards();
    
    // TODO mariusz Get only job creators for selected VO (if it's possible)
    
    for( IGridJobCreator jobCreator : parent.getJobCreators() ) {
      for( JobSubmissionWizard wizard : allWizards ) {
        if( wizard.creatorId.equals( jobCreator.getClass().getName() ) ) {
          filteredWizardsList.add( wizard.wizardId );
        }
      }      
    }
    
    return filteredWizardsList;
  }

  static class JobSubmissionWizard {
    String wizardId;
    String creatorId;
    
    JobSubmissionWizard( final String wizardId, final String creatorId ) {
      super();
      this.wizardId = wizardId;
      this.creatorId = creatorId;
    }
  }
  
  /**
   * @return
   */
  private static List<JobSubmissionWizard> getJobSubmissionWizards() {
    List<JobSubmissionWizard> wizards = new ArrayList<JobSubmissionWizard>(); 
    IExtensionRegistry registry = Platform.getExtensionRegistry();
    IExtensionPoint extensionPoint = registry.getExtensionPoint( EXTPOINT_NAME );
    if( extensionPoint != null ) {
      IExtension[] extensions = extensionPoint.getExtensions();
      for( IExtension extension : extensions ) {
        // read data from single extension
        String wizard = null;
        String creator = null;
        IConfigurationElement[] elements = extension.getConfigurationElements();
        for( IConfigurationElement element : elements ) {
          // check if job creator from extension point is allowed
          if( EXT_JOBCREATOR.equals( element.getName() ) ) {
            creator = element.getAttribute( EXT_CLASS );
          }
          if( EXT_WIZARD.equals( element.getName() ) ) {
            wizard = element.getAttribute( EXT_CLASS );
          }
        }
        
       
        wizards.add( new JobSubmissionWizard( wizard, creator ) );
      }
    }
    return wizards;
  }
}
