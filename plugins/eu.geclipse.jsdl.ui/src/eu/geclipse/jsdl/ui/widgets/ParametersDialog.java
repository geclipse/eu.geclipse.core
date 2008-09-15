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
 *     PSNC: 
 *      - Katarzyna Bylec (katis@man.poznan.pl)
 *           
 *****************************************************************************/
package eu.geclipse.jsdl.ui.widgets;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.ManagedForm;

import eu.geclipse.jsdl.ui.internal.SweepRule;

public class ParametersDialog extends Dialog implements ModifyListener {

  public static final String EDIT_ELEMENT = "edit";
  public static final String NEW_ELEMENT = "new";
  private Combo refElement;
  // private Combo sweepRule;
  private Combo element;
  private List<String> parameters;
  private String refElementInit;
  private String mode;
  private String memElement;
  private String refElementReturn;
  private String elementReturn;
  private String sweepRuleReturn;
  private List<String> refElements;
  private Text valuesText;
  private List<String> values;

  public ParametersDialog( final Shell parentShell,
                           final List<String> parameters,
                           final List<String> refElements,
                           final String refElement,
                           final String mode )
  {
    super( parentShell );
    this.parameters = parameters;
    this.refElementInit = refElement;
    this.refElements = refElements;
    this.mode = mode;
  }

  @Override
  protected Control createDialogArea( final Composite parent ) {
    Composite mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayout( new GridLayout( 2, false ) );
    GridData gData = new GridData();
    // sweeped element controls
    Label elemLabel = new Label( mainComp, SWT.LEAD );
    elemLabel.setText( "Sweeped element" );
    elemLabel.setLayoutData( gData );
    this.element = new Combo( mainComp, SWT.DROP_DOWN );
    this.element.setVisibleItemCount( 16 );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    this.element.setLayoutData( gData );
    for( String val : this.parameters ) {
      this.element.add( val );
    }
    this.element.addModifyListener( this );
    if( mode.equals( EDIT_ELEMENT ) ) {
      Label refLabel = new Label( mainComp, SWT.LEAD );
      refLabel.setText( "Referenced JSDL element" );
      gData = new GridData();
      refLabel.setLayoutData( gData );
      this.refElement = new Combo( mainComp, SWT.DROP_DOWN );
      this.refElement.setVisibleItemCount( 16 );
      int selIndex = -1;
      for( String val : this.refElements ) {
        this.refElement.add( val );
      }
      if( this.refElementInit != null ) {
        int i = -1;
        for( String comboElem : this.refElement.getItems() ) {
          i++;
          if( comboElem.equals( this.refElementInit ) ) {
            selIndex = i;
          }
        }
      }
      if( selIndex != -1 ) {
        this.refElement.select( selIndex );
      }
      gData = new GridData( GridData.FILL_HORIZONTAL );
      gData.widthHint = 500;
      this.refElement.setLayoutData( gData );
      this.refElement.addModifyListener( this );
    }
    // values area
    // values label
    Label valuesLabel = new Label( mainComp, SWT.LEAD );
    valuesLabel.setText( "Sweep values" );
    gData = new GridData();
    valuesLabel.setLayoutData( gData );
    // values text area
    this.valuesText = new Text( mainComp, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL );
    gData = new GridData( GridData.FILL_BOTH
                          | GridData.GRAB_HORIZONTAL
                          | GridData.GRAB_VERTICAL );
    gData.heightHint = 30;
    gData.verticalSpan = 15;
    this.valuesText.setLayoutData( gData );
    this.valuesText.setToolTipText( "Seperate values with new line." );
    // hint label
    Label valuesHint = new Label( mainComp, SWT.LEAD );
    valuesHint.setText( "(put each value in new line)" );
    gData = new GridData();
    valuesHint.setLayoutData( gData );
    return mainComp;
  }

  @Override
  protected Control createButtonBar( final Composite parent ) {
    Control result = super.createButtonBar( parent );
    updateButtons();
    return result;
  }

  private void updateButtons() {
    if( this.mode.equals( NEW_ELEMENT ) ) {
      super.getButton( IDialogConstants.OK_ID )
        .setEnabled( !this.element.getText().equals( "" ) );
    } else if( !this.element.getText().equals( "" )
               && !this.refElement.getText().equals( "" ) )
    {
      super.getButton( IDialogConstants.OK_ID ).setEnabled( true );
    } else {
      super.getButton( IDialogConstants.OK_ID ).setEnabled( false );
    }
  }

  public void modifyText( final ModifyEvent event ) {
    updateButtons();
  }

  @Override
  protected void okPressed() {
    this.elementReturn = this.element.getText();
    if( this.mode.equals( EDIT_ELEMENT ) ) {
      this.refElementReturn = this.refElement.getText();
      if( this.refElementReturn.equals( this.elementReturn ) ) {
        MessageDialog.openError( getShell(),
                                 "Error",
                                 "Cannot add sweep element with sweep rule relative to itself." );
      }
    }
    this.values = new ArrayList<String>();
    if( !this.valuesText.getText().equals( "" ) ) {
      String wholeValues = this.valuesText.getText();
      for( String value : wholeValues.split( System.getProperty( "line.separator" ) ) )
      {
        this.values.add( value );
      }
    }
    super.okPressed();
  }

  public String getRefElementReturn() {
    return this.refElementReturn;
  }

  public String getElementReturn() {
    return this.elementReturn;
  }

  public List<String> getValuesReturn() {
    return this.values;
  }
}
