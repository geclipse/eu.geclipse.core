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
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IMemento;
import eu.geclipse.ui.dialogs.ConfigureFiltersDialog;


/**
 *
 */
abstract public class GridFilterConfigurationsManager {
  static final public String ID_JOBVIEW = "JobView"; //$NON-NLS-1$
  
  static final private String MEMENTO_TYPE_CONFIGURATIONS_LIST = "FilterConfigurationsList"; //$NON-NLS-1$
  static final private String MEMENTO_TYPE_CONFIGURATION = "FilterConfiguration"; //$NON-NLS-1$
  
  private String id;
  private List<IGridFilterConfiguration> configurations = new ArrayList<IGridFilterConfiguration>();
  private StructuredViewer viewer; // TODO mariusz use IFilterConfigurationListener instead of directly calling viewer
  private List<IFilterConfigurationListener> listeners = new ArrayList<IFilterConfigurationListener>();
  
  /**
   * @param id identifier used to read/save state into {@link IMemento}. Shoule be unique. The best - view class name
   */
  public GridFilterConfigurationsManager( final String id, final StructuredViewer viewer ) {
    super();
    this.id = id;
    this.viewer = viewer;
  }
  
  public abstract IGridFilterConfiguration createConfiguration( final String name );
  
  public void saveState( final IMemento memento ) {
    if( memento != null
        && !this.configurations.isEmpty() ) {
      IMemento configurationsListMemento = memento.createChild( getMementoTypeName() );
      
      for( IGridFilterConfiguration configuration : this.configurations ) {
        IMemento configurationMemento = configurationsListMemento.createChild( MEMENTO_TYPE_CONFIGURATION, configuration.getName() );
        configuration.saveState( configurationMemento );
      }
    }
  }

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
    if( this.viewer != null ) {
      this.viewer.setFilters( getEnabledFilters() );
    }    
  }
  
  private ViewerFilter[] getEnabledFilters() {
    List<ViewerFilter> filters = new ArrayList<ViewerFilter>();
    
    for( IGridFilterConfiguration configuration : this.configurations ) {
      if( configuration.isEnabled() )
      {
        for( IGridFilter filter : configuration.getFilters() ) {
          if( filter.isEnabled() ) {
            filters.add( filter.getFilter() );
          }
        }
      }
    }
    
    return filters.toArray( new ViewerFilter[filters.size()] );
  }
  
  private String getMementoTypeName() {
    return MEMENTO_TYPE_CONFIGURATIONS_LIST + "." + this.id; //$NON-NLS-1$
  }
  
  public final List<IGridFilterConfiguration> getConfigurations() {
    return this.configurations;
  }
  
  public boolean configure( final Shell shell ) {
    boolean configurationChanged = false;
    
    ConfigureFiltersDialog dialog = new ConfigureFiltersDialog( shell, this );          
    if( dialog.open() == Window.OK ) {
      this.configurations = dialog.getConfigurations();
      setChangedConfigurations();
      configurationChanged = true;
    }

    return configurationChanged;
  }
  
  private void setChangedConfigurations() {
    if( this.viewer != null ) {
      this.viewer.setFilters( getEnabledFilters() );
    }
    
    fireConfigurationChanged();
  }
  
  /**
   * Enables given configuration, and disables rest of configurations
   * @param configuration
   */
  public void enableConfiguration( final IGridFilterConfiguration configuration ) {
    for( IGridFilterConfiguration curConfiguration : this.configurations ) {
      curConfiguration.setEnabled( curConfiguration == configuration );
    }
    
    setChangedConfigurations();
  }

  public void addConfigurationListener( final IFilterConfigurationListener listener ) {
    this.listeners.add( listener );
  }
  
  public void removeConfigurationListener( final IFilterConfigurationListener listener ) {
    this.listeners.remove( listener );
  }
  
  private void fireConfigurationChanged() {
    for( IFilterConfigurationListener listener : this.listeners ) {
      listener.configurationChanged();
    }
  }

}
