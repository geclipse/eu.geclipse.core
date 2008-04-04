/******************************************************************************
 * Copyright (c) 2007, 2008 g-Eclipse consortium 
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
 *      - Katarzyna Bylec (katis@man.poznan.pl)
 *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
 *           
 *****************************************************************************/
package eu.geclipse.jsdl.ui.widgets;

import java.net.URI;

import org.eclipse.emf.ecore.EEnum;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
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
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.jsdl.model.JsdlPackage;
import eu.geclipse.ui.dialogs.NewGridFileDialog;

/**
 * @author nickl
 */
public class DataStagingInDialog extends Dialog {

  /**
   * Variable that specifies that a simple dialog should be created.
   */
  public static final int SIMPLE_DIALOG = 0;
  /**
   * Variable that specifies that an advanced dialog should be created,
   * extending the fields of the SIMPLE_DIALOG the two following fields: <b>
   * Creation Flag Delete On Termination
   */
  public static final int ADVANCED_DIALOG = 1;
  protected Combo creationFlagCombo;
  protected Combo deleteOnTerminationCombo;
  protected Text pathText;
  Text nameText;
  private int dialogStyle;
  private String returnName;
  private String returnPath;
  private String initCreationFlag;
  private String initDeleteFlag;
  private String initName;
  private String initPath;
  private int returnCreationFlag;
  private Boolean returnDeleteFlag;

  /**
   * @param parentShell
   * @param style
   */
  public DataStagingInDialog( final Shell parentShell, final int style ) {
    super( parentShell );
    this.dialogStyle = style;
  }

  /**
   * @param parentShell
   * @param style
   * @param name
   * @param path
   */
  public DataStagingInDialog( final Shell parentShell,
                              final int style,
                              final String name,
                              final String path )
  {
    this( parentShell, style );
    this.initName = name;
    this.initPath = path;
    this.dialogStyle = style;
  }

  /**
   * @param parentShell
   * @param style
   * @param name
   * @param path
   * @param deleteFlag
   * @param creationFlag
   */
  public DataStagingInDialog( final Shell parentShell,
                              final int style,
                              final String name,
                              final String path,
                              final String creationFlag,
                              final Boolean deleteFlag )
  {
    this( parentShell, style );
    this.initName = name;
    this.initPath = path;
    this.dialogStyle = style;
    this.initCreationFlag = creationFlag;
    this.initDeleteFlag = deleteFlag.toString();
  }

  @Override
  protected void configureShell( final Shell shell ) {
    super.configureShell( shell );
    shell.setText( Messages.getString( "DataStageInTable.add_dialog_title" ) ); //$NON-NLS-1$
  }

  @Override
  protected Control createDialogArea( final Composite parent ) {
    Composite composite = ( Composite )super.createDialogArea( parent );
    composite.setLayout( new GridLayout( 1, false ) );
    composite.setLayoutData( new GridData( GridData.FILL_BOTH ) );
    Composite panel = new Composite( composite, SWT.NONE );
    this.initializeDialogUnits( composite );
    GridData gd;
    GridLayout gLayout = new GridLayout( 3, false );
    panel.setLayout( gLayout );
    gd = new GridData( GridData.FILL_HORIZONTAL );
    panel.setLayoutData( gd );
    Label pathLabel = new Label( panel, SWT.LEAD );
    pathLabel.setText( Messages.getString( "DataStageInTable.source_location_field_label" ) ); //$NON-NLS-1$
    gd = new GridData();
    pathLabel.setLayoutData( gd );
    this.pathText = new Text( panel, SWT.BORDER );
    gd = new GridData( GridData.FILL_HORIZONTAL );
    this.pathText.setLayoutData( gd );
    if( this.initPath != null ) {
      this.pathText.setText( this.initPath );
    }
    Button browseButton = new Button( panel, SWT.PUSH );
    // IPreferenceStore prefs = Activator.getDefault().getPreferenceStore();
    ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
    Image fileImage = sharedImages.getImage( ISharedImages.IMG_OBJ_FILE );
    browseButton.setImage( fileImage );
    browseButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent event ) {
        String filename = null;
        NewGridFileDialog dialog = new NewGridFileDialog( PlatformUI.getWorkbench()
                                                            .getActiveWorkbenchWindow()
                                                            .getShell(),
                                                          NewGridFileDialog.STYLE_ALLOW_ONLY_EXISTING
                                                              | NewGridFileDialog.STYLE_ALLOW_ONLY_FILES );
        if( dialog.open() == Window.OK ) {
          URI[] uris = dialog.getSelectedURIs();
          if ((uris != null)&&(uris.length > 0)){
            filename = uris[0].toString();
            DataStagingInDialog.this.pathText.setText( filename );
            if( DataStagingInDialog.this.nameText.getText().equals( "" ) ) { //$NON-NLS-1$
              String nameToSet = ""; //$NON-NLS-1$
              nameToSet = filename.substring( filename.lastIndexOf( "/" ) + 1, filename.length() ); //$NON-NLS-1$
              DataStagingInDialog.this.nameText.setText( nameToSet );
            }
          }
        }
        // IGridConnectionElement connection = GridFileDialog.openFileDialog(
        // PlatformUI.getWorkbench()
        // .getActiveWorkbenchWindow()
        // .getShell(),
        // Messages.getString( "DataStageInTable.grid_file_dialog_title" ),
        // //$NON-NLS-1$
        // null,
        // true );
        // if( connection != null ) {
        // filename = connection.getURI().toString();
        // if( filename != null ) {
        // DataStagingInDialog.this.pathText.setText( filename );
        // if( DataStagingInDialog.this.nameText.getText().equals( "" ) ) {
        // //$NON-NLS-1$
        // String nameToSet = ""; //$NON-NLS-1$
        // nameToSet = filename.substring( filename.lastIndexOf( "/" ) + 1,
        // filename.length() ); //$NON-NLS-1$
        // DataStagingInDialog.this.nameText.setText( nameToSet );
        // }
        // }
        // }
        updateButtons();
      }
    } );
    Label nameLabel = new Label( panel, SWT.LEAD );
    nameLabel.setText( Messages.getString( "DataStageInTable.name_field_label" ) ); //$NON-NLS-1$
    nameLabel.setLayoutData( new GridData() );
    this.nameText = new Text( panel, SWT.BORDER );
    gd = new GridData();
    gd.widthHint = 260;
    gd.horizontalSpan = 2;
    gd.horizontalAlignment = SWT.FILL;
    this.nameText.setLayoutData( gd );
    if( this.initName != null ) {
      this.nameText.setText( this.initName );
    }
    if( this.dialogStyle == DataStagingInDialog.ADVANCED_DIALOG ) {
      gd = new GridData();
      Label creationFlagLabel = new Label( panel, SWT.LEAD );
      creationFlagLabel.setText( Messages.getString( "DataStageInTable.CreationFlag_field_label" ) ); //$NON-NLS-1$
      creationFlagLabel.setLayoutData( gd );
      this.creationFlagCombo = new Combo( panel, SWT.BORDER
                                                 | SWT.SIMPLE
                                                 | SWT.DROP_DOWN
                                                 | SWT.READ_ONLY );
      gd = new GridData();
      gd.widthHint = 260;
      gd.horizontalSpan = 2;
      gd.horizontalAlignment = SWT.FILL;
      this.creationFlagCombo.setLayoutData( gd );
      /* Populate the Combo Box with the Creation Flag Literals */
      EEnum cFEnum = JsdlPackage.Literals.CREATION_FLAG_ENUMERATION;
      for( int i = 0; i < cFEnum.getELiterals().size(); i++ ) {
        this.creationFlagCombo.add( cFEnum.getEEnumLiteral( i ).toString() );
      }
      cFEnum = null;
      int indexOfOverwite;
      if( this.initCreationFlag != null ) {
        indexOfOverwite = this.creationFlagCombo.indexOf( this.initCreationFlag );
        this.creationFlagCombo.select( indexOfOverwite );
      } else {
        indexOfOverwite = this.creationFlagCombo.indexOf( "overwrite" ); //$NON-NLS-1$
        this.creationFlagCombo.select( indexOfOverwite );
      }
      gd = new GridData();
      Label deleteOnTerminationLabel = new Label( panel, SWT.LEAD );
      deleteOnTerminationLabel.setText( Messages.getString( "DataStageInTable.DeleteOnTermination_field_label" ) ); //$NON-NLS-1$
      deleteOnTerminationLabel.setLayoutData( gd );
      this.deleteOnTerminationCombo = new Combo( panel, SWT.BORDER
                                                        | SWT.SIMPLE
                                                        | SWT.DROP_DOWN
                                                        | SWT.READ_ONLY );
      /* Populate the Combo Box with the Delete On Termination Literals */
      this.deleteOnTerminationCombo.add( "true" ); //$NON-NLS-1$
      this.deleteOnTerminationCombo.add( "false" ); //$NON-NLS-1$
      if( this.initDeleteFlag != null ) {
        int indexOfDelete = this.deleteOnTerminationCombo.indexOf( this.initDeleteFlag );
        this.deleteOnTerminationCombo.select( indexOfDelete );
      } else {
        this.deleteOnTerminationCombo.select( 0 );
      }
      gd = new GridData();
      gd.horizontalSpan = 2;
      gd.horizontalAlignment = SWT.FILL;
      this.deleteOnTerminationCombo.setLayoutData( gd );
    }
    ModifyListener listener = new UpdateAdapter();
    this.nameText.addModifyListener( listener );
    this.pathText.addModifyListener( listener );
    return composite;
  }

  /**
   * Access to path value provided by the user.
   * 
   * @return string representing URI
   */
  public String getPath() {
    return this.returnPath;
  }

  /**
   * Access to name value provided by the user.
   * 
   * @return name of the data staging in
   */
  public String getName() {
    return this.returnName;
  }

  /**
   * Access to file Delete On Termination Flag provided by the user.
   * 
   * @return Delete On Termination flag set by the user.
   */
  public Boolean getDeleteOnTermination() {
    return this.returnDeleteFlag;
  }

  /**
   * Access to file Creation Flag provided by the user.
   * 
   * @return Creation Flag set by the user.
   */
  public int getCreationFlag() {
    return this.returnCreationFlag;
  }

  @Override
  protected void okPressed() {
    this.returnName = this.nameText.getText();
    this.returnPath = this.pathText.getText();
    if( this.dialogStyle == ADVANCED_DIALOG ) {
      this.returnCreationFlag = this.creationFlagCombo.getSelectionIndex();
      if( this.deleteOnTerminationCombo.getSelectionIndex() != -1 ) {
        this.returnDeleteFlag = Boolean.valueOf( Boolean.parseBoolean( this.deleteOnTerminationCombo.getItem( this.deleteOnTerminationCombo.getSelectionIndex() ) ) );
      }
    }
    super.okPressed();
  }

  void updateButtons() {
    if( !this.nameText.getText().equals( "" ) //$NON-NLS-1$
        && !this.pathText.getText().equals( "" ) ) //$NON-NLS-1$
    {
      super.getButton( IDialogConstants.OK_ID ).setEnabled( true );
    } else {
      super.getButton( IDialogConstants.OK_ID ).setEnabled( false );
    }
  }

  @Override
  protected void createButtonsForButtonBar( final Composite parent ) {
    super.createButtonsForButtonBar( parent );
    updateButtons();
  }
  class UpdateAdapter implements ModifyListener {

    public void modifyText( final ModifyEvent event ) {
      updateButtons();
    }
  }
}
