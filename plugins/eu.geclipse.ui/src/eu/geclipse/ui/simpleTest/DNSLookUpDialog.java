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
 // protected Button lookUpButton;
 // protected Button stopButton;
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
    gData = new GridData( SWT.FILL, SWT.FILL, true, true);
    mainComp.setLayoutData( gData );
    
    Group outPutGroup = new Group( mainComp, SWT.NONE );
    outPutGroup.setLayout( new GridLayout( 2, false ) );
    outPutGroup.setText( Messages.getString( "DNSLookUpDialog.output_group" ) ); //$NON-NLS-1$
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    gData.verticalAlignment = SWT.BEGINNING;
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
    tableComp.setLayoutData( gData );

    TableColumn hostColumn = new TableColumn( this.tableOutPut, SWT.LEFT );
    hostColumn.setText( Messages.getString( "DNSLookUpDialog.hostName" ) ); //$NON-NLS-1$
    hostColumn.setWidth( 250 );

    TableColumn ipAdrColumn = new TableColumn( this.tableOutPut, SWT.LEFT );
    ipAdrColumn.setText( Messages.getString( "DNSLookUpDialog.ipAddress" ) ); //$NON-NLS-1$
    ipAdrColumn.setWidth( 150 );

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

    if ( Job.NONE == DNSLookUpDialog.this.lookUpJob.getState()  ) {
        DNSLookUpDialog.this.lookUpJob.schedule(); 
        //DNSLookUpDialog.this.stopButton.setEnabled( true );
      }
    
    //parent.getShell().setDefaultButton( this.lookUpButton );
    
    
    return mainComp;
  }
}
