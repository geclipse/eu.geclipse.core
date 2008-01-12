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
 *     UCY (http://www.cs.ucy.ac.cy)
 *      - Harald Gjermundrod (harald@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.ui.simpleTest;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Button;

import eu.geclipse.core.simpleTest.ISimpleTest;
import eu.geclipse.core.simpleTest.PingTest;
import eu.geclipse.ui.dialogs.AbstractSimpleTestDialog;


public class PingTestDialog extends AbstractSimpleTestDialog  {

  public PingTestDialog( final ISimpleTest test, final Shell parentShell ) {
    super( test, parentShell );
  }

  private Label numberOfPings = null;
  private Text outPut = null;
  private Label delayLabel = null;
  private Spinner numberSpn = null;
  private Spinner delaySpn = null;
  private Label outPutLabel = null;
  private Button startButton = null;
  private Button cancelButton = null;
  private Button exitButton = null;

  /* (non-Javadoc)
   * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Control createDialogArea( final Composite parent ) {
//    createContents( parent );
    //this.createDialogArea( parent );
//    createMessageArea(parent);

    GridData gData;
    
    Composite mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayout( new GridLayout( 2, false ) );
    gData = new GridData( GridData.FILL_BOTH );
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    gData.widthHint = 313;
    gData.heightHint = 338;
    mainComp.setLayoutData( gData );
    
    
    numberOfPings = new Label( mainComp, SWT.NONE );
    numberOfPings.setText("Number of pings:");
    numberOfPings.setBounds(new Rectangle(15, 15, 166, 24));
    outPut = new Text(mainComp, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
    outPut.setBounds(new Rectangle(15, 105, 271, 136));
    delayLabel = new Label(mainComp, SWT.NONE);
    delayLabel.setBounds(new Rectangle(15, 45, 166, 26));
    delayLabel.setText("Delay between pings (sec):");
    numberSpn = new Spinner(mainComp, SWT.NONE);
    numberSpn.setBounds(new Rectangle(195, 15, 59, 30));
    delaySpn = new Spinner(mainComp, SWT.NONE);
    delaySpn.setBounds(new Rectangle(195, 45, 59, 30));
    outPutLabel = new Label(mainComp, SWT.NONE);
    outPutLabel.setBounds(new Rectangle(15, 90, 61, 16));
    outPutLabel.setText("Output:");
    startButton = new Button(mainComp, SWT.NONE);
    startButton.setBounds(new Rectangle(15, 255, 76, 28));
    startButton.setText("&Start");
    startButton.addSelectionListener( new org.eclipse.swt.events.SelectionListener()
    {
      public void widgetDefaultSelected( org.eclipse.swt.events.SelectionEvent e ) {
        PingTestDialog.this.runTest();
      }
      public void widgetSelected( org.eclipse.swt.events.SelectionEvent e ) {
        PingTestDialog.this.runTest();

      }
    } );
    exitButton = new Button(mainComp, SWT.NONE);
    exitButton.setBounds(new Rectangle(225, 255, 76, 28));
    exitButton.setText("&Exit");
    exitButton.addSelectionListener( new org.eclipse.swt.events.SelectionListener()
    {
      public void widgetSelected( org.eclipse.swt.events.SelectionEvent e ) {
        System.out.println( "widgetSelected()" ); // TODO Auto-generated Event stub widgetSelected()
      }
      public void widgetDefaultSelected( org.eclipse.swt.events.SelectionEvent e ) {
      }
    } );
    
    return mainComp;
    
    
    
    /*
     lData = new GridData();
    lData.minimumHeight = 0;

    this.mainComp = new Composite( parent, SWT.NONE );
    this.mainComp.setLayout( new GridLayout( 2, false ) );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    this.mainComp.setLayoutData( gData );

    this.numberLabel = new Label( this.mainComp, SWT.NONE );
    this.numberLabel.setText( Messages.getString( "PingTestWizardPage.numberLabel" ) ); //$NON-NLS-1$
    this.numberLabel.setLayoutData( lData );    
    
    this.numberSpinner = new Spinner( this.mainComp, SWT.BORDER );
    this.numberSpinner.setValues( 10, 1, 100, 0, 1, 10 );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    this.numberSpinner.setLayoutData( gData );

    this.delayLabel = new Label( this.mainComp, SWT.RIGHT | SWT.NONE );
    this.delayLabel.setText( Messages.getString( "PingTestWizardPage.delayLabel" ) ); //$NON-NLS-1$
    this.delayLabel.setLayoutData( lData );

    this.delaySpinner = new Spinner( this.mainComp, SWT.BORDER );
    this.delaySpinner.setValues( 1, 1, 10, 0, 1, 10 );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    this.delaySpinner.setLayoutData( gData );
    
    this.outPut = new Text ( this.mainComp, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL );
    this.outPut.setEditable( false );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    this.outPut.setLayoutData( gData );
    
    this.test = new Button( this.mainComp, SWT.PUSH );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    this.test.setLayoutData( gData );
    
    this.test.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e) {
        PingTestWizardPage.this.runTest();
      }
    });
    
    this.cancel = new Button( this.mainComp, SWT.PUSH );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    this.cancel.setLayoutData( gData );

    this.cancel.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e) {
        PingTestWizardPage.this.cancelTest();
      }
    });

    
    setControl( this.mainComp );

    */
  }

  /*  
  64 bytes from 194.42.27.239: icmp_seq=2 ttl=63 time=0.409 ms
  ^C
  --- 194.42.27.239 ping statistics ---
  3 packets transmitted, 3 packets received, 0% packet loss
  round-trip min/avg/max/stddev = 0.390/0.444/0.532/0.063 ms
*/
  protected void runTest() {
    long pingDelay;
    long min = Long.MAX_VALUE; 
    long max = Long.MIN_VALUE;
    long avg = 0;
    int nOk = 0;
    int nFailed = 0;
    int delay = this.delaySpn.getSelection();
    int number = this.numberSpn.getSelection();

    InetAddress adr = null;
   
    try {
      adr = InetAddress.getByName( "194.42.27.239" );
    } catch( UnknownHostException e ) {
    }
    
    // Clear the text field 
    this.outPut.selectAll();
    this.outPut.clearSelection();
   
    for ( int i = 0; i < number; ++i ) {
      pingDelay = ( ( PingTest )this.test ).ping( adr );
      
      if ( -1 == pingDelay ) {
        ++nFailed;
        this.outPut.append( "Ping " + i + ": Host not rechable" + this.outPut.getLineDelimiter() );
      }
      else {
        ++nOk;
        if ( pingDelay < min )
          min = pingDelay;
        if ( pingDelay > max )
          max = pingDelay;
        avg += pingDelay;
        
        this.outPut.append( "Ping " + i + ": time=" + pingDelay + " ms" + this.outPut.getLineDelimiter() );
      }
      
      // Write the summary
      this.outPut.append( "round-trip min/avg/max/stddev = " + min +"/" + avg/nOk + "/" + max + " ms" + this.outPut.getLineDelimiter() );
    }
  }
  
  
/*  @Override
  protected Image getImage() {
    return getInfoImage();
  }
  */
}
