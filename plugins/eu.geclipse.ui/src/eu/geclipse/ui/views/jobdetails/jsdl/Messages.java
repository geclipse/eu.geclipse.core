package eu.geclipse.ui.views.jobdetails.jsdl;

import org.eclipse.osgi.util.NLS;

/**
 *
 */
public class Messages extends NLS {

  /**
   * 
   */
  public static String SectionRequirements_itemCpuArch;
  /**
   * 
   */
  public static String SectionRequirements_itemFSDiskSpace;
  /**
   * 
   */
  public static String SectionRequirements_itemFSMountPoint;
  /**
   * 
   */
  public static String SectionRequirements_itemFSName;
  /**
   * 
   */
  public static String SectionRequirements_itemOS;
  /**
   * 
   */
  public static String SectionRequirements_itemOSVersion;
  /**
   * 
   */
  public static String SectionRequirements_sectionRequirements;
  
  private static final String BUNDLE_NAME = "eu.geclipse.ui.views.jobdetails.jsdl.messages"; //$NON-NLS-1$
  
  static {
    // initialize resource bundle
    NLS.initializeMessages( BUNDLE_NAME, Messages.class );
  }

  private Messages() {
    // empty implementation
  }
}
