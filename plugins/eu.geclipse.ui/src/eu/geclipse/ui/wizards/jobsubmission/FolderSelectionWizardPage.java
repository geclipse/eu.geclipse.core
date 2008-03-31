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
 *           
 *****************************************************************************/
package eu.geclipse.ui.wizards.jobsubmission;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
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

import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridJobDescription;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.ILocalFolder;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.providers.GridFileDialogContentProvider;
import eu.geclipse.ui.providers.NewProgressTreeNode;

public class FolderSelectionWizardPage extends WizardPage {

  private IGridProject project;
  private Tree tree;
  private Text jobNameText;
  private List<IGridJobDescription> jobDescriptions;
  private TreeViewer treeViewer;

  public FolderSelectionWizardPage( final String pageName,
                                    final IGridProject project,
                                    final List<IGridJobDescription> jobDescriptions )
  {
    super( pageName );
    this.project = project;
    this.jobDescriptions = jobDescriptions;
  }

  public void createControl( final Composite parent ) {
    Composite mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayout( new GridLayout( 2, false ) );
    GridData gd = new GridData( GridData.FILL_BOTH );
    gd.grabExcessHorizontalSpace = true;
    gd.grabExcessVerticalSpace = true;
    gd.verticalSpan = 3;
    gd.horizontalSpan = 2;
    gd.heightHint = 300;
    gd.widthHint = 250;
    treeViewer = new TreeViewer( mainComp, SWT.SINGLE | SWT.BORDER );
    treeViewer.setContentProvider( new RProvider() );
    // treeViewer.setContentProvider( new GridFileDialogContentProvider() );
    treeViewer.setLabelProvider( new LProvider() );
    treeViewer.setInput( this.project.getResource() );
    this.setTree( treeViewer.getTree() );
    this.getTree().setLayoutData( gd );
    this.tree.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent e ) {
        FolderSelectionWizardPage.this.updateButtons();
      }
    } );
    for( TreeItem item : this.tree.getItems() ) {
      if( item.getData() instanceof ILocalFolder ) {
        this.tree.setSelection( item );
        break;
      }
    }
    // name control
    Label jobNameLabel = new Label( mainComp, SWT.LEAD );
    jobNameLabel.setText( "Job name" );
    gd = new GridData();
    jobNameLabel.setLayoutData( gd );
    this.jobNameText = new Text( mainComp, SWT.LEAD | SWT.BORDER );
    this.jobNameText.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent e ) {
        FolderSelectionWizardPage.this.updateButtons();
      }
    } );
    gd = new GridData( GridData.GRAB_HORIZONTAL | GridData.FILL_HORIZONTAL );
    this.jobNameText.setLayoutData( gd );
    setControl( mainComp );
  }

  protected void updateButtons() {
    this.getContainer().updateButtons();
  }

  @Override
  public boolean canFlipToNextPage() {
    boolean result = false;
    if( !this.jobNameText.getText().equals( "" ) //$NON-NLS-1$
        && ( this.getDestinationFolder() != null ) )
    {
      result = true;
    }
    if( result ) {
      if( getWizard() instanceof JobCreatorSelectionWizard ) {
        ( ( JobCreatorSelectionWizard )getWizard() ).changeInitData();
      }
    }
    return result;
  }

  private void setTree( final Tree tree ) {
    this.tree = tree;
  }

  public IResource getDestinationFolder() {
    IResource result = null;
    for( TreeItem item : this.tree.getSelection() ) {
      if( item.getData() instanceof IContainer ) {
        result = ( IContainer )item.getData();
      }
    }
    return result;
  }

  public List<String> getJobNames() {
    List<String> result = new ArrayList<String>();
    String baseName = this.jobNameText.getText();
    int i = 1;
    String sufix = "";
    for( IGridJobDescription jobDescr : this.jobDescriptions ) {
      result.add( baseName + sufix );
      sufix = "_" + Integer.valueOf( i ).toString();
      i++;
    }
    return result;
  }

  private Tree getTree() {
    return tree;
  }
  class RProvider implements ITreeContentProvider {

    public Object[] getChildren( final Object parentElement ) {
      List<IResource> list = new ArrayList<IResource>();
      if( parentElement instanceof IContainer ) {
        try {
          if( ( ( IContainer )parentElement ).members().length != 0 ) {
            for( IResource member : ( ( IContainer )parentElement ).members() )
            {
              if( member instanceof IContainer
                  && !( member.getName().startsWith( "." ) ) ) //$NON-NLS-1$
              {
                list.add( member );
              }
            }
          }
        } catch( CoreException e ) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
      return list.toArray();
    }

    public Object getParent( final Object element ) {
      // TODO Auto-generated method stub
      return null;
    }

    public boolean hasChildren( final Object element ) {
      boolean result = false;
      if( element instanceof IContainer ) {
        try {
          for( IResource member : ( ( IContainer )element ).members() ) {
            if( member instanceof IContainer
                && !( member.getName().startsWith( "." ) ) ) //$NON-NLS-1$
            {
              result = true;
              break;
            }
          }
        } catch( CoreException e ) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
      return result;
    }

    public Object[] getElements( final Object inputElement ) {
      List<IResource> list = new ArrayList<IResource>();
      if( inputElement instanceof IContainer ) {
        try {
          if( ( ( IContainer )inputElement ).members().length != 0 ) {
            for( IResource member : ( ( IContainer )inputElement ).members() ) {
              if( member instanceof IContainer
                  && !( member.getName().startsWith( "." ) ) )
              {
                list.add( member );
              }
            }
          }
        } catch( CoreException e ) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
      return list.toArray();
    }

    public void dispose() {
      // TODO Auto-generated method stub
    }

    public void inputChanged( final Viewer viewer,
                              final Object oldInput,
                              final Object newInput )
    {
      // empty implementation
    }
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
      return ( ( IResource )element ).getName();
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
}
