package eu.geclipse.ui.views.filters;

import org.eclipse.jface.viewers.ViewerFilter;

import eu.geclipse.ui.internal.actions.ConfigureFiltersAction;


/**
 * Listener which will notified about changes in {@link IGridFilterConfiguration}
 */
public interface IFilterConfigurationListener {
  
  /**
   * Called by {@link GridFilterConfigurationsManager}, when any property in configuration was changed
   * (e.g. new configuration added, configuration deleted, or changed).
   * Used mainly by {@link ConfigureFiltersAction} to update menu items with created configurations
   */
  public void configurationChanged();
  
  /**
   * Called when user changed current configuration
   * @param filters new filters selected by user, which should be applied to the view
   */
  public void filterConfigurationSelected( final ViewerFilter[] filters );
}
