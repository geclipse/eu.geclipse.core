package eu.geclipse.webview.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import eu.geclipse.webview.Activator;

/**
 * This class represents a preference page that is contributed to the
 * Preferences dialog. By subclassing <samp>FieldEditorPreferencePage</samp>,
 * we can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 */
public class WebviewPreferencePage extends FieldEditorPreferencePage
  implements IWorkbenchPreferencePage
{

  public WebviewPreferencePage() {
    super( GRID );
    setPreferenceStore( Activator.getDefault().getPreferenceStore() );
    setDescription( Messages.getString("WebviewPreferencePage.Description") ); //$NON-NLS-1$
  }

  /**
   * Creates the field editors. Field editors are abstractions of the common GUI
   * blocks needed to manipulate various types of preferences. Each field editor
   * knows how to save and restore itself.
   */
  @Override
  public void createFieldEditors() {
    addField( new StringFieldEditor( PreferenceConstants.PROJECT_URL,
                                        Messages.getString("WebviewPreferencePage.ProjectHomeLabel"), //$NON-NLS-1$
                                        getFieldEditorParent() ) );

    addField( new StringFieldEditor( PreferenceConstants.USERSUPPORT_URL,
                                     Messages.getString("WebviewPreferencePage.UserSupportLabel"), //$NON-NLS-1$
                                     getFieldEditorParent() ) );

    addField( new StringFieldEditor( PreferenceConstants.VOMS_URL,
                                     Messages.getString("WebviewPreferencePage.VOMSLabel"), //$NON-NLS-1$
                                     getFieldEditorParent() ) );

  }

  /* (non-Javadoc)
   * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
   */
  public void init( final IWorkbench workbench ) {
    /*
     * This is on purpose
     */
  }
}