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

import org.eclipse.ui.IMemento;


/**
 *
 */
public class JobViewFilterConfiguration extends AbstractGridFilterConfiguration
{
  public JobViewFilterConfiguration( final String name ) {
    super( name );
    addFilter( new JobStatusFilter() );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.ui.views.filters.AbstractGridFilterConfiguration#readFilter(org.eclipse.ui.IMemento, java.lang.String)
   */
  @Override
  protected void readFilter( final IMemento filterMemento, final String filterId )
  {
    if( filterId.equals( JobStatusFilter.getId() ) ) {
      getJobStatusFilter().readState( filterMemento );
    }
  }
  
  public JobStatusFilter getJobStatusFilter() {
    return findFilter( JobStatusFilter.class );
  }
}
