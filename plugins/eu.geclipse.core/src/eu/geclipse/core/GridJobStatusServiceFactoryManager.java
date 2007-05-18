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
 *    Pawel Wolniewicz
 *****************************************************************************/
package eu.geclipse.core;

import java.util.Hashtable;
import java.util.List;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.model.IGridJobStatusServiceFactory;

/**
 * Class fog managin of GridJobStatusServiceFactories. The class gets registered
 * factories from extention point.
 */
public class GridJobStatusServiceFactoryManager {

  static Hashtable<String, IGridJobStatusServiceFactory> factories = new Hashtable<String, IGridJobStatusServiceFactory>();

  /**
   * @param id - The class for which the factory should be found. The class can be 
   * implementation of IGridJobStatusServiceFactory or IGridJobID.
   * @return GridJobStatusServiceFactory capable to get informatino for given GridjobID class
   *  or null if factory cannot be found.
   */
  static public IGridJobStatusServiceFactory getFactory( final Class cl )
  {
    IGridJobStatusServiceFactory factory = null;
    if( factories.containsKey( cl.getName() ) ) {
      factory = factories.get( cl.getName() );
    } 
    return factory;
  }

static{  
  ExtensionManager manager = new ExtensionManager();
  List< IExtension > l = manager.getExtensions( "eu.geclipse.core.gridJobStatusServiceManager");
  for( IExtension extension : l ) {
    IConfigurationElement[] configurationElements = extension.getConfigurationElements();
    IGridJobStatusServiceFactory factory=null;
    for ( int cel = 0 ; cel < configurationElements.length ; cel++ ) {
      String name = configurationElements[ cel ].getName();
      if ( name.equals( "factory" ) ) {
        try {
          factory = ( IGridJobStatusServiceFactory )configurationElements[ cel ].createExecutableExtension( "class" );
          factories.put( factory.getClass().getName(), factory );
        } catch( CoreException e ) {
          Activator.logException( e );
        }
      }
      if ( name.equals( "jobID" ) ) {
        String jobIdClass=configurationElements[ cel ].getAttribute( "class" );
        if(jobIdClass!=null && factory!=null){
          factories.put( jobIdClass, factory );
        }
      }
    }
  }
}
  
}
