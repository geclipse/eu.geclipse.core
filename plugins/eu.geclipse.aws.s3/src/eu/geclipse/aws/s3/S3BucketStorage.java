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
import eu.geclipse.aws.s3.service.S3AWSService;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridStorage;
import eu.geclipse.core.model.impl.AbstractGridElement;

/**
 * This {@link IGridStorage} element wraps an {@link S3Bucket}.
 * 
 * @author Moritz Post
 */
public class S3BucketStorage extends AbstractGridElement
  implements IGridStorage
{

  /** This {@link IAWSService} provides the connection details required. */
  private S3AWSService s3AWSService;

  /** The {@link S3Bucket} contains the bucket data from the S3 system. */
  private S3Bucket s3Bucket;

  /**
   * Create a new {@link S3BucketStorage} with the given {@link S3AWSService} as
   * the host and the {@link S3Bucket} as meta data provider.
   * 
   * @param s3AWSService the housing service
   * @param bucket the bucket to fetch data from
   */
  public S3BucketStorage( final S3AWSService s3AWSService, final S3Bucket bucket )
  {
    this.s3AWSService = s3AWSService;
    this.s3Bucket = bucket;
  }

  public URI[] getAccessTokens() {
    StringBuilder sb = new StringBuilder( "s3://" ); //$NON-NLS-1$
    sb.append( IS3Constants.S3_ROOT );
    sb.append( "/" ); //$NON-NLS-1$
    sb.append( getName() );

    URI accessToken = null;
    try {
      accessToken = new URI( sb.toString() );
    } catch( URISyntaxException exc ) {
      Activator.log( exc );
    }
    return new URI[]{
      accessToken
    };
  }

  public String getName() {
    return this.s3Bucket.getName();
  }

  public String getHostName() {
    try {
      String s3Url = this.s3AWSService.getProperties().getS3Url();
      URL url = new URL( s3Url );
      return url.getHost();
    } catch( GridModelException gridModelEx ) {
      Activator.log( "Could not obtain properties from s3AWSService", //$NON-NLS-1$
                     gridModelEx );
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
    } catch( GridModelException gridModelEx ) {
      Activator.log( "Could not obtain properties from awsVo", gridModelEx ); //$NON-NLS-1$
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
    return this.s3AWSService;
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

}
