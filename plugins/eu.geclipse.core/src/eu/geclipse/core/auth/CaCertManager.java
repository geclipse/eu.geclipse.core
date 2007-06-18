/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
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

package eu.geclipse.core.auth;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.zip.GZIPInputStream;
import org.eclipse.compare.IContentChangeListener;
import org.eclipse.compare.IContentChangeNotifier;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Path;
import eu.geclipse.core.util.tar.TarInputStream;
import eu.geclipse.core.util.tar.TarEntry;
import eu.geclipse.core.CoreProblems;
import eu.geclipse.core.GridException;
import eu.geclipse.core.Preferences;
import eu.geclipse.core.internal.Activator;

/**
 * This class manages all CA certificates that are currently loaded in the
 * workspace. It also provides methods for retrieving new certificates from
 * local or remote repositories. The {@link #getCaCertLocation()} method
 * can be used to initialise external packages like the org.globus.
 * 
 * @author stuempert-m
 */
public class CaCertManager implements IContentChangeNotifier {
  
  /**
   * Internally used implementation of the {@link ICaCertificate} interface.
   * 
   * @author stuempert-m
   */
  private class CaCertificate extends AbstractCaCertificate {
    
    /**
     * Protected standard constructor.
     */
    protected CaCertificate() {
      // empty implementation
    }
    
    /**
     * Delete all files from disk that are related to this CA certificate.
     */
    protected void delete() {
      IPath[] files = getFiles();
      for ( int i = 0 ; i < files.length ; i++ ) {
        File file = files[i].toFile();
        if ( file.exists() ) {
          file.delete();
        }
      }
    }
    
  }
  
  
  /**
   * The singleton.
   */
  static private CaCertManager singleton = null;
  
  /**
   * The list holds the currently managed certificates.
   */
  private Hashtable< String, ICaCertificate > certs = new Hashtable< String, ICaCertificate >();
  
  /**
   * This list holds the currently registered IContentChangeListeners. 
   */
  private ListenerList ccListeners = new ListenerList();
  
  /**
   * Private constructor. The created manager is initialised with all
   * certificates that could be found in the certificate location.
   */
  private CaCertManager() {
    update();
    fireContentChanged();
  }
  
  /**
   * This methods can be used to get a <code>CaCertManager</code>. If no manager
   * was yet requested the singleton is instantiated once.
   * 
   * @return The singleton.
   */
  static public CaCertManager getManager() {
    if ( singleton == null ) {
      singleton = new CaCertManager();
    }
    return singleton;
  }
  
  /**
   * Get a copy of the internal list of all currently managed certificates.
   * 
   * @return A list containing all currently available certificates.
   */
  public ICaCertificate[] getCertificates() {
    return this.certs.values().toArray( new ICaCertificate[0] );
  }
  
  /**
   * Get the certificate with the specified ID or null if not found.
   * 
   * @param id The unique ID of the certificate.
   * @return The certificate with the specified ID or null if no such
   * certificate was found.
   */
  public ICaCertificate getCertificate( final String id ) {
    return this.certs.get( id );
  }
  
  /**
   * Deletes the certificate with the specified id from the manager.
   * Deleting a certificate means also deleting the corresponding file
   * from disk. If the certificate is once deleted it should not
   * longer be referenced or used outside the <code>CaCertManager</code>.
   * 
   * @param id The ID of the certificate that should be deleted.
   * If no such certificate was found nothing will be done.
   */
  public void deleteCertificate( final String id ) {
    CaCertificate cert = ( CaCertificate )getCertificate( id );
    if ( cert != null ) {
      cert.delete();
      this.certs.remove( id );
    }
  }
  
  /**
   * Get the path to the location where the certificates are stored. This
   * method can be used for instance to initialise the CoG-jglobus with
   * <code>CoGProperties.setCaCertLocations(String)</code>.
   * 
   * @return An absolute IPath that describes the location of the certificates in
   * the filesystem.
   */
  public IPath getCaCertLocation() {
    IPath location = Activator.getDefault().getStateLocation();
    if ( !location.hasTrailingSeparator() ) {
      location = location.addTrailingSeparator();
    }
    location = location.append( ".certs" ); //$NON-NLS-1$
    File file = location.toFile();
    if ( !file.exists() ) {
      file.mkdir();
    }
    return location;
  }
  
  /**
   * Import CA certificates from an online repository.
   * 
   * @param repo The URL of the repository.
   * @param monitor A progress monitor used to monitor the import operation.
   * @throws GridException If a problem occurs when connecting to the specified
   *             repository.
   */
  public void importFromRepository( final URL repo,
                                    final IProgressMonitor monitor )
      throws GridException {
    
    monitor.beginTask( Messages.getString( "CaCertManager.fetching_files_task" ), 103 ); //$NON-NLS-1$
    
    // Connecting to repository
    monitor.subTask( Messages.getString( "CaCertManager.connecting_task" ) ); //$NON-NLS-1$
    monitor.worked( 1 );
    
    // Getting list of files
    List< String > files = null;
    if ( !monitor.isCanceled() ) {
      monitor.subTask( Messages.getString( "CaCertManager.getting_files_task" ) ); //$NON-NLS-1$
      files = getFilesFromRepository( repo );
      monitor.worked( 1 );
    }
    
    // Loading files from repository
    if ( !monitor.isCanceled() && ( files != null ) ) {
      int lastProg = 0;
      for ( int i = 0 ; i < files.size() ; i++ ) {
        String message =   Messages.getString( "CaCertManager.download_unzip_task" ) //$NON-NLS-1$
                         + files.get( i );
        monitor.subTask( message );
        try {
          URL url = new URL( repo.toString() + "/" + files.get( i ) ); //$NON-NLS-1$
          loadFileFromRepository( url );
        } catch( MalformedURLException muExc ) {
          Activator.logException( muExc );
        }
        int prog = 100 * i / files.size();
        if ( prog > lastProg ) {
          monitor.worked( prog - lastProg );
          lastProg = prog;
        }
        if ( monitor.isCanceled() ) {
          break;
        }
      }
      monitor.worked( 1 );
    }
    
    // Update list of CAs if necessary
    if ( !monitor.isCanceled() && ( files != null ) && ( files.size() > 0 ) ) {
      monitor.setTaskName( Messages.getString( "CaCertManager.updating_list_task" ) ); //$NON-NLS-1$
      update();
      fireContentChanged();
      monitor.worked( 1 );
    }
    
    monitor.done();
    
  }
  
  /**
   * Import CA certificates from a local directory.
   * 
   * @param dir The directory from which to import the certificates.
   * 
   * @param monitor A progress monitor used to indicate the progress of
   * this operation.
   * 
   * @throws IOException Thrown if an error occured when accessing the
   * specified directory.
   */
  public void importFromDirectory( final File dir,
                                   final IProgressMonitor monitor )
      throws IOException {
    
    if( !dir.exists() ) {
      throw new IOException( Messages.getString( "CaCertManager.dir_not_found_error" ) + dir.getPath() ); //$NON-NLS-1$
    }
    
    if( !dir.isDirectory() ) {
      throw new IOException( Messages.getString( "CaCertManager.not_a_dir_error" ) + dir.getPath() ); //$NON-NLS-1$
    }
    
    File[] certFiles = dir.listFiles( new FilenameFilter() {
      public boolean accept( final File ddir, final String name ) {
        return name.endsWith( ".0" ); //$NON-NLS-1$
      }
    } );
    
    IPath destPath = getCaCertLocation();

    monitor.beginTask( Messages.getString( "CaCertManager.copy_files_prog_msg" ), certFiles.length+1 ); //$NON-NLS-1$
    for ( int i = 0 ; i < certFiles.length ; i++ ) {
    
      IPath srcPath = new Path( certFiles[i].getPath() );
      IPath file = new Path( srcPath.removeFileExtension().lastSegment() );
      srcPath = srcPath.removeLastSegments( 1 );
      
      copyFile( srcPath, destPath, file, "0", monitor ); //$NON-NLS-1$
      copyFile( srcPath, destPath, file, "r0", monitor ); //$NON-NLS-1$
      copyFile( srcPath, destPath, file, "crl_url", monitor ); //$NON-NLS-1$
      copyFile( srcPath, destPath, file, "info", monitor ); //$NON-NLS-1$
      
      monitor.worked( 1 );
      if ( monitor.isCanceled() ) {
        i = certFiles.length;
      }
      
    }
    
    if ( certFiles.length > 0 ) {
      monitor.setTaskName( Messages.getString( "CaCertManager.updating_list_task" ) ); //$NON-NLS-1$
      update();
      fireContentChanged();
    }
    monitor.worked( 1 );
    
    monitor.done();
    
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.compare.IContentChangeNotifier#addContentChangeListener(org.eclipse.compare.IContentChangeListener)
   */
  public void addContentChangeListener( final IContentChangeListener listener ) {
    this.ccListeners.add( listener );
  }

  /* (non-Javadoc)
   * @see org.eclipse.compare.IContentChangeNotifier#removeContentChangeListener(org.eclipse.compare.IContentChangeListener)
   */
  public void removeContentChangeListener( final IContentChangeListener listener ) {
    this.ccListeners.remove( listener );
  }
  
  /**
   * Copy a file from one local location to another.
   *  
   * @param from The source folder.
   * 
   * @param to The target folder.
   * 
   * @param file The file to be copied.
   * 
   * @param extension The extension of the copied file.
   * 
   * @param monitor A progress monitor to indicate the progress of
   * this operation.
   * 
   * @return The path of the new file.
   * 
   * @throws IOException If an exception occurs during this operation.
   */
  private IPath copyFile( final IPath from,
                          final IPath to,
                          final IPath file,
                          final String extension,
                          final IProgressMonitor monitor ) throws IOException {
    
    IPath fileName = file.addFileExtension( extension );
    IPath srcFile = from.append( fileName );
    IPath dstFile = to.append( fileName );
    monitor.subTask( fileName.toString() );
    FileReader fReader = null;
    FileWriter fWriter = null;
    BufferedReader bReader = null;
    BufferedWriter bWriter = null;
    try {
      fReader = new FileReader( srcFile.toFile() );
      bReader = new BufferedReader( fReader );
      fWriter = new FileWriter( dstFile.toFile() );
      bWriter = new BufferedWriter( fWriter );
      String line;
      while ( ( line = bReader.readLine() ) != null ) {
        bWriter.write( line );
        bWriter.newLine();
      }
    } catch ( final FileNotFoundException fnfExc ) {
      // We won't do anything here since not all files have to be present
      dstFile = null;
    } catch ( final IOException ioExc ) {
      throw ioExc;
    } finally {
      if ( bReader != null ) bReader.close();
      if ( bWriter != null ) bWriter.close();
      if ( fReader != null ) fReader.close();
      if ( fWriter != null ) fWriter.close();
    }
    
    return dstFile;
    
  }
  
  /**
   * Update this manager.
   */
  private void update() {
    
    this.certs.clear();
    IPath certLocation = getCaCertLocation();
    File certDir = certLocation.toFile();
    
    if ( certDir.isDirectory() ) {
      
      String[] certFiles = certDir.list( new FilenameFilter() {
        public boolean accept( final File dir, final String name ) {
          return name.endsWith( ".0" ); //$NON-NLS-1$
        }
      } );
      
      for ( int i = 0 ; i < certFiles.length ; i++ ) {
        
        final IPath certPath   = new Path( certFiles[i] );
        
        final String filename = certPath.removeFileExtension().lastSegment();
        String[] caFiles = certDir.list( new FilenameFilter() {
          public boolean accept( final File dir, final String name ) {
            return name.startsWith( filename );
          }
        } );
        
        CaCertificate cert = new CaCertificate();
        for ( int j = 0 ; j < caFiles.length ; j++ ) {
          cert.addFile( certLocation.append( caFiles[j] ) );
        }
        this.certs.put( cert.getID(), cert );
        
      }
      
    }
    
  }
  
  /**
   * Get a list of files contained in the specified repository.
   * 
   * @param url The URL of the repository.
   * 
   * @return The list of files contained in the repository.
   * 
   * @throws CoreException If an exception occures while
   * contacting the repository.
   */
  private List<String> getFilesFromRepository( final URL url )
      throws GridException {
    
    List< String > files = new ArrayList< String >();
    try {
      URLConnection connection = Preferences.getURLConnection( url );
      try {
        connection.connect();
      } catch ( SocketTimeoutException toExc ) {
        throw new GridException( CoreProblems.CONNECTION_TIMEOUT, toExc );
      }
      InputStream iStream = connection.getInputStream();
      InputStreamReader iReader = new InputStreamReader( iStream );
      BufferedReader bReader = new BufferedReader( iReader );
      String line;
      StringBuffer buffer = new StringBuffer();
      while ( ( line = bReader.readLine() ) != null ) {
        buffer.append( line );
      }
      bReader.close();
      String content = buffer.toString().replaceAll( " ", "" ); //$NON-NLS-1$ //$NON-NLS-2$
      int index = -1;
      while ( ( index = content.indexOf( "ahref=\"", index+1 ) ) > 0 ) { //$NON-NLS-1$
        int endIndex = content.indexOf( "\">", index+7 ); //$NON-NLS-1$
        if ( endIndex > 0 ) {
          String file = content.substring( index+7, endIndex );
          if ( file.toLowerCase().endsWith( ".tar.gz" ) ) { //$NON-NLS-1$
            files.add( file );
          }
        }
        index = endIndex;
      }
    } catch ( IOException ioExc ) {
      throw new GridException( CoreProblems.CONNECTION_FAILED, ioExc );
    }
    return files;
  }
  
  /**
   * Load a file from the specified online repository.
   * 
   * @param url The URL of the repository.
   * 
   * @throws CoreException Thrown if an exception occurs while
   * contacting the specified repository.
   */
  private void loadFileFromRepository( final URL url )
      throws GridException {
    IPath location = getCaCertLocation();
    try {
      URLConnection connection = Preferences.getURLConnection( url );
      try {
        connection.connect();
      } catch ( SocketTimeoutException toExc ) {
        throw new GridException( CoreProblems.CONNECTION_TIMEOUT, toExc );
      }
      InputStream iStream = connection.getInputStream();
      BufferedInputStream bStream = new BufferedInputStream( iStream );
      GZIPInputStream zStream = new GZIPInputStream( bStream );
      TarInputStream tIStream = new TarInputStream( zStream );
      TarEntry tEntry;
      while ( ( tEntry = tIStream.getNextEntry() ) != null ) {
        if ( !tEntry.isDirectory() ) {
          IPath oPath = tEntry.getPath();
          oPath = location.append( oPath.lastSegment() );
          FileOutputStream fOStream = new FileOutputStream( oPath.toFile() );
          tIStream.copyEntryContents(fOStream);
          fOStream.close();
        }
      }
    } catch ( IOException ioExc ) {
      throw new GridException( CoreProblems.CONNECTION_FAILED, ioExc );
    }
  }
  
  /**
   * Notify all registered IContentChangeListeners about content changes.
   */
  private void fireContentChanged() {
    Object[] list = this.ccListeners.getListeners();
    for ( int i = 0 ; i < list.length ; i++ ) {
      if ( list[i] instanceof IContentChangeListener ) {
        IContentChangeListener listener = ( IContentChangeListener ) list[i];
        listener.contentChanged( this );
      }
    }
  }
   
}
