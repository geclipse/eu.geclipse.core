/*****************************************************************************
 * Copyright (c) 2007 g-Eclipse Consortium 
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
 *    mknauer - initial API and implementation
 *****************************************************************************/
package eu.geclipse.jsdl.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLMapImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import eu.geclipse.jsdl.ApplicationType;
import eu.geclipse.jsdl.DocumentRoot;
import eu.geclipse.jsdl.JobDefinitionType;
import eu.geclipse.jsdl.JobDescriptionType;
import eu.geclipse.jsdl.JsdlFactory;
import eu.geclipse.jsdl.JsdlPackage;

/**
 *
 */
public class Jsdl_Emf_PDETest {

  private static final String FILE_NAME = "demo.jsdl"; //$NON-NLS-1$

  private static final String TEST_APPLICATION 
    = "TestApplicationString"; //$NON-NLS-1$
  
  private IProject testProject = null;

  /**
   * Creates a test project in the runtime workspace of JUnit.
   * 
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception
  {
    IWorkspace workspace = ResourcesPlugin.getWorkspace();
    IWorkspaceRoot workspaceRoot = workspace.getRoot();
    this.testProject = workspaceRoot.getProject( "test" ); //$NON-NLS-1$
    this.testProject.create( new NullProgressMonitor() );
    this.testProject.open( new NullProgressMonitor() );
  }

  /**
   * Creates a demo JSDL model and writes it into a file; afterwards the file is
   * loaded again into a second instance of an JSDL model.
   * 
   * @throws IOException
   */
  @Test
  public final void playWithEmfAndJsdl() throws IOException
  {
    EObject jsdlJob = createModel();

    IFile outputFile = this.testProject.getFile( FILE_NAME );
    writeModelToFile( jsdlJob, outputFile );

    EObject eObject = readModelFromFile( outputFile );
    
    Assert.assertNotNull( "eObject is NULL",  //$NON-NLS-1$
                          eObject );
    
    Assert.assertTrue( "eObject not of expected type",  //$NON-NLS-1$
                       eObject instanceof DocumentRoot );
    
    DocumentRoot documentRoot = ( DocumentRoot )eObject;
    JobDefinitionType jobDefinition = documentRoot.getJobDefinition();
    JobDescriptionType jobDescription = jobDefinition.getJobDescription();
    String name = jobDescription.getApplication().getApplicationName();
    
    Assert.assertEquals( TEST_APPLICATION, name );
  }


  // helping methods
  //////////////////
  
  /**
   * Loads the JsdlFactory instance and uses this instance to create new objects
   * of the different JSDL types.
   * 
   * @return a generic <code>EObject</code> (here: a
   *         <code>JobDefinitionType</code> object - this is a common root
   *         element in a JSDL file)
   */
  private EObject createModel() {
    JsdlFactory jsdlFactory = JsdlFactory.eINSTANCE;

    // create a application definition
    ApplicationType application = jsdlFactory.createApplicationType();
    application.setApplicationName( TEST_APPLICATION );
    application.setApplicationVersion( "1" ); //$NON-NLS-1$
    application.setDescription( "THIS IS MY DESCRIPTION" ); //$NON-NLS-1$

    // create a job description and put the application into it
    JobDescriptionType jobDescription = jsdlFactory.createJobDescriptionType();
    jobDescription.setApplication( application );

    // create a job definition and put the job description into it
    JobDefinitionType jobDefinition = jsdlFactory.createJobDefinitionType();
    jobDefinition.setId( "ID" ); //$NON-NLS-1$
    jobDefinition.setJobDescription( jobDescription );

    return jobDefinition;
  }

  /**
   * Writes a EMF model to a specified XML file. The file will be encoded with
   * UTF8.
   * 
   * @param jsdlJob the JSDL Job Description - in this case any
   *          <code>EObject</code> works
   * @param file the output JSDL output file
   * @throws IOException if the save operation failed
   */
  private void writeModelToFile( final EObject jsdlJob,
                                 final IFile file ) throws IOException
  {
    String filePath = file.getFullPath().toString();
    URI uri = URI.createPlatformResourceURI( filePath );
    ResourceSet resourceSet = new ResourceSetImpl();
    Resource resource = resourceSet.createResource( uri );
    resource.getContents().add( jsdlJob );
    Map<String, Object> options = new HashMap<String, Object>();
    options.put( XMLResource.OPTION_ENCODING, "UTF8" ); //$NON-NLS-1$
    resource.save( options );
  }

  /**
   * Reads an JSDL EMF model from a file.
   * 
   * @param file input file with the JSDL data
   * @return an <code>EObject</code> containing the EMF root element of the
   *         file; the file must contain at least one root element
   * @throws IOException if the load operation fails
   */
  private EObject readModelFromFile( final IFile file )
    throws IOException
  {
    String filePath = file.getFullPath().toString();
    URI uri = URI.createPlatformResourceURI( filePath );
    ResourceSet resourceSet = new ResourceSetImpl();
    Resource resource = resourceSet.createResource( uri );

    XMLMapImpl xmlmap = new XMLMapImpl();
    xmlmap.setNoNamespacePackage( JsdlPackage.eINSTANCE );
    Map<String, Object> options = new HashMap<String, Object>();
    options.put( XMLResource.OPTION_XML_MAP, xmlmap );
    options.put( XMLResource.OPTION_ENCODING, "UTF8" ); //$NON-NLS-1$
    
    resource.load( options );

    return ( EObject )resource.getContents().get( 0 );
  }
}

