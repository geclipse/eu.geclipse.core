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

package eu.geclipse.aws.ec2;

import java.util.List;

import com.xerox.amazonws.ec2.AddressInfo;
import com.xerox.amazonws.ec2.AvailabilityZone;
import com.xerox.amazonws.ec2.DescribeImageAttributeResult;
import com.xerox.amazonws.ec2.GroupDescription;
import com.xerox.amazonws.ec2.ImageDescription;
import com.xerox.amazonws.ec2.ImageListAttribute;
import com.xerox.amazonws.ec2.Jec2;
import com.xerox.amazonws.ec2.KeyPairInfo;
import com.xerox.amazonws.ec2.ReservationDescription;
import com.xerox.amazonws.ec2.TerminatingInstanceDescription;
import com.xerox.amazonws.ec2.ImageAttribute.ImageAttributeType;
import com.xerox.amazonws.ec2.Jec2.ImageListAttributeOperationType;

import eu.geclipse.aws.ec2.op.AMILaunchConfiguration;

/**
 * This interface provides methods to access the Amazon EC2 Webservices.
 * 
 * @author Moritz Post
 */
public interface IEC2 {

  /**
   * Set the login url and the user credentials to setup a connection to the
   * Amazon EC2 services via the {@link Jec2} java library.
   * 
   * @param ec2Url the url to the Amazon EC2 Webservice interface
   * @param awsAccessId the access Id for the EC2 REST Service
   * @param awsSecretId the secret Id for the EC2 REST Service
   * @return returns whether the initialization was successful
   * @throws Exception when the user details are not correct and sets all
   *           properties to <code>null</code>
   */
  public boolean initEc2( final String ec2Url,
                          final String awsAccessId,
                          final String awsSecretId ) throws Exception;

  /**
   * Describe the AMIs belonging to the given owner.
   * 
   * @param ownerList Only AMIs owned by the specified owners are returned.
   *          Owners may be: An account id as returned by
   *          {@link #describeImages(List, List, List)} 'self' to return AMIs
   *          owned by the user sending the request 'amazon' to return AMIs
   *          owned by Amazon
   * @return A list of {@link ImageDescription} instances describing each AMI
   *         ID.
   * @throws Exception when a problem with the ec2 operation occurred
   * @throws Jec2SoapFaultException If a SOAP fault is received from the EC2
   *           API.
   * @throws EC2ServiceException If any other error occurs issuing the SOAP
   *           call.
   */
  public List<ImageDescription> describeImagesByOwner( List<String> ownerList )
    throws EC2ServiceException;

  /**
   * Describe the AMIs where the user as execution rights
   * 
   * @param execList the list of users whos AMIs we can execute
   * @return A list of {@link ImageDescription} instances describing each AMI
   *         ID.
   * @throws Exception when a problem with the ec2 operation occurred
   * @throws Jec2SoapFaultException If a SOAP fault is received from the EC2
   *           API.
   * @throws EC2ServiceException If any other error occurs issuing the SOAP
   *           call.
   */
  public List<ImageDescription> describeImagesByExec( List<String> execList )
    throws EC2ServiceException;

  /**
   * Requests reservation of a number of instances.
   * <p>
   * This will begin launching those instances for which a reservation was
   * successfully obtained.
   * <p>
   * If less than <code>minCount</code> instances are available no instances
   * will be reserved.
   * <p>
   * 
   * @param launchConfig the configuration parameter
   * @return the {@link ReservationDescription}
   * @throws EC2ServiceException wraps checked exceptions
   */
  public ReservationDescription runInstances( AMILaunchConfiguration launchConfig )
    throws EC2ServiceException;

  /**
   * Gets a list of security groups and their associated permissions.
   * 
   * @param securityGroups A list of groups to describe.
   * @return A list of groups ({@link GroupDescription}.
   * @throws EC2ServiceException wraps checked exceptions
   */
  public List<GroupDescription> describeSecurityGroups( List<String> securityGroups )
    throws EC2ServiceException;

  /**
   * Returns a list of availability zones and their status.
   * 
   * @param zones a list of zones to limit the results, or null
   * @return a list of zones and their availability
   * @throws EC2ServiceException wraps checked exceptions
   */
  public List<AvailabilityZone> describeAvailabilityZones( List<String> zones )
    throws EC2ServiceException;

  /**
   * Lists public/private keypairs.
   * 
   * @param keypairs
   * @param keyIds A list of keypairs.
   * @return A list of keypair descriptions ({@link KeyPairInfo}).
   * @throws EC2ServiceException wraps checked exceptions
   */
  public List<KeyPairInfo> describeKeypairs( List<String> keypairs )
    throws EC2ServiceException;

  /**
   * Gets a list of running instances.
   * <p>
   * If the list of instance IDs is empty then a list of all instances owned by
   * the caller will be returned. Otherwise the list will contain information
   * for the requested instances only.
   * 
   * @param instanceList A list of instances (
   *          {@link ReservationDescription.Instance#instanceId}.
   * @return A list of {@link ReservationDescription} instances.
   * @throws EC2ServiceException wraps checked exceptions
   */
  public List<ReservationDescription> describeInstances( List<String> instanceList )
    throws EC2ServiceException;

  /**
   * Terminates a selection of running instances.
   * 
   * @param instanceList A list of instances ids.
   * @return A list of {@link TerminatingInstanceDescription} instances.
   * @throws EC2ServiceException wraps checked exceptions
   */
  public List<TerminatingInstanceDescription> terminateInstances( List<String> instanceList )
    throws EC2ServiceException;

  /**
   * Determines whether a successful call to
   * {@link #initEc2(String, String, String)} has been executed.
   * 
   * @return if the {@link IEC2} object has been initialized
   */
  public boolean isInitialized();

  /**
   * Tries to set up any necessary access credentials to execute any preceding
   * calls to the <code>describe*</code> methods.
   * 
   * @return if the {@link IEC2} could be initialized successfully
   * @throws EC2ServiceException the exception is thrown when the credentials
   *           could not be obtained
   */
  public boolean ensureAuthentication() throws EC2ServiceException;

  /**
   * Adds incoming permissions to a security group.
   * 
   * @param groupName name of group to modify
   * @param ipProtocol protocol to authorize (tcp, udp, icmp)
   * @param fromPort bottom of port range to authorize
   * @param toPort top of port range to authorize
   * @param cidrIp CIDR IP range to authorize (i.e. 0.0.0.0/0)
   * @throws EC2ServiceException wraps checked exceptions
   */
  public void authorizeSecurityGroup( String groupName,
                                      String cidrIp,
                                      String ipProtocol,
                                      int fromPort,
                                      int toPort ) throws EC2ServiceException;

  /**
   * Revokes incoming permissions from a security group.
   * 
   * @param groupName name of group to modify
   * @param ipProtocol protocol to revoke (tcp, udp, icmp)
   * @param fromPort bottom of port range to revoke
   * @param toPort top of port range to revoke
   * @param cidrIp CIDR IP range to revoke (i.e. 0.0.0.0/0)
   * @throws EC2ServiceException wraps checked exceptions
   */
  public void revokeSecurityGroup( String groupName,
                                   String cidrIp,
                                   String ipProtocol,
                                   int fromPort,
                                   int toPort ) throws EC2ServiceException;

  /**
   * Adds incoming permissions to a security group.
   * 
   * @param groupName name of group to modify
   * @param secGroupName name of security group to authorize access to
   * @param secGroupOwnerId owner of security group to authorize access to
   * @throws EC2ServiceException wraps checked exceptions
   */
  void authorizeSecurityGroup( String groupName,
                               String secGroupName,
                               String secGroupOwnerId )
    throws EC2ServiceException;

  /**
   * Revokes incoming permissions from a security group.
   * 
   * @param groupName name of group to modify
   * @param secGroupName name of security group to revoke access from
   * @param secGroupOwnerId owner of security group to revoke access from
   * @throws EC2ServiceException wraps checked exceptions
   */
  public void revokeSecurityGroup( String groupName,
                                   String secGroupName,
                                   String secGroupOwnerId )
    throws EC2ServiceException;

  /**
   * Deletes a security group.
   * 
   * @param securityGroup the name of the security group.
   * @throws EC2ServiceException wraps checked exceptions
   */
  public void deleteSecurityGroup( String securityGroup )
    throws EC2ServiceException;

  /**
   * Creates a security group.
   * 
   * @param securityGroupName the name of the security group.
   * @param securityGroupDescription the description of the security group.
   * @throws EC2ServiceException wraps checked exceptions
   */
  public void createSecurityGroup( String securityGroupName,
                                   String securityGroupDescription )
    throws EC2ServiceException;

  /**
   * Creates a public/private keypair.
   * 
   * @param keypairName name of the keypair.
   * @return A keypair description ({@link KeyPairInfo}).
   * @throws EC2ServiceException wraps checked exceptions
   */
  public KeyPairInfo createKeypair( String keypairName )
    throws EC2ServiceException;

  /**
   * Allocates an address for this account.
   * 
   * @return the new address allocated
   * @throws EC2ServiceException wraps checked exceptions
   */
  public String allocateAddress() throws EC2ServiceException;

  /**
   * Returns a list of addresses associated with this account.
   * 
   * @param addresses a list of zones to limit the results, or null
   * @return a list of addresses and their associated instance
   * @throws EC2ServiceException wraps checked exceptions
   */
  public List<AddressInfo> describeAddresses( List<String> addresses )
    throws EC2ServiceException;

  /**
   * Associates an address with an instance.
   * 
   * @param instanceId the instance to assign the IP to
   * @param elasticIP the IP address to associate
   * @throws EC2ServiceException wraps checked exceptions
   */
  public void associateAddress( String instanceId, String elasticIP )
    throws EC2ServiceException;

  /**
   * Disassociates an address with an instance.
   * 
   * @param elasticIP the IP address to disassociate
   * @throws EC2ServiceException wraps checked exceptions
   */
  public void disassociateAddress( String elasticIP )
    throws EC2ServiceException;

  /**
   * Releases an elastic IP address.
   * 
   * @param elasticIP the IP address to release
   * @throws EC2ServiceException wraps checked exceptions
   */
  public void releaseAddress( String elasticIP ) throws EC2ServiceException;

  /**
   * Reboot a selection of running instances.
   * 
   * @param instances a list of instance ids.
   * @throws EC2ServiceException wraps checked exceptions
   */
  public void rebootInstances( List<String> instances )
    throws EC2ServiceException;

  /**
   * Register the given AMI.
   * 
   * @param bucketPath an AMI path within S3.
   * @return a unique AMI ID that can be used to create and manage instances of
   *         this AMI.
   * @throws EC2ServiceException wraps checked exceptions
   */
  public String registerImage( String bucketPath ) throws EC2ServiceException;

  /**
   * Describes an attribute of an AMI.
   * 
   * @param imageId the AMI for which the attribute is described
   * @param imageAttribute the attribute type to describe
   * @return An object containing the imageId and a list of list attribute item
   *         types and values.
   * @throws EC2ServiceException wraps checked exceptions
   */
  public DescribeImageAttributeResult describeImageAttributes( String imageId,
                                                               ImageAttributeType imageAttribute )
    throws EC2ServiceException;

  /**
   * Modifies an attribute by the given items with the given operation.
   * 
   * @param imageId The ID of the AMI to modify the attributes for.
   * @param attribute The name of the attribute to change.
   * @param operationType The name of the operation to change. May be add or
   *          remove.
   * @throws EC2ServiceException wraps checked exceptions
   */
  public void modifyImageAttribute( String imageId,
                                    ImageListAttribute attribute,
                                    ImageListAttributeOperationType operationType )
    throws EC2ServiceException;

  /**
   * Deletes a public/private keypair.
   * 
   * @param keypair Name of the keypair.
   * @throws EC2ServiceException wraps checked exceptions
   */
  public void deleteKeypair( String keypair ) throws EC2ServiceException;

}