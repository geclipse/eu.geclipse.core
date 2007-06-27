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

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import eu.geclipse.core.model.IGridJobCreator;
import eu.geclipse.ui.wizards.wizardselection.ExtPointWizardSelectionListPage;

public class JobCreatorSelectionWizardPage
  extends ExtPointWizardSelectionListPage
{

  static final String EXTPOINT_NAME = "eu.geclipse.ui.jobSubmissionWizard";
  static final String EXT_CLASS = "class"; //$NON-NLS-1$
  static final String EXT_WIZARD = "wizard"; //$NON-NLS-1$
  static final String EXT_JOBCREATOR = "job_creator"; //$NON-NLS-1$

  protected JobCreatorSelectionWizardPage( final JobCreatorSelectionWizard parent )
  {
    super( "jobCreationPage", //$NON-NLS-1$
           "eu.geclipse.ui.jobSubmissionWizard",
           getFilterList( parent ),
           "Job Creation",
           "Select the type of job to create",
           "No job creators for this type of job description available." );
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
    Map<String, String> wizardCreators = getWizardCreators();
    List<String> filterList = new LinkedList<String>();
    List<IGridJobCreator> jobCreators = parent.getJobCreators();
    for( final IGridJobCreator creator : jobCreators ) {
      String creatorId = creator.getClass().getName();
      String wizardId = wizardCreators.get( creatorId );
      if( wizardId != null ) {
        filterList.add( wizardId );
      }
      // String id = creator.getJobSubmissionWizardId();
      // if ( id != null ) filterList.add( id );
    }
    return filterList;
  }

  /**
   * @return
   */
  private static Map<String, String> getWizardCreators() {
    Hashtable<String, String> data = new Hashtable<String, String>();
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
        data.put( creator, wizard );
      }
    }
    return data;
  }
}
