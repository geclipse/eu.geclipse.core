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

package eu.geclipse.aws.s3.service;

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.jets3t.service.S3Service;
import org.jets3t.service.S3ServiceException;
import org.jets3t.service.model.S3Bucket;

import eu.geclipse.aws.AWSInfoService;
import eu.geclipse.aws.s3.S3BucketStorage;
import eu.geclipse.aws.s3.internal.Activator;
import eu.geclipse.aws.s3.internal.S3ServiceRegistry;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridApplication;
import eu.geclipse.core.model.IGridComputing;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridInfoService;
import eu.geclipse.core.model.IGridService;
import eu.geclipse.core.model.IGridStorage;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.model.impl.AbstractGridElement;
import eu.geclipse.core.reporting.ProblemException;

/**
 * The {@link S3InfoService}is used to provide S3 specific input to the
 * underlying {@link AWSInfoService}. Mainly this constitutes to provide input
 * for the "Storage" element.
 * 
 * @author Moritz Post
 * @see AWSInfoService
 */
public class S3InfoService extends AbstractGridElement
  implements IGridInfoService
{

  /** The name of the file to save this grid element in. */
  public static String STORAGE_NAME = ".s3InfoService"; //$NON-NLS-1$

  /** The underlying {@link S3Service}. */
  private S3AWSService s3Service;

  /**
   * Creates a new {@link S3InfoService} by directly setting the various fields
   * described in the parameter list.
   * 
   * @param s3Service the parent element to obtain various information from
   */
  public S3InfoService( final S3AWSService s3Service ) {
    this.s3Service = s3Service;
  }

  public IGridApplication[] fetchApplications( final IGridContainer parent,
                                               final IVirtualOrganization vo,
                                               final IGridComputing computing,
                                               final IProgressMonitor monitor )
  {
    // not supported at the moment
    return null;
  }

  public IGridComputing[] fetchComputing( final IGridContainer parent,
                                          final IVirtualOrganization vo,
                                          final IProgressMonitor monitor )
  {
    // not supported at the moment
    return null;
  }

  public IGridService[] fetchServices( final IGridContainer parent,
                                       final IVirtualOrganization vo,
                                       final IProgressMonitor monitor )
  {
    // not supported at the moment
    return null;
  }

  public IGridStorage[] fetchStorage( final IGridContainer parent,
                                      final IVirtualOrganization vo,
                                      IProgressMonitor monitor )
  {
    if( monitor == null ) {
      monitor = new NullProgressMonitor();
    }

    monitor.beginTask( Messages.getString( "S3InfoService.monitor_fetch_storage_title" ), 3 ); //$NON-NLS-1$

    S3ServiceRegistry s3ServiceRegistry = S3ServiceRegistry.getRegistry();
    S3BucketStorage[] s3BucketStorages = null;

    monitor.worked( 1 );

    try {
      S3Service service = s3ServiceRegistry.getService();

      if( service != null ) {
        S3Bucket[] buckets = service.listAllBuckets();
        monitor.worked( 1 );

        if( buckets != null ) {
          s3BucketStorages = new S3BucketStorage[ buckets.length ];

          for( int i = 0; i < buckets.length; i++ ) {
            s3BucketStorages[ i ] = new S3BucketStorage( this.s3Service,
                                                         buckets[ i ] );
          }
        }
        monitor.worked( 3 );
      }
    } catch( ProblemException probEx ) {
      Activator.log( "Could not obtain S3Service", probEx ); //$NON-NLS-1$
    } catch( S3ServiceException s3ServEx ) {
      Activator.log( "Could fetch list of buckets from S3Service", s3ServEx ); //$NON-NLS-1$
    } finally {
      monitor.done();
    }
    return s3BucketStorages;
  }

  public String getHostName() {
    try {
      S3ServiceProperties properties = this.s3Service.getProperties();
      if( properties != null ) {
        return properties.getS3Url();
      }
    } catch( GridModelException gridModelEx ) {
      Activator.log( "Could not load properties from s3AWSService", gridModelEx ); //$NON-NLS-1$
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
    return getParent().getFileStore().getChild( S3InfoService.STORAGE_NAME );
  }

  public String getName() {
    return S3InfoService.STORAGE_NAME;
  }

  public IGridContainer getParent() {
    return this.s3Service;
  }

  public IPath getPath() {
    return getParent().getPath().append( S3InfoService.STORAGE_NAME );
  }

  public IResource getResource() {
    return null;
  }

  public boolean isLocal() {
    return false;
  }

}
