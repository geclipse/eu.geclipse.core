package eu.geclipse.ui;

import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.PreferencesUtil;
import eu.geclipse.core.ISolution;

public class PreferenceSolution extends UISolution {
  
  public static final String NETWORK_PREFERENCE_PAGE
    = "eu.geclipse.ui.internal.preference.NetworkPreferencePage"; //$NON-NLS-1$
  
  /**
   * The id of the preference page that should be opened.
   */
  private String preferencePageId;
  
  public PreferenceSolution( final ISolution slave,
                             final Shell shell,
                             final String preferencePageId ) {
    this( slave.getID(), slave.getText(), shell, preferencePageId );
  }

  public PreferenceSolution( final int id,
                             final String text,
                             final Shell shell,
                             final String preferencePageId ) {
    super( id, text, shell );
    this.preferencePageId = preferencePageId;
  }
  
  @Override
  public boolean isActive() {
    return true;
  }

  @Override
  public void solve() {
    PreferenceDialog dialog
      = PreferencesUtil.createPreferenceDialogOn( getShell(),
                                                  this.preferencePageId,
                                                  null,
                                                  null );
    dialog.open();
  }
  
}
