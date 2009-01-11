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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;

import com.xerox.amazonws.ec2.InstanceType;

import eu.geclipse.aws.ec2.EC2ProblemException;
import eu.geclipse.aws.ec2.EC2Registry;
import eu.geclipse.aws.ec2.IEC2;
import eu.geclipse.aws.ec2.IEC2Problems;
import eu.geclipse.aws.ec2.internal.Activator;
import eu.geclipse.aws.ec2.internal.Messages;
import eu.geclipse.aws.ec2.op.AMILaunchConfiguration;
import eu.geclipse.aws.ec2.op.EC2OpRunInstances;
import eu.geclipse.aws.ec2.op.OperationExecuter;
import eu.geclipse.aws.vo.AWSVirtualOrganization;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridResourceCategory;
import eu.geclipse.core.model.IVoManager;
import eu.geclipse.core.model.impl.GridResourceCategoryFactory;
import eu.geclipse.core.reporting.ProblemException;

/**
 * This EC2 specific implementation of the {@link ILaunchConfigurationDelegate}
 * manages the process of launching a specific Amazon Machine Image.
 * 
 * @author Moritz Post
 */
public class EC2LaunchConfigurationDelegate
  implements ILaunchConfigurationDelegate
{

  /**
   * The launch configuration type as specified in the <code>plugin.xml</code>
   * file.
   */
  public static final String LAUNCH_CONFIGURATION_TYPE = "eu.geclipse.aws.ec2.launch.ec2LaunchConfigurationType"; //$NON-NLS-1$

  public void launch( final ILaunchConfiguration configuration,
                      final String mode,
                      final ILaunch launch,
                      final IProgressMonitor monitor ) throws CoreException
  {
    String identifier = configuration.getType().getIdentifier();
    if( identifier.equals( EC2LaunchConfigurationDelegate.LAUNCH_CONFIGURATION_TYPE ) )
    {
      AMILaunchConfiguration launchConfig = getLaunchConfiguration( configuration );

      monitor.beginTask( Messages.getString( "EC2LaunchConfigurationDelegate.monitor_description_start_ami" ) //$NON-NLS-1$
                             + " " //$NON-NLS-1$
                             + launchConfig.getAmiId(),
                         IProgressMonitor.UNKNOWN );

      String emptyString = new String();
      String awsAccessId = configuration.getAttribute( IEC2LaunchConfigurationConstants.AWS_ACCESS_ID,
                                                       emptyString );
      IEC2 ec2 = EC2Registry.getRegistry().getEC2( awsAccessId );

      try {

        EC2OpRunInstances opRunInstances = new EC2OpRunInstances( ec2,
                                                                  launchConfig );
        new OperationExecuter().execOp( opRunInstances );

        if( opRunInstances.getException() != null ) {
          throw opRunInstances.getException();
        }

        refreshComputingCategory( awsAccessId );

      } catch( Exception ex ) {
        Throwable cause = ex.getCause();
        EC2ProblemException exception = new EC2ProblemException( IEC2Problems.EC2_INTERACTION,
                                                                 cause.getLocalizedMessage(),
                                                                 cause,
                                                                 Activator.PLUGIN_ID );
        Activator.log( exception );
        throw exception;
      } finally {
        monitor.done();
      }
    }
  }

  /**
   * Refreshes the computing category
   * {@link GridResourceCategoryFactory#ID_COMPUTING} in the GridProject view of
   * projects associated with a vo holding a reference to the given aws access
   * id.
   * 
   * @param awsAccessId the access id associated with a
   * @throws ProblemException any problem while interacting with the vo
   */
  private void refreshComputingCategory( final String awsAccessId )
    throws ProblemException
  {
    IVoManager voManager = GridModel.getVoManager();

    IGridElement[] children = voManager.getChildren( new NullProgressMonitor() );

    for( IGridElement gridElement : children ) {

      if( gridElement instanceof AWSVirtualOrganization ) {
        AWSVirtualOrganization awsVo = ( AWSVirtualOrganization )gridElement;
        String currentAwsAccessId = awsVo.getProperties().getAwsAccessId();

        if( currentAwsAccessId.equals( awsAccessId ) ) {
          IGridResourceCategory category = GridResourceCategoryFactory.getCategory( GridResourceCategoryFactory.ID_COMPUTING );
          awsVo.refreshResources( category, new NullProgressMonitor() );

        }
      }
    }
  }

  /**
   * Creates a {@link AMILaunchConfiguration} from the data inputed via this
   * wizard pages form elements.
   * 
   * @param configuration the configuration to fetch the launch parameters from
   * @return the newly filled {@link AMILaunchConfiguration} or
   *         <code>null</code> if no AMI id is present.
   * @throws CoreException this exception is thrown when there is an error with
   *           {@link ILaunchConfiguration}
   */
  public AMILaunchConfiguration getLaunchConfiguration( final ILaunchConfiguration configuration )
    throws CoreException
  {
    String emptyString = ""; //$NON-NLS-1$
    String amiId = configuration.getAttribute( IEC2LaunchConfigurationConstants.AMI_ID,
                                               emptyString );
    if( amiId.equals( emptyString ) ) {
      return null;
    }
    AMILaunchConfiguration launchConfig = new AMILaunchConfiguration();
    launchConfig.setAmiId( amiId );
    String minCountString = configuration.getAttribute( IEC2LaunchConfigurationConstants.MIN_COUNT,
                                                        emptyString );
    launchConfig.setMinCount( Integer.valueOf( minCountString ) );
    String maxCountString = configuration.getAttribute( IEC2LaunchConfigurationConstants.MAX_COUNT,
                                                        emptyString );
    launchConfig.setMaxCount( Integer.valueOf( maxCountString ) );
    launchConfig.setKeyName( configuration.getAttribute( IEC2LaunchConfigurationConstants.KEY_NAME,
                                                         emptyString ) );
    List<?> securityGroupsList = configuration.getAttribute( IEC2LaunchConfigurationConstants.SECURITY_GROUP,
                                                             new ArrayList<String>() );
    ArrayList<String> securityGroupsStringList = new ArrayList<String>( securityGroupsList.size() );

    for( Object object : securityGroupsList ) {
      securityGroupsStringList.add( object.toString() );
    }
    launchConfig.setSecurityGroup( securityGroupsStringList );
    launchConfig.setZone( configuration.getAttribute( IEC2LaunchConfigurationConstants.ZONE,
                                                      emptyString ) );
    String instanceTypeString = configuration.getAttribute( IEC2LaunchConfigurationConstants.INSTANCE_TYPE,
                                                            emptyString );
    InstanceType instanceType = InstanceType.getTypeFromString( instanceTypeString );
    launchConfig.setInstanceType( instanceType );

    String userDataString = configuration.getAttribute( IEC2LaunchConfigurationConstants.USER_DATA,
                                                        emptyString );
    String userDataFileString = configuration.getAttribute( IEC2LaunchConfigurationConstants.USER_DATA_FILE_PATH,
                                                            emptyString );
    byte[] userData = null;
    if( !userDataString.equals( emptyString ) ) {
      userData = userDataString.getBytes();
    } else if( !userDataFileString.equals( emptyString ) ) {
      try {
        File userDataFile = new File( userDataFileString );
        FileInputStream fis = new FileInputStream( userDataFile );
        userData = new byte[ ( int )userDataFile.length() ];
        fis.read( userData );
        fis.close();
      } catch( FileNotFoundException fnfEx ) {
        Activator.log( "User Data File was not found", fnfEx ); //$NON-NLS-1$
      } catch( IOException ioEx ) {
        Activator.log( "Could not read user data file", ioEx ); //$NON-NLS-1$

      }
    }
    launchConfig.setUserData( userData );
    return launchConfig;
  }
}
