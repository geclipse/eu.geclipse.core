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
 *    Jie Tao - test class (Junit test)
 *****************************************************************************/

package eu.geclipse.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/** this is a test class for the functionality of the methods in class Messages
 * 
 * @author jie
 *
 */

public class Messages_Test {
  private final String key1 = "IGridProject.dir_mounts"; //$NON-NLS-1$
  private final String key2 = "failmessage"; //$NON-NLS-1$
    
  /**
   * @brief Set up the number of frames to test the Messages class.
   * @throws Exception IOException
   */

  @Before
  public void setUp() throws Exception
  {
    //  currently nothing to be inserted
  }

  /**
   * @brief Test method for
   * {@link eu.geclipse.core.Messages#getString(String)}.
   * @throws Exception IOException
   */
  
  @Test
  public void testGetString() throws Exception { 
    Assert.assertEquals( Messages.getString( this.key1 ), "Filesystems" ); //$NON-NLS-1$
    Assert.assertEquals( Messages.getString( this.key2 ), "!failmessage!" ); //$NON-NLS-1$
  }
}
