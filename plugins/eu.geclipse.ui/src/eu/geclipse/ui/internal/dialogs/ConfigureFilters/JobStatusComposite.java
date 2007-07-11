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

import java.util.ArrayList;
import java.util.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import eu.geclipse.core.model.IGridJobStatus;
import eu.geclipse.ui.views.filters.IGridFilterConfiguration;
import eu.geclipse.ui.views.filters.JobStatusFilter;
import eu.geclipse.ui.views.filters.JobViewFilterConfiguration;

public class JobStatusComposite implements IFilterComposite {
  private JobStatusFilter filter;
  private List<StatusOption> optionsList = new ArrayList<StatusOption>();  
  private Group topGroup;
  private boolean readOnly = false;

  public JobStatusComposite( final JobStatusFilter filter, final Composite parent ) {
    this.filter = filter;
    
    this.topGroup = new Group( parent, SWT.NONE );
    GridLayout layout = new GridLayout( 1, false );
    this.topGroup.setLayout( layout );
    this.topGroup.setLayoutData( new GridData( SWT.FILL, SWT.TOP, false, false ) );
    
    Label label = new Label( this.topGroup, SWT.NONE );
    label.setText( "Show jobs with following status:" );
    
    this.optionsList.add( new StatusOption( this.topGroup, Messages.getString("JobStatusComposite.submitted"), IGridJobStatus.SUBMITTED ) ); //$NON-NLS-1$
    this.optionsList.add( new StatusOption( this.topGroup, Messages.getString("JobStatusComposite.waiting"), IGridJobStatus.WAITING ) ); //$NON-NLS-1$
    this.optionsList.add( new StatusOption( this.topGroup, Messages.getString("JobStatusComposite.running"), IGridJobStatus.RUNNING ) ); //$NON-NLS-1$
    this.optionsList.add( new StatusOption( this.topGroup, Messages.getString("JobStatusComposite.done"), IGridJobStatus.DONE ) ); //$NON-NLS-1$
    this.optionsList.add( new StatusOption( this.topGroup, Messages.getString("JobStatusComposite.aborted"), IGridJobStatus.ABORTED ) ); //$NON-NLS-1$
    this.optionsList.add( new StatusOption( this.topGroup, Messages.getString("JobStatusComposite.purged"), IGridJobStatus.PURGED ) ); //$NON-NLS-1$
    this.optionsList.add( new StatusOption( this.topGroup, Messages.getString("JobStatusComposite.others"), IGridJobStatus.UNKNOWN ) );     //$NON-NLS-1$
    
    refresh();
  }
     
  private class StatusOption {
    private int statusValue;
    private Button checkbox;

    public StatusOption( final Composite parent, final String descriptionString, final int statusValue ) {
      this.statusValue = statusValue;
      this.checkbox = new Button( parent, SWT.CHECK );
      this.checkbox.setText( descriptionString );
    }
    
    void setSelected( final boolean selected ) {
      this.checkbox.setSelection( selected );
    }
    
    boolean isSelected() {
      return this.checkbox.getSelection();
    }
    
    void enable( final boolean enable ) {
      this.checkbox.setEnabled( enable );
    }
    
    int getStatusValue() {
      return this.statusValue;
    }
    
    void setReadOnly( final boolean readOnly ) {
      this.checkbox.setEnabled( !readOnly );
    }
  }

  public void saveToFilter() {
    if( this.filter != null ) {
      for( StatusOption option : this.optionsList ) {
        this.filter.setStatusState( option.getStatusValue(), option.isSelected() );
      }      
    }
  }

  public void setFilter( final IGridFilterConfiguration filterConfiguration ) {
    this.filter = filterConfiguration == null ? null : ((JobViewFilterConfiguration)filterConfiguration).getJobStatusFilter();
    refresh();
  }
  
  private void refresh() {
    if( this.filter != null )
    {      
      for( StatusOption option : this.optionsList ) {
        option.setSelected( this.filter.getStatusState( option.getStatusValue() ) );
      }      
      onChangeEnabledState();
    }
  }

  public void setReadOnly( final boolean readOnly ) {
    this.readOnly = readOnly;
    onChangeEnabledState();
  }
  
  void onChangeEnabledState() {    
    for( StatusOption option : this.optionsList ) {
      option.setReadOnly( this.readOnly );
    }    
  }

  public boolean validate() {
    return true;
  }
}
