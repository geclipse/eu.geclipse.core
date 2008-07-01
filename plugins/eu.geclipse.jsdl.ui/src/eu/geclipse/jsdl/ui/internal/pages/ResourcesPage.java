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


import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.TableWrapData;

import eu.geclipse.jsdl.model.base.JobDefinitionType;
import eu.geclipse.jsdl.ui.adapters.jsdl.JobDefinitionTypeAdapter;
import eu.geclipse.jsdl.ui.adapters.jsdl.JobIdentificationTypeAdapter;
import eu.geclipse.jsdl.ui.adapters.jsdl.ResourcesTypeAdapter;
import eu.geclipse.jsdl.ui.editors.JsdlEditor;
import eu.geclipse.jsdl.ui.internal.Activator;
import eu.geclipse.jsdl.ui.internal.pages.sections.AdditionalResourceElemetsSection;
import eu.geclipse.jsdl.ui.internal.pages.sections.CandidateHostsSection;
import eu.geclipse.jsdl.ui.internal.pages.sections.CpuArchitectureSection;
import eu.geclipse.jsdl.ui.internal.pages.sections.ExclusiveExecutionSection;
import eu.geclipse.jsdl.ui.internal.pages.sections.FileSystemSection;
import eu.geclipse.jsdl.ui.internal.pages.sections.OperatingSystemSection;


/**
 * This class provides the Resources page that appears in the JSDL editor.
 */
public final class ResourcesPage extends JsdlFormPage {
  
   
  protected static final String PAGE_ID = "RESOURCES";  //$NON-NLS-1$
  
  protected JobDefinitionType jobDefinitionType;
  protected ResourcesTypeAdapter resourcesTypeAdapter;
  protected Object[] value = null;  
  protected Composite body = null;
  protected Composite jobRescComposite = null;
  protected Composite left = null;
  protected Composite right = null; 
  
  private AdditionalResourceElemetsSection additionalResourceElemetsSection = null;
  private CandidateHostsSection candidateHostsSection = null;
  private OperatingSystemSection operatingSystemSection = null;
  private CpuArchitectureSection cpuArchitectureSection = null;
  private FileSystemSection fileSystemSection = null;
  private ExclusiveExecutionSection exclusiveExecutionSection = null;
  
  
  /**
   * <code>ResourcesPage</code> class constructor. Creates the page by
   * passing as an argument the container JSDL editor.
   * 
   * @param editor The JSDL editor.
   */
  public ResourcesPage( final FormEditor editor ) {
   
    super( editor, PAGE_ID ,
           Messages.getString("ResourcesPage_PageTitle") ); //$NON-NLS-1$
        
  } // End Class Constructor.
    
  
  /**
   * Returns the instance of the JSDL Editor that contains this page  
   *
   * @return JSDL editor that contains this page. 
   */
  public JsdlEditor getParentEditor() {
    return ( JsdlEditor )getEditor();
  }
  
  
  /*
   * (non-Javadoc)
   * @see org.eclipse.ui.forms.editor.FormPage#setActive(boolean)
   */
  @Override
  public void setActive(final boolean active) {
    
    if ( active ){
      if ( isContentRefreshed() ) {    
        this.candidateHostsSection.setInput( this.jobDefinitionType );
        this.additionalResourceElemetsSection.setInput( this.jobDefinitionType );
        this.operatingSystemSection.setInput( this.jobDefinitionType );
        this.cpuArchitectureSection.setInput( this.jobDefinitionType );
        this.fileSystemSection.setInput( this.jobDefinitionType );
        this.exclusiveExecutionSection.setInput( this.jobDefinitionType );
        
      } // end_if (isContentRefreshed())
    } //  end_if (active)
    
  } // End void setActive()
  
  
  /**
   * Method that set's the Resources Page content. The content is the root 
   * JSDL element. Also this method is responsible to initialize the associated 
   * type adapters for the elements of this page. This method must be called only
   * from the JSDL Editor.
   * 
   * Associated Type Adapters for this page are: 
   * @see JobDefinitionTypeAdapter
   * @see JobIdentificationTypeAdapter
   *  
   * @param jobDefinitionRoot
   * 
   * @param refreshStatus
   * Set to TRUE if the original page content is already set, but there is a need
   * to refresh the page because there was a change to this content
   *  from an outside editor.
   * 
   */
  public void setPageContent( final JobDefinitionType jobDefinitionRoot, 
                              final boolean refreshStatus ){

   if ( refreshStatus ) {
      this.contentRefreshed = true;
   }
   this.jobDefinitionType = jobDefinitionRoot;   
          
  } // End void getPageContent() 

 
  @Override
  /*
   * This method is used to create the Forms content by
   * creating the form layout and then creating the form
   * Sub-Sections
   */
  protected void createFormContent( final IManagedForm managedForm ) {
            
    ScrolledForm form = managedForm.getForm();    
    FormToolkit toolkit = managedForm.getToolkit();
    
    form.setText( Messages.getString( "ResourcesPage_ResourcePageTitle") );  //$NON-NLS-1$
    
    this.body = form.getBody();
    this.body.setLayout( FormLayoutFactory.createFormTableWrapLayout( false, 2 ) );
  
  
    
    this.left = toolkit.createComposite( this.body );
    this.left.setLayout( FormLayoutFactory.createFormPaneTableWrapLayout (false, 1) );
    this.left.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
    
    this.right = toolkit.createComposite( this.body );
    this.right.setLayout( FormLayoutFactory.createFormPaneTableWrapLayout( false, 1 ) );
    this.right.setLayoutData( new TableWrapData( TableWrapData.FILL_GRAB ) );
    
    /* Create the Candidate Hosts Section */
    this.candidateHostsSection = new CandidateHostsSection(this, this.left, toolkit);
    this.candidateHostsSection.setInput( this.jobDefinitionType );
    this.candidateHostsSection.addListener( this );

    /* Create the Operating System Section */
    this.operatingSystemSection = new OperatingSystemSection(this.left, toolkit);
    this.operatingSystemSection.setInput( this.jobDefinitionType );
    this.operatingSystemSection.addListener( this );

    /* Create the File System Section */    
    this.fileSystemSection = new FileSystemSection(this.left, toolkit);
    this.fileSystemSection.setInput( this.jobDefinitionType );
    this.fileSystemSection.addListener( this );
   
    /* Create the CPU Architecture Section */
    this.cpuArchitectureSection = new CpuArchitectureSection(this.right, toolkit);
    this.cpuArchitectureSection.setInput( this.jobDefinitionType );
    this.cpuArchitectureSection.addListener( this );

    /* Create the Exclusive Execution Section */
    this.exclusiveExecutionSection = new ExclusiveExecutionSection(this.right, toolkit);
    this.exclusiveExecutionSection.setInput( this.jobDefinitionType );
    this.exclusiveExecutionSection.addListener( this );
    
    /* Create the Additional Elements Section */
    this.additionalResourceElemetsSection = new AdditionalResourceElemetsSection(this, this.right, toolkit);
    this.additionalResourceElemetsSection.setInput( this.jobDefinitionType );
    this.additionalResourceElemetsSection.addListener( this );
   
   /* Set Form Background */
   form.setBackgroundImage( Activator.getDefault().
                            getImageRegistry().get( "formsbackground" ) ); //$NON-NLS-1$

   /* Also add the help system */
   addFormPageHelp( form );
   
  }
  
  
  @Override
  protected String getHelpResource() {
    return "/eu.geclipse.doc.user/html/concepts/jobmanagement/editorpages/resources.html"; //$NON-NLS-1$
  }
  
  
} // end ResourcesPage class
