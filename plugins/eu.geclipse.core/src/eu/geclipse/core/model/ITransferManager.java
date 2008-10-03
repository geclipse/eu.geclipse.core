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

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.IProgressMonitor;


/**
 * Interface class for the transfer manager
 *
 */
public interface ITransferManager {
  
  /**
   * Main method doing transfer from source IFileStore to destination
   * IFileStore.
   * 
   * @param source IFileStore from which transfer starts
   * @param destination IFileStore which is target of the transfer
   * @param moveFlag flag representing move operation
   * @param monitor progress monitor
   * @return true if transfer succeeded, false if the transfer failed
   */
  public boolean startTransfer( final IFileStore source, 
                             final IFileStore destination,
                             final boolean moveFlag,
                             final IProgressMonitor monitor );
  
  /**
   * Method used to resume transfer described by <code>transfer</code> parameter.
   * 
   * @param transfer Informations about the transfer to be resumed
   * @param monitor progress monitor
   * @return true if transfer resuming was successful
   */
  public boolean resumeTransfer( final ITransferInformation transfer,
                                 final IProgressMonitor monitor );
  
  /**
   * Removes transfer with the specified ID.
   * @param transferID
   */
  public void unregisterTransfer( final Integer transferID );
  
}
