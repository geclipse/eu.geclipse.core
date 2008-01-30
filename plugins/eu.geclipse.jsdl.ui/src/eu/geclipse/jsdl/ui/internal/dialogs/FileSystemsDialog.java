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

import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
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

import eu.geclipse.jsdl.model.BoundaryType;
import eu.geclipse.jsdl.model.FileSystemType;
import eu.geclipse.jsdl.model.FileSystemTypeEnumeration;
import eu.geclipse.jsdl.model.JsdlFactory;
import eu.geclipse.jsdl.model.JsdlPackage;
import eu.geclipse.jsdl.model.RangeValueType;
import eu.geclipse.jsdl.ui.internal.Activator;


/**
 * @author nloulloud
 *
 */
public class FileSystemsDialog extends Dialog {
  
  private static final int WIDGET_HEIGHT = 100;
  protected FileSystemType fileSystemType = JsdlFactory.eINSTANCE.createFileSystemType();  
  protected boolean editMode = false;
  protected Composite panel = null;
  protected Label lblFileSystemName = null;
  protected Label lblMountPoint = null;
  protected Label lblMountSource = null;  
  protected Label lblDiskSpace = null;
  protected Label lblFileSystemType = null;
  protected Label lblFileSystemDescr = null;
  protected Text txtFileSystemName = null;
  protected Text txtFileSystemDescr = null;
  protected Text txtMountPoint = null;  
  protected Text txtMountSource = null;
  protected Text txtDiskSpace = null;
  protected Combo cmbFileSystemType = null;
  protected Combo cmbDiskSpaceRange = null;
  private String title = null;
  private FileSystemType[] newFileSystem = null;
  
  
  
  /**
   * @param parentShell
   * @param title
   */
  public FileSystemsDialog( final Shell parentShell, final String title ) {
    
    super( parentShell );
    this.title = title;    
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
    this.lblFileSystemName.setText( Messages.getString( "ResourcesPage_FileSystemName" ) ); //$NON-NLS-1$
    
    
    this.txtFileSystemName = new Text( this.panel , SWT.SINGLE | SWT.BORDER);
    
    /* Initial Values for Edit Operation */
    if ( this.editMode ) {
      this.txtFileSystemName.setText( this.fileSystemType.getName() );
    }

    
    this.txtFileSystemName.addModifyListener( new ModifyListener() {

      public void modifyText( final ModifyEvent e ) {

        FileSystemsDialog.this.fileSystemType.setName( FileSystemsDialog.this.txtFileSystemName.getText()); 
        validateFields();
      }
      
    });
    
    this.txtFileSystemName.setLayoutData( gd );
    
    if ( this.editMode ) {
      this.txtFileSystemName.setText( this.fileSystemType.getName() );
    }
 
  
  /* =========================== Description Widgets =========================*/
  
  gd = new GridData( GridData.HORIZONTAL_ALIGN_FILL );
  gd.verticalAlignment = GridData.BEGINNING;

  this.lblFileSystemDescr = new Label( this.panel, SWT.NONE );  
  this.lblFileSystemDescr.setText( Messages.getString( "ResourcesPage_Description" ) ); //$NON-NLS-1$
  this.lblFileSystemDescr.setLayoutData( gd );
   
  
 
  this.txtFileSystemDescr = new Text(this.panel, SWT.BORDER | SWT.MULTI 
                                                |SWT.H_SCROLL|SWT.V_SCROLL 
                                                | SWT.WRAP ); 
     
  gd = new GridData( GridData.FILL_HORIZONTAL);
  gd.grabExcessVerticalSpace = true;
  gd.horizontalSpan = 2;
  gd.heightHint = FileSystemsDialog.WIDGET_HEIGHT;
  
  /* Initial Values for Edit Operation */
  if ( this.editMode ) {
    this.txtFileSystemDescr.setText( this.fileSystemType.getDescription() );
  }
  
  this.txtFileSystemDescr.addModifyListener( new ModifyListener() {

    public void modifyText( final ModifyEvent e ) {
      FileSystemsDialog.this.fileSystemType.setDescription( FileSystemsDialog.this.txtFileSystemDescr.getText() );
      
    }
    
  });
  
  this.txtFileSystemDescr.setLayoutData( gd );
    
  /* ========================= Mount Point Widget ============================*/
  gd = new GridData();
  this.lblMountPoint = new Label(this.panel, SWT.NONE);
  this.lblMountPoint.setText( Messages.getString( "ResourcesPage_MountPoint" ) ); //$NON-NLS-1$
    
  gd = new GridData( GridData.FILL_BOTH );
  gd.horizontalSpan = 2;

  this.txtMountPoint = new Text (this.panel, SWT.SINGLE | SWT.BORDER );
  /* Initial Values for Edit Operation */
  if ( this.editMode ) {
    if ( this.fileSystemType.getMountPoint() != null ) {
      this.txtMountPoint.setText( this.fileSystemType.getMountPoint() );
    }
  }
  
  this.txtMountPoint.addModifyListener( new ModifyListener() {

    public void modifyText( final ModifyEvent e ) {
      FileSystemsDialog.this.fileSystemType.setMountPoint( FileSystemsDialog.this.txtMountPoint.getText() );
      
    }
    
  });
  this.txtMountPoint.setLayoutData(gd);
  
  
  /* ========================= Mount Source Widget ============================*/
  gd = new GridData();
  this.lblMountSource = new Label(this.panel, SWT.NONE);
  this.lblMountSource.setText( Messages.getString( "ResourcesPage_MountSource" ) ); //$NON-NLS-1$
    
  gd = new GridData( GridData.FILL_BOTH );
  gd.horizontalSpan = 2;

  this.txtMountSource = new Text (this.panel, SWT.SINGLE | SWT.BORDER );
  /* Initial Values for Edit Operation */
  
  //TODO: nloulloud MountSource Not yet defined in the Model
//  if ( this.editMode ) {
//    if ( this.fileSystemType.get() != null ) {
//      this.txtMountSource.setText( this.fileSystemType.getMountPoint() );
//    }
//  }
//  
//  this.txtMountSource.addModifyListener( new ModifyListener() {
//
//    public void modifyText( final ModifyEvent e ) {
//      FileSystemsDialog.this.fileSystemType.setMountPoint( FileSystemsDialog.this.txtMountSource.getText() );
//      
//    }
//    
//  });
  this.txtMountSource.setLayoutData(gd);


  /* ======================== Disk Space Widgets =============================*/
  
  
  gd = new GridData();
  this.lblDiskSpace = new Label( this.panel, SWT.None );
  this.lblDiskSpace.setText( Messages.getString( "ResourcesPage_DiskSpace" ) ); //$NON-NLS-1$
  
  this.txtDiskSpace = new Text(this.panel, SWT.SINGLE | SWT.BORDER );
  
  this.txtDiskSpace.setLayoutData( gd );

  gd = new GridData(GridData.FILL_BOTH);  
  this.cmbDiskSpaceRange = new Combo( this.panel, SWT.DROP_DOWN 
                                                | SWT.READ_ONLY
                                                | SWT.BORDER );    
  this.cmbDiskSpaceRange.add( Messages.getString( "ResourcesPage_LowBoundRange" ) ); //$NON-NLS-1$
  this.cmbDiskSpaceRange.add( Messages.getString( "ResourcesPage_UpBoundRange" ) ); //$NON-NLS-1$  
  
  this.cmbDiskSpaceRange.setLayoutData( gd );

  
  this.txtDiskSpace.addModifyListener( new ModifyListener() {

    BoundaryType boundaryType = JsdlFactory.eINSTANCE.createBoundaryType();    
    RangeValueType rangeValueType = JsdlFactory.eINSTANCE.createRangeValueType();
    
    public void modifyText( final ModifyEvent e ) {
      
      if ( !FileSystemsDialog.this.txtDiskSpace.getText().equals( "" ) && !FileSystemsDialog.this.editMode ) { //$NON-NLS-1$ 
        
        
        this.boundaryType.setValue( Double.parseDouble( FileSystemsDialog.this.txtDiskSpace.getText() ) );        
        switch( FileSystemsDialog.this.cmbDiskSpaceRange.getSelectionIndex() ) {
          /* INDEX 0 = UPPER RANGE */
          case 0 : this.rangeValueType.setLowerBoundedRange( this.boundaryType ); 
          break;
          /* INDEX 1 = UPPER RANGE */
          case 1 : this.rangeValueType.setUpperBoundedRange( this.boundaryType );
          break;
          default:
          break;
        }
              
        FileSystemsDialog.this.fileSystemType.setDiskSpace( this.rangeValueType );
        }
      
    }
    
  });

  this.cmbDiskSpaceRange.addSelectionListener( new SelectionListener() {

    BoundaryType boundaryType = JsdlFactory.eINSTANCE.createBoundaryType();    
    RangeValueType rangeValueType = JsdlFactory.eINSTANCE.createRangeValueType();
    
    public void widgetDefaultSelected( final SelectionEvent e ) {
      /* Auto-generated method stub */
      
    }

    public void widgetSelected( final SelectionEvent e ) {

      if ( !FileSystemsDialog.this.txtDiskSpace.getText().equals( "" ) ) { //$NON-NLS-1$
            
      this.boundaryType.setValue( Double.parseDouble( FileSystemsDialog.this.txtDiskSpace.getText() ) );        
      switch( FileSystemsDialog.this.cmbDiskSpaceRange.getSelectionIndex() ) {
        /* INDEX 0 = UPPER RANGE */
        case 0 : this.rangeValueType.setLowerBoundedRange( this.boundaryType ); 
        break;
        /* INDEX 1 = UPPER RANGE */
        case 1 : this.rangeValueType.setUpperBoundedRange( this.boundaryType );
        break;
        default:
        break;
      }
   
      FileSystemsDialog.this.fileSystemType.setDiskSpace( this.rangeValueType );
            
      } // end_if equals ""
      
    }
    
  });
  
  
  /* Initial Values for Edit Operation */
  if ( this.editMode ) {
    
    RangeValueType rangeValueType = JsdlFactory.eINSTANCE.createRangeValueType();
    rangeValueType = this.fileSystemType.getDiskSpace();
    
    rangeValueType = (RangeValueType) checkProxy( rangeValueType );
    
    
    BoundaryType boundaryType = JsdlFactory.eINSTANCE.createBoundaryType();
    
    if (this.fileSystemType.getDiskSpace() != null ){
    
      if ( this.fileSystemType.getDiskSpace().getLowerBoundedRange() != null ) {
    
        boundaryType = this.fileSystemType.getDiskSpace().getLowerBoundedRange();
        
        /* check for Lazy Loading */
        boundaryType = (BoundaryType) checkProxy( boundaryType );
    
        this.txtDiskSpace.setText( Double.toString( boundaryType.getValue() ) );
    
        /* Select the Lower Bound */                  
        this.cmbDiskSpaceRange.select( 0 );
   
      }
  
      else {
        boundaryType = this.fileSystemType.getDiskSpace().getUpperBoundedRange();
    
        /* check for Lazy Loading */
        boundaryType = (BoundaryType) checkProxy( boundaryType );
  
        this.txtDiskSpace.setText( Double.toString( boundaryType.getValue() ) );
        /* Select the Lower Bound */
        this.cmbDiskSpaceRange.select( 1 );
      }
      
    } //end_if (this.fileSystemType.getDiskSpace() != null )
    
  }
  
  
  /* ========================= File System Widgets ===========================*/

  this.lblFileSystemType = new Label( this.panel, SWT.NONE);
  this.lblFileSystemType.setText( Messages.getString( "ResourcesPage_FileSysType" ) ); //$NON-NLS-1$

  gd = new GridData( GridData.FILL_BOTH );
  gd.horizontalSpan = 2;
  this.cmbFileSystemType = new Combo(this.panel, SWT.NONE 
                                               | SWT.READ_ONLY 
                                               | SWT.BORDER);
  
  
  /* Populate the Combo Box with the File System Type Literals */    
  EEnum cFEnum = JsdlPackage.Literals.FILE_SYSTEM_TYPE_ENUMERATION;
     for ( int i=0; i<cFEnum.getELiterals().size(); i++ ) {         
       this.cmbFileSystemType.add( cFEnum.getEEnumLiteral( i ).toString() );
     }

     
  this.cmbFileSystemType.setLayoutData(gd);
  this.cmbFileSystemType.addSelectionListener( new SelectionListener() {

    public void widgetDefaultSelected( final SelectionEvent e ) {
      /* Auto-generated method stub */
      
    }

    public void widgetSelected( final SelectionEvent e ) {
      
      FileSystemsDialog.this.fileSystemType
                            .setFileSystemType(FileSystemTypeEnumeration.get( 
                                      FileSystemsDialog.this.cmbFileSystemType
                                      .getSelectionIndex() ) );
      
    }
    
  });
  
  /* Initial Values for Edit Operation */
  if ( this.editMode ) {
    this.cmbFileSystemType.setText( this.fileSystemType.getFileSystemType().toString() );
  }
  
  /* Hack to apply initial values to widgets */  
     if ( this.editMode ) {
       this.editMode = false;
     }
    Dialog.applyDialogFont( container );
    

    return parent;

  } // end Control createDialogArea()
  
  
  
  
  /**
   * @param dialogInput
   */
  public void setInput( final Object dialogInput ) {
    
    this.fileSystemType = ( FileSystemType ) dialogInput;
    this.editMode = true;
    
  }
  
  
  /**
   * Get's the new File System
   * 
   * @return The new File System
   */
  public Object[] getValue() {
              
    return this.newFileSystem;
  }
  
  
  @Override
  protected void okPressed() {
    try {
      
      this.newFileSystem = new FileSystemType[1];
      this.newFileSystem[0] = this.fileSystemType;
      
    } catch( Exception e ) {
      Activator.logException( e );
    }
    
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
  
  
  
  protected void validateFields() {
    if ( this.txtFileSystemName.getText() != "" ) { //$NON-NLS-1$
      enableOKButton( true );
    }
  }
  
  
  
  private String getDialogSettingsSectionName() {
    return IDebugUIConstants.PLUGIN_ID + ".FILE_SYSTEMS_DIALOG"; //$NON-NLS-1$
  }
  
  
  protected EObject checkProxy(final EObject refEObject) {
    
    EObject eObject = refEObject;
    
    if (eObject != null && eObject.eIsProxy() ) {
     
      eObject =  EcoreUtil.resolve( eObject, this.fileSystemType );
    }
        
    return eObject;
    
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
  
  
  
} // End Class FileSystemsDialog()
