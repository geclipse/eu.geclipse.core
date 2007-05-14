/*******************************************************************************
 * Copyright (c) 2006 g-Eclipse Consortium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Harald Kornmayer - initial implementation
 *******************************************************************************/

package eu.geclipse.webview.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import eu.geclipse.webview.Activator;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
   */
  @Override
  public void initializeDefaultPreferences() {
    IPreferenceStore store = Activator.getDefault().getPreferenceStore();
    store.setDefault( PreferenceConstants.PROJECT_URL, "http://www.geclipse.eu/" ); //$NON-NLS-1$
    store.setDefault( PreferenceConstants.USERSUPPORT_URL, "http://www.ggus.org/" ); //$NON-NLS-1$
    store.setDefault( PreferenceConstants.VOMS_URL, "https://dgrid-voms.fzk.de:8443/voms/geclipse/" ); //$NON-NLS-1$
  }
}
