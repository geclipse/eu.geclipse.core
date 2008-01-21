/**
 * 
 */
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
 * Class to test JobIdentificationTypeAdapter class. 
 */
public class JobIdentificationTypeAdapter_PDETest {
  
  
  /**
   * The JobDefinitionType object.
   */
  private static JobDefinitionType jobDefinitionType;
  
  
  /**
   * The JobIdentificationTypeAdapter object.
   */
  
  private static JobIdentificationTypeAdapter jobIdentificationTypeAdapter;

  /**
   * @throws java.lang.Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    jobDefinitionType = JsdlFactory.eINSTANCE.createJobDefinitionType();
    // jobIdentificationTypeAdapter = new JobIdentificationTypeAdapter(jobDefinitionType);
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
    jobIdentificationTypeAdapter = new JobIdentificationTypeAdapter(jobDefinitionType);
  }

  /**
   * Test method for {@link eu.geclipse.jsdl.ui.adapters.jsdl.JobIdentificationTypeAdapter#JobIdentificationTypeAdapter(org.eclipse.emf.ecore.EObject)}.
   */
  @Test
  public void testJobIdentificationTypeAdapter() {
    Assert.assertNotNull( jobIdentificationTypeAdapter );
  }

  /**
   * Test method for {@link eu.geclipse.jsdl.ui.adapters.jsdl.JobIdentificationTypeAdapter#setContent(org.eclipse.emf.ecore.EObject)}.
   */
  @Test
  public void testSetContent() {
    Assert.assertNotNull( jobIdentificationTypeAdapter );
  }

  /**
   * Test method for eu.geclipse.jsdl.ui.adapters.jsdl.JobIdentificationTypeAdapter#contentChanged()
   */
  @Test
  public void testContentChanged() {
    jobIdentificationTypeAdapter.contentChanged();
  }

  /**
   * Test method for {@link eu.geclipse.jsdl.ui.adapters.jsdl.JobIdentificationTypeAdapter#attachToJobName(org.eclipse.swt.widgets.Text)}.
   */
  @Test
  public void testAttachToJobName() {
    /* Cannot test methods that deal with UI widgets */
  }

  /**
   * Test method for {@link eu.geclipse.jsdl.ui.adapters.jsdl.JobIdentificationTypeAdapter#attachToJobDescription(org.eclipse.swt.widgets.Text)}.
   */
  @Test
  public void testAttachToJobDescription() {
    /* Cannot test methods that deal with UI widgets */
  }

  /**
   * Test method for {@link eu.geclipse.jsdl.ui.adapters.jsdl.JobIdentificationTypeAdapter#attachToJobProject(org.eclipse.swt.widgets.List)}.
   */
  @Test
  public void testAttachToJobProject() {
    /* Cannot test methods that deal with UI widgets */
  }

  /**
   * Test method for {@link eu.geclipse.jsdl.ui.adapters.jsdl.JobIdentificationTypeAdapter#attachToJobAnnotation(org.eclipse.swt.widgets.List)}.
   */
  @Test
  public void testAttachToJobAnnotation() {
    /* Cannot test methods that deal with UI widgets */
  }

  /**
   * Test method for {@link eu.geclipse.jsdl.ui.adapters.jsdl.JobIdentificationTypeAdapter#attachToDelete(org.eclipse.swt.widgets.Button, org.eclipse.swt.widgets.List)}.
   */
  @Test
  public void testAttachToDelete() {
    /* Cannot test methods that deal with UI widgets */
  }

  /**
   * Test method for eu.geclipse.jsdl.ui.adapters.
   * jsdl.JobIdentificationTypeAdapter#performDelete
   * (org.eclipse.swt.widgets.List, java.lang.String)
   */
  @Test
  public void testPerformDelete() {
    /* Cannot test methods that deal with UI widgets */
  }

  /**
   * Test method for {@link eu.geclipse.jsdl.ui.adapters.jsdl.JobIdentificationTypeAdapter#performAdd(org.eclipse.swt.widgets.List, java.lang.String, java.lang.Object)}.
   */
  @Test
  public void testPerformAdd() {
    /* Cannot test methods that deal with UI widgets */
  }

  /**
   * Test method for {@link eu.geclipse.jsdl.ui.adapters.jsdl.JobIdentificationTypeAdapter#load()}.
   */
  @Test
  public void testLoad() {
    /*
     *  Cannot test the load() method since it has access to a HashMap
     *  containing SWT widgets. 
     */ 
  }

  /**
   * Test method for {@link eu.geclipse.jsdl.ui.adapters.jsdl.JobIdentificationTypeAdapter#isEmpty()}.
   */
  @Test
  public void testIsEmpty() {
    Assert.assertTrue( jobIdentificationTypeAdapter.isEmpty() );
  }
}
