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

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import eu.geclipse.ui.internal.Activator;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

  private static final String DEFAULT_RENDERING = "remote"; //$NON-NLS-1$

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
   */
  public void initializeDefaultPreferences() {
    IPreferenceStore store = Activator.getDefault().getPreferenceStore();
    store.setDefault( PreferenceConstants.P_RENDERING_OPTION, DEFAULT_RENDERING );
    store.setDefault( PreferenceConstants.P_USING_TEMPLATE_OPTION, false );
    store.setDefault( PreferenceConstants.P_CREATE_NEW_PROJECT_OPTION, false );
  }
}
