package eu.geclipse.ui.internal.actions;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

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
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import eu.geclipse.core.filesystem.GEclipseFileSystem;
import eu.geclipse.core.filesystem.GEclipseURI;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridConnection;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridPreferences;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.IMountable;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.model.IMountable.MountPoint;
import eu.geclipse.core.model.IMountable.MountPointID;
import eu.geclipse.ui.dialogs.ProblemDialog;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.wizards.ConnectionWizard;

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
   * @param mountID T
   */
  protected MountAction( final Shell shell,
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
    Job mountJob = new Job( "Storage mount job" ) {
      @Override
      protected IStatus run( final IProgressMonitor monitor ) {
        return mountOperation( monitor );
      }
    };
    mountJob.setUser( true );
    mountJob.schedule();
  }
  
  protected IStatus mountOperation( final IProgressMonitor monitor ) {
    
    List< IStatus > errors = new ArrayList< IStatus >();
    
    SubMonitor sMonitor = SubMonitor.convert( monitor, "Creating mounts", this.mountables.length );
    
    for ( IMountable mountable : this.mountables ) {
      
      try {
        
        if ( sMonitor.isCanceled() ) {
          throw new OperationCanceledException();
        }
        
        sMonitor.subTask( "Mounting " + mountable.getName() );
        createMount( mountable, sMonitor.newChild( 1 ) );
        
      } catch ( CoreException cExc ) {
        IStatus status = new Status(
            IStatus.ERROR,
            Activator.PLUGIN_ID,
            String.format( "Failed to mount %s", mountable.getName() ),
            cExc
        );
        errors.add( status );
      }
      
    }
    
    sMonitor.done();
    
    IStatus result = Status.OK_STATUS;
    
    if ( ! errors.isEmpty() ) {
      IStatus[] errorArray = errors.toArray( new IStatus[ errors.size() ] );
      result = new MultiStatus(
          Activator.PLUGIN_ID,
          IStatus.ERROR,
          errorArray,
          "Mount failed",
          null
      );
    }
    
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
        if ( ! checkExists( path ) ) {
          createLocalMount( uri, path, monitor );
        }
      }

    }
    
  }
  
  public static void createGlobalMount( final URI uri,
                                        final String name,
                                        final IProgressMonitor monitor )
      throws CoreException {
    
    SubMonitor sMonitor = SubMonitor.convert( monitor, 10 );
    
    try {
      
      sMonitor.subTask( "Preparing resources" );
      GEclipseURI geclURI = new GEclipseURI( uri );
      boolean isDirectory = isDirectory( geclURI );
      sMonitor.worked( 4 );
      if ( sMonitor.isCanceled() ) {
        throw new OperationCanceledException();
      }
 
      sMonitor.subTask( "Creating connection" );
      IGridPreferences preferences = GridModel.getPreferences();
      preferences.createGlobalConnection( name, geclURI.toMasterURI(), sMonitor.newChild( 6 ) );
      
    } finally {
      sMonitor.done();
    }
    
  }
  
  public static void createLocalMount( final URI uri,
                                       final IPath path,
                                       final IProgressMonitor monitor )
      throws CoreException {
    
    SubMonitor sMonitor = SubMonitor.convert( monitor, 10 );
    
    try {
    
      sMonitor.subTask( "Preparing resources" );
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
      sMonitor.subTask( "Creating connection" );
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
      if( !uri.getScheme().equals( "file" ) ) {
        try {
          String schemeSpecificPart = uri.getSchemeSpecificPart();
          if( schemeSpecificPart.contains( "?" ) ) {
            schemeSpecificPart += "&vo=" + voName;
          } else {
            schemeSpecificPart += "?vo=" + voName;
            
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
  
  private boolean checkExists( final IPath target ) {
    
    boolean result
      = ResourcesPlugin.getWorkspace().getRoot().getFile( target ).exists()
      || ResourcesPlugin.getWorkspace().getRoot().getFolder( target ).exists();
    
    if ( result ) {
      ProblemDialog.openProblem(
          this.shell,
          "Resource already exists",
          String.format( "The mount target already exists: %s", target.toString() ),
          null
      );
    }
    
    return result;
    
  }
  
  private static boolean isDirectory( final GEclipseURI uri )
      throws CoreException {
    URI masterURI = uri.toMasterURI();
    IFileStore fileStore = EFS.getStore( masterURI );
    GEclipseFileSystem.assureFileStoreIsActive( fileStore );
    IFileInfo fileInfo = fileStore.fetchInfo();
    
    // Check if the server could be really contacted
    if ( ! fileInfo.exists() ) {
      throw new CoreException( new Status( IStatus.ERROR,
                                           Activator.PLUGIN_ID,
                                           String.format( "Error contacting the server for mounting %s",
                                                          fileStore.toString() ) ) );
    }
    
    return fileInfo.isDirectory();
  }
  
}
