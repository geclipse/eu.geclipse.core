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
 *     UCY (http://www.ucy.cs.ac.cy)
 *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.jsdl.ui.internal.pages.sections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.forms.widgets.FormToolkit;

import eu.geclipse.jsdl.model.base.FileSystemType;
import eu.geclipse.jsdl.model.base.JobDefinitionType;
import eu.geclipse.jsdl.model.base.JobDescriptionType;
import eu.geclipse.jsdl.model.base.JsdlFactory;
import eu.geclipse.jsdl.model.base.JsdlPackage;
import eu.geclipse.jsdl.model.base.ResourcesType;
import eu.geclipse.jsdl.ui.adapters.jsdl.JsdlAdaptersFactory;
import eu.geclipse.jsdl.ui.internal.dialogs.FileSystemsDialog;
import eu.geclipse.jsdl.ui.internal.pages.FormSectionFactory;
import eu.geclipse.jsdl.ui.internal.pages.Messages;
import eu.geclipse.jsdl.ui.providers.FeatureContentProvider;
import eu.geclipse.jsdl.ui.providers.FeatureLabelProvider;


/**
 * @author nloulloud
 *
 */
public class FileSystemSection extends JsdlAdaptersFactory {
  
  
  private static final int TXT_LENGTH = 300;
  private static final int WIDGET_HEIGHT = 100;
  protected JobDescriptionType jobDescriptionType = JsdlFactory.eINSTANCE.createJobDescriptionType();
  protected ResourcesType resourcesType = JsdlFactory.eINSTANCE.createResourcesType();
  protected FileSystemType fileSystemType = JsdlFactory.eINSTANCE.createFileSystemType();
  protected Button btnFileSystemAdd = null;
  protected Button btnFileSystemDel = null;
  protected Button btnFileSystemEdit = null;  
  protected TableViewer fileSystemsViewer = null;
  protected Object[] value = null;
    
  private Table tblFileSystems = null;
  private TableColumn column = null;
  private boolean adapterRefreshed = false;
  private boolean isNotifyAllowed = true;
  private Composite containerComposite = null;
  
  /**
   * @param parent
   * @param toolkit
   */
  public FileSystemSection (final Composite parent,
                            final FormToolkit toolkit) {
    
    this.containerComposite = parent;
    createSection( parent, toolkit );
  }
  
  
  
  /**
   * @param jobDefinitionType
   */
  public void setInput( final JobDefinitionType jobDefinitionType ) { 

    this.adapterRefreshed = true;
    this.jobDescriptionType = jobDefinitionType.getJobDescription();
    
    if ( this.jobDescriptionType.getResources() != null ) {
      this.resourcesType = this.jobDescriptionType.getResources();
    }
    fillFields();
    
  }
  
  
  
  private void createSection  ( final Composite parent,
                                final FormToolkit toolkit ) {
    
    String sectionTitle = Messages.getString( "ResourcesPage_FileSystem") ;  //$NON-NLS-1$
    String sectionDescription = Messages.getString( "ResourcesPage_FileSystemDesc" ); //$NON-NLS-1$
    

       
    Composite client = FormSectionFactory.createGridStaticSection( toolkit,
                                                               parent,
                                                               sectionTitle,
                                                               sectionDescription,
                                                               2 );
    GridData gd;
    gd = new GridData();
    
    /* ========================= File System Widgets ===========================*/
    
   
    
    this.tblFileSystems = new Table( client, SWT.BORDER | SWT.H_SCROLL 
                                            | SWT.V_SCROLL | SWT.MULTI );
    gd = new GridData( GridData.FILL_BOTH );
    gd.grabExcessHorizontalSpace = true;
    gd.grabExcessVerticalSpace = true;
    gd.verticalSpan = 5;
    gd.horizontalSpan = 1;
    gd.heightHint = WIDGET_HEIGHT;
    gd.widthHint = TXT_LENGTH;
    
    this.tblFileSystems.setLayoutData( gd );
    
    //FIXME This is a work-around for the Bug#: 201705 for Windows.
    this.fileSystemsViewer = new TableViewer( this.tblFileSystems );
    this.tblFileSystems = this.fileSystemsViewer.getTable();    
    this.fileSystemsViewer.setContentProvider( new FeatureContentProvider() );
    this.fileSystemsViewer.setLabelProvider( new FeatureLabelProvider() );
    this.tblFileSystems.setHeaderVisible( true );
    this.column = new TableColumn( this.tblFileSystems, SWT.NONE );
    this.column.setText( "Name" ); //$NON-NLS-1$
    this.column.setWidth( 150 );
    this.column = new TableColumn( this.tblFileSystems, SWT.NONE );
    this.column.setText( "Type" ); //$NON-NLS-1$
    this.column.setWidth( 60 );
    this.column = new TableColumn( this.tblFileSystems, SWT.NONE );
    this.column.setText( "Mount Point" ); //$NON-NLS-1$
    this.column.setWidth( 60 );
        
    this.fileSystemsViewer.addSelectionChangedListener( new ISelectionChangedListener()
    {

      public void selectionChanged( final SelectionChangedEvent event ) {
        updateButtons( ( TableViewer )event.getSource() );
      }
    } );
    
    

    this.tblFileSystems.setData(  FormToolkit.KEY_DRAW_BORDER );

    
    /* Create the Add button */
    gd = new GridData();
    gd.verticalSpan = 2;
    gd.verticalAlignment = GridData.END;
    gd.horizontalAlignment = GridData.FILL;
    
    this.btnFileSystemAdd = toolkit.createButton( client,
                                        Messages.getString( "JsdlEditor_AddButton" ), //$NON-NLS-1$
                                        SWT.BUTTON1);  
    
    this.btnFileSystemAdd.addSelectionListener( new SelectionListener() {
      public void widgetSelected( final SelectionEvent event ) {
        handleAddFsDialog(Messages.getString( "ResourcesPage_NewFileSystemsDialog" ), //$NON-NLS-1$
                                                 ( Button ) event.getSource() ); 
                
        addFileSystem(FileSystemSection.this.fileSystemsViewer,                                                          
                      FileSystemSection.this.value );
      }

       public void widgetDefaultSelected( final SelectionEvent event ) {
           // Do Nothing - Required method
       }
     });
    
    this.btnFileSystemAdd.setLayoutData( gd );
    
    //FIXME Un-comment for Edit Functionality
    
    /* Create the Edit button */
    gd = new GridData();
    gd.verticalSpan = 2;
    gd.verticalAlignment = GridData.END;
    gd.horizontalAlignment = GridData.FILL;
    this.btnFileSystemEdit = toolkit.createButton(client,
                                    Messages.getString("JsdlEditor_EditButton"), //$NON-NLS-1$
                                    SWT.BUTTON1);  
    
    this.btnFileSystemEdit.addSelectionListener(new SelectionListener() {
      public void widgetSelected(final SelectionEvent event) {
        handleAddFsDialog( Messages.getString( "ResourcesPage_EditHostNameDialog" ), //$NON-NLS-1$
                                                    (Button) event.getSource()); 
        
//        ResourcesPage.this.resourcesTypeAdapter.performEdit(ResourcesPage.this.fileSystemsViewer,                                                          
//                                                           ResourcesPage.this.value[0]);
      }

       public void widgetDefaultSelected(final SelectionEvent event) {
           // Do Nothing - Required method
       }
     });
    this.btnFileSystemEdit.setEnabled( false );
    this.btnFileSystemEdit.setLayoutData( gd );
    
    
    /* Create the Remove button */
    gd = new GridData();
    gd.verticalSpan = 1;
    gd.verticalAlignment = GridData.BEGINNING;
    gd.horizontalAlignment = GridData.FILL;
    
    this.btnFileSystemDel = toolkit.createButton(client, 
                                 Messages.getString( "JsdlEditor_RemoveButton" ), //$NON-NLS-1$
                                 SWT.BUTTON1 );
    
    this.btnFileSystemDel.setEnabled( false );
//    this.resourcesTypeAdapter.attachToDelete( this.btnFileSystemDel, this.fileSystemsViewer );
    this.btnFileSystemDel.setLayoutData( gd );
        
    toolkit.paintBordersFor( client );
    
  } //End void FileSystemsSection()
    
  
  
  protected void contentChanged() {
    
    if (this.isNotifyAllowed){
      fireNotifyChanged( null);
    }
    
  }
  
  
  
  /*
   * Method which opens a Dialog for adding new File Systems.
   */
  protected void handleAddFsDialog( final String dialogTitle, final Button button ){
    
    this.value = null;
    
    FileSystemsDialog fileSystemDialog = new FileSystemsDialog( this.containerComposite.getShell(), dialogTitle );

    /* Edit Element */ 
    if (button != this.btnFileSystemAdd ) {
       IStructuredSelection structSelection 
                   = ( IStructuredSelection ) this.fileSystemsViewer.getSelection();
       
       fileSystemDialog.setInput( structSelection.getFirstElement() );

    }  
 
    if( fileSystemDialog.open() != Window.OK ) {
      return;        
    }
    
      this.value = fileSystemDialog.getValue();
    
  }
  
  
  
  /**
   * 
   * Method that adds a list of File Systems.
   * 
   * @param tableViewer The {@link TableViewer} that will hold the File Systems.
   * @param innerValue The list of File Systems.
   */
  @SuppressWarnings("unchecked")
  public void addFileSystem( final TableViewer tableViewer, final Object[] innerValue ) {
    
    if (innerValue == null) {
      return;
    }

    Collection<FileSystemType> collection = new ArrayList<FileSystemType>();

     
    EList <FileSystemType> newInputList = ( EList<FileSystemType> )tableViewer.getInput(); 
        
    
    if (newInputList == null) {
      newInputList = new BasicEList<FileSystemType>();
    }
    
    for (int i=0; i < innerValue.length; i++) {
      
      newInputList.add( (FileSystemType) innerValue[i] );  
    }

    tableViewer.setInput( newInputList  );
    
    
    for ( int i=0; i<tableViewer.getTable().getItemCount(); i++ ) {      
      collection.add( (FileSystemType) tableViewer.getElementAt( i ) );
    }
    
    checkFileSystemElement();
    this.resourcesType.getFileSystem().clear();
    this.resourcesType.getFileSystem().addAll( collection );
  


    this.contentChanged();
    

    collection = null;
    
  } // end addFileSystem()
  
  
  

  protected void checkResourcesElement() {
    
    EStructuralFeature eStructuralFeature = this.jobDescriptionType.eClass()
          .getEStructuralFeature( JsdlPackage.JOB_DESCRIPTION_TYPE__RESOURCES );
    
    /*
     * Check if the Resources element is not set. If not set then set it to its 
     * container (JobDescriptionType).
     */
    if (!this.jobDescriptionType.eIsSet( eStructuralFeature )) {      
      this.jobDescriptionType.eSet( eStructuralFeature, this.resourcesType );
    }
    /* 
     * If the Resources Element is set, check for any possible contents which may
     * be set. Also check if the Exclusive Execution attribute is set.
     * If none of the above are true, then delete the Resources Element from it's
     * container (JobDescriptionType).
     */
    else {
      if ( !this.resourcesType.isExclusiveExecution() && this.resourcesType.eContents().size() == 0) {
        EcoreUtil.remove( this.resourcesType );
      }
    }
    
  } // end void checkResourcesElement()
  
  
  
  
  protected void checkFileSystemElement() {
    
    checkResourcesElement();
    
    EStructuralFeature eStructuralFeature = this.resourcesType.eClass()
    .getEStructuralFeature( JsdlPackage.RESOURCES_TYPE__FILE_SYSTEM);
    
    Collection<FileSystemType> collection = new ArrayList<FileSystemType>();

    // Make sure the FileSystem Type is not NULL. If NULL then instantiate it.
    if (this.fileSystemType == null) {
        this.fileSystemType = JsdlFactory.eINSTANCE.createFileSystemType();
    }
    collection.add( this.fileSystemType );
    
    if ( !this.resourcesType.eIsSet( eStructuralFeature ) ) {   
      this.resourcesType.eSet( eStructuralFeature, collection );
    }
    
  } // end void checkFileSystemElement()
  
  
  
  /*
   * This function updates the status of the buttons related to
   * the File System Viewers. The Status of the 
   * buttons is adjusted according to the selection and content of the respective
   * table viewer.
   * 
   */ 
    protected void updateButtons( final TableViewer tableViewer ) {
    
    ISelection selection = tableViewer.getSelection();
    boolean selectionAvailable = !selection.isEmpty();    
         
    this.btnFileSystemAdd.setEnabled( true );
    this.btnFileSystemEdit.setEnabled( selectionAvailable );
    this.btnFileSystemDel.setEnabled( selectionAvailable );
    
    
  } // End updateButtons    

    
    
  @SuppressWarnings("unchecked")
  private void fillFields() {
    
    this.isNotifyAllowed = false;
        
    if ( null != this.resourcesType.getFileSystem() ) {
      EList<FileSystemType> valueEList = this.resourcesType.getFileSystem();
          
      for (Iterator<FileSystemType>  it = valueEList.iterator(); it.hasNext();){                    
        this.fileSystemType = it.next();                   
        this.fileSystemsViewer.setInput( valueEList );
        
        } // End Iterator
    }
               
    this.isNotifyAllowed = true;
    
    if ( this.adapterRefreshed ) {
      this.adapterRefreshed = false;
    }
    
  }
  
  
}
