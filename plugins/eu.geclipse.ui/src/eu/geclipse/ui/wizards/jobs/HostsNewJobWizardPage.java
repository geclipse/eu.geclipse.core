package eu.geclipse.ui.wizards.jobs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import eu.geclipse.ui.internal.wizards.jobs.ChildrenElements;
import eu.geclipse.ui.internal.wizards.jobs.MultipleArgumentList;
import eu.geclipse.ui.internal.wizards.jobs.StringLabelProvider;
import eu.geclipse.ui.wizards.jobs.FilesOutputNewJobWizardPage.IOFileContentProvider;
import eu.geclipse.ui.wizards.jobs.FilesOutputNewJobWizardPage.IOFileLabelProvider;
import eu.geclipse.ui.wizards.jobs.FilesOutputNewJobWizardPage.IOFilesTab;


public class HostsNewJobWizardPage extends WizardPage {

  private MultipleArgumentList tab;
  private List<String> osTypes;
  private List<String> namesArch;
  private Combo osList;
  private Combo cpuList;
  
  
  protected HostsNewJobWizardPage( String pageName, List<String> OSList, List<String> namesArch) {
    super( pageName );
    this.osTypes = OSList;
    this.namesArch = namesArch;
    this.setTitle( "Execution hosts" );
    this.setDescription( "Choose operating system type for execution host and candidate host to execute your job." );
  }

  public void createControl( Composite parent ) {
    Composite mainComp = new Composite( parent, SWT.NONE );
    
    GridLayout gLayout = new GridLayout( 3, false );
    gLayout.horizontalSpacing = 10;
    gLayout.verticalSpacing = 12;
    mainComp.setLayout( gLayout );
    
    GridData layout = new GridData();
    
    Label osLabel = new Label(mainComp, GridData.HORIZONTAL_ALIGN_BEGINNING
                              | GridData.VERTICAL_ALIGN_CENTER );
    osLabel.setText( "Operating system" );
    layout = new GridData();
    layout.horizontalAlignment = GridData.FILL;
    layout.horizontalSpan = 3;
    osLabel.setLayoutData( layout );
    
    this.osList = new Combo ( mainComp, SWT.SINGLE );
    for (String cpu: this.osTypes){
      this.osList.add( cpu );
    }
    layout = new GridData();
    layout.horizontalAlignment = GridData.FILL;
    layout.horizontalSpan = 3;
    layout.horizontalIndent = 20;
    this.osList.setLayoutData( layout );
    
    Label cpuArch = new Label(mainComp, GridData.HORIZONTAL_ALIGN_BEGINNING
                              | GridData.VERTICAL_ALIGN_CENTER );
    cpuArch.setText( "Architecture" );
    layout = new GridData();
    layout.horizontalAlignment = GridData.FILL;
    layout.horizontalSpan = 3;
    cpuArch.setLayoutData( layout );
    
    this.cpuList = new Combo ( mainComp, SWT.SINGLE );
    for (String cpu: this.namesArch){
      this.cpuList.add( cpu );
    }
    layout = new GridData();
    layout.horizontalAlignment = GridData.FILL;
    layout.horizontalSpan = 3;
    layout.horizontalIndent = 20;
    this.cpuList.setLayoutData( layout );
    
    Label hostLabel = new Label(mainComp, GridData.HORIZONTAL_ALIGN_BEGINNING
                              | GridData.VERTICAL_ALIGN_CENTER );
    hostLabel.setText( "Candidate hosts" );
    layout = new GridData();
//    layout.horizontalAlignment = GridData.FILL;
    layout.horizontalSpan = 3;
    hostLabel.setLayoutData( layout );
    
    HashMap<String, String> temp = new HashMap<String, String>();
    temp.put( "Candidate host", "Candidate host" );
    this.tab = new MultipleArgumentList( new ArrayContentProvider(),
                                         new StringLabelProvider(),
                                         temp );
    this.tab.createControl( mainComp );
    
    setControl( mainComp );
  }

  public List<String> getCandidateHosts() {
    return tab.getInput();
  }

  public String getOS() {
    return this.osList.getText();
  }
  
  public String getArch(){
    return this.cpuList.getText();
  }
}
