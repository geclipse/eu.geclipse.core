package eu.geclipse.ui.wizards;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.actions.WorkspaceModifyDelegatingOperation;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.eclipse.ui.dialogs.WizardNewProjectReferencePage;
import eu.geclipse.ui.internal.Activator;

public class NewGridProjectWizard extends Wizard implements INewWizard {
  
  private WizardNewProjectCreationPage creationPage;
  
  private VoSelectionWizardPage voPage;
  
  private WizardNewProjectReferencePage referencePage;
  
  private IProject project = null;

  @Override
  public boolean performFinish() {
    IProject proj = createNewProject();
    return proj != null;
  }

  public void init( final IWorkbench workbench, final IStructuredSelection selection ) {
    setWindowTitle( Messages.getString("NewGridProjectWizard.windowTitle") ); //$NON-NLS-1$
    URL imgUrl = Activator.getDefault().getBundle().getEntry( "icons/wizban/newgridprj_wiz.gif" ); //$NON-NLS-1$
    setDefaultPageImageDescriptor( ImageDescriptor.createFromURL( imgUrl ) );
  }
  
  @Override
  public void addPages() {
    
    this.creationPage = new WizardNewProjectCreationPage( "newGridProjectBasicPage" ); //$NON-NLS-1$
    this.creationPage.setTitle( Messages.getString( "NewGridProjectWizard.basic_page_title" ) ); //$NON-NLS-1$
    this.creationPage.setDescription( Messages.getString( "NewGridProjectWizard.basic_page_description" ) ); //$NON-NLS-1$
    addPage( this.creationPage );
    
    this.voPage = new VoSelectionWizardPage();
    addPage( this.voPage );
    
    if ( ResourcesPlugin.getWorkspace().getRoot().getProjects().length > 0 ) {
      this.referencePage = new WizardNewProjectReferencePage( "newGridProjectReferencePage" ); //$NON-NLS-1$
      this.referencePage.setTitle( Messages.getString( "NewGridProjectWizard.reference_page_title" ) ); //$NON-NLS-1$
      this.referencePage.setDescription( Messages.getString( "NewGridProjectWizard.reference_page_description" ) ); //$NON-NLS-1$
      addPage( this.referencePage );
    }
    
  }
  
  @Override
  public boolean canFinish() {
    boolean result = false;
    IWizardPage currentPage = getContainer().getCurrentPage();
    if ( currentPage != this.creationPage ) {
      result = super.canFinish();
    }
    return result;
  }
  
  private IProject createNewProject() {
    
    if ( ( this.project == null ) && ( this.creationPage != null ) ) {
    
      GridProjectProperties properties = new GridProjectProperties();
      properties.setProjectName( this.creationPage.getProjectName() );
      properties.setProjectLocation( this.creationPage.useDefaults() ? null : this.creationPage.getLocationPath() );
      properties.setProjectVo( this.voPage.getSelectedVo() );
      if ( this.referencePage != null ) {
        properties.setReferencesProjects( this.referencePage.getReferencedProjects() );
      }
      
      GridProjectCreationOperation creationOp = new GridProjectCreationOperation( properties );
      WorkspaceModifyDelegatingOperation modifyOp = new WorkspaceModifyDelegatingOperation( creationOp );
      
      // Create the new project
      try {
        getContainer().run( true, true, modifyOp );
        this.project = creationOp.getProject();
      } catch ( InterruptedException intExc ) {
        eu.geclipse.ui.internal.Activator.logException( intExc );
      } catch ( InvocationTargetException itExc ) {
        eu.geclipse.ui.internal.Activator.logException( itExc );
      }
      
    }
    
    return this.project;
    
  }

}
