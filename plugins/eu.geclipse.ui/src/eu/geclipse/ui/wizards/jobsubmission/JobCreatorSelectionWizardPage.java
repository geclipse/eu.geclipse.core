package eu.geclipse.ui.wizards.jobsubmission;

import java.util.LinkedList;
import java.util.List;
import eu.geclipse.core.model.IGridJobCreator;
import eu.geclipse.ui.wizards.wizardselection.ExtPointWizardSelectionListPage;

public class JobCreatorSelectionWizardPage extends ExtPointWizardSelectionListPage {
  protected JobCreatorSelectionWizardPage( final JobCreatorSelectionWizard parent ) {
    super( "jobCreationPage", //$NON-NLS-1$
           "eu.geclipse.ui.jobSubmissionWizard",
           getFilterList( parent ),
           "Job Creation",
           "Select the type of job to create" );
  }

  private static List<String> getFilterList( final JobCreatorSelectionWizard parent ) {
    List<String> filterList = new LinkedList<String>();
    List<IGridJobCreator> jobCreators = parent.getJobCreators();
    for ( final IGridJobCreator creator : jobCreators ) {
      String id = creator.getJobSubmissionWizardId();
      if ( id != null ) filterList.add( id );
    }
    return filterList;
  }
}
