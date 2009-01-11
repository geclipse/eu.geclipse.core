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

import com.xerox.amazonws.ec2.GroupDescription;

import eu.geclipse.aws.ec2.EC2;
import eu.geclipse.aws.ec2.IEC2;

/**
 * This {@link IOperation} uses the {@link EC2} to interface with the Amazon
 * Webservices. It fetches all security Groups denotes by name or if no group
 * name is specified, every group which can be found.
 * <p>
 * The default security group which is always present is the "default" group.
 * 
 * @author Moritz Post
 */
public class EC2OpDescribeSecurityGroups implements IOperation {

  /** The {@link IEC2} to obtain data from. */
  private final IEC2 ec2Service;

  /** The list of security groups. */
  private List<String> securityGroupList;

  /** The resulting list of {@link GroupDescription}. */
  private List<GroupDescription> result;

  /** Any exception which came up during the inquiry. */
  private Exception exception;

  /**
   * Creates a new {@link EC2OpDescribeSecurityGroups} with no particular group
   * to query for.
   * 
   * @param ec2Service the {@link IEC2} to obtain data from
   */
  public EC2OpDescribeSecurityGroups( final IEC2 ec2Service ) {
    this.ec2Service = ec2Service;
    this.securityGroupList = new ArrayList<String>();
  }

  /**
   * Creates a new {@link EC2OpDescribeSecurityGroups} with the given security
   * groups as parameter. Convenience method for
   * {@link EC2OpDescribeSecurityGroups#EC2OpDescribeSecurityGroups(IEC2, List)}.
   * 
   * @param ec2Service the {@link IEC2} to obtain data from
   * @param securityGroupArray the list of groups to fetch
   */
  public EC2OpDescribeSecurityGroups( final IEC2 ec2Service,
                                      final String[] securityGroupArray )
  {
    this.ec2Service = ec2Service;
    this.securityGroupList = Arrays.asList( securityGroupArray );
  }

  /**
   * Creates a new {@link EC2OpDescribeImagesByExec} with the given security
   * group as parameter.
   * 
   * @param ec2Service the {@link IEC2} to obtain data from
   * @param securityGroupList the list of groups to fetch
   */
  public EC2OpDescribeSecurityGroups( final IEC2 ec2Service,
                                      final List<String> securityGroupList )
  {
    this.ec2Service = ec2Service;
    this.securityGroupList = securityGroupList;
  }

  public void run() {
    this.result = null;
    this.exception = null;
    try {
      this.result = this.ec2Service.describeSecurityGroups( this.securityGroupList );
    } catch( Exception ex ) {
      this.exception = ex;
    }
  }

  public List<GroupDescription> getResult() {
    return this.result;
  }

  public Exception getException() {
    return this.exception;
  }
}
