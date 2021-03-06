/******************************************************************************
 * Copyright (c) 2007 - 2008 g-Eclipse consortium 
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
package eu.geclipse.jsdl.ui.internal.dialogs;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.ImageDescriptor;
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
import org.eclipse.ui.PlatformUI;

import eu.geclipse.jsdl.model.base.CreationFlagEnumeration;
import eu.geclipse.jsdl.model.base.DataStagingType;
import eu.geclipse.jsdl.model.base.JsdlFactory;
import eu.geclipse.jsdl.model.base.JsdlPackage;
import eu.geclipse.jsdl.model.base.SourceTargetType;
import eu.geclipse.jsdl.ui.internal.Activator;
import eu.geclipse.jsdl.ui.widgets.Messages;
import eu.geclipse.ui.dialogs.GridFileDialog;

/**
 * Dialog class for providing information (entering new one or editing existing
 * one) of data staging out entries in JSDL.
 */
public class DataStagingOutDialog extends Dialog {

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
  protected Text pathText;
  protected URI[] uris;
  protected String[] filename;
  protected Text nameText;
  protected boolean editMode = false;
  private int dialogStyle;
  private Combo creationFlagCombo;
  private Combo deleteOnTerminationCombo;
  private DataStagingType dataStagingType;
  private ArrayList<DataStagingType> dataStageList = new ArrayList<DataStagingType>();
  private SourceTargetType sourceTargetType;

  /**
   * @param parentShell
   * @param style
   */
  public DataStagingOutDialog( final Shell parentShell, final int style ) {
    super( parentShell );
    this.dialogStyle = style;
  }

  /**
   * @param parentShell
   * @param style
   * @param existingDataStage
   */
  public DataStagingOutDialog( final Shell parentShell,
                               final int style,
                               final DataStagingType existingDataStage )
  {
    super( parentShell );
    this.dialogStyle = style;
    this.editMode = true;
    this.dataStagingType = existingDataStage;
    this.uris = new URI[ 1 ];
    this.filename = new String[ 1 ];
    this.uris[ 0 ] = URI.create( this.dataStagingType.getTarget().getURI() );
    this.filename[ 0 ] = this.dataStagingType.getFileName();
  }

  @Override
  protected void configureShell( final Shell shell ) {
    super.configureShell( shell );
    shell.setText( Messages.getString( "DataStageOutTable.dialog_title" ) ); //$NON-NLS-1$
  }

  @Override
  protected Control createDialogArea( final Composite parent ) {
    Composite composite = ( Composite )super.createDialogArea( parent );
    composite.setLayout( new GridLayout( 1, false ) );
    composite.setLayoutData( new GridData( GridData.FILL_BOTH ) );
    Composite panel = new Composite( composite, SWT.NONE );
    initializeDialogUnits( composite );
    GridData gd;
    GridLayout gLayout = new GridLayout( 3, false );
    panel.setLayout( gLayout );
    gd = new GridData( GridData.FILL_HORIZONTAL );
    panel.setLayoutData( gd );
    Label nameLabel = new Label( panel, SWT.LEAD );
    nameLabel.setText( Messages.getString( "DataStageInDialog.name_field_label" ) ); //$NON-NLS-1$
    nameLabel.setLayoutData( new GridData() );
    this.nameText = new Text( panel, SWT.BORDER );
    gd = new GridData();
    gd.widthHint = 260;
    gd.horizontalSpan = 2;
    gd.horizontalAlignment = SWT.FILL;
    this.nameText.setLayoutData( gd );
    if( this.dataStagingType != null ) {
      this.nameText.setText( this.dataStagingType.getFileName() );
    }
    this.nameText.addModifyListener( new ModifyListener() {

      public void modifyText( final ModifyEvent e ) {
        if( DataStagingOutDialog.this.editMode == true ) {
          DataStagingOutDialog.this.filename[ 0 ] = DataStagingOutDialog.this.nameText.getText();
        }
      }
    } );
    Label pathLabel = new Label( panel, SWT.LEAD );
    pathLabel.setText( Messages.getString( "DataStageInDialog.target_location_field_label" ) ); //$NON-NLS-1$
    gd = new GridData();
    pathLabel.setLayoutData( gd );
    this.pathText = new Text( panel, SWT.BORDER );
    if( this.dataStagingType != null ) {
      this.pathText.setText( this.dataStagingType.getTarget().getURI() );
    }
    this.pathText.addModifyListener( new ModifyListener() {

      public void modifyText( final ModifyEvent e ) {
        if( DataStagingOutDialog.this.editMode == true ) {
          try{
          DataStagingOutDialog.this.uris[ 0 ] = URI.create( DataStagingOutDialog.this.pathText.getText() );
          }catch (IllegalArgumentException exc){
//            ignore
          }
        }
      }
    } );
    gd = new GridData( GridData.FILL_HORIZONTAL );
    this.pathText.setLayoutData( gd );
    Button browseButton = new Button( panel, SWT.PUSH );
    URL openFileIcon = Activator.getDefault()
      .getBundle()
      .getEntry( "icons/obj16/open_file.gif" ); //$NON-NLS-1$
    Image openFileImage = ImageDescriptor.createFromURL( openFileIcon )
      .createImage();
    browseButton.setImage( openFileImage );
    browseButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent e ) {
        String currentURI = null;
        GridFileDialog dialog = new GridFileDialog( PlatformUI.getWorkbench()
          .getActiveWorkbenchWindow()
          .getShell(), GridFileDialog.STYLE_ALLOW_ONLY_FILES );
        if( dialog.open() == Window.OK ) {
          DataStagingOutDialog.this.uris = dialog.getSelectedURIs();
          for( int j = 0; j < DataStagingOutDialog.this.uris.length; j++ ) {
            URI uri = DataStagingOutDialog.this.uris[ j ];
            String query = uri.getQuery();
            if( query != null && query.trim().length() > 0 ) {
              String[] qParts = query.split( "&" ); //$NON-NLS-1$
              query = ""; //$NON-NLS-1$
              for( int i = 0; i < qParts.length; i++ ) {
                String qPart = qParts[ i ];
                if( !qPart.startsWith( "vo" ) ) { //$NON-NLS-1$
                  if( query.trim().length() > 0 ) {
                    query += "&"; //$NON-NLS-1$
                  }
                  query += qPart;
                }
              }
              if( query.trim().length() == 0 ) {
                query = null;
              }
            }
            try {
              uri = new URI( uri.getScheme(),
                             uri.getUserInfo(),
                             uri.getHost(),
                             uri.getPort(),
                             uri.getPath(),
                             query,
                             uri.getFragment() );
              DataStagingOutDialog.this.uris[ j ] = uri;
            } catch( URISyntaxException e1 ) {
              // TODO
              Activator.logException( e1 );
            }
          }
          if( ( DataStagingOutDialog.this.uris != null )
              && ( DataStagingOutDialog.this.uris.length > 0 ) )
          {
            DataStagingOutDialog.this.filename = new String[ DataStagingOutDialog.this.uris.length ];
            for( int i = 0; i < DataStagingOutDialog.this.uris.length; i++ ) {
              currentURI = DataStagingOutDialog.this.uris[ i ].toString();
              URI uri;
              try {
                uri = new URI( currentURI );
                DataStagingOutDialog.this.filename[ i ] = new Path( uri.getPath() ).lastSegment();
              } catch( URISyntaxException e1 ) {
                // TODO
                Activator.logException( e1 );
              }
              if( i == 0 ) {
                DataStagingOutDialog.this.pathText.setText( DataStagingOutDialog.this.uris[ i ].toString() );
                /*
                 * If in edit mode , then change only the source location and
                 * not the Stage-in Name.
                 */
                if( ( !DataStagingOutDialog.this.editMode ) ) {
                  DataStagingOutDialog.this.nameText.setText( DataStagingOutDialog.this.filename[ i ] );
                }
              } else {
                DataStagingOutDialog.this.pathText.setText( DataStagingOutDialog.this.pathText.getText()
                                                            + ", " + DataStagingOutDialog.this.uris[ i ].toString() ); //$NON-NLS-1$
                DataStagingOutDialog.this.nameText.setText( DataStagingOutDialog.this.nameText.getText()
                                                            + ", " + DataStagingOutDialog.this.filename[ i ] ); //$NON-NLS-1$
              }
            }
          }
        }
        updateButtons();
      }
    } );
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
      int indexOfOverwite;
      if( this.dataStagingType != null ) {
        indexOfOverwite = this.creationFlagCombo.indexOf( this.dataStagingType.getCreationFlag()
          .getLiteral() );
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
      if( this.dataStagingType != null ) {
        int indexOfDelete = this.deleteOnTerminationCombo.indexOf( Boolean.toString( this.dataStagingType.isDeleteOnTermination() ) );
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
   * Access to the List of Data Stage-In elements.
   * 
   * @return DataStage-In list set by the user.
   */
  public ArrayList<DataStagingType> getDataStageInList() {
    return this.dataStageList;
  }

  @SuppressWarnings("boxing")
  @Override
  protected void okPressed() {
    if( ( null != this.uris ) && ( this.uris.length > 1 ) ) {
      for( int i = 0; i < this.uris.length; i++ ) {
        this.dataStagingType = JsdlFactory.eINSTANCE.createDataStagingType();
        this.sourceTargetType = JsdlFactory.eINSTANCE.createSourceTargetType();
        this.sourceTargetType.setURI( this.uris[ i ].toString() );
        this.dataStagingType.setName( this.filename[ i ] );
        this.dataStagingType.setSource( null );
        this.dataStagingType.setTarget( this.sourceTargetType );
        this.dataStagingType.setFileName( this.filename[ i ] );
        // newDataStagingType.setFilesystemName( value[0].toString() );
        if( this.dialogStyle == ADVANCED_DIALOG ) {
          this.dataStagingType.setCreationFlag( CreationFlagEnumeration.get( this.creationFlagCombo.getSelectionIndex() ) );
          if( this.deleteOnTerminationCombo.getSelectionIndex() != -1 ) {
            this.dataStagingType.setDeleteOnTermination( Boolean.valueOf( Boolean.parseBoolean( this.deleteOnTerminationCombo.getItem( this.deleteOnTerminationCombo.getSelectionIndex() ) ) ) );
          }
        }
        this.dataStageList.add( this.dataStagingType );
      }
    } else {
      this.dataStagingType = JsdlFactory.eINSTANCE.createDataStagingType();
      this.sourceTargetType = JsdlFactory.eINSTANCE.createSourceTargetType();
      this.sourceTargetType.setURI( this.pathText.getText() );
      this.dataStagingType.setSource( null );
      this.dataStagingType.setTarget( this.sourceTargetType );
      this.dataStagingType.setFileName( this.nameText.getText() );
      this.dataStagingType.setName( this.nameText.getText() );
      // newDataStagingType.setFilesystemName( value[0].toString() );
      if( this.dialogStyle == ADVANCED_DIALOG ) {
        this.dataStagingType.setCreationFlag( CreationFlagEnumeration.get( this.creationFlagCombo.getSelectionIndex() ) );
        if( this.deleteOnTerminationCombo.getSelectionIndex() != -1 ) {
          this.dataStagingType.setDeleteOnTermination( Boolean.valueOf( Boolean.parseBoolean( this.deleteOnTerminationCombo.getItem( this.deleteOnTerminationCombo.getSelectionIndex() ) ) ) );
        }
      }
      this.dataStageList.add( this.dataStagingType );
    }
    super.okPressed();
  }

  void updateButtons() {
    if( !this.nameText.getText().equals( "" ) )//$NON-NLS-1$
    // Commented-out for Gria..refer to Bug#: 241940
    //        && !this.pathText.getText().equals( "" ) ) //$NON-NLS-1$
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

    public void modifyText( final ModifyEvent e ) {
      updateButtons();
    }
  }
}
