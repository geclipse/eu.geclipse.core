package eu.geclipse.core.model;

import java.net.URI;
import java.util.Hashtable;

public interface IMountable extends IGridElement {
  
  public static class MountPointID {
    
    private String uid;
    
    private String scheme;
    
    public MountPointID( final String uid, final String scheme ) {
      this.uid = uid;
      this.scheme = scheme;
    }
    
    public String getUID() {
      return this.uid;
    }
    
    public String getScheme() {
      return this.scheme;
    }
    
    public boolean equals( final Object o ) {
       return ( o instanceof MountPointID )
         && this.uid.equals( ( ( MountPointID ) o ).getUID() )
         && this.scheme.equalsIgnoreCase( ( ( MountPointID ) o ).getScheme() );  
    }
    
  }
  
  public static class MountPoint {
    
    private String name;
    
    private URI uri;
    
    public MountPoint( final String name, final URI uri ) {
      this.name = name;
      this.uri = uri;
    }
    
    public String getName() {
      return this.name;
    }
    
    public URI getURI() {
      return this.uri;
    }
    
  }
  
  public MountPointID[] getMountPointIDs();
  
  public MountPoint getMountPoint( final MountPointID mountID );
  
}
