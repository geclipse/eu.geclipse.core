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

import org.eclipse.core.resources.IFile;
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
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridRoot;
import eu.geclipse.jsdl.model.base.CandidateHostsType;
import eu.geclipse.jsdl.model.base.JobDefinitionType;
import eu.geclipse.jsdl.model.base.JobDescriptionType;
import eu.geclipse.jsdl.model.base.JsdlFactory;
import eu.geclipse.jsdl.model.base.JsdlPackage;
import eu.geclipse.jsdl.model.base.ResourcesType;
import eu.geclipse.jsdl.ui.internal.Activator;
import eu.geclipse.jsdl.ui.internal.dialogs.CandidateHostsDialog;
import eu.geclipse.jsdl.ui.internal.pages.FormSectionFactory;
import eu.geclipse.jsdl.ui.internal.pages.Messages;
import eu.geclipse.jsdl.ui.providers.FeatureContentProvider;
import eu.geclipse.jsdl.ui.providers.FeatureLabelProvider;


/**
 * @author nloulloud
 *
 */
public class CandidateHostsSection extends 
JsdlFormPageSection {
  
  private static final int TXT_LENGTH = 300;
  private static final int WIDGET_HEIGHT = 100;
  protected Object[] value = null;
  protected Button btnHostsAdd = null;
  protected Button btnHostsDel = null;
  protected TableViewer hostsViewer = null;
  protected TableViewer fileSystemsViewer = null; 
  protected JobDescriptionType jobDescriptionType = JsdlFactory.eINSTANCE.createJobDescriptionType();
  protected ResourcesType resourcesType = JsdlFactory.eINSTANCE.createResourcesType();
  protected CandidateHostsType candidateHosts = JsdlFactory.eINSTANCE.createCandidateHostsType();
  private FormPage containerPage = null; 
  private Composite containerComposite = null;
  
  
  /**
   * @param formPage 
   * @param parent
   * @param toolkit
   */
  public CandidateHostsSection( final FormPage formPage,
                                final Composite parent,
                                final FormToolkit toolkit ) {

    this.containerPage = formPage;
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
  
  
  
//  protected void contentChanged() {
//    
//    if (this.isNotifyAllowed){
//      fireNotifyChanged( null);
//    }
//    
//  }
    
  private void createSection (final Composite parent,
                              final FormToolkit toolkit ) {
    
    String sectionTitle = Messages.getString( "ResourcesPage_CanHost" ); //$NON-NLS-1$txtFileSystemName
    String sectionDescription = Messages.getString( "ResourcesPage_CandHostDesc" ); //$NON-NLS-1$
    
    GridData gd;
       
    Composite client = FormSectionFactory.createGridStaticSection( toolkit,
                                           parent,
                                           sectionTitle,
                                           sectionDescription,
                                           2 );
    
    gd = new GridData();
    
    /* ========================= Host Name Widgets ===========================*/
   
    
    Table tblHosts = new Table( client, SWT.BORDER | SWT.H_SCROLL 
                                      | SWT.V_SCROLL | SWT.MULTI );
    gd = new GridData( GridData.FILL_BOTH );
    gd.grabExcessHorizontalSpace = true;
    gd.grabExcessVerticalSpace = true;
    gd.verticalSpan = 5;
    gd.horizontalSpan = 1;
    gd.heightHint = WIDGET_HEIGHT;
    gd.widthHint = TXT_LENGTH;

    tblHosts.setLayoutData( gd );
    
    //FIXME This is a work-around for the Bug#: 201705 for Windows.
    this.hostsViewer = new TableViewer( tblHosts );
    tblHosts = this.hostsViewer.getTable();    
    this.hostsViewer.setContentProvider( new FeatureContentProvider() );
    FeatureLabelProvider hostsLabelProvider = new FeatureLabelProvider();
    hostsLabelProvider.setStringType( FeatureLabelProvider.STRING_TYPE_CANDIDATE_HOSTS );
    this.hostsViewer.setLabelProvider( hostsLabelProvider );
        
    this.hostsViewer.addSelectionChangedListener( new ISelectionChangedListener()
    {

      public void selectionChanged( final SelectionChangedEvent event ) {
        updateButtons( ( TableViewer )event.getSource() );
      }
    } );
    
    
    tblHosts.setData(  FormToolkit.KEY_DRAW_BORDER );

    
    /* Create the Add button */
    gd = new GridData();
    gd.verticalSpan = 2;
    gd.verticalAlignment = GridData.END;
    gd.horizontalAlignment = GridData.FILL;
    this.btnHostsAdd = toolkit.createButton( client,
                                        Messages.getString( "JsdlEditor_AddButton" ), //$NON-NLS-1$
                                        SWT.BUTTON1);  
    
    this.btnHostsAdd.addSelectionListener( new SelectionListener() {
      public void widgetSelected( final SelectionEvent event ) {
        handleAddDialog(Messages.getString( "ResourcesPage_NewHostNameDialog" ) ); //$NON-NLS-1$
                                                    
        addCandidateHosts(CandidateHostsSection.this.hostsViewer, CandidateHostsSection.this.value );
      }

       public void widgetDefaultSelected( final SelectionEvent event ) {
           // Do Nothing - Required method
       }
     });
    
    this.btnHostsAdd.setLayoutData( gd);
    
    
    /* Create the Remove button */
    gd = new GridData();
    gd.verticalSpan = 1;
    gd.verticalAlignment = GridData.BEGINNING;
    gd.horizontalAlignment = GridData.FILL;
    this.btnHostsDel = toolkit.createButton(client, 
                                 Messages.getString( "JsdlEditor_RemoveButton" ), //$NON-NLS-1$
                                 SWT.BUTTON1 );
    
    this.btnHostsDel.addSelectionListener(new SelectionListener() {

      public void widgetSelected(final SelectionEvent event) {
        performDelete( CandidateHostsSection.this.hostsViewer  );
      }

      public void widgetDefaultSelected(final SelectionEvent event) {
          // Do Nothing - Required method
      }
    });
    
    this.btnHostsDel.setEnabled( false );
        
    this.btnHostsDel.setLayoutData( gd);
        
    toolkit.paintBordersFor( client );
    
    updateButtons( this.hostsViewer );
  }
  
  
  
  /*
   * Method which opens a Dialog for selecting Candidate Hosts for Job Submission.
   */
  @SuppressWarnings("unchecked")
  protected void handleAddDialog( final String dialogTitle ) {
    
    this.value = null;
    
    CandidateHostsDialog hostsDialog = new CandidateHostsDialog( this.containerComposite.getShell(), dialogTitle );
    
    IFile file = ( (IFileEditorInput) this.containerPage.getEditor().getEditorInput() ).getFile();
    IGridRoot root = GridModel.getRoot();
    IGridElement element = root.findElement( file );
    hostsDialog.setDialogInput( element );
    hostsDialog.setExistingCandidateHosts( this.hostsViewer.getInput() );
 
    if( hostsDialog.open() != Window.OK ) {
      return;
        
    }    
      this.value = hostsDialog.getValue();
    
  }
  
  
  /**
   * 
   * Method that adds a list of Candidate Hosts .
   * 
   * @param tableViewer The {@link TableViewer} that will hold the Candidate Hosts.
   * @param innerValue The list of Candidate Hosts.
   */
  @SuppressWarnings("unchecked")
  public void addCandidateHosts(final TableViewer tableViewer, final Object[] innerValue) {
    
    if (innerValue == null) {
      return;
    }

    Collection<String> collection = new ArrayList<String>();

     
    EList <String> newInputList = ( EList<String> )tableViewer.getInput(); 
        
    
    if (newInputList == null) {
      newInputList = new BasicEList<String>();
    }
    
    for (int i=0; i < innerValue.length; i++) {
      
      newInputList.add( (String)innerValue[i] );  
    }
    
    for ( int i=0; i<newInputList.size(); i++ ) {      
      collection.add( newInputList.get( i ) );
    }
    
    checkCandidateHostsElement();
    this.candidateHosts.getHostName().clear();
    this.candidateHosts.getHostName().addAll( collection );
    tableViewer.setInput( this.candidateHosts.getHostName()  );
    


    this.contentChanged();
    

    collection = null;
    
  } // end void addCandidateHosts()
  
  
  
  protected void performDelete( final TableViewer viewer ){
    
    
    IStructuredSelection structSelection 
                              = ( IStructuredSelection ) viewer.getSelection();
    

    if (structSelection != null) {
      
    
      Iterator<?> it = structSelection.iterator();

      /*
       * Iterate over the selections and delete them from the model.
       */
      while ( it.hasNext() ) {
    
        Object feature = it.next();
           
        try {
          /* Delete only Multi-Valued Elements */
          
          if ( !this.adapterRefreshed ) {
            
            /*
             * Check the instance of the Selection.
             */            
            if ( feature instanceof String) {
              
              /*
               * If this feature is an instance of String then, this 
               * is a CandidateHosts instance. Therefore, remove the
               * CandidataHosts instance.
               */
      
              this.candidateHosts.getHostName().remove( feature );
              
              if ( this.candidateHosts.getHostName().size() == 0 ) {
                
                EcoreUtil.remove( this.candidateHosts );
                
                checkResourcesElement();
              
              }
            }
            
            contentChanged();    
          }
        else {
          viewer.remove( feature );
        }
          
          
      } //end try
      catch ( Exception e ) {
        Activator.logException( e );
      }
    
        /*
         * Refresh the table viewer and notify the editor that the page content has
         * changed.
         */  
      viewer.refresh();
    
      } // end While
      
    }// end_if
    
    
  } //end performDelete()
  
  
  
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
  
  

  protected void checkCandidateHostsElement() {
    checkResourcesElement();
    EStructuralFeature eStructuralFeature = this.resourcesType.eClass()
      .getEStructuralFeature( JsdlPackage.RESOURCES_TYPE__CANDIDATE_HOSTS );
    if( !this.resourcesType.eIsSet( eStructuralFeature ) ) {
      this.resourcesType.eSet( eStructuralFeature, this.candidateHosts );
    }
  } // end void checkCandidateHostsElement()
  
  
  
  /*
   * This function updates the status of the buttons related to the respective
   * Stage-In and Stage-Out Table Viewers. The Status of the buttons is adjusted
   * according to the selection and content of the respective table viewer.
   */ 
    protected void updateButtons( final TableViewer tableViewer ) {
    
    ISelection selection = tableViewer.getSelection();
    boolean selectionAvailable = !selection.isEmpty();    

     
      this.btnHostsAdd.setEnabled( true );
      this.btnHostsDel.setEnabled( selectionAvailable );

    
  } // End updateButtons
    
  
    @SuppressWarnings("unchecked")
    private void fillFields() {
      
      if( this.resourcesType.getCandidateHosts() != null ) {
        this.candidateHosts = this.resourcesType.getCandidateHosts();
        EList<String> valueEList = this.candidateHosts.getHostName();
        this.hostsViewer.setInput( valueEList );
    }
      
      if ( this.adapterRefreshed ) {
        this.adapterRefreshed = false;
      }
  }
  
}
