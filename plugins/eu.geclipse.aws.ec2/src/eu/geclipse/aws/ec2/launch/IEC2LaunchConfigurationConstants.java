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

package eu.geclipse.aws.ec2.launch;

import java.io.File;

/**
 * A list of constants used to denote configuration properties for an
 * {@link EC2LaunchConfigurationDelegate}.
 * 
 * @author Moritz Post
 * @see EC2LaunchConfigurationDelegate
 */
public interface IEC2LaunchConfigurationConstants {

  /** The id of the ec2 launch configuration type. */
  public static final String LAUNCH_CONFIGURATION_ID = "eu.geclipse.aws.ec2.launch.ec2LaunchConfigurationType"; //$NON-NLS-1$

  /** The ID of the AMI to launch. */
  public static final String AWS_ACCESS_ID = "awsAccessId"; //$NON-NLS-1$

  /** The ID of the AMI to launch. */
  public static final String AMI_ID = "amiId"; //$NON-NLS-1$

  /** The minimum number of AMIs to launch. */
  public static final String MIN_COUNT = "minCount"; //$NON-NLS-1$

  /** The maximum (desired) number of AMIs to launch. */
  public static final String MAX_COUNT = "maxCount"; //$NON-NLS-1$

  /** The name of the key file to access the AMI via ssh. */
  public static final String KEY_NAME = "keyName"; //$NON-NLS-1$

  /** The security group to launch the AMI in. */
  public static final String SECURITY_GROUP = "securityGroup"; //$NON-NLS-1$

  /** The availability zone to launch the AMI in. */
  public static final String ZONE = "zone"; //$NON-NLS-1$

  /** The size of the hardware to launch the AMI in. */
  public static final String INSTANCE_TYPE = "instanceType"; //$NON-NLS-1$

  /** Custom User Data in {@link String} form to init the AMI with. */
  public static final String USER_DATA = "userData"; //$NON-NLS-1$

  /** Custom User Data with a path to a local {@link File} to init the AMI with. */
  public static final String USER_DATA_FILE_PATH = "userDataFilePath"; //$NON-NLS-1$

  /** The id of the kernel to use. */
  public static final String KERNEL_ID = "kernelId"; //$NON-NLS-1$

  /** The ramdisk to use. */
  public static final String RAMDISK_ID = "ramdiskId"; //$NON-NLS-1$

  /** The block device mapping to use. */
  public static final String BLOCK_DEVICE_MAPPINGS = "blockDeviceMappings"; //$NON-NLS-1$

  /** Determines whether this instance should use a public address. */
  public static final String PUBLIC_ADDRESS = "publicAddress"; //$NON-NLS-1$

}
