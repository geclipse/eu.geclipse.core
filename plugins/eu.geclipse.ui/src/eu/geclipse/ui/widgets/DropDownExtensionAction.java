/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributors:
 *    Thomas Koeckerbauer GUP, JKU - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.widgets;

import java.util.LinkedList;
import java.util.List;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import eu.geclipse.ui.internal.Activator;

/**
 * Drop down menu which gets it's contents from an extension point.
 * 
 * @param <UserDataType> type of the user data which is passed then a entry
 *                       gets selected.
 */
public class DropDownExtensionAction<UserDataType> extends Action implements IMenuCreator {
  UserDataType userData;
  private String extensionPointId;

  /**
   * Create a new drop down menu which gets its contents from the specified
   * extension point.
   * 
   * @param userData user data to pass when a entry gets selected.
   * @param extensionPointId id of the extension point which holds the drop down
   *                         entries.
   */
  public DropDownExtensionAction( final UserDataType userData,
                                  final String extensionPointId ) {
    this.userData = userData;
    this.extensionPointId = extensionPointId;
    setImageDescriptor( PlatformUI.getWorkbench()
      .getSharedImages()
      .getImageDescriptor( ISharedImages.IMG_TOOL_NEW_WIZARD ) );
    setMenuCreator( this );
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.action.IMenuCreator#dispose()
   */
  public void dispose() {
    // empty
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.action.IMenuCreator#getMenu(org.eclipse.swt.widgets.Control)
   */
  public Menu getMenu( final Control parent ) {
    Menu menu = new Menu( parent );
    Action[] terminalFactories = getFactoryList();
    for( Action action :  terminalFactories ) {
      ActionContributionItem item = new ActionContributionItem( action );
      item.fill( menu, -1 );
    }
    return menu;
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.action.IMenuCreator#getMenu(org.eclipse.swt.widgets.Menu)
   */
  public Menu getMenu( final Menu parent ) {
    // no menu for a parent menu
    return null;
  }

  @SuppressWarnings("unchecked")
  private Action[] getFactoryList() {
    List<Action> list = new LinkedList<Action>();
    IExtensionRegistry registry = Platform.getExtensionRegistry();
    IExtensionPoint extensionPoint 
      = registry.getExtensionPoint( this.extensionPointId );
    if ( extensionPoint != null ) {
      IExtension[] extensions = extensionPoint.getExtensions();
      for( IExtension extension : extensions ) {
        IConfigurationElement[] elements = extension.getConfigurationElements();
        for( IConfigurationElement element : elements ) {
          if( IDropDownEntry.EXT_DROP_DOWN_ENTRY.equals( element.getName() ) ) {
            try {
              final String name = element.getAttribute( IDropDownEntry.EXT_LABEL );
              Object object = element.createExecutableExtension( IDropDownEntry.EXT_CLASS );
              if ( object instanceof IDropDownEntry ) {
                final IDropDownEntry<UserDataType> factory = ( IDropDownEntry<UserDataType> )object;
                String iconName = element.getAttribute( IDropDownEntry.EXT_ICON );
                ImageDescriptor icon = null;
                if ( iconName != null ) {
                  String pluginId = element.getContributor().getName();
                  icon = AbstractUIPlugin.imageDescriptorFromPlugin( pluginId, iconName );
                }
                final ImageDescriptor finalIcon = icon;
                Action action = new Action() {
                  /* (non-Javadoc)
                   * @see org.eclipse.jface.action.Action#getImageDescriptor()
                   */
                  @Override
                  public ImageDescriptor getImageDescriptor() {
                    return finalIcon;
                  }
                  
                  /* (non-Javadoc)
                   * @see org.eclipse.jface.action.Action#run()
                   */
                  @Override
                  public void run() {
                    factory.action( DropDownExtensionAction.this.userData );
                  }
                };
                action.setText( name );
                action.setImageDescriptor( icon );
                list.add( action );
              }
            } catch( CoreException coreEx ) {
              Activator.logException( coreEx );
            }
          }
        }
      }
    }
    return list.toArray( new Action[ 0 ] );
  }
}
