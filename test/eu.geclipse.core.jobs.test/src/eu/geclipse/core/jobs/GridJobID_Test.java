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

import eu.geclipse.test.GridTestStub;

/**tests the class {@link GridJobID}
 * @author tao-j
 *
 */

public class GridJobID_Test {

  private static GridJobID jobid;
  private static Element rootElement;
  
  /**initial setups: create a jobfolder
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance()
    .newDocumentBuilder();
    String path = GridTestStub.setUpLocalDir() + "jobfolder/.jobID"; //$NON-NLS-1$
    FileInputStream input = new FileInputStream( path );
    Document document = documentBuilder.parse( input);
    input.close();
    rootElement = document.getDocumentElement();
    jobid = new GridJobID( rootElement );
  }

  /**tests the method {@link GridJobID#GridJobID()}
   * 
   */
  @Test
  public void testGridJobID() {
    GridJobID id = new GridJobID();
    Assert.assertNotNull( id );
  }

  /**tests the method {@link GridJobID#GridJobID(org.w3c.dom.Node)}
   * 
   */
  @Test
  public void testGridJobIDNode() {
    Assert.assertNotNull( jobid );
  }

  /**tests the method {@link GridJobID#setXMLNode(org.w3c.dom.Node)}
   * 
   */
  @Test
  public void testSetXMLNode() {
   jobid.setXMLNode( rootElement );
  }

  /**tests the method {@link GridJobID#getJobID()}
   * 
   */
  @Test
  public void testGetJobID() {
    Assert.assertNotNull( jobid.getJobID() );
  }

  /**tests the method GridJobID#setData(String)
   * 
   */
  @Test
  public void testSetData() {
  //the tested method is not implemented 
  }

  /**tests the method {@link GridJobID#getXML()}
   * 
   */
  @Test
  public void testGetXML() {
    Assert.assertNotNull( jobid.getXML() );
  }

  /**tests the method GridJobID#getData()
   * 
   */
  @Test
  public void testGetData() {
  // protected method can not be tested
  }
}

