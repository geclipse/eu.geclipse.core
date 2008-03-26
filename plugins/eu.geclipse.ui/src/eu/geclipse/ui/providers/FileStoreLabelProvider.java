package eu.geclipse.ui.providers;

import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.ui.internal.Activator;

public class FileStoreLabelProvider
    extends LabelProvider
    implements ITableLabelProvider {
  
  public static final String COLUMN_TYPE_NAME = "NAME"; //$NON-NLS-1$
  
  public static final String COLUMN_TYPE_SIZE = "SIZE"; //$NON-NLS-1$
  
  public static final String COLUMN_TYPE_MOD_DATE = "MOD_DATE"; //$NON-NLS-1$
  
  protected static final String EMPTY_STRING = ""; //$NON-NLS-1$
  
  protected static final String NA_STRING = "N/A"; //$NON-NLS-1$
  
  /**
   * Prefixes for the magnitude of the file size.
   */
  private static final String[] prefixes = {
    "", //$NON-NLS-1$
    "k", //$NON-NLS-1$
    "M", //$NON-NLS-1$
    "G", //$NON-NLS-1$
    "T", //$NON-NLS-1$
    "P", //$NON-NLS-1$
    "E" //$NON-NLS-1$
  };
  
  /**
   * Unit of the file size, i.e. B for bytes. 
   */
  private static final String FILE_SIZE_UNIT = "B"; //$NON-NLS-1$
  
  /**
   * Format string for the file size format.
   */
  private static final String FILE_SIZE_FORMAT = "0.#"; //$NON-NLS-1$
  
  private static Hashtable< String, Image > images;
  
  /**
   * Date format used to format the modification time.
   */
  private DateFormat dateFormat;
  
  /**
   * Decimal format used to format file sizes.
   */
  private DecimalFormat sizeFormat;
  
  private Hashtable< Integer, String > columnMap;
  
  /**
   * Create a new <code>ConnectionViewLabelProvider</code>
   */
  public FileStoreLabelProvider() {
    this.dateFormat = new SimpleDateFormat();
    this.sizeFormat = new DecimalFormat( FILE_SIZE_FORMAT );
  }
  
  public void addColumn( final int index, final String type ) {
    if ( this.columnMap == null ) {
      this.columnMap = new Hashtable< Integer, String >();
    }
    this.columnMap.put( new Integer( index ), type );
  }

  public Image getColumnImage( final Object element,
                               final int columnIndex ) {

    Image result = null;
    
    if ( element instanceof IFileStore ) {
      result = getColumnImage( ( IFileStore ) element, columnIndex );
    } else if ( element instanceof IAdaptable ) {
      IFileStore adapter = ( IFileStore ) ( ( IAdaptable ) element ).getAdapter( IFileStore.class );
      if ( adapter != null ) {
        result = getColumnImage( adapter, columnIndex );
      }
    }
    
    return result;
    
  }

  public String getColumnText( final Object element,
                               final int columnIndex ) {

    String result = null;
    
    if ( element instanceof IFileStore ) {
      result = getColumnText( ( IFileStore ) element, columnIndex );
    } else if ( element instanceof IAdaptable ) {
      IFileStore adapter = ( IFileStore ) ( ( IAdaptable ) element ).getAdapter( IFileStore.class );
      if ( adapter != null ) {
        result = getColumnText( adapter, columnIndex );
      }
    }
    
    return result;
    
  }
  
  @Override
  public Image getImage( final Object element ) {
    return getColumnImage( element, 0 );
  }
  
  @Override
  public String getText( final Object element ) {
    return getColumnText( element, 0 );
  }
  
  protected Image getColumnImage( final IFileStore fileStore,
                                  final int columnIndex ) {
    
    Image result = null;
    
    if ( columnIndex == 0 ) {
      IFileInfo info = fileStore.fetchInfo();
      if ( info.isDirectory() ) {
        result = getFolderImage();
      } else {
        result = getFileImage();
      }
    }
    
    return result;
    
  }
  
  protected String getColumnText( final IFileStore fileStore,
                                  final int columnIndex ) {
    
    String result = EMPTY_STRING;
    
    String type = getColumnType( columnIndex );
    if ( COLUMN_TYPE_NAME.equals( type ) ) {
      result = getName( fileStore );
    } else if ( COLUMN_TYPE_SIZE.equals( type ) ) {
      result = getSize( fileStore );
    } else if ( COLUMN_TYPE_MOD_DATE.equals( type ) ) {
      result = getModificationDate( fileStore );
    }
    
    return result;
    
  }
  
  protected String getColumnType( final int columnIndex ) {
    
    String result = null;
    
    if ( this.columnMap != null ) {
      result = this.columnMap.get( new Integer( columnIndex ) );
    }
    
    return result;
    
  }
  
  protected String convertTimeToString( final long time ) {
    
    String result = NA_STRING;
    
    if ( time != EFS.NONE ) {
      Date date = new Date( time );
      result = this.dateFormat.format( date );
    }
    
    return result;
    
  }
  
  protected String convertSizeToString( final long size ) {
    
    String result = NA_STRING;
    
    if ( size == 0 ) {
      result = "0 " + FILE_SIZE_UNIT; //$NON-NLS-1$
    } else if ( size > 0 ) {
      double mag = Math.floor( Math.log( size ) / Math.log( 1024 ) );
      if ( mag >= prefixes.length ) mag = prefixes.length - 1;
      double ref = Math.pow( 1024., mag );
      double value = size / ref;
      /*
       * The DecimalFormat.format() method rounds the number up if necessary.
       * So we will end up with "1024 kB" if the file is a few Bytes smaller than
       * 1024 kB... What do other tools do?
       */
      result
      = this.sizeFormat.format( value )
      + " " + prefixes[ ( int ) mag ] + FILE_SIZE_UNIT; //$NON-NLS-1$
    }
    
    return result;
    
  }
  
  /**
   * Get the properly formatted string representing the time of
   * the last modification for the specified {@link IFileStore}.
   * 
   * @param fileStore The {@link IFileStore} for which to create a
   * modification string.
   * @return The properly formatted modification string.
   */
  protected String getModificationDate( final IFileStore fileStore ) {
    
    String result = NA_STRING;
    
    if ( fileStore != null ) {
      result = convertTimeToString( fileStore.fetchInfo().getLastModified() );
    }
    
    return result;
    
  }
  
  protected String getName( final IFileStore fileStore ) {
    return fileStore.getName();
  }
  
  /**
   * Get the properly formatted string representing the file size
   * for the specified {@link IFileStore}.
   * 
   * @param fileInfo The {@link IFileStore} for which to create a
   * file size string.
   * @return The properly formatted file size string.
   */
  protected String getSize( final IFileStore fileStore ) {
    
    String result = NA_STRING;
    
    if ( fileStore != null ) {
      if ( fileStore.fetchInfo().isDirectory() ) {
        result = EMPTY_STRING;
      } else {
        result = convertSizeToString( fileStore.fetchInfo().getLength() );
      }
    }
    
    return result;
    
  }
  
  protected static Image getFileImage() {
    return PlatformUI.getWorkbench().getSharedImages().getImage( ISharedImages.IMG_OBJ_FILE );
  }
  
  protected static Image getFolderImage() {
    return PlatformUI.getWorkbench().getSharedImages().getImage( ISharedImages.IMG_OBJ_FOLDER );
  }
  
  protected static Image getImage( final String path ) {
    
    Image result = null;
    
    if ( images == null ) {
      images = new Hashtable< String, Image >();
    } else {
      result = images.get( path );
    }
    
    if ( result == null ) {
      result = loadImage( path );
      images.put( path, result );
    }
    
    return result;
    
  }
  
  protected static Image loadImage( final String path ) {
    URL url = Activator.getDefault().getBundle().getEntry( path );
    ImageDescriptor descriptor = ImageDescriptor.createFromURL( url );
    return descriptor.createImage();
  }

}
