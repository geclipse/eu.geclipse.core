package eu.geclipse.ui.providers;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import eu.geclipse.core.model.IGridComputing;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridJob;
import eu.geclipse.core.model.IGridService;
import eu.geclipse.core.model.IGridStorage;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.ui.internal.Activator;

public class GridModelLabelProvider
    extends LabelProvider
    implements ILabelProviderListener {
  
  private Image computingImage;
  
  private Image fileImage;

  private Image folderImage;
  
  private Image invalidElementImage;
  
  private Image serviceImage;
  
  private Image jobImage;

  private Image storageImage;
  
  private Image virtualFileImage;
  
  private Image virtualFolderImage;
  
  private Image voImage;

  private ILabelProvider workbenchLabelProvider = WorkbenchLabelProvider.getDecoratingWorkbenchLabelProvider();

  private ILabelDecorator labelDecorator =  PlatformUI.getWorkbench().getDecoratorManager().getLabelDecorator();
    
  public GridModelLabelProvider() {
    this.workbenchLabelProvider.addListener( this );
  }
  
  @Override
  public void dispose() {
    this.workbenchLabelProvider.removeListener( this );
    super.dispose();
  }
  
  @Override
  public Image getImage( final Object element ) {
    Image resultImage = null;
    if ( element instanceof IGridElement ) {
      IResource resource = ( ( IGridElement ) element ).getResource();
      if ( resource != null ) {
        resultImage = this.workbenchLabelProvider.getImage( resource );
      } else if ( element instanceof IGridContainer ) {
        resultImage = getFolderImage( ( ( IGridElement ) element ).isVirtual() );
      } else {
        resultImage = getFileImage( ( ( IGridElement ) element ).isVirtual() );
      }
    }
    if ( element instanceof IGridConnectionElement ) {
      IGridConnectionElement gridMount = ( IGridConnectionElement ) element;
      if ( !gridMount.isValid() ) {
        resultImage = getInvalidElementImage();
      } else if ( gridMount.isFolder() ) {
        resultImage = getFolderImage( true );
      } else {
        resultImage = getFileImage( true );
      }
    } else if ( element instanceof IVirtualOrganization ) {
      resultImage = getVoImage();
    } else if ( element instanceof IGridComputing ) {
      resultImage = getComputingImage();
    } else if ( element instanceof IGridStorage ) {
      resultImage = getStorageImage();
    } else if ( element instanceof IGridService ) {
      resultImage = getServiceImage();
//    } else if ( element instanceof IGridJob ) {
//      resultImage = getJobImage();
    }
    return resultImage;
  }
  
  @Override
  public String getText( final Object element ) {
    String resultText = null;
    if ( element instanceof IGridElement ) {
      resultText = ( ( IGridElement ) element ).getName();
      IResource resource = ( ( IGridElement ) element ).getResource();
      if ( resource != null ) {
        resultText = this.labelDecorator.decorateText( resultText, resource );
      }
    } else if ( element instanceof ProgressTreeNode ) {
      resultText = element.toString();
    }
    if ( element instanceof IGridConnectionElement ) {
      IGridConnectionElement connection
        = ( IGridConnectionElement ) element;
      if ( !connection.isValid() ) {
        resultText += "(Error: " + connection.getError() +")";
      }
    }
    return resultText;
  }
  
  protected Image getFolderImage( final boolean isVirtual ) {
    Image result;
    if ( isVirtual ) {
      if ( this.virtualFolderImage == null ) {
        this.virtualFolderImage
          = Activator.getDefault().getImageRegistry().get( "virtualfolder" ); //$NON-NLS-1$
      }
      result = this.virtualFolderImage;
    } else {
      if ( this.folderImage == null ) {
        this.folderImage
          = PlatformUI.getWorkbench().getSharedImages().getImage(
              ISharedImages.IMG_OBJ_FOLDER ); 
      }
      result = this.folderImage;
    }
    return result;
  }
  
  protected Image getFileImage( final boolean isVirtual ) {
    Image result = null;
    if ( isVirtual ) {
      if ( this.virtualFileImage == null ) {
        this.virtualFileImage
          = Activator.getDefault().getImageRegistry().get( "virtualfile" ); //$NON-NLS-1$
      }
      result = this.virtualFileImage;
    } else {
      if ( this.fileImage == null ) {
        this.fileImage
          = PlatformUI.getWorkbench().getSharedImages().getImage(
              ISharedImages.IMG_OBJ_FILE ); 
      }
      result = this.fileImage;
    }
    return result;
  }
  
  protected Image getVoImage() {
    if ( this.voImage == null ) {
      this.voImage
        = Activator.getDefault().getImageRegistry().get( "vo" ); //$NON-NLS-1$
    }
    return this.voImage;
  }
  
  protected Image getInvalidElementImage() {
    if ( this.invalidElementImage == null ) {
      this.invalidElementImage
        = Activator.getDefault().getImageRegistry().get( "invalidelement" ); //$NON-NLS-1$
    }
    return this.invalidElementImage;
  }
  
  private Image getServiceImage() {
    if ( this.serviceImage == null ) {
      this.serviceImage
        = Activator.getDefault().getImageRegistry().get( "service" ); //$NON-NLS-1$
    }
    return this.serviceImage;
  }

  private Image getJobImage() {
    if ( this.jobImage == null ) {
      this.jobImage
        = Activator.getDefault().getImageRegistry().get( "job" ); //$NON-NLS-1$
    }
    return this.jobImage;
  }

  private Image getStorageImage() {
    if ( this.storageImage == null ) {
      this.storageImage
        = Activator.getDefault().getImageRegistry().get( "storage" ); //$NON-NLS-1$
    }
    return this.storageImage;
  }

  private Image getComputingImage() {
    if ( this.computingImage == null ) {
      this.computingImage
        = Activator.getDefault().getImageRegistry().get( "computing" ); //$NON-NLS-1$
    }
    return this.computingImage;
  }

  public void labelProviderChanged( final LabelProviderChangedEvent event ) {
    // TODO find the grid element to the IRessource for only updating necessary nodes
    fireLabelProviderChanged( new LabelProviderChangedEvent(this) );
  }
}
