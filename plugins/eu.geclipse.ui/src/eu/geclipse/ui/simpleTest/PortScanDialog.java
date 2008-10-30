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
 *      - Christodoulos Efstathiades (cs05ce1@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.ui.simpleTest;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import eu.geclipse.core.model.IGridResource;
import eu.geclipse.core.simpleTest.ISimpleTest;
import eu.geclipse.ui.dialogs.AbstractSimpleTestDialog;


/**
 * A dialog that allows the user to port scan selected resources
 * 
 * @author cs05ce1
 *
 */
public class PortScanDialog extends AbstractSimpleTestDialog{
  
  protected Button port21chk, port22chk, port23chk, port25chk, port80chk;
  protected Button addKnown, addRange, addSpecific;
  protected Button scan,stop;
  protected Button multiThreadButton;
  
  protected Label wellKnownPortsLbl;
  protected Label rangeLbl;
  protected Label portLbl;
  protected Label specificLbl;
  protected Label portsScannedLabel;
  
  protected Spinner from, to, specificTxt;
  protected Label dashlbl;

  protected org.eclipse.swt.widgets.List portList;
  
  protected TreeMap<Integer, Boolean> portMap;
  
  protected ArrayList<PortRange> list;
  

  
  protected int portsScanned = 0;
  protected int portsOpen = 0;
  protected int portsClosed = 0;
  
  protected Integer[] ports;
  
  protected PortScanJob job;
  
  protected String ip;
  
  protected boolean port21_entered = false;
  protected boolean port22_entered = false;
  protected boolean port23_entered = false;
  protected boolean port25_entered = false;
  protected boolean port80_entered = false;

  protected ArrayList< PortScanJob > scanJobs = new ArrayList < PortScanJob >();

  protected StyledText results;
  
  private Composite mainComp;
  
  private Composite portComp,  resultsComp, listComp;
  private ArrayList< String > hostNames = new ArrayList< String >();
  
  
  private ArrayList<TreeItem> treeItem = new ArrayList<TreeItem>();
  
  /**
   * Construct a new dialog from the specified test.
   * 
   * @param test The <code>ISimpleTest</code> for which to create the dialog for.
   * @param resources The resources that this test should be applied to.
   * @param parentShell  The parent shell of this dialog.
   */
  public PortScanDialog( final ISimpleTest test, final List< IGridResource > resources, final Shell parentShell ) {
    super( test, resources, parentShell );
  }
  
  @Override
  protected void configureShell( final Shell newShell ) {
    super.configureShell( newShell );
    //newShell.setMinimumSize( 500, 520 );
    newShell.setText( Messages.getString( "PortScanDialog.dialogTitle" ) ); //$NON-NLS-1$
  }
  
  @Override
  protected Control createDialogArea( final Composite parent ) {
    
    //get the hostname (ip) from the selected resources
    for ( int i = 0; i < this.resources.size(); ++i ) {
      this.ip = this.resources.get( i ).getHostName();
      
      if ( this.ip != null )
          this.hostNames.add( this.ip );
    }
    

    
    this.list = new ArrayList<PortRange>();
    this.portMap = new TreeMap<Integer,Boolean>();
    
   // setSize(new Point(500, 520));
    //setLayout(new GridLayout());
    
    
    GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
    gridData.horizontalSpan = 4;
    
    GridData gridData2 = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
    gridData2.horizontalSpan = 3;
    
    GridData gridData3 = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
    gridData3.horizontalSpan = 4;
    
    GridData gridData4 = new GridData(GridData.HORIZONTAL_ALIGN_END);
    gridData4.horizontalSpan = 3;
    
    GridData gridData5 = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
    gridData5.horizontalSpan = 2;
    
    GridData listData = new GridData( GridData.HORIZONTAL_ALIGN_CENTER );
    listData.horizontalSpan = 3;
    listData.verticalSpan = 7;
    listData.widthHint = 150;
    listData.heightHint = 150;
    
    
    this.mainComp = new Composite( parent, SWT.FILL );
    this.mainComp.setLayout( new GridLayout( 1, false ) );
    GridData gData = new GridData( SWT.FILL, SWT.FILL, true, true);
    this.mainComp.setLayoutData( gData );
    
    /*
    this.mainComp = new Composite( parent, SWT.NONE );
    this.mainComp.setLayout( new GridLayout( 1, false ) );
    */
    
    Group settingsGroup = new Group( this.mainComp, SWT.FILL );
    settingsGroup.setLayout( new GridLayout( 2, false ) );
    settingsGroup.setText( Messages.getString("PortScanDialog.selectionGroup" ) ); //$NON-NLS-1$
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    settingsGroup.setLayoutData( gData );
    
    /*
    Group settingsGroup = new Group( this.mainComp, SWT.NONE );
    settingsGroup.setLayout( new GridLayout( 2, false ) );
    settingsGroup.setText( Messages.getString("PortScanDialog.selectionGroup" )); //$NON-NLS-1$
    */
    
    Group resultsGroup = new Group( this.mainComp, SWT.FILL );
    resultsGroup.setLayout( new GridLayout( 2, false ) );
    resultsGroup.setText( Messages.getString("PortScanDialog.resultsGroup" ) ); //$NON-NLS-1$
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    resultsGroup.setLayoutData( gData );
    
    /*
    Group resultsGroup = new Group( this.mainComp, SWT.NONE );
    resultsGroup.setLayout( new GridLayout( 2, false ) );
    resultsGroup.setText(Messages.getString("PortScanDialog.resultsGroup" )); //$NON-NLS-1$
    */
    
    
    this.portComp = new Composite( settingsGroup, SWT.FILL );
    this.portComp.setLayout( new GridLayout( 4, false ) );
    this.wellKnownPortsLbl = new Label( this.portComp, 0 );
    
    //this.wellKnownPortsLbl.setLayoutData(gridData);
    this.portLbl = new Label(this.portComp, 0);
    this.portLbl.setLayoutData(gridData2);
    this.portLbl.setText( Messages.getString("PortScanDialog.selectedPortsLbl" )); //$NON-NLS-1$

    this.port21chk = new Button( this.portComp, SWT.CHECK );
   // this.port21chk.setLayoutData(gridData);
    
    this.portList = new org.eclipse.swt.widgets.List(this.portComp, SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI | SWT.BORDER);
    ( ( Control )this.portList ).setLayoutData(listData);


    
    this.port22chk = new Button( this.portComp, SWT.CHECK );
   // this.port22chk.setLayoutData(gridData);
    this.port23chk = new Button( this.portComp, SWT.CHECK );
    //this.port23chk.setLayoutData(gridData);
    this.port25chk = new Button( this.portComp, SWT.CHECK );
    //this.port25chk.setLayoutData(gridData);
    this.port80chk = new Button( this.portComp, SWT.CHECK );
    //this.port80chk.setLayoutData(gridData);

    this.wellKnownPortsLbl.setText(Messages.getString("PortScanDialog.wellKnownPortsLbl" )); //$NON-NLS-1$

    this.port21chk.setText( Messages.getString("PortScanDialog.port21" )); //$NON-NLS-1$
    this.port22chk.setText( Messages.getString("PortScanDialog.port22" )); //$NON-NLS-1$ 
    this.port23chk.setText( Messages.getString("PortScanDialog.port23" )); //$NON-NLS-1$ 
    this.port25chk.setText( Messages.getString("PortScanDialog.port25" )); //$NON-NLS-1$ 
    this.port80chk.setText( Messages.getString("PortScanDialog.port80" )); //$NON-NLS-1$ 
    
    this.addKnown = new Button( this.portComp, SWT.Activate );
    this.addKnown.setText( Messages.getString("PortScanDialog.addSelectedButton" ) ); //$NON-NLS-1$
    //add selected well-known ports to the list
    this.addKnown.addSelectionListener( new org.eclipse.swt.events.SelectionAdapter() {
      @Override
      public void widgetSelected( final org.eclipse.swt.events.SelectionEvent e ) {     
        if( PortScanDialog.this.port21chk.getSelection() && !PortScanDialog.this.port21_entered ) {
          PortScanDialog.this.port21_entered = true;
          PortScanDialog.this.portList.add( PortScanDialog.this.port21chk.getText() );
          PortScanDialog.this.list.add( new PortRange( 21 ) );
        }
        if( PortScanDialog.this.port22chk.getSelection() && !PortScanDialog.this.port22_entered ) {
          PortScanDialog.this.port22_entered = true;
          PortScanDialog.this.portList.add( PortScanDialog.this.port22chk.getText() );
          PortScanDialog.this.list.add( new PortRange( 22 ) );
        }
        if( PortScanDialog.this.port23chk.getSelection() && !PortScanDialog.this.port23_entered ) {
          PortScanDialog.this.port23_entered = true;
          PortScanDialog.this.portList.add( PortScanDialog.this.port23chk.getText() );
          PortScanDialog.this.list.add( new PortRange( 23 ) );
        }
        if( PortScanDialog.this.port25chk.getSelection() && !PortScanDialog.this.port25_entered ) {
          PortScanDialog.this.port25_entered = true;
          PortScanDialog.this.portList.add( PortScanDialog.this.port25chk.getText() );
          PortScanDialog.this.list.add(new PortRange( 25 ) );
        }
        if( PortScanDialog.this.port80chk.getSelection() && !PortScanDialog.this.port80_entered ) {
          PortScanDialog.this.port80_entered = true;
          PortScanDialog.this.portList.add( PortScanDialog.this.port80chk.getText() );
          PortScanDialog.this.list.add(new PortRange(80));
        }
      }
    });
   // new Label( this.portComp, SWT.NONE ).setLayoutData(gridData); //$NON-NLS-1$
   // gridData.horizontalSpan = 4;
    

    new Label( this.portComp, SWT.HORIZONTAL );
    new Label( this.portComp, SWT.HORIZONTAL );
    //new Label( this.portComp, SWT.HORIZONTAL );
    
    Button removeButton = new Button( this.portComp, 0 );
    removeButton.setText( Messages.getString("PortScanDialog.removeButton" ) ); //$NON-NLS-1$ 
    //removeButton.setLayoutData(gridData4);
    //remove selected port(s) from the list
    removeButton.addSelectionListener( new org.eclipse.swt.events.SelectionAdapter() {
      @Override
      public void widgetSelected( final org.eclipse.swt.events.SelectionEvent e ) {
        if( !PortScanDialog.this.list.isEmpty() && PortScanDialog.this.portList.getFocusIndex() != -1 ){
          if ( PortScanDialog.this.list.get( PortScanDialog.this.portList.getSelectionIndex() ).getFinish() == 0 
               && PortScanDialog.this.list.get( PortScanDialog.this.portList.getSelectionIndex()).getStart() == 21)
            PortScanDialog.this.port21_entered = false;
          else if ( PortScanDialog.this.list.get( PortScanDialog.this.portList.getSelectionIndex()).getFinish() == 0 
                    && PortScanDialog.this.list.get( PortScanDialog.this.portList.getSelectionIndex()).getStart() == 22)
            PortScanDialog.this.port22_entered = false;
          else if ( PortScanDialog.this.list.get( PortScanDialog.this.portList.getSelectionIndex()).getFinish() == 0 
                    && PortScanDialog.this.list.get( PortScanDialog.this.portList.getSelectionIndex()).getStart() == 23)
            PortScanDialog.this.port23_entered = false;
          else if ( PortScanDialog.this.list.get( PortScanDialog.this.portList.getSelectionIndex()).getFinish() == 0 
                    && PortScanDialog.this.list.get( PortScanDialog.this.portList.getSelectionIndex()).getStart() == 25)
            PortScanDialog.this.port25_entered = false;
          else if ( PortScanDialog.this.list.get( PortScanDialog.this.portList.getSelectionIndex()).getFinish() == 0 
                    && PortScanDialog.this.list.get( PortScanDialog.this.portList.getSelectionIndex()).getStart() == 80)
            PortScanDialog.this.port80_entered = false;  
              
          PortScanDialog.this.list.remove( PortScanDialog.this.portList.getSelectionIndex() );
          PortScanDialog.this.portList.remove( PortScanDialog.this.portList.getSelectionIndex() );
        }
      }
    });
    
    Button removeAll = new Button(this.portComp,0);
    removeAll.setText( Messages.getString("PortScanDialog.removeAllButton" )); //$NON-NLS-1$ 
    
    removeAll.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
      @Override
      public void widgetSelected( final org.eclipse.swt.events.SelectionEvent e ) {
        if( !PortScanDialog.this.list.isEmpty() ){
          PortScanDialog.this.port21_entered = false;
          PortScanDialog.this.port22_entered = false;
          PortScanDialog.this.port23_entered = false;
          PortScanDialog.this.port25_entered = false;
          PortScanDialog.this.port80_entered = false;  
              
          PortScanDialog.this.list.clear();
          PortScanDialog.this.portList.removeAll();
        }
      }
    });
    
    this.listComp = new Composite( settingsGroup, SWT.FILL );
    this.listComp.setLayout( new GridLayout( 4, false ) );
    
    this.rangeLbl = new Label( this.listComp, 0 );
    this.rangeLbl.setText( Messages.getString("PortScanDialog.rangeLbl" )); //$NON-NLS-1$ 
    this.rangeLbl.setLayoutData(gridData3);
    
    this.from = new Spinner( this.listComp, SWT.LEFT | SWT.SINGLE | SWT.BORDER );
    this.from.setValues(1, 1, 65535, 0, 1, 65535);
    
    this.dashlbl = new Label( this.listComp, 0 );
    this.dashlbl.setText( "-" ); //$NON-NLS-1$

    //this.dashlbl.setLayoutData(gridData3);
    
    this.to = new Spinner( this.listComp, SWT.LEFT | SWT.SINGLE | SWT.BORDER );
    this.to.setValues(65535, 1, 65535, 0, 1, 65535);
    
    this.addRange= new Button( this.listComp, SWT.Activate );
    this.addRange.setText( Messages.getString("PortScanDialog.addRangeButton" )); //$NON-NLS-1$ 
    this.dashlbl = new Label( this.listComp, SWT.HORIZONTAL );
    //add range of ports to the list
    this.addRange.addSelectionListener( new org.eclipse.swt.events.SelectionAdapter() {
      @Override
      public void widgetSelected( final org.eclipse.swt.events.SelectionEvent e ) {
        String toBeAdded = ""; //$NON-NLS-1$
        boolean toExit = false;
            
        int fromInt = 0, toInt = 0;
        try{
          fromInt = PortScanDialog.this.from.getSelection();
          toInt = PortScanDialog.this.to.getSelection();
        }catch(NumberFormatException ex){
          toExit = true;
        }
            
        if( fromInt > toInt || fromInt <= 0 || toInt <= 0 || fromInt > 65535 || toInt > 65535 ){
          toExit = true;
        }
            
        if( !toExit ){
          toBeAdded = PortScanDialog.this.from.getSelection() 
                      + " - " + PortScanDialog.this.to.getSelection(); //$NON-NLS-1$
          if( PortScanDialog.this.portList.getItemCount() == 0 ){
            PortScanDialog.this.portList.add( toBeAdded );
            
            PortScanDialog.this.list.add( new PortRange( 
                                 PortScanDialog.this.from.getSelection(), PortScanDialog.this.to.getSelection() ) );
            
            toExit = true;
          }
        }
        else{
          return;
        }
            
        int count = 0;
        for( int i = 0; i < PortScanDialog.this.portList.getItemCount(); i++ ){
          if( PortScanDialog.this.portList.getItem( i ).compareTo( toBeAdded ) == 0 ){
            count++;
          }
        }
        if( count == 0 ){
          PortScanDialog.this.portList.add( toBeAdded );
          PortScanDialog.this.list.add( new PortRange( 
                  PortScanDialog.this.from.getSelection(), PortScanDialog.this.to.getSelection() ) );
        }
      }
    });

    this.specificLbl = new Label( this.listComp, 0 );
    this.specificLbl.setText( Messages.getString("PortScanDialog.addSpecificLbl" )); //$NON-NLS-1$ 
    this.specificLbl.setLayoutData(gridData3);
    
    this.specificTxt = new Spinner( this.listComp, SWT.LEFT | SWT.SINGLE | SWT.BORDER );
    this.specificTxt.setLayoutData(gridData5);
    this.specificTxt.setValues(1, 1, 65535, 0, 1, 65535);
    this.addSpecific = new Button(this.listComp, SWT.Activate);
    this.addSpecific.setText( Messages.getString("PortScanDialog.addPortButton" )); //$NON-NLS-1$ 
    
    
    //add the specific port to the list
    this.addSpecific.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
      @Override
      public void widgetSelected(final org.eclipse.swt.events.SelectionEvent e) {
        int portInt=0;
        int counter = 0;

        portInt = PortScanDialog.this.specificTxt.getSelection();
      
        for( int i = 0; i < PortScanDialog.this.list.size(); i++ ){
          if( PortScanDialog.this.list.get( i ).getStart() == portInt 
              && PortScanDialog.this.list.get( i ).getFinish() == 0 ){
            counter++;
          }
        }

        if( counter > 0 ){
          return;
        }
        PortScanDialog.this.list.add( new PortRange( portInt, 0 ) );
        PortScanDialog.this.portList.add( "" + portInt ); //$NON-NLS-1$
      }
    });
    
    new Label( this.listComp, SWT.HORIZONTAL );
    new Label( this.listComp, SWT.HORIZONTAL );
    new Label( this.listComp, SWT.HORIZONTAL );
    
    this.resultsComp = new Composite( resultsGroup, SWT.NONE );
    this.resultsComp.setLayout( new GridLayout( 2, false ) );
    GridData resultsData = new GridData( GridData.FILL_BOTH );
    resultsData.horizontalSpan = 3;
    resultsData.verticalSpan = 1;
    resultsData.grabExcessHorizontalSpace = true;
    resultsData.grabExcessVerticalSpace = true;
    resultsData.heightHint = 300;

    resultsGroup.setLayoutData(resultsData);
    this.resultsComp.setLayoutData(resultsData);
    
    // Create the tabbed panel with the results 
    TabFolder tabFolder = new TabFolder (  this.resultsComp, SWT.NONE );
    tabFolder.setLayoutData(resultsData);
    
    TabItem itemTree = new TabItem ( tabFolder, SWT.NULL );
    itemTree.setText ( Messages.getString("PortScanDialog.resultsList" )); //$NON-NLS-1$ 
    
    Tree tree = new Tree(tabFolder, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
    tree.setHeaderVisible(true);
    tree.setLayoutData(resultsData);
    
    TreeColumn hostColumn = new TreeColumn( tree, SWT.LEFT );
    hostColumn.setText( Messages.getString("PortScanDialog.hostColumn" )); //$NON-NLS-1$ 
    hostColumn.setWidth( 200 );
    TreeColumn statusColumn = new TreeColumn( tree, SWT.CENTER );
    statusColumn.setText( Messages.getString( "PortScanDialog.statusColumn" ) ); //$NON-NLS-1$ 
    statusColumn.setWidth( 70 );
    
    for( int i = 0; i < this.hostNames.size(); i++ ){
      this.treeItem.add( new TreeItem( tree,SWT.NONE ) );
      this.treeItem.get( i ).setText( new String[]{ this.hostNames.get( i ), "N/A" } );
    }

    itemTree.setControl(tree);
    
    
    TabItem itemResults = new TabItem ( tabFolder, SWT.NULL );
    itemResults.setText ( Messages.getString("PortScanDialog.logItem" )); //$NON-NLS-1$ 

    this.results = new StyledText(tabFolder, SWT.H_SCROLL | SWT.V_SCROLL);
    this.results.setLayoutData(resultsData);
    
    itemResults.setControl(this.results);
    
    
    this.scan = new Button(this.resultsComp, SWT.Activate);
    this.scan.setText( Messages.getString("PortScanDialog.scanButton" )); //$NON-NLS-1$ 
    //this.scan.setLayoutData(GridData.HORIZONTAL_ALIGN_CENTER);
    this.stop = new Button(this.resultsComp, 1<<9);
    this.stop.setText( Messages.getString("PortScanDialog.stopButton" )); //$NON-NLS-1$ 
    
    //stop the running job
    this.stop.addSelectionListener( new org.eclipse.swt.events.SelectionAdapter() {
      @Override
      public void widgetSelected( final org.eclipse.swt.events.SelectionEvent e ) {
        for( int i = 0; i < PortScanDialog.this.scanJobs.size(); i++ ){
          if( PortScanDialog.this.scanJobs.get( i ) != null ){
            PortScanDialog.this.scanJobs.get( i ).cancel();
          }
        }
        if( PortScanDialog.this.scanJobs.size() > 0 )
          PortScanDialog.this.results.append( "Scanning Stopped.\n\n" );  
        PortScanDialog.this.scanJobs.clear();
      }
    });     
    
    //calls the portScan() method to initiate the job for Port Scan
    this.scan.addSelectionListener( new org.eclipse.swt.events.SelectionAdapter() {
      @Override
      public void widgetSelected( final org.eclipse.swt.events. SelectionEvent e ) {
        // Make sure the potential current port scans are done
        boolean done = true;
        for ( PortScanJob portJob : PortScanDialog.this.scanJobs ) {
          if ( null == portJob.getResult() )
            done = false;
        }
        // At least one of the prev. jobs haven't finished yet
        if ( done ){
          PortScanDialog.this.scan.setEnabled( false );
          generatePortList();
          portScan();
        }
            
        PortScanDialog.this.scan.setEnabled( true );
      }
    });
    
    
    return this.mainComp;
  }

  /**
   * 
   * Initiates the job to Port Scan the selected host
   */
  protected void portScan() {
    InetAddress ia = null;

    this.results.setText( "" ); //$NON-NLS-1$
    this.results.append( "Scanning the following hosts:\n" );
    
    for ( int i = 0; i < this.hostNames.size(); i++ ) {
      try {
        ia = InetAddress.getByName( this.hostNames.get( i ) );

        if( ia != null ){
          this.results.append( this.hostNames.get( i ) + "\n" ); //$NON-NLS-1$
          this.treeItem.get( i ).removeAll();
          this.job = new PortScanJob( ia, this.portMap, this.results, this.treeItem.get( i ) );

          this.job.schedule();
            
          this.scanJobs.add( this.job );
        }
        else{
          ///
        }
      } catch ( UnknownHostException e ) {
        this.results.append( "Cannot connect to " + this.hostNames.get( i ) );
      }
    }
    
    this.results.append( "\n" ); //$NON-NLS-1$
  }

  /**
   * Generates the list of ports to scan.
   * 
   */
  protected void generatePortList() {
    for( int i = 0; i < this.list.size(); i++ ){
      if( this.list.get( i ).getFinish() == 0 ){
        this.portMap.put( this.list.get( i ).getStart(), false );
      }
      else{
        for( int j = this.list.get( i ).getStart(); j <= this.list.get( i ).getFinish(); j++ ){
          this.portMap.put( j, false );
        }
      }
    }
  }

}