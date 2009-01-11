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

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * Creates the tabs to display when activating a LaunchConfiguration.
 * 
 * @author Moritz Post
 */
public class EC2LaunchConfigurationTabGroup
  extends AbstractLaunchConfigurationTabGroup
{

  /**
   * The indention for the {@link Composite}s belonging to a {@link SWT#RADIO}
   * button.
   */
  static final int RADIO_INDENT = 15;

  public void createTabs( final ILaunchConfigurationDialog dialog,
                          final String mode )
  {
    // add tabs for launch dialog
    ILaunchConfigurationTab[] tabs = new ILaunchConfigurationTab[]{
      new EC2MainTab(),
      // new EC2MachineTab(),
      new EC2ParameterTab(),
      new CommonTab()
    };
    setTabs( tabs );
  }

}
