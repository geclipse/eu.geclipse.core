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

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import eu.geclipse.core.model.IGridComputing;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridResource;
import eu.geclipse.core.model.impl.GridResourceCategoryFactory;
import eu.geclipse.jsdl.JSDLJobDescription;
import eu.geclipse.jsdl.ui.internal.Activator;
import eu.geclipse.jsdl.ui.internal.dialogs.MultipleInputDialog.Validator;
import eu.geclipse.jsdl.ui.providers.FeatureContentProvider;
import eu.geclipse.jsdl.ui.providers.FeatureLabelProvider;


/**
 * 
 * Create a new <code>CandidateHostsDialog</code>. The dialog consists of 
 * a {@link CheckboxTableViewer} that displays all Computing
 * Elements that support a specific Virtual Organization (VO). 
 * This VO is the same as the VO of the Grid Project the JSDL file belongs. 
 */
public class CandidateHostsDialog extends Dialog {
 
  protected CheckboxTableViewer hostsViewer = null;
  protected Composite panel = null;
  protected String title = null;
  protected List<Validator> validators = new ArrayList<Validator>();  
  private IGridElement gridElement = null;
  private JSDLJobDescription jobDescription = null;
  private List<String> existingHostsInViewer = new ArrayList<String>();  
  private String[] selectedHosts = null;

  

  /**
   * <code>CandidateHostsDialog</code> class constructor. Create a new dialog with
   * the following:
   * 
   * @param shell The Parent shell.
   * @param title The dialog title.
   */
  public CandidateHostsDialog( final Shell shell, final String title ) {
    
    super( shell );    
    this.title = title;    
    setShellStyle( getShellStyle() | SWT.RESIZE |SWT.APPLICATION_MODAL  );
    
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
    getButton( IDialogConstants.OK_ID ).setEnabled( false );
    return btnBar;
    
  }
  
  
  
  private void enableOKButton( final boolean value) {
    
      getButton( IDialogConstants.OK_ID ).setEnabled( value );
          
  } // end void enableOKButton()
  
  
  
  @Override
  protected Control createDialogArea( final Composite parent ) {
           
    Composite container = ( Composite ) super.createDialogArea( parent );
    GridData gd = new GridData( GridData.FILL_BOTH );
    container.setLayout( new GridLayout( 1, false ) );
    gd.grabExcessHorizontalSpace = true;
    gd.grabExcessVerticalSpace = true;
    container.setLayoutData( gd );
    this.panel = new Composite( container, SWT.NONE );
    GridLayout layout = new GridLayout( 1, false );
    this.panel.setLayout( layout );
    gd.grabExcessHorizontalSpace = true;
    gd.grabExcessVerticalSpace = true;
    this.panel.setLayoutData( gd );
    
    
    
    Label label = new Label( this.panel, SWT.NONE );
    label.setText( Messages.getString( "ResourcesPage_NewHostsDialogDescr" ) ); //$NON-NLS-1$
    label.setLayoutData( new GridData( GridData.HORIZONTAL_ALIGN_BEGINNING ) );
        
    gd = new GridData( GridData.FILL_BOTH );
    gd.grabExcessHorizontalSpace = true;
    gd.grabExcessVerticalSpace = true;
    gd.verticalSpan = 3;
    gd.horizontalSpan = 2;
    gd.widthHint = 300;
    gd.heightHint = 100;
    
    
    Table table = new Table( this.panel, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CHECK );
    table.setLayoutData( gd );
    
    this.hostsViewer = new CheckboxTableViewer( table );
    
    this.hostsViewer.setContentProvider( new FeatureContentProvider() );
    FeatureLabelProvider hostsLabelProvider = new FeatureLabelProvider();
    hostsLabelProvider.setStringType( FeatureLabelProvider.STRING_TYPE_CANDIDATE_HOSTS );
    this.hostsViewer.setLabelProvider( hostsLabelProvider );
    
    this.hostsViewer.addCheckStateListener( new ICheckStateListener () {

      public void checkStateChanged( final CheckStateChangedEvent event ) {        
        setValue( CandidateHostsDialog.this.hostsViewer.getCheckedElements() );
        
      }
      
    });
    
    try {
      
      Collection <String> computingElements =  new ArrayList<String>();
      
      IGridResource[] gridComputings = this.jobDescription.getProject().getVO().getInfoService().
                                         fetchResources( this.jobDescription.getProject().getVO(),
                                                         this.jobDescription.getProject().getVO(),
                                                         GridResourceCategoryFactory
                                                         .getCategory(  GridResourceCategoryFactory.ID_COMPUTING),
                                                         false,
                                                         IGridComputing.class,
                                                         new NullProgressMonitor());
      
//      IGridComputing[] gridComputings =  this.jobDescription.getProject().getVO()
//                                                                            .getComputing( new NullProgressMonitor() ) ;
      String hostnameOnly;
          
      for (int i=0; i < gridComputings.length; i++){        
       
        /* Get only the CE host name substring
         * 
         * Start from (last occurrence of empty space + 1) to remove the "CE @ " prefix.
         * Finish to the indexOf ":" character to remove any trailing characters.
         * 
         */
        if (gridComputings[i].getName().indexOf( ":" ) != -1){ //$NON-NLS-1$
        hostnameOnly = gridComputings[i].getName().substring( 
                                                         gridComputings[i].getName().lastIndexOf( " " )+1 //$NON-NLS-1$
                                                         , gridComputings[i].getName().indexOf( ":" ) ); //$NON-NLS-1$
      
        /*
         * If there are elements in the Candidate Hosts Table Viewer then
         */
        if ( this.existingHostsInViewer != null ) {
          /* check to see if the host name is already included. If not add it.*/
          if ( !this.existingHostsInViewer.contains( hostnameOnly)   ) 
          {
            /* Check if the host name appears twice from the InfoSystem and if it does do not
             * include it twice.
             */
            if ( !computingElements.contains( hostnameOnly ) )
              computingElements.add( hostnameOnly );
           }
        }
        else {
          /* Check if the host name appears twice from the InfoSystem and if it does do not
           * include it twice.
           */
          if ( !computingElements.contains( hostnameOnly ) )
            computingElements.add( hostnameOnly );
        }
      } // end 
    }
      this.hostsViewer.setInput( computingElements );
    } catch( Exception e ) {
      Activator.logException( e );
    }
    
     
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
    return IDebugUIConstants.PLUGIN_ID + ".CANDIDATE_HOSTS_DIALOG"; //$NON-NLS-1$
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
   * This method specifies the input the for {@link CheckboxTableViewer} in the 
   * dialog. The input is of type {@link IGridElement} and is obtained by getting
   * the input file of the JSDL editor.
   *  
   * @param input The IGridElement obtained from the JSDL editor.
   */
  public void setDialogInput(final IGridElement input ) {
    this.gridElement = input ;
    getJobDescription( this.gridElement );
  }

    
  
  /**
   * This is a optional method that allows to filter the Computing Elements shown
   * in the {@link CheckboxTableViewer} of the dialog. Computing Elements that
   * already are included in the Candidate Hosts {@link TableViewer} of the JSDL 
   * Resources page are not shown. 
   * 
   * @param input The Input of the Candidates Hosts {@link TableViewer} 
   */
  @SuppressWarnings("unchecked")
  public void setExistingCandidateHosts( final Object input ) {
    
    this.existingHostsInViewer = (List<String>) input;   
    
    
  }
  
  
  
  protected void setValue (final Object[] value) {
    
        
    Object[] checkedElements = value;
    if ( checkedElements != null && checkedElements.length > 0 ) {
      enableOKButton( true );
      this.selectedHosts = new String[ checkedElements.length ];
      for ( int i = 0 ; i < checkedElements.length ; i++ ) {
        this.selectedHosts[ i ] = ( String ) checkedElements[ i ];
      }
    }
    else {
      enableOKButton( false );
    }
    
  }  // end void setValue()
  
  
  
  /**
   * Get's the selected Candidate Hosts in the Dialog.
   * 
   * @return The selected Candidate Host(s).
   */
  public Object[] getValue() {
    
    String[] returnHosts = new String[this.selectedHosts.length];
    
    System.arraycopy( this.selectedHosts, 0,  returnHosts, 0, this.selectedHosts.length );

    return returnHosts;
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

