package eu.geclipse.ui.properties;

import java.util.Hashtable;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.dialogs.PropertyPage;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.widgets.GridProjectStructureComposite;

public class GridProjectFoldersPropertiesPage
    extends PropertyPage {
  
  private GridProjectStructureComposite projectStructureComposite;

  @Override
  public boolean performOk() {

    boolean result = true;
    
    IProject project = ( IProject ) getElement().getAdapter( IProject.class );
    
    if ( project != null ) {
      
      try {
      
        IScopeContext projectScope = new ProjectScope( project );
        final Preferences folderNode = projectScope.getNode( "eu.geclipse.core.folders" ); //$NON-NLS-1$
        Hashtable< String, String > folders = this.projectStructureComposite.getProjectFolders();
        
        boolean updateNeeded = false;
        
        for ( String id : folderNode.keys() ) {
          
          String oldLabel = folderNode.get( id, null );
          String newLabel = folders.get( id );
          
          if ( newLabel == null ) {
            folderNode.remove( id );
            updateNeeded = true;
          }
          
          else if ( ! newLabel.equals( oldLabel ) ) {
            IResource resource = project.findMember( oldLabel );
            if ( resource != null ) {
              IPath path = resource.getFullPath();
              path = path.removeLastSegments( 1 );
              path = path.append( newLabel );
              try {
                resource.move( path, true, null );
                folderNode.put( id, newLabel );
                updateNeeded = true;
              } catch ( CoreException cExc ) {
                Activator.logException( cExc );
              }
            }
          }
          
        }

        
        for ( String id : folders.keySet() ) {
          String oldLabel = folderNode.get( id, null );
          if ( oldLabel == null ) {
            String label = folders.get( id );
            IFolder folder = project.getFolder( new Path( label ) );
            if ( !folder.exists() ) {
              try {
                folder.create( IResource.FORCE, true, null );
              } catch( CoreException cExc ) {
                Activator.logException( cExc );
              }
            }
            folderNode.put( id, label );
            updateNeeded = true;
          }
        }
        
        if ( updateNeeded ) {
          WorkspaceJob updater = new WorkspaceJob( "project_folder_updater" ) {
            @Override
            public IStatus runInWorkspace( final IProgressMonitor monitor )
                throws CoreException {
              IStatus jobResult = Status.OK_STATUS;
              try {
                folderNode.flush();
              } catch ( BackingStoreException bsExc ) {
                jobResult = new Status( IStatus.ERROR,
                                        Activator.PLUGIN_ID,
                                        "Error while writing project preferences",
                                        bsExc );
              }
              return jobResult;
            }
          };
          updater.setRule( project );
          updater.setSystem( true );
          updater.schedule();
          /*
            Activator.logException( cExc );
          }*/
        }
        
      } catch ( BackingStoreException bsExc ) {
        Activator.logException( bsExc );
        result = false;
      }
      
    }
    
    return result;
    
  }
  
  @Override
  public void setElement( final IAdaptable element ) {
    super.setElement( element );
    updateProjectStructureComposite();
  }
  
  @Override
  protected Control createContents( final Composite parent ) {
    this.projectStructureComposite = new GridProjectStructureComposite( parent, SWT.NULL );
    updateProjectStructureComposite();
    return this.projectStructureComposite;
  }
  
  @Override
  protected void performDefaults() {
    this.projectStructureComposite.performDefaults();
    super.performDefaults();
  }
  
  private void updateProjectStructureComposite() {
    if ( this.projectStructureComposite != null ) {
      IProject project = ( IProject ) getElement().getAdapter( IProject.class );
      if ( project != null ) {
        try {
          this.projectStructureComposite.setProject( project );
          this.projectStructureComposite.setEnabled( true );
        } catch ( CoreException cExc ) {
          Activator.logException( cExc );
        }
      } else {
        this.projectStructureComposite.setEnabled( false );
      }
    }
  }

}
