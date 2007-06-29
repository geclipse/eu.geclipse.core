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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import eu.geclipse.core.model.IGridJobStatus;
import eu.geclipse.ui.views.filters.IGridFilterConfiguration;
import eu.geclipse.ui.views.filters.JobStatusFilter;
import eu.geclipse.ui.views.filters.JobViewFilterConfiguration;

public class JobStatusComposite implements IFilterComposite {
  private JobStatusFilter filter;
  private List<StatusOption> optionsList = new ArrayList<StatusOption>();
  private Button enabledCheckbox;
  private Group topGroup;
  private boolean readOnly = false;

  public JobStatusComposite( final JobStatusFilter filter, final Composite parent ) {
    this.filter = filter;
    
    this.topGroup = new Group( parent, SWT.NONE );
    this.topGroup.setLayout( new GridLayout( 1, false ) );
    createEnabledCheckBox( this.topGroup );
    
    this.optionsList.add( new StatusOption( this.topGroup, Messages.getString("JobStatusComposite.submitted"), IGridJobStatus.SUBMITTED ) ); //$NON-NLS-1$
    this.optionsList.add( new StatusOption( this.topGroup, Messages.getString("JobStatusComposite.waiting"), IGridJobStatus.WAITING ) ); //$NON-NLS-1$
    this.optionsList.add( new StatusOption( this.topGroup, Messages.getString("JobStatusComposite.running"), IGridJobStatus.RUNNING ) ); //$NON-NLS-1$
    this.optionsList.add( new StatusOption( this.topGroup, Messages.getString("JobStatusComposite.done"), IGridJobStatus.DONE ) ); //$NON-NLS-1$
    this.optionsList.add( new StatusOption( this.topGroup, Messages.getString("JobStatusComposite.aborted"), IGridJobStatus.ABORTED ) ); //$NON-NLS-1$
    this.optionsList.add( new StatusOption( this.topGroup, Messages.getString("JobStatusComposite.purged"), IGridJobStatus.PURGED ) ); //$NON-NLS-1$
    this.optionsList.add( new StatusOption( this.topGroup, Messages.getString("JobStatusComposite.others"), IGridJobStatus.UNKNOWN ) );     //$NON-NLS-1$
    
    refresh();
  }
  
  private void createEnabledCheckBox( final Composite parent ) {
    this.enabledCheckbox = new Button( parent, SWT.CHECK );
    this.enabledCheckbox.setText( Messages.getString("JobStatusComposite.enabled_box_text") ); //$NON-NLS-1$
    
    this.enabledCheckbox.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent event ) {
        onChangeEnabledState();
      }
      
    } );
  }
    
  private class StatusOption {
    private int statusValue;
    private Button checkbox;

    public StatusOption( final Composite parent, final String descriptionString, final int statusValue ) {
      this.statusValue = statusValue;
      this.checkbox = new Button( parent, SWT.CHECK );
      this.checkbox.setText( descriptionString );
      GridData gridData = new GridData();
      gridData.horizontalIndent = 10;
      this.checkbox.setLayoutData( gridData );
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
      this.filter.setEnabled( this.enabledCheckbox.getSelection() );
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
      this.enabledCheckbox.setSelection( this.filter.isEnabled() );
      
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
    this.enabledCheckbox.setEnabled( !this.readOnly );
    for( StatusOption option : this.optionsList ) {
      option.setReadOnly( this.readOnly || !this.enabledCheckbox.getSelection() );
    }    
  }
}
