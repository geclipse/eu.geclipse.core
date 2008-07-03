/*******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *    Sylva Girtelschmid GUP, JKU - initial API and implementation
 ******************************************************************************/
package eu.geclipse.ui.visualisation.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import eu.geclipse.ui.internal.Activator;

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
public class VisualisationPreferencePage extends FieldEditorPreferencePage
  implements IWorkbenchPreferencePage
{

  public VisualisationPreferencePage() {
    super( GRID );
    setPreferenceStore( Activator.getDefault().getPreferenceStore() );
    setDescription( Messages.getString( "VisPreferencePage.settingsForScVisualisation" ) ); //$NON-NLS-1$
  }

  /**
   * Creates the field editors. Field editors are abstractions of the common GUI
   * blocks needed to manipulate various types of preferences. Each field editor
   * knows how to save and restore itself.
   */
  @Override
  public void createFieldEditors() {
    addField( new RadioGroupFieldEditor( PreferenceConstants.P_RENDERING_OPTION,
                                         Messages.getString( "VisPreferencePage.renderingOption" ), //$NON-NLS-1$
                                         1,
                                         new String[][]{
                                           {
                                             "&Local", "local"}, //$NON-NLS-1$ //$NON-NLS-2$
                                           {
                                             "&Remote", "remote"}}, //$NON-NLS-1$ //$NON-NLS-2$
                                         getFieldEditorParent(),
                                         true ) );
    addField( new BooleanFieldEditor( PreferenceConstants.P_CREATE_NEW_PROJECT_OPTION,
                                      Messages.getString( "VisPreferencePage.createNewProjectBoolean" ), //$NON-NLS-1$
                                      getFieldEditorParent() ) );
  }

  /* (non-Javadoc)
   * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
   */
  public void init( IWorkbench workbench ) {
    //noop
  }
}