package eu.geclipse.ui.providers;

import org.eclipse.compare.ResourceNode;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import eu.geclipse.core.model.IGridComputing;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridJob;
import eu.geclipse.core.model.IGridMount;
import eu.geclipse.core.model.IGridService;
import eu.geclipse.core.model.IGridStorage;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.ui.internal.Activator;

public class GridModelLabelProvider extends LabelProvider {
  
  private Image closedProjectImage;
  
  private Image computingImage;
  
  private Image fileImage;

  private Image folderImage;
  
  private Image invalidElementImage;
  
  private Image jobImage;
  
  private Image openProjectImage;
  
  private Image serviceImage;
  
  private Image storageImage;
  
  private Image virtualFileImage;
  
  private Image virtualFolderImage;
  
  private Image voImage;
  
  @Override
  public Image getImage( final Object element ) {
    Image resultImage = null;
    if ( element instanceof IGridElement ) {
      IResource resource = ( ( IGridElement ) element ).getResource();
      if ( resource != null ) {
        resultImage = new ResourceNode(resource).getImage();
      } else if ( element instanceof IGridContainer ) {
        resultImage = getFolderImage( ( ( IGridElement ) element ).isVirtual() );
      } else {
        resultImage = getFileImage( ( ( IGridElement ) element ).isVirtual() );
      }
    }
/*    if ( element instanceof IGridProject ) {
      IGridProject project = ( IGridProject ) element;
      if ( project.isGridProject() ) {
        resultImage = getGridProjectImage( project.isOpen() );
      } else {
        resultImage = getProjectImage( project.isOpen() );
      }
    } else */
    if ( element instanceof IGridMount ) {
      IGridMount gridMount = ( IGridMount ) element;
      if ( !gridMount.isValid() ) {
        resultImage = getInvalidElementImage();
      } else if ( gridMount.isFolder() ) {
        resultImage = getFolderImage( true );
      } else {
        resultImage = getFileImage( true );
      }
      // XXX replace by icon from resourcenode (= set icon in plugin.xml)
    } else if ( element instanceof IGridJob ) {
      resultImage = getJobImage();
    } else if ( element instanceof IVirtualOrganization ) {
      resultImage = getVoImage();
    } else if ( element instanceof IGridComputing ) {
      resultImage = getComputingImage();
    } else if ( element instanceof IGridStorage ) {
      resultImage = getStorageImage();
    } else if ( element instanceof IGridService ) {
      resultImage = getServiceImage();
    }
    return resultImage;
  }
  
  @Override
  public String getText( final Object element ) {
    String resultText = null;
    if ( element instanceof IGridElement ) {
      resultText = ( ( IGridElement ) element ).getName();
    } else if ( element instanceof ProgressTreeNode ) {
      resultText = element.toString();
    }
    if ( element instanceof IGridMount ) {
      IGridMount gridMount = ( IGridMount ) element;
      if ( !gridMount.isValid() ) {
        resultText += "(Error: " + gridMount.getError() +")";
      }
    }
    return resultText;
  }
  
  protected Image getProjectImage( final boolean isOpen ) {
    Image image;
    if ( isOpen ) {
      if ( this.openProjectImage == null ) {
        this.openProjectImage
          = PlatformUI.getWorkbench().getSharedImages().getImage(
              IDE.SharedImages.IMG_OBJ_PROJECT ); 
      }
      image = this.openProjectImage;
    } else {
      if ( this.openProjectImage == null ) {
        this.closedProjectImage
          = PlatformUI.getWorkbench().getSharedImages().getImage(
              IDE.SharedImages.IMG_OBJ_PROJECT_CLOSED ); 
      }
      image = this.closedProjectImage;
    }
    return image;
  }
  
  protected Image getGridProjectImage( final boolean isOpen ) {
    // TODO mathias
    return getProjectImage( isOpen );
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
  
  protected Image getJobImage() {
    if ( this.jobImage == null ) {
      this.jobImage
        = Activator.getDefault().getImageRegistry().get( "job" ); //$NON-NLS-1$
    }
    return this.jobImage;
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
  
}
