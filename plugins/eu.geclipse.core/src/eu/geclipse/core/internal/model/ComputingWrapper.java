package eu.geclipse.core.internal.model;

import java.net.URI;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;

import eu.geclipse.core.model.IGridComputing;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.IWrappedElement;
import eu.geclipse.core.model.impl.AbstractGridElement;

public class ComputingWrapper
    extends WrappedElement
    implements IGridComputing {
  
  public ComputingWrapper( final IGridContainer parent,
                           final IGridComputing computing ) {
    super( parent, computing );
  }

  public URI getURI() {
    return ( ( IGridComputing ) getWrappedElement() ).getURI();
  }

}
