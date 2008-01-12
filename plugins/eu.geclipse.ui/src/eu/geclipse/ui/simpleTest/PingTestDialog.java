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
import java.net.URI;
import java.net.UnknownHostException;
import java.util.List;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Button;

import eu.geclipse.core.model.IGridResource;
import eu.geclipse.core.simpleTest.ISimpleTest;
import eu.geclipse.core.simpleTest.PingTest;
import eu.geclipse.ui.dialogs.AbstractSimpleTestDialog;


/**
 * @author harald
 *
 */
public class PingTestDialog extends AbstractSimpleTestDialog  {
  private Label numberOfPings = null;
  private Text outPut = null;
  private Label delayLabel = null;
  private Spinner numberSpn = null;
  private Spinner delaySpn = null;
  private Label outPutLabel = null;
  private Button pingButton = null;

  /**
   * Construct a new dialog from the specified test.
   * 
   * @param test The <code>ISimpleTest</code> for which to create the dialog for.
   * @param resources The resources that this test should be applied to.
   * @param parentShell  The parent shell of this dialog.
   */
  public PingTestDialog( final ISimpleTest test, final List< IGridResource > resources, final Shell parentShell ) {
    super( test, resources, parentShell );
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Control createDialogArea( final Composite parent ) {
    GridData gData;
    
    Composite mainComp = new Composite( parent, SWT.NONE );
    gData = new GridData( GridData.FILL_BOTH );
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    gData.widthHint = 413;
    gData.heightHint = 338;
    mainComp.setLayoutData( gData );
    
    this.numberOfPings = new Label( mainComp, SWT.NONE );
    this.numberOfPings.setText( Messages.getString( "PingTestDialog.nPingsLabel" ) ); //$NON-NLS-1$
    this.numberOfPings.setBounds( new Rectangle( 15, 15, 166, 24 ) );

    this.outPut = new Text( mainComp, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL );
    this.outPut.setBounds( new Rectangle( 15, 105, 371, 136 ) );
    this.outPut.setEditable( false );

    this.delayLabel = new Label( mainComp, SWT.NONE );
    this.delayLabel.setBounds( new Rectangle( 15, 45, 166, 26 ) );
    this.delayLabel.setText( Messages.getString( "PingTestDialog.delayLabel" ) ); //$NON-NLS-1$

    this.numberSpn = new Spinner( mainComp, SWT.NONE );
    this.numberSpn.setBounds( new Rectangle( 195, 15, 59, 30 ) );
    this.numberSpn.setValues( 3, 1, 100, 0, 1, 10 );

    this.delaySpn = new Spinner( mainComp, SWT.NONE );
    this.delaySpn.setBounds( new Rectangle( 195, 45, 59, 30 ) );
    this.delaySpn.setValues( 1, 1, 10, 0, 1, 10 );
    
    this.outPutLabel = new Label( mainComp, SWT.NONE );
    this.outPutLabel.setBounds( new Rectangle( 15, 90, 61, 16 ) );
    this.outPutLabel.setText( Messages.getString( "PingTestDialog.outPutLabel" ) ); //$NON-NLS-1$

    this.pingButton = new Button( mainComp, SWT.NONE );
    this.pingButton.setBounds( new Rectangle( 110, 255, 76, 28 ) );
    this.pingButton.setText( Messages.getString( "PingTestDialog.pingButton" ) ); //$NON-NLS-1$
    this.pingButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e) {
        PingTestDialog.this.runPing();
      }
    });
    
    return mainComp;
  }

  /*  
  64 bytes from 194.42.27.239: icmp_seq=2 ttl=63 time=0.409 ms
  ^C
  --- 194.42.27.239 ping statistics ---
  3 packets transmitted, 3 packets received, 0% packet loss
  round-trip min/avg/max/stddev = 0.390/0.444/0.532/0.063 ms
*/
  protected void runPing() {
    long pingDelay;
    String host;
    InetAddress adr = null;
    long min = Long.MAX_VALUE; 
    long max = Long.MIN_VALUE;
    long avg = 0;
    int nOk = 0;
    int nFailed = 0;
    int delay = this.delaySpn.getSelection();
    int number = this.numberSpn.getSelection();

    if ( null != this.resources ) {
      // Clear the text field 
      this.outPut.selectAll();
      this.outPut.clearSelection();

      // For each of the hosts to test
      for ( int i = 0; i < this.resources.size(); ++i ) {

        // Initialize the counters
        min = Long.MAX_VALUE; 
        max = Long.MIN_VALUE;
        avg = 0;
        nOk = 0;
        nFailed = 0;
        URI uri = this.resources.get( i ).getURI();
        host = uri.getScheme();//.getHost();
        try {
          adr = InetAddress.getByName( host );
        } catch( UnknownHostException e ) {
          // TODO fix tis
        }

        // Print out which host we ping
        this.outPut.append( "Pinging: " + host + this.outPut.getLineDelimiter() );
        
        for ( int j = 0; j < number; ++j ) {
          pingDelay = ( ( PingTest )this.test).ping( adr );
      
          if ( -1 == pingDelay ) {
            ++nFailed;
            this.outPut.append( "Ping " + i + ": Host not reachable" + this.outPut.getLineDelimiter() );
          }
          else {
            ++nOk;
            if ( pingDelay < min )
              min = pingDelay;
            if ( pingDelay > max )
              max = pingDelay;
            avg += pingDelay;
        
            this.outPut.append( "Ping " + j + ": time=" + pingDelay + " ms" + this.outPut.getLineDelimiter() );
          }
        }
        // Write the summary
        this.outPut.append( "round-trip min/avg/max/stddev = " + min +"/" + avg/nOk + "/" + max + " ms" + this.outPut.getLineDelimiter() );
      }
    }
  }
}
