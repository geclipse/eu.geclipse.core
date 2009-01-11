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
import java.util.Arrays;
import java.util.List;

import com.xerox.amazonws.ec2.ReservationDescription;

import eu.geclipse.aws.ec2.EC2;
import eu.geclipse.aws.ec2.IEC2;

/**
 * This {@link IOperation} uses the {@link EC2} to interface with the Amazon
 * Webservices. It fetches all currently running instances or the ones specified
 * by instance id.
 * 
 * @author Moritz Post
 */
public class EC2OpDescribeInstances implements IOperation {

  /** The {@link IEC2} to obtain data from. */
  private final IEC2 ec2Service;

  /** The list of availability instances. */
  private List<String> instanceList;

  /** The resulting list of {@link ReservationDescription}s. */
  private List<ReservationDescription> result;

  /** Any exception which came up during the inquiry. */
  private Exception exception;

  /**
   * Creates a new {@link EC2OpDescribeInstances} with no particular instnace to
   * query for.
   * 
   * @param ec2Service the {@link IEC2} to obtain data from
   */
  public EC2OpDescribeInstances( final IEC2 ec2Service ) {
    this.ec2Service = ec2Service;
    this.instanceList = new ArrayList<String>();
  }

  /**
   * Creates a new {@link EC2OpDescribeInstances} with the given instances as
   * parameter. Convenience method for
   * {@link EC2OpDescribeInstances#EC2OpDescribeInstances(IEC2, List)}.
   * 
   * @param instanceArray the list of groups to fetch
   * @param ec2Service the {@link IEC2} to obtain data from
   */
  public EC2OpDescribeInstances( final IEC2 ec2Service,
                                 final String[] instanceArray )
  {
    this.ec2Service = ec2Service;
    this.instanceList = Arrays.asList( instanceArray );
  }

  /**
   * Creates a new {@link EC2OpDescribeInstances} with the given instances as
   * parameter.
   * 
   * @param instanceList the list of instances to fetch
   * @param ec2Service the {@link IEC2} to obtain data from
   */
  public EC2OpDescribeInstances( final IEC2 ec2Service,
                                 final List<String> instanceList )
  {
    this.ec2Service = ec2Service;
    this.instanceList = instanceList;
  }

  public void run() {
    this.result = null;
    this.exception = null;
    try {
      this.result = this.ec2Service.describeInstances( this.instanceList );
    } catch( Exception ex ) {
      this.exception = ex;
    }
  }

  public List<ReservationDescription> getResult() {
    return this.result;
  }

  public Exception getException() {
    return this.exception;
  }
}
