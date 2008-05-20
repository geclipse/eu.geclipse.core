/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium
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
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;

import eu.geclipse.jsdl.model.base.JobDefinitionType;
import eu.geclipse.jsdl.model.base.JobIdentificationType;
import eu.geclipse.jsdl.model.base.JsdlFactory;
import eu.geclipse.jsdl.ui.adapters.jsdl.JsdlAdaptersFactory;
import eu.geclipse.jsdl.ui.internal.Activator;
import eu.geclipse.jsdl.ui.internal.dialogs.MultipleInputDialog;
import eu.geclipse.jsdl.ui.internal.pages.FormSectionFactory;
import eu.geclipse.jsdl.ui.internal.pages.Messages;
import eu.geclipse.jsdl.ui.providers.FeatureContentProvider;
import eu.geclipse.jsdl.ui.providers.FeatureLabelProvider;

/**
 * @author nloulloud
 */
public class JobIdentificationSection extends JsdlAdaptersFactory {

  private static final int WIDGET_HEIGHT = 100;
  protected JobDefinitionType jobDefinitionType = JsdlFactory.eINSTANCE.createJobDefinitionType();
  protected JobIdentificationType jobIdentificationType = JsdlFactory.eINSTANCE.createJobIdentificationType();
  protected Object[] value = null;
  protected TableViewer annotationsViewer = null;
  protected TableViewer projectsViewer = null;
  protected Text txtDescription = null;
  protected Text txtJobName = null;
  protected List lstJobProject = null;
  protected List lstJobAnnotation = null;
  protected Label lblJobDescripiton = null;
  protected Label lblJobAnnotation = null;
  protected Label lblJobProject = null;
  protected Button btnAnnotationsAdd = null;
  protected Button btnAnnotationsDel = null;
  protected Button btnProjectsAdd = null;
  protected Button btnProjectsDel = null;
  protected Composite body = null;
  
  private boolean isNotifyAllowed = true;
  private boolean adapterRefreshed = false;
  private Composite containerComposite = null;

  /**
   * @param formPage The FormPage that contains this Section
   * @param parent The parent composite.
   * @param toolkit The parent Form Toolkit.
   */
  public JobIdentificationSection( final FormPage formPage,
                                   final Composite parent,
                                   final FormToolkit toolkit ) {
    
    this.containerComposite = parent;
    createSection( parent, toolkit );
  }

  
  /*
   * Create the Job Identification Section which includes the following: -Job
   * Name (String) -Job Description (String) -Job Annotation (EList) -Job
   * Project (EList)
   */
  private void createSection( final Composite parent, final FormToolkit toolkit ) {
    
    String sectionTitle = Messages.getString( "JobDefinitionPage_JobIdentificationTitle" ); //$NON-NLS-1$    
    String sectionDescription = Messages.getString( "JobDefinitionPage_JobIdentificationDescr" ); //$NON-NLS-1$
    GridData gd;
    Composite client = FormSectionFactory.createGridStaticSection( toolkit,
                                                                   parent,
                                                                   sectionTitle,
                                                                   sectionDescription,
                                                                   4 );
    
    /* ================================ Job Name ============================ */
    toolkit.createLabel( client,
                         Messages.getString( "JobDefinitionPage_JobName" ) ); //$NON-NLS-1$
    gd = new GridData();
    gd.grabExcessHorizontalSpace = true;
    gd.verticalAlignment = GridData.CENTER;
    gd.horizontalSpan = 3;
    gd.widthHint = 300;
    this.txtJobName = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    this.txtJobName.addModifyListener( new ModifyListener() {

      public void modifyText( final ModifyEvent e ) {
        JobIdentificationSection.this.jobIdentificationType
                                                     .setJobName( JobIdentificationSection.this.txtJobName.getText() );
        contentChanged();
      }
    } );
    this.txtJobName.setLayoutData( gd );
    
    /* ========================= Job Description ============================ */
    this.lblJobDescripiton = toolkit.createLabel( client,
                                                  Messages.getString( "JobDefinitionPage_JobDescr" ) ); //$NON-NLS-1$
    gd = new GridData();
    gd.verticalSpan = 1;
    gd.verticalAlignment = GridData.BEGINNING;
    this.lblJobDescripiton.setLayoutData( gd );
    this.txtDescription = toolkit.createText( client,
                                              "", SWT.MULTI | SWT.V_SCROLL | SWT.WRAP ); //$NON-NLS-1$
    this.txtDescription.addModifyListener( new ModifyListener() {

      public void modifyText( final ModifyEvent e ) {
        JobIdentificationSection.this.jobIdentificationType
                                             .setDescription( JobIdentificationSection.this.txtDescription.getText() );
        contentChanged();
      }
    } );
    gd = new GridData();
    gd.verticalAlignment = GridData.FILL;
    gd.verticalSpan = 1;
    gd.horizontalSpan = 3;
    gd.widthHint = 285;
    gd.heightHint = WIDGET_HEIGHT;
    this.txtDescription.setLayoutData( gd );
    
    /* ========================= Job Annotation ============================= */
    this.lblJobAnnotation = toolkit.createLabel( client,
                                               Messages.getString( "JobDefinitionPage_JobAnnotation" ) ); //$NON-NLS-1$
    gd = new GridData();
    gd.horizontalSpan = 1;
    gd.verticalSpan = 2;
    gd.verticalAlignment = GridData.BEGINNING;
    this.lblJobAnnotation.setLayoutData( gd );
    Table tblAnnotations = new Table( client, SWT.BORDER
                                              | SWT.H_SCROLL
                                              | SWT.V_SCROLL
                                              | SWT.MULTI );
    gd = new GridData();
    gd.verticalAlignment = GridData.FILL;
    gd.horizontalSpan = 1;
    gd.verticalSpan = 2;
    gd.widthHint = 250;
    gd.heightHint = WIDGET_HEIGHT;
    tblAnnotations.setLayoutData( gd );
    // FIXME This is a work-around for the Bug#: 201705 for Windows.
    this.annotationsViewer = new TableViewer( tblAnnotations );
    tblAnnotations = this.annotationsViewer.getTable();
    this.annotationsViewer.setContentProvider( new FeatureContentProvider() );
    FeatureLabelProvider annotationsLabelProvider = new FeatureLabelProvider();
    annotationsLabelProvider.setStringType( FeatureLabelProvider.STRING_TYPE_ANNOTATION );
    
    this.annotationsViewer.setLabelProvider( annotationsLabelProvider );
    this.annotationsViewer.addSelectionChangedListener( new ISelectionChangedListener()    {

      public void selectionChanged( final SelectionChangedEvent event ) {
        updateButtons( ( TableViewer )event.getSource() );
      }
    } );
    tblAnnotations.setData( FormToolkit.KEY_DRAW_BORDER );
    
    /* Create Button ADD */
    gd = new GridData();
    gd.horizontalSpan = 2;
    gd.verticalSpan = 1;
    gd.widthHint = 60;
    this.btnAnnotationsAdd = toolkit.createButton( client,
                                                   Messages.getString( "JsdlEditor_AddButton" ), //$NON-NLS-1$
                                                   SWT.PUSH );
    this.btnAnnotationsAdd.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent event ) {
        handleAddDialog( Messages.getString( "JobDefinitionPage_JobAnnotationDialog" ) ); //$NON-NLS-1$       
        performAdd( JobIdentificationSection.this.annotationsViewer,
                    JobIdentificationSection.this.value );
      }
    } );
    this.btnAnnotationsAdd.setLayoutData( gd );
    
    /* Create Button DEL */
    gd = new GridData();
    gd.horizontalSpan = 2;
    gd.verticalSpan = 1;
    gd.widthHint = 60;
    gd.verticalAlignment = GridData.BEGINNING;
    this.btnAnnotationsDel = toolkit.createButton( client,
                                                   Messages.getString( "JsdlEditor_RemoveButton" ), //$NON-NLS-1$
                                                   SWT.PUSH );
    this.btnAnnotationsDel.setEnabled( false );
    this.btnAnnotationsDel.addSelectionListener( new SelectionListener() {

      public void widgetSelected( final SelectionEvent event ) {
        performDelete( JobIdentificationSection.this.annotationsViewer );
      }

      public void widgetDefaultSelected( final SelectionEvent event ) {
        // Do Nothing
      }
    } );
    this.btnAnnotationsDel.setLayoutData( gd );
    
    /* ============================= Job Project ============================= */
    this.lblJobProject = toolkit.createLabel( client,
                                              Messages.getString( "JobDefinitionPage_JobProject" ) ); //$NON-NLS-1$
    gd = new GridData();
    gd.verticalSpan = 2;
    gd.horizontalSpan = 1;
    gd.verticalAlignment = GridData.BEGINNING;
    this.lblJobProject.setLayoutData( gd );
    Table tblProjects = new Table( client, SWT.BORDER
                                           | SWT.H_SCROLL
                                           | SWT.V_SCROLL
                                           | SWT.MULTI );
    gd = new GridData();
    gd.verticalSpan = 2;
    gd.horizontalSpan = 1;
    gd.widthHint = 250;
    gd.heightHint = WIDGET_HEIGHT;
    tblProjects.setLayoutData( gd );
    // FIXME This is a work-around for the Bug#: 201705 for Windows.
    this.projectsViewer = new TableViewer( tblProjects );
    tblProjects = this.annotationsViewer.getTable();
    this.projectsViewer.setContentProvider( new FeatureContentProvider() );
    FeatureLabelProvider projectsLabelProvider = new FeatureLabelProvider();
    projectsLabelProvider.setStringType( FeatureLabelProvider.STRING_TYPE_PROJECT );
    this.projectsViewer.setLabelProvider( projectsLabelProvider );
    this.projectsViewer.addSelectionChangedListener( new ISelectionChangedListener() {

      public void selectionChanged( final SelectionChangedEvent event ) {
        updateButtons( ( TableViewer )event.getSource() );
      }
    } );
    tblAnnotations.setData( FormToolkit.KEY_DRAW_BORDER );
    
    // Create Button ADD
    gd = new GridData();
    gd.horizontalSpan = 2;
    gd.verticalSpan = 1;
    gd.widthHint = 60;
    this.btnProjectsAdd = toolkit.createButton( client,
                                                Messages.getString( "JsdlEditor_AddButton" ), //$NON-NLS-1$
                                                SWT.PUSH );
    this.btnProjectsAdd.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent event ) {
        handleAddDialog( Messages.getString( "JobDefinitionPage_JobProjectDialog" ) ); //$NON-NLS-1$
        performAdd( JobIdentificationSection.this.projectsViewer,
                    JobIdentificationSection.this.value );
      }
    } );
    this.btnProjectsAdd.setLayoutData( gd );
    /* Create Button DEL */
    gd = new GridData();
    gd.horizontalSpan = 2;
    gd.widthHint = 60;
    gd.verticalSpan = 1;
    gd.verticalAlignment = GridData.BEGINNING;
    this.btnProjectsDel = toolkit.createButton( client,
                                                Messages.getString( "JsdlEditor_RemoveButton" ), //$NON-NLS-1$
                                                SWT.PUSH );
    this.btnProjectsDel.setEnabled( false );
    this.btnProjectsDel.addSelectionListener( new SelectionListener() {

      public void widgetSelected( final SelectionEvent event ) {
        performDelete( JobIdentificationSection.this.projectsViewer );
      }

      public void widgetDefaultSelected( final SelectionEvent event ) {
        // Do Nothing
      }
    } );
    this.btnProjectsDel.setLayoutData( gd );
    toolkit.paintBordersFor( client );
    
  }

  
  protected void performDelete( final TableViewer viewer ) {
    
    IStructuredSelection structSelection = ( IStructuredSelection )viewer.getSelection();
    
    if( structSelection != null ) {
      Iterator<?> it = structSelection.iterator();
      /*
       * Iterate over the selections and delete them from the model.
       */
      while( it.hasNext() ) {
        Object feature = it.next();
        try {
          /* Delete only Multi-Valued Elements */
          if( !this.adapterRefreshed ) {
            /*
             * Check the instance of the Selection.
             */
            if( feature instanceof String ) {
              /*
               * If this feature is an instance of String then, this is a
               * CandidateHosts instance. Therefore, remove the CandidataHosts
               * instance.
               */
              if( viewer == this.annotationsViewer ) {
                this.jobIdentificationType.getJobAnnotation().remove( feature );
              } else {
                this.jobIdentificationType.getJobProject().remove( feature );
              }
            }
            contentChanged();
          } else {
            viewer.remove( feature );
          }
        } // end try
        catch( Exception e ) {
          Activator.logException( e );
        }
        /*
         * Refresh the table viewer and notify the editor that the page content
         * has changed.
         */
        viewer.refresh();
      } // end While
    }// end_if
    
  } // end performDelete()

    
  /**
   * Method that adds a list of Candidate Hosts .
   * 
   * @param tableViewer The {@link TableViewer} that will hold the Candidate
   *            Hosts.
   * @param innerValue The list of Candidate Hosts.
   */
  @SuppressWarnings("unchecked")
  public void performAdd( final TableViewer tableViewer,
                          final Object[] innerValue ) {
    
    boolean elementExists = false;
    String newElement;
    
    for( int i = 0; i < innerValue.length; i++ ) {
      if( null == innerValue[ i ] ) {
        return;
      }
    }
    
    Collection<String> collection = new ArrayList<String>();
    EList<String> newInputList = ( EList<String> )tableViewer.getInput();
    
    if( newInputList == null ) {
      newInputList = new BasicEList<String>();
    }
    
    for( int i = 0; ( ( i < innerValue.length ) && ( !elementExists ) ); i++ ) {
      newElement = ( String )innerValue[ i ];
      elementExists = newInputList.contains( newElement );
      if( !elementExists ) {
        newInputList.add( newElement );
      }
    }
    
    if( !elementExists ) {
      for( int i = 0; i < newInputList.size(); i++ ) {
        collection.add( newInputList.get( i ) );
      }
      if( tableViewer == this.annotationsViewer ) {
        this.jobIdentificationType.getJobAnnotation().clear();
        this.jobIdentificationType.getJobAnnotation().addAll( collection );
        tableViewer.setInput( this.jobIdentificationType.getJobAnnotation() );
      } else {
        this.jobIdentificationType.getJobProject().clear();
        this.jobIdentificationType.getJobProject().addAll( collection );
        tableViewer.setInput( this.jobIdentificationType.getJobProject() );
      }
      this.contentChanged();
    } else {
      MessageDialog.openError( tableViewer.getControl().getShell(),
                               Messages.getString( "GenericDialog_DuplicateEntryDialog_Title" ), //$NON-NLS-1$
                               Messages.getString( "GenericDialog_New_DuplicateEntryDialog_Message" ) ); //$NON-NLS-1$
    }
    collection = null;
    
  } // end void addCandidateHosts()

  
  protected void handleAddDialog( final String dialogTitle ) {
    
    this.value = new Object[ 1 ];
    MultipleInputDialog dialog = new MultipleInputDialog( this.containerComposite.getShell(), dialogTitle );
    
    dialog.addStoredComboField( Messages.getString( "JobDefinitionPage_Value" ), //$NON-NLS-1$
                                "", //$NON-NLS-1$
                                false,
                                "JobDefinitionPage_Value" ); //$NON-NLS-1$
    
    if( dialog.open() != Window.OK ) {
      return;
    }
    this.value[ 0 ] = dialog.getStringValue( Messages.getString( "JobDefinitionPage_Value" ) ); //$NON-NLS-1$
  }

  
  /**
   * @param jobDefinition The JSDL Job Definition element.
   */
  public void setInput( final JobDefinitionType jobDefinition ) {
    
    this.adapterRefreshed = true;
    if( null != jobDefinition ) {
      this.jobDefinitionType = jobDefinition;
      this.jobIdentificationType = this.jobDefinitionType.getJobDescription()
        .getJobIdentification();
      fillFields();
    }
    
  }

  
  private void fillFields() {
    
    this.isNotifyAllowed = false;
    
    if( null != this.jobIdentificationType ) {
      if(null != this.jobIdentificationType.getJobName()){
        this.txtJobName.setText( this.jobIdentificationType.getJobName() );
      }
      if (null != this.jobIdentificationType.getDescription()) {
        this.txtDescription.setText( this.jobIdentificationType.getDescription() );
      }
      this.projectsViewer.setInput( this.jobIdentificationType.getJobProject() );
      this.annotationsViewer.setInput( this.jobIdentificationType.getJobAnnotation() );
    }
    this.isNotifyAllowed = true;
    if( this.adapterRefreshed ) {
      this.adapterRefreshed = false;
    }
    
  }

  
  /*
   * This function updates the status of the buttons related to the respective
   * Stage-In and Stage-Out Table Viewers. The Status of the buttons is adjusted
   * according to the selection and content of the respective table viewer.
   */
  protected void updateButtons( final TableViewer tableViewer ) {
    
    ISelection selection = tableViewer.getSelection();
    boolean selectionAvailable = !selection.isEmpty();
    
    if( tableViewer == this.annotationsViewer ) {
      this.btnAnnotationsAdd.setEnabled( true );
      this.btnAnnotationsDel.setEnabled( selectionAvailable );
    } else {
      this.btnProjectsAdd.setEnabled( true );
      this.btnProjectsDel.setEnabled( selectionAvailable );
    }
    
  } // End updateButtons

  
  protected void contentChanged() {
    
    if( this.isNotifyAllowed ) {
      fireNotifyChanged( null );
    }
  }
  
}