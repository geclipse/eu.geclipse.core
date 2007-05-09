package eu.geclipse.ui.views.jobdetails;

import org.eclipse.osgi.util.NLS;

/**
 *
 */
public class Messages extends NLS {
  /**
   * 
   */
  public static String JobDetailsView_actionShowEmptyVals;

  /**
   * 
   */
  public static String JobDetailsView_EmptyJobDesc;

  /**
   * 
   */
  public static String JobDetailsView_name;

  /**
   * 
   */
  public static String SectionDescription_description;

  /**
   * 
   */
  public static String SectionDescription_exe;

  /**
   * 
   */
  public static String SectionDescription_exeArgs;

  /**
   * 
   */
  public static String SectionDescription_input;

  /**
   * 
   */
  public static String SectionDescription_name;

  /**
   * 
   */
  public static String SectionDescription_output;

  /**
   * 
   */
  public static String SectionGeneral_general;

  /**
   * 
   */
  public static String SectionGeneral_id;

  /**
   * 
   */
  public static String SectionGeneral_name;

  /**
   * 
   */
  public static String SectionGeneral_status;
  
  private static final String BUNDLE_NAME = "eu.geclipse.ui.views.jobdetails.messages"; //$NON-NLS-1$  
  
  static {
    // initialize resource bundle
    NLS.initializeMessages( BUNDLE_NAME, Messages.class );
  }

  private Messages() {
    // empty constructor
  }
}
