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

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.provider.FileInfo;
import org.eclipse.core.filesystem.provider.FileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.ChannelSftp.LsEntry;

import eu.geclipse.efs.sftp.internal.CloseInputStream;
import eu.geclipse.efs.sftp.internal.CloseOutputStream;
import eu.geclipse.efs.sftp.internal.ConnectionKey;
import eu.geclipse.efs.sftp.internal.ConnectionManager;
import eu.geclipse.efs.sftp.internal.SFTPConnection;

/**
 * SFTP EFS FileStore implementation
 */
public class SFTPFileStore extends FileStore {

  private URI uri;
  private ConnectionKey connectionKey;
  private SFTPFileStore parent;
  private IPath path;
  private FileInfo myFileInfo;
  private HashMap<String, FileStore> children;
  private ArrayList<String> childNames;
  private ArrayList<IFileInfo> childInfos;

  /**
   * Creates a new SFTP filestore
   * 
   * @param uri
   */
  protected SFTPFileStore( final URI uri ) {
    this.connectionKey = new ConnectionKey( uri );
    this.uri = uri;
    this.parent = null;
    this.path = new Path( uri.getPath() );
    this.children = new HashMap<String, FileStore>();
    this.childNames = new ArrayList<String>();
    this.childInfos = new ArrayList<IFileInfo>();
    String name = new Path( uri.getPath() ).lastSegment();
    if( name != null && name.length() > 0 ) {
      this.myFileInfo = new FileInfo( name );
    } else {
      this.myFileInfo = new FileInfo();
    }
  }

  private SFTPFileStore( final SFTPFileStore parent, final FileInfo fileInfo ) {
    this.connectionKey = parent.connectionKey;
    this.uri = parent.uri;
    this.parent = parent;
    this.path = parent.path.append( fileInfo.getName() );
    this.children = new HashMap<String, FileStore>();
    this.childNames = new ArrayList<String>();
    this.childInfos = new ArrayList<IFileInfo>();
    this.myFileInfo = fileInfo;
    try {
      this.uri = new URI( this.uri.getScheme(),
                          this.uri.getUserInfo(),
                          this.uri.getHost(),
                          this.uri.getPort(),
                          this.path.toString(),
                          this.uri.getQuery(),
                          this.uri.getFragment() );
    } catch( URISyntaxException uriSyntaxException ) {
      Activator.logException( uriSyntaxException );
    }
  }

  @SuppressWarnings("unchecked")
  private void update() throws CoreException {
    SFTPConnection connection = ConnectionManager.getInstance()
      .acquireConnection( this.connectionKey );
    ChannelSftp channel = connection.getChannel();
    if( this.uri.getPath().length() == 0 ) {
      try {
        this.uri = new URI( this.uri.getScheme(),
                            this.uri.getUserInfo(),
                            this.uri.getHost(),
                            this.uri.getPort(),
                            channel.getHome(),
                            this.uri.getQuery(),
                            this.uri.getFragment() );
        this.path = new Path( this.uri.getPath() );
      } catch( URISyntaxException uriSyntaxException ) {
        Activator.logException( uriSyntaxException );
      }
      // necessary for ganymede
      catch( SftpException sftpException ) {
        Activator.logException( sftpException );
      }
    }
    try {
      String remotePath = this.path.toString();
      if( remotePath.length() == 0 ) {
        remotePath = "/"; //$NON-NLS-1$
      }
      Vector<LsEntry> vector = channel.ls( remotePath );
      for( LsEntry lsEntry : vector ) {
        String filename = lsEntry.getFilename();
        if( !"..".equals( filename ) && filename.indexOf( ':' ) == -1 ) { //$NON-NLS-1$
          SftpATTRS attributes = lsEntry.getAttrs();
          FileInfo fileInfo = new FileInfo( filename );
          fileInfo.setLength( attributes.getSize() );
          fileInfo.setDirectory( attributes.isDir() );
          fileInfo.setExists( true );
          fileInfo.setLastModified( ( long )attributes.getMTime() * 1000 );
          // TODO think about a ways to get attribute
          fileInfo.setAttribute( EFS.ATTRIBUTE_READ_ONLY, false );
          if( ".".equals( filename ) || vector.size() == 1 ) { //$NON-NLS-1$
            if( ".".equals( filename ) ) { //$NON-NLS-1$
              fileInfo.setName( remotePath );
            } else {
              fileInfo.setName( filename );
            }
            if( this.myFileInfo == null
                || this.myFileInfo.getLastModified() != fileInfo.getLastModified() )
            {
              this.myFileInfo = fileInfo;
            }
          } else {
            this.children.put( filename, new SFTPFileStore( this, fileInfo ) );
            this.childNames.add( filename );
            this.childInfos.add( fileInfo );
          }
        }
      }
    } catch( SftpException sftpException ) {
      this.myFileInfo = new FileInfo( this.path.lastSegment() );
      this.myFileInfo.setExists( false );
      this.myFileInfo.setAttribute( EFS.ATTRIBUTE_READ_ONLY, false );
      if( sftpException.id != 2 ) {
        Activator.logException( sftpException );
      }
    } finally {
      connection.unlock();
    }
  }

  @Override
  public String getName() {
    return this.myFileInfo.getName();
  }

  @Override
  public IFileStore getParent() {
    return this.parent;
  }

  @Override
  public IFileStore getChild( final String name ) {
    IFileStore child = this.children.get( name );
    if( child == null ) {
      FileInfo childFileInfo = new FileInfo( name );
      childFileInfo.setExists( false );
      child = new SFTPFileStore( this, childFileInfo );
    }
    return child;
  }

  @Override
  public void putInfo( final IFileInfo info,
                       final int options,
                       final IProgressMonitor monitor ) throws CoreException
  {
    // empty
  }

  @Override
  public IFileInfo fetchInfo() {
    return this.myFileInfo;
  }

  @Override
  public IFileInfo fetchInfo( final int options, final IProgressMonitor monitor )
    throws CoreException
  {
    update();
    return this.myFileInfo;
  }

  @Override
  public void delete( final int options, final IProgressMonitor monitor )
    throws CoreException
  {
    SFTPConnection connection = ConnectionManager.getInstance()
      .acquireConnection( this.connectionKey );
    ChannelSftp channel = connection.getChannel();
    update();
    try {
      if( this.myFileInfo.isDirectory() ) {
        if( this.children.size() != 0 ) {
          for( FileStore filestore : this.children.values() ) {
            filestore.delete( 0, monitor );
          }
        }
        channel.rmdir( this.path.toString() );
      } else {
        channel.rm( this.path.toString() );
        // this.parent.update();
      }
    } catch( SftpException sftpException ) {
      Activator.logException( sftpException );
    } finally {
      this.myFileInfo.setExists( false );
      connection.unlock();
    }
  }

  @Override
  public InputStream openInputStream( final int options,
                                      final IProgressMonitor monitor )
    throws CoreException
  {
    SFTPConnection connection = ConnectionManager.getInstance()
      .acquireConnection( this.connectionKey );
    ChannelSftp channel = connection.getChannel();
    InputStream inputStream = null;
    try {
      inputStream = new CloseInputStream( channel.get( this.path.toString() ),
                                          connection );
    } catch( SftpException sftpException ) {
      Activator.logException( sftpException );
      connection.unlock();
    }
    return inputStream;
  }

  @Override
  public OutputStream openOutputStream( final int options,
                                        final IProgressMonitor monitor )
    throws CoreException
  {
    SFTPConnection connection = ConnectionManager.getInstance()
      .acquireConnection( this.connectionKey );
    ChannelSftp channel = connection.getChannel();
    OutputStream outputStream = null;
    try {
      OutputStream o = channel.put( this.path.toString(), ChannelSftp.OVERWRITE );
      outputStream = new CloseOutputStream( o, connection );
    } catch( SftpException sftpException ) {
      Activator.logException( sftpException );
      connection.unlock();
    }
    return outputStream;
  }

  @Override
  public IFileStore mkdir( final int options, final IProgressMonitor monitor )
    throws CoreException
  {
    SFTPConnection connection = ConnectionManager.getInstance()
      .acquireConnection( this.connectionKey );
    ChannelSftp channel = connection.getChannel();
    try {
      channel.mkdir( this.path.toString() );
      this.myFileInfo.setExists( true );
      this.myFileInfo.setDirectory( true );
    } catch( SftpException sftpException ) {
      Activator.logException( sftpException );
      connection.unlock();
    }
    return this;
  }

  @Override
  public String[] childNames( final int options, final IProgressMonitor monitor )
    throws CoreException
  {
    update();
    return this.childNames.toArray( new String[ 0 ] );
  }

  @Override
  public IFileInfo[] childInfos( final int options,
                                 final IProgressMonitor monitor )
    throws CoreException
  {
    update();
    return this.childInfos.toArray( new IFileInfo[ 0 ] );
  }

  @Override
  public IFileStore[] childStores( final int options,
                                   final IProgressMonitor monitor )
    throws CoreException
  {
    update();
    return this.children.entrySet().toArray( new IFileStore[ 0 ] );
  }

  @Override
  public URI toURI() {
    return this.uri;
  }

  @Override
  public void move( final IFileStore destination,
                    final int options,
                    final IProgressMonitor monitor ) throws CoreException
  {
    // TODO implement to improve performance
    super.move( destination, options, monitor );
  }

  @Override
  protected void copyFile( final IFileInfo sourceInfo,
                           final IFileStore destination,
                           final int options,
                           final IProgressMonitor monitor )
    throws CoreException
  {
    // TODO implement to improve performance
    super.copyFile( sourceInfo, destination, options, monitor );
  }

  @Override
  protected void copyDirectory( final IFileInfo sourceInfo,
                                final IFileStore destination,
                                final int options,
                                final IProgressMonitor monitor )
    throws CoreException
  {
    // TODO implement to improve performance
    super.copyDirectory( sourceInfo, destination, options, monitor );
  }

  @Override
  public void copy( final IFileStore destination,
                    final int options,
                    final IProgressMonitor monitor ) throws CoreException
  {
    // TODO implement to improve performance
    super.copy( destination, options, monitor );
  }
}
