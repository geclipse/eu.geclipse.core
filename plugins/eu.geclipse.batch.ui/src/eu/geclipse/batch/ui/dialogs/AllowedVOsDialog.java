/******************************************************************************
 * Copyright (c) 2007-2008 g-Eclipse consortium
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
 *     UCY (http://www.cs.ucy.ac.cy)
 *      - Harald Gjermundrod (harald@cs.ucy.ac.cy)
 *
 *****************************************************************************/

package eu.geclipse.batch.ui.dialogs;

import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import eu.geclipse.batch.model.qdl.AllowedVirtualOrganizationsType;
import eu.geclipse.batch.ui.internal.Activator;
import eu.geclipse.batch.ui.internal.Messages;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.widgets.StoredCombo;


/**
 * @author nloulloud
 */
public class AllowedVOsDialog extends Dialog {
    
//  protected AllowedVirtualOrganizationsType allowedVO = QdlFactory.eINSTANCE.createAllowedVirtualOrganizationsType();
  private static final String VONAME_STRINGS = "voname_string"; //$NON-NLS-1$
  
  protected String newVO = null;
  protected boolean editMode = false;
  protected Composite panel = null;
  protected Label lblVOName = null;
  protected StoredCombo txtVOName = null;

  private String title = null;
  

  /**
   * 
   * The AllowedVODialog Class constructor.
   * 
   * Get's as input parameters the parentShell and the Dialog Title.
   *
   * @param parentShell
   * @param title
   */
  public AllowedVOsDialog( final Shell parentShell,
                           final String title) {
    
    super( parentShell );
    this.title = title;    
    setShellStyle( SWT.CLOSE | SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL
                   | SWT.RESIZE | SWT.MIN | SWT.MAX );
  }
  
  
  
  @Override
  protected void configureShell( final Shell shell ) {
    
    super.configureShell( shell );
    
    shell.setMinimumSize( 300, 130 );
    
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
    container.setLayout( new GridLayout( 3, false ) );
    container.setLayoutData( gd );
    this.panel = new Composite( container, SWT.NONE );
    GridLayout layout = new GridLayout( 3, false );
    this.panel.setLayout( layout );
    gd = new GridData(GridData.FILL_HORIZONTAL);
    this.panel.setLayoutData( gd );
    
    gd = new GridData( GridData.HORIZONTAL_ALIGN_FILL );
    gd.grabExcessHorizontalSpace = true;
    gd.horizontalSpan = 2;

    GridData gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    
    this.lblVOName = new Label( this.panel, SWT.NONE );
    this.lblVOName.setText( Messages.getString( "AllowedVODialog_VOName" ) ); //$NON-NLS-1$
    
    Activator activator = Activator.getDefault();
    IPreferenceStore preferenceStore = activator.getPreferenceStore();
    this.txtVOName = new StoredCombo( this.panel, SWT.DROP_DOWN );
    this.txtVOName.setPreferences( preferenceStore, VONAME_STRINGS );
    this.txtVOName.setLayoutData( gData );
    
    /* Initial Values for Edit Operation */
    if ( this.editMode ) {
      this.txtVOName.setText( this.newVO );
    }
    
    this.txtVOName.addModifyListener( new ModifyListener() {

      public void modifyText( final ModifyEvent e ) {

        AllowedVOsDialog.this.newVO = AllowedVOsDialog.this.txtVOName.getText(); 
        validateFields();
      }
      
    });
    
    //this.txtVOName.setLayoutData( gd );
    
    Dialog.applyDialogFont( container );

    addVONames();
    
    return parent;
    
  } // end void createDialogArea()
  
  
  
  protected void validateFields() {
    if ( 0 < this.txtVOName.getText().trim().length() )
      enableOKButton( true );
    else
      enableOKButton( false );
  }
  
  
  
  /**
   * @param dialogInput
   */
  public void setInput( final String dialogInput ) {
    
    this.newVO = dialogInput;
    this.editMode = true;
    
  }
  
  
  
  private String getDialogSettingsSectionName() {
    return IDebugUIConstants.PLUGIN_ID + ".ALLOWED_VOS_DIALOG"; //$NON-NLS-1$
  }
  
  
  
  /**
   * Get's the new {@link AllowedVirtualOrganizationsType}
   * 
   * @return The new {@link AllowedVirtualOrganizationsType}
   */
  public String getValue() {
              
    return this.newVO.trim();
  }
  
  
  @Override
  protected void okPressed() {
    
    super.okPressed();
  }
 
  
  
  @Override
  protected void cancelPressed() {
    super.cancelPressed();
  }
  
  
  @Override
  public int open() {
    
    applyDialogFont( this.panel );
    
    return super.open();
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
   * Provides a default list of VO names in the VO name field.
   */
  private void addVONames() {
    IGridElement[] gridElements = null;
    try {
      gridElements = GridModel.getVoManager().getChildren( null );
    } catch ( ProblemException e ) {
      // Nothing to do
    }
    
    if ( null != gridElements ) {
      for ( IGridElement element : gridElements ) {
        String name = element.getName();
        if ( name != null && this.txtVOName.indexOf( name ) == -1 ) {
          this.txtVOName.add( name );
        }
      }
    }
  }
  
}// end class AllowedVOsDialog
