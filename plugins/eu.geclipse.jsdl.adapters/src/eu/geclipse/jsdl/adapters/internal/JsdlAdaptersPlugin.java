package eu.geclipse.jsdl.adapters.internal;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class JsdlAdaptersPlugin extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "eu.geclipse.jsdl.adapters";

	// The shared instance
	private static JsdlAdaptersPlugin plugin;
	
	/**
	 * The constructor
	 */
	public JsdlAdaptersPlugin() {
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static JsdlAdaptersPlugin getDefault() {
		return plugin;
	}

}
