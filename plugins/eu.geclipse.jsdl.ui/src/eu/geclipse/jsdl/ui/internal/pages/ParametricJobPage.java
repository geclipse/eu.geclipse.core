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
 *     PSNC: 
 *      - Katarzyna Bylec (katis@man.poznan.pl)
 *           
 *****************************************************************************/
package eu.geclipse.jsdl.ui.internal.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.TableWrapData;

import eu.geclipse.jsdl.model.base.JobDefinitionType;
import eu.geclipse.jsdl.ui.adapters.jsdl.DataStageTypeAdapter;
import eu.geclipse.jsdl.ui.adapters.jsdl.ParametricJobAdapter;

public class ParametricJobPage extends JsdlFormPage {

  protected static final String PAGE_ID = "PARAMETRIC";  //$NON-NLS-1$
  protected List<String> jsdlElementsList = new ArrayList<String>();
  protected Map<String, String> assignmentContent = new HashMap<String, String>();
  protected Map<String, String> sweepContent = new HashMap<String, String>();
  protected List<String> sweepRules = new ArrayList<String>();
  private Composite body;
  private Composite assignmentSection;
  private Composite valuesSection;
  private Composite sweepSection;
  private ParametricJobAdapter adapter;  
  private TableViewer sweepTableViewer;

  public ParametricJobPage( final FormEditor editor )  {
    
    super( editor, PAGE_ID, Messages.getString("ParametersPage_PageTitle") ); //$NON-NLS-1$
    this.sweepRules.add( "changes with" ); //$NON-NLS-1$
    this.sweepRules.add( "changes after" ); //$NON-NLS-1$
  }

  @Override
  protected void createFormContent( final IManagedForm managedForm ) {
    ScrolledForm form = managedForm.getForm();
    FormToolkit toolkit = managedForm.getToolkit();
    form.setText( "Parameters for parametric jobs" ); //$NON-NLS-1$
    this.body = form.getBody();
    this.body.setLayout( FormLayoutFactory.createFormTableWrapLayout( false, 1 ) );
    // assignment section
    this.assignmentSection = toolkit.createComposite( this.body );
    this.assignmentSection.setLayout( FormLayoutFactory.createFormPaneTableWrapLayout( false,
                                                                                       1 ) );
    this.assignmentSection.setLayoutData( new TableWrapData( TableWrapData.FILL_GRAB ) );
    createAssignmentSection( this.assignmentSection, toolkit );
    // sweep section
    this.sweepSection = toolkit.createComposite( this.body );
    this.sweepSection.setLayout( FormLayoutFactory.createFormPaneTableWrapLayout( false,
                                                                                  1 ) );
    this.sweepSection.setLayoutData( new TableWrapData( TableWrapData.FILL_GRAB ) );
    createSweepSection( this.sweepSection, toolkit );
  }

  /**
   * Method that set's the DataStage Page content. The content is the root JSDL
   * element. Also this method is responsible to initialize the associated type
   * adapters for the elements of this page. This method must be called only
   * from the JSDL Editor. Associated Type Adapters for this page are:
   * 
   * @see DataStageTypeAdapter
   * @param jobDefinitionRoot
   * @param refreshStatus Set to TRUE if the original page content is already
   *            set, but there is a need to refresh the page because there was a
   *            change to this content from an outside editor.
   */
  public void setPageContent( final JobDefinitionType jobDefinitionRoot,
                              final boolean refreshStatus )
  {
    this.adapter = new ParametricJobAdapter( jobDefinitionRoot );
    this.adapter.getElementsList();
    if( refreshStatus ) {
      this.contentRefreshed = true;
      // this.dataStageTypeAdapter.setContent( jobDefinitionRoot );
    } else {
      // this.dataStageTypeAdapter = new DataStageTypeAdapter( jobDefinitionRoot
      // );
      // this.dataStageTypeAdapter.addListener( this );
    }
  } // End void getPageContent()

  private void createAssignmentSection( final Composite parent,
                                        final FormToolkit toolkit )
  {
    Composite client = FormSectionFactory.createGridStaticSection( toolkit,
                                                                   parent,
                                                                   "Parameters assignment", //$NON-NLS-1$
                                                    "Define parameters and assign them to JSDL fields.", //$NON-NLS-1$
                                                                   3 );
    // assignment table
    Table assignmentTable = new Table( client, SWT.MULTI | SWT.FULL_SELECTION );
    TableColumn parameterColumn = new TableColumn( assignmentTable, SWT.NONE );
    parameterColumn.setText( "Parameter" ); //$NON-NLS-1$
    parameterColumn.setWidth( 150 );
    TableColumn jsdlElementsColumn = new TableColumn( assignmentTable, SWT.NONE );
    jsdlElementsColumn.setText( "JSDL elements" ); //$NON-NLS-1$
    jsdlElementsColumn.setWidth( 300 );
    TableColumn valuesColumn = new TableColumn( assignmentTable, SWT.NONE );
    valuesColumn.setText( "Values" ); //$NON-NLS-1$
    valuesColumn.setWidth( 160 );
    GridData gData = new GridData( GridData.FILL_BOTH );
    gData.grabExcessHorizontalSpace = true;
    gData.horizontalSpan = 2;
    gData.verticalSpan = 4;
    gData.heightHint = 150;
    assignmentTable.setLayoutData( gData );
    final TableViewer assignmentTableViewer = new TableViewer( assignmentTable );
    assignmentTableViewer.setContentProvider( new CProvider() );
    assignmentTableViewer.setLabelProvider( new AssignmentLabelProvider() );
    assignmentTableViewer.setInput( this.assignmentContent );
    assignmentTable.setHeaderVisible( true );
    assignmentTable.setLinesVisible( true );
    // add button
    Button assignmentAddButton = new Button( client, SWT.PUSH );
    assignmentAddButton.setText( "Add..." ); //$NON-NLS-1$
    final Shell shell = client.getShell();
    this.jsdlElementsList = this.adapter.getElementsList();
    final TableViewer tempViewer = this.sweepTableViewer; 
    assignmentAddButton.addSelectionListener( new SelectionAdapter() {
    
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        EditDialog dialog = null;
        if( ParametricJobPage.this.assignmentContent != null && ParametricJobPage.this.assignmentContent.size() > 0 ) {
          List<String> connectedToTemp = new ArrayList<String>();
          for( String connEl : ParametricJobPage.this.assignmentContent.keySet()){
            connectedToTemp.add( connEl );
          }
          dialog = new EditDialog( shell,
                                   "", //$NON-NLS-1$
                                   ParametricJobPage.this.jsdlElementsList,
                                   connectedToTemp,
                                   ParametricJobPage.this.sweepRules );
        } else {
          dialog = new EditDialog( shell,
                                   "", //$NON-NLS-1$
                                   ParametricJobPage.this.jsdlElementsList );
        }
        if( dialog.open() == Window.OK ) {
          ParametricJobPage.this.assignmentContent.put( dialog.getNameReturn(),
                                                        dialog.getElementReturn() );
          if (!"".equals(dialog.getConnectedTo()) && !"".equals(dialog.getSweepRule())){ //$NON-NLS-1$ //$NON-NLS-2$
            ParametricJobPage.this.sweepContent.put( dialog.getNameReturn(), dialog.getSweepRule() + " "  //$NON-NLS-1$
                                                     + dialog.getConnectedTo() );
            shell.getDisplay().asyncExec( new Runnable(){

              public void run() {
                ParametricJobPage.this.sweepTableViewer.refresh();
                
              }
              
            });
          }
          assignmentTableViewer.refresh();
        }
      }
    } );
    gData = new GridData();
    assignmentAddButton.setLayoutData( gData );
    Button assignmentEditButton = new Button( client, SWT.PUSH );
    assignmentEditButton.setText( "Edit..." );
    gData = new GridData();
    assignmentEditButton.setLayoutData( gData );
    Button assignmentRemoveButton = new Button( client, SWT.PUSH );
    assignmentRemoveButton.setText( "Delete" );
    gData = new GridData();
    assignmentRemoveButton.setLayoutData( gData );
    toolkit.paintBordersFor( client );
  }

  private void createSweepSection( final Composite parent,
                                   final FormToolkit toolkit )
  {
    Composite client = FormSectionFactory.createGridStaticSection( toolkit,
                                                                   parent,
                                                                   "Values assignment", //$NON-NLS-1$
                                                                   "Decide how should parameters' values change." +
                                                                   " This will result in different number of JSDL" +
                                                                   " files to submit.",
                                                                   3 );
    // assignment table
    Table sweepTable = new Table( client, SWT.MULTI | SWT.FULL_SELECTION );
    TableColumn parameterColumn = new TableColumn( sweepTable, SWT.NONE );
    parameterColumn.setText( "Parameter" ); //$NON-NLS-1$
    parameterColumn.setWidth( 150 );
    TableColumn sweepColumn = new TableColumn( sweepTable, SWT.NONE );
    sweepColumn.setText( "Sweep rule" ); //$NON-NLS-1$
    sweepColumn.setWidth( 300 );
    GridData gData = new GridData( GridData.FILL_BOTH );
    gData.grabExcessHorizontalSpace = true;
    gData.horizontalSpan = 2;
    gData.verticalSpan = 4;
    gData.heightHint = 150;
    sweepTable.setLayoutData( gData );
    this.sweepTableViewer = new TableViewer( sweepTable );
    this.sweepTableViewer.setContentProvider( new CSProvider() );
    this.sweepTableViewer.setLabelProvider( new SweepLabelProvider() );
    this.sweepTableViewer.setInput( this.sweepContent );
    sweepTable.setHeaderVisible( true );
    sweepTable.setLinesVisible( true );
    toolkit.paintBordersFor( client );
  }
  class EditDialog extends Dialog {

    private String parameterName;
    private List<String> jsdlElements;
    private Text parameterControl;
    private Combo elementsListControl;
    private String nameReturn;
    private String elementReturn;
    private Text values;
    private String valuesReturn;
    private Combo sweepConnectedToList;
    private String sweepConnectedToReturn;
    private Combo sweepRuleList;
    private String sweepRuleReturn;
    private boolean showSweep;
    private List<String> sweepConnectionInit;
    private List<String> sweepRules;

    protected EditDialog( final Shell parentShell ) {
      super( parentShell );
    }

    public String getSweepRule() {
      return this.sweepRuleReturn;
    }

    public String getConnectedTo() {
      return this.sweepConnectedToReturn;
    }

    public String getElementReturn() {
      return this.elementReturn;
    }

    public String getNameReturn() {
      return this.nameReturn;
    }

    protected EditDialog( final Shell parentShell,
                          final String parameterName,
                          final List<String> elements,
                          final List<String> sweepConnections,
                          final List<String> sweepRules )
    {
      super( parentShell );
      this.parameterName = parameterName;
      this.jsdlElements = elements;
      this.sweepConnectionInit = sweepConnections;
      this.sweepRules = sweepRules;
    }

    protected EditDialog( final Shell parentShell,
                          final String parameterName,
                          final List<String> elements )
    {
      this( parentShell, parameterName, elements, null, null );
    }

    @Override
    protected void createButtonsForButtonBar( final Composite parent ) {
      super.createButtonsForButtonBar( parent );
      updateButtons();
    }

    void updateButtons() {
      if( !this.parameterControl.getText().equals( "" ) && !this.elementsListControl.getText().equals( "" ) ) { //$NON-NLS-1$ //$NON-NLS-2$
        if (!this.sweepConnectedToList.isEnabled()){ 
        super.getButton( IDialogConstants.OK_ID ).setEnabled( true );
        this.nameReturn = this.parameterControl.getText();
        this.elementReturn = this.elementsListControl.getText();
        this.sweepConnectedToReturn = ""; //$NON-NLS-1$
        this.sweepRuleReturn = ""; //$NON-NLS-1$
        } else {
          if (!this.sweepConnectedToList.getText().equals( "" )  //$NON-NLS-1$
              && !this.sweepRuleList.getText().equals( "" )){ //$NON-NLS-1$
            super.getButton( IDialogConstants.OK_ID ).setEnabled( true );
            this.nameReturn = this.parameterControl.getText();
            this.elementReturn = this.elementsListControl.getText();
            this.sweepConnectedToReturn = this.sweepConnectedToList.getText();
            this.sweepRuleReturn = this.sweepRuleList.getText();
          } else {
            super.getButton( IDialogConstants.OK_ID ).setEnabled( false );
          }
        }
      } else {
        super.getButton( IDialogConstants.OK_ID ).setEnabled( false );
      }
    }

    @Override
    protected Control createDialogArea( final Composite parent ) {
      getShell().setText( "Define parameter" ); //$NON-NLS-1$
      Composite composite = ( Composite )super.createDialogArea( parent );
      composite.setLayout( new GridLayout( 1, false ) );
      composite.setLayoutData( new GridData( GridData.FILL_BOTH ) );
      Composite panel = new Composite( composite, SWT.NONE );
      GridData gd;
      GridLayout gLayout = new GridLayout( 3, false );
      panel.setLayout( gLayout );
      gd = new GridData( GridData.FILL_HORIZONTAL );
      panel.setLayoutData( gd );
      Label subTestNameLabel = new Label( panel, SWT.LEAD );
      subTestNameLabel.setText( "Parameter name" ); //$NON-NLS-1$
      gd = new GridData();
      subTestNameLabel.setLayoutData( gd );
      this.parameterControl = new Text( panel, SWT.BORDER );
      gd = new GridData( GridData.FILL_BOTH );
      gd.horizontalSpan = 2;
      this.parameterControl.setLayoutData( gd );
      SelectionAdapter selectionAdapter = new SelectionAdapter() {

        @Override
        public void widgetSelected( final SelectionEvent e ) {
          updateButtons();
        }
      };
      if( this.parameterName != null ) {
        this.parameterControl.setText( this.parameterName );
      }
      this.parameterControl.addModifyListener( new ModifyListener() {

        public void modifyText( final ModifyEvent e ) {
          updateButtons();
        }
      } );
      Label subTestPortLabel = new Label( panel, SWT.LEAD );
      subTestPortLabel.setText( "JSDL element" ); //$NON-NLS-1$
      gd = new GridData();
      gd.horizontalSpan = 2;
      subTestPortLabel.setLayoutData( new GridData() );
      this.elementsListControl = new Combo( panel, SWT.READ_ONLY );
      this.elementsListControl.setLayoutData( gd );
      this.elementsListControl.setItems( this.jsdlElements.toArray( new String[ 0 ] ) );
      this.elementsListControl.addSelectionListener( selectionAdapter );
      Label valuesLabel = new Label( panel, SWT.NONE );
      valuesLabel.setLayoutData( new GridData() );
      valuesLabel.setText( "Values" );
      this.values = new Text( panel, SWT.LEAD | SWT.BORDER );
      gd = new GridData( GridData.FILL_BOTH );
      gd.horizontalSpan = 2;
      this.values.setLayoutData( gd );
      Label sweepRuleLabel = new Label( panel, SWT.NONE );
      sweepRuleLabel.setLayoutData( new GridData() );
      sweepRuleLabel.setText( "Sweep rule" );
      this.sweepRuleList = new Combo( panel, SWT.READ_ONLY );
      this.sweepRuleList.setLayoutData( new GridData() );
      this.sweepConnectedToList = new Combo( panel, SWT.READ_ONLY );
      this.sweepConnectedToList.setLayoutData( new GridData() );
      if( this.sweepConnectionInit != null && this.sweepRules != null ) {
        this.sweepConnectedToList.setItems( this.sweepConnectionInit.toArray( new String[ 0 ] ) );
        this.sweepConnectedToList.addSelectionListener( selectionAdapter );
        this.sweepRuleList.setItems( this.sweepRules.toArray( new String[ 0 ] ) );
        this.sweepRuleList.addSelectionListener( selectionAdapter );
      } else {
        this.sweepConnectedToList.setEnabled( false );
        this.sweepRuleList.setEnabled( false );
      }
      return composite;
    }

    @Override
    protected void okPressed() {
      super.okPressed();
    }
  }

//  public void notifyChanged( final Notification notification ) {
//    setDirty( true );
//  }

//  /**
//   * This method set's the dirty status of the page.
//   * 
//   * @param dirty TRUE when the page is Dirty (content has been changed) and
//   *            hence a Save operation is needed.
//   */
//  public void setDirty( final boolean dirty ) {
//    if( this.dirtyFlag != dirty ) {
//      this.dirtyFlag = dirty;
//      this.getEditor().editorDirtyStateChanged();
//    }
//  }
  class CProvider implements IStructuredContentProvider {

    public Object[] getElements( final Object inputElement ) {
      Object[] result = new Object[ 0 ];
      if( inputElement instanceof Map ) {
        Map<String, String> map = ( Map<String, String> )inputElement;
        result = new Object[ map.size() ];
        for( int i = 0; i < map.size(); i++ ) {
          result[ i ] = new ArrayList<String>();
        }
        int i = 0;
        for( String key : map.keySet() ) {
          ( ( List )result[ i ] ).add( key );
          ( ( List )result[ i ] ).add( map.get( key ) );
          i++;
        }
      }
      return result;
    }

    public void dispose() {
      // TODO Auto-generated method stub
    }

    public void inputChanged( final Viewer viewer, final Object oldInput, final Object newInput )
    {
      // empty implementation
    }
  }
  class AssignmentLabelProvider implements ITableLabelProvider {

    public Image getColumnImage( final Object element, final int columnIndex ) {
      return null;
    }

    public String getColumnText( final Object element, final int columnIndex ) {
      String result = "";
      if( element instanceof List ) {
        List<String> list = ( List<String> )element;
        switch( columnIndex ) {
          case 0:
            result = list.get( 0 );
          break;
          case 1:
            result = list.get( 1 );
          break;
          default:
            result = "";
          break;
        }
      }
      return result;
    }

    public void addListener( final ILabelProviderListener listener ) {
      // TODO Auto-generated method stub
    }

    public void dispose() {
      // TODO Auto-generated method stub
    }

    public boolean isLabelProperty( final Object element,final String property ) {
      // TODO Auto-generated method stub
      return false;
    }

    public void removeListener( final ILabelProviderListener listener ) {
      // TODO Auto-generated method stub
    }
  }
  class SweepLabelProvider implements ITableLabelProvider {

    public Image getColumnImage( final Object element, final int columnIndex ) {
      return null;
    }

    public String getColumnText( final Object element, final int columnIndex ) {
      String result = "";
      if( element instanceof List ) {
        List<String> list = ( List<String> )element;
        switch( columnIndex ) {
          case 0:
            result = list.get( 0 );
          break;
          case 1:
            result = list.get( 1 );
          break;
          default:
            result = "";
          break;
        }
      }
      return result;
    }

    public void addListener( final ILabelProviderListener listener ) {
      // TODO Auto-generated method stub
    }

    public void dispose() {
      // TODO Auto-generated method stub
    }

    public boolean isLabelProperty( final Object element, final String property ) {
      // TODO Auto-generated method stub
      return false;
    }

    public void removeListener( final ILabelProviderListener listener ) {
      // TODO Auto-generated method stub
    }
  }
  class CSProvider implements IStructuredContentProvider {

    public Object[] getElements( final Object inputElement ) {
      Object[] result = new Object[ 0 ];
      if( inputElement instanceof Map ) {
        Map<String, String> map = ( Map<String, String> )inputElement;
        result = new Object[ map.size() ];
        for( int i = 0; i < map.size(); i++ ) {
          result[ i ] = new ArrayList<String>();
        }
        int i = 0;
        for( String key : map.keySet() ) {
          ( ( List )result[ i ] ).add( key );
          ( ( List )result[ i ] ).add( map.get( key ) );
          i++;
        }
      }
      return result;
    }

    public void dispose() {
      // TODO Auto-generated method stub
    }

    public void inputChanged( final Viewer viewer, final Object oldInput, final Object newInput )
    {
      // empty implementation
    }
  }
}
