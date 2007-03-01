package eu.geclipse.core;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import eu.geclipse.core.internal.Activator;

public class SolutionRegistry {
  
  public static final int CHECK_INTERNET_CONNECTION = uniqueID();
  
  public static final int CHECK_PROXY_SETTINGS = uniqueID();
  
  public static final int CHECK_TIMEOUT_SETTINGS = uniqueID();
  
  public static final int CHECK_SERVER_URL = uniqueID();
  
  public static final int SERVER_DOWN = uniqueID();
  

  private static SolutionRegistry singleton;
  
  private static int lastUniqueID = 0;
  
  protected SolutionRegistry() {
    // empty implementation
  }
  
  public static SolutionRegistry getRegistry() {
    if ( singleton == null ) {
      singleton = new SolutionRegistry();
    }
    return singleton;
  }
  
  public ISolution getSolution( final int solutionID ) {
    ISolution solution = findSolution( solutionID );
    if ( solution == null ) {
      IStatus status = new Status(
        IStatus.WARNING,
        Activator.PLUGIN_ID,
        IStatus.OK,
        "Unable to find solution with ID " + solutionID,
        null
      );
      Activator.logStatus( status );
    }
    return solution;
  }
  
  protected ISolution findSolution( final int solutionID ) {
    
    ISolution solution = null;
    
    if ( solutionID == CHECK_INTERNET_CONNECTION ) {
      solution = new Solution( CHECK_INTERNET_CONNECTION, "Check your internet connection" );
    }
    
    if ( solutionID == CHECK_PROXY_SETTINGS ) {
      solution = new Solution( CHECK_PROXY_SETTINGS, "Check your proxy settings" );
    }

    else if ( solutionID == CHECK_SERVER_URL ) {
      solution = new Solution( CHECK_SERVER_URL, "Check your server URL");
    }
    
    else if ( solutionID == CHECK_TIMEOUT_SETTINGS ) {
      solution = new Solution( CHECK_TIMEOUT_SETTINGS, "Set your connection timeout settings to a higher value" );
    }
    
    else if ( solutionID == SERVER_DOWN ) {
      solution = new Solution( SERVER_DOWN, "The server may be down. Contact the administrator");
    }
    
    return solution;
    
  }
  
  private static int uniqueID() {
    return ++lastUniqueID;
  }
  
}
