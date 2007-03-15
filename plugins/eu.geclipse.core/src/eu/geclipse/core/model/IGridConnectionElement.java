package eu.geclipse.core.model;

import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

public interface IGridConnectionElement extends IGridResource, IGridContainer {
  
  public IFileStore getConnectionFileStore() throws CoreException;
  
  public IFileInfo getConnectionFileInfo();
  
  /**
   * Get an error message that describes an error that occured
   * during the last operation. This functionality is not yet
   * fully defined.
   * 
   * @return An error string.
   */
  public String getError();
  
  public IResource createLocalCopy( final IProgressMonitor monitor ) throws CoreException;
  
  /**
   * Determine if this grid mount object specifies a folder. If it
   * is not a folder it has to be a file.
   * 
   * @return True if this is a folder.
   */
  public boolean isFolder();
  
  /**
   * Determine if this mount is valid. There are several reasons why
   * a grid mount may be invalid. For instance it could request
   * a file system that is currently not available.
   *  
   * @return True if this mount is valid and can be browsed.
   */
  public boolean isValid();
  
}
