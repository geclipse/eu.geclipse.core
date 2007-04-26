package eu.geclipse.ui.wizards.jobs;

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
import eu.geclipse.ui.internal.wizards.jobs.MultipleArgumentList;
import eu.geclipse.ui.internal.wizards.jobs.StringLabelProvider;


/**
 * Page fo parameters specific for execution host
 */
public class HostsNewJobWizardPage extends WizardPage {

  private MultipleArgumentList tab;
  private List<String> osTypes;
  private List<String> namesArch;
  private Combo osList;
  private Combo cpuList;
  
  
  protected HostsNewJobWizardPage( final String pageName, final List<String> OSList, final List<String> namesArch) {
    super( pageName );
    this.osTypes = OSList;
    this.namesArch = namesArch;
    this.setTitle( Messages.getString("HostsNewJobWizardPage.execution_host") ); //$NON-NLS-1$
    this.setDescription( Messages.getString("HostsNewJobWizardPage.execution_host_page_description") ); //$NON-NLS-1$
  }

  public void createControl( final Composite parent ) {
    Composite mainComp = new Composite( parent, SWT.NONE );
    
    GridLayout gLayout = new GridLayout( 3, false );
    gLayout.horizontalSpacing = 10;
    gLayout.verticalSpacing = 12;
    mainComp.setLayout( gLayout );
    
    GridData layout = new GridData();
    
    Label osLabel = new Label(mainComp, GridData.HORIZONTAL_ALIGN_BEGINNING
                              | GridData.VERTICAL_ALIGN_CENTER );
    osLabel.setText( Messages.getString("HostsNewJobWizardPage.os") ); //$NON-NLS-1$
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
    cpuArch.setText( Messages.getString("HostsNewJobWizardPage.cpu_architecture") ); //$NON-NLS-1$
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
    hostLabel.setText( Messages.getString("HostsNewJobWizardPage.candidate_hosts") ); //$NON-NLS-1$
    layout = new GridData();
//    layout.horizontalAlignment = GridData.FILL;
    layout.horizontalSpan = 3;
    hostLabel.setLayoutData( layout );
    
    HashMap<String, String> temp = new HashMap<String, String>();
    temp.put( Messages.getString("HostsNewJobWizardPage.candidate_host"), Messages.getString("HostsNewJobWizardPage.candidate_host") ); //$NON-NLS-1$ //$NON-NLS-2$
    this.tab = new MultipleArgumentList( new ArrayContentProvider(),
                                         new StringLabelProvider(),
                                         temp );
    this.tab.createControl( mainComp );
    
    setControl( mainComp );
  }

  /**
   * Method to access list of candidate hosts
   * @return list of addresses of cnadidate hosts to execute job
   */
  public List<String> getCandidateHosts() {
    return this/**
     * @return
     */
    .tab.getInput();
  }

  /**
   * Method to access name of the preffered OS
   * @return name of the OS on execution host
   */
  public String getOS() {
    return this.osList.getText();
  }
  
  /**
   * Metod to access CPU architecture of execution host
   * @return CPU architecture
   */
  public String getArch(){
    return this.cpuList.getText();
  }
}
