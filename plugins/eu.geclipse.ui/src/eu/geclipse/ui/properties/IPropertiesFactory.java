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
package eu.geclipse.ui.properties;

import java.util.List;


/**
 * Factory, which creates and returns {@link AbstractPropertySource} objects, for object
 */
public interface IPropertiesFactory {
  /**
   * @param sourceObject object, for which properties will be shown  
   * @return list of property sources, which handles properties for sourceObject
   */
  List<AbstractPropertySource<?>> getPropertySources( final Object sourceObject );
}
