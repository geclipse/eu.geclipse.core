/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
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
 *    Moritz Post - initial API and implementation
 *****************************************************************************/

package eu.geclipse.aws.ec2.op;

import eu.geclipse.aws.ec2.IEC2;

/**
 * This {@link IOperation} allocates a new Elastic IP. The obtained address is
 * assigned to the current EC2 account.
 * 
 * @author Moritz Post
 */
public class EC2OpAllocateAddress implements IOperation {

  /** The {@link IEC2} to obtain data from. */
  private final IEC2 ec2Service;

  /** The resulting IP address of the allocation operation. */
  private String result;

  /** The exception containing any problems during the operation. */
  private Exception exception;

  /**
   * Creates a new {@link EC2OpAllocateAddress} object using the provided EC2
   * as its interfacing instance.
   * 
   * @param ec2 the gateway to the EC2 service
   */
  public EC2OpAllocateAddress( final IEC2 ec2 ) {
    this.ec2Service = ec2;
  }

  public Exception getException() {
    return this.exception;
  }

  public String getResult() {
    return this.result;
  }

  public void run() {
    this.result = null;
    this.exception = null;
    try {
      this.result = this.ec2Service.allocateAddress();
    } catch( Exception ex ) {
      this.exception = ex;
    }

  }

}
