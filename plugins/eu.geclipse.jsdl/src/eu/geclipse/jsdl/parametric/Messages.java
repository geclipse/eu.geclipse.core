package eu.geclipse.jsdl.parametric;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

  private static final String BUNDLE_NAME = "eu.geclipse.jsdl.parametric.messages"; //$NON-NLS-1$
  public static String ParametricJsdlSaver_createFolderFailed;
  public static String ParametricJsdlSaver_deleteExistingFolderFailed;
  static {
    // initialize resource bundle
    NLS.initializeMessages( BUNDLE_NAME, Messages.class );
  }

  private Messages() {
  }
}
