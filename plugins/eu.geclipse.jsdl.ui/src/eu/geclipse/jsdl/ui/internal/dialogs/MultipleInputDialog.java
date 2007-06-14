/******************************************************************************
 * Copyright (c) 2006 g-Eclipse consortium 
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
 *     PSNC - Katarzyna Bylec
 *           
 *****************************************************************************/
package eu.geclipse.jsdl.ui.internal.dialogs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.jsdl.ui.internal.Activator;
import eu.geclipse.ui.dialogs.GridFileDialog;
import eu.geclipse.ui.dialogs.NewProblemDialog;
import eu.geclipse.ui.widgets.StoredCombo;

/**
 * This class is in fact adaptation of Eclipse's class
 * org.eclipse.debug.internal.ui.MultipleInputDialog for gEclipse.
 * Reimplementation was needed to avoid internal dependencies warnings. Some
 * methods were changed and some other added to suit gEclipse needs.
 */
public class MultipleInputDialog extends Dialog {

  protected static final String FIELD_NAME = "FIELD_NAME"; //$NON-NLS-1$
  protected static final int TEXT = 100;
  protected static final int BROWSE = 101;
  protected static final int VARIABLE = 102;
  protected static final int COMBO = 103;
  protected Composite panel;
  protected List<FieldSummary> fieldList = new ArrayList<FieldSummary>();
  protected List<Text> controlList = new ArrayList<Text>();
  protected List<Validator> validators = new ArrayList<Validator>();
  protected List<Combo> combosList = new ArrayList<Combo>();
  protected Map<Object, String> valueMap = new HashMap<Object, String>();
  protected Map<String, ArrayList<String>> combosData = new HashMap<String, ArrayList<String>>();
  private String title;
  private Map<String, String> connectedFields = new HashMap<String, String>();

  /**
   * Method to create new MultipleInputDialog
   * 
   * @param shell shell for the dialog
   * @param title title of the dialog
   */
  public MultipleInputDialog( final Shell shell, final String title ) {
    super( shell );
    this.title = title;
    setShellStyle( getShellStyle() | SWT.RESIZE );
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
   */
  @Override
  protected void configureShell( final Shell shell )
  {
    super.configureShell( shell );
    if( this.title != null ) {
      shell.setText( this.title );
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.dialogs.Dialog#createButtonBar(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Control createButtonBar( final Composite parent )
  {
    Control bar = super.createButtonBar( parent );
    validateFields();
    return bar;
  }

  /**
   * Method for adding new text field to dialog. This field is connected with
   * {@link GridFileDialog}
   * 
   * @param labelText label for the filed
   * @param initialValue initial value to set field to
   * @param allowsEmpty parameter allowing this field to be empty or not
   */
  public void addBrowseField( final String labelText,
                              final String initialValue,
                              final boolean allowsEmpty,
                              final boolean allowLocal )
  {
    this.fieldList.add( new FieldSummary( BROWSE,
                                          labelText,
                                          initialValue,
                                          allowsEmpty,
                                          allowLocal ) );
  }

  // public void setConnectionFromBrowserToText( final String sourceFieldName,
  // final String targetFieldName ){
  // source = null;
  // target = null;
  // for (Control control: this.controlList){
  // if (control.getData(FIELD_NAME).equals( sourceFieldName )){
  // source = ( Text )control;
  // } else {
  // if (control.getData(FIELD_NAME).equals( sourceFieldName )){
  // target = ( Text )control;
  // }
  // }
  // }
  // if (target != null && source != null){
  //      
  // source.addFocusListener( new FocusListener(){
  //
  // public void focusGained( FocusEvent e ) {
  // // do nothing
  // }
  //
  // public void focusLost( FocusEvent e ) {
  // if (source.getText() != null && source.getText().length() > 0){
  // target.setText( source.getText() );
  // }
  // }
  //        
  // });
  // }
  // }
  /**
   * Method for adding new combo field to this dialog
   * 
   * @param labelText label to describe combo field
   * @param values values to put on the list of the combo field
   * @param initialValue one of the values from values list. Combo field will be
   *          set to this value. If <code>null</code> - combo field is set to
   *          point on the first value from the values set
   */
  public void addComboField( final String labelText,
                             final ArrayList<String> values,
                             final String initialValue )
  {
    this.combosData.put( labelText, values );
    this.fieldList.add( new FieldSummary( COMBO, labelText, initialValue, false ) );
  }

  private boolean checkReadyToConnect( final String name, int positionInDialog )
  {
    boolean result = false;
    if( this.connectedFields.containsKey( name ) ) {
      String connectionName = this.connectedFields.get( name );
      int connectionPosition = this.fieldList.size();
      for( int i = 0; i < positionInDialog; i++ ) {
        if( this.fieldList.get( i ).name.equals( connectionName ) ) {
          connectionPosition = i;
        }
      }
      if( connectionPosition < positionInDialog ) {
        result = true;
      }
    } else {
      if( this.connectedFields.containsValue( name ) ) {
        String connectionName = "";
        for( String connName : this.connectedFields.keySet() ) {
          if( this.connectedFields.get( connName ).equals( name ) ) {
            connectionName = connName;
          }
        }
        int connectionPosition = this.fieldList.size();
        for( int i = 0; i < positionInDialog; i++ ) {
          if( this.fieldList.get( i ).name.equals( connectionName ) ) {
            connectionPosition = i;
          }
        }
        if( connectionPosition < positionInDialog ) {
          result = true;
        }
      } else {
        // do nothing
      }
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Control createDialogArea( final Composite parent )
  {
    Composite container = ( Composite )super.createDialogArea( parent );
    container.setLayout( new GridLayout( 2, false ) );
    container.setLayoutData( new GridData( GridData.FILL_BOTH ) );
    this.panel = new Composite( container, SWT.NONE );
    GridLayout layout = new GridLayout( 2, false );
    this.panel.setLayout( layout );
    this.panel.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
    for( Iterator<FieldSummary> i = this.fieldList.iterator(); i.hasNext(); ) {
      FieldSummary field = i.next();
      boolean connected = checkReadyToConnect( field.name,
                                               this.fieldList.indexOf( field ) );
      switch( field.type ) {
        case TEXT:
          createTextField( field.name,
                           field.initialValue,
                           field.allowsEmpty,
                           connected );
        break;
        case BROWSE:
          createBrowseField( field.name,
                             field.initialValue,
                             field.allowsEmpty,
                             connected,
                             field.allowLocal );
        break;
        case VARIABLE:
          createVariablesField( field.name,
                                field.initialValue,
                                field.allowsEmpty );
        break;
        case COMBO:
          createComboField( field.name, field.initialValue, field.allowsEmpty );
        break;
      }
    }
    this.fieldList = null; // allow it to be gc'd
    Dialog.applyDialogFont( container );
    return container;
  }

  /**
   * Validates all dialog fields and makes OK button disabled or enabled
   */
  public void validateFields() {
    for( Iterator<Validator> i = this.validators.iterator(); i.hasNext(); ) {
      Validator validator = i.next();
      if( !validator.validate() ) {
        getButton( IDialogConstants.OK_ID ).setEnabled( false );
        return;
      }
    }
    getButton( IDialogConstants.OK_ID ).setEnabled( true );
  }

  protected void createTextField( final String labelText,
                                  final String initialValue,
                                  final boolean allowEmpty,
                                  final boolean connected )
  {
    Label label = new Label( this.panel, SWT.NONE );
    label.setText( labelText );
    label.setLayoutData( new GridData( GridData.HORIZONTAL_ALIGN_BEGINNING ) );
    final Text text = new Text( this.panel, SWT.SINGLE | SWT.BORDER );
    text.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
    text.setData( FIELD_NAME, labelText );
    // make sure rows are the same height on both panels.
    label.setSize( label.getSize().x, text.getSize().y );
    if( initialValue != null ) {
      text.setText( initialValue );
    }
    if( !allowEmpty ) {
      this.validators.add( new Validator() {

        @Override
        public boolean validate()
        {
          return !text.getText().equals( "" ); //$NON-NLS-1$
        }
      } );
      text.addModifyListener( new ModifyListener() {

        public void modifyText( final ModifyEvent e ) {
          validateFields();
        }
      } );
    }
    this.controlList.add( text );
    if( connected ) {
      // mamy target, trzeba dodac listenera do source
      if( this.connectedFields.containsKey( labelText ) ) {
        String sourceName = this.connectedFields.get( labelText );
        Text sourceControl = null;
        int sourceIndex = -1;
        int targetIndex = -1;
        for( Control control : this.controlList ) {
          if( control.getData( FIELD_NAME ).equals( sourceName ) ) {
            sourceControl = ( Text )control;
            sourceIndex = this.controlList.indexOf( control );
          } else {
            if( control.getData( FIELD_NAME ).equals( labelText ) ) {
              targetIndex = this.controlList.indexOf( control );
            }
          }
        }
        final int targetPosition = targetIndex;
        final int sourcePosition = sourceIndex;
        sourceControl.addModifyListener( new ModifyListener() {

          public void modifyText( ModifyEvent e ) {
            // String value = "";
            // Path p = new Path(controlList.get( sourcePosition ).getText());
            // value = p.lastSegment();
            // if (controlList.get( targetPosition ).getText() != null &&
            // controlList.get( targetPosition ).getText().length() == 0 ){
            // controlList.get( targetPosition ).setText( value );
            // }
          }
        } );
        sourceControl.addFocusListener( new FocusListener() {

          public void focusGained( FocusEvent e ) {
            // do nothing
          }

          public void focusLost( FocusEvent e ) {
            String value = "";
            Path p = new Path( controlList.get( sourcePosition ).getText() );
            value = p.lastSegment();
            if( controlList.get( targetPosition ).getText() != null
                && controlList.get( targetPosition ).getText().length() == 0 )
            {
              controlList.get( targetPosition ).setText( value );
            }
          }
        } );
      } else {
        // mamy source, do niego dodac listenera, znalezc target
        // szukanie targetu
        String targetName = null;
        for( String name : connectedFields.keySet() ) {
          if( connectedFields.get( name ).equals( labelText ) ) {
            targetName = name;
          }
        }
        Text targetControl = null;
        int sourceIndex = -1;
        int targetIndex = -1;
        for( Control control : this.controlList ) {
          if( control.getData( FIELD_NAME ).equals( targetName ) ) {
            targetControl = ( Text )control;
            targetIndex = this.controlList.indexOf( control );
          } else {
            if( control.getData( FIELD_NAME ).equals( labelText ) ) {
              sourceIndex = this.controlList.indexOf( control );
            }
          }
        }
        final int sourcePosition = sourceIndex;
        final int targetPosition = targetIndex;
        text.addModifyListener( new ModifyListener() {

          public void modifyText( ModifyEvent e ) {
            // controlList.get( targetPosition ).setText( controlList.get(
            // sourcePosition ).getText() );
          }
        } );
        text.addFocusListener( new FocusListener() {

          public void focusGained( FocusEvent e ) {
            // do nothing
          }

          public void focusLost( FocusEvent e ) {
            String value = "";
            Path p = new Path( controlList.get( sourcePosition ).getText() );
            value = p.lastSegment();
            if( controlList.get( targetPosition ).getText() != null
                && controlList.get( targetPosition ).getText().length() == 0 )
            {
              controlList.get( targetPosition ).setText( value );
            }
          }
        } );
      }
    }
  }

  public void setConnectedFields( final String sourceFieldName,
                                  final String targetFieldName )
  {
    this.connectedFields.put( targetFieldName, sourceFieldName );
  }

  protected void createBrowseField( final String labelText,
                                    final String initialValue,
                                    final boolean allowEmpty,
                                    final boolean connected,
                                    final boolean allowLocal )
  {
    Label label = new Label( this.panel, SWT.NONE );
    label.setText( labelText );
    label.setLayoutData( new GridData( GridData.HORIZONTAL_ALIGN_BEGINNING ) );
    Composite comp = new Composite( this.panel, SWT.NONE );
    GridLayout layout = new GridLayout();
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    comp.setLayout( layout );
    comp.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
    final Text text = new Text( comp, SWT.SINGLE | SWT.BORDER );
    GridData data = new GridData( GridData.FILL_HORIZONTAL );
    data.widthHint = 200;
    text.setLayoutData( data );
    text.setData( FIELD_NAME, labelText );
    // make sure rows are the same height on both panels.
    label.setSize( label.getSize().x, text.getSize().y );
    if( initialValue != null ) {
      text.setText( initialValue );
    }
    if( !allowEmpty ) {
      this.validators.add( new Validator() {

        @Override
        public boolean validate()
        {
          return !text.getText().equals( "" ); //$NON-NLS-1$
        }
      } );
      text.addModifyListener( new ModifyListener() {

        public void modifyText( final ModifyEvent e ) {
          validateFields();
        }
      } );
    }
    Button button = createButton( comp,
                                  IDialogConstants.IGNORE_ID,
                                  Messages.getString( "MultilpeInputDialog.browse_button" ), //$NON-NLS-1$
                                  false );
    button.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent e )
      {
        {
          IGridConnectionElement connection = GridFileDialog.openFileDialog( getShell(),
                                                                             "Choose a file",
                                                                             null,
                                                                             allowLocal );
          if( connection != null ) {
            try {
              String filename = connection.getConnectionFileStore().toString();
              if( filename != null ) {
                text.setText( filename );
              }
            } catch( CoreException cExc ) {
              NewProblemDialog.openProblem( getShell(), "error", "error", cExc );
            }
          }
        }
      }
    } );
    this.controlList.add( text );
    if( connected ) {
      // mamy target, trzeba dodac listenera do source
      if( this.connectedFields.containsKey( labelText ) ) {
        String sourceName = this.connectedFields.get( labelText );
        Text sourceControl = null;
        int sourceIndex = -1;
        int targetIndex = -1;
        for( Control control : this.controlList ) {
          if( control.getData( FIELD_NAME ).equals( sourceName ) ) {
            sourceControl = ( Text )control;
            sourceIndex = this.controlList.indexOf( control );
          } else {
            if( control.getData( FIELD_NAME ).equals( labelText ) ) {
              targetIndex = this.controlList.indexOf( control );
            }
          }
        }
        final int targetPosition = targetIndex;
        final int sourcePosition = sourceIndex;
        if( sourceControl != null ) {
          sourceControl.addModifyListener( new ModifyListener() {

            public void modifyText( ModifyEvent e ) {
              // controlList.get( targetPosition ).setText( controlList.get(
              // sourcePosition ).getText() );
            }
          } );
          sourceControl.addFocusListener( new FocusListener() {

            public void focusGained( FocusEvent e ) {
              // do nothing
            }

            public void focusLost( FocusEvent e ) {
              String value = "";
              Path p = new Path( MultipleInputDialog.this.controlList.get( sourcePosition )
                .getText() );
              value = p.lastSegment();
              if( MultipleInputDialog.this.controlList.get( targetPosition )
                .getText() != null
                  && MultipleInputDialog.this.controlList.get( targetPosition )
                    .getText()
                    .length() == 0 )
              {
                MultipleInputDialog.this.controlList.get( targetPosition )
                  .setText( value );
              }
            }
          } );
        }
      } else {
        // mamy source, do niego dodac listenera, znalezc target
        // szukanie targetu
        String targetName = null;
        for( String name : this.connectedFields.keySet() ) {
          if( this.connectedFields.get( name ).equals( labelText ) ) {
            targetName = name;
          }
        }
        // Text targetControl = null;
        int sourceIndex = -1;
        int targetIndex = -1;
        for( Control control : this.controlList ) {
          if( control.getData( FIELD_NAME ).equals( targetName ) ) {
            // targetControl = ( Text )control;
            targetIndex = this.controlList.indexOf( control );
          } else {
            if( control.getData( FIELD_NAME ).equals( labelText ) ) {
              sourceIndex = this.controlList.indexOf( control );
            }
          }
        }
        final int sourcePosition = sourceIndex;
        final int targetPosition = targetIndex;
        text.addModifyListener( new ModifyListener() {

          public void modifyText( ModifyEvent e ) {
            // controlList.get( targetPosition ).setText( controlList.get(
            // sourcePosition ).getText() );
          }
        } );
        text.addFocusListener( new FocusListener() {

          public void focusGained( FocusEvent e ) {
            // do nothing
          }

          public void focusLost( FocusEvent e ) {
            String value = "";
            Path p = new Path( MultipleInputDialog.this.controlList.get( sourcePosition )
              .getText() );
            value = p.lastSegment();
            if( MultipleInputDialog.this.controlList.get( targetPosition )
              .getText() != null
                && MultipleInputDialog.this.controlList.get( targetPosition )
                  .getText()
                  .length() == 0 )
            {
              MultipleInputDialog.this.controlList.get( targetPosition )
                .setText( value );
            }
          }
        } );
      }
    }
  }

  // TODO katis - is this method used??
  /**
   * @param labelText
   * @param initialValue
   * @param allowEmpty
   */
  public void createVariablesField( final String labelText,
                                    final String initialValue,
                                    final boolean allowEmpty )
  {
    Label label = new Label( this.panel, SWT.NONE );
    label.setText( labelText );
    label.setLayoutData( new GridData( GridData.HORIZONTAL_ALIGN_BEGINNING ) );
    Composite comp = new Composite( this.panel, SWT.NONE );
    GridLayout layout = new GridLayout();
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    comp.setLayout( layout );
    comp.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
    final Text text = new Text( comp, SWT.SINGLE | SWT.BORDER );
    GridData data = new GridData( GridData.FILL_HORIZONTAL );
    data.widthHint = 200;
    text.setLayoutData( data );
    text.setData( FIELD_NAME, labelText );
    // make sure rows are the same height on both panels.
    label.setSize( label.getSize().x, text.getSize().y );
    if( initialValue != null ) {
      text.setText( initialValue );
    }
    if( !allowEmpty ) {
      this.validators.add( new Validator() {

        @Override
        public boolean validate()
        {
          return !text.getText().equals( "" ); //$NON-NLS-1$
        }
      } );
      text.addModifyListener( new ModifyListener() {

        public void modifyText( final ModifyEvent e ) {
          validateFields();
        }
      } );
    }
    this.controlList.add( text );
  }

  protected void createComboField( final String labelText,
                                   final String initialValue,
                                   final boolean allowEmpty )
  {
    Label label = new Label( this.panel, SWT.NONE );
    label.setText( labelText );
    label.setLayoutData( new GridData( GridData.HORIZONTAL_ALIGN_BEGINNING ) );
    Composite comp = new Composite( this.panel, SWT.NONE );
    GridLayout layout = new GridLayout();
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    comp.setLayout( layout );
    comp.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
    final Combo combo = new StoredCombo( comp, SWT.SINGLE | SWT.BORDER );
    for( String comboText : this.combosData.get( labelText ) ) {
      combo.add( comboText );
    }
    if( initialValue != null ) {
      for( int i = 0; i < combo.getItemCount(); i++ ) {
        if( combo.getItem( i ).equals( initialValue ) ) {
          combo.select( i );
        }
      }
    } else {
      combo.select( 0 );
    }
    GridData data = new GridData( GridData.FILL_HORIZONTAL );
    data.widthHint = 200;
    combo.setLayoutData( data );
    combo.setData( FIELD_NAME, labelText );
    // make sure rows are the same height on both panels.
    label.setSize( label.getSize().x, combo.getSize().y );
    if( initialValue != null ) {
      combo.setText( initialValue );
    }
    if( !allowEmpty ) {
      this.validators.add( new Validator() {

        @Override
        public boolean validate()
        {
          return !combo.getText().equals( "" ); //$NON-NLS-1$
        }
      } );
      combo.addModifyListener( new ModifyListener() {

        public void modifyText( final ModifyEvent e ) {
          validateFields();
        }
      } );
    }
    this.combosList.add( combo );
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.dialogs.Dialog#okPressed()
   */
  @Override
  protected void okPressed()
  {
    for( Iterator<Text> i = this.controlList.iterator(); i.hasNext(); ) {
      Control control = i.next();
      if( control instanceof Text ) {
        this.valueMap.put( control.getData( FIELD_NAME ),
                           ( ( Text )control ).getText() );
      }
    }
    for( Iterator<Combo> i = this.combosList.iterator(); i.hasNext(); ) {
      Control control = i.next();
      if( control instanceof Combo ) {
        this.valueMap.put( control.getData( FIELD_NAME ),
                           ( ( Combo )control ).getText() );
      }
    }
    this.controlList = null;
    this.combosList = null;
    super.okPressed();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.window.Window#open()
   */
  @Override
  public int open()
  {
    applyDialogFont( this.panel );
    return super.open();
  }

  /**
   * Method to access value held by field with given name (key)
   * 
   * @param key name of the field
   * @return value held by field of a given key
   */
  public Object getValue( final String key ) {
    return this.valueMap.get( key );
  }

  /**
   * Method to access String value held by field with given name (key)
   * 
   * @param key name of the field
   * @return String value held by field of a given key
   */
  public String getStringValue( final String key ) {
    return ( String )getValue( key );
  }

  private String getDialogSettingsSectionName() {
    return IDebugUIConstants.PLUGIN_ID + ".MULTIPLE_INPUT_DIALOG_2"; //$NON-NLS-1$
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.dialogs.Dialog#getDialogBoundsSettings()
   */
  @Override
  protected IDialogSettings getDialogBoundsSettings()
  {
    IDialogSettings settings = Activator.getDefault().getDialogSettings();
    IDialogSettings section = settings.getSection( getDialogSettingsSectionName() );
    if( section == null ) {
      section = settings.addNewSection( getDialogSettingsSectionName() );
    }
    return section;
  }

  /**
   * Method to add text field ({@link Text}) to {@link MultipleInputDialog}
   * 
   * @param labelText label to describe this field
   * @param initialValue initial value of this field
   * @param allowsEmpty indicates whether this field can be empty (if
   *          <code>false</code> dialog cannot be closed until this field
   *          holds some value)
   */
  public void addTextField( final String labelText,
                            final String initialValue,
                            final boolean allowsEmpty )
  {
    this.fieldList.add( new FieldSummary( TEXT,
                                          labelText,
                                          initialValue,
                                          allowsEmpty ) );
  }

  // TODO katis - is this method used
  /**
   * @param labelText
   * @param initialValue
   * @param allowsEmpty
   */
  public void addVariablesField( final String labelText,
                                 final String initialValue,
                                 final boolean allowsEmpty )
  {
    this.fieldList.add( new FieldSummary( VARIABLE,
                                          labelText,
                                          initialValue,
                                          allowsEmpty ) );
  }
  protected class FieldSummary {

    int type;
    String name;
    String initialValue;
    boolean allowsEmpty;
    boolean allowLocal;

    /**
     * Creates new instance of {@link FieldSummary}. By default this field will
     * allow local filesystem values. This constructor should be used by all
     * field types except form {@link MultipleInputDialog#BROWSE}.
     * 
     * @param type type of the field (see {@link MultipleInputDialog#BROWSE},
     *          {@link MultipleInputDialog#COMBO},
     *          {@link MultipleInputDialog#FIELD_NAME},
     *          {@link MultipleInputDialog#TEXT} and
     *          {@link MultipleInputDialog#VARIABLE})
     * @param name name of the field (used to access this field's value by
     *          method {@link MultipleInputDialog#getValue(String)} or
     *          {@link MultipleInputDialog#getStringValue(String)}
     * @param initialValue initial value to set this field to
     * @param allowsEmpty indicates whether this field can be empty (if
     *          <code>false</code> dialog cannot be closed until this field
     *          holds some value)
     */
    public FieldSummary( final int type,
                         final String name,
                         final String initialValue,
                         final boolean allowsEmpty )
    {
      this( type, name, initialValue, allowsEmpty, true );
    }

    /**
     * Creates new instance of {@link FieldSummary}
     * 
     * @param type type of the field (see {@link MultipleInputDialog#BROWSE},
     *          {@link MultipleInputDialog#COMBO},
     *          {@link MultipleInputDialog#FIELD_NAME},
     *          {@link MultipleInputDialog#TEXT} and
     *          {@link MultipleInputDialog#VARIABLE})
     * @param name name of the field (used to access this field's value by
     *          method {@link MultipleInputDialog#getValue(String)} or
     *          {@link MultipleInputDialog#getStringValue(String)}
     * @param initialValue initial value to set this field to
     * @param allowsEmpty indicates whether this field can be empty (if
     *          <code>false</code> dialog cannot be closed until this field
     *          holds some value)
     * @param allowLocal if <code>false</code> this field will accept only
     *          remote filesystems values, all values otherwise. For now this
     *          setting works only for {@link MultipleInputDialog#BROWSE} field.
     */
    public FieldSummary( final int type,
                         final String name,
                         final String initialValue,
                         final boolean allowsEmpty,
                         boolean allowLocal )
    {
      this.type = type;
      this.name = name;
      this.initialValue = initialValue;
      this.allowsEmpty = allowsEmpty;
      this.allowLocal = allowLocal;
    }
  }
  protected class Validator {

    boolean validate() {
      return true;
    }
  }
}
