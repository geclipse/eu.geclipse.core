/*****************************************************************************
 * Copyright (c) 2006, 2008 g-Eclipse Consortium 
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
 *    Christof Klausecker GUP, JKU - initial API and implementation
 *****************************************************************************/

package eu.geclipse.efs.sftp;

import java.net.URI;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.IFileTree;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * EFS implementation of SFTP
 */
public class SFTPFileSystem
  extends org.eclipse.core.filesystem.provider.FileSystem
{

  @Override
  public IFileStore getStore( final URI uri ) {
    return new SFTPFileStore( uri );
  }

  @Override
  public boolean canDelete() {
    return true;
  }

  @Override
  public boolean canWrite() {
    return true;
  }

  @Override
  public boolean isCaseSensitive() {
    return true;
  }

  @Override
  public IFileTree fetchFileTree( final IFileStore root,
                                  final IProgressMonitor monitor )
  {
    return super.fetchFileTree( root, monitor );
  }

  @Override
  public IFileStore getStore( final IPath path ) {
    return super.getStore( path );
  }
}
