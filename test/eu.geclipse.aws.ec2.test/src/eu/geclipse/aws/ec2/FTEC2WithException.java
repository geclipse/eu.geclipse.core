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
import com.xerox.amazonws.ec2.KeyPairInfo;
import com.xerox.amazonws.ec2.ReservationDescription;
import com.xerox.amazonws.ec2.TerminatingInstanceDescription;
import com.xerox.amazonws.ec2.ImageAttribute.ImageAttributeType;
import com.xerox.amazonws.ec2.Jec2.ImageListAttributeOperationType;

import eu.geclipse.aws.ec2.op.AMILaunchConfiguration;

/**
 * A fake type implementation of the {@link IEC2} interface. This implementation
 * ignores any input, delivers fixed results and does throw {@link Exception}s
 * on every method.
 * 
 * @author Moritz Post
 */
public class FTEC2WithException implements IEC2 {

  /**
   * The SingletonHolder is loaded on the first execution of
   * SingletonHolder.getInstance() or the first access to
   * SingletonHolder.instance
   */
  private static class SingletonHolder {

    /** The store of the {@link FTEC2WithException} instance. */
    private final static FTEC2WithException instance = new FTEC2WithException();
  }

  /** The exception thrown on method invocation. */
  private static EC2ServiceException exception = new EC2ServiceException( "EC2 Failure" ); //$NON-NLS-1$

  /**
   * Get the instance of the {@link FTEC2WithException}.
   * 
   * @return the instantiated singleton
   */
  public static FTEC2WithException getInstance() {
    return SingletonHolder.instance;
  }

  /**
   * Private constructor to refuse public instantiation.
   */
  private FTEC2WithException() {
    // nothing to do here
  }

  public List<AvailabilityZone> describeAvailabilityZones( final List<String> zones )
    throws EC2ServiceException
  {

    throw FTEC2WithException.exception;
  }

  public List<ImageDescription> describeImagesByExec( final List<String> execList )
    throws EC2ServiceException
  {
    throw FTEC2WithException.exception;
  }

  public List<ImageDescription> describeImagesByOwner( final List<String> ownerList )
    throws EC2ServiceException
  {
    throw FTEC2WithException.exception;
  }

  public List<KeyPairInfo> describeKeypairs( final List<String> keypairs )
    throws EC2ServiceException
  {
    throw FTEC2WithException.exception;
  }

  public List<GroupDescription> describeSecurityGroups( final List<String> securityGroups )
    throws EC2ServiceException
  {
    throw FTEC2WithException.exception;
  }

  public boolean initEc2( final String ec2Url,
                          final String awsAccessId,
                          final String awsSecretId ) throws Exception
  {
    throw FTEC2WithException.exception;
  }

  public ReservationDescription runInstances( final AMILaunchConfiguration launchConfig )
    throws EC2ServiceException
  {
    throw FTEC2WithException.exception;
  }

  public List<ReservationDescription> describeInstances( final List<String> instanceList )
    throws EC2ServiceException
  {
    throw FTEC2WithException.exception;
  }

  public List<TerminatingInstanceDescription> terminateInstances( final List<String> instanceList )
    throws EC2ServiceException
  {
    throw FTEC2WithException.exception;
  }

  public boolean isInitialized() {
    return false;
  }

  public boolean ensureAuthentication() throws EC2ServiceException {
    return false;
  }

  public void authorizeSecurityGroup( final String groupName,
                                      final String cidrIp,
                                      final String ipProtocol,
                                      final int fromPort,
                                      final int toPort )
    throws EC2ServiceException
  {
    throw FTEC2WithException.exception;
  }

  public void revokeSecurityGroup( final String groupName,
                                   final String cidrIp,
                                   final String ipProtocol,
                                   final int fromPort,
                                   final int toPort )
    throws EC2ServiceException
  {
    throw FTEC2WithException.exception;
  }

  public void authorizeSecurityGroup( final String groupName,
                                      final String secGroupName,
                                      final String secGroupOwnerId )
    throws EC2ServiceException
  {
    throw FTEC2WithException.exception;
  }

  public void revokeSecurityGroup( final String groupName,
                                   final String secGroupName,
                                   final String secGroupOwnerId )
    throws EC2ServiceException
  {
    throw FTEC2WithException.exception;
  }

  public void deleteSecurityGroup( final String securityGroup )
    throws EC2ServiceException
  {
    throw FTEC2WithException.exception;
  }

  public void createSecurityGroup( final String securityGroupName,
                                   final String securityGroupDescription )
    throws EC2ServiceException
  {
    throw FTEC2WithException.exception;
  }

  public KeyPairInfo createKeypair( final String keypairName )
    throws EC2ServiceException
  {
    throw FTEC2WithException.exception;
  }

  public String allocateAddress() throws EC2ServiceException {
    throw FTEC2WithException.exception;
  }

  public List<AddressInfo> describeAddresses( final List<String> addresses )
    throws EC2ServiceException
  {
    throw FTEC2WithException.exception;
  }

  public void associateAddress( final String instanceId, final String elasticIP )
    throws EC2ServiceException
  {
    throw FTEC2WithException.exception;
  }

  public void disassociateAddress( final String elasticIP )
    throws EC2ServiceException
  {
    throw FTEC2WithException.exception;
  }

  public void releaseAddress( final String elasticIP )
    throws EC2ServiceException
  {
    throw FTEC2WithException.exception;
  }

  public void rebootInstances( final List<String> instances )
    throws EC2ServiceException
  {
    throw FTEC2WithException.exception;
  }

  public String registerImage( final String bucketPath )
    throws EC2ServiceException
  {
    throw FTEC2WithException.exception;
  }

  public DescribeImageAttributeResult describeImageAttributes( final String imageId,
                                                               final ImageAttributeType imageAttribute )
    throws EC2ServiceException
  {
    throw FTEC2WithException.exception;
  }

  public void modifyImageAttribute( final String imageId,
                                    final ImageListAttribute attribute,
                                    final ImageListAttributeOperationType operationType )
    throws EC2ServiceException
  {
    throw FTEC2WithException.exception;
  }

  public void deleteKeypair( final String keypair ) throws EC2ServiceException {
    throw FTEC2WithException.exception;
  }
}
