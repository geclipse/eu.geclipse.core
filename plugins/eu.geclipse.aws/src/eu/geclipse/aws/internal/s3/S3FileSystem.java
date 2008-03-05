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

import java.net.URI;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.filesystem.provider.FileSystem;

/**
 * {@link IFileSystem} implementation for the amazon S3 service. 
 */
public class S3FileSystem
    extends FileSystem
    implements IFileSystem {
  
  /* (non-Javadoc)
   * @see org.eclipse.core.filesystem.provider.FileSystem#canDelete()
   */
  @Override
  public boolean canDelete() {
    return true;
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.core.filesystem.provider.FileSystem#canWrite()
   */
  @Override
  public boolean canWrite() {
    return true;
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.filesystem.provider.FileSystem#getStore(java.net.URI)
   */
  @Override
  public IFileStore getStore( final URI uri ) {
    
    S3FileStore result;
    
    String accessKeyID = uri.getAuthority();
    String bucketName = uri.getPath();
    
    if ( ( bucketName != null ) && bucketName.startsWith( IS3Constants.S3_PATH_SEPARATOR ) ){
      bucketName = bucketName.substring( 1 );
    }
    
    if ( ( bucketName == null ) || bucketName.length() == 0 ) {
      result = new S3FileStore( accessKeyID );
    } else {
      S3FileStore parent = new S3FileStore( accessKeyID );
      result = ( S3FileStore ) parent.getChild( bucketName );
    }
    
    return result;
    
  }

}
