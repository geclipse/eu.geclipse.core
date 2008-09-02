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
package eu.geclipse.ui.simpleTest;

import java.util.List;

import org.eclipse.swt.widgets.Shell;

import eu.geclipse.core.model.IGridResource;
import eu.geclipse.core.simpleTest.ISimpleTest;
import eu.geclipse.core.simpleTest.ISimpleTestDescription;
import eu.geclipse.core.simpleTest.ReachabilityTestDescription;
import eu.geclipse.ui.AbstractSimpleTestUIFactory;
import eu.geclipse.ui.dialogs.AbstractSimpleTestDialog;

/**
 * This UI factory is dedicated to ping test and provides the ui elements
 * that are dealing with this test.
 * 
 * @author harald
 *
 */
public class ReachabilityTestUIFactory extends AbstractSimpleTestUIFactory {

  public AbstractSimpleTestDialog getSimpleTestDialog( final ISimpleTest test, 
                                                       final List< IGridResource > resources, 
                                                       final Shell parentShell ) {
    ReachabilityTestDialog dialog = new ReachabilityTestDialog( test, resources, parentShell );
    return dialog;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.ui.ISimpleTestUIFactory#getSupportedDescription()
   */
  public ISimpleTestDescription getSupportedDescription() {
    return new ReachabilityTestDescription();
  }

}
