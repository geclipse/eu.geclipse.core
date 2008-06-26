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
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
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
  protected Table tableOutPut = null;
  protected String[][] itemStrings;

  private Text outPut = null;
  private Spinner numberSpn = null;
  private Spinner delaySpn = null;
  private ArrayList< InetAddress > hostAdrs = new ArrayList< InetAddress >();  
  private ArrayList< String > hostNames = new ArrayList< String >();
  
//  private Hashtable< PingHostJob, ProgressTreeNode > progressNodes
//  = new Hashtable< PingHostJob, ProgressTreeNode >();
  
  /**
   * Construct a new dialog from the specified test.
   * 
   * @param test The <code>ISimpleTest</code> for which to create the dialog for.
   * @param resources The resources that this test should be applied to.
   * @param parentShell  The parent shell of this dialog.
   */
  public PingTestDialog( final ISimpleTest test, final List< IGridResource > resources, final Shell parentShell ) {
    super( test, resources, parentShell );

    // First we gather the host name of all the resources
    String name;
    for ( int i = 0; i < this.resources.size(); ++i ) {
      name = this.resources.get( i ).getHostName();

      if ( null != name )
        this.hostNames.add( name );
    }
  
    // Then we prepare the datastructure for the table of results with the hostnames 
    this.itemStrings = new String [ this.hostNames.size() ] [ 6 ];  
    
    for ( int i = 0; i < this.hostNames.size(); ++i )
      this.itemStrings[ i ] [ 0 ] = this.hostNames.get( i ); 
  }
  
  @Override
  protected void configureShell( final Shell newShell ) {
    super.configureShell( newShell );
    newShell.setMinimumSize( 500, 400 );
    newShell.setText( Messages.getString( "PingTestDialog.dialogTitle" ) ); //$NON-NLS-1$
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

    // Create the tabbed panel with the results 
    TabFolder tabFolder = new TabFolder ( outPutGroup, SWT.NONE );

    TabItem itemTblOutPut = new TabItem ( tabFolder, SWT.NULL );
    itemTblOutPut.setText ( Messages.getString( "PingTestDialog.results" ) ); //$NON-NLS-1$

    // Create the table that holds the result
    this.tableOutPut = new Table( tabFolder, SWT.MULTI | SWT.VIRTUAL | SWT.BORDER );
    this.tableOutPut.setHeaderVisible( true );
    this.tableOutPut.setLinesVisible( true );    
    
    TableColumn hostColumn = new TableColumn( this.tableOutPut, SWT.NONE );
    hostColumn.setText( Messages.getString( "PingTestDialog.hostName" ) ); //$NON-NLS-1$
    hostColumn.setWidth( 150 );    
    hostColumn.setAlignment( SWT.LEFT );

    TableColumn sndColumn = new TableColumn( this.tableOutPut, SWT.CENTER );
    sndColumn.setText( Messages.getString( "PingTestDialog.snd" ) ); //$NON-NLS-1$
    sndColumn.setWidth( 75 );    
    sndColumn.setAlignment( SWT.CENTER );
    
    TableColumn recColumn = new TableColumn( this.tableOutPut, SWT.CENTER );
    recColumn.setText( Messages.getString( "PingTestDialog.rec" ) ); //$NON-NLS-1$
    recColumn.setWidth( 75 );    
    recColumn.setAlignment( SWT.CENTER );
    
    TableColumn minColumn = new TableColumn( this.tableOutPut, SWT.CENTER );
    minColumn.setText( Messages.getString( "PingTestDialog.min" ) ); //$NON-NLS-1$
    minColumn.setWidth( 75 );    
    minColumn.setAlignment( SWT.CENTER );
    
    TableColumn avgColumn = new TableColumn( this.tableOutPut, SWT.CENTER );
    avgColumn.setText( Messages.getString( "PingTestDialog.avg" ) ); //$NON-NLS-1$
    avgColumn.setWidth( 75 );    
    avgColumn.setAlignment( SWT.CENTER );
    
    TableColumn maxColumn = new TableColumn( this.tableOutPut, SWT.CENTER );
    maxColumn.setText( Messages.getString( "PingTestDialog.max" ) ); //$NON-NLS-1$
    maxColumn.setWidth( 75 );    
    maxColumn.setAlignment( SWT.CENTER );
    
    itemTblOutPut.setControl( this.tableOutPut );
    
    // Listener that waits for the results to be presented
    this.tableOutPut.addListener( SWT.SetData, new Listener() {
      public void handleEvent( final Event event ) {
        TableItem item = ( TableItem ) event.item;
        int index = PingTestDialog.this.tableOutPut.indexOf( item );
        item.setText( PingTestDialog.this.itemStrings [ index ] );
      }
    });

    this.tableOutPut.setItemCount( this.hostNames.size() );
    
    TabItem itemRawOutPut = new TabItem ( tabFolder, SWT.NULL );
    itemRawOutPut.setText ( Messages.getString( "PingTestDialog.logging" ) ); //$NON-NLS-1$
    
    this.outPut = new Text( tabFolder, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI );
    this.outPut.setEditable( false );
    itemRawOutPut.setControl( this.outPut );
    
    gData = new GridData( SWT.FILL, SWT.FILL, true, true );
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    tabFolder.setLayoutData( gData );
    
    Composite outControls = new Composite( outPutGroup, SWT.NONE );
    GridLayout gLayout = new GridLayout( 1, false );
    gLayout.marginHeight = 0;
    gLayout.marginWidth = 0;
    outControls.setLayout( gLayout );
    gData = new GridData( GridData.VERTICAL_ALIGN_BEGINNING );
    outControls.setLayoutData( gData );    
    
    Button pingButton = new Button( outControls, SWT.PUSH );
    pingButton.setText( Messages.getString( "PingTestDialog.pingButton" ) ); //$NON-NLS-1$
    pingButton.setToolTipText( Messages.getString( "PingTestDialog.pingButtonToolTip" ) ); //$NON-NLS-1$
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.verticalAlignment = GridData.BEGINNING;
    pingButton.setLayoutData( gData );

    Button stopButton = new Button( outControls, SWT.PUSH );
    stopButton.setText( Messages.getString( "PingTestDialog.stopButton" ) ); //$NON-NLS-1$
    stopButton.setToolTipText( Messages.getString( "PingTestDialog.stopButtonToolTip" ) ); //$NON-NLS-1$
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.verticalAlignment = GridData.BEGINNING;
    stopButton.setLayoutData( gData );

    Button clearButton = new Button( outControls, SWT.PUSH );
    clearButton.setText( Messages.getString( "PingTestDialog.clearButton" ) ); //$NON-NLS-1$
    clearButton.setToolTipText( Messages.getString( "PingTestDialog.clearButtonToolTip" ) ); //$NON-NLS-1$
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.verticalAlignment = GridData.BEGINNING;
    clearButton.setLayoutData( gData );

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

    clearButton.addSelectionListener( new SelectionAdapter() {
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
          PingTestDialog.this.clearLogs();
      }
    });

   // parent.getShell().setDefaultButton( pingButton );
    
    return mainComp;
  }

  /**
   * Method that initiates a job for each of the hosts that we want to ping. 
   * The jobs then performs the ping and the GUIs are updated with the result
   */
  protected void runPing() {
    InetAddress adr = null;
    int number = this.numberSpn.getSelection();
    int delay = this.delaySpn.getSelection();

    if ( null != this.resources ) {
      // Clear the previous jobs
      this.hostAdrs.clear();
      this.pingJobs.clear();

      // Also clear the table 
      this.clearItemString();
      
      // For each of the hosts to test
      this.outPut.append( Messages.getString( "PingTestDialog.pingHostsPlusSpace" )  //$NON-NLS-1$
                          + this.outPut.getLineDelimiter() );
        
      for ( String host : this.hostNames ) {
        try {
          adr = InetAddress.getByName( host );
          this.hostAdrs.add( adr );
            
          this.outPut.append( host + this.outPut.getLineDelimiter() );
        } catch( UnknownHostException e ) {
          this.hostAdrs.add( null );
          // Print out which host we ping
          this.outPut.append( Messages.getString( "PingTestDialog.UnknownHostException" )  //$NON-NLS-1$
                              + host + this.outPut.getLineDelimiter() );
        }
      }
      
      this.outPut.append( this.outPut.getLineDelimiter() );
      
      for ( int i = 0; i < this.hostAdrs.size(); ++i ) {
        InetAddress tmpAdr = this.hostAdrs.get( i );
      
        if ( null != tmpAdr ) { 
          PingHostJob pingJob = new PingHostJob( tmpAdr, number, delay, this.outPut, this.tableOutPut, 
                                                 this.itemStrings[ i ], i, ( PingTest )this.test );
          pingJob.schedule();
          
          this.pingJobs.add( pingJob );
        } else {
          this.itemStrings[ i ][ 1 ] = Messages.getString( "PingTestDialog.n_a" ); //$NON-NLS-1$
          this.itemStrings[ i ][ 2 ] = Messages.getString( "PingTestDialog.n_a" ); //$NON-NLS-1$ 
          this.itemStrings[ i ][ 3 ] = Messages.getString( "PingTestDialog.n_a" ); //$NON-NLS-1$
          this.itemStrings[ i ][ 4 ] = Messages.getString( "PingTestDialog.n_a" ); //$NON-NLS-1$
          this.itemStrings[ i ][ 5 ] = Messages.getString( "PingTestDialog.n_a" ); //$NON-NLS-1$
          this.tableOutPut.clear( i );
        }
      }        
    }
  }
  
  private void clearItemString() {
    for ( int i = 0; i < this.hostNames.size(); ++i ) {
      for ( int j = 1; j < 6; ++j ) {
        this.itemStrings[ i ][ j ] = null;
      }
    }
    this.tableOutPut.clearAll();
  }
  
  protected void clearLogs() {
    this.clearItemString();
    this.outPut.setText( "" ); //$NON-NLS-1$
  }

}
