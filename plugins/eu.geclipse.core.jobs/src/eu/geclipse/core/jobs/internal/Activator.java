package eu.geclipse.core.jobs.internal;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends Plugin {

  /** The plug-in ID */
  public static final String PLUGIN_ID = "eu.geclipse.core.jobs"; //$NON-NLS-1$

  // The shared instance
  private static Activator plugin;

  /**
   * The constructor
   */
  public Activator() {
    // empty implementation
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
   */
  @Override
  public void start( final BundleContext context ) throws Exception {
    super.start( context );
    plugin = this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
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
   * Helper method that enables debug logging to the console window
   * 
   * @param message String that will be written to the console
   */
  public static void consoleLog( final String message ) {
    if( Activator.getDefault().isDebugging() ) {
      System.out.println( message );
    }
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
    logStatus( status );
  }

  /**
   * Logs a status object to the eclipse logger.
   * 
   * @param status The status to be logged.
   */
  public static void logStatus( final IStatus status ) {
    getDefault().getLog().log( status );
  }
}
