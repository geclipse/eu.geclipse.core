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
import java.util.List;

import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import eu.geclipse.core.model.IGridJobStatus;


/**
 *
 */
public class JobFiltersDialog extends TrayDialog {
  
  private ViewerFilter[] filters;
  private List<AbstractFilterComposite> filterCompositesList = new ArrayList<AbstractFilterComposite>();

  public JobFiltersDialog( final Shell shell, final ViewerFilter[] filters ) {
    super( shell );
    this.filters = filters;
    setShellStyle( getShellStyle() | SWT.RESIZE );
  }
  
  public ViewerFilter[] getFilters() {
    return this.filters;
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Control createDialogArea( final Composite parent )
  {
    Composite dialogAreaComposite = new Composite( parent, SWT.NONE );
    
    GridLayout layout = new GridLayout( 2, false );   
    dialogAreaComposite.setLayout( layout );
    dialogAreaComposite.setLayoutData( new GridData( GridData.FILL_BOTH ) );
    
    
    this.filterCompositesList.add( new JobStatusComposite( dialogAreaComposite, (JobStatusFilter) findFilter( this.filters, JobStatusFilter.class ) ) );    
        
    return super.createDialogArea( parent );
  }
  
  private ViewerFilter findFilter( final ViewerFilter[] filterList, final Class<? extends ViewerFilter> filterClass ) {
    ViewerFilter filter = null;
    
    for( int index = 0; index < filterList.length && filter == null; index++ ) {
      ViewerFilter currentFilter = filterList[index];
      if( currentFilter.getClass().equals( filterClass ) ) {
        filter = currentFilter;
      }
    }
    
    return filter;
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.dialogs.Dialog#okPressed()
   */
  @Override
  protected void okPressed()
  {    
    for( AbstractFilterComposite filterComposie : this.filterCompositesList ) {
      filterComposie.saveFilter();
    }
    super.okPressed();
  }
  
  private abstract class AbstractFilterComposite {
    abstract protected void saveFilter();    
  }
  
  private class JobStatusComposite extends AbstractFilterComposite
  {
    final JobStatusFilter filter;
    private List<StatusOption> optionsList = new ArrayList<StatusOption>();
    private Button enabledCheckbox;
    
    public JobStatusComposite( final Composite parent, final JobStatusFilter jobStatusFilter ) {
      this.filter = jobStatusFilter;
      Group jobStatusGroup = new Group( parent, SWT.NONE );
      jobStatusGroup.setLayoutData( new GridData( GridData.FILL_BOTH ) );
      
      jobStatusGroup.setLayout( new GridLayout( 1, false ) );
      
      createEnabledCheckBox( jobStatusGroup );
      
      optionsList.add( new StatusOption( jobStatusGroup, "Submitted", IGridJobStatus.SUBMITTED ) );
      optionsList.add( new StatusOption( jobStatusGroup, "Waiting", IGridJobStatus.WAITING ) );
      optionsList.add( new StatusOption( jobStatusGroup, "Running", IGridJobStatus.RUNNING ) );
      optionsList.add( new StatusOption( jobStatusGroup, "Done", IGridJobStatus.DONE ) );
      optionsList.add( new StatusOption( jobStatusGroup, "Aborted", IGridJobStatus.ABORTED ) );
      optionsList.add( new StatusOption( jobStatusGroup, "Abandoned", IGridJobStatus.PURGED ) );
      optionsList.add( new StatusOption( jobStatusGroup, "Others", IGridJobStatus.UNKNOWN ) );
            
      refresh( jobStatusFilter );
    }
    
    void createEnabledCheckBox( final Composite parent ) {
      this.enabledCheckbox = new Button( parent, SWT.CHECK );
      this.enabledCheckbox.setText( "Show jobs with following status:" );
      
      this.enabledCheckbox.addSelectionListener( new SelectionAdapter() {

        public void widgetSelected( final SelectionEvent event ) {
          enableFilter( enabledCheckbox.getSelection() );
        }
        
      } );
    }
    
    private void refresh( final JobStatusFilter filter ) {    

      for( StatusOption option : this.optionsList ) {
        option.setSelected( filter.getStatusState( option.getStatusValue() ) );
      }
      
      enableFilter( filter.isEnabled() );
    }
    
    private void enableFilter( final boolean enable ) {
      this.enabledCheckbox.setSelection( enable );
      
      for( StatusOption option : this.optionsList ) {
        option.enable( enable );
      }
    }
    
    @Override
    protected void saveFilter() {
      
      for( StatusOption option : this.optionsList ) {
        this.filter.setStatusState( option.getStatusValue(), option.isSelected() );
      }      
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
    }
    
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
   */
  @Override
  protected void configureShell( final Shell newShell )
  {
    newShell.setText( "Configure filters" );
    super.configureShell( newShell );
  }
  
}
  

