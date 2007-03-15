package eu.geclipse.ui.wizards.jobsubmission;

import java.net.URL;
import java.util.List;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.Wizard;
import eu.geclipse.core.model.IGridJobCreator;
import eu.geclipse.core.model.IGridJobDescription;
import eu.geclipse.ui.internal.Activator;

public class JobCreatorSelectionWizard extends Wizard {
  
  protected List< IGridJobDescription > jobDescriptions;
  
  private final List< IGridJobCreator > jobCreators;
  
  private JobCreatorSelectionWizardPage selectionPage;
  
  public JobCreatorSelectionWizard( final List< IGridJobDescription > jobDescriptions,
                                    final List< IGridJobCreator > jobCreators ) {
    this.jobDescriptions = jobDescriptions;
    this.jobCreators = jobCreators;
    setNeedsProgressMonitor( true );
    setForcePreviousAndNextButtons( true );
    setWindowTitle( "Submit Job" );
    URL imgUrl = Activator.getDefault().getBundle().getEntry( "icons/wizban/jobsubmit_wiz.gif" ); //$NON-NLS-1$
    setDefaultPageImageDescriptor( ImageDescriptor.createFromURL( imgUrl ) );
  }

  List< IGridJobCreator > getJobCreators() {
    return this.jobCreators;
  }

  @Override
  public void addPages() {
    this.selectionPage = new JobCreatorSelectionWizardPage( this );
    this.selectionPage.setInitData( this.jobDescriptions );
    addPage( this.selectionPage );
  }

  @Override
  public boolean performFinish() {
    return false;
  }
}
