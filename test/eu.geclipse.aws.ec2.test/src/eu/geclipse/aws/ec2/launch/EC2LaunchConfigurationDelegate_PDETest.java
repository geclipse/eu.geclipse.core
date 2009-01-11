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

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.junit.Test;

import com.xerox.amazonws.ec2.InstanceType;

import eu.geclipse.aws.ec2.op.AMILaunchConfiguration;

/**
 * Test class for the {@link EC2LaunchConfigurationDelegate}.
 * 
 * @author Moritz Post
 */
public class EC2LaunchConfigurationDelegate_PDETest {

  /**
   * Test method for
   * {@link EC2LaunchConfigurationDelegate#getLaunchConfiguration(org.eclipse.debug.core.ILaunchConfiguration)}
   * 
   * @throws CoreException
   */
  @Test
  public void testGetLaunchConfiguration() throws CoreException {
    String dummyString = "dummy"; //$NON-NLS-1$
    String dummyInt = "1"; //$NON-NLS-1$
    EC2LaunchConfigurationDelegate ec2launchConfigDelegate = new EC2LaunchConfigurationDelegate();
    ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
    ILaunchConfigurationType type = manager.getLaunchConfigurationType( IEC2LaunchConfigurationConstants.LAUNCH_CONFIGURATION_ID );

    ILaunchConfigurationWorkingCopy ec2LaunchConfig = type.newInstance( null,
                                                                        dummyString );

    ec2LaunchConfig.setAttribute( IEC2LaunchConfigurationConstants.AMI_ID,
                                  dummyString );
    ec2LaunchConfig.setAttribute( IEC2LaunchConfigurationConstants.MIN_COUNT,
                                  dummyInt );
    ec2LaunchConfig.setAttribute( IEC2LaunchConfigurationConstants.MAX_COUNT,
                                  dummyInt );
    ec2LaunchConfig.setAttribute( IEC2LaunchConfigurationConstants.KEY_NAME,
                                  dummyString );
    List<String> secGrList = new ArrayList<String>();
    secGrList.add( dummyString );
    ec2LaunchConfig.setAttribute( IEC2LaunchConfigurationConstants.SECURITY_GROUP,
                                  secGrList );
    ec2LaunchConfig.setAttribute( IEC2LaunchConfigurationConstants.ZONE,
                                  dummyString );
    ec2LaunchConfig.setAttribute( IEC2LaunchConfigurationConstants.INSTANCE_TYPE,
                                  InstanceType.DEFAULT.getTypeId() );
    ec2LaunchConfig.setAttribute( IEC2LaunchConfigurationConstants.KERNEL_ID,
                                  dummyString );
    ec2LaunchConfig.setAttribute( IEC2LaunchConfigurationConstants.PUBLIC_ADDRESS,
                                  dummyString );
    ec2LaunchConfig.setAttribute( IEC2LaunchConfigurationConstants.RAMDISK_ID,
                                  dummyString );
    ec2LaunchConfig.setAttribute( IEC2LaunchConfigurationConstants.USER_DATA,
                                  dummyString );
    ec2LaunchConfig.setAttribute( IEC2LaunchConfigurationConstants.USER_DATA_FILE_PATH,
                                  dummyString );

    AMILaunchConfiguration launchConfiguration = ec2launchConfigDelegate.getLaunchConfiguration( ec2LaunchConfig );

    ArrayList<String> dummyList = new ArrayList<String>();
    dummyList.add( dummyString );

    assertEquals( dummyString, launchConfiguration.getAmiId() );
    assertEquals( Integer.valueOf( dummyInt ),
                  launchConfiguration.getMinCount() );
    assertEquals( Integer.valueOf( dummyInt ),
                  launchConfiguration.getMaxCount() );
    assertEquals( dummyString, launchConfiguration.getKeyName() );
    assertEquals( dummyList, launchConfiguration.getSecurityGroup() );
    assertEquals( dummyString, launchConfiguration.getZone() );
    assertEquals( InstanceType.DEFAULT, launchConfiguration.getInstanceType() );
    assertEquals( null, launchConfiguration.getBlockDeviceMappings() );
    assertEquals( null, launchConfiguration.getKernelId() );
    assertEquals( null, launchConfiguration.getRamdiskId() );
    assertArrayEquals( dummyString.getBytes(),
                       launchConfiguration.getUserData() );

  }
}
