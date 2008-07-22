package eu.geclipse.servicejob.ui;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import eu.geclipse.core.model.IServiceJob;
import eu.geclipse.core.model.IServiceJobResult;
import eu.geclipse.servicejob.ui.properties.Factory;
import eu.geclipse.servicejob.ui.views.ServiceJobDetailsView;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

  /**
   * The plug-in ID
   */
  public static final String PLUGIN_ID = "eu.geclipse.servicejob.ui"; //$NON-NLS-1$
  /**
   * The shared instance
   */
  private static Activator plugin;
  private static Map<IServiceJobResult, File> testResultsVsExternalFiles = new HashMap<IServiceJobResult, File>();

  /**
   * The constructor
   */
  public Activator() {
    // empty implementation
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
   */
  @Override
  public void start( final BundleContext context ) throws Exception {
    super.start( context );
    plugin = this;
    File testFiles = getDefault().getStateLocation()
      .append( ServiceJobDetailsView.TEST_FILES_FOLDER )
      .toFile();
    if( testFiles.exists() && testFiles.isDirectory() ) {
      boolean isEmpty = true;
      for( String fileName : testFiles.list() ) {
        IPath path = new Path( testFiles.getAbsolutePath() );
        path = path.append( fileName );
        if( !path.toFile().delete() ) {
          isEmpty = false;
          break;
        }
      }
      if( isEmpty ) {
        testFiles.delete();
      }
    }
    Platform.getAdapterManager().registerAdapters( new Factory(),
                                                   IServiceJob.class );
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
   */
  @Override
  public void stop( final BundleContext context ) throws Exception {
    plugin = null;
    super.stop( context );
  }

  /**
   * Returns the shared instance
   * 
   * @return the shared instance
   */
  public static Activator getDefault() {
    return plugin;
  }

  /**
   * Logs an exception to the eclipse logger.
   * 
   * @param exc The exception to be logged.
   */
  public static void logException( final Throwable exc ) {
    String message = exc.getLocalizedMessage();
    if( message == null )
      message = exc.getClass().getName();
    IStatus status = new Status( IStatus.ERROR,
                                 PLUGIN_ID,
                                 IStatus.OK,
                                 message,
                                 exc );
    getDefault().getLog().log( status );
  }

  /**
   * ???
   * TODO katis
   * @param result
   * @param file
   */
  public static void addFileForTestResult( final IServiceJobResult result,
                                           final File file )
  {
    testResultsVsExternalFiles.put( result, file );
  }

  /**
   * ???
   * TODO katis
   * @param result
   * @return file
   */
  public static File getFileForTestResult( final IServiceJobResult result ) {
    return testResultsVsExternalFiles.get( result );
  }
}
