/* Copyright (c) 2006, 2007 g-Eclipse Consortium 
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
 *    Jie Tao - test class (Plug-in test)
 *****************************************************************************/

package eu.geclipse.core.internal;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;


/**this class tests the methods in {@link PreferenceInitializer}
 * @author tao-j
 *
 */
public class PreferenceInitializer_PDETest {

  PreferenceInitializer init;
  
  /**initialization: create a PreferenceInitializer object
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception {
    this.init = new PreferenceInitializer();
  }

  /**test the method {@link PreferenceInitializer#initializeDefaultPreferences()}
   * check whether the preferences are correctly set
   */
  @Test
  public void testInitializeDefaultPreferences() {
    this.init.initializeDefaultPreferences();
    org.eclipse.core.runtime.Preferences preferenceStore = Activator.getDefault().getPluginPreferences();
    Assert.assertTrue( preferenceStore.getBoolean( PreferenceConstants.JOBS_UPDATE_JOBS_STATUS ));
    Assert.assertEquals( new Integer( 30 ), new Integer( preferenceStore.getInt( 
                        PreferenceConstants.JOBS_UPDATE_JOBS_PERIOD )));
    Assert.assertEquals( new Integer( 30 ), new Integer( preferenceStore.getInt( 
                       PreferenceConstants.JOBS_UPDATE_UPDATERS_LIMTI )));
  }
}
