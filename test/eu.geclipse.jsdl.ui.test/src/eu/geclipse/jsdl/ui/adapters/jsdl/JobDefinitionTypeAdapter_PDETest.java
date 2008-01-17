/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     PSNC: 
 *      - Katarzyna Bylec (katis@man.poznan.pl)
 *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
 *           
 *****************************************************************************/
package eu.geclipse.jsdl.ui.adapters.jsdl;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.geclipse.jsdl.JSDLJobDescription;
import eu.geclipse.jsdl.model.JobDefinitionType;
import eu.geclipse.jsdl.model.JsdlFactory;
import eu.geclipse.test.GridTestStub;

/**
 * Class to test JobDefinitionTypeAdapter class. 
 */
public class JobDefinitionTypeAdapter_PDETest {
  
  /**
   * The JobDefinitionType object.
   */
  private static JobDefinitionType jobDefinitionType;
  
  
  /**
   * The JobDefinitionTypeAdapter object.
   */
  private static JobDefinitionTypeAdapter jobDefinitionTypeAdapter;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    jobDefinitionType = JsdlFactory.eINSTANCE.createJobDefinitionType();
    jobDefinitionTypeAdapter = new JobDefinitionTypeAdapter(jobDefinitionType);
    GridTestStub.setUpVO();
    GridTestStub.setUpProject();
    IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
    IProject proj = workspaceRoot.getProject( "TestGridProject" ); //$NON-NLS-1$
    String localfile = GridTestStub.setUpLocalDir() + "testJsdl.jsdl"; //$NON-NLS-1$
    IFile file = proj.getFile( "testJsdl.jsdl" ); //$NON-NLS-1$
    IPath localLocation = new Path (localfile);
    file.createLink( localLocation, 0, null );
    JSDLJobDescription jsdlfile = new JSDLJobDescription(file);
    jobDefinitionType = jsdlfile.getRoot().getJobDefinition();
  }

  @Test
  public void testJobDefinitionTypeAdapter() {
   Assert.assertNotNull( jobDefinitionTypeAdapter );
  }

  @Test
  public void testContentChanged() {
  }

  @Test
  public void testAttachID() {
    /* Cannot test methods that deal with UI widgets */
  }

  @Test
  public void testSetContent() {
    Assert.assertNotNull( jobDefinitionType );    
  }

  @Test
  public void testLoad() {
    /*
     *  Cannot test the load() method since it has access to a HashMap
     *  containing SWT widgets. 
     */    
  }

  @Test
  public void testIsEmpty() {
    Assert.assertFalse( jobDefinitionTypeAdapter.isEmpty() );
  }
}
