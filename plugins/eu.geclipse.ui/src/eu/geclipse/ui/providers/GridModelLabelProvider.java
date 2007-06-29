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

package eu.geclipse.ui.providers;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import eu.geclipse.core.model.IGridComputing;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridService;
import eu.geclipse.core.model.IGridStorage;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.ui.internal.Activator;

/**
 * Label provider implementation to be used by any Grid model view.
 */
public class GridModelLabelProvider
    extends LabelProvider
    implements ILabelProviderListener {

  private Image computingImage;
  private Image fileImage;
  private Image folderImage;
  private Image invalidElementImage;
  private Image serviceImage;
  private Image storageImage;
  private Image virtualFileImage;
  private Image virtualFolderImage;
  private Image voImage;
  private ILabelProvider workbenchLabelProvider = WorkbenchLabelProvider.getDecoratingWorkbenchLabelProvider();
  private ILabelDecorator labelDecorator = PlatformUI.getWorkbench()
    .getDecoratorManager()
    .getLabelDecorator();

  /**
   * Construct a new <code>GridModelLabelProvider</code>.
   */
  public GridModelLabelProvider() {
    this.workbenchLabelProvider.addListener( this );
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.BaseLabelProvider#dispose()
   */
  @Override
  public void dispose() {
    this.workbenchLabelProvider.removeListener( this );
    super.dispose();
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
   */
  @Override
  public Image getImage( final Object element ) {
    
    Image resultImage = null;
    
    if( element instanceof IGridConnectionElement ) {
      IGridConnectionElement gridMount = ( IGridConnectionElement )element;
      if( !gridMount.isValid() ) {
        resultImage = getInvalidElementImage();
      } else if( gridMount.isFolder() ) {
        resultImage = getFolderImage( true );
      } else {
        resultImage = getFileImage( true );
      }
    } else if( element instanceof IVirtualOrganization ) {
      resultImage = getVoImage();
    } else if( element instanceof IGridComputing ) {
      resultImage = getComputingImage();
    } else if( element instanceof IGridStorage ) {
      resultImage = getStorageImage();
    } else if( element instanceof IGridService ) {
      resultImage = getServiceImage();
    } else if ( element instanceof IGridElement ) {
      
      IGridElement gElement = ( IGridElement ) element;
      
      // check if there is adapter to provide image
      IWorkbenchAdapter adapter = ( IWorkbenchAdapter )( gElement.getAdapter( IWorkbenchAdapter.class ) );
      if( adapter != null ) {
        ImageDescriptor imageDescriptor = adapter.getImageDescriptor( element );
        if( imageDescriptor != null ) {
          resultImage = imageDescriptor.createImage();
          PlatformUI.getWorkbench().getDecoratorManager().getLabelDecorator().decorateImage( resultImage, element );
        }
      }

      // get image from resource
      if( resultImage == null ) {
        IResource resource = ( ( IGridElement )element ).getResource();
        if( ( resource != null ) && ( resource.getProject() != null ) ) {
          resultImage = this.workbenchLabelProvider.getImage( resource );
        } else if( element instanceof IGridContainer ) {
          resultImage = getFolderImage( ( ( IGridElement )element ).isVirtual() );
        } else {
          resultImage = getFileImage( ( ( IGridElement )element ).isVirtual() );
        }
      }
      
    }
    
    return resultImage;
    
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
   */
  @Override
  public String getText( final Object element ) {
    
    String resultText = null;
    
    if( element instanceof IGridElement ) {
      resultText = ( ( IGridElement )element ).getName();
      IResource resource = ( ( IGridElement )element ).getResource();
      if( ( resource != null ) && ( resource.getProject() != null ) ) {
        resultText = this.labelDecorator.decorateText( resultText, element );
      }
    } else if( element instanceof ProgressTreeNode ) {
      resultText = element.toString();
    }
    
    if( element instanceof IGridConnectionElement ) {
      IGridConnectionElement connection = ( IGridConnectionElement )element;
      if( !connection.isValid() ) {
        resultText += "(Error: " + connection.getError() + ")"; //$NON-NLS-1$ //$NON-NLS-2$
      }
    }
    
    return resultText;
    
  }

  /**
   * Get an image that represents a folder.
   * 
   * @param isVirtual Determine if the provided image should represent
   * a virtual folder.
   * @return An image representing a folder.
   */
  protected Image getFolderImage( final boolean isVirtual ) {
    Image result;
    if( isVirtual ) {
      if( this.virtualFolderImage == null ) {
        this.virtualFolderImage = Activator.getDefault()
          .getImageRegistry()
          .get( "virtualfolder" ); //$NON-NLS-1$
      }
      result = this.virtualFolderImage;
    } else {
      if( this.folderImage == null ) {
        this.folderImage = PlatformUI.getWorkbench()
          .getSharedImages()
          .getImage( ISharedImages.IMG_OBJ_FOLDER );
      }
      result = this.folderImage;
    }
    return result;
  }

  /**
   * Get an image that represents a file.
   * 
   * @param isVirtual Determine if the provided image should represent
   * a virtual file.
   * @return An image representing a file.
   */
  protected Image getFileImage( final boolean isVirtual ) {
    Image result = null;
    if( isVirtual ) {
      if( this.virtualFileImage == null ) {
        this.virtualFileImage = Activator.getDefault()
          .getImageRegistry()
          .get( "virtualfile" ); //$NON-NLS-1$
      }
      result = this.virtualFileImage;
    } else {
      if( this.fileImage == null ) {
        this.fileImage = PlatformUI.getWorkbench()
          .getSharedImages()
          .getImage( ISharedImages.IMG_OBJ_FILE );
      }
      result = this.fileImage;
    }
    return result;
  }

  /**
   * Get an image that represents a virtual organisation.
   * 
   * @return The image for a VO.
   */
  protected Image getVoImage() {
    if( this.voImage == null ) {
      this.voImage = Activator.getDefault().getImageRegistry().get( "vo" ); //$NON-NLS-1$
    }
    return this.voImage;
  }

  /**
   * Get an image that represents an invalid Grid element.
   * 
   * @return The image for an invalid {@link IGridElement}.
   */
  protected Image getInvalidElementImage() {
    if( this.invalidElementImage == null ) {
      this.invalidElementImage = Activator.getDefault()
        .getImageRegistry()
        .get( "invalidelement" ); //$NON-NLS-1$
    }
    return this.invalidElementImage;
  }

  /**
   * Get an image that represents an {@link IGridService}.
   * 
   * @return The image for a Grid service.
   */
  private Image getServiceImage() {
    if( this.serviceImage == null ) {
      this.serviceImage = Activator.getDefault()
        .getImageRegistry()
        .get( "service" ); //$NON-NLS-1$
    }
    return this.serviceImage;
  }

  /**
   * Get an image that represents an {@link IGridStorage}.
   * 
   * @return The image for a Grid storage.
   */
  private Image getStorageImage() {
    if( this.storageImage == null ) {
      this.storageImage = Activator.getDefault()
        .getImageRegistry()
        .get( "storage" ); //$NON-NLS-1$
    }
    return this.storageImage;
  }

  /**
   * Get an image that represents an {@link IGridComputing}.
   * 
   * @return The image for a Grid computing.
   */
  private Image getComputingImage() {
    if( this.computingImage == null ) {
      this.computingImage = Activator.getDefault()
        .getImageRegistry()
        .get( "computing" ); //$NON-NLS-1$
    }
    return this.computingImage;
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.ILabelProviderListener#labelProviderChanged(org.eclipse.jface.viewers.LabelProviderChangedEvent)
   */
  public void labelProviderChanged( final LabelProviderChangedEvent event ) {
    // TODO find the grid element to the IRessource for only updating necessary
    // nodes
    fireLabelProviderChanged( new LabelProviderChangedEvent( this ) );
  }
  
}
