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
import org.eclipse.swt.widgets.Group;

import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.providers.DeploymentTargetTreeContentProvider;
import eu.geclipse.ui.providers.GridModelLabelProvider;

/**
 * @author Yifan Zhou
 *
 */
public class DeploymentTarget extends WizardPage {

  /**
   * Create a tree for listing computing element.
   */
  private CheckboxTreeViewer ceTree;
  
  /**
   * Create a tree for listing storage element.
   */  
  private CheckboxTreeViewer seTree;

  protected DeploymentTarget( final String pageName ) {
    super( pageName );
    this.setDescription( Messages.getString( "Deployment.deployment_target_description" ) ); //$NON-NLS-1$
    this.setTitle( Messages.getString( "Deployment.deployment_target_title" ) ); //$NON-NLS-1$
  }

  public void createControl( final Composite parent ) {
    Composite composite = new Composite( parent, SWT.NONE );
    composite.setLayout( new GridLayout( 1, false ) );
    Group ceGroup = new Group( composite, SWT.BORDER );
    GridData gridData = new GridData( GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.FILL_BOTH );
    ceGroup.setText( Messages.getString( "Deployment.deployment_target_ce_group_label" ) ); //$NON-NLS-1$
    ceGroup.setLayoutData( gridData );
    ceGroup.setLayout( new GridLayout( 2, false ) );
    this.ceTree = new CheckboxTreeViewer( ceGroup, SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER );
    this.ceTree.getTree().setLayoutData( new GridData( GridData.FILL_BOTH ) );
    Composite ceComposite = new Composite( ceGroup, SWT.NONE );
    ceComposite.setLayoutData( new GridData( GridData.END ) );
    RowLayout rowLayout = new RowLayout( SWT.VERTICAL );
    rowLayout.spacing = 5;
    ceComposite.setLayout( rowLayout );
    Button ceSelectAll = new Button( ceComposite, SWT.NONE );
    ceSelectAll.setText( Messages.getString( "Deployment.deployment_source_select_all" ) ); //$NON-NLS-1$
    ceSelectAll.setLayoutData( new RowData( 85, 22 ) );
    ceSelectAll.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent event ) {
        for ( Object ceElement 
            : ( ( DeploymentTargetTreeContentProvider ) getCETree()
                .getContentProvider() ).getElements( getCERootElement() ) ) {
          getCETree().setSubtreeChecked( ceElement, true );
        }
        updatePageComplete();
      }
    });
    Button ceDeselectAll = new Button( ceComposite, SWT.NONE );
    ceDeselectAll.setText( Messages.getString( "Deployment.deployment_source_deselect_all" ) ); //$NON-NLS-1$
    ceDeselectAll.setLayoutData( new RowData( 85, 22 ) );
    ceDeselectAll.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent event ) {
        getCETree().setAllChecked( false );
        updatePageComplete();
      }
    });
    Group seGroup = new Group( composite, SWT.BORDER );
    seGroup.setText( Messages.getString( "Deployment.deployment_target_se_group_label" ) ); //$NON-NLS-1$
    gridData = new GridData( GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.FILL_BOTH );
    seGroup.setLayoutData( gridData );
    seGroup.setLayout( new GridLayout( 2, false ) );
    this.seTree = new CheckboxTreeViewer( seGroup, SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER );
    this.seTree.getTree().setLayoutData( new GridData( GridData.FILL_BOTH ) );
    Composite seComposite = new Composite( seGroup, SWT.NONE );
    seComposite.setLayoutData( new GridData( GridData.END ) );
    seComposite.setLayout( rowLayout );
    Button seSelectAll = new Button( seComposite, SWT.NONE );
    seSelectAll.setText( Messages.getString( "Deployment.deployment_source_select_all" ) ); //$NON-NLS-1$
    seSelectAll.setLayoutData( new RowData( 85, 22 ) );
    seSelectAll.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent event ) {
        for ( Object ceElement 
            : ( ( DeploymentTargetTreeContentProvider ) getSETree()
                .getContentProvider() ).getElements( getSERootElement() ) ) {
          getSETree().setSubtreeChecked( ceElement, true );
        }
        updatePageComplete();
      }
    });
    Button seDeselectAll = new Button( seComposite, SWT.NONE );
    seDeselectAll.setText( Messages.getString( "Deployment.deployment_source_deselect_all" ) ); //$NON-NLS-1$
    seDeselectAll.setLayoutData( new RowData( 85, 22 ) );
    seDeselectAll.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent event ) {
        getSETree().setAllChecked( false );
        updatePageComplete();
      }
    });
    this.setControl( composite );
  }

  @Override
  public void setVisible( final boolean visible )
  {
    if( visible ) {
      this.initContents();
    }
    super.setVisible( visible );
  }
  
  private void initContents() {
    this.ceTree.setContentProvider( new DeploymentTargetTreeContentProvider() );
    this.ceTree.setLabelProvider( new GridModelLabelProvider() );
    this.ceTree.setInput( this.getCERootElement() );
    this.ceTree.setSorter( new ViewerSorter() {
      @Override
      public int compare( final Viewer viewer, final Object e1, final Object e2 ) {
        return ( ( IGridElement ) e1 ).getName().compareToIgnoreCase( ( ( IGridElement ) e2 ).getName() );
      }
    });
    this.ceTree.addCheckStateListener( new ICheckStateListener() {
      public void checkStateChanged( final CheckStateChangedEvent event ) {
        if ( getCETree().getChecked( event.getElement() ) ) {
          getCETree().setSubtreeChecked( event.getElement(), true );
        } else {
          getCETree().setSubtreeChecked( event.getElement(), false );
          ( ( DeploymentSource ) getPreviousPage() ).recursionUnchecked( getCETree(), event.getElement() );
        }
        updatePageComplete();
      }
    });
    this.seTree.setContentProvider( new DeploymentTargetTreeContentProvider() );
    this.seTree.setLabelProvider( new GridModelLabelProvider() );
    this.seTree.setInput( this.getSERootElement() );
    this.seTree.setSorter( new ViewerSorter() {
      @Override
      public int compare( final Viewer viewer, final Object e1, final Object e2 ) {
        return ( ( IGridElement ) e1 ).getName().compareToIgnoreCase( ( ( IGridElement ) e2 ).getName() );
      }
    });
    this.seTree.addCheckStateListener( new ICheckStateListener() {
      public void checkStateChanged( final CheckStateChangedEvent event ) {
        if ( getSETree().getChecked( event.getElement() ) ) {
          getSETree().setSubtreeChecked( event.getElement(), true );
        } else {
          getSETree().setSubtreeChecked( event.getElement(), false );
          ( ( DeploymentSource ) getPreviousPage() ).recursionUnchecked( getSETree(), event.getElement() );
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
    if ( this.ceTree.getCheckedElements().length == 0 && this.seTree.getCheckedElements().length == 0 ) {
      this.setMessage( null );
      this.setErrorMessage( Messages.getString( "Deployment.deployment_target_is_empty" ) ); //$NON-NLS-1$
      return;
    }
    this.setPageComplete( this.canFlipToNextPage() );
    this.setMessage( null );
    this.setErrorMessage( null );
  }

  @Override
  public boolean canFlipToNextPage()
  {
    boolean next = false;
    if ( this.ceTree.getCheckedElements().length > 0 || this.seTree.getCheckedElements().length > 0 ) {
      next =  true;
    } 
    return next;
  }

  @Override
  public boolean isPageComplete()
  {
    return false;
  }

  protected CheckboxTreeViewer getSETree() {
    return this.seTree;
  }
  
  protected CheckboxTreeViewer getCETree() {
    return this.ceTree;
  }
  
  protected IGridElement getCERootElement() {
    IGridElement rootElement = null;
    IGridElement gridProject = ( ( DeploymentWizard ) this.getWizard() ).getGridProject();
    try {
      IGridElement[] containers = ( ( IGridProject ) gridProject ).getChildren( null );
      if ( containers != null ) {
        for ( IGridElement container : containers ) {
          if ( container.isVirtual() ) {
            IGridElement[] voContainers = ( ( IGridContainer ) container ).getChildren( null );
            if ( voContainers != null ) {
              for ( IGridElement voContainer : voContainers ) {
                if ( voContainer.getName()
                    .equals( Messages.getString( "Deployment.deployment_target_computing" ) ) ) { //$NON-NLS-1$
                    rootElement = voContainer;
                }
              }
            }
          }
        }
      }
    } catch( GridModelException e ) {
      Activator.logException( e );
    }
    return rootElement;
  }
  
  protected IGridElement getSERootElement() {
    IGridElement rootElement = null;
    IGridElement gridProject = ( ( DeploymentWizard ) this.getWizard() ).getGridProject();
    try {
      IGridElement[] containers = ( ( IGridProject ) gridProject ).getChildren( null );
      if ( containers != null ) {
        for ( IGridElement container : containers ) {
          if ( container.getName()
              .equals( Messages.getString( "Deployment.deployment_target_filesystems" ) ) ) { //$NON-NLS-1$
            rootElement = container;
            }
          }
        }
    } catch( GridModelException e ) {
      Activator.logException( e );
    }
    return rootElement;
  }
  
}