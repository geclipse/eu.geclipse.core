/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributors:
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.actions;

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import eu.geclipse.core.auth.AbstractAuthTokenProvider;
import eu.geclipse.core.filesystem.GEclipseFileSystem;
import eu.geclipse.core.filesystem.GEclipseURI;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridConnection;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridPreferences;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.IMountable;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.model.IMountable.MountPoint;
import eu.geclipse.core.model.IMountable.MountPointID;
import eu.geclipse.core.reporting.IProblem;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.dialogs.ProblemDialog;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.internal.actions.Messages;
import eu.geclipse.ui.problems.RemountSolution;
import eu.geclipse.ui.wizards.ConnectionWizard;

/**
 * Action used for mounting storage elements. 
 *
 */
public class MountAction extends Action {

  private IMountable[] mountables;
  
  private MountPointID mountID;
  
  private Shell shell;
  
  private boolean mountAs;
  
  /**
   * Construct a new <code>MountAction</code> for the specified
   * {@link IMountable}s using the specified mount ID.
   * 
   * @param shell A shell used to report errors.
   * @param mountables The {@link IMountable}s that should be mounted.
   * @param mountID The {@link MountPointID} that should be used.
   * @param mountAs Specifies if this is a mountAs operation.
   */
  public MountAction( final Shell shell,
                      final IMountable[] mountables,
                      final MountPointID mountID,
                      final boolean mountAs ) {
    super( mountID.getUID() );
    this.shell = shell;
    this.mountables = mountables;
    this.mountID = mountID;
    this.mountAs = mountAs;
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    Job mountJob = new Job( Messages.getString("MountAction.mount_job_name") ) { //$NON-NLS-1$
      @Override
      protected IStatus run( final IProgressMonitor monitor ) {
        return mountOperation( monitor );
      }
    };
    mountJob.setUser( true );
    mountJob.schedule();
  }
  
  protected IStatus mountOperation( final IProgressMonitor monitor ) {    
    
    SubMonitor sMonitor = SubMonitor.convert( monitor,
                                              Messages.getString("MountAction.monitor_title"), //$NON-NLS-1$
                                              this.mountables.length );
    
    for ( IMountable mountable : this.mountables ) {
      
      try {
        
        if ( sMonitor.isCanceled() ) {
          throw new OperationCanceledException();
        }
        
        sMonitor.subTask( Messages.getString("MountAction.monitor_subtask_mounting") + mountable.getName() ); //$NON-NLS-1$
        createMount( mountable, sMonitor.newChild( 1 ) );
        
      } catch ( CoreException cExc ) {
        if ( ! AbstractAuthTokenProvider.isTokenRequestCanceledException( cExc ) ) {
          ProblemDialog.openProblem( this.shell,
                                     String.format( Messages.getString("MountAction.problem_dialog_title") ),  //$NON-NLS-1$
                                     String.format( Messages.getString("MountAction.failed_mount_info"), mountable.getName() ),  //$NON-NLS-1$
                                     cExc );
        }
      }
      
    }
    
    sMonitor.done();
    
    IStatus result = Status.OK_STATUS;
    
    return result;
    
  }
  
  /**
   * Create a mount file for the specified {@link IMountable}. The mount
   * file is created in the connections folder of the selected project.
   * 
   * @param mountable The {@link IMountable} to be mounted.
   * @throws CoreException 
   * @throws CoreException If the mount file count not be created.
   */
  protected void createMount( final IMountable mountable,
                              final IProgressMonitor monitor ) throws CoreException {

    boolean global = false;
    
    if ( mountable instanceof IGridConnectionElement ) {
      
      IGridContainer root = ( IGridConnectionElement ) mountable;
      
      while ( ! ( root instanceof IGridConnection ) && ( root != null ) ) {
        root = root.getParent();
      }
      
      if ( ( root != null ) && ( root instanceof IGridConnection ) ) {
        global = ( ( IGridConnection ) root ).isGlobal();
      }
      
    }

    MountPoint mountPoint = mountable.getMountPoint( this.mountID );

    if ( mountPoint != null ) {

      String mountName = mountPoint.getName();
      URI uri = mountPoint.getURI();

      if ( this.mountAs ) {
        ConnectionWizard wizard = new ConnectionWizard( uri, mountName, global );
        if ( ! global ) {
          IGridProject project = mountable.getProject();
          IGridContainer mountFolder = project.getProjectFolder( IGridConnection.class );
          wizard.init( null, new StructuredSelection( mountFolder.getResource() ) );
        }
        final WizardDialog dialog = new WizardDialog( this.shell, wizard );
        this.shell.getDisplay().asyncExec( new Runnable() {
          public void run() {
            dialog.open();
          }
        } );
      }

      else if ( global ) {
        createGlobalMount( uri, mountName, monitor );
      }

      else {
        IGridProject project = mountable.getProject();
        IGridContainer mountFolder = project.getProjectFolder( IGridConnection.class );
        IPath path = mountFolder.getPath().append( mountName );
        if ( ! checkExists( mountable, path ) ) {
          createLocalMount( uri, path, monitor );
        }
      }

    }
    
  }
  
  /**
   * Static method for creating global connection.
   * 
   * @param uri Location of the storage element that should be mounted.
   * @param name Name of the mount.
   * @param monitor Monitor used to present progress of the mount operation.
   * @throws CoreException Throws {@link CoreException} in case mount operation
   *           fails.
   */
  public static void createGlobalMount( final URI uri,
                                        final String name,
                                        final IProgressMonitor monitor )
      throws CoreException {
    
    SubMonitor sMonitor = SubMonitor.convert( monitor, 10 );
    
    try {
      sMonitor.subTask( String.format( Messages.getString("MountAction.monitor_subtask_preparing_resources"), name ) ); //$NON-NLS-1$
      
      GEclipseURI geclURI = new GEclipseURI( uri );
      isDirectory( geclURI );
      sMonitor.worked( 4 );
      if ( sMonitor.isCanceled() ) {
        throw new OperationCanceledException();
      }
 
      sMonitor.subTask( Messages.getString("MountAction.monitor_subtask_creating_connection") ); //$NON-NLS-1$
      IGridPreferences preferences = GridModel.getPreferences();
      preferences.createGlobalConnection( name, geclURI.toMasterURI(), sMonitor.newChild( 6 ) );
      
    } finally {
      sMonitor.done();
    }
    
  }
  
  /**
   * Static method for creating local mounts.
   * 
   * @param uri Location of the storage element that should be mounted.
   * @param path Local relative {@link IPath}, where link will be stored.
   * @param monitor Monitor used to present progress of the mount operation.
   * @throws CoreException Throws {@link CoreException} in case mount operation
   *           fails.
   */
  public static void createLocalMount( final URI uri,
                                       final IPath path,
                                       final IProgressMonitor monitor )
      throws CoreException {
    
    SubMonitor sMonitor = SubMonitor.convert( monitor, 10 );
    
    try {
    
      sMonitor.subTask( String.format( Messages.getString("MountAction.monitor_subtask_preparing_resources"), path.toString() ) ); //$NON-NLS-1$
      String projectName = path.segment( 0 );
      IGridProject project = ( IGridProject ) GridModel.getRoot().findChild( projectName );
      IVirtualOrganization vo = project.getVO();
      String voName = vo != null ? vo.getName() : null;
      
      URI newUri = processURI( voName, uri ); 
      GEclipseURI geclURI = new GEclipseURI( newUri );
      boolean isDirectory = isDirectory( geclURI );
      sMonitor.worked( 4 );
      if ( sMonitor.isCanceled() ) {
        throw new OperationCanceledException();
      }
      sMonitor.subTask( Messages.getString("MountAction.monitor_subtask_creating_connection") ); //$NON-NLS-1$
      if ( isDirectory ) {
        IFolder folder = ResourcesPlugin.getWorkspace().getRoot().getFolder( path );
        folder.createLink( geclURI.toMasterURI(), IResource.ALLOW_MISSING_LOCAL, sMonitor.newChild( 6 ) );
      } else {
        IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile( path );
        file.createLink( geclURI.toMasterURI(), IResource.ALLOW_MISSING_LOCAL, sMonitor.newChild( 6 ) );
      }
      
    } finally {
      sMonitor.done();
    }
    
  }
  
  private static URI processURI( final String voName,
                          final URI uri ) {
    
    URI result = uri;
    if ( voName != null && uri != null ) {
      //Default file store should not be processed
      if( !uri.getScheme().equals( "file" ) ) { //$NON-NLS-1$
        try {
          String schemeSpecificPart = uri.getSchemeSpecificPart();
          if( schemeSpecificPart.contains( "?" ) ) { //$NON-NLS-1$
            schemeSpecificPart += "&vo=" + voName; //$NON-NLS-1$
          } else {
            schemeSpecificPart += "?vo=" + voName; //$NON-NLS-1$
            
          }
          result = new URI( uri.getScheme(), schemeSpecificPart, uri.getFragment() );
        } catch( URISyntaxException e ) {
           //TODO Auto-generated catch block
          Activator.logException( e );
        }
      }
    }
    return result;
  }
  
  private boolean checkExists( final IMountable mountable, final IPath target ) {
    
    boolean result
      = ResourcesPlugin.getWorkspace().getRoot().getFile( target ).exists()
      || ResourcesPlugin.getWorkspace().getRoot().getFolder( target ).exists();
    
    if ( result ) {
      ProblemException problemExc = new ProblemException( "eu.geclipse.ui.problem.mountAlreadyExists", //$NON-NLS-1$
                                                          Activator.PLUGIN_ID );
      IProblem problem = problemExc.getProblem();
      if( this.mountID != null ) {
        problem.addSolution( new RemountSolution( mountable, this.mountID ) );
      }
      ProblemDialog.openProblem( this.shell,
                                 Messages.getString("MountAction.resource_exists_problem"),  //$NON-NLS-1$
                                 String.format( Messages.getString("MountAction.resource_exists_info"), target.toString() ), //$NON-NLS-1$
                                 problemExc );
      }
    
    return result;
    
  }
  
  private static boolean isDirectory( final GEclipseURI uri )
      throws CoreException, ProblemException {
    URI masterURI = uri.toMasterURI();
    IFileStore fileStore = EFS.getStore( masterURI );
    GEclipseFileSystem.assureFileStoreIsActive( fileStore );
    IFileInfo fileInfo = null;
    try {
      fileInfo = fileStore.fetchInfo( EFS.NONE, new NullProgressMonitor() );
    } catch ( CoreException coreExc ) {
      throw new ProblemException( "eu.geclipse.core.filesystem.serverCouldNotBeContacted", //$NON-NLS-1$
                                  String.format( Messages.getString("MountAction.cannot_fetch_info_error"), //$NON-NLS-1$
                                                 uri.toSlaveURI() ),
                                  coreExc,
                                  Activator.PLUGIN_ID );
    }
    // Check if the server could be really contacted
    if ( fileInfo == null || !fileInfo.exists() ) {
      throw new ProblemException( "eu.geclipse.core.filesystem.serverCouldNotBeContacted", //$NON-NLS-1$
                                  String.format( Messages.getString("MountAction.server_not_responding_info"), //$NON-NLS-1$
                                                 uri.toSlaveURI() ),
                                  Activator.PLUGIN_ID );
    }
    
    return fileInfo.isDirectory();
  }
  
}
