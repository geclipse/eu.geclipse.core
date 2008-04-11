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
 *    Jie Tao - extension
 *****************************************************************************/

package eu.geclipse.ui.wizards.deployment;

import java.io.File;
import java.net.URI;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.dialogs.PreferencesUtil;

import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.ui.providers.DeploymentSourceTreeContentProvider;
import eu.geclipse.ui.providers.GridModelLabelProvider;
import eu.geclipse.ui.widgets.StoredCombo;
import eu.geclipse.ui.dialogs.GridFileDialog;

/**
 * @author Yifan Zhou
 */
public class DeploymentSource extends WizardPage implements SelectionListener {

  /**
   * Create a tree for listing referenced projects of the grid project.
   */
  
  private static Composite composite;
  //private static String TAR_SUFFIX = "tar"; //$NON-NLS-1$
  private CheckboxTreeViewer sourceTree;
  private Label descriptionLabel;
  private StoredCombo tarCombo;
  private Button tarButton;
  private Button treebutton;
  private IGridElement[] source;
  private URI [] disksource;
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
    //IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
    composite = new Composite( parent, SWT.NONE );
    composite.setLayout( new GridLayout( 1, false ) );
    
    this.treebutton = new Button( composite, SWT.RADIO);
    this.treebutton.setText( "Choose from the referenced project" ); //$NON-NLS-1$
   // this.tarButton.setImage( fileImage );
    GridData gridData = new GridData();
    this.treebutton.setLayoutData( gridData );
    
    this.descriptionLabel = new Label ( composite, SWT.NONE );
    this.descriptionLabel.setText( Messages.getString( "Deployment.deployment_source_available_referenced_projects" ) ); //$NON-NLS-1$
    this.descriptionLabel.setVisible( false );
    gridData = new GridData();
    gridData.horizontalAlignment = GridData.BEGINNING;
    this.descriptionLabel.setLayoutData( gridData );
    this.linkForProperty = new Link( composite, SWT.NONE );
    this.linkForProperty.setText( Messages.getString( "Deployment.deployment_source_link_for_preference" ) ); //$NON-NLS-1$
    gridData = new GridData( GridData.BEGINNING );
    this.linkForProperty.setLayoutData( gridData );
    this.linkForProperty.setVisible( false ); 
    this.sourceTree = new CheckboxTreeViewer( composite, SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER );
    gridData = new GridData( GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL  | GridData.GRAB_VERTICAL );
    this.sourceTree.getTree().setLayoutData( gridData );
    /*Composite bComposite = new Composite( composite, SWT.NONE );
    ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
    gridData = new GridData();
    Image fileImage = sharedImages.getImage( ISharedImages.IMG_OBJ_FILE );
    gridData.horizontalAlignment = GridData.BEGINNING;
    bComposite.setLayoutData( gridData );
    RowLayout rowLayout = new RowLayout();
    rowLayout.spacing = 10;
    bComposite.setLayout( rowLayout );*/
    //Button selectAll = new Button( bComposite, SWT.NONE );
    //selectAll.setText( Messages.getString( "Deployment.deployment_source_select_all" ) ); //$NON-NLS-1$
    //selectAll.setLayoutData( new RowData( 85, 22 ) );
    //selectAll.addSelectionListener( new SelectionAdapter() {
     // @Override
     // public void widgetSelected( final SelectionEvent event ) {
      //  for ( IGridElement referencedProject : getReferencedProjects() ) {
      //    getSourceTree().setSubtreeChecked( referencedProject, true );
      //  }
      //  updatePageComplete();
     // }
    //});
    //Button deselectAll = new Button( bComposite, SWT.NONE );
    //deselectAll.setText( Messages.getString( "Deployment.deployment_source_deselect_all" ) ); //$NON-NLS-1$
    //deselectAll.setLayoutData( new RowData( 85, 22 ) );
    //deselectAll.addSelectionListener( new SelectionAdapter() {
      //@Override
      //public void widgetSelected( final SelectionEvent event ) {
       // getSourceTree().setAllChecked( false );
       // updatePageComplete();
     // }
   // });
    Label seperator = new Label( composite, SWT.SEPARATOR | SWT.HORIZONTAL );
    gridData = new GridData( GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL );
    seperator.setLayoutData( gridData );
    
    this.tarButton = new Button( composite, SWT.RADIO);
    this.tarButton.setText( Messages.getString("Deployment.deployment_source_tar_select") ); //$NON-NLS-1$
 
    gridData = new GridData();
    this.tarButton.setLayoutData( gridData );
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
    this.treebutton.addSelectionListener( this);
    
    this.tarButton.addSelectionListener(this);
    this.linkForProperty.addSelectionListener(this);
//    this.updatePageComplete();
  //  this.setMessage( null );
  //  this.setErrorMessage( null );
  }
  
  public void widgetDefaultSelected( final SelectionEvent e ) {
    // empty implementation
  }

  public void widgetSelected( final SelectionEvent e ) {
    if( e.getSource().equals( this.treebutton ) && this.treebutton.getSelection()) {
      this.linkForProperty.setEnabled( true );
      this.descriptionLabel.setVisible( true );
      this.linkForProperty.setVisible( true );
      
      this.sourceTree.setContentProvider( new DeploymentSourceTreeContentProvider() );
      this.sourceTree.setLabelProvider( new GridModelLabelProvider() );
      this.sourceTree.setInput( getsourceWizard() );
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
          IGridElement [] stores = null;
          int i = 0;
          if (getSourceTree().getCheckedElements().length != 0) {
            stores = new IGridElement [getSourceTree().getCheckedElements().length];
            for (Object element: getSourceTree().getCheckedElements()) {
              stores [i++] = (IGridElement) element;
            }           
          }        
          updatePageComplete(stores);
        }
      });
      this.sourceTree.getCheckedElements();
      this.sourceTree.expandAll();
    }
 
    if( e.getSource().equals( this.tarButton ) && this.tarButton.getSelection() ) {
      this.descriptionLabel.setVisible( false ); 
      this.linkForProperty.setVisible( false ); 
      if (this.sourceTree.getInput() != null) this.sourceTree.setInput( null );
      URI[] urls = GridFileDialog.openFileDialog( getShell(), GridFileDialog.STYLE_MULTI_SELECTION );
      //getSourceTree().collapseAll();
      updatePagebuttonComplete(urls);
    }
 
    if( e.getSource().equals( this.linkForProperty )  ) {

      PreferencesUtil.createPropertyDialogOn( getShell(), getGridProject()
                                              .getResource().getProject(), null, null, null ).open();
      ( ( DeploymentWizard ) getWizard() ).initProjects();
      //initContents();
    }
    }
      
  /**
   * Show a dialog for selecting a file. This is called by pressing the buttons
   * for defining the certificate and the key files.
   * 
   * @param initial The initial path to be set on the dialog.
   * @param title The title of the dialog window.
   * @return The chosen file.
   */
  protected String showFileDialog( final String initial, final String title  ) {
    String[] filterExtensions = { "*.tar", "*.tar.gz", "*.*" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    //String[] filterNames = { "tar file", "tar compressed file", "All Files" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    FileDialog fileDialog = new FileDialog( getShell(), SWT.OPEN | SWT.SINGLE );
    fileDialog.setFilterExtensions( filterExtensions );
    //fileDialog.setFilterNames( filterNames );
    fileDialog.setFileName( initial );
    fileDialog.setText( title );
    String selected = fileDialog.open();
    return selected;
  }
  
  protected void updatePagebuttonComplete(final URI[] urls) {
    this.setPageComplete(false);
    this.disksource = urls;
    this.source = null;
    if ( urls == null ) {
      this.setMessage( null );
      this.setErrorMessage( Messages.getString( "Deployment.deployment_source_is_empty" ) ); //$NON-NLS-1$
      return;
    }
  
 
    String sourcelabel = "The selected deployment source is (are): "; //$NON-NLS-1$
    for (URI uri: urls)
      sourcelabel += uri.toString() + ", "; //$NON-NLS-1$
    sourcelabel = sourcelabel.substring( 0, sourcelabel.length()-2 );
    this.setMessage( sourcelabel );

    this.setPageComplete( this.canFlipToNextPage() );
    this.setErrorMessage( null );
  }
  
  protected void updatePageComplete(final IGridElement [] urls) {
    this.setPageComplete( false );
    this.disksource = null;
    if ( urls == null ) {
      this.setMessage( null );
      this.setErrorMessage( Messages.getString( "Deployment.deployment_source_is_empty" ) ); //$NON-NLS-1$
      return;
    }
   
    String sourcelabel = "The selected deployment source is (are): "; //$NON-NLS-1$
    for (IGridElement uri: urls)
      sourcelabel += uri.getName() + ", "; //$NON-NLS-1$
    sourcelabel = sourcelabel.substring( 0, sourcelabel.length()-2 );
    
    this.source = urls;
    this.setMessage( sourcelabel );
    this.setPageComplete( this.canFlipToNextPage() );
    this.setErrorMessage( null );
  }
  
  @Override
  public boolean canFlipToNextPage() {
    boolean next = false;
    if ( this.sourceTree.getCheckedElements().length >0 || this.disksource != null) {
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

  /** the tree for selecting the source files
   * @return CheckboxTreeViewer
   */
  public CheckboxTreeViewer getSourceTree() {
    return this.sourceTree;
  }
  
  /** the wizard 
   * @return IWizard
   */
  public IWizard getsourceWizard() {
    return this.getWizard();
  }
  /**the tar file that will be used for install
   * the software, including application source programs, must be packed in a tar file
   * @return String
   */
  public String getTarFile() {
    return this.tarCombo.getText();
  }
  
  /**give back the chosen deployment source
   * @return IFileStore[]
   */
  public IGridElement[] getSource() {
    return this.source;
  }
  
  protected IGridElement[] getReferencedProjects() {
    return ( ( DeploymentWizard ) this.getWizard() ).getReferencedProjects();
  }
  
  protected IGridElement getGridProject() {
    return ( ( DeploymentWizard ) this.getWizard() ).getGridProject();
  }

 protected void updateUI() {
    
    //String tarFile = DeploymentSource.this.tarCombo.getText();
    //String tarType = new Path( tarFile ).getFileExtension();
    
  /*  if ( tarType.contains( ".tar" )) { //$NON-NLS-1$
      DeploymentSource.this.tarCombo.setEnabled( true );
    } else {
      DeploymentSource.this.tarCombo.setEnabled( false );
    }*/
    
  }
  
  protected void validatePage() {
    
    // Do some initialization
    String errorMessage = null;
    
    // Check if the specified certificate file exists
    String tarText = this.tarCombo.getText();
    File tarFile = new File( tarText );
    if ( !tarFile.exists() ) {
      errorMessage = Messages.getString("Deployment.deployment_source_tar_does_not_exist"); //$NON-NLS-1$
      
    }
    setErrorMessage( errorMessage );
    setPageComplete( this.canFlipToNextPage() );
        
  }
}