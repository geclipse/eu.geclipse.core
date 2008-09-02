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

import java.util.Date;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IMemento;

import eu.geclipse.core.model.IGridJob;

/**
 * Filter for jobs using time when job was submitted
 */
public class JobSubmissionTimeFilter extends AbstractGridViewerFilter {

  static private String MEMENTO_KEY_AFTERDATE = "AfterDate"; //$NON-NLS-1$
  static private String MEMENTO_KEY_BEFOREDATE = "BeforeDate"; //$NON-NLS-1$
  private Date afterDate;
  private Date beforeDate;

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
   *      java.lang.Object, java.lang.Object)
   */
  @Override
  public boolean select( final Viewer viewer,
                         final Object parentElement,
                         final Object element )
  {
    boolean showOnView = true;
    if( element instanceof IGridJob ) {
      IGridJob job = ( IGridJob )element;
      if( job.getSubmissionTime() != null ) {
        if( this.afterDate != null ) {
          showOnView &= job.getSubmissionTime().after( this.afterDate );
        }
        if( this.beforeDate != null ) {
          showOnView &= job.getSubmissionTime().before( this.beforeDate );
        }
      }
    }
    return showOnView;
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.ui.views.filters.IGridFilter#getFilterId()
   */
  public String getFilterId() {
    return getId();
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.ui.views.filters.IGridFilter#isEnabled()
   */
  public boolean isEnabled() {
    return this.afterDate != null || this.beforeDate != null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.ui.views.filters.IGridFilter#makeClone()
   */
  public IGridFilter makeClone() throws CloneNotSupportedException {
    JobSubmissionTimeFilter newFilter = ( JobSubmissionTimeFilter )super.clone();
    if( this.afterDate != null ) {
      newFilter.afterDate = ( Date )this.afterDate.clone();
    }
    if( this.beforeDate != null ) {
      newFilter.beforeDate = ( Date )this.beforeDate.clone();
    }
    return newFilter;
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.ui.views.filters.IGridFilter#readState(org.eclipse.ui.IMemento)
   */
  public void readState( final IMemento filterMemento ) {
    this.afterDate = readDate( filterMemento, MEMENTO_KEY_AFTERDATE );
    this.beforeDate = readDate( filterMemento, MEMENTO_KEY_BEFOREDATE );
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.ui.views.filters.IGridFilter#saveState(org.eclipse.ui.IMemento)
   */
  public void saveState( final IMemento filterMemento ) {
    if( this.afterDate != null ) {
      saveDate( filterMemento, MEMENTO_KEY_AFTERDATE, this.afterDate );
    }
    if( this.beforeDate != null ) {
      saveDate( filterMemento, MEMENTO_KEY_BEFOREDATE, this.beforeDate );
    }
  }

  static String getId() {
    return "JobSubmissionTimeFilter"; //$NON-NLS-1$
  }

  /**
   * @param newAfterDate jobs submitted after this date will be shown on view
   *            (may be null)
   * @param newBeforeDate jobs submitted before this date will be shown on view
   *            (may be null)
   */
  public void setDates( final Date newAfterDate, final Date newBeforeDate ) {
    this.afterDate = newAfterDate;
    this.beforeDate = newBeforeDate;
  }

  /**
   * @return date after, which jobs will be visibled on view, or null if this
   *         date shouldn't be used to filter
   */
  public Date getAfterDate() {
    return this.afterDate;
  }

  /**
   * @return date before, which jobs will be visibled on view, or null if this
   *         date shouldn't be used to filter
   */
  public Date getBeforeDate() {
    return this.beforeDate;
  }
}
