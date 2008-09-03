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
package eu.geclipse.ui.dialogs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.internal.dialogs.configureFilters.FilterCompositeFactory;
import eu.geclipse.ui.internal.dialogs.configureFilters.IFilterComposite;
import eu.geclipse.ui.views.filters.GridFilterConfigurationsManager;
import eu.geclipse.ui.views.filters.IGridFilter;
import eu.geclipse.ui.views.filters.IGridFilterConfiguration;

/**
 * Dialog, which allow user to make filter configurations
 */
public class ConfigureFiltersDialog extends TrayDialog {

  protected TableViewer tableViewer;
  protected List<IFilterComposite> composites = new ArrayList<IFilterComposite>();
  protected List<IGridFilterConfiguration> configurations;
  protected GridFilterConfigurationsManager configurationsManager;  

  /**
   * @param shell parent shell, or <code>null</code> to create a top-level
   *            shell
   * @param filterConfigurationsManager manager, from which filters will be
   *            obtained
   */
  public ConfigureFiltersDialog( final Shell shell,
                                 final GridFilterConfigurationsManager filterConfigurationsManager )
  {
    super( shell );
    this.configurationsManager = filterConfigurationsManager;
    this.configurations = copyConfigurations( filterConfigurationsManager.getConfigurations() );
    setShellStyle( getShellStyle() | SWT.RESIZE );
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
   */
  @Override
  protected void configureShell( final Shell newShell )
  {
    newShell.setText( Messages.getString("ConfigureFiltersDialog.shell_text") ); //$NON-NLS-1$
    super.configureShell( newShell );
  } 

  /* (non-Javadoc)
   * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Control createDialogArea( final Composite parent )
  {
    Composite dialogAreaComposite = ( Composite )super.createDialogArea( parent );

    ((GridLayout)dialogAreaComposite.getLayout()).numColumns = 2;
    createConfigurationsComposite( dialogAreaComposite );
    createFiltersComposite( dialogAreaComposite );
    selectEnabledConfiguration();

    return dialogAreaComposite;
  }
  
  private void createConfigurationsComposite( final Composite parent ) {
    Composite configComposite = new Composite( parent, SWT.NONE );
    
    GridLayout layout = new GridLayout( 2, false );
    configComposite.setLayout( layout );
    configComposite.setLayoutData( new GridData( SWT.FILL, SWT.TOP, false, false ) );
    createTableLabel( configComposite );
    createConfigsTable( configComposite );
    createTableButtons( configComposite );
  }
  
  private void createTableLabel( final Composite parent ) {
    Label label = new Label( parent, SWT.NONE );
    GridData gridData = new GridData();
    gridData.horizontalSpan = 2;
    label.setLayoutData( gridData );
    label.setText( Messages.getString("ConfigureFiltersDialog.table_label") ); //$NON-NLS-1$
  }
  
  private void createConfigsTable( final Composite parent ) {    
    this.tableViewer = new TableViewer( parent, SWT.BORDER );
    this.tableViewer.setContentProvider( new ArrayContentProvider() );
    this.tableViewer.setLabelProvider( createTableLabelProvider() );    
    this.tableViewer.addSelectionChangedListener( createTableSelectionListener() );

    GridData gridData = new GridData();
    gridData.heightHint = 150;
    gridData.widthHint = 75;
    this.tableViewer.getTable().setLayoutData( gridData );
    this.tableViewer.setInput( this.configurations );
  }
  
  protected void enableConfiguration( final IGridFilterConfiguration configuration ) {
    for( IGridFilterConfiguration curConfiguration : this.configurations ) {
      curConfiguration.setEnabled( curConfiguration == configuration );
    }
  }

  private ILabelProvider createTableLabelProvider() {
    return new LabelProvider() {

      /* (non-Javadoc)
       * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
       */
      @Override
      public String getText( final Object element )
      {
        return ((IGridFilterConfiguration)element).getName();
      }
    };    
  }
  
  ISelectionChangedListener createTableSelectionListener() {
    return new ISelectionChangedListener() {

      private IGridFilterConfiguration selectedConfiguration;

      public void selectionChanged( final SelectionChangedEvent event ) {
        StructuredSelection selection = ( StructuredSelection )event.getSelection();
        if( selection != null ) {
          if( saveFilter() ) {
            this.selectedConfiguration = ( IGridFilterConfiguration )selection.getFirstElement();
            enableConfiguration( this.selectedConfiguration );
            for( IFilterComposite filterComposite : ConfigureFiltersDialog.this.composites )
            {
              filterComposite.setFilter( this.selectedConfiguration );
            }
          } else {
            ConfigureFiltersDialog.this.tableViewer.removeSelectionChangedListener( this );
            
            ConfigureFiltersDialog.this.tableViewer.setSelection( this.selectedConfiguration != null
                                            ? new StructuredSelection( this.selectedConfiguration )
                                            : null );
            
            ConfigureFiltersDialog.this.tableViewer.addSelectionChangedListener( this );
          }
        }
        setEnabledComposites( selection != null && !selection.isEmpty() );
      }
    };
  }
  
  void setEnabledComposites( final boolean enabled ) {
    for( IFilterComposite filterComposite : ConfigureFiltersDialog.this.composites ) {
      filterComposite.setEnabled( enabled );
    }    
  }

  private void createTableButtons( final Composite parent ) {
    Composite buttonsComposite = new Composite( parent, SWT.NONE );
    GridData gridData = new GridData();
    gridData.verticalAlignment = SWT.TOP;
    buttonsComposite.setLayoutData( gridData );
    buttonsComposite.setLayout( new GridLayout( 1, false ) );
    createNewButton( buttonsComposite );
    createDeleteButton( buttonsComposite );
  }
  
  private void createNewButton( final Composite parent ) {
    Button button = createButton( parent, Messages.getString("ConfigureFiltersDialog.new_button")); //$NON-NLS-1$
    button.addSelectionListener( new SelectionAdapter() {

      /* (non-Javadoc)
       * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
       */
      @Override
      public void widgetSelected( final SelectionEvent e )
      {
        if( saveFilter() ) {
          InputDialog dialog = new InputDialog( getShell(),
                  Messages.getString( "ConfigureFiltersDialog.create_new_filter" ),  //$NON-NLS-1$
                  Messages.getString( "ConfigureFiltersDialog.enter_filter_name" ),  //$NON-NLS-1$
                  "", createNameValidator() ); //$NON-NLS-1$ 
          
          if( dialog.open() == Window.OK ) {
            IGridFilterConfiguration newConfiguration
              = ConfigureFiltersDialog.this.configurationsManager.createConfiguration( dialog.getValue() );
            ConfigureFiltersDialog.this.configurations.add( newConfiguration );
            ConfigureFiltersDialog.this.tableViewer.add( newConfiguration );
            ConfigureFiltersDialog.this.tableViewer.setSelection( new StructuredSelection( newConfiguration ) );            
          }
        }
      }
      
      private IInputValidator createNameValidator() {
        return new IInputValidator() {

          public String isValid( final String newText ) {
            String invalidMsg = null;
            if( findConfiguration( newText ) != null ) {
              invalidMsg = String.format( Messages.getString("ConfigureFiltersDialog.filter_already_exists"),//$NON-NLS-1$ 
                                          newText ); 
            }
            return invalidMsg;
          }
        };
      }
      
    } );    
  }
  
  private void createDeleteButton( final Composite parent ) {
    Button button = createButton( parent, Messages.getString("ConfigureFiltersDialog.delete_button") ); //$NON-NLS-1$
    
    button.addSelectionListener( new SelectionAdapter() {

      /* (non-Javadoc)
       * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
       */
      @Override
      public void widgetSelected( final SelectionEvent e )
      {
        if( saveFilter() ) {
          StructuredSelection selection
            = (StructuredSelection)ConfigureFiltersDialog.this.tableViewer.getSelection();
          if( selection == null || selection.isEmpty() ) {
            MessageDialog.openWarning( getShell(), Messages.getString("ConfigureFiltersDialog.delete_filter"),  //$NON-NLS-1$
                                       Messages.getString("ConfigureFiltersDialog.select_filter") ); //$NON-NLS-1$
          }
          else {
            IGridFilterConfiguration configuration = (IGridFilterConfiguration)selection.getFirstElement();
            ConfigureFiltersDialog.this.tableViewer.remove( configuration );
            ConfigureFiltersDialog.this.configurations.remove( configuration );
            selectDefaultConfiguration();
          }
        }
      }
      
    });
  }
  
  void selectDefaultConfiguration() {
    if( !this.configurations.isEmpty() ) {
      IGridFilterConfiguration configuration = this.configurations.get( 0 );
      this.tableViewer.setSelection( new StructuredSelection( configuration ) );
    }
  }
  
  private Button createButton( final Composite parent, final String textString ) {
    Button button = new Button( parent, SWT.NONE );
    GridData gridData = new GridData();
    gridData.widthHint = 80;
    button.setLayoutData( gridData );
    button.setText( textString );    
    return button;
  }
  
  private void createFiltersComposite( final Composite parent ) {
    Composite filtersComposite = new Composite( parent, SWT.NONE );
    filtersComposite.setLayout( new GridLayout( 2, false ) );
    IGridFilterConfiguration configuration = this.configurations.get( 0 );
    for( IGridFilter filter : configuration.getFilters() ) {
      IFilterComposite composite = FilterCompositeFactory.create( filter, filtersComposite );
      
      Assert.isTrue( composite != null, "Probably IGridFilter.makeClone() returned null" ); //$NON-NLS-1$
      
      if( composite != null ) {
        this.composites.add( composite );
      }
    }
  }
  
  private List<IGridFilterConfiguration> copyConfigurations( final List<IGridFilterConfiguration> sourceConfigurations )
  {
    List<IGridFilterConfiguration> newConfigurations = 
        new ArrayList<IGridFilterConfiguration>( 3 
            + ( sourceConfigurations != null ? sourceConfigurations.size() : 0 ) );
    
    if( sourceConfigurations != null ) {
      try {
        for( IGridFilterConfiguration configuration : sourceConfigurations ) {
          newConfigurations.add( configuration.clone() );
        }
      } catch( CloneNotSupportedException exception ) {
        Activator.logException( exception );
      }
    }
    if( newConfigurations.isEmpty() ) {
      IGridFilterConfiguration defaultConfiguration = 
        this.configurationsManager.createConfiguration( Messages.getString( "ConfigureFiltersDialog.default_config" ) ); //$NON-NLS-1$
      
      newConfigurations.add( defaultConfiguration );
    }
    return newConfigurations;
  }
  
  IGridFilterConfiguration findConfiguration( final String name ) {
    IGridFilterConfiguration foundConfiguration = null;
    
    for( Iterator<IGridFilterConfiguration> iterator = this.configurations.iterator(); 
        iterator.hasNext() && foundConfiguration == null;  )
    {
      IGridFilterConfiguration configuration = iterator.next();
      if( configuration.getName().equals( name ) ) {
        foundConfiguration = configuration;
      }
    }
    return foundConfiguration;
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.dialogs.Dialog#okPressed()
   */
  @Override
  protected void okPressed()
  {
    if( saveFilter() ) {
      super.okPressed();
    }
  }

  /**
   * @return configurations changed in dialog
   */
  public List<IGridFilterConfiguration> getConfigurations() {
    return this.configurations;
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.dialogs.TrayDialog#isHelpAvailable()
   */
  @Override
  public boolean isHelpAvailable()
  {
    return false;
  }

  protected boolean saveFilter() {
    boolean success = true;
    Iterator<IFilterComposite> iterator = this.composites.iterator();    
    
    while( success && iterator.hasNext() ) {
      IFilterComposite composite = iterator.next();
      success &= composite.saveToFilter();
    }
    
    return success;
  }
  
  private void selectEnabledConfiguration() {
    if( !this.configurations.isEmpty() ) {
      IGridFilterConfiguration enabledConfiguration = findEnabledConfiguration();
      
      if( enabledConfiguration != null ) {
        this.tableViewer.setSelection( new StructuredSelection( enabledConfiguration ) );
      }
    }    
  }

  private IGridFilterConfiguration findEnabledConfiguration() {
    IGridFilterConfiguration enabledConfiguration = null;
    
    for( Iterator<IGridFilterConfiguration> iterator = this.configurations.iterator(); 
        iterator.hasNext() && enabledConfiguration == null; ) 
    {
      IGridFilterConfiguration configuration = iterator.next();
      
      if( configuration.isEnabled() ) {
        enabledConfiguration = configuration;
      }       
    }
    
    return enabledConfiguration;
  }
  
}
