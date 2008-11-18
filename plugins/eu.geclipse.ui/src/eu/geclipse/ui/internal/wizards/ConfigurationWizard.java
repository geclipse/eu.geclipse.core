package eu.geclipse.ui.internal.wizards;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.internal.net.Activator;

import eu.geclipse.core.config.ConfiguratorFactory;
import eu.geclipse.core.config.IConfigurator;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.dialogs.ProblemDialog;

public class ConfigurationWizard extends Wizard {

  private ConfiguratorSelectionPage selectionPage;
  
  @Override
  public void addPages() {
    this.selectionPage = new ConfiguratorSelectionPage();
    addPage( this.selectionPage );
  }
  
  @Override
  public boolean canFinish() {
    return super.canFinish();
  }
  
  @Override
  public String getWindowTitle() {
    return "g-Eclipse Easy Configuration";
  }
  
  @Override
  public boolean performFinish() {
    
    boolean result = false;
    
    final IConfigurationElement[] selection = this.selectionPage.getSelection();
    final Shell shell = getShell();

    Job configurationJob = new Job( "g-Eclipse configuration" ) {

      @Override
      protected IStatus run( final IProgressMonitor monitor ) {

        MultiStatus status = new MultiStatus( Activator.PLUGIN_ID,
                                              0,
                                              "Problems while configuring g-Eclipse",
                                              null );
        SubMonitor sMonitor = SubMonitor.convert( monitor, "Configuring g-Eclipse", selection.length );

        try {

          for ( IConfigurationElement element : selection ) {
            IConfigurator configurator = ConfiguratorFactory.createConfigurator( element );
            sMonitor.setTaskName( "Configuring " + element.getAttribute( "name" ) );
            IStatus stat = configurator.configure( sMonitor.newChild( 1 ) );
            if ( ! stat.isOK() ) {
              status.merge( stat );
            }
            if ( sMonitor.isCanceled() ) {
              throw new OperationCanceledException();
            }
          }

        } catch( ProblemException pExc ) {
          ProblemDialog.openProblem( shell,
                                     "g-Eclipse configuration problem",
                                     "The configuration process could not be finished. It is possible that some parts are configured properly whereas others are not configured at all.",
                                     pExc );
        }

        return
          ( status.getChildren() == null ) || ( status.getChildren().length == 0 )
          ? Status.OK_STATUS
          : status;

      } 
    };

    configurationJob.setUser( true );
    configurationJob.schedule();

    return true;
    
  }
  
}
