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

package eu.geclipse.core.jobs;

import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import eu.geclipse.core.model.IGridJobID;
import eu.geclipse.core.model.IGridJobStatus;


/**tests the class {@link GridJobStatus}
 * @author tao-j
 *
 */
public class GridJobStatus_Test {

  private static GridJobStatus jobstatus;
  private static Element rootElement;
  
  /**initial setups
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance()
    .newDocumentBuilder();
    String path = "d:/geclipsetest/" + "jobfolder/.jobID"; //$NON-NLS-1$ //$NON-NLS-2$
    FileInputStream input = new FileInputStream( path );
    Document document = documentBuilder.parse( input);
    input.close();
    rootElement = document.getDocumentElement();
 }

  /**tests the method {@link GridJobStatus#GridJobStatus()}
   * 
   */
  @Test
  public void testGridJobStatus() {
    jobstatus = new GridJobStatus();
    Assert.assertNotNull( jobstatus );
    Assert.assertEquals( "Unknown", jobstatus.getName() ); //$NON-NLS-1$
  }

  /**tests the method {@link GridJobStatus#GridJobStatus(String)}
   * 
   */
  @Test
  public void testGridJobStatusString() {
   String reason = "middleware failor"; //$NON-NLS-1$
   jobstatus = new GridJobStatus( reason );
   Assert.assertNotNull( jobstatus );
   Assert.assertEquals( "middleware failor", jobstatus.getReason() ); //$NON-NLS-1$
  }

  /**tests the method {@link GridJobStatus#GridJobStatus(String, int)}
   * 
   */
  @Test
  public void testGridJobStatusStringInt() {
    String name = "test"; //$NON-NLS-1$
    jobstatus = new GridJobStatus( name, IGridJobStatus.SUBMITTED);
    Assert.assertNotNull( jobstatus );
    Assert.assertEquals( "test", jobstatus.getName() ); //$NON-NLS-1$
    Assert.assertEquals( new Integer( IGridJobStatus.SUBMITTED ), new Integer( jobstatus.getType()));
  }

  /**tests the method {@link GridJobStatus#GridJobStatus(IGridJobID)}
   * 
   */
  @Test
  public void testGridJobStatusIGridJobID() {
   IGridJobID jobid = new GridJobID();
   jobstatus = new GridJobStatus( jobid );
   Assert.assertNotNull( jobstatus );
  }

  /**tests the method {@link GridJobStatus#GridJobStatus(org.w3c.dom.Node)}
   * 
   */
  @Test
  public void testGridJobStatusNode() {
    jobstatus = new GridJobStatus( rootElement );
    Assert.assertNotNull( jobstatus );
  }

  /**tests the method {@link GridJobStatus#setXMLNode(org.w3c.dom.Node)}
   * 
   */
  @Test
  public void testSetXMLNode() {
   jobstatus.setXMLNode( rootElement );
  }

  /**tests the method {@link GridJobStatus#canChange()}
   * 
   */
  @Test
  public void testCanChange() {
    jobstatus = new GridJobStatus();
    Assert.assertTrue( jobstatus.canChange() );
  }

  /**tests the method {@link GridJobStatus#isSuccessful()}
   * 
   */
  @Test
  public void testIsSuccessful() {
    jobstatus = new GridJobStatus();
    Assert.assertFalse( jobstatus.isSuccessful() );
    jobstatus = new GridJobStatus("Test",IGridJobStatus.DONE); //$NON-NLS-1$
    Assert.assertTrue( jobstatus.isSuccessful() );
  }

  /**tests the method {@link GridJobStatus#getJobStatusData()}
   * 
   */
  @Test
  public void testGetJobStatusData() {
    jobstatus = new GridJobStatus();
    Assert.assertNull( jobstatus.getData() );
  }

  /**tests the method {@link GridJobStatus#getXML()}
   * 
   */
  @Test
  public void testGetXML() {
    Assert.assertNotNull( jobstatus.getXML() );
  }

  /**tests the method {@link GridJobStatus#getName()}
   * 
   */
  @Test
  public void testGetName() {
    jobstatus = new GridJobStatus();
    Assert.assertEquals( "Unknown", jobstatus.getName() ); //$NON-NLS-1$
  }

  /**tests the method {@link GridJobStatus#getType()}
   * 
   */
  @Test
  public void testGetType() {
    jobstatus = new GridJobStatus("Test",IGridJobStatus.DONE); //$NON-NLS-1$
    Assert.assertEquals( new Integer( IGridJobStatus.DONE ), new Integer( jobstatus.getType()) );

  }

  /**tests the method {@link GridJobStatus#getReason()}
   * 
   */
  @Test
  public void testGetReason() {
    String reason = "middleware failor"; //$NON-NLS-1$
    jobstatus = new GridJobStatus( reason );
    Assert.assertEquals( "middleware failor", jobstatus.getReason() ); //$NON-NLS-1$
  }

  /**tests the method {@link GridJobStatus#getLastUpdateTime()}
   * 
   */
  @Test
  public void testGetLastUpdateTime() {
    jobstatus = new GridJobStatus();
    Assert.assertNotNull( jobstatus.getLastUpdateTime() );
  }

  /**tests the method GridJobStatus#getData()
   * 
   */
  @Test
  public void testGetData() {
    Assert.assertNull( jobstatus.getData() );
  }

  /**tests the method GridJobStatus#setData(String)
   * 
   */
  @Test
  public void testSetData() {
    //tested method is not implemented
  }
}
