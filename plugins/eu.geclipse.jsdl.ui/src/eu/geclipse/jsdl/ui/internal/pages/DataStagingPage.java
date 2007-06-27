/******************************************************************************
  * All rights reserved. This program and the accompanying materials
  * are made available under the terms of the Eclipse Public License v1.0
  * which accompanies this distribution, and is available at
  * http://www.eclipse.org/legal/epl-v10.html
  *
  * Initialial development of the original code was made for
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
 * @author nicholas
 *
 */

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.ColumnLayout;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import eu.geclipse.jsdl.jsdl.DataStageTypeAdapter;
import eu.geclipse.jsdl.ui.internal.Activator;

/**
 * This class provides the DataStaging Page page that appears in the JSDL editor.
 * It provides a graphical user interface to the following elements of a JSDL 
 * document:
 * 
 * 
 */
public final class DataStagingPage extends FormPage 
                                              implements INotifyChangedListener{
  
  protected Composite jobDataStaging = null;
  
  protected List lstFileName = null;
  protected Text txtFileSystemName = null;
  protected Text txtSource = null;
  protected Text txtTarget = null;
  protected Text txtName = null;
   
  protected Label lblFileName = null;
  protected Label lblFileSystemName = null;
  protected Label lblCreationFlag = null;
  protected Label lblDelOnTerm = null;
  protected Label lblSource = null;
  protected Label lblTarget = null;
  protected Label lblName = null;
  
  protected Button btnAdd = null;
  protected Button btnDel = null;
  
  protected Combo cmbCreationFlag = null;
  protected Combo cmbDelOnTerm = null;

  
  private DataStageTypeAdapter dataStageTypeAdapter;
  private boolean contentRefreshed = false;
  private boolean dirtyFlag = false;
  
  private final int WIDGET_HEIGHT = 100;
  
  
  
  /**
   * 
   * DataStagingPage class constructor. Initialiazes the DataStaging Page by 
   * passing as an argument the container JSDL editor.
   * @param editor
   */
  public DataStagingPage( final FormEditor editor )
                            
   {
    
    super(editor,Messages.getString("DataStagingPage_pageId"),  //$NON-NLS-1$
          Messages.getString("DataStagingPage_PageTitle")); //$NON-NLS-1$
   
    }
  
  
  @Override
  public boolean isDirty() {
    
    return this.dirtyFlag;
    
  }
  
  
  
  /**
   * This method set's the dirty status of the page.
   * 
   * @param dirtyFlag
   * If TRUE then the page is Dirty and a Save operation is needed.
   * 
   */
  public void setDirty(final boolean dirtyFlag) {
    if (this.dirtyFlag != dirtyFlag) {
      this.dirtyFlag = dirtyFlag;     
      this.getEditor().editorDirtyStateChanged();  
    }
  }
  
  
  
  /* This method is used to create the Forms content by
   * creating the form layout and then creating the form
   * sub sections.
   */
  @Override
  protected void createFormContent(final IManagedForm managedForm) {
    
        
    ScrolledForm form = managedForm.getForm();
    form.setText(Messages.getString("DataStagingPage_DataStagingPageTitle"));  //$NON-NLS-1$
  
    ColumnLayout layout = new ColumnLayout();
   
    layout.leftMargin = 10;
    layout.rightMargin = 10;
    /*  Set Max and Min Columns to be 1... */
    layout.maxNumColumns = 1;
    layout.minNumColumns = 1;    
    form.getBody().setLayout(layout);
      

        
   this.jobDataStaging = createDataStagingSection(managedForm,
                                Messages.getString("DataStagingPage_PageTitle"),  //$NON-NLS-1$
                        Messages.getString("DataStagingPage_DataStagingDescr"));   //$NON-NLS-1$
      
   this.dataStageTypeAdapter.load();
   
   /* Set Form Background */
   form.setBackgroundImage(Activator.getDefault().
                           getImageRegistry().get( "formsbackground" )); //$NON-NLS-1$
   
  } //End void createFormContent()
  

  @Override
  public void setActive(final boolean active) {
    
    if (active){
      if (isContentRefreshed()){
        this.dataStageTypeAdapter.load();
      }//endif isContentRefreshed
    } // endif active
    
  } //End void setActive()



  private boolean isContentRefreshed(){
    
    return this.contentRefreshed;
    
  } //End boolean isContentRefreshed()
  
  
     
  /**
   * Method that set's the DataStagingPage content. The content is the root 
   * JSDL element. Also this method is responsible to initialize the associated 
   * type adapters for the elements of this page. This method must be called only
   * from the JSDL Editor.
   * 
   * Associated Type Adapters for this page are: 
   * @see DataStageTypeAdapter 
   *  
   * @param rootJsdlElement
   * 
   * @param refreshStatus
   * Set to TRUE if the original page content is already set, but there is a need
   * to refresh the page because there was a change to this content
   *  from an outside editor.
   * 
   */
    public void setPageContent(final EObject rootJsdlElement, 
                             final boolean refreshStatus){

   if (refreshStatus) {
      this.contentRefreshed = true;
      this.dataStageTypeAdapter.setContent( rootJsdlElement );
    }
   else{     
      this.dataStageTypeAdapter = new DataStageTypeAdapter(rootJsdlElement);
      this.dataStageTypeAdapter.addListener( this );
   }
          
  } // End void getPageContent()
    
    

  /* This method is used to create individual subsections */
  
  private Composite createSection(final IManagedForm mform, final String title,
                                final String desc, final int numColumns) {
    
   final ScrolledForm form = mform.getForm();
   FormToolkit toolkit = mform.getToolkit();
       
                   
   Section section = toolkit.createSection(form.getBody(), 
                                                  ExpandableComposite.TITLE_BAR 
                                                  | Section.DESCRIPTION 
                                                  | SWT.WRAP);

   section.clientVerticalSpacing = 5;   
   section.setText(title);
   section.setDescription(desc);
   toolkit.createCompositeSeparator(section);
   
   Composite client = toolkit.createComposite(section);
          
   GridLayout layout = new GridLayout();
   layout.verticalSpacing = 8;
   layout.marginTop = 10;
   layout.marginBottom = 10;
   layout.marginWidth = 0;
   layout.marginHeight = 0;
   layout.numColumns = numColumns;
   client.setLayout(layout);
   
   section.setClient(client);
   
   toolkit.paintBordersFor( client );
   
   return client;
   
   }// End void createSection()
  
  
  
  private Composite createSubSection(final Composite composite, 
                                     final IManagedForm mform,
                                     final String title, 
                                     final String desc, 
                                     final int numColumns,
                                     final int width,
                                     final int height ) 
  {
    
    FormToolkit toolkit = mform.getToolkit();
    
    Section subSection = toolkit.createSection(composite, 
                                               ExpandableComposite.TITLE_BAR 
                                               | Section.DESCRIPTION 
                                               | SWT.WRAP);    
    subSection.setText(title);
    subSection.setDescription(desc);
    toolkit.createCompositeSeparator(subSection);
    GridData gd = new GridData();
    gd.widthHint =  width;
    gd.heightHint = height;
    subSection.setLayoutData( gd );
    
    Composite clientsubSection = toolkit.createComposite(subSection);
    GridLayout gridlayout = new GridLayout();
    gridlayout.numColumns = numColumns;
    clientsubSection.setLayout(gridlayout);
    
    
    subSection.setClient( clientsubSection );
    
    
       
          
    /* ========================== File Name Widgets ========================= */
    
    gd = new GridData();
    gd.verticalSpan = 2;
    gd.horizontalSpan = 1;
    this.lblFileName = toolkit.createLabel(clientsubSection,
                                 Messages.getString("DataStagingPage_FileName")); //$NON-NLS-1$
    
    this.lstFileName = new List(clientsubSection, SWT.NONE | SWT.V_SCROLL);
    this.lstFileName.setData( FormToolkit.KEY_DRAW_BORDER,
                                                      FormToolkit.TEXT_BORDER );
    
    this.dataStageTypeAdapter.attachToFileName( this.lstFileName );
    gd.horizontalAlignment = GridData.FILL;
    gd.verticalAlignment = GridData.FILL;
    gd.verticalSpan = 2;
    gd.horizontalSpan = 1;
    gd.widthHint = 220;
    gd.heightHint = this.WIDGET_HEIGHT;
    this.lstFileName.setLayoutData( gd );
    
    /*  Create "Add" Button */
    gd = new GridData();
    gd.horizontalSpan = 2;
    gd.verticalSpan = 1;
    gd.widthHint = 60;
    this.btnAdd = toolkit.createButton(clientsubSection,
                                     Messages.getString("JsdlEditor_AddButton"), //$NON-NLS-1$
                                     SWT.PUSH);
    this.dataStageTypeAdapter.attachToAdd( this.btnAdd );
    this.btnAdd.setLayoutData( gd );
    
    
    /* Create "Remove" Button */
    gd = new GridData();
    gd.horizontalSpan = 2;
    gd.verticalSpan = 1;
    gd.widthHint = 60;
    gd.verticalAlignment = GridData.BEGINNING;
    this.btnDel = toolkit.createButton(clientsubSection,
                                  Messages.getString("JsdlEditor_RemoveButton"), //$NON-NLS-1$
                                   SWT.PUSH);
    this.btnDel.setEnabled( false );
    this.dataStageTypeAdapter.attachToDelete(this.lstFileName, this.btnDel );
    this.btnDel.setLayoutData( gd );
    
    
    /* =================== File-System Name Widgets ========================= */

    gd = new GridData();
    this.lblFileSystemName = toolkit.createLabel(clientsubSection,
                          Messages.getString("DataStagingPage_FileSystemName")); //$NON-NLS-1$
    this.txtFileSystemName = toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.dataStageTypeAdapter.attachToFileSystemName(this.txtFileSystemName );
    gd.horizontalSpan=3;
    gd.widthHint = 300;
    this.txtFileSystemName.setLayoutData(gd);
    
    
    /* ====================== Creation Flag Widgets ========================= */
    
    this.lblCreationFlag = toolkit.createLabel(clientsubSection,
                            Messages.getString("DataStagingPage_CreationFlag")); //$NON-NLS-1$
    
    this.cmbCreationFlag = new Combo(clientsubSection,  SWT.SIMPLE |
                                                     SWT.DROP_DOWN | 
                                                     SWT.READ_ONLY);
    
    this.cmbCreationFlag.setData( FormToolkit.KEY_DRAW_BORDER );
    this.dataStageTypeAdapter.attachToCreationFlag( this.cmbCreationFlag );
    gd.horizontalSpan=3;
    gd.widthHint = 300;
    this.cmbCreationFlag.setLayoutData(gd);

    /* =================== Delete On Termination Widgets ==================== */
    
    this.lblDelOnTerm = toolkit.createLabel(clientsubSection,
                     Messages.getString("DataStagingPage_DeleteOnTermination")); //$NON-NLS-1$
    
    this.cmbDelOnTerm = new Combo(clientsubSection,  SWT.SIMPLE |
                                                  SWT.DROP_DOWN |
                                                  SWT.READ_ONLY);
    
    this.cmbDelOnTerm.setData( FormToolkit.KEY_DRAW_BORDER);    
    this.cmbDelOnTerm.add(Messages.getString("DataStagingPage_true")); //$NON-NLS-1$
    this.cmbDelOnTerm.add(Messages.getString("DataStagingPage_false")); //$NON-NLS-1$
    this.dataStageTypeAdapter.attachToDelOnTermination( this.cmbDelOnTerm );
    gd.horizontalSpan=3;
    gd.widthHint = 300;
    this.cmbDelOnTerm.setLayoutData(gd);
    
    /* ===================== Source Location Widgets ======================== */
    
    this.lblSource = toolkit.createLabel(clientsubSection,
                                  Messages.getString("DataStagingPage_Source")); //$NON-NLS-1$
    
    this.txtSource = toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.dataStageTypeAdapter.attachToSource( this.txtSource );
    gd.horizontalSpan=3;
    gd.widthHint = 300;
    this.txtSource.setLayoutData(gd);
    
    /* ====================== Target Location Widgets ======================= */
    
    this.lblTarget = toolkit.createLabel(clientsubSection,
                                  Messages.getString("DataStagingPage_Target")); //$NON-NLS-1$
    this.txtTarget = toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.dataStageTypeAdapter.attachToTarget( this.txtTarget );
    gd.horizontalSpan=3;
    gd.widthHint = 300;
    this.txtTarget.setLayoutData(gd);
    
    /* ========================== Name Widgets ============================ */
    
    this.lblName = toolkit.createLabel(clientsubSection,
                                    Messages.getString("DataStagingPage_Name")); //$NON-NLS-1$
    this.txtName= toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.dataStageTypeAdapter.attachToName( this.txtName );
    gd.horizontalSpan=3;
    gd.widthHint = 300;
    this.txtName.setLayoutData(gd);
    
    toolkit.paintBordersFor( clientsubSection );
    
    return clientsubSection;
  }
  
  

  /* 
   * Create the Data Staging Section
   * 
   */
  private Composite createDataStagingSection(final IManagedForm mform, 
                                          final String title, final String desc)
  {
   
    Composite client = createSection(mform, title, desc, 2);
    
    /* Create the Staged Files Sub-Section */
    this.jobDataStaging = createSubSection (client,mform,
                                 Messages.getString("DataStagingPage_Section"), //$NON-NLS-1$
                              Messages.getString("DataStagingPage_SectionDesc"), //$NON-NLS-1$
                              4,490,330);    
  
    
    return client;
  }



  public void notifyChanged( Notification notification ) {
    
    setDirty( true );
    
  }// End notifyChanged()


} //End Class
