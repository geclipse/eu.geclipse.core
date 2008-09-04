/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributors:
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.core.model;

import java.net.URI;

/**
 * The <code>IMountable</code> interface defines methods and anonymous classes
 * for making grid elements accessible via a grid connection. The
 * {@link #getMountPointIDs()}-method returns a list of {@link MountPointID}s
 * that may be used afterwards to create a specific {@link MountPoint} with the
 * {@link #getMountPoint(MountPointID)}-method. The separation of the mount
 * point creation into these two methods is necessary since the
 * {@link MountPoint} creation itself may require online and therefore long
 * running operations if some remote services have to be invoked in order to
 * obtain the URI for a specific mount. 
 */
public interface IMountable extends IGridElement {
  
  /**
   * This anonymous class acts as a unique identifier of a mount point for a
   * specific <code>IMountable</code> implementation.
   */
  public static class MountPointID {
    
    /**
     * The unique identifier.
     */
    private String uid;
    
    /**
     * The scheme of the corresponding EFS-implementation.
     */
    private String scheme;
    
    /**
     * Create a new <code>MountPointID</code> from the specified uid and
     * scheme.
     * 
     * @param uid The unique identifier of this <code>MountPointID</code>. The
     * identifier has to be unique in terms of a specific {@link IMountable}
     * implementation, i.e. different {@link IMountable} implementations may
     * have mount point IDs with the same unique identifier.
     * @param scheme The scheme of the EFS-implementation (i.e. the URI-scheme)
     * that is used to access the {@link IMountable} afterwards via a
     * {@link IGridConnection}.
     */
    public MountPointID( final String uid, final String scheme ) {
      this.uid = uid;
      this.scheme = scheme;
    }
    
    /**
     * Get this {@link MountPointID}'s unique identifier.
     * 
     * @return The unique identifier.
     */
    public String getUID() {
      return this.uid;
    }
    
    /**
     * Get this {@link MountPointID}'s scheme.
     * 
     * @return The scheme of the corresponding EFS-implementation that is used
     * to create the corresponding {@link IGridConnection}.
     */
    public String getScheme() {
      return this.scheme;
    }
    
    @Override
    public boolean equals( final Object o ) {
       return ( o instanceof MountPointID )
         && this.uid.equals( ( ( MountPointID ) o ).getUID() )
         && this.scheme.equalsIgnoreCase( ( ( MountPointID ) o ).getScheme() );  
    }
    
  }
  
  /**
   * This anonymous class represents a mount point and {@link IGridConnection}
   * can be created from the enclosed information.
   */
  public static class MountPoint {
    
    /**
     * The name of the mount point.
     */
    private String name;
    
    /**
     * The {@link URI} of the mount point.
     */
    private URI uri;
    
    /**
     * Create a new mount point with the specified name and uri.
     * 
     * @param name The name of the mount point. This may be used to represent
     * the mount point to the user. By default this is used as the name of the
     * {@link IGridConnection} that is generated from this mount point.
     * @param uri The uri of the mount point, i.e. the remote (or local)
     * location the resulting {@link IGridConnection} points to.
     */
    public MountPoint( final String name, final URI uri ) {
      this.name = name;
      this.uri = uri;
    }
    
    /**
     * Get this mount point's name.
     * 
     * @return The name of this mount point.
     */
    public String getName() {
      return this.name;
    }
    
    /**
     * Get this mount point's uri, i.e. the remote (or local) target.
     * 
     * @return The {@link URI} of this mount point.
     */
    public URI getURI() {
      return this.uri;
    }
    
  }
  
  /**
   * Get a list of mount point IDs for this <code>IMountable</code>. These IDs
   * may be used afterwards in order to create specific {@link MountPoint}s.
   * Implementations of this methods should be straight-forward and fast. A
   * specific implementation could for instance just return a statically defined
   * array of {@link MountPointID}s.
   * 
   * @return A list of {@link MountPointID}s.
   */
  public MountPointID[] getMountPointIDs();
  
  /**
   * Create a {@link MountPoint} from a specific {@link MountPointID}. Callers
   * should expect implementations of this methods not to be very fast.
   * 
   * @param mountID The {@link MountPointID} from which to create the mount
   * point.
   * @return The {@link MountPoint} corresponding to the specified
   * {@link MountPointID} or <code>null</code> if this implementation can not
   * create a mount point for the specified ID.
   */
  public MountPoint getMountPoint( final MountPointID mountID );
  
}
