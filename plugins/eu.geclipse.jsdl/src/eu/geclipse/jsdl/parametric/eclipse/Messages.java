package eu.geclipse.jsdl.parametric.eclipse;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

  private static final String BUNDLE_NAME = "eu.geclipse.jsdl.parametric.eclipse.messages"; //$NON-NLS-1$
  public static String ParametricJsdlSaver_errCannotCreateFolder;
  public static String ParametricJsdlSaver_errCannotDeleteFolder;
  public static String ParametricJsdlSaver_errUnableToPrepareFolder;
  public static String ParametricJsdlSaver_errUnableToSaveGeneratedJsdl;
  public static String ParametricJsdlSaver_taskGeneratingJsdl;
  static {
    // initialize resource bundle
    NLS.initializeMessages( BUNDLE_NAME, Messages.class );
  }

  private Messages() {
  }
}
