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
 * This {@link IOperation} associates a given ec2 instance with a specified
 * elastic IP address.
 * 
 * @author Moritz Post
 */
public class EC2OpAssociateAddress implements IOperation {

  /** The {@link IEC2} to obtain data from. */
  private IEC2 ec2Service;

  /** Any exception which came up during the inquiry. */
  private Exception exception;

  /** An IP address identifying the Elastic IP */
  private String elasticIP;

  /** An id of a running EC2 instance. */
  private String instanceId;

  /**
   * Creates a new {@link EC2OpAssociateAddress} object which is capable to
   * associate the given instance id with the passed elastic IP via the
   * {@link IEC2} instance.
   * 
   * @param ec2 the interfacing instance
   * @param elasticIP the elastic IP to bind to an instance
   * @param instanceId the instance to bind the elastic IP to
   */
  public EC2OpAssociateAddress( final IEC2 ec2,
                                final String elasticIP,
                                final String instanceId )
  {
    this.ec2Service = ec2;
    this.elasticIP = elasticIP;
    this.instanceId = instanceId;
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

  public void run() {
    this.exception = null;
    try {
      this.ec2Service.associateAddress( this.instanceId, this.elasticIP );
    } catch( Exception ex ) {
      this.exception = ex;
    }
  }

}
