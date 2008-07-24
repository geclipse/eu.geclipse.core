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

package eu.geclipse.aws.s3;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.jets3t.service.model.S3Bucket;

import eu.geclipse.aws.IAWSService;
import eu.geclipse.aws.s3.internal.Activator;
import eu.geclipse.aws.s3.internal.Messages;
import eu.geclipse.aws.s3.service.S3AWSService;
import eu.geclipse.aws.vo.AWSVirtualOrganization;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridResource;
import eu.geclipse.core.model.IGridStorage;
import eu.geclipse.core.model.IMountable;
import eu.geclipse.core.model.impl.AbstractGridElement;
import eu.geclipse.core.reporting.ProblemException;

/**
 * This {@link IGridStorage} element wraps an {@link S3Bucket}.
 * 
 * @author Moritz Post
 */
public class S3BucketStorage extends AbstractGridElement
  implements IGridResource, IMountable
{

  /** The id of the s3 mount point. */
  public static final MountPointID MOUNT_ID = new MountPointID( Messages.getString( "S3BucketStorage.mount_action_title" ), //$NON-NLS-1$
                                                                IS3Constants.S3_SCHEME );

  /** This {@link IAWSService} provides the connection details required. */
  private S3AWSService s3AWSService;

  /** The {@link S3Bucket} contains the bucket data from the S3 system. */
  private S3Bucket s3Bucket;

  /** The parent of this {@link IGridElement} in the vo tree. */
  private IGridContainer parent;

  /**
   * Create a new {@link S3BucketStorage} with the given {@link S3AWSService} as
   * the host and the {@link S3Bucket} as meta data provider.
   * 
   * @param parent the parent of this {@link S3BucketStorage} in the vo tree
   * @param s3AWSService the housing service
   * @param bucket the bucket to fetch data from
   */
  public S3BucketStorage( final IGridContainer parent,
                          final S3AWSService s3AWSService,
                          final S3Bucket bucket )
  {
    this.parent = parent;
    this.s3AWSService = s3AWSService;
    this.s3Bucket = bucket;
  }

  public URI[] getAccessTokens() {
    URI accessToken = getMountURI();
    return new URI[]{
      accessToken
    };
  }

  /**
   * Creates an {@link URI} complying to the general mount paradigm of geclipse.
   * The created URI has the form:
   * <p>
   * <code>s3://&lt;awsAccessId&gt;/&lt;bucketName&gt;</code>
   * 
   * @return the {@link URI} to use for mount actions
   */
  private URI getMountURI() {
    StringBuilder sb = new StringBuilder( IS3Constants.S3_SCHEME );
    sb.append( "://" ); //$NON-NLS-1$
    AWSVirtualOrganization awsVo = ( AWSVirtualOrganization )this.s3AWSService.getParent();
    String awsAccessId = null;
    try {
      awsAccessId = awsVo.getProperties().getAwsAccessId();
    } catch( ProblemException problemEx ) {
      Activator.log( "Could not obtain aws properties", problemEx ); //$NON-NLS-1$
    }
    if( awsAccessId != null ) {
      sb.append( awsAccessId );
    }
    sb.append( IS3Constants.S3_PATH_SEPARATOR );
    sb.append( getName() );

    URI accessToken = null;
    try {
      accessToken = new URI( sb.toString() );
    } catch( URISyntaxException exc ) {
      Activator.log( exc );
    }
    return accessToken;
  }

  public String getName() {
    return this.s3Bucket.getName();
  }

  public String getHostName() {
    try {
      String s3Url = this.s3AWSService.getProperties().getS3Url();
      URL url = new URL( s3Url );
      return url.getHost();
    } catch( ProblemException problemEx ) {
      Activator.log( "Could not obtain properties from s3AWSService", //$NON-NLS-1$
                     problemEx );
    } catch( MalformedURLException malformedURLEx ) {
      Activator.log( "Could not convert URL from s3AWSService", malformedURLEx ); //$NON-NLS-1$
    }
    return null;
  }

  /**
   * This implementation creates an {@link URI} which provides direct access to
   * a S3 bucket.
   * <p>
   * For example: <code>https://s3.amazonaws.com/bucket</code>
   */
  public URI getURI() {
    String s3Url = null;
    try {
      s3Url = this.s3AWSService.getProperties().getS3Url();
      StringBuilder strBuilder = new StringBuilder( s3Url );

      if( !s3Url.endsWith( IS3Constants.S3_PATH_SEPARATOR ) ) {
        strBuilder.append( '/' );
      }
      s3Url = s3Url + this.s3Bucket.getName();
      return new URI( s3Url );
    } catch( ProblemException problemEx ) {
      Activator.log( "Could not obtain properties from awsVo", problemEx ); //$NON-NLS-1$
    } catch( URISyntaxException uriSyntaxEx ) {
      Activator.log( "Could not construct URI from awsVo: " + s3Url, //$NON-NLS-1$
                     uriSyntaxEx );
    }
    return null;
  }

  public IFileStore getFileStore() {
    return null;
  }

  public IGridContainer getParent() {
    return this.parent;
  }

  public IPath getPath() {
    return null;
  }

  public IResource getResource() {
    return null;
  }

  public boolean isLocal() {
    return false;
  }

  public MountPoint getMountPoint( final MountPointID mountID ) {
    MountPoint result = null;

    if( S3BucketStorage.MOUNT_ID.equals( mountID ) ) {
      URI uri = getMountURI();
      if( uri != null ) {
        result = new MountPoint( this.s3Bucket.getName(), uri );
      }
    }

    return result;
  }

  public MountPointID[] getMountPointIDs() {
    return new MountPointID[]{
      S3BucketStorage.MOUNT_ID
    };
  }
}
