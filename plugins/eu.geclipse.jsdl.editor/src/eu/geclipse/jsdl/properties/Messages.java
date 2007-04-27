package eu.geclipse.jsdl.properties;

import org.eclipse.osgi.util.NLS;

/**
 *
 */
public class Messages extends NLS {

  private static final String BUNDLE_NAME = "eu.geclipse.jsdl.properties.messages"; //$NON-NLS-1$
  /**
   * 
   */
  public static String JsdlJobDescSource_propertyCpuArchitecture;
  /**
   * 
   */
  public static String JsdlJobDescSource_propertyDiskSpace;
  /**
   * 
   */
  public static String JsdlJobDescSource_propertyDiskSpaceMax;
  /**
   * 
   */
  public static String JsdlJobDescSource_propertyDiskSpaceMin;
  /**
   * 
   */
  public static String JsdlJobDescSource_propertyFileSystem;
  /**
   * 
   */
  public static String JsdlJobDescSource_propertyMountPoint;
  /**
   * 
   */
  public static String JsdlJobDescSource_properyOSType;
  /**
   * 
   */
  public static String JsdlJobDescSource_properyOSVersion;
  /**
   * 
   */
  public static String JsdlJobDescSource_sectionRequirements;
  /**
   * 
   */
  public static String JsdlJobDescSource_sectionRequirementsFS;
  /**
   * 
   */
  public static String JsdlJobDescSource_sectionRequirementsOS;
  static {
    // initialize resource bundle
    NLS.initializeMessages( BUNDLE_NAME, Messages.class );
  }

  private Messages() {
    // empty constructor
  }
}
