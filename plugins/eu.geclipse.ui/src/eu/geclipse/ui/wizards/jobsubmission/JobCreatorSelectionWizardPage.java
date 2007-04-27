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
           "Select the type of job to create" );
  }

  /**
   * Find job submission wizzards that are able to create jobs using given
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
