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
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

import eu.geclipse.traceview.internal.Activator;

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
                                    Display.getCurrent()
                                      .getSystemColor( SWT.COLOR_BLACK )
                                      .getRGB() );
    // event color
    PreferenceConverter.setDefault( store,
                                    PreferenceConstants.P_SEND_EVENT
                                        + PreferenceConstants.P_COLOR,
                                    Display.getCurrent()
                                      .getSystemColor( SWT.COLOR_BLACK )
                                      .getRGB() );
    PreferenceConverter.setDefault( store,
                                    PreferenceConstants.P_RECV_EVENT
                                        + PreferenceConstants.P_COLOR,
                                    Display.getCurrent()
                                      .getSystemColor( SWT.COLOR_BLACK )
                                      .getRGB() );
    PreferenceConverter.setDefault( store,
                                    PreferenceConstants.P_TEST_EVENT
                                        + PreferenceConstants.P_COLOR,
                                    Display.getCurrent()
                                      .getSystemColor( SWT.COLOR_GREEN )
                                      .getRGB() );
    PreferenceConverter.setDefault( store,
                                    PreferenceConstants.P_OTHER_EVENT
                                        + PreferenceConstants.P_COLOR,
                                    Display.getCurrent()
                                      .getSystemColor( SWT.COLOR_BLUE )
                                      .getRGB() );
    // event fill color
    PreferenceConverter.setDefault( store,
                                    PreferenceConstants.P_SEND_EVENT
                                        + PreferenceConstants.P_FILL_COLOR,
                                    Display.getCurrent()
                                      .getSystemColor( SWT.COLOR_DARK_MAGENTA )
                                      .getRGB() );
    PreferenceConverter.setDefault( store,
                                    PreferenceConstants.P_RECV_EVENT
                                        + PreferenceConstants.P_FILL_COLOR,
                                    Display.getCurrent()
                                      .getSystemColor( SWT.COLOR_YELLOW )
                                      .getRGB() );
    PreferenceConverter.setDefault( store,
                                    PreferenceConstants.P_TEST_EVENT
                                        + PreferenceConstants.P_FILL_COLOR,
                                    Display.getCurrent()
                                      .getSystemColor( SWT.COLOR_GREEN )
                                      .getRGB() );
    PreferenceConverter.setDefault( store,
                                    PreferenceConstants.P_OTHER_EVENT
                                        + PreferenceConstants.P_FILL_COLOR,
                                    Display.getCurrent()
                                      .getSystemColor( SWT.COLOR_BLUE )
                                      .getRGB() );
    // selection Color
    PreferenceConverter.setDefault( store,
                                    PreferenceConstants.P_SELECTION_COLOR,
                                    Display.getCurrent()
                                      .getSystemColor( SWT.COLOR_GRAY )
                                      .getRGB() );
  }
}
