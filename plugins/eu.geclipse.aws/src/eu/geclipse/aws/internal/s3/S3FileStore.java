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
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.aws.internal.s3;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.provider.FileInfo;
import org.eclipse.core.filesystem.provider.FileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.jets3t.service.S3Service;
import org.jets3t.service.S3ServiceException;
import org.jets3t.service.model.S3Bucket;
import org.jets3t.service.model.S3Object;

import eu.geclipse.aws.internal.Activator;
import eu.geclipse.aws.internal.IAwsProblems;
import eu.geclipse.aws.internal.ServiceRegistry;
import eu.geclipse.core.reporting.ProblemException;

/**
 * {@link IFileStore} implementation for the amazon S3 service. 
 */
public class S3FileStore
    extends FileStore
    implements IFileStore {
  
  /**
   * The parent node of this store. <code>null</code> for root stores.
   */
  private S3FileStore storeParent;
  
  /**
   * The name of this store.
   */
  private String storeName;
  
  /**
   * This store's file info or <code>null</code> if not yet available.
   */
  private FileInfo storeInfo;
  
  /**
   * Create a root node for the account with the specified access key ID.
   * 
   * @param accessKeyID The ID of the account.
   */
  public S3FileStore( final String accessKeyID ) {
    this( null, accessKeyID );
  }
  
  /**
   * Create a child node with the specified name.
   * 
   * @param parent The parent node of this node.
   * @param name The name of this node.
   */
  public S3FileStore( final S3FileStore parent, final String name ) {
    this.storeParent = parent;
    this.storeName = name;
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.filesystem.provider.FileStore#childNames(int, org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public String[] childNames( final int options, final IProgressMonitor monitor )
      throws CoreException {
    
    String[] result = new String[0];
    S3Service service = getService();
    
    if ( service != null ) { 
      if ( isRoot() ) {
        result = childNamesFromRoot( service );
      } else if ( isBucket() ) {
        result = childNamesFromBucket( service, getName() );
      }
    }
    
    return result;
    
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.core.filesystem.provider.FileStore#delete(int, org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public void delete( final int options, final IProgressMonitor monitor )
      throws CoreException {
    
    try {
      if ( isBucket() ) {
        S3Service service = getService();
        S3Bucket bucket = getBucket();
        service.deleteBucket( bucket );
      } else if ( isObject() ) {
        S3Service service = getService();
        S3Bucket bucket = getBucket();
        service.deleteObject( bucket, getName() );
      }
    } catch ( S3ServiceException s3Exc ) {
      throw new ProblemException( IAwsProblems.S3_DELETE_FAILED, s3Exc, Activator.PLUGIN_ID );
    } finally {
      internalFetchInfo().setExists( false );
    }
    
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.filesystem.provider.FileStore#fetchInfo(int, org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public IFileInfo fetchInfo( final int options, final IProgressMonitor monitor )
      throws CoreException {
    return internalFetchInfo();
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.core.filesystem.provider.FileStore#getChild(java.lang.String)
   */
  @Override
  public IFileStore getChild( final String name ) {
    return new S3FileStore( this, name );
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.filesystem.provider.FileStore#getName()
   */
  @Override
  public String getName() {
    return this.storeName;
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.filesystem.provider.FileStore#getParent()
   */
  @Override
  public IFileStore getParent() {
    return this.storeParent;
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.core.filesystem.provider.FileStore#mkdir(int, org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public IFileStore mkdir( final int options, final IProgressMonitor monitor )
      throws CoreException {
    
    if ( ! isBucket() ) {
      throw new ProblemException( IAwsProblems.S3_BUCKET_IN_BUCKET_FAILED, Activator.PLUGIN_ID );
    }
    
    S3Service service = getService();
    try {
      service.createBucket( getName() );
      this.storeInfo = null;
    } catch ( S3ServiceException s3Exc ) {
      ProblemException pExc
        = new ProblemException( IAwsProblems.S3_BUCKET_CREATION_FAILED, s3Exc, Activator.PLUGIN_ID );
      throw pExc;
    }
    
    return this;
    
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.core.filesystem.provider.FileStore#openInputStream(int, org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public InputStream openInputStream( final int options, final IProgressMonitor monitor )
      throws CoreException {
    
    InputStream result = null;
    S3Object object = getObject();
    
    if ( object != null ) {
      try {
        result = object.getDataInputStream();
      } catch ( S3ServiceException s3Exc ) {
        throw new ProblemException( IAwsProblems.S3_INPUT_FAILED, s3Exc, Activator.PLUGIN_ID );
      }
    }
    
    internalFetchInfo().setExists( true );
    
    return result;
    
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.core.filesystem.provider.FileStore#openOutputStream(int, org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public OutputStream openOutputStream( final int options, final IProgressMonitor monitor)
      throws CoreException {
    
    PipedOutputStream result = null;
    
    if ( isObject() ) {
      
      try {
        
        final S3Service service = getService();
        final S3Bucket bucket = getBucket();
      
        result = new PipedOutputStream();
        final PipedOutputStream pos = result;
        final PipedInputStream pis = new PipedInputStream( result );

        final S3Object object = new S3Object( getName() );
        object.setDataInputStream( pis );
        
        Thread worker = new Thread( new Runnable() {
          public void run() {
            try {
              service.putObject( bucket, object );
              pos.close();
              pis.close();
            } catch ( S3ServiceException s3Exc ) {
              try {
                internalFetchInfo().setExists( false );
              } catch ( ProblemException pExc ) {
                Activator.logException( pExc );
              }
              Activator.logException( s3Exc );
            } catch ( IOException ioExc ) {
              Activator.logException( ioExc );
            }
          }
        } );
        worker.start();
        internalFetchInfo().setExists( true );
        
      } catch ( IOException ioExc ) {
        throw new ProblemException( IAwsProblems.S3_OUTPUT_FAILED, ioExc, Activator.PLUGIN_ID );
      }
      
    }
    
    return result;
    
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.core.filesystem.provider.FileStore#toURI()
   */
  @Override
  public URI toURI() {
    
    String authority = getName();
    String path = null;
    IFileStore par = getParent();
    
    if ( par != null ) {
      URI parURI = par.toURI();
      authority = parURI.getAuthority();
      path = parURI.getPath();
      if ( ( path == null ) || path.length() == 0 ) {
        path = IS3Constants.S3_PATH_SEPARATOR + getName();
      } else {
        path += IS3Constants.S3_PATH_SEPARATOR + getName();
      }
    } else {
      authority = getAccessKeyID();
    }
    
    URI result = null;
    try {
      result = new URI( IS3Constants.S3_SCHEME, authority, path, null, null );
    } catch ( URISyntaxException uriExc ) {
      Activator.logException( uriExc );
    }
    
    return result;
    
  }
  
  /**
   * Fetch this node's info.
   * 
   * @throws ProblemException If the info retrieval from the
   * service failed.
   */
  protected FileInfo internalFetchInfo()
      throws ProblemException {
    
    if ( this.storeInfo == null ) {
      
      this.storeInfo = new FileInfo();
      S3Service service = ServiceRegistry.getRegistry().getService( getAccessKeyID(), false );
      
      this.storeInfo.setName( getName() );
      this.storeInfo.setDirectory( isDirectory() );
      this.storeInfo.setExists( false );
      
      if ( isRoot() ) {
        this.storeInfo.setExists( service != null );
      } else if ( isBucket() ) {
        try {
          boolean exists = ( service != null ) && service.isBucketAccessible( getName() );
          this.storeInfo.setExists( exists );
          this.storeInfo.setDirectory( exists );
        } catch ( S3ServiceException s3Exc ) {
          // Just ignore and leave exists to be false
        }
      } else if ( isObject() ) {
        S3Object object = getObject();
        this.storeInfo.setExists( object != null );
        if ( object != null ) {
          this.storeInfo.setLastModified( object.getLastModifiedDate().getTime() );
          this.storeInfo.setLength( object.getContentLength() );
        }
      }
      
    }
    
    return this.storeInfo;
    
  }
  
  /**
   * Determine if this store corresponds to a S3 bucket, i.e. a directory.
   * 
   * @return True if this store is a bucket.
   */
  private boolean isBucket() {
    return ( getParent() != null ) && ( getParent().getParent() == null );
  }
  
  /**
   * Determine if this store corresponds to a S3 object, i.e. a file.
   * 
   * @return True if this store is an object.
   */
  private boolean isObject() {
    return ( ! isRoot() ) && ( ! isBucket() ); 
  }
  
  /**
   * Determine if this store corresponds to a S3 account.
   * 
   * @return True if this store is an account.
   */
  private boolean isRoot() {
    return getParent() == null;
  }
  
  /**
   * Get the access key ID of this node.
   * 
   * @return This node's access key, i.e. the access key of the root node.
   */
  private String getAccessKeyID() {
    return isRoot() ? this.storeName : ( ( S3FileStore ) getParent() ).getAccessKeyID();
  }
  
  /**
   * Get the bucket corresponding to this node or the underlying node.
   * 
   * @return The bucket or <code>null<code> if this is a root node.
   */
  private S3Bucket getBucket() {
    
    S3Bucket result = null;
    
    if ( isBucket() ) {
      result = new S3Bucket( getName() );
    } else if ( isObject() ) {
      result = ( ( S3FileStore ) getParent() ).getBucket();
    }
    
    return result;
    
  }
  
  /**
   * Get the object corresponding to this node.
   * 
   * @return The object or <code>null</code> if this node represents
   * a root or a bucket.
   * @throws ProblemException If the object could not be retrieved.
   */
  private S3Object getObject()
      throws ProblemException {
    
    S3Object result = null;
    
    if ( isObject() ) {
      S3Service service = getService();
      S3Bucket bucket = getBucket();
      try {
        result = service.getObject( bucket, getName() );
      } catch ( S3ServiceException s3Exc ) {
        throw new ProblemException( IAwsProblems.S3_OBJECT_LOAD_FAILED, s3Exc, Activator.PLUGIN_ID ); 
      }
    }
    
    return result;
    
  }
  
  /**
   * Get the service that is used to perform operations on this node. If
   * the service is not yet created it will be created by this method and
   * cached for later use.
   * 
   * @return This node's service.
   * @throws ProblemException If the service was not already in the cache
   * and its creation failed.
   */
  private S3Service getService()
      throws ProblemException {
    return ServiceRegistry.getRegistry().getService( getAccessKeyID(), true );
  }
  
  /**
   * Determines if this node may be seen as a directory.
   * 
   * @return True if this node is either a root or a bucket.
   */
  private boolean isDirectory() {
    return ! isObject();
  }
  
  /**
   * Get the names of the buckets found for the specified service.
   * 
   * @param service The service to be queried.
   * @return The names of all buckets contained in the specified service.
   * @throws ProblemException If an error occurred during child retrieval.
   */
  private static String[] childNamesFromRoot( final S3Service service )
      throws ProblemException {
    
    String[] result = new String[ 0 ];
    
    try {
    
      S3Bucket[] buckets = service.listAllBuckets();
      
      if ( buckets != null ) {
      
        result = new String[ buckets.length ];
    
        for ( int i = 0 ; i < buckets.length ; i++ ) {
          result[ i ] = buckets[ i ].getName();
        }
        
      }
      
    } catch ( S3ServiceException s3sExc ) {
      throw new ProblemException(
          IAwsProblems.S3_LIST_FAILED,
          s3sExc,
          Activator.PLUGIN_ID );
    }
    
    return result;
    
  }
  
  /**
   * Get the names of all objects contained in the specified bucket using the
   * specified service.
   *  
   * @param service The service to be used.
   * @param bucketName The name of the bucket to list.
   * @return The names of all children found in the specified bucket.
   * @throws ProblemException If an error occurred during child retrieval. 
   */
  private static String[] childNamesFromBucket( final S3Service service, final String bucketName )
      throws ProblemException {
    
    String[] result = new String[ 0 ];
    
    try {
      
      S3Bucket bucket = new S3Bucket( bucketName );
      S3Object[] objects = service.listObjects( bucket );
      
      if ( objects != null ) {
        
        result = new String[ objects.length ];
    
        for ( int i = 0 ; i < objects.length ; i++ ) {
          result[ i ] = objects[ i ].getKey();
        }
        
      }
      
    } catch ( S3ServiceException s3sExc ) { 
      throw new ProblemException(
          IAwsProblems.S3_LIST_FAILED,
          s3sExc,
          Activator.PLUGIN_ID );
    }
    
    return result;
    
  }
  
}
