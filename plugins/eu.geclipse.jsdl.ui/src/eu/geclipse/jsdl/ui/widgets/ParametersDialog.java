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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
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

import eu.geclipse.jsdl.ui.adapters.jsdl.ParametricJobAdapter;

/**
 * Dialog for defining new parameter sweep.
 */
public class ParametersDialog extends Dialog implements ModifyListener {

  /**
   * Variable for launching ParametersDialog as a dialog for defining new
   * parameter with reference to parameter already defined.
   */
  public static final String WITH_REF = "ref"; //$NON-NLS-1$
  /**
   * Variable for launching ParametersDialog as a dialog for defining new
   * parameter without reference to any sweep parameter. New parameter will be
   * defined on zero level in sweep extension in JSDL.
   */
  public static final String NEW_ELEMENT = "new"; //$NON-NLS-1$
  private static final String SEPARATOR_PROPERTY = "line.separator"; //$NON-NLS-1$
  Text valuesText;
  private Combo refElement;
  // private Combo sweepRule;
  private Combo element;
  private List<String> parameters;
  private String refElementInit;
  private String mode;
  private String refElementReturn;
  private String elementReturn;
  private List<String> refElements;
  private List<String> values;

  /**
   * Creates instance of ParametersDialog class.
   * 
   * @param parentShell shell of a parent widget
   * @param parameters list of JSDL elements to choose from and create sweep
   *          parameters
   * @param refElements list of sweep parameters already defined in JSDL
   * @param refElement chosen element form <code>eElements<code> list
   * @param mode
   */
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
    elemLabel.setText( Messages.getString( "ParametersDialog.sweeped_element_label" ) ); //$NON-NLS-1$
    elemLabel.setLayoutData( gData );
    this.element = new Combo( mainComp, SWT.DROP_DOWN | SWT.READ_ONLY );
    this.element.setVisibleItemCount( 16 );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    this.element.setLayoutData( gData );
    for( String val : this.parameters ) {
      this.element.add( val );
    }
    this.element.addModifyListener( this );
    if( this.mode.equals( WITH_REF ) ) {
      Label refLabel = new Label( mainComp, SWT.LEAD );
      refLabel.setText( Messages.getString( "ParametersDialog.referenced_element_label" ) ); //$NON-NLS-1$
      gData = new GridData();
      refLabel.setLayoutData( gData );
      this.refElement = new Combo( mainComp, SWT.DROP_DOWN | SWT.READ_ONLY );
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
    valuesLabel.setText( Messages.getString( "ParametersDialog.values_label" ) ); //$NON-NLS-1$
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
    this.valuesText.setToolTipText( Messages.getString( "ParametersDialog.values_new_line_separation_info_tooltip" ) ); //$NON-NLS-1$
    // hint label
    Label valuesHint = new Label( mainComp, SWT.LEAD );
    valuesHint.setText( Messages.getString( "ParametersDialog.values_new_line_separation_info" ) ); //$NON-NLS-1$
    gData = new GridData();
    valuesHint.setLayoutData( gData );
    Button loopButton = new Button( mainComp, SWT.PUSH );
    loopButton.setText( Messages.getString( "ParametersDialog.loop_button" ) ); //$NON-NLS-1$
    gData = new GridData();
    gData.verticalIndent = 10;
    gData.horizontalAlignment = SWT.CENTER;
    gData.verticalAlignment = SWT.CENTER;
    loopButton.setLayoutData( gData );
    loopButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent e ) {
        SweepLoopDialog dialog = new SweepLoopDialog( parent.getShell() );
        if( dialog.open() == Window.OK ) {
          List<BigInteger> exceptions = new ArrayList<BigInteger>();
          for( String exc : dialog.getExceptionsReturn() ) {
            exceptions.add( new BigInteger( exc ) );
          }
          String loop = ParametricJobAdapter.createLOOPString( new BigInteger( dialog.getStartReturn() ),
                                                               new BigInteger( dialog.getEndReturn() ),
                                                               new BigInteger( dialog.getStepReturn() ),
                                                               exceptions );
          ParametersDialog.this.valuesText.setText( loop );
        }
      }
    } );
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
        .setEnabled( !this.element.getText().equals( "" ) ); //$NON-NLS-1$
    } else if( !this.element.getText().equals( "" ) //$NON-NLS-1$
               && !this.refElement.getText().equals( "" ) ) //$NON-NLS-1$
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
    if( this.mode.equals( WITH_REF ) ) {
      this.refElementReturn = this.refElement.getText();
      if( this.refElementReturn.equals( this.elementReturn ) ) {
        MessageDialog.openError( getShell(),
                                 Messages.getString( "ParametersDialog.error_title" ), //$NON-NLS-1$
                                 Messages.getString( "ParametersDialog.element_referenig_to_itself_error" ) ); //$NON-NLS-1$
      }
    }
    this.values = new ArrayList<String>();
    if( !this.valuesText.getText().equals( "" ) ) { //$NON-NLS-1$
      String wholeValues = this.valuesText.getText();
      for( String value : wholeValues.split( System.getProperty( ParametersDialog.SEPARATOR_PROPERTY ) ) )
      {
        this.values.add( value );
      }
    }
    super.okPressed();
  }

  /**
   * Return the selected referenced element as selected in dialog when user
   * pressed OK.
   * 
   * @return String with XPath to element chosen as a referenced one for new
   *         parameter
   */
  public String getRefElementReturn() {
    return this.refElementReturn;
  }

  /**
   * Method to access JSDL element chosen as a new sweep parameter as selected
   * in dialog when user pressed OK.
   * 
   * @return String with XPath to element chosen as a new parameter sweep
   */
  public String getElementReturn() {
    return this.elementReturn;
  }

  /**
   * Method to access values for new parameter as entered in dialog when user
   * pressed OK.
   * 
   * @return List of strings values for parameter sweep. This may also be loop
   *         definition.
   */
  public List<String> getValuesReturn() {
    return this.values;
  }
}
