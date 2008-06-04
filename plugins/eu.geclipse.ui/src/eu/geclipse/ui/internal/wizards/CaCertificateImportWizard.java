/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributors:
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.internal.wizards;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;

import eu.geclipse.core.auth.CaCertManager;
import eu.geclipse.core.auth.ICaCertificate;
import eu.geclipse.core.auth.ICaCertificateLoader;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.dialogs.ProblemDialog;
import eu.geclipse.ui.internal.Activator;

/**
 * This wizard is used to import CA certificates either from a
 * local directory or file or from a remote location. Therefore
 * it queries the extension registry for all available extensions
 * of the <code>eu.geclipse.core.caCertificateLoader</code>
 * extension point and uses them to import the certificates.
 */
public class CaCertificateImportWizard extends Wizard {
  private static String CERT_IMPORT_LOCATIONS_ID = "cert_import_locations"; //$NON-NLS-1$

  /**
   * The import method used to import certificates.
   */
  public enum ImportMethod {

    /**
     * Local state parameter.
     */
    LOCAL,

    /**
     * Remote state parameter.
     */
    REMOTE

  }

  private ImportMethod importMethod;

  private CertificateLoaderSelectionPage selectionPage;

  private AbstractLocationChooserPage locationPage;

  private CertificateChooserPage certPage;

  /**
   * Create a new CA import wizard that either imports certificates
   * from a local file/directory or from a remote repository.
   *
   * @param importType The import type. Either <code>LOCAL</code>
   * or <code>REMOTE</code>.
   */
  public CaCertificateImportWizard( final ImportMethod importType ) {
    super();
    this.importMethod = importType;
    setNeedsProgressMonitor( true );
    URL imgUrl = Activator.getDefault().getBundle().getEntry( "icons/wizban/cacert_wiz.gif" ); //$NON-NLS-1$
    setDefaultPageImageDescriptor( ImageDescriptor.createFromURL( imgUrl ) );
  }

  @Override
  public void addPages() {

    this.selectionPage = new CertificateLoaderSelectionPage( this.importMethod );
    if ( this.selectionPage.initCertificateLoaders() > 1 ) {
      addPage( this.selectionPage );
    }

    if ( this.importMethod == ImportMethod.LOCAL ) {
      IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
      this.locationPage = new LocalLocationChooserPage( this.selectionPage, prefStore, CERT_IMPORT_LOCATIONS_ID );
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
    return Messages.getString("CaCertificateImportWizard.title"); //$NON-NLS-1$
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

      getContainer().run( true, true, new IRunnableWithProgress() {
        public void run( final IProgressMonitor monitor )
            throws InvocationTargetException, InterruptedException {

          monitor.beginTask( Messages.getString("CaCertificateImportWizard.fetching_task"), certificates.length ); //$NON-NLS-1$
          List< ICaCertificate > certList = new ArrayList< ICaCertificate >();

          try {
            for ( String certID : certificates ) {
              
              if ( monitor.isCanceled() ) {
                throw new OperationCanceledException();
              }
              
              SubProgressMonitor subMonitor = new SubProgressMonitor( monitor, 1 );
              ICaCertificate certificate = loader.getCertificate( location, certID, subMonitor );
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
            ICaCertificate[] certArray = certList.toArray( new ICaCertificate[ certList.size() ] );
            CaCertManager.getManager().addCertificates( certArray );
          }

        }
      } );

    } catch ( InvocationTargetException itExc ) {
      Throwable cause = itExc.getCause();
      ProblemDialog.openProblem(
          getShell(),
          Messages.getString("CaCertificateImportWizard.import_failed_title"), //$NON-NLS-1$
          Messages.getString("CaCertificateImportWizard.import_failed_description"), //$NON-NLS-1$
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
