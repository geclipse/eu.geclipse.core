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
 * Webservices. It adds an access permission rule to a given security group.
 * 
 * @author Moritz Post
 */
public class EC2OpAuthorizeSecurityGroup implements IOperation {

  /** The {@link IEC2} to obtain data from. */
  private final IEC2 ec2Service;

  /** Any exception which came up during the inquiry. */
  private Exception exception;

  /** The security group to add permissions to. */
  private String groupName;

  /** The ip protocol to grant access to. */
  private String ipProtocol;

  /** The start of the port range to open. */
  private int fromPort;

  /** The end of the port range to open. */
  private int toPort;

  /** The CIDR based ip to grant access. */
  private String cidrIp;

  /**
   * When using group based permission, this is the security group to grant
   * access to.
   */
  private String secGroupName;

  /**
   * When using group based permissions, this is the account id to grant access
   * to.
   */
  private String secGroupOwnerId;

  /**
   * Creates a new {@link EC2OpAuthorizeSecurityGroup} with no particular
   * security group to authorize.
   * 
   * @param ec2Service the {@link IEC2} to obtain data from
   */
  public EC2OpAuthorizeSecurityGroup( final IEC2 ec2Service ) {
    this.ec2Service = ec2Service;
  }

  /**
   * Creates a new {@link EC2OpAuthorizeSecurityGroup} with the given security
   * group as parameter.
   * 
   * @param ec2Service the {@link IEC2} to obtain data from
   * @param groupName name of group to modify
   * @param ipProtocol protocol to authorize (tcp, udp, icmp)
   * @param fromPort bottom of port range to authorize
   * @param toPort top of port range to authorize
   * @param cidrIp CIDR IP range to authorize (i.e. 0.0.0.0/0)
   */
  public EC2OpAuthorizeSecurityGroup( final IEC2 ec2Service,
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
   * Adds incoming permissions to a security group.
   * 
   * @param ec2Service the {@link IEC2} to obtain data from
   * @param groupName name of group to modify
   * @param secGroupName name of security group to authorize access to
   * @param secGroupOwnerId owner of security group to authorize access to
   * @throws EC2ServiceException wraps checked exceptions
   */
  public EC2OpAuthorizeSecurityGroup( final IEC2 ec2Service,
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
        this.ec2Service.authorizeSecurityGroup( this.groupName,
                                                this.secGroupName,
                                                this.secGroupOwnerId );
      } else {
        this.ec2Service.authorizeSecurityGroup( this.groupName,
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

  /**
   * @return the groupName
   */
  public String getGroupName() {
    return this.groupName;
  }

  /**
   * @param groupName the groupName to set
   */
  public void setGroupName( final String groupName ) {
    this.groupName = groupName;
  }

  /**
   * @return the ipProtocol
   */
  public String getIpProtocol() {
    return this.ipProtocol;
  }

  /**
   * @param ipProtocol the ipProtocol to set
   */
  public void setIpProtocol( final String ipProtocol ) {
    this.ipProtocol = ipProtocol;
  }

  /**
   * @return the fromPort
   */
  public int getFromPort() {
    return this.fromPort;
  }

  /**
   * @param fromPort the fromPort to set
   */
  public void setFromPort( final int fromPort ) {
    this.fromPort = fromPort;
  }

  /**
   * @return the toPort
   */
  public int getToPort() {
    return this.toPort;
  }

  /**
   * @param toPort the toPort to set
   */
  public void setToPort( final int toPort ) {
    this.toPort = toPort;
  }

  /**
   * @return the cidrIp
   */
  public String getCidrIp() {
    return this.cidrIp;
  }

  /**
   * @param cidrIp the cidrIp to set
   */
  public void setCidrIp( final String cidrIp ) {
    this.cidrIp = cidrIp;
  }

  /**
   * @return the secGroupName
   */
  public String getSecGroupName() {
    return this.secGroupName;
  }

  /**
   * @param secGroupName the secGroupName to set
   */
  public void setSecGroupName( final String secGroupName ) {
    this.secGroupName = secGroupName;
  }

  /**
   * @return the secGroupOwnerId
   */
  public String getSecGroupOwnerId() {
    return this.secGroupOwnerId;
  }

  /**
   * @param secGroupOwnerId the secGroupOwnerId to set
   */
  public void setSecGroupOwnerId( final String secGroupOwnerId ) {
    this.secGroupOwnerId = secGroupOwnerId;
  }

  /**
   * @return the ec2Service
   */
  public IEC2 getEc2Service() {
    return this.ec2Service;
  }

  /**
   * @param exception the exception to set
   */
  public void setException( final Exception exception ) {
    this.exception = exception;
  }
}
