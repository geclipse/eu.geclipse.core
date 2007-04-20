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
 *     PSNC - Mariusz Wojtysiak
 *           
 *****************************************************************************/
package eu.geclipse.jsdl.properties;

import java.util.ArrayList;
import java.util.List;
import eu.geclipse.core.model.impl.JSDLJobDescription;
import eu.geclipse.ui.properties.AbstractPropertySource;
import eu.geclipse.ui.properties.IPropertiesFactory;


/**
 * Properties factory extending extension-point for objects {@link JSDLJobDescription} 
 */
public class PropertiesFactory  implements IPropertiesFactory {

  public List<AbstractPropertySource<?>> getPropertySources( final Object sourceObject ) {
    List<AbstractPropertySource<?>> propertySources = new ArrayList<AbstractPropertySource<?>>();
    
    if( sourceObject instanceof JSDLJobDescription ) {
      propertySources.add( new JsdlJobDescSource( ( JSDLJobDescription ) sourceObject ) );
    }
    
    return propertySources;
  }
}
