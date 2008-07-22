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
 *      - Katarzyna Bylec (katis@man.poznan.pl)
 *      - Szymon Mueller    
 *****************************************************************************/
package eu.geclipse.servicejob.ui.wizard;

import java.net.URL;
import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.IGridRoot;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.servicejob.ui.Activator;
import eu.geclipse.ui.dialogs.ProblemDialog;

/**
 * Wizard page for selecting project in which should test be created.
 */
public class ProjectSelectionPage extends WizardPage {

  static IGridProject selectedProject;
  String testName;
  Text nameText;
  private Tree tree;
  private IGridProject initialProject;

  /**
   * Constructor of project selection page.
   * 
   * @param pageName Name of this page.
   */
  public ProjectSelectionPage( final String pageName ) {
    super( pageName );
    this.setTitle( "Project selection page" );
    this.setDescription( "Choose project that contains services you want to perform operator's job on." );
  }

  public ProjectSelectionPage( final String pageName,
                               final IGridProject selectedProject )
  {
    this( pageName );
    this.initialProject = selectedProject;
  }

  protected void updateButtons() {
    this.getContainer().updateButtons();
  }

  @Override
  public boolean canFlipToNextPage() {
    boolean flag = false;
    setErrorMessage( null );
    if( this.selectedProject != null
        && this.testName != null
        && !this.testName.equals( "" ) )
    {
      flag = true;
      IPath projectPath = this.selectedProject.getPath();
      IPath testsFolderPath = projectPath.append( "/" + ServiceJobWizard.TEST_FOLDER + "/" ); //$NON-NLS-1$ //$NON-NLS-2$
      IWorkspaceRoot workspaceRoot = ( IWorkspaceRoot )GridModel.getRoot()
        .getResource();
      IFolder testsFolder = workspaceRoot.getFolder( testsFolderPath );
      if( testsFolder.exists() ) {
        IFile testFile = testsFolder.getFile( this.testName
                                              + ServiceJobWizard.TEST_EXTENSION );
        if( testFile.exists() ) {
          setErrorMessage( "Operator's Job with this name already exists." );
          flag = false;
        }
      }
    }
    return flag && getNextPage() != null;
  }

  public void createControl( final Composite parent ) {
    Composite mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayout( new GridLayout( 1, false ) );
    GridData gd = new GridData( GridData.FILL_BOTH );
    gd.grabExcessHorizontalSpace = true;
    gd.grabExcessVerticalSpace = true;
    gd.verticalSpan = 3;
    gd.horizontalSpan = 2;
    gd.heightHint = 300;
    gd.widthHint = 250;
    final TreeViewer treeViewer = new TreeViewer( mainComp, SWT.SINGLE
                                                            | SWT.BORDER );
    treeViewer.setContentProvider( new CProvider() );
    treeViewer.setLabelProvider( new LProvider() );
    treeViewer.setInput( GridModel.getRoot() );
    this.setTree( treeViewer.getTree() );
    this.getTree().setLayoutData( gd );
    this.getTree().addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent e ) {
        getTree().getSelection();
        Object selectedData = ( ( ( Tree )e.getSource() ).getSelection()[ 0 ] ).getData();
        if( selectedData instanceof IGridProject ) {
          // ((TestWizard)getWizard()).setSelectedProject( ( IGridProject
          // )selectedData );
          ProjectSelectionPage.selectedProject = ( IGridProject )selectedData;
          ProjectSelectionPage.this.updateButtons();
        }
      }
    } );
    for( TreeItem item : this.tree.getItems() ) {
      if( item.getData() instanceof IGridProject ) {
        if( item.getData().equals( this.initialProject ) ) {
          this.tree.setSelection( item );
          break;
        }
      }
    }
    Composite textComp = new Composite( mainComp, SWT.NONE );
    textComp.setLayout( new GridLayout( 2, false ) );
    gd = new GridData( GridData.FILL_BOTH );
    gd.grabExcessHorizontalSpace = true;
    textComp.setLayoutData( gd );
    Label nameLabel = new Label( textComp, SWT.LEAD );
    nameLabel.setText( "Operator's Job name" );
    gd = new GridData();
    nameLabel.setLayoutData( gd );
    this.nameText = new Text( textComp, SWT.BORDER | SWT.LEAD );
    gd = new GridData( GridData.GRAB_HORIZONTAL | GridData.FILL_BOTH );
    gd.grabExcessHorizontalSpace = true;
    this.nameText.setLayoutData( gd );
    this.nameText.addModifyListener( new ModifyListener() {

      public void modifyText( final ModifyEvent e ) {
        ProjectSelectionPage.this.testName = ProjectSelectionPage.this.nameText.getText();
        ProjectSelectionPage.this.updateButtons();
      }
    } );
    setControl( mainComp );
  }

  @Override
  public IWizardPage getNextPage() {
    return super.getNextPage();
  }

  public IGridProject getProject() {
    return this.selectedProject;
  }

  private void setTree( final Tree tree ) {
    this.tree = tree;
  }

  private Tree getTree() {
    return tree;
  }
  class LProvider implements ILabelProvider {

    private Image projectImage;

    /**
     * Constructor of label provider for project selection page
     */
    public LProvider() {
      super();
      URL argsURL = Activator.getDefault()
        .getBundle()
        .getEntry( "icons/gridprojects.gif" ); //$NON-NLS-1$
      ImageDescriptor argsDesc = ImageDescriptor.createFromURL( argsURL );
      this.projectImage = argsDesc.createImage();
    }

    public Image getImage( final Object element ) {
      return this.projectImage;
    }

    public String getText( final Object element ) {
      return ( ( IGridProject )element ).getName();
    }

    public void addListener( final ILabelProviderListener listener ) {
      // empty implementation
    }

    public void dispose() {
      this.projectImage.dispose();
    }

    public boolean isLabelProperty( final Object element, final String property )
    {
      return false;
    }

    public void removeListener( final ILabelProviderListener listener ) {
      // empty implementation
    }
  }
  class CProvider implements ITreeContentProvider {

    public Object[] getChildren( final Object parentElement ) {
      return new Object[ 0 ];
    }

    public Object getParent( final Object element ) {
      return new Object();
    }

    public boolean hasChildren( final Object element ) {
      return false;
    }

    public Object[] getElements( final Object inputElement ) {
      ArrayList<IGridProject> projects = new ArrayList<IGridProject>();
      IGridElement[] elements = null;
      try {
        elements = ( ( IGridRoot )inputElement ).getChildren( null );
      } catch( ProblemException e ) {
        ProblemDialog.openProblem( PlatformUI.getWorkbench()
                                     .getActiveWorkbenchWindow()
                                     .getShell(),
                                   "Error when fetching children",
                                   "Error when fetching children of "
                                       + inputElement.toString(),
                                   e );
      }
      for( IGridElement elem : elements ) {
        if( elem instanceof IGridProject
            && !( ( IGridProject )elem ).isHidden() )
        {
          projects.add( ( IGridProject )elem );
        }
      }
      return projects.toArray();
    }

    public void dispose() {
      // empty implementation
    }

    public void inputChanged( final Viewer viewer,
                              final Object oldInput,
                              final Object newInput )
    {
      // empty implementation
    }
  }

  /**
   * Getter method to access name of service job.
   * 
   * @return String name of service job that will be used in all views as well
   *         as a name of GTDL file.
   */
  public String getJobName() {
    return this.testName;
  }
}
