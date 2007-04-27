package eu.geclipse.glite.editor;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

  // The plug-in ID
  public static final String PLUGIN_ID = "eu.geclipse.glite.editor"; //$NON-NLS-1$
  // The shared instance
  private static Activator plugin;
  // Resource bundle.
  private ResourceBundle resourceBundle;

  /**
   * The constructor
   */
  public Activator() {
    plugin = this;
    try {
      String name = Activator.class.getName();
      this.resourceBundle = ResourceBundle.getBundle( name );
    } catch( MissingResourceException x ) {
      this.resourceBundle = null;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
   */
  @Override
  public void start( final BundleContext context ) throws Exception
  {
    super.start( context );
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
   */
  @Override
  public void stop( final BundleContext context ) throws Exception
  {
    plugin = null;
    super.stop( context );
  }

  /**
   * Returns the shared instance.
   */
  public static Activator getDefault() {
    return plugin;
  }

  /**
   * Returns the string from the plugin's resource bundle, or '!' + 'key' + '!'
   * if not found.
   */
  public static String getResourceString( final String key ) {
    String result = null;
    ResourceBundle bundle = Activator.getDefault().getResourceBundle();
    try {
      result = bundle.getString( key );
    } catch( MissingResourceException e ) {
      result = "!" + key + "!";  //$NON-NLS-1$ //$NON-NLS-2$
    }
    return result;
  }

  /** Returns the plugin's resource bundle. */
  public ResourceBundle getResourceBundle() {
    return this.resourceBundle;
  }
}
