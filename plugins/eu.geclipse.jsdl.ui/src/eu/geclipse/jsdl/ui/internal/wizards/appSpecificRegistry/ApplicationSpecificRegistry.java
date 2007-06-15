package eu.geclipse.jsdl.ui.internal.wizards.appSpecificRegistry;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import eu.geclipse.jsdl.ui.internal.Activator;

/**
 * singleton
 */
public class ApplicationSpecificRegistry {

  private List<ApplicationSpecificObject> apps;
  static private ApplicationSpecificRegistry instance;

  private ApplicationSpecificRegistry() {
    apps = new ArrayList<ApplicationSpecificObject>();
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
        result = new ApplicationSpecificObject( appName, path, xmlPath );
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
    ApplicationSpecificObject newObject = new ApplicationSpecificObject(name, path, xmlPath);
    if (! this.apps.contains( newObject )){
      this.apps.add( newObject );
    }
  }
}
