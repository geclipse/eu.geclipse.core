package eu.geclipse.ui.decorators;

import java.net.URL;
import java.util.Hashtable;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.ui.IDecoratorManager;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;

import eu.geclipse.core.ExtensionManager;
import eu.geclipse.core.Extensions;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.ui.internal.Activator;

public class GridProjectFolderDecorator
    extends LabelProvider
    implements ILightweightLabelDecorator {
  
  private static final String ID
    = "eu.geclipse.ui.gridProjectFolderDecorator"; //$NON-NLS-1$

  private Hashtable< String, ImageDescriptor > images
    = new Hashtable< String, ImageDescriptor >();
  
  public static GridProjectFolderDecorator getDecorator() {
    
    GridProjectFolderDecorator result = null;
    IDecoratorManager decoratorManager = PlatformUI.getWorkbench().getDecoratorManager();
    
    if ( decoratorManager.getEnabled( ID ) ) {
      result = 
        ( GridProjectFolderDecorator ) decoratorManager.getBaseLabelProvider( ID );
    }
    
    return result;
    
  }
  
  public GridProjectFolderDecorator() {
    
    ImageDescriptor standardImage = null;
    ImageDescriptor image = null;
    
    ExtensionManager extm = new ExtensionManager();
    List< IConfigurationElement > configurationElements
      = extm.getConfigurationElements( Extensions.PROJECT_FOLDER_POINT, Extensions.PROJECT_FOLDER_ELEMENT );
    
    for ( IConfigurationElement element : configurationElements ) {
      
      String id = element.getAttribute( Extensions.PROJECT_FOLDER_ID_ATTRIBUTE );
      String icon = element.getAttribute( Extensions.PROJECT_FOLDER_ICON_ATTRIBUTE );
      
      if ( icon != null ) {
        IExtension extension = element.getDeclaringExtension();
        IContributor contributor = extension.getContributor();
        String name = contributor.getName();
        Bundle bundle = Platform.getBundle( name );
        URL url = FileLocator.find( bundle, new Path( icon ), null );
        image = ImageDescriptor.createFromURL( url );
      }
      
      else {
        if ( standardImage == null ) {
          URL url = Activator.getDefault().getBundle().getEntry( "icons/ovr16/project_ovr.gif" );
          standardImage = ImageDescriptor.createFromURL( url );
        }
        image = standardImage;
      }
      
      this.images.put( id, image );
      
    }
    
  }

  public void decorate( final Object element, final IDecoration decoration ) {
    if ( element instanceof IGridContainer ) {
      decorate( ( IGridContainer ) element, decoration );
    }
  }
  
  public void refresh( final IGridElement toUpdate ) {
    LabelProviderChangedEvent event
      = new LabelProviderChangedEvent( getDecorator(), toUpdate );
    fireLabelProviderChanged( event );
  }
  
  public void refresh( final IGridElement[] toUpdate ) {
    LabelProviderChangedEvent event
      = new LabelProviderChangedEvent( getDecorator(), toUpdate );
    fireLabelProviderChanged( event );
  }
  
  private void decorate( final IGridContainer container, final IDecoration decoration ) {
    IGridContainer parent = container.getParent();
    if ( parent instanceof IGridProject ) {
      String id = ( ( IGridProject ) parent ).getProjectFolderID( container );
      if ( id != null ) {
        ImageDescriptor image = this.images.get( id );
        if ( image != null ) {
          decoration.addOverlay( image, IDecoration.TOP_RIGHT );
        }
      }
    }
  }

}
