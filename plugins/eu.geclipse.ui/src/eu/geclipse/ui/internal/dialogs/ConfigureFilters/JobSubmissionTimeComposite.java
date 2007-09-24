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
package eu.geclipse.ui.internal.dialogs.ConfigureFilters;

import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import eu.geclipse.core.GridException;
import eu.geclipse.ui.dialogs.NewProblemDialog;
import eu.geclipse.ui.views.filters.IGridFilterConfiguration;
import eu.geclipse.ui.views.filters.JobSubmissionTimeFilter;
import eu.geclipse.ui.views.filters.JobViewFilterConfiguration;
import eu.geclipse.ui.widgets.DateTimeText;

/**
 * Composite to edit {@link JobSubmissionTimeFilter}
 */
public class JobSubmissionTimeComposite implements IFilterComposite {

  private JobSubmissionTimeFilter filter;
  private DateTimeText afterDateTimeText;
  private DateTimeText beforeDateTimeText;
  private Composite parent;

  /**
   * @param filter edited filter
   * @param parent
   */
  public JobSubmissionTimeComposite( final JobSubmissionTimeFilter filter,
                                     final Composite parent )
  {
    super();
    this.filter = filter;
    this.parent = parent;
    Group group = new Group( parent, SWT.NONE );
    group.setLayout( new GridLayout( 1, false ) );
    group.setLayoutData( new GridData( SWT.FILL, SWT.FILL, false, false ) );
    Label label = new Label( group, SWT.NONE );
    label.setText( Messages.getString( "JobSubmissionTimeComposite.afterDateDescription" ) ); //$NON-NLS-1$
    this.afterDateTimeText = new DateTimeText( group,
                                               DateTimeText.Style.DATETIME,
                                               true );
    label = new Label( group, SWT.NONE );
    label.setText( Messages.getString( "JobSubmissionTimeComposite.beforeDateDescription" ) ); //$NON-NLS-1$
    this.beforeDateTimeText = new DateTimeText( group,
                                                DateTimeText.Style.DATETIME,
                                                true );
    refresh();
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.ui.internal.dialogs.ConfigureFilters.IFilterComposite#saveToFilter()
   */
  public boolean saveToFilter() {
    boolean success = false;
    if( this.filter != null ) {
      Date afterDate;
      try {
        afterDate = this.afterDateTimeText.getDate();
        try {
          Date beforeDate = this.beforeDateTimeText.getDate();
          this.filter.setDates( afterDate, beforeDate );
          success = true;
        } catch( GridException exception ) {
          this.beforeDateTimeText.setFocus();
          NewProblemDialog.openProblem( this.parent.getShell(),
                                        Messages.getString( "JobSubmissionTimeComposite.ErrTitle" ),  //$NON-NLS-1$
                                        Messages.getString( "JobSubmissionTimeComposite.ErrSavingBeforeDate" ),  //$NON-NLS-1$
                                        exception );
        }
      } catch( GridException exception ) {
        this.afterDateTimeText.setFocus();
        NewProblemDialog.openProblem( this.parent.getShell(),
                                      Messages.getString( "JobSubmissionTimeComposite.ErrTitle" ),  //$NON-NLS-1$
                                      Messages.getString( "JobSubmissionTimeComposite.ErrSavingAfterDate" ),  //$NON-NLS-1$
                                      exception );
      }
    }
    return success;
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.ui.internal.dialogs.ConfigureFilters.IFilterComposite#setFilter(eu.geclipse.ui.views.filters.IGridFilterConfiguration)
   */
  public void setFilter( final IGridFilterConfiguration filterConfiguration ) {
    if( filterConfiguration != null ) {
      this.filter = ( ( JobViewFilterConfiguration )filterConfiguration ).getSubmissionTimeFilter();
    }
    refresh();
  }

  private void refresh() {
    Date afterDate = null, beforeDate = null;
    if( this.filter != null ) {
      afterDate = this.filter.getAfterDate();
      beforeDate = this.filter.getBeforeDate();
    }
    this.afterDateTimeText.setDate( afterDate );
    this.beforeDateTimeText.setDate( beforeDate );
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.ui.internal.dialogs.ConfigureFilters.IFilterComposite#setEnabled(boolean)
   */
  public void setEnabled( final boolean enabled ) {
    this.afterDateTimeText.setEnabled( enabled );
    this.beforeDateTimeText.setEnabled( enabled );
  }
}
