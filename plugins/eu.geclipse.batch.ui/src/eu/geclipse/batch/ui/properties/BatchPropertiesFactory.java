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
 *     UCY (http://www.cs.ucy.ac.cy)
 *      - Harald Gjermundrod (harald@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.batch.ui.properties;

import java.util.ArrayList;
import java.util.List;

import eu.geclipse.batch.BatchConnectionInfo;
import eu.geclipse.batch.BatchQueueDescription;
import eu.geclipse.ui.properties.AbstractPropertySource;
import eu.geclipse.ui.properties.IPropertiesFactory;


/**
 * @author harald
 *
 */
public class BatchPropertiesFactory implements IPropertiesFactory {

  /* (non-Javadoc)
   * @see eu.geclipse.ui.properties.IPropertiesFactory#getPropertySources(java.lang.Object)
   */
  public List<AbstractPropertySource<?>> getPropertySources( final Object sourceObject ) {
    List<AbstractPropertySource<?>> sourcesList = new ArrayList<AbstractPropertySource<?>>(); 
    
    if ( sourceObject instanceof BatchConnectionInfo ) {
       sourcesList.add( new BatchPropertySource( ( BatchConnectionInfo ) sourceObject ) );
    }
    else if( sourceObject instanceof BatchQueueDescription ) {
      sourcesList.add( new BatchQueueDescriptionPropertySource( ( BatchQueueDescription ) sourceObject ) );
    }

    return sourcesList; 
  }
}
