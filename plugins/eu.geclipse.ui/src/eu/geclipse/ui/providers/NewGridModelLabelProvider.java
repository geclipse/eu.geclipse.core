package eu.geclipse.ui.providers;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import eu.geclipse.core.model.IGridComputing;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridJob;
import eu.geclipse.core.model.IGridJobDescription;
import eu.geclipse.core.model.IGridService;
import eu.geclipse.core.model.IGridStorage;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.model.IWrappedElement;
import eu.geclipse.core.model.impl.ContainerMarker;
import eu.geclipse.core.model.impl.ContainerMarker.MarkerType;
import eu.geclipse.info.model.GridGlueService;


public class NewGridModelLabelProvider
    extends FileStoreLabelProvider {
  
  private static final String IMG_COMPUTING = "icons/obj16/computing_obj.gif"; //$NON-NLS-1$
  
  private static final String IMG_ERROR_MARKER = "icons/obj16/ihigh_obj.gif"; //$NON-NLS-1$

  private static final String IMG_INFO_MARKER = "icons/obj16/info_obj.gif"; //$NON-NLS-1$
  
  private static final String IMG_JOB = "icons/obj16/job_file_obj.gif"; //$NON-NLS-1$
  
  private static final String IMG_JOB_DESCRIPTION = "icons/obj16/jsdl_file_obj.gif"; //$NON-NLS-1$
  
  private static final String IMG_SERVICE = "icons/obj16/service_obj.gif"; //$NON-NLS-1$
  
  private static final String IMG_SERVICE_UNSUPPORTED = "icons/obj16/service_unsupported_obj.gif"; //$NON-NLS-1$
  
  private static final String IMG_STORAGE = "icons/obj16/storage_obj.gif"; //$NON-NLS-1$
  
  private static final String IMG_VIRTUAL_FILE = "icons/obj16/virtual_file_obj.gif"; //$NON-NLS-1$
  
  private static final String IMG_VIRTUAL_FOLDER = "icons/obj16/virtual_folder_obj.gif"; //$NON-NLS-1$
  
  private static final String IMG_VO = "icons/obj16/vo_obj.gif"; //$NON-NLS-1$
  
  private static ILabelProvider workbenchLabelProvider
    = WorkbenchLabelProvider.getDecoratingWorkbenchLabelProvider();

  @Override
  public Image getColumnImage( final Object element,
                               final int columnIndex ) {
    
    Image result = null;
    
    if ( element instanceof IGridElement ) {
      result = getColumnImage( ( IGridElement ) element, columnIndex );
    } else if ( element instanceof IAdaptable ) {
      IGridElement adapter = ( IGridElement ) ( ( IAdaptable ) element ).getAdapter( IGridElement.class );
      if ( adapter != null ) {
        getColumnImage( adapter, columnIndex );
      }
    }

    if ( result == null ) {
      result = super.getColumnImage( element, columnIndex );
    }
    
    return result;
    
  }
  
  @Override
  public String getColumnText( final Object element,
                               final int columnIndex ) {
    
    String result = null;
    
    if ( element instanceof IGridElement ) {
      result = getColumnText( ( IGridElement ) element, columnIndex );
    } else if ( element instanceof IAdaptable ) {
      IGridElement adapter = ( IGridElement ) ( ( IAdaptable ) element ).getAdapter( IGridElement.class );
      if ( adapter != null ) {
        getColumnText( adapter, columnIndex );
      }
    }
    
    if ( result == null ) {
      result = super.getColumnText( element, columnIndex );
    }
    
    return result;
    
  }
  
  protected Image getColumnImage( final IGridElement element,
                                  final int columnIndex ) {
    
    Image result = null;
    
    if ( columnIndex == 0 ) {
      if ( element.isVirtual() ) {
        result = getVirtualElementImage( element );
      } else {
        result = getRealElementImage( element );
      }
    }
    
    return result;
    
  }
  
  protected String getColumnText( final IGridElement element,
                                  final int columnIndex ) {
    
    String result = EMPTY_STRING;
    
    String type = getColumnType( columnIndex );
    if ( COLUMN_TYPE_NAME.equals( type ) ) {
      result = getName( element );
    } else if ( COLUMN_TYPE_SIZE.equals( type ) ) {
      result = getSize( element );
    } else if ( COLUMN_TYPE_MOD_DATE.equals( type ) ) {
      result = getModificationDate( element );
    }
    
    return result;
    
  }
  
  private String getName( final IGridElement element ) {
    return element.getName();
  }
  
  private String getSize( final IGridElement element ) {
    
    String result = NA_STRING;
    IFileStore fileStore = null;
    
    if ( element instanceof IGridConnectionElement ) {
      try {
        fileStore = ( ( IGridConnectionElement ) element ).getConnectionFileStore();
      } catch (CoreException e) {
        // Silently ignored
      }
    } else {
      fileStore = element.getFileStore();
    }
    
    
    if ( fileStore != null ) {
      result = getSize( fileStore );
    }
    
    return result;
    
  }
  
  private String getModificationDate( final IGridElement element ) {
    
    String result = NA_STRING;
    IFileStore fileStore = null;
    
    if ( element instanceof IGridConnectionElement ) {
      try {
        fileStore = ( ( IGridConnectionElement ) element ).getConnectionFileStore();
      } catch (CoreException e) {
        // Silently ignored
      }
    } else {
      fileStore = element.getFileStore();
    }
    
    
    if ( fileStore != null ) {
      result = getModificationDate( fileStore );
    }
    
    return result;
    
  }
  
  private static Image getRealElementImage( final IGridElement element ) {
    
    Image result = null;
    
    if ( element instanceof IGridJob ) {
      result = getImage( IMG_JOB );
    } else if ( element instanceof IGridJobDescription ) {
      result = getImage( IMG_JOB_DESCRIPTION );
    } else {
      IResource resource = element.getResource();
      result = workbenchLabelProvider.getImage( resource );
    }
    
    return result;
    
  }
  
  private static Image getVirtualElementImage( final IGridElement element ) {
    
    Image result = null;
    
    if( element instanceof IVirtualOrganization ) {
      result = getImage( IMG_VO );
    }
    
    else if( element instanceof IGridComputing ) {
      result = getImage( IMG_COMPUTING );
    }
    
    else if( element instanceof IGridStorage ) {
      result = getImage( IMG_STORAGE );
    }
    
    else if( element instanceof IGridService ) {
      if ( element instanceof IWrappedElement ) {
        IGridElement wrappedElement = ((IWrappedElement) element ).getWrappedElement();
        if (wrappedElement instanceof GridGlueService)
        {
          boolean isSupported = ( ( GridGlueService ) wrappedElement ).getGlueService().isSupported();
          if ( isSupported ) {
            result = getImage( IMG_SERVICE );
          } else {
            result = getImage( IMG_SERVICE_UNSUPPORTED );
          }
        }
        else {
          result = getImage( IMG_SERVICE );
        }
      } else {
        result = getImage( IMG_SERVICE );
      }
    }
    
    else if ( element instanceof ContainerMarker ) {
      ContainerMarker.MarkerType type = ( ( ContainerMarker ) element ).getType();
      if ( type == MarkerType.INFO ) {
        result = getImage( IMG_INFO_MARKER );
      } else if ( type == MarkerType.ERROR ) {
        result = getImage( IMG_ERROR_MARKER );
      }
    }
    
    else if ( element instanceof IGridContainer ) {
      result = getImage( IMG_VIRTUAL_FOLDER );
    }
    
    else {
      result = getImage( IMG_VIRTUAL_FILE );
    }
    
    return result;
    
  }
  
}
