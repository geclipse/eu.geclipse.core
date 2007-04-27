package eu.geclipse.ui.internal.connection;

import java.util.ArrayList;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.runtime.CoreException;
import eu.geclipse.core.connection.AbstractConnection;
import eu.geclipse.core.connection.ConnectionManager;
import eu.geclipse.ui.views.gexplorer.ResourceNode;

/**
 * Auxiliary class to get filesystems from .filesystems file
 */
public class FileSystemsProvider {

  /**
   * Returns array of ResourceNodes containing (wrapping) IFileStore. This
   * IFileStore is created from information of file system that is kept in
   * /Filesystems/.filesystems files in every grid project. Each IFileStore
   * represents one entry in .filesystems file.
   * 
   * @return ArrayList of ResourceNodes wrapping IFileStore class. This array
   *         may be empty (when /Filesystems/.filesystems of project with given
   *         name does not exist in active workspace)
   */
//  public static ArrayList<ResourceNode> getFileSystems( final String projectName )
  public static ArrayList<ResourceNode> getFileSystems( )
  {
    ArrayList<ResourceNode> result = new ArrayList<ResourceNode>();
    ConnectionManager manager = ConnectionManager.getManager();
    for (AbstractConnection conn: manager.getConnections()){
      IFileSystem fileSystem;
      try {
        fileSystem = EFS.getFileSystem( conn.getDescription().getFileSystemURI().getScheme() );
        result.add( new ResourceNode( fileSystem.getStore( conn.getDescription().getFileSystemURI() ), true ) );
      } catch( CoreException coreEx ) {
        eu.geclipse.ui.internal.Activator.logException( coreEx );
      } catch (NullPointerException nullExc){
//        IStatus status = new Status( IStatus.ERROR,
//                                     eu.geclipse.ui.internal.Activator.PLUGIN_ID,
//                                     IStatus.OK,
//                                     Messages.getString( "ConnectionWizard.error_reason" ), //$NON-NLS-1$
//                                     nullExc.getCause() );
//        ProblemDialog dialog = new ProblemDialog( null,
//                                                  Messages.getString( "ConnectionWizard.problem_dialog_title" ), //$NON-NLS-1$
//                                                  Messages.getString( "ConnectionWizard.error_message" ), status ); //$NON-NLS-1$
//        dialog.addSolution( new Solution("Check if parameters are not malformed (e.g. containing special characters)") ); //$NON-NLS-1$
//        dialog.addSolution( new Solution("Make sure plug-in for connection type you have chosen is running in current workspace") ); //$NON-NLS-1$
//        dialog.open();
        eu.geclipse.ui.internal.Activator.logException( nullExc );
      }
      
    }
    return result;
  }
}