/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium
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
 *     IT Research Division, NEC Laboratories Europe, NEC Europe Ltd. (http://www.it.neclab.eu)
 *     - Harald Kornmayer (harald.kornmayer@it.neclab.eu)
 *
 *****************************************************************************/
package eu.geclipse.core.sla.ui.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import eu.geclipse.core.sla.model.SimpleTerm;

/**
 * @author korn
 *
 */
public class SlaTermInputDialog extends Dialog {

  private final String types[] = {
    "http://www.it.neclab.eu/sla/terms/webmail/MaxNumAccounts",
    "http://www.it.neclab.eu/sla/terms/service/Availability",
    "http://www.it.neclab.eu/sla/terms/service/ResponseTime",
    "http://www.it.neclab.eu/sla/terms/environment/C02Emission",
    "http://www.it.neclab.eu/sla/terms/service/Payment/Yen"
  };
  private final String valueTypes[] = {
    "integer", "float", "String"
  };
  /**
   * The title of the dialog.
   */
  private String title;
  /**
   * The message to display, or <code>null</code> if none.
   */
  private String message;
  /**
   * The SimpleTerm input value;
   */
  private SimpleTerm value = null;
  /**
   * Ok button widget.
   */
  private Button okButton;
  /**
   * The input text widgest
   */
  private Text name;
  private Combo parameter;
  private Combo valueType;
  private Combo minCrit;
  private Text minValue;
  private Combo maxCrit;
  private Text maxValue;

  public SlaTermInputDialog( Shell parentShell, SimpleTerm initialValue ) {
    super( parentShell );
    this.title = "Enter the SLA terms";
    this.message = "Fill the SLA term required for your SLA query!";
    if( initialValue == null ) {
      value = new SimpleTerm( "", "", "", "", "", "", "" ); //$NON-NLS-2$
    } else {
      value = initialValue;
    }
  }

  /*
   * (non-Javadoc)
   * @see
   * org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.
   * swt.widgets.Composite)
   */
  protected void createButtonsForButtonBar( Composite parent ) {
    // create OK and Cancel buttons by default
    this.okButton = createButton( parent,
                                  IDialogConstants.OK_ID,
                                  IDialogConstants.OK_LABEL,
                                  true );
    createButton( parent,
                  IDialogConstants.CANCEL_ID,
                  IDialogConstants.CANCEL_LABEL,
                  false );
  }

  protected Control createDialogArea( Composite parent ) {
    // create composite
    Composite composite = ( Composite )super.createDialogArea( parent );
    // create the desired layout for this wizard page
    GridLayout gl = new GridLayout();
    int ncol = 5;
    gl.numColumns = ncol;
    composite.setLayout( gl );
    // the name
    GridData gridData = new GridData( GridData.BEGINNING );
    gridData.horizontalSpan = 2;
    Label nameLabel = new Label( composite, SWT.NONE );
    nameLabel.setLayoutData( gridData );
    nameLabel.setText( "Name:" );
    gridData = new GridData( GridData.BEGINNING | GridData.FILL_HORIZONTAL );
    gridData.horizontalSpan = 3;
    this.name = new Text( composite, getInputTextStyle() );
    this.name.setLayoutData( gridData );
    this.name.setText( value.getName() );
    this.name.addModifyListener( new ModifyListener() {

      public void modifyText( ModifyEvent e ) {
        validateInput();
      }
    } );
    // the parameter
    gridData = new GridData( GridData.END );
    gridData.horizontalSpan = 2;
    Label parameterLabel = new Label( composite, SWT.NONE );
    parameterLabel.setText( "Parameter:" );
    parameterLabel.setLayoutData( gridData );
    gridData = new GridData( GridData.BEGINNING | GridData.FILL_HORIZONTAL );
    gridData.horizontalSpan = 3;
    this.parameter = new Combo( composite, SWT.READ_ONLY );
    this.parameter.setLayoutData( gridData );
    this.parameter.setItems( this.types );
    this.parameter.setText( value.getParameterName() );
    gridData = new GridData( GridData.BEGINNING | GridData.FILL_HORIZONTAL );
    gridData.horizontalSpan = 5;
    this.valueType = new Combo( composite, SWT.READ_ONLY );
    this.valueType.setLayoutData( gridData );
    this.valueType.setItems( this.valueTypes );
    this.valueType.setText( this.value.getValueType() );
    this.minValue = new Text( composite, this.getInputTextStyle() );
    gridData = new GridData( GridData.BEGINNING | GridData.FILL_HORIZONTAL );
    this.minValue.setLayoutData( gridData );
    String temp = this.value.getMinValue() + "";
    this.minValue.setText( temp );
    this.minCrit = new Combo( composite, SWT.READ_ONLY );
    String items[] = {
      "", "<", "<="
    };
    this.minCrit.setItems( items );
    this.minCrit.setText( value.getMinCriteria() );
    Label valueLabel2 = new Label( composite, SWT.NONE );
    valueLabel2.setText( " Value " );
    maxCrit = new Combo( composite, SWT.READ_ONLY );
    String item2[] = {
      "", "<", "<=", "=="
    };
    maxCrit.setItems( item2 );
    this.maxCrit.setText( value.getMaxCriteria() );
    this.maxValue = new Text( composite, this.getInputTextStyle() );
    gridData = new GridData( GridData.BEGINNING | GridData.FILL_HORIZONTAL );
    this.maxValue.setLayoutData( gridData );
    String temp2 = this.value.getMaxValue() + "";
    this.maxValue.setText( temp2 );
    applyDialogFont( composite );
    return composite;
  }

  public SimpleTerm getValue() {
    return value;
  }

  protected void buttonPressed( int buttonId ) {
    if( buttonId == IDialogConstants.OK_ID ) {
      this.validateInput();
      value = this.getSlaTerm();
    } else {
      value = null;
    }
    super.buttonPressed( buttonId );
  }

  private SimpleTerm getSlaTerm() {
    this.value.setName( this.name.getText() );
    this.value.setParameterName( this.parameter.getText() );
    this.value.setValueType( this.valueType.getText() );
    this.value.setMinValue( this.minValue.getText() );
    this.value.setMinCriteria( this.minCrit.getText() );
    this.value.setMaxCriteria( this.maxCrit.getText() );
    this.value.setMaxValue( this.maxValue.getText() );
    return this.value;
  }

  protected Button getOkButton() {
    return this.okButton;
  }

  protected void validateInput() {

    if( this.name.getText().equalsIgnoreCase( "" ) //$NON-NLS-1$
        && !this.parameter.getText().equals( "" ) ) //$NON-NLS-1$
    {
      this.name.setText( this.parameter.getText() );
    }
  }

  protected int getInputTextStyle() {
    return SWT.SINGLE | SWT.BORDER;
  }
}
