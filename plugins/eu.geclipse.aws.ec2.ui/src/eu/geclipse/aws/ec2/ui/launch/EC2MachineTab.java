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

package eu.geclipse.aws.ec2.ui.launch;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.swt.widgets.Composite;

import eu.geclipse.aws.ec2.launch.IEC2LaunchConfigurationConstants;
import eu.geclipse.aws.ec2.ui.Messages;

/**
 * Presents the machine/hardware specific properties of an AMI. Particularly the
 * following items:
 * <ul>
 * <li> {@link IEC2LaunchConfigurationConstants#BLOCK_DEVICE_MAPPINGS}</li>
 * <li> {@link IEC2LaunchConfigurationConstants#KERNEL_ID}</li>
 * <li> {@link IEC2LaunchConfigurationConstants#RAMDISK_ID}</li>
 * </ul>
 * 
 * @author Moritz Post
 */
public class EC2MachineTab extends AbstractLaunchConfigurationTab {

  public void createControl( final Composite parent ) {
    // TODO Auto-generated method stub

  }

  public String getName() {
    return Messages.getString("EC2MachineTab.tab_title"); //$NON-NLS-1$
  }

  public void initializeFrom( final ILaunchConfiguration configuration ) {
    // TODO Auto-generated method stub

  }

  public void performApply( final ILaunchConfigurationWorkingCopy configuration )
  {
    // TODO Auto-generated method stub

  }

  public void setDefaults( final ILaunchConfigurationWorkingCopy configuration )
  {
    // TODO Auto-generated method stub

  }

}
