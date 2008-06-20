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


import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.custom.StyledText;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import org.eclipse.swt.widgets.Text;

import org.eclipse.swt.widgets.Button;

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
  
  protected Text from, to, specificTxt;
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
    gridData4.horizontalSpan = 4;
    
    GridData listData = new GridData( GridData.HORIZONTAL_ALIGN_CENTER );
    listData.horizontalSpan = 3;
    listData.verticalSpan = 7;
    listData.widthHint = 150;
    listData.heightHint = 150;
    
    mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayout( new GridLayout( 2, false ) );
    

    portComp = new Composite( this.mainComp, SWT.NONE );
    this.portComp.setLayout( new GridLayout( 4, false ) );
    this.wellKnownPortsLbl = new Label( this.portComp, 0 );
    
    //this.wellKnownPortsLbl.setLayoutData(gridData);
    this.portLbl = new Label(this.portComp, 0);
    this.portLbl.setLayoutData(gridData2);
    this.portLbl.setText("Selected Ports");

    this.port21chk = new Button( this.portComp, SWT.CHECK );
   // this.port21chk.setLayoutData(gridData);
    
    this.portList = new org.eclipse.swt.widgets.List(this.portComp, SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI);
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
    
    
    Button removeButton = new Button(this.portComp,0);
    removeButton.setText("Remove Port");
    removeButton.setLayoutData(gridData4);
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
    
    listComp = new Composite( this.mainComp, SWT.NONE );
    this.listComp.setLayout( new GridLayout( 2, false ) );
    

    
    this.rangeLbl = new Label( this.listComp, 0 );
    this.rangeLbl.setText("Add a range of ports");
    this.rangeLbl.setLayoutData(gridData3);
    
    this.from = new Text( this.listComp, 0 );
    this.dashlbl = new Label( this.listComp, SWT.HORIZONTAL );
    this.dashlbl.setText("To:");
    this.dashlbl.setLayoutData(gridData3);
    
    this.to = new Text( this.listComp, 0 );
    
    this.addRange= new Button( this.listComp, SWT.Activate );
    this.addRange.setText("Add Range");
    
    //add range of ports to the list
    addRange.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        public void widgetSelected(final org.eclipse.swt.events.SelectionEvent e) {
            System.out.println("widgetRangeSelected()"); 
  
            //if(!(from.getSelectionText().matches("\\d") || to.getSelectionText().matches("\\d"))){
            //  return;
            //}
            
            boolean toExit = false;
            
            int fromInt=0, toInt=0;
            try{
                fromInt = Integer.parseInt(from.getText());
                toInt = Integer.parseInt(to.getText());
            }catch(NumberFormatException ex){
              toExit = true;
            }
            
            if(fromInt > toInt || fromInt<=0 || toInt<=0 || fromInt > 65535 || toInt > 65535){
              toExit = true;
            }
            
            String toBeAdded = from.getText() + " - " + to.getText();
            if(portList.getItemCount()==0){
                portList.add(toBeAdded);
                
                list.add( new PortRange(Integer.parseInt(from.getText()), Integer.parseInt(to.getText()) ) );

                toExit = true;
            }
            
            if(toExit){
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
                list.add( new PortRange(Integer.parseInt(from.getText()), Integer.parseInt(to.getText())) );

            }
            
        }
    });

    this.specificLbl = new Label( this.listComp, 0 );
    this.specificLbl.setText("Add specific Port");
    this.specificLbl.setLayoutData(gridData3);
    
    this.specificTxt = new Text( this.listComp, 0 );
    
    this.addSpecific = new Button(this.listComp, SWT.Activate);
    this.addSpecific.setText("Add Port");
    
    //add the specific port to the list
    addSpecific.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        public void widgetSelected(final org.eclipse.swt.events.SelectionEvent e) {
            int portInt=0;
            int counter = 0;
 
            boolean toExit = false;
            
            try{
                portInt = Integer.parseInt(specificTxt.getText());
                
            }catch(NumberFormatException ex){
              toExit = true;
            }
            
            if(!toExit){
              if(portInt<=0 || portInt>65535){
                toExit = true;
              }
            
              if(!toExit){
                for(int i=0; i<list.size(); i++){
                  if(list.get(i).getStart()==portInt && list.get(i).getFinish()==0){
                    counter++;
                  }
                }
              
                if(!toExit){
                  if(counter>0){
                    toExit = true;
                  }
                  list.add( new PortRange(portInt, 0 ) );
                  portList.add(""+portInt);
                }
              }
            }
        }
    });
    
    new Label( this.listComp, SWT.HORIZONTAL );
    new Label( this.listComp, SWT.HORIZONTAL );
    new Label( this.listComp, SWT.HORIZONTAL );
    
    this.resultsComp = new Composite( this.mainComp, SWT.NONE );
    this.resultsComp.setLayout( new GridLayout( 2, false ) );
    GridData resultsData = new GridData( 100, 100, false, false);
    resultsData.horizontalSpan = 2;
    resultsData.verticalSpan = 1;
    resultsData.widthHint =270;
    resultsData.heightHint = 200;
    this.results = new StyledText(this.resultsComp, SWT.H_SCROLL | SWT.V_SCROLL);
    this.results.setLayoutData(resultsData);

    this.scan = new Button(this.resultsComp, SWT.Activate);
    this.scan.setText("Scan");
    //this.scan.setLayoutData(GridData.HORIZONTAL_ALIGN_CENTER);
    this.stop = new Button(this.resultsComp, 1<<9);
    this.stop.setText("Stop");
    
    //stop the running job
    stop.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        public void widgetSelected(final org.eclipse.swt.events.SelectionEvent e) {
            //portsScannedLabel.setText("Scanning Stopped");
            
          if(job!=null){
            job.cancel();
            results.setText("Job cancelled");
            job=null;
          }
         
        }
    });     
    
    //calls the portScan() method to initiate the job for Port Scan
    scan.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        public void widgetSelected(final org.eclipse.swt.events. SelectionEvent e) {
            
            generatePortList();

            portScan(ip);
            
     //       portMap.clear();
//            portsScanned=0;
 //           portsOpen=0;
   //         portsClosed=0;

        }
    });
    
    return mainComp;
  }

  //initiates the job to Port Scan the selected host
protected void portScan(final String ipToScan) {

    InetAddress ia = null;
    try {
        ia = InetAddress.getByName(ipToScan);
    } catch (UnknownHostException e) {
        results.setText( "Cannot connect to " + ipToScan );
        return;
    }
    
    job = new PortScanJob(ia,portMap,results);
    job.schedule();
    
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
