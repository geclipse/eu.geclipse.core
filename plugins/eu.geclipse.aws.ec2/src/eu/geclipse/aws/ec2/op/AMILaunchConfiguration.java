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

import com.xerox.amazonws.ec2.BlockDeviceMapping;
import com.xerox.amazonws.ec2.InstanceType;

/**
 * A launch configuration encapsulates the parameters used for launching an AMI.
 * <p>
 * The parameter "public address" is set to true by default.
 * 
 * @author Moritz Post
 */
public class AMILaunchConfiguration {

  /** The ID of the AMI to launch. */
  private String amiId;

  /** The minimum number of AMIs to launch. */
  private int minCount;

  /** The maximum (desired) number of AMIs to launch. */
  private int maxCount;

  /** The name of the key file to access the AMI via ssh. */
  private String keyName;

  /** The security group to launch the AMI in. */
  private List<String> securityGroup;

  /** The availability zone to launch the AMI in. */
  private String zone;

  /** The size of the hardware to launch the AMI in. */
  private InstanceType instanceType;

  /** Custom User Data to init the AMI with. */
  private byte[] userData;

  /** The id of the kernel to use. */
  private String kernelId;

  /** The ramdisk to use. */
  private String ramdiskId;

  /** The block device mapping to use. */
  private List<BlockDeviceMapping> blockDeviceMappings;

  /**
   * Launches the given AMI one time. The min and max values are '1'.
   * 
   * @param amiId the AMI to launch
   */
  public AMILaunchConfiguration( final String amiId ) {
    this( amiId, 1, 1 );
  }

  /**
   * Create a new empty {@link AMILaunchConfiguration}.
   */
  public AMILaunchConfiguration() {
    // nothing to do here
  }

  /**
   * The launch parameter with the minimum required number of parameter.
   * 
   * @param amiId the id of the AMI to launch
   * @param minCount the minimum required number of instances
   * @param maxCount the maximum number of AMIs desired
   */
  public AMILaunchConfiguration( final String amiId,
                                 final int minCount,
                                 final int maxCount )
  {
    this.amiId = amiId;
    this.minCount = minCount;
    this.maxCount = maxCount;
  }

  /**
   * @return the amiId
   */
  public String getAmiId() {
    return this.amiId;
  }

  /**
   * @param amiId the amiId to set
   */
  public void setAmiId( final String amiId ) {
    this.amiId = amiId;
  }

  /**
   * @return the minCount
   */
  public int getMinCount() {
    return this.minCount;
  }

  /**
   * @param minCount the minCount to set
   */
  public void setMinCount( final int minCount ) {
    this.minCount = minCount;
  }

  /**
   * @return the maxCount
   */
  public int getMaxCount() {
    return this.maxCount;
  }

  /**
   * @param maxCount the maxCount to set
   */
  public void setMaxCount( final int maxCount ) {
    this.maxCount = maxCount;
  }

  /**
   * @return the keyName
   */
  public String getKeyName() {
    return this.keyName;
  }

  /**
   * @param keyName the keyName to set
   */
  public void setKeyName( final String keyName ) {
    this.keyName = keyName;
  }

  /**
   * @return the securityGroup
   */
  public List<String> getSecurityGroup() {
    return this.securityGroup;
  }

  /**
   * @param securityGroup the securityGroups to set
   */
  public void setSecurityGroup( final List<String> securityGroup ) {
    this.securityGroup = securityGroup;
  }

  /**
   * @return the zone
   */
  public String getZone() {
    return this.zone;
  }

  /**
   * @param zone the zone to set
   */
  public void setZone( final String zone ) {
    this.zone = zone;
  }

  /**
   * @return the instanceType
   */
  public InstanceType getInstanceType() {
    return this.instanceType;
  }

  /**
   * @param instanceType the instanceType to set
   */
  public void setInstanceType( final InstanceType instanceType ) {
    this.instanceType = instanceType;
  }

  /**
   * @return the userData
   */
  public byte[] getUserData() {
    return this.userData;
  }

  /**
   * @param userData the userData to set
   */
  public void setUserData( final byte[] userData ) {
    this.userData = userData;
  }

  /**
   * @return the kernelId
   */
  public String getKernelId() {
    return this.kernelId;
  }

  /**
   * @param kernelId the kernelId to set
   */
  public void setKernelId( final String kernelId ) {
    this.kernelId = kernelId;
  }

  /**
   * @return the ramdiskId
   */
  public String getRamdiskId() {
    return this.ramdiskId;
  }

  /**
   * @param ramdiskId the ramdiskId to set
   */
  public void setRamdiskId( final String ramdiskId ) {
    this.ramdiskId = ramdiskId;
  }

  /**
   * @return the blockDeviceMappings
   */
  public List<BlockDeviceMapping> getBlockDeviceMappings() {
    return this.blockDeviceMappings;
  }

  /**
   * @param blockDeviceMappings the blockDeviceMappings to set
   */
  public void setBlockDeviceMappings( final List<BlockDeviceMapping> blockDeviceMappings )
  {
    this.blockDeviceMappings = blockDeviceMappings;
  }

}
