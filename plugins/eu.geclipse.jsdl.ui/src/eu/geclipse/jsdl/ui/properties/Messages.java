package eu.geclipse.jsdl.ui.properties;

import org.eclipse.osgi.util.NLS;

/**
 *
 */
public class Messages extends NLS {

  private static final String BUNDLE_NAME = "eu.geclipse.jsdl.ui.properties.messages"; //$NON-NLS-1$

  static {
    // initialize resource bundle
    NLS.initializeMessages( BUNDLE_NAME, Messages.class );
  }

  private Messages() {
    // empty constructor
  }

  /**
   * 
   */
  public static String JsdlJobDescSource_CpuArchitecture;
  /**
   * 
   */
  public static String JsdlJobDescSource_DiskSpace;
  /**
   * 
   */
  public static String JsdlJobDescSource_FileSystem;
  /**
   * 
   */
  public static String JsdlJobDescSource_Max;
  /**
   * 
   */
  public static String JsdlJobDescSource_Min;
  
  /**
   * 
   */
  public static String JsdlJobDescSource_MountPoint;
  /**
   * 
   */
  public static String JsdlJobDescSource_OS;
  /**
   * 
   */
  public static String JsdlJobDescSource_OSVersion;
  /**
   * 
   */
  public static String JsdlJobDescSource_Requirements;
  /**
   * 
   */
  public static String JsdlJobDescSource_RequirementsFS;
  /**
   * 
   */
  public static String JsdlJobDescSource_RequirementsOS;
}
