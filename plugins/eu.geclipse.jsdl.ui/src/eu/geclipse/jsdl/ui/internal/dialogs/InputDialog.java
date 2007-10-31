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
package eu.geclipse.jsdl.ui.internal.dialogs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import eu.geclipse.ui.dialogs.GridFileDialog;
import eu.geclipse.ui.widgets.StoredCombo;

/**
 * Class supports usage of {@link Dialog} with any number of text fields. This
 * class is similar to {@link MultipleInputDialog}, but it's suited to
 * g-Eclipse needs and uses widgets characteristic of g-Eclipse (e.g.
 * {@link GridFileDialog}}).
 */
public class InputDialog extends Dialog {

  /**
   * Key used when setting Widgets data. Identifies this widget name used by
   * dialog to manipulate this field.
   */
  public static final String FIELD_NAME = "FIELD_NAME"; //$NON-NLS-1$
  private Composite panel;
  private List<FieldSummary> fieldList = new ArrayList<FieldSummary>();
  private String title;
  private List<Text> textList = new ArrayList<Text>();
  private Map<Object, String> valueMap = new HashMap<Object, String>();
  private List<StoredCombo> storedList = new ArrayList<StoredCombo>();
  private List<Combo> comboList = new ArrayList<Combo>();

  public InputDialog( final Shell parentShell ) {
    super( parentShell );
  }

  /**
   * Method to add text field ({@link Text}) to {@link MultipleInputDialog}
   * 
   * @param labelText label to describe this field
   * @param initialValue initial value of this field
   * @param allowsEmpty indicates whether this field can be empty (if
   *            <code>false</code> dialog cannot be closed until this field
   *            holds some value)
   */
  public void addTextField( final String labelText,
                            final String initialValue,
                            final boolean allowsEmpty )
  {
    this.fieldList.add( new FieldSummary( FieldType.TEXT,
                                          labelText,
                                          initialValue,
                                          allowsEmpty ) );
  }

  /**
   * Method to add stored combo field ({@link StoredCombo}) to
   * {@link MultipleInputDialog}
   * 
   * @param labelText label to describe this field
   * @param initialValue initial value of this field
   * @param allowsEmpty indicates whether this field can be empty (if
   *            <code>false</code> dialog cannot be closed until this field
   *            holds some value)
   */
  public void addStoredComboField( final String labelText,
                                   final String initialValue,
                                   final boolean allowsEmpty,
                                   final String prefID )
  {
    this.fieldList.add( new FieldSummary( FieldType.STORED_COMBO,
                                          labelText,
                                          initialValue,
                                          allowsEmpty,
                                          prefID ) );
  }

  @Override
  protected void configureShell( final Shell shell ) {
    super.configureShell( shell );
    if( this.title != null ) {
      shell.setText( this.title );
    }
  }

  @Override
  protected Control createDialogArea( final Composite parent ) {
    Composite mainComp = ( Composite )super.createDialogArea( parent );
    mainComp.setLayout( new GridLayout( 2, false ) );
    mainComp.setLayoutData( new GridData( GridData.FILL_BOTH ) );
    this.panel = new Composite( mainComp, SWT.NONE );
    GridLayout layout = new GridLayout( 2, false );
    this.panel.setLayout( layout );
    this.panel.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
    for( FieldSummary fieldSumm : this.fieldList ) {
      switch( fieldSumm.type ) {
        case TEXT:
          createTextField( this.panel,
                           fieldSumm.name,
                           fieldSumm.initialValue,
                           fieldSumm.allowsEmpty );
        break;
        case BROWSE:
          createBrowseField( this.panel,
                             fieldSumm.name,
                             fieldSumm.initialValue,
                             fieldSumm.allowsEmpty,
                             fieldSumm.allowLocal );
        break;
      }
    }
    return mainComp;
  }

  private void createBrowseField( final Composite parent,
                                  final String name,
                                  final String initialValue,
                                  final boolean allowsEmpty,
                                  final boolean allowLocal )
  {
    GridData gData = new GridData();
    Label label = new Label( parent, SWT.LEAD );
    label.setText( name );
    label.setLayoutData( gData );
    Text text = new Text( parent, SWT.LEAD );
    text.setText( initialValue );
    gData = new GridData( GridData.GRAB_HORIZONTAL | GridData.FILL_HORIZONTAL );
    text.setLayoutData( gData );
    this.textList.add( text );
  }

  private void createTextField( final Composite parent,
                                final String name,
                                final String initialValue,
                                final boolean allowsEmpty )
  {
    GridData gData = new GridData();
    Label label = new Label( parent, SWT.LEAD );
    label.setText( name );
    label.setLayoutData( gData );
    Text text = new Text( parent, SWT.BORDER | SWT.LEAD );
    text.setText( initialValue );
    gData = new GridData( GridData.GRAB_HORIZONTAL | GridData.FILL_HORIZONTAL );
    text.setLayoutData( gData );
    this.textList.add( text );
    Button button = new Button( parent, SWT.PUSH );
    
  }

  protected Button createButton( final Composite parent,
                                 final String label,
                                 final boolean defaultButton )
  {
    // increment the number of columns in the button bar
    ( ( GridLayout )parent.getLayout() ).numColumns++;
    Button button = new Button( parent, SWT.PUSH );
    button.setText( label );
    button.setFont( JFaceResources.getDialogFont() );
//    button.addSelectionListener( new SelectionAdapter() {
//
//      @Override
//      public void widgetSelected( final SelectionEvent event ) {
//        buttonPressed( ( ( Integer )event.widget.getData() ).intValue() );
//      }
//    } );
    if( defaultButton ) {
      Shell shell = parent.getShell();
      if( shell != null ) {
        shell.setDefaultButton( button );
      }
    }
    setButtonLayoutData( button );
    return button;
  }

  
  @Override
  protected void okPressed() {
    for( Text textControl : this.textList ) {
      this.valueMap.put( textControl.getData( FIELD_NAME ),
                         textControl.getText() );
    }
    for( StoredCombo storedControl : this.storedList ) {
      this.valueMap.put( storedControl.getData( FIELD_NAME ),
                         storedControl.getText() );
    }
    for( Combo comboControl : this.comboList ) {
      this.valueMap.put( comboControl.getData( FIELD_NAME ),
                         comboControl.getText() );
    }
    // for( Iterator<Text> i = this.controlList.iterator(); i.hasNext(); ) {
    // Control control = i.next();
    // if( control instanceof Text ) {
    // this.valueMap.put( control.getData( FIELD_NAME ),
    // ( ( Text )control ).getText() );
    // }
    // }
    // for( Iterator<StoredCombo> i = this.storedComboList.iterator();
    // i.hasNext(); ) {
    // Control control = i.next();
    // if( control instanceof StoredCombo ) {
    // this.valueMap.put( control.getData( FIELD_NAME ),
    // ( ( StoredCombo )control ).getText() );
    // }
    // }
    // for( Iterator<Combo> i = this.combosList.iterator(); i.hasNext(); ) {
    // Control control = i.next();
    // if( control instanceof Combo ) {
    // this.valueMap.put( control.getData( FIELD_NAME ),
    // ( ( Combo )control ).getText() );
    // }
    // }
    // this.controlList = null;
    // this.combosList = null;
    super.okPressed();
  }
  protected class FieldSummary {

    FieldType type;
    String name;
    String initialValue;
    boolean allowsEmpty;
    boolean allowLocal;
    final String prefID;

    /**
     * Creates new instance of {@link FieldSummary}. By default this field will
     * allow local file system values. This constructor should be used by all
     * field types except from {@link MultipleInputDialog#BROWSE}.
     * 
     * @param type type of the field (see {@link MultipleInputDialog#BROWSE},
     *            {@link MultipleInputDialog#COMBO},
     *            {@link MultipleInputDialog#FIELD_NAME},
     *            {@link MultipleInputDialog#STORED_COMBO},
     *            {@link MultipleInputDialog#TEXT} and
     *            {@link MultipleInputDialog#VARIABLE})
     * @param name name of the field (used to access this field's value by
     *            method {@link MultipleInputDialog#getValue(String)} or
     *            {@link MultipleInputDialog#getStringValue(String)}
     * @param initialValue initial value to set this field to
     * @param allowsEmpty indicates whether this field can be empty (if
     *            <code>false</code> dialog cannot be closed until this field
     *            holds some value)
     */
    public FieldSummary( final FieldType type,
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
     *            {@link MultipleInputDialog#COMBO},
     *            {@link MultipleInputDialog#FIELD_NAME},
     *            {@link MultipleInputDialog#STORED_COMBO},
     *            {@link MultipleInputDialog#TEXT} and
     *            {@link MultipleInputDialog#VARIABLE})
     * @param name name of the field (used to access this field's value by
     *            method {@link MultipleInputDialog#getValue(String)} or
     *            {@link MultipleInputDialog#getStringValue(String)}
     * @param initialValue initial value to set this field to
     * @param allowsEmpty indicates whether this field can be empty (if
     *            <code>false</code> dialog cannot be closed until this field
     *            holds some value)
     * @param allowLocal if <code>false</code> this field will accept only
     *            remote file systems values, all values otherwise. For now this
     *            setting works only for {@link MultipleInputDialog#BROWSE}
     *            field.
     */
    public FieldSummary( final FieldType type,
                         final String name,
                         final String initialValue,
                         final boolean allowsEmpty,
                         final boolean allowLocal )
    {
      this.type = type;
      this.name = name;
      this.initialValue = initialValue;
      this.allowsEmpty = allowsEmpty;
      this.allowLocal = allowLocal;
      this.prefID = ""; //$NON-NLS-1$
    }

    /**
     * @param type
     * @param name
     * @param initialValue
     * @param allowsEmpty
     * @param prefID
     */
    public FieldSummary( final FieldType type,
                         final String name,
                         final String initialValue,
                         final boolean allowsEmpty,
                         final String prefID )
    {
      this.type = type;
      this.name = name;
      this.initialValue = initialValue;
      this.allowsEmpty = allowsEmpty;
      this.prefID = prefID;
    }
  }
  protected class Validator {

    boolean validate() {
      return true;
    }
  }
  private enum FieldType {
    /**
     * Represents {@link Text} control.
     */
    TEXT,
    /**
     * Represents {@link Text} control with button that opens a
     * {@link GridFileDialog} for choosing a file.
     */
    BROWSE,
    /**
     * Represents {@link Combo} control.
     */
    COMBO,
    /**
     * Represents {@link StoredCombo} control.
     */
    STORED_COMBO
  }
}