package eu.geclipse.ui.internal.wizards;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;

import eu.geclipse.core.GridException;
import eu.geclipse.core.auth.CaCertManager;
import eu.geclipse.core.auth.ICaCertificate;
import eu.geclipse.core.auth.ICaCertificateLoader;
import eu.geclipse.ui.dialogs.NewProblemDialog;

public class CaCertificateImportWizard extends Wizard {
  
  public enum ImportMethod {
    LOCAL,
    REMOTE
  }
  
  private ImportMethod importMethod;
  
  private CertificateLoaderSelectionPage selectionPage;
  
  private AbstractLocationChooserPage locationPage;
  
  private CertificateChooserPage certPage;
  
  public CaCertificateImportWizard( final ImportMethod importType ) {
    super();
    this.importMethod = importType;
    setNeedsProgressMonitor( true );
  }
  
  @Override
  public void addPages() {
    
    this.selectionPage = new CertificateLoaderSelectionPage( this.importMethod );
    addPage( this.selectionPage );
    
    if ( this.importMethod == ImportMethod.LOCAL ) {
      this.locationPage = new LocalLocationChooserPage( this.selectionPage );
    } else if ( this.importMethod == ImportMethod.REMOTE ) {
      this.locationPage = new RemoteLocationChooserPage( this.selectionPage );
    }
    addPage( this.locationPage );
    
    this.certPage = new CertificateChooserPage( this.locationPage );
    addPage( this.certPage );
    
  }
  
  @Override
  public boolean canFinish() {
    return getContainer().getCurrentPage() == this.certPage;
  }
  
  @Override
  public String getWindowTitle() {
    return "Import CA Certificates";
  }

  @Override
  public boolean performFinish() {
    
    boolean result = true;
    WizardPage currentPage = ( WizardPage ) getContainer().getCurrentPage();
    currentPage.setErrorMessage( null );
    
    final ICaCertificateLoader loader = this.selectionPage.getSelectedLoader();
    final URI location = this.locationPage.getSelectedLocation();
    final String[] certificates = this.certPage.getSelectedCertificates();
    
    try {
      
      getContainer().run( false, false, new IRunnableWithProgress() {
        public void run( final IProgressMonitor monitor )
            throws InvocationTargetException, InterruptedException {
          
          monitor.beginTask( "Fetching certificates...", certificates.length );
          List< ICaCertificate > certList = new ArrayList< ICaCertificate >();
          
          try {
            for ( String certID : certificates ) {
              SubProgressMonitor subMonitor = new SubProgressMonitor( monitor, 1 );
              ICaCertificate certificate = loader.getCertificate( location, certID, subMonitor );
              if ( certificate != null ) {
                certList.add( certificate );
              }
            }
          } catch ( GridException gExc ) {
            throw new InvocationTargetException( gExc );
          } finally {
            monitor.done();
          }
          
          if ( !certList.isEmpty() ) {
            ICaCertificate[] certArray = certList.toArray( new ICaCertificate[ certList.size() ] );
            CaCertManager.getManager().addCertificates( certArray );
          }
          
        }
      } );
      
    } catch ( InvocationTargetException itExc ) {
      Throwable cause = itExc.getCause();
      NewProblemDialog.openProblem(
          getShell(),
          "Import Failed",
          "Unable to load certificates",
          cause
      );
      currentPage.setErrorMessage( cause.getLocalizedMessage() );
      result = false;
    } catch ( InterruptedException intExc ) {
      currentPage.setErrorMessage( intExc.getLocalizedMessage() );
      result = false;
    }
    
    return result;
    
  }

}
