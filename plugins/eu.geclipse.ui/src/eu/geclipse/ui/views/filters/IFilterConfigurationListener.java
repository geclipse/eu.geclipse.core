package eu.geclipse.ui.views.filters;


public interface IFilterConfigurationListener {
  
  /**
   * Called by {@link GridFilterConfigurationsManager}, when any property in configuration was changed
   */
  public void configurationChanged();
}
