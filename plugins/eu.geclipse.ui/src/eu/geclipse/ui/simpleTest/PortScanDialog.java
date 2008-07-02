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
  
  boolean port21_entered = false;
  boolean port22_entered = false;
  boolean port23_entered = false;
  boolean port25_entered = false;
  boolean port80_entered = false;
  
  StyledText results;
  
  
  private Composite mainComp;
  
  private Composite portComp,  resultsComp, listComp;
  private ArrayList< String > hostNames = new ArrayList< String >();
  
  private ArrayList< PortScanJob > scanJobs = new ArrayList < PortScanJob >();
  
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
  
  protected void configureShell( final Shell newShell ) {
    super.configureShell( newShell );
    newShell.setMinimumSize( 500, 520 );
    newShell.setText( Messages.getString( "PortScanDialog.dialogTitle" ) ); //$NON-NLS-1$
  }
  
  protected Control createDialogArea( final Composite parent ) {
    
    //get the hostname (ip) from the selected resources
    for ( int i = 0; i < this.resources.size(); ++i ) {
      ip = this.resources.get( i ).getHostName();
      
      if ( ip != null )
          this.hostNames.add( ip );
    }
    

    
    list = new ArrayList<PortRange>();
    portMap = new TreeMap<Integer,Boolean>();
    
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
    
    mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayout( new GridLayout( 1, false ) );
    
    Group settingsGroup = new Group( mainComp, SWT.NONE );
    settingsGroup.setLayout( new GridLayout( 2, false ) );
    settingsGroup.setText( "Port Selection" ); 
    
    Group resultsGroup = new Group( mainComp, SWT.NONE );
    resultsGroup.setLayout( new GridLayout( 2, false ) );
    resultsGroup.setText( "Results" ); 

    portComp = new Composite( settingsGroup, SWT.NONE );
    this.portComp.setLayout( new GridLayout( 4, false ) );
    this.wellKnownPortsLbl = new Label( this.portComp, 0 );
    
    //this.wellKnownPortsLbl.setLayoutData(gridData);
    this.portLbl = new Label(this.portComp, 0);
    this.portLbl.setLayoutData(gridData2);
    this.portLbl.setText("Selected Ports");

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

    this.wellKnownPortsLbl.setText("Well-Known Ports");

    this.port21chk.setText("21 - FTP");
    this.port22chk.setText("22 - SSH");
    this.port23chk.setText("23 - Telnet");
    this.port25chk.setText("25 - SMTP");
    this.port80chk.setText("80 - HTTP");
    
    this.addKnown = new Button( this.portComp, SWT.Activate );
    this.addKnown.setText("Add Selected");
    //add selected well-known ports to the list
    addKnown.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        public void widgetSelected(final org.eclipse.swt.events.SelectionEvent e) {     
            
            if(port21chk.getSelection() && !port21_entered)
            {
                port21_entered = true;
                portList.add(port21chk.getText());
                list.add(new PortRange(21));
            }
            if(port22chk.getSelection()&& !port22_entered)
            {
                port22_entered = true;
                portList.add(port22chk.getText());
                list.add(new PortRange(22));
            }
            if(port23chk.getSelection()&& !port23_entered)
            {
                port23_entered = true;
                portList.add(port23chk.getText());
                list.add(new PortRange(23));
            }
            if(port25chk.getSelection()&& !port25_entered)
            {
                port25_entered = true;
                portList.add(port25chk.getText());
                list.add(new PortRange(25));
            }
            if(port80chk.getSelection()&& !port80_entered)
            {
                port80_entered = true;
                portList.add(port80chk.getText());
                list.add(new PortRange(80));
            }
        
        }
    });
   // new Label( this.portComp, SWT.NONE ).setLayoutData(gridData); //$NON-NLS-1$
   // gridData.horizontalSpan = 4;
    

    new Label( this.portComp, SWT.HORIZONTAL );
    new Label( this.portComp, SWT.HORIZONTAL );
    //new Label( this.portComp, SWT.HORIZONTAL );
    
    Button removeButton = new Button(this.portComp,0);
    removeButton.setText("Remove Port");
    //removeButton.setLayoutData(gridData4);
    //remove selected port(s) from the list
    removeButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        public void widgetSelected(final org.eclipse.swt.events.SelectionEvent e) {
            if(!list.isEmpty() && portList.getFocusIndex()!=-1){
              if (list.get( portList.getSelectionIndex() ).getFinish()==0 && list.get( portList.getSelectionIndex()).getStart() ==  21)
                port21_entered = false;
              else if (list.get( portList.getSelectionIndex()).getFinish()==0 && list.get( portList.getSelectionIndex()).getStart() ==  22)
                port22_entered = false;
              else if (list.get( portList.getSelectionIndex()).getFinish()==0 && list.get( portList.getSelectionIndex()).getStart() ==  23)
                port23_entered = false;
              else if (list.get( portList.getSelectionIndex()).getFinish()==0 && list.get( portList.getSelectionIndex()).getStart() ==  25)
                port25_entered = false;
              else if (list.get( portList.getSelectionIndex()).getFinish()==0 && list.get( portList.getSelectionIndex()).getStart() ==  80)
                port80_entered = false;  
              
                list.remove(portList.getSelectionIndex());
                portList.remove(portList.getSelectionIndex());
            }
            
        }
    });
    
    Button removeAll = new Button(this.portComp,0);
    removeAll.setText("Remove All");
    
    removeAll.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        public void widgetSelected(final org.eclipse.swt.events.SelectionEvent e) {
            if(!list.isEmpty()){

                port21_entered = false;
                port22_entered = false;
                port23_entered = false;
                port25_entered = false;
                port80_entered = false;  
              
                list.clear();

                portList.removeAll();

                
            }
            
        }
    });
    
    listComp = new Composite( settingsGroup, SWT.NONE );
    this.listComp.setLayout( new GridLayout( 4, false ) );
    
    this.rangeLbl = new Label( this.listComp, 0 );
    this.rangeLbl.setText("Add a range of ports");
    this.rangeLbl.setLayoutData(gridData3);
    
    this.from = new Spinner( this.listComp, SWT.LEFT | SWT.SINGLE | SWT.BORDER );
    this.from.setValues(1, 1, 65535, 0, 1, 65535);
    
    this.dashlbl = new Label( this.listComp, 0 );
    this.dashlbl.setText("-");

    //this.dashlbl.setLayoutData(gridData3);
    
    this.to = new Spinner( this.listComp, SWT.LEFT | SWT.SINGLE | SWT.BORDER );
    this.to.setValues(65535, 1, 65535, 0, 1, 65535);
    
    this.addRange= new Button( this.listComp, SWT.Activate );
    this.addRange.setText("Add Range");
    this.dashlbl = new Label( this.listComp, SWT.HORIZONTAL );
    //add range of ports to the list
    addRange.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        public void widgetSelected(final org.eclipse.swt.events.SelectionEvent e) {

            String toBeAdded="";
            boolean toExit = false;
            
            int fromInt=0, toInt=0;
            try{
                fromInt = from.getSelection();
                toInt = to.getSelection();
            }catch(NumberFormatException ex){
              toExit = true;
            }
            
            if(fromInt > toInt || fromInt<=0 || toInt<=0 || fromInt > 65535 || toInt > 65535){
              toExit = true;
            }
            
            if(!toExit){
            
           toBeAdded = from.getSelection() + " - " + to.getSelection();
            if(portList.getItemCount()==0){
            portList.add(toBeAdded);
            
            list.add( new PortRange(from.getSelection(), to.getSelection() ) );
            
            toExit = true;
            }
            }
            
            else{
              return;
            }
            
            int count=0;
            for(int i=0; i<portList.getItemCount(); i++){
                if(portList.getItem(i).compareTo(toBeAdded) == 0){
                    count++;
                }
            }
            if(count==0){
                
                portList.add(toBeAdded);
                list.add( new PortRange(from.getSelection(), to.getSelection() ) );

            }
            
        }
    });

    this.specificLbl = new Label( this.listComp, 0 );
    this.specificLbl.setText("Add specific Port");
    this.specificLbl.setLayoutData(gridData3);
    
    this.specificTxt = new Spinner( this.listComp, SWT.LEFT | SWT.SINGLE | SWT.BORDER );
    this.specificTxt.setLayoutData(gridData5);
    this.specificTxt.setValues(1, 1, 65535, 0, 1, 65535);
    this.addSpecific = new Button(this.listComp, SWT.Activate);
    this.addSpecific.setText("Add Port");
    
    
    //add the specific port to the list
    addSpecific.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        public void widgetSelected(final org.eclipse.swt.events.SelectionEvent e) {
            int portInt=0;
            int counter = 0;

            portInt = specificTxt.getSelection();
      
            for(int i=0; i<list.size(); i++){
              if(list.get(i).getStart()==portInt && list.get(i).getFinish()==0){
                counter++;
              }
            }

            if(counter>0){
                return;
            }
            list.add( new PortRange(portInt, 0 ) );
            portList.add(""+portInt);

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
    itemTree.setText ( "Results List" );
    
    Tree tree = new Tree(tabFolder, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
    tree.setHeaderVisible(true);
    tree.setLayoutData(resultsData);
    
    TreeColumn hostColumn = new TreeColumn(tree, SWT.LEFT);
    hostColumn.setText("Host");
    hostColumn.setWidth(200);
    TreeColumn statusColumn = new TreeColumn(tree, SWT.CENTER);
    statusColumn.setText("Status");
    statusColumn.setWidth(70);
    
    for( int i = 0; i<hostNames.size(); i++ ){
         treeItem.add(new TreeItem(tree,SWT.NONE));
         treeItem.get(i).setText(new String[]{hostNames.get(i), "N/A"});
    }

    itemTree.setControl(tree);
    
    
    TabItem itemResults = new TabItem ( tabFolder, SWT.NULL );
    itemResults.setText ( "Log File" );

    this.results = new StyledText(tabFolder, SWT.H_SCROLL | SWT.V_SCROLL);
    this.results.setLayoutData(resultsData);
    
    itemResults.setControl(this.results);
    
    
    this.scan = new Button(this.resultsComp, SWT.Activate);
    this.scan.setText("Scan");
    //this.scan.setLayoutData(GridData.HORIZONTAL_ALIGN_CENTER);
    this.stop = new Button(this.resultsComp, 1<<9);
    this.stop.setText("Stop");
    
    //stop the running job
    stop.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        public void widgetSelected(final org.eclipse.swt.events.SelectionEvent e) {

  
        for(int i = 0; i<scanJobs.size(); i++){
            if(scanJobs.get(i)!=null){
                scanJobs.get(i).cancel();
            }
        }
        if(scanJobs.size()>0)
            results.append("Scanning Stopped.\n\n");  
        
        scanJobs.clear();

        }
    });     
    
    //calls the portScan() method to initiate the job for Port Scan
    scan.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        public void widgetSelected(final org.eclipse.swt.events. SelectionEvent e) {
          
            // Make sure the potential current pings are done
            boolean done = true;
            for ( PortScanJob portJob : scanJobs ) {
              if ( null == portJob.getResult() )
                done = false;
            }
            // At least one of the prev. jobs haven't finished yet
            if ( done ){
                generatePortList();
                portScan();
            }

        }
    });
    
    return mainComp;
  }

  //initiates the job to Port Scan the selected host
protected void portScan() {

    InetAddress ia = null;

    results.setText("");
    this.results.append("Scanning the following hosts:\n");
    
    for ( int i=0; i<this.hostNames.size(); i++ ) {
        try {
            
            ia = InetAddress.getByName(this.hostNames.get(i));

            if(ia != null){
                this.results.append(this.hostNames.get(i) + "\n");
                this.treeItem.get(i).removeAll();
                job = new PortScanJob(ia,portMap,results, this.treeItem.get(i));
                job.schedule();
            
                scanJobs.add(job);
            }
            else{
            ///
            }
        } catch (UnknownHostException e) {
            results.append( "Cannot connect to " + this.hostNames.get(i) );
           // return;
        }
    }
    
    this.results.append("\n");
}

protected void generatePortList() {
    for(int i=0; i<list.size(); i++){
        if(list.get(i).getFinish()==0){
            portMap.put(list.get(i).getStart(), false);
        }
        else{
            for(int j=list.get(i).getStart(); j<=list.get(i).getFinish(); j++){
                portMap.put(j, false);
            }
        }
    }
    
}

protected void printPortList() {
    
    //results.append(portMap.keySet().toString()+"\n");
    //for(int i=0; i<portMap.size(); i++){
        //results.append(portMap.get(key).toString()+"\n");
    //}
    
}

protected void cancelPressed() {
  super.cancelPressed();
  if(job!=null)
    job.cancel();
}

protected void okPressed() {
  super.okPressed();
  if(job!=null)
    job.cancel();
}


}