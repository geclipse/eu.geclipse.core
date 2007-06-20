package eu.geclipse.jsdl.ui.internal.preference;

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
import org.eclipse.swt.widgets.Listener;
import eu.geclipse.jsdl.ui.internal.Activator;

/**
 * singleton
 */
public class ApplicationSpecificRegistry implements IContentChangeNotifier {

  private int currentIdPointer;
  private List<ApplicationSpecificObject> apps;
  private static final String DIRECTORY_NAME = ".appsData";
  static private ApplicationSpecificRegistry instance;
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
    apps = new ArrayList<ApplicationSpecificObject>();
    this.currentIdPointer = 0;
    IPath location = Activator.getDefault().getStateLocation();
    location = location.append( ApplicationSpecificRegistry.DIRECTORY_NAME ); //$NON-NLS-1$
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
   * Method is silent when sth is wrong with the file - it only loges an
   * exception
   * 
   * @param filePath path to file fith application specific data
   * @return an instance of {@link ApplicationSpecificObject} or null if sth
   *         goes wrong (e.g. file is not found or has malformed data)
   */
  private ApplicationSpecificObject readDataFile( IPath filePath ) {
    ApplicationSpecificObject result = null;
    File file = filePath.toFile();
    if( file.isFile() ) {
      BufferedReader in;
      try {
        in = new BufferedReader( new FileReader( file ) );
        String line;
        String appName = null;
        String path = null;
        IPath xmlPath = null;
        while( ( line = in.readLine() ) != null ) {
          if( line.startsWith( "appName=" ) ) { //$NON-NLS-1$
            appName = line.substring( "appName=".length() ).trim(); //$NON-NLS-1$
          }
          if( line.startsWith( "appPath=" ) ) { //$NON-NLS-1$
            path = line.substring( "appPath=".length() ).trim(); //$NON-NLS-1$
          }
          if( line.startsWith( "XMLPath=" ) ) { //$NON-NLS-1$
            xmlPath = new Path( line.substring( "XMLPath=".length() ).trim() ); //$NON-NLS-1$
          }
        }
        if( appName != null && path != null && xmlPath != null ) {
          result = new ApplicationSpecificObject( this.currentIdPointer,
                                                  appName,
                                                  path,
                                                  xmlPath );
          this.appSpecObjectsToFiles.put( result, filePath.lastSegment() );
          this.currentIdPointer++;
        }
      } catch( FileNotFoundException fileNotFoundExc ) {
        Activator.logException( fileNotFoundExc );
      } catch( IOException ioExc ) {
        Activator.logException( ioExc );
      }
    }
    return result;
  }

  public static ApplicationSpecificRegistry getInstance() {
    if( instance == null ) {
      instance = new ApplicationSpecificRegistry();
    }
    return instance;
  }

  public ApplicationSpecificObject getApplicationData( int id ) {
    ApplicationSpecificObject result = null;
    for( ApplicationSpecificObject aSO : this.apps ) {
      if( aSO.getId() == id ) {
        result = aSO;
      }
    }
    return result;
  }

  public List<ApplicationSpecificObject> getApplicationDataList() {
    return this.apps;
  }

  public void addApplicationSpecificData( final String name,
                                          final String path,
                                          final IPath xmlPath )
  {
    ApplicationSpecificObject newObject = new ApplicationSpecificObject( this.currentIdPointer,
                                                                         name,
                                                                         path,
                                                                         xmlPath );
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

  private void saveObjectToDisc( ApplicationSpecificObject aSO )
    throws IOException
  {
    IPath path = Activator.getDefault().getStateLocation();
    path = path.append( ApplicationSpecificRegistry.DIRECTORY_NAME );
    File directory = path.toFile();
    if( !directory.exists() ) {
      directory.createNewFile();
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
    writer.write( "appName=" + aSO.getAppName() + "\n" );
    writer.write( "appPath=" + aSO.getAppPath() + "\n" );
    writer.write( "XMLPath=" + aSO.getXmlPath() );
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
  public Map<Integer, String> getApplicationDataMapping() {
    Map<Integer, String> result = new HashMap<Integer, String>();
    Map<String, Integer> numberOfOccurences = new HashMap<String, Integer>();
    for( ApplicationSpecificObject appSO : this.apps ) {
      if( numberOfOccurences.containsKey( appSO.getAppName() ) ) {
        numberOfOccurences.put( appSO.getAppName(),
                                new Integer( numberOfOccurences.get( appSO.getAppName() )
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
        numberOfOccurences.put( appSpecObject.getAppName(), numOfOcc - 1 );
        nameToDisplay = nameToDisplay + "(" + ( numOfOcc - 1 ) + ")";
      }
      result.put( new Integer( appSpecObject.getId() ), nameToDisplay );
    }
    return result;
  }

  public void addContentChangeListener( IContentChangeListener listener ) {
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

  public void removeApplicationSpecificData( ApplicationSpecificObject selectedAppSpecificObject )
  {
    String fileName = this.appSpecObjectsToFiles.get( selectedAppSpecificObject );
    IPath path = Activator.getDefault().getStateLocation();
    path = path.append( ApplicationSpecificRegistry.DIRECTORY_NAME );
    path = path.append( fileName );
    File file = path.toFile();
    if( file.delete() ) {
      this.apps.remove( selectedAppSpecificObject );
      notifyListeners();
    } else {
      // TODO katis: Problem Dialog
    }
  }

  public void editApplicationSpecificData( ApplicationSpecificObject oldASO,
                                           String newAppName,
                                           String newAppPath,
                                           String newXMLPath )
  {
    IPath path = Activator.getDefault().getStateLocation();
    path = path.append( ApplicationSpecificRegistry.DIRECTORY_NAME );
    path = path.append( this.appSpecObjectsToFiles.get( oldASO ) );
    FileWriter writer;
    try {
      writer = new FileWriter( path.toFile(), false );
      writer.write( "appName=" + newAppName + "\n" );
      writer.write( "appPath=" + newAppPath + "\n" );
      writer.write( "XMLPath=" + newXMLPath );
      writer.close();
      oldASO.setAppName( newAppName );
      oldASO.setAppPath( newAppPath );
      oldASO.setXmlPath( new Path( newXMLPath ) );
      notifyListeners();
    } catch( IOException exc ) {
      // TODO katis: problem dialog
      Activator.logException( exc );
    }
  }
}
