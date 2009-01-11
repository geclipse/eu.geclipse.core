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

package eu.geclipse.aws.ec2.ui.actions;

import java.util.ArrayList;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import eu.geclipse.aws.ec2.EC2AMIImage;
import eu.geclipse.aws.ec2.launch.IEC2LaunchConfigurationConstants;
import eu.geclipse.aws.ec2.service.EC2Service;
import eu.geclipse.aws.ec2.ui.internal.Activator;
import eu.geclipse.aws.vo.AWSVirtualOrganization;
import eu.geclipse.core.model.IGridService;
import eu.geclipse.core.reporting.ProblemException;


/**
 * This {@link IAction} is registered on the list of AMIs ({@link IGridService}
 * instances). Upon activation a {@link RunAMIInstancesWizard} is displayed
 * which can be used to configure run parameter for an AMI instance and to
 * finally launch a new instance with those parameter.
 * 
 * @author Moritz Post
 */
public class LaunchAMI implements IObjectActionDelegate {

  /** The id of the external tools launch group. */
  private static final String EXTERNAL_TOOLS_LAUNCH_GROUP = "org.eclipse.ui.externaltools.launchGroup"; //$NON-NLS-1$

  /** The list of AMIs which are selected. */
  private ArrayList<EC2AMIImage> amiList;

  /** The workbench this {@link IAction} resides in. */
  private IWorkbenchPart workbenchPart;

  /**
   * Default constructor.
   */
  public LaunchAMI() {
    this.amiList = new ArrayList<EC2AMIImage>();
  }

  public void setActivePart( final IAction action,
                             final IWorkbenchPart targetPart )
  {
    this.workbenchPart = targetPart;
  }

  public void run( final IAction action ) {
    if( action.isEnabled() ) {

      ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
      ILaunchConfigurationType type = manager.getLaunchConfigurationType( IEC2LaunchConfigurationConstants.LAUNCH_CONFIGURATION_ID );
      ILaunchConfigurationWorkingCopy ec2LaunchConfig = null;

      String amiId = null;
      if( this.amiList != null && this.amiList.size() > 0 ) {
        amiId = this.amiList.get( 0 ).getImageId();
      }

      ILaunchConfiguration savedLaunchConfig = null;
      try {
        ILaunchConfiguration[] launchConfigurations = manager.getLaunchConfigurations( type );
        String launchName = createUniqueName( launchConfigurations, amiId, 0 );
        String awsAccessId = null;

        EC2AMIImage ami = this.amiList.get( 0 );
        if ( ami != null ) {
          EC2AMIImage ec2AMIImage = ami;
          EC2Service ec2Service = ec2AMIImage.getEC2Service();
          AWSVirtualOrganization awsVo = ( AWSVirtualOrganization )ec2Service.getParent();
          try {
            awsAccessId = awsVo.getProperties().getAwsAccessId();
          } catch ( ProblemException problemEx ) {
            Activator.log( "Could not read aws vo properties", problemEx ); //$NON-NLS-1$
          }
        }

        ec2LaunchConfig = type.newInstance( null, launchName );
        ec2LaunchConfig.setAttribute( IEC2LaunchConfigurationConstants.AMI_ID,
                                      amiId );
        ec2LaunchConfig.setAttribute( IEC2LaunchConfigurationConstants.AWS_ACCESS_ID,
                                      awsAccessId );
        savedLaunchConfig = ec2LaunchConfig.doSave();
      } catch ( CoreException coreEx ) {
        Activator.log( "Could not initiate launch wizard", coreEx ); //$NON-NLS-1$
      }

      DebugUITools.openLaunchConfigurationDialog( this.workbenchPart.getSite()
                                                    .getShell(),
                                                  savedLaunchConfig,
                                                  LaunchAMI.EXTERNAL_TOOLS_LAUNCH_GROUP,
                                                  null );
    }
  }

  /**
   * Checks if the passed name already exists in the launch configuration list.
   * If this is the case, the number i is appended and the method
   * {@link #createUniqueName(ILaunchConfiguration[], String, int)} is called
   * recursively.
   * 
   * @param launchConfigurations the list of {@link ILaunchConfiguration}s to
   *            check against
   * @param amiId the name to check
   * @param i the current index of the AMI
   * @return the unique name
   */
  private String createUniqueName( final ILaunchConfiguration[] launchConfigurations,
                                   final String amiId,
                                   int i )
  {
    String tempAmi = amiId;
    if( i > 0 ) {
      tempAmi = amiId + " (" + i + ")"; //$NON-NLS-1$//$NON-NLS-2$
    }
    for( ILaunchConfiguration existingLaunchConfig : launchConfigurations ) {
      if( existingLaunchConfig.getName().equals( tempAmi ) ) {
        i++;
        return createUniqueName( launchConfigurations, amiId, i );
      }
    }
    return tempAmi;
  }

  public void selectionChanged( final IAction action, final ISelection selection )
  {
    boolean enable = false;
    this.amiList.clear();

    if( selection instanceof IStructuredSelection ) {
      IStructuredSelection structuredSelection = ( IStructuredSelection )selection;

      for( Object element : structuredSelection.toList() ) {
        if( element instanceof EC2AMIImage ) {
          EC2AMIImage amiImage = ( EC2AMIImage )element;
          this.amiList.add( amiImage );
        }
      }
    }
    if( this.amiList.size() == 1 ) {
      enable = true;
    }
    action.setEnabled( enable );
  }
}
