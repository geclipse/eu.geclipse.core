/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     PSNC - Katarzyna Bylec
 *           
 *****************************************************************************/
package eu.geclipse.jsdl.ui.preference;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.compare.IContentChangeListener;
import org.eclipse.compare.IContentChangeNotifier;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Path;
import eu.geclipse.jsdl.ui.internal.Activator;

/**
 * Register that manages {@link ApplicationSpecificObject}, which are created,
 * removed and edited while gEclipse is run. Those objects are serialized, so
 * there are accessible after Eclipse is restarted. This class is singleton.
 */
public class ApplicationSpecificRegistry implements IContentChangeNotifier {

  private static final String DIRECTORY_NAME = ".appsData"; //$NON-NLS-1$
  private static final String APP_NAME_PREFIX = "appName="; //$NON-NLS-1$
  private static final String APP_PATH_PREFIX = "appPath="; //$NON-NLS-1$
  private static final String XML_PATH_PREFIX = "XMLPath="; //$NON-NLS-1$
  private static final String JSDL_PATH_PEFIX = "JSDLPath="; //$NON-NLS-1$
  static private ApplicationSpecificRegistry instance;
  private int currentIdPointer;
  private List<ApplicationSpecificObject> apps;
  /**
   * Content change event listeners
   */
  private ListenerList ccListeners = new ListenerList();
  /**
   * maps {@link ApplicationSpecificObject} to file names (files in which their
   * data is kept)
   */
  private Map<ApplicationSpecificObject, String> appSpecObjectsToFiles = new HashMap<ApplicationSpecificObject, String>();

  private ApplicationSpecificRegistry() {
    this.apps = new ArrayList<ApplicationSpecificObject>();
    this.currentIdPointer = 0;
    IPath location = Activator.getDefault().getStateLocation();
    location = location.append( ApplicationSpecificRegistry.DIRECTORY_NAME );
    File file = location.toFile();
    if( file.exists() ) {
      String[] names = file.list();
      for( String name : names ) {
        ApplicationSpecificObject asO = readDataFile( ( new Path( file.getAbsolutePath() ) ).append( name ) );
        if( asO != null ) {
          this.apps.add( asO );
        }
      }
    }
  }

  /**
   * Method to read application specific data form file at given location.
   * Method is silent when something is wrong with the file - it only logs an
   * exception
   * 
   * @param filePath path to file with application specific data
   * @return an instance of {@link ApplicationSpecificObject} or null if
   *         something goes wrong (e.g. file is not found or has malformed data)
   */
  private ApplicationSpecificObject readDataFile( final IPath filePath ) {
    ApplicationSpecificObject result = null;
    File file = filePath.toFile();
    if( file.isFile() ) {
      BufferedReader in = null;
      try {
        in = new BufferedReader( new FileReader( file ) );
        String line;
        String appName = null;
        String path = null;
        IPath xmlPath = null;
        IPath jsdlPath = null;
        while( ( line = in.readLine() ) != null ) {
          if( line.startsWith( ApplicationSpecificRegistry.APP_NAME_PREFIX ) ) {
            appName = line.substring( ApplicationSpecificRegistry.APP_NAME_PREFIX.length() )
              .trim();
          }
          if( line.startsWith( ApplicationSpecificRegistry.APP_PATH_PREFIX ) ) {
            path = line.substring( ApplicationSpecificRegistry.APP_PATH_PREFIX.length() )
              .trim();
          }
          if( line.startsWith( ApplicationSpecificRegistry.XML_PATH_PREFIX ) ) {
            xmlPath = new Path( line.substring( ApplicationSpecificRegistry.XML_PATH_PREFIX.length() )
              .trim() );
          }
          if( line.startsWith( ApplicationSpecificRegistry.JSDL_PATH_PEFIX ) ) {
            jsdlPath = new Path( line.substring( ApplicationSpecificRegistry.JSDL_PATH_PEFIX.length() )
              .trim() );
          }
        }
        if( appName != null && path != null && xmlPath != null ) {
          result = new ApplicationSpecificObject( this.currentIdPointer,
                                                  appName,
                                                  path,
                                                  xmlPath,
                                                  jsdlPath );
          this.appSpecObjectsToFiles.put( result, filePath.lastSegment() );
          this.currentIdPointer++;
        }
      } catch( FileNotFoundException fileNotFoundExc ) {
        Activator.logException( fileNotFoundExc );
      } catch( IOException ioExc ) {
        Activator.logException( ioExc );
      } finally {
        try {
          if( in != null ) {
            in.close();
          }
        } catch( IOException e ) {
          // TODO Auto-generated catch block
          Activator.logException( e );
        }
      }
    }
    return result;
  }

  /**
   * Method to access instance of ApplicationSpecificRegistry
   * 
   * @return instance of ApplicationSpecificRegistry
   */
  public static ApplicationSpecificRegistry getInstance() {
    if( instance == null ) {
      instance = new ApplicationSpecificRegistry();
    }
    return instance;
  }

  /**
   * Returns {@link ApplicationSpecificObject} object with given id
   * 
   * @param id id of {@link ApplicationSpecificObject} object
   * @return {@link ApplicationSpecificObject} object with given id
   */
  public ApplicationSpecificObject getApplicationData( final int id ) {
    ApplicationSpecificObject result = null;
    for( ApplicationSpecificObject aSO : this.apps ) {
      if( aSO.getId() == id ) {
        result = aSO;
      }
    }
    return result;
  }

  /**
   * Method to access list of {@link ApplicationSpecificObject} managed by this
   * registry
   * 
   * @return list of {@link ApplicationSpecificObject} available in this
   *         registry
   */
  public List<ApplicationSpecificObject> getApplicationDataList() {
    return this.apps;
  }

  /**
   * Adds new application specific data to this registry. Data is written down
   * on disk and will be accessible after Eclipse is restarted.
   * 
   * @param name name of application
   * @param path path to executable file of this application
   * @param xmlPath path to XML file with information how exta wizard's pages
   *            should look like
   * @param jsdlPath path to JSDL file used as a base for JSDL file generated by
   *            New Job Wizard for this application settings
   */
  public void addApplicationSpecificData( final String name,
                                          final String path,
                                          final IPath xmlPath,
                                          final IPath jsdlPath )
  {
    ApplicationSpecificObject newObject = new ApplicationSpecificObject( this.currentIdPointer,
                                                                         name,
                                                                         path,
                                                                         xmlPath,
                                                                         jsdlPath );
    this.currentIdPointer++;
    if( !this.apps.contains( newObject ) ) {
      try {
        saveObjectToDisc( newObject );
        this.apps.add( newObject );
        notifyListeners();
      } catch( IOException exc ) {
        // TODO katis: problem dialog
        Activator.logException( exc );
      }
    }
  }

  private void saveObjectToDisc( final ApplicationSpecificObject aSO )
    throws IOException
  {
    IPath path = Activator.getDefault().getStateLocation();
    path = path.append( ApplicationSpecificRegistry.DIRECTORY_NAME );
    File directory = path.toFile();
    if( !directory.exists() ) {
      directory.mkdir();
    }
    int name = aSO.getId();
    boolean canCreate = false;
    while( !canCreate ) {
      if( path.append( Integer.valueOf( name ).toString() ).toFile().exists() )
      {
        name++;
      } else {
        path = path.append( Integer.valueOf( name ).toString() );
        canCreate = true;
      }
    }
    File newFile = path.toFile();
    newFile.createNewFile();
    FileWriter writer = new FileWriter( newFile, true );
    writer.write( ApplicationSpecificRegistry.APP_NAME_PREFIX
                  + aSO.getAppName()
                  + "\n" ); //$NON-NLS-1$
    writer.write( ApplicationSpecificRegistry.APP_PATH_PREFIX
                  + aSO.getAppPath()
                  + "\n" ); //$NON-NLS-1$
    writer.write( ApplicationSpecificRegistry.XML_PATH_PREFIX
                  + aSO.getXmlPath()
                  + "\n" ); //$NON-NLS-1$
    writer.write( ApplicationSpecificRegistry.JSDL_PATH_PEFIX
                  + aSO.getJsdlPath() );
    writer.close();
    this.appSpecObjectsToFiles.put( aSO, Integer.valueOf( name ).toString() );
  }

  /**
   * @return Map with id of an {@link ApplicationSpecificObject} as a key and
   *         display name of an application for which extra parameters are
   *         provided. "Display name" is usually the same as the name of
   *         application. The only exception is where there are more than one
   *         parameters' settings for the same application. Then its name is
   *         appended with ints (e.g. grep (1), grep (2), etc...).
   */
  public Map<String, Integer> getApplicationDataMapping() {
    Map<String, Integer> result = new HashMap<String, Integer>();
    Map<String, Integer> numberOfOccurences = new HashMap<String, Integer>();
    for( ApplicationSpecificObject appSO : this.apps ) {
      if( numberOfOccurences.containsKey( appSO.getAppName() ) ) {
        numberOfOccurences.put( appSO.getAppName(),
                                Integer.valueOf( numberOfOccurences.get( appSO.getAppName() )
                                  .intValue() + 1 ) );
      } else {
        numberOfOccurences.put( appSO.getAppName(), new Integer( 1 ) );
      }
    }
    for( ApplicationSpecificObject appSpecObject : this.apps ) {
      String nameToDisplay = appSpecObject.getAppName();
      int numOfOcc = numberOfOccurences.get( appSpecObject.getAppName() )
        .intValue();
      if( numOfOcc != 1 ) {
        numberOfOccurences.put( appSpecObject.getAppName(),
                                Integer.valueOf( numOfOcc - 1 ) );
        nameToDisplay = nameToDisplay + "(" + ( numOfOcc - 1 ) + ")"; //$NON-NLS-1$ //$NON-NLS-2$
      }
      result.put( nameToDisplay, new Integer( appSpecObject.getId() ) );
    }
    return result;
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

  protected void notifyListeners() {
    Object[] list = this.ccListeners.getListeners();
    for( int i = 0; i < list.length; i++ ) {
      if( list[ i ] instanceof IContentChangeListener ) {
        IContentChangeListener listener = ( IContentChangeListener )list[ i ];
        listener.contentChanged( this );
      }
    }
  }

  /**
   * Removes application specific data held in this registry. This data won't be
   * accessible even after Eclipse is restarted.
   * 
   * @param selectedAppSpecificObject object to remove
   */
  public void removeApplicationSpecificData( final ApplicationSpecificObject selectedAppSpecificObject )
  {
    String fileName = this.appSpecObjectsToFiles.get( selectedAppSpecificObject );
    IPath path = Activator.getDefault().getStateLocation();
    path = path.append( ApplicationSpecificRegistry.DIRECTORY_NAME );
    path = path.append( fileName );
    File file = path.toFile();
    file.exists();
    if( file.delete() ) {
      this.apps.remove( selectedAppSpecificObject );
      notifyListeners();
    } else {
      // TODO katis: Problem Dialog
    }
  }

  /**
   * Changes given {@link ApplicationSpecificObject}. In this object given
   * values will be set.
   * 
   * @param oldASO object to change
   * @param newAppName application name to change in given
   *            ApplicationSpecificObject
   * @param newAppPath path to executable file to change in given
   *            ApplicationSpecificObject
   * @param newXMLPath path to XML file to change in given
   *            ApplicationSpecificObject
   * @param newJSDLPath path to JSDL file to change in given
   *            ApplicationSpecificObject
   */
  public void editApplicationSpecificData( final ApplicationSpecificObject oldASO,
                                           final String newAppName,
                                           final String newAppPath,
                                           final String newXMLPath,
                                           final String newJSDLPath )
  {
    IPath path = Activator.getDefault().getStateLocation();
    path = path.append( ApplicationSpecificRegistry.DIRECTORY_NAME );
    path = path.append( this.appSpecObjectsToFiles.get( oldASO ) );
    FileWriter writer;
    try {
      writer = new FileWriter( path.toFile(), false );
      writer.write( ApplicationSpecificRegistry.APP_NAME_PREFIX
                    + newAppName
                    + "\n" ); //$NON-NLS-1$
      writer.write( ApplicationSpecificRegistry.APP_PATH_PREFIX
                    + newAppPath
                    + "\n" ); //$NON-NLS-1$
      writer.write( ApplicationSpecificRegistry.XML_PATH_PREFIX
                    + newXMLPath
                    + "\n" ); //$NON-NLS-1$
      writer.write( ApplicationSpecificRegistry.JSDL_PATH_PEFIX + newJSDLPath );
      writer.close();
      oldASO.setAppName( newAppName );
      oldASO.setAppPath( newAppPath );
      oldASO.setXmlPath( new Path( newXMLPath ) );
      oldASO.setJSDLPath( new Path( newJSDLPath ) );
      notifyListeners();
    } catch( IOException exc ) {
      // TODO katis: problem dialog
      Activator.logException( exc );
    }
  }

  /**
   * Removes application with given name, path, xml and jsdl path from the
   * registry and notifies registered listeners.
   * 
   * @param appName
   * @param appPath
   * @param xmlPath
   * @param jsdlPath
   */
  public void removeApplicationSpecificData( final String appName,
                                             final String appPath,
                                             final IPath xmlPath,
                                             final IPath jsdlPath )
  {
    for( ApplicationSpecificObject object : apps ) {
      if( object.getAppName().equalsIgnoreCase( appName )
          && object.getAppPath().equals( appPath )
          && object.getXmlPath().equals( xmlPath )
          && object.getJsdlPath().equals( jsdlPath ) )
      {
        // apps.remove( object );
        removeApplicationSpecificData( object );
        break;
      }
    }
  }
}
