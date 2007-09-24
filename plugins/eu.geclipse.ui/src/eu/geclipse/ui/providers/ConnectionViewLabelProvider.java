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

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.runtime.CoreException;

import eu.geclipse.core.model.IGridConnection;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.views.GridConnectionView;

/**
 * An {@link ElementManagerLabelProvider} that is used by the
 * {@link GridConnectionView}.
 * 
 */
public class ConnectionViewLabelProvider
    extends ElementManagerLabelProvider {
  
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
    "E", //$NON-NLS-1$
    "Z", //$NON-NLS-1$
    "Y" //$NON-NLS-1$
  };
  
  /**
   * Unit of the file size, i.e. B for bytes. 
   */
  private static final String FILE_SIZE_UNIT = "B"; //$NON-NLS-1$
  
  /**
   * Format string for the file size format.
   */
  private static final String FILE_SIZE_FORMAT = "0.###"; //$NON-NLS-1$
  
  /**
   * Date format used to format the modification time.
   */
  private DateFormat dateFormat;
  
  /**
   * Decimal format used to format file sizes.
   */
  private DecimalFormat sizeFormat;
  
  /**
   * Create a new <code>ConnectionViewLabelProvider</code>
   */
  public ConnectionViewLabelProvider() {
    this.dateFormat = new SimpleDateFormat();
    this.sizeFormat = new DecimalFormat( FILE_SIZE_FORMAT );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.ui.providers.ElementManagerLabelProvider#getColumnText(eu.geclipse.core.model.IGridElement, int)
   */
  @Override
  protected String getColumnText( final IGridElement element,
                                  final int columnIndex ) {
    String text = ""; //$NON-NLS-1$
    if ( ( element instanceof IGridConnectionElement )
        && !( element instanceof IGridConnection ) ) {
      IGridConnectionElement connection
        = ( IGridConnectionElement ) element;
      IFileInfo fileInfo = null;
      try {
        fileInfo = connection.getConnectionFileInfo();
      } catch ( CoreException cExc ) {
        Activator.logException( cExc );
      }
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
  
  /**
   * Get the properly formatted string representing the time of
   * the last modification for the specified {@link IFileInfo}.
   * 
   * @param fileInfo The {@link IFileInfo} for which to create a
   * modification string.
   * @return The properly formatted modification string.
   */
  private String getModificationString( final IFileInfo fileInfo ) {
    String result = "N/A"; //$NON-NLS-1$
    if ( fileInfo != null ) {
      long time = fileInfo.getLastModified();
      if ( time != EFS.NONE ) {
        Date date = new Date( time );
        result = this.dateFormat.format( date );
      }
    }
    return result;
  }
  
  /**
   * Get the properly formatted string representing the file size
   * for the specified {@link IFileInfo}.
   * 
   * @param fileInfo The {@link IFileInfo} for which to create a
   * file size string.
   * @return The properly formatted file size string.
   */
  private String getSizeString( final IFileInfo fileInfo ) {
    String result = "N/A"; //$NON-NLS-1$
    if ( fileInfo != null ) {
      long length = fileInfo.getLength();
      if ( length != EFS.NONE ) {
        double mag = Math.floor( Math.log( length ) / Math.log( 1024 ) );
        if ( mag >= prefixes.length ) mag = prefixes.length - 1;
        double ref = Math.pow( 1024., mag );
        double value = length / ref;
        result
          = this.sizeFormat.format( value )
          + " " + prefixes[ ( int ) mag ] + FILE_SIZE_UNIT; //$NON-NLS-1$
      }
    }
    return result;
  }
  
}
