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
import org.eclipse.jface.action.IAction;
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
 * Action for Filter viewers
 */
public class ConfigureFiltersAction extends Action {
  
  private static final String CONFIGURE_IMG = "configure_filters"; //$NON-NLS-1$

  GridFilterConfigurationsManager filterConfigurationsManager;
  private IWorkbenchSite site;
  
  /**
   * @param site - workbench site, on which action will be placed 
   * @param filterConfigurationsManager - filters manager for workbench site 
   */
  public ConfigureFiltersAction( final IWorkbenchSite site, final GridFilterConfigurationsManager filterConfigurationsManager ) {
    super( Messages.getString("ConfigureFiltersAction.name"), IAction.AS_DROP_DOWN_MENU ); //$NON-NLS-1$
    this.filterConfigurationsManager = filterConfigurationsManager;
    this.site = site;
    
    setImageDescriptor( Activator.getDefault().getImageRegistry().getDescriptor( CONFIGURE_IMG ) );    
    setToolTipText( Messages.getString("ConfigureFiltersAction.description") ); //$NON-NLS-1$
    
    setMenuCreator( new MenuCreator() );
  }
  
  private class MenuCreator implements IMenuCreator, IFilterConfigurationListener {
    private Menu menu;
    
    protected MenuCreator() {
      super();
      ConfigureFiltersAction.this.filterConfigurationsManager.addConfigurationListener( this );
    }

    public void dispose() {
      if( this.menu != null ) {
        disposeMenu();
      }
      
      ConfigureFiltersAction.this.filterConfigurationsManager.removeConfigurationListener( this );
    }

    public Menu getMenu( final Control parent ) {
      if( this.menu == null ) {
        this.menu = createMenu( parent );
      }
      return this.menu;
    }

    public Menu getMenu( final Menu parent ) {
      return null;
    }
    
    protected void disposeMenu() {
      if( this.menu != null ) {
        this.menu.dispose();
      }
      this.menu = null;
    }
    
    private Menu createMenu( final Control parent ) {
      Menu rootMenu =  new Menu( parent );
      
      for( IGridFilterConfiguration configuration : ConfigureFiltersAction.this.filterConfigurationsManager.getConfigurations() ) {
        createMenuItem( rootMenu, configuration );
      }
      
      if( !ConfigureFiltersAction.this.filterConfigurationsManager.getConfigurations().isEmpty() ) {
        new MenuItem( rootMenu, SWT.SEPARATOR );
      }
      
      createConfigureMenu( rootMenu );
      
      return rootMenu;
    }
    
    private void createConfigureMenu( final Menu parent ) {
      MenuItem item = new MenuItem( parent, SWT.CASCADE );
      
      item.setText( Messages.getString("ConfigureFiltersAction.ConfigureFilters") ); //$NON-NLS-1$
      
      item.addSelectionListener( new SelectionAdapter() {

        /* (non-Javadoc)
         * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
         */
        @Override
        public void widgetSelected( final SelectionEvent e )
        {
          run();
        }
        
      });
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
  }
  
  private class MenuItemSelectionListener extends SelectionAdapter {
    private IGridFilterConfiguration configuration;

    protected MenuItemSelectionListener( final IGridFilterConfiguration configuration ) {
      super();
      this.configuration = configuration;
    }

    /* (non-Javadoc)
     * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
     */
    @Override
    public void widgetSelected( final SelectionEvent event )
    {
      // check selection before enabling, because widgetSelected is called twice: for old-selected item and for new
      MenuItem menuItem = (MenuItem)event.getSource();
      if( menuItem != null
          && menuItem.getSelection() ) {
        ConfigureFiltersAction.this.filterConfigurationsManager.enableConfiguration( this.configuration );
      }
    }
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run()
  {
    this.filterConfigurationsManager.configure( this.site.getShell() );        
  }
}
