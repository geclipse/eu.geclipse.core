/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     Mariusz Wojtysiak - initial API and implementation
 *     
 *****************************************************************************/
package eu.geclipse.ui.views.filters;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.ui.IMemento;
import eu.geclipse.ui.dialogs.ConfigureFiltersDialog;

/**
 * Class implementing base functionality for viewer filters Inherited class
 * should implement interface {@link Cloneable} in order to be shown in
 * {@link ConfigureFiltersDialog}
 */
public abstract class AbstractGridViewerFilter extends ViewerFilter
  implements IGridFilter, Cloneable
{

  protected boolean readBoolean( final IMemento memento, final String key ) {
    boolean value = false;
    Integer integer = memento.getInteger( key );
    if( integer != null ) {
      value = integer.intValue() != 0;
    }
    return value;
  }

  protected void saveDate( final IMemento memento,
                           final String key,
                           final Date date )
  {
    String dateString = getDateTimeFormatter().format( date );
    memento.putString( key, dateString );
  }

  protected Date readDate( final IMemento memento, final String key ) {
    Date date = null;
    String dateString = memento.getString( key );
    if( dateString != null ) {
      try {
        date = getDateTimeFormatter().parse( dateString );
      } catch( ParseException exception ) {
        // ignore errors
      }
    }
    return date;
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.ui.views.filters.IGridFilter#getFilter()
   */
  public ViewerFilter getFilter() {
    return this;
  }

  /**
   * @return formatter used to store and read data into {@link IMemento}
   */
  private DateFormat getDateTimeFormatter() {
    DateFormat formatter = DateFormat.getDateTimeInstance( DateFormat.SHORT,
                                                           DateFormat.SHORT,
                                                           new Locale( "Locale.US" ) ); //$NON-NLS-1$
    if( formatter == null ) {
      formatter = DateFormat.getDateTimeInstance( DateFormat.SHORT,
                                                  DateFormat.SHORT );
    }
    return formatter;
  }
}
