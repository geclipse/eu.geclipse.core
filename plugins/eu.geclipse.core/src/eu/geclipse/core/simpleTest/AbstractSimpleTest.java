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
package eu.geclipse.core.simpleTest;

import java.util.List;

import eu.geclipse.core.model.IGridResource;

/**
 * Abstract implementation of the {@link ISimpleTest} interface.
 * 
 * @author harald
 *
 */
public abstract class AbstractSimpleTest implements ISimpleTest {
  /**
   * The simple test description that was used to initialize this test.
   */
  private ISimpleTestDescription description;

  /**
   * Construct a new <code>AbstractSimpleTest</code> from the
   * specified {@link ISimpleTestDescription}.
   * 
   * @param description The {@link ISimpleTestDescription} from which
   *                    this test should be created.
   */
  public AbstractSimpleTest( final ISimpleTestDescription description ) {
    this.description = description;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.simpleTest.ISimpleTest#getDescription()
   */
  public ISimpleTestDescription getDescription() {
    return this.description;
  }
  
}
