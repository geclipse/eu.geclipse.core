/*******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Initial development of the original code was made for the g-Eclipse project 
 * funded by European Union project number: FP6-IST-034327 
 * http://www.geclipse.eu/
 *  
 * Contributors:
 *     RUR (http://acet.rdg.ac.uk/)
 *     - Ashish Thandavan - initial API and implementation
 ******************************************************************************/
package eu.geclipse.workflow.model.test;

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
import eu.geclipse.workflow.IWorkflow;
import eu.geclipse.workflow.IWorkflowFactory;
import eu.geclipse.workflow.IWorkflowJob;
import eu.geclipse.workflow.IWorkflowPackage;
import eu.geclipse.workflow.WorkflowFactory;



/**
 * @author ash
 *
 */
public class WorkflowModel_PDETest {

  private static final String FILE_NAME = "test.workflow"; //$NON-NLS-1$

  private static final String TEST_WORKFLOW  = "TestWorkflowName"; //$NON-NLS-1$
  
  private static final String TEST_WORKFLOW_JOB = "TestWorkflowJobName"; //$NON-NLS-1$
  
  private IProject testProject = null;
  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception
  {
    IWorkspace workspace = ResourcesPlugin.getWorkspace();
    IWorkspaceRoot workspaceRoot = workspace.getRoot();
    this.testProject = workspaceRoot.getProject( "junittest" ); //$NON-NLS-1$
    if (workspaceRoot.findMember( "junittest" ) == null ) { //$NON-NLS-1$ 
    this.testProject.create( new NullProgressMonitor() );
    }
    this.testProject.open( new NullProgressMonitor() );
  }
  
  /**
   * Creates a demo workflow model and writes it into a file; later the file is
   * loaded again into a second instance of a workflow model.
   * 
   * @throws IOException
   */
  @Test
  public final void playWithEmfAndWorkflowModel() throws IOException
  {
    EObject workflowModel = createModel();
    
    IFile outputFile = this.testProject.getFile( FILE_NAME );
    writeModelToFile( workflowModel, outputFile );

    EObject eObject = readModelFromFile( outputFile );
    
    Assert.assertNotNull( "eObject is NULL",  //$NON-NLS-1$
                          eObject );
    
    Assert.assertTrue( "eObject not of expected type",  //$NON-NLS-1$
                       eObject instanceof IWorkflow );
    
    IWorkflow workflow = ( IWorkflow )eObject;

    String name = workflow.getName();
    
    Assert.assertEquals( TEST_WORKFLOW, name );
  }


  
  /**
   * Loads the WorkflowFactory instance and uses this instance to create new objects
   * of the different workflow types.
   * 
   * @return a generic <code>EObject</code> (here: a
   *         <code>JobDefinitionType</code> object - this is a common root
   *         element in a JSDL file)
   */
  private EObject createModel() {
    WorkflowFactory workflowFactory = WorkflowFactory.eINSTANCE;
    
    IWorkflow workflow = workflowFactory.createIWorkflow();
    workflow.setName( TEST_WORKFLOW );
    workflow.setId( "someID" );  //$NON-NLS-1$
    
    IWorkflowJob workflowJob = workflowFactory.createIWorkflowJob();
    workflowJob.setName( TEST_WORKFLOW_JOB );
    workflowJob.setWorkflow( workflow );

    return workflow;
  }

  /** 
   * Writes an EMF model to a specified XML file. The file will be encoded with
   * UTF8.
   * 
   * @param workflow the Workflow Description - in this case any
   *          <code>EObject</code> works
   * @param file the output workflow file
   * @throws IOException if the save operation failed
   */
  private void writeModelToFile( final EObject workflow,
                                 final IFile file ) throws IOException
  {
    
    String filePath = file.getFullPath().toString();
    URI uri = URI.createURI( filePath );
    ResourceSet resourceSet = new ResourceSetImpl();
    Resource resource = resourceSet.createResource( uri );
    resource.getContents().add( workflow );
    Map<String, Object> options = new HashMap<String, Object>();
    options.put( XMLResource.OPTION_ENCODING, "UTF8" ); //$NON-NLS-1$
    resource.save( options );
  }

  /**
   * Reads a Workflow EMF model from a file.
   * 
   * @param file input file with the workflow data
   * @return an <code>EObject</code> containing the EMF root element of the
   *         file; the file must contain at least one root element
   * @throws IOException if the load operation fails
   */
  private EObject readModelFromFile( final IFile file )
    throws IOException
  {
    String filePath = file.getFullPath().toString();
    URI uri = URI.createURI( filePath );
    ResourceSet resourceSet = new ResourceSetImpl();
    Resource resource = resourceSet.createResource( uri );

    XMLMapImpl xmlmap = new XMLMapImpl();
    xmlmap.setNoNamespacePackage( IWorkflowPackage.eINSTANCE );
    Map<String, Object> options = new HashMap<String, Object>();
    options.put( XMLResource.OPTION_XML_MAP, xmlmap );
    options.put( XMLResource.OPTION_ENCODING, "UTF8" ); //$NON-NLS-1$
    
    resource.load( options );

    return resource.getContents().get( 0 );
  }
}
