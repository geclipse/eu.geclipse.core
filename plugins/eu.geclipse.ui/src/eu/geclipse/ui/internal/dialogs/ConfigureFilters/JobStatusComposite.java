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

  public JobStatusComposite( JobStatusFilter filter, Composite parent ) {
    this.filter = filter;
    
    topGroup = new Group( parent, SWT.NONE );
    topGroup.setLayout( new GridLayout( 1, false ) );
    createEnabledCheckBox( topGroup );
    
    optionsList.add( new StatusOption( topGroup, "Submitted", IGridJobStatus.SUBMITTED ) );
    optionsList.add( new StatusOption( topGroup, "Waiting", IGridJobStatus.WAITING ) );
    optionsList.add( new StatusOption( topGroup, "Running", IGridJobStatus.RUNNING ) );
    optionsList.add( new StatusOption( topGroup, "Done", IGridJobStatus.DONE ) );
    optionsList.add( new StatusOption( topGroup, "Aborted", IGridJobStatus.ABORTED ) );
    optionsList.add( new StatusOption( topGroup, "Purged", IGridJobStatus.PURGED ) );
    optionsList.add( new StatusOption( topGroup, "Others", IGridJobStatus.UNKNOWN ) );    
    
    refresh();
  }
  
  private void createEnabledCheckBox( final Composite parent ) {
    this.enabledCheckbox = new Button( parent, SWT.CHECK );
    this.enabledCheckbox.setText( "Show jobs with following status:" );
    
    this.enabledCheckbox.addSelectionListener( new SelectionAdapter() {

      public void widgetSelected( SelectionEvent event ) {
        onChangeEnabledState();
      }
      
    } );
  }
    
  private class StatusOption {
    private int statusValue;
    private Button checkbox;

    public StatusOption( final Composite parent, final String descriptionString, int statusValue ) {
      this.statusValue = statusValue;
      this.checkbox = new Button( parent, SWT.CHECK );
      this.checkbox.setText( descriptionString );
      GridData gridData = new GridData();
      gridData.horizontalIndent = 10;
      this.checkbox.setLayoutData( gridData );
    }
    
    void setSelected( boolean selected ) {
      this.checkbox.setSelection( selected );
    }
    
    boolean isSelected() {
      return this.checkbox.getSelection();
    }
    
    void enable( boolean enable ) {
      this.checkbox.setEnabled( enable );
    }
    
    int getStatusValue() {
      return statusValue;
    }
    
    void setReadOnly( boolean readOnly ) {
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

  public void setFilter( IGridFilterConfiguration filterConfiguration ) {
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

  public void setReadOnly( boolean readOnly ) {
    this.readOnly = readOnly;
    onChangeEnabledState();
  }
  
  private void onChangeEnabledState() {
    this.enabledCheckbox.setEnabled( !this.readOnly );
    for( StatusOption option : this.optionsList ) {
      option.setReadOnly( readOnly || !this.enabledCheckbox.getSelection() );
    }    
  }
}
