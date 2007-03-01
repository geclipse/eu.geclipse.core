package eu.geclipse.core.model;

import org.eclipse.core.filesystem.IFileStore;

public interface IStorableElementCreator
    extends IGridElementCreator, IExtensible {
  
  public boolean canCreate( final IFileStore fromFileStore );
  
}
