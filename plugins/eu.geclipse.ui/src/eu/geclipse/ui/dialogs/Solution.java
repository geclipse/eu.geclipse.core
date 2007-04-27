package eu.geclipse.ui.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Link;

/**
 * A solution is used in combination with a {@link eu.geclipse.ui.dialogs.ProblemDialog}
 * to present solutions for a certain problem to the user. This class is the base class
 * for any solution. This default implementation does nothing except for displaying a
 * solution text to the user.
 * 
 * @author stuempert-m
 */
public class Solution {
  
  /**
   * The solutions text. Has to contain HTML link tags to get an active link.
   */
  private String text;
  
  /**
   * Create a new solution. The text has to have HTML link tags in order to be active.
   * 
   * @param text A text that may contain a link part.
   */
  public Solution( final String text ) {
    this.text = text;
  }
  
  /**
   * Create a <code>Link</code> from the text of this solution and add it to the specified parent.
   * 
   * @param parent The parent of the newly created <code>Link</code>.
   * @return The newly created <code>Link</code>:
   */
  public Link createLink( final Composite parent ) {
    Link link = new Link( parent, SWT.NONE );
    link.setText( this.text );
    return link;
  }
  
  /**
   * Try to solve the problem. This default implementation does nothing.
   */
  protected void solve() {
    // empty implementation
  }
  
}
