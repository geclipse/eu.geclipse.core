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
 *    Katarzyna Bylec - initial API and implementation
 *    Mathias Stuempert
 *****************************************************************************/
package eu.geclipse.core.connection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.compare.IContentChangeListener;
import org.eclipse.compare.IContentChangeNotifier;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.ListenerList;
import eu.geclipse.core.connection.impl.ConnectionDescription;
import eu.geclipse.core.internal.Activator;

/**
 * This class (singleton) is ment to manage any connection to filesystem that
 * was made from Grid Project or from gExplorer.
 * 
 * @author katis
 */
public class ConnectionManager implements IContentChangeNotifier {

  /**
   * Instance of singleton class
   */
  private static ConnectionManager singleton;
  /**
   * List of connections managed by this manager (connections made from gExplorer)
   */
  private List<AbstractConnection> connections = new ArrayList<AbstractConnection>();
  
  /**
   * List of names of connections managed by this manager (both - connections made form Grid Project View and from gExplorer)
   */
  private List<String> connectionsNames = new ArrayList<String>();
  /**
   * Content change event listeners
   */
  private ListenerList ccListeners = new ListenerList();

  /**
   * Creates new instance of ConnectionManager
   */
  private ConnectionManager() {
    IPath location = eu.geclipse.core.internal.Activator.getDefault()
      .getStateLocation();
    location = location.append( ".filesystems" ); //$NON-NLS-1$
//    location = location.append( ".connections.xml" ); //$NON-NLS-1$
    File file = location.toFile();
    if (file.exists()){
      String[] names = file.list();
      for (String name: names){
        File f = new File(file.getAbsolutePath() + "/" + name); //$NON-NLS-1$
        if( f.exists() ) {
          try {
            BufferedReader in = new BufferedReader( new FileReader( f ) );
            String line;
            while( ( line = in.readLine() ) != null ) {
              ConnectionDescription desc = new ConnectionDescription( new URI( line ),
                                                                      null );
              createConnection( desc );
            }
          } catch( FileNotFoundException fileExc ) {
            Activator.logException( fileExc );
          } catch( IOException ioExc ) {
            Activator.logException( ioExc );
          } catch( URISyntaxException uriExc ) {
            Activator.logException( uriExc );
          }
        }
      }
    }
  }

  /**
   * Gets singleton instance
   * 
   * @return singleton instance of ConnectionManager
   */
  public static ConnectionManager getManager() {
    if( singleton == null ) {
      singleton = new ConnectionManager();
    }
    return singleton;
  }

  /**
   * Creates new connection from its description given, adds this connection to
   * list of connections managed by this connections manager and saves
   * information of new connection in a plug-in's metadata file
   * 
   * @param description Description from which new connection is created
   * @param propertiesFile file where to store information (not used anymore?)
   * @param connectionName name of the connection
   * @return newly created connection
   */
  public IConnection createAndSaveConnection( final IConnectionDescription description,
                                              final IFile propertiesFile, final String connectionName )
  {
    IConnection result = description.createConnection();
    //TODO katis - is propertiesFile used?
    saveConnectionMetadata( result, propertiesFile, connectionName );
    if( propertiesFile == null ) {
      addConnection( ( AbstractConnection )result );
    }
    this.connectionsNames.add( connectionName );
    return result;
  }

  /**
   * Creates new connection from its description given and adds this connection
   * to list of connections managed by this connections manager. Information
   * about nwe connection is not persistent - it is not written in metadata
   * file.
   * 
   * @param description Description from which new connection is created
   * @return newly created connection
   */
  public IConnection createConnection( final IConnectionDescription description )
  {
    IConnection result = description.createConnection();
    addConnection( ( AbstractConnection )result );
    return result;
  }

  /**
   * Adds new connection to list of connections managed by this
   * ConnectionManager and notifies any listeners of content changed event
   * 
   * @param connection
   */
  protected void addConnection( final AbstractConnection connection ) {
    this.connections.add( connection );
    fireContentChanged();
  }

  /**
   * Saves information about connection (project, path in project and URI) in
   * metadata file of this plug-in
   * 
   * @param connection Connection whose data will be saved in the plug-in's
   *          metadatafile
   * @param propertiesFile
   */
  private void saveConnectionMetadata( final IConnection connection,
                                       final IFile propertiesFile, final String connectionName )
  {
    if( propertiesFile == null ) {
      IPath location = eu.geclipse.core.internal.Activator.getDefault()
        .getStateLocation();
      if( !location.hasTrailingSeparator() ) {
        location = location.addTrailingSeparator();
      }
      location = location.append( ".filesystems"); //$NON-NLS-1$
      File file = location.toFile();
      if( !file.exists() ) {
        file.mkdir();
      }
      location = location.append( "." + connectionName + ".fs"); //$NON-NLS-1$ //$NON-NLS-2$
      file = location.toFile();
      if( !file.exists() ) {
        try {
          file.createNewFile();
        } catch( IOException ioExc ) {
          Activator.logException( ioExc );
        }
      }
      try {
        FileWriter reader = new FileWriter( file, true );
        reader.write( connection.getDescription().getFileSystemURI().toString()
                      + "\n" ); //$NON-NLS-1$
        reader.close();
      } catch( FileNotFoundException fileExc ) {
        Activator.logException( fileExc );
      } catch( IOException ioExc ) {
        Activator.logException( ioExc );
      }
    }
    // wrtiting also to .filesystems file in grid project structure
    if( propertiesFile != null ) {
      File file = propertiesFile.getLocation().toFile();
      if( !propertiesFile.exists() ) {
        file.mkdir();
      }
      IPath loc = propertiesFile.getLocation();
      loc = loc.append( "." + connectionName + ".fs"); //$NON-NLS-1$ //$NON-NLS-2$
      file = loc.toFile();
      try {
        file.createNewFile();
      } catch( IOException e ) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      FileWriter reader;
      try {
        reader = new FileWriter( file, true );
        reader.write( connection.getDescription().getFileSystemURI().toString()
                      + "\n" ); //$NON-NLS-1$
        reader.close();
      } catch( IOException ioExc ) {
        Activator.logException( ioExc );
      }
    }
  }

  /**
   * Returns list of connections managed by this connections manager
   * 
   * @return list of connections
   */
  public ArrayList<AbstractConnection> getConnections() {
    // TODO Auto-generated method stub
    return ( ArrayList<AbstractConnection> )this.connections;
  }

  public void addContentChangeListener( final IContentChangeListener listener )
  {
    if( this.ccListeners == null ) {
      this.ccListeners = new ListenerList();
    }
    this.ccListeners.add( listener );
  }

  public void removeContentChangeListener( final IContentChangeListener listener )
  {
    if( this.ccListeners != null ) {
      this.ccListeners.remove( listener );
    }
  }

  /**
   * Notify all listeners that content of list of connections this
   * ConnectionManager is managing has changed
   */
  protected void fireContentChanged() {
    Object[] list = this.ccListeners.getListeners();
    for( int i = 0; i < list.length; i++ ) {
      if( list[ i ] instanceof IContentChangeListener ) {
        IContentChangeListener listener = ( IContentChangeListener )list[ i ];
        listener.contentChanged( this );
      }
    }
  }

  /**
   * Method to access names of connections in gExplorer
   * @return ArrayList of names of connections in gExplorer
   */
  public ArrayList<String> getConnectionNames() {
    ArrayList<String> result = new ArrayList<String>();
    IPath location = eu.geclipse.core.internal.Activator.getDefault()
    .getStateLocation();
    location = location.append( ".filesystems" ); //$NON-NLS-1$
    File file = location.toFile();
    if (!file.exists()){
      //do nothing
    } else {
      String[] names = file.list();
      for (String name: names){
        if (name.startsWith( "." ) && name.endsWith( ".fs" ) ){ //$NON-NLS-1$ //$NON-NLS-2$
          String conName = name.substring( 1, name.length() - 3 );
          result.add( conName );
        }
      }
    }
    return result;
  }
}
