package eu.geclipse.ui.internal.wizards;

import java.net.URI;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;

import eu.geclipse.core.auth.ICaCertificateLoader;

public abstract class AbstractLocationChooserPage extends WizardPage {
  
  private CertificateLoaderSelectionPage loaderSelectionPage;
  
  public AbstractLocationChooserPage( final String pageName,
                                      final String title,
                                      final ImageDescriptor titleImage,
                                      final CertificateLoaderSelectionPage loaderSelectionPage ) {
    super( pageName, title, titleImage );
    this.loaderSelectionPage = loaderSelectionPage;
  }
  
  public ICaCertificateLoader getLoader() {
    return this.loaderSelectionPage.getSelectedLoader();
  }
  
  public abstract URI getSelectedLocation();

}
