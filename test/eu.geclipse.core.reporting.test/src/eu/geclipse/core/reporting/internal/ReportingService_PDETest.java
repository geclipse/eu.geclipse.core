/*****************************************************************************
 * Copyright (c) 2006, 2007, 2008 g-Eclipse Consortium 
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

package eu.geclipse.core.reporting.internal;

import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.geclipse.core.ICoreProblems;
import eu.geclipse.core.ICoreSolutions;
import eu.geclipse.core.reporting.IProblem;
import eu.geclipse.core.reporting.ISolution;
import eu.geclipse.voms.IVomsProblems;


/**tests the methods in class {@link ReportingService}
 * @author tao-j
 *
 */
public class ReportingService_PDETest {

  private static ReportingService service;
  
  /**initialization
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    service = ReportingService.getService();
  }

  /**
   * tests the methods {@link ReportingService#getService()}
   */
  @Test
  public void testGetService() {
    Assert.assertNotNull( service );
  }

  /**
   * tests the methods {@link ReportingService#createProblem(String, Throwable, String, String)}
   */
  @Test
  public void testCreateProblem() {
    IProblem problem = service.createProblem( "test", null, null, null ); //$NON-NLS-1$
    Assert.assertNotNull( problem );
    Assert.assertEquals( "test", problem.getDescription() ); //$NON-NLS-1$
  }

  /**
   * tests the methods {@link ReportingService#getProblem(String, String, Throwable, String)}
   */
  @Test
  public void testGetProblem() {
    IProblem problem = service.getProblem( ICoreProblems.AUTH_CERTIFICATE_LOAD_FAILED, 
                                       null, null, null );
    Assert.assertNotNull( problem );
    Assert.assertEquals( "Unable to load certificate", problem.getDescription() ); //$NON-NLS-1$
    Assert.assertNotNull( service.getProblem( ICoreProblems.AUTH_CREDENTIAL_CREATE_FAILED, 
                                       null, null, null ) );
    Assert.assertNotNull( service.getProblem( ICoreProblems.AUTH_CREDENTIAL_SAVE_FAILED, 
                                              null, null, null ) );
    Assert.assertNotNull( service.getProblem( ICoreProblems.AUTH_INVALID_TOKEN_DESCRIPTION, 
                                              null, null, null ) );
    Assert.assertNotNull( service.getProblem( ICoreProblems.AUTH_KEY_LOAD_FAILED, 
                                              null, null, null ) );
    Assert.assertNotNull( service.getProblem( ICoreProblems.AUTH_LOGIN_FAILED, 
                                              null, null, null ) );
    Assert.assertNotNull( service.getProblem( ICoreProblems.AUTH_TOKEN_ACTIVATE_FAILED, 
                                              null, null, null ) );
    Assert.assertNotNull( service.getProblem( ICoreProblems.AUTH_TOKEN_NOT_YET_VALID, 
                                              null, null, null ) );
    Assert.assertNotNull( service.getProblem( ICoreProblems.IO_CORRUPTED_FILE, 
                                              null, null, null ) );
    Assert.assertNotNull( service.getProblem( ICoreProblems.IO_UNSPECIFIED_PROBLEM, 
                                              null, null, null ) );
    Assert.assertNotNull( service.getProblem( ICoreProblems.JOB_SUBMISSION_FAILED, 
                                              null, null, null ) );
    Assert.assertNotNull( service.getProblem( ICoreProblems.MODEL_CONTAINER_CAN_NOT_CONTAIN, 
                                              null, null, null ) );
    Assert.assertNotNull( service.getProblem( ICoreProblems.MODEL_ELEMENT_CREATE_FAILED, 
                                              null, null, null ) );
    Assert.assertNotNull( service.getProblem( ICoreProblems.MODEL_ELEMENT_DELETE_FAILED, 
                                              null, null, null ) );
    Assert.assertNotNull( service.getProblem( ICoreProblems.MODEL_ELEMENT_LOAD_FAILED, 
                                              null, null, null ) );
    Assert.assertNotNull( service.getProblem( ICoreProblems.MODEL_ELEMENT_NOT_MANAGEABLE, 
                                              null, null, null ) );
    Assert.assertNotNull( service.getProblem( ICoreProblems.MODEL_ELEMENT_SAVE_FAILED, 
                                              null, null, null ) );
    Assert.assertNotNull( service.getProblem( ICoreProblems.MODEL_FETCH_CHILDREN_FAILED, 
                                              null, null, null ) );
    Assert.assertNotNull( service.getProblem( ICoreProblems.MODEL_PREFERENCE_CREATION_FAILED, 
                                              null, null, null ) );
    Assert.assertNotNull( service.getProblem( ICoreProblems.MODEL_REFRESH_FAILED, 
                                              null, null, null ) );
    Assert.assertNotNull( service.getProblem( ICoreProblems.NET_BIND_FAILED, 
                                              null, null, null ) );
    Assert.assertNotNull( service.getProblem( ICoreProblems.NET_CONNECTION_FAILED, 
                                              null, null, null ) );
    Assert.assertNotNull( service.getProblem( ICoreProblems.NET_MALFORMED_URL, 
                                              null, null, null ) );
    Assert.assertNotNull( service.getProblem( ICoreProblems.NET_UNKNOWN_HOST, 
                                              null, null, null ) );
    Assert.assertNotNull( service.getProblem( ICoreProblems.SYS_SYSTEM_TIME_CHECK_FAILED, 
                                              null, null, null ) );
    Assert.assertNotNull( service.getProblem( ICoreProblems.TAR_BAD_HEADER_CHECKSUM, 
                                              null, null, null ) );
    Assert.assertNotNull( service.getProblem( ICoreProblems.TAR_INVALID_ENTRY_SIZE, 
                                              null, null, null ) );
    Assert.assertNotNull( service.getProblem( ICoreProblems.TAR_INVALID_ENTRY_TYPE, 
                                              null, null, null ) );
    Assert.assertNotNull( service.getProblem( ICoreProblems.TAR_UNSUPPORTED_ENTRY_TYPE, 
                                              null, null, null ) );
    Assert.assertNotNull( service.getProblem( ICoreProblems.TAR_WRONG_HEADER_SIZE, 
                                              null, null, null ) );
    Assert.assertNotNull( service.getProblem( IVomsProblems.AC_ENCODING_FAILED, 
                                              null, null, null ) );
    Assert.assertNotNull( service.getProblem( IVomsProblems.BAD_RESPONSE, 
                                              null, null, null ) );
    Assert.assertNotNull( service.getProblem( IVomsProblems.CIC_QUERY_FAILED, 
                                              null, null, null ) );
    Assert.assertNotNull( service.getProblem( IVomsProblems.FQAN_ELEMENT_SYNTAX_ERROR, 
                                              null, null, null ) );
    Assert.assertNotNull( service.getProblem( IVomsProblems.REQUEST_BUILD_FAILED, 
                                              null, null, null ) );
    Assert.assertNotNull( service.getProblem( IVomsProblems.REQUEST_WRITE_FAILED, 
                                              null, null, null ) );
    Assert.assertNotNull( service.getProblem( IVomsProblems.RESPONSE_READ_FAILED, 
                                              null, null, null ) );
    Assert.assertNotNull( service.getProblem( IVomsProblems.VO_BAD_TYPE, 
                                              null, null, null ) );
    Assert.assertNotNull( service.getProblem( IVomsProblems.VO_NOT_DEFINED, 
                                              null, null, null ) );
    Assert.assertNotNull( service.getProblem( IVomsProblems.VOMS_ERRORS, 
                                              null, null, null ) );
    Assert.assertNotNull( service.getProblem( IVomsProblems.VOMS_SERVER_CONNECT_FAILED, 
                                              null, null, null ) );
    Assert.assertNotNull( service.getProblem( IVomsProblems.VOMS_SERVER_QUERY_FAILED, 
                                              null, null, null ) );
    Assert.assertNotNull( service.getProblem( IVomsProblems.VOMS_SERVER_REQUEST_FAILED, 
                                              null, null, null ) );
  }

  /**
   * tests the methods {@link ReportingService#createSolution(String, eu.geclipse.core.reporting.ISolver)}
   */
  @Test
  public void testCreateSolution() {
    ISolution solution = service.createSolution( "test", null ); //$NON-NLS-1$
    Assert.assertNotNull( solution );
    Assert.assertEquals( "test", solution.getDescription() ); //$NON-NLS-1$
  }

  /**
   * tests the methods {@link ReportingService#getSolution(String, String)}
   */
  @Test
  public void testGetSolution() {
    Assert.assertNotNull( service.getSolution
                      ( ICoreSolutions.AUTH_CHECK_CA_CERTIFICATES, null ) );
  }
}
