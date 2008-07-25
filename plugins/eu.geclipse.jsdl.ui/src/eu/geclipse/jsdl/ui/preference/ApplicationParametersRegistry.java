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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.compare.IContentChangeListener;
import org.eclipse.compare.IContentChangeNotifier;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;

import eu.geclipse.core.ICoreProblems;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridApplication;
import eu.geclipse.core.model.IGridApplicationParameters;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridResource;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.model.impl.GridApplicationParameters;
import eu.geclipse.core.model.impl.GridResourceCategoryFactory;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.jsdl.ui.internal.Activator;

/**
 * Register that manages {@link GridApplicationParameters}, which are created,
 * removed and edited while gEclipse is run. Those objects are serialized, so
 * there are accessible after Eclipse is restarted. This class is singleton.
 */
public class ApplicationParametersRegistry implements IContentChangeNotifier {

  private static final String DIRECTORY_NAME = ".appsData"; //$NON-NLS-1$
  private static final String APP_NAME_PREFIX = "appName="; //$NON-NLS-1$
  private static final String APP_PATH_PREFIX = "appPath="; //$NON-NLS-1$
  private static final String XML_PATH_PREFIX = "XMLPath="; //$NON-NLS-1$
  private static final String JSDL_PATH_PEFIX = "JSDLPath="; //$NON-NLS-1$
  private static final String VO_PATH_PREFIX = "VOName="; //$NON-NLS-1$
  private static ApplicationParametersRegistry instance;
  int currentIdPointer = 0;
  private List<IGridApplicationParameters> userAppsParamsList = new ArrayList<IGridApplicationParameters>()
  {

    /**
     * 
     */
    private static final long serialVersionUID = -6190123705427391030L;

    @Override
    public boolean add( final IGridApplicationParameters param ) {
      param.setId( ApplicationParametersRegistry.this.currentIdPointer );
      ApplicationParametersRegistry.this.currentIdPointer++;
      return super.add( param );
    }
  };
  private List<IGridApplicationParameters> generatedAppsParamList = new ArrayList<IGridApplicationParameters>()
  {

    /**
     * 
     */
    private static final long serialVersionUID = 1433643044865859460L;

    @Override
    public boolean add( final IGridApplicationParameters param ) {
      param.setId( ApplicationParametersRegistry.this.currentIdPointer );
      ApplicationParametersRegistry.this.currentIdPointer++;
      return super.add( param );
    }
  };
  private Map<IGridApplicationParameters, String> appSpecObjectsToFiles = new HashMap<IGridApplicationParameters, String>();
  private ListenerList ccListeners = new ListenerList();

  private ApplicationParametersRegistry() {
    this.userAppsParamsList = new ArrayList<IGridApplicationParameters>();
    IPath location = Activator.getDefault().getStateLocation();
    location = location.append( ApplicationParametersRegistry.DIRECTORY_NAME );
    File file = location.toFile();
    if( file.exists() ) {
      String[] names = file.list();
      for( String name : names ) {
        GridApplicationParameters asO = readDataFile( ( new Path( file.getAbsolutePath() ) ).append( name ) );
        if( asO != null ) {
          this.userAppsParamsList.add( asO );
        }
      }
    }
  }

  /**
   * Returns the only instance (singleton) of this registry. If this instance
   * was not created yet - this method will trigger a constructor.
   * 
   * @return singleton instance of registry
   */
  public static ApplicationParametersRegistry getInstance() {
    if( instance == null ) {
      instance = new ApplicationParametersRegistry();
    }
    return instance;
  }

  /**
   * Returns stored values of applications' parameters for given virtual
   * organization.
   * 
   * @param vo virtual organization for which applications' parameters will be
   *            returned. This value can be <code>null</code> - indicating
   *            that parameters for all available virtual organizations should
   *            be returned.
   * @return list of applications' parameters stored by this registry. To make
   *         sure you will get the most up-to-date values call
   *         {@link ApplicationParametersRegistry#updateApplicationsParameters(IVirtualOrganization, IProgressMonitor)}
   *         before calling this method.
   */
  public List<IGridApplicationParameters> getApplicationParameters( final IVirtualOrganization vo )
  {
    List<IGridApplicationParameters> result = new ArrayList<IGridApplicationParameters>();
    for( IGridApplicationParameters param : this.userAppsParamsList ) {
      if( vo == null || param.getVO().equals( vo ) ) {
        result.add( param );
      }
    }
    for( IGridApplicationParameters param : this.generatedAppsParamList ) {
      if( vo == null || param.getVO().equals( vo ) ) {
        result.add( param );
      }
    }
    return result;
  }

  /**
   * Updates registry with most up-to-date values of applications' parameters of
   * a given virtual organization. Calling this method results in contacting
   * info service and replacing data held by this data with new values obtained
   * from this service. </br>This method do not affects applications parameters
   * defined by user through user interface .
   * 
   * @param vo virtual organization for which applications' parameters will be
   *            updated. This value can be <code>null</code> - indicating that
   *            parameters for all available virtual organizations should be
   *            updated.
   * @param monitor this may be a long-running operation. Parent method calling
   *            this operation should provide a progress monitor for it.
   * @throws ProblemException it is thrown in case registry was not able to
   *             access resources
   */
  public void updateApplicationsParameters( final IVirtualOrganization vo,
                                            final IProgressMonitor monitor )
    throws ProblemException
  {
    monitor.beginTask( Messages.getString("ApplicationParametersRegistry.fetching_information"), IProgressMonitor.UNKNOWN ); //$NON-NLS-1$
    if( vo == null ) {
      IGridElement[] els;
      els = GridModel.getVoManager().getChildren( new NullProgressMonitor() );
      monitor.worked( 1 );
      for( IGridElement el : els ) {
        if( el instanceof IVirtualOrganization ) {
          IVirtualOrganization singleVO = ( IVirtualOrganization )el;
          List<IGridApplicationParameters> params = new ArrayList<IGridApplicationParameters>();
          IGridResource[] resources = null;
          monitor.setTaskName( Messages.getString("ApplicationParametersRegistry.fetching_apps_list") ); //$NON-NLS-1$
          resources = singleVO.getAvailableResources( GridResourceCategoryFactory.getCategory( GridResourceCategoryFactory.ID_APPLICATIONS ),
                                                      false,
                                                      new NullProgressMonitor() );
          monitor.worked( 1 );
          if( resources != null ) {
            for( IGridResource param : resources ) {
              monitor.setTaskName( Messages.getString("ApplicationParametersRegistry.processing_apps_data") ); //$NON-NLS-1$
              IGridApplicationParameters parameter = ( ( IGridApplication )param ).getApplicationParameters();
              if( parameter != null ) {
                params.add( parameter );
              }
              monitor.worked( 1 );
            }
          } else {
            throw new ProblemException( ICoreProblems.MODEL_FETCH_CHILDREN_FAILED,
                                        Messages.getString("ApplicationParametersRegistry.cannot_fetch_resources"), //$NON-NLS-1$
                                        Activator.PLUGIN_ID );
          }
          monitor.setTaskName( Messages.getString("ApplicationParametersRegistry.updating_registry") ); //$NON-NLS-1$
          replaceParametersForVO( singleVO, params );
          monitor.worked( 1 );
        }
      }
    } else {
      monitor.beginTask( Messages.getString("ApplicationParametersRegistry.fetching_apps_list"), //$NON-NLS-1$
                         IProgressMonitor.UNKNOWN );
      List<IGridApplicationParameters> params = new ArrayList<IGridApplicationParameters>();
      IGridResource[] resources = null;
      resources = vo.getAvailableResources( GridResourceCategoryFactory.getCategory( GridResourceCategoryFactory.ID_APPLICATIONS ),
                                            false,
                                            new NullProgressMonitor() );
      if( resources != null ) {
        for( IGridResource param : resources ) {
          monitor.setTaskName( Messages.getString("ApplicationParametersRegistry.processing_apps_data") ); //$NON-NLS-1$
          IGridApplicationParameters parameter = ( ( IGridApplication )param ).getApplicationParameters();
          if( parameter != null ) {
            params.add( parameter );
          }
          monitor.worked( 1 );
        }
      } else {
        throw new ProblemException( ICoreProblems.MODEL_FETCH_CHILDREN_FAILED,
                                    Messages.getString("ApplicationParametersRegistry.cannot_fetch_resources"), //$NON-NLS-1$
                                    Activator.PLUGIN_ID );
      }
      monitor.setTaskName( Messages.getString("ApplicationParametersRegistry.updating_registry") ); //$NON-NLS-1$
      replaceParametersForVO( vo, params );
      monitor.worked( 1 );
    }
  }

  /**
   * Done for {@link ApplicationParametersRegistry#generatedAppsParamList} list.
   * 
   * @param vo
   * @param newParams
   */
  private void replaceParametersForVO( final IVirtualOrganization vo,
                                       final List<IGridApplicationParameters> newParams )
  {
    removeParamsForVO( vo );
    internalAddParams( newParams, 1 );
  }

  private void internalAddParams( final List<IGridApplicationParameters> newParams,
                                  final int list )
  {
    if( list == 1 ) {
      for( IGridApplicationParameters param : newParams ) {
        this.generatedAppsParamList.add( param );
        notifyListeners();
      }
    } else if( list == 0 ) {
      for( IGridApplicationParameters param : newParams ) {
        try {
          saveObjectToDisc( param );
          this.userAppsParamsList.add( param );
          notifyListeners();
        } catch( IOException e ) {
          // ignore
        }
      }
    }
  }

  private void removeParamsForVO( final IVirtualOrganization vo ) {
    Iterator<IGridApplicationParameters> iterator = this.generatedAppsParamList.iterator();
    while( iterator.hasNext() ) {
      IGridApplicationParameters param = iterator.next();
      if( param.getVO().equals( vo ) ) {
        iterator.remove();
        notifyListeners();
      }
    }
  }

  private boolean removeParam( final IGridApplicationParameters param ) {
    boolean result = false;
    String fileName = this.appSpecObjectsToFiles.get( param );
    IPath path = Activator.getDefault().getStateLocation();
    path = path.append( ApplicationParametersRegistry.DIRECTORY_NAME );
    path = path.append( this.appSpecObjectsToFiles.get( param ) );
    if( fileName != null ) {
      File file = path.toFile();
      file.exists();
      result = file.delete();
    } else {
      result = true;
    }
    if( result ) {
      if( this.userAppsParamsList.contains( param ) ) {
        this.userAppsParamsList.remove( param );
      } else if( this.generatedAppsParamList.contains( param ) ) {
        this.generatedAppsParamList.remove( param );
      }
    }
    return result;
  }

  private void saveObjectToDisc( final IGridApplicationParameters aSO )
    throws IOException
  {
    IPath path = Activator.getDefault().getStateLocation();
    path = path.append( ApplicationParametersRegistry.DIRECTORY_NAME );
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
    writer.write( ApplicationParametersRegistry.APP_NAME_PREFIX
                  + aSO.getApplicationName()
                  + "\n" ); //$NON-NLS-1$
    writer.write( ApplicationParametersRegistry.APP_PATH_PREFIX
                  + aSO.getApplicationPath()
                  + "\n" ); //$NON-NLS-1$
    writer.write( ApplicationParametersRegistry.XML_PATH_PREFIX
                  + aSO.getXmlPath()
                  + "\n" ); //$NON-NLS-1$
    writer.write( ApplicationParametersRegistry.JSDL_PATH_PEFIX
                  + aSO.getJsdlPath()
                  + "\n" ); //$NON-NLS-1$
    String vo = ""; //$NON-NLS-1$
    if( aSO.getVO() != null ) {
      vo = aSO.getVO().getName();
    }
    writer.write( ApplicationParametersRegistry.VO_PATH_PREFIX + vo );
    writer.close();
    this.appSpecObjectsToFiles.put( aSO, Integer.valueOf( name ).toString() );
  }

  /**
   * Method to read application specific data form file at given location.
   * Method is silent when something is wrong with the file - it only logs an
   * exception
   * 
   * @param filePath path to file with application specific data
   * @return an instance of {@link GridApplicationParameters} or null if
   *         something goes wrong (e.g. file is not found or has malformed data)
   */
  private GridApplicationParameters readDataFile( final IPath filePath ) {
    GridApplicationParameters result = null;
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
        IVirtualOrganization vo = null;
        boolean doCreate = true;
        while( ( line = in.readLine() ) != null ) {
          if( line.startsWith( ApplicationParametersRegistry.APP_NAME_PREFIX ) )
          {
            appName = line.substring( ApplicationParametersRegistry.APP_NAME_PREFIX.length() )
              .trim();
          }
          if( line.startsWith( ApplicationParametersRegistry.APP_PATH_PREFIX ) )
          {
            path = line.substring( ApplicationParametersRegistry.APP_PATH_PREFIX.length() )
              .trim();
          }
          if( line.startsWith( ApplicationParametersRegistry.XML_PATH_PREFIX ) )
          {
            xmlPath = new Path( line.substring( ApplicationParametersRegistry.XML_PATH_PREFIX.length() )
              .trim() );
          }
          if( line.startsWith( ApplicationParametersRegistry.JSDL_PATH_PEFIX ) )
          {
            jsdlPath = new Path( line.substring( ApplicationParametersRegistry.JSDL_PATH_PEFIX.length() )
              .trim() );
          }
          if( line.startsWith( ApplicationParametersRegistry.VO_PATH_PREFIX ) )
          {
            String voName = line.substring( ApplicationParametersRegistry.VO_PATH_PREFIX.length() )
              .trim();
            if( voName != "" ) { //$NON-NLS-1$
              vo = ( IVirtualOrganization )GridModel.getVoManager()
                .findChild( voName );
              if( vo == null ) {
                // this means ASP was defined for VO that cannot be found in
                // GridModel
              }
            } else {
              // this means ASP is defined for all VOs
              vo = null;
            }
          }
        }
        if( appName != null && path != null && xmlPath != null && doCreate ) {
          result = new GridApplicationParameters( appName,
                                                  path,
                                                  xmlPath,
                                                  jsdlPath,
                                                  vo );
          this.appSpecObjectsToFiles.put( result, filePath.lastSegment() );
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
   * Method for removing application's parameters from registry. All listeners
   * are informed that parameter was removed.</br>Note that this can be done
   * only for user-defined parameters - not for those fetched from info system,
   * as they are defined on "server side" and removing them from registy might
   * result in confusion if they are deleted also on "the server side" or not.
   * 
   * @param param parameter to be removed
   * @throws ApplicationParametersException is thrown when registry doesn't
   *             contain user-defined parameter equal to one passed to this
   *             method
   */
  public void removeApplicationParameters( final GridApplicationParameters param )
    throws ApplicationParametersException
  {
    if( this.userAppsParamsList.contains( param ) ) {
      if( removeParam( param ) ) {
        notifyListeners();
      } else {
        // TODO katis: Problem Dialog
      }
    } else {
      throw new ApplicationParametersException( new Status( IStatus.ERROR,
                                                            Activator.PLUGIN_ID,
                                                            Messages.getString( "ApplicationParametersRegistry.removing_not_allowed_exception" ) ) ); //$NON-NLS-1$
    }
  }

  /**
   * Method for adding user-defined parameters to this registry. All listeners
   * are informed that new parameter was added.
   * 
   * @param appName application name
   * @param appPath application executable path, this is optional and may be
   *            <code>null</code>
   * @param xmlPath path to file containing XML definition of JSDL wizard
   *            additional pages.
   * @param jsdlPath path to file containing base JSDL file which will be used
   *            as a "parent" for JSDL files created for this application.
   * @param vo virtual application for which this application is defined,
   *            <code>null</code> indicates that it is defined for all virtual
   *            organization available
   */
  public void addApplicationSpecificData( final String appName,
                                          final String appPath,
                                          final Path xmlPath,
                                          final Path jsdlPath,
                                          final IVirtualOrganization vo )
  {
    GridApplicationParameters newObject = new GridApplicationParameters( appName,
                                                                         appPath,
                                                                         xmlPath,
                                                                         jsdlPath,
                                                                         vo );
    if( !this.userAppsParamsList.contains( newObject ) ) {
      try {
        saveObjectToDisc( newObject );
        this.userAppsParamsList.add( newObject );
        notifyListeners();
      } catch( IOException exc ) {
        // TODO katis: problem dialog
        Activator.logException( exc );
      }
    }
  }

  /**
   * Changes given {@link GridApplicationParameters}. In this object given
   * values will be set. All listeners are notified of this change. </br>Note
   * that this can be done only for user-defined parameters - not for those
   * fetched from info system, as they are defined on "server side" and
   * modifying them from registy might result in confusion if they are changed
   * also on "the server side" or not.
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
   * @throws ApplicationParametersException is thrown when registry doesn't
   *             contain user-defined parameter equal to one passed to this
   *             method
   */
  public void editApplicationSpecificData( final GridApplicationParameters oldASO,
                                           final String newAppName,
                                           final String newAppPath,
                                           final String newXMLPath,
                                           final String newJSDLPath )
    throws ApplicationParametersException
  {
    IPath path = Activator.getDefault().getStateLocation();
    try {
      if( this.userAppsParamsList.contains( oldASO ) ) {
        path = path.append( ApplicationParametersRegistry.DIRECTORY_NAME );
        path = path.append( this.appSpecObjectsToFiles.get( oldASO ) );
        FileWriter writer;
        writer = new FileWriter( path.toFile(), false );
        writer.write( ApplicationParametersRegistry.APP_NAME_PREFIX
                      + newAppName
                      + "\n" ); //$NON-NLS-1$
        writer.write( ApplicationParametersRegistry.APP_PATH_PREFIX
                      + newAppPath
                      + "\n" ); //$NON-NLS-1$
        writer.write( ApplicationParametersRegistry.XML_PATH_PREFIX
                      + newXMLPath
                      + "\n" ); //$NON-NLS-1$
        writer.write( ApplicationParametersRegistry.JSDL_PATH_PEFIX
                      + newJSDLPath );
        writer.close();
        oldASO.setApplicationName( newAppName );
        oldASO.setAppPath( newAppPath );
        oldASO.setXmlPath( new Path( newXMLPath ) );
        oldASO.setJSDLPath( new Path( newJSDLPath ) );
        notifyListeners();
      } else {
        throw new ApplicationParametersException( new Status( IStatus.ERROR,
                                                              Activator.PLUGIN_ID,
                                                              Messages.getString( "ApplicationParametersRegistry.editing_not_allowed_exception" ) ) ); //$NON-NLS-1$
      }
    } catch( IOException exc ) {
      // TODO katis: problem dialog
      Activator.logException( exc );
    }
  }

  /**
   * Returns map of applications names as a keys and corresponding application's
   * parameters object's id as a value. The key - application name - is modified
   * this way that if there are more then one applications with the same name an
   * ordinal numeral is added to name. This change affects only list returned by
   * this method - not applications' parameters contained by this registry.
   * 
   * @param vo virtual organization for which list of application's parameters
   *            should be created. <code>null</code> value indicates all
   *            virtual organizations available.
   * @return map of applications names (modified with an ordinal number when
   *         needed) as a keys and corresponding application's parameters
   *         object's id as a value
   */
  public Map<String, Integer> getApplicationDataMapping( final IVirtualOrganization vo )
  {
    Map<String, Integer> result = new HashMap<String, Integer>();
    List<IGridApplicationParameters> chosenParams = new ArrayList<IGridApplicationParameters>();
    Map<String, Integer> nameVsQuantity = new HashMap<String, Integer>();
    List<IVirtualOrganization> vos = new ArrayList<IVirtualOrganization>();
    if( vo == null ) {
      IGridElement[] els = new IGridElement[ 0 ];
      try {
        els = GridModel.getVoManager().getChildren( new NullProgressMonitor() );
      } catch( ProblemException e ) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      for( IGridElement el : els ) {
        if( el instanceof IVirtualOrganization ) {
          vos.add( ( IVirtualOrganization )el );
        }
      }
    } else {
      vos.add( vo );
    }
    for( IGridApplicationParameters param : this.userAppsParamsList ) {
      if( vos.contains( param.getVO() ) || param.getVO() == null ) {
        chosenParams.add( param );
        String appName = param.getApplicationName();
        Integer occur = new Integer( 0 );
        if( nameVsQuantity.keySet().contains( appName ) ) {
          occur = nameVsQuantity.get( appName );
        }
        nameVsQuantity.put( appName, new Integer( occur.intValue() + 1 ) );
      }
    }
    for( IGridApplicationParameters param : this.generatedAppsParamList ) {
      if( vos.contains( param.getVO() ) || param.getVO() == null ) {
        chosenParams.add( param );
        String appName = param.getApplicationName();
        Integer occur = new Integer( 0 );
        if( nameVsQuantity.keySet().contains( appName ) ) {
          occur = nameVsQuantity.get( appName );
        }
        nameVsQuantity.put( appName, new Integer( occur.intValue() + 1 ) );
      }
    }
    for( IGridApplicationParameters param : chosenParams ) {
      String sufix = ""; //$NON-NLS-1$
      for( int i = 1; i <= nameVsQuantity.get( param.getApplicationName() )
        .intValue(); i++ )
      {
        if( i != 1 ) {
          sufix = "[" + i + "]"; //$NON-NLS-1$ //$NON-NLS-2$
        }
        result.put( param.getApplicationName() + sufix,
                    new Integer( param.getId() ) );
      }
    }
    return result;
  }

  /**
   * Returns application parameter object with given id.
   * 
   * @param paramId id of application parameter object
   * @return application parameter object with given id or <code>null</code>
   *         if registry does not hold object with such id
   */
  public IGridApplicationParameters getApplicationData( final int paramId ) {
    IGridApplicationParameters result = null;
    boolean haveIt = false;
    for( IGridApplicationParameters param : this.userAppsParamsList ) {
      if( param.getId() == paramId ) {
        result = param;
        haveIt = true;
        break;
      }
    }
    if( !haveIt ) {
      for( IGridApplicationParameters param : this.generatedAppsParamList ) {
        if( param.getId() == paramId ) {
          result = param;
          haveIt = true;
          break;
        }
      }
    }
    return result;
  }
}
