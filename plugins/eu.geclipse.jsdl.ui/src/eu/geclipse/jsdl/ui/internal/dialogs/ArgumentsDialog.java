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

import java.util.Iterator;

import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import eu.geclipse.jsdl.model.FileSystemType;
import eu.geclipse.jsdl.model.JobDefinitionType;
import eu.geclipse.jsdl.model.JsdlFactory;
import eu.geclipse.jsdl.model.posix.ArgumentType;
import eu.geclipse.jsdl.model.posix.PosixFactory;
import eu.geclipse.jsdl.ui.internal.Activator;


/**
 * @author nloulloud
 *
 */
public class ArgumentsDialog extends Dialog {
  

  protected JobDefinitionType jobDefinitionType = JsdlFactory.eINSTANCE.createJobDefinitionType();
  protected FileSystemType fileSystemType = JsdlFactory.eINSTANCE.createFileSystemType();
  protected ArgumentType argumentType = PosixFactory.eINSTANCE.createArgumentType();
  protected Label lblValue = null;
  protected Label lblFileSystemName = null;
  protected Combo cmbFileSystemName = null;
  protected Text txtValue = null;
  protected boolean editMode = false;
  protected Composite panel = null;
  private String title = null;

  /**
   * @param parentShell The Dialog's Parent shell
   * @param title Dialog Title.
   * @param jsdlRoot The Root element ( {@link JobDefinitionType} ) of the JSDL document.
   */
  public ArgumentsDialog( final Shell parentShell, final String title, 
                          final JobDefinitionType jsdlRoot ) {
    
    super( parentShell );
    
    this.title = title;
    if (jsdlRoot != null ) {
      this.jobDefinitionType = jsdlRoot;
    }
    
    setShellStyle( getShellStyle() | SWT.RESIZE |SWT.APPLICATION_MODAL  );
    
    

  } // End class Constructor
  
  
  @Override
  protected void configureShell( final Shell shell ) {
   
    super.configureShell( shell );
    if( this.title != null ) {
      shell.setText( this.title );
    }
    
  } // End void configureShell()
  
  
  
  @Override
  protected Control createButtonBar( final Composite parent ) {
    
    Control btnBar = super.createButtonBar( parent );
    getButton( IDialogConstants.OK_ID ).setEnabled( false );
    return btnBar;
    
  } // End Control createButtonBar()
  
  
  
  private void enableOKButton( final boolean value) {

    /* If the button has been created change it's value */
    if ( getButton( IDialogConstants.OK_ID ) != null ) {
      getButton( IDialogConstants.OK_ID ).setEnabled( value );
    }
            
  } // end void validateFields()
  
  
  @SuppressWarnings("unchecked")
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
    
    
    /* ======================== File System Name Widgets =====================*/
    
    gd = new GridData( GridData.HORIZONTAL_ALIGN_FILL );
    gd.grabExcessHorizontalSpace = true;
    gd.horizontalSpan = 2;

    this.lblFileSystemName = new Label( this.panel, SWT.NONE );
    this.lblFileSystemName.setText( Messages.getString( "Dialog_FileSystem" ) ); //$NON-NLS-1$
    
    
    this.cmbFileSystemName = new Combo( this.panel, SWT.DROP_DOWN 
                                                   | SWT.READ_ONLY
                                                   | SWT.BORDER);
    

    EList<FileSystemType> fileSystemList = null;
    
    if (this.jobDefinitionType.getJobDescription().getResources() != null ) {
    
      try {
        fileSystemList = this.jobDefinitionType.getJobDescription().getResources().getFileSystem();
      } catch( Exception e ) {
        Activator.logException( e );
      }
    }
    
    if (fileSystemList != null ) {
      Iterator<FileSystemType> it = fileSystemList.iterator();
    
      while ( it.hasNext() ) {
        this.fileSystemType = it.next();
        this.cmbFileSystemName.add( this.fileSystemType.getName() );
      } // end while
      
    } //end if
    
    /* Initial Values for Edit Operation */
    if ( this.editMode ) {
      if (this.argumentType.getFilesystemName() != null ){ 
        this.cmbFileSystemName.setText( this.argumentType.getFilesystemName() );
      }
    }
    
    
    this.cmbFileSystemName.addSelectionListener( new SelectionListener(){

      public void widgetDefaultSelected( final SelectionEvent e ) {
        // Auto-generated method stub
        
      }

      public void widgetSelected( final SelectionEvent e ) {
        ArgumentsDialog.this.argumentType.setFilesystemName( ArgumentsDialog.this.cmbFileSystemName
                                                             .getItem(ArgumentsDialog.this.cmbFileSystemName.getSelectionIndex() ) );
        validateFields();
        
      }
      
    });
    
    this.cmbFileSystemName.setLayoutData( gd );
    
    
    /* =========================== Value Widgets =============================*/
    
    gd = new GridData( GridData.HORIZONTAL_ALIGN_FILL );
    gd.grabExcessHorizontalSpace = true;
    gd.horizontalSpan = 2;

    this.lblValue = new Label( this.panel, SWT.NONE );
    this.lblValue.setText( Messages.getString( "Dialog_Value" ) ); //$NON-NLS-1$
    
    
    this.txtValue = new Text( this.panel , SWT.SINGLE | SWT.BORDER);
    
    /* Initial Values for Edit Operation */
    if ( this.editMode ) {
      this.txtValue.setText( this.argumentType.getValue() );
    }

    
    this.txtValue.addModifyListener( new ModifyListener() {

      public void modifyText( final ModifyEvent e ) {

        ArgumentsDialog.this.argumentType.setValue( ( ArgumentsDialog.this.txtValue.getText() ) ); 
        validateFields();
      }
      
    });
    
    this.txtValue.setLayoutData( gd );
    Dialog.applyDialogFont( container );
    
    return parent;
    
  
  } // End createDialogArea()
  
  
  protected void validateFields() {
    if ( this.txtValue.getText() != "" ) { //$NON-NLS-1$
      enableOKButton( true );
    }
  }
  
  
  
  /**
   * @param dialogInput
   */
  public void setInput( final Object dialogInput ) {
    
    this.argumentType = ( ArgumentType ) dialogInput;
    this.editMode = true;
    
  }
  
  
  
  private String getDialogSettingsSectionName() {
    return IDebugUIConstants.PLUGIN_ID + ".ARGUMENTS_DIALOG"; //$NON-NLS-1$
  }
  
  
  
  /**
   * Get's the new {@link ArgumentType}
   * 
   * @return The new {@link ArgumentType}
   */
  public Object getValue() {
              
    return this.argumentType;
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
  
  
  
} // end FeatureDialog Class
