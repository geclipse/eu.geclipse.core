package eu.geclipse.ui.providers;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import eu.geclipse.core.model.IGridConnection;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridElement;


public class ConnectionViewLabelProvider
    extends ElementManagerLabelProvider {
  
  private static final String[] prefixes
    = { "", "k", "M", "G", "T", "P", "E", "Z", "Y" };
  
  private DateFormat dateFormat;
  
  private DecimalFormat sizeFormat;
  
  public ConnectionViewLabelProvider() {
    this.dateFormat = new SimpleDateFormat();
    this.sizeFormat = new DecimalFormat( "0.###" );
  }

  protected String getColumnText( final IGridElement element,
                                  final int columnIndex ) {
    String text = ""; //$NON-NLS-1$
    if ( ( element instanceof IGridConnectionElement )
        && !( element instanceof IGridConnection ) ) {
      IGridConnectionElement connection
      = ( IGridConnectionElement ) element;
      IFileInfo fileInfo
      = connection.getConnectionFileInfo();
      if ( fileInfo != null ) {
        switch ( columnIndex ) {
          case 2:
            text = getSizeString( fileInfo );
            break;
          case 3:
            text = getModificationString( fileInfo );
            break;
        }
      }
    }
    return text;
  }
  
  private String getModificationString( final IFileInfo fileInfo ) {
    String result = "N/A"; //$NON-NLS-1$
    long time = fileInfo.getLastModified();
    if ( time != EFS.NONE ) {
      Date date = new Date( time );
      result = this.dateFormat.format( date );
    }
    return result;
  }
  
  private String getSizeString( final IFileInfo fileInfo ) {
    String result = "N/A"; //$NON-NLS-1$
    long length = fileInfo.getLength();
    if ( length != EFS.NONE ) {
      double mag = Math.floor( Math.log( length ) / Math.log( 1024 ) );
      if ( mag >= prefixes.length ) mag = prefixes.length - 1;
      double ref = Math.pow( 1024., mag );
      double value = length / ref;
      result
        = this.sizeFormat.format( value )
        + " " + prefixes[ ( int ) mag ] + "B";
    }
    return result;
  }
  
}
