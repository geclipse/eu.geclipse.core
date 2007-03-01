package eu.geclipse.ui;

import org.eclipse.swt.widgets.Shell;
import eu.geclipse.core.ISolution;
import eu.geclipse.core.Solution;

public abstract class UISolution extends Solution {
  
  private Shell shell;
  
  protected UISolution( final ISolution slave,
                        final Shell shell ) {
    this( slave.getID(), slave.getText(), shell );
  }
  
  protected UISolution( final int id,
                        final String text,
                        final Shell shell ) {
    super( id, text );
    this.shell = shell;
  }
  
  public Shell getShell() {
    return this.shell;
  }
  
  @Override
  public boolean isActive() {
    return true;
  }
  
  @Override
  public abstract void solve();
  
}
