package eu.geclipse.ui.util;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.runtime.CoreException;

public class FieldValidator {
  
  public static boolean validateHost( final String host ) {
    // TODO mathias
    return true;
  }
  
  public static boolean validatePort( final String port ) {
    boolean result = false;
    try {
      int iport = Integer.parseInt( port );
      result = validatePort( iport );
    } catch ( NumberFormatException nfExc ) {
      // Just catch since result is already false;
    }
    return result;
  }
  
  public static boolean validatePort( final int port ) {
    return ( port > 0 ) && ( port < 65536 ); 
  }
  
  public static boolean validateScheme( final String scheme ) {
    boolean result = false;
    try {
      IFileSystem fileSystem = EFS.getFileSystem( scheme );
      result = fileSystem != null;
    } catch( CoreException cExc ) {
      // Just catch since result is already false
    }
    return result;
  }
  
}
