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
 *     PSNC: 
 *      - Katarzyna Bylec (katis@man.poznan.pl)
 *           
 *****************************************************************************/
package eu.geclipse.jsdl.ui.widgets;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.ui.dialogs.GridFileDialog;


public class DataStagingInDialog extends Dialog {
  Text pathText;
  private Text nameText;
  private String returnName;
  private String returnPath;
  private String initName;
  private String initPath;

  protected DataStagingInDialog( final Shell parentShell ) {
    super( parentShell );
  }

  protected DataStagingInDialog( final Shell parentShell,
                        final String name,
                        final String path )
  {
    this( parentShell );
    this.initName = name;
    this.initPath = path;
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
    pathLabel.setText( Messages.getString( "DataStageInTable.location_field_label" ) ); //$NON-NLS-1$
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
        IGridConnectionElement connection = GridFileDialog.openFileDialog( PlatformUI.getWorkbench()
                                                                             .getActiveWorkbenchWindow()
                                                                             .getShell(),
                                                                           Messages.getString( "DataStageInTable.grid_file_dialog_title" ), //$NON-NLS-1$
                                                                           null,
                                                                           true );
        if( connection != null ) {
          try {
            filename = connection.getConnectionFileStore().toString();
          } catch( CoreException e1 ) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
          }
          if( filename != null ) {
            DataStagingInDialog.this.pathText.setText( filename );
          }
        }
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

  @Override
  protected void okPressed() {
    this.returnName = this.nameText.getText();
    this.returnPath = this.pathText.getText();
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
