package eu.geclipse.core.connection;

import org.eclipse.core.resources.IFile;

public class FilesystemsStore {
  
  private IFile file;
  
  public FilesystemsStore( final IFile file ) {
    this.file = file;
  }
  
  public IFile getFile() {
    return this.file;
  }
  
}
