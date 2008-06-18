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
package eu.geclipse.jsdl.ui.internal.pages;

/**
 * @author nloulloud
 *
 */

import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import eu.geclipse.jsdl.model.base.JobDefinitionType;
import eu.geclipse.jsdl.ui.adapters.jsdl.ApplicationTypeAdapter;
import eu.geclipse.jsdl.ui.adapters.posix.PosixApplicationTypeAdapter;
import eu.geclipse.jsdl.ui.editors.JsdlEditor;

/**
 * 
 * This class provides the Overview page that appears in the JSDL editor.
 *  
 */ 
public final class OverviewPage extends JsdlFormPage implements IHyperlinkListener{
  
  protected static final String PAGE_ID = "OVERVIEW";  //$NON-NLS-1$
  private static final int WIDGET_MAX_WIDTH = 100;
  protected ApplicationTypeAdapter applicationTypeAdapter;
  protected PosixApplicationTypeAdapter posixApplicationTypeAdapter;   
  protected Composite composite = null;
  protected Label lblApplicationName = null;
  protected Label lblExecutable = null;
  protected Label lblInput = null;
  protected Label lblOutput = null;
  protected Label lblError = null;    
  protected FormText sectionContent = null;
  protected Text txtApplicationName = null;
  protected Text txtExecutable = null;
  protected Text txtInput = null;
  protected Text txtOutput = null;
  protected Text txtError = null;
  
  private FormToolkit toolkit;     
  /**
   * OverviewPage class constructor.
   * @param editor
   */
  public OverviewPage(final JsdlEditor editor){
    
    super( editor, "",  //$NON-NLS-1$
          Messages.getString("OverviewPage_PageTitle")); //$NON-NLS-1$
         
    
  } //End Class Constructor
  
  
  @Override
  public void setActive( final boolean active ) {
    
    if ( active ){
      if ( isContentRefreshed() ) {    
        
        this.applicationTypeAdapter.load();
        this.posixApplicationTypeAdapter.load();
        
      }//end_if isContentRefreshed
    } // end_if active
    
  } // End void setActive()
 
  
  /**
   * Method that set's the Overview Page content. The content is the root 
   * JSDL element. Also this method is responsible to initialize the associated 
   * type adapters for the elements of this page.  This method must be called only
   * from the JSDL Editor.
   * 
   * Associated Type Adapters for this page are: 
   * @see ApplicationTypeAdapter
   * @see PosixApplicationTypeAdapter
   *  
   * @param jobDefinitionRoot
   * 
   * @param refreshStatus
   * Set to TRUE if the original page content is already set, but there is a need
   * to refresh the page because there was a change to this content
   * from an outside editor.
   * 
   */
  public void setPageContent( final JobDefinitionType jobDefinitionRoot, 
                             final boolean refreshStatus ) {

   if (refreshStatus) {
      this.contentRefreshed = true;
      this.applicationTypeAdapter.setContent( jobDefinitionRoot );
      this.posixApplicationTypeAdapter.setContent( jobDefinitionRoot );
      
    }
   
   else {
     
      this.applicationTypeAdapter = new ApplicationTypeAdapter(jobDefinitionRoot);
      this.applicationTypeAdapter.addListener( this );
      this.posixApplicationTypeAdapter = new PosixApplicationTypeAdapter(jobDefinitionRoot);
      this.posixApplicationTypeAdapter.addListener( this );
      
   }
          
  } // End void setPageContent()   
  
  
  @Override
  protected void createFormContent(final IManagedForm managedForm){
  
    final ScrolledForm form = managedForm.getForm();
    form.setText(Messages.getString("OverviewPage_PageTitle"));  //$NON-NLS-1$
    
    TableWrapLayout layout = new TableWrapLayout();
    layout.verticalSpacing = 8;
    layout.horizontalSpacing = 8;
    layout.leftMargin = 10;
    layout.rightMargin = 10;
    layout.topMargin = 10;
    layout.bottomMargin = 10;
    layout.numColumns = 2;
    layout.makeColumnsEqualWidth = false;

    form.getBody().setLayout(layout);
    
    
    this.composite = createGeneralInfoSection(managedForm,
                     Messages.getString("OverviewPage_GeneralInfoTitle"),  //$NON-NLS-1$
                    Messages.getString("OverviewPage_GeneralInfoDescription")); //$NON-NLS-1$
    
    /* Load data from the adapters */
    this.applicationTypeAdapter.load();
    this.posixApplicationTypeAdapter.load();
    
    this.composite = createJobDefSection(managedForm,
                             Messages.getString("OverviewPage_JobDefTitle"),  //$NON-NLS-1$
                             Messages.getString("OverviewPage_JobDefDescription")); //$NON-NLS-1$
    
    this.composite = createApplicationSection(managedForm,
                     Messages.getString("OverviewPage_ApplicationTitle"),  //$NON-NLS-1$
                     Messages.getString("OverviewPage_ApplicationDescription")); //$NON-NLS-1$
    
    this.composite = createResourcesSection(managedForm,
                              Messages.getString("OverviewPage_ResourcesTitle"),  //$NON-NLS-1$
                       Messages.getString("OverviewPage_ResourcesDescription")); //$NON-NLS-1$
    
    this.composite = createDataStageSection(managedForm,
                                 Messages.getString("OverviewPage_DataStagingTitle"),  //$NON-NLS-1$
                          Messages.getString("OverviewPage_DataStagingDescription")); //$NON-NLS-1$
    
    addFormPageHelp( form );
   
  }
  
  @Override
  protected String getHelpResource() {
    return "/eu.geclipse.doc.user/html/concepts/jobmanagement/editingJSDL.html"; //$NON-NLS-1$
  }
  
  private Composite createSection( final IManagedForm mform, final String title,
                                   final String desc, final int numColumns ) {
      
     final ScrolledForm form = mform.getForm();
     this.toolkit = mform.getToolkit();
         
                     
     Section section = this.toolkit.createSection(form.getBody(), 
                                                   ExpandableComposite.TITLE_BAR 
                                                   | Section.DESCRIPTION 
                                                   | SWT.WRAP);

     section.clientVerticalSpacing = 5;   
     section.setText( title );
     section.setDescription( desc );
     this.toolkit.createCompositeSeparator( section );
     Composite client = this.toolkit.createComposite( section );
            
     TableWrapLayout layout = new TableWrapLayout();
     layout.verticalSpacing = 8;
     layout.horizontalSpacing = 8;
     layout.leftMargin = 10;
     layout.rightMargin = 10;
     layout.topMargin = 10;
     layout.bottomMargin = 10;
     layout.numColumns = numColumns;
     layout.makeColumnsEqualWidth = false;
     client.setLayout(layout);
     TableWrapData data = new TableWrapData( TableWrapData.FILL_GRAB );
     section.setLayoutData( data );               
     section.setClient( client );
    
        
     return client;
     
     }
  
  
  private Composite createGeneralInfoSection( final IManagedForm mform, 
                                              final String title, 
                                              final String desc ) {
    
    Composite client = createSection( mform, title, desc, 2 );
    TableWrapData td ;
    
    /* ======================== Application Name ============================ */
    
    this.lblApplicationName = this.toolkit.createLabel( client,
                                   Messages.getString( "OverviewPage_ApplName" ) ); //$NON-NLS-1$
    
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    td.maxWidth = OverviewPage.WIDGET_MAX_WIDTH;
    
    this.txtApplicationName = this.toolkit.createText( client,"", SWT.NONE );     //$NON-NLS-1$
    
    this.applicationTypeAdapter
                            .attachToApplicationName( this.txtApplicationName );
    
    this.txtApplicationName.setLayoutData( td );
        
    /* ============================== Executable ============================ */
    
    this.lblExecutable = this.toolkit.createLabel( client,
                                 Messages.getString( "OverviewPage_Executable" ) ); //$NON-NLS-1$
                        
     td = new TableWrapData( TableWrapData.FILL_GRAB );
     td.maxWidth = OverviewPage.WIDGET_MAX_WIDTH;
     
     this.txtExecutable = this.toolkit.createText( client,"", SWT.NONE );     //$NON-NLS-1$
     
     this.posixApplicationTypeAdapter
                        .attachPosixApplicationExecutable( this.txtExecutable );
     
     this.txtExecutable.setLayoutData( td );
    
    /* ============================== Input File ============================ */
    
     this.lblInput = this.toolkit.createLabel( client,
                                  Messages.getString( "OverviewPage_Input" ) ); //$NON-NLS-1$
                                          
     td = new TableWrapData( TableWrapData.FILL_GRAB );
     td.maxWidth = OverviewPage.WIDGET_MAX_WIDTH;
                       
     this.txtInput = this.toolkit.createText( client,"", SWT.NONE );     //$NON-NLS-1$
     
     this.posixApplicationTypeAdapter
                                 .attachPosixApplicationInput( this.txtInput );
     
     this.txtInput.setLayoutData( td );
    /* ==============================Output File ============================ */
     
     this.lblOutput = this.toolkit.createLabel( client,
                                  Messages.getString( "OverviewPage_Output" ) ); //$NON-NLS-1$
                                                      
     td = new TableWrapData( TableWrapData.FILL_GRAB );
     td.maxWidth = OverviewPage.WIDGET_MAX_WIDTH;
     
                                   
     this.txtOutput = this.toolkit.createText( client, "" , SWT.NONE );     //$NON-NLS-1$
     
     
     this.posixApplicationTypeAdapter
                                .attachPosixApplicationOutput( this.txtOutput );
     
     this.txtOutput.setLayoutData( td );     
    
    
    /* ============================== Error File ============================ */
     
     this.lblError = this.toolkit.createLabel( client,
                                      Messages.getString( "OverviewPage_Error" ) ); //$NON-NLS-1$
                                                               
     td = new TableWrapData( TableWrapData.FILL_GRAB );
     td.maxWidth = OverviewPage.WIDGET_MAX_WIDTH;
                                            
     this.txtError = this.toolkit.createText( client,"", SWT.NONE );     //$NON-NLS-1$
     
     this.posixApplicationTypeAdapter
                                  .attachPosixApplicationError( this.txtError );
     
     this.txtError.setLayoutData( td );          
    
    //Paint Flat Borders
    
    this.toolkit.paintBordersFor( client );
    
    return client;
    
  } //End Composite createGeneralInfoSection()
  
  
  
  private Composite createJobDefSection( final IManagedForm mform, 
                                         final String title, 
                                         final String desc ) {

  Composite client = createSection( mform, title, desc, 2 );
  
   this.sectionContent = this.toolkit.createFormText( client, true );
    try {
      this.sectionContent.setText(Messages.getString( "OverviewPage_JobDefContent" ), true, false); //$NON-NLS-1$
    } catch ( SWTException e ) {
      this.sectionContent.setText( e.getMessage(), false, false );
    }
    
    this.sectionContent.addHyperlinkListener( this );

  return client;

  } //End Composite createJobDefSection()
  
  
  
  private Composite createApplicationSection( final IManagedForm mform, 
                                              final String title, 
                                              final String desc ) {
    
    Composite client = createSection( mform, title, desc, 2 );
    
    this.sectionContent = this.toolkit.createFormText( client, true );
     try {
       this.sectionContent.setText(Messages.getString( "OverviewPage_ApplicationContent" ), true, false); //$NON-NLS-1$
     } catch ( SWTException e ) {
       this.sectionContent.setText( e.getMessage(),false, false );
     }
     
     this.sectionContent.addHyperlinkListener( this );
     
   return client;
    
  }// End Composite createApplicationSection()

  
  
  private Composite createResourcesSection( final IManagedForm mform, 
                                            final String title, 
                                            final String desc ) {
  
    Composite client = createSection( mform, title, desc, 2 );
    
    this.sectionContent = this.toolkit.createFormText( client, true );
     try {
       this.sectionContent.setText(Messages.getString( "OverviewPage_ResourcesContent" ), true, false); //$NON-NLS-1$
     } catch ( SWTException e ) {
       this.sectionContent.setText( e.getMessage(),false, false );
     }
     
     this.sectionContent.addHyperlinkListener( this );
     
   return client;
    
  } // End Composite createResourcesSection()
  
  
  
  private Composite createDataStageSection( final IManagedForm mform, 
                                            final String title, 
                                            final String desc ) {
  
    Composite client = createSection( mform, title, desc, 2 );
    
    this.sectionContent = this.toolkit.createFormText( client, true );
     try {
       this.sectionContent.setText( Messages.getString( "OverviewPage_DataStagingContent" ),true,false ); //$NON-NLS-1$
     } catch ( SWTException e ) {
       this.sectionContent.setText( e.getMessage(),false, false );
     }
     
     this.sectionContent.addHyperlinkListener( this );

  
  return client;
  
} // End Composite createDataStageSection()



  public void linkActivated( final HyperlinkEvent e ) {
    
    String href = ( String ) e.getHref();    
    this.getEditor().setActivePage( href );
    
  } // End void linkActiveted()



  public void linkEntered( final HyperlinkEvent e ) {
    IStatusLineManager mng = getEditor().getEditorSite().getActionBars()
                                                        .getStatusLineManager();
    mng.setMessage( e.getLabel() );
    
  }// End void linkEntered()



  public void linkExited( final HyperlinkEvent e ) {
    IStatusLineManager mng = getEditor().getEditorSite().getActionBars()
                                                        .getStatusLineManager();
    mng.setMessage( null );
    
  } // End void linkExited()
 
  
} // End OverviewPage Class
