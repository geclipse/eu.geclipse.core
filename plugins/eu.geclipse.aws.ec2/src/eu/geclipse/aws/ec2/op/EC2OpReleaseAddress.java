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
 * This {@link IOperation} releases an elastic IP from the pool of currently
 * held elastic IPs.
 * 
 * @author Moritz Post
 */
public class EC2OpReleaseAddress implements IOperation {

  /**
   * Creates the new {@link IOperation} with the given {@link IEC2} as the
   * interfacing gateway and the elastic IP as the address to release.
   * 
   * @param ec2 the gateway to ec2
   * @param elasticIP the IP address to release
   */
  public EC2OpReleaseAddress( final IEC2 ec2, final String elasticIP ) {
    this.ec2Service = ec2;
    this.elasticIP = elasticIP;
  }

  /** The {@link IEC2} to obtain data from. */
  private IEC2 ec2Service;

  /** Any exception which came up during the inquiry. */
  private Exception exception;

  /** An IP address identifying the Elastic IP */
  private String elasticIP;

  public void run() {
    this.exception = null;
    try {
      this.ec2Service.releaseAddress( this.elasticIP );
    } catch( Exception ex ) {
      this.exception = ex;
    }
  }

  public Exception getException() {
    return this.exception;
  }

  /**
   * This {@link IOperation} does not provide a return value.
   */
  public Object getResult() {
    return null;
  }

}
