package eu.geclipse.ui.internal.wizards;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;

import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.core.security.ICertificateLoader;
import eu.geclipse.core.security.Security;
import eu.geclipse.core.security.ICertificateLoader.CertificateID;
import eu.geclipse.core.security.ICertificateManager.CertTrust;
import eu.geclipse.ui.dialogs.ProblemDialog;
import eu.geclipse.ui.internal.Activator;


public class CertificateImportWizard extends Wizard {

  private CertificateLoaderSelectionPage loaderPage;
  
  private CertificateChooserPage chooserPage;
  
  public CertificateImportWizard() {
    setNeedsProgressMonitor( true );
    URL imgUrl = Activator.getDefault().getBundle().getEntry( "icons/wizban/cacert_wiz.gif" ); //$NON-NLS-1$
    setDefaultPageImageDescriptor( ImageDescriptor.createFromURL( imgUrl ) );
  }
  
  @Override
  public void addPages() {
    this.loaderPage = new CertificateLoaderSelectionPage();
    addPage( this.loaderPage );
    this.chooserPage = new CertificateChooserPage( this.loaderPage );
    addPage( this.chooserPage );
  }
  
  @Override
  public boolean canFinish() {
    return ( getContainer().getCurrentPage() == this.chooserPage )
    && this.chooserPage.isPageComplete();
  }
  
  @Override
  public String getWindowTitle() {
    return "Certificate import";
  }
  
  @Override
  public boolean performFinish() {
    
    boolean result = true;
    WizardPage currentPage = ( WizardPage ) getContainer().getCurrentPage();
    currentPage.setErrorMessage( null );

    final ICertificateLoader loader = this.loaderPage.getSelectedLoader();
    final CertificateID[] certificates = this.chooserPage.getSelectedCertificates();

    try {

      getContainer().run( true, true, new IRunnableWithProgress() {
        public void run( final IProgressMonitor monitor )
            throws InvocationTargetException, InterruptedException {

          monitor.beginTask( "Importing certificates", certificates.length );
          List< X509Certificate > certList = new ArrayList< X509Certificate >();

          try {
            for ( CertificateID certID : certificates ) {
              
              if ( monitor.isCanceled() ) {
                throw new OperationCanceledException();
              }
              
              SubProgressMonitor subMonitor = new SubProgressMonitor( monitor, 1 );
              X509Certificate certificate = loader.fetchCertificate( certID, subMonitor );
              if ( certificate != null ) {
                certList.add( certificate );
              }
              
            }
          } catch ( ProblemException pExc ) {
            throw new InvocationTargetException( pExc );
          } finally {
            monitor.done();
          }

          if ( !certList.isEmpty() ) {
            X509Certificate[] certArray = certList.toArray( new X509Certificate[ certList.size() ] );
            try {
              Security.getCertificateManager().addCertificates( certArray, CertTrust.AlwaysTrusted );
            } catch( ProblemException pExc ) {
              throw new InvocationTargetException( pExc );
            }
          }

        }
      } );

    } catch ( InvocationTargetException itExc ) {
      Throwable cause = itExc.getCause() == null ? itExc : itExc.getCause();
      ProblemDialog.openProblem(
          getShell(),
          "Certifcate import failed",
          "A problem occured while the certificates were imported. At least parts of the certificates may not have been imported properly.",
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
