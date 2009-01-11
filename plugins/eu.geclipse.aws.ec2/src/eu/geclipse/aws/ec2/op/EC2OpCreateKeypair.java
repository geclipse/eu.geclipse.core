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

import com.xerox.amazonws.ec2.AvailabilityZone;
import com.xerox.amazonws.ec2.KeyPairInfo;

import eu.geclipse.aws.ec2.IEC2;

/**
 * This operation creates a new keypair identified by the provided name. The
 * result of type {@link KeyPairInfo} holds the private key which can be used to
 * log in to an EC2 instance launched with that keypair.
 * 
 * @author Moritz Post
 */
public class EC2OpCreateKeypair implements IOperation {

  /** The {@link IEC2} to obtain data from. */
  private final IEC2 ec2Service;

  /** The name of the keypair to create. */
  private String keypairName;

  /** The resulting list of {@link AvailabilityZone}s. */
  private KeyPairInfo result;

  /** Any exception which came up during the inquiry. */
  private Exception exception;

  /**
   * Create a new {@link EC2OpCreateKeypair} via the given {@link IEC2} and
   * using the provided keypair name.
   * 
   * @param ec2 the interface to access the EC2 infrastructure
   * @param keypairName the name of the keypair to create
   */
  public EC2OpCreateKeypair( final IEC2 ec2, final String keypairName ) {
    this.ec2Service = ec2;
    this.keypairName = keypairName;
  }

  public void run() {
    this.result = null;
    this.exception = null;
    try {
      this.result = this.ec2Service.createKeypair( this.keypairName );
    } catch( Exception ex ) {
      this.exception = ex;
    }
  }

  public Exception getException() {
    return this.exception;
  }

  public KeyPairInfo getResult() {
    return this.result;
  }

}
