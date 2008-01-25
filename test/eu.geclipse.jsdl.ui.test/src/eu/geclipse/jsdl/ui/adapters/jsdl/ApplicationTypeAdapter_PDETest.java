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
 * JUnit test Class for JobDefinitionTypeAdapter class. 
 */
public class ApplicationTypeAdapter_PDETest {
  
  
  /**
   * The JobDefinitionType object.
   */
  private static JobDefinitionType jobDefinitionType;
  
  
  /**
   * The ApplicationTypeAdapter object.
   */
  private static ApplicationTypeAdapter applicationTypeAdapter;

  /**
   * @throws java.lang.Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    jobDefinitionType = JsdlFactory.eINSTANCE.createJobDefinitionType();
    //applicationTypeAdapter = new ApplicationTypeAdapter(jobDefinitionType);
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
    applicationTypeAdapter = new ApplicationTypeAdapter(jobDefinitionType);
  }

  /**
   * Test method for {@link eu.geclipse.jsdl.ui.adapters.jsdl.ApplicationTypeAdapter#ApplicationTypeAdapter(eu.geclipse.jsdl.model.JobDefinitionType)}.
   */
  @Test
  public void testApplicationTypeAdapter() {
    Assert.assertNotNull( applicationTypeAdapter );
  }

  /**
   * Test method for eu.geclipse.jsdl.ui.adapters.jsdl.ApplicationTypeAdapter#contentChanged()
   */
  @Test
  public void testContentChanged() {
    applicationTypeAdapter.contentChanged();
  }

  /**
   * Test method for {@link eu.geclipse.jsdl.ui.adapters.jsdl.ApplicationTypeAdapter#setContent(eu.geclipse.jsdl.model.JobDefinitionType)}.
   */
  @Test
  public void testSetContent() {
    Assert.assertNull( applicationTypeAdapter );
  }

  /**
   * Test method for {@link eu.geclipse.jsdl.ui.adapters.jsdl.ApplicationTypeAdapter#attachToApplicationName(org.eclipse.swt.widgets.Text)}.
   */
  @Test
  public void testAttachToApplicationName() {
    /* Cannot test methods that deal with UI widgets */
  }

  /**
   * Test method for {@link eu.geclipse.jsdl.ui.adapters.jsdl.ApplicationTypeAdapter#attachToApplicationVersion(org.eclipse.swt.widgets.Text)}.
   */
  @Test
  public void testAttachToApplicationVersion() {
    /* Cannot test methods that deal with UI widgets */
  }

  /**
   * Test method for {@link eu.geclipse.jsdl.ui.adapters.jsdl.ApplicationTypeAdapter#attachToApplicationDescription(org.eclipse.swt.widgets.Text)}.
   */
  @Test
  public void testAttachToApplicationDescription() {
    /* Cannot test methods that deal with UI widgets */
  }

  /**
   * Test method for {@link eu.geclipse.jsdl.ui.adapters.jsdl.ApplicationTypeAdapter#load()}.
   */
  @Test
  public void testLoad() {
    /*
     *  Cannot test the load() method since it has access to a HashMap
     *  containing SWT widgets. 
     */    
  }

}
