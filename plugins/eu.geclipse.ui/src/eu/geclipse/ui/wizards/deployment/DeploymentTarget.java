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
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.ui.providers.DeploymentTargetTreeContentProvider;
import eu.geclipse.ui.providers.GridModelLabelProvider;

/**
 * @author Yifan Zhou
 *
 */
public class DeploymentTarget extends WizardPage {

  /**
   * Create a tree for listing computing element and filesystems in the grid
   * project.
   */
  private CheckboxTreeViewer targetTree;
  /**
   * The current grid project.
   */
  private IGridElement gridProject;

  /**
   * Create a target wizard page for deployment.
   * 
   * @param pageName The name of the page.
   */
  protected DeploymentTarget( final String pageName ) {
    super( pageName );
    this.setDescription( Messages.getString( "DeploymentSource.deployment_target_description" ) ); //$NON-NLS-1$
    this.setTitle( Messages.getString( "DeploymentSource.deployment_target_title" ) ); //$NON-NLS-1$
  }

  /**
   * Create the page controls and arrange them on a page.
   * 
   * @param parent The current page.
   */
  public void createControl( final Composite parent ) {
    Composite composite = new Composite( parent, SWT.NONE );
    composite.setLayout( new FillLayout() );
    Group group = new Group( composite, SWT.BORDER );
    group.setLayout( new GridLayout( 1, false ) );
    group.setText( Messages.getString( "DeploymentSource.deployment_target_selection_list" ) ); //$NON-NLS-1$
    this.targetTree = new CheckboxTreeViewer( group, SWT.V_SCROLL
                                                | SWT.H_SCROLL
                                                | SWT.BORDER );
    GridData gridData = new GridData( GridData.FILL,
                                      GridData.FILL,
                                      true,
                                      true,
                                      1,
                                      1 );
    this.targetTree.getTree().setLayoutData( gridData );
    this.initContents();
    this.setControl( composite );
  }

  /**
   * Page initialization.
   * 
   * @param gProject The grid project.
   */
  public void init( final IGridElement gProject ) {
    this.gridProject = gProject;
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
      this.targetTree.getTree().layout();
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
    if( this.targetTree.getCheckedElements().length > 0 ) {
      this.setErrorMessage( null );
      next =  true;
    } else {
      this.setErrorMessage( Messages.getString( "DeploymentSource.deployment_target_is_empty" ) ); //$NON-NLS-1$
    }
    return next;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
   */
  @Override
  public boolean isPageComplete()
  {
    return false;
  }

  /**
   * Content initialization.
   */
  private void initContents() {
    this.targetTree.setContentProvider( new DeploymentTargetTreeContentProvider( this.gridProject ) );
    this.targetTree.setLabelProvider( new GridModelLabelProvider() );
    this.targetTree.setInput( "Target" ); //$NON-NLS-1$
    this.targetTree.setSorter( new ViewerSorter() {

      @Override
      public int compare( final Viewer viewer, final Object e1, final Object e2 )
      {
        return ( ( IGridElement )e1 ).getName()
          .compareToIgnoreCase( ( ( IGridElement )e2 ).getName() );
      }
    } );
    Object[] objects = ( ( DeploymentTargetTreeContentProvider )this.targetTree.getContentProvider() ).getElements( "Target" ); //$NON-NLS-1$
    for( Object object : objects ) {
      if( ( ( IGridElement )object ).getName()
        .equals( Messages.getString( "DeploymentWizard.deployment_target_computing" ) ) ) { //$NON-NLS-1$
        this.targetTree.setSubtreeChecked( ( object ), true );
      }
    }
    this.targetTree.addCheckStateListener( new ICheckStateListener() {

      public void checkStateChanged( final CheckStateChangedEvent event ) {
        if( DeploymentTarget.this.getTargetTree().getChecked( event.getElement() ) ) {
          DeploymentTarget.this.getTargetTree().setSubtreeChecked( event.getElement(), true );
        } else {
          DeploymentTarget.this.getTargetTree().setSubtreeChecked( event.getElement(), false );
          recursionUnchecked( DeploymentTarget.this.getTargetTree(), event.getElement() );
        }
        setPageComplete( canFlipToNextPage() );
      }
    } );
  }

  /**
   * Recursively backward unchecked the nodes.
   * 
   * @param treeView The target tree.
   * @param element The current element.
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
   * Get the target tree.
   * 
   * @return The target tree.
   */
  public CheckboxTreeViewer getTargetTree() {
    return this.targetTree;
  }
}