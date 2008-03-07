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
 *     UCY (http://www.cs.ucy.ac.cy)
 *      - Harald Gjermundrod (harald@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.ui.simpleTest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Button;

import eu.geclipse.core.model.IGridResource;
import eu.geclipse.core.simpleTest.ISimpleTest;
import eu.geclipse.core.simpleTest.PingTest;
import eu.geclipse.ui.dialogs.AbstractSimpleTestDialog;


/**
 * A dialog that allows the user to ping selected resources. 
 * @author hgjermund
 *
 */
public class PingTestDialog extends AbstractSimpleTestDialog  {
  protected ArrayList< PingHostJob > pingJobs = new ArrayList< PingHostJob >();

  private Text outPut = null;
  private Spinner numberSpn = null;
  private Spinner delaySpn = null;
  private ArrayList< InetAddress > hostAdrs = new ArrayList< InetAddress >();  

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
  
  @Override
  protected void configureShell( final Shell newShell ) {
    super.configureShell( newShell );
    newShell.setMinimumSize( 500, 400 );
    newShell.setText( Messages.getString( "PingTestDialog.dialogTitle" ) ); //$NON-NLS-1$
    
    // Escape stops all the pinging
//    newShell.addListener( SWT.Traverse, new Listener() {
//      public void handleEvent( final Event e ) {
//        for ( PingHostJob job : PingTestDialog.this.pingJobs ) {
//          job.cancel();
//        }
//      }
//    });
  }

  @Override
  public boolean close() {
    for ( PingHostJob job : PingTestDialog.this.pingJobs ) {
      job.cancel();
    }

    return super.close(); 
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Control createDialogArea( final Composite parent ) {
    GridData gData;
    
    Composite mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayout( new GridLayout( 1, false ) );
    gData = new GridData( SWT.FILL, SWT.FILL, true, true);
    mainComp.setLayoutData( gData );
    
    Group settingsGroup = new Group( mainComp, SWT.NONE );
    settingsGroup.setLayout( new GridLayout( 3, false ) );
    settingsGroup.setText( Messages.getString( "PingTestDialog.settings_group" ) ); //$NON-NLS-1$
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    settingsGroup.setLayoutData( gData );

    Label numPingsLabel = new Label( settingsGroup, SWT.LEFT  );
    numPingsLabel.setText( Messages.getString( "PingTestDialog.nPingsLabel" ) ); //$NON-NLS-1$
    gData = new GridData();
    numPingsLabel.setLayoutData( gData );
    
    this.numberSpn = new Spinner( settingsGroup, SWT.LEFT | SWT.SINGLE | SWT.BORDER );
    this.numberSpn.setValues( 2, 1, 100, 0, 1, 2 );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.horizontalSpan = 2;
    gData.grabExcessHorizontalSpace = true;
    this.numberSpn.setLayoutData( gData );    

    Label delayLabel = new Label( settingsGroup, SWT.LEFT );
    delayLabel.setText( Messages.getString( "PingTestDialog.delayLabel" ) ); //$NON-NLS-1$
    gData = new GridData();
    delayLabel.setLayoutData( gData );

    this.delaySpn = new Spinner( settingsGroup, SWT.LEFT | SWT.SINGLE | SWT.BORDER );
    this.delaySpn.setValues( 1, 1, 10, 0, 1, 10 );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.horizontalSpan = 2;
    gData.grabExcessHorizontalSpace = true;
    this.delaySpn.setLayoutData( gData );    
    
    Group outPutGroup = new Group( mainComp, SWT.NONE );
    outPutGroup.setLayout( new GridLayout( 3, false ) );
    outPutGroup.setText( Messages.getString( "PingTestDialog.output_group" ) ); //$NON-NLS-1$
    gData = new GridData( GridData.FILL_BOTH );
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    outPutGroup.setLayoutData( gData );
    
    Label outPutLabel = new Label( outPutGroup, SWT.LEFT );
    outPutLabel.setText( Messages.getString( "PingTestDialog.outPutLabel" ) ); //$NON-NLS-1$
    gData = new GridData();
    gData.horizontalSpan = 3;
    outPutLabel.setLayoutData( gData );
    
    this.outPut = new Text( outPutGroup, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI );
    this.outPut.setEditable( false );
    gData = new GridData( SWT.FILL, SWT.FILL, true, true );
//    gData.horizontalSpan = 2;
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    this.outPut.setLayoutData( gData );

    Composite outControls = new Composite( outPutGroup, SWT.NONE );
    GridLayout gLayout = new GridLayout( 1, false );
    gLayout.marginHeight = 0;
    gLayout.marginWidth = 0;
    outControls.setLayout( gLayout );
    gData = new GridData( GridData.VERTICAL_ALIGN_BEGINNING );
    outControls.setLayoutData( gData );    
    
    Button pingButton = new Button( outControls, SWT.PUSH );
    pingButton.setText( Messages.getString( "PingTestDialog.pingButton" ) ); //$NON-NLS-1$
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.verticalAlignment = GridData.BEGINNING;
    pingButton.setLayoutData( gData );

    Button stopButton = new Button( outControls, SWT.PUSH );
    stopButton.setText( Messages.getString( "PingTestDialog.stopButton" ) ); //$NON-NLS-1$
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.verticalAlignment = GridData.BEGINNING;
    stopButton.setLayoutData( gData );
    
    pingButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e) {

        // Make sure the potential current pings are done
        boolean done = true;
        for ( PingHostJob job : PingTestDialog.this.pingJobs ) {
          if ( null == job.getResult() )
            done = false;
        }
        // At least one of the prev. jobs haven't finished yet
        if ( done )
          PingTestDialog.this.runPing();
      }
    });
    
    stopButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e) {
        for ( PingHostJob job : PingTestDialog.this.pingJobs ) {
          job.cancel();
        }
        
        PingTestDialog.this.pingJobs.clear();
      }
    });
    

    
    return mainComp;
  }

  protected void runPing() {
    String host;
    InetAddress adr = null;
    int number = this.numberSpn.getSelection();
    int delay = this.delaySpn.getSelection();

    if ( null != this.resources ) {
      // Clear the text field 
      this.outPut.selectAll();
      this.outPut.clearSelection();

      // Clear the previous jobs
      this.hostAdrs.clear();
      this.pingJobs.clear();
      
      // For each of the hosts to test
      this.outPut.append( Messages.getString( "PingTestDialog.pingHostsPlusSpace" )  //$NON-NLS-1$
                          + this.outPut.getLineDelimiter() );
        
      for ( int i = 0; i < this.resources.size(); ++i ) {
        host = this.resources.get( i ).getHostName();
        
        if ( null == host ) {
          this.outPut.append( this.resources.get( i ).getName() 
                              + Messages.getString( "PingTestDialog.notResolved" )  //$NON-NLS-1$
                              + this.outPut.getLineDelimiter() );
        } else {
          try {
            adr = InetAddress.getByName( host );
            this.hostAdrs.add( adr );
            
            this.outPut.append( host + this.outPut.getLineDelimiter() );
          } catch( UnknownHostException e ) {
            // Print out which host we ping
            this.outPut.append( Messages.getString( "PingTestDialog.UnknownHostException" )  //$NON-NLS-1$
                                + host + this.outPut.getLineDelimiter() );
          }
        }
      }
      
      this.outPut.append( this.outPut.getLineDelimiter() );
      
      for ( InetAddress tmpAdr : this.hostAdrs ) { 
        PingHostJob pingJob = new PingHostJob( tmpAdr, number, delay, this.outPut, ( PingTest )this.test );
        pingJob.schedule();
        
        this.pingJobs.add( pingJob );
      }        
    }
  }
}
