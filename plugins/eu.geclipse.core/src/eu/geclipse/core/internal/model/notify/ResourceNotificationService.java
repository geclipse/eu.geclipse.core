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
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.core.internal.model.notify;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.internal.model.GridRoot;
import eu.geclipse.core.model.GridModel;

/**
 * This class tracks changes in the Eclipse resource tree and
 * processes them using the {@link GridElementLifecycleManager}.
 */
public class ResourceNotificationService
    implements IResourceChangeListener {
  
  private static ResourceNotificationService instance; 
  
  private ResourceNotificationService() {
    ResourcesPlugin.getWorkspace().addResourceChangeListener( this );
  }
  
  /**
   * Get the singleton of this class.
   * 
   * @return The singleton instance.
   */
  public static ResourceNotificationService getInstance() {
    
    if ( instance == null ) {
      instance = new ResourceNotificationService();
    }
    
    return instance;
    
  }
  
  public void resourceChanged( final IResourceChangeEvent event ) {
    
    IResourceDelta delta = event.getDelta();
    
    if ( delta != null ) {
    
      GridNotificationService gridNotificationService
        = GridRoot.getGridNotificationService();
      
      gridNotificationService.lock( GridModel.getRoot() );
      
      GridElementLifecycleManager visitor = new GridElementLifecycleManager();
      
      try {
        delta.accept( visitor );
      } catch ( CoreException cExc ) {
        Activator.logException( cExc );
      }
      
      gridNotificationService.unlock( GridModel.getRoot() );
      
    }
    
  }

}
