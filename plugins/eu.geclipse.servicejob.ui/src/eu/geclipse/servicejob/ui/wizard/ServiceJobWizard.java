/******************************************************************************
 * Copyright (c) 2007, 2008 g-Eclipse consortium 
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
 *           
 *****************************************************************************/
package eu.geclipse.servicejob.ui.wizard;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.xml.sax.SAXException;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.IGridResource;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.servicejob.parsers.GTDLWriter;
import eu.geclipse.servicejob.ui.Activator;
import eu.geclipse.servicejob.ui.interfaces.IServiceJobWizardNode;
import eu.geclipse.servicejob.ui.internal.WizardInitObject;
import eu.geclipse.ui.wizards.IProjectSelectionProvider;
import eu.geclipse.ui.wizards.wizardselection.ExtPointWizardSelectionListPage;
import eu.geclipse.ui.wizards.wizardselection.IWizardSelectionNode;
import eu.geclipse.ui.wizards.wizardselection.WizardSelectionListPage;

/**
 * Wizard for creating grid test for grid resources. First page of it is wizard
 * selection page and its content is created from tests providers. Wizard nodes
 * for this page are defined through eu.geclipse.servicejob.servicejobProvider
 * extension point.
 * 
 * @author Katarzyna Bylec
 */
public class ServiceJobWizard extends Wizard
  implements INewWizard, IProjectSelectionProvider
{

  static final String TEST_FOLDER = ".tests"; //$NON-NLS-1$
  static final String TEST_EXTENSION = ".gtdl"; //$NON-NLS-1$
  private IGridProject project;
  private ProjectSelectionPage projectPage;
  private List<IGridResource> testedResources;
  private WizardSelectionListPage testTypeSelectionPage;

  /**
   * Constructor for wizard.
   * 
   * @param project if wizard was run through resource's context menu this
   *            object represents grid project to which selected resource
   *            belongs.
   * @param resource grid resource for which new test will be created
   */
  public ServiceJobWizard( final IGridProject project,
                           final List<IGridResource> resource )
  {
    setForcePreviousAndNextButtons( true );
    this.project = project;
    this.testedResources = resource;
  }

  /**
   * Constructor for wizard. Does the same as
   * <code>TestWizard((IGridProject) null,
   * (IGridResource) null)</code>.
   */
  public ServiceJobWizard() {
    this( null, null );
  }

  @Override
  public String getWindowTitle() {
    return "New Operator's Job";
  }

  @Override
  public void addPages() {
    this.projectPage = new ProjectSelectionPage( "Project selection page",
                                                 this.project );
    addPage( this.projectPage );
    this.testTypeSelectionPage = new ExtPointWizardSelectionListPage( "Operator's jobs' type selection page",
                                                                      "eu.geclipse.servicejob.servicejobProvider",
                                                                      "Operator's job type selection page",
                                                                      "Select type of operator's job you want to perform",
                                                                      "No jobs to choose from",
                                                                      false );
    addPage( this.testTypeSelectionPage );
  }

  @Override
  public IWizardPage getNextPage( final IWizardPage page ) {
    this.testTypeSelectionPage.setInitData( new WizardInitObject( this.projectPage.getJobName(),
                                                                  this,
                                                                  this.testedResources ) );
//    this.testTypeSelectionPage.forceUpdate( );
    return super.getNextPage( page );
  }

  @Override
  public boolean performFinish() {
    boolean result = true;
    IWizard selectedNode = this.testTypeSelectionPage.getSelectedNode()
      .getWizard();
    if( selectedNode instanceof IServiceJobWizardNode ) {
      IServiceJobWizardNode testNode = ( IServiceJobWizardNode )selectedNode;
      IGridProject destinationProject = testNode.getSelectedProject();
      String testName = testNode.getTestName();
      InputStream testInputData = testNode.getTestInputData();
      String testPluginId = testNode.getPluginID();
      List<String> testedResources1 = testNode.getResourcesToTestNames();
      createTest( testName,
                  destinationProject,
                  testInputData,
                  testPluginId,
                  testedResources1 );
    }
    if( result ) {
      try {
        PlatformUI.getWorkbench()
          .getActiveWorkbenchWindow()
          .getActivePage()
          .showView( "eu.geclipse.servicejob.views.testsView" ); //$NON-NLS-1$
      } catch( PartInitException partExc ) {
        Activator.logException( partExc );
      }
    }
    return result;
  }

  private void createTest( final String testName,
                           final IGridProject gProject,
                           final InputStream inputStream,
                           final String plugInID,
                           final List<String> testedResources1 )
  {
    IPath projectPath = gProject.getPath();
    IPath testsFolderPath = projectPath.append( "/" + TEST_FOLDER + "/" ); //$NON-NLS-1$ //$NON-NLS-2$
    IWorkspaceRoot workspaceRoot = ( IWorkspaceRoot )GridModel.getRoot()
      .getResource();
    try {
      IFolder testsFolder = workspaceRoot.getFolder( testsFolderPath );
      if( !testsFolder.exists() ) {
        testsFolder.create( true, true, null );
      }
      IFile testFile = testsFolder.getFile( testName + TEST_EXTENSION );
      if( !testFile.exists() ) {
        testFile.create( GTDLWriter.getInitialInputStream( plugInID,
                                                           testedResources1,
                                                           inputStream ),
                         true,
                         new NullProgressMonitor() );
      }
    } catch( CoreException coreExc ) {
      Activator.logException( coreExc );
    }
    // return test;
    catch( IOException e ) {
      // TODO Auto-generated catch block
      Activator.logException( e );
    } catch( ParserConfigurationException e ) {
      // TODO Auto-generated catch block
      Activator.logException( e );
    } catch( SAXException e ) {
      // TODO Auto-generated catch block
      Activator.logException( e );
    } catch( TransformerException e ) {
      // TODO Auto-generated catch block
      Activator.logException( e );
    }
  }

  public void init( final IWorkbench workbench,
                    final IStructuredSelection selection )
  {
    // do nothing
  }

  public List<IGridResource> getInitResources() {
    return this.testedResources;
  }

  

  public IGridProject getGridProject() {
    IGridProject result = null;
    if (this.projectPage != null){
      result = this.projectPage.getProject();
    }
    return result;
  }
  
 
  
}