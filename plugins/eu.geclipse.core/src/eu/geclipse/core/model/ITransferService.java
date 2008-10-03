/*****************************************************************************
 * Copyright (c) 2008, g-Eclipse Consortium 
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
 *    Szymon Mueller - PSNC - Initial API and implementation
 *****************************************************************************/
package eu.geclipse.core.model;

import org.eclipse.core.runtime.IProgressMonitor;


/**
 * Interface of the transfer operation service
 *
 */
public interface ITransferService {
  
  /**
   * Transfers contents of the source IFileStore to the target IFileStore.
   * 
   * @param operation details of the transfer
   * @param isMove true if this is move operation, false if this is copy operation
   * @param monitor progress monitor
   * @return true if transfer succeeded, false if the transfer failed
   */
  public boolean transfer( final ITransferInformation operation,
                           final boolean isMove, 
                           final IProgressMonitor monitor );
    
  
  
}
