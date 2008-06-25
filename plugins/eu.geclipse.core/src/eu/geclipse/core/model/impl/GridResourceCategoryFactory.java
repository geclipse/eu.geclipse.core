/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
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
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.core.model.impl;

import java.util.Hashtable;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;

import eu.geclipse.core.ExtensionManager;
import eu.geclipse.core.Extensions;
import eu.geclipse.core.internal.model.ResourceCategory;
import eu.geclipse.core.model.IGridResourceCategory;

/**
 * Factory class for resource categories. The class looks up all extensions
 * of the eu.geclipse.core.gridResourceCategory extension point for possible
 * implementations.
 */
public class GridResourceCategoryFactory {
  
  /**
   * Unique ID for the default applications category as defined by the g-Eclipse core plug-in.
   */
  public static final String ID_APPLICATIONS = "eu.geclipse.core.model.category.applications"; //$NON-NLS-1$
  
  /**
   * Unique ID for the default computing category as defined by the g-Eclipse core plug-in.
   */
  public static final String ID_COMPUTING = "eu.geclipse.core.model.category.computing"; //$NON-NLS-1$
  
  /**
   * Unique ID for the default services category as defined by the g-Eclipse core plug-in.
   */
  public static final String ID_SERVICES = "eu.geclipse.core.model.category.services"; //$NON-NLS-1$
  
  /**
   * Unique ID for the default data services category as defined by the g-Eclipse core plug-in.
   */
  public static final String ID_DATA_SERVICES = "eu.geclipse.core.model.category.services.data"; //$NON-NLS-1$
  
  /**
   * Unique ID for the default info services category as defined by the g-Eclipse core plug-in.
   */
  public static final String ID_INFO_SERVICES = "eu.geclipse.core.model.category.services.info"; //$NON-NLS-1$
  
  /**
   * Unique ID for the default job services category as defined by the g-Eclipse core plug-in.
   */
  public static final String ID_JOB_SERVICES = "eu.geclipse.core.model.category.services.jobs"; //$NON-NLS-1$
  
  /**
   * Unique ID for the default other services category as defined by the g-Eclipse core plug-in.
   */
  public static final String ID_OTHER_SERVICES = "eu.geclipse.core.model.category.services.others"; //$NON-NLS-1$
  
  /**
   * Unique ID for the default storage category as defined by the g-Eclipse core plug-in.
   */
  public static final String ID_STORAGE = "eu.geclipse.core.model.category.storage"; //$NON-NLS-1$
  
  /**
   * Internal hashtable used to manage already loaded categories.
   */
  private static Hashtable< String, IGridResourceCategory > categories
    = new Hashtable< String, IGridResourceCategory >();
  
  /**
   * Internal extension manager used to look up for implementations.
   */
  private static ExtensionManager manager
    = new ExtensionManager();

  /**
   * Get the resource category with the specified ID or <code>null</code>
   * if no such category could be found.
   * 
   * @param id The ID of the category.
   * @return An implementation of {@link IGridResourceCategory} filled with the
   * information from the corresponding extension or <code>null</code> if no
   * category with the specified ID could be found.
   */
  public static IGridResourceCategory getCategory( final String id ) {
    
    IGridResourceCategory result = categories.get( id );
    
    if ( result == null ) {
      List< IConfigurationElement > elements
        = manager.getConfigurationElements( Extensions.GRID_RESOURCE_CATEGORY_POINT, Extensions.GRID_RESOURCE_CATEGORY_ELEMENT );
      for ( IConfigurationElement element : elements ) {
        String extid = element.getAttribute( Extensions.GRID_RESOURCE_CATEGORY_ID_ATTRIBUTE );
        if ( id.equals( extid ) ) {
          
          String name = element.getAttribute( Extensions.GRID_RESOURCE_CATEGORY_NAME_ATTRIBUTE );
          String parent = element.getAttribute( Extensions.GRID_RESOURCE_CATEGORY_PARENT_ATTRIBUTE );
          String active = element.getAttribute( Extensions.GRID_RESOURCE_CATEGORY_ACTIVE_ATTRIBUTE );
          
          IGridResourceCategory parentCategory = null;
          if ( parent != null ) {
            parentCategory = getCategory( parent );
          }
          
          boolean isActive = true;
          if ( active != null ) {
            isActive = Boolean.parseBoolean( active );
          }
          
          result = new ResourceCategory( name, parentCategory, isActive );
          categories.put( id, result );
          
        }
      }
    }
    
    return result;
    
  }

}
