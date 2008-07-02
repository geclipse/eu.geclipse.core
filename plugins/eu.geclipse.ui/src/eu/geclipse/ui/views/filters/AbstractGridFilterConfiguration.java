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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.ui.IMemento;

/**
 * Configuration object join all filters, which can be applied for given view.
 * User can create many filter configurations and switch between them.
 */
abstract public class AbstractGridFilterConfiguration
  implements IGridFilterConfiguration
{

  private static final String MEMENTO_KEY_ENABLED = "Enabled"; //$NON-NLS-1$
  private static final String MEMENTO_KEY_TYPE = "Filter"; //$NON-NLS-1$
  protected ArrayList<IGridFilter> filtersList = new ArrayList<IGridFilter>();
  private String name;
  private boolean enabled = false;

  /**
   * @param name configuration name
   */
  public AbstractGridFilterConfiguration( final String name ) {
    super();
    this.name = name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#clone()
   */
  @Override
  public IGridFilterConfiguration clone() throws CloneNotSupportedException {
    AbstractGridFilterConfiguration newConfiguration = ( AbstractGridFilterConfiguration )super.clone();
    newConfiguration.filtersList = new ArrayList<IGridFilter>();
    for( IGridFilter filter : this.filtersList ) {
      newConfiguration.filtersList.add( filter.makeClone() );
    }
    return newConfiguration;
  }

  public void read( final IMemento configurationMemento ) {
    Integer integer = configurationMemento.getInteger( MEMENTO_KEY_ENABLED );
    if( integer != null ) {
      this.enabled = ( integer.intValue() != 0 );
    }
    for( IMemento filterMemento : configurationMemento.getChildren( MEMENTO_KEY_TYPE ) )
    {
      IGridFilter filter = findFilter( filterMemento.getID() );
      if( filter != null ) {
        filter.readState( filterMemento );
      } // if filter wasn't found, then maybe it exists in IMemento, but current
        // implementation doesn't support this filter longer. Just ignore it.
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.ui.views.filters.IGridFilterConfiguration#isEnabled()
   */
  public boolean isEnabled() {
    return this.enabled;
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.ui.views.filters.IGridFilterConfiguration#getFilters()
   */
  public List<IGridFilter> getFilters() {
    return this.filtersList;
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.ui.views.filters.IGridFilterConfiguration#saveState(org.eclipse.ui.IMemento)
   */
  public void saveState( final IMemento memento ) {
    memento.putInteger( MEMENTO_KEY_ENABLED, this.enabled ? 1 : 0 );
    for( IGridFilter filter : this.filtersList ) {
      IMemento filterMemento = memento.createChild( MEMENTO_KEY_TYPE, filter.getFilterId() );
      filter.saveState( filterMemento );
    }
  }

  protected void addFilter( final IGridFilter filter ) {
    this.filtersList.add( filter );
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.ui.views.filters.IGridFilterConfiguration#getName()
   */
  public String getName() {
    return this.name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.ui.views.filters.IGridFilterConfiguration#setEnabled(boolean)
   */
  public void setEnabled( final boolean enabled ) {
    this.enabled = enabled;
  }

  protected IGridFilter findFilter( final String id ) {
    IGridFilter filter = null;
    Iterator<IGridFilter> iterator = this.filtersList.iterator();
    while( iterator.hasNext() && filter == null ) {
      IGridFilter currentFilter = iterator.next();
      if( currentFilter.getFilterId().equals( id ) ) {
        filter = currentFilter;
      }
    }
    return filter;
  }
}
