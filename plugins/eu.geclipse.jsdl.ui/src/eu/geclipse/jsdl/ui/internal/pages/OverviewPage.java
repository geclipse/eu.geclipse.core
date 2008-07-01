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
import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.TableWrapData;

import eu.geclipse.jsdl.model.base.JobDefinitionType;
import eu.geclipse.jsdl.ui.editors.JsdlEditor;
import eu.geclipse.jsdl.ui.internal.pages.sections.GeneralInfoSection;

/**
 * 
 * This class provides the Overview page that appears in the JSDL editor.
 *  
 */ 
public final class OverviewPage extends JsdlFormPage implements IHyperlinkListener{
  
  protected static final String PAGE_ID = "OVERVIEW";  //$NON-NLS-1$
  
  protected Composite body = null;
  protected JobDefinitionType jobDefinitionType = null;
  protected Composite generalInfoSectionComposite = null;
  protected Composite jobDefSectionComposite = null;
  protected Composite appSectionComposite = null;
  protected Composite resourcesSectionComposite = null;
  protected Composite dataStageSectionComposite = null;
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
  protected GeneralInfoSection generalInfoSection = null;
  
  /**
   * OverviewPage class constructor.
   * @param editor The JSDL Editor in which this page will be contained.
   */
  public OverviewPage(final JsdlEditor editor){
    
    super( editor, PAGE_ID, Messages.getString("OverviewPage_PageTitle")); //$NON-NLS-1$
         
    
  } //End Class Constructor
  
  
  @Override
  public void setActive( final boolean active ) {
    if( active ) {
      if( isContentRefreshed() ) {
        this.generalInfoSection.setInput( this.jobDefinitionType );        
      }// end_if isContentRefreshed
    } // end_if active
  } // End void setActive()
  
  
  @Override
  protected void createFormContent( final IManagedForm managedForm ){
  
    final ScrolledForm form = managedForm.getForm();
    FormToolkit toolkit = managedForm.getToolkit();
    form.setText( Messages.getString("OverviewPage_PageTitle") );  //$NON-NLS-1$
    
    this.body = form.getBody();
    this.body.setLayout( FormLayoutFactory.createFormTableWrapLayout( false, 2 ) );
    
    this.generalInfoSectionComposite = toolkit.createComposite( this.body );
    this.generalInfoSectionComposite.setLayout( FormLayoutFactory.createFormPaneTableWrapLayout( false, 1 ) );
    this.generalInfoSectionComposite.setLayoutData( new TableWrapData( TableWrapData.FILL_GRAB ) );
    
    this.generalInfoSection = new GeneralInfoSection( this.generalInfoSectionComposite, toolkit);
    this.generalInfoSection.setInput( this.jobDefinitionType );
    this.generalInfoSection.addListener( this );

   
    this.jobDefSectionComposite = toolkit.createComposite( this.body );
    this.jobDefSectionComposite.setLayout( FormLayoutFactory.createFormPaneTableWrapLayout( false, 1 ) );
    this.jobDefSectionComposite.setLayoutData( new TableWrapData( TableWrapData.FILL_GRAB ) );
    
    this.jobDefSectionComposite = createJobDefSection( this.jobDefSectionComposite, toolkit ); 

    this.appSectionComposite = toolkit.createComposite( this.body );
    this.appSectionComposite.setLayout( FormLayoutFactory.createFormPaneTableWrapLayout( false, 1 ) );
    this.appSectionComposite.setLayoutData( new TableWrapData( TableWrapData.FILL_GRAB ) );
    
    this.appSectionComposite = createApplicationSection(this.appSectionComposite, toolkit);
    
    this.resourcesSectionComposite = toolkit.createComposite( this.body );
    this.resourcesSectionComposite.setLayout( FormLayoutFactory.createFormPaneTableWrapLayout( false, 1 ) );
    this.resourcesSectionComposite.setLayoutData( new TableWrapData( TableWrapData.FILL_GRAB ) );

    this.resourcesSectionComposite = createResourcesSection(this.resourcesSectionComposite, toolkit); 
    
    this.dataStageSectionComposite = toolkit.createComposite( this.body );
    this.dataStageSectionComposite.setLayout( FormLayoutFactory.createFormPaneTableWrapLayout( false, 1 ) );
    this.dataStageSectionComposite.setLayoutData( new TableWrapData( TableWrapData.FILL_GRAB ) );

    this.dataStageSectionComposite = createDataStageSection(this.dataStageSectionComposite, toolkit);
    
    addFormPageHelp( form );
   
  }
  
  @Override
  protected String getHelpResource() {
    return "/eu.geclipse.doc.user/html/concepts/jobmanagement/editingJSDL.html"; //$NON-NLS-1$
  }
  
  
  private Composite createJobDefSection( final Composite parentComposite, final FormToolkit toolkit ) {
  
  String sectionTitle =  Messages.getString("OverviewPage_JobDefTitle");  //$NON-NLS-1$
  String sectionDescription = Messages.getString("OverviewPage_JobDefDescription");   //$NON-NLS-1$

  Composite client = FormSectionFactory.createStaticSection( toolkit, parentComposite, sectionTitle, 
                                                             sectionDescription, 2 );
  
   this.sectionContent = toolkit.createFormText( client, true );
    try {
      this.sectionContent.setText(Messages.getString( "OverviewPage_JobDefContent" ), true, false); //$NON-NLS-1$
    } catch ( SWTException e ) {
      this.sectionContent.setText( e.getMessage(), false, false );
    }
    
    this.sectionContent.addHyperlinkListener( this );

  return client;

  } //End Composite createJobDefSection()
   
  
  private Composite createApplicationSection( final Composite parentComposite, final FormToolkit toolkit ) {
   
    String sectionTitle =  Messages.getString("OverviewPage_ApplicationTitle");  //$NON-NLS-1$
    String sectionDescription = Messages.getString("OverviewPage_ApplicationDescription");   //$NON-NLS-1$
    
    Composite client = FormSectionFactory.createStaticSection( toolkit, parentComposite, sectionTitle, 
                                                               sectionDescription, 2 );
    
    this.sectionContent = toolkit.createFormText( client, true );
     try {
       this.sectionContent.setText(Messages.getString( "OverviewPage_ApplicationContent" ), true, false); //$NON-NLS-1$
     } catch ( SWTException e ) {
       this.sectionContent.setText( e.getMessage(),false, false );
     }
     
     this.sectionContent.addHyperlinkListener( this );
     
   return client;
    
  }// End Composite createApplicationSection()

  
  private Composite createResourcesSection( final Composite parentComposite, final FormToolkit toolkit  ) {
    
    String sectionTitle =  Messages.getString("OverviewPage_ResourcesTitle");  //$NON-NLS-1$
    String sectionDescription = Messages.getString("OverviewPage_ResourcesDescription");   //$NON-NLS-1$
    
    Composite client = FormSectionFactory.createStaticSection( toolkit, parentComposite, sectionTitle, 
                                                               sectionDescription, 2 );
    
    this.sectionContent = toolkit.createFormText( client, true );
     try {
       this.sectionContent.setText(Messages.getString( "OverviewPage_ResourcesContent" ), true, false); //$NON-NLS-1$
     } catch ( SWTException e ) {
       this.sectionContent.setText( e.getMessage(),false, false );
     }
     
     this.sectionContent.addHyperlinkListener( this );
     
   return client;
    
  } // End Composite createResourcesSection()
   
  
  private Composite createDataStageSection( final Composite parentComposite, final FormToolkit toolkit ) {
  
    String sectionTitle =  Messages.getString("OverviewPage_DataStagingTitle");  //$NON-NLS-1$
    String sectionDescription = Messages.getString("OverviewPage_DataStagingDescription");   //$NON-NLS-1$
        
    Composite client = FormSectionFactory.createStaticSection( toolkit, parentComposite, sectionTitle, 
                                                               sectionDescription, 2 );
    
    this.sectionContent = toolkit.createFormText( client, true );
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
    
    IStatusLineManager mng = getEditor().getEditorSite().getActionBars().getStatusLineManager();
    mng.setMessage( e.getLabel() );
    
  }// End void linkEntered()

  
  public void linkExited( final HyperlinkEvent e ) {
    
    IStatusLineManager mng = getEditor().getEditorSite().getActionBars().getStatusLineManager();
    mng.setMessage( null );
    
  } // End void linkExited()
  
  
  @Override
  public void dispose() {
    
    super.dispose();
    this.jobDefinitionType = null;      
    
  }
  
  
  public void setPageContent( final JobDefinitionType jobDefinitionRoot, final boolean refreshStatus ) {
    
    if( refreshStatus ) {
      this.contentRefreshed = true;
      this.jobDefinitionType = jobDefinitionRoot;
    }
    this.jobDefinitionType = jobDefinitionRoot;
    
  } // End void getPageContent()
 
  
} // End OverviewPage Class
