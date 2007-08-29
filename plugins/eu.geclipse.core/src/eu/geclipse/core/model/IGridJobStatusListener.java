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
 *    Pawel Wolniewicz - Some improvements
 *****************************************************************************/
package eu.geclipse.core.model;

/**
 * Interface for listener called when job status is updated
 */
public interface IGridJobStatusListener {

  /**
   * Called when job status is changed. Warning! Can be called from working
   * thread, so please from statusChanged() don't call SWT methods directly. 
   * Rather use:
   * use: 
   *    display.syncExec ( new Runnable () { 
   *        public void run () { } 
   *        } );
   * 
   * @param job
   */
  public void statusChanged( IGridJob job );
  
}
