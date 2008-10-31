package eu.geclipse.jsdl.parametric.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

  private static final String BUNDLE_NAME = "eu.geclipse.jsdl.parametric.internal.messages"; //$NON-NLS-1$
  public static String Assignment_errParameterEmpty;
  public static String Assignment_errParameterWithoutFunction;
  public static String FunctionIntegerLoop_errAttrIsMissing;
  public static String FunctionIntegerLoop_errIntegerExpected;
  public static String FunctionIntegerLoop_errOnlyOneLoopIsAllowed;
  public static String FunctionIntegerLoop_errStepIsZero;
  public static String ParametricJsdlGenerator_descParametersWithWrongNr;
  public static String ParametricJsdlGenerator_errAssignmentShouldBeElement;
  public static String ParametricJsdlGenerator_errSweepShouldBeElement;
  public static String ParametricJsdlGenerator_errSweepShouldContainAssignment;
  public static String ParametricJsdlGenerator_errTwoFunctionsDefined;
  public static String ParametricJsdlGenerator_taskGeneratingJsdl;
  static {
    // initialize resource bundle
    NLS.initializeMessages( BUNDLE_NAME, Messages.class );
  }

  private Messages() {
  }
}
