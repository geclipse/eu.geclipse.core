package eu.geclipse.core.model;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

public interface IGridTransfer
    extends IGridContainer {
  
  public IGridElement getSource();
  
  public IGridContainer getTarget();
  
  public boolean isAtomic();
  
}
