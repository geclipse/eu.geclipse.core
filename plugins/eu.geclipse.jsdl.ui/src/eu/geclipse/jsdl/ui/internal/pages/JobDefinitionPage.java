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


import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.TableWrapData;

import eu.geclipse.jsdl.model.base.JobDefinitionType;
import eu.geclipse.jsdl.model.base.JobIdentificationType;
import eu.geclipse.jsdl.ui.adapters.jsdl.JobDefinitionTypeAdapter;
import eu.geclipse.jsdl.ui.adapters.jsdl.JobIdentificationTypeAdapter;
import eu.geclipse.jsdl.ui.editors.JsdlEditor;
import eu.geclipse.jsdl.ui.internal.pages.sections.JobDefinitionSection;
import eu.geclipse.jsdl.ui.internal.pages.sections.JobIdentificationSection;

/**
 * This class provides the Job Definition page that appears in the JSDL editor.
 * It provides a graphical user interface to the following elements of a JSDL 
 * document:
 * 
 * - Job Definition ID
 * - Job Name
 * - Job Description
 * - Job Annotation
 * - Job Project
 * 
 */
public final class JobDefinitionPage extends JsdlFormPage {
  
  protected static final String PAGE_ID = "JOB_DEFINITION";  //$NON-NLS-1$

  protected Object value = null;    
  protected Composite body = null;
  protected Composite jobDefComposite = null;
  protected Composite jobIdentComposite = null;
  protected JobDefinitionType jobDefinitionType;
  protected JobIdentificationType jobIdentificationType;
    
  private JobDefinitionSection jobDefinitionSection = null;
  private JobIdentificationSection jobIdentificationSection = null;
   
  
  
  /**
   * <code>JobDefinitionPage</code> class constructor. Creates the page by
   * passing as an argument the container JSDL editor.
   * 
   * @param editor The JSDL editor.
   */
  public JobDefinitionPage( final JsdlEditor editor) { 
    
    super( editor, PAGE_ID, 
           Messages.getString("JobDefinitionPage_JobDefinitionTitle") ); //$NON-NLS-1$   
    
  }
  
    
  @Override
  public void setActive( final boolean active ) {
    
    if ( active ) {
      if ( isContentRefreshed() ) {
        this.jobDefinitionSection.setInput( this.jobDefinitionType );
        this.jobIdentificationSection.setInput( this.jobDefinitionType );
      } // end_if isContentRefreshed
      
    } // end_if active
    
  } // end void setActive()
 
  
  @Override
  public void dispose() {
    
    super.dispose();
    this.jobDefinitionType = null;
    this.jobIdentificationType = null;
    
  }
    
  
  /**
   * Method that set's the JobDefinition Page content. The content is the root 
   * JSDL element. This method is also responsible to initialize the associated 
   * type adapters for the elements of this page. This method must be called only
   * from the JSDL Editor.
   * 
   * Associated Type Adapters for this page are: 
   * @see JobDefinitionTypeAdapter
   * @see JobIdentificationTypeAdapter
   *  
   * @param jobDefinitionRoot The root element ({@link JobDefinitionType}) of a JSDL #
   * document.
   * 
   * @param refreshStatus Set to TRUE if the original page content is already set,
   * but there is a need to refresh the page because there was a change to its content
   * from an outside editor.
   * 
   */  
  public void setPageContent( final JobDefinitionType jobDefinitionRoot, final boolean refreshStatus ) {
      
   if ( refreshStatus ) {
      this.contentRefreshed = true;
   }
   
   this.jobDefinitionType = jobDefinitionRoot;     
         
  } // End void setPageContent()   
  
  
  /* This method is used to create the Forms content by
  * creating the form layout and then creating the form
  * sub sections.
  */
  @Override
  protected void createFormContent( final IManagedForm managedForm ) {    
        
    ScrolledForm form = managedForm.getForm();
    FormToolkit toolkit = managedForm.getToolkit();
    
    form.setText( Messages.getString( "JobDefinitionPage_JobDefinitionPageTitle" ) );  //$NON-NLS-1$
    this.body = form.getBody();
    this.body.setLayout( FormLayoutFactory.createFormTableWrapLayout( true, 1 ));
        

    this.jobDefComposite = toolkit.createComposite( this.body );
    this.jobDefComposite.setLayout( FormLayoutFactory.createFormPaneTableWrapLayout( false, 1 ) );
    this.jobDefComposite.setLayoutData( new TableWrapData(TableWrapData.FILL_GRAB ) );
    
    /* Create Job Definition Section */
    this.jobDefinitionSection = new JobDefinitionSection( this.jobDefComposite, toolkit );
    this.jobDefinitionSection.setInput( this.jobDefinitionType );
    this.jobDefinitionSection.addListener( this );
    
    this.jobIdentComposite = toolkit.createComposite( this.body );
    this.jobIdentComposite.setLayout( FormLayoutFactory.createFormPaneTableWrapLayout( false, 1 ) );
    this.jobIdentComposite.setLayoutData( new TableWrapData(TableWrapData.FILL_GRAB ) );
    
    /* Create Job Identification Section */
    this.jobIdentificationSection = new JobIdentificationSection( this, this.jobIdentComposite, toolkit );
    this.jobIdentificationSection.setInput( this.jobDefinitionType );
    this.jobIdentificationSection.addListener( this );  
    
    /* Also add the help system */
    addFormPageHelp( form );

  }

  
  @Override
  protected String getHelpResource() {
    return "/eu.geclipse.doc.user/html/concepts/jobmanagement/editorpages/jobdefinition.html"; //$NON-NLS-1$
  }
   

 } // End Class JobDefinitionPage
  