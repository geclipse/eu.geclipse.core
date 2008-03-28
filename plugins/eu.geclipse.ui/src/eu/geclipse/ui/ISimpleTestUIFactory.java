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

import org.eclipse.swt.widgets.Shell;

import eu.geclipse.core.model.IGridResource;
import eu.geclipse.core.simpleTest.ISimpleTest;
import eu.geclipse.core.simpleTest.ISimpleTestDescription;
import eu.geclipse.ui.dialogs.AbstractSimpleTestDialog;

/**
 * An <code>ISimpleTestUIFactory</code> is responsible to provide
 * UI elements for handling a certain kind of simple test.
 * 
 * @author harald
 *
 */
public interface ISimpleTestUIFactory {

  /**
   * Get an dialog that is used to present the user with how to 
   * perform the simple test.
   * 
   * @param test The test from which to show the dialog.
   * @param resources The resources that this test should be applied to.
   * @param parentShell The parent shell for the dialog.
   * @return An {@link AbstractSimpleTestDialog} that is dedicated
   * to a certain type of test.
   */
  public AbstractSimpleTestDialog getSimpleTestDialog( final ISimpleTest test, 
                                                       final List< IGridResource > resources, 
                                                       final Shell parentShell );

  /**
   * Get an {@link ISimpleTestDescription} that represents the tests
   * and descriptions that are supported by this factory.
   * 
   * @return An simple test description describing the supported tests.
   */
  public ISimpleTestDescription getSupportedDescription();
}
