/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initialia development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     UCY (http://www.ucy.cs.ac.cy)
 *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.jsdl.ui.internal.pages;

import java.net.URL;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.TableWrapData;
import eu.geclipse.jsdl.model.base.JobDefinitionType;
import eu.geclipse.jsdl.ui.adapters.jsdl.DataStageTypeAdapter;
import eu.geclipse.jsdl.ui.internal.Activator;
import eu.geclipse.jsdl.ui.internal.pages.sections.DataStageInSection;
import eu.geclipse.jsdl.ui.internal.pages.sections.DataStageOutSection;

/**
 * This class provides the Data Staging page that appears in the JSDL editor.
 * It provides a graphical user interface to the DataStage elements of a JSDL
 * document. 
 */
public class DataStagingPage extends FormPage implements INotifyChangedListener {

  protected static final String PAGE_ID = "DATA_STAGING"; //$NON-NLS-1$
  protected Composite body = null;
  protected Composite dataStageSection = null;
  protected JobDefinitionType jobDefinitionType = null;
    
  private ImageDescriptor helpDesc = null;
  private boolean contentRefreshed = false;
  private boolean dirtyFlag = false;
  private DataStageInSection dataStageInSection;
  private DataStageOutSection dataStageOutSection;
  
  
  
  /**
   * <code>DataStagingPage</code> class constructor. Creates the page by
   * passing as an argument the container JSDL editor.
   * <p>
   * - FileName
   * <p>
   * - FileSystemName
   * <p>
   * - Source/Target Location
   * <p>
   * - CreationFlag
   * <p>
   * - DeleteOnTermination
   * <p>
   * 
   * @param editor The parent Jsdl Editor .
   *  
   */
  public DataStagingPage( final FormEditor editor ) {
   
   super( editor, PAGE_ID , Messages.getString("DataStagingPage_PageTitle") ); //$NON-NLS-1$
  
   }
   
  
  public void notifyChanged( final Notification arg0 ) {
    setDirty( true );
  }
    
  
  /**
   * This method set's the dirty status of the page.
   * 
   * @param dirty TRUE when the page is Dirty (content has been changed) and hence a 
   * Save operation is needed.
   * 
   */
  public void setDirty( final boolean dirty ) {
    
    if ( this.dirtyFlag != dirty ) {
      this.dirtyFlag = dirty;     
      this.getEditor().editorDirtyStateChanged();  
    }
    
  }
    
  
  @Override
  public boolean isDirty() {
    
    return this.dirtyFlag;
    
  }  
  
    
  @Override
  public void setActive( final boolean active ) {
    
    if ( active ){
      if ( isContentRefreshed() ) {
        this.dataStageInSection.setInput( this.jobDefinitionType );
        this.dataStageOutSection.setInput( this.jobDefinitionType );
      }//end_if isContentRefreshed()
    } // end_if active
    
  } //End void setActive()
  
  
  
  private boolean isContentRefreshed() {
    
    return this.contentRefreshed;
    
  } //End boolean isContentRefreshed()
  
  
  
  @Override
  protected void createFormContent( final IManagedForm managedForm ) {
    
    
    ScrolledForm form = managedForm.getForm();
    FormToolkit toolkit = managedForm.getToolkit();
    
    form.setText( Messages.getString( "DataStagingPage_DataStagingPageTitle" ) );  //$NON-NLS-1$
    
    this.body = form.getBody();
    this.body.setLayout( FormLayoutFactory.createFormTableWrapLayout( false, 2) );
    
    
    this.dataStageSection = toolkit.createComposite( this.body );
    this.dataStageSection.setLayout( FormLayoutFactory.createFormPaneTableWrapLayout( false, 1) );
    this.dataStageSection.setLayoutData( new TableWrapData(TableWrapData.FILL_GRAB) );

    /* Create the Stage-In Section */
    this.dataStageInSection = new DataStageInSection( this.dataStageSection, toolkit );
    this.dataStageInSection.setInput( this.jobDefinitionType );
    this.dataStageInSection.addListener( this );
    

    /* Create the Stage-Out Section */
    this.dataStageOutSection = new DataStageOutSection( this.dataStageSection, toolkit );
    this.dataStageOutSection.setInput( this.jobDefinitionType );
    this.dataStageOutSection.addListener( this );
    
    /* Also add the help system */
    addFormPageHelp( form );
    
  }
  
 
  /**
   * Method that set's the DataStage Page content. The content is the root 
   * JSDL element. Also this method is responsible to initialize the associated 
   * type adapters for the elements of this page.  This method must be called only
   * from the JSDL Editor.
   * 
   * Associated Type Adapters for this page are: 
   * @see DataStageTypeAdapter
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

    if( refreshStatus ) {
      this.contentRefreshed = true;
      this.jobDefinitionType = jobDefinitionRoot;
    }
    this.jobDefinitionType = jobDefinitionRoot;      
         
  } // End void getPageContent()

    
  private void addFormPageHelp( final ScrolledForm form ) {
    
    final String href = getHelpResource();
    if ( href != null ) {
        IToolBarManager manager = form.getToolBarManager();
        Action helpAction = new Action( "help" ) { //$NON-NLS-1$
            @Override
            public void run() {
                BusyIndicator.showWhile(form.getDisplay(), new Runnable() {
                    public void run() {
                        PlatformUI.getWorkbench().getHelpSystem().displayHelpResource( href );
                    }
                });
            }
        };
        helpAction.setToolTipText( Messages.getString( "DataStagingPage_Help" ) );  //$NON-NLS-1$
        URL stageInURL = Activator.getDefault().getBundle().getEntry( "icons/help.gif" ); //$NON-NLS-1$       
        this.helpDesc = ImageDescriptor.createFromURL( stageInURL ) ;   
        helpAction.setImageDescriptor( this.helpDesc );
        manager.add( helpAction );
        form.updateToolBar();
    }
    
  }
  
    
  protected String getHelpResource() {
    return "/eu.geclipse.doc.user/html/concepts/jobmanagement/editorpages/datastaging.html"; //$NON-NLS-1$
  }
  
  
} // End Class

