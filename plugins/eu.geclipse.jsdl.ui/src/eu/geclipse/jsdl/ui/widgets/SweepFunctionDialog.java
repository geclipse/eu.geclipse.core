/*****************************************************************************
 * Copyright (c) 2008, g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributors:
 *    Katarzyna Bylec - PSNC - Initial API and implementation
 *****************************************************************************/
package eu.geclipse.jsdl.ui.widgets;

import java.math.BigInteger;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import eu.geclipse.ui.widgets.NumberVerifier;

public class SweepFunctionDialog extends Dialog implements ModifyListener {

  private Text startValueText;
  private Text endValueText;
  private Text stepValueText;
  private Text exceptionsText;
  private String startReturn;
  private String endReturn;
  private String stepReturn;
  private String[] exceptionsReturn;

  public SweepFunctionDialog( final Shell parentShell ) {
    super( parentShell );
  }

  @Override
  protected Control createDialogArea( final Composite parent ) {
    Composite dialogComp = new Composite( parent, SWT.NONE );
    dialogComp.setLayout( new GridLayout( 2, false ) );
    GridData gData = new GridData();
    Label startLabel = new Label( dialogComp, SWT.LEAD );
    startLabel.setText( "Start value" );
    startLabel.setLayoutData( new GridData() );
    this.startValueText = new Text( dialogComp, SWT.BORDER | SWT.LEAD );
    this.startValueText.setLayoutData( new GridData( GridData.FILL_BOTH
                                                     | GridData.GRAB_HORIZONTAL ) );
    this.startValueText.addListener( SWT.Verify, new NegativeVerifier() );
    this.startValueText.addModifyListener( this );
    Label endLabel = new Label( dialogComp, SWT.LEAD );
    endLabel.setText( "End value" );
    endLabel.setLayoutData( new GridData() );
    this.endValueText = new Text( dialogComp, SWT.BORDER | SWT.LEAD );
    this.endValueText.setLayoutData( new GridData( GridData.FILL_BOTH
                                                   | GridData.GRAB_HORIZONTAL ) );
    this.endValueText.addModifyListener( this );
    this.endValueText.addListener( SWT.Verify, new NegativeVerifier() );
    Label stepLabel = new Label( dialogComp, SWT.LEAD );
    stepLabel.setText( "Step value" );
    stepLabel.setLayoutData( new GridData() );
    this.stepValueText = new Text( dialogComp, SWT.BORDER | SWT.LEAD );
    this.stepValueText.setLayoutData( new GridData( GridData.FILL_BOTH
                                                    | GridData.GRAB_HORIZONTAL ) );
    this.stepValueText.setText( "1" );
    this.stepValueText.addModifyListener( this );
    this.stepValueText.addListener( SWT.Verify, new NumberVerifier() );
    Label exceptLabel = new Label( dialogComp, SWT.LEAD );
    exceptLabel.setText( "Excluded values" );
    exceptLabel.setLayoutData( new GridData() );
    this.exceptionsText = new Text( dialogComp, SWT.MULTI
                                                | SWT.WRAP
                                                | SWT.V_SCROLL
                                                | SWT.BORDER );
    gData = new GridData( GridData.FILL_BOTH
                          | GridData.GRAB_HORIZONTAL
                          | GridData.GRAB_VERTICAL );
    gData.heightHint = 50;
    gData.widthHint = 80;
    this.exceptionsText.setLayoutData( gData );
    this.exceptionsText.addListener( SWT.Verify, new NegativeVerifier() );
    return dialogComp;
  }

  @Override
  protected void okPressed() {
    BigInteger start = new BigInteger( this.startValueText.getText() );
    BigInteger end = new BigInteger( this.endValueText.getText() );
    if( start.compareTo( end ) > -1 ) {
      MessageBox box = new MessageBox( getShell(), SWT.ICON_ERROR | SWT.OK );
      box.setMessage( "Start value must not be smaller than the end one." );
      box.setText( "Error" );
      box.open();
    } else {
      this.startReturn = this.startValueText.getText();
      this.endReturn = this.endValueText.getText();
      this.stepReturn = this.stepValueText.getText();
      if( !this.exceptionsText.getText().equals( "" ) ) {
        this.exceptionsReturn = this.exceptionsText.getText()
          .split( System.getProperty( "line.separator" ) );
      }
      super.okPressed();
    }
  }

  public void modifyText( final ModifyEvent e ) {
    updateButtons();
  }

  private void updateButtons() {
    if( !this.startValueText.getText().equals( "" )
        && !this.endValueText.getText().equals( "" )
        && !this.stepValueText.getText().equals( "" ) )
    {
      super.getButton( IDialogConstants.OK_ID ).setEnabled( true );
    } else {
      super.getButton( IDialogConstants.OK_ID ).setEnabled( false );
    }
  }

  @Override
  protected Control createButtonBar( final Composite parent ) {
    Control result = super.createButtonBar( parent );
    updateButtons();
    return result;
  }

  public String getStartReturn() {
    return startReturn;
  }

  public String getEndReturn() {
    return endReturn;
  }

  public String getStepReturn() {
    return stepReturn;
  }

  public String[] getExceptionsReturn() {
    return exceptionsReturn;
  }
  // also for multi-line Text fields
  class NegativeVerifier implements Listener {

    public void handleEvent( final Event event ) {
      String eventText = event.text;
      Text text = ( Text )event.widget;
      String value = text.getText().substring( 0, event.start )
                     + event.text
                     + text.getText().substring( event.end );
      String[] values = value.split( System.getProperty( "line.separator" ) );
      for( String val : value.split( System.getProperty( "line.separator" ) ) )
      {
        try {
          new BigInteger( val );
        } catch( NumberFormatException exc ) {
          if( !val.equals( "-" ) && !(val.equals( "" ))) {
            event.doit = false;
            break;
          }
        }
      }
    }
  }
}
