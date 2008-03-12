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

package eu.geclipse.ui.internal;

import org.eclipse.jface.viewers.ISelection;

/**
 * Helper class that simply represents an empty {@link ISelection}.
 */
public class EmptySelection implements ISelection {
  
  /**
   * Create a new empty selection. 
   */
  public EmptySelection() {
    // empty implementation
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.ISelection#isEmpty()
   */
  public boolean isEmpty() {
    return true;
  }
  
}