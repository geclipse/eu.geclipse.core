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
package eu.geclipse.jsdl.ui.internal.pages;

/**
 * @author nloulloud
 */
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.TableWrapData;

import eu.geclipse.jsdl.model.base.JobDefinitionType;
import eu.geclipse.jsdl.ui.adapters.jsdl.ApplicationTypeAdapter;
import eu.geclipse.jsdl.ui.adapters.posix.PosixApplicationTypeAdapter;
import eu.geclipse.jsdl.ui.internal.pages.sections.AdditionalPosixElementSection;
import eu.geclipse.jsdl.ui.internal.pages.sections.ApplicationSection;
import eu.geclipse.jsdl.ui.internal.pages.sections.PosixApplicationSection;

/**
 * This class provides the Job Application page that appears in the JSDL editor.
 * It provides a graphical user interface to the generic and POSIX elements of a JSDL
 * document. 
 */
public final class JobApplicationPage extends JsdlFormPage  {

  protected static final String PAGE_ID = "APPLICATION";   //$NON-NLS-1$
  protected Composite body = null;
  protected Composite applicationSectionComposit = null;
  protected Composite pS = null;
  protected Composite apS = null;
  protected JobDefinitionType jobDefinitionType = null;
  protected Object value = null;  
  private ApplicationSection applicationSection = null;
  private PosixApplicationSection posixApplicationSection = null;
  private AdditionalPosixElementSection additionalPosixElementSection = null;
  /**
   * <code>JobApplicationPage</code> class constructor. Creates the page by
   * passing as an argument the container JSDL editor.
   * 
   * @param editor The JSDL editor.
   */
  public JobApplicationPage( final FormEditor editor ) {
    super( editor, PAGE_ID, Messages.getString( "JobApplicationPage_PageTitle" ) ); //$NON-NLS-1$
  } // End Class Constructor

  
  
  @Override
  public void setActive( final boolean active ) {
    if( active ) {
      if( isContentRefreshed() ) {
        this.applicationSection.setInput( this.jobDefinitionType );
        this.posixApplicationSection.setInput( this.jobDefinitionType );
        this.additionalPosixElementSection.setInput( this.jobDefinitionType );        
      }// end_if isContentRefreshed
    } // end_if active
  } // End void setActive()

  
  
  @Override
  public void dispose() {
    
    super.dispose();
    this.jobDefinitionType = null;      
    
  }
  
  
  /**
   * Method that set's the JobApplication Page content. The content is the root
   * JSDL element. Also this method is responsible to initialize the associated
   * type adapters for the elements of this page. This method must be called
   * only from the JSDL Editor. Associated Type Adapters for this page are:
   * 
   * @see ApplicationTypeAdapter
   * @see PosixApplicationTypeAdapter
   * @param jobDefinitionRoot
   * @param refreshStatus Set to TRUE if the original page content is already
   *            set, but there is a need to refresh the page because there was a
   *            change to this content from an outside editor.
   */
  public void setPageContent( final JobDefinitionType jobDefinitionRoot,
                              final boolean refreshStatus )
  {
    if( refreshStatus ) {
      this.contentRefreshed = true;
      this.jobDefinitionType = jobDefinitionRoot;
    }
    this.jobDefinitionType = jobDefinitionRoot;      
  } // End void getPageContent()

  
  
  /*
   * This method is used to create the Forms content by creating the form layout
   * and then creating the form sub sections.
   */
  @Override
  protected void createFormContent( final IManagedForm managedForm ) {
    
    ScrolledForm form = managedForm.getForm();
    FormToolkit toolkit = managedForm.getToolkit();
    
    form.setText( Messages.getString( "JobApplicationPage_ApplicationTitle" ) ); //$NON-NLS-1$
    this.body = form.getBody();
    this.body.setLayout( FormLayoutFactory.createFormTableWrapLayout( false, 2 ) );
    
    this.applicationSectionComposit = toolkit.createComposite( this.body );
    this.applicationSectionComposit.setLayout( FormLayoutFactory.createFormPaneTableWrapLayout( false, 1 ) );
    this.applicationSectionComposit.setLayoutData( new TableWrapData( TableWrapData.FILL_GRAB ) );
    
    this.applicationSection = new ApplicationSection( this.applicationSectionComposit, toolkit);
    this.applicationSection.setInput( this.jobDefinitionType );
    this.applicationSection.addListener( this );
    
    this.apS = toolkit.createComposite( this.body );
    this.apS.setLayout( FormLayoutFactory.createFormPaneTableWrapLayout( false, 1 ) );
    this.apS.setLayoutData( new TableWrapData( TableWrapData.FILL_GRAB ) );

    this.additionalPosixElementSection = new AdditionalPosixElementSection(this, this.apS, toolkit);
    this.additionalPosixElementSection.setInput( this.jobDefinitionType );
    this.additionalPosixElementSection.addListener( this );
    
    this.posixApplicationSection = new PosixApplicationSection( this.applicationSectionComposit, toolkit);
    this.posixApplicationSection.setInput( this.jobDefinitionType );
    this.posixApplicationSection.addListener( this );
    
    /* Also add the help system */
    addFormPageHelp( form );
    
  }

  
  @Override
  protected String getHelpResource() {
    return "/eu.geclipse.doc.user/html/concepts/jobmanagement/editorpages/application.html"; //$NON-NLS-1$
  }
  
  
} // End JobApplicationPage Class
