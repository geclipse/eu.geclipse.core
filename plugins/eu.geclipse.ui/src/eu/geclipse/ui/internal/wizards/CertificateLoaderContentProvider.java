package eu.geclipse.ui.internal.wizards;

import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import eu.geclipse.core.Extensions;

public class CertificateLoaderContentProvider implements ITreeContentProvider {

  public Object[] getChildren( final Object parentElement ) {
    
    Object[] result = new Object[ 0 ];
    
    if ( parentElement instanceof List< ? > ) {
      result = ( ( List< ? > ) parentElement ).toArray();
    } else if ( parentElement instanceof IConfigurationElement ) {
      result = getChildren( ( IConfigurationElement ) parentElement );
    }
    
    return result;
    
  }

  public Object getParent( final Object element ) {
    return null;
  }

  public boolean hasChildren( final Object element ) {
    
    boolean result = false;
    
    if ( element instanceof List< ? > ) {
      result = true;
    } else if ( element instanceof IConfigurationElement ) {
      String name = ( ( IConfigurationElement ) element ).getName();
      result
        = Extensions.CERT_LOADER_ELEMENT.equals( name )
        || Extensions.CERT_LOADER_AUTHORITY_ELEMENT.equals( name );
    }
    
    return result;
    
  }

  public Object[] getElements( final Object inputElement ) {
    return getChildren( inputElement );
  }

  public void dispose() {
    // TODO Auto-generated method stub
  }

  public void inputChanged( final Viewer viewer,
                            final Object oldInput,
                            final Object newInput ) {
    // TODO Auto-generated method stub
  }
  
  private Object[] getChildren( final IConfigurationElement element ) {
    
    Object[] result = new Object[ 0 ];
    
    if ( Extensions.CERT_LOADER_ELEMENT.equals( element.getName() ) ) {
      result = element.getChildren( Extensions.CERT_LOADER_AUTHORITY_ELEMENT );
    } else if ( Extensions.CERT_LOADER_AUTHORITY_ELEMENT.equals( element.getName() ) ) {
      result = element.getChildren( Extensions.CERT_LOADER_DISTRIBUTION_ELEMENT );
    }
    
    return result;
    
  }
  
}
