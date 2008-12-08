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
 *     PSNC: 
 *      - Szymon Mueller
 *****************************************************************************/
package eu.geclipse.core.model;


/**
 * Status listener for service jobs. This status listener will be notified when 
 * status of the service job changes.
 *
 */
public interface IServiceJobStatusListener {
  
  /**
   * Method invoked when status of the service job changes.
   * 
   * @param serviceJob {@link IServiceJob} for which status has changed.
   */
  public void statusChanged ( IServiceJob serviceJob );
  
}
