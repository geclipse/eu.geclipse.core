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
 *     UCY (http://www.cs.ucy.ac.cy)
 *      - Harald Gjermundrod (harald@cs.ucy.ac.cy)
 *
 *****************************************************************************/

package eu.geclipse.batch.ui.dialogs;

import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import eu.geclipse.batch.model.qdl.AllowedVirtualOrganizationsType;
import eu.geclipse.batch.ui.internal.Activator;
import eu.geclipse.batch.ui.internal.Messages;



/**
 * @author nloulloud
 *
 */
public class AllowedVOsDialog extends Dialog {
    
//  protected AllowedVirtualOrganizationsType allowedVO = QdlFactory.eINSTANCE.createAllowedVirtualOrganizationsType();
  protected String newVO = null;
  protected boolean editMode = false;
  protected Composite panel = null;
  protected Label lblVOName = null;
  protected Text txtVOName = null;
  
  
  
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
    setShellStyle( getShellStyle() | SWT.RESIZE |SWT.APPLICATION_MODAL  );

  }
  
  
  
  @Override
  protected void configureShell( final Shell shell ) {
    
    shell.setSize( 300 , 100 );
    
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

    this.lblVOName = new Label( this.panel, SWT.NONE );
    this.lblVOName.setText( Messages.getString( "AllowedVODialog_VOName" ) ); //$NON-NLS-1$
    
    
    this.txtVOName = new Text( this.panel , SWT.SINGLE | SWT.BORDER);
    
    /* Initial Values for Edit Operation */
    if ( this.editMode ) {
      this.txtVOName.setText( this.newVO );
    }

    
    this.txtVOName.addModifyListener( new ModifyListener() {

      public void modifyText( final ModifyEvent e ) {

        AllowedVOsDialog.this.newVO =  AllowedVOsDialog.this.txtVOName.getText(); 
        validateFields();
      }
      
    });
    
    this.txtVOName.setLayoutData( gd );
    
    
    Dialog.applyDialogFont( container );
    
    return parent;
    
  } // end void createDialogArea()
  
  
  
  protected void validateFields() {
    if ( 0 < this.txtVOName.getText().length() )
      enableOKButton( true );
    else
      enableOKButton( false );
  }
  
  
  
  /**
   * @param dialogInput
   */
  public void setInput( final Object dialogInput ) {
    
    this.newVO = ( String ) dialogInput;
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
  public Object getValue() {
              
    return this.newVO;
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
  
  
}// end class AllowedVOsDialog
