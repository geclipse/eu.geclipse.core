package eu.geclipse.ui.wizards.jobs;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import eu.geclipse.ui.dialogs.gexplorer.GridFileDialog;
import eu.geclipse.ui.widgets.NumberVerifier;
import eu.geclipse.ui.widgets.StoredCombo;


public class ResourcesNewJobWizardPage extends WizardPage{

  private List<String> OSNames;
  private List<String> cpuArchs;
  
  private Combo cpuList;
  private Text cpuIndivdSpeed;
  private Text cpuTotalCount;
  private Text diskSpaceIndividual;
  private Text diskSpaceTotal;
  private Text physicalMemory;
  private Text network;

  protected ResourcesNewJobWizardPage( String pageName, List<String> names, List<String> cpuArchitectures) {
    super( pageName );
    this.OSNames = names;
    this.cpuArchs = cpuArchitectures;
    this.setTitle( "Resources" );
    this.setDescription( "Set details of resources required by your job to execute." );
  }

  public void createControl( Composite parent ) {
    Composite mainComp = new Composite( parent, SWT.NONE );
           
    GridLayout gLayout = new GridLayout( 3, false );
    gLayout.horizontalSpacing = 10;
    gLayout.verticalSpacing = 12;
    mainComp.setLayout( gLayout );
    
    GridData layout = new GridData();
    Label cpuLabel = new Label( mainComp, GridData.HORIZONTAL_ALIGN_BEGINNING
                                           | GridData.VERTICAL_ALIGN_CENTER | SWT.BOLD );
    cpuLabel.setText( "CPU" ); 
    layout.horizontalAlignment = GridData.FILL;
    layout.horizontalSpan = 3;
    cpuLabel.setLayoutData( layout );
    
    Label cpuArch = new Label(mainComp, GridData.HORIZONTAL_ALIGN_BEGINNING
                              | GridData.VERTICAL_ALIGN_CENTER );
    cpuArch.setText( "Architecture" );
    layout = new GridData();
    layout.horizontalAlignment = GridData.FILL;
    layout.horizontalIndent = 20;
    cpuArch.setLayoutData( layout );
    
    this.cpuList = new Combo ( mainComp, SWT.SINGLE );
    for (String cpu: this.cpuArchs){
      this.cpuList.add( cpu );
    }
    layout = new GridData();
    layout.horizontalAlignment = GridData.FILL;
    layout.horizontalSpan = 2;
    this.cpuList.setLayoutData( layout );
    
    Label cpuSpeedLabel = new Label(mainComp, GridData.HORIZONTAL_ALIGN_BEGINNING
                                    | GridData.VERTICAL_ALIGN_CENTER );
    cpuSpeedLabel.setText( "Individual speed" );
    layout = new GridData();
    layout.horizontalAlignment = GridData.FILL;
    layout.horizontalIndent = 20;
    cpuSpeedLabel.setLayoutData( layout );
    
    this.cpuIndivdSpeed = new Text(mainComp, SWT.BORDER);
    layout = new GridData();
    layout.horizontalAlignment = GridData.FILL;
    layout.horizontalSpan = 2;
    this.cpuIndivdSpeed.setLayoutData( layout );
    this.cpuIndivdSpeed.addListener( SWT.Verify, new NumberVerifier() );
    
    Label cpuCountLabel = new Label(mainComp, GridData.HORIZONTAL_ALIGN_BEGINNING
                                    | GridData.VERTICAL_ALIGN_CENTER );
    cpuCountLabel.setText( "Total count" );
    layout = new GridData();
    layout.horizontalAlignment = GridData.FILL;
    layout.horizontalIndent = 20;
    cpuCountLabel.setLayoutData( layout );
        
    
    this.cpuTotalCount = new Text(mainComp, SWT.BORDER);
    layout = new GridData();
    layout.horizontalAlignment = GridData.FILL;
    layout.horizontalSpan = 2;
    this.cpuTotalCount.setLayoutData( layout );
    this.cpuTotalCount.addListener( SWT.Verify, new NumberVerifier() );
    
    Label separator1 = new Label(mainComp, SWT.SEPARATOR | SWT.HORIZONTAL);
    layout = new GridData();
    layout.horizontalAlignment = GridData.FILL;
    layout.horizontalSpan = 3;
    separator1.setLayoutData( layout );
    
    
    
    
    
    Label diskSpaceLabel = new Label(mainComp, GridData.HORIZONTAL_ALIGN_BEGINNING
                                     | GridData.VERTICAL_ALIGN_CENTER );
    diskSpaceLabel.setText( "Disk space" );
    layout = new GridData();
    layout.horizontalAlignment = GridData.FILL;
    layout.horizontalSpan = 3;
    diskSpaceLabel.setLayoutData( layout );
    
    Label diskSpaceIndividualLabel = new Label(mainComp, GridData.HORIZONTAL_ALIGN_BEGINNING
                                               | GridData.VERTICAL_ALIGN_CENTER );
    diskSpaceIndividualLabel.setText( "Individual" );
    layout = new GridData();
    layout.horizontalAlignment = GridData.FILL;
    layout.horizontalIndent = 20;
    diskSpaceIndividualLabel.setLayoutData( layout );
    
    this.diskSpaceIndividual = new Text(mainComp, SWT.BORDER);
    layout = new GridData();
    layout.horizontalAlignment = GridData.FILL;
    layout.horizontalSpan = 2;
    this.diskSpaceIndividual.setLayoutData( layout );
    this.diskSpaceIndividual.addListener( SWT.Verify, new NumberVerifier() );
    
    Label diskSpaceTotalLabel = new Label(mainComp, GridData.HORIZONTAL_ALIGN_BEGINNING
                                          | GridData.VERTICAL_ALIGN_CENTER );
    diskSpaceTotalLabel.setText( "Total" );
    layout = new GridData();
    layout.horizontalAlignment = GridData.FILL;
    layout.horizontalIndent = 20;
    diskSpaceTotalLabel.setLayoutData( layout );
    
    this.diskSpaceTotal = new Text(mainComp, SWT.BORDER);
    layout = new GridData();
    layout.horizontalAlignment = GridData.FILL;
    layout.horizontalSpan = 2;
    this.diskSpaceTotal.setLayoutData( layout );
    this.diskSpaceTotal.addListener( SWT.Verify, new NumberVerifier() );
    
    Label separator2 = new Label(mainComp, SWT.SEPARATOR | SWT.HORIZONTAL);
    layout = new GridData();
    layout.horizontalAlignment = GridData.FILL;
    layout.horizontalSpan = 3;
    separator2.setLayoutData( layout );
    
    
    Label otherLabel = new Label( mainComp, GridData.HORIZONTAL_ALIGN_BEGINNING
                                           | GridData.VERTICAL_ALIGN_CENTER  );
    otherLabel.setText( "Other" );
    layout = new GridData();
    layout.horizontalAlignment = GridData.FILL;
    layout.horizontalSpan = 3;
    otherLabel.setLayoutData( layout );
    
    Label physicalMemoryLabel = new Label(mainComp, GridData.HORIZONTAL_ALIGN_BEGINNING
                                          | GridData.VERTICAL_ALIGN_CENTER );
    physicalMemoryLabel.setText( "Toatal physical memory" );
    layout = new GridData();
    layout.horizontalAlignment = GridData.FILL;
    layout.horizontalIndent = 20;
    physicalMemoryLabel.setLayoutData( layout );
    
    this.physicalMemory = new Text(mainComp, SWT.BORDER);
    layout = new GridData();
    layout.horizontalAlignment = GridData.FILL;
    layout.horizontalSpan = 2;
    this.physicalMemory.setLayoutData( layout );
    this.physicalMemory.addListener( SWT.Verify, new NumberVerifier() );
    
    Label networkLabel = new Label(mainComp, GridData.HORIZONTAL_ALIGN_BEGINNING
                                   | GridData.VERTICAL_ALIGN_CENTER );
    networkLabel.setText( "Individual Network Bandwidth" );
    layout = new GridData();
    layout.horizontalAlignment = GridData.FILL;
    layout.horizontalIndent = 20;
    networkLabel.setLayoutData( layout );
    
    this.network = new Text(mainComp, SWT.BORDER);
    layout = new GridData();
    layout.horizontalAlignment = GridData.FILL;
    layout.horizontalSpan = 2;
    this.network.setLayoutData( layout );
    this.network.addListener( SWT.Verify, new NumberVerifier() );
    
    setControl( mainComp );
    
  }

  
  public String getCpuIndivdSpeed() {
    return this.cpuIndivdSpeed.getText();
  }

  
  public String getCpuList() {
    return this.cpuList.getText();
  }

  
  public String getCpuTotalCount() {
    return this.cpuTotalCount.getText();
  }

  
  public String getDiskSpaceIndividual() {
    return this.diskSpaceIndividual.getText();
  }

  
  public String getDiskSpaceTotal() {
    return this.diskSpaceTotal.getText();
  }

  
  public String getNetwork() {
    return this.network.getText();
  }

  
  public String getPhysicalMemory() {
    return this.physicalMemory.getText();
  }
  
  
}
