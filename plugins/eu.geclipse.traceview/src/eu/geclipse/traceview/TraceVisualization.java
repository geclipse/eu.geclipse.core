/*****************************************************************************
 * Copyright (c) 2006, 2008 g-Eclipse Consortium 
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
 *    Christof Klausecker GUP, JKU - initial API and implementation
 *****************************************************************************/

package eu.geclipse.traceview;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * Abstract Class for a Trace Visualization
 */
abstract public class TraceVisualization extends Composite {

  /**
   * Constructor for TraceVisualization
   * 
   * @param parent
   * @param style
   */
  public TraceVisualization( final Composite parent, final int style ) {
    super( parent, style | SWT.DOUBLE_BUFFERED );
  }

  /**
   * Returns the items that will be added to the toolbar
   * 
   * @return toolbar items
   */
  abstract public IContributionItem[] getToolBarItems();

  /**
   * Returns the Trace
   * 
   * @return trace
   */
  abstract public ITrace getTrace();
}
