package eu.geclipse.core.jobs;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;

import eu.geclipse.core.model.ICreatorSourceMatcher;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.impl.AbstractGridElementCreator;
import eu.geclipse.core.reporting.ProblemException;


/**
 * Creator for container, which has links to remote files (e.g. Output Files folder) 
 */
public class RemoteFilesContainerCreator extends AbstractGridElementCreator
  implements ICreatorSourceMatcher
{

  public boolean canCreate( final Object source ) {
    boolean canCreate = false;

    if( source instanceof IFolder ) {
      IFolder folder = ( IFolder )source;

      // Can create only for folders with name "Output files", which parent is job
      if( folder.getName().equalsIgnoreCase( GridJob.FOLDERNAME_OUTPUT_FILES )
          || folder.getName().equalsIgnoreCase( GridJob.FOLDERNAME_INPUT_FILES ) ) {
        IContainer parent = folder.getParent();
        
        if( parent instanceof IFolder ) {          
          if( parent.getName().endsWith( GridJob.JOBFILE_EXTENSION ) ) {
            // TODO mariusz now this creator have to be registered for all midlewares independly
            // but, when bug #248403 Attribute "default" in extension point eu.geclipse.core.gridElementCreator is ignored
            // is resolved, we will able to register it for all in/out folders, which has not specialised creator (like GliteSandboxFilesCreator) 
            canCreate = true;
          }
        }   
      }
    }
     
    return canCreate;
  }

  public IGridElement create( final IGridContainer parent ) throws ProblemException {
    return new RemoteFilesContainer( ( IResource )getSource() );
  }
}
