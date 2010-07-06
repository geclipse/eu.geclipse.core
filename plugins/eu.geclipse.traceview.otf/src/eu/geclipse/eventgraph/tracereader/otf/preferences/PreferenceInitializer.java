/*****************************************************************************
 * Copyright (c) 2010 g-Eclipse Consortium 
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
 *    Christof Klausecker MNM-Team, LMU Munich - initial API and implementation
 *****************************************************************************/

package eu.geclipse.eventgraph.tracereader.otf.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import eu.geclipse.eventgraph.tracereader.otf.Activator;

/**
 * Initializer for Nope Preferences
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

  @Override
  public void initializeDefaultPreferences() {
    IPreferenceStore store = Activator.getDefault().getPreferenceStore();
    store.setDefault( PreferenceConstants.calculateVectorClocks, false );
    store.setDefault( PreferenceConstants.readFunctions, true );
  }
}
