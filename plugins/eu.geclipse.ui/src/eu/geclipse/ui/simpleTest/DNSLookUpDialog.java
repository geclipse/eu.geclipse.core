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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
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
  protected Button lookUpButton;
  protected Button stopButton;
  protected DNSLookUpJob lookUpJob;
  private ArrayList< String > hostNames = new ArrayList< String >();
  
  /**
   * Construct a new dialog from the specified test.
   * 
   * @param resources The resources that this test should be applied to.
   * @param parentShell  The parent shell of this dialog.
   */
  public DNSLookUpDialog( final List< IGridResource > resources, final Shell parentShell ) {
    super( null, resources, parentShell );
    
    // First we gather the host name of all the resources
    String name;
    for ( int i = 0; i < this.resources.size(); ++i ) {
      name = this.resources.get( i ).getHostName();

      if ( null != name )
        this.hostNames.add( name );
    }
  
    // Then we prepare the datastructure for the table of results with the hostnames 
    this.itemStrings = new String [ this.hostNames.size() ] [ 2 ];  
    
    for ( int i = 0; i < this.hostNames.size(); ++i )
      this.itemStrings[ i ] [ 0 ] = this.hostNames.get( i );
  }

  @Override
  protected void configureShell( final Shell newShell ) {
    super.configureShell( newShell );
    newShell.setMinimumSize( 560, 320 );
    newShell.setText( Messages.getString( "DNSLookUpDialog.dialogTitle" ) ); //$NON-NLS-1$
  }

  @Override
  public boolean close() {
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
    gData = new GridData( SWT.BEGINNING, SWT.BEGINNING, false, false );
    mainComp.setLayoutData( gData );

    Group outPutGroup = new Group( mainComp, SWT.NONE );
    outPutGroup.setLayout( new GridLayout( 2, false ) );
    outPutGroup.setText( Messages.getString( "DNSLookUpDialog.output_group" ) ); //$NON-NLS-1$
    gData = new GridData( SWT.BEGINNING, SWT.BEGINNING, true, true );
    outPutGroup.setLayoutData( gData );

    
    Composite tableComp = new Composite( outPutGroup, SWT.NONE );
    // Create the table that holds the result
    this.tableOutPut = new Table( tableComp, SWT.MULTI | SWT.VIRTUAL | SWT.BORDER );
    this.tableOutPut.setHeaderVisible( true );
    this.tableOutPut.setLinesVisible( true );    

    GridData gd = new GridData( SWT.FILL, SWT.FILL, true, true );
    this.tableOutPut.setLayoutData( gd );

    GridData gridData = new GridData( SWT.BEGINNING, SWT.BEGINNING, true, true );
    gridData.heightHint = 200;
    tableComp.setLayout( new GridLayout( 2, false ) );
    tableComp.setLayoutData( gridData );
    
    
//    this.tableList = new TableViewer( this.tableOutPut );
//    this.tableList.setLabelProvider( new JobLabelProvider() );
//    this.tableList.setContentProvider( new JobContentProvider() );
    
    //TableColumnListener columnListener = new TableColumnListener( this.tableList );

    TableColumn hostColumn = new TableColumn( this.tableOutPut, SWT.LEFT );
    hostColumn.setText( Messages.getString( "DNSLookUpDialog.hostName" ) ); //$NON-NLS-1$
    hostColumn.setWidth( 250 );    
//    hostColumn.setAlignment( SWT.LEFT );
//    hostColumn.addSelectionListener( columnListener );

    TableColumn ipAdrColumn = new TableColumn( this.tableOutPut, SWT.LEFT );
    ipAdrColumn.setText( Messages.getString( "DNSLookUpDialog.ipAddress" ) ); //$NON-NLS-1$
    ipAdrColumn.setWidth( 150 );    
//    ipAdrColumn.setAlignment( SWT.LEFT );
//    ipAdrColumn.addSelectionListener( columnListener );
    
//    TableColumn dnsColumn = new TableColumn( this.tableOutPut, SWT.CENTER );
//    dnsColumn.setText( Messages.getString( "DNSLookUpDialog.dnsServer" ) ); //$NON-NLS-1$
//    dnsColumn.setWidth( 200 );    
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
    this.lookUpJob = new DNSLookUpJob( this.tableOutPut, this.itemStrings );
    
    Composite outControls = new Composite( outPutGroup, SWT.NONE );
    GridLayout gLayout = new GridLayout( 1, false );
    gLayout.marginHeight = 0;
    gLayout.marginWidth = 0;
    outControls.setLayout( gLayout );
    gData = new GridData( GridData.VERTICAL_ALIGN_BEGINNING );
    outControls.setLayoutData( gData );    
    
    this.lookUpButton = new Button( outControls, SWT.PUSH );
    this.lookUpButton.setText( Messages.getString( "DNSLookUpDialog.lookUpButton" ) ); //$NON-NLS-1$
    this.lookUpButton.setToolTipText( Messages.getString( "DNSLookUpDialog.lookUpButtonToolTip" ) ); //$NON-NLS-1$
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.verticalAlignment = GridData.BEGINNING;
    this.lookUpButton.setLayoutData( gData );

    this.stopButton = new Button( outControls, SWT.PUSH );
    this.stopButton.setText( Messages.getString( "DNSLookUpDialog.stopButton" ) ); //$NON-NLS-1$
    this.stopButton.setToolTipText( Messages.getString( "DNSLookUpDialog.stopButtonToolTip" ) ); //$NON-NLS-1$
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.verticalAlignment = GridData.BEGINNING;
    this.stopButton.setLayoutData( gData );
    this.stopButton.setEnabled( false );
    
    this.lookUpButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e) {

        if ( Job.NONE == DNSLookUpDialog.this.lookUpJob.getState()  ) {
          DNSLookUpDialog.this.lookUpJob.schedule(); 
          DNSLookUpDialog.this.stopButton.setEnabled( true );
        }
      }
    });
    
    this.stopButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e) {
        DNSLookUpDialog.this.lookUpJob.cancel();
      }
    });

    parent.getShell().setDefaultButton( this.lookUpButton );
    
    
    return mainComp;
  }
}
