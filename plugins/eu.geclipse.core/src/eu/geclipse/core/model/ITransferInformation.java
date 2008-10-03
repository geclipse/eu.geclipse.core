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


/**
 * Class representing transfer operation. Has information about the source of the
 * transfer and it's destination
 */
public interface ITransferInformation {
  
  /**
   * Transfer-specific information which should be stored and used for transfer
   * resuming.
   * @return Transfer-specific token
   */
  public String getData();
  
  /**
   * Returns EFS for the source of transfer
   * @return EFS of the source
   */
  public IFileStore getSource();
  
  /**
   * Return EFS for the destination of transfer 
   * @return EFS of the target
   */
  public IFileStore getDestination();
  
  /**
   * Specifies if the transfer was move or copy operation.
   * @return true if transfer presents move operation,
   * false if transfer is not a move operation
   */
  public boolean isMove();
  
  /**
   * Getter of the internal transfer number
   * @return unique, internal id of the transfer
   */
  public Integer getId();
  
  /**
   * Getter of the size of the transfer
   * @return size of the transfered file, or 0 if the transfer object is a folder.
   */
  public long getSize();
}
