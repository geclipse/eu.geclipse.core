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

import java.util.ArrayList;
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
 * ignores any input, delivers fixed results and does not throw any errors.
 * 
 * @author Moritz Post
 */
public class FTEC2WithResult implements IEC2 {

  /** A dummy String. */
  private static final String DUMMY_STRING = "dummy"; //$NON-NLS-1$

  /**
   * The SingletonHolder is loaded on the first execution of
   * SingletonHolder.getInstance() or the first access to
   * SingletonHolder.instance
   */
  private static class SingletonHolder {

    /** The store of the {@link FTEC2WithResult} instance. */
    private final static FTEC2WithResult instance = new FTEC2WithResult();
  }

  /**
   * Get the instance of the {@link FTEC2WithResult}.
   * 
   * @return the instantiated singleton
   */
  public static FTEC2WithResult getInstance() {
    return SingletonHolder.instance;
  }

  /**
   * Private constructor to refuse public instantiation.
   */
  private FTEC2WithResult() {
    // nothing to do here
  }

  public List<AvailabilityZone> describeAvailabilityZones( final List<String> zones )
    throws EC2ServiceException
  {
    ArrayList<AvailabilityZone> list = new ArrayList<AvailabilityZone>();
    list.add( new AvailabilityZone( FTEC2WithResult.DUMMY_STRING,
                                    FTEC2WithResult.DUMMY_STRING ) );
    return list;
  }

  public List<ImageDescription> describeImagesByExec( final List<String> execList )
    throws EC2ServiceException
  {
    ArrayList<ImageDescription> list = new ArrayList<ImageDescription>();
    list.add( new ImageDescription( FTEC2WithResult.DUMMY_STRING,
                                    FTEC2WithResult.DUMMY_STRING,
                                    FTEC2WithResult.DUMMY_STRING,
                                    FTEC2WithResult.DUMMY_STRING,
                                    true,
                                    new ArrayList<String>() ) );
    return list;
  }

  public List<ImageDescription> describeImagesByOwner( final List<String> ownerList )
    throws EC2ServiceException
  {
    ArrayList<ImageDescription> list = new ArrayList<ImageDescription>();
    list.add( new ImageDescription( FTEC2WithResult.DUMMY_STRING,
                                    FTEC2WithResult.DUMMY_STRING,
                                    FTEC2WithResult.DUMMY_STRING,
                                    FTEC2WithResult.DUMMY_STRING,
                                    true,
                                    new ArrayList<String>() ) );
    return list;
  }

  public List<KeyPairInfo> describeKeypairs( final List<String> keypairs )
    throws EC2ServiceException
  {
    ArrayList<KeyPairInfo> list = new ArrayList<KeyPairInfo>();
    list.add( new KeyPairInfo( FTEC2WithResult.DUMMY_STRING,
                               FTEC2WithResult.DUMMY_STRING,
                               FTEC2WithResult.DUMMY_STRING ) );
    return list;
  }

  public List<GroupDescription> describeSecurityGroups( final List<String> securityGroups )
    throws EC2ServiceException
  {
    ArrayList<GroupDescription> list = new ArrayList<GroupDescription>();
    list.add( new GroupDescription( FTEC2WithResult.DUMMY_STRING,
                                    FTEC2WithResult.DUMMY_STRING,
                                    FTEC2WithResult.DUMMY_STRING ) );
    return list;
  }

  public boolean initEc2( final String ec2Url,
                          final String awsAccessId,
                          final String awsSecretId ) throws Exception
  {
    return true;
  }

  public ReservationDescription runInstances( final AMILaunchConfiguration launchConfig )
    throws EC2ServiceException
  {
    return new ReservationDescription( FTEC2WithResult.DUMMY_STRING,
                                       FTEC2WithResult.DUMMY_STRING );
  }

  public List<ReservationDescription> describeInstances( final List<String> instanceList )
    throws EC2ServiceException
  {
    ArrayList<ReservationDescription> list = new ArrayList<ReservationDescription>();
    ReservationDescription reservationDescription = new ReservationDescription( FTEC2WithResult.DUMMY_STRING,
                                                                                FTEC2WithResult.DUMMY_STRING );
    list.add( reservationDescription );
    return list;
  }

  public List<TerminatingInstanceDescription> terminateInstances( final List<String> instanceList )
    throws EC2ServiceException
  {
    ArrayList<TerminatingInstanceDescription> list = new ArrayList<TerminatingInstanceDescription>();
    TerminatingInstanceDescription terminatingInstanceDescription = new TerminatingInstanceDescription( FTEC2WithResult.DUMMY_STRING,
                                                                                                        FTEC2WithResult.DUMMY_STRING,
                                                                                                        0,
                                                                                                        FTEC2WithResult.DUMMY_STRING,
                                                                                                        0 );
    list.add( terminatingInstanceDescription );
    return list;
  }

  public boolean isInitialized() {
    return true;
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
    // nothing to do here. no exception stored
  }

  public void revokeSecurityGroup( final String groupName,
                                   final String cidrIp,
                                   final String ipProtocol,
                                   final int fromPort,
                                   final int toPort )
    throws EC2ServiceException
  {
    // nothing to do here. no exception stored
  }

  public void authorizeSecurityGroup( final String groupName,
                                      final String secGroupName,
                                      final String secGroupOwnerId )
    throws EC2ServiceException
  {
    // nothing to do here. no exception stored
  }

  public void revokeSecurityGroup( final String groupName,
                                   final String secGroupName,
                                   final String secGroupOwnerId )
    throws EC2ServiceException
  {
    // nothing to do here. no exception stored
  }

  public void deleteSecurityGroup( final String securityGroup )
    throws EC2ServiceException
  {
    // nothing to do here. no exception stored
  }

  public void createSecurityGroup( final String securityGroupName,
                                   final String securityGroupDescription )
    throws EC2ServiceException
  {
    // nothing to do here. no exception stored
  }

  public KeyPairInfo createKeypair( final String keypairName )
    throws EC2ServiceException
  {
    return new KeyPairInfo( FTEC2WithResult.DUMMY_STRING,
                            FTEC2WithResult.DUMMY_STRING,
                            FTEC2WithResult.DUMMY_STRING );
  }

  public String allocateAddress() throws EC2ServiceException {
    return FTEC2WithResult.DUMMY_STRING;
  }

  public List<AddressInfo> describeAddresses( final List<String> addresses )
    throws EC2ServiceException
  {
    List<AddressInfo> addressList = new ArrayList<AddressInfo>();
    AddressInfo addressInfo = new AddressInfo( FTEC2WithResult.DUMMY_STRING,
                                               FTEC2WithResult.DUMMY_STRING );
    addressList.add( addressInfo );
    return addressList;
  }

  public void associateAddress( final String instanceId, final String elasticIP )
    throws EC2ServiceException
  {
    // nothing to do here. no exception stored
  }

  public void disassociateAddress( final String elasticIP )
    throws EC2ServiceException
  {
    // nothing to do here. no exception stored
  }

  public void releaseAddress( final String elasticIP )
    throws EC2ServiceException
  {
    // nothing to do here. no exception stored
  }

  public void rebootInstances( final List<String> instances )
    throws EC2ServiceException
  {
    // nothing to do here. no exception stored
  }

  public String registerImage( final String bucketPath )
    throws EC2ServiceException
  {
    return FTEC2WithResult.DUMMY_STRING;
  }

  public DescribeImageAttributeResult describeImageAttributes( final String imageId,
                                                               final ImageAttributeType imageAttribute )
    throws EC2ServiceException
  {
    DescribeImageAttributeResult describeImageAttributeResult = new DescribeImageAttributeResult( FTEC2WithResult.DUMMY_STRING,
                                                                                                  null,
                                                                                                  null,
                                                                                                  null,
                                                                                                  null,
                                                                                                  null );
    return describeImageAttributeResult;
  }

  public void modifyImageAttribute( final String imageId,
                                    final ImageListAttribute attribute,
                                    final ImageListAttributeOperationType operationType )
    throws EC2ServiceException
  {
    // nothing to do here. no exception stored
  }

  public void deleteKeypair( final String keypair ) throws EC2ServiceException {
    // nothing to do here. no exception stored
  }
}
