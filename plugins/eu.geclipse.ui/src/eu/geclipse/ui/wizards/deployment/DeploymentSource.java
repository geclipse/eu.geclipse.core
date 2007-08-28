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
 *    Yifan Zhou - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.wizards.deployment;

import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.dialogs.PreferencesUtil;

import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.ui.providers.DeploymentSourceTreeContentProvider;
import eu.geclipse.ui.providers.GridModelLabelProvider;

/**
 * @author Yifan Zhou
 */
public class DeploymentSource extends WizardPage {

  /**
   * Create a tree for listing referenced projects of the grid project.
   */
  private CheckboxTreeViewer sourceTree;
  
  /**
   * Create a property link for the current grid project. 
   */
  private Link linkForProperty;
  
  protected DeploymentSource( final String pageName ) {
    super( pageName );
    this.setDescription( Messages.getString( "Deployment.deployment_source_description" ) ); //$NON-NLS-1$
    this.setTitle( Messages.getString( "Deployment.deployment_source_title" ) ); //$NON-NLS-1$
  }

  public void createControl( final Composite parent ) {
    Composite composite = new Composite( parent, SWT.NONE );
    composite.setLayout( new GridLayout( 1, false ) );
    Label descriptionLabel = new Label ( composite, SWT.NONE );
    descriptionLabel.setText( Messages.getString( "Deployment.deployment_source_available_referenced_projects" ) ); //$NON-NLS-1$
    GridData gridData = new GridData();
    gridData.horizontalAlignment = GridData.BEGINNING;
    descriptionLabel.setLayoutData( gridData );
    this.sourceTree = new CheckboxTreeViewer( composite, SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER );
    gridData = new GridData( GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL  | GridData.GRAB_VERTICAL );
    this.sourceTree.getTree().setLayoutData( gridData );
    Composite bComposite = new Composite( composite, SWT.NONE );
    gridData = new GridData();
    gridData.horizontalAlignment = GridData.BEGINNING;
    bComposite.setLayoutData( gridData );
    RowLayout rowLayout = new RowLayout();
    rowLayout.spacing = 10;
    bComposite.setLayout( rowLayout );
    Button selectAll = new Button( bComposite, SWT.NONE );
    selectAll.setText( Messages.getString( "Deployment.deployment_source_select_all" ) ); //$NON-NLS-1$
    selectAll.setLayoutData( new RowData( 85, 22 ) );
    selectAll.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent event ) {
        for ( IGridElement referencedProject : getReferencedProjects() ) {
          getSourceTree().setSubtreeChecked( referencedProject, true );
        }
        updatePageComplete();
      }
    });
    Button deselectAll = new Button( bComposite, SWT.NONE );
    deselectAll.setText( Messages.getString( "Deployment.deployment_source_deselect_all" ) ); //$NON-NLS-1$
    deselectAll.setLayoutData( new RowData( 85, 22 ) );
    deselectAll.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent event ) {
        getSourceTree().setAllChecked( false );
        updatePageComplete();
      }
    });
    Label seperator = new Label( composite, SWT.SEPARATOR | SWT.HORIZONTAL );
    gridData = new GridData( GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL );
    seperator.setLayoutData( gridData );
    this.linkForProperty = new Link( composite, SWT.NONE );
    this.linkForProperty.setText( Messages.getString( "Deployment.deployment_source_link_for_preference" ) ); //$NON-NLS-1$
    gridData = new GridData( GridData.BEGINNING );
    this.linkForProperty.setLayoutData( gridData );
    this.linkForProperty.setEnabled( true );
    this.linkForProperty.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent event ) {
        PreferencesUtil.createPropertyDialogOn( getShell(), getGridProject()
                                                .getResource().getProject(), null, null, null ).open();
        ( ( DeploymentWizard ) getWizard() ).initProjects();
        initContents();
      }
    });
    this.setControl( composite );
  }

  @Override
  public void setVisible( final boolean visible ) {
    if( visible ) {
      this.initContents();
    }
    super.setVisible( visible );
  }
  
  protected void initContents() {
    this.sourceTree.setContentProvider( new DeploymentSourceTreeContentProvider() );
    this.sourceTree.setLabelProvider( new GridModelLabelProvider() );
    this.sourceTree.setInput( this.getWizard() );
    this.sourceTree.setSorter( new ViewerSorter() {
      @Override
      public int compare( final Viewer viewer, final Object e1, final Object e2 ) {
        return ( ( IGridElement ) e1 ).getName().compareToIgnoreCase( ( ( IGridElement ) e2 ).getName() );
      }
     });
    this.sourceTree.addCheckStateListener( new ICheckStateListener() {
      public void checkStateChanged( final CheckStateChangedEvent event ) {
        if ( getSourceTree().getChecked( event.getElement() ) ) {
          getSourceTree().setSubtreeChecked( event.getElement(), true );
        } else {
          getSourceTree().setSubtreeChecked( event.getElement(), false );
          recursionUnchecked( getSourceTree(), event.getElement() );
        }
        updatePageComplete();
      }
    });
    this.updatePageComplete();
    this.setMessage( null );
    this.setErrorMessage( null );
  }
  
  protected void updatePageComplete() {
    this.setPageComplete( false );
    if ( this.sourceTree.getCheckedElements().length == 0 ) {
      this.setMessage( null );
      this.setErrorMessage( Messages.getString( "Deployment.deployment_source_is_empty" ) ); //$NON-NLS-1$
      return;
    }
    this.setPageComplete( this.canFlipToNextPage() );
    this.setMessage( null );
    this.setErrorMessage( null );
  }
  
  @Override
  public boolean canFlipToNextPage() {
    boolean next = false;
    if ( this.sourceTree.getCheckedElements().length > 0 ) {
      next = true;
    }
    return next; 
  }
  
  @Override
  public boolean isPageComplete() {
    return false;
  }

  protected void recursionUnchecked( final CheckboxTreeViewer treeView, final Object element ) {
    IGridContainer parentElement = ( ( IGridElement ) element ).getParent();
    if( !parentElement.getName().equals( "" ) ) { //$NON-NLS-1$
      treeView.setChecked( parentElement, false );
      recursionUnchecked( treeView, parentElement );
    }
  }

  public CheckboxTreeViewer getSourceTree() {
    return this.sourceTree;
  }
  
  protected IGridElement[] getReferencedProjects() {
    return ( ( DeploymentWizard ) this.getWizard() ).getReferencedProjects();
  }
  
  protected IGridElement getGridProject() {
    return ( ( DeploymentWizard ) this.getWizard() ).getGridProject();
  }

}