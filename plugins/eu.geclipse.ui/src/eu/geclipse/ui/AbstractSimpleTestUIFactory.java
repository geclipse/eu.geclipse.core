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
package eu.geclipse.ui;

import java.util.List;

import eu.geclipse.core.simpleTest.ISimpleTest;
import eu.geclipse.core.simpleTest.ISimpleTestDescription;

/**
 * Abstract implementation of the {@link ISimpleTestUIFactory} that provides methods
 * for finding extensions of the simpleTest ui factory mechanism.
 * 
 */
public abstract class AbstractSimpleTestUIFactory implements ISimpleTestUIFactory {

  /**
   * Find an {@link ISimpleTestUIFactory} that is able to provide UI elements for the
   * specified simple test.
   * 
   * @param test The test to provide UI elements for.
   * @return An simple test manager that is capable to provide UI elements
   * for the specified test.
   */
  public static ISimpleTestUIFactory findFactory( final ISimpleTest test ) {
    ISimpleTestDescription description = test.getDescription();
    return findFactory( description );
  }  
  /**
   * Find an {@link ISimpleTestUIFactory} that is able to provide UI elements for
   * tests described by the specified simple test description.
   * 
   * @param description The simple test description that describes tests to provide UI elements for.
   * @return An simple test manager that is capable to provide UI elements
   * for test describes by the the specified test description.
   */
  public static ISimpleTestUIFactory findFactory( final ISimpleTestDescription description ) {
    ISimpleTestUIFactory resultFactory = null;
    List< ISimpleTestUIFactory > factories = Extensions.getRegisteredSimpleTestUIFactories();
    for ( ISimpleTestUIFactory factory : factories ) {
      ISimpleTestDescription desc = factory.getSupportedDescription();
      if ( desc.getClass().equals( description.getClass() ) ) {
        resultFactory = factory;
        break;
      }
    }
    return resultFactory;
  }
}
