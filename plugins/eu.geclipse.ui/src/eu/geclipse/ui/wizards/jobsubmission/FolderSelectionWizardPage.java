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

import org.eclipse.core.resources.IFolder;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.IWizardPage;
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
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridJobDescription;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.IGridRoot;
import eu.geclipse.core.model.ILocalFolder;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.model.impl.ResourceGridContainer;
import eu.geclipse.ui.internal.Activator;

public class FolderSelectionWizardPage extends WizardPage {

  private IGridProject project;
  private Tree tree;
  private Text jobNameText;
  private List<IGridJobDescription> jobDescriptions;

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
    final TreeViewer treeViewer = new TreeViewer( mainComp, SWT.SINGLE
                                                            | SWT.BORDER );
    treeViewer.setContentProvider( new CProvider() );
    treeViewer.setLabelProvider( new LProvider() );
    treeViewer.setInput( this.project );
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
        // getTree().getSelection();
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
    if( !this.jobNameText.getText().equals( "" )
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

  public ILocalFolder getDestinationFolder() {
    ILocalFolder result = null;
    for( TreeItem item : this.tree.getSelection() ) {
      if( item.getData() instanceof ILocalFolder ) {
        result = ( ILocalFolder )item.getData();
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
  class CProvider implements ITreeContentProvider {

    public Object[] getChildren( final Object parentElement ) {
      // Object[] result = new Object[0];
      ArrayList<IGridContainer> containers = new ArrayList<IGridContainer>();
      if( parentElement instanceof IGridContainer ) {
        try {
          for( IGridElement elem : ( ( IGridContainer )parentElement ).getChildren( null ) )
          {
            if( elem instanceof ILocalFolder
                && !( elem.isHidden() )
                && !( elem instanceof IVirtualOrganization ) )
            {
              containers.add( ( IGridContainer )elem );
            }
          }
        } catch( GridModelException e ) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
      return containers.toArray();
    }

    public Object getParent( final Object element ) {
      // TODO katis ???
      return new Object();
    }

    public boolean hasChildren( final Object element ) {
      boolean result = false;
      if( element instanceof IGridContainer ) {
        try {
          for( IGridElement elem : ( ( IGridContainer )element ).getChildren( null ) )
          {
            if( elem instanceof ILocalFolder
                && !( elem.isHidden() )
                && !( elem instanceof IVirtualOrganization ) )
            {
              result = true;
              break;
            }
          }
        } catch( GridModelException e ) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
      return result;
    }

    public Object[] getElements( final Object inputElement ) {
      ArrayList<IGridContainer> folders = new ArrayList<IGridContainer>();
      if( inputElement instanceof IGridContainer ) {
        // IGridContainer[] elements = null;
        IGridContainer project = ( IGridContainer )inputElement;
        try {
          for( IGridElement element : project.getChildren( null ) ) {
            if( element instanceof ILocalFolder
                && !( element.isHidden() )
                && !( element instanceof IVirtualOrganization ) )
            {
              folders.add( ( IGridContainer )element );
            }
          }
        } catch( GridModelException e ) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
      return folders.toArray();
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
      return ( ( IGridContainer )element ).getName();
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
