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

package eu.geclipse.traceview.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.RGB;

import eu.geclipse.traceview.internal.Activator;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {
  private final static RGB RGB_BLACK = new RGB(0, 0, 0);
  private final static RGB RGB_GREEN = new RGB(0, 255, 0);
  private final static RGB RGB_BLUE = new RGB(0, 0, 255);
  private final static RGB RGB_DARK_MAGENTA = new RGB(128, 0, 128);
  private final static RGB RGB_YELLOW = new RGB(255, 255, 0);
  private final static RGB RGB_GRAY = new RGB(192, 192, 192);

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
   */
  @Override
  public void initializeDefaultPreferences() {
    IPreferenceStore store = Activator.getDefault().getPreferenceStore();
    // settings
    store.setDefault( PreferenceConstants.P_ANTI_ALIASING, false );
    // draw
    store.setDefault( PreferenceConstants.P_SEND_EVENT
                      + PreferenceConstants.P_DRAW, true );
    store.setDefault( PreferenceConstants.P_RECV_EVENT
                      + PreferenceConstants.P_DRAW, true );
    store.setDefault( PreferenceConstants.P_TEST_EVENT
                      + PreferenceConstants.P_DRAW, true );
    store.setDefault( PreferenceConstants.P_OTHER_EVENT
                      + PreferenceConstants.P_DRAW, true );
    // fill
    store.setDefault( PreferenceConstants.P_SEND_EVENT
                      + PreferenceConstants.P_FILL, true );
    store.setDefault( PreferenceConstants.P_RECV_EVENT
                      + PreferenceConstants.P_FILL, true );
    store.setDefault( PreferenceConstants.P_TEST_EVENT
                      + PreferenceConstants.P_FILL, false );
    store.setDefault( PreferenceConstants.P_OTHER_EVENT
                      + PreferenceConstants.P_FILL, false );
    // message color
    PreferenceConverter.setDefault( store,
                                    PreferenceConstants.P_MESSAGE
                                        + PreferenceConstants.P_COLOR,
                                    RGB_BLACK );
    // event color
    PreferenceConverter.setDefault( store,
                                    PreferenceConstants.P_SEND_EVENT
                                        + PreferenceConstants.P_COLOR,
                                    RGB_BLACK );
    PreferenceConverter.setDefault( store,
                                    PreferenceConstants.P_RECV_EVENT
                                        + PreferenceConstants.P_COLOR,
                                    RGB_BLACK );
    PreferenceConverter.setDefault( store,
                                    PreferenceConstants.P_TEST_EVENT
                                        + PreferenceConstants.P_COLOR,
                                    RGB_GREEN );
    PreferenceConverter.setDefault( store,
                                    PreferenceConstants.P_OTHER_EVENT
                                        + PreferenceConstants.P_COLOR,
                                    RGB_BLUE );
    // event fill color
    PreferenceConverter.setDefault( store,
                                    PreferenceConstants.P_SEND_EVENT
                                        + PreferenceConstants.P_FILL_COLOR,
                                    RGB_DARK_MAGENTA );
    PreferenceConverter.setDefault( store,
                                    PreferenceConstants.P_RECV_EVENT
                                        + PreferenceConstants.P_FILL_COLOR,
                                    RGB_YELLOW );
    PreferenceConverter.setDefault( store,
                                    PreferenceConstants.P_TEST_EVENT
                                        + PreferenceConstants.P_FILL_COLOR,
                                    RGB_GREEN);
    PreferenceConverter.setDefault( store,
                                    PreferenceConstants.P_OTHER_EVENT
                                        + PreferenceConstants.P_FILL_COLOR,
                                    RGB_BLUE );
    // selection Color
    PreferenceConverter.setDefault( store,
                                    PreferenceConstants.P_SELECTION_COLOR,
                                    RGB_GRAY );
    
    // cache dir
    store.setDefault( PreferenceConstants.P_CACHE_DIR, System.getProperty( "java.io.tmpdir" ) ); //$NON-NLS-1$
  }
}
