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

import java.util.ArrayList;
import java.util.List;

import com.xerox.amazonws.ec2.AddressInfo;

import eu.geclipse.aws.ec2.EC2;
import eu.geclipse.aws.ec2.IEC2;

/**
 * This {@link IOperation} lists the specified allocated addresses or all if no
 * address has been given.
 * 
 * @author Moritz Post
 */
public class EC2OpDescribeAddresses implements IOperation {

  /** The {@link IEC2} to obtain data from. */
  private IEC2 ec2Service;

  /** The resulting list of {@link AddressInfo}. */
  private List<AddressInfo> result;

  /** Any exception which came up during the inquiry. */
  private Exception exception;

  /** The list of addresses to obtain information about. */
  private List<String> addressList;

  /**
   * Create a new {@link IOperation} with the given {@link EC2} as the
   * underlying interfacing infrastructure.
   * 
   * @param ec2 the {@link IEC2} to interface with
   */
  public EC2OpDescribeAddresses( final IEC2 ec2 ) {
    this.ec2Service = ec2;
  }

  /**
   * Create a new {@link IOperation} with the given {@link EC2} as the
   * underlying interfacing infrastructure. The operation lists only the one
   * address given in the <code>address</code> parameter.
   * 
   * @param ec2 the {@link IEC2} to interface with
   * @param address the one address to describe
   */
  public EC2OpDescribeAddresses( final IEC2 ec2, final String address ) {
    this.ec2Service = ec2;
    this.addressList = new ArrayList<String>();
    this.addressList.add( address );
  }

  /**
   * Create a new {@link IOperation} with the given {@link EC2} as the
   * underlying interfacing infrastructure. The operation lists only the
   * addresses given in the <code>addresses</code> parameter.
   * 
   * @param ec2 the {@link IEC2} to interface with
   * @param addresses the addresses to describe
   */
  public EC2OpDescribeAddresses( final IEC2 ec2, final List<String> addresses )
  {
    this.ec2Service = ec2;
    this.addressList = addresses;
  }

  public Exception getException() {
    return this.exception;
  }

  public List<AddressInfo> getResult() {
    return this.result;
  }

  public void run() {
    this.result = null;
    this.exception = null;
    try {
      this.result = this.ec2Service.describeAddresses( this.addressList );
    } catch( Exception ex ) {
      this.exception = ex;
    }
  }

}
