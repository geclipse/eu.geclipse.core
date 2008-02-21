/******************************************************************************
 * Copyright (c) 2007, 2008 g-Eclipse consortium 
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
 *     Mathias Stuempert - initial API and implementation
 *****************************************************************************/

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

/**
 * A property page for modifying the project folders of a Grid project.
 */
public class GridProjectFoldersPropertiesPage
    extends PropertyPage {
  
  /**
   * Internally used project structure composite.
   */
  private GridProjectStructureComposite projectStructureComposite;

  /* (non-Javadoc)
   * @see org.eclipse.jface.preference.PreferencePage#performOk()
   */
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
          WorkspaceJob updater = new WorkspaceJob( "project_folder_updater" ) { //$NON-NLS-1$
            @Override
            public IStatus runInWorkspace( final IProgressMonitor monitor )
                throws CoreException {
              IStatus jobResult = Status.OK_STATUS;
              try {
                folderNode.flush();
              } catch ( BackingStoreException bsExc ) {
                jobResult = new Status( IStatus.ERROR,
                                        Activator.PLUGIN_ID,
                                        Messages.getString("GridProjectFoldersPropertiesPage.project_setup_error"), //$NON-NLS-1$
                                        bsExc );
              }
              return jobResult;
            }
          };
          updater.setRule( project );
          updater.setSystem( true );
          updater.schedule();

        }
        
      } catch ( BackingStoreException bsExc ) {
        Activator.logException( bsExc );
        result = false;
      }
      
    }
    
    return result;
    
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.dialogs.PropertyPage#setElement(org.eclipse.core.runtime.IAdaptable)
   */
  @Override
  public void setElement( final IAdaptable element ) {
    super.setElement( element );
    updateProjectStructureComposite();
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Control createContents( final Composite parent ) {
    this.projectStructureComposite = new GridProjectStructureComposite( parent, SWT.NULL );
    updateProjectStructureComposite();
    return this.projectStructureComposite;
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.preference.PreferencePage#performDefaults()
   */
  @Override
  protected void performDefaults() {
    this.projectStructureComposite.performDefaults();
    super.performDefaults();
  }
  
  /**
   * Update the project structure composite if it is already
   * available, i.e. set its project to be the selected element.
   */
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
