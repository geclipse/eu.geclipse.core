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
package eu.geclipse.jsdl.ui.internal.dialogs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import eu.geclipse.core.model.IGridComputing;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.jsdl.JSDLJobDescription;
import eu.geclipse.jsdl.ui.internal.Activator;
import eu.geclipse.jsdl.ui.internal.dialogs.MultipleInputDialog.Validator;
import eu.geclipse.jsdl.ui.providers.FeatureContentProvider;
import eu.geclipse.jsdl.ui.providers.FeatureLabelProvider;


/**
 * @author nloulloud
 *
 */
public class CandidateHostsDialog extends Dialog {
  
  protected Composite panel = null;
  protected String title = null;
  protected List<Validator> validators = new ArrayList<Validator>();  
  private IGridElement gridElement = null;
  private JSDLJobDescription jobDescription = null;
  private String initialValue = null;
  private Text text = null; 
  private Object[] selectedHosts = null;

  /**
   * @param parentShell
   * @param title
   */
  public CandidateHostsDialog( final Shell parentShell, final String title ) {
    
    super( parentShell );    
    this.title = title;    
    setShellStyle( getShellStyle() | SWT.RESIZE );
    
  } // end Class Constructor

  
  
  @Override
  protected void configureShell( final Shell shell ) {
    
    shell.setSize( 600, 300 );
    super.configureShell( shell );
    if( this.title != null ) {
      shell.setText( this.title );
    }
  }
  
  
  
  @Override
  protected Control createButtonBar( final Composite parent ) {
    
    Control btnBar = super.createButtonBar( parent );
    validateFields();
    return btnBar;
    
  }
  
  
  /**
   * Validates all dialog fields and makes OK button disabled or enabled
   */
  public void validateFields() {
    
//      if ( this.text.getText() == "" ) { //$NON-NLS-1$
//        getButton( IDialogConstants.OK_ID ).setEnabled( false );        
//      } // end_if
//      else{
//        getButton( IDialogConstants.OK_ID ).setEnabled( true );
//      }
      
  } // end void validateFields()
  
  
  
  @Override
  protected Control createDialogArea( final Composite parent ) {
           
    Composite container = ( Composite )super.createDialogArea( parent );
    container.setLayout( new GridLayout( 2, false ) );
    container.setLayoutData( new GridData( GridData.FILL_BOTH ) );
    this.panel = new Composite( container, SWT.NONE );
    GridLayout layout = new GridLayout( 2, false );
    this.panel.setLayout( layout );
    this.panel.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
    
    
    Label label = new Label( this.panel, SWT.NONE );
    label.setText( Messages.getString( "ResourcesPage_NewHostsDialogDescr" ) ); //$NON-NLS-1$
    label.setLayoutData( new GridData( GridData.HORIZONTAL_ALIGN_BEGINNING ) );
    GridData gd;
    
    gd = new GridData( GridData.FILL_BOTH );
    gd.grabExcessHorizontalSpace = true;
    gd.grabExcessVerticalSpace = true;
    gd.verticalSpan = 3;
    gd.horizontalSpan = 2;
    gd.widthHint = 300;
    gd.heightHint = 100;
    
    
    Table table = new Table( this.panel, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CHECK );
    table.setLayoutData( gd );
    
    CheckboxTableViewer hostsViewer = new CheckboxTableViewer( table );
    
    hostsViewer.setContentProvider( new FeatureContentProvider() );
    hostsViewer.setLabelProvider( new FeatureLabelProvider() );
    
//    hostsViewer.addSelectionChangedListener( new ISelectionChangedListener()
//    {
//
//      public void selectionChanged( final SelectionChangedEvent event ) {        
//        IStructuredSelection selection = ( IStructuredSelection )event.getSelection();
//        
//        setValue( selection.toArray() );
//      }
//    } );
    
//    TableColumn column = new TableColumn(tblHosts, SWT.FILL);
//    column.setWidth( 300 );
//    tblHosts.setLayoutData( gd );
    
    try {
      
      Collection <String> computingElements =  new ArrayList<String>();
      IGridComputing[] gridComputings = this.jobDescription.getProject().getVO().getComputing() ;
      for (int i=0; i<gridComputings.length; i++){
        computingElements.add( gridComputings[i].getName() );
      }
      
      hostsViewer.setInput( computingElements );
    } catch( Exception e ) {
      Activator.logException( e );
    }
    
    
//    this.text = new Text( this.panel, SWT.SINGLE | SWT.BORDER | SWT.WRAP );
//    this.text.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
//    // make sure rows are the same height on both panels.
//    label.setSize( label.getSize().x, this.text.getSize().y );
//    
//    if ( this.initialValue != null) {
//      this.text.setText( this.initialValue );
//    }
//    
//    if ( this.text.getText().equals( "" ) ) { //$NON-NLS-1$
////      getButton( IDialogConstants.OK_ID ).setEnabled( false );
//    }
     
    Dialog.applyDialogFont( container );
    return parent;

  } // end Control createDialogArea()
 

  
  @Override
  protected void okPressed() {
  
    super.okPressed();
  }
  
  
  @Override
  public int open() {
    
    applyDialogFont( this.panel );
    
    return super.open();
  }
  
  
  private String getDialogSettingsSectionName() {
    return IDebugUIConstants.PLUGIN_ID + ".MULTIPLE_INPUT_DIALOG_2"; //$NON-NLS-1$
  }
  
  
  
  @Override
  protected IDialogSettings getDialogBoundsSettings() {
    
    IDialogSettings settings = Activator.getDefault().getDialogSettings();
    IDialogSettings section = settings.getSection( getDialogSettingsSectionName() );
    if( section == null ) {
      section = settings.addNewSection( getDialogSettingsSectionName() );
    }
    return section;
    
  } // end IDialogSetting getDialogBoundsSettings()
  
  
  
  /**
   * @param input
   */
  public void setDialogInput(final IGridElement input ) {
    this.gridElement = input ;
    getJobDescription( this.gridElement );
  }

  
  
  /**
   * @param value
   */
  public void setInitialValue( final String value ) {
    this.initialValue  = value;
  }
  
  
  
  protected void setValue (final Object value) {
            
    if ( value instanceof Object[]) {      
      
      this.selectedHosts = ( Object[] ) value;      
      String tempValue = "";  //$NON-NLS-1$
      for ( int i=0; i < this.selectedHosts.length; i++ ) {        
        tempValue = tempValue + " [ " + this.selectedHosts[i].toString() + " ] "; //$NON-NLS-1$ //$NON-NLS-2$
      } // end for
      this.text.setText( tempValue );
      
    }
    
    else {
      
      this.selectedHosts[0] = value.toString();
      this.text.setText( this.selectedHosts[0].toString() );
      
    }
    
  }  // end void setValue()
  
  
  
  /**
   * @return The selected Candidate Host(s).
   */
  public Object[] getValue() {
        
    return this.selectedHosts;
  }

  
  private JSDLJobDescription getJobDescription (final IGridElement input) {
    
       
    if ( input instanceof JSDLJobDescription ) {      
      this.jobDescription = ( JSDLJobDescription ) input;           
    }
    else {      
      this.jobDescription = null;
    }
    
    return this.jobDescription;
    
  }
   
  
} // end Class

