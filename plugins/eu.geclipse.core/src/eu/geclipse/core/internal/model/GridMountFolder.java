package eu.geclipse.core.internal.model;

import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import eu.geclipse.core.model.IGridMount;

public class GridMountFolder extends VirtualGridContainer {

  public GridMountFolder( final IGridMount parent,
                          final IFileStore fileStore,
                          final IFileInfo fileInfo ) {
    super( parent, fileInfo.getName() );
  }
  
}
