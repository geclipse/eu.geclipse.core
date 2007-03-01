package eu.geclipse.ui;

import org.eclipse.swt.widgets.Shell;
import eu.geclipse.core.ISolution;
import eu.geclipse.core.SolutionRegistry;

public class UISolutionRegistry extends SolutionRegistry {
  
  private static UISolutionRegistry singleton;
  
  private Shell shell;
  
  protected UISolutionRegistry( final Shell problemDialog ) {
    this.shell = problemDialog;
  }
  
  public static UISolutionRegistry getRegistry( final Shell shell ) {
    if ( singleton == null ) {
      singleton = new UISolutionRegistry( shell );
    }
    return singleton;
  }
  
  @Override
  protected ISolution findSolution( final int solutionID ) {

    ISolution solution = super.findSolution( solutionID );
    
    if ( solutionID == SolutionRegistry.CHECK_PROXY_SETTINGS ) {
      solution = new PreferenceSolution( solution,
                                         this.shell,
                                         PreferenceSolution.NETWORK_PREFERENCE_PAGE );
    }
    
    else if ( solutionID == SolutionRegistry.CHECK_TIMEOUT_SETTINGS ) {
      solution = new PreferenceSolution( solution,
                                         this.shell,
                                         PreferenceSolution.NETWORK_PREFERENCE_PAGE );
    }

    return solution;

  }
  
}
