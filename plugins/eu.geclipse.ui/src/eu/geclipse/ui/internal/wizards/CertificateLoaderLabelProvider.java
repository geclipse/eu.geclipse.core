package eu.geclipse.ui.internal.wizards;

import java.net.URL;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import eu.geclipse.core.Extensions;
import eu.geclipse.ui.internal.Activator;

public class CertificateLoaderLabelProvider extends LabelProvider {
  
  private static final String IMG_REPO = "icons/obj16/service_new_obj.gif"; //$NON-NLS-1$
  
  private static final String IMG_URL = "icons/obj16/acl_anyone_tbl.gif"; //$NON-NLS-1$
  
  private static Image REPO_IMAGE = null;
  
  private static Image URL_IMAGE = null;
  
  @Override
  public Image getImage( final Object element ) {
    
    Image result = null;
    
    if ( element instanceof IConfigurationElement ) {
      IConfigurationElement cElement = ( IConfigurationElement ) element;
      if ( Extensions.CERT_LOADER_ELEMENT.equals( cElement.getName() ) ) {
        result = getRepoImage();
      } else if ( Extensions.CERT_LOADER_AUTHORITY_ELEMENT.equals( cElement.getName() ) ) {
        result = getRepoImage();
      } else if ( Extensions.CERT_LOADER_DISTRIBUTION_ELEMENT.equals( cElement.getName() ) ) {
        result = getUrlImage();
      }
    }
    
    return result;
    
  }
  
  @Override
  public String getText( final Object element ) {
    
    String result = ""; //$NON-NLS-1$
    
    if ( element instanceof IConfigurationElement ) {
      IConfigurationElement cElement = ( IConfigurationElement ) element;
      if ( Extensions.CERT_LOADER_ELEMENT.equals( cElement.getName() ) ) {
        result = cElement.getAttribute( Extensions.CERT_LOADER_NAME_ATTRIBUTE );
      } else if ( Extensions.CERT_LOADER_AUTHORITY_ELEMENT.equals( cElement.getName() ) ) {
        result = cElement.getAttribute( Extensions.CERT_LOADER_AUTHORITY_NAME_ATTRIBUTE );
      } else if ( Extensions.CERT_LOADER_DISTRIBUTION_ELEMENT.equals( cElement.getName() ) ) {
        result = cElement.getAttribute( Extensions.CERT_LOADER_DISTRIBUTION_NAME_ATTRIBUTE );
      }
    }
    
    return result;
    
  }
  
  private static Image getRepoImage() {
    if ( REPO_IMAGE == null ) {
      REPO_IMAGE = loadImage( IMG_REPO );
    }
    return REPO_IMAGE;
  }
  
  private static Image getUrlImage() {
    if ( URL_IMAGE == null ) {
      URL_IMAGE = loadImage( IMG_URL );
    }
    return URL_IMAGE;
  }
  
  private static Image loadImage( final String path ) {
    URL url = Activator.getDefault().getBundle().getEntry( path );
    ImageDescriptor descriptor = ImageDescriptor.createFromURL( url );
    return descriptor.createImage();
  }

}
