/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
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
 *    Moritz Post - initial API and implementation
 *****************************************************************************/

package eu.geclipse.aws.ec2;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.Ignore;
import org.junit.Test;

import eu.geclipse.aws.AWSInfoService;
import eu.geclipse.aws.ec2.test.util.AWSVoTestUtil;
import eu.geclipse.aws.vo.AWSVirtualOrganization;
import eu.geclipse.core.model.IGridResource;

/**
 * @author Moritz Post
 */
public class EC2InfoService_PDETest {

  /**
   * Test method for the
   * {@link AWSInfoService#fetchResources(eu.geclipse.core.model.IGridContainer, eu.geclipse.core.model.IVirtualOrganization, eu.geclipse.core.model.IGridResourceCategory, org.eclipse.core.runtime.IProgressMonitor)}
   * method.
   * 
   * @throws Exception thrown when an error occurred
   */
  @Test
  @Ignore("Test required user interaction in auth token wizard")
  public void testGetComputingElements() throws Exception {

    // the test does not work in its current form

    AWSVirtualOrganization awsVo = AWSVoTestUtil.getAwsVo();
    AWSInfoService infoService = new AWSInfoService( awsVo );

    IGridResource[] computingElements = infoService.fetchResources( awsVo,
                                                                    awsVo,
                                                                    null,
                                                                    new NullProgressMonitor() );
    assertNotNull( computingElements );
    assertTrue( computingElements.length > 0 );

    // faulty ec2 service -> failure
    infoService = new AWSInfoService( awsVo );

    computingElements = infoService.fetchResources( awsVo,
                                                    awsVo,
                                                    null,
                                                    new NullProgressMonitor() );
    assertNotNull( computingElements );
    assertTrue( computingElements.length > 0 );
  }
}
