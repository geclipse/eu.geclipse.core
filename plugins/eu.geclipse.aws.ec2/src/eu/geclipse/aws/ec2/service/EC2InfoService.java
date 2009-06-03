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

package eu.geclipse.aws.ec2.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.xerox.amazonws.ec2.AddressInfo;
import com.xerox.amazonws.ec2.GroupDescription;
import com.xerox.amazonws.ec2.ImageDescription;
import com.xerox.amazonws.ec2.KeyPairInfo;
import com.xerox.amazonws.ec2.ReservationDescription;
import com.xerox.amazonws.ec2.ReservationDescription.Instance;

import eu.geclipse.aws.AWSInfoService;
import eu.geclipse.aws.IAWSCategories;
import eu.geclipse.aws.auth.AWSAuthTokenDescription;
import eu.geclipse.aws.ec2.EC2AMIImage;
import eu.geclipse.aws.ec2.EC2ElasticIPAddress;
import eu.geclipse.aws.ec2.EC2Instance;
import eu.geclipse.aws.ec2.EC2Keypair;
import eu.geclipse.aws.ec2.EC2Registry;
import eu.geclipse.aws.ec2.EC2SecurityGroup;
import eu.geclipse.aws.ec2.IEC2;
import eu.geclipse.aws.ec2.IEC2Categories;
import eu.geclipse.aws.ec2.internal.Activator;
import eu.geclipse.aws.ec2.internal.Messages;
import eu.geclipse.aws.ec2.op.AbstractEC2OpDescribeImages;
import eu.geclipse.aws.ec2.op.EC2OpDescribeAddresses;
import eu.geclipse.aws.ec2.op.EC2OpDescribeImagesByExec;
import eu.geclipse.aws.ec2.op.EC2OpDescribeImagesByOwner;
import eu.geclipse.aws.ec2.op.EC2OpDescribeInstances;
import eu.geclipse.aws.ec2.op.EC2OpDescribeKeypairs;
import eu.geclipse.aws.ec2.op.EC2OpDescribeSecurityGroups;
import eu.geclipse.aws.ec2.op.OperationExecuter;
import eu.geclipse.aws.vo.AWSVirtualOrganization;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridInfoService;
import eu.geclipse.core.model.IGridResource;
import eu.geclipse.core.model.IGridResourceCategory;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.model.impl.AbstractGridInfoService;
import eu.geclipse.core.model.impl.GridResourceCategoryFactory;
import eu.geclipse.core.reporting.ProblemException;

/**
 * The {@link EC2InfoService}is used to provide EC2 specific input to the
 * underlying {@link AWSInfoService}. The elements presented in the VO tree are
 * <ul>
 * <li>Available Images</li>
 * <li>Running Instances</li>
 * <li>Security Groups</li>
 * <li>Keypairs</li>
 * <li>Elastic IPs</li>
 * </ul>
 * 
 * @author Moritz Post
 * @see AWSInfoService
 */
public class EC2InfoService extends AbstractGridInfoService
  implements IGridInfoService
{

  /** The identifier of "amazon" as an AMI owner. */
  private static final String AMI_OWNER_ALL = "all"; //$NON-NLS-1$

  /** The identifier "self" as an AMI owner. */
  private static final String AMI_OWNER_SELF = "self"; //$NON-NLS-1$

  /** The name of the file to save this grid element in. */
  public static String STORAGE_NAME = ".ec2InfoService"; //$NON-NLS-1$

  /**
   * The Vo housing the {@link EC2Service} and thereby this
   * {@link EC2InfoService}. It is also used to create
   * {@link AWSAuthTokenDescription}s.
   */
  private AWSVirtualOrganization awsVo;

  /**
   * The parent {@link EC2Service} providing connection details for this info
   * service.
   */
  private EC2Service ec2Service;

  /**
   * Creates a new {@link EC2InfoService} by directly setting the various fields
   * described in the parameter list.
   * 
   * @param awsVo the vo to use for authentication services
   * @param ec2Service the parent element to obtain various information from
   */
  public EC2InfoService( final AWSVirtualOrganization awsVo,
                         final EC2Service ec2Service )
  {
    this.awsVo = awsVo;
    this.ec2Service = ec2Service;
  }

  public IGridResource[] fetchResources( final IGridContainer parent,
                                         final IVirtualOrganization vo,
                                         final IGridResourceCategory category,
                                         final boolean exclusive,
                                         final Class<? extends IGridResource> typeFilter,
                                         final IProgressMonitor monitor )
  {
    IGridResource[] result = null;
    if( category.equals( GridResourceCategoryFactory.getCategory( IAWSCategories.CATEGORY_AWS_COMPUTING ) )
        || category.equals( GridResourceCategoryFactory.getCategory( GridResourceCategoryFactory.ID_COMPUTING ) ) )
    {
      result = fetchInstances( parent, vo, monitor );
    } else if( category.equals( GridResourceCategoryFactory.getCategory( IEC2Categories.CATEGORY_EC2_IMAGES_ALL ) ) )
    {
      String[] owner = {
        EC2InfoService.AMI_OWNER_ALL, EC2InfoService.AMI_OWNER_SELF
      };
      result = fetchImages( parent,
                            vo,
                            monitor,
                            new EC2OpDescribeImagesByExec( getEc2(), owner ) );
    } else if( category.equals( GridResourceCategoryFactory.getCategory( IEC2Categories.CATEGORY_EC2_IMAGES_MY_OWNED ) ) )
    {
      String[] owner = {
        EC2InfoService.AMI_OWNER_SELF
      };
      result = fetchImages( parent,
                            vo,
                            monitor,
                            new EC2OpDescribeImagesByOwner( getEc2(), owner ) );
    } else if( category.equals( GridResourceCategoryFactory.getCategory( IEC2Categories.CATEGORY_EC2_IMAGES_MY_ACCESSIBLE ) ) )
    {
      String[] owner = {
        EC2InfoService.AMI_OWNER_SELF
      };
      result = fetchImages( parent,
                            vo,
                            monitor,
                            new EC2OpDescribeImagesByExec( getEc2(), owner ) );
    } else if( category.equals( GridResourceCategoryFactory.getCategory( IEC2Categories.CATEGORY_EC2_SECURITY_GROUPS ) ) )
    {
      result = fetchSecurityGroups( parent, vo, monitor );
    } else if( category.equals( GridResourceCategoryFactory.getCategory( IEC2Categories.CATEGORY_EC2_KEYPAIRS ) ) )
    {
      result = fetchKeypairs( parent, vo, monitor );
    } else if( category.equals( GridResourceCategoryFactory.getCategory( IEC2Categories.CATEGORY_EC2_ELASTIC_IP ) ) )
    {
      result = fetchElasticIPs( parent, vo, monitor );
    }

    if (result == null)
      result = new IGridResource[0];

    return result;
  }

  /**
   * Fetches the list of remotely existing EC2 security groups and transforms
   * them into {@link EC2SecurityGroup}s.
   * 
   * @param parent the parent housing this {@link EC2InfoService}
   * @param vo the vo providing the {@link EC2InfoService}
   * @param monitor the monitor to be informed of progress
   * @return the resulting array of {@link EC2SecurityGroup}s
   */
  private EC2SecurityGroup[] fetchSecurityGroups( final IGridContainer parent,
                                                  final IVirtualOrganization vo,
                                                  IProgressMonitor monitor )
  {
    if( monitor == null ) {
      monitor = new NullProgressMonitor();
    }

    // fetch existing security groups from EC2 service
    monitor.beginTask( Messages.getString( "EC2InfoService.monitor_task_description" ), //$NON-NLS-1$
                       2 );

    EC2OpDescribeSecurityGroups opDescSecurityGroups = new EC2OpDescribeSecurityGroups( getEc2() );
    new OperationExecuter().execOp( opDescSecurityGroups );

    monitor.worked( 1 );

    if( opDescSecurityGroups.getException() == null ) {
      // transform answer into gEclipse format
      ArrayList<EC2SecurityGroup> resultSecurityGroups = new ArrayList<EC2SecurityGroup>( opDescSecurityGroups.getResult()
        .size() );

      for( GroupDescription groupDesc : opDescSecurityGroups.getResult() ) {
        resultSecurityGroups.add( new EC2SecurityGroup( parent,
                                                        this.ec2Service,
                                                        groupDesc ) );
      }

      monitor.worked( 2 );
      monitor.done();
      return resultSecurityGroups.toArray( new EC2SecurityGroup[ resultSecurityGroups.size() ] );
    }
    return null;
  }

  /**
   * Fetches the list of remotely existing EC2 keypairs and transforms them into
   * {@link EC2Keypair}s.
   * 
   * @param parent the parent housing this {@link EC2InfoService}
   * @param vo the vo providing the {@link EC2InfoService}
   * @param monitor the monitor to be informed of progress
   * @return the resulting array of {@link EC2Keypair}s
   */
  private EC2Keypair[] fetchKeypairs( final IGridContainer parent,
                                      final IVirtualOrganization vo,
                                      IProgressMonitor monitor )
  {
    if( monitor == null ) {
      monitor = new NullProgressMonitor();
    }

    // fetch existing security groups from EC2 service
    monitor.beginTask( Messages.getString( "EC2InfoService.monitor_task_description" ), //$NON-NLS-1$
                       2 );

    EC2OpDescribeKeypairs opDescKeypairs = new EC2OpDescribeKeypairs( getEc2() );
    new OperationExecuter().execOp( opDescKeypairs );

    monitor.worked( 1 );

    if( opDescKeypairs.getException() == null ) {
      // transform answer into gEclipse format
      ArrayList<EC2Keypair> resultKeypairs = new ArrayList<EC2Keypair>( opDescKeypairs.getResult()
        .size() );

      for( KeyPairInfo keyPairInfo : opDescKeypairs.getResult() ) {
        resultKeypairs.add( new EC2Keypair( parent, this.ec2Service, keyPairInfo ) );
      }

      monitor.worked( 2 );
      monitor.done();
      return resultKeypairs.toArray( new EC2Keypair[ resultKeypairs.size() ] );
    }
    return null;
  }

  /**
   * Fetches the list of remotely existing Elastic IP addresses and transforms
   * them into {@link EC2ElasticIPAddress}s.
   * 
   * @param parent the parent housing this {@link EC2InfoService}
   * @param vo the vo providing the {@link EC2InfoService}
   * @param monitor the monitor to be informed of progress
   * @return the resulting array of {@link EC2ElasticIPAddress}s
   */
  private EC2ElasticIPAddress[] fetchElasticIPs( final IGridContainer parent,
                                                 final IVirtualOrganization vo,
                                                 IProgressMonitor monitor )
  {
    if( monitor == null ) {
      monitor = new NullProgressMonitor();
    }

    // fetch existing addresses from EC2 service
    monitor.beginTask( Messages.getString( "EC2InfoService.monitor_task_description" ), //$NON-NLS-1$
                       2 );

    EC2OpDescribeAddresses opDescAddresses = new EC2OpDescribeAddresses( getEc2() );
    new OperationExecuter().execOp( opDescAddresses );

    monitor.worked( 1 );

    if( opDescAddresses.getException() == null ) {
      // transform answer into gEclipse format
      ArrayList<EC2ElasticIPAddress> resultAddresses = new ArrayList<EC2ElasticIPAddress>( opDescAddresses.getResult()
        .size() );

      for( AddressInfo addressInfo : opDescAddresses.getResult() ) {
        resultAddresses.add( new EC2ElasticIPAddress( parent,
                                                      this.ec2Service,
                                                      addressInfo ) );
      }

      monitor.worked( 2 );
      monitor.done();
      return resultAddresses.toArray( new EC2ElasticIPAddress[ resultAddresses.size() ] );
    }
    return null;
  }

  /**
   * Fetches an IEC2 instance from the {@link EC2Registry}. The initiator of
   * the fetch process is the {@link AWSVirtualOrganization} with its contained
   * aws access id, which is used for the mapping from the user to an instance
   * of an {@link IEC2}.
   * 
   * @return the obtained {@link IEC2} instance
   */
  private IEC2 getEc2() {
    IEC2 ec2 = null;
    try {
      EC2Registry ec2Registry = EC2Registry.getRegistry();
      ec2 = ec2Registry.getEC2( this.awsVo );
    } catch( ProblemException problemEx ) {
      Activator.log( "Could not obtain EC2 instance", problemEx ); //$NON-NLS-1$
    }
    return ec2;
  }

  /**
   * Fetches all running instances from the EC2 service.
   * 
   * @param parent the parent in the grid model
   * @param vo the VO to use for accessing the services
   * @param monitor a monitor to track progress
   * @return an array of fetched {@link EC2Instance}
   */
  public IGridResource[] fetchInstances( final IGridContainer parent,
                                         final IVirtualOrganization vo,
                                         IProgressMonitor monitor )
  {
    if( monitor == null ) {
      monitor = new NullProgressMonitor();
    }

    // fetch existing instances from EC2 service
    monitor.beginTask( Messages.getString( "EC2InfoService.monitor_task_description" ), //$NON-NLS-1$
                       2 );

    EC2OpDescribeInstances opDescInstances = new EC2OpDescribeInstances( getEc2() );
    new OperationExecuter().execOp( opDescInstances );
    monitor.worked( 1 );

    if( opDescInstances.getException() == null ) {
      // transform answer into gEclipse format
      ArrayList<EC2Instance> resultComputingElements = new ArrayList<EC2Instance>( opDescInstances.getResult()
        .size() );

      for( ReservationDescription reservationDesc : opDescInstances.getResult() )
      {
        for( Instance instance : reservationDesc.getInstances() ) {
          resultComputingElements.add( new EC2Instance( parent,
                                                        this.ec2Service,
                                                        instance ) );
        }
      }

      monitor.worked( 2 );
      monitor.done();
      return resultComputingElements.toArray( new IGridResource[ resultComputingElements.size() ] );
    }
    return null;
  }

  /**
   * Fetches the available AMIs of the EC2 service using the owner as a filter.
   * 
   * @param parent the parent of this {@link IGridInfoService}
   * @param vo the vo initiating the query
   * @param monitor the monitor to notify of progress
   * @param operation the operation to execute
   * @return an array of {@link EC2AMIImage}s.
   */
  public IGridResource[] fetchImages( final IGridContainer parent,
                                      final IVirtualOrganization vo,
                                      IProgressMonitor monitor,
                                      final AbstractEC2OpDescribeImages operation )
  {
    if( monitor == null ) {
      monitor = new NullProgressMonitor();
    }

    // fetch existing AMI images from EC2 service
    monitor.beginTask( Messages.getString( "EC2InfoService.monitor_task_description" ), //$NON-NLS-1$
                       2 );

    new OperationExecuter().execOp( operation );

    monitor.worked( 1 );
    if( operation.getException() == null ) {
      // transform answer into gEclipse format
      ArrayList<IGridResource> resultGridService = new ArrayList<IGridResource>( operation.getResult()
        .size() );

      for( ImageDescription imageDescription : operation.getResult() ) {
        resultGridService.add( new EC2AMIImage( parent,
                                                this.ec2Service,
                                                imageDescription ) );
      }

      monitor.worked( 2 );
      monitor.done();
      return resultGridService.toArray( new IGridResource[ resultGridService.size() ] );
    }
    return null;
  }

  public String getHostName() {
    try {
      EC2ServiceProperties properties = this.ec2Service.getProperties();
      if( properties != null ) {
        return properties.getEc2Url();
      }
    } catch( ProblemException problemEx ) {
      Activator.log( "Could not load properties from ec2Service", problemEx ); //$NON-NLS-1$
    }
    return null;
  }

  public URI getURI() {
    String hostName = getHostName();
    try {
      if( hostName != null ) {
        return new URI( hostName );
      }
    } catch( URISyntaxException uriEx ) {
      Activator.log( "Could not creat URI from " + hostName, uriEx ); //$NON-NLS-1$
    }
    return null;
  }

  public IFileStore getFileStore() {
    return getParent().getFileStore().getChild( EC2InfoService.STORAGE_NAME );
  }

  public String getName() {
    return EC2InfoService.STORAGE_NAME;
  }

  public IGridContainer getParent() {
    return this.ec2Service;
  }

  public IPath getPath() {
    return getParent().getPath().append( EC2InfoService.STORAGE_NAME );
  }

  public IResource getResource() {
    return null;
  }

  public boolean isLocal() {
    return false;
  }

}
