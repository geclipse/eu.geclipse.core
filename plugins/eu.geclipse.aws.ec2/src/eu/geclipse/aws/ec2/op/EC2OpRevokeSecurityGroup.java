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

import eu.geclipse.aws.ec2.EC2;
import eu.geclipse.aws.ec2.EC2ServiceException;
import eu.geclipse.aws.ec2.IEC2;

/**
 * This {@link IOperation} uses the {@link EC2} to interface with the Amazon
 * Webservices. It revokes an access permission rule to a given security group.
 * 
 * @author Moritz Post
 */
public class EC2OpRevokeSecurityGroup implements IOperation {

  /** The {@link IEC2} to obtain data from. */
  private final IEC2 ec2Service;

  /** Any exception which came up during the inquiry. */
  private Exception exception;

  /** The security group to revoke permissions from. */
  private String groupName;

  /** The ip protocol to revoke access from. */
  private String ipProtocol;

  /** The start of the port range to revoke. */
  private int fromPort;

  /** The end of the port range to revoke. */
  private int toPort;

  /** The CIDR based ip to revoke access from. */
  private String cidrIp;

  /** The group name of the user group. */
  private String secGroupName;

  /** The account id of the account from which to revoke access permissions. */
  private String secGroupOwnerId;

  /**
   * Revokes access rights for security group.
   * 
   * @param ec2Service the {@link IEC2} to obtain data from *
   * @param groupName name of group to modify
   * @param ipProtocol protocol to authorize (tcp, udp, icmp)
   * @param fromPort bottom of port range to authorize
   * @param toPort top of port range to authorize
   * @param cidrIp CIDR IP range to authorize (i.e. 0.0.0.0/0)
   */
  public EC2OpRevokeSecurityGroup( final IEC2 ec2Service,
                                   final String groupName,
                                   final String cidrIp,
                                   final String ipProtocol,
                                   final int fromPort,
                                   final int toPort )
  {
    this.ec2Service = ec2Service;
    this.groupName = groupName;
    this.ipProtocol = ipProtocol;
    this.fromPort = fromPort;
    this.toPort = toPort;
    this.cidrIp = cidrIp;
  }

  /**
   * Revokes permissions to a security group.
   * 
   * @param ec2Service the {@link IEC2} to obtain data from
   * @param groupName name of group to modify
   * @param secGroupName name of security group to authorize access to
   * @param secGroupOwnerId owner of security group to authorize access to
   * @throws EC2ServiceException wraps checked exceptions
   */
  public EC2OpRevokeSecurityGroup( final IEC2 ec2Service,
                                   final String groupName,
                                   final String secGroupName,
                                   final String secGroupOwnerId )
  {
    this.ec2Service = ec2Service;
    this.groupName = groupName;
    this.secGroupName = secGroupName;
    this.secGroupOwnerId = secGroupOwnerId;
  }

  public void run() {
    this.exception = null;
    try {
      if( this.secGroupOwnerId != null ) {
        this.ec2Service.revokeSecurityGroup( this.groupName,
                                             this.secGroupName,
                                             this.secGroupOwnerId );
      } else {
        this.ec2Service.revokeSecurityGroup( this.groupName,
                                             this.cidrIp,
                                             this.ipProtocol,
                                             this.fromPort,
                                             this.toPort );
      }
    } catch( Exception ex ) {
      this.exception = ex;
    }
  }

  public List<Object> getResult() {
    return null;
  }

  public Exception getException() {
    return this.exception;
  }
}
