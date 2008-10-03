/*****************************************************************************
 * Copyright (c) 2006, 2008 g-Eclipse Consortium 
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
 *    Christof Klausecker GUP, JKU - initial API and implementation
 *****************************************************************************/

package eu.geclipse.traceview.nope.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.RGB;

import eu.geclipse.traceview.IEventMarker;
import eu.geclipse.traceview.nope.Activator;

/**
 * Initializer for Nope Preferences
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {
 
  @Override
  public void initializeDefaultPreferences() {
    IPreferenceStore store = Activator.getDefault().getPreferenceStore();
    // disable vector clocks
    store.setDefault( PreferenceConstants.vectorClocks, true );
    
    // disable markers
    for( int i = 0; i < PreferenceConstants.Names.length; i++ ) {
      store.setDefault( PreferenceConstants.Names[ i ]
                        + PreferenceConstants.enabled, false );
    }
    // set all markers to ellipse
    for( int i = 0; i < PreferenceConstants.Names.length; i++ ) {
      store.setDefault( PreferenceConstants.Names[ i ]
                        + PreferenceConstants.shape, IEventMarker.Ellipse_Event );
    }
    // make all colors black
    for( int i = 0; i < PreferenceConstants.Names.length; i++ ) {
      PreferenceConverter.setDefault( store,
                                      PreferenceConstants.Names[ i ]
                                          + PreferenceConstants.color,
                                      new RGB(0,0,0) );
    }
  }
}
