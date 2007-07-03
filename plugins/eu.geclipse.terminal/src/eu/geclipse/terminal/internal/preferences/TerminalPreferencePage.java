/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributors:
 *    Thomas Koeckerbauer GUP, JKU - initial API and implementation
 *****************************************************************************/

package eu.geclipse.terminal.internal.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import eu.geclipse.terminal.internal.Activator;

/**
 * Preference page for the terminal emulator.
 */
public class TerminalPreferencePage extends FieldEditorPreferencePage
  implements IWorkbenchPreferencePage {
  /**
   * Creates the preferences page for the terminal emulator. 
   */
  public TerminalPreferencePage() {
    super( GRID );
    setPreferenceStore( Activator.getDefault().getPreferenceStore() );
    setDescription( Messages.getString("TerminalPreferencePage.description") ); //$NON-NLS-1$
  }

  /**
   * Creates the field editors. Field editors are abstractions of the common GUI
   * blocks needed to manipulate various types of preferences. Each field editor
   * knows how to save and restore itself.
   */
  @Override
  public void createFieldEditors() {
    addField( new IntegerFieldEditor( PreferenceConstants.P_HISTORY_SIZE,
                                      Messages.getString("TerminalPreferencePage.sizeOfHistory"), //$NON-NLS-1$
                                      getFieldEditorParent() ) );
  }

  /* (non-Javadoc)
   * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
   */
  public void init( final IWorkbench workbench ) {
    // nothing to initialize
  }
}
