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
package eu.geclipse.core.filesystem;

import org.eclipse.core.filesystem.IFileStore;

/**
 * Simple bean class representing transfer operation between two file stores.
 *
 */
public class TransferOperation {
  
  private IFileStore source;
  private IFileStore destination;
  private Integer id;
  private String data;
  private long size;
  private boolean move;

  /**
   * Basic constructor which sets up all required fields.
   * @param id transfer id
   * @param source file store from transfer should occur
   * @param destination target file store
   * @param data transfer-specific data
   * @param size size of the transfered file info associated with the file store
   */
  public TransferOperation( final Integer id,
                            final IFileStore source,
                            final IFileStore destination,
                            final String data,
                            final long size ) {
    this.id = id;
    this.source = source;
    this.destination = destination;
    this.data = data;
    this.size = size;
  }
  
  /**
   * @return move status of the transfer operation
   */
  public boolean isMove() {
    return this.move;
  }
  
  /**
   * @param move move status of the transfer operation
   */
  public void setMove( final boolean move ) {
    this.move = move;
  }
  
  public Integer getId() {
    return this.id;
  }
  public String getData() {
    return this.data;
  }

  public IFileStore getDestination() {
    return this.destination;
  }

  public IFileStore getSource() {
    return this.source;
  }
  
  public void setId( final Integer id ) {
    this.id =id;
  }

  public void setData( final String data ) {
    this.data = data;
  }

  public void setDestination( final IFileStore destination ) {
    this.destination = destination;
  }

  public void setSource( final IFileStore source ) {
    this.source = source;
  }
    
  public long getSize() {
    return this.size;
  }
  
  public void setSize( final long size ) {
    this.size = size;
  }
  
}
