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
 *    Moritz Post - Introduction of new root element
 *****************************************************************************/

package eu.geclipse.aws.s3.internal.fileSystem;

import java.net.URI;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.filesystem.provider.FileSystem;

import eu.geclipse.aws.s3.IS3Constants;

/**
 * {@link IFileSystem} implementation for the amazon S3 service.
 */
public class S3FileSystem extends FileSystem implements IFileSystem {

  @Override
  public boolean canDelete() {
    return true;
  }

  @Override
  public boolean canWrite() {
    return true;
  }

  @Override
  public IFileStore getStore( final URI uri ) {

    S3FileStore result;

    String bucketName = uri.getPath();
    String awsAccessId = uri.getAuthority();

    if( bucketName != null
        && bucketName.startsWith( IS3Constants.S3_PATH_SEPARATOR ) )
    {
      bucketName = bucketName.substring( 1 );
    }

    if( bucketName == null || bucketName.length() == 0 ) {
      result = new S3FileStore( awsAccessId );
    } else {
      S3FileStore parent = new S3FileStore( awsAccessId );
      result = ( S3FileStore )parent.getChild( bucketName );
    }

    return result;
  }

}
