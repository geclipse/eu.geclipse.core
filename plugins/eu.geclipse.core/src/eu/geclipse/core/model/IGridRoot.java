package eu.geclipse.core.model;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;

/**
 * This is the root element of the whole grid model. it is the
 * parent of all currently contained projects. It also holds the
 * various managers as children. The managers are not available
 * via the {@link IGridContainer#getChildren()} method but only
 * via the dedicated getManager-methods.
 */
public interface IGridRoot extends IGridContainer {
  
  public IGridElement findElement( final IPath path );
  
  /**
   * Recursively search the root's children for an element that
   * corresponds to the specified <code>IResource</code>.
   *  
   * @param resource The resource that is searched.
   * @return The grid element that corresponds to the specified
   * resource.
   */
  public IGridElement findElement( final IResource resource );

  /**
   * Get the manager that is dedicated to the management of
   * {@link IVirtualOrganization}s.
   * 
   * @return The {@link IVoManager}.
   */
  public IVoManager getVoManager();
  
}
