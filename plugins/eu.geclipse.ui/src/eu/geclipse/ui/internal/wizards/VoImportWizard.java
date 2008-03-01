/*****************************************************************************
 * Copyright (c) 2006-2008 g-Eclipse Consortium 
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
 *    Ariel Garcia      - updated to new problem reporting
 *****************************************************************************/

package eu.geclipse.ui.internal.wizards;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URL;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;

import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.model.IVoLoader;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.dialogs.ProblemDialog;
import eu.geclipse.ui.internal.Activator;


/**
 * Wizard that is used by the VO preference page to import
 * virtual organizations from remote repositories.
 */
public class VoImportWizard extends Wizard {
  
  private VoLoaderSelectionPage selectionPage;
  
  private VoImportLocationChooserPage locationPage;
  
  private VoChooserPage chooserPage;
  
  /**
   * Standard constructor.
   */
  public VoImportWizard() {
    super();
    setNeedsProgressMonitor( true );
    URL imgUrl = Activator.getDefault().getBundle().getEntry( "icons/wizban/vo_wiz.gif" ); //$NON-NLS-1$
    setDefaultPageImageDescriptor( ImageDescriptor.createFromURL( imgUrl ) );
  }
  
  @Override
  public void addPages() {
    
    this.selectionPage = new VoLoaderSelectionPage();
    if ( this.selectionPage.initVoLoaderList() > 1 ) {
      addPage( this.selectionPage );
    }
    
    this.locationPage = new VoImportLocationChooserPage( this.selectionPage );
    addPage( this.locationPage );
    
    this.chooserPage = new VoChooserPage( this.locationPage );
    addPage( this.chooserPage );
    
  }
  
  @Override
  public boolean canFinish() {
    return getContainer().getCurrentPage() == this.chooserPage;
  }
  
  @Override
  public String getWindowTitle() {
    return Messages.getString("VoImportWizard.title"); //$NON-NLS-1$
  }

  @Override
  public boolean performFinish() {

    boolean result = true;
    WizardPage currentPage = ( WizardPage ) getContainer().getCurrentPage();
    currentPage.setErrorMessage( null );
    
    final IVoLoader loader = this.selectionPage.getSelectedLoader();
    final URI location = this.locationPage.getSelectedLocation();
    final String[] certificates = this.chooserPage.getSelectedVos();
    
    try {
      
      getContainer().run( false, false, new IRunnableWithProgress() {
        public void run( final IProgressMonitor monitor )
            throws InvocationTargetException, InterruptedException {
          
          monitor.beginTask( "Loading...", certificates.length );
          
          try {
            for ( String certID : certificates ) {
              SubProgressMonitor subMonitor = new SubProgressMonitor( monitor, 1 );
              IVirtualOrganization vo = loader.getVo( location, certID, subMonitor );
              /*if ( vo != null ) {
                certList.add( certificate );
              }*/
            }
          } catch ( ProblemException pExc ) {
            throw new InvocationTargetException( pExc );
          } finally {
            monitor.done();
          }
          /*
          if ( !certList.isEmpty() ) {
            ICaCertificate[] certArray = certList.toArray( new ICaCertificate[ certList.size() ] );
            CaCertManager.getManager().addCertificates( certArray );
          }
          */
        }
      } );
      
    } catch ( InvocationTargetException itExc ) {
      Throwable cause = itExc.getCause();
      ProblemDialog.openProblem( getShell(),
                                 "Import failed",
                                 "Import failed",
                                 cause );
      currentPage.setErrorMessage( cause.getLocalizedMessage() );
      result = false;
    } catch ( InterruptedException intExc ) {
      currentPage.setErrorMessage( intExc.getLocalizedMessage() );
      result = false;
    }
    
    return result;
    
  }

}
