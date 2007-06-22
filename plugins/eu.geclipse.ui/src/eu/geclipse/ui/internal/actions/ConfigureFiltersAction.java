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
package eu.geclipse.ui.internal.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IWorkbenchSite;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.views.filters.GridFilterConfigurationsManager;
import eu.geclipse.ui.views.filters.IFilterConfigurationListener;
import eu.geclipse.ui.views.filters.IGridFilterConfiguration;


/**
 *
 */
public class ConfigureFiltersAction extends Action {

  private GridFilterConfigurationsManager filterConfigurationsManager;
  private IWorkbenchSite site;
  private IMenuCreator menuCreator;
  
  public ConfigureFiltersAction( final IWorkbenchSite site, final GridFilterConfigurationsManager filterConfigurationsManager ) {
    super( "&Configure Filters...", Action.AS_DROP_DOWN_MENU );
    this.filterConfigurationsManager = filterConfigurationsManager;
    this.site = site;
    
    setImageDescriptor( Activator.getDefault().getImageRegistry().getDescriptor( "configure_filters" ) );    
    setToolTipText( "Configure the filters to be applied to this view" );
    
    setMenuCreator( new MenuCreator() );
  }
  
  private class MenuCreator implements IMenuCreator, IFilterConfigurationListener {
    private Menu menu;
    
    public MenuCreator() {
      super();
      filterConfigurationsManager.addConfigurationListener( this );
    }

    public void dispose() {
      if( menu != null ) {
        disposeMenu();
      }
      
      filterConfigurationsManager.removeConfigurationListener( this );
    }

    public Menu getMenu( Control parent ) {
      if( this.menu == null ) {
        this.menu = createMenu( parent );
      }
      return this.menu;
    }

    public Menu getMenu( Menu parent ) {
      return null;
    }
    
    protected void disposeMenu() {
      menu.dispose();
      this.menu = null;
    }
    
    private Menu createMenu( final Control parent ) {
      Menu menu =  new Menu( parent );
      
      for( IGridFilterConfiguration configuration : filterConfigurationsManager.getConfigurations() ) {
        createMenuItem( menu, configuration );
      }
      
      return menu;
    }
    
    private void createMenuItem( final Menu parent, final IGridFilterConfiguration configuration ) {
      MenuItem item = new MenuItem( parent, SWT.RADIO );
      item.setText( configuration.getName() );
      item.setSelection( configuration.isEnabled() );
      item.addSelectionListener( new MenuItemSelectionListener( configuration ) );
    }
    
    public void configurationChanged() {
      disposeMenu();
    }
  };
  
  private class MenuItemSelectionListener extends SelectionAdapter {
    private IGridFilterConfiguration configuration;

    public MenuItemSelectionListener( final IGridFilterConfiguration configuration ) {
      super();
      this.configuration = configuration;
    }

    /* (non-Javadoc)
     * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
     */
    @Override
    public void widgetSelected( SelectionEvent event )
    {
      // check selection before enabling, because widgetSelected is called twice: for old-selected item and for new
      MenuItem menuItem = (MenuItem)event.getSource();
      if( menuItem != null
          && menuItem.getSelection() ) {
        filterConfigurationsManager.enableConfiguration( configuration );
      }
    }
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run()
  {
    filterConfigurationsManager.configure( this.site.getShell() );        
  }
}
