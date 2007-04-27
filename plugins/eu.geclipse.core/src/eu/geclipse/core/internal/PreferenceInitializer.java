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
 *****************************************************************************/

package eu.geclipse.core.internal;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.osgi.service.prefs.Preferences;

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
    Preferences node = new DefaultScope().getNode( Activator.PLUGIN_ID );
    node.putInt( PreferenceConstants.CONNECTION_TIMEOUT, 20 );
    node.putBoolean( PreferenceConstants.HTTP_PROXY_ENABLED, false );
    node.put( PreferenceConstants.HTTP_PROXY_HOST, "" ); //$NON-NLS-1$
    node.putInt( PreferenceConstants.HTTP_PROXY_PORT, 0 );
    node.putBoolean( PreferenceConstants.HTTP_PROXY_AUTH_REQUIRED, false );
    node.put( PreferenceConstants.HTTP_PROXY_AUTH_LOGIN, "" ); //$NON-NLS-1$
    node.put( PreferenceConstants.HTTP_PROXY_AUTH_PW, "" ); //$NON-NLS-1$
  }
  
}
