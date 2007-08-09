package eu.geclipse.ui.views.jobdetails;

import org.eclipse.osgi.util.NLS;

/**
 *
 */
public class Messages extends NLS {
  /**
   * 
   */
  public static String GridJobDetailsFactory_arguments;
  /**
   * 
   */
  public static String GridJobDetailsFactory_executable;
  /**
   * 
   */
  public static String GridJobDetailsFactory_id;
  /**
   * 
   */
  public static String GridJobDetailsFactory_input;
  /**
   * 
   */
  public static String GridJobDetailsFactory_lastUpdateTime;
  /**
   * 
   */
  public static String GridJobDetailsFactory_name;
  /**
   * 
   */
  public static String GridJobDetailsFactory_output;
  /**
   * 
   */
  public static String GridJobDetailsFactory_reason;
  /**
   * 
   */
  public static String GridJobDetailsFactory_status;
  /**
   * 
   */
  public static String JobDetailSectionsManager_application;
  /**
   * 
   */
  public static String JobDetailSectionsManager_general;
  /**
   * 
   */
  public static String JobDetailsView_emptyJobDescription;
  
  private static final String BUNDLE_NAME = "eu.geclipse.ui.views.jobdetails.messages"; //$NON-NLS-1$
  
  static {
    // initialize resource bundle
    NLS.initializeMessages( BUNDLE_NAME, Messages.class );
  }

  private Messages() {
    // empty implementation
  }
}
