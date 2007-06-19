package eu.geclipse.jsdl.ui.internal.preference;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import eu.geclipse.jsdl.ui.internal.Activator;

/**
 * singleton
 */
public class ApplicationSpecificRegistry {

  private int currentIdPointer;
  private List<ApplicationSpecificObject> apps;
  static private ApplicationSpecificRegistry instance;

  private ApplicationSpecificRegistry() {
    apps = new ArrayList<ApplicationSpecificObject>();
    this.currentIdPointer = 0;
    IPath location = Activator.getDefault().getStateLocation();
    location = location.append( ".appsData" ); //$NON-NLS-1$
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
    BufferedReader in;
    try {
      in = new BufferedReader( new FileReader( file ) );
      String line;
      String appName = null;
      String path = null;
      IPath xmlPath = null;
      while( ( line = in.readLine() ) != null ) {
        if( line.startsWith( "name=" ) ) { //$NON-NLS-1$
          appName = line.substring( "name=".length() ).trim(); //$NON-NLS-1$
        }
        if( line.startsWith( "path=" ) ) { //$NON-NLS-1$
          path = line.substring( "path=".length() ).trim(); //$NON-NLS-1$
        }
        if( line.startsWith( "XMLpath=" ) ) { //$NON-NLS-1$
          xmlPath = new Path( line.substring( "XMLpath=".length() ).trim() ); //$NON-NLS-1$
        }
      }
      if( appName != null && path != null && xmlPath != null ) {
        result = new ApplicationSpecificObject( this.currentIdPointer,
                                                appName,
                                                path,
                                                xmlPath );
        this.currentIdPointer++;
      }
    } catch( FileNotFoundException fileNotFoundExc ) {
      Activator.logException( fileNotFoundExc );
    } catch( IOException ioExc ) {
      Activator.logException( ioExc );
    }
    return result;
  }

  public static ApplicationSpecificRegistry getInstance() {
    if( instance == null ) {
      instance = new ApplicationSpecificRegistry();
    }
    return instance;
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
      this.apps.add( newObject );
    }
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
    for (ApplicationSpecificObject appSO: this.apps){
      if (numberOfOccurences.containsKey( appSO.getAppName() )){
        numberOfOccurences.put( appSO.getAppName(), new Integer(numberOfOccurences.get( appSO.getAppName() ).intValue() + 1) );
      } else {
        numberOfOccurences.put( appSO.getAppName(), new Integer(1) );
      }
    }
    for (ApplicationSpecificObject appSpecObject: this.apps){
      
        String nameToDisplay = appSpecObject.getAppName();
        int numOfOcc = numberOfOccurences.get( appSpecObject.getAppName() ).intValue();
        if ( numOfOcc != 1 ){
          numberOfOccurences.put( appSpecObject.getAppName(), numOfOcc - 1 );
          nameToDisplay = nameToDisplay + "("+ (numOfOcc - 1) +")";
        }
        result.put( new Integer(appSpecObject.getId()), nameToDisplay );
      }
    
    return result;
  }
}
