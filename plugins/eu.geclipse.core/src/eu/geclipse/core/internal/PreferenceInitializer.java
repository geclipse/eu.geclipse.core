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
 *    Mathias Stuempert - initial API and implementation
 *    Szymon Mueller
 *****************************************************************************/

package eu.geclipse.core.internal;

import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;

/**
 * This preference initializer is intended to initialize the preferences
 * of the geclipse core plugin.
 * 
 * @author stuempert-m
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

  /* (non-Javadoc)
   * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
   */
  @Override
  public void initializeDefaultPreferences() {
    Preferences prefs = Activator.getDefault().getPluginPreferences();
    prefs.setDefault( PreferenceConstants.JOBS_UPDATE_JOBS_STATUS, true );
    prefs.setDefault( PreferenceConstants.JOBS_UPDATE_JOBS_CANCEL_BAHAVIOUR, true );
    prefs.setDefault( PreferenceConstants.JOBS_UPDATE_JOBS_PERIOD, 30 );
    prefs.setDefault( PreferenceConstants.JOBS_UPDATE_UPDATERS_LIMTI, 30 );
  }
  
}
