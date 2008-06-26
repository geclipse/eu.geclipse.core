package eu.geclipse.core.jobs.internal;

import org.eclipse.osgi.util.NLS;

/**
 *
 */
public class Messages extends NLS {
  /**
   * 
   */
  public static String ParametricJobID_jobIdParametric;
  private static final String BUNDLE_NAME = "eu.geclipse.core.jobs.internal.messages"; //$NON-NLS-1$
  
  static {
    // initialize resource bundle
    NLS.initializeMessages( BUNDLE_NAME, Messages.class );
  }

  private Messages() {
    //
  }
}
