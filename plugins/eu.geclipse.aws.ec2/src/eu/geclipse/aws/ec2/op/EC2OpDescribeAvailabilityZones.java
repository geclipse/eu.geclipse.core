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

import com.xerox.amazonws.ec2.AvailabilityZone;

import eu.geclipse.aws.ec2.EC2;
import eu.geclipse.aws.ec2.IEC2;

/**
 * This {@link IOperation} uses the {@link EC2} to interface with the Amazon
 * Webservices. It fetches all availability zones specified by name or if no
 * zone name is given, every zone which can be found.
 * 
 * @author Moritz Post
 */
public class EC2OpDescribeAvailabilityZones implements IOperation {

  /** The {@link IEC2} to obtain data from. */
  private final IEC2 ec2Service;

  /** The list of availability zones. */
  private List<String> availabilityZonesList;

  /** The resulting list of {@link AvailabilityZone}s. */
  private List<AvailabilityZone> result;

  /** Any exception which came up during the inquiry. */
  private Exception exception;

  /**
   * Creates a new {@link EC2OpDescribeAvailabilityZones} with no particular
   * zone to query for.
   * 
   * @param ec2Service the {@link IEC2} to obtain data from
   */
  public EC2OpDescribeAvailabilityZones( final IEC2 ec2Service ) {
    this.ec2Service = ec2Service;
    this.availabilityZonesList = new ArrayList<String>();
  }

  /**
   * Creates a new {@link EC2OpDescribeAvailabilityZones} with the given
   * availability zones as parameter. Convenience method for
   * {@link EC2OpDescribeAvailabilityZones#EC2OpDescribeAvailabilityZones(IEC2, List)}.
   * 
   * @param availabilityZonesArray the list of groups to fetch
   * @param ec2Service the {@link IEC2} to obtain data from
   */
  public EC2OpDescribeAvailabilityZones( final IEC2 ec2Service,
                                         final String[] availabilityZonesArray )
  {
    this.ec2Service = ec2Service;
    this.availabilityZonesList = Arrays.asList( availabilityZonesArray );
  }

  /**
   * Creates a new {@link EC2OpDescribeAvailabilityZones} with the given
   * availability zones as parameter.
   * 
   * @param availabilityZonesList the list of zones to fetch
   * @param ec2Service the {@link IEC2} to obtain data from
   */
  public EC2OpDescribeAvailabilityZones( final IEC2 ec2Service,
                                         final List<String> availabilityZonesList )
  {
    this.ec2Service = ec2Service;
    this.availabilityZonesList = availabilityZonesList;
  }

  public void run() {
    this.result = null;
    this.exception = null;
    try {
      this.result = this.ec2Service.describeAvailabilityZones( this.availabilityZonesList );
    } catch( Exception ex ) {
      this.exception = ex;
    }
  }

  public List<AvailabilityZone> getResult() {
    return this.result;
  }

  public Exception getException() {
    return this.exception;
  }
}
