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
import eu.geclipse.ui.views.filters.IGridFilterConfiguration;
import eu.geclipse.ui.views.filters.JobSubmissionTimeFilter;
import eu.geclipse.ui.views.filters.JobViewFilterConfiguration;
import eu.geclipse.ui.widgets.DateTimeText;


/**
 *
 */
public class JobSubmissionTimeComposite implements IFilterComposite{

  private JobSubmissionTimeFilter filter;
  private DateTimeText afterDateTimeText;
  private DateTimeText beforeDateTimeText;
  
  public JobSubmissionTimeComposite( final JobSubmissionTimeFilter filter, final Composite parent ) {
    super();    

    this.filter = filter;
    Group group = new Group( parent, SWT.NONE );
    group.setLayout( new GridLayout( 1, false ) );
    group.setLayoutData( new GridData( SWT.FILL, SWT.FILL, false, false ) );
    
    Label label = new Label( group, SWT.NONE );
    label.setText( "Show jobs submitted after:" );
    afterDateTimeText = new DateTimeText( group, DateTimeText.Style.DATETIME );
    
    label = new Label( group, SWT.NONE );
    label.setText( "Show jobs submitted before:" );
    beforeDateTimeText = new DateTimeText( group, DateTimeText.Style.DATETIME );

    refresh();
  }

  public void saveToFilter() {
    if( this.filter != null ) {
      this.filter.setDates( this.afterDateTimeText.getDate(), this.beforeDateTimeText.getDate() );
    }
  }

  public void setFilter( IGridFilterConfiguration filterConfiguration ) {
    if( filterConfiguration != null ) {
      this.filter = ((JobViewFilterConfiguration)filterConfiguration).getSubmissionTimeFilter();
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

  public void setReadOnly( boolean readOnly ) {
    this.afterDateTimeText.setReadOnly( readOnly );
    this.beforeDateTimeText.setReadOnly( readOnly );
  }

  public boolean validate() {
    boolean ok = true;
    if( !this.afterDateTimeText.isValid() 
        || !this.beforeDateTimeText.isValid() ) {      
      // TODO mariusz show problem dialog
//      "You entered date in wrong format.\nPlease enter date in format "
//                    + this.afterDateTimeText.getValidDateFormat()
//                    + " or delete this date.";
      
      ok = false;
    } 
    return ok;
  }
  
}
