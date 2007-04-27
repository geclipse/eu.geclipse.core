package eu.geclipse.ui.dialogs;

import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.PreferencesUtil;

/**
 * Specialised {@link eu.geclipse.ui.dialogs.Solution} that opens a certain
 * preference page where the problem could be solved.
 * 
 * @author stuempert-m
 */
public class PreferenceSolution extends Solution {
  
  /**
   * The id of the preference page that should be opened.
   */
  private String preferencePageId;
  
  /**
   * The shell that is used to open the preference page.
   */
  private Shell shell;

  /**
   * Create a new <code>PreferenceSolution</code> that will open the 
   * specified preference page when <code>solve</code> is called.
   * 
   * @param text The text of the solution. Has to have HTML link tags in order
   * to be activated.
   * @param preferencePageId The id of the preference page that will be opened
   * when <code>solve</code> is called.
   * @param shell The parent shell on which the preference dialog will be opened.
   */
  public PreferenceSolution( final String text,
                             final String preferencePageId,
                             final Shell shell ) {
    super( text );
    this.preferencePageId = preferencePageId;
    this.shell = shell;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.ui.dialogs.Solution#solve()
   */
  @Override
  protected void solve() {
    PreferenceDialog dialog
    = PreferencesUtil.createPreferenceDialogOn( this.shell,
                                                this.preferencePageId,
                                                null,
                                                null );
    dialog.open();
  }
  
}
