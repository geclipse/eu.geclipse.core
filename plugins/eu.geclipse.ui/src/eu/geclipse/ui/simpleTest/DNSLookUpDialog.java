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

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import eu.geclipse.core.model.IGridResource;
import eu.geclipse.ui.dialogs.AbstractSimpleTestDialog;


/**
 * A dialog that allows the user to lookup the IP of selected resources. 
 * @author hgjermund
 *
 */
public class DNSLookUpDialog extends AbstractSimpleTestDialog  {
  protected Table tableOutPut = null;
  /**
   * A <code>TableViewer</code> that helps to present the entries
   * in a more elegant way.
   */
//  protected TableViewer tableList;
  
  protected String[][] itemStrings;
//  protected Button lookUpButton;
//  protected Button stopButton;
  protected boolean stop;
  private ArrayList< String > hostNames = new ArrayList< String >();
  
  /**
   * Construct a new dialog from the specified test.
   * 
   * @param resources The resources that this test should be applied to.
   * @param parentShell  The parent shell of this dialog.
   */
  public DNSLookUpDialog( final List< IGridResource > resources, final Shell parentShell ) {
    super( null, resources, parentShell );
    
    this.stop = false;
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

    Group outPutGroup = new Group( mainComp, SWT.NONE );
    outPutGroup.setLayout( new GridLayout( /*3*/1, false ) );
    outPutGroup.setText( Messages.getString( "DNSLookUpDialog.output_group" ) ); //$NON-NLS-1$
    gData = new GridData( GridData.FILL_BOTH );
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    outPutGroup.setLayoutData( gData );
    
    // Create the table that holds the result
    this.tableOutPut = new Table( outPutGroup, SWT.MULTI | SWT.VIRTUAL | SWT.BORDER );
    this.tableOutPut.setHeaderVisible( true );
    this.tableOutPut.setLinesVisible( true );    
    
//    this.tableList = new TableViewer( this.tableOutPut );
//    this.tableList.setLabelProvider( new JobLabelProvider() );
//    this.tableList.setContentProvider( new JobContentProvider() );
    
    //TableColumnListener columnListener = new TableColumnListener( this.tableList );

    TableColumn hostColumn = new TableColumn( this.tableOutPut, SWT.NONE );
    hostColumn.setText( Messages.getString( "DNSLookUpDialog.hostName" ) ); //$NON-NLS-1$
    hostColumn.setWidth( 250 );    
    hostColumn.setAlignment( SWT.LEFT );
//    hostColumn.addSelectionListener( columnListener );

    TableColumn ipAdrColumn = new TableColumn( this.tableOutPut, SWT.CENTER );
    ipAdrColumn.setText( Messages.getString( "DNSLookUpDialog.ipAddress" ) ); //$NON-NLS-1$
    ipAdrColumn.setWidth( 200 );    
    ipAdrColumn.setAlignment( SWT.CENTER );
//    ipAdrColumn.addSelectionListener( columnListener );
    
//    TableColumn dnsColumn = new TableColumn( this.tableOutPut, SWT.CENTER );
//    dnsColumn.setText( Messages.getString( "DNSLookUpDialog.dnsServer" ) ); //$NON-NLS-1$
//    dnsColumn.setWidth( 200 );    
//    dnsColumn.setAlignment( SWT.CENTER );
//    dnsColumn.addSelectionListener( columnListener );
    
    // Initially we sort the jobs by ID, ascending
//    this.tableOutPut.setSortColumn( hostColumn );
//    this.tableOutPut.setSortDirection( SWT.UP );

//    this.tableList.setComparator( new TableColumnComparator( hostColumn ) );

    // Listener that waits for the results to be presented
    this.tableOutPut.addListener( SWT.SetData, new Listener() {
      public void handleEvent( final Event event ) {
        TableItem item = ( TableItem ) event.item;
        int index = DNSLookUpDialog.this.tableOutPut.indexOf( item );
        item.setText( DNSLookUpDialog.this.itemStrings [ index ] );
      }
    });

    this.tableOutPut.setItemCount( this.hostNames.size() );
    
    gData = new GridData( SWT.FILL, SWT.FILL, true, true );
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    
/*    Composite outControls = new Composite( outPutGroup, SWT.NONE );
    GridLayout gLayout = new GridLayout( 1, false );
    gLayout.marginHeight = 0;
    gLayout.marginWidth = 0;
    outControls.setLayout( gLayout );
    gData = new GridData( GridData.VERTICAL_ALIGN_BEGINNING );
    outControls.setLayoutData( gData );    
    
    this.lookUpButton = new Button( outControls, SWT.PUSH );
    this.lookUpButton.setText( Messages.getString( "DNSLookUpDialog.lookUpButton" ) ); //$NON-NLS-1$
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.verticalAlignment = GridData.BEGINNING;
    this.lookUpButton.setLayoutData( gData );

    this.stopButton = new Button( outControls, SWT.PUSH );
    this.stopButton.setText( Messages.getString( "DNSLookUpDialog.stopButton" ) ); //$NON-NLS-1$
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.verticalAlignment = GridData.BEGINNING;
    this.stopButton.setLayoutData( gData );
    this.stopButton.setEnabled( false );
    
    this.lookUpButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e) {
        DNSLookUpDialog.this.stopButton.setEnabled( true );
        DNSLookUpDialog.this.lookUpButton.setEnabled( false );
        runLookup();
        DNSLookUpDialog.this.stopButton.setEnabled( false );
        DNSLookUpDialog.this.lookUpButton.setEnabled( true );
      }
    });
    
    this.stopButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e) {
        DNSLookUpDialog.this.stop = true; 
      }
    });

    parent.getShell().setDefaultButton( this.lookUpButton );
    */
    runLookup();
    
    return mainComp;
  }
  
  /**
   * Method that looks up the IP for each of the hosts. 
   */
  protected void runLookup() {
    InetAddress adr = null;

//    clearItemString();
    
    for ( int i = 0; i < this.hostNames.size() && !this.stop; ++i ) {
      try {
        adr = InetAddress.getByName( this.hostNames.get( i ) );
          
        if ( null != adr ) 
          this.itemStrings[ i ][ 1 ] = adr.getHostAddress();
        else
          this.itemStrings[ i ][ 1 ] = Messages.getString( "DNSLookUpDialog.n_a" ); //$NON-NLS-1$
      } catch( UnknownHostException e ) {
        this.itemStrings[ i ][ 1 ] = Messages.getString( "DNSLookUpDialog.n_a" ); //$NON-NLS-1$
      }
      this.tableOutPut.clear( i );
    }
      
    // If the end of the look were due to the stop we reset it
    this.stop = false;
  }
  
 /* private void clearItemString() {
    for ( int i = 0; i < this.hostNames.size(); ++i ) {
      for ( int j = 1; j < 2; ++j ) {
        this.itemStrings[ i ][ j ] = null;
      }
    }
    this.tableOutPut.clearAll();
  }
*/
  
}
