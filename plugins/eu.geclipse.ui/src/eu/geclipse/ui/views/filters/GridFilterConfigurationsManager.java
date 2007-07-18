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

import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IMemento;

import eu.geclipse.ui.dialogs.ConfigureFiltersDialog;
import eu.geclipse.ui.views.GridJobView;

/**
 * Manager handling {@link IGridFilterConfiguration}
 * Every view should create class inherit from {@link GridFilterConfigurationsManager} with abstract methods implementation
 */
abstract public class GridFilterConfigurationsManager {

  /**
   * Id for filters for {@link GridJobView}. Used to read/save filters state
   */
  static final public String ID_JOBVIEW = "JobView"; //$NON-NLS-1$
  static final private String MEMENTO_TYPE_CONFIGURATIONS_LIST = "FilterConfigurationsList"; //$NON-NLS-1$
  static final private String MEMENTO_TYPE_CONFIGURATION = "FilterConfiguration"; //$NON-NLS-1$
  private String id;
  private List<IGridFilterConfiguration> configurations = new ArrayList<IGridFilterConfiguration>();
  private List<IFilterConfigurationListener> listeners = new ArrayList<IFilterConfigurationListener>();

  /**
   * @param id identifier used to read/save state into {@link IMemento}. Should be unique. The best - view class name
   */
  public GridFilterConfigurationsManager( final String id ) {
    super();
    this.id = id;
  }

  /**
   * Implementators should create and return new objects implementing IGridFilterConfiguration
   * @param name for configuration
   * @return created configuration
   */
  public abstract IGridFilterConfiguration createConfiguration( final String name );

  /**
   * Save configurations state into memento
   * @param memento into, which configurations will be saved
   */
  public void saveState( final IMemento memento ) {
    if( memento != null && !this.configurations.isEmpty() ) {
      IMemento configurationsListMemento = memento.createChild( getMementoTypeName() );
      for( IGridFilterConfiguration configuration : this.configurations ) {
        IMemento configurationMemento = configurationsListMemento.createChild( MEMENTO_TYPE_CONFIGURATION,
                                                                               configuration.getName() );
        configuration.saveState( configurationMemento );
      }
    }
  }

  /**
   * Read configurations state from memento
   * @param memento from which state will be read
   */
  public void readState( final IMemento memento ) {
    this.configurations.clear();
    if( memento != null ) {
      IMemento configurationsListMemento = memento.getChild( getMementoTypeName() );
      if( configurationsListMemento != null ) {
        for( IMemento configurationMemento : configurationsListMemento.getChildren( MEMENTO_TYPE_CONFIGURATION ) )
        {
          String nameString;
          nameString = configurationMemento.getID();
          if( nameString != null ) {
            IGridFilterConfiguration configuration = createConfiguration( nameString );
            configuration.read( configurationMemento );
            this.configurations.add( configuration );
          }
        }
      }
    }    
    fireConfigurationChanged();
    fireFilterConfigurationSelected();
  }

  /**
   * @return filters, which are active in given configuration, and should be
   *         applied to current view (when this configuration is enabled)
   */
  public ViewerFilter[] getEnabledFilters() {
    List<ViewerFilter> filters = new ArrayList<ViewerFilter>();
    for( IGridFilterConfiguration configuration : this.configurations ) {
      if( configuration.isEnabled() ) {
        for( IGridFilter filter : configuration.getFilters() ) {
          if( filter.isEnabled() ) {
            filters.add( filter.getFilter() );
          }
        }
      }
    }
    return filters.toArray( new ViewerFilter[ filters.size() ] );
  }

  private String getMementoTypeName() {
    return MEMENTO_TYPE_CONFIGURATIONS_LIST + "." + this.id; //$NON-NLS-1$
  }

  /**
   * @return all created filter configurations
   */
  public final List<IGridFilterConfiguration> getConfigurations() {
    return this.configurations;
  }

  /**
   * Opens dialog to add/delete/change filter configurations
   * @param shell parent for dialog
   * @return true if dialog was closed by OK, and all changes should be stored
   */
  public boolean configure( final Shell shell ) {
    boolean configurationChanged = false;
    ConfigureFiltersDialog dialog = new ConfigureFiltersDialog( shell, this );
    if( dialog.open() == Window.OK ) {
      this.configurations = dialog.getConfigurations();
      configurationChanged = true;
      fireConfigurationChanged();
      fireFilterConfigurationSelected();
    }
    return configurationChanged;
  }

  /**
   * Enables given configuration, and disables rest of configurations
   * @param configuration
   */
  public void enableConfiguration( final IGridFilterConfiguration configuration )
  {
    for( IGridFilterConfiguration curConfiguration : this.configurations ) {
      curConfiguration.setEnabled( curConfiguration == configuration );
    }
    fireFilterConfigurationSelected();
  }

  /**
   * Add listener if this listener hasn't added yet
   * @param listener
   */
  public void addConfigurationListener( final IFilterConfigurationListener listener )
  {
    if( !this.listeners.contains( listener ) ) {
      this.listeners.add( listener );
    }
  }

  /**
   * @param listener
   */
  public void removeConfigurationListener( final IFilterConfigurationListener listener )
  {
    this.listeners.remove( listener );
  }

  private void fireConfigurationChanged() {
    for( IFilterConfigurationListener listener : this.listeners ) {
      listener.configurationChanged();
    }
  }

  private void fireFilterConfigurationSelected() {
    ViewerFilter[] filters = getEnabledFilters();
    for( IFilterConfigurationListener listener : this.listeners ) {
      listener.filterConfigurationSelected( filters );
    }
  }
}
