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

import java.util.List;

import com.xerox.amazonws.ec2.KeyPairInfo;

import eu.geclipse.aws.ec2.IEC2;

/**
 * The {@link EC2OpDescribeKeypairs} queries the EC2 service to provide
 * information about registered keys.
 * 
 * @author Moritz Post
 */
public class EC2OpDescribeKeypairs implements IOperation {

  /** The {@link IEC2} to obtain data from. */
  private final IEC2 ec2Service;

  /** The list of keypairs to fetch information about. Can be empty. */
  private List<String> keypairs;

  /** The result list of {@link KeyPairInfo} instances. */
  private List<KeyPairInfo> result;

  /** The {@link Exception} which might have been thrown. */
  private Exception exception;

  /**
   * The constructor specifies the {@link IEC2} to use.
   * 
   * @param ec2Service the {@link IEC2} to use when obtaining the keypairs
   */
  public EC2OpDescribeKeypairs( final IEC2 ec2Service ) {
    this.ec2Service = ec2Service;
  }

  public void run() {
    this.result = null;
    this.exception = null;
    try {
      this.result = this.ec2Service.describeKeypairs( this.keypairs );
    } catch( Exception ex ) {
      this.exception = ex;
    }
  }

  public List<KeyPairInfo> getResult() {
    return this.result;
  }

  public Exception getException() {
    return this.exception;
  }

}
