/*******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Initial development of
 * the original code was made for the g-Eclipse project founded by European
 * Union project number: FP6-IST-034327 http://www.geclipse.eu/ Contributors:
 * Yifan Zhou - initial API and implementation
 ******************************************************************************/
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
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.ui.providers.DeploymentSourceTreeContentProvider;
import eu.geclipse.ui.providers.GridModelLabelProvider;

/**
 * @author Yifan Zhou
 *
 */
public class DeploymentSource extends WizardPage {

  /**
   * Create a tree for listing referenced projects of the grid project.
   */
  private CheckboxTreeViewer sourceTree;
  /**
   * All referenced projects.
   */
  private IGridElement[] referencedProjects;

  /**
   * Create a source wizard page for deployment.
   * 
   * @param pageName The name of the page.
   */
  protected DeploymentSource( final String pageName ) {
    super( pageName );
    this.setDescription( Messages.getString( "DeploymentSource.deployment_source_description" ) ); //$NON-NLS-1$
    this.setTitle( Messages.getString( "DeploymentSource.deployment_source_title" ) ); //$NON-NLS-1$
  }

  /**
   * Create the page controls and arrange them on a page
   * 
   * @param parent The current page
   */
  public void createControl( final Composite parent ) {
    Composite composite = new Composite( parent, SWT.NONE );
    composite.setLayout( new FillLayout() );
    Group group = new Group( composite, SWT.BORDER );
    group.setText( Messages.getString( "DeploymentSource.deployment_source_selection_list" ) ); //$NON-NLS-1$
    group.setLayout( new GridLayout( 2, false ) );
    GridData gridData = new GridData( GridData.FILL,
                                      GridData.FILL,
                                      true,
                                      true,
                                      1,
                                      1 );
    this.sourceTree = new CheckboxTreeViewer( group, SWT.V_SCROLL
                                                | SWT.H_SCROLL
                                                | SWT.BORDER );
    this.sourceTree.getTree().setLayoutData( gridData );
    Composite buttonsComposite = new Composite( group, SWT.NONE );
    buttonsComposite.setLayoutData( new GridData( GridData.END ) );
    RowLayout rowLayout = new RowLayout( SWT.VERTICAL );
    rowLayout.spacing = 5;
    buttonsComposite.setLayout( rowLayout );
    Button selectAll = new Button( buttonsComposite, SWT.NONE );
    selectAll.setText( Messages.getString( "DeploymentSource.deployment_source_select_all" ) ); //$NON-NLS-1$
    selectAll.setLayoutData( new RowData( 130, 25 ) );
    selectAll.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent event ) {
        for( IGridElement referencedProject : DeploymentSource.this.getReferencedProjects() ) {
          DeploymentSource.this.getSourceTree().setSubtreeChecked( referencedProject, true );
        }
        setPageComplete( canFlipToNextPage() );
      }
    } );
    Button deselectAll = new Button( buttonsComposite, SWT.NONE );
    deselectAll.setText( Messages.getString( "DeploymentSource.deployment_source_deselect_all" ) ); //$NON-NLS-1$
    deselectAll.setLayoutData( new RowData( 130, 25 ) );
    deselectAll.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent event ) {
        DeploymentSource.this.getSourceTree().setAllChecked( false );
        setPageComplete( canFlipToNextPage() );
      }
    } );
    this.initContents();
    this.setControl( composite );
    if( this.referencedProjects.length == 0 ) {
      this.setErrorMessage( Messages.getString( "DeploymentSource.deployment_source_no_referenced_project" ) ); //$NON-NLS-1$
    }
  }

  /**
   * Page initialization.
   * 
   * @param refProjects All referenced projects
   */
  public void init( final IGridElement[] refProjects ) {
    this.referencedProjects = refProjects;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.dialogs#setVisible( boolean visible )
   */
  @Override
  public void setVisible( final boolean visible )
  {
    if( visible ) {
      this.sourceTree.getTree().layout();
    }
    super.setVisible( visible );
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.wizard.WizardPage#canFlipToNextPage()
   */
  @Override
  public boolean canFlipToNextPage()
  {
    boolean next = false;
    if( this.sourceTree.getCheckedElements().length > 0 ) {
      this.setErrorMessage( null );
      next = true;
    } else {
      this.setErrorMessage( Messages.getString( "DeploymentSource.deployment_source_is_empty" ) ); //$NON-NLS-1$
    }
    return next; 
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
   */
  @Override
  public boolean isPageComplete() {
    return false;
  }

  /**
   * Content initialization.
   */
  private void initContents() {
    this.sourceTree.setContentProvider( new DeploymentSourceTreeContentProvider( this.referencedProjects ) );
    this.sourceTree.setLabelProvider( new GridModelLabelProvider() );
    this.sourceTree.setInput( "Source" ); //$NON-NLS-1$
    this.sourceTree.setSorter( new ViewerSorter() {

      @Override
      public int compare( final Viewer viewer, final Object e1, final Object e2 )
      {
        return ( ( IGridElement )e1 ).getName()
          .compareToIgnoreCase( ( ( IGridElement )e2 ).getName() );
      }
    } );
    this.sourceTree.addCheckStateListener( new ICheckStateListener() {

      public void checkStateChanged( final CheckStateChangedEvent event ) {
        if( DeploymentSource.this.getSourceTree().getChecked( event.getElement() ) ) {
          DeploymentSource.this.getSourceTree().setSubtreeChecked( event.getElement(), true );
        } else {
          DeploymentSource.this.getSourceTree().setSubtreeChecked( event.getElement(), false );
          recursionUnchecked( DeploymentSource.this.getSourceTree(), event.getElement() );
        }
        setPageComplete( canFlipToNextPage() );
      }
    } );
  }

  /**
   * Recursively backward unchecked the nodes.
   * 
   * @param treeView The soure tree
   * @param element The current element
   */
  void recursionUnchecked( final CheckboxTreeViewer treeView,
                                   final Object element )
  {
    IGridContainer parentElement = ( ( IGridElement )element ).getParent();
    if( !parentElement.getName().equals( "" ) ) { //$NON-NLS-1$
      treeView.setChecked( parentElement, false );
      recursionUnchecked( treeView, parentElement );
    }
  }

  /**
   * @return The source tree
   */
  public CheckboxTreeViewer getSourceTree() {
    return this.sourceTree;
  }

  /**
   * @return all referenced projects
   */
  public IGridElement[] getReferencedProjects() {
    return this.referencedProjects;
  }
}