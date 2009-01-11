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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.net.proxy.IProxyChangeEvent;
import org.eclipse.core.net.proxy.IProxyChangeListener;
import org.eclipse.core.net.proxy.IProxyData;
import org.eclipse.core.net.proxy.IProxyService;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.osgi.framework.Bundle;
import org.osgi.util.tracker.ServiceTracker;

import com.xerox.amazonws.ec2.AddressInfo;
import com.xerox.amazonws.ec2.AvailabilityZone;
import com.xerox.amazonws.ec2.DescribeImageAttributeResult;
import com.xerox.amazonws.ec2.EC2Exception;
import com.xerox.amazonws.ec2.GroupDescription;
import com.xerox.amazonws.ec2.ImageDescription;
import com.xerox.amazonws.ec2.ImageListAttribute;
import com.xerox.amazonws.ec2.Jec2;
import com.xerox.amazonws.ec2.KeyPairInfo;
import com.xerox.amazonws.ec2.LaunchConfiguration;
import com.xerox.amazonws.ec2.ReservationDescription;
import com.xerox.amazonws.ec2.TerminatingInstanceDescription;
import com.xerox.amazonws.ec2.ImageAttribute.ImageAttributeType;
import com.xerox.amazonws.ec2.Jec2.ImageListAttributeOperationType;

import eu.geclipse.aws.auth.AWSAuthTokenDescription;
import eu.geclipse.aws.ec2.internal.Activator;
import eu.geclipse.aws.ec2.internal.Messages;
import eu.geclipse.aws.ec2.op.AMILaunchConfiguration;
import eu.geclipse.aws.ec2.service.EC2Service;
import eu.geclipse.aws.vo.AWSVirtualOrganization;
import eu.geclipse.core.auth.AbstractAuthTokenProvider;
import eu.geclipse.core.auth.AuthTokenRequest;
import eu.geclipse.core.auth.IAuthenticationToken;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.reporting.ProblemException;

/**
 * This class wraps the amazon ec2 webservice functionality provided via the
 * plugin org.xerox.amazonws and the typica library.
 * <p>
 * typica: <a
 * href="http://code.google.com/p/typica/">http://code.google.com/p/typica/</a>
 * 
 * @author Moritz Post
 */
public class EC2 implements IEC2, IProxyChangeListener {

  /** The HTTPS protocol: "https" */
  private static final String HTTPS_PROTOCOL = "https"; //$NON-NLS-1$

  /** The instance of the AWS EC2 library. */
  private Jec2 jec2;

  /** The URL to connect to the EC2 webservice */
  private String ec2Url;

  /** The Service tracker used to track the {@link IProxyService}. */
  private ServiceTracker tracker;

  /** The aws access id to bind this EC2 instance to. */
  private String awsAccessId;

  /** The initial VO to be used in the auth token wizard. */
  private AWSVirtualOrganization awsVo;

  /**
   * Create a new Instance of the EC2 class, bound to the passed aws access id.
   * 
   * @param awsAccessId the aws access id to bind to
   */
  EC2( final String awsAccessId ) {
    this.awsAccessId = awsAccessId;
    Bundle bundle = Activator.getDefault().getBundle();
    this.tracker = new ServiceTracker( bundle.getBundleContext(),
                                       IProxyService.class.getName(),
                                       null );
    this.tracker.open();
    getProxyService().addProxyChangeListener( this );
    updateProxySettings();
  }

  /**
   * Creates a new EC2 instance with the {@link AWSVirtualOrganization} as the
   * initial {@link IVirtualOrganization}. The constructor extracts the aws
   * access id from the aws vo and invokes the {@link #EC2(String)} constructor.
   * <p>
   * Additionally the aws vo is stored to be used as the initial VO when trying
   * to create a new {@link AWSAuthTokenDescription}. Thereby there is no need
   * to explicitly select a VO in the auth token wizard.
   * 
   * @param awsVo the vo to extract the aws access id from and to use as the
   *          initial vo when creating an {@link AWSAuthTokenDescription}.
   * @throws ProblemException a problem which might arise when accessing the AWS
   *           VO.
   */
  public EC2( final AWSVirtualOrganization awsVo ) throws ProblemException {
    this( awsVo.getProperties().getAwsAccessId() );
    this.awsVo = awsVo;
    this.awsAccessId = awsVo.getProperties().getAwsAccessId();
  }

  public boolean initEc2( final String ec2Url,
                          final String awsAccessId,
                          final String awsSecretId ) throws EC2ServiceException
  {
    if( ec2Url == null || awsAccessId == null || awsSecretId == null ) {
      this.ec2Url = null;
      this.jec2 = null;
      throw new EC2ServiceException( "Missing required initialization parameter" ); //$NON-NLS-1$
    }
    this.ec2Url = ec2Url;

    URL url;
    try {
      url = new URL( ec2Url );
    } catch( MalformedURLException e ) {
      throw new EC2ServiceException( "Could not validate ec2 url", e ); //$NON-NLS-1$
    }
    String host = url.getHost();

    boolean secure = false;
    if( url.getProtocol().equals( EC2.HTTPS_PROTOCOL ) ) {
      secure = true;
    }

    int port = url.getPort();
    if( port == -1 ) {
      port = secure
                   ? 443
                   : 80;
    }

    this.jec2 = new Jec2( awsAccessId, awsSecretId, secure, host, port );
    updateProxySettings();
    return true;
  }

  /**
   * Return the {@link IProxyService} or <code>null</code> if the service is not
   * available.
   * 
   * @return the {@link IProxyService} or <code>null</code>
   */
  private IProxyService getProxyService() {
    return ( IProxyService )this.tracker.getService();
  }

  public void proxyInfoChanged( final IProxyChangeEvent event ) {
    updateProxySettings();
  }

  /**
   * Update the proxy settings of the {@link Jec2} class.
   */
  private void updateProxySettings() {
    if( this.jec2 == null ) {
      return;
    }

    IProxyService proxyService = ( IProxyService )this.tracker.getService();
    boolean enabled = proxyService.isProxiesEnabled();

    if( enabled ) {
      IProxyData proxyData = proxyService.getProxyData( IProxyData.HTTP_PROXY_TYPE );
      String host = proxyData.getHost();
      int port = proxyData.getPort();
      String userName = proxyData.getUserId();
      String userPassword = proxyData.getPassword();

      this.jec2.setProxyValues( host, port, userName, userPassword );
    } else {
      this.jec2.setProxyValues( null, 0, null, null, null );
    }
  }

  public boolean isInitialized() {
    if( this.jec2 != null ) {
      return true;
    }
    return false;
  }

  /**
   * This method tries to obtain an {@link AWSAuthTokenDescription} to extract
   * the AWS credentials. If no such token exists, the user is asked whether he
   * wants to create one. In the case that no token could be found or created
   * the method throws an {@link EC2ServiceException}.
   * <p>
   * Additionally, in case of a valid token this {@link IEC2} instance is
   * initiated with the obtained AWS credentials by invoking
   * {@link #initEc2(String, String, String)}.
   * 
   * @return if the {@link EC2} could be initialized successfully
   * @throws EC2ServiceException the exception is thrown when the AWS
   *           credentials could not be obtained
   */
  public boolean ensureAuthentication() throws EC2ServiceException {
    // check for an existing token or create a new one
    AWSAuthTokenDescription authTokenDescription = new AWSAuthTokenDescription( this.awsAccessId );
    authTokenDescription.setAwsVo( this.awsVo );
    AuthTokenRequest request = new AuthTokenRequest( authTokenDescription,
                                                     Messages.getString( "EC2.dialog_auth_confirmation_title" ), //$NON-NLS-1$
                                                     Messages.getString( "EC2.dialog_auth_confirmation_description" ) ); //$NON-NLS-1$

    IAuthenticationToken authToken;
    try {
      authToken = AbstractAuthTokenProvider.staticRequestToken( request );
    } catch( ProblemException pExc ) {
      throw new EC2ServiceException( "No valid authentication token could be created (might be user canceled).", pExc ); //$NON-NLS-1$
    }

    // no valid connection credentials existing
    if( authToken == null ) {
      throw new EC2ServiceException( "No valid authentication token could be created (might be user canceled)." ); //$NON-NLS-1$
    }

    // fetch connection information
    AWSAuthTokenDescription awsDesc = ( AWSAuthTokenDescription )authToken.getDescription();
    String ec2Url = null;
    List<EC2Service> ec2ServiceList;
    try {
      ec2ServiceList = awsDesc.getAwsVo()
        .getChildren( new NullProgressMonitor(), EC2Service.class );

      EC2Service ec2Service = null;
      if( ec2ServiceList != null ) {
        ec2Service = ec2ServiceList.get( 0 );
      }
      if( ec2Service != null ) {
        ec2Url = ec2Service.getProperties().getEc2Url();
      }
    } catch( ProblemException problemEx ) {
      throw new EC2ServiceException( "Could not get ec2url from awsVo via ec2Service", //$NON-NLS-1$
                                     problemEx );
    }
    return initEc2( ec2Url, awsDesc.getAwsAccessId(), awsDesc.getAwsSecretId() );
  }

  /**
   * Checks to see if the provided list is <code>null</code>. If it is
   * <code>null</code> a new {@link ArrayList} with the provided type is created
   * and returned. If the list is not <code>null</code> the current list is
   * returned.
   * 
   * @param <T> the type of the list elements
   * @param list the list to check for the <code>null</code> reference
   * @return the inputed list or a new empty {@link ArrayList}
   */
  private <T> List<T> nullCheck( final List<T> list ) {
    return list == null
                       ? new ArrayList<T>()
                       : list;
  }

  public List<ImageDescription> describeImagesByOwner( List<String> ownerList )
    throws EC2ServiceException
  {
    ensureAuthentication();

    ownerList = nullCheck( ownerList );

    try {
      return this.jec2.describeImagesByOwner( ownerList );
    } catch( EC2Exception ec2Ex ) {
      throw getEC2ServiceException( "describeImagesByOwner", ec2Ex ); //$NON-NLS-1$
    }
  }

  public List<ImageDescription> describeImagesByExec( List<String> execList )
    throws EC2ServiceException
  {
    ensureAuthentication();

    execList = nullCheck( execList );

    try {
      return this.jec2.describeImagesByExecutability( execList );
    } catch( EC2Exception ec2Ex ) {
      throw getEC2ServiceException( "describeImagesByExecutability", ec2Ex ); //$NON-NLS-1$
    }
  }

  public ReservationDescription runInstances( final AMILaunchConfiguration launchConfig )
    throws EC2ServiceException
  {
    ensureAuthentication();

    LaunchConfiguration lc = new LaunchConfiguration( launchConfig.getAmiId() );

    lc.setMinCount( launchConfig.getMinCount() );
    lc.setMaxCount( launchConfig.getMaxCount() );
    lc.setSecurityGroup( launchConfig.getSecurityGroup() );
    lc.setUserData( launchConfig.getUserData() );
    lc.setKeyName( launchConfig.getKeyName() );
    lc.setInstanceType( launchConfig.getInstanceType() );
    lc.setAvailabilityZone( launchConfig.getZone() );
    lc.setRamdiskId( launchConfig.getRamdiskId() );
    lc.setKernelId( launchConfig.getKernelId() );
    lc.setBlockDevicemappings( launchConfig.getBlockDeviceMappings() );

    try {
      return this.jec2.runInstances( lc );
    } catch( EC2Exception ec2Ex ) {
      throw getEC2ServiceException( "runInstances", ec2Ex ); //$NON-NLS-1$
    }
  }

  public List<GroupDescription> describeSecurityGroups( List<String> securityGroups )
    throws EC2ServiceException
  {
    ensureAuthentication();

    securityGroups = nullCheck( securityGroups );

    try {
      return this.jec2.describeSecurityGroups( securityGroups );
    } catch( EC2Exception ec2Ex ) {
      throw getEC2ServiceException( "describeSecurityGroups", ec2Ex ); //$NON-NLS-1$
    }
  }

  public List<AvailabilityZone> describeAvailabilityZones( List<String> zones )
    throws EC2ServiceException
  {
    ensureAuthentication();

    zones = nullCheck( zones );

    try {
      return this.jec2.describeAvailabilityZones( zones );
    } catch( EC2Exception ec2Ex ) {
      throw getEC2ServiceException( "describeAvailabilityZones", ec2Ex ); //$NON-NLS-1$
    }
  }

  public List<KeyPairInfo> describeKeypairs( List<String> keypairs )
    throws EC2ServiceException
  {
    ensureAuthentication();

    keypairs = nullCheck( keypairs );

    try {
      return this.jec2.describeKeyPairs( keypairs );
    } catch( EC2Exception ec2Ex ) {
      throw getEC2ServiceException( "describeKeyPairs", ec2Ex ); //$NON-NLS-1$
    }
  }

  public List<ReservationDescription> describeInstances( List<String> instanceList )
    throws EC2ServiceException
  {
    ensureAuthentication();

    instanceList = nullCheck( instanceList );

    try {
      return this.jec2.describeInstances( instanceList );
    } catch( EC2Exception ec2Ex ) {
      throw getEC2ServiceException( "describeInstances", ec2Ex ); //$NON-NLS-1$
    }
  }

  public List<TerminatingInstanceDescription> terminateInstances( List<String> instanceList )
    throws EC2ServiceException
  {
    ensureAuthentication();

    instanceList = nullCheck( instanceList );

    try {
      return this.jec2.terminateInstances( instanceList );
    } catch( EC2Exception ec2Ex ) {
      throw getEC2ServiceException( "terminateInstances", ec2Ex ); //$NON-NLS-1$
    }
  }

  public void authorizeSecurityGroup( final String groupName,
                                      final String cidrIp,
                                      final String ipProtocol,
                                      final int fromPort,
                                      final int toPort )
    throws EC2ServiceException
  {
    ensureAuthentication();

    try {
      this.jec2.authorizeSecurityGroupIngress( groupName,
                                               ipProtocol,
                                               fromPort,
                                               toPort,
                                               cidrIp );
    } catch( EC2Exception ec2Ex ) {
      throw getEC2ServiceException( "authorizeSecurityGroupIngress", ec2Ex ); //$NON-NLS-1$
    }
  }

  public void authorizeSecurityGroup( final String groupName,
                                      final String secGroupName,
                                      final String secGroupOwnerId )
    throws EC2ServiceException
  {
    ensureAuthentication();

    try {
      this.jec2.authorizeSecurityGroupIngress( groupName,
                                               secGroupName,
                                               secGroupOwnerId );
    } catch( EC2Exception ec2Ex ) {
      throw getEC2ServiceException( "authorizeSecurityGroupIngress", ec2Ex ); //$NON-NLS-1$
    }
  }

  public void revokeSecurityGroup( final String groupName,
                                   final String cidrIp,
                                   final String ipProtocol,
                                   final int fromPort,
                                   final int toPort )
    throws EC2ServiceException
  {
    ensureAuthentication();

    try {
      this.jec2.revokeSecurityGroupIngress( groupName,
                                            ipProtocol,
                                            fromPort,
                                            toPort,
                                            cidrIp );
    } catch( EC2Exception ec2Ex ) {
      throw getEC2ServiceException( "revokeSecurityGroupIngress", ec2Ex ); //$NON-NLS-1$
    }
  }

  public void revokeSecurityGroup( final String groupName,
                                   final String secGroupName,
                                   final String secGroupOwnerId )
    throws EC2ServiceException
  {
    ensureAuthentication();

    try {
      this.jec2.revokeSecurityGroupIngress( groupName,
                                            secGroupName,
                                            secGroupOwnerId );
    } catch( EC2Exception ec2Ex ) {
      throw getEC2ServiceException( "revokeSecurityGroupIngress", ec2Ex ); //$NON-NLS-1$
    }
  }

  public void deleteSecurityGroup( final String securityGroup )
    throws EC2ServiceException
  {
    ensureAuthentication();

    try {
      this.jec2.deleteSecurityGroup( securityGroup );
    } catch( EC2Exception ec2Ex ) {
      throw getEC2ServiceException( "deleteSecurityGroup", ec2Ex ); //$NON-NLS-1$
    }
  }

  @Override
  public boolean equals( final Object obj ) {
    if( obj instanceof EC2 ) {
      EC2 ec2 = ( EC2 )obj;
      if( ec2.awsAccessId.equals( this.awsAccessId ) ) {
        return true;
      }
    }
    return false;
  }

  /**
   * Creates an {@link EC2ServiceException} with a meaningful error message.
   * Also logs the message + exception.
   * 
   * @param origine the source of the exception
   * @param ec2Ex the exception to wrap
   * @return the newly created exception
   */
  private EC2ServiceException getEC2ServiceException( final String origine,
                                                      final EC2Exception ec2Ex )
  {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append( "Could not '" ); //$NON-NLS-1$
    stringBuilder.append( origine );
    stringBuilder.append( "' for " ); //$NON-NLS-1$
    stringBuilder.append( this.awsAccessId );
    stringBuilder.append( " @ " ); //$NON-NLS-1$
    stringBuilder.append( this.ec2Url );
    Activator.log( stringBuilder.toString(), ec2Ex );
    return new EC2ServiceException( stringBuilder.toString(), ec2Ex );
  }

  public void createSecurityGroup( final String securityGroupName,
                                   final String securityGroupDescription )
    throws EC2ServiceException
  {
    ensureAuthentication();

    try {
      this.jec2.createSecurityGroup( securityGroupName,
                                     securityGroupDescription );
    } catch( EC2Exception ec2Ex ) {
      throw getEC2ServiceException( "createSecurityGroup", ec2Ex ); //$NON-NLS-1$
    }

  }

  public KeyPairInfo createKeypair( final String keypairName )
    throws EC2ServiceException
  {
    ensureAuthentication();

    try {
      return this.jec2.createKeyPair( keypairName );
    } catch( EC2Exception ec2Ex ) {
      throw getEC2ServiceException( "createKeypair", ec2Ex ); //$NON-NLS-1$
    }
  }

  public String allocateAddress() throws EC2ServiceException {
    ensureAuthentication();

    try {
      return this.jec2.allocateAddress();
    } catch( EC2Exception ec2Ex ) {
      throw getEC2ServiceException( "allocateAddress", ec2Ex ); //$NON-NLS-1$
    }
  }

  public List<AddressInfo> describeAddresses( final List<String> addresses )
    throws EC2ServiceException
  {
    ensureAuthentication();

    try {
      return this.jec2.describeAddresses( addresses );
    } catch( EC2Exception ec2Ex ) {
      throw getEC2ServiceException( "describeAddresses", ec2Ex ); //$NON-NLS-1$
    }
  }

  public void associateAddress( final String instanceId, final String elasticIP )
    throws EC2ServiceException
  {
    ensureAuthentication();

    try {
      this.jec2.associateAddress( instanceId, elasticIP );
    } catch( EC2Exception ec2Ex ) {
      throw getEC2ServiceException( "associateAddress", ec2Ex ); //$NON-NLS-1$
    }

  }

  public void disassociateAddress( final String elasticIP )
    throws EC2ServiceException
  {
    ensureAuthentication();

    try {
      this.jec2.disassociateAddress( elasticIP );
    } catch( EC2Exception ec2Ex ) {
      throw getEC2ServiceException( "disassociateAddress", ec2Ex ); //$NON-NLS-1$
    }
  }

  public void releaseAddress( final String elasticIP )
    throws EC2ServiceException
  {
    ensureAuthentication();

    try {
      this.jec2.releaseAddress( elasticIP );
    } catch( EC2Exception ec2Ex ) {
      throw getEC2ServiceException( "releaseAddress", ec2Ex ); //$NON-NLS-1$
    }
  }

  public void rebootInstances( final List<String> instances )
    throws EC2ServiceException
  {
    ensureAuthentication();

    try {
      this.jec2.rebootInstances( instances );
    } catch( EC2Exception ec2Ex ) {
      throw getEC2ServiceException( "rebootInstances", ec2Ex ); //$NON-NLS-1$
    }
  }

  public String registerImage( final String bucketPath )
    throws EC2ServiceException
  {
    ensureAuthentication();

    try {
      return this.jec2.registerImage( bucketPath );
    } catch( EC2Exception ec2Ex ) {
      throw getEC2ServiceException( "registerImage", ec2Ex ); //$NON-NLS-1$
    }
  }

  public DescribeImageAttributeResult describeImageAttributes( final String imageId,
                                                               final ImageAttributeType imageAttribute )
    throws EC2ServiceException
  {
    ensureAuthentication();

    try {
      return this.jec2.describeImageAttribute( imageId, imageAttribute );
    } catch( EC2Exception ec2Ex ) {
      throw getEC2ServiceException( "describeImageAttribute", ec2Ex ); //$NON-NLS-1$
    }
  }

  public void modifyImageAttribute( final String imageId,
                                    final ImageListAttribute attribute,
                                    final ImageListAttributeOperationType operationType )
    throws EC2ServiceException
  {
    ensureAuthentication();

    try {
      this.jec2.modifyImageAttribute( imageId, attribute, operationType );
    } catch( EC2Exception ec2Ex ) {
      throw getEC2ServiceException( "modifyImageAttribute", ec2Ex ); //$NON-NLS-1$
    }
  }

  public void deleteKeypair( final String keypair ) throws EC2ServiceException {
    ensureAuthentication();

    try {
      this.jec2.deleteKeyPair( keypair );
    } catch( EC2Exception ec2Ex ) {
      throw getEC2ServiceException( "deleteKeypair", ec2Ex ); //$NON-NLS-1$
    }

  }
}